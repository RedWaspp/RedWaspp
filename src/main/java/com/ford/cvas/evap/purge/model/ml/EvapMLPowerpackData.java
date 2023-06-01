package com.ford.cvas.evap.purge.model.ml;

import com.ford.cvas.evap.purge.model.EvapPowerpack;
import com.ford.cvas.evap.purge.model.PowerpackData;
import lombok.Data;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;


@Data
@Entity(name = "EVAP_ML_POWERPACK_DATA")
@Component
@NoArgsConstructor
public class EvapMLPowerpackData extends PowerpackData<EvapMLPowerpackDataBin> {

  public EvapMLPowerpackData(EvapPowerpack powerpack) {
    super(powerpack);
  }
}
