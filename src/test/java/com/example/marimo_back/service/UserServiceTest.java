package com.example.marimo_back.service;

import com.example.marimo_back.Dto.UserDto;
import com.example.marimo_back.domain.Users;
import com.example.marimo_back.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Test
    public void 로그인() throws Exception {
        //given
        Map<Object, String> userInfo = new HashMap<>();
        String email = "1234@naver.com";
        userInfo.put("email", email);
        userInfo.put("username", "김유진");

        //when
        UserDto userDto = userService.saveUser(userInfo);
        Long userId = userDto.getId();
        userService.saveUser(userInfo);

        //then
        assertEquals(userRepository.findUser(email).size(), 1);
        assertEquals(userRepository.findById(userId).getEmail(), email);
    }

    @Test
    public void 음성인식이름저장() throws Exception {
        //given
        Users user = Users.builder().username("yujin").email("1234@naver.com").build();
        userRepository.save(user);
        Long id = user.getId();

        Map<Object, String> userInfo = new HashMap<>();
        userInfo.put("id", id.toString());
        userInfo.put("nickname", "유진");

        //when
        userService.saveName(userInfo);

        //then
        assertEquals(user.getNickname(), "유진");

    }

    @Test
    public void 유저캐릭터지정() throws Exception {
        //given
        Users user = Users.builder().username("yujin").email("1234@naver.com").build();
        userRepository.save(user);
        Long id = user.getId();

        Map<Object, String> userInfo = new HashMap<>();
        userInfo.put("userId", id.toString());
        userInfo.put("character", "2");

        //when
        userService.saveCharacter(userInfo);

        //then
        int character_id = user.getCharacter();
        assertEquals(character_id, 2);

    }

    @Test
    public void 닉네임가져오기() throws Exception {
        //given
        Users user = Users.builder().username("yujin").nickname("닉네임").email("1234@naver.com").build();
        userRepository.save(user);

        //when
        String nickName = userService.getNickName(user.getId());

        //then
        assertEquals(nickName, "닉네임");

    }

}