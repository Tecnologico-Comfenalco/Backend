package com.tecno_comfenalco.pa.features.payment_receipt.repository.mongo;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.tecno_comfenalco.pa.features.payment_receipt.PaymentReceiptEntity;
import com.tecno_comfenalco.pa.features.payment_receipt.repository.IPaymentReceiptRepository;

@Profile("mongo")
public interface IMongoPaymentReceiptRepository
        extends MongoRepository<PaymentReceiptEntity, Long>, IPaymentReceiptRepository {

}
