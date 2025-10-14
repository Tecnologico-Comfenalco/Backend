package com.tecno_comfenalco.pa.features.routes.deliveries;

import com.tecno_comfenalco.pa.features.delivery.DeliveryEntity;
import com.tecno_comfenalco.pa.features.payment_receipt.PaymentReceiptEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class DeliveryRoutesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "payment_receipt_id", referencedColumnName = "id")
    private PaymentReceiptEntity paymentReceipt;

    @ManyToOne
    @JoinColumn(name = "delivery_id", referencedColumnName = "id", nullable = true, unique = false)
    private DeliveryEntity delivery;
}
