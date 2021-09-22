package com.example.marimo_back.Dto;

import com.example.marimo_back.domain.Category;
import lombok.Getter;

import java.util.List;

@Getter
public class GameRequestDto {

    private Long userId;

    private List<String> success;

    private List<String> fail;

    private Integer category;

    private Integer score;
}
