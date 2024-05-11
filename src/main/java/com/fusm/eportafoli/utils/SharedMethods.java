package com.fusm.eportafoli.utils;

import com.fusm.eportafoli.dto.FileDto;
import com.fusm.eportafoli.external.IDocumentManagerService;
import com.fusm.eportafoli.external.ISettingsService;
import com.fusm.eportafoli.model.FileListModel;
import com.fusm.eportafoli.model.FileModel;
import com.fusm.eportafoli.model.PageModel;
import com.fusm.eportafoli.model.ResponsePage;
import com.fusm.eportafoli.model.external.DocumentRequest;
import com.fusm.eportafoli.model.external.SettingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SharedMethods {

    @Autowired
    private IDocumentManagerService documentManagerService;

    @Autowired
    private ISettingsService settingsService;


    public String saveFile(FileModel fileModel, String createdBy) {
        return documentManagerService.saveFile(
                DocumentRequest.builder()
                        .documentBytes(fileModel.getFileContent())
                        .documentExtension(fileModel.getFileExtension())
                        .documentVersion("1")
                        .idUser(createdBy)
                        .build()
        );
    }

    public ResponsePage<FileDto> mapResponsePage(Page<FileDto> fileListModels, PageModel pageModel) {
        return ResponsePage.<FileDto>builder()
                .content(fileListModels.getContent())
                .numberOfPage(pageModel.getPage() + 1)
                .itemsPerPage(pageModel.getSize())
                .itemsOnThisPage(fileListModels.getContent().size())
                .totalNumberPages(fileListModels.getTotalPages())
                .totalNumberItems(fileListModels.getTotalElements())
                .hasNextPage(fileListModels.hasNext())
                .hasPreviousPage(fileListModels.hasPrevious())
                .build();
    }

    public Integer getSettingValue(String settingName) {
        return Integer.parseInt(
                settingsService.getSetting(
                        SettingRequest.builder()
                                .settingName(settingName)
                                .build()
                )
        );
    }

}
