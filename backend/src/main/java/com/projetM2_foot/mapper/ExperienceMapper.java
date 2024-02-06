package com.projetM2_foot.mapper;

import com.projetM2_foot.api.request.experience.ExperienceRequestCreate;
import com.projetM2_foot.api.response.ExperienceResponse;
import com.projetM2_foot.entity.Experience;
import com.projetM2_foot.entity.Scenario;

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

        final Scenario scenario = scenarioService.getScenario(request.getScenario());

        return Experience.builder()
                .scenario(scenario)
                .typeFeedback(request.getTypeFeedback())
                .freqFeedback(request.getFreqFeedback())
                .visuFeedback(request.getVisuFeedback())
                .build();
    }


    public ExperienceResponse toDto (Experience entity){

        return ExperienceResponse.builder()
                .id(entity.getId())
                .scenario(entity.getScenario().getId())
                .typeFeedback(entity.getTypeFeedback())
                .freqFeedback(entity.getFreqFeedback())
                .visuFeedback(entity.getVisuFeedback())
                .build();
    }

    public List<ExperienceResponse> toDtoAll (List<Experience> listEntity){
        return listEntity.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
