package com.example.marimo_back.controller;

import com.example.marimo_back.Dto.RecordResponseDto;
import com.example.marimo_back.domain.Users;
import com.example.marimo_back.repository.UserRepository;
import com.example.marimo_back.service.RecordService;
import com.example.marimo_back.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://192.168.1.5:8081")
public class RecordController {

    private final RecordService recordService;
    private final UserRepository userRepository;

    @ResponseBody
    @PostMapping("marimo/user/record")
    public RecordResponseDto getAchievement(@RequestBody Map<String, Long> userInfo) {
        Long userId = userInfo.get("userId");
        return recordService.getUserAchievement(userId);
    }



}
