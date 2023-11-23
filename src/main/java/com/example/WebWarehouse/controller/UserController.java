package com.example.WebWarehouse.controller;

import com.example.WebWarehouse.entity.User;
import com.example.WebWarehouse.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/regist")
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) throws IOException {
        if (bindingResult.hasErrors()) return "registration";
        if (userService.emailExist(user.getEmail())){
            model.addAttribute("error", "Такой пользователь уже существует");
            model.addAttribute("user", user);
            return "registration";
        }
        userService.saveUser(user);
        return "/";
    }
    @GetMapping("/login")
    public String openLogin(){
        return "registration";
    }
}

