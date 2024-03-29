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
    private Boolean threeDim;
    /** Couleur du terrain (par défaut vert) */
    @Column(name = "sce_ground_color")
    private String groundColor = "#00FF00";

    // **** JOUEUR **** //
    /** Indique comment un joueur est monté sur le terrain (par défaut 0 = point) */
    @Column(name = "sce_player_icon")
    private int playerIcon = 0;


    // **** VISUEL **** //
    /** Nombre de zone en longueur sur le terrain (par défaut 5) */
    @Column(name = "sce_colon_area")
    private int colonArea;
    /** Nombre de couloirs sur le terrain (par défaut 1) */
    @Column(name = "sce_corridor_area")
    private int corridorArea;

}