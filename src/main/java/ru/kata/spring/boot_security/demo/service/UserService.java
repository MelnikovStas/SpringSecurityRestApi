package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;


public interface UserService {

    void create(User user);

    void update(User user, long id);

    void delete(User user, long id);

    User findById(long id);

    List<User> findAll();

    Object loadUserByUsername(String username);
}

