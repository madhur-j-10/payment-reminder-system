package com.system.payment_reminder_system.controller;


import com.system.payment_reminder_system.entity.User;
import com.system.payment_reminder_system.model.UserModel;
import com.system.payment_reminder_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//@RequestMapping("/auth")
@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    private String registerUser(@RequestBody UserModel userModel) {

        User user =  userService.registerUser(userModel);

        return "Registration Completed!!";

    }
}
