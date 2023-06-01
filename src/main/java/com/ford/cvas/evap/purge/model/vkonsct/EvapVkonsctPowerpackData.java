package com.ford.cvas.evap.purge.model.vkonsct;



import com.ford.cvas.evap.purge.model.EvapPowerpack;
import com.ford.cvas.evap.purge.model.PowerpackData;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@Entity(name = "EVAP_VKONSCT_POWERPACK_DATA")
@NoArgsConstructor
public class EvapVkonsctPowerpackData extends PowerpackData<EvapVkonsctPowerpackDataBin> {

    public EvapVkonsctPowerpackData(EvapPowerpack powerpack) {
        super(powerpack);
    }

}
