package com.projetM2_foot.repository;

import com.projetM2_foot.entity.Deplacement;
import com.projetM2_foot.entity.Scenario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeplacementRepository extends CrudRepository<Deplacement, Long> {

}
