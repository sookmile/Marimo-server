package com.example.marimo_back.controller;


import com.example.marimo_back.Dto.GameResultRequestDto;
import com.example.marimo_back.Dto.TaleDataRequestDto;
import com.example.marimo_back.Dto.TaleResultDataDto;
import com.example.marimo_back.service.TaleService;
import com.example.marimo_back.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class TaleController {

    private final TaleService taleService;


    @ResponseBody
    @PostMapping("marimo/tale/save")
    public String saveTale(@RequestBody TaleResultDataDto dto){

        System.out.println(dto.getTaleName()+dto.getLastpage()+dto.getUserId());

        taleService.saveResult(dto);

        return "Success";
    }

    @ResponseBody
    @PostMapping("marimo/tale/feedback")
    public String saveTaleAndFeedBack (@RequestBody TaleDataRequestDto dto) {

        System.out.println(dto.getOWord()+dto.getRWord()+dto.getLastpage());
        return taleService.saveTaleFeedback(dto);
    }

    @ResponseBody
    @PostMapping("marimo/tale/speechuri")
    public String sendSpeeachUri (@RequestBody Map<String,String> oWord) {

        String[] requestList={"침대", "이불", "시계", "축구공", "액자", "냉장고", "사과", "포도", "수박", "바나나", "달력", "기차", "자전거", "색연필", "도토리", "장갑"};
        for(int i=0; i<requestList.length; i++){
            if(oWord.get("word").equals(requestList[i])){
                System.out.println(requestList[i]);
//                return "http://"+requestList[i];
                return "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4";
            }
        }
        return "";
    }
//    침대, 이불, 시계, 축구공, 액자, 냉장고, 사과포도, 수박, 바나나, 달력, 기차, 자전거, 색연필, 도토리, 장갑
}
