package com.mercadolibre.mutant_detector.service;

import com.mercadolibre.mutant_detector.entity.DnaRecord;
import com.mercadolibre.mutant_detector.repository.DnaRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MutantService {

    private final MutantDetector mutantDetector;
    private final DnaRecordRepository dnaRecordRepository;

    public boolean analyzeDna(String[] dna) {
        // 1. Calcular Hash único del ADN (Optimización de caché)
        String dnaHash = calculateHash(dna);

        // 2. Buscar en BD si ya existe
        Optional<DnaRecord> existingRecord = dnaRecordRepository.findByDnaHash(dnaHash);
        if (existingRecord.isPresent()) {
            return existingRecord.get().isMutant(); // Retornar resultado guardado
        }

        // 3. Si no existe, calcular
        boolean isMutant = mutantDetector.isMutant(dna);

        // 4. Guardar resultado en BD
        DnaRecord newRecord = DnaRecord.builder()
                .dnaHash(dnaHash)
                .isMutant(isMutant)
                .createdAt(LocalDateTime.now())
                .build();

        dnaRecordRepository.save(newRecord);

        return isMutant;
    }

    private String calculateHash(String[] dna) {
        try {
            // Convertimos el array a un solo String para hashear
            String rawDna = Arrays.toString(dna);
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(rawDna.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error calculando Hash SHA-256", e);
        }
    }
}