package com.system.payment_reminder_system.controller;

import com.system.payment_reminder_system.model.OtpModel;
import com.system.payment_reminder_system.model.UserModel;
import com.system.payment_reminder_system.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    @GetMapping("/register")
    private String registrationPage() {

        return "index";
    }

    @PostMapping("/register")
    private String registerUser(@ModelAttribute UserModel userModel,
                                Model model) {
        //save user to DB with isVerified=false
        // return "" if no duplicate found
        String error = authService.registerUser(userModel);
        // no duplicate
        if("".equals(error)){
            model.addAttribute("registerFlag","abc");
            model.addAttribute("email", userModel.getEmail());

            return "otpPage";
        }
        //duplicate email found during registration redirect to /auth/register
        model.addAttribute("update", error); //dom
        return "index";
//            return "redirect:/auth/register";

    }

    @PostMapping("/verify-otp")
    private String verifyOtp(@RequestParam String email,
                             @RequestParam String otp,
                             Model model) {


        String result = authService.verifyOtp(otp,email);

        // verified --> redirect to /auth/register
        if("".equals(result)){
            model.addAttribute("update", "User Verified!!"); //dom
            return "index";
//            return "redirect:/auth/register";
        }


        model.addAttribute("registerFlag","abc");
        model.addAttribute("error", result);

        return "otpPage";
    }

    @GetMapping("/resend-otp/email")
    public String resendOtp(@RequestParam String email,
            Model model) {

        authService.resendOtp(email);

        model.addAttribute("registerFlag","abc");
        model.addAttribute("email", email);
        return "otpPage";
    }



}
