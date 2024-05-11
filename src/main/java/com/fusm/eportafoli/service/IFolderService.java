package com.fusm.eportafoli.service;

import com.fusm.eportafoli.entity.Folder;
import com.fusm.eportafoli.model.FolderRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IFolderService {

    List<Folder> getFoldersByUser(String createdBy);
    void createFolder(FolderRequest folderRequest);
    void updateFolder(FolderRequest folderRequest, Integer folderId);
    void deleteFolder(Integer folderId);

}
