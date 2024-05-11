package com.fusm.eportafoli.service;

import com.fusm.eportafoli.dto.FileDto;
import com.fusm.eportafoli.model.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IFileService {

    ResponsePage<FileDto> getFilesByFolder(FileSearch fileSearch, PageModel pageModel, Integer folderId);
    FileRequest<String> getFileById(Integer fileId);
    void createFile(FileRequest<FileModel> fileRequest);
    void deleteFile(Integer fileId);
    void updateFile(FileRequest<FileModel> fileRequest, Integer fileId);
    ConsumedModel getConsumedSpace(String userId);

}
