package com.fusm.eportafoli.repository;

import com.fusm.eportafoli.dto.FileDto;
import com.fusm.eportafoli.entity.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IFileRepository extends JpaRepository<File, Integer> {

    @Query(
            value = "select * from get_files(:createdBy, :folderId, :name, :startDate, :endDate)",
            countQuery = "select count(*) from get_files(:createdBy, :folderId, :name, :startDate, :endDate)",
            nativeQuery = true
    )
    Page<FileDto> findFilesByFolder(
            Pageable pageable,
            @Param("folderId") Integer folderId,
            @Param("createdBy") String createdBy,
            @Param("name") String name,
            @Param("startDate")Date startDate,
            @Param("endDate") Date endDate
    );

    @Query(
            value = "select file_url from file " +
                    "where file_url is not null " +
                    "and folder_id = :id ",
            nativeQuery = true
    )
    List<String> findFilesUrlByFolder(
            @Param("id") Integer folderId
    );

    @Query(
            value = "SELECT sum(file_size) " +
                    "FROM file " +
                    "where created_by = :userId ",
            nativeQuery = true
    )
    Double findConsumedSpace(
            @Param("userId") String createdBy
    );

}
