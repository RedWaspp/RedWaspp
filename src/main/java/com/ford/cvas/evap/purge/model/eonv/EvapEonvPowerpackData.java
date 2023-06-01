package com.ford.cvas.evap.purge.model.eonv;

import com.ford.cvas.evap.purge.model.EvapPowerpack;
import com.ford.cvas.evap.purge.model.PowerpackData;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


import javax.persistence.Entity;


@Data
@Entity(name = "EVAP_EONV_POWERPACK_DATA")
@Component
@NoArgsConstructor
public class EvapEonvPowerpackData extends PowerpackData<EvapEonvPowerpackDataBin> {

  public EvapEonvPowerpackData(EvapPowerpack powerpack) {
    super(powerpack);
  }
}
