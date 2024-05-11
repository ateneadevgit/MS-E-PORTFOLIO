package com.fusm.eportafoli.service.impl;

import com.fusm.eportafoli.dto.FileDto;
import com.fusm.eportafoli.entity.Favorite;
import com.fusm.eportafoli.entity.File;
import com.fusm.eportafoli.model.FileListModel;
import com.fusm.eportafoli.model.FileSearch;
import com.fusm.eportafoli.model.PageModel;
import com.fusm.eportafoli.model.ResponsePage;
import com.fusm.eportafoli.repository.IFavoriteRepository;
import com.fusm.eportafoli.repository.IFileRepository;
import com.fusm.eportafoli.service.IFavoriteService;
import com.fusm.eportafoli.utils.SharedMethods;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FavoriteService implements IFavoriteService {

    @Autowired
    private IFavoriteRepository favoriteRepository;

    @Autowired
    private IFileRepository fileRepository;

    @Autowired
    private SharedMethods sharedMethods;


    @Override
    public void deleteFavoriteMassive(Integer fileId) {
        List<Favorite> favoriteList = favoriteRepository.findAllByFileId_FileId(fileId);
        favoriteRepository.deleteAll(favoriteList);
    }

    @Override
    public ResponsePage<FileDto> getFavoritesFiles(FileSearch fileSearch, PageModel pageModel) {
        Pageable pageable = PageRequest.of(pageModel.getPage(), pageModel.getSize());
        Page<FileDto> favoriteList = favoriteRepository.findAllFavorite(
                pageable, fileSearch.getCreatedBy(), fileSearch.getName(), fileSearch.getStartDate(), fileSearch.getEndDate());
        return sharedMethods.mapResponsePage(favoriteList, pageModel);
    }

    @Override
    public void addFavorite(Integer fileId, String userId, Boolean isFavorite) {
        if (isFavorite) {
            Optional<File> fileOptional = fileRepository.findById(fileId);
            fileOptional.ifPresent(file -> favoriteRepository.save(
                    Favorite.builder()
                            .userId(userId)
                            .createdAt(new Date())
                            .fileId(file)
                            .build()
            ));
        } else {
            List<Favorite> favoriteList = favoriteRepository.findFavoriteByUserAndFile(fileId, userId);
            favoriteRepository.deleteAll(favoriteList);
        }
    }

}
