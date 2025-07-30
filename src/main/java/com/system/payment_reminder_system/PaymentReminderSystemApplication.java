package com.system.payment_reminder_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class PaymentReminderSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentReminderSystemApplication.class, args);
	}

}
