package com.projetM2_foot.service;

import com.projetM2_foot.entity.Entite;
import com.projetM2_foot.repository.EntiteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EntiteService {

    @Autowired
    private final EntiteRepository entiteRepository;
    private final DeplacementService deplacementService;

    public Entite create(Entite entity){
        return entiteRepository.save(entity);
    }

    public List<Entite> getByScenario(Long id){
        return entiteRepository.findByScenarioIdOrderByNumero(id);
    }

    public Entite getEntite(Long id){
        return entiteRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id){
        deplacementService.deleteByEntite(id);
        entiteRepository.deleteById(id);
    }

    public void deleteByScenario(Long scenario){
        final List<Entite> entity = entiteRepository.findByScenarioIdOrderByNumero(scenario);
        for(Entite e : entity) deplacementService.deleteByEntite(e.getId());
        entiteRepository.deleteByScenarioId(scenario);
    }

    public Entite updateEntite(Entite entity){
        return entiteRepository.save(entity);
    }

}
