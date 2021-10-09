package com.example.marimo_back.Dto;

import com.example.marimo_back.domain.FailWord;
import com.example.marimo_back.domain.SuccessWord;
import io.swagger.models.auth.In;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Builder
@Getter
public class RecordResponseDto {

    private String nickName;

    private LocalDate registerDate;

    private Long achievementRate;

    private List<String> mostSuccess;

    private Integer successCount;

    private List<String> mostFail;

    private Long gameJoinNum;

    private Long taleJoinNum;

    private String analysis;

    private Map<String, Integer> successwordInGame;


    private Map<String, Integer> successwordInTale;

    private Map<String, Integer> successwordInExplore;

    private Integer talePlayCount;

    private Integer gamePlayCount;

//    private Map<String,Integer> taleBestWord;
//    private Map<String,Integer> gameBestWord;

    private String  taleBestWord;
    private String gameBestWord;

    private Map<String,Integer> mostSuccessWord;



}
