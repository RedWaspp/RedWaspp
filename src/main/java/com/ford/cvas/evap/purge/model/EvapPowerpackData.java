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
@Entity(name = "EVAP_POWERPACK_DATA")
@Component
@NoArgsConstructor
public class EvapPowerpackData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @JoinColumn(name = "POWERPACK_ID")
    @OneToOne
    public EvapPowerpack powerpack;

    @Column(name = "VEHICLES_ABOVE_WARNING_THRESHOLD")
    public int vehiclesAboveWarningThreshold;

    public EvapPowerpackData(EvapPowerpack powerpack, int vehiclesAboveWarningThreshold) {
        this.powerpack = powerpack;
        this.vehiclesAboveWarningThreshold = vehiclesAboveWarningThreshold;
    }

}
