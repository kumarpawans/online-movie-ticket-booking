package com.psp.test.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class PaymentDetails {
    private String paymentMethod;
    private double paymentAmount;
    private String cardHolderName;
    private String cardNumber;
    private String expirationDate;
    private String cvv;
    private  User user;
}

