package com.example.marimo_back.repository;

import com.example.marimo_back.domain.Images;
import com.example.marimo_back.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ImageRepository {

    private final EntityManager em;

    public void save(Images image) {
        em.persist(image);
    }

    public List<Images> findImage(String link) {
        return em.createQuery("select p from Images p where p.link = :link", Images.class)
                .setParameter("link", link)
                .getResultList();
    }

    public List<Images> findByUserId(Users user) {
        return em.createQuery("select p from Images p where p.user = :user", Images.class)
                .setParameter("user", user)
                .getResultList();
    }
}
