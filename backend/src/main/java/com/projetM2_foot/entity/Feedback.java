package com.projetM2_foot.entity;


//import com.projetM2_foot.constant.TypeFeedback;
import lombok.*;
import org.neo4j.ogm.annotation.NodeEntity;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NodeEntity
public class Feedback {
    private int frequence;
    /** Nombre de visionnage (kr ,krr) */
    private int condition;
   // private TypeFeedback type;
}
