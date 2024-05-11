package com.fusm.eportafoli.service;

import com.fusm.eportafoli.dto.FileDto;
import com.fusm.eportafoli.model.FileListModel;
import com.fusm.eportafoli.model.FileSearch;
import com.fusm.eportafoli.model.PageModel;
import com.fusm.eportafoli.model.ResponsePage;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IFavoriteService {

    void deleteFavoriteMassive(Integer fileId);
    ResponsePage<FileDto> getFavoritesFiles(FileSearch fileSearch, PageModel pageModel);
    void addFavorite(Integer fileId, String userId, Boolean isFavorite);

}
