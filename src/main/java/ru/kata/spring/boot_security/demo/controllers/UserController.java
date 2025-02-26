package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.security.UserDetailsImpl;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping()
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String index(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "admin";
    }

    @GetMapping("/user")
    public String showUserInfo(Model model, Principal principal) {
        String username = principal.getName();
        UserDetailsImpl userDetails = (UserDetailsImpl) userService.loadUserByUsername(username);
        User user = userDetails.getUser();
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/admin/user")
    public String userShow(@RequestParam("id") long id, Model model) { // Используем long вместо int
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "show";
    }

    @GetMapping("/admin/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "new";
    }

    @PostMapping("/admin/new")
    public String create(@ModelAttribute("user") User user) {
        userService.create(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/{id}/edit")
    public String edit(@PathVariable long id, Model model) { // Используем long вместо int
        model.addAttribute("user", userService.findById(id));
        return "edit";
    }

    @PostMapping("/admin/edit")
    public String update(@ModelAttribute("user") User user,
                         BindingResult bindingResult, @RequestParam("id") long id) { // Используем long вместо int
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        userService.update(user, id);
        return "redirect:/admin";
    }

    @PostMapping("/admin/delete")
    public String delete(@RequestParam("id") long id, Model model) { // Используем long вместо int
        User user = userService.findById(id);
        model.addAttribute("user", user);
        userService.delete(user, id);
        return "redirect:/admin";
    }
}