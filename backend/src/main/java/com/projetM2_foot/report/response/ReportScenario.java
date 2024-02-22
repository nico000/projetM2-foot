package com.projetM2_foot.report.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReportScenario {


    private Long id;
    private String nom;
    private Boolean scene3D;
    private String couleur;
    private String taille;
    private int nombreJoueur;

    private Boolean zoneUtiliser;
    private Boolean zoneAfficher;
    private int nombreZone;
    private int nombreCouloir;

    private List<ReportEntite> entiteList;
    private List<ReportDeplacement> deplacementList;
}
