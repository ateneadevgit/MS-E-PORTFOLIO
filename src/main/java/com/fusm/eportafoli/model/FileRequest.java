package com.fusm.eportafoli.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileRequest<T> {

    private Integer type;
    private String name;
    private String url;
    private T file;
    private Double fileSize;
    private String keyWord;
    private Integer privacity;
    private Integer folderId;
    private Integer facultyId;
    private List<String> sharedWith;
    private String createdBy;

}
