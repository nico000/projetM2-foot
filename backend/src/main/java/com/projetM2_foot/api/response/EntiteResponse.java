package com.projetM2_foot.api.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EntiteResponse {

    private Long id;
    private Long scenario;
    private int type;
    private int numero;
    private float x;
    private float y;
}
