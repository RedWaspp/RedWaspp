package com.ford.cvas.evap.purge.model.evl;



import com.ford.cvas.evap.purge.common.EvapVehicle;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;


@Entity(name = "EVAP_EVL_VEHICLE")
@NoArgsConstructor
@AllArgsConstructor
public class EvapEvlVehicle extends EvapVehicle {

    @ManyToOne
    @JoinColumn(name = "powerpack_data_id")
    private EvapEvlPowerpackData powerpackData;

    public EvapEvlVehicle( String vinhash, String vin, LocalDate productionDate,
                            LocalDate warrantyStartDate, String fuelTypeCategory, String orderTypeCategory,
                           EvapEvlPowerpackData powerpackData) {
        super( vinhash, vin, productionDate, warrantyStartDate, fuelTypeCategory, orderTypeCategory);
        this.powerpackData = powerpackData;
    }
}
