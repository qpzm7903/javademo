package com.example.transaction_demo.domain.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Payment {
    private String id;
    private Date paymentDate;
    private BigDecimal amount;
    private PaymentStatus paymentStatus;
}
