package com.ford.cvas.evap.purge.repository.ml;


import com.ford.cvas.evap.purge.common.IndependentVariable;
import com.ford.cvas.evap.purge.common.PowerpackDataBin;
import com.ford.cvas.evap.purge.model.dto.WarningThresholdDataDto;
import com.ford.cvas.evap.purge.model.ml.EvapMLPowerpackDataBin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EvapMLPowerpackDataBinRepository extends JpaRepository<EvapMLPowerpackDataBin, Integer> {


  @Query("select new com.ford.cvas.evap.purge.model.dto.WarningThresholdDataDto(MAX (m.warningThreshold), MIN (m.warningThreshold), AVG (m.warningThreshold)) " +
      "from EVAP_ML_POWERPACK_DATA_BIN m where " +
      "m.powerpackData.id=?1  and m.independentVariable=?2 group by m.powerpackData.id ")
  WarningThresholdDataDto getWarningThresholdStats(
      Integer pId, IndependentVariable independentVariable
  );
  List<PowerpackDataBin> findEvapMlPowerpackDataBinsByPowerpackDataIdAndIndependentVariable(Integer pId, IndependentVariable independentVariable);


}
