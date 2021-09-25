package com.example.marimo_back.service;

import com.example.marimo_back.Dto.RecordResponseDto;
import com.example.marimo_back.domain.FailWord;
import com.example.marimo_back.domain.SuccessWord;
import com.example.marimo_back.domain.Users;
import com.example.marimo_back.repository.RecordRepository;
import com.example.marimo_back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RecordService {

    private final UserRepository userRepository;
    private final RecordRepository recordRepository;

    public RecordResponseDto getUserAchievement(Long userId) {
        Users user = userRepository.findById(userId);

        double successCount = recordRepository.findUserSuccessCount(user);
        double failCount = recordRepository.findUserFailCount(user);
        Long achievementRate = Math.round((successCount / (successCount + failCount)) * 100);

        List<SuccessWord> mostSuccessWordList = recordRepository.findMostSuccessWord(user);
        List<String> mostSuccess = new ArrayList<>();
        for (SuccessWord w : mostSuccessWordList) {
            mostSuccess.add(w.getWord());
        }

        List<FailWord> mostFailWordList = recordRepository.findMostFailWord(user);
        List<String> mostFail = new ArrayList<>();
        for (FailWord w : mostFailWordList) {
            mostFail.add(w.getWord());
        }

        return RecordResponseDto.builder().nickName(user.getNickname()).achievementRate(achievementRate).mostSuccess(mostSuccess).mostFail(mostFail).build();
    }
}
