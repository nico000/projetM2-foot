package com.projetM2_foot.entity;

import lombok.*;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "scenario")
public class Scenario {

    /*
     Convention de nommage :
     Scenario - sce
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sce_id")
    private Long id;
    @NotNull
    @Column(name = "sce_name")
    private String name;

    // **** DESIGN **** //
    /** Visuel du terrain en 3D (par défaut inactif) */
    @Column(name = "sce_three_dim")
    private Boolean threeDim = false;
    /** action par action / scene par scene */
    @Column(name = "sce_mode_scene")
    private String modeScene = "action";
    /** Couleur du terrain (par défaut vert) */
    @Column(name = "sce_ground_color")
    private String groundColor = "#00FF00";
    /** Taille du terrains (par défaut grand) */
    @Column(name = "sce_ground_width")
    private String groundWidth;

    // **** JOUEUR **** //
    /** Nombre du joueur dans le scénario (par défaut 0) */
    @Column(name = "sce_player_nb")
    private int playerNb = 0;
    /** Indique comment un joueur est monté sur le terrain (par défaut 0 = point) */
    @Column(name = "sce_player_icon")
    private int playerIcon = 0;


    // **** VISUEL **** //
    /** Affichage des lignes de délimitations de zone (par défaut vrai) */
    @Column(name = "sce_display_area")
    private Boolean displayArea = true;
    /** Nombre de zone en longueur sur le terrain (par défaut 5) */
    @Column(name = "sce_colon_area")
    private int colonArea = 5;
    /** Nombre de couloirs sur le terrain (par défaut 1) */
    @Column(name = "sce_corridor_area")
    private int corridorArea = 2;

}