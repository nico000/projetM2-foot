package com.projetM2_foot.repository;

import com.projetM2_foot.entity.Deplacement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DeplacementRepository extends CrudRepository<Deplacement, Long> {

}
