package com.projetM2_foot.service;


import com.projetM2_foot.entity.Examen;
import com.projetM2_foot.entity.Experience;
import com.projetM2_foot.repository.ExamenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
        return examenRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Examen non trouvé avec l'id : " + id));
    }

    public Examen getByName(String name) {
        return examenRepository
                .findByName(name)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Examen non trouvé avec le nom : " + name));
    }

    public List<Examen> getAll(){
        return examenRepository.findAll();
    }

    public Examen addExp(Long id , Experience exp){
        Examen exa = getById(id);
        exa.getExperienceSet().add(exp);
        return examenRepository.save(exa);

    }

    public Examen addExp(String name , Experience exp){
        Examen exa = getByName(name);
        exa.getExperienceSet().add(exp);
        return examenRepository.save(exa);
    }

}
