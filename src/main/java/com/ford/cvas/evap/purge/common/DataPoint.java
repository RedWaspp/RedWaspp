package com.ford.cvas.evap.purge.common;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@AllArgsConstructor
public class DataPoint {
  Integer odometer;
  double value;
  LocalDateTime eventDateTime;
  Double normalizedValue;
  boolean AnomalousFlag;
}
