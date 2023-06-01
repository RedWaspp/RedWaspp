package com.ford.cvas.evap.purge.model.pkonsct;



import com.ford.cvas.evap.purge.common.EvapVehicle;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity(name = "EVAP_PKONSCT_VEHICLE")
@NoArgsConstructor
@AllArgsConstructor
public class EvapPkonsctVehicle extends EvapVehicle {

    @ManyToOne
    @JoinColumn(name = "POWERPACK_DATA_ID")
    private EvapPkonsctPowerpackData powerpackData;

    public EvapPkonsctVehicle( String vinhash, String vin, LocalDate productionDate,
                          LocalDate warrantyStartDate, String fuelTypeCategory, String orderTypeCategory,
                          EvapPkonsctPowerpackData powerpackData) {
        super( vinhash, vin, productionDate, warrantyStartDate, fuelTypeCategory, orderTypeCategory);
        this.powerpackData = powerpackData;
    }
}
