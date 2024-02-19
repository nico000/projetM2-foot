package com.projetM2_foot.repository;

import com.projetM2_foot.entity.ResultatEssai;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EssaiRepository extends CrudRepository<ResultatEssai, Long> {

    @Override
    List<ResultatEssai> findAll();

}
