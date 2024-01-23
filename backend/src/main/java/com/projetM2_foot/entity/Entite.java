package com.projetM2_foot.entity;

import lombok.*;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.session.delegates.GraphIdDelegate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NodeEntity
public class Entite {

    @Id
    private int id; // Type temporaire voir pour changer le type
    private int numero; // le ballon porte le numéro 0 le reste des numéro lambda
    private int position; // position = numero de zone ?
}