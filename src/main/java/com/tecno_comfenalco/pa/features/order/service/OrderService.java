package com.tecno_comfenalco.pa.features.order.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tecno_comfenalco.pa.features.order.OrderDetailEntity;
import com.tecno_comfenalco.pa.features.order.OrderDetailIdEmbedded;
import com.tecno_comfenalco.pa.features.order.OrderEntity;
import com.tecno_comfenalco.pa.features.order.dto.OrderDto;
import com.tecno_comfenalco.pa.features.order.dto.OrderProductDto;
import com.tecno_comfenalco.pa.features.order.dto.request.CreateOrderRequestDto;
import com.tecno_comfenalco.pa.features.order.dto.request.EditOrderRequetDto;
import com.tecno_comfenalco.pa.features.order.dto.response.CanceledOrderResponseDto;
import com.tecno_comfenalco.pa.features.order.dto.response.CreateOrderResponseDto;
import com.tecno_comfenalco.pa.features.order.dto.response.EditOrderResponseDto;
import com.tecno_comfenalco.pa.features.order.dto.response.ListOrderResponseDto;
import com.tecno_comfenalco.pa.features.order.dto.response.ShowOrderResponseDto;
import com.tecno_comfenalco.pa.features.order.repository.IOrderRepository;
import com.tecno_comfenalco.pa.features.presales.PresalesEntity;
import com.tecno_comfenalco.pa.features.presales.dto.PresalesDto;
import com.tecno_comfenalco.pa.features.presales.repository.IPresalesRepository;
import com.tecno_comfenalco.pa.features.product.ProductEntity;
import com.tecno_comfenalco.pa.features.product.repository.IProductRepository;
import com.tecno_comfenalco.pa.features.store.StoreEntity;
import com.tecno_comfenalco.pa.features.store.dto.StoreDto;
import com.tecno_comfenalco.pa.features.store.repository.IStoreRepository;
import com.tecno_comfenalco.pa.shared.utils.result.Result;

@Service
public class OrderService {
    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private IStoreRepository storeRepository;

    @Autowired
    private IPresalesRepository presalesRepository;

    @Autowired
    private IProductRepository productRepository;

    public Result<CreateOrderResponseDto, Exception> createOrder(CreateOrderRequestDto dtoOrder) {
        // validar que la tienda exista !importante
        var optionalStore = storeRepository.findById(dtoOrder.store_id());
        if (optionalStore.isEmpty()) {
            return Result.error(new Exception("The store does not exist!"));
        }
        StoreEntity store = optionalStore.get();

        // el preventista puede ser nulo en este caso porque un pedido puede ser
        // realizado enteramente por la tienda
        PresalesEntity presale = presalesRepository.findById(dtoOrder.presales_id()).orElse(null);

        try {

            /*
             * Empezamos seteando la entidad pedidos con su iva fijo de 19%, con su tienda y
             * preventista si este existe!
             */
            OrderEntity order = new OrderEntity();
            order.setIva_percent(0.19);
            order.setStore(store);
            order.setPresales(presale);

            // preparamos el arrayList de detalles de pedido
            List<OrderDetailEntity> details = new ArrayList<>();
            double total = 0; // esto para calcular el total del pedido!;

            // recorremos cada producto de la lista que le llega del dto
            for (OrderProductDto p : dtoOrder.productEntities()) {
                ProductEntity productEntity = productRepository.findById(p.id())
                        .orElseThrow(() -> new RuntimeException("The product not exists!"));

                // enlazamos la id de pedido y la id de producto
                OrderDetailIdEmbedded orderDetailIdEmbedded = new OrderDetailIdEmbedded();
                orderDetailIdEmbedded.setOrderId(order.getId());
                orderDetailIdEmbedded.setProductId(productEntity.getId());

                // terminamos de setear el objeto del detalle de pedido
                OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
                orderDetailEntity.setId(orderDetailIdEmbedded);
                orderDetailEntity.setOrder(order);
                orderDetailEntity.setProduct(productEntity);
                orderDetailEntity.setQuantity(p.quantity());

                details.add(orderDetailEntity);
                total += productEntity.getPrice() * p.quantity();
            }

            order.setTotal(total);
            order.setOrderDetails(details);

            // guardamos el pedido en la base de datos
            orderRepository.save(order);

            CreateOrderResponseDto response = new CreateOrderResponseDto("Order created succesfull!");
            return Result.ok(response);
        } catch (Exception e) {
            return Result.error(new Exception("Error to create order!", e));
        }
    }

