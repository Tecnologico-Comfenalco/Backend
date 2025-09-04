package com.tecno_comfenalco.pa.security.dto.requests;

public record LoginRequestDto(String username, String password, boolean rememberMe) {
    public LoginRequestDto(String username, String password) {
        this(username, password, false);
    }
}
