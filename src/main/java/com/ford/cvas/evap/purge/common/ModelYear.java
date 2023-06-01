package com.ford.cvas.evap.purge.common;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;


@Value
@AllArgsConstructor
public class ModelYear {
  Integer modelyear;
  List<Engine> engines;
}
