package com.projetM2_foot.repository;

import com.projetM2_foot.entity.Scenario;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScenarioRepository extends Neo4jRepository<Scenario,Long> {
    @Override
    List<Scenario> findAll();

}
