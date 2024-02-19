package com.projetM2_foot.api.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultatDeplacementRequestCreate {

    private Long entite;
    private int numAction;

    private float startPosX;
    private float startPosY;
    private float endPosX;
    private float endPosY;

    @Override
    public String toString() {
        return "\n\tDep{" +
                "  entite=" + entite +
                ", numAction=" + numAction +
                ", startPosX=" + startPosX +
                ", startPosY=" + startPosY +
                ", endPosX=" + endPosX +
                ", endPosY=" + endPosY +
                "}";
    }
}
