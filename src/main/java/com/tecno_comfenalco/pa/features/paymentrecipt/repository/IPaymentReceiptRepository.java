package com.tecno_comfenalco.pa.features.paymentrecipt.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import com.tecno_comfenalco.pa.features.paymentrecipt.PaymentReceiptEntity;

@NoRepositoryBean
public interface IPaymentReceiptRepository extends Repository<PaymentReceiptEntity, Long> {

}
