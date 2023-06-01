package com.ford.cvas.evap.purge.common;

import lombok.Value;

import java.util.List;

@Value
public class VinData {
    String namePlate;
    int modelYear;
    String engine;
    Double fuelTanksize;
    String vin;
    List<VinSpecificData> EvapPurgeTestsStats;
}
