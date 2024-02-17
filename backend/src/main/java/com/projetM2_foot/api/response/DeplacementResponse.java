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

    private float startPosX;
    private float startPosY;
    private float endPosX;
    private float endPosY;

}
