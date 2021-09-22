package com.example.marimo_back.service;

import com.example.marimo_back.Dto.GameRequestDto;
import com.example.marimo_back.domain.SuccessWord;
import com.example.marimo_back.domain.Users;
import com.example.marimo_back.repository.GameRepository;
import com.example.marimo_back.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class GameServiceTest {

    @Autowired
    GameService gameService;
    @Autowired
    GameRepository gameRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    public void 게임결과저장() throws Exception {
        //given
        Users user = Users.builder().username("yujin").email("1234@naver.com").build();
        userRepository.save(user);

        List<String> success = new ArrayList<>();
        success.add("word1");
        success.add("word2");
        List<String> fail = new ArrayList<>();
        fail.add("word3");
        fail.add("word4");
        List<String> fail2 = new ArrayList<>();
        GameRequestDto dto = GameRequestDto.builder().userId(user.getId()).success(success).fail(fail).category(1).score(90).build();
        GameRequestDto dto2 = GameRequestDto.builder().userId(user.getId()).success(success).fail(fail2).category(1).score(90).build();

        //when
        gameService.saveResult(dto);
        gameService.saveResult(dto2);

        //then
        int successNum = gameRepository.findSuccessWord("word1", user).get(0).getNum();
        assertEquals(successNum, 2);
        int failNum = gameRepository.findFailWord("word3", user).get(0).getNum();
        assertEquals(failNum, 1);
    }

}