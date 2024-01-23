package com.projetM2_foot.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Entite {
    @Id
    private String id; // Type temporaire voir pour changer le type
    private int numero; // le ballon porte le numéro 0 le reste des numéro lambda
    private int position; // position = numero de zone ?
}