package com.projetM2_foot.entity;

import lombok.*;
import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NodeEntity
public class Zone {
    @Id
    private int id; // Type temporaire voir pour changer le type
    private Boolean use;
    private Boolean display;
    private int nb_zone;
    private int nb_couloir;
    private int epaisseur;
}