package com.system.payment_reminder_system.service;


import com.system.payment_reminder_system.model.UserModel;

public interface AuthService {
    String registerUser(UserModel userModel);

    String verifyOtp(String otp, String email);

    void resendOtp(String email);

    boolean isUserExists(String email);

    String verifyLoginOtp(String otp, String email);

    String sendLoginOtp(String email);
}
