package com.example.marimo_back.Dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GameWordRequestDto {

    private Long userId;

    private String word;

    private String speakWord;
}
