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
@Table(name = "examen")
public class Examen {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "exa_id")
    private Long id;

    @ManyToMany
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JoinTable(
            name = "exa_exp",
            joinColumns = @JoinColumn(name = "exa_id"),
            inverseJoinColumns = @JoinColumn(name = "exp_id"))
    Set<Experience> experienceSet;

    @Column(name = "exa_name")
    private String name;

    @Column(name = "exa_sequencage")
    private String sequencage;
    @Column(name = "exa_complexite")
    private String complexite;
    @Column(name = "exa_contenu_feedback")
    private String contenuFeedback;

}