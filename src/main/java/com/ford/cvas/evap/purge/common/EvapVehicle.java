package com.ford.cvas.evap.purge.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public abstract class EvapVehicle implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    protected Integer id;

    @Column(name = "VINHASH")
    private String vinhash;

    @Column(name = "VIN")
    private String vin;

    @Column(name = "PRODUCTION_DATE")
    private LocalDate productionDate;

    @Column(name = "WARRANTY_START_DATE")
    private LocalDate warrantyStartDate;

    @Column(name = "FUEL_TYPE_CATEGORY")
    private String fuelTypeCategory;

    @Column(name = "ORDER_TYPE_CATEGORY")
    private String orderTypeCategory;

    public EvapVehicle(String vinhash, String vin, LocalDate productionDate,
                       LocalDate warrantyStartDate, String fuelTypeCategory,
                       String orderTypeCategory) {
        this.vinhash=vinhash;
        this.vin=vin;
        this.productionDate=productionDate;
        this.warrantyStartDate=warrantyStartDate;
        this.fuelTypeCategory=fuelTypeCategory;
        this.orderTypeCategory=orderTypeCategory;
    }
}
