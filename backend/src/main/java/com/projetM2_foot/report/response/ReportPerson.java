package com.projetM2_foot.report.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReportPerson {

    private String nom;
    private String prenom;
    private String groupe;
    private int age;
    private String genre;
    private String pratique;

    private Boolean pro;
    private int anneeExperience;
    public int nombreMatches;
    private int entrainement;
    private int heure;


}
