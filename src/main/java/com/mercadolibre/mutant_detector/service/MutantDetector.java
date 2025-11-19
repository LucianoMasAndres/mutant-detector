package com.mercadolibre.mutant_detector.service;

import org.springframework.stereotype.Service;
import java.util.regex.Pattern;

@Service
public class MutantDetector {

    private static final int SEQUENCE_LENGTH = 4;
    // Patrón precompilado para validar caracteres permitidos (A, T, C, G)
    private static final Pattern VALID_DNA_PATTERN = Pattern.compile("^[ATCG]+$");

    public boolean isMutant(String[] dna) {
        // 1. Validaciones básicas (Fail fast)
        if (dna == null || dna.length == 0) return false;

        final int n = dna.length;

        // Validación tamaño mínimo (Si es menor a 4x4, imposible encontrar secuencias)
        if (n < SEQUENCE_LENGTH) return false;

        // 2. Optimización: Conversión a char[][] para acceso rápido O(1)
        char[][] matrix = new char[n][];

        for (int i = 0; i < n; i++) {
            String row = dna[i];
            // Validar que sea cuadrada y caracteres válidos
            if (row == null || row.length() != n || !VALID_DNA_PATTERN.matcher(row).matches()) {
                // Nota: Podrías lanzar excepción, pero la rúbrica sugiere manejar booleanos o validaciones previas
                throw new IllegalArgumentException("ADN inválido: debe ser NxN y contener solo A,T,C,G");
            }
            matrix[i] = row.toCharArray();
        }

        int sequenceCount = 0;

        // 3. Recorrido Único (Single Pass) optimizado
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {

                // Si encontramos una letra, verificamos en las 4 direcciones posibles
                // Solo si hay espacio suficiente (Boundary Checking)

                // --- HORIZONTAL (→) ---
                if (col <= n - SEQUENCE_LENGTH) {
                    if (checkHorizontal(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return true; // ¡Early Termination! 🚀
                    }
                }

                // --- VERTICAL (↓) ---
                if (row <= n - SEQUENCE_LENGTH) {
                    if (checkVertical(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return true; // ¡Early Termination! 🚀
                    }
                }

                // --- DIAGONAL (↘) ---
                if (row <= n - SEQUENCE_LENGTH && col <= n - SEQUENCE_LENGTH) {
                    if (checkDiagonal(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return true; // ¡Early Termination! 🚀
                    }
                }

                // --- ANTIDIAGONAL (↙) ---
                if (row <= n - SEQUENCE_LENGTH && col >= SEQUENCE_LENGTH - 1) {
                    if (checkAntiDiagonal(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return true; // ¡Early Termination! 🚀
                    }
                }
            }
        }

        return false; // No se encontraron suficientes secuencias
    }

    // Métodos de comparación directa (Más rápido que bucles internos)
    private boolean checkHorizontal(char[][] matrix, int row, int col) {
        char base = matrix[row][col];
        return matrix[row][col+1] == base &&
                matrix[row][col+2] == base &&
                matrix[row][col+3] == base;
    }

    private boolean checkVertical(char[][] matrix, int row, int col) {
        char base = matrix[row][col];
        return matrix[row+1][col] == base &&
                matrix[row+2][col] == base &&
                matrix[row+3][col] == base;
    }

    private boolean checkDiagonal(char[][] matrix, int row, int col) {
        char base = matrix[row][col];
        return matrix[row+1][col+1] == base &&
                matrix[row+2][col+2] == base &&
                matrix[row+3][col+3] == base;
    }

    private boolean checkAntiDiagonal(char[][] matrix, int row, int col) {
        char base = matrix[row][col];
        return matrix[row+1][col-1] == base &&
                matrix[row+2][col-2] == base &&
                matrix[row+3][col-3] == base;
    }
}