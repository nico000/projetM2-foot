package com.projetM2_foot.service;

import com.projetM2_foot.entity.Entite;
import com.projetM2_foot.mapper.EntiteMapper;
import com.projetM2_foot.repository.EntiteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntiteService {

    private final EntiteMapper entiteMapper;
    private final EntiteRepository entiteRepository;

    public Entite create(Entite entity){
        return entiteRepository.save(entity);
    }


}
