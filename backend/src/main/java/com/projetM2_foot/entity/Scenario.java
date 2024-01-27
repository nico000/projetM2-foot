package com.projetM2_foot.entity;

import lombok.*;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NodeEntity
public class Scenario {

    @Id @GeneratedValue
    private Long id;
    @NotNull
    private String name;

    // **** DESIGN **** //
    /** Visuel du terrain en 3D (par défaut inactif) */
    private Boolean threeDim = false;
    /** action par action / scene par scene */
    private String modeScene = "action";
    /** Couleur du terrain (par défaut vert) */
    private String groundColor = "#00FF00";
    /** Taille du terrains (par défaut grand) */
    private int groundWidth = 1;

    // **** JOUEUR **** //
    /** Nombre du joueur dans le scénario (par défaut 0) */
    private int playerNb = 0;
    /** Indique comment un joueur est monté sur le terrain (par défaut 0 = point) */
    private int playerIcon = 0;


    // **** VISUEL **** //
    /** Affichage des lignes de délimitations de zone (par défaut vrai) */
    private Boolean displayArea = true;
    /** Nombre de zone en longueur sur le terrain (par défaut 5) */
    private int colonArea = 5;
    /** Nombre de couloirs sur le terrain (par défaut 1) */
    private int corridorArea = 2;

}