package com.projetM2_foot.report.response;

import lombok.*;


@Data
@Builder
public class ReportExperience {

    private Long id;

    private String typeFeedback;
    private int freqFeedback;
    private int visuFeedback;

    private Boolean displayArea;
    private int colonArea;
    private int corridorArea;

    private String modeScene;
    private String groundColor;

    private ReportScenario scenario;


}