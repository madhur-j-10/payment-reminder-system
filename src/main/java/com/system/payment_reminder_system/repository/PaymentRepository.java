package com.system.payment_reminder_system.repository;

import com.system.payment_reminder_system.entity.Payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
    List<Payment> findByUserId(Long userId);


    List<Payment> findByDeadlineBetween(LocalDateTime dateTime1, LocalDateTime dateTime2);

    List<Payment> findByDeadlineBefore(LocalDateTime dateTime);
}
