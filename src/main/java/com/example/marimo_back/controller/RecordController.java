package com.example.marimo_back.controller;

import com.example.marimo_back.Dto.RecordResponseDto;
import com.example.marimo_back.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class RecordController {

    private final RecordService recordService;

    @ResponseBody
    @PostMapping("marimo/user/record")
    public RecordResponseDto getAchievement(@RequestBody Map<String, Long> userInfo) {
        Long userId = userInfo.get("userId");
        return recordService.getUserAchievement(userId);
    }

}
