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

    private Feedback feedback;
    private Zone zone;

    private List<Entite> listEntite;
    private List<Deplacement> listDeplacement;


}