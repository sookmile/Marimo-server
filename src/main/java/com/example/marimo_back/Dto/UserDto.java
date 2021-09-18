package com.example.marimo_back.Dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long id;

    private String nickname;

    private String email;

    private Integer character;

    private Boolean premium;

    private Integer playnum;

    private Boolean playnow;

    private Integer achieve;
}
