package com.projetM2_foot.mapper;

import com.projetM2_foot.dto.request.ScenarioRequestCreate;
import com.projetM2_foot.dto.response.ScenarioResponseAll;
import com.projetM2_foot.dto.response.ScenarioResponse;
import com.projetM2_foot.entity.Scenario;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ScenarioMapper {

    public Scenario ToEntity (ScenarioRequestCreate scenarioRequestCreate){
        return Scenario.builder()
                .nom(scenarioRequestCreate.getNom())
                .mode_scene(scenarioRequestCreate.getMode_scene())
                .terrain_couleur(scenarioRequestCreate.getTerrain_couleur())
                .terrain_taille(scenarioRequestCreate.getTerrain_taille())
                .joueur_nb(scenarioRequestCreate.getJoueur_nb())
                .zone_use(scenarioRequestCreate.getZone_use())
                .zone_display(scenarioRequestCreate.getZone_display())
                .zone_nb_zone(scenarioRequestCreate.getZone_nb_zone())
                .zone_nb_couloir(scenarioRequestCreate.getZone_nb_couloir())
                .zone_epaisseur(scenarioRequestCreate.getZone_epaisseur())
                .build();
    }

    public ScenarioResponse ToDto (Scenario scenario){
        return ScenarioResponse.builder()
                .id(scenario.getId())
                .nom(scenario.getNom())
                .mode_scene(scenario.getMode_scene())
                .terrain_couleur(scenario.getTerrain_couleur())
                .terrain_taille(scenario.getTerrain_taille())
                .joueur_nb(scenario.getJoueur_nb())
                .zone_use(scenario.getZone_use())
                .zone_display(scenario.getZone_display())
                .zone_nb_zone(scenario.getZone_nb_zone())
                .zone_nb_couloir(scenario.getZone_nb_couloir())
                .zone_epaisseur(scenario.getZone_epaisseur())
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
