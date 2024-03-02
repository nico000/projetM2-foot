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

    @Column(name = "dep_reussi")
    private Boolean reussi;

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

    // Deplacement zone essai

    /** Position X de début */
    @Column(name = "dep_start_zonex")
    private int startZoneX;
    /** Position Y de début */
    @Column(name = "dep_start_zoney")
    private int startZoneY;

    /** Position X de fin */
    @Column(name = "dep_end_zonex")
    private int endZoneX;
    /** Position Y de fin */
    @Column(name = "dep_end_zoney")
    private int endZoneY;

    // Deplacement zone scenario

    /** Position X de début */
    @Column(name = "sce_start_zonex")
    private int scenarioStartZoneX;
    /** Position Y de début */
    @Column(name = "sce_start_zoney")
    private int scenarioStartZoneY;

    /** Position X de fin */
    @Column(name = "sce_end_zonex")
    private int scenarioEndZoneX;
    /** Position Y de fin */
    @Column(name = "sce_end_zoney")
    private int scenarioEndZoneY;


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ResultatDeplacement{");
        sb.append("id=").append(id);
        sb.append(", numAction=").append(numAction);
        sb.append(", reussi=").append(reussi);
        sb.append(", entite=").append(entite);
        sb.append(", startZoneX=").append(startZoneX);
        sb.append(", startZoneY=").append(startZoneY);
        sb.append(", endZoneX=").append(endZoneX);
        sb.append(", endZoneY=").append(endZoneY);
        sb.append('}');
        return sb.toString();
    }
}
