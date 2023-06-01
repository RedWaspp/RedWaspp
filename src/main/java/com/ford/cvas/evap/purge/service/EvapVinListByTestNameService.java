package com.ford.cvas.evap.purge.service;

import com.ford.cvas.evap.purge.common.AppConstants;
import com.ford.cvas.evap.purge.exception.ApiRequestException;
import com.ford.cvas.evap.purge.model.EvapPowerpack;
import com.ford.cvas.evap.purge.model.FuelTank;
import com.ford.cvas.evap.purge.repository.EvapEonvPPDRepo;
import com.ford.cvas.evap.purge.repository.EvapEonvVehicleRepo;
import com.ford.cvas.evap.purge.repository.EvapFuelTankRepository;
import com.ford.cvas.evap.purge.repository.EvapPowerPackRepository;
import com.ford.cvas.evap.purge.repository.EvapVehicleDbRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class EvapVinListByTestNameService {
    @Autowired
    EvapEonvVehicleRepo eonvVehicleRepository;
    @Autowired
    EvapEonvPPDRepo eonvRepository;
    @Autowired
    EvapPowerPackRepository powerPackRepository;


    @Autowired
    EvapFuelTankRepository evapFuelTankRepo;
    @Autowired
    EvapVehicleDbRepository dbRepo;


    public List<String> getVinsList(String testName, String nameplate,
                                    String modelYear, String engine, Double fuelTankSize) {
        StringBuilder ppdIds = new StringBuilder();

        //Test + powerpack (MY name engine) +  fueltank
        if (testName != null && (nameplate != null && !nameplate.equals("")) && (modelYear != null && !modelYear.equals(""))
                && (engine != null && !engine.equals("")) && fuelTankSize != null) {
            if (!Arrays.asList(AppConstants.AVAILABLE_TEST_NAMES).contains(testName)) {
                throw new ApiRequestException(AppConstants.INVALID_TEST_NAMES_MESSAGE);
            }
            FuelTank evapFuelTank = evapFuelTankRepo.getFuelData(fuelTankSize);
            List<EvapPowerpack> evapPowerpacksList = powerPackRepository.getDataByConditionWithFuelTankSize(nameplate, Integer.parseInt(modelYear), engine, evapFuelTank.getId());
            if (evapPowerpacksList.isEmpty()) {
                return null;
            }
            for (EvapPowerpack mymodel : evapPowerpacksList) {
                ppdIds = ppdIds.append(",").append(mymodel.getId());
            }
            if (ppdIds.charAt(0) == ',') {
                ppdIds.deleteCharAt(0);
            }

        }
        return dbRepo.getVehiclesListByTestNameAndPowerPackIds(testName, ppdIds);
    }
}




