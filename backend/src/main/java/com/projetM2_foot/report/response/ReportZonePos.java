package com.projetM2_foot.report.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReportZonePos {

    private int zoneDepartX;
    private int zoneDepartY;
    private int zoneArriveeX;
    private int zoneArriveeY;
}
