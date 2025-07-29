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
    public String registerUser(UserModel userModel) {

        if(userRepository.findByEmail(userModel.getEmail()).isPresent()){
            User user = userRepository.findByEmail(userModel.getEmail()).get();

            if(user.isVerified()){
                return "User Already Exists!!";
            }
            user.setUsername(userModel.getUsername());
            userRepository.save(user);
            otpService.generateAndSendOtp(user.getEmail());
            return "";
        }

        //creating User entity for saving in DB
        User user = new User();
        user.setUsername(userModel.getUsername());
        user.setEmail(userModel.getEmail());

        //save user in DB
        userRepository.save(user);
        // generate and send otp to user for confirmation of email
        otpService.generateAndSendOtp(user.getEmail());
        return "";
    }

    @Override
    public String verifyOtp(String otp, String email) {

        //verifying otp for confirming email
       String result = otpService.verifyOtp(otp, email);
       if("".equals(result)) {
           //extract user from email
           User user = userRepository.findByEmail(email)
                   .orElseThrow(() -> new RuntimeException("No user with email:" + email));
           // set isVerified as TRUE
           user.setVerified(true);
           //save user
           userRepository.save(user);
       }
       return result;

    }

    @Override
    public void resendOtp(String email) {

        otpService.generateAndSendOtp(email);

    }

    @Override
    public boolean isUserExists(String email) {

        if(userRepository.findByEmail(email).isPresent())
            return true;
        return false;
    }

    @Override
    public String verifyLoginOtp(String otp, String email) {

        return otpService.verifyOtp(otp,email);
    }

    @Override
    public String sendLoginOtp(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(user.isVerified()){

            otpService.generateAndSendOtp(email);
            return "";

        }

        return "notVerified";

    }


}
