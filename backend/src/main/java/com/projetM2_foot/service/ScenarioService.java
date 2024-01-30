package com.projetM2_foot.service;

import com.projetM2_foot.entity.Scenario;
//import com.projetM2_foot.repository.ScenarioRepository;
import com.projetM2_foot.repository.ScenarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScenarioService {

    private final ScenarioRepository scenarioRepository;

    /**
     * Créer un scénario
     * @param entity Entité qui doit-être créer
     * @return L'entité nouvellement créer dans la BDD
     */
    public Scenario create(Scenario entity){
        return scenarioRepository.save(entity);
    }

    public List<Scenario> getAllScenario(){
        return scenarioRepository.findAll();
    }

    public Scenario getScenario(Long id){
        return scenarioRepository.findById(id).orElse(null);
    }

    public void deleteScenario(Long id){
        scenarioRepository.deleteById(id);
    }

    public Scenario updateScenario(Scenario entity){
        return scenarioRepository.save(entity);
    }

}
