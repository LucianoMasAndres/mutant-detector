package com.mercadolibre.mutant_detector.controller;

import com.mercadolibre.mutant_detector.dto.DnaRequest;
import com.mercadolibre.mutant_detector.dto.StatsResponse;
import com.mercadolibre.mutant_detector.service.MutantService;
import com.mercadolibre.mutant_detector.service.StatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Tag(name = "Mutant Detector", description = "API para detección de mutantes basada en secuencias de ADN")
public class MutantController {

    private final MutantService mutantService;
    private final StatsService statsService;

    @PostMapping("/mutant")
    @Operation(summary = "Detectar si un humano es mutante",
            description = "Recibe una secuencia de ADN y determina si pertenece a un mutante. Un humano es mutante si tiene MÁS DE UNA secuencia de 4 letras iguales en dirección horizontal, vertical u oblicua.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Es Mutante"),
            @ApiResponse(responseCode = "403", description = "Es Humano"),
            @ApiResponse(responseCode = "400", description = "ADN Inválido (Formato incorrecto)")
    })
    public ResponseEntity<Void> checkMutant(@Valid @RequestBody DnaRequest dnaRequest) {
        boolean isMutant = mutantService.analyzeDna(dnaRequest.getDna());
        if (isMutant) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/stats")
    @Operation(summary = "Obtener estadísticas de verificaciones",
            description = "Retorna el ratio de mutantes vs humanos basados en los datos históricos almacenados.")
    @ApiResponse(responseCode = "200", description = "Estadísticas obtenidas correctamente")
    public ResponseEntity<StatsResponse> getStats() {
        return ResponseEntity.ok(statsService.getStats());
    }
}