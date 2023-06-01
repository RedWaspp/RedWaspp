package com.ford.cvas.evap.purge.common;

import lombok.Value;


@Value
public class Counts {
  Integer healthyReadingsCount;
  Integer anomalousReadingsCount;
}
