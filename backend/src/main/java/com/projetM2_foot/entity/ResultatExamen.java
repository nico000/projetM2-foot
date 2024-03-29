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


    // Identité user
    @Column(name = "user_groupe")
    private String userGroupe;
    @Column(name = "user_nom")
    private String userNom;
    @Column(name = "user_prenom")
    private String userPrenom;
    @Column(name = "user_age")
    private int userAge;
    @Column(name = "user_genre")
    private String userGenre;
    @Column(name = "user_vision")
    private String userVision;

    // pratique user
    @Column(name = "user_pratique")
    private String userPratique;
    @Column(name = "user_pratique_type")
    private String userPratiqueType;

    // club user
    @Column(name = "user_club")
    private String userClub;
    @Column(name = "user_club_actu")
    private String userClubActu;
    @Column(name = "user_club_niveau")
    private String userClubNiveau;
    @Column(name = "user_club_annee")
    private int userClubAnnee;
    @Column(name = "user_club_match")
    private int userClubMatch;
    @Column(name = "user_club_entrainement")
    private int userClubEntrainement;

    // extra user
    @Column(name = "user_match_tv")
    private int userMatchTv;
    @Column(name = "user_match_stade")
    private int userMatchStade;

    @Column(name = "user_jeu")
    private String userJeu;
    @Column(name = "user_jeu_foot")
    private String userJeuFoot;
    @Column(name = "user_jeu_frequence")
    private int userJeuFrequence;



    @Column(name = "rexa_score")
    private int score;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "rexa_create_date")
    private Date createDate;

}