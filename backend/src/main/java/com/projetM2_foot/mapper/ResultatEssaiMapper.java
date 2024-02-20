package com.projetM2_foot.mapper;

import com.projetM2_foot.api.request.ResultatEssaiRequestCreate;
import com.projetM2_foot.api.response.FeedbackResponse;
import com.projetM2_foot.entity.*;
import com.projetM2_foot.service.ResultatExperienceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Component
@AllArgsConstructor
public class ResultatEssaiMapper {

    final ResultatExperienceService resultatExperienceService;

    public ResultatEssai toEntity (ResultatEssaiRequestCreate request , Set<ResultatDeplacement> rdepSet){

        return ResultatEssai.builder()
                .num(request.getNum())
                .temps(request.getTemps())
                .resultatExperience(resultatExperienceService.getById(request.getResultatExperience()))
                .deplacementSet(rdepSet)
                .build();
    }




    public FeedbackResponse toFeedbackDto (ResultatEssai rtry){

        rtry.getDeplacementSet().removeIf(ResultatDeplacement::getReussi);
        return FeedbackResponse.builder()
                .essai(rtry.getId())
                .score(rtry.getScore())
                .listError(rtry.getDeplacementSet()
                        .stream()
                        .map(DeplacementMapper::toResDto)
                        .collect(Collectors.toList()))
                .reussi(rtry.isReussi())
                .build();
    }

    public List<FeedbackResponse> toFeedbackDtoAll (List<ResultatEssai> listEntity){
        return listEntity.stream()
                .map(this::toFeedbackDto)
                .collect(Collectors.toList());
    }

}
