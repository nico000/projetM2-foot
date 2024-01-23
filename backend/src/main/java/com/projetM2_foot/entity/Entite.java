package com.projetM2_foot.entity;

import lombok.*;
import org.neo4j.ogm.annotation.NodeEntity;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NodeEntity
public class Entite {
    private int numero; // le ballon porte le numéro 0 le reste des numéro lambda
    private int position; // position = numero de zone ?
}