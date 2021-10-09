package com.example.marimo_back.controller;


import com.example.marimo_back.Dto.GameResultRequestDto;
import com.example.marimo_back.Dto.TaleDataRequestDto;
import com.example.marimo_back.Dto.TaleResultDataDto;
import com.example.marimo_back.service.TaleService;
import com.example.marimo_back.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

//    침대, 이불, 시계, 축구공, 액자, 냉장고, 사과포도, 수박, 바나나, 달력, 기차, 자전거, 색연필, 도토리, 장갑
}
