package com.example.marimo_back.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "users")
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
    private String email;

    @Column(name = "CHARAC", length = 20)
    private String character;

    @Column
    private Boolean premium;

    @Column
    private Integer playnum;

    @Column
    private Boolean playnow;

    @Column
    private Integer achieve;
}
