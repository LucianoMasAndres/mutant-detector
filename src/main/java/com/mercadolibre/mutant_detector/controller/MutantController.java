package com.mercadolibre.mutant_detector.controller;

import com.mercadolibre.mutant_detector.dto.DnaRequest;
import com.mercadolibre.mutant_detector.dto.StatsResponse;
import com.mercadolibre.mutant_detector.service.MutantService;
import com.mercadolibre.mutant_detector.service.StatsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class MutantController {

    private final MutantService mutantService;
    private final StatsService statsService;

    // Endpoint para detectar mutantes (POST)
    @PostMapping("/mutant")
    public ResponseEntity<Void> checkMutant(@Valid @RequestBody DnaRequest dnaRequest) {
        boolean isMutant = mutantService.analyzeDna(dnaRequest.getDna());
        if (isMutant) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    // Endpoint de estadísticas (GET) - ¡ESTE ES EL QUE FALTABA!
    @GetMapping("/stats")
    public ResponseEntity<StatsResponse> getStats() {
        return ResponseEntity.ok(statsService.getStats());
    }
}