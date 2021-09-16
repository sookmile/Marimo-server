package com.example.marimo_back.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "USER_PHOTOS")
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PHOTO_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private Users user;

    @Column(nullable = false)
    private String link;

    @Column(name = "PHOTO_DATE", nullable = false)
    private LocalDateTime date;

    @Column
    private Boolean success;

    @Column(length = 20)
    private String word;
}
