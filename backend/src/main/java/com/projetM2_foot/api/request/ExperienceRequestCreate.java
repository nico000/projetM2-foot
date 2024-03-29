package com.projetM2_foot.api.request;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ExperienceRequestCreate {

    private Long scenario;
    private String typeFeedback;
    private int freqFeedback;
    private int visuFeedback;

    private String modeScene;

    private String terrain_couleur;
    private Boolean zone_display;
    private int zone_nb_zone;
    private int zone_nb_couloir;

    private int ordre;

    @Override
    public String toString() {
        return "ExperienceRequestCreate{" + "scenario=" + scenario +
                ", typeFeedback='" + typeFeedback + '\'' +
                ", freqFeedback=" + freqFeedback +
                ", visuFeedback=" + visuFeedback +
                ", modeScene='" + modeScene + '\'' +
                ", terrain_couleur='" + terrain_couleur + '\'' +
                '}';
    }
}
