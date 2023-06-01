package com.ford.cvas.evap.purge.model.vkonsct;



import com.ford.cvas.evap.purge.common.EvapVehicle;
import lombok.NoArgsConstructor;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;


@Entity(name = "EVAP_VKONSCT_VEHICLE")
@NoArgsConstructor
public class EvapVkonsctVehicle extends EvapVehicle {

    @ManyToOne
    @JoinColumn(name = "POWERPACK_DATA_ID")
    private EvapVkonsctPowerpackData powerpackData;

    public EvapVkonsctVehicle(String vinhash, String vin, LocalDate productionDate,
                              LocalDate warrantyStartDate, String fuelTypeCategory, String orderTypeCategory,
                              EvapVkonsctPowerpackData powerpackData) {
        super( vinhash, vin, productionDate, warrantyStartDate, fuelTypeCategory, orderTypeCategory);
        this.powerpackData = powerpackData;
    }
}
