package com.projetM2_foot.api.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class ScenarioRequestCreate {

    @NotNull
    private String nom;
    private String mode_scene;
    private String terrain_couleur;
    private String terrain_taille;
    private int joueur_nb;

    private Boolean zone_use;
    private Boolean zone_display;
    private int zone_nb_zone;
    private int zone_nb_couloir;
    private int zone_epaisseur;
}
