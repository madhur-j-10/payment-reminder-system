package com.system.payment_reminder_system.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DashboardController {



    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }



}
