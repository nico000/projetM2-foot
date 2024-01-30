package com.projetM2_foot.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class ScenarioDto {

    @Schema(
            description = "Identifiant unique d'une entité",
            example = "653280"
    )
    private Long id;

    @Schema(
            description = "Nom du scénario",
            example = "scénario A"
    )
    private String name;

    @Schema(
            description = "Indique si l'affichage se fait en 3 dimension",
            example = "false"
    )
    private Boolean threeDim;

    @Schema(
            description = "Indique comment ce déroule la scène",
            example = "action"
    )
    private String modeScene;

    @Schema(
            description = "Couleur du terrain",
            example = "#00FF00"
    )
    private String groundColor;
    @Schema(
            description = "Taille du terrain",
            example = "large"
    )
    private String groundWidth;

    @Schema(
            description = "Nombre de joueur sur le terrain",
            example = "11"
    )
    private int playerNb;

    @Schema(
            description = "Indique comment est affiché le joueur",
            example = "1"
    )
    private int playerIcon;

    @Schema(
            description = "Affichage de la délimitation de zone ou non",
            example = "true"
    )
    private Boolean displayArea;

    @Schema(
            description = "Nombre de zone en colonne",
            example = "5"
    )
    private int colonArea;

    @Schema(
            description = "Nombre de couloirs",
            example = "2"
    )
    private int corridorArea;
}