package com.ford.cvas.evap.purge.response;

import lombok.Builder;
import lombok.Data;

@Builder(toBuilder = true)
@Data
public class Statistics {
    Integer anamolousVinsCount;
    Integer processedVinsCount;
    Integer totalNoofReadingsCount;
    Integer totalNoOfAnomolousReadingsCount;
    Double failureThreshold;
    Double minimumThreshold;
    Double maximumThreshold;
    Double averageThreshold;
    String anamaloyTrendIndicator;
}
