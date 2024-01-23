package com.projetM2_foot.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScenarioResponse {
    private long id;
    private String nom;
}
