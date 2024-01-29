package com.projetM2_foot.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Entite {

    @Id
    @GeneratedValue
    private Long id;

    /** Scenario auxquel il est rattaché */
    @ManyToOne
    private Scenario scenario;

    /** Type d'entité :
     * 0 -> ballon /
     * 1 -> joueur /
     * 2 -> objet
     */
    @NotNull
    private int type;

    /** Numero que porte un joueur (par défaut 0 si entité n'est pas un joueur) */
    @NotNull
    private int numero = 0;

    /** Position initial coordonnées X */
    @NotNull
    private int initialPosX;

    /** Position initial coordonnées Y */
    @NotNull
    private int initialPosY;

}