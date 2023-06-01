package com.ford.cvas.evap.purge.model.vbvp;



import com.ford.cvas.evap.purge.common.EvapVehicle;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;


@Entity(name = "EVAP_VBVP_VEHICLE")
@NoArgsConstructor
@AllArgsConstructor
public class EvapVbvpVehicle extends EvapVehicle {

    @ManyToOne
    @JoinColumn(name = "POWERPACK_DATA_ID")
    private EvapVbvpPowerpackData powerpackData;

    public EvapVbvpVehicle( String vinhash, String vin, LocalDate productionDate,
                          LocalDate warrantyStartDate, String fuelTypeCategory, String orderTypeCategory,
                            EvapVbvpPowerpackData powerpackData) {
        super( vinhash, vin, productionDate, warrantyStartDate, fuelTypeCategory, orderTypeCategory);
        this.powerpackData = powerpackData;
    }
}
