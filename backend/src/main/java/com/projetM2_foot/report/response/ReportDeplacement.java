package com.projetM2_foot.report.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReportDeplacement {

    private Long id;
    private int action;

    private float posXDepart;
    private float posYDepart;
    private float posXArrive;
    private float posYArrive;
    private ReportEntite entite;

}
