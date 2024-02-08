package com.projetM2_foot.repository;


import com.projetM2_foot.entity.Examen;
import com.projetM2_foot.entity.Scenario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamenRepository extends CrudRepository<Examen, Long> {


    @Override
    List<Examen> findAll();
}
