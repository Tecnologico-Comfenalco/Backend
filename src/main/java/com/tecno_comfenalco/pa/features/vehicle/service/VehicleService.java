package com.tecno_comfenalco.pa.features.vehicle.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tecno_comfenalco.pa.features.vehicle.VehicleEntity;
import com.tecno_comfenalco.pa.features.vehicle.dto.VehicleDto;
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

    public Result<RegisterVehicleResponseDto, Exception> newVehicle(RegisterVehicleRequestDto dtoVehicle) {
        boolean existsVehicle = vehicleRepository.existsByName(dtoVehicle.vehiclePlate());

        if (existsVehicle) {
            return Result.error(new Exception("the vehicle already registed!"));
        }

        try {
            VehicleEntity vehicleEntity = new VehicleEntity();
            vehicleEntity.setVehiclePlate(dtoVehicle.vehiclePlate());
            vehicleEntity.setModel(dtoVehicle.model());
            vehicleEntity.setBrand(dtoVehicle.brand());

            vehicleRepository.save(vehicleEntity);

            RegisterVehicleResponseDto response = new RegisterVehicleResponseDto("Vehicle register succesfull");
            return Result.ok(response);

        } catch (Exception e) {
            return Result.error(new Exception("Failed to register vehicle!"));
        }
    }

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

    public Result<ListVehiclesResponseDto, Exception> listAllVehicles() {
        List<VehicleEntity> vehicleEntities = vehicleRepository.findAll();

        try {
            List<VehicleDto> vehicleDtos = vehicleEntities.stream()
                    .map(vehicle -> new VehicleDto(vehicle.getVehiclePlate(), vehicle.getModel(), vehicle.getBrand()))
                    .toList();
            ListVehiclesResponseDto response = new ListVehiclesResponseDto(vehicleDtos, "vehicles found succesfull!");
            return Result.ok(response);

        } catch (Exception e) {
            return Result.error(new Exception("Error to get all vehicles!"));
        }
    }

    public Result<VehicleResponseDto, Exception> showVehicle(Long id) {
        try {
            return vehicleRepository.findById(id)
                    .map(vehicle -> {
                        VehicleDto vehicleDto = new VehicleDto(vehicle.getVehiclePlate(), vehicle.getModel(),
                                vehicle.getBrand());
                        VehicleResponseDto response = new VehicleResponseDto(vehicleDto, "Vehicle show succesfull!");
                        return Result.ok(response);
                    })
                    .orElseGet(() -> Result.error(new Exception("Error found vehicle!")));

        } catch (Exception e) {
            return Result.error(new Exception("Error to show vehicle!"));
        }
    }
}
