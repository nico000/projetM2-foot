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
    @JoinTable(
            name = "exa_exp",
            joinColumns = @JoinColumn(name = "exa_id"),
            inverseJoinColumns = @JoinColumn(name = "exp_id"))
    Set<Experience> experienceSet;

    @Column(name = "exp_name")
    private String name;

    @Column(name = "exp_mode")
    private String mode;

}