package spring_mvc_boot.springBoot.SpringMVCApp.dao;

import spring_mvc_boot.springBoot.SpringMVCApp.models.User;

import java.util.List;

public interface UserDao {
    void save(User user);

    User show(int id);

    void update(int id, User updateUser);

    void delete(int id);

    List<User> index();
}
