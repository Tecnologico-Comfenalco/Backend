package com.tecno_comfenalco.pa.features.delivery.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tecno_comfenalco.pa.features.delivery.DeliveryEntity;
import com.tecno_comfenalco.pa.features.delivery.dto.DeliveryDto;
import com.tecno_comfenalco.pa.features.delivery.dto.request.EditDeliveryRequestDto;
import com.tecno_comfenalco.pa.features.delivery.dto.request.RegisterDeliveryRequestDto;
import com.tecno_comfenalco.pa.features.delivery.dto.response.DeliveryResponseDto;
import com.tecno_comfenalco.pa.features.delivery.dto.response.EditDeliveryResponseDto;
import com.tecno_comfenalco.pa.features.delivery.dto.response.ListDeliveriesResponseDto;
import com.tecno_comfenalco.pa.features.delivery.dto.response.RegisterDeliveryResponseDto;
import com.tecno_comfenalco.pa.features.delivery.repository.IDeliveryRepository;
import com.tecno_comfenalco.pa.security.AuthenticationService;
import com.tecno_comfenalco.pa.security.domain.UserEntity;
import com.tecno_comfenalco.pa.security.dto.requests.RegisterUserRequestDto;
import com.tecno_comfenalco.pa.security.repository.IUserRepository;
import com.tecno_comfenalco.pa.shared.utils.result.Result;

@Service
public class DeliveryService {
    @Autowired
    private IDeliveryRepository deliveryRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private IUserRepository userRepository;

    public Result<RegisterDeliveryResponseDto, Exception> newDelivery(RegisterDeliveryRequestDto dtoDelivery) {

        boolean existsDelivery = deliveryRepository.existsByDocumentNumber(dtoDelivery.documentnumber());

        if (existsDelivery) {
            return Result.error(new Exception("The current delivery is already registered!"));
        }

        try {

            DeliveryEntity deliveryEntity = new DeliveryEntity();
            deliveryEntity.setName(dtoDelivery.name());
            deliveryEntity.setDocumentType(dtoDelivery.documentTypeEnum());
            deliveryEntity.setDocumentNumber(dtoDelivery.documentnumber());
            deliveryEntity.setPhoneNumber(dtoDelivery.phoneNumber());
            deliveryEntity.setLicenseNumber(dtoDelivery.licenseNumber());
            deliveryEntity.setLicenseType(dtoDelivery.licenseType());

            Long userId = authenticationService.registerUser(
                    new RegisterUserRequestDto(dtoDelivery.name().toLowerCase().replace(" ", "_"),
                            "password", Set.of("DELIVERY"), true))
                    .getValue().userId();

            UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found!"));

            deliveryEntity.setUser(userEntity);
            deliveryRepository.save(deliveryEntity);

            return Result.ok(new RegisterDeliveryResponseDto("Delivery register successful!"));
        } catch (Exception e) {
            return Result.error(new Exception("Error to register delivery!"));
        }
    }

    public Result<EditDeliveryResponseDto, Exception> editDelivery(Long id,
            EditDeliveryRequestDto dtoDelivery) {
        try {
            return deliveryRepository.findById(id)
                    .map(delivery -> {

                        delivery.setName(dtoDelivery.name());
                        delivery.setDocumentType(dtoDelivery.documentTypeEnum());
                        delivery.setDocumentNumber(dtoDelivery.documentnumber());
                        delivery.setPhoneNumber(dtoDelivery.phoneNumber());
                        delivery.setLicenseNumber(dtoDelivery.licenseNumber());
                        delivery.setLicenseType(dtoDelivery.licenseType());

                        deliveryRepository.save(delivery);

                        EditDeliveryResponseDto response = new EditDeliveryResponseDto(
                                "delivery edited successfully!");

                        return Result.ok(response);

                    }).orElseGet(() -> Result.error(new Exception("delivery not found!")));
        } catch (Exception e) {
            return Result.error(new Exception("delivery not exists by id cause!"));
        }
    }

    public Result<ListDeliveriesResponseDto, Exception> listDelivery() {
        List<DeliveryEntity> deliveryEntities = deliveryRepository.findAll();

        try {

            List<DeliveryDto> deliverysDto = deliveryEntities.stream()
                    .map(delivery -> new DeliveryDto(delivery.getId(), delivery.getName(), delivery.getDocumentType(),
                            delivery.getDocumentNumber(), delivery.getPhoneNumber(), delivery.getLicenseNumber(),
                            delivery.getLicenseType()))
                    .toList();

            return Result.ok(new ListDeliveriesResponseDto(deliverysDto, "deliveries found successfully!"));
        } catch (Exception e) {
            return Result.error(new Exception("Error retrieving deliveries!"));
        }
    }

    public Result<DeliveryResponseDto, Exception> showDelivery(Long id) {
        try {
            return deliveryRepository.findById(id).map(delivery -> {

                DeliveryDto deliveryDto = new DeliveryDto(delivery.getId(), delivery.getName(),
                        delivery.getDocumentType(), delivery.getDocumentNumber(), delivery.getPhoneNumber(),
                        delivery.getLicenseNumber(), delivery.getLicenseType());

                return Result.ok(new DeliveryResponseDto(deliveryDto, "delivery found successfully"));

            }).orElseThrow();

        } catch (Exception e) {
            return Result.error(new Exception("Error retrieving delivery!"));
        }
    }

}
