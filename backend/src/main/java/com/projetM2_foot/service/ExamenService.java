package com.projetM2_foot.service;


import com.projetM2_foot.entity.Examen;
import com.projetM2_foot.entity.Experience;
import com.projetM2_foot.mapper.ExamenMapper;
import com.projetM2_foot.repository.ExamenRepository;
import com.projetM2_foot.repository.ExperienceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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




}
