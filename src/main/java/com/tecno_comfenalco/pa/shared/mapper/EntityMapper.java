package com.tecno_comfenalco.pa.shared.mapper;

import java.util.List;
import java.util.Set;

//Interfaz para mapear entidades y DTOs.
//<D> Tipo de DTO.
//<E> Tipo de Entidad.

public interface EntityMapper<D, E> {


    //Convierte una entidad a un DTO.
    //Entity la entidad a convertir.
    D toDto(E entity);


    //dto el DTO a convertir.
    //Convierte un DTO a una entidad.
    E toEntity(D dto);


    //Convierte una lista de entidades a una lista de DTOs.
    //entityList la lista de entidades a convertir.
    List<D> toDto(List<E> entityList);


    //Convierte una lista de DTOs a una lista de entidades.
    //dtoList la lista de DTOs a convertir.
    List<E> toEntity(List<D> dtoList);


    //Convierte un conjunto de entidades a un conjunto de DTOs.
    //entitySet el conjunto de entidades a convertir.
    Set<D> toDto(Set<E> entitySet);


    //Convierte un conjunto de DTOs a un conjunto de entidades.
    //dtoSet el conjunto de DTOs a convertir.
    Set<E> toEntity(Set<D> dtoSet);


    //Actualiza una entidad existente con los datos de un DTO.
    //dto el DTO con los datos actualizados.
    void updateEntityFromDto(D dto, E entity);

}
