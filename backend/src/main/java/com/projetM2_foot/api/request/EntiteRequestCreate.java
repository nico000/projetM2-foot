package com.projetM2_foot.api.request;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EntiteRequestCreate {

    private Long scenario;
    private int type;
    private int numero;
    private float x;
    private float y;

}
