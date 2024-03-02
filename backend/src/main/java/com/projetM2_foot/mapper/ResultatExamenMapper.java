package com.projetM2_foot.mapper;

import com.projetM2_foot.api.request.ResultatExamenRequestCreate;
import com.projetM2_foot.api.response.ResNomPrenomResponse;
import com.projetM2_foot.api.response.ResultatCreationResponse;
import com.projetM2_foot.api.response.ResultatExamenResponse;
import com.projetM2_foot.entity.ResultatExamen;
import com.projetM2_foot.service.ExamenService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Component
@AllArgsConstructor
public class ResultatExamenMapper {


    final ExamenService examenService;

    public ResultatExamen toEntity (ResultatExamenRequestCreate request){

        return ResultatExamen.builder()
                .groupePerson(request.getGroupe())
                .nomPerson(request.getNom())
                .prenomPerson(request.getPrenom())
                .agePerson(request.getAge())
                .sexPerson(request.getSex())
                .pratiquePerson(request.getPratique())
                .proPerson(request.getPro())
                .anneeExperiencePerson(request.getAnneeExperience())
                .matchePerson(request.getMatche())
                .entrainementPerson(request.getEntrainement())
                .heurePerson(request.getHeure())
                .score(0)
                .examen(examenService.getById(request.getExamen()))
                .createDate(new Date())
                .vueMatchPerson(request.getVueMatch())
                .vueTvPerson(request.getVueTv())
                .jvPerson(request.getJv())
                .visionPerson(request.getVision())
                .build();
    }


    public ResultatExamenResponse toDto (ResultatExamen entity){

        return ResultatExamenResponse.builder()
                .id(entity.getId())
                .groupe(entity.getGroupePerson())
                .nom(entity.getNomPerson())
                .prenom(entity.getPrenomPerson())
                .age(entity.getAgePerson())
                .sex(entity.getSexPerson())
                .pratique(entity.getPratiquePerson())
                .pro(entity.getProPerson())
                .anneeExperience(entity.getAnneeExperiencePerson())
                .matche(entity.getMatchePerson())
                .entrainement(entity.getEntrainementPerson())
                .heure(entity.getHeurePerson())
                .score(entity.getScore())
                .examen(entity.getExamen().getId())
                .vueMatch(entity.getVueMatchPerson())
                .vueTv(entity.getVueTvPerson())
                .jv(entity.getJvPerson())
                .vision(entity.getVisionPerson())
                .build();
    }

    public ResultatCreationResponse toDtoCreation(Long id , List<Long> rexp){
        return ResultatCreationResponse.builder()
                .id(id)
                .resultat_experience(rexp)
                .build();
    }

    public ResNomPrenomResponse toDtoGetIdentite(ResultatExamen listEntity){

        return ResNomPrenomResponse.builder()
                .nom(listEntity.getNomPerson())
                .prenom(listEntity.getPrenomPerson())
                .build();
    }

    public List<ResultatExamenResponse> toDtoAll (List<ResultatExamen> listEntity){
        return listEntity.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
