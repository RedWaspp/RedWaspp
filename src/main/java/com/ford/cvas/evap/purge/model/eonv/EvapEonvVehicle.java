package com.ford.cvas.evap.purge.model.eonv;

import com.ford.cvas.evap.purge.common.EvapVehicle;
import lombok.NoArgsConstructor;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;


@Entity(name="EVAP_EONV_VEHICLE")
@NoArgsConstructor
public class EvapEonvVehicle extends EvapVehicle {

    @ManyToOne
    @JoinColumn(name = "POWERPACK_DATA_ID")
    private EvapEonvPowerpackData powerpackData;

    public EvapEonvVehicle( String vinhash, String vin, LocalDate productionDate,
                            LocalDate warrantyStartDate, String fuelTypeCategory, String orderTypeCategory,
                            EvapEonvPowerpackData powerpackData) {
        super( vinhash, vin, productionDate, warrantyStartDate, fuelTypeCategory, orderTypeCategory);
        this.powerpackData = powerpackData;
    }
}
