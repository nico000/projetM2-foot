package com.projetM2_foot.entity;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "experience")
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "exp_id")
    private Long id;

    /** dirige sur un scénario */
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "exp_fk_sce")
    private Scenario scenario;

    /* **** FEEDBACK ****/
    /** Type de feedback parmis static , vidéo , score ... */
    @Column(name = "exp_type_feedback")
    private String typeFeedback;
    /** Fréquence d'apparition du feedback de 0 à 100 (par défaut 100) */
    @Column(name = "exp_freq_feedback")
    private int freqFeedback = 100;
    /** Nombre de visualisation du feedback (par défaut 1) */
    @Column(name = "exp_visu_feedback")
    private int visuFeedback = 1;


    // **** VISUEL **** //
    /** Affichage des lignes de délimitations de zone (par défaut vrai) */
    @Column(name = "exp_display_area")
    private Boolean displayArea;
    /** Nombre de zone en longueur sur le terrain (par défaut 5) */
    @Column(name = "exp_colon_area")
    private int colonArea;
    /** Nombre de couloirs sur le terrain (par défaut 1) */
    @Column(name = "exp_corridor_area")
    private int corridorArea;

    // **** DESIGN **** //
    /** action par action / scene par scene */
    @Column(name = "exp_mode_scene")
    private String modeScene;
    /** Couleur du terrain (par défaut vert) */
    @Column(name = "exp_ground_color")
    private String groundColor;


    @Column(name = "exp_ordre")
    private int ordre;


}