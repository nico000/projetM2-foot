package com.projetM2_foot.api.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultatExamenResponse {

    private Long id;

    private Long examen;

    private String groupe;
    private String nom;
    private String prenom;
    private int age;
    private String sex;
    private String pratique;
    private Boolean pro;
    private int anneeExperience;
    public int matche;
    private int entrainement;
    private int heure;

    private int vueMatch;
    private int vueTv;
    private Boolean jv;
    private Boolean vision;

    private int score;
}
