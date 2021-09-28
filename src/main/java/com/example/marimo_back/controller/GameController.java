package com.example.marimo_back.controller;

import com.example.marimo_back.Dto.GameDataResponseDto;
import com.example.marimo_back.Dto.GameRequestDto;
import com.example.marimo_back.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class GameController {

    private final GameService gameService;

    @ResponseBody
    @PostMapping("marimo/game/save")
    public String saveGameResult(@RequestBody GameRequestDto dto) {
        gameService.saveResult(dto);
        return "success";
    }

    @ResponseBody
    @GetMapping("marimo/game/data")
    public List<GameDataResponseDto> sendData() {
        return gameService.sendGameData();
    }
}
