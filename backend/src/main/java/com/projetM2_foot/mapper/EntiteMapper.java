package com.projetM2_foot.mapper;

import com.projetM2_foot.api.request.entite.EntiteRequestCreate;
import com.projetM2_foot.api.request.entite.EntiteRequestUpdate;
import com.projetM2_foot.api.response.EntiteResponse;
import com.projetM2_foot.entity.Entite;
import com.projetM2_foot.entity.Scenario;
import com.projetM2_foot.service.ScenarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EntiteMapper {

    private final ScenarioService scenarioService;

    public Entite toEntity (EntiteRequestCreate request){

        final Scenario scenario = scenarioService.getScenario(request.getScenario());

        return Entite.builder()
                .scenario(scenario)
                .type(request.getType())
                .numero(request.getNumero())
                .initialPosX(request.getX())
                .initialPosY(request.getY())
                .createDate(new Date())
                .build();
    }

    public Entite toEntity (EntiteRequestUpdate request){

        final Scenario scenario = scenarioService.getScenario(request.getScenario());

        return Entite.builder()
                .id(request.getId())
                .scenario(scenario)
                .type(request.getType())
                .numero(request.getNumero())
                .initialPosX(request.getX())
                .initialPosY(request.getY())
                .createDate(new Date())
                .build();
    }

    public EntiteResponse toDto (Entite scenario){

        long scenar;
        if(scenario.getScenario() == null){
            scenar = 0L;
        }
        else{
            scenar = scenario.getScenario().getId();
        }

        return EntiteResponse.builder()
                .id(scenario.getId())
                .scenario(scenar)
                .type(scenario.getType())
                .numero(scenario.getNumero())
                .x(scenario.getInitialPosX())
                .y(scenario.getInitialPosY())
                .build();
    }

    public List<EntiteResponse> toDtoAll (List<Entite> listEntity){
        return listEntity.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
