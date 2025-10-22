package com.tecno_comfenalco.pa.security.dto.responses;

import java.util.Set;

import com.tecno_comfenalco.pa.security.dto.UserDto;

public record UserResponseDto(UserDto user, String message) {
    public UserResponseDto(Long id, String username, Set<String> roles, boolean enabled) {
        this(new UserDto(id, username, roles, enabled), "Usuario obtenido exitosamente");
    }
}
