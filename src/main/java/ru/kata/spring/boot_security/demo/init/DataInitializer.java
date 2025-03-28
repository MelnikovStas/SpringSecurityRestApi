package ru.kata.spring.boot_security.demo.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
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
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setAge(18);
        admin.setRoles(Set.of(adminRole, userRole));
        userRepository.save(admin);

        // Создаем обычного пользователя
        User user = new User();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("user"));
        user.setAge(18);
        user.setRoles(Set.of(userRole));
        userRepository.save(user);
    }
}
