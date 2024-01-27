package com.projetM2_foot.entity;

import lombok.*;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NodeEntity
public class Deplacement {

    @Id
    @GeneratedValue
    private Long id;

    /** numero de l'action */
    @NotNull
    private int numAction;
    /** numero de la scene */
    @NotNull
    private int numScene;
    /** numero du bloc */
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