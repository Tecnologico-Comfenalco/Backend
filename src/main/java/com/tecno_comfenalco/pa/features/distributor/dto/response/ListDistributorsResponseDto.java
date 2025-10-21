package com.tecno_comfenalco.pa.features.distributor.dto.response;

import java.util.List;

import com.tecno_comfenalco.pa.features.distributor.dto.DistributorDto;

public record ListDistributorsResponseDto(List<DistributorDto> distributorEntities, String message) {

}
