package com.projetM2_foot.entity;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "res_experience")
public class ResultatExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rexp_id")
    private Long id;

    /** dirige sur une experience */
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "rexp_fk_exp")
    private Experience experience;

    @Column(name = "rexp_score")
    private int score;

    /** Relié à un resultat examen */
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "rexp_fk_rexa")
    private ResultatExamen resultatExamen;




}