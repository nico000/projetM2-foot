package com.projetM2_foot.api.request;

import lombok.*;

@Data
@Builder
public class ScenarioRequestCreate {

    private String name;

    private Boolean threeDim;
    private String modeScene;
    private String groundColor;
    private int groundWidth;

    private int playerNb;
    private int playerIcon;

    private Boolean displayArea;
    private int colonArea;
    private int corridorArea;
}
