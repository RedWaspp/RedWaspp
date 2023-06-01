package com.ford.cvas.evap.purge.model.kobl;



import com.ford.cvas.evap.purge.model.EvapPowerpack;
import com.ford.cvas.evap.purge.model.PowerpackData;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@Entity(name = "EVAP_KOBL_POWERPACK_DATA")
@NoArgsConstructor
public class EvapKoblPowerpackData extends PowerpackData<EvapKoblPowerpackDataBin> {


    public EvapKoblPowerpackData(EvapPowerpack powerpack) {
        super(powerpack);
    }
}
