package com.ford.cvas.evap.purge.model.kofsct;


import com.ford.cvas.evap.purge.model.EvapPowerpack;
import com.ford.cvas.evap.purge.model.PowerpackData;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@Entity(name = "EVAP_KOFSCT_POWERPACK_DATA")
@NoArgsConstructor
public class EvapKofsctPowerpackData extends PowerpackData<EvapKofsctPowerpackDataBin> {

    public EvapKofsctPowerpackData(EvapPowerpack powerpack) {
        super(powerpack);
    }


}
