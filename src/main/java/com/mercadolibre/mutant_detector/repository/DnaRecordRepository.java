package com.mercadolibre.mutant_detector.repository;

import com.mercadolibre.mutant_detector.entity.DnaRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DnaRecordRepository extends JpaRepository<DnaRecord, Long> {

    // Busca por Hash (SELECT * FROM dna_records WHERE dna_hash = ?)
    Optional<DnaRecord> findByDnaHash(String dnaHash);

    // Cuenta mutantes o humanos (SELECT COUNT(*) FROM ...)
    long countByIsMutant(boolean isMutant);
}