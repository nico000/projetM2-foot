package com.projetM2_foot.api.response;

import com.projetM2_foot.entity.Experience;
import com.projetM2_foot.entity.ResultatExamen;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ResultatExperienceResponse {

    private Long id;
    private Long experience;
    private Long resultatExamen;
    private int score;
}
