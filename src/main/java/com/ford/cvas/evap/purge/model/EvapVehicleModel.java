package com.ford.cvas.evap.purge.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Component
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class EvapVehicleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;


    @Column(name = "VINHASH")
    public String vinHash;

    @Column(name = "VIN")
    public String vin;

    @Column(name = "POWERPACK_DATA_ID")
    public int powerPackDataId;
}
