package com.projetM2_foot.api.request.experience;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExperienceRequestCreate {
    private Long scenario;
    private String typeFeedback;
    private int freqFeedback;
    private int visuFeedback;
}
