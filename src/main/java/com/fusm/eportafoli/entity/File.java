package com.fusm.eportafoli.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Builder
@Entity
@Data
@Table(name = "file")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class File {

    @Id
    @Column(name =  "id_file", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fileId;

    @NonNull
    @Column(name = "type", nullable = false)
    private Integer type;

    @NonNull
    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Column(name = "file_url", length = 300, nullable = true)
    private String fileUrl;

    @Column(name = "key_word", length = 300, nullable = true)
    private String keyWord;

    @Column(name = "url", length = 300, nullable = true)
    private String url;

    @Column(name = "file_size", nullable = true)
    private Double fileSize;

    @NonNull
    @Column(name = "privacity", nullable = false)
    private Integer privacity;

    @NonNull
    @Column(name = "file_format", nullable = false)
    private String fileFormat;

    @NonNull
    @Column(name = "created_by", length = 50, nullable = false)
    private String createdBy;

    @NonNull
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = true)
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "folder_id", nullable = false)
    private Folder folderId;

}
