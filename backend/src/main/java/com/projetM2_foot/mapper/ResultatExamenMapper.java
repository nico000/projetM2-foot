package com.projetM2_foot.mapper;

import com.projetM2_foot.api.request.ResultatExamenRequestCreate;
import com.projetM2_foot.api.response.ResultatExamenResponse;
import com.projetM2_foot.entity.Examen;
import com.projetM2_foot.entity.ResultatExamen;
import com.projetM2_foot.service.ExamenService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
@AllArgsConstructor
public class ResultatExamenMapper {


    final ExamenService examenService;

    public ResultatExamen toEntity (ResultatExamenRequestCreate request){

        Examen exa = examenService.getById(request.getExamen());
        if(exa == null) return null;

        return ResultatExamen.builder()
                .nomPerson(request.getNom_person())
                .prenomPerson(request.getPrenom_person())
                .score(-1)
                .examen(exa)
                .build();
    }


    public ResultatExamenResponse toDto (ResultatExamen entity){

        return ResultatExamenResponse.builder()
                .id(entity.getId())
                .nom_person(entity.getNomPerson())
                .prenom_person(entity.getPrenomPerson())
                .score(entity.getScore())
                .examen(entity.getExamen().getId())
                .build();
    }

    public List<ResultatExamenResponse> toDtoAll (List<ResultatExamen> listEntity){
        return listEntity.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
