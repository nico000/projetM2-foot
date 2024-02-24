package com.projetM2_foot.report.mapper;

import com.projetM2_foot.entity.*;
import com.projetM2_foot.report.response.*;
import com.projetM2_foot.repository.DeplacementRepository;
import com.projetM2_foot.repository.EntiteRepository;
import com.projetM2_foot.repository.ScenarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ReportMapper {

    final EntiteRepository entiteRepository;
    final DeplacementRepository deplacementRepository;
    final ScenarioRepository scenarioRepository;


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
