package com.tecno_comfenalco.pa.security.dto;

import java.util.Set;

public record UserDto(String username, Set<String> roles, boolean enabled) {

}
