package com.projetM2_foot.service;


import com.projetM2_foot.entity.ResultatExperience;
import com.projetM2_foot.repository.ResultatExperienceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResultatExperienceService {

    @Autowired
    private final ResultatExperienceRepository resultatExperienceRepository;

    public ResultatExperience create(ResultatExperience entity){
        return resultatExperienceRepository.save(entity);
    }

    public ResultatExperience getById(Long id){
        return resultatExperienceRepository.findById(id).orElse(null);
    }

    public List<ResultatExperience> getAll(){
        return resultatExperienceRepository.findAll();
    }



}
