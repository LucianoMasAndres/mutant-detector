package com.mercadolibre.mutant_detector.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MutantDetectorTest {

    private final MutantDetector detector = new MutantDetector();

    // --- CASOS MUTANTES (Deben dar TRUE) ---

    @Test
    void testMutantHorizontal() {
        // Fila 0: AAAA
        // Fila 1: CCCC
        String[] dna = {
                "AAAA",
                "CCCC",
                "TCAG",
                "GGTC"
        };
        assertTrue(detector.isMutant(dna), "Debería detectar 2 horizontales");
    }

    @Test
    void testMutantVertical() {
        // Col 0: A-A-A-A
        // Col 1: T-T-T-T
        String[] dna = {
                "ATCG",
                "ATCG",
                "ATCG",
                "ATCG"
        };
        assertTrue(detector.isMutant(dna), "Debería detectar 4 verticales");
    }

    @Test
    void testMutantDiagonal() {
        // Diagonal Principal (↘): A-A-A-A
        // Diagonal Inversa (↙):   G-G-G-G (empieza en fila 0, col 3)
        String[] dna = {
                "ATCG",
                "CAGT",
                "CGAT",
                "GCTA"
        };
        assertTrue(detector.isMutant(dna), "Debería detectar 2 diagonales cruzadas");
    }

    @Test
    void testMutantMixed() {
        // Fila 0: AAAA (Horizontal)
        // Col 3: A-A-A-A (Vertical)
        String[] dna = {
                "AAAA",
                "AGTA",
                "AGTA",
                "AGTA"
        };
        assertTrue(detector.isMutant(dna), "Debería detectar horizontal + vertical");
    }

    // --- CASOS HUMANOS (Deben dar FALSE) ---

    @Test
    void testHumanNoSequence() {
        String[] dna = {
                "TGAC",
                "ACTG",
                "CGAT",
                "TACG"
        };
        assertFalse(detector.isMutant(dna), "No debería detectar ninguna secuencia");
    }

    @Test
    void testHumanOneSequenceOnly() {
        // Solo una secuencia horizontal AAAA en la primera fila.
        // El resto es ruido sin secuencias.
        String[] dna = {
                "AAAA",
                "CAGT",
                "TCAG",
                "AGTC"
        };
        assertFalse(detector.isMutant(dna), "Con solo 1 secuencia debe ser humano");
    }

    // --- VALIDACIONES ---

    @Test
    void testNullDna() {
        assertFalse(detector.isMutant(null));
    }

    @Test
    void testEmptyDna() {
        assertFalse(detector.isMutant(new String[]{}));
    }

    @Test
    void testInvalidCharacters() {
        String[] dna = {"AATX", "AGTC", "AGTC", "AGTC"};
        assertThrows(IllegalArgumentException.class, () -> detector.isMutant(dna));
    }

    @Test
    void testShortDna() {
        String[] dna = {"AAA", "AAA", "AAA"};
        assertFalse(detector.isMutant(dna));
    }
}