package com.projetM2_foot.service;


import com.projetM2_foot.entity.ResultatExperience;
import com.projetM2_foot.repository.ResultatExperienceRepository;
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
public class ResultatExperienceService {

    @Autowired
    private final ResultatExperienceRepository resultatExperienceRepository;
    private final ResultatExamenService resultatExamenService;

    public ResultatExperience create(ResultatExperience entity){
        return resultatExperienceRepository.save(entity);
    }

    public ResultatExperience getById(Long id){
        return resultatExperienceRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Resultat expérience non trouvé avec l'id : " + id));
    }

    public List<ResultatExperience> getAll(){
        return resultatExperienceRepository.findAll();
    }


    public List<ResultatExperience> getListByResultatExamen(Long idResultatExamen){

        return resultatExperienceRepository
                .findByResultatExamenId(idResultatExamen);
    }

    public ResultatExperience addToScore(ResultatExperience rexp , int increment){

        rexp.setScore(rexp.getScore() + increment);
        resultatExamenService.addToScore(rexp.getResultatExamen() , increment);
        return resultatExperienceRepository.save(rexp);

    }


}
