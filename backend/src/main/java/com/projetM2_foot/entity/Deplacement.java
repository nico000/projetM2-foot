package com.projetM2_foot.entity;

import lombok.*;
import org.neo4j.ogm.annotation.NodeEntity;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NodeEntity
public class Deplacement {

    /** numero de l'action */
    private int action;
    private Entite entite;
    private int position_debut;
    private int position_fin;

}