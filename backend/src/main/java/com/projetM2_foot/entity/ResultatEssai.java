package com.projetM2_foot.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "res_essai")
public class ResultatEssai {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rtry_id")
    private Long id;

    /** indique si l'essai est réussi */
    @Column(name = "rtry_reussi")
    private boolean reussi;

    /** Indique le nombre de bonne réponse sur cet essai */
    @Column(name = "rtry_score")
    private int score;

    /** Temps qui c'est passé */
    @Column(name = "rtry_temps")
    private Long temps;

    /** Numéro de l'essai pour ce résultat experience */
    @Column(name = "rtry_num")
    private int num;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "rtry_fk_rexp")
    private ResultatExperience resultatExperience;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "rtry_dep",
            joinColumns = @JoinColumn(name = "rtry_id"),
            inverseJoinColumns = @JoinColumn(name = "rdep_id")
    )
    Set<ResultatDeplacement> deplacementSet;



}