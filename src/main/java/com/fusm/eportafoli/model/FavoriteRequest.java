package com.fusm.eportafoli.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteRequest {

    private Boolean isFavorite;
    private String createdBy;

}
