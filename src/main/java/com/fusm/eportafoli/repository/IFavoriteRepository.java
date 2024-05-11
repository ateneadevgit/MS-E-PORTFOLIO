package com.fusm.eportafoli.repository;

import com.fusm.eportafoli.dto.FileDto;
import com.fusm.eportafoli.entity.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IFavoriteRepository extends JpaRepository<Favorite, Integer> {

    List<Favorite> findAllByFileId_FileId(Integer fileId);

    @Query(
            value = "select * from get_files_favorite(:createdBy, :name, :startDate, :endDate)",
            countQuery = "select count(*) from get_files_favorite(:createdBy, :name, :startDate, :endDate)",
            nativeQuery = true
    )
    Page<FileDto> findAllFavorite(
            Pageable pageable,
            @Param("createdBy") String createdBy,
            @Param("name") String name,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );

    @Query(
            value = "select * from favorite " +
                    "where user_id = :userId " +
                    "and file_id = :fileId ",
            nativeQuery = true
    )
    List<Favorite> findFavoriteByUserAndFile(
            @Param("fileId") Integer fileId,
            @Param("userId") String userId
    );

}
