package com.fusm.eportafoli.dto;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public interface FileDto {

    @Value("#{target.file_id}")
    Integer getFileId();

    @Value("#{target.file_format}")
    String getFileFormat();

    @Value("#{target.name}")
    String getName();

    @Value("#{target.url}")
    String getUrl();

    @Value("#{target.created_at}")
    Date getCreatedAt();

    @Value("#{target.file_url}")
    String getFileUrl();

    @Value("#{target.created_by}")
    String getCreatedBy();

    @Value("#{target.is_favorite}")
    Boolean getIsFavorite();

    @Value("#{target.type}")
    Integer getType();

}
