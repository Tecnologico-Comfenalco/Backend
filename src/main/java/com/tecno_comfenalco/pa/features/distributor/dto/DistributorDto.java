package com.tecno_comfenalco.pa.features.distributor.dto;

import com.tecno_comfenalco.pa.shared.dto.DirectionDto;

public record DistributorDto(Long id, Long NIT, String name, String phoneNumber, String email, DirectionDto direction) {

}
