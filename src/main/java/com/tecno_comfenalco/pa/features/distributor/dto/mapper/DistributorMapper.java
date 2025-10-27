package com.tecno_comfenalco.pa.features.distributor.dto.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tecno_comfenalco.pa.features.distributor.DistributorEntity;
import com.tecno_comfenalco.pa.features.distributor.dto.DistributorDto;
import com.tecno_comfenalco.pa.shared.mapper.EntityMapper;


@Service
public class DistributorMapper implements EntityMapper<DistributorDto, DistributorEntity> {
    

    //Mapeo basico de entidad a dto.
    @Override
    public DistributorDto toDto(DistributorEntity entity) {
        if (entity == null) {
            return null;
        }

        return new DistributorDto(
                entity.getId(),
                entity.getNIT(),
                entity.getName(),
                entity.getPhoneNumber(),
                entity.getEmail(),
                entity.getDirection());
    }

    //Mapeo basico de dto a entidad.
    @Override
    public DistributorEntity toEntity(DistributorDto dto) {
        if (dto == null) {
            return null;
        }

        DistributorEntity entity = new DistributorEntity();
        entity.setNIT(dto.NIT());
        entity.setName(dto.name());
        entity.setPhoneNumber(dto.phoneNumber());
        entity.setEmail(dto.email());
        entity.setDirection(dto.direction());

        return entity;
    }

    //Mapeo de actualizacion de dto a entidad existente.
    @Override
    public void updateEntityFromDto(DistributorDto dto, DistributorEntity entity) {
        if (dto == null || entity == null) {
            return;
        }

        //El id y el NIT creo que no se pueden modificar.
        entity.setName(dto.name());
        entity.setPhoneNumber(dto.phoneNumber());
        entity.setEmail(dto.email());
        entity.setDirection(dto.direction());
    }

    //Mapeo de colecciones: List y Set
    @Override
    public List<DistributorDto> toDto(List<DistributorEntity> entityList) {
        if (entityList == null) {
            return List.of();
        }

        return entityList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DistributorEntity> toEntity(List<DistributorDto> dtoList) {
        if (dtoList == null) {
            return List.of();
        }

        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Set<DistributorDto> toDto(Set<DistributorEntity> entitySet) {
        if (entitySet == null) {
            return Set.of();
        }

        return entitySet.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<DistributorEntity> toEntity(Set<DistributorDto> dtoSet) {
        if (dtoSet == null) {
            return Set.of();
        }

        return dtoSet.stream()
                .map(this::toEntity)
                .collect(Collectors.toSet());
    }
}
