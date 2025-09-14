package com.tecno_comfenalco.pa.features.comprobantes.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import com.tecno_comfenalco.pa.features.comprobantes.PaymentReceiptEntity;

@NoRepositoryBean
public interface IPaymentReceiptRepository extends Repository<PaymentReceiptEntity, Long> {

}
