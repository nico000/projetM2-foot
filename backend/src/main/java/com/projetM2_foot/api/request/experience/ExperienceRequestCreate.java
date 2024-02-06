package com.projetM2_foot.api.request.experience;


import com.projetM2_foot.entity.Scenario;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Builder
public class ExperienceRequestCreate {
    private Long scenario;
    private String typeFeedback;
    private int freqFeedback;
    private int visuFeedback;
}
