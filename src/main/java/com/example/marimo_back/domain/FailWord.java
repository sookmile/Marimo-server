package com.example.marimo_back.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "FAIL_WORD")
public class FailWord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WORD_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private Users user;

    @Column(name = "FAIL_WORD", nullable = false, length = 20)
    private String word;

    @Column(name = "FAIL_NUM", nullable = false)
    private Integer num;

    @Column(name = "PLAY_CATEGORY", nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    public void increaseNum(Integer num) {
        num += 1;
        this.num = num;
    }
}
