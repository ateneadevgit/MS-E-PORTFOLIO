package com.fusm.eportafoli.service.impl;

import com.fusm.eportafoli.dto.FileDto;
import com.fusm.eportafoli.entity.File;
import com.fusm.eportafoli.entity.Folder;
import com.fusm.eportafoli.external.IDocumentManagerService;
import com.fusm.eportafoli.external.INotificationService;
import com.fusm.eportafoli.model.*;
import com.fusm.eportafoli.model.external.NotificationRequest;
import com.fusm.eportafoli.model.external.Template;
import com.fusm.eportafoli.repository.IFileRepository;
import com.fusm.eportafoli.repository.IFolderRepository;
import com.fusm.eportafoli.service.IFavoriteService;
import com.fusm.eportafoli.service.IFileService;
import com.fusm.eportafoli.service.ISharedService;
import com.fusm.eportafoli.utils.Constant;
import com.fusm.eportafoli.utils.SharedMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FileService implements IFileService {

    @Autowired
    private IFileRepository fileRepository;

    @Autowired
    private ISharedService sharedService;

    @Autowired
    private SharedMethods sharedMethods;

    @Autowired
    private IFolderRepository folderRepository;

    @Autowired
    private IFavoriteService favoriteService;

    @Autowired
    private IDocumentManagerService documentManagerService;

    @Autowired
    private INotificationService notificationService;


    @Override
    public ResponsePage<FileDto> getFilesByFolder(FileSearch fileSearch, PageModel pageModel, Integer folderId) {
        Pageable pageable = PageRequest.of(pageModel.getPage(), pageModel.getSize());
        Page<FileDto> fileList = fileRepository.findFilesByFolder(
                pageable, folderId, fileSearch.getCreatedBy(), fileSearch.getName(), fileSearch.getStartDate(), fileSearch.getEndDate());
        return sharedMethods.mapResponsePage(fileList, pageModel);
    }

    @Override
    public FileRequest<String> getFileById(Integer fileId) {
        Optional<File> fileOptional = fileRepository.findById(fileId);
        FileRequest<String> fileRequest = new FileRequest<String>();
        if (fileOptional.isPresent()) {
            File file = fileOptional.get();
            fileRequest = FileRequest.<String>builder()
                    .type(file.getType())
                    .name(file.getName())
                    .url(file.getUrl())
                    .file(file.getFileUrl())
                    .keyWord(file.getKeyWord())
                    .privacity(file.getPrivacity())
                    .folderId(file.getFolderId().getFolderId())
                    .facultyId(sharedService.getFacultyByFileShared(fileId))
                    .sharedWith(sharedService.getSharedWithByFileShared(fileId))
                    .build();
        }
        return fileRequest;
    }

    @Override
    public void createFile(FileRequest<FileModel> fileRequest) {
        Optional<Folder> folderOptional = folderRepository.findById(fileRequest.getFolderId());
        if (folderOptional.isPresent()) {
            File file = fileRepository.save(
                    File.builder()
                            .type(fileRequest.getType())
                            .name(fileRequest.getName())
                            .fileUrl((!fileRequest.getType().equals(Constant.FILE_LINK)) ?
                                    sharedMethods.saveFile(fileRequest.getFile(), fileRequest.getCreatedBy()) : null)
                            .keyWord(fileRequest.getKeyWord())
                            .url(fileRequest.getUrl())
                            .fileSize(fileRequest.getFileSize())
                            .privacity(fileRequest.getPrivacity())
                            .fileFormat((fileRequest.getType().equals(Constant.FILE_LINK)) ?
                                    "Link" : fileRequest.getFile().getFileExtension())
                            .createdBy(fileRequest.getCreatedBy())
                            .createdAt(new Date())
                            .folderId(folderOptional.get())
                            .build()
            );
            sharedService.createFileShared(
                    SharedFileRequest.builder()
                            .facultyId((fileRequest.getPrivacity().equals(Constant.SHARE_PUBLIC)) ? fileRequest.getFacultyId() : null)
                            .sharedWith((fileRequest.getPrivacity().equals(Constant.SHARE_STUDENT) || fileRequest.getPrivacity().equals(Constant.SHARE_TEACHER)) ?
                                    fileRequest.getSharedWith() : new ArrayList<>())
                            .build(),
                    file
            );

            if (fileRequest.getPrivacity().equals(Constant.SHARE_STUDENT) || fileRequest.getPrivacity().equals(Constant.SHARE_TEACHER)) {
                sendNotification(file, fileRequest.getSharedWith().toArray(new String[0]));
            }
        }
    }

    @Override
    public void deleteFile(Integer fileId) {
        Optional<File> fileOptional = fileRepository.findById(fileId);
        if (fileOptional.isPresent()) {
            if (fileOptional.get().getFileUrl() != null)
                documentManagerService.deleteDocumentMassive(List.of(fileOptional.get().getFileUrl()));
            fileRepository.delete(fileOptional.get());
        }
    }

    @Override
    public void updateFile(FileRequest<FileModel> fileRequest, Integer fileId) {
        Optional<File> fileOptional = fileRepository.findById(fileId);
        Optional<Folder> folderOptional = folderRepository.findById(fileRequest.getFolderId());
        if (fileOptional.isPresent() && folderOptional.isPresent()) {
            File file = fileOptional.get();
            file.setName(fileRequest.getName());
            file.setKeyWord(fileRequest.getKeyWord());
            file.setFolderId(folderOptional.get());
            file.setPrivacity(fileRequest.getPrivacity());
            List<String> sharedUsers = sharedService.getSharedWithByFileShared(fileId);
            sharedService.deleteSharedMassive(fileId);
            favoriteService.deleteFavoriteMassive(fileId);
            sharedService.createFileShared(
                    SharedFileRequest.builder()
                            .facultyId((fileRequest.getPrivacity().equals(Constant.SHARE_PUBLIC)) ? fileRequest.getFacultyId() : null)
                            .sharedWith((fileRequest.getPrivacity().equals(Constant.SHARE_STUDENT) || fileRequest.getPrivacity().equals(Constant.SHARE_TEACHER)) ?
                                    fileRequest.getSharedWith() : new ArrayList<>())
                            .build(),
                    file
            );
            fileRepository.save(file);
            if (fileRequest.getPrivacity().equals(Constant.SHARE_STUDENT) || fileRequest.getPrivacity().equals(Constant.SHARE_TEACHER)) {
                fileRequest.getSharedWith().removeAll(sharedUsers);
                sendNotification(file, fileRequest.getSharedWith().toArray(new String[0]));
            }
        }
    }

    @Override
    public ConsumedModel getConsumedSpace(String userId) {
        Double consumed = fileRepository.findConsumedSpace(userId);
        if (consumed == null) consumed = 0.0;
        int consumeAllowed = sharedMethods.getSettingValue(Constant.SPACE_ATENEA);
        return ConsumedModel.builder()
                .consumedPercentage((consumed * 100) / consumeAllowed)
                .consumed(consumed)
                .avaliable(consumeAllowed)
                .build();
    }

    private void sendNotification(File file, String[] sendTo) {
        Template eportafolioTemplate = notificationService.getTemplate(Constant.NOTIFICATION_EPORTAFOLIO);
        String template  = eportafolioTemplate.getEmailBody();

        template = template.replace(Constant.ORIGIN_USER_FLAG, file.getCreatedBy());
        template = template.replace(Constant.FILE_NAME_FLAG, file.getName());

        notificationService.sendNotification(
                NotificationRequest.builder()
                        .sendTo(sendTo)
                        .subject(eportafolioTemplate.getSubject())
                        .body(template)
                        .build()
        );
    }

}
