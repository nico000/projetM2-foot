package com.projetM2_foot.report.mapper;

import com.projetM2_foot.entity.*;
import com.projetM2_foot.report.response.*;
import com.projetM2_foot.repository.DeplacementRepository;
import com.projetM2_foot.repository.EntiteRepository;
import com.projetM2_foot.repository.ExperienceRepository;
import com.projetM2_foot.repository.ScenarioRepository;
import com.projetM2_foot.service.ExamenService;
import com.projetM2_foot.service.ExperienceService;
import lombok.AllArgsConstructor;
import org.apache.commons.math3.analysis.function.Exp;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ReportMapper {

    final EntiteRepository entiteRepository;
    final DeplacementRepository deplacementRepository;
    final ScenarioRepository scenarioRepository;

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

        return ReportDeplacement.builder()
                .id(entity.getId())
                .entite(toDtoEntite(entite))
                .action(entity.getNumAction())
                .posXDepart(entity.getStartPosX())
                .posYDepart(entity.getStartPosY())
                .posXArrive(entity.getEndPosX())
                .posYArrive(entity.getEndPosY())
                .build();
    }

    /*
    public static ResultatDeplacementResponse toDtoResultatDeplacement(ResultatDeplacement entity){

        return ResultatDeplacementResponse.builder()
                .id(entity.getId())
                .reussi(entity.getReussi())
                .entite(entity.getEntite().getId())
                .numAction(entity.getNumAction())
                .startPosX(entity.getStartPosX())
                .startPosY(entity.getStartPosY())
                .endPosX(entity.getEndPosX())
                .endPosY(entity.getEndPosY())
                .build();
    }

     */

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



    public List<ReportScenario> toDtoAllScenario (List<Scenario> scenarioList){

        return scenarioList.stream()
                .map(this::toDtoScenario)
                .collect(Collectors.toList());
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
