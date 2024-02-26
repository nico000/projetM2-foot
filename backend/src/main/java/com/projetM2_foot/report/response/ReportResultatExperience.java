package com.projetM2_foot.report.response;


import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class ReportResultatExperience {


    private Long id;
    private int score;
    private ReportExperience experience;

    private List<ReportEssai> essais;
    private Date createDate;

}