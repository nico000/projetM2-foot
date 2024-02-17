package com.projetM2_foot.api.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeplacementRequestCreate {

    private Long scenario;
    private Long entite;
    private int numAction;

    private float startPosX;
    private float startPosY;
    private float endPosX;
    private float endPosY;

}
