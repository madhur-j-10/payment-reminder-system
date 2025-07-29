package com.system.payment_reminder_system.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentModel {

    private String paymentName;
    private String description;
    private BigDecimal amount;
    private String category;
    private LocalDateTime deadline;

    private Long userId;


}
