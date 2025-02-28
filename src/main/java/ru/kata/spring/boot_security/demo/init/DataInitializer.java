package ru.kata.spring.boot_security.demo.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public DataInitializer(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Удаляем все существующие данные (если они есть)
        userRepository.deleteAll();
        roleRepository.deleteAll();

        // Создаем роли
        Role adminRole = new Role();
        adminRole.setRole("ROLE_ADMIN");
        roleRepository.save(adminRole);

        Role userRole = new Role();
        userRole.setRole("ROLE_USER");
        roleRepository.save(userRole);

        // Создаем администратора
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("admin"); // Пароль без кодировки
        admin.setAge(18);
        admin.setRoles(Set.of(adminRole,userRole));
        userRepository.save(admin);

        // Создаем обычного пользователя
        User user = new User();
        user.setUsername("user");
        user.setPassword("user"); // Пароль без кодировки
        user.setAge(18);
        user.setRoles(Set.of(userRole));
        userRepository.save(user);
    }
}