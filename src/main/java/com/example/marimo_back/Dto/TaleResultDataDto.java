package com.example.marimo_back.Dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TaleResultDataDto {


    private Long userId;

//    private Long id;

    private String taleName;

    private Integer lastpage;


}
