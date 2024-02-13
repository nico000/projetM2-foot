package com.projetM2_foot.api.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExamenRequestCreate {

   // List<Long> experienceSet;
    private String name;
    private String mode;
}
