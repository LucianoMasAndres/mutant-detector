package com.mercadolibre.mutant_detector.service;

import com.mercadolibre.mutant_detector.entity.DnaRecord;
import com.mercadolibre.mutant_detector.repository.DnaRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MutantServiceTest {

    @Mock
    private MutantDetector mutantDetector;

    @Mock
    private DnaRecordRepository dnaRecordRepository;

    @InjectMocks
    private MutantService mutantService;

    @Test
    void analyzeDna_NewMutant_ShouldSave() {
        // Simular que NO existe en BD y que el detector dice TRUE
        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(mutantDetector.isMutant(any())).thenReturn(true);

        boolean result = mutantService.analyzeDna(new String[]{"AAAA"});

        assertTrue(result);
        verify(dnaRecordRepository, times(1)).save(any(DnaRecord.class));
    }

    @Test
    void analyzeDna_NewHuman_ShouldSave() {
        // Simular que NO existe en BD y que el detector dice FALSE
        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(mutantDetector.isMutant(any())).thenReturn(false);

        boolean result = mutantService.analyzeDna(new String[]{"ATCG"});

        assertFalse(result);
        verify(dnaRecordRepository, times(1)).save(any(DnaRecord.class));
    }

    @Test
    void analyzeDna_CachedResult_ShouldReturnWithoutCalculating() {
        // Simular que YA EXISTE en BD como mutante
        DnaRecord existing = new DnaRecord();
        existing.setMutant(true);
        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.of(existing));

        boolean result = mutantService.analyzeDna(new String[]{"AAAA"});

        assertTrue(result);
        // Verificar que NO se llamó al detector (ahorro de recursos)
        verify(mutantDetector, never()).isMutant(any());
        verify(dnaRecordRepository, never()).save(any());
    }
}