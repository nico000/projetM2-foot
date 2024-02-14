package com.projetM2_foot.api.request;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ResultatExamenRequestCreate {

    private Long examen;
    private String nom_person;
    private String prenom_person;
}
