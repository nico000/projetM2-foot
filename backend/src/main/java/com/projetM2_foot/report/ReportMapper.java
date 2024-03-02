package com.projetM2_foot.report;

import com.projetM2_foot.entity.*;
import com.projetM2_foot.report.response.*;
import com.projetM2_foot.repository.*;
import com.projetM2_foot.service.EssaiService;
import com.projetM2_foot.service.ResultatExperienceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ReportMapper {

    final EntiteRepository entiteRepository;
    final DeplacementRepository deplacementRepository;
    final ScenarioRepository scenarioRepository;
    final EssaiService essaiService;
    final ResultatExperienceService resultatExperience;



    public ReportResultatExamen toDtoResultatExamen(ResultatExamen entity){

        List<ResultatExperience> list =
                    resultatExperience.getListByResultatExamen(entity.getId());

        return ReportResultatExamen.builder()
                .id(entity.getId())
                .personalInformation(toDtoPerson(entity))
                .resultatExperiences(list
                        .stream()
                        .map(this::toDtoResultatExperience)
                        .collect(Collectors.toList()))
                .createDate(entity.getCreateDate())
                .score(entity.getScore())
                .examen(toDtoExamen(entity.getExamen()))
                .build();
    }


    public ReportPerson toDtoPerson(ResultatExamen entity){

        return ReportPerson.builder()
                .nom(entity.getNomPerson())
                .prenom(entity.getPrenomPerson())
                .groupe(entity.getGroupePerson())
                .age(entity.getAgePerson())
                .genre(entity.getSexPerson())
                .pratique(entity.getPratiquePerson())

                .pro(entity.getProPerson())
                .anneeExperience(entity.getAnneeExperiencePerson())
                .nombreMatches(entity.getMatchePerson())
                .entrainement(entity.getEntrainementPerson())
                .heure(entity.getHeurePerson())

                .vueMatch(entity.getVueMatchPerson())
                .vueTv(entity.getVueTvPerson())
                .jv(entity.getJvPerson())
                .vision(entity.getVisionPerson())
                .build();
    }

    public ReportResultatExperience toDtoResultatExperience(ResultatExperience entity){
        List<ResultatEssai> listEssai  = essaiService.getEssaisByIdResultatExperience(entity.getId());


        return ReportResultatExperience.builder()
                .id(entity.getId())
                .score(entity.getScore())
                .createDate(entity.getCreateDate())
                .experience(toDtoExperience(entity.getExperience()))
                .essais(listEssai.stream().map(this::toDtoEssai).collect(Collectors.toList()))
                .build();
    }

    public ReportEssai toDtoEssai(ResultatEssai entity){

        List<ResultatDeplacement> rdep =
                new ArrayList<>(entity.getDeplacementSet());
        Comparator<ResultatDeplacement> comparator = Comparator.comparingInt(ResultatDeplacement::getNumAction);
        rdep.sort(comparator);

        return ReportEssai.builder()
                .id(entity.getId())
                .reussi(entity.isReussi())
                .score(entity.getScore())
                .temps(entity.getTemps())
                .numEssai(entity.getNum())
                .deplacementsRealiser(rdep
                        .stream()
                        .map(this::toDtoResultatDeplacement)
                        .collect(Collectors.toList()))
                .build();
    }


    public ReportZonePos toDtoZone(int x1 , int y1 , int x2 , int y2){

        return ReportZonePos.builder()
                .zoneDepartX(x1)
                .zoneDepartY(y1)
                .zoneArriveeX(x2)
                .zoneArriveeY(y2)
                .build();
    }

    public ReportResultatDeplacement toDtoResultatDeplacement(ResultatDeplacement entity){

        return ReportResultatDeplacement.builder()
                .id(entity.getId())
                .reussi(entity.getReussi())
                .action(entity.getNumAction())
                .entite(toDtoEntite(entity.getEntite()))
                .scenarioPos(toDtoZone(
                        entity.getScenarioStartZoneX() ,
                        entity.getScenarioStartZoneY() ,
                        entity.getScenarioEndZoneX() ,
                        entity.getScenarioEndZoneY()))
                .essaiPos(toDtoZone(
                        entity.getStartZoneX() ,
                        entity.getStartZoneY() ,
                        entity.getEndZoneX() ,
                        entity.getEndZoneY()))
                .startPosX(entity.getStartPosX())
                .startPosY(entity.getStartPosY())
                .endPosX(entity.getEndPosX())
                .endPosY(entity.getEndPosY())
                .build();
    }

    public ReportExamen toDtoExamen (Examen entity){

        return ReportExamen.builder()
                .id(entity.getId())
                .name(entity.getName())

                .sequencage(entity.getSequencage())
                .complexite(entity.getComplexite())
                .contenuFeedback(entity.getContenuFeedback())
                .experiences(entity.getExperienceSet().stream().map(this::toDtoExperience).collect(Collectors.toList()))
                .build();
    }



    public ReportExperience toDtoExperience (Experience entity){

        return ReportExperience.builder()
                .id(entity.getId())
                .scenario(toDtoScenario(entity.getScenario()))
                .displayArea(entity.getDisplayArea())
                .freqFeedback(entity.getFreqFeedback())
                .typeFeedback(entity.getTypeFeedback())
                .visuFeedback(entity.getVisuFeedback())
                .colonArea(entity.getColonArea())
                .corridorArea(entity.getCorridorArea())
                .groundColor(entity.getGroundColor())
                .modeScene(entity.getModeScene())
                .build();
    }

    public ReportDeplacement toDtoDeplacement (Deplacement entity){

        Entite entite = entiteRepository.findById(entity.getEntite().getId()).orElse(null);

        ReportEntite reportEntite = null;
        if(entite != null) reportEntite = toDtoEntite(entite);

        return ReportDeplacement.builder()
                .id(entity.getId())
                .entite(reportEntite)
                .action(entity.getNumAction())
                .posXDepart(entity.getStartPosX())
                .posYDepart(entity.getStartPosY())
                .posXArrive(entity.getEndPosX())
                .posYArrive(entity.getEndPosY())
                .build();
    }


    public ReportScenario toDtoScenario(Scenario scenario){

        List<Entite> entiteList = entiteRepository.findByScenarioIdOrderByNumero(scenario.getId());
        List<Deplacement> deplacementList = deplacementRepository.findByScenarioId(scenario.getId());

        return ReportScenario.builder()
                .id(scenario.getId())
                .nom(scenario.getName())
                .scene3D(scenario.getThreeDim())
                .couleur(scenario.getGroundColor())
                .taille(scenario.getGroundWidth())
                .nombreJoueur(entiteList.size())
                .zoneUtiliser(scenario.getDisplayArea())
                .zoneAfficher(scenario.getDisplayArea())
                .nombreZone(scenario.getColonArea())
                .nombreCouloir(scenario.getCorridorArea())
                .entiteList(entiteList.stream().map(this::toDtoEntite).collect(Collectors.toList()))
                .deplacementList(deplacementList.stream().map(this::toDtoDeplacement).collect(Collectors.toList()))
                .build();

    }


    public ReportEntite toDtoEntite (Entite entity){

        return ReportEntite.builder()
                .id(entity.getId())
                .type(entity.getType())
                .numero(entity.getNumero())
                .initialPosX(entity.getInitialPosX())
                .initialPosY(entity.getInitialPosY())
                .createDate(entity.getCreateDate())
                .build();
    }



}
