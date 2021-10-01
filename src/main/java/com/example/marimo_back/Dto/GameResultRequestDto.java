package com.example.marimo_back.Dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GameResultRequestDto {

    private Long userId;

    private Integer score;
}
