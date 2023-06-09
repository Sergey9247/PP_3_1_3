package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RolesServiceImp;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImpl userService;
    private final RolesServiceImp rolesServiceImp;

    public AdminController(UserServiceImpl userService, RolesServiceImp rolesServiceImp) {
        this.userService = userService;
        this.rolesServiceImp = rolesServiceImp;
    }

    @GetMapping()
    public String findAll(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "admin";
    }

    @GetMapping("/create")
    public String createUserForm(Model model) {
        model.addAttribute("roles", rolesServiceImp.getRoles());
        model.addAttribute("user", new User());
        return "create";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") long id){
        userService.deleteById(id);
        return "redirect:/admin";
    }


    @GetMapping("{id}/update")
    public String updateUserForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("roles", rolesServiceImp.getRoles());
        model.addAttribute("user", userService.findById(id));
        return "/update";
    }

    @PostMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
        userService.saveUser(user);
        return "redirect:/admin";
    }
}
