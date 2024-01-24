package com.projetM2_foot.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ScenarioResponseAll {
    private List<ScenarioResponse> scenarioResponseList;
}
