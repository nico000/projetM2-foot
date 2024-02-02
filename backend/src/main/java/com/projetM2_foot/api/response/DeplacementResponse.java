package com.projetM2_foot.api.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeplacementResponse {

    private Long id;
    private Long scenario;
    private Long entite;

    private int numAction;
    private int numScene;
    private int numBloc;

    private int startPosX;
    private int startPosY;
    private int endPosX;
    private int endPosY;

}