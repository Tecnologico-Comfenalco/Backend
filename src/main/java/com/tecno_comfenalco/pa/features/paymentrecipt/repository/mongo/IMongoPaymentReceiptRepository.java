package com.tecno_comfenalco.pa.features.paymentrecipt.repository.mongo;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.tecno_comfenalco.pa.features.paymentrecipt.PaymentReceiptEntity;
import com.tecno_comfenalco.pa.features.paymentrecipt.repository.IPaymentReceiptRepository;

@Profile("mongo")
public interface IMongoPaymentReceiptRepository
        extends MongoRepository<PaymentReceiptEntity, Long>, IPaymentReceiptRepository {

}
