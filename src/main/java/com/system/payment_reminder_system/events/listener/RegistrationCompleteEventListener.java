package com.system.payment_reminder_system.events.listener;

import com.system.payment_reminder_system.entity.User;
import com.system.payment_reminder_system.events.RegistrationCompleteEvent;
import com.system.payment_reminder_system.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Component
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private OtpService otpService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {

        // call otpService to generate and send otp via mail
        User user = event.getUser();
        System.out.println("\n******* listener aa gye *********\n");
        otpService.generateAndSendOtp(user);

    }
}
