package com.projetM2_foot.mapper;

import com.projetM2_foot.api.request.ScenarioRequestCreate;
import com.projetM2_foot.api.response.ScenarioResponseAll;
import com.projetM2_foot.api.response.ScenarioResponse;
import com.projetM2_foot.entity.Scenario;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ScenarioMapper {

    public Scenario ToEntity (ScenarioRequestCreate scenarioRequestCreate){

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

    public ScenarioResponse ToDto (Scenario scenario){

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



    public ScenarioResponseAll toGetAll (List<Scenario> scenarioList){
        final List<ScenarioResponse> dtos = scenarioList.stream()
                .map(this::ToDto)
                .collect(Collectors.toList());
        return ScenarioResponseAll.builder()
                .scenarioResponseList(dtos)
                .build();
    }



}
