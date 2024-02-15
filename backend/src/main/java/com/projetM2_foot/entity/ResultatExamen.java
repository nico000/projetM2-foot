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

    @Column(name = "rexa_nom_person")
    private String nomPerson;
    @Column(name = "rexa_prenom_person")
    private String prenomPerson;
    @Column(name = "rexa_score")
    private int score;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "rexa_create_date")
    private Date createDate;

}