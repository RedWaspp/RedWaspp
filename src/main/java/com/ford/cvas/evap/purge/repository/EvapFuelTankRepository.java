package com.ford.cvas.evap.purge.repository;

import com.ford.cvas.evap.purge.model.FuelTank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EvapFuelTankRepository extends JpaRepository<FuelTank, Long> {
    @Query("select a from FUEL_TANK a where a.fuelTankSize=:evapFuelTankSize")
    FuelTank getFuelData(Double evapFuelTankSize);
}
