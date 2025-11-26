package com.tecno_comfenalco.pa.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/seeder")
@RequiredArgsConstructor
@Tag(name = "Data Seeder", description = "Endpoints para generar datos de prueba")
public class DataSeederController {

    private final DataSeederService dataSeederService;

    @PostMapping("/generate")
    @Operation(summary = "Generar datos de prueba", description = "Genera registros falsos en la base de datos usando Faker. Por defecto genera 1000 registros.")
    public ResponseEntity<String> generateFakeData(
            @Parameter(description = "Número de registros base a generar (por defecto 1000)", example = "1000") @RequestParam(defaultValue = "1000") int records) {
        try {
            dataSeederService.seedDatabase(records);
            return ResponseEntity.ok(String
                    .format("Se generaron exitosamente aproximadamente %d registros en la base de datos", records));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al generar datos: " + e.getMessage());
        }
    }
}
