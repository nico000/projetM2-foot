package com.projetM2_foot.report.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ReportEntite {

    private Long id;
    private int type;
    private int numero;
    private float initialPosX;
    private float initialPosY;
    private Date createDate;
}
