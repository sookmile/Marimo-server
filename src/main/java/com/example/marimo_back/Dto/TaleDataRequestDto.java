package com.example.marimo_back.Dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TaleDataRequestDto {

    private Long userId;

    private Integer lastpage;

    private String oWord;

    private String rWord;

    private String taleName;



}
