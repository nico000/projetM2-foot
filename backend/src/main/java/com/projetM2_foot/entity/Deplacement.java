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
@Table(name = "deplacement")
public class Deplacement {

    /*
     Convention de nommage :
     Deplacement - dep
     Foreign key - fk
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "dep_id")
    private Long id;

    /** Numero de l'action */
    @NotNull
    @Column(name = "dep_action")
    private int numAction;
    /** Numero de la scene */
    @NotNull
    @Column(name = "dep_scene")
    private int numScene;
    /** Numero du bloc */
    @NotNull
    @Column(name = "dep_bloc")
    private int numBloc;

    /** [FK] Scenario */
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "dep_fk_sce")
    private Scenario scenario;

    /** [FK] Entité */
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "dep_fk_ent")
    private Entite entite;
    /** Position X de début */
    @Column(name = "dep_start_x")
    private float startPosX;
    /** Position Y de début */
    @Column(name = "dep_start_y")
    private float startPosY;
    /** Position X de fin */
    @Column(name = "dep_end_x")
    private float endPosX;
    /** Position Y de fin */
    @Column(name = "dep_end_y")
    private float endPosY;

}