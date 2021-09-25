package com.example.marimo_back.repository;

import com.example.marimo_back.domain.FailWord;
import com.example.marimo_back.domain.SuccessWord;
import com.example.marimo_back.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RecordRepository {

    private final EntityManager em;

    public Long findUserSuccessCount(Users user) {
        long sum = 0;
        List<SuccessWord> wordList = em.createQuery("select w from SuccessWord as w where w.user = :user", SuccessWord.class)
                .setParameter("user", user)
                .getResultList();
        for (SuccessWord word : wordList) {
            sum += word.getNum();
        }
        return sum;
    }

    public Long findUserFailCount(Users user) {
        long sum = 0;
        List<FailWord> wordList = em.createQuery("select w from FailWord as w where w.user = :user", FailWord.class)
                .setParameter("user", user)
                .getResultList();
        for (FailWord word : wordList) {
            sum += word.getNum();
        }
        return sum;
    }
}
