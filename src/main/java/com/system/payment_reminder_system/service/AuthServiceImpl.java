package com.system.payment_reminder_system.service;


import com.system.payment_reminder_system.entity.User;
import com.system.payment_reminder_system.model.UserModel;
import com.system.payment_reminder_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OtpService otpService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;


    @Override
    public void registerUser(UserModel userModel) {

        //creating User entity for saving in DB
        User user = new User();
        user.setUsername(userModel.getUsername());
        user.setEmail(userModel.getEmail());

        //save user in DB
        userRepository.save(user);
        // generate and send otp to user for confirmation of email
        otpService.generateAndSendOtp(user.getEmail());
    }

    @Override
    public boolean verifyOtp(String otp, String email) {

        //verifying otp for confirming email
       boolean isValid = otpService.verifyOtp(otp, email);
       if(isValid) {
           //extract user from email
           User user = userRepository.findByEmail(email);
           // set isVerified as TRUE
           user.setVerified(true);
           //save user
           userRepository.save(user);
       }
       return isValid;

    }
}
