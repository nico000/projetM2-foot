package com.projetM2_foot.service;

import com.projetM2_foot.entity.Deplacement;
import com.projetM2_foot.entity.Scenario;
import com.projetM2_foot.repository.DeplacementRepository;
import com.projetM2_foot.repository.ScenarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class DeplacementService {

    @Autowired
    private final DeplacementRepository deplacementRepository;
    private final ScenarioRepository scenarioRepository;

    public Deplacement create(Deplacement entity){
        return deplacementRepository.save(entity);
    }

    public List<Deplacement> getByScenario(Long scenario){
        return deplacementRepository.findByScenarioId(scenario);
    }

    public void deleteByEntite(Long entite){
        deplacementRepository.deleteByEntiteId(entite);
    }

    public void deleteLast(Long scenario){

        Scenario  scenarioE = scenarioRepository.findById(scenario).orElse(null);

        if(scenarioE != null){

            final List<Deplacement>  entityToSupp = deplacementRepository.deleteLast(scenarioE, PageRequest.of(0,1));

            if(!entityToSupp.isEmpty()){
                deplacementRepository.deleteById(entityToSupp.get(0).getId());
            }
        }
    }


}
