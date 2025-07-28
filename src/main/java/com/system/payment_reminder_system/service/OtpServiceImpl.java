package com.system.payment_reminder_system.service;

import com.system.payment_reminder_system.entity.OtpEntity;
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
    public void generateAndSendOtp(String email) {

        String otp = String.valueOf((int) (Math.random() * 900000) + 100000); // 6-digit OTP

        //creating otp entity to save in DB
        OtpEntity otpEntity = new OtpEntity();
        otpEntity.setOtp(otp);
        otpEntity.setEmail(email);
        otpEntity.setExpirationTime(LocalDateTime.now().plusMinutes(2));

        // save otp to DB
        otpRepository.save(otpEntity);

        //send otp to user
        emailService.sendMail(email,"OTP For verification", "Your OTP is\n" + otp);

    }

    @Override
    public boolean verifyOtp(String otp, String email) {

            //to avoid NullPointerException
            Optional<OtpEntity> otpData =
                otpRepository.findByOtpAndEmail(otp,email);

        // checking that otp and email matches or not , and check expiry of otp
        if(otpData.isPresent() && otpData.get().getExpirationTime().isAfter(LocalDateTime.now())) {
            otpRepository.delete(otpData.get());
            return true;
        }
        return false;
    }


}
