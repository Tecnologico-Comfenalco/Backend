package com.tecno_comfenalco.pa.features.order.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tecno_comfenalco.pa.features.distributor.DistributorEntity;
import com.tecno_comfenalco.pa.features.distributor.repository.IDistributorRepository;
import com.tecno_comfenalco.pa.features.order.OrderDetailEntity;
import com.tecno_comfenalco.pa.features.order.OrderDetailIdEmbedded;
import com.tecno_comfenalco.pa.features.order.OrderEntity;
import com.tecno_comfenalco.pa.features.order.dto.OrderDto;
import com.tecno_comfenalco.pa.features.order.dto.OrderProductDto;
import com.tecno_comfenalco.pa.features.order.dto.request.CreateOrderRequestDto;
import com.tecno_comfenalco.pa.features.order.dto.response.CancelledOrderResponseDto;
import com.tecno_comfenalco.pa.features.order.dto.response.CreateOrderResponseDto;
import com.tecno_comfenalco.pa.features.order.dto.response.ListOrderResponseDto;
import com.tecno_comfenalco.pa.features.order.dto.response.ShowOrderResponseDto;
import com.tecno_comfenalco.pa.features.order.repository.IOrderRepository;
import com.tecno_comfenalco.pa.features.presales.PresalesEntity;
import com.tecno_comfenalco.pa.features.presales.dto.PresalesDto;
import com.tecno_comfenalco.pa.features.presales.repository.IPresalesRepository;
import com.tecno_comfenalco.pa.features.product.ProductEntity;
import com.tecno_comfenalco.pa.features.product.repository.IProductRepository;
import com.tecno_comfenalco.pa.features.store.StoreEntity;
import com.tecno_comfenalco.pa.features.store.StoresDistributorsEntity;
import com.tecno_comfenalco.pa.features.store.dto.StoreDto;
import com.tecno_comfenalco.pa.features.store.repository.IStoreRepository;
import com.tecno_comfenalco.pa.features.store.repository.IStoresDistributorsRepository;
import com.tecno_comfenalco.pa.security.CustomUserDetails;
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

    @Autowired
    private IStoresDistributorsRepository storesDistributorsRepository;

    @Autowired
    private IDistributorRepository distributorRepository;

    public Result<CreateOrderResponseDto, Exception> createOrder(CreateOrderRequestDto dtoOrder) {
        // Validar que la tienda exista
        var optionalStore = storeRepository.findById(dtoOrder.store_id());
        if (optionalStore.isEmpty()) {
            return Result.error(new Exception("The store does not exist!"));
        }
        StoreEntity store = optionalStore.get();

        // Validar que la distribuidora exista
        var optionalDistributor = distributorRepository.findById(dtoOrder.distributor_id());
        if (optionalDistributor.isEmpty()) {
            return Result.error(new Exception("The distributor does not exist!"));
        }
        DistributorEntity distributor = optionalDistributor.get();

        // El preventista es opcional - puede ser null si la tienda pide directamente
        PresalesEntity presale = null;
        if (dtoOrder.presales_id() != null) {
            var optionalPresales = presalesRepository.findById(dtoOrder.presales_id());
            if (optionalPresales.isEmpty()) {
                return Result.error(new Exception("The presales does not exist!"));
            }
            presale = optionalPresales.get();

            // Validar que el preventista pertenezca a la distribuidora especificada
            if (!presale.getDistributor().getId().equals(distributor.getId())) {
                return Result.error(new Exception(
                        "The presales does not belong to the specified distributor!"));
            }
        }

        try {
            /*
             * Verificar/crear la relación entre la tienda y la distribuidora
             * Si la relación no existe (tienda auto-registrada haciendo su primer pedido),
             * se crea automáticamente
             */
            var relationshipOpt = storesDistributorsRepository
                    .findByStore_IdAndDistributor_Id(store.getId(), distributor.getId());

            if (relationshipOpt.isEmpty()) {
                // La relación no existe, crear automáticamente
                StoresDistributorsEntity newRelationship = new StoresDistributorsEntity();
                newRelationship.setStore(store);
                newRelationship.setDistributor(distributor);
                newRelationship.setInternalClientCode(null); // El distribuidor puede asignarlo después
                storesDistributorsRepository.save(newRelationship);
            }

            /*
             * Empezamos seteando la entidad pedidos con su iva fijo de 19%, con su tienda,
             * preventista (si existe) y distribuidora
             */
            OrderEntity order = new OrderEntity();
            order.setIva_percent(0.19);
            order.setStore(store);
            order.setPresales(presale); // Puede ser null si la tienda pide directamente
            order.setStatus(OrderEntity.OrderStatus.PENDING);
            order.setDistributorId(distributor.getId()); // Guardar el ID de la distribuidora

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
                orderDetailEntity.setUnitPrice(productEntity.getPrice());

                details.add(orderDetailEntity);
                total += orderDetailEntity.getUnitPrice() * orderDetailEntity.getQuantity();
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

    /**
     * Cancela un pedido
     * Valida que el usuario tenga permiso para cancelar ese pedido:
     * - STORE: Solo puede cancelar pedidos de su tienda
     * - PRESALES: Solo puede cancelar pedidos que ha tomado
     */
    public Result<CancelledOrderResponseDto, Exception> cancelOrder(UUID id) {
        try {
            // Obtener el usuario autenticado
            CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
            Long userId = userDetails.getUserId();

            // Buscar el pedido
            var orderOpt = orderRepository.findByid(id);
            if (orderOpt.isEmpty()) {
                return Result.error(new Exception("Order not found"));
            }

            OrderEntity order = orderOpt.get();

            // Verificar si el usuario tiene rol STORE
            boolean isStore = userDetails.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_STORE"));

            // Verificar si el usuario tiene rol PRESALES
            boolean isPresales = userDetails.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_PRESALES"));

            // Validar permisos según el rol
            if (isStore) {
                // Buscar la tienda asociada al usuario
                var storeOpt = storeRepository.findByUser_Id(userId);
                if (storeOpt.isEmpty()) {
                    return Result.error(new Exception("Store not found for the authenticated user"));
                }
                StoreEntity store = storeOpt.get();

                // Validar que el pedido pertenezca a esta tienda
                if (!order.getStore().getId().equals(store.getId())) {
                    return Result.error(new Exception("You don't have permission to cancel this order"));
                }

            } else if (isPresales) {
                // Buscar el preventista asociado al usuario
                var presalesOpt = presalesRepository.findByUser_Id(userId);
                if (presalesOpt.isEmpty()) {
                    return Result.error(new Exception("Presales not found for the authenticated user"));
                }
                PresalesEntity presales = presalesOpt.get();

                // Validar que el pedido haya sido tomado por este preventista
                if (order.getPresales() == null || !order.getPresales().getId().equals(presales.getId())) {
                    return Result.error(new Exception("You don't have permission to cancel this order"));
                }

            } else {
                return Result.error(new Exception("User does not have STORE or PRESALES role"));
            }

            // Cancelar el pedido
            order.setStatus(OrderEntity.OrderStatus.CANCELLED);
            orderRepository.save(order);

            CancelledOrderResponseDto response = new CancelledOrderResponseDto("Order cancelled successfully!");
            return Result.ok(response);

        } catch (Exception e) {
            return Result.error(new Exception("Error to cancel order: " + e.getMessage()));
        }
    }

    /**
     * Lista los pedidos según el rol del usuario autenticado
     * - STORE: Solo ve sus propios pedidos
     * - PRESALES: Solo ve los pedidos que ha tomado
     */
    public Result<ListOrderResponseDto, Exception> listOrders() {
        try {
            // Obtener el usuario autenticado
            CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
            Long userId = userDetails.getUserId();

            List<OrderEntity> orderEntities;

            // Verificar si el usuario tiene rol STORE
            boolean isStore = userDetails.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_STORE"));

            // Verificar si el usuario tiene rol PRESALES
            boolean isPresales = userDetails.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_PRESALES"));

            if (isStore) {
                // Buscar la tienda asociada al usuario
                var storeOpt = storeRepository.findByUser_Id(userId);
                if (storeOpt.isEmpty()) {
                    return Result.error(new Exception("Store not found for the authenticated user"));
                }
                StoreEntity store = storeOpt.get();

                // Obtener solo los pedidos de esta tienda
                orderEntities = orderRepository.findByStore_Id(store.getId());

            } else if (isPresales) {
                // Buscar el preventista asociado al usuario
                var presalesOpt = presalesRepository.findByUser_Id(userId);
                if (presalesOpt.isEmpty()) {
                    return Result.error(new Exception("Presales not found for the authenticated user"));
                }
                PresalesEntity presales = presalesOpt.get();

                // Obtener solo los pedidos tomados por este preventista
                orderEntities = orderRepository.findByPresales_Id(presales.getId());

            } else {
                return Result.error(new Exception("User does not have STORE or PRESALES role"));
            }

            // Mapear las entidades a DTOs
            List<OrderDto> orderDtos = orderEntities.stream()
                    .map(order -> {
                        // Obtener el código interno desde la relación StoresDistributors
                        String internalClientCode = null;
                        var relationshipOpt = storesDistributorsRepository
                                .findByStore_IdAndDistributor_Id(order.getStore().getId(), order.getDistributorId());
                        if (relationshipOpt.isPresent()) {
                            internalClientCode = relationshipOpt.get().getInternalClientCode();
                        }

                        return new OrderDto(
                                order.getId(),
                                order.getIva_percent(),
                                order.getTotal(),
                                order.getStatus(),
                                new StoreDto(order.getStore().getId(), order.getStore().getNIT(),
                                        order.getStore().getName(),
                                        order.getStore().getPhoneNumber(), order.getStore().getEmail(),
                                        order.getStore().getDirection()),
                                order.getPresales() != null
                                        ? new PresalesDto(order.getPresales().getId(),
                                                order.getPresales().getName(), order.getPresales().getPhoneNumber(),
                                                order.getPresales().getEmail(), order.getPresales().getDocumentType(),
                                                order.getPresales().getDocumentNumber(),
                                                order.getPresales().getUser().getId(),
                                                order.getPresales().getDistributor().getId())
                                        : null,
                                order.getOrderDetails().stream().map(detail -> new OrderProductDto(
                                        detail.getProduct().getId(),
                                        detail.getQuantity(),
                                        detail.getUnitPrice())).toList(),
                                internalClientCode, // Código interno desde la relación StoresDistributors
                                order.getDistributorId() // ID de la distribuidora
                        );
                    })
                    .toList();

            ListOrderResponseDto response = new ListOrderResponseDto(orderDtos, "Orders successfully obtained");

            return Result.ok(response);

        } catch (Exception e) {
            return Result.error(new Exception("Error to get orders: " + e.getMessage()));
        }
    }

    /**
     * Muestra un pedido específico
     * Valida que el usuario tenga permiso para ver ese pedido:
     * - STORE: Solo puede ver pedidos de su tienda
     * - PRESALES: Solo puede ver pedidos que ha tomado
     */
    public Result<ShowOrderResponseDto, Exception> showOrder(UUID id) {
        try {
            // Obtener el usuario autenticado
            CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
            Long userId = userDetails.getUserId();

            // Buscar el pedido
            var orderOpt = orderRepository.findByid(id);
            if (orderOpt.isEmpty()) {
                return Result.error(new Exception("Order not found"));
            }

            OrderEntity order = orderOpt.get();

            // Verificar si el usuario tiene rol STORE
            boolean isStore = userDetails.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_STORE"));

            // Verificar si el usuario tiene rol PRESALES
            boolean isPresales = userDetails.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_PRESALES"));

            // Validar permisos según el rol
            if (isStore) {
                // Buscar la tienda asociada al usuario
                var storeOpt = storeRepository.findByUser_Id(userId);
                if (storeOpt.isEmpty()) {
                    return Result.error(new Exception("Store not found for the authenticated user"));
                }
                StoreEntity store = storeOpt.get();

                // Validar que el pedido pertenezca a esta tienda
                if (!order.getStore().getId().equals(store.getId())) {
                    return Result.error(new Exception("You don't have permission to view this order"));
                }

            } else if (isPresales) {
                // Buscar el preventista asociado al usuario
                var presalesOpt = presalesRepository.findByUser_Id(userId);
                if (presalesOpt.isEmpty()) {
                    return Result.error(new Exception("Presales not found for the authenticated user"));
                }
                PresalesEntity presales = presalesOpt.get();

                // Validar que el pedido haya sido tomado por este preventista
                if (order.getPresales() == null || !order.getPresales().getId().equals(presales.getId())) {
                    return Result.error(new Exception("You don't have permission to view this order"));
                }

            } else {
                return Result.error(new Exception("User does not have STORE or PRESALES role"));
            }

            // Obtener el código interno desde la relación StoresDistributors
            String internalClientCode = null;
            var relationshipOpt = storesDistributorsRepository
                    .findByStore_IdAndDistributor_Id(order.getStore().getId(), order.getDistributorId());
            if (relationshipOpt.isPresent()) {
                internalClientCode = relationshipOpt.get().getInternalClientCode();
            }

            // Mapear a DTO
            OrderDto orderDto = new OrderDto(
                    order.getId(),
                    order.getIva_percent(),
                    order.getTotal(),
                    order.getStatus(),
                    new StoreDto(order.getStore().getId(), order.getStore().getNIT(), order.getStore().getName(),
                            order.getStore().getPhoneNumber(), order.getStore().getEmail(),
                            order.getStore().getDirection()),
                    order.getPresales() != null
                            ? new PresalesDto(order.getPresales().getId(),
                                    order.getPresales().getName(), order.getPresales().getPhoneNumber(),
                                    order.getPresales().getEmail(), order.getPresales().getDocumentType(),
                                    order.getPresales().getDocumentNumber(), order.getPresales().getUser().getId(),
                                    order.getPresales().getDistributor().getId())
                            : null,
                    order.getOrderDetails().stream().map(detail -> new OrderProductDto(
                            detail.getProduct().getId(),
                            detail.getQuantity(),
                            detail.getUnitPrice())).toList(),
                    internalClientCode, // Código interno desde la relación StoresDistributors
                    order.getDistributorId() // ID de la distribuidora
            );

            ShowOrderResponseDto response = new ShowOrderResponseDto(orderDto, "Order successfully obtained!");

            return Result.ok(response);

        } catch (Exception e) {
            return Result.error(new Exception("Error to show Order: " + e.getMessage()));
        }
    }
}
