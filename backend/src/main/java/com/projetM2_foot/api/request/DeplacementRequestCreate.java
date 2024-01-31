package com.projetM2_foot.api.request;

import com.projetM2_foot.entity.Entite;
import com.projetM2_foot.entity.Scenario;
import lombok.Builder;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class DeplacementRequestCreate {

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
