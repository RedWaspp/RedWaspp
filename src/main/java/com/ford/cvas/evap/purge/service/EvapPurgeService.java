package com.ford.cvas.evap.purge.service;

import com.ford.cvas.evap.purge.common.*;
import com.ford.cvas.evap.purge.exception.ApiRequestException;
import com.ford.cvas.evap.purge.model.EvapPowerpack;
import com.ford.cvas.evap.purge.model.EvapPowerpackData;
import com.ford.cvas.evap.purge.model.dto.EvapPurgeMeasurementDTO;
import com.ford.cvas.evap.purge.repository.EvapPowerPackDataRepository;
import com.ford.cvas.evap.purge.repository.EvapPowerPackRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;


@Service
@Slf4j
public class EvapPurgeService {

    private final List<EvapPurgeMeasurementService> engineTypeEvapTestMap;
    private final EvapPowerPackDataRepository evapPowerPackDataRepository;
    private final EvapPowerPackRepository evapPowerPackRepository;

    public EvapPurgeService(EvapPurgeEonvMeasurementService evapPurgeEonvMeasurementService,
                            EvapPurgeMLMeasurementService evapPurgeMLMeasurementService,
                            EvapPurgeEvlMeasurementService evapPurgeEvlMeasurementService,
                            EvapPurgeDppcvMeasurementService evapPurgeDppcvMeasurementService,
                            EvapPurgeKoblMeasurementService evapPurgeKoblMeasurementService,
                            EvapPurgeVbvpMeasurementService evapPurgeVbvpMeasurementService,
                            EvapPurgeKofsctlMeasurementService evapPurgeKofsctlMeasurementService,
                            EvapPurgeVkonsctMeasurementService evapPurgeVkonsctMeasurementService,
                            EvapPurgePkonsctMeasurementService evapPurgePkonsctMeasurementService,
                            EvapPowerPackDataRepository evapPowerPackDataRepository,
                            EvapPowerPackRepository evapPowerPackRepository) {
        this.engineTypeEvapTestMap =  List.of(evapPurgeEonvMeasurementService, evapPurgeMLMeasurementService,
                                      evapPurgeEvlMeasurementService,evapPurgeDppcvMeasurementService,
                                      evapPurgeKoblMeasurementService,evapPurgeKofsctlMeasurementService,
                                      evapPurgeVbvpMeasurementService,evapPurgeVkonsctMeasurementService,
                                      evapPurgePkonsctMeasurementService);
        this.evapPowerPackDataRepository=evapPowerPackDataRepository;
        this.evapPowerPackRepository=evapPowerPackRepository;
    }

    public Object getPowerpacks(){
          List<EvapPowerpack> data= evapPowerPackRepository.findAll();
          List<PowerpackData> pd = new ArrayList<>();
          Map<String, List<EvapPowerpack>> nameplateMap = data.stream().collect(groupingBy(EvapPowerpack::getNamePlate));
          Map<Integer, List<EvapPowerpack>> modelYearMap = data.stream().collect(groupingBy(EvapPowerpack::getModelYear));
          Map<String, List<EvapPowerpack>> engineMap = data.stream().collect(groupingBy(EvapPowerpack::getEngine));

          Map<String, List<EvapPowerpack>> namePlateModelYearEngineMap =
              data.stream().collect(groupingBy(p -> p.getNamePlate() + " " +p.getModelYear()+ " "+ p.getEngine()));

          for(String namePlate: nameplateMap.keySet()) {
                  List<ModelYear> models = new ArrayList<>();
                for(Integer modelYear: modelYearMap.keySet()) {
                  List<Engine> engines = new ArrayList<>();
                for(String engine: engineMap.keySet()) {
                  List<FuelTankSize> fs = new ArrayList<>();
            List <EvapPowerpack> eps = namePlateModelYearEngineMap.get(namePlate + " " + modelYear + " " + engine);
            if(eps != null) {
              for (EvapPowerpack ep : namePlateModelYearEngineMap.get(namePlate + " " + modelYear + " " + engine)) {
                fs.add(new FuelTankSize(ep.fuelTank.fuelTankSize));
              }
              engines.add(new Engine(engine, fs));
            }
          }
          if(!engines.isEmpty())
          models.add(new ModelYear(modelYear, engines));
        }
        if(!models.isEmpty())
         pd.add(new PowerpackData(namePlate, models));
      }
          return Map.of("namePlates", pd);
    }


