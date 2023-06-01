package com.ford.cvas.evap.purge.repository.vkonsct;


import com.ford.cvas.evap.purge.common.IndependentVariable;
import com.ford.cvas.evap.purge.common.PowerpackDataBin;
import com.ford.cvas.evap.purge.model.dto.WarningThresholdDataDto;
import com.ford.cvas.evap.purge.model.vkonsct.EvapVkonsctPowerpackDataBin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface EvapVkonsctPowerpackDataBinRepository extends JpaRepository<EvapVkonsctPowerpackDataBin, Integer> {


   @Query("select new com.ford.cvas.evap.purge.model.dto.WarningThresholdDataDto(MAX (m.warningThreshold), MIN (m.warningThreshold), AVG (m.warningThreshold)) " +
       "from EVAP_VKONSCT_POWERPACK_DATA_BIN m where " +
       "m.powerpackData.id=?1  and m.independentVariable=?2 group by m.powerpackData.id ")
   WarningThresholdDataDto getWarningThresholdStats(
       Integer pId, IndependentVariable independentVariable
   );

   List<PowerpackDataBin> findEvapVkonsctPowerpackDataBinsByPowerpackDataIdAndIndependentVariable(Integer pId, IndependentVariable independentVariable);

}
