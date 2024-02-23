package com.projetM2_foot.report.response;

import com.projetM2_foot.entity.Scenario;
import lombok.*;

import javax.persistence.*;


@Data
@Builder
public class ReportExperience {

    private Long id;
    private ReportScenario scenario;
    private String typeFeedback;
    private int freqFeedback;
    private int visuFeedback;

    private Boolean displayArea;
    private int colonArea;
    private int corridorArea;

    private String modeScene;
    private String groundColor;


}