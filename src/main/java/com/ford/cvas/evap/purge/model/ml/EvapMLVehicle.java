package com.ford.cvas.evap.purge.model.ml;


import com.ford.cvas.evap.purge.common.EvapVehicle;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;


@Entity(name = "EVAP_ML_VEHICLE")
@NoArgsConstructor
@AllArgsConstructor
public class EvapMLVehicle extends EvapVehicle {

    @ManyToOne
    @JoinColumn(name = "POWERPACK_DATA_ID")
    private EvapMLPowerpackData powerpackData;

    public EvapMLVehicle( String vinhash, String vin, LocalDate productionDate,
                            LocalDate warrantyStartDate, String fuelTypeCategory, String orderTypeCategory,
                            EvapMLPowerpackData powerpackData) {
        super( vinhash, vin, productionDate, warrantyStartDate, fuelTypeCategory, orderTypeCategory);
        this.powerpackData = powerpackData;
    }
}
