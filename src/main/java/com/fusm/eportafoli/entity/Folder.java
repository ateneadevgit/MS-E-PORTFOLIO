package com.fusm.eportafoli.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Builder
@Entity
@Data
@Table(name = "folder")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Folder {

    @Id
    @Column(name =  "id_folder", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer folderId;

    @NonNull
    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @NonNull
    @Column(name = "icon", length = 200, nullable = false)
    private String icon;

    @NonNull
    @Column(name = "color", length = 50, nullable = false)
    private String color;

    @NonNull
    @Column(name = "created_by", length = 50, nullable = false)
    private String createdBy;

    @NonNull
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = true)
    private Date updatedAt;

}
