package com.projetM2_foot.api.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExperienceRequestCreate {
    private Long scenario;
    private String typeFeedback;
    private int freqFeedback;
    private int visuFeedback;

    private Boolean displayArea;
    private int colonArea;
    private int corridorArea;

    private String modeScene;
    private String groundColor;
}