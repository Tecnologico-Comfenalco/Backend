package com.tecno_comfenalco.pa.features.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tecno_comfenalco.pa.features.store.dto.request.EditStoreRequestDto;
import com.tecno_comfenalco.pa.features.store.dto.request.RegisterStoreRequestDto;
import com.tecno_comfenalco.pa.features.store.dto.response.DisableStoreResponseDto;
import com.tecno_comfenalco.pa.features.store.dto.response.EditStoreResponseDto;
import com.tecno_comfenalco.pa.features.store.dto.response.ListStoresResponseDto;
import com.tecno_comfenalco.pa.features.store.dto.response.RegisterStoreResponseDto;
import com.tecno_comfenalco.pa.features.store.dto.response.StoresResponseDto;
import com.tecno_comfenalco.pa.features.store.service.StoreService;
import com.tecno_comfenalco.pa.shared.utils.helper.ResponseEntityHelper;
import com.tecno_comfenalco.pa.shared.utils.result.Result;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/store")
@PreAuthorize("hasAnyRole('ADMIN', 'DISTRIBUTOR')")
public class StoreController {

    @Autowired
    public StoreService storeService;

    @PostMapping("/register")
    public ResponseEntity<RegisterStoreResponseDto> newStore(
            @RequestBody @Valid RegisterStoreRequestDto dtoVehicle) {
        Result<RegisterStoreResponseDto, Exception> result = storeService.newStore(dtoVehicle);
        return ResponseEntityHelper.toResponseEntity(result);
    }

    @GetMapping
    public ResponseEntity<ListStoresResponseDto> listStores() {
        Result<ListStoresResponseDto, Exception> result = storeService.listAllStores();
        return ResponseEntityHelper.toResponseEntity(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EditStoreResponseDto> editStore(@PathVariable Long id,
            @RequestBody @Valid EditStoreRequestDto dtoStore) {
        Result<EditStoreResponseDto, Exception> result = storeService.editStore(id, dtoStore);
        return ResponseEntityHelper.toResponseEntity(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoresResponseDto> showVehicle(@PathVariable Long id) {
        Result<StoresResponseDto, Exception> result = storeService.showStore(id);
        return ResponseEntityHelper.toResponseEntity(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DisableStoreResponseDto> disableVehicle(@PathVariable Long id) {
        Result<DisableStoreResponseDto, Exception> result = storeService.disableStore(id);
        return ResponseEntityHelper.toResponseEntity(result);
    }
}
