package com.ford.cvas.evap.purge.model.vbvp;


import com.ford.cvas.evap.purge.common.IndependentVariable;
import com.ford.cvas.evap.purge.common.PowerpackDataBin;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity(name = "EVAP_VBVP_POWERPACK_DATA_BIN")
@NoArgsConstructor
@Data
public class EvapVbvpPowerpackDataBin implements PowerpackDataBin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name="INDEPENDENT_VARIABLE")
    private IndependentVariable independentVariable;

    @Column(name="MEAN")
    private double mean = 0.0;
    @Column(name="STANDARD_DEVIATION")
    private double standardDeviation = 0.0;
    @Column(name="COUNT")
    private int count = 0;
    @Column(name="WARNING_THRESHOLD")
    private double warningThreshold = 0.0;
    @Column(name="LOWER_BOUND")
    private int lowerBound;
    @Column(name="UPPER_BOUND")
    private int upperBound;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name="POWERPACK_DATA_ID")
    private EvapVbvpPowerpackData powerpackData;


    public EvapVbvpPowerpackDataBin(EvapVbvpPowerpackData powerpackData,
                                  IndependentVariable independentVariable,
                                  int lowerBound, int upperBound,
                                  double warningThreshold) {
        this.independentVariable = independentVariable;
        this.warningThreshold = warningThreshold;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.powerpackData = powerpackData;
    }
}
