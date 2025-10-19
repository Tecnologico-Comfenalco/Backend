package com.tecno_comfenalco.pa.security.dto.requests;

import java.util.Set;

public record RegisterUserRequestDto(String username, String password, Set<String> roles, boolean enabled) {
    public RegisterUserRequestDto(String username, String password, Set<String> roles) {
        this(username, password, roles, true);
    }
}
