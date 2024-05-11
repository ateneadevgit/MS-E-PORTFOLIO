package com.fusm.eportafoli.repository;

import com.fusm.eportafoli.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFolderRepository extends JpaRepository<Folder, Integer> {

    @Query(
            value = "select * from folder " +
                    "where created_by = :userId " +
                    "order by created_at desc ",
            nativeQuery = true
    )
    List<Folder> findAllByUser(String userId);

}
