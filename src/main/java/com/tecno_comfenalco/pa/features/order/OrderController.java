package com.tecno_comfenalco.pa.features.order;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tecno_comfenalco.pa.features.order.dto.request.CreateOrderRequestDto;
import com.tecno_comfenalco.pa.features.order.dto.request.EditOrderRequetDto;
import com.tecno_comfenalco.pa.features.order.dto.response.CreateOrderResponseDto;
import com.tecno_comfenalco.pa.features.order.dto.response.CanceledOrderResponseDto;
import com.tecno_comfenalco.pa.features.order.dto.response.EditOrderResponseDto;
import com.tecno_comfenalco.pa.features.order.dto.response.ListOrderResponseDto;
import com.tecno_comfenalco.pa.features.order.dto.response.ShowOrderResponseDto;
import com.tecno_comfenalco.pa.features.order.service.OrderService;
import com.tecno_comfenalco.pa.shared.utils.helper.ResponseEntityHelper;
import com.tecno_comfenalco.pa.shared.utils.result.Result;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/order")
@PreAuthorize("hasAnyRole('ADMIN', 'DISTRIBUTOR', 'STORE', 'PRESALES')")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<CreateOrderResponseDto> createOrder(@RequestBody @Valid CreateOrderRequestDto dtoOrder) {
        Result<CreateOrderResponseDto, Exception> result = orderService.createOrder(dtoOrder);
        return ResponseEntityHelper.toResponseEntity(result);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EditOrderResponseDto> editOrder(@PathVariable UUID id,
            @RequestBody @Valid EditOrderRequetDto dtoOrder) {
        Result<EditOrderResponseDto, Exception> result = orderService.editOrder(id, dtoOrder);
        return ResponseEntityHelper.toResponseEntity(result);
    }

    @DeleteMapping("/order/{id}")
    public ResponseEntity<CanceledOrderResponseDto> canceledOrder(@PathVariable UUID id) {
        Result<CanceledOrderResponseDto, Exception> result = orderService.canceledOrder(id);
        return ResponseEntityHelper.toResponseEntity(result);
    }

    @GetMapping
    public ResponseEntity<ListOrderResponseDto> listOrders() {
        Result<ListOrderResponseDto, Exception> result = orderService.listOrders();
        return ResponseEntityHelper.toResponseEntity(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowOrderResponseDto> showOrder(@PathVariable UUID id) {
        Result<ShowOrderResponseDto, Exception> result = orderService.showOrder(id);
        return ResponseEntityHelper.toResponseEntity(result);
    }
}
