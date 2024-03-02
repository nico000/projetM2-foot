package com.projetM2_foot.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "res_examen")
public class ResultatExamen {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rexa_id")
    private Long id;

    /** resultat examen relié à un examen */
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "rexa_fk_exa")
    private Examen examen;



    @Column(name = "rexa_groupe_person")
    private String groupePerson;
    @Column(name = "rexa_nom_person")
    private String nomPerson;
    @Column(name = "rexa_prenom_person")
    private String prenomPerson;
    @Column(name = "rexa_age_person")
    private int agePerson;
    @Column(name = "rexa_sex_person")
    private String sexPerson;
    @Column(name = "rexa_pratique_person")
    private String pratiquePerson;
    @Column(name = "rexa_pro_person")
    private Boolean proPerson;
    @Column(name = "rexa_annee_experience_person")
    private int anneeExperiencePerson;
    @Column(name = "rexa_matche_person")
    public int matchePerson;
    @Column(name = "rexa_entrainement_person")
    private int entrainementPerson;
    @Column(name = "rexa_heure_person")
    private int heurePerson;

    @Column(name = "rexa_vue_match_person")
    private int vueMatchPerson;
    @Column(name = "rexa_vue_tv_person")
    private int vueTvPerson;
    @Column(name = "rexa_jv_person")
    private Boolean jvPerson;
    @Column(name = "rexa_vision_person")
    private Boolean visionPerson;




    @Column(name = "rexa_score")
    private int score;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "rexa_create_date")
    private Date createDate;

}