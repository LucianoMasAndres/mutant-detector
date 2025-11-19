package com.mercadolibre.mutant_detector.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "dna_records")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DnaRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String dnaHash; // Hash SHA-256 del ADN (Índice único)

    private boolean isMutant;

    private LocalDateTime createdAt;
}