package com.system.payment_reminder_system.service;


import com.system.payment_reminder_system.model.OtpModel;
import com.system.payment_reminder_system.model.UserModel;

public interface UserService {
    void registerUser(UserModel userModel);

    boolean verifyOtp(OtpModel otpModel);
}
