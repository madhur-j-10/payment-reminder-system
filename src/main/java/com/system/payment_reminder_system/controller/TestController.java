package com.system.payment_reminder_system.controller;


import com.system.payment_reminder_system.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/reminder")
    public ResponseEntity<String> testReminder() {
        paymentService.sendPaymentReminder();
        return ResponseEntity.ok("Reminder logic executed!!");
    }



}
