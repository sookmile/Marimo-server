package com.example.marimo_back.service;

import com.example.marimo_back.Dto.UserDto;
import com.example.marimo_back.domain.Users;
import com.example.marimo_back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDto saveUser(Map<Object, String> userinfo) {
        String email = userinfo.get("email");
        String username = userinfo.get("username");

        List<Users> userList = userRepository.findUser(email);
        if (userList.size() > 0) {
            // 이미 저장된 유저라면 저장된 정보 넘겨주기
            Users user;
            try {
                user = userList.get(0);
            } catch (IndexOutOfBoundsException e) {
                return null;
            }
            return makeDto(user);
        }
        // 새로 가입하는 유저라면 정보 저장하기
        Users newUser = Users.builder().email(email).username(username).playnum(0).premium(false).regidate(LocalDate.now()).build();
        userRepository.save(newUser);
        return makeDto(newUser);
    }


    public void saveName(Map<Object, String> userinfo) {
        Long id = Long.parseLong(userinfo.get("id"));
        String nickname = userinfo.get("nickname");

        Users user = userRepository.findById(id);

        user.setNickname(nickname);
    }

    public void saveCharacter(Map<Object, String> userinfo) {
        Long userId = Long.parseLong(userinfo.get("userId"));
        Integer character;
        try {
            character = Integer.parseInt(userinfo.get("character"));
        } catch (NumberFormatException e) {
            character = null;
        }

        Users user = userRepository.findById(userId);

        user.setCharacter(character);
    }

    public String getNickName(Long userId) {
        Users user = userRepository.findById(userId);
        return user.getNickname();
    }

    //==Dto 생성==//
    private UserDto makeDto(Users user) {
        return UserDto.builder().id(user.getId()).email(user.getEmail())
                .nickname(user.getNickname()).character(user.getCharacter()).playnow(user.getPlaynow())
                .playnum(user.getPlaynum()).premium(user.getPremium()).achieve(user.getAchieve()).regidate(user.getRegidate()).build();
    }

}
