package com.projetM2_foot.mapper;

import com.projetM2_foot.api.response.ResultatExperienceResponse;
import com.projetM2_foot.entity.Experience;
import com.projetM2_foot.entity.ResultatExamen;
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

        Experience exp = experienceService.getById(experience);
        ResultatExamen rexa = resultatExamenService.getById(resultatExamen);
        if(exp == null || rexa == null) return null;

        return ResultatExperience.builder()
                .experience(exp)
                .resultatExamen(rexa)
                .score(-1)
                .createDate(new Date())
                .build();
    }


    public ResultatExperienceResponse toDto (ResultatExperience entity){

        return ResultatExperienceResponse.builder()
                .id(entity.getId())
                .experience(entity.getExperience().getId())
                .resultatExamen(entity.getResultatExamen().getId())
                .score(entity.getScore())
                .build();
    }

    public List<ResultatExperienceResponse> toDtoAll (List<ResultatExperience> listEntity){
        return listEntity.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}