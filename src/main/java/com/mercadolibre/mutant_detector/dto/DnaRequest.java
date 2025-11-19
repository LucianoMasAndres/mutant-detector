package com.mercadolibre.mutant_detector.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DnaRequest implements Serializable {

    @NotNull(message = "El ADN no puede ser nulo")
    @NotEmpty(message = "El ADN no puede estar vacío")
    @Schema(
            description = "Array de Strings que representan la secuencia de ADN (NxN)",
            example = "[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]"
    )
    private String[] dna;
}