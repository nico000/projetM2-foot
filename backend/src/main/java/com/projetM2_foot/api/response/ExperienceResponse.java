package com.projetM2_foot.api.response;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;

@Data
@Builder
public class ExperienceResponse {
    private Long id;
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
