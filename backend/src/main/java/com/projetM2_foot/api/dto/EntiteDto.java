package com.projetM2_foot.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EntiteDto {

    @Schema(
            description = "Identifiant unique d'une entité",
            example = "653280"
    )
    private Long id;

    @Schema(
            description = "Identifiant du scénario rattaché",
            example = "65326754"
    )
    private Long scenario;

    @Schema(
            description = "Type d'entité par exemple un joueur = 1",
            example = "1"
    )
    private int type;

    @Schema(
            description = "Numero du joueur",
            example = "1"
    )
    private int numero;

    @Schema(
            description = "Position initial X de l'entité",
            example = "5"
    )
    private int x;

    @Schema(
            description = "Position initial Y de l'entité",
            example = "3"
    )
    private int y;
}