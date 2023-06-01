package com.ford.cvas.evap.purge.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.MappedSuperclass;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class MeasurementDTO implements TimedEvent {

    protected Double value;

    protected LocalDateTime time;

    protected Integer odometer;

    protected Double nonNormalizedValue;

    protected Double nonNormalizedMaxLimit;

    protected Double nonNormalizedMinLimit;

    protected Integer timeInService;

    protected String vinhash;

    private LocalDate productionDate;
    private LocalDate warrantyStartDate;

    private String fuelTypeCategory;
    private String orderTypeCategory;

    private String vin;

    private Integer warningStatus;


}
