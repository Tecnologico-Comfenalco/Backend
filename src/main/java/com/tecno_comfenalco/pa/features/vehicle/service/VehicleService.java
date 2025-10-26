package com.tecno_comfenalco.pa.features.vehicle.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tecno_comfenalco.pa.features.vehicle.VehicleEntity;
import com.tecno_comfenalco.pa.features.vehicle.dto.VehicleDto;
import com.tecno_comfenalco.pa.features.vehicle.dto.mapper.VehicleMapper;
import com.tecno_comfenalco.pa.features.vehicle.dto.request.RegisterVehicleRequestDto;
import com.tecno_comfenalco.pa.features.vehicle.dto.response.DisableVehicleResponseDto;
import com.tecno_comfenalco.pa.features.vehicle.dto.response.ListVehiclesResponseDto;
import com.tecno_comfenalco.pa.features.vehicle.dto.response.RegisterVehicleResponseDto;
import com.tecno_comfenalco.pa.features.vehicle.dto.response.VehicleResponseDto;
import com.tecno_comfenalco.pa.features.vehicle.repository.IVehicleRepository;
import com.tecno_comfenalco.pa.shared.utils.result.Result;

@Service
public class VehicleService {

    @Autowired
    private IVehicleRepository vehicleRepository;

    private final VehicleMapper vehicleMapper;

    //Definimos el constructor para inyectar el mapper.
    public VehicleService(IVehicleRepository  vehicleRepository, VehicleMapper vehicleMapper) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleMapper = vehicleMapper;
    }

    //Implementacion de nuevo metodo Vechicle(CREATE).
    //Se usa el mapper para convertir entre dto de request a entidad.

    public Result<RegisterVehicleResponseDto, Exception> newVehicle(RegisterVehicleRequestDto dtoVehicle) {
        

        if (vehicleRepository.existsByName(dtoVehicle.vehiclePlate())) {
            return Result.error(new Exception("Vehicle plate already exists!"));
        }

        //Usamos el mapper para convertir el dto a entidad.
        try {
            VehicleEntity vehicleEntity = vehicleMapper.toEntity(
                    new VehicleDto(dtoVehicle.vehiclePlate(), dtoVehicle.model(), dtoVehicle.brand()));

                    vehicleEntity = vehicleRepository.save(vehicleEntity);

                    RegisterVehicleResponseDto response = new RegisterVehicleResponseDto("Vehicle registered successfully!");
                    return Result.ok(response);

        } catch (Exception e) {
            return Result.error(new Exception("Error registering vehicle!"));
        }
    }

    //Metodo disable vehicle (DELETE).
    //Aqui solo eliminamos la entidad por id.
    public Result<DisableVehicleResponseDto, Exception> disableVehicle(Long id) {
        try {
            return vehicleRepository.findById(id).map(vehicle -> {
                vehicleRepository.deleteById(id);
                DisableVehicleResponseDto response = new DisableVehicleResponseDto("Vehicle remove successfull!");
                return Result.ok(response);
            }).orElseGet(() -> Result.error(new Exception("Vehicle not found!")));

        } catch (Exception e) {
            return Result.error(new Exception("Error deleting vehicle!"));
        }
    }

    //Metodo list all vehicles (READ).
    //Usamos el metodo toDto del mapper para mapear toda la coleccion.
    public Result<ListVehiclesResponseDto, Exception> listAllVehicles() {
        List<VehicleEntity> vehicleEntities = vehicleRepository.findAll();

        try {
            //Usar el mapper para convertir la lista de entidades a lista de dtos.
            List<VehicleDto> vehicleDtos = vehicleMapper.toDto(vehicleEntities);

            ListVehiclesResponseDto response = new ListVehiclesResponseDto(vehicleDtos, "vehicles found succesfull!");
            return Result.ok(response);

        } catch (Exception e) {
            return Result.error(new Exception("Error to get all vehicles!"));
        }
    }


    //Metodo show vehicle by id (READ).
    //Usamos el mapper para convertir la entidad a dto.
    public Result<VehicleResponseDto, Exception> showVehicle(Long id) {
        try {
            return vehicleRepository.findById(id)
                    .map(vehicleEntity -> {
                        //Usar el mapper para convertir la entidad a dto.
                        VehicleDto vehicleDto = vehicleMapper.toDto(vehicleEntity);

                        VehicleResponseDto response = new VehicleResponseDto(vehicleDto, "Vehicle show succesfull!");
                        return Result.ok(response);
                    })
                    .orElseGet(() -> Result.error(new Exception("Error found vehicle!")));

        } catch (Exception e) {
            return Result.error(new Exception("Error to show vehicle!"));
        }
    }
}
