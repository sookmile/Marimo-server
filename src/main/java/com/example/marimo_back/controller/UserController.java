package com.example.marimo_back.controller;

import com.example.marimo_back.Dto.UserDto;
import com.example.marimo_back.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    @ResponseBody
    @PostMapping("marimo/login")
    public UserDto loginUser(@RequestBody Map<Object, String> userinfo) {
        return userService.saveUser(userinfo);
    }

    @ResponseBody
    @PostMapping("marimo/nickname")
    public String saveName(@RequestBody Map<Object, String> userinfo) {
        userService.saveName(userinfo);
        return "success";
    }

    @ResponseBody
    @PostMapping("marimo/character")
    public String saveCharacter(@RequestBody Map<Object, String> userinfo) {
        userService.saveCharacter(userinfo);
        return "success";
    }

    @ResponseBody
    @PostMapping("marimo/getNickName")
    public String getNickName(@RequestBody Map<String, Long> userinfo) {
        Long userId = userinfo.get("userId");
        return userService.getNickName(userId);
    }
}
