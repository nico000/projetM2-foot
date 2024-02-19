package com.projetM2_foot.api.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResultatEssaiRequestCreate {


    private Long temps;
    private int num;
    private Long resultatExperience;

    List<ResultatDeplacementRequestCreate> deplacements;

    @Override
    public String toString() {
        return "\nEssai{" +
                "  temps=" + temps +
                ", num=" + num +
                ", resultatExperience=" + resultatExperience +
                ", \ndeplacements = " + deplacements +
                '}';
    }
}
