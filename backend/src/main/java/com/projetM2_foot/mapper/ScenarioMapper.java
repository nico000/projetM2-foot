package com.projetM2_foot.mapper;

import com.projetM2_foot.dto.request.ScenarioRequestCreate;
import com.projetM2_foot.dto.response.ScenarioResponse;
import com.projetM2_foot.entity.Scenario;
import org.springframework.stereotype.Component;

@Component
public class ScenarioMapper {

    public Scenario ToEntity (ScenarioRequestCreate scenarioRequestCreate){
        return Scenario.builder()
                .nom(scenarioRequestCreate.getNom())
                .mode_scene(scenarioRequestCreate.getMode_scene())
                .terrain_couleur(scenarioRequestCreate.getTerrain_couleur())
                .terrain_taille(scenarioRequestCreate.getTerrain_taille())
                .joueur_nb(scenarioRequestCreate.getJoueur_nb())
                .feedback_frequence(scenarioRequestCreate.getFeedback_frequence())
                .feedback_condition(scenarioRequestCreate.getFeedback_condition())
                .feedback_type(scenarioRequestCreate.getFeedback_type())
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
                .build();
    }
}
