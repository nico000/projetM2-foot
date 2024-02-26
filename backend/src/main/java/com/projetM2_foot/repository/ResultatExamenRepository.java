package com.projetM2_foot.repository;

import com.projetM2_foot.entity.ResultatExamen;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultatExamenRepository extends CrudRepository<ResultatExamen, Long> {

    @Override
    List<ResultatExamen> findAll();

    List<ResultatExamen> findByNomPersonAndPrenomPersonOrderByCreateDate(String nomPerson , String prenomPerson);

}
