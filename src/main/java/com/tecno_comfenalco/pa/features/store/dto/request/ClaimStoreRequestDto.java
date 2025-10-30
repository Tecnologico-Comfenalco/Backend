package com.tecno_comfenalco.pa.features.store.dto.request;

/**
 * DTO para que el DUEÑO de la tienda reclame su tienda
 */
public record ClaimStoreRequestDto(
        Long NIT, // Identificador único para encontrar la tienda
        String email, // Email del dueño para validación
        String password // Password para crear el usuario
) {
}
