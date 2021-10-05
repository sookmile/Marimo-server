package com.example.marimo_back.service;

import com.example.marimo_back.Dto.GameDataResponseDto;
import com.example.marimo_back.Dto.GameResultRequestDto;
import com.example.marimo_back.Dto.GameWordRequestDto;
import com.example.marimo_back.domain.*;
import com.example.marimo_back.repository.GameRepository;
import com.example.marimo_back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    public void saveResult(GameResultRequestDto dto) {
        long userId = dto.getUserId();
        Users user = userRepository.findById(userId);

        Game game = Game.builder().user(user).score(dto.getScore()).playtime(LocalDateTime.now()).build();
        gameRepository.saveGame(game);

    }

    public List<GameDataResponseDto> sendGameData() {
        String[] JOONG = {"ㅏ", "ㅐ", "ㅑ", "ㅒ", "ㅓ", "ㅔ", "ㅕ", "ㅖ", "ㅗ", "ㅘ", "ㅙ", "ㅚ", "ㅛ", "ㅜ", "ㅝ", "ㅞ", "ㅟ", "ㅠ", "ㅡ", "ㅢ", "ㅣ"};

        Map<String, String[]> wordData = new HashMap<>();
        wordData.put("ㅇㅇ", new String[]{"오이", "아이", "아야", "오이", "이유", "여우"});
        wordData.put("ㅇㄷ", new String[]{"어디", "오디"});
        wordData.put("ㅇㄹ", new String[]{"오리", "요리", "아래", "위로", "우리", "유리"});
        wordData.put("ㅇㅈ", new String[]{"의자", "여자", "어제", "의지", "우주"});
        wordData.put("ㅅㄹ", new String[]{"소리", "세로"});
        wordData.put("ㄱㄱ", new String[]{"고기", "가게", "거기", "가구",});
        wordData.put("ㄴㅁ", new String[]{"나무", "네모"});
        wordData.put("ㅈㄹ", new String[]{"자리", "자라"});
        wordData.put("ㅈㄷ", new String[]{"지도", "자두"});
        wordData.put("ㅇㅅ", new String[]{"이사", "의사"});
        wordData.put("ㄱㅅ", new String[]{"가시", "기사"});
        wordData.put("ㅅㅈ", new String[]{"사자", "수저"});
        wordData.put("ㅎㄹ", new String[]{"하루", "허리"});
        wordData.put("ㄱㄷ", new String[]{"기도", "구두", "기대"});
        wordData.put("ㅇㄱ", new String[]{"아기", "야구"});

        String[] initials = {"ㅇㅇ", "ㅇㄷ", "ㅇㄹ", "ㅇㅈ", "ㅅㄹ", "ㄱㄱ", "ㄴㅁ", "ㅈㄹ", "ㅈㄷ", "ㅇㅅ", "ㄱㅅ", "ㅅㅈ", "ㅎㄹ", "ㄱㄷ", "ㅇㄱ"};

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

            int random = (int) (Math.random() * 2);
            String answer = wordList.get(random);
            String vowelAnswer = vowelList.get(random);


            dtoList.add(GameDataResponseDto.builder().initial(initials[randomNum[k]]).vowel(vowelList).word(wordList).answer(answer).vowelAnswer(vowelAnswer).build());
        }

        return dtoList;
    }

    public String saveAndFeedback(GameWordRequestDto dto) {
        Long userId = dto.getUserId();
        Users user = userRepository.findById(userId);

        String word = dto.getWord();
        String speakWord = dto.getSpeakWord();

        // 발음 성공 시 성공 단어 저장
        if (word.equals(speakWord)) {
            List<SuccessWord> successWordList = gameRepository.findSuccessWord(word, user);
            if (successWordList.size() == 0) {
                SuccessWord successWord = SuccessWord.builder().user(user).word(word).num(1).category(Category.GAME).build();
                gameRepository.saveSuccessWord(successWord);
            } else {
                SuccessWord existWord = successWordList.get(0);
                existWord.increaseNum(existWord.getNum());
            }
            return "발음을 아주 잘했어요!";
        }

        // 발음 실패 시
        ArrayList<String> phoneme = new ArrayList<>();
        ArrayList<Integer> feedback = new ArrayList<>();

        String[] CHO = {"ㄱ", "ㄲ", "ㄴ", "ㄷ", "ㄸ", "ㄹ", "ㅁ", "ㅂ", "ㅃ", "ㅅ", "ㅆ", "ㅇ", "ㅈ", "ㅉ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ"};
        String[] JOONG = {"ㅏ", "ㅐ", "ㅑ", "ㅒ", "ㅓ", "ㅔ", "ㅕ", "ㅖ", "ㅗ", "ㅘ", "ㅙ", "ㅚ", "ㅛ", "ㅜ", "ㅝ", "ㅞ", "ㅟ", "ㅠ", "ㅡ", "ㅢ", "ㅣ"};
        String[] JONG = {"", "ㄱ", "ㄲ", "ㄳ", "ㄴ", "ㄵ", "ㄶ", "ㄷ", "ㄹ", "ㄺ", "ㄻ", "ㄼ", "ㄽ", "ㄾ", "ㄿ", "ㅀ", "ㅁ", "ㅂ", "ㅄ", "ㅅ", "ㅆ", "ㅇ", "ㅈ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ"};

        ArrayList<String> wordList = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            char chosung = (char) ((c - 0xAC00) / 28 / 21);
            char joongsung = (char) ((c - 0xAC00) / 28 % 21);
            char jongsung = (char) ((c - 0xAC00) % 28);
            wordList.add(CHO[chosung]);
            wordList.add(JOONG[joongsung]);
            wordList.add(JONG[jongsung]);
        }

        ArrayList<String> speakWordList = new ArrayList<>();
        for (int i = 0; i < speakWord.length(); i++) {
            char c = speakWord.charAt(i);
            char chosung = (char) ((c - 0xAC00) / 28 / 21);
            char joongsung = (char) ((c - 0xAC00) / 28 % 21);
            char jongsung = (char) ((c - 0xAC00) % 28);
            speakWordList.add(CHO[chosung]);
            speakWordList.add(JOONG[joongsung]);
            speakWordList.add(JONG[jongsung]);
        }

        StringBuilder result = new StringBuilder();

        if (word.length() != speakWord.length()) {
            if (wordList.get(wordList.size() - 1).equals("")) {
                result.append(word).append("라고 다시 발음해보세요!");
            } else {
                result.append(word).append("이라고 다시 발음해보세요!");
            }
        } else {
            int count = 0;
            for (int i = 0; i < speakWordList.size(); i++) {
                String w = wordList.get(i);
                String sw = speakWordList.get(i);
                if (!w.equals(sw)) {
                    count++;
                    if (count == 4) {
                        result = new StringBuilder();
                        if (wordList.get(wordList.size() - 1).equals("")) {
                            result.append(word).append("라고 다시 발음해보세요!");
                            phoneme = new ArrayList<>();
                            feedback = new ArrayList<>();
                        } else {
                            result.append(word).append("이라고 다시 발음해보세요!");
                            phoneme = new ArrayList<>();
                            feedback = new ArrayList<>();
                        }
                        break;
                    }
                    // 일단 단어는 5글자를 넘어가지 않는다고 가정했음.
                    // 받침 틀린 경우
                    if (i == 2 || i == 5 || i == 8 || i == 11 || i == 14) {
                        if (w.equals("")) {
                            result.append("'").append(word.charAt(i / 3)).append("'에는 받침이 없어요.").append("\n");
                            phoneme.add(null);
                            feedback.add(3);
                        }
                        if (sw.equals("")) {
                            result.append("'").append(word.charAt(i / 3)).append("'의 받침을 발음하지 않았어요.").append("\n");
                            phoneme.add(w);
                            feedback.add(5);
                        }
                        if (!w.equals("") && !sw.equals("")) {
                            result.append("'").append(word.charAt(i / 3)).append("'의 받침을 잘못 발음했어요.").append("\n");
                            phoneme.add(w);
                            feedback.add(4);
                        }
                    }
                    // 초성 틀린 경우
                    if (i == 0 || i == 3 || i == 6 || i == 9 || i == 12) {
                        result.append("'").append(word.charAt(i / 3)).append("'의 ").append(w).append("을 ").append(sw).append("으로 발음했어요.").append("\n");
                        phoneme.add(w);
                        feedback.add(1);
                    }
                    // 중성 틀린 경우
                    if (i == 1 || i == 4 || i == 7 || i == 10 || i == 13) {
                        result.append("'").append(word.charAt(i / 3)).append("'의 ").append(w).append("를 ").append(sw).append("로 발음했어요.").append("\n");
                        phoneme.add(w);
                        feedback.add(2);
                    }
                }
            }
        }

        for (int i = 0; i < feedback.size(); i++) {
            FailDetail failDetail = FailDetail.builder().user(user).word(word).speakWord(speakWord).phoneme(phoneme.get(i)).feedback(feedback.get(i)).build();
            gameRepository.saveFailDetail(failDetail);
        }
        if (feedback.size() != 0) {
            List<FailWord> failWordList = gameRepository.findFailWord(word, user);
            if (failWordList.size() == 0) {
                FailWord failWord = FailWord.builder().user(user).word(word).num(1).category(Category.GAME).build();
                gameRepository.saveFailWord(failWord);
            } else {
                FailWord existWord = failWordList.get(0);
                existWord.increaseNum(existWord.getNum());
            }
        }

        return result.toString();
    }
}
