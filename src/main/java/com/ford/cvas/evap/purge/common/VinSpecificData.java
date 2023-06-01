package com.ford.cvas.evap.purge.common;

import lombok.Value;


@Value
public class VinSpecificData {
  String evapPurgeTest;
  Integer healthyReadingsCount;
  Integer anomalousReadingsCount;
  Double maxWarningThreshold;
  Double minWarningThreshold;
  Double averageWarningThreshold;
  Double failureThreshold;
  String anamaloyTrendIndicator;
  Object[] bins;
}