    public Object getDataForVin(String selectedVin, List<String> testNames) {
        if ((testNames == null || testNames.isEmpty())) {
            throw new ApiRequestException(AppConstants.NO_TESTS_ERROR_MESSAGE_VIN_SPECIFIC);
        }
        LocalDateTime timeFilter = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT).minusDays(90);
        ArrayList<VinSpecificData> data = new ArrayList<>();
        VinData powerpackData = null;

        for (String testName : testNames) {
            EvapPurgeMeasurementService measurementService = engineTypeEvapTestMap.stream().filter(
                evapPurgeMeasurementService -> evapPurgeMeasurementService.isFetchMeasurementsForEvapTest(testName)
            ).findFirst().orElseThrow(() -> new ApiRequestException(AppConstants.INVALID_TEST_NAMES_MESSAGE));
            List<EvapPurgeMeasurementDTO> testMeasurements = (measurementService.getVehicleAllMeasurements(selectedVin, timeFilter));
         Map<String, BinsDataInformation[]> measurementsGroupedByBins;
            VinSpecificData vinSpecificData = new VinSpecificData(testName,
              null,null,null,null,
              null,null,null,null);
            if (!testMeasurements.isEmpty()) {
                powerpackData = getPowerpackData(testMeasurements, selectedVin, data);

                Counts counts = measurementService.getCounts(testMeasurements);
                WarningThresholdData warningData = measurementService.getWarningThresholdData(testMeasurements);
                measurementsGroupedByBins =groupMeasurementsByBins(testMeasurements, warningData);
                 vinSpecificData = new VinSpecificData(
                    testName,
                    counts.getHealthyReadingsCount(),
                    counts.getAnomalousReadingsCount(),
                    warningData.getMaxWarningThreshold(),
                    warningData.getMinWarningThreshold(),
                    Math.ceil(warningData.getAverageWarningThreshold()),
                    measurementService.getFailureThreshold(),
                     warningData.getAnamaloyTrendIndicator(),
                     new Map[]{measurementsGroupedByBins}
                 );
            }
            data.add(vinSpecificData);
        }

           if (powerpackData != null) {
                  return powerpackData;
           } else {
                  Map<String, Object> map =new HashMap<>();
                  map.put("messages", new String[]{AppConstants.NO_OUTPUT_MESSAGE});
                  return map;
           }
    }


    protected VinData getPowerpackData(List<EvapPurgeMeasurementDTO> measurements, String vin, List< VinSpecificData> testsData){
      EvapPowerpackData pd = evapPowerPackDataRepository.findFirstById((int)(long)measurements.get(0).getPowerpackDataId());
        return new VinData(pd.getPowerpack().namePlate, pd.getPowerpack().modelYear, pd.getPowerpack().engine,
          pd.getPowerpack().fuelTank.fuelTankSize, vin, testsData);
    }


    protected  Map<String, BinsDataInformation[] >groupMeasurementsByBins(List<EvapPurgeMeasurementDTO> allMeasurements, WarningThresholdData warningData) {
      Map<PowerpackDataBin, List<EvapPurgeMeasurementDTO>> binGroups = allMeasurements.stream().collect(groupingBy(measurement -> {
        for(PowerpackDataBin bin: warningData.getBinsData()) {
          if(measurement.getOdometer() > bin.getLowerBound() && measurement.getOdometer() <= bin.getUpperBound())
            return bin;
        }
        return new DefaultPowerpackDataBin();
      }));

      Map<String, BinsDataInformation[] > binMap = new LinkedHashMap<>();
          warningData.getBinsData()
                .forEach(data ->  {
                  List<EvapPurgeMeasurementDTO> measurements = binGroups.get(data);
                  List<DataPoint> readings = null;
                  if(measurements!=null) readings= binGroups.get(data).stream().map(m ->
                       new DataPoint(m.getOdometer(), m.getValue(), m.getTime(), m.getNonNormalizedValue(),
                        m.getWarningStatus() == 2)
                        ).collect(Collectors.toList());
                  if(readings!=null) {
                    binMap.put("bin: " + data.getLowerBound() + "-" + data.getUpperBound(), new BinsDataInformation[]{
                        new BinsDataInformation(
                            data.getLowerBound(),
                            data.getUpperBound(),
                            data.getCount(),
                            data.getMean(),
                            data.getStandardDeviation(),
                            data.getWarningThreshold(),
                            readings)});
                  }
                }
                   );
          return binMap;

    }
}

