package com.ford.cvas.evap.purge.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;



@Data
@AllArgsConstructor
public class WarningThresholdDataDto {
    double maxWarningThreshold;
    double minWarningThreshold;
    double averageWarningThreshold;
}
