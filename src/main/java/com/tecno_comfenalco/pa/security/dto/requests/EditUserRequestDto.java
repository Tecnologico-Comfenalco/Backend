package com.tecno_comfenalco.pa.security.dto.requests;

import java.util.Set;

public record EditUserRequestDto(String username, String password, Set<String> roles, boolean enabled) {
}
