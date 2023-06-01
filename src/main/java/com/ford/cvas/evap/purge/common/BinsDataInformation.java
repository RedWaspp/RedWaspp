package com.ford.cvas.evap.purge.common;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@AllArgsConstructor
public class BinsDataInformation {
  Integer lowerRange;
  Integer upperRange;
  Integer count;
  Double mean;
  Double stdDev;
  Double warningThreshold;
  List<DataPoint> readings;
}
