package com.mercadolibre.mutant_detector.service;

import com.mercadolibre.mutant_detector.dto.StatsResponse;
import com.mercadolibre.mutant_detector.repository.DnaRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final DnaRecordRepository dnaRecordRepository;

    public StatsResponse getStats() {
        long countMutant = dnaRecordRepository.countByIsMutant(true);
        long countHuman = dnaRecordRepository.countByIsMutant(false);

        double ratio = 0.0;
        if (countHuman > 0) {
            ratio = (double) countMutant / countHuman;
        } else if (countMutant > 0) {
            ratio = 1.0; // O el valor que prefieras si no hay humanos (algunos usan countMutant)
        }

        return new StatsResponse(countMutant, countHuman, ratio);
    }
}