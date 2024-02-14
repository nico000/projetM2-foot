package com.projetM2_foot.api.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExamenRequestCreate {

   // List<Long> experienceSet;
    private String nom;
    private String mode;
}
