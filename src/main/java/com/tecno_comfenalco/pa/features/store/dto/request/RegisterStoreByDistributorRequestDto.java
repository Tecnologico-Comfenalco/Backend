package com.tecno_comfenalco.pa.features.store.dto.request;

import com.tecno_comfenalco.pa.shared.dto.DirectionDto;

/**
 * DTO para que una DISTRIBUIDORA registre una tienda (sin usuario)
 */
public record RegisterStoreByDistributorRequestDto(
    Long NIT,
    String name,
    String phoneNumber,
    String email,
    DirectionDto direction,
    String internalClientCode // Código de cliente interno de la distribuidora
) {}
