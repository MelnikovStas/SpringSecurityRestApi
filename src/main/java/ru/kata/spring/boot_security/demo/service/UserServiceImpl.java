package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    @Override
    public void create(User user) {
        userDao.save(user);
    }

    @Transactional
    @Override
    public void update(User user, int id) {

        userDao.update(id, user);

    }

    @Transactional
    @Override
    public void delete(User user, int id) {
        userDao.delete(id);
    }

    @Transactional
    @Override
    public User findById(int id) {
        return userDao.show(id);
    }

    @Transactional
    @Override
    public List<User> findAll() {
        return userDao.index();
    }

    @Override
    public User findByUsername(String username) {
        if ("user".equals(username)) {
            return new User(1, "user", "user");
        } else if ("admin".equals(username)) {
            return new User(2, "admin", "admin");
        }
        return null;
    }

}
