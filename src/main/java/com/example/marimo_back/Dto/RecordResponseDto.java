package com.example.marimo_back.Dto;

import com.example.marimo_back.domain.FailWord;
import com.example.marimo_back.domain.SuccessWord;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class RecordResponseDto {

    private String nickName;

    private LocalDate registerDate;

    private Long achievementRate;

    private List<String> mostSuccess;

    private List<String> mostFail;

    private Integer gameJoinNum;

    private Integer taleJoinNum;
}
