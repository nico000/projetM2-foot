package com.projetM2_foot.report.response;

import com.projetM2_foot.entity.Examen;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ReportPerson {

    private String nom;
    private String prenom;
    private String groupe;
    private int age;
    private String sex;
    private String pratique;

    private Boolean pro;
    private int anneeExperience;
    public int nombreMatches;
    private int entrainement;
    private int heure;


}
