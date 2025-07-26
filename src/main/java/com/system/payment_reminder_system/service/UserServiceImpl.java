package com.system.payment_reminder_system.service;


import com.system.payment_reminder_system.entity.Otp;
import com.system.payment_reminder_system.entity.User;
import com.system.payment_reminder_system.events.RegistrationCompleteEvent;
import com.system.payment_reminder_system.model.OtpModel;
import com.system.payment_reminder_system.model.UserModel;
import com.system.payment_reminder_system.repository.OtpRepository;
import com.system.payment_reminder_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OtpService otpService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;


    @Override
    public void registerUser(UserModel userModel) {

        User user = new User();
        user.setUserName(userModel.getUserName());
        user.setUserEmail(userModel.getUserEmail());

        userRepository.save(user);

        applicationEventPublisher.publishEvent(new RegistrationCompleteEvent(user));

    }

    @Override
    public boolean verifyOtp(OtpModel otpModel) {

       boolean isValid = otpService.verifyOtp(otpModel);
       if(isValid) {
           User user = userRepository.findByUserName(otpModel.getUserName());
           user.setVerified(true);
           userRepository.save(user);
       }
       return isValid;

    }
}
