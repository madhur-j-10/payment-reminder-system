package com.system.payment_reminder_system.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "otp_table")
@Data
@NoArgsConstructor
public class Otp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long otpId;

    private String otp;

    private String userName;

    private LocalDateTime expirationTime;



//    public Otp(String otp, User user) {
//        this.otp = otp;
//        this.user = user;
//        this.expirationTime = LocalDateTime.now().plusMinutes(5);
//    }
}
