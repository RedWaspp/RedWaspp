package com.ford.cvas.evap.purge.service;


import com.ford.cvas.evap.purge.common.*;
import com.ford.cvas.evap.purge.model.dto.EvapPurgeMeasurementDTO;
import com.ford.cvas.evap.purge.model.dto.WarningThresholdDataDto;
import com.ford.cvas.evap.purge.repository.evl.EvapEvlPowerpackDataBinRepository;
import com.ford.cvas.evap.purge.repository.evl.EvapPurgeEvlMeasurementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Service
@Slf4j
public class EvapPurgeEvlMeasurementService extends EvapPurgeMeasurementService  {
  private static final double FAILURE_THRESHOLD = 1.0;
  private static final String TEST_NAME = "EVL";
  private static final String ANAMALOY_TREND_INDICATOR="Up";
  private final EvapPurgeEvlMeasurementRepository evapPurgeEvlMeasurementRepository;
  private final EvapEvlPowerpackDataBinRepository evapEvlPowerpackDataBinRepository;

  public EvapPurgeEvlMeasurementService(EvapPurgeEvlMeasurementRepository evapPurgeEvlMeasurementRepository,
                                        EvapEvlPowerpackDataBinRepository evapEvlPowerpackDataBinRepository) {
    this.evapPurgeEvlMeasurementRepository = evapPurgeEvlMeasurementRepository;
    this.evapEvlPowerpackDataBinRepository = evapEvlPowerpackDataBinRepository;
  }

  @Override
  public List<EvapPurgeMeasurementDTO> getVehicleAllMeasurements(String selectedVin, LocalDateTime timeFilter) {
    LocalDateTime beforeTime = LocalDateTime.now();
    List<EvapPurgeMeasurementDTO> evapPurgeMeasurements = evapPurgeEvlMeasurementRepository.getVehicleAllMeasurements(selectedVin, timeFilter);
    log.info("EvapPurgeEVL Database Query Time In Milliseconds - Evap Measurement (Miles in Service)- " + beforeTime.until(LocalDateTime.now(), ChronoUnit.MILLIS));
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
          evapEvlPowerpackDataBinRepository.getWarningThresholdStats((int) (long)measurements.get(0).getPowerpackDataId(), IndependentVariable.MILES_IN_SERVICE);

      List<PowerpackDataBin> data=evapEvlPowerpackDataBinRepository.findEvapEvlPowerpackDataBinsByPowerpackDataIdAndIndependentVariable((int) (long)measurements.get(0).getPowerpackDataId(), IndependentVariable.MILES_IN_SERVICE);
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
