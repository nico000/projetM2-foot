package com.projetM2_foot.api.request;

import com.projetM2_foot.entity.Examen;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
public class ResultatExamenRequestCreate {

    private Long examen;
    private String nom_person;
    private String prenom_person;
}
