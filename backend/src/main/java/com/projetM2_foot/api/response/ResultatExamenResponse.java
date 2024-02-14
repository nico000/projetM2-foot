package com.projetM2_foot.api.response;

import com.projetM2_foot.entity.Examen;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultatExamenResponse {

    private Long id;

    private Long examen;
    private String nom_person;
    private String prenom_person;
    private int score;
}
