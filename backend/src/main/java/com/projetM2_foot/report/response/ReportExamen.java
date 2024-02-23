package com.projetM2_foot.report.response;


import lombok.*;
import java.util.List;

@Data
@Builder
public class ReportExamen {

    private Long id;
    private String name;

    private String sequencage;
    private String complexite;
    private String contenuFeedback;

    //Set<Experience> experienceSet;
    private List<ReportExperience> experiences;

}