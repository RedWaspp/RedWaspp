package com.ford.cvas.evap.purge.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "FUEL_TANK")
@Data
@NoArgsConstructor
public class FuelTank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "FUEL_TANK_SIZE")
    public Double fuelTankSize;

    @Column(name = "FUEL_TANK_SIZE_UNIT")
    public String fuelTankSizeUnit;

    public FuelTank(Double fuelTankSize, String fuelTankSizeUnit) {
        this.fuelTankSize = fuelTankSize;
        this.fuelTankSizeUnit = fuelTankSizeUnit;
    }

}
