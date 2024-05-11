package com.fusm.eportafoli.service;

import com.fusm.eportafoli.dto.FileDto;
import com.fusm.eportafoli.entity.File;
import com.fusm.eportafoli.model.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ISharedService {

    void createFileShared(SharedFileRequest sharedFileRequest, File fileId);
    Integer getFacultyByFileShared(Integer fileId);
    List<String> getSharedWithByFileShared(Integer fileId);
    void deleteSharedMassive(Integer fileId);
    ResponsePage<FileDto> getSharedFiles(FileSearch fileSearch, PageModel pageModel);

}
