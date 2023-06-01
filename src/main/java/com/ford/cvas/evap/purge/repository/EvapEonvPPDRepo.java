package com.ford.cvas.evap.purge.repository;

import com.ford.cvas.evap.purge.model.eonv.EvapEonvPowerpackData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvapEonvPPDRepo extends JpaRepository<EvapEonvPowerpackData, Long> {
    @Query(value = "SELECT eonv FROM EVAP_EONV_POWERPACK_DATA eonv")
    List<EvapEonvPowerpackData> getAllEonvPowerPackData();

    List<EvapEonvPowerpackData> getEvapEonvPowerpackDataByPowerpackId(int powerPackId);
}
