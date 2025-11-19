package com.mercadolibre.mutant_detector.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class StatsResponse implements Serializable {

    @JsonProperty("count_mutant_dna")
    @Schema(description = "Cantidad total de ADNs mutantes verificados", example = "40")
    private long countMutantDna;

    @JsonProperty("count_human_dna")
    @Schema(description = "Cantidad total de ADNs humanos verificados", example = "100")
    private long countHumanDna;

    @Schema(description = "Ratio de mutantes respecto al total de humanos", example = "0.4")
    private double ratio;
}