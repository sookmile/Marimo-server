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

    public List<Users> findUser(String email) {
        return em.createQuery("select u from users u where u.email = :email", Users.class)
                .setParameter("email", email)
                .getResultList();
    }
}
