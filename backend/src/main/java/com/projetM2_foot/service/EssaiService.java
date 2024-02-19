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
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class EssaiService {

    @Autowired
    private final EssaiRepository essaiRepository;
    private final ResultatDeplacementRepository resultatDeplacementRepository;

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



}
