package com.example.marimo_back.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "FAIL_DETAIL")
public class FailDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WORD_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private Users user;

    @Column(name = "FAIL_WORD", nullable = false, length = 20)
    private String word;

    @Column(name = "SPEAK_WORD", nullable = false, length = 20)
    private String speakWord;

    @Column(length = 10)
    private String phoneme;

    @Column
    private Integer feedback;
}
