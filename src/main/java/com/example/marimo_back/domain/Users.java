package com.example.marimo_back.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "USERS")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(nullable = false, length = 20)
    private String username;

    @Column(length = 20)
    private String nickname;

    @Column(nullable = false, unique = true, length = 100)
    private String identifier;

    @Column(name = "CHARAC")
    private Integer character;

    @Column
    private Boolean premium;

    @Column
    private Integer playnum;

    @Column
    private Boolean playnow;

    @Column
    private Integer achieve;

    @Column
    private LocalDate regidate;
}
