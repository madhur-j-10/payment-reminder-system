package com.system.payment_reminder_system.service;

import com.system.payment_reminder_system.entity.Payment;
import com.system.payment_reminder_system.model.PaymentModel;
import com.system.payment_reminder_system.model.PaymentStatus;

import java.util.List;

public interface PaymentService {
    void createPayment(PaymentModel paymentModel);

    List<Payment> getPaymentsByUser(Long userId);

    void updateStatus(Long id, PaymentStatus status, Long userId);

    void deletePayment(Long id, Long userId);
}
