package com.projetM2_foot.repository;


import com.projetM2_foot.entity.Experience;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExperienceRepository extends CrudRepository<Experience, Long> {


}
