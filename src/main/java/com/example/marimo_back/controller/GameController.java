package com.example.marimo_back.controller;

import com.example.marimo_back.Dto.GameDataResponseDto;
import com.example.marimo_back.Dto.GameResultRequestDto;
import com.example.marimo_back.Dto.GameWordRequestDto;
import com.example.marimo_back.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://192.168.1.5:8081")
//@CrossOrigin(origins = "http://192.168.1.5:3000")
public class GameController {

    private final GameService gameService;

    @ResponseBody
    @PostMapping("marimo/game/save")
    public String saveGameResult(@RequestBody GameResultRequestDto dto) {
        gameService.saveResult(dto);
        return "success";
    }

    @ResponseBody
    @GetMapping("marimo/game/data")
    public List<GameDataResponseDto> sendData() {
        return gameService.sendGameData();
    }

    @ResponseBody
    @PostMapping("marimo/game/feedback")
    public String getFeedback(@RequestBody GameWordRequestDto dto) {
        return gameService.saveAndFeedback(dto);
    }
}
