package com.projetM2_foot.repository;

import com.projetM2_foot.entity.Scenario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScenarioRepository extends CrudRepository<Scenario , Long> {

}
