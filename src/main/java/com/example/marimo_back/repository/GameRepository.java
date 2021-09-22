package com.example.marimo_back.repository;

import com.example.marimo_back.domain.FailWord;
import com.example.marimo_back.domain.Game;
import com.example.marimo_back.domain.SuccessWord;
import com.example.marimo_back.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GameRepository {

    private final EntityManager em;

    public void saveGame(Game game) {
        em.persist(game);
    }

    public void saveSuccessWord(SuccessWord successWord) {
        em.persist(successWord);
    }

    public List<SuccessWord> findSuccessWord(String word, Users user) {
        return em.createQuery("select w from SuccessWord w where w.user = :user and w.word = :word", SuccessWord.class)
                .setParameter("user", user)
                .setParameter("word", word)
                .getResultList();
    }

    public void saveFailWord(FailWord failWord) {
        em.persist(failWord);
    }

    public List<FailWord> findFailWord(String word, Users user) {
        return em.createQuery("select w from FailWord w where w.user = :user and w.word = :word", FailWord.class)
                .setParameter("user", user)
                .setParameter("word", word)
                .getResultList();
    }
}
