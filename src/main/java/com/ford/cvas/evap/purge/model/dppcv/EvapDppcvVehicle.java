package com.ford.cvas.evap.purge.model.dppcv;



import com.ford.cvas.evap.purge.common.EvapVehicle;
import lombok.NoArgsConstructor;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;


@Entity(name = "EVAP_DPPCV_VEHICLE")
@NoArgsConstructor
public class EvapDppcvVehicle extends EvapVehicle {

    @ManyToOne
    @JoinColumn(name = "POWERPACK_DATA_ID")
    private EvapDppcvPowerpackData powerpackData;

    public EvapDppcvVehicle( String vinhash, String vin, LocalDate productionDate,
                            LocalDate warrantyStartDate, String fuelTypeCategory, String orderTypeCategory,
                             EvapDppcvPowerpackData powerpackData) {
        super( vinhash, vin, productionDate, warrantyStartDate, fuelTypeCategory, orderTypeCategory);
        this.powerpackData = powerpackData;
    }
}
