package com.projetM2_foot.api.response;

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

    // extra
    private int nb_joueur;
    private int nb_deplacement;


    // List de déplacement
    /*
    deplacements:
        nb_total_dep,
        [
            1 : {
                nom
                pos
                entité {
                    numero
                    pos
                }
                ..
            } ,
            2 : {
                nom
                pos
                entité {
                    numero
                    pos
                }
                ...
            }
        ]

     */
}
