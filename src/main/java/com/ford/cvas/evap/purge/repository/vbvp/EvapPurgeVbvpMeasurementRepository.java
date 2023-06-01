package com.ford.cvas.evap.purge.repository.vbvp;


import com.ford.cvas.evap.purge.model.dto.EvapPurgeMeasurementDTO;
import com.ford.cvas.evap.purge.model.vbvp.EvapPurgeVbvpMeasurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EvapPurgeVbvpMeasurementRepository extends JpaRepository<EvapPurgeVbvpMeasurement, Integer> {


    @Query("select new com.ford.cvas.evap.purge.model.dto.EvapPurgeMeasurementDTO(" +
        "m.value,m.time, m.odometer,m.nonNormalizedValue,m.nonNormalizedMaxLimit,m.nonNormalizedMinLimit," +
            "m.timeInService,m.vehicle.vinhash,m.vehicle.productionDate,m.vehicle.warrantyStartDate," +
        "m.vehicle.fuelTypeCategory,m.vehicle.orderTypeCategory,m.vehicle.vin,m.warningStatusMis, m.units,'EONV',m.vehicle.powerpackData.id) " +
            "from EVAP_PURGE_VBVP m where " +
            "m.vehicle.vin=?1  and m.time>?2  and m.value is not null and m.processed=true")
    List<EvapPurgeMeasurementDTO> getVehicleAllMeasurements(
            String vin,
            LocalDateTime timeFilter
    );

}
