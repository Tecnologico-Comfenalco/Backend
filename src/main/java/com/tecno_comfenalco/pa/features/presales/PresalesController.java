package com.tecno_comfenalco.pa.features.presales;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tecno_comfenalco.pa.features.presales.dto.request.EditPresalesRequestDto;
import com.tecno_comfenalco.pa.features.presales.dto.request.RegisterPresalesRequestDto;
import com.tecno_comfenalco.pa.features.presales.dto.response.EditPresalesResponseDto;
import com.tecno_comfenalco.pa.features.presales.dto.response.ListPresalesResponseDto;
import com.tecno_comfenalco.pa.features.presales.dto.response.PresalesResponseDto;
import com.tecno_comfenalco.pa.features.presales.dto.response.RegisterPresalesResponseDto;
import com.tecno_comfenalco.pa.features.presales.service.PresalesService;
import com.tecno_comfenalco.pa.shared.utils.helper.ResponseEntityHelper;
import com.tecno_comfenalco.pa.shared.utils.result.Result;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/presales")
@PreAuthorize("hasAnyRole('ADMIN','PRESALES')")
public class PresalesController {

    @Autowired
    public PresalesService presalesService;

    @PostMapping("/register")
    public ResponseEntity<RegisterPresalesResponseDto> newPresales(
            @RequestBody @Valid RegisterPresalesRequestDto dtoPresales) {
        Result<RegisterPresalesResponseDto, Exception> result = presalesService.newPresales(dtoPresales);
        return ResponseEntityHelper.toResponseEntity(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EditPresalesResponseDto> editPresales(@PathVariable Long id,
            @RequestBody @Valid EditPresalesRequestDto dtoPresales) {
        Result<EditPresalesResponseDto, Exception> result = presalesService.editPresales(id, dtoPresales);
        return ResponseEntityHelper.toResponseEntity(result);
    }

    @GetMapping
    public ResponseEntity<ListPresalesResponseDto> listPresales() {
        Result<ListPresalesResponseDto, Exception> result = presalesService.listPresales();
        return ResponseEntityHelper.toResponseEntity(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PresalesResponseDto> showPresales(@PathVariable Long id) {
        Result<PresalesResponseDto, Exception> result = presalesService.showPresale(id);
        return ResponseEntityHelper.toResponseEntity(result);
    }

}
