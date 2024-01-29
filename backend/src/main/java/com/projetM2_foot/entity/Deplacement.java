package com.projetM2_foot.entity;

import lombok.*;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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

    /** [FK] Entité */
    private Long idEntite;
    /** Position X de début */
    private int startPosX;
    /** Position Y de début */
    private int startPosY;
    /** Position X de fin */
    private int endPosX;
    /** Position Y de fin */
    private int endPosY;

}