package com.projetM2_foot.repository;


import com.projetM2_foot.entity.Examen;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamenRepository extends CrudRepository<Examen, Long> {

    @Override
    List<Examen> findAll();

    Examen findByName(String name);
}
