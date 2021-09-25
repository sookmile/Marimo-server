package com.example.marimo_back.service;

import com.example.marimo_back.Dto.RecordResponseDto;
import com.example.marimo_back.domain.Users;
import com.example.marimo_back.repository.RecordRepository;
import com.example.marimo_back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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

        return RecordResponseDto.builder().achievementRate(achievementRate).build();
    }
}
