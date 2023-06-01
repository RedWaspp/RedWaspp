package com.ford.cvas.evap.purge.service;


import com.ford.cvas.evap.purge.common.*;
import com.ford.cvas.evap.purge.model.dto.EvapPurgeMeasurementDTO;
import com.ford.cvas.evap.purge.model.dto.WarningThresholdDataDto;
import com.ford.cvas.evap.purge.repository.ml.EvapMLPowerpackDataBinRepository;
import com.ford.cvas.evap.purge.repository.ml.EvapPurgeMLMeasurementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Service
@Slf4j
public class EvapPurgeMLMeasurementService extends EvapPurgeMeasurementService  {
  private static final double FAILURE_THRESHOLD = 0.7;
  private static final String TEST_NAME = "ML";
  private static final String ANAMALOY_TREND_INDICATOR="Up";
  private final EvapPurgeMLMeasurementRepository evapPurgeMLMeasurementRepository;
  private final EvapMLPowerpackDataBinRepository evapMLPowerpackDataBinRepository;

  public EvapPurgeMLMeasurementService(EvapPurgeMLMeasurementRepository evapPurgeMLMeasurementRepository,
                                       EvapMLPowerpackDataBinRepository evapMLPowerpackDataBinRepository) {
    this.evapPurgeMLMeasurementRepository = evapPurgeMLMeasurementRepository;
    this.evapMLPowerpackDataBinRepository = evapMLPowerpackDataBinRepository;
  }

  @Override
  public List<EvapPurgeMeasurementDTO> getVehicleAllMeasurements(String selectedVin, LocalDateTime timeFilter) {
    LocalDateTime beforeTime = LocalDateTime.now();
    List<EvapPurgeMeasurementDTO> evapPurgeMeasurements = evapPurgeMLMeasurementRepository.getVehicleAllMeasurements(selectedVin, timeFilter);
    log.info("EvapPurgeML Database Query Time In Milliseconds - Evap Measurement (Miles in Service)- " + beforeTime.until(LocalDateTime.now(), ChronoUnit.MILLIS));
    return evapPurgeMeasurements;
  }

  @Override
  public double getFailureThreshold() {
    return FAILURE_THRESHOLD;
  }


  @Override
  public String getTestName() {
    return TEST_NAME;
  }

  @Override
  public WarningThresholdData getWarningThresholdData(List<EvapPurgeMeasurementDTO> measurements) {
    WarningThresholdData warningThreshold = null;
    if(!measurements.isEmpty()) {
      WarningThresholdDataDto vinBins =
          evapMLPowerpackDataBinRepository.getWarningThresholdStats((int) (long)measurements.get(0).getPowerpackDataId(), IndependentVariable.MILES_IN_SERVICE);

      List<PowerpackDataBin> data=evapMLPowerpackDataBinRepository.findEvapMlPowerpackDataBinsByPowerpackDataIdAndIndependentVariable((int) (long)measurements.get(0).getPowerpackDataId(), IndependentVariable.MILES_IN_SERVICE);
      warningThreshold = new WarningThresholdData(vinBins.getMaxWarningThreshold(), vinBins.getMinWarningThreshold(),vinBins.getAverageWarningThreshold(),ANAMALOY_TREND_INDICATOR, data );
    }
    return warningThreshold;
  }

  @Override
  public Counts getCounts(List<EvapPurgeMeasurementDTO> measurements) {
    Integer earlywarningReadingsCount=
        Long.valueOf(measurements.stream().
            filter(m -> m.getWarningStatus() == WarningLevel.EARLY_WARNING.getWarningLevel()).count()).intValue();
     return new Counts(measurements.size()-earlywarningReadingsCount, earlywarningReadingsCount);
  }

}
