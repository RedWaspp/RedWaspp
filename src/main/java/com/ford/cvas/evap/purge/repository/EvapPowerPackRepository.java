package com.ford.cvas.evap.purge.repository;

import com.ford.cvas.evap.purge.model.EvapPowerpack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvapPowerPackRepository extends JpaRepository<EvapPowerpack, Long> {
    @Query("select a from EVAP_POWERPACK a where a.namePlate=:nameplate AND a.modelYear=:modelYear and a.engine = :engine")
    List<EvapPowerpack> getDataByCondition(String nameplate, int modelYear, String engine);

    @Query("select a from EVAP_POWERPACK a where a.namePlate=:nameplate AND a.modelYear=:modelYear and a.engine = :engine and a.fuelTank.id=:fuelTankId")
    List<EvapPowerpack> getDataByConditionWithFuelTankSize(String nameplate, int modelYear, String engine, int fuelTankId);

    @Query("select a from EVAP_POWERPACK a where a.namePlate=:nameplate AND a.modelYear=:modelYear")
    List<EvapPowerpack> getByNameAndModel(String nameplate, int modelYear);

    @Query("select a from EVAP_POWERPACK a where a.namePlate=:nameplate AND a.modelYear=:modelYear and a.engine = :engine and a.fuelTank.id=:fuelTankId")
    EvapPowerpack getpowerPackDataWithFuelTankSize(String nameplate, int modelYear, String engine, int fuelTankId);

    @Query("SELECT DISTINCT e.namePlate, e.modelYear FROM EVAP_POWERPACK e")
    List getDistinctByNamePlateAndModelYear();
}
