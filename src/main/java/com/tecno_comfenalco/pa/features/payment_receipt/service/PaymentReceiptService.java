package com.tecno_comfenalco.pa.features.payment_receipt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tecno_comfenalco.pa.features.payment_receipt.repository.IPaymentReceiptRepository;

@Service
public class PaymentReceiptService {
    @Autowired
    private IPaymentReceiptRepository paymentReceiptRepository;
}
