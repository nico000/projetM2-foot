package com.projetM2_foot.report.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReportResultatDeplacement {


    private Long id;
    private int action;
    private Boolean reussi;

    private ReportEntite entite;
    private float startPosX;
    private float startPosY;
    private float endPosX;
    private float endPosY;


    private ReportZonePos essaiPos;

    private ReportZonePos scenarioPos;

}
