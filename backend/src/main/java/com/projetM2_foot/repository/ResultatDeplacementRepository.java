package com.projetM2_foot.repository;

import com.projetM2_foot.entity.ResultatDeplacement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultatDeplacementRepository extends CrudRepository<ResultatDeplacement, Long> {

  //  List<Deplacement> findByScenarioId(Long scenario);

    //void deleteByEntiteId(Long entite);

}
