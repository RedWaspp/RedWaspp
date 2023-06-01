package com.ford.cvas.evap.purge.service;


import com.ford.cvas.evap.purge.common.Counts;
import com.ford.cvas.evap.purge.common.WarningThresholdData;
import com.ford.cvas.evap.purge.model.dto.EvapPurgeMeasurementDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Slf4j
public abstract class EvapPurgeMeasurementService {



    public abstract List<EvapPurgeMeasurementDTO> getVehicleAllMeasurements(String selectedVin, LocalDateTime timeFilter);

    public abstract String getTestName();

    public abstract WarningThresholdData getWarningThresholdData(List<EvapPurgeMeasurementDTO> measurements);

    public abstract Counts getCounts(List<EvapPurgeMeasurementDTO> measurements);

    public boolean isFetchMeasurementsForEvapTest(String testName) {
        return this.getTestName().equals(testName);
    }

    public abstract double getFailureThreshold();


}
