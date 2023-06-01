package com.ford.cvas.evap.purge.model.dppcv;



import com.ford.cvas.evap.purge.model.EvapPowerpack;
import com.ford.cvas.evap.purge.model.PowerpackData;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@Entity(name = "EVAP_DPPCV_POWERPACK_DATA")
@NoArgsConstructor
public class EvapDppcvPowerpackData extends PowerpackData<EvapDppcvPowerpackDataBin> {

  public EvapDppcvPowerpackData(EvapPowerpack powerpack) {
    super(powerpack);
  }
}
