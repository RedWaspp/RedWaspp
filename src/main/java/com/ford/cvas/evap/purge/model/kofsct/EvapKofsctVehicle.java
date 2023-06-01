package com.ford.cvas.evap.purge.model.kofsct;



import com.ford.cvas.evap.purge.common.EvapVehicle;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;


@Entity(name = "EVAP_KOFSCT_VEHICLE")
public class EvapKofsctVehicle extends EvapVehicle {

    @ManyToOne
    @JoinColumn(name = "POWERPACK_DATA_ID")
    private EvapKofsctPowerpackData powerpackData;



    public EvapKofsctVehicle(String vinhash, String vin, LocalDate productionDate,
                             LocalDate warrantyStartDate, String fuelTypeCategory, String orderTypeCategory,
                             EvapKofsctPowerpackData powerpackData) {
        super( vinhash, vin, productionDate, warrantyStartDate, fuelTypeCategory, orderTypeCategory);
        this.powerpackData = powerpackData;
    }

}
