package com.ford.cvas.evap.purge.repository;

import com.ford.cvas.evap.purge.model.EvapPowerpackData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EvapPowerPackDataRepository extends JpaRepository<EvapPowerpackData, Long> {

    EvapPowerpackData findFirstById(int id);
    EvapPowerpackData findEvapPowerpackDataByPowerpackId(int id);
}
