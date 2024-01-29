package com.projetM2_foot.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScenarioResponse {
    private Long id;
    private String nom;
    private String mode_scene;
    private String terrain_couleur;
    private String terrain_taille;
    private int joueur_nb;

    //zone
    private Boolean zone_use;
    private Boolean zone_display;
    private int zone_nb_zone;
    private int zone_nb_couloir;
    private int zone_epaisseur;
}
