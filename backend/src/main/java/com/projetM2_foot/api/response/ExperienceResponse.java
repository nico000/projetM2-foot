package com.projetM2_foot.api.response;

import com.projetM2_foot.entity.Scenario;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Builder
public class ExperienceResponse {
    private Long id;
    private Long scenario;
    private String typeFeedback;
    private int freqFeedback = 100;
    private int visuFeedback;
}
