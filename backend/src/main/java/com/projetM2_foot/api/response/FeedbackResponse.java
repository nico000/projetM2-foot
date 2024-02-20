package com.projetM2_foot.api.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FeedbackResponse {


    private Long essai;
    private Boolean reussi;
    private List<ResultatDeplacementResponse> listError;
    private int score;

}
