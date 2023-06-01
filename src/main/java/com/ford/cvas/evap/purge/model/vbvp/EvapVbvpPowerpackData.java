package com.ford.cvas.evap.purge.model.vbvp;



import com.ford.cvas.evap.purge.model.EvapPowerpack;
import com.ford.cvas.evap.purge.model.PowerpackData;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@Entity(name = "EVAP_VBVP_POWERPACK_DATA")
@NoArgsConstructor
public class EvapVbvpPowerpackData extends PowerpackData<EvapVbvpPowerpackDataBin> {

  public EvapVbvpPowerpackData(EvapPowerpack powerpack) {
    super(powerpack);
  }

}
