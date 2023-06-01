package com.ford.cvas.evap.purge.common;

import lombok.Value;

import java.util.List;

@Value
public class WarningThresholdData {
    double maxWarningThreshold;
    double minWarningThreshold;
    double averageWarningThreshold;
    String anamaloyTrendIndicator;
    List<PowerpackDataBin> binsData;
}
