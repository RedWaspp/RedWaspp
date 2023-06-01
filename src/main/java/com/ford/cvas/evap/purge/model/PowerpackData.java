package com.ford.cvas.evap.purge.model;

import com.ford.cvas.evap.purge.common.PowerpackDataBin;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Data
@MappedSuperclass
@NoArgsConstructor
public abstract class PowerpackData<T extends PowerpackDataBin> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(name = "VEHICLES_ABOVE_WARNING_THRESHOLD")
    int vehiclesAboveWarningThreshold;

    @OneToOne
    @JoinColumn(name = "POWERPACK_ID")
    private EvapPowerpack powerpack;

    @OneToMany(cascade = {CascadeType.ALL, CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @JoinColumn(name = "powerpack_data_id")
    @Getter
    @Setter
    private List<T> bins;

    public PowerpackData(EvapPowerpack powerpack){
        this.powerpack=powerpack;
    }
}
