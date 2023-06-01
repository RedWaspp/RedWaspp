package com.ford.cvas.evap.purge.model;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
public class EvapPPDModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "POWERPACK_ID")
    public int powerPackId;

    @Column(name = "VEHICLES_ABOVE_WARNING_THRESHOLD")
    public int vehiclesAboveWarningThreshold;
}
