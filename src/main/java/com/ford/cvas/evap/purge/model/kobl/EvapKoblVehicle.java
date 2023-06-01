package com.ford.cvas.evap.purge.model.kobl;



import com.ford.cvas.evap.purge.common.EvapVehicle;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;


@Entity(name = "EVAP_KOBL_VEHICLE")
public class EvapKoblVehicle extends EvapVehicle {

    @ManyToOne
    @JoinColumn(name = "POWERPACK_DATA_ID")
    private EvapKoblPowerpackData powerpackData;


    public EvapKoblVehicle( String vinhash, String vin, LocalDate productionDate,
                            LocalDate warrantyStartDate, String fuelTypeCategory, String orderTypeCategory,
                            EvapKoblPowerpackData powerpackData) {
        super( vinhash, vin, productionDate, warrantyStartDate, fuelTypeCategory, orderTypeCategory);
        this.powerpackData = powerpackData;

    }
}
