package com.ford.cvas.evap.purge.repository;

import com.ford.cvas.evap.purge.model.EvapPPDModel;
import com.ford.cvas.evap.purge.model.EvapVehicleModel;
import com.ford.cvas.evap.purge.response.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EvapVehicleDbRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    static final String POWER_PACK_ID_QRY = "SELECT a.ID, a.POWERPACK_ID,a.VEHICLES_ABOVE_WARNING_THRESHOLD FROM ";
    static final String VEHICLE_QRY = "SELECT ID,VINHASH,VIN,POWERPACK_DATA_ID FROM ";
    static final String EVAP = "EVAP_";
    static final String POWERPACKDATA = "_POWERPACK_DATA";
    static final String VEHICLE = "_VEHICLE";
    static final String EVAPPURGE = "EVAP_PURGE_";
    static final String POWERPACKDATABIN = "_POWERPACK_DATA_BIN";

    public List<Integer> getPowerPackDataIDlistByTestName(String testName) {
        String powerPackDataTableName = EVAP + testName + POWERPACKDATA;
        String ppdataQuery = POWER_PACK_ID_QRY + powerPackDataTableName + " a";
        List<EvapPPDModel> powerPackDataListByTestName = jdbcTemplate.query(ppdataQuery,
                (rs, rowNum) ->
                        new EvapPPDModel(
                                rs.getInt("ID"),
                                rs.getInt("POWERPACK_ID"),
                                rs.getInt("VEHICLES_ABOVE_WARNING_THRESHOLD")
                        )
        );
        List<Integer> powerPackDataIdList = new ArrayList<>();
        for (EvapPPDModel evapPPDModel : powerPackDataListByTestName) {
            powerPackDataIdList.add(evapPPDModel.getId());
        }
        return powerPackDataIdList;
    }

    public Statistics getVehicleCounts(StringBuilder powerPackIds, String testName, int noOfDays) {
        String powerPackDataTableName = "";
        String vechicleTableName = "";
        String purgeTableName = "";

        powerPackDataTableName = EVAP + testName + POWERPACKDATA;
        vechicleTableName = EVAP + testName + VEHICLE;
        purgeTableName = EVAPPURGE + testName;

        // EVAP_POWER_PACK  = name, mY , engine, Nameplate
        String anamolousVinsCountQry = "select count(distinct M.VINHASH) anamolousVinsCount, count(M.value) totalNoOfAnomolousReadingsCount\n" +
                "from " + purgeTableName + " M,\n" + vechicleTableName + "  V,\n" + powerPackDataTableName + " P1,\n" +
                "     EVAP_POWERPACK P,\n" +
                "     FUEL_TANK F\n" +
                "WHERE M.VINHASH=V.VINHASH\n" +
                "AND V.POWERPACK_DATA_ID=P1.ID\n" +
                "AND P1.POWERPACK_ID=P.ID\n" +
                " AND P.FUEL_TANK_ID=F.ID\n" +
                "  AND P1.ID IN(" + powerPackIds + ") AND" +
                " M.TIME>GETDATE()-" + noOfDays + "\n" +
                " AND ( M.WARNING_STATUS_MIS = 2 OR M.WARNING_STATUS_TIS = 2 )";

        String processedCountQry = "select count(distinct M.VINHASH) processedVinsCount,count(M.VINHASH) totalNoofReadingsCount \n" +
                "from " + purgeTableName + " M,\n" + vechicleTableName + "  V,\n" + powerPackDataTableName + " P1,\n" +
                "     EVAP_POWERPACK P,\n" +
                "     FUEL_TANK F\n" +
                "WHERE M.VINHASH=V.VINHASH\n" +
                "  AND V.POWERPACK_DATA_ID=P1.ID\n" +
                "  AND P1.POWERPACK_ID=P.ID\n" +
                "  AND P.FUEL_TANK_ID=F.ID\n" +
                "  AND P1.ID IN(" + powerPackIds + ") AND M.TIME>GETDATE()-" + noOfDays + "\n" +
                "  AND M.PROCESSED = 1 \n";

        String thresholdStatQry = "SELECT SUM(WARNING_THRESHOLD) MAXTHRESHOLD ,AVG(WARNING_THRESHOLD) AVGTHRESHOLD, MIN(WARNING_THRESHOLD) MINIMUMTHRESHOLD  \n" +
                "  FROM [" + EVAP + testName + POWERPACKDATABIN + "] \n" +
                "  where POWERPACK_DATA_ID IN (" + powerPackIds + " ) AND INDEPENDENT_VARIABLE='TIME_IN_SERVICE'";

        Statistics anomolousResponse = jdbcTemplate.queryForObject(
                anamolousVinsCountQry,
                (rs, rowNum) -> Statistics.builder()
                        .anamolousVinsCount(rs.getInt("anamolousVinsCount"))
                        .totalNoOfAnomolousReadingsCount(rs.getInt("totalNoOfAnomolousReadingsCount")).build());
        Statistics processedResponse = jdbcTemplate.queryForObject(
                processedCountQry,
                (rs, rowNum) -> Statistics.builder().processedVinsCount(rs.getInt("processedVinsCount"))
                        .totalNoofReadingsCount(rs.getInt("totalNoofReadingsCount")).build());

        Statistics thresholdResponse = jdbcTemplate.queryForObject(
                thresholdStatQry,
                (rs, rowNum) -> Statistics.builder().minimumThreshold(rs.getDouble("MINIMUMTHRESHOLD"))
                        .maximumThreshold(rs.getDouble("MAXTHRESHOLD"))
                        .averageThreshold(rs.getDouble("AVGTHRESHOLD"))
                        .build());

        Statistics outputResponseObject = Statistics.builder().maximumThreshold(null).minimumThreshold(null).failureThreshold(null)
                .totalNoOfAnomolousReadingsCount(null).anamolousVinsCount(null).processedVinsCount(null).totalNoofReadingsCount(null).
                build();

        if (processedResponse != null && processedResponse.getProcessedVinsCount() > 0) {
                outputResponseObject.setProcessedVinsCount(processedResponse.getProcessedVinsCount());
                outputResponseObject.setTotalNoofReadingsCount(processedResponse.getTotalNoofReadingsCount());
                if (thresholdResponse != null) {
                    outputResponseObject.setMinimumThreshold(thresholdResponse.getMinimumThreshold());
                    outputResponseObject.setMaximumThreshold(thresholdResponse.getMaximumThreshold());
                    outputResponseObject.setAverageThreshold(thresholdResponse.getAverageThreshold());
                }
                if (testName.equals("EONV")) {
                    outputResponseObject.setFailureThreshold(0.7);
                } else {
                    outputResponseObject.setFailureThreshold(1.0);
                }

            if(testName.equals("EVL") || testName.equals("ML") || testName.equals("KOBL")) {
                outputResponseObject.setAnamaloyTrendIndicator("Up");
            } else {
                outputResponseObject.setAnamaloyTrendIndicator("Down");
            }

                if (anomolousResponse != null) {
                    outputResponseObject.setAnamolousVinsCount(anomolousResponse.getAnamolousVinsCount());
                    outputResponseObject.setTotalNoOfAnomolousReadingsCount(anomolousResponse.getTotalNoOfAnomolousReadingsCount());
                }
        }
        return outputResponseObject;
    }


    public List<String> getVehiclesListByTestNameAndPowerPackIds(String testName, StringBuilder powerPackIdSet) {

        String powerPackDataTableName = "";
        String vechicleTableName = "";
        String purgeTableName = "";

        powerPackDataTableName = EVAP + testName + POWERPACKDATA;
        vechicleTableName = EVAP + testName + VEHICLE;
        purgeTableName = EVAPPURGE + testName;

        String myQry = "select distinct v.vin \n" +
                "from "+purgeTableName+" m,\n" +
                "     "+vechicleTableName+" v,\n" +
                "     "+powerPackDataTableName+" p1,\n" +
                "     evap_powerpack p \n" +
                "    -- FUEL_TANK f\n" +
                "where m.time > DATEADD(DAY,-90,CAST(GETDATE() AS DATE))\n" +
                "  and (m.WARNING_STATUS_MIS = 2)\n" +
                "  --and m.PROCESSED = 1\n" +
                "  and m.VINHASH=v.VINHASH\n" +
                "and v.POWERPACK_DATA_ID=p1.ID\n" +
                "and p1.POWERPACK_ID=p.ID\n" +
                "and p.id IN ( "+powerPackIdSet+")";


        List<String> vinsList = new ArrayList<>();
        SqlParameterSource parameters = new MapSqlParameterSource("ids", powerPackIdSet);

        List<EvapVehicleModel> vehicles = namedParameterJdbcTemplate.query(
                myQry,
                parameters,
                (rs, rowNum) ->  EvapVehicleModel.builder().vin(rs.getString(1)).build());

        for (EvapVehicleModel mymoVehicleModel : vehicles) {
            vinsList.add(mymoVehicleModel.getVin());
        }
        return vinsList;
    }

}
