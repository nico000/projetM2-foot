package com.projetM2_foot.service;


import com.projetM2_foot.entity.*;
import com.projetM2_foot.repository.EssaiRepository;
import com.projetM2_foot.repository.ResultatDeplacementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
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

    @Transactional
    public ResultatEssai create(ResultatEssai entity){

        Set<ResultatDeplacement> rdepSet = new HashSet<>();

        for(ResultatDeplacement dep : entity.getDeplacementSet()){
            rdepSet.add(addDep(dep));
        }
        entity.setDeplacementSet(rdepSet);

        return essaiRepository.save(entity);
    }


    public ResultatDeplacement addDep(ResultatDeplacement dep){
        assert dep != null;
        return resultatDeplacementRepository.save(dep);
    }


    public void getFeedbackByDeplacement(ResultatDeplacement rdep , Long exp_id){

        // trouver les délimitation
        ResultatExperience rexp = resultatExperienceService.getById(exp_id);

        int nb_colonne = rexp.getExperience().getColonArea();
        int nb_ligne = rexp.getExperience().getCorridorArea();
        // trouver le deplacement de essai
        float res_x = rdep.getEndPosX();
        float res_y = rdep.getEndPosY();
        int num_action = rdep.getNumAction();
        //log.info( "X = " + res_x);
        //log.info( "Y = " + res_y);
        //log.info( "A = " + num_action);

        // get by scenario & num_action
        Long scenario = rexp.getExperience().getScenario().getId();
        Deplacement dep = deplacementService.getByScenarioAndAction(scenario , num_action);

        float x = dep.getEndPosX();
        float y = dep.getEndPosY();

        //log.info( "XI = " + x);
        //log.info( "YI = " + y);

        // effectuer le calcul

        // Vérifie si les entités sont les même
        if(Objects.equals(dep.getEntite().getNumero(), rdep.getEntite().getNumero())){


            for(int i = 0 ; i < nb_colonne ; i++){
                // Condition X
                if( ((((float) 100 /nb_colonne)*i) <= x) && (x < (((float) 100 /nb_colonne)*(i+1)))){

                    for(int j = 0 ; j < nb_ligne ; j++){
                        // Condition Y
                        if( ((((float) 100 /nb_ligne)*j) <= y) && (y < (((float) 100 /nb_ligne)*(j+1)))) {
                            //log.info("[I,J] = [" + i + "," + j + "]");

                            // Check comparaison
                            if(((((float) 100 /nb_colonne)*i) <= res_x) && (res_x < (((float) 100 /nb_colonne)*(i+1)))){
                                if(((((float) 100 /nb_ligne)*j) <= res_y) && (res_y < (((float) 100 /nb_ligne)*(j+1)))){

                                    // modifier le deplacement en réussi
                                    rdep.setReussi(true);
                                }
                            }
                            i = nb_colonne;
                            j = nb_ligne;

                        }
                    }
                }
            }
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



}
