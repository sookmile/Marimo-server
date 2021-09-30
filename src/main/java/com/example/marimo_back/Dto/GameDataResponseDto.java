package com.example.marimo_back.Dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GameDataResponseDto {

    private String initial;

    private List<String> vowel;

    private List<String> word;

    private String answer;

}
