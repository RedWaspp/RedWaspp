package com.ford.cvas.evap.purge.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;


@Data
@Entity(name = "EVAP_POWERPACK")
@Component
@NoArgsConstructor
public class EvapPowerpack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;


    @Column(name = "NAMEPLATE")
    public String namePlate;


    @Column(name = "MODEL_YEAR")
    public int modelYear;


    @Column(name = "ENGINE")
    public String engine;


    @OneToOne
    @JoinColumn(name = "FUEL_TANK_ID")
    public FuelTank fuelTank;


    public EvapPowerpack(String namePlate, int modelYear, String engine, FuelTank fuelTank) {
        this.namePlate = namePlate;
        this.modelYear = modelYear;
        this.engine = engine;
        this.fuelTank = fuelTank;
    }
}
