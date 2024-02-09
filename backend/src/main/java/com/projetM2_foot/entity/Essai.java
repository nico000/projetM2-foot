package com.projetM2_foot.entity;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "essai")
public class Essai {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "exp_id")
    private Long id;

    /** dirige sur un scénario */
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "exp_fk_sce")
    private Scenario scenario;



}