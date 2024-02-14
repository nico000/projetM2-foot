package com.projetM2_foot.mapper;

import com.projetM2_foot.api.request.ExamenRequestCreate;
import com.projetM2_foot.api.response.ExamenResponse;
import com.projetM2_foot.entity.Examen;
import com.projetM2_foot.entity.Experience;
import com.projetM2_foot.service.ExperienceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;


@Component
@AllArgsConstructor
public class ExamenMapper {


    final ExperienceService experienceService;

    public Examen toEntity (ExamenRequestCreate request){

        //Set<Experience> set = request.getExperienceSet().stream().map(experienceService::getById).collect(Collectors.toSet());

        return Examen.builder()
                .name(request.getName())
                .mode(request.getMode())
                .experienceSet(new HashSet<>())
                .build();
    }


    public ExamenResponse toDto (Examen entity){

        return ExamenResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .mode(entity.getMode())
                .experienceSet(entity.getExperienceSet().stream().map(Experience::getId).collect(Collectors.toList()))
                .build();
    }

    public List<ExamenResponse> toDtoAll (List<Examen> listEntity){
        return listEntity.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
