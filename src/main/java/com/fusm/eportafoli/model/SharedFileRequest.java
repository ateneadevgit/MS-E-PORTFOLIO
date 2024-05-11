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
public class SharedFileRequest {

    private Integer facultyId;
    private List<String> sharedWith;

}
