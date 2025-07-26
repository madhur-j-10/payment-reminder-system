package com.system.payment_reminder_system.service;

import com.system.payment_reminder_system.entity.Otp;
import com.system.payment_reminder_system.entity.User;
import com.system.payment_reminder_system.model.OtpModel;
import com.system.payment_reminder_system.repository.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OtpServiceImpl implements OtpService{

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public void generateAndSendOtp(User user) {

        String otp = String.valueOf((int) (Math.random() * 900000) + 100000); // 6-digit OTP

        Otp otpEntity = new Otp();
        otpEntity.setOtp(otp);
        otpEntity.setUserName(user.getUserName());
        otpEntity.setExpirationTime(LocalDateTime.now().plusMinutes(5));

        otpRepository.save(otpEntity);

        emailService.sendMail(user.getUserEmail(),"OTP For verification", "Your OTP is: " + otp);

    }

    @Override
    public boolean verifyOtp(OtpModel otpModel) {

        // to avoid NullPointerException
        Optional<Otp> otpData =
                otpRepository.findByOtpAndUserName(otpModel.getOtp(),otpModel.getUserName());

        if(otpData.isPresent() && otpData.get().getExpirationTime().isAfter(LocalDateTime.now())) {
            otpRepository.delete(otpData.get());
            return true;
        }

        return false;
    }


}
