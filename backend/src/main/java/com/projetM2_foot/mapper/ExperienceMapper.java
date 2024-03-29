package com.projetM2_foot.mapper;

import com.projetM2_foot.api.request.ExperienceRequestCreate;
import com.projetM2_foot.api.response.ExperienceResponse;
import com.projetM2_foot.entity.Experience;

import com.projetM2_foot.service.ScenarioService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
@AllArgsConstructor
public class ExperienceMapper {


    final ScenarioService scenarioService;

    public Experience toEntity (ExperienceRequestCreate request){

        return Experience.builder()
                .scenario(scenarioService.getScenario(request.getScenario()))
                .typeFeedback(request.getTypeFeedback())
                .freqFeedback(request.getFreqFeedback())
                .visuFeedback(request.getVisuFeedback())
                .colonArea(request.getZone_nb_zone())
                .corridorArea(request.getZone_nb_couloir())
                .modeScene(request.getModeScene())
                .displayArea(request.getZone_display())
                .groundColor(request.getTerrain_couleur())
                .ordre(request.getOrdre())
                .build();
    }


    public ExperienceResponse toDto (Experience entity){

        return ExperienceResponse.builder()
                .id(entity.getId())
                .scenario(entity.getScenario().getId())
                .typeFeedback(entity.getTypeFeedback())
                .freqFeedback(entity.getFreqFeedback())
                .visuFeedback(entity.getVisuFeedback())
                .zone_nb_zone(entity.getColonArea())
                .zone_nb_couloir(entity.getCorridorArea())
                .zone_display(entity.getDisplayArea())
                .mode_scene(entity.getModeScene())
                .terrain_couleur(entity.getGroundColor())
                .ordre(entity.getOrdre())
                .build();
    }

    public List<ExperienceResponse> toDtoAll (List<Experience> listEntity){
        return listEntity.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
