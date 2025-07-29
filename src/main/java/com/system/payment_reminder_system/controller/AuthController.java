package com.system.payment_reminder_system.controller;

import com.system.payment_reminder_system.model.OtpModel;
import com.system.payment_reminder_system.model.UserModel;
import com.system.payment_reminder_system.service.AuthService;
import com.system.payment_reminder_system.service.OtpService;
import com.system.payment_reminder_system.utility.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private OtpService otpService;

    @Autowired
    private JwtUtil jwtUtil;


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

        //returns empty string if verified successfully else return error message
        String result = authService.verifyOtp(otp,email);

        // verified --> redirect to /auth/register
        if("".equals(result)){
            model.addAttribute("update", "User Verified!!"); //dom
            return "index";
//            return "redirect:/auth/register";
        }


        model.addAttribute("registerFlag","abc");
        model.addAttribute("email",email);
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

    @PostMapping("/send-otp")
    public String sendLoginOtp(@RequestParam String email,
                               Model model) {
        //check user exists or not by email
        if(authService.isUserExists(email)){

            //check user is verified or not
            String result = authService.sendLoginOtp(email);

            //send otp to user for login(if verified)
            if("".equals(result)){
                model.addAttribute("loginFlag", "123");
                model.addAttribute("email", email);
                //redirect to otpPage for verification
                return "otpPage";
            }

            if("notVerified".equalsIgnoreCase(result)){
                model.addAttribute("error", "User Not Verified!!");
                return "index";
            }

        }

        model.addAttribute("error", "User Not Exists!!");
        return "index";
    }

    @PostMapping("/verify-login-otp")
    public String verifyLoginOtp(@RequestParam String email,
                                 @RequestParam String otp,
                                 HttpServletResponse response,
                                 Model model) {
        //returns empty string if verified successfully else return error message
        String result = authService.verifyLoginOtp(otp, email);

        if("".equals(result)){

            //generate JWT
            String token = jwtUtil.generateToken(email);

            //Store Jwt in browser cookies
            Cookie cookie = new Cookie("jwt", token);
            cookie.setHttpOnly(true);// Prevent JS access
            cookie.setSecure(false); // true in production
            cookie.setPath("/");// Cookie is sent to all endpoints of this app
            cookie.setMaxAge(3600); //1 hour expiry
            response.addCookie(cookie); // Add cookie to response

            // go to dashboard
            return "redirect:/dashboard";

        }

        model.addAttribute("loginFlag","123");
        model.addAttribute("email",email);
        model.addAttribute("error", result);

        return "otpPage";
    }



}
