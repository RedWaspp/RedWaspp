package com.ford.cvas.evap.purge.repository;

import com.ford.cvas.evap.purge.model.eonv.EvapEonvVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvapEonvVehicleRepo extends JpaRepository<EvapEonvVehicle, Long> {
    @Query("SELECT eonv FROM EVAP_EONV_VEHICLE eonv where eonv.powerpackData.id  = :powerPackDataId")
    List<EvapEonvVehicle> getEonvVehiclesInfo(int powerPackDataId);
}
