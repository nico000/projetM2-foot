package com.projetM2_foot.repository;


import com.projetM2_foot.entity.Entite;
import com.projetM2_foot.entity.Scenario;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntiteRepository extends CrudRepository<Entite, Long> {

    List<Entite> findByScenarioIdOrderByNumero(Long scenario);

    void deleteByScenarioId(Long scenario);


    @Query("SELECT e FROM Entite e WHERE e.scenario = :scenarioId ORDER BY e.createDate DESC")
    List<Entite> deleteLatest(@Param("scenarioId") Scenario scenarioId, Pageable pageable);
}
