package com.projetM2_foot.repository;

import com.projetM2_foot.entity.Deplacement;
import com.projetM2_foot.entity.Scenario;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface DeplacementRepository extends CrudRepository<Deplacement, Long> {

    List<Deplacement> findByScenarioId(Long scenario);

    Optional<Deplacement> findByScenarioIdAndNumAction(Long scenario , int numAction);

    void deleteByEntiteId(Long entite);



    @Query("SELECT e FROM Deplacement e WHERE e.scenario = :scenarioId ORDER BY e.numAction DESC")
    List<Deplacement> deleteLast(@Param("scenarioId") Scenario scenarioId , Pageable pageable);

  //  @Transactional
  //  List<Deplacement> deleteTopByScenarioIdAndOrderByNumActionDesc(Long scenarioId, Pageable pageable);
}
