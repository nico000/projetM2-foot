package com.projetM2_foot.dto.response;

public class ScenarioResponseGet {

    private String nom;
    private String mode_scene;
    private String terrain_couleur;
    private String terrain_taille;



    //zone
    private Boolean zone_use;
    private Boolean zone_display;
    private int zone_nb_zone;
    private int zone_nb_couloir;
    private int zone_epaisseur;

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
