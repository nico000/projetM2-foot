package com.projetM2_foot.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "entite")
public class Entite {

    /*
     Convention de nommage :
     Entite - ent
     Foreign key - fk
     */

    @Id
    @GeneratedValue
    @Column(name = "ent_id")
    private Long id;

    /** Scenario auxquel il est rattaché */
    @ManyToOne
    @JoinColumn(name = "ent_fk_sce")
    private Scenario scenario;

    /** Type d'entité :
     * 0 -> ballon /
     * 1 -> joueur /
     * 2 -> objet
     */
    @NotNull
    @Column(name = "ent_type")
    private int type;

    /** Numero que porte un joueur (par défaut 0 si entité n'est pas un joueur) */
    @NotNull
    @Column(name = "ent_numero")
    private int numero = 0;

    /** Position initial coordonnées X */
    @NotNull
    @Column(name = "ent_x")
    private float initialPosX;

    /** Position initial coordonnées Y */
    @NotNull
    @Column(name = "ent_y")
    private float initialPosY;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ent_create_date")
    private Date createDate;

}