package ru.kata.spring.boot_security.demo.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Создаем пользователя admin
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setRole("ADMIN");
        admin.setAge(18);
        userRepository.save(admin);

        // Создаем пользователя user
        User user = new User();
        user.setUsername("user");
        user.setPassword("user");
        user.setRole("USER");
        user.setAge(18);
        userRepository.save(user);
    }
}