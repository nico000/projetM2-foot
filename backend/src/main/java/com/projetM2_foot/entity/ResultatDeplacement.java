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
@Table(name = "res_deplacement")
public class ResultatDeplacement {

    /*
     Convention de nommage :
     Resultat Deplacement - rdep
     Foreign key - fk
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rdep_id")
    private Long id;

    /** Numero de l'action */
    @NotNull
    @Column(name = "rdep_action")
    private int numAction;

    /** [FK] Entité */
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "rdep_fk_ent")
    private Entite entite;
    /** Position X de début */
    @Column(name = "rdep_start_x")
    private float startPosX;
    /** Position Y de début */
    @Column(name = "rdep_start_y")
    private float startPosY;
    /** Position X de fin */
    @Column(name = "rdep_end_x")
    private float endPosX;
    /** Position Y de fin */
    @Column(name = "rdep_end_y")
    private float endPosY;

}