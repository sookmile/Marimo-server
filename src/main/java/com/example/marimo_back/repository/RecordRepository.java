package com.example.marimo_back.repository;

import com.example.marimo_back.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RecordRepository {

    private final EntityManager em;

    public Long getUserSuccessCount(Users user) {
        long sum = 0;
        List<SuccessWord> wordList = em.createQuery("select w from SuccessWord as w where w.user = :user", SuccessWord.class)
                .setParameter("user", user)
                .getResultList();
        for (SuccessWord word : wordList) {
            sum += word.getNum();
        }
        return sum;
    }

    public Long getUserFailCount(Users user) {
        long sum = 0;
        List<FailWord> wordList = em.createQuery("select w from FailWord as w where w.user = :user", FailWord.class)
                .setParameter("user", user)
                .getResultList();
        for (FailWord word : wordList) {
            sum += word.getNum();
        }
        return sum;
    }

    public List<String> findMostSuccessWord(Users user) {
        // select w.SUCCESS_WORD FROM SUCCESS_WORD w WHERE w.SUCCESS_NUM = (SELECT MAX(s.SUCCESS_NUM) FROM SUCCESS_WORD s WHERE USER_ID = ?) and USER_ID = ?;
        return em.createQuery("select w.word from SuccessWord w where w.num = (select max(subW.num) from SuccessWord subW where subW.user = :user)  and w.user = :user", String.class)
                .setParameter("user", user)
                .getResultList();
    }

    public List<String> findMostFailWord(Users user) {
        return em.createQuery("select w.word from FailWord w where w.num = (select max(subW.num) from FailWord subW where subW.user = :user) and w.user = :user", String.class)
                .setParameter("user", user)
                .getResultList();
    }

    public Long getGameJoinCount(Users user) {
        return em.createQuery("select count(g) from Game as g where g.user = :user", Long.class)
                .setParameter("user", user)
                .getSingleResult();
    }

    public Integer findMostSuccessWordCount(Users user) {
        return em.createQuery("select max(subW.num) from SuccessWord subW where subW.user = :user ", Integer.class)
                .setParameter("user", user)
                .getSingleResult();
    }

    public List<String> getAllFailRisk(Users users){
        return em.createQuery("select w.phoneme from FailDetail w where w.user =:user", String.class)
                .setParameter("user",users)
                .getResultList();
    }
    public List<Integer> getVowelFeedback(Users users, String s){
        return em.createQuery("select w.feedback from FailDetail w where w.user=:user and  w.phoneme=:s", Integer.class )
                .setParameter("user",users)
                .setParameter("s",s)
                .getResultList();
    }

    public List<SuccessWord> successWords5(Users users){
        return em.createQuery("select  w from SuccessWord w where w.user=:user order by w.num desc", SuccessWord.class)
                .setParameter("user", users)
                .getResultList();
    }

    public List<Tale> tales(Users users, String tailName){
        return em.createQuery("select t from Tale t where t.user=:users and t.taleName=:tailName", Tale.class)
                .setParameter("users", users)
                .setParameter("tailName", tailName)
                .getResultList();
    }

    public List<Game> gamePlayCount (Users users){
        return em.createQuery("select g from Game g where g.user=:users", Game.class)
                .setParameter("users", users)
                .getResultList();
    }

    public List<SuccessWord> categoryBestSuccessWord(Users users , Category category){
        return em.createQuery("select s from SuccessWord s where s.user=:users and s.category=:category order by s.num", SuccessWord.class)
                .setParameter("users", users)
                .setParameter("category", category)
                .getResultList();
    }

}