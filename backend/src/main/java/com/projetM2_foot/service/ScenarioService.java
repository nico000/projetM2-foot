package com.projetM2_foot.service;

import com.projetM2_foot.entity.Deplacement;
import com.projetM2_foot.entity.Entite;
import com.projetM2_foot.entity.Scenario;
import com.projetM2_foot.repository.ScenarioRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScenarioService {

    private final ScenarioRepository scenarioRepository;
    private final EntiteService entiteService;
    private final DeplacementService deplacementService;

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
        return scenarioRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Scenario non trouvé avec l'id : " + id));
    }

    public Scenario getScenarioByName(String nom){
        return scenarioRepository.findByName(nom)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Scenario non trouvé avec le nom : " + nom));
    }

    @Transactional
    public void deleteScenario(Long id){
        // Effacer les entités lié au scénario
        entiteService.deleteByScenario(id);
        scenarioRepository.deleteById(id);
    }

    public Scenario updateScenario(Scenario entity){
        return scenarioRepository.save(entity);
    }

    public int CountJoueurByScenario(Long idScenario){
        List<Entite> list = entiteService.getByScenario(idScenario);
        int nbJoueur = 0;
        for(Entite e : list){
            if(e.getType() != 0) nbJoueur++;
        }
        return nbJoueur;
    }

    public int CountDeplacementByScenario(Long idScenario){
        List<Deplacement> list = deplacementService.getByScenario(idScenario);
        return list.size();
    }



}
