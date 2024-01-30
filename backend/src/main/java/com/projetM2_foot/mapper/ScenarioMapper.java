package com.projetM2_foot.mapper;

import com.projetM2_foot.api.request.ScenarioRequestCreate;
import com.projetM2_foot.api.request.ScenarioRequestUpdate;
import com.projetM2_foot.api.response.ScenarioResponse;
import com.projetM2_foot.entity.Scenario;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ScenarioMapper {

    public Scenario toEntity (ScenarioRequestCreate scenarioRequestCreate){

        return Scenario.builder()
                .name(scenarioRequestCreate.getNom())
                .modeScene(scenarioRequestCreate.getMode_scene())
                .groundColor(scenarioRequestCreate.getTerrain_couleur())
                .groundWidth(scenarioRequestCreate.getTerrain_taille())
                .playerNb(scenarioRequestCreate.getJoueur_nb())
                .displayArea(scenarioRequestCreate.getZone_display())
                .colonArea(scenarioRequestCreate.getZone_nb_zone())
                .corridorArea(scenarioRequestCreate.getZone_nb_couloir())
                .build();
    }

    public Scenario toEntity (ScenarioRequestUpdate request){

        return Scenario.builder()
                .id(request.getId())
                .name(request.getNom())
                .modeScene(request.getMode_scene())
                .groundColor(request.getTerrain_couleur())
                .groundWidth(request.getTerrain_taille())
                .playerNb(request.getJoueur_nb())
                .displayArea(request.getZone_display())
                .colonArea(request.getZone_nb_zone())
                .corridorArea(request.getZone_nb_couloir())
                .build();
    }

    public ScenarioResponse toDto (Scenario scenario){

        return ScenarioResponse.builder()
                .id(scenario.getId())
                .nom(scenario.getName())
                .mode_scene(scenario.getModeScene())
                .terrain_couleur(scenario.getGroundColor())
                .terrain_taille(scenario.getGroundWidth())
                .joueur_nb(scenario.getPlayerNb())
                .zone_use(true)
                .zone_display(scenario.getDisplayArea())
                .zone_nb_zone(scenario.getColonArea())
                .zone_nb_couloir(scenario.getCorridorArea())
                .zone_epaisseur(2)
                .build();

    }



    public List<ScenarioResponse> toGetAll (List<Scenario> scenarioList){

        return scenarioList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }





}
