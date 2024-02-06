package com.projetM2_foot.service;


import com.projetM2_foot.entity.Experience;
import com.projetM2_foot.repository.ExperienceRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExperienceService {

    @Autowired
    private final ExperienceRepository experienceRepository;

    public Experience create(Experience entity){
        return experienceRepository.save(entity);
    }

    public Experience getById(Long experience){
        return experienceRepository.findById(experience).orElse(null);
    }




}
