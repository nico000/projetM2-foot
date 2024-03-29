package com.projetM2_foot.api.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class ScenarioRequestCreate {

    @NotNull
    private String nom;
    private String terrain_couleur;

    private int zone_nb_zone;
    private int zone_nb_couloir;
}
