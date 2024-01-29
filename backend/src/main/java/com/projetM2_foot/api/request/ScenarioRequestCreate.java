package com.projetM2_foot.api.request;

import lombok.*;

@Data
@Builder
public class ScenarioRequestCreate {

    private String nom;
    private String mode_scene;
    private String terrain_couleur;
    private String terrain_taille;
    private int joueur_nb;

    //feedBack
    private int feedback_frequence;
    private int feedback_condition;
    private String feedback_type;

    //zone
    private Boolean zone_use;
    private Boolean zone_display;
    private int zone_nb_zone;
    private int zone_nb_couloir;
    private int zone_epaisseur;


}
