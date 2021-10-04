package com.example.marimo_back.repository;


import com.example.marimo_back.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TaleRepository {

    private final EntityManager em;

    public List<SuccessWord> findSuccessWord(String word, Users user) {
        return em.createQuery("select w from SuccessWord w where w.user = :user and w.word = :word", SuccessWord.class)
                .setParameter("user", user)
                .setParameter("word", word)
                .getResultList();
    }

    public void saveSuccessWord(SuccessWord successWord) {
        em.persist(successWord);
    }

    public void saveFailWord(FailWord failWord) {
        em.persist(failWord);
    }

    public void saveFailDetail(FailDetail failDetail) {
        em.persist(failDetail);
    }

    public List<FailWord> findFailWord(String word, Users user) {
        return em.createQuery("select w from FailWord w where w.user = :user and w.word = :word", FailWord.class)
                .setParameter("user", user)
                .setParameter("word", word)
                .getResultList();
    }

    public List<Tale> findTailCount(Users user, Long tailId){
        return em.createQuery("select w from Tale w where w.user = :user and w.id = :tailId", Tale.class)
                .setParameter("user", user)
                .setParameter("tailId", tailId)
                .getResultList();
    }

    public void saveTail(Tale tale) {
        em.persist(tale);
    }

}
