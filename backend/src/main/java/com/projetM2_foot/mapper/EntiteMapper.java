package com.projetM2_foot.mapper;

import com.projetM2_foot.api.request.EntiteRequestCreate;
import com.projetM2_foot.api.request.EntiteRequestUpdate;
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

        return Entite.builder()
                .scenario(scenarioService.getScenario(request.getScenario()))
                .type(request.getType())
                .numero(request.getNumero())
                .initialPosX(request.getX())
                .initialPosY(request.getY())
                .createDate(new Date())
                .build();
    }

    public Entite toEntity (EntiteRequestUpdate request){

        return Entite.builder()
                .id(request.getId())
                .scenario(scenarioService.getScenario(request.getScenario()))
                .type(request.getType())
                .numero(request.getNumero())
                .initialPosX(request.getX())
                .initialPosY(request.getY())
                .createDate(new Date())
                .build();
    }

    public EntiteResponse toDto (Entite entity){

        return EntiteResponse.builder()
                .id(entity.getId())
                .scenario(entity.getScenario().getId())
                .type(entity.getType())
                .numero(entity.getNumero())
                .x(entity.getInitialPosX())
                .y(entity.getInitialPosY())
                .build();
    }

    public List<EntiteResponse> toDtoAll (List<Entite> listEntity){
        return listEntity.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
