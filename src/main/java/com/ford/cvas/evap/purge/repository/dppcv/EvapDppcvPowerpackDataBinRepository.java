package com.ford.cvas.evap.purge.repository.dppcv;


import com.ford.cvas.evap.purge.common.IndependentVariable;
import com.ford.cvas.evap.purge.common.PowerpackDataBin;
import com.ford.cvas.evap.purge.model.dppcv.EvapDppcvPowerpackDataBin;
import com.ford.cvas.evap.purge.model.dto.WarningThresholdDataDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface EvapDppcvPowerpackDataBinRepository extends JpaRepository<EvapDppcvPowerpackDataBin, Integer> {


   @Query("select new com.ford.cvas.evap.purge.model.dto.WarningThresholdDataDto(MAX (m.warningThreshold), MIN (m.warningThreshold), AVG (m.warningThreshold)) " +
       "from EVAP_DPPCV_POWERPACK_DATA_BIN m where " +
       "m.powerpackData.id=?1  and m.independentVariable=?2 group by m.powerpackData.id ")
   WarningThresholdDataDto getWarningThresholdStats(
       Integer pId, IndependentVariable independentVariable
   );

   List<PowerpackDataBin> findEvapDppcvPowerpackDataBinsByPowerpackDataIdAndIndependentVariable(Integer pId, IndependentVariable independentVariable);

}
