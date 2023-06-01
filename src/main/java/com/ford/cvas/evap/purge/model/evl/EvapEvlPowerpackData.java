package com.ford.cvas.evap.purge.model.evl;



import com.ford.cvas.evap.purge.model.EvapPowerpack;
import com.ford.cvas.evap.purge.model.PowerpackData;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@Entity(name = "EVAP_EVL_POWERPACK_DATA")
@NoArgsConstructor
public class EvapEvlPowerpackData extends PowerpackData<EvapEvlPowerpackDataBin> {

  public EvapEvlPowerpackData(EvapPowerpack powerpack) {
    super(powerpack);
  }
}
