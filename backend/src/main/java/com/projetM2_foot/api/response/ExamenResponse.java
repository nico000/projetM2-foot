package com.projetM2_foot.api.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExamenResponse {
    private Long id;

    private String name;
    private String mode;

    List<Long> experienceSet; // A changer
}
