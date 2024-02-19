package com.projetM2_foot.api.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExperienceResponse {
    private Long id;
    private Long scenario;
    private String typeFeedback;
    private int freqFeedback;
    private int visuFeedback;


    private Boolean zone_display;
    private int zone_nb_zone;
    private int zone_nb_couloir;

    private String mode_scene;
    private String terrain_couleur;
}
