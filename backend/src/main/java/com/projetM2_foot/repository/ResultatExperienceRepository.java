package com.projetM2_foot.repository;

import com.projetM2_foot.entity.ResultatExperience;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultatExperienceRepository extends CrudRepository<ResultatExperience, Long> {

    @Override
    List<ResultatExperience> findAll();

    List<ResultatExperience> findByResultatExamenId(Long resultatExamenId);

}
