package spring_mvc_boot.springBoot.SpringMVCApp.service;

import spring_mvc_boot.springBoot.SpringMVCApp.models.User;

import java.util.List;


public interface UserService {

    void create(User user);

    void update(User user, int id);

    void delete(User user, int id);

    User findById(int id);

    List<User> findAll();


}
