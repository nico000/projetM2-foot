package com.projetM2_foot.service;


import com.projetM2_foot.entity.Examen;
import com.projetM2_foot.entity.Experience;
import com.projetM2_foot.repository.ExamenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExamenService {

    @Autowired
    private final ExamenRepository examenRepository;

    public Examen create(Examen entity){
        return examenRepository.save(entity);
    }

    public Examen getById(Long id){
        return examenRepository.findById(id).orElse(null);
    }

    public List<Examen> getAll(){ return examenRepository.findAll();}

    public Examen addExp(Long id , Experience exp){

        Examen exa = examenRepository.findById(id).orElse(null);

        assert exa != null;
        exa.getExperienceSet().add(exp);

        return examenRepository.save(exa);

    }




}
