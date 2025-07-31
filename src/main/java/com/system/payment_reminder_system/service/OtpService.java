package com.system.payment_reminder_system.service;

public interface OtpService {
    void generateAndSendOtp(String email);

    String verifyOtp(String otp, String email);

}
