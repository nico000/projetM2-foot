package com.projetM2_foot.service;

import com.projetM2_foot.dto.request.ScenarioRequestCreate;
import com.projetM2_foot.entity.Scenario;
import com.projetM2_foot.mapper.ScenarioMapper;
import com.projetM2_foot.repository.ScenarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScenarioService {

    private final ScenarioMapper scenarioMapper;
    private final ScenarioRepository scenarioRepository;

    public Scenario create (ScenarioRequestCreate scenarioRequestCreate){
        final Scenario scenario = scenarioMapper.ToEntity(scenarioRequestCreate);

        return scenarioRepository.save(scenario);
    }

    public List<Scenario> getAllScenario(){
        return  scenarioRepository.findAll();
    }

}
