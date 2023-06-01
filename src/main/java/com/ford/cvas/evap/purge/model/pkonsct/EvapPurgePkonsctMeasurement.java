package com.ford.cvas.evap.purge.model.pkonsct;


import com.ford.cvas.evap.purge.model.EvapPurgeMeasurement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;


@Entity(name = "EVAP_PURGE_PKONSCT")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvapPurgePkonsctMeasurement extends EvapPurgeMeasurement {

    @ManyToOne(cascade = {CascadeType.ALL, CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "VINHASH", referencedColumnName = "VINHASH")
    private EvapPkonsctVehicle vehicle;

    public EvapPurgePkonsctMeasurement(EvapPkonsctVehicle vehicle,
                                  Double value, Double nonNormalizedValue,
                                  Double nonNormalizedMaxLimit, Double nonNormalizedMinLimit,
                                  LocalDateTime time, Integer timeInService,
                                  Integer odometer, Integer odometerInKilometer,
                                  Integer warningStatusMis, Integer warningStatusTis,
                                  Boolean processed) {
        super(value, nonNormalizedValue, nonNormalizedMaxLimit, nonNormalizedMinLimit, time,
                timeInService, odometer, odometerInKilometer, warningStatusMis, warningStatusTis, processed);
        this.vehicle = vehicle;
    }
}
