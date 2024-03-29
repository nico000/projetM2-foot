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
                .playerIcon(0)
                .groundColor(scenarioRequestCreate.getTerrain_couleur())
                .colonArea(scenarioRequestCreate.getZone_nb_zone())
                .corridorArea(scenarioRequestCreate.getZone_nb_couloir())
                .build();
    }

    public Scenario toEntity (ScenarioRequestUpdate request){

        return Scenario.builder()
                .id(request.getId())
                .name(request.getNom())
                .playerIcon(0)
                .groundColor(request.getTerrain_couleur())
                .colonArea(request.getZone_nb_zone())
                .corridorArea(request.getZone_nb_couloir())
                .build();
    }

    public ScenarioResponse toDto (Scenario scenario){

        return ScenarioResponse.builder()
                .id(scenario.getId())
                .nom(scenario.getName())
                .terrain_couleur(scenario.getGroundColor())
                .zone_nb_zone(scenario.getColonArea())
                .zone_nb_couloir(scenario.getCorridorArea())
                .build();

    }



    public List<ScenarioResponse> toGetAll (List<Scenario> scenarioList){

        return scenarioList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }





}
