package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping()
public class UserController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    public UserController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/admin")
    public String index(Model model, Principal principal) {
        List<User> users = userService.findAll();
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("users", users);
        model.addAttribute("allRoles", roles);

        // Получаем текущего пользователя
        String username = principal.getName();
        User currentUser = (User) userService.loadUserByUsername(username);

        // Отладочный вывод
        System.out.println("Текущий пользователь: " + currentUser.getUsername());
        System.out.println("Роли пользователя: " + currentUser.getRoles());


        model.addAttribute("username", username);
        model.addAttribute("user", currentUser); // Передаем информацию о текущем пользователе
        model.addAttribute("roles", currentUser.getRoles()); // Передаем роли
        return "admin";
    }

    @GetMapping("/user")
    public String userPanel(Model model, Principal principal) {
        // Получаем текущего пользователя
        String username = principal.getName();
        User currentUser = (User) userService.loadUserByUsername(username);

        // Передаем данные в модель
        model.addAttribute("username", username);
        model.addAttribute("user", currentUser);
        model.addAttribute("roles", currentUser.getRoles()); // Передаем роли

        return "user";
    }

    @GetMapping("/admin/user")
    @ResponseBody
    public User getUserById(@RequestParam Long id) {
        return userService.findById(id);
    }


    @PostMapping("/admin/new")
    public String create(@ModelAttribute("user") User user, @RequestParam("roleIds") Set<Long> roleIds) {
        Set<Role> roles = new HashSet<>(roleRepository.findAllById(roleIds)); // Преобразуем List в Set
        user.setRoles(roles);
        userService.create(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/{id}/edit")
    public String edit(@PathVariable long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "edit";
    }

    @PostMapping("/admin/edit")
    public String update(@ModelAttribute("user") User user,
                         BindingResult bindingResult, @RequestParam("id") long id,
                         @RequestParam("roleIds") Set<Long> roleIds) {
        if (bindingResult.hasErrors()) {
            return "edit";
        }

        // Получаем роли по их ID из базы данных
        Set<Role> roles = roleIds.stream()
                .map(roleId -> roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Role not found")))
                .collect(Collectors.toSet());
        User existingUser = userService.findById(id);

        // Устанавливаем выбранные роли для пользователя
        user.setRoles(roles);
        user.setPassword(existingUser.getPassword());

        // Обновляем пользователя
        userService.update(user, id);

        return "redirect:/admin";
    }

    @PostMapping("/admin/delete")
    public String delete(@RequestParam("id") long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
