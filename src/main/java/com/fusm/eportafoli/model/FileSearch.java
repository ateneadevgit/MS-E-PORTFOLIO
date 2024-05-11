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
public class FileSearch {

    private String name;
    private String keyWord;
    private Date startDate;
    private Date endDate;
    private String createdBy;
    private Integer facultyId;
    private Integer formatType;

}
