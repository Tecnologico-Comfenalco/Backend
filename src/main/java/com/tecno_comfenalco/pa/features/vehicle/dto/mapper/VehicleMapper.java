package com.tecno_comfenalco.pa.features.vehicle.dto.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tecno_comfenalco.pa.features.vehicle.VehicleEntity;
import com.tecno_comfenalco.pa.features.vehicle.dto.VehicleDto;
import com.tecno_comfenalco.pa.shared.mapper.EntityMapper;

//Implementar la logica del mapper.
@Service
public class VehicleMapper implements EntityMapper<VehicleDto, VehicleEntity> {

    // Mapeo basico de entidad a dto
    @Override
    public VehicleDto toDto(VehicleEntity entity) {
        if (entity == null) {
            return null;
        }

        // Usamos el contructor del record de VehicleDto para mapear los campos
        return new VehicleDto(
                entity.getVehiclePlate(),
                entity.getModel(),
                entity.getBrand());
    }

    // Mapeo basico de dto a entidad
    @Override
    public VehicleEntity toEntity(VehicleDto dto) {
        if (dto == null) {
            return null;
        }

        // El id no se mapea porque es generado por la base de datos.
        // Creamos una nueva instancia de VehicleEntity y seteamos los campos.
        VehicleEntity entity = new VehicleEntity();
        entity.setVehiclePlate(dto.vehiclePlate());
        entity.setModel(dto.model());
        entity.setBrand(dto.brand());

        return entity;
    }

    // Mapeo de actualizacion de dto a entidad existente.
    @Override
    public void updateEntityFromDto(VehicleDto dto, VehicleEntity entity) {
        if (dto == null || entity == null) {
            return;
        }

        // Actualizamos los campos que nos interesa modificar desde el dto.
        entity.setVehiclePlate(dto.vehiclePlate());
        entity.setModel(dto.model());
        entity.setBrand(dto.brand());
    }

    // Mapeo de colecciones: List<Entity> a List<Dto>.
    @Override
    public List<VehicleEntity> toEntity(List<VehicleDto> dtoList) {
        if (dtoList == null) {
            return List.of();
        }

        // Usamos Stream para mapear toEntity en cada elemento de la lista.
        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    // Mapeo de collecciones: Set
    @Override
    public Set<VehicleDto> toDto(Set<VehicleEntity> entitySet) {
        if (entitySet == null) {
            return Set.of();
        }

        // Usamos Stream para mapear toDto en cada elemento del set.
        return entitySet.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<VehicleEntity> toEntity(Set<VehicleDto> dtoSet) {
        if (dtoSet == null) {
            return Set.of();
        }

        // Usamos Stream para mapear toEntity en cada elemento del set.
        return dtoSet.stream()
                .map(this::toEntity)
                .collect(Collectors.toSet());
    }

    @Override
    public List<VehicleDto> toDto(List<VehicleEntity> entityList) {
        if (entityList == null) {
            return List.of(); // Devuelve una lista vacia si la entrada es nula.
        }

        // Usamos Stream para mapear toDto en cada elemento de la lista.
        return entityList.stream()
                // Llamamos al metodo toDto para cada elemento de la lista.
                .map(this::toDto)
                // Convertimos el Stream de vuelta a una lista.
                .collect(Collectors.toList());
    }
}
