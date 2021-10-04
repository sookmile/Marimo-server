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

        taleService.saveResult(dto);

        return "Success";
    }

    @ResponseBody
    @PostMapping("marimo/tale/feedback")
    public String saveTaleAndFeedBack (@RequestBody TaleDataRequestDto dto) {

        return taleService.saveTaleFeedback(dto);
    }


}
