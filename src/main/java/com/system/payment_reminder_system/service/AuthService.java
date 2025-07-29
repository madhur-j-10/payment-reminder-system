package com.system.payment_reminder_system.service;


import com.system.payment_reminder_system.model.OtpModel;
import com.system.payment_reminder_system.model.UserModel;

public interface AuthService {
    String registerUser(UserModel userModel);

    String verifyOtp(String otp, String email);

    void resendOtp(String email);
}
