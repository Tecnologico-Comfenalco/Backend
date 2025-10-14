package com.tecno_comfenalco.pa.features.payment_receipt.repository.postgres;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tecno_comfenalco.pa.features.payment_receipt.PaymentReceiptEntity;
import com.tecno_comfenalco.pa.features.payment_receipt.repository.IPaymentReceiptRepository;

@Profile("postgres")
public interface IPostgresPaymentReciptRepository
        extends JpaRepository<PaymentReceiptEntity, Long>, IPaymentReceiptRepository {

}
