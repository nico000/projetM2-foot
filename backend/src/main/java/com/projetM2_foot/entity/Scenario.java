package com.projetM2_foot.entity;

import lombok.*;
import org.neo4j.ogm.annotation.NodeEntity;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NodeEntity
public class Scenario {

    private String nom;

    private String mode_scene;
    //private int dimension;
    private String terrain_couleur;
    private String terrain_taille;

    private int joueur_nb;
    //private String joueur_icon;

    //feedBack
    private int feedback_frequence;
    /** Nombre de visionnage (kr ,krr) */
    private int feedback_condition;
    private String feedback_type;

    //zone
    private Boolean zone_use;
    private Boolean zone_display;
    private int zone_nb_zone;
    private int zone_nb_couloir;
    private int zone_epaisseur;

    //private List<Entite> listEntite;
    //private List<Deplacement> listDeplacement;


}