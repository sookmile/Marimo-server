package com.example.marimo_back.service;

import com.example.marimo_back.domain.Users;
import com.example.marimo_back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void saveUser(Map<Object, String> userinfo) {
        String email = userinfo.get("email");
        String username = userinfo.get("username");

        List<Users> userList = userRepository.findUser(email);
        if (userList.size() > 0)
            return;
        Users user = Users.builder().email(email).username(username).playnum(0).premium(false).build();
        userRepository.save(user);
    }

    public void saveName(Map<Object, String> userinfo) {
        String email = userinfo.get("email");
        String nickname = userinfo.get("nickname");

        List<Users> userList = userRepository.findUser(email);
        Users user;
        try {
            user = userList.get(0);
        } catch(IndexOutOfBoundsException e) {
            return;
        }

        user.setNickname(nickname);
    }
}
