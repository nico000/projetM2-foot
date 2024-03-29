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
    private String sex;
    private String pratique;
    private Boolean pro;
    private int anneeExperience;
    private int matche;
    private int entrainement;
    private int heure;

    private int vueMatch;
    private int vueTv;
    private Boolean jv;
    private Boolean vision;

    @Override
    public String toString() {
        return "ResultatExamenRequestCreate{" + "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", pro=" + pro +
                ", vueMatch=" + vueMatch +
                ", vueTv=" + vueTv +
                ", jv=" + jv +
                ", vision=" + vision +
                '}';
    }
}
