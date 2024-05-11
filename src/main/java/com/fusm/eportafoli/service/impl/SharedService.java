package com.fusm.eportafoli.service.impl;

import com.fusm.eportafoli.dto.FileDto;
import com.fusm.eportafoli.entity.File;
import com.fusm.eportafoli.entity.Shared;
import com.fusm.eportafoli.model.*;
import com.fusm.eportafoli.repository.ISharedRepository;
import com.fusm.eportafoli.service.ISharedService;
import com.fusm.eportafoli.utils.SharedMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SharedService implements ISharedService {

    @Autowired
    private ISharedRepository sharedRepository;

    @Autowired
    private SharedMethods sharedMethods;


    @Override
    public void createFileShared(SharedFileRequest sharedFileRequest, File fileId) {
        for (String sharedWith : sharedFileRequest.getSharedWith()) {
            saveFileShared(null, sharedWith, fileId);
        }
        if (sharedFileRequest.getFacultyId() != null) {
            saveFileShared(sharedFileRequest.getFacultyId(), null, fileId);
        }
    }

    @Override
    public Integer getFacultyByFileShared(Integer fileId) {
        List<Integer> faculty = sharedRepository.findFacultyByFile(fileId);
        return (!faculty.isEmpty()) ? faculty.get(0) : null;
    }

    @Override
    public List<String> getSharedWithByFileShared(Integer fileId) {
        return sharedRepository.findUserIdByFile(fileId);
    }

    @Override
    public void deleteSharedMassive(Integer fileId) {
        List<Shared> sharedList = sharedRepository.findAllByFileId_FileId(fileId);
        sharedRepository.deleteAll(sharedList);
    }

    @Override
    public ResponsePage<FileDto> getSharedFiles(FileSearch fileSearch, PageModel pageModel) {
        Pageable pageable = PageRequest.of(pageModel.getPage(), pageModel.getSize());
        Page<FileDto> sharedList = sharedRepository.findAllShared(
                pageable, fileSearch.getCreatedBy(), fileSearch.getFacultyId(), fileSearch.getName(),
                fileSearch.getStartDate(), fileSearch.getEndDate(), fileSearch.getKeyWord(), fileSearch.getFormatType());
        return sharedMethods.mapResponsePage(sharedList, pageModel);
    }

    private void saveFileShared(Integer facultyId, String userId, File file) {
        sharedRepository.save(
                Shared.builder()
                        .userId(userId)
                        .fileId(file)
                        .facultyId(facultyId)
                        .createdAt(new Date())
                        .build()
        );
    }

}
