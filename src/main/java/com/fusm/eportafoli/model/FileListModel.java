package com.fusm.eportafoli.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileListModel {

    private Integer fileId;
    private String fileFormat;
    private String name;
    private String url;
    private Date createdAt;
    private String fileUrl;
    private String owner;
    private Boolean isFavorite;

}
