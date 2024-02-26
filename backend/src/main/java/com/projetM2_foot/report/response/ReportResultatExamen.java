package com.projetM2_foot.report.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class ReportResultatExamen {

    private Long id;

    private ReportExamen examen;
    private int score;
    private Date createDate;

    private ReportPerson personalInformation;

    private List<ReportResultatExperience> resultatExperiences;






}
