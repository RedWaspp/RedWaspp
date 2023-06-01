package com.ford.cvas.evap.purge.model.dto;


import com.ford.cvas.evap.purge.common.MeasurementDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@NoArgsConstructor
@Data
public class EvapPurgeMeasurementDTO extends MeasurementDTO {


    private String units;


    private String testName;
    private Integer  powerpackDataId;

    public EvapPurgeMeasurementDTO(
            Double value, LocalDateTime time, Integer odometer, Double nonNormalizedValue, Double nonNormalizedMaxLimit,
            Double nonNormalizedMinLimit, Integer timeInService, String vinhash, LocalDate productionDate,
            LocalDate warrantyStartDate, String fuelTypeCategory, String orderTypeCategory,
            String vin,Integer warningStatus, String units, String testName, Integer powerpackDataId
    ) {
        super(value, time, odometer, nonNormalizedValue, nonNormalizedMaxLimit, nonNormalizedMinLimit,
            timeInService, vinhash, productionDate, warrantyStartDate, fuelTypeCategory, orderTypeCategory, vin,warningStatus);
        this.units=units;
        this.testName=testName;
        this.powerpackDataId=powerpackDataId;
    }
}
