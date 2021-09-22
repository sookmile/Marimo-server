package com.example.marimo_back.service;

import com.example.marimo_back.Dto.GameRequestDto;
import com.example.marimo_back.domain.*;
import com.example.marimo_back.repository.GameRepository;
import com.example.marimo_back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    public void saveResult(GameRequestDto dto) {
        long userId = dto.getUserId();
        Users user = userRepository.findById(userId);

        //게임 저장
        Game game = Game.builder().user(user).category(dto.getCategory()).score(dto.getScore()).playtime(LocalDateTime.now()).build();
        gameRepository.saveGame(game);

        //성공 단어 저장
        for (String word : dto.getSuccess()) {
            List<SuccessWord> successWordList = gameRepository.findSuccessWord(word, user);
            if (successWordList.size() == 0) {
                SuccessWord successWord = SuccessWord.builder().user(user).word(word).num(1).category(Category.GAME).build();
                gameRepository.saveSuccessWord(successWord);
            } else {
                SuccessWord existWord = successWordList.get(0);
                existWord.increaseNum(existWord.getNum());
            }
        }
        //실패 단어 저장
        for (String word : dto.getFail()) {
            List<FailWord> failWordList = gameRepository.findFailWord(word, user);
            if (failWordList.size() == 0) {
                FailWord failWord = FailWord.builder().user(user).word(word).num(1).category(Category.GAME).build();
                gameRepository.saveFailWord(failWord);
            } else {
                FailWord existWord = failWordList.get(0);
                existWord.increaseNum(existWord.getNum());
            }
        }

    }
}
