package com.projetM2_foot.service;

import com.projetM2_foot.entity.Deplacement;
import com.projetM2_foot.repository.DeplacementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class DeplacementService {

    @Autowired
    private final DeplacementRepository deplacementRepository;

    public Deplacement create(Deplacement entity){
        return deplacementRepository.save(entity);
    }

    public List<Deplacement> getByScenario(Long scenario){
        return deplacementRepository.findByScenarioId(scenario);
    }


}
