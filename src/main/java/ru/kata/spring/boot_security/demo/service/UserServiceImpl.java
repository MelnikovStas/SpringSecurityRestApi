package spring_mvc_boot.springBoot.SpringMVCApp.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring_mvc_boot.springBoot.SpringMVCApp.dao.UserDao;
import spring_mvc_boot.springBoot.SpringMVCApp.models.User;

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
}
