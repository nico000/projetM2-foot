package com.projetM2_foot.api.request;

import lombok.Builder;
import lombok.Data;



@Data
@Builder
public class ResultatExamenRequestCreate {

    private Long examen;

    private String groupe;
    private String nom;
    private String prenom;
    private int age;
    private String genre;
    private String vision;

    private String pratique;
    private String pratique_type;

    private String club;
    private String club_actu;
    private String club_niveau;
    private int club_annee;
    private int club_match;
    private int club_entrainement;

    private int match_tv;
    private int match_stade;

    private String jeu;
    private String jeu_foot;
    private int jeu_frequence;

    @Override
    public String toString() {
        return "ResultatExamenRequestCreate{" + "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", age=" + age +
                '}';
    }
}
