package com.projetM2_foot.api.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EntiteRequestUpdate {

    private Long id;
    private Long scenario;
    private int type;
    private int numero;
    private int x;
    private int y;

}
