package com.projetM2_foot.entity;

import lombok.*;
import org.neo4j.ogm.annotation.NodeEntity;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NodeEntity
public class Zone {
    private Boolean use;
    private Boolean display;
    private int nb_zone;
    private int nb_couloir;
    private int epaisseur;
}