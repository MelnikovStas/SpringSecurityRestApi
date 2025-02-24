package ru.kata.spring.boot_security.demo.dao;


import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

    public UserDaoImpl() {
        System.out.println("UserDaoImpl bean created!");
    }


    public List<User> index() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }

    public User show(int id) {
        return em.find(User.class, id);
    }


    public void save(User user) {
        em.persist(user);
    }

    public void update(int id, User updateUser) {
        User user = em.find(User.class, id);
        user.setUsername(updateUser.getUsername());
        user.setPassword(updateUser.getPassword());
        em.merge(user);
    }

    public void delete(int id) {
        User user = em.find(User.class, id);
        em.remove(user);
    }

}
