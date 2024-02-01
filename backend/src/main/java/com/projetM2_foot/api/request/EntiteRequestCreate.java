package com.projetM2_foot.api.request;

import com.projetM2_foot.entity.Scenario;
import lombok.Builder;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class EntiteRequestCreate {

    private Long scenario;
    private int type;
    private int numero;
    private float x;
    private float y;

}
