package com.example.marimo_back.repository;

import com.example.marimo_back.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public void save(Users user) {
        em.persist(user);
    }

    public Users findById(Long userId) {
        return em.find(Users.class, userId);
    }

    public List<Users> findUser(String identifier) {
        return em.createQuery("select u from Users u where u.identifier = :identifier", Users.class)
                .setParameter("identifier", identifier)
                .getResultList();
    }
}
