package com.projetM2_foot.repository;

import com.projetM2_foot.entity.ResultatDeplacement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ResultatDeplacementRepository extends CrudRepository<ResultatDeplacement, Long> {

  //  List<Deplacement> findByScenarioId(Long scenario);

    //void deleteByEntiteId(Long entite);

}
