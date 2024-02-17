package com.projetM2_foot.repository;

import com.projetM2_foot.entity.Scenario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScenarioRepository extends CrudRepository<Scenario , Long> {

    @Override
    List<Scenario> findAll();

    Optional<Scenario> findByName(String name);
}
