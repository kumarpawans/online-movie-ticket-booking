package com.psp.test.model.service;

import com.psp.test.model.PaymentDetails;

public class PaymentService {
    public boolean processPayment(PaymentDetails paymentDetails) {
        // validate payment details
        if (paymentDetails.getPaymentMethod() == null || paymentDetails.getPaymentMethod().trim().length() == 0) {
            return false;
        }
        if (paymentDetails.getPaymentAmount() <= 0) {
            return false;
        }

        // process payment
        try {
            // simulate payment processing
            Thread.sleep(1000);
            return true;
        } catch (InterruptedException e) {
            return false;
        }
    }
}

