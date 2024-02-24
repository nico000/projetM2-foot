package com.projetM2_foot.report.response;


import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class ReportEssai {


    private Long id;
    private boolean reussi;
    private int score;
    private Long temps;
    private int numEssai;
    List<ReportResultatDeplacement> deplacementsRealiser;

}
