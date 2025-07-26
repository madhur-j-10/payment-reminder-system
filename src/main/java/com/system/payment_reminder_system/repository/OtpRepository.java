package com.system.payment_reminder_system.repository;

import com.system.payment_reminder_system.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp,Long> {
    Otp findByOtp(String otp);


    Optional<Otp> findByOtpAndUserName(String otp, String userName);
}
