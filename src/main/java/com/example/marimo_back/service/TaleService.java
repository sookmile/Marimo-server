package com.example.marimo_back.service;


import com.example.marimo_back.Dto.GameResultRequestDto;
import com.example.marimo_back.Dto.TaleDataRequestDto;
import com.example.marimo_back.Dto.TaleResultDataDto;
import com.example.marimo_back.domain.*;
import com.example.marimo_back.repository.GameRepository;
import com.example.marimo_back.repository.RecordRepository;
import com.example.marimo_back.repository.TaleRepository;
import com.example.marimo_back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TaleService {

    private final TaleRepository taleRepository;

    private final UserRepository userRepository;

    private final GameRepository gameRepository;

    private final RecordRepository recordRepository;


    public String saveTaleFeedback(TaleDataRequestDto dto){

        TaleResultDataDto dataDto = TaleResultDataDto.builder().taleName(dto.getTaleName()).lastpage(dto.getLastpage()).userId(dto.getUserId()).build();
//        saveResult(dataDto);

        Long userId = dto.getUserId();
        Users user = userRepository.findById(userId);

        Integer lastpage =dto.getLastpage();

        String word = dto.getOWord();
        String speakWord = dto.getRWord();

        // 발음 성공 시 성공 단어 저장
        if (word.equals(speakWord)) {
            List<SuccessWord> successWordList =taleRepository.findSuccessWord(word, user);
            if (successWordList.size() == 0) {
                SuccessWord successWord = SuccessWord.builder().user(user).word(word).num(1).category(Category.TALE).build();
                taleRepository.saveSuccessWord(successWord);
            } else {
//                SuccessWord existWord = successWordList.get(0);
//                existWord.increaseNum(existWord.getNum());
                taleRepository.updateSuccessWordCount(userId, Category.TALE, word);
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

        StringBuilder result = new StringBuilder();

        // 영문 들어오면 바로 return
        for (int i = 0; i < speakWord.length(); i++) {
            char index = speakWord.charAt(i);
            if (index >= 65 && index <= 122) {
                if (wordList.get(wordList.size() - 1).equals("")) {
                    result.append(word).append("라고 다시 발음해보세요!");
                } else {
                    result.append(word).append("이라고 다시 발음해보세요!");
                }
                return result.toString();
            }
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
            taleRepository.saveFailDetail(failDetail);
        }
        if (feedback.size() != 0) {
            List<FailWord> failWordList = taleRepository.findFailWord(word, user);
            if (failWordList.size() == 0) {
                FailWord failWord = FailWord.builder().user(user).word(word).num(1).category(Category.TALE).build();
                taleRepository.saveFailWord(failWord);
            } else {
                FailWord existWord = failWordList.get(0);
                existWord.increaseNum(existWord.getNum());
            }
        }

        return result.toString();

    }


    public void saveResult(TaleResultDataDto dto) {
        long userId = dto.getUserId();
        Users user = userRepository.findById(userId);

        int playcount =0;

        List<Tale> tails =taleRepository.findTailCount(user, dto.getTaleName());
        if(tails.size()!=0){
            taleRepository.updateTalePlayCount(dto.getTaleName(), userId, dto.getLastpage() );
            System.out.println("dd"+tails.size());
        }

        else {
            Tale tale = Tale.builder().user(user).taleName(dto.getTaleName()).talePlaynum(1).lastpage(dto.getLastpage()).build();
            taleRepository.saveTail(tale);
        }

//        System.out.println(recordRepository.talePlayCount(user,dto.getTaleName()));

    }
}
