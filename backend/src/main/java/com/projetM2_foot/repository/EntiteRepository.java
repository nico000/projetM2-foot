package com.projetM2_foot.repository;

import com.projetM2_foot.entity.Entite;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntiteRepository extends CrudRepository<Entite, Long> {

}
