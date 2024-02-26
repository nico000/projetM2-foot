package com.projetM2_foot.api.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResNomPrenomResponse {
    private String nom;
    private String prenom;
}
