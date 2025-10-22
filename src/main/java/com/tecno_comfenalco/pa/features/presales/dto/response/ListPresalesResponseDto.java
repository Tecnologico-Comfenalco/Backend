package com.tecno_comfenalco.pa.features.presales.dto.response;

import java.util.List;

import com.tecno_comfenalco.pa.features.presales.dto.PresalesDto;

public record ListPresalesResponseDto(List<PresalesDto> presales, String message) {

}
