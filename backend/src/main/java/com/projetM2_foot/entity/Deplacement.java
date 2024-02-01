package com.projetM2_foot.entity;

import lombok.*;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Deplacement {

    @Id
    @GeneratedValue
    private Long id;

    /** Numero de l'action */
    @NotNull
    private int numAction;
    /** Numero de la scene */
    @NotNull
    private int numScene;
    /** Numero du bloc */
    @NotNull
    private int numBloc;

    /** [FK] Scenario */
    @ManyToOne
    private Scenario scenario;

    /** [FK] Entité */
    @ManyToOne
    private Entite entite;
    /** Position X de début */
    private float startPosX;
    /** Position Y de début */
    private float startPosY;
    /** Position X de fin */
    private float endPosX;
    /** Position Y de fin */
    private float endPosY;

}