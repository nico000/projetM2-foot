package com.projetM2_foot.api.response;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultatExperienceResponse {

    private Long id;
    private Long experience;
    private Long resultatExamen;
    private int score;
    private int ordre;
}
