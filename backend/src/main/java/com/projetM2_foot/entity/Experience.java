package com.projetM2_foot.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Experience {

    @Id
    @GeneratedValue
    private Long id;

    /** dirige sur un scénario */
    private Long idScenario;

    /* **** FEEDBACK ****/
    /** Type de feedback parmis static , vidéo , score ... */
    private String typeFeedback;

    /** Fréquence d'apparition du feedback de 0 à 100 (par défaut 100) */
    private int freqFeedback = 100;

    /** Nombre de visualisation du feedback (par défaut 1) */
    private int visuFeedback = 1;



}