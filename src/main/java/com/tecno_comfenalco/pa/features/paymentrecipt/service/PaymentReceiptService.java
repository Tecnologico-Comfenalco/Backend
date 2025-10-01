package com.tecno_comfenalco.pa.features.paymentrecipt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tecno_comfenalco.pa.features.paymentrecipt.repository.IPaymentReceiptRepository;

@Service
public class PaymentReceiptService {
    @Autowired
    private IPaymentReceiptRepository paymentReceiptRepository;
}
