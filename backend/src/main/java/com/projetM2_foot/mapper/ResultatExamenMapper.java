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
                .userGroupe(request.getGroupe())
                .userNom(request.getNom())
                .userPrenom(request.getPrenom())
                .userAge(request.getAge())
                .userGenre(request.getGenre())
                .userVision(request.getVision())

                .userPratique(request.getPratique())
                .userPratiqueType(request.getPratique_type())

                .userClub(request.getClub())
                .userClubActu(request.getClub_actu())
                .userClubNiveau(request.getClub_niveau())
                .userClubAnnee(request.getClub_annee())
                .userClubMatch(request.getClub_match())
                .userClubEntrainement(request.getClub_entrainement())

                .userMatchTv(request.getMatch_tv())
                .userMatchStade(request.getMatch_stade())

                .userJeu(request.getJeu())
                .userJeuFoot(request.getJeu_foot())
                .userJeuFrequence(request.getJeu_frequence())


                .score(0)
                .examen(examenService.getById(request.getExamen()))
                .createDate(new Date())
                .build();
    }


    public ResultatExamenResponse toDto (ResultatExamen entity){

        return ResultatExamenResponse.builder()
                .id(entity.getId())
                .score(entity.getScore())
                .examen(entity.getExamen().getId())

                .groupe(entity.getUserGroupe())
                .nom(entity.getUserNom())
                .prenom(entity.getUserPrenom())
                .age(entity.getUserAge())
                .genre(entity.getUserGenre())
                .vision(entity.getUserVision())

                .pratique(entity.getUserPratique())
                .pratique_type(entity.getUserPratiqueType())

                .club(entity.getUserClub())
                .club_actu(entity.getUserClubActu())
                .club_niveau(entity.getUserClubNiveau())
                .club_annee(entity.getUserClubAnnee())
                .club_match(entity.getUserClubMatch())
                .club_entrainement(entity.getUserClubEntrainement())

                .jeu(entity.getUserJeu())
                .jeu_foot(entity.getUserJeuFoot())
                .jeu_frequence(entity.getUserJeuFrequence())
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
                .nom(listEntity.getUserNom())
                .prenom(listEntity.getUserPrenom())
                .build();
    }

    public List<ResultatExamenResponse> toDtoAll (List<ResultatExamen> listEntity){
        return listEntity.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
