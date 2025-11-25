package com.tecno_comfenalco.pa.features.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tecno_comfenalco.pa.features.store.dto.request.ClaimStoreRequestDto;
import com.tecno_comfenalco.pa.features.store.dto.request.EditStoreRequestDto;
import com.tecno_comfenalco.pa.features.store.dto.request.RegisterStoreByDistributorRequestDto;
import com.tecno_comfenalco.pa.features.store.dto.request.RegisterStoreRequestDto;
import com.tecno_comfenalco.pa.features.store.dto.response.ClaimStoreResponseDto;
import com.tecno_comfenalco.pa.features.store.dto.response.DisableStoreResponseDto;
import com.tecno_comfenalco.pa.features.store.dto.response.EditStoreResponseDto;
import com.tecno_comfenalco.pa.features.store.dto.response.ListStoresResponseDto;
import com.tecno_comfenalco.pa.features.store.dto.response.RegisterStoreResponseDto;
import com.tecno_comfenalco.pa.features.store.dto.response.StoresResponseDto;
import com.tecno_comfenalco.pa.features.store.service.StoreService;
import com.tecno_comfenalco.pa.shared.utils.helper.ResponseEntityHelper;
import com.tecno_comfenalco.pa.shared.utils.result.Result;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/stores")
@PreAuthorize("hasAnyRole('ADMIN', 'DISTRIBUTOR')")
public class StoreController {

    @Autowired
    public StoreService storeService;

    /**
     * FLUJO 1: Autogestión - Registro directo por el dueño de la tienda
     * Crea usuario + tienda con estado SELF_REGISTERED
     */
    @PostMapping("/register")
    @PreAuthorize("permitAll()") // Público para que cualquiera pueda registrarse
    public ResponseEntity<RegisterStoreResponseDto> newStore(
            @RequestBody @Valid RegisterStoreRequestDto dtoStore) {
        Result<RegisterStoreResponseDto, Exception> result = storeService.newStore(dtoStore);
        return ResponseEntityHelper.toResponseEntity(result);
    }

    /**
     * FLUJO 2: Registro por Distribuidora - Una distribuidora registra a su cliente
     * Crea tienda sin usuario (estado PENDING_CLAIM) + relación StoresDistributors
     */
    @PostMapping("/distributor/{distributorId}/register")
    @PreAuthorize("hasAnyRole('ADMIN', 'DISTRIBUTOR')")
    public ResponseEntity<RegisterStoreResponseDto> registerStoreByDistributor(
            @PathVariable Long distributorId,
            @RequestBody @Valid RegisterStoreByDistributorRequestDto dtoStore) {

        Result<RegisterStoreResponseDto, Exception> result = storeService.registerStoreByDistributor(distributorId,
                dtoStore);
        return ResponseEntityHelper.toResponseEntity(result);
    }

    /**
     * PROCESO DE CLAIM: El dueño reclama una tienda existente
     * Válido solo para tiendas en estado PENDING_CLAIM
     */
    @PostMapping("/claim")
    @PreAuthorize("permitAll()") // Público para que el dueño pueda reclamar sin autenticación previa
    public ResponseEntity<ClaimStoreResponseDto> claimStore(
            @RequestBody @Valid ClaimStoreRequestDto dtoClaimRequest) {

        Result<ClaimStoreResponseDto, Exception> result = storeService.claimStore(dtoClaimRequest);
        return ResponseEntityHelper.toResponseEntity(result);
    }

    @GetMapping
    @PreAuthorize("permitAll()")
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
