package com.system.payment_reminder_system.service;

import com.system.payment_reminder_system.entity.User;

public interface OtpService {
    void generateAndSendOtp(User user);
}
