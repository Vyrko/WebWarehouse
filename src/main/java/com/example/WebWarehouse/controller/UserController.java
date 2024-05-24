package com.example.WebWarehouse.controller;

import com.example.WebWarehouse.entity.Product;
import com.example.WebWarehouse.entity.User;
import com.example.WebWarehouse.entity.WarehouseWorkerLink;
import com.example.WebWarehouse.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                String fieldName = error.getField();
                String errorMessage = error.getDefaultMessage();
                model.addAttribute(fieldName+ "Error" , errorMessage);
            }
            model.addAttribute("newUser",user);
            return "registration";
        }
        if (userService.emailExist(user.getEmail())){
            model.addAttribute("nameError", "Такой пользователь уже существует");
            model.addAttribute("user", user);
            return "registration";
        }
        userService.saveUser(user);
        return "index";
    }
    @MessageMapping("/user.connectedUser")
    @SendTo("/user/public")
    public User connectedUser(
            @Payload User user
    ){
        String newUser = user.getEmail().replace('_','@');
        return userService.connectedWithChat(newUser);
    }
    @MessageMapping("/user.disconnectUser")
    @SendTo("/user/public")
    public User disconnect(
            @Payload User user
    ){
        userService.disconnect(user);
        return user;
    }
    @GetMapping("/users")
    public ResponseEntity<List<User>> findConnectedUser(){
        return ResponseEntity.ok(userService.findConnectedUsers());
    }
}

