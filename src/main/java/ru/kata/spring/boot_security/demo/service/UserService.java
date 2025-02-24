package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;


public interface UserService {

    void create(User user);

    void update(User user, int id);

    void delete(User user, int id);

    User findById(int id);

    List<User> findAll();


    User findByUsername(String username);
}
