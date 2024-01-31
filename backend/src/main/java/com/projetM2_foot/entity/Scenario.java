package com.projetM2_foot.entity;

import lombok.*;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Set;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Scenario {

    @Id
    @GeneratedValue
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
    private String groundWidth;

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