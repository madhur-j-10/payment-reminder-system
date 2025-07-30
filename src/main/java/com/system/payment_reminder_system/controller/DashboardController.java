package com.system.payment_reminder_system.controller;

import com.system.payment_reminder_system.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {



    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }



}
