package com.tecno_comfenalco.pa.security.dto.responses;

import java.util.Set;

public record LoginResponseDto(String message, String token, Set<String> roles) {
}
