package com.projetM2_foot.mapper;

import com.projetM2_foot.api.response.ResultatExperienceResponse;
import com.projetM2_foot.entity.ResultatExperience;
import com.projetM2_foot.service.ExperienceService;
import com.projetM2_foot.service.ResultatExamenService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Component
@AllArgsConstructor
public class ResultatExperienceMapper {

    final ExperienceService experienceService;
    final ResultatExamenService resultatExamenService;

    public ResultatExperience toEntity (Long resultatExamen , Long experience){

        return ResultatExperience.builder()
                .experience(experienceService.getById(experience))
                .resultatExamen(resultatExamenService.getById(resultatExamen))
                .score(0)
                .createDate(new Date())
                .ordre(experienceService.getById(experience).getOrdre())
                .build();
    }


    public ResultatExperienceResponse toDto (ResultatExperience entity){

        return ResultatExperienceResponse.builder()
                .id(entity.getId())
                .experience(entity.getExperience().getId())
                .resultatExamen(entity.getResultatExamen().getId())
                .score(entity.getScore())
                .ordre(entity.getOrdre())
                .build();
    }

    public List<ResultatExperienceResponse> toDtoAll (List<ResultatExperience> listEntity){
        return listEntity.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
