package com.fusm.eportafoli.repository;

import com.fusm.eportafoli.dto.FileDto;
import com.fusm.eportafoli.entity.Shared;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ISharedRepository extends JpaRepository<Shared, Integer> {

    @Query(
            value = "select faculty_id from shared " +
                    "where file_id = :fileId " +
                    "and faculty_id is not null ",
            nativeQuery = true
    )
    List<Integer> findFacultyByFile(
            @Param("fileId") Integer fileId
    );

    @Query(
            value = "select user_id from shared " +
                    "where file_id = :fileId " +
                    "and user_id is not null ",
            nativeQuery = true
    )
    List<String> findUserIdByFile(
            @Param("fileId") Integer fileId
    );

    List<Shared> findAllByFileId_FileId(Integer fileId);

    @Query(
            value = "select * from get_files_shared(:userId, :facultyId, :name, :startDate, :endDate, :keyWord, :type)",
            countQuery = "select count(*) from get_files_shared(:userId, :facultyId, :name, :startDate, :endDate, :keyWord, :type)",
            nativeQuery = true
    )
    Page<FileDto> findAllShared(
            Pageable pageable,
            @Param("userId") String userId,
            @Param("facultyId") Integer facultyId,
            @Param("name") String name,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            @Param("keyWord") String keyWord,
            @Param("type") Integer type
    );

}
