package com.tecno_comfenalco.pa.features.comprobantes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tecno_comfenalco.pa.features.comprobantes.repository.IPaymentReceiptRepository;

@Service
public class PaymentReceiptService {
    @Autowired
    private IPaymentReceiptRepository sellRepository;
}
