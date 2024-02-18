package com.projetM2_foot.service;

import com.projetM2_foot.entity.ResultatExamen;
import com.projetM2_foot.repository.ResultatExamenRepository;
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
public class ResultatExamenService {

    @Autowired
    private final ResultatExamenRepository resultatExamenRepository;

    public ResultatExamen create(ResultatExamen entity){
        return resultatExamenRepository.save(entity);
    }

    public ResultatExamen getById(Long id){
        return resultatExamenRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Resultat examen non trouvé avec l'id : " + id));
    }

    public List<ResultatExamen> getAll(){ return resultatExamenRepository.findAll();}


}
