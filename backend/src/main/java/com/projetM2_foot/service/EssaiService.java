package com.projetM2_foot.service;


import com.projetM2_foot.api.request.ResultatEssaiRequestCreate;
import com.projetM2_foot.entity.*;
import com.projetM2_foot.mapper.ResultatEssaiMapper;
import com.projetM2_foot.repository.EssaiRepository;
import com.projetM2_foot.repository.ResultatDeplacementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class EssaiService {

    @Autowired
    private final EssaiRepository essaiRepository;
    private final ResultatDeplacementRepository resultatDeplacementRepository;

    private final ResultatExperienceService resultatExperienceService;
    private final DeplacementService deplacementService;
    private final ResultatEssaiMapper resultatEssaiMapper;



    public ResultatEssai create(Set<ResultatDeplacement> rdepSet , ResultatEssaiRequestCreate request){


        // Vérifie la cohérence entre le résultat et le scénario et retourne le nombre de bonne réponse sur cette essai
        int score = getFeedbackListDeplacement(rdepSet , request.getResultatExperience());

        // Creer l'essai avec la liste de deplacement
        ResultatEssai rtry = resultatEssaiMapper.toEntity(request , rdepSet);
        rtry.setScore(score);
        rtry.setReussi(score == rdepSet.size());

        if(rtry.isReussi()){
            resultatExperienceService.addToScore(rtry.getResultatExperience() , 1);
        }
        return essaiRepository.save(rtry);
    }


    public List<ResultatEssai> getEssaisByIdResultatExperience(Long idResultatExperience){

        return essaiRepository
                .findByResultatExperienceId(idResultatExperience);

    }


    final static float MAX_POURCENTAGE_TERRAIN_WIDTH = 100;
    final static float MAX_POURCENTAGE_TERRAIN_HEIGHT = 100;

    /**
     * Retourne les numero de zone X Y en focntion des délimitation
     * @param x Coordonées X
     * @param y Coordonnée Y
     * @param nb_colonne Nombre de zone
     * @param nb_ligne Nombre de couloir
     */
    public int[] getZoneByCoord(float x , float y , int nb_colonne , int nb_ligne){

        int[] zone = new int[2];

        for(int i = 0 ; i < nb_colonne ; i++) {
            // Condition X
            if ((((MAX_POURCENTAGE_TERRAIN_WIDTH / nb_colonne) * i) <= x) && (x < ((MAX_POURCENTAGE_TERRAIN_WIDTH / nb_colonne) * (i + 1)))) {
                zone[0] = i + 1;
            }
        }

        for (int j = 0; j < nb_ligne; j++) {
            // Condition Y
            if (((( MAX_POURCENTAGE_TERRAIN_HEIGHT / nb_ligne) * j) <= y) && (y < ((MAX_POURCENTAGE_TERRAIN_HEIGHT / nb_ligne) * (j + 1)))) {
                zone[1] = j + 1;
            }
        }

        return zone;
    }


    public void getFeedbackByDeplacement(ResultatDeplacement rdep , Long exp_id){

        // trouver les délimitation
        ResultatExperience rexp = resultatExperienceService.getById(exp_id);

        int nb_colonne = rexp.getExperience().getColonArea();
        int nb_ligne = rexp.getExperience().getCorridorArea();

        if(nb_colonne == 0){
            nb_colonne = rexp.getExperience().getScenario().getColonArea();
        }
        if(nb_ligne == 0){
            nb_ligne = rexp.getExperience().getScenario().getCorridorArea();
        }

        // trouver le deplacement de essai
        float res_x = rdep.getEndPosX();
        float res_y = rdep.getEndPosY();
        int num_action = rdep.getNumAction();

        // get by scenario & num_action
        Long scenario = rexp.getExperience().getScenario().getId();
        Deplacement dep = deplacementService.getByScenarioAndAction(scenario , num_action);

        // Position de départ
        int[] zoneScenarioStart = getZoneByCoord(
                dep.getStartPosX(),
                dep.getStartPosY(), nb_colonne, nb_ligne);
        rdep.setScenarioStartZoneX(zoneScenarioStart[0]);
        rdep.setScenarioStartZoneY(zoneScenarioStart[1]);

        // Position de départ
        int[] zoneEssaiStart = getZoneByCoord(rdep.getStartZoneX(), rdep.getStartZoneY(), nb_colonne, nb_ligne);
        rdep.setStartZoneX(zoneEssaiStart[0]);
        rdep.setStartZoneY(zoneEssaiStart[1]);

        // Récupération et set des valeurs de zones
        int[] zoneScenario = getZoneByCoord(
                dep.getEndPosX(),
                dep.getEndPosY(), nb_colonne, nb_ligne);
        rdep.setScenarioEndZoneX(zoneScenario[0]);
        rdep.setScenarioEndZoneY(zoneScenario[1]);

        // Vérifie si les entités sont les même
        if(Objects.equals(dep.getEntite().getNumero(), rdep.getEntite().getNumero()) &&
                Objects.equals(dep.getEntite().getType() , rdep.getEntite().getType())){

            int[] zoneEssai = getZoneByCoord(res_x , res_y , nb_colonne , nb_ligne);
            rdep.setEndZoneX(zoneEssai[0]);
            rdep.setEndZoneY(zoneEssai[1]);

            //log.info("[I,J] = [" + i + "," + j + "]");

            // Check comparaison
            // modifier le deplacement en réussi
            rdep.setReussi((rdep.getScenarioEndZoneX() == rdep.getEndZoneX()) &&
                    (rdep.getScenarioEndZoneY() == rdep.getEndZoneY()));

        }
        else {
            rdep.setReussi(false);
            rdep.setEndZoneX(0);
            rdep.setEndZoneY(0);
        }
    }

    public int getFeedbackListDeplacement(Set<ResultatDeplacement> rdepSet , Long exp){

        int score = 0;
        for (ResultatDeplacement res : rdepSet) {
            getFeedbackByDeplacement(res , exp);

            if(res.getReussi()){
                score++;
            }
        }
        return score;
    }


    public float Convert(float x , float pourcent){
        return (x * MAX_POURCENTAGE_TERRAIN_WIDTH ) / pourcent;
    }

}
