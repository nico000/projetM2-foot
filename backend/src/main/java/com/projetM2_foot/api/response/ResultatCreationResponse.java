package com.projetM2_foot.api.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResultatCreationResponse {

    private Long id;
    private List<Long> resultat_experience;
}
