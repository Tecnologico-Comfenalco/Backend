package com.tecno_comfenalco.pa.features.comprobantes.repository.postgres;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tecno_comfenalco.pa.features.comprobantes.PaymentReceiptEntity;
import com.tecno_comfenalco.pa.features.comprobantes.repository.IPaymentReceiptRepository;

@Profile("postgres")
public interface IPostgresPaymentReciptRepository
        extends JpaRepository<PaymentReceiptEntity, Long>, IPaymentReceiptRepository {

}
