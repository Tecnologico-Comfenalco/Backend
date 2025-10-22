package com.tecno_comfenalco.pa.features.delivery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tecno_comfenalco.pa.features.delivery.dto.request.EditDeliveryRequestDto;
import com.tecno_comfenalco.pa.features.delivery.dto.request.RegisterDeliveryRequestDto;
import com.tecno_comfenalco.pa.features.delivery.dto.response.DeliveryResponseDto;
import com.tecno_comfenalco.pa.features.delivery.dto.response.EditDeliveryResponseDto;
import com.tecno_comfenalco.pa.features.delivery.dto.response.ListDeliveriesResponseDto;
import com.tecno_comfenalco.pa.features.delivery.dto.response.RegisterDeliveryResponseDto;
import com.tecno_comfenalco.pa.features.delivery.service.DeliveryService;
import com.tecno_comfenalco.pa.shared.utils.helper.ResponseEntityHelper;
import com.tecno_comfenalco.pa.shared.utils.result.Result;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/delivery")
@PreAuthorize("hasAnyRole('ADMIN','DELIVERY')")
public class DeliveryController {
    @Autowired
    public DeliveryService deliveryService;

    @PostMapping("/register")
    public ResponseEntity<RegisterDeliveryResponseDto> newDelivery(
            @RequestBody @Valid RegisterDeliveryRequestDto dtoDelivery) {
        Result<RegisterDeliveryResponseDto, Exception> result = deliveryService.newDelivery(dtoDelivery);
        return ResponseEntityHelper.toResponseEntity(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EditDeliveryResponseDto> editDelivery(@PathVariable Long id,
            @RequestBody @Valid EditDeliveryRequestDto dtoDelivery) {
        Result<EditDeliveryResponseDto, Exception> result = deliveryService.editDelivery(id, dtoDelivery);
        return ResponseEntityHelper.toResponseEntity(result);
    }

    @GetMapping
    public ResponseEntity<ListDeliveriesResponseDto> listDeliverys() {
        Result<ListDeliveriesResponseDto, Exception> result = deliveryService.listDelivery();
        return ResponseEntityHelper.toResponseEntity(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryResponseDto> showDelivery(@PathVariable Long id) {
        Result<DeliveryResponseDto, Exception> result = deliveryService.showDelivery(id);
        return ResponseEntityHelper.toResponseEntity(result);
    }
}
