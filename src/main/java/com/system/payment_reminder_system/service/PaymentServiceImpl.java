package com.system.payment_reminder_system.service;

import com.system.payment_reminder_system.entity.Payment;
import com.system.payment_reminder_system.model.PaymentModel;
import com.system.payment_reminder_system.model.PaymentStatus;
import com.system.payment_reminder_system.repository.PaymentRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private PaymentRepository paymentRepository;


    @Override
    public void createPayment(PaymentModel paymentModel) {
        Payment payment = new Payment();

        payment.setPaymentName(paymentModel.getPaymentName());
        payment.setUserId(paymentModel.getUserId());
        payment.setDescription(paymentModel.getDescription());
        payment.setAmount(paymentModel.getAmount());
        payment.setDeadline(paymentModel.getDeadline());
        payment.setCategory(paymentModel.getCategory());

        paymentRepository.save(payment);

    }

    @Override
    public List<Payment> getPaymentsByUser(Long userId) {
        return paymentRepository.findByUserId(userId);
    }

    @Override
    public void updateStatus(Long id, PaymentStatus status, Long userId) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment Not Found!!"));

        //optional double authentication
        if(!payment.getUserId().equals(userId)){
            throw new RuntimeException("UNAUTHORIZED!!");
        }

        payment.setStatus(status);
        paymentRepository.save(payment);

    }

    @Override
    public void deletePayment(Long id, Long userId) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment Not Found!!"));

        //optional double authentication
        if(!payment.getUserId().equals(userId)){
            throw new RuntimeException("UNAUTHORIZED!!");
        }

        paymentRepository.deleteById(id);
    }
}










