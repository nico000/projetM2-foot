package com.projetM2_foot.service;

import com.projetM2_foot.dto.request.ScenarioRequestCreate;
import com.projetM2_foot.entity.Scenario;
import com.projetM2_foot.mapper.ScenarioMapper;
import com.projetM2_foot.repository.ScenarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScenarioService {

    private final ScenarioMapper scenarioMapper;
    private final ScenarioRepository scenarioRepository;

    public Scenario create (ScenarioRequestCreate scenarioRequestCreate){
        final Scenario scenario = scenarioMapper.ToEntity(scenarioRequestCreate);
        return scenarioRepository.save(scenario);
    }

}
