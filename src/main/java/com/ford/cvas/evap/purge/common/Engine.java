package com.ford.cvas.evap.purge.common;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;


@Value
@AllArgsConstructor
public class Engine {
  String Engine;
  List<FuelTankSize> fuelTankSize;
}
