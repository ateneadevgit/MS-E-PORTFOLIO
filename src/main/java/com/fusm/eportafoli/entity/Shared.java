package com.fusm.eportafoli.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Builder
@Entity
@Data
@Table(name = "shared")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Shared {

    @Id
    @Column(name =  "id_shared", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sharedId;

    @Column(name = "user_id", length = 100, nullable = true)
    private String userId;

    @Column(name = "faculty_id", nullable = true)
    private Integer facultyId;

    @NonNull
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "file_id", nullable = false)
    private File fileId;

}
