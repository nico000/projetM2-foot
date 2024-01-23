package com.projetM2_foot.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Zone {
    @Id
    private String id;
    private Boolean use;
    private Boolean display;
    private int nb_zone;
    private int nb_couloir;
    private int epaisseur;
}