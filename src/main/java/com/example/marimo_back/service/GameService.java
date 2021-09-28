package com.example.marimo_back.service;

import com.example.marimo_back.Dto.GameDataResponseDto;
import com.example.marimo_back.Dto.GameRequestDto;
import com.example.marimo_back.domain.*;
import com.example.marimo_back.repository.GameRepository;
import com.example.marimo_back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<GameDataResponseDto> sendGameData() {
        String[] JOONG = {"ㅏ", "ㅐ", "ㅑ", "ㅒ", "ㅓ", "ㅔ", "ㅕ", "ㅖ", "ㅗ", "ㅘ", "ㅙ", "ㅚ", "ㅛ", "ㅜ", "ㅝ", "ㅞ", "ㅟ", "ㅠ", "ㅡ", "ㅢ", "ㅣ"};

        Map<String, String[]> wordData = new HashMap<>();
        wordData.put("ㅇㅇ", new String[]{"오이", "아이", "아야", "오이", "이유", "여우"});
        wordData.put("ㅇㄷ", new String[]{"어디", "오디"});
        wordData.put("ㅇㄹ", new String[]{"오리", "요리", "아래", "위로", "우리"});
        wordData.put("ㅇㅈ", new String[]{"의자", "여자", "어제", "의지", "우주"});
        wordData.put("ㅅㄹ", new String[]{"소리", "세로"});
        wordData.put("ㄱㄱ", new String[]{"고기", "가게", "거기", "가구",});
        String[] initials = {"ㅇㅇ", "ㅇㄷ", "ㅇㄹ", "ㅇㅈ", "ㅅㄹ", "ㄱㄱ"};

        List<GameDataResponseDto> dtoList = new ArrayList<>();

        int[] randomNum = new int[5];
        for (int i = 0; i < 5; i++) {
            randomNum[i] = (int) (Math.random() * wordData.size());
            for (int j = 0; j < i; j++) {
                if (randomNum[i] == randomNum[j]) {
                    i--;
                }
            }
        }

        for (int k = 0; k < 5; k++) {
            String[] words = wordData.get(initials[randomNum[k]]);

            List<String> vowelList = new ArrayList<>();
            List<String> wordList = new ArrayList<>();

            int[] randomNum2 = new int[2];
            for (int i = 0; i < 2; i++) {
                randomNum2[i] = (int) (Math.random() * words.length);
                for (int j = 0; j < i; j++) {
                    if (randomNum2[i] == randomNum2[j]) {
                        i--;
                    }
                }
            }

            for (int i = 0; i < 2; i++) {
                wordList.add(words[randomNum2[i]]);

                StringBuilder vowel = new StringBuilder();
                for (int j = 0; j < 2; j++) {
                    char c = words[randomNum2[i]].charAt(j);
                    vowel.append(JOONG[(char) ((c - 0xAC00) / 28 % 21)]);
                }
                vowelList.add(vowel.toString());
            }

            dtoList.add(GameDataResponseDto.builder().initial(initials[randomNum[k]]).vowel(vowelList).word(wordList).answer(wordList.get(0)).build());
        }

        return dtoList;
    }
}
