package com.ford.cvas.evap.purge.model.pkonsct;



import com.ford.cvas.evap.purge.model.EvapPowerpack;
import com.ford.cvas.evap.purge.model.PowerpackData;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@Entity(name = "EVAP_PKONSCT_POWERPACK_DATA")
@NoArgsConstructor
public class EvapPkonsctPowerpackData extends PowerpackData<EvapPkonsctPowerpackDataBin> {

    public EvapPkonsctPowerpackData(EvapPowerpack powerpack) {
        super(powerpack);
    }
}
