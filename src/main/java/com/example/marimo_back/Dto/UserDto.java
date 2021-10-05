package com.example.marimo_back.Dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long id;

    private String nickname;

    private String identifier;

    private Integer character;

    private Boolean premium;

    private Integer playnum;

    private Boolean playnow;

    private Integer achieve;

    private LocalDate regidate;
}
