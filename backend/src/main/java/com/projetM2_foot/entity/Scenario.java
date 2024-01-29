package com.projetM2_foot.entity;

import lombok.*;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NodeEntity
public class Scenario {

    @Id
    @GeneratedValue
    private Long id;
    private String nom;

    /** action par action scene par scene */
    private String mode_scene;
    //private int dimension;
    private String terrain_couleur;
    private String terrain_taille;

    private int joueur_nb;
    //private String joueur_icon;


    //zone
    private Boolean zone_use;
    private Boolean zone_display;
    private int zone_nb_zone;
    private int zone_nb_couloir;
    private int zone_epaisseur;

    //private List<Entite> listEntite;
    //private List<Deplacement> listDeplacement;


}