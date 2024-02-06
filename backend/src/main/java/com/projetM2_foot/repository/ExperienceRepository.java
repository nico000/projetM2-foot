package com.projetM2_foot.repository;

import com.projetM2_foot.entity.Deplacement;
import com.projetM2_foot.entity.Experience;
import com.projetM2_foot.entity.Scenario;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ExperienceRepository extends CrudRepository<Experience, Long> {


}
