package com.tecno_comfenalco.pa.security.dto.responses;

import java.util.Set;

import com.tecno_comfenalco.pa.security.dto.UserDto;

public record UserResponseDto(UserDto user, String message) {
    public UserResponseDto(String username, Set<String> roles, boolean enabled) {
        this(new UserDto(username, roles, enabled), "Usuario obtenido exitosamente");
    }
}
