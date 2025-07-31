package com.system.payment_reminder_system.schedular;


import com.system.payment_reminder_system.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PaymentReminderScheduler {

    @Autowired
    private PaymentService paymentService;

    @Scheduled(cron = "0 0 9 * * ?", zone = "Asia/Kolkata")
    public void SendDailyReminders() {paymentService.sendPaymentReminder();}

}