    public Result<EditOrderResponseDto, Exception> editOrder(UUID order_id, EditOrderRequetDto dtoOrder) {
        try {
            OrderEntity orderEntity = orderRepository.findByid(order_id)
                    .orElseThrow(() -> new RuntimeException("Order not found!"));

            List<OrderDetailEntity> newDetails = dtoOrder.productEntities().stream().map(p -> {
                ProductEntity product = productRepository.findById(p.id())
                        .orElseThrow(() -> new RuntimeException("Product not found"));

                OrderDetailIdEmbedded id = new OrderDetailIdEmbedded();
                id.setOrderId(orderEntity.getId());
                id.setProductId(product.getId());

                OrderDetailEntity detail = new OrderDetailEntity();
                detail.setId(id);
                detail.setOrder(orderEntity);
                detail.setProduct(product);
                detail.setQuantity(p.quantity());
                return detail;
            }).toList();

            orderEntity.setOrderDetails(newDetails);
            orderRepository.save(orderEntity);

            EditOrderResponseDto response = new EditOrderResponseDto("order modified succesfull!");
            return Result.ok(response);

        } catch (Exception e) {
            return Result.error(new Exception("Error to editing order!"));
        }
    }

    public Result<CanceledOrderResponseDto, Exception> canceledOrder(UUID id) {
        try {
            return orderRepository.findByid(id).map(order -> {
                orderRepository.deleteById(id);
                CanceledOrderResponseDto response = new CanceledOrderResponseDto("Order remove succesfull!");

                return Result.ok(response);
            }).orElseGet(() -> Result.error(new Exception("Order not found!")));
        } catch (Exception e) {
            return Result.error(new Exception("Error to remove order!"));
        }
    }

    public Result<ListOrderResponseDto, Exception> listOrders() {
        List<OrderEntity> orderEntities = orderRepository.findAll();
        try {
            List<OrderDto> orderDtos = orderEntities.stream()
                    .map(order -> new OrderDto(order.getId(), order.getIva_percent(), order.getTotal(),
                            new StoreDto(order.getStore().getNIT(), order.getStore().getName(),
                                    order.getStore().getPhoneNumber(), order.getStore().getEmail(),
                                    order.getStore().getDirection()),
                            new PresalesDto(order.getPresales().getId(),
                                    order.getPresales().getName(), order.getPresales().getPhoneNumber(),
                                    order.getPresales().getEmail(), order.getPresales().getDocumentType(),
                                    order.getPresales().getDocumentNumber(), order.getPresales().getUser().getId(),
                                    order.getPresales().getDistributor().getId()),
                            order.getOrderDetails().stream().map(detail -> new OrderProductDto(
                                    detail.getProduct().getId(),
                                    detail.getQuantity())).toList()))
                    .toList();

            ListOrderResponseDto response = new ListOrderResponseDto(orderDtos, "orders successfully obtained");

            return Result.ok(response);

        } catch (Exception e) {
            return Result.error(new Exception("Error to get all orders!"));
        }
    }

    public Result<ShowOrderResponseDto, Exception> showOrder(UUID id) {
        try {
            return orderRepository.findByid(id).map(order -> {
                OrderDto orderDto = new OrderDto(order.getId(), order.getIva_percent(), order.getTotal(),
                        new StoreDto(order.getStore().getNIT(), order.getStore().getName(),
                                order.getStore().getPhoneNumber(), order.getStore().getEmail(),
                                order.getStore().getDirection()),
                        new PresalesDto(order.getPresales().getId(),
                                order.getPresales().getName(), order.getPresales().getPhoneNumber(),
                                order.getPresales().getEmail(), order.getPresales().getDocumentType(),
                                order.getPresales().getDocumentNumber(), order.getPresales().getUser().getId(),
                                order.getPresales().getDistributor().getId()),
                        order.getOrderDetails().stream().map(detail -> new OrderProductDto(
                                detail.getProduct().getId(),
                                detail.getQuantity())).toList());

                ShowOrderResponseDto response = new ShowOrderResponseDto(orderDto, "Order succesfull obtain!");

                return Result.ok(response);
            }).orElseGet(() -> Result.error(new Exception("Order not found!")));

        } catch (Exception e) {
            return Result.error(new Exception("Error to show Order!"));
        }
    }
}
