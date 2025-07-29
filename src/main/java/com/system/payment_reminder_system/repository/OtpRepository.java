package com.system.payment_reminder_system.repository;

import com.system.payment_reminder_system.entity.OtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<OtpEntity,Long> {
    OtpEntity findByOtp(String otp);


    Optional<OtpEntity> findByOtpAndEmail(String otp, String email);

    Optional<OtpEntity> findByEmail(String email);
}
