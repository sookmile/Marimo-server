package com.example.marimo_back.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "TALE")
public class Tale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TALE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private Users user;

    @Column(name = "TALE_NAME")
    private String TaleName;

    @Column(name = "TALE_PLAYNUM")
    private Integer TalePlaynum;

    @Column(name = "LASTPAGE")
    private Integer Lastpage;

}
