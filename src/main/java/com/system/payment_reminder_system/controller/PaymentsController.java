package com.system.payment_reminder_system.controller;


import com.system.payment_reminder_system.entity.Payment;
import com.system.payment_reminder_system.entity.User;
import com.system.payment_reminder_system.model.PaymentModel;
import com.system.payment_reminder_system.model.PaymentStatus;
import com.system.payment_reminder_system.repository.UserRepository;
import com.system.payment_reminder_system.service.PaymentService;
import com.system.payment_reminder_system.utility.JwtUtil;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentsController {


    @Autowired
    private PaymentService paymentService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    private Long getUserId(HttpServletRequest request) {

        String token = null;
        // get token from all cookies in browser
        if(request.getCookies() != null){
            for(Cookie cookie : request.getCookies()){
                if("jwt".equals(cookie.getName())){
                    token = cookie.getValue();
                }
            }
        }
        //extract email from token
        String email = jwtUtil.extractEmail(token);
        //extract userId from email
        return jwtUtil.getUserIdFromEmail(email);

    }

    @PostMapping
    public void createPayment(@RequestBody PaymentModel paymentModel,
                              HttpServletRequest request) {

        Long userId = getUserId(request);
        //maps userId in Payment Table
        paymentModel.setUserId(userId);
        paymentService.createPayment(paymentModel);
    }

    @GetMapping
    public List<Payment> getPayments(HttpServletRequest request) {
        Long userId = getUserId(request);
        return paymentService.getPaymentsByUser(userId);
    }

    @PatchMapping("/{id}/status")
    public void updateStatus(@PathVariable Long id,
                               @RequestParam PaymentStatus status,
                               HttpServletRequest request) {
        Long userId = getUserId(request);

        paymentService.updateStatus(id,status,userId);


    }

    @DeleteMapping("/{id}")
    public void deletePayment(@PathVariable Long id,
                              HttpServletRequest request){
        Long userId = getUserId(request);
        paymentService.deletePayment(id,userId);

    }



    @GetMapping("/user")
    public String getUserName(HttpServletRequest request) {
        Long userId = getUserId(request);
        User user =  userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        return user.getUsername();
    }





}














