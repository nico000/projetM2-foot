package com.projetM2_foot.report.response;

import com.projetM2_foot.entity.Entite;
import lombok.Builder;
import lombok.Data;

import java.util.List;

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
