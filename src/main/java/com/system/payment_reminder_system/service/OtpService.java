package com.system.payment_reminder_system.service;

import com.system.payment_reminder_system.entity.User;
import com.system.payment_reminder_system.model.OtpModel;

public interface OtpService {
    void generateAndSendOtp(String email);

    String verifyOtp(String otp, String email);

}
