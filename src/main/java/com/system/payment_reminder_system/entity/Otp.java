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

    private LocalDateTime expirationTime;

    @OneToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_USER_VERIFY_TOKEN")
//            referencedColumnName = "userId"
    )
    private User user;

//
//    public Otp(String otp, User user) {
//        this.otp = otp;
//        this.user = user;
//        this.expirationTime = LocalDateTime.now().plusMinutes(5);
//    }
}
