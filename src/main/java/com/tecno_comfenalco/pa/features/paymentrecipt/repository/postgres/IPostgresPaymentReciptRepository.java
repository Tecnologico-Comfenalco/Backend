package com.tecno_comfenalco.pa.features.paymentrecipt.repository.postgres;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tecno_comfenalco.pa.features.paymentrecipt.PaymentReceiptEntity;
import com.tecno_comfenalco.pa.features.paymentrecipt.repository.IPaymentReceiptRepository;

@Profile("postgres")
public interface IPostgresPaymentReciptRepository
        extends JpaRepository<PaymentReceiptEntity, Long>, IPaymentReceiptRepository {

}
