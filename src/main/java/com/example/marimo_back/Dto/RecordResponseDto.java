package com.example.marimo_back.Dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RecordResponseDto {

    private String nickName;

    private Long achievementRate;
}
