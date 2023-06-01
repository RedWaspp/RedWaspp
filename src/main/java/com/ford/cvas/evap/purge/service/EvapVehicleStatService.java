package com.ford.cvas.evap.purge.service;

import com.ford.cvas.evap.purge.common.AppConstants;
import com.ford.cvas.evap.purge.exception.ApiRequestException;
import com.ford.cvas.evap.purge.model.EvapPowerpack;
import com.ford.cvas.evap.purge.model.EvapPowerpackData;
import com.ford.cvas.evap.purge.model.FuelTank;
import com.ford.cvas.evap.purge.repository.EvapFuelTankRepository;
import com.ford.cvas.evap.purge.repository.EvapPowerPackDataRepository;
import com.ford.cvas.evap.purge.repository.EvapPowerPackRepository;
import com.ford.cvas.evap.purge.repository.EvapVehicleDbRepository;
import com.ford.cvas.evap.purge.response.Statistics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

@Service
@Slf4j
public class EvapVehicleStatService {

    EvapVehicleDbRepository vehicledbRepo;
    EvapFuelTankRepository evapFuelTankRepository;
    EvapPowerPackRepository powerPackRepository;
    EvapPowerPackDataRepository powerPackDataRepository;

    EvapVehicleStatService(EvapVehicleDbRepository evapVehicleDbRepository,
                           EvapFuelTankRepository evapFuelTankRepository,
                           EvapPowerPackRepository powerPackRepository,
                           EvapPowerPackDataRepository powerPackDataRepository) {
        this.vehicledbRepo = evapVehicleDbRepository;
        this.evapFuelTankRepository = evapFuelTankRepository;
        this.powerPackRepository = powerPackRepository;
        this.powerPackDataRepository = powerPackDataRepository;
    }

    String noOfDays1 = "90";

    public Object getVehicleStats(List<String> testNames, String nameplate, String modelYear, String engine, Double fuelTankSize, Integer noOfDays) {

        int emptyTestCount = 0;
        //90 days default from properties file for now.
        int days = Integer.parseInt(noOfDays1);
        List outputResponseList = new ArrayList<>();
        LinkedHashMap<String, Statistics> orderedStatisticsMap = new LinkedHashMap<>();


        if (testNames == null || testNames.isEmpty() || modelYear == null || nameplate == null || engine == null || fuelTankSize == null || noOfDays == null) {
            throw new ApiRequestException(AppConstants.NO_TESTS_ERROR_MESSAGE);
        }
        for (String eachTest : testNames) {
            if (!Arrays.asList(AppConstants.AVAILABLE_TEST_NAMES).contains(eachTest)) {
                throw new ApiRequestException(AppConstants.INVALID_TEST_NAMES_MESSAGE);
            } else {
                Statistics statistics = getVehiclesCount(eachTest, nameplate, modelYear, engine, fuelTankSize, days);
                orderedStatisticsMap.put("evapPurgeTest: " + eachTest, statistics);
                if (statistics.getProcessedVinsCount() == null) {
                    emptyTestCount++;
                }
            }
        }
        if (emptyTestCount == testNames.size()) {
            Map<String, Object> map = new HashMap<>();
            map.put("messages", new String[]{AppConstants.NO_OUTPUT_MESSAGE});
            return map;
        }

        outputResponseList.add("nameplate:" + nameplate);
        outputResponseList.add("modelYear:" + modelYear);
        outputResponseList.add("engine:" + engine);
        outputResponseList.add("fuelTankSize:" + fuelTankSize);
        outputResponseList.add("noOfDays:" + noOfDays1);
        outputResponseList.add("evapPurgeTestStats:");
        outputResponseList.add(orderedStatisticsMap);
        return outputResponseList;
    }


    public Statistics getVehiclesCount
            (String testName, String nameplate, String modelYear, String engine, Double fuelTankSize, int noOfDays) {
        StringBuilder ppdIds = new StringBuilder();

        // nameplate + modelYear, Engine, FuelTank ==> PowerPackId
        FuelTank evapFuelTank = evapFuelTankRepository.getFuelData(fuelTankSize);
        List<EvapPowerpack> ppdList = powerPackRepository.getDataByConditionWithFuelTankSize(nameplate, Integer.parseInt(modelYear), engine, evapFuelTank.getId());
        if (ppdList.isEmpty()) {
            return null;
        }

        for (EvapPowerpack mymodel : ppdList) {
            EvapPowerpackData powerpackData = powerPackDataRepository.findEvapPowerpackDataByPowerpackId(mymodel.getId());
            ppdIds = ppdIds.append(",").append(powerpackData.getId());
        }
        if(ppdIds.charAt(0)==','){ppdIds.deleteCharAt(0); }
        return vehicledbRepo.getVehicleCounts(ppdIds, testName, noOfDays);
    }
}
