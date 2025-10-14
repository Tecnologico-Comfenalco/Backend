package com.tecno_comfenalco.pa.features.payment_receipt.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import com.tecno_comfenalco.pa.features.payment_receipt.PaymentReceiptEntity;

@NoRepositoryBean
public interface IPaymentReceiptRepository extends Repository<PaymentReceiptEntity, Long> {

}
