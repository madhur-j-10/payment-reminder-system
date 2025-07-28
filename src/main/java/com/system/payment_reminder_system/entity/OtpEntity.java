package com.system.payment_reminder_system.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "otp_table")
@Data
@NoArgsConstructor
public class OtpEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long otpId;

    private String otp;

    private String email;

    private LocalDateTime expirationTime;



//    public Otp(String otp, User user) {
//        this.otp = otp;
//        this.user = user;
//        this.expirationTime = LocalDateTime.now().plusMinutes(5);
//    }
}
