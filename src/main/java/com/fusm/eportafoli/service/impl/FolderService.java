package com.fusm.eportafoli.service.impl;

import com.fusm.eportafoli.entity.Folder;
import com.fusm.eportafoli.external.IDocumentManagerService;
import com.fusm.eportafoli.model.FolderRequest;
import com.fusm.eportafoli.repository.IFileRepository;
import com.fusm.eportafoli.repository.IFolderRepository;
import com.fusm.eportafoli.service.IFolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FolderService implements IFolderService {

    @Autowired
    private IFolderRepository folderRepository;

    @Autowired
    private IFileRepository fileRepository;

    @Autowired
    private IDocumentManagerService documentManagerService;


    @Override
    public List<Folder> getFoldersByUser(String createdBy) {
        return folderRepository.findAllByUser(createdBy);
    }

    @Override
    public void createFolder(FolderRequest folderRequest) {
        folderRepository.save(
                Folder.builder()
                        .name(folderRequest.getName())
                        .icon(folderRequest.getIcon())
                        .color(folderRequest.getColor())
                        .createdBy(folderRequest.getCreatedBy())
                        .createdAt(new Date())
                        .build()
        );
    }

    @Override
    public void updateFolder(FolderRequest folderRequest, Integer folderId) {
        Optional<Folder> folderOptional = folderRepository.findById(folderId);
        if (folderOptional.isPresent()) {
            Folder folder = folderOptional.get();
            folder.setName(folderRequest.getName());
            folder.setUpdatedAt(new Date());
            folderRepository.save(folder);
        }
    }

    @Override
    public void deleteFolder(Integer folderId) {
        Optional<Folder> folderOptional = folderRepository.findById(folderId);
        documentManagerService.deleteDocumentMassive(fileRepository.findFilesUrlByFolder(folderId));
        folderOptional.ifPresent(folder -> folderRepository.delete(folder));
    }

}
