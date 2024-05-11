package com.fusm.eportafoli.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Builder
@Entity
@Data
@Table(name = "favorite")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Favorite {

    @Id
    @Column(name =  "id_shared", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sharedId;

    @NonNull
    @Column(name = "user_id", length = 100, nullable = true)
    private String userId;

    @NonNull
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "file_id", nullable = false)
    private File fileId;

}
