package com.ford.cvas.evap.purge.model.kofsct;


import com.ford.cvas.evap.purge.model.EvapPurgeMeasurement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity(name = "EVAP_PURGE_KOFSCT")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvapPurgeKofsctMeasurement extends EvapPurgeMeasurement {

    @ManyToOne(cascade = {CascadeType.ALL, CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "VINHASH", referencedColumnName = "VINHASH")
    private EvapKofsctVehicle vehicle;

    public EvapPurgeKofsctMeasurement(EvapKofsctVehicle vehicle,
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
