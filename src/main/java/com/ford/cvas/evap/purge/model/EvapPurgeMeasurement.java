package com.ford.cvas.evap.purge.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@NoArgsConstructor
public abstract class EvapPurgeMeasurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String monitor;
    private String testId;
    private Double value;
    private Double nonNormalizedValue;
    private Double nonNormalizedMaxLimit;
    private Double nonNormalizedMinLimit;
    private LocalDateTime time;
    private Integer timeInService;
    private Integer odometer;
    private Integer odometerInKilometer;
    private Integer warningStatusMis;
    private Integer warningStatusTis;
    protected Boolean processed;
    private String units;

    public EvapPurgeMeasurement(Double value, Double nonNormalizedValue, Double nonNormalizedMaxLimit,
                                Double nonNormalizedMinLimit, LocalDateTime time, Integer timeInService,
                                Integer odometer, Integer odometerInKilometer, Integer warningStatusMis,
                                Integer warningStatusTis, Boolean processed) {
        this.value = value;
        this.nonNormalizedValue = nonNormalizedValue;
        this.nonNormalizedMaxLimit = nonNormalizedMaxLimit;
        this.nonNormalizedMinLimit = nonNormalizedMinLimit;
        this.time = time;
        this.timeInService = timeInService;
        this.odometer = odometer;
        this.odometerInKilometer = odometerInKilometer;
        this.warningStatusMis = warningStatusMis;
        this.warningStatusTis = warningStatusTis;
        this.processed = processed;
    }
}
