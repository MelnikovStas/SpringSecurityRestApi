package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user; // Возвращаем объект User, так как он реализует UserDetails
    }
    @Transactional
    @Override
    public void create(User user) {
        userRepository.save(user); // Пароль сохраняется как есть (без кодировки)
    }
    @Transactional
    @Override
    public void update(User user, long id) {
        user.setId(id);
        userRepository.save(user); // Пароль сохраняется как есть (без кодировки)
    }
    @Transactional
    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }
    @Transactional
    @Override
    public User findById(long id) {
        return userRepository.findById(id).orElse(null);
    }
    @Transactional
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}