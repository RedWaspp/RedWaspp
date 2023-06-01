package com.ford.cvas.evap.purge.api;


import com.ford.cvas.evap.purge.common.AppConstants;
import com.ford.cvas.evap.purge.common.IndependentVariable;
import com.ford.cvas.evap.purge.model.EvapPowerpack;
import com.ford.cvas.evap.purge.model.EvapPowerpackData;
import com.ford.cvas.evap.purge.model.FuelTank;
import com.ford.cvas.evap.purge.model.dppcv.EvapDppcvPowerpackData;
import com.ford.cvas.evap.purge.model.dppcv.EvapDppcvPowerpackDataBin;
import com.ford.cvas.evap.purge.model.dppcv.EvapDppcvVehicle;
import com.ford.cvas.evap.purge.model.dppcv.EvapPurgeDppcvMeasurement;
import com.ford.cvas.evap.purge.model.eonv.EvapEonvPowerpackData;
import com.ford.cvas.evap.purge.model.eonv.EvapEonvPowerpackDataBin;
import com.ford.cvas.evap.purge.model.eonv.EvapEonvVehicle;
import com.ford.cvas.evap.purge.model.eonv.EvapPurgeEonvMeasurement;
import com.ford.cvas.evap.purge.model.evl.EvapEvlPowerpackData;
import com.ford.cvas.evap.purge.model.evl.EvapEvlPowerpackDataBin;
import com.ford.cvas.evap.purge.model.evl.EvapEvlVehicle;
import com.ford.cvas.evap.purge.model.evl.EvapPurgeEvlMeasurement;
import com.ford.cvas.evap.purge.model.kobl.EvapKoblPowerpackData;
import com.ford.cvas.evap.purge.model.kobl.EvapKoblPowerpackDataBin;
import com.ford.cvas.evap.purge.model.kobl.EvapKoblVehicle;
import com.ford.cvas.evap.purge.model.kobl.EvapPurgeKoblMeasurement;
import com.ford.cvas.evap.purge.model.kofsct.EvapKofsctPowerpackData;
import com.ford.cvas.evap.purge.model.kofsct.EvapKofsctPowerpackDataBin;
import com.ford.cvas.evap.purge.model.kofsct.EvapKofsctVehicle;
import com.ford.cvas.evap.purge.model.kofsct.EvapPurgeKofsctMeasurement;
import com.ford.cvas.evap.purge.model.ml.EvapMLPowerpackData;
import com.ford.cvas.evap.purge.model.ml.EvapMLPowerpackDataBin;
import com.ford.cvas.evap.purge.model.ml.EvapMLVehicle;
import com.ford.cvas.evap.purge.model.ml.EvapPurgeMLMeasurement;
import com.ford.cvas.evap.purge.model.pkonsct.EvapPkonsctPowerpackData;
import com.ford.cvas.evap.purge.model.pkonsct.EvapPkonsctPowerpackDataBin;
import com.ford.cvas.evap.purge.model.pkonsct.EvapPkonsctVehicle;
import com.ford.cvas.evap.purge.model.pkonsct.EvapPurgePkonsctMeasurement;
import com.ford.cvas.evap.purge.model.vbvp.EvapPurgeVbvpMeasurement;
import com.ford.cvas.evap.purge.model.vbvp.EvapVbvpPowerpackData;
import com.ford.cvas.evap.purge.model.vbvp.EvapVbvpPowerpackDataBin;
import com.ford.cvas.evap.purge.model.vbvp.EvapVbvpVehicle;
import com.ford.cvas.evap.purge.model.vkonsct.EvapPurgeVkonsctMeasurement;
import com.ford.cvas.evap.purge.model.vkonsct.EvapVkonsctPowerpackData;
import com.ford.cvas.evap.purge.model.vkonsct.EvapVkonsctPowerpackDataBin;
import com.ford.cvas.evap.purge.model.vkonsct.EvapVkonsctVehicle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class EvapPurgeApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestEntityManager testEntityManager;


    @Test
    void structureOfEndpointIsCorrect() throws Exception {
        setUpFakeDataForEONV();
        mockMvc.perform(
            get("/api/evappurge/vin/data")
                .queryParam("vin", "00000000000000001")
                .queryParam("testNames", "EONV")
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isMap())
            .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats").isArray())
            .andExpect(jsonPath("$.responseObj.namePlate").value("F-150"))
            .andExpect(jsonPath("$.responseObj.vin").value("00000000000000001"))
            .andExpect(jsonPath("$.responseObj.engine").value("3.5l V6 Gtdi"))
            .andExpect(jsonPath("$.responseObj.fuelTanksize").value(23.9));

    }

    @Test
    void apiReturnsCorrectDataForVinAndTestNames() throws Exception {
        setUpFakeDataForEONV();

        mockMvc.perform(
                        get("/api/evappurge/vin/data")
                                .queryParam("vin", "00000000000000001")
                                .queryParam("testNames", "EONV")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats").isArray())
                .andExpect(jsonPath("$.responseObj.namePlate").value("F-150"))
                .andExpect(jsonPath("$.responseObj.vin").value("00000000000000001"))
                .andExpect(jsonPath("$.responseObj.engine").value("3.5l V6 Gtdi"))
                .andExpect(jsonPath("$.responseObj.fuelTanksize").value(23.9))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[0].evapPurgeTest").value("EONV"))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[0].healthyReadingsCount").value(2))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[0].maxWarningThreshold").value(1.93))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[0].minWarningThreshold").value(0.8))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[0].averageWarningThreshold").value(2.0))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[0].failureThreshold").value(0.7))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[0].anamaloyTrendIndicator").value("Down"))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[0].bins").isArray());
    }


    @Test
    void apiValidationForInvalidVin() throws Exception {
        setUpFakeDataForEONV();
        mockMvc.perform(
                        get("/api/evappurge/vin/data")
                                .queryParam("vin", "0000000000000000")
                                .queryParam("testNames", "EONV")
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.message").isArray())
                .andExpect(jsonPath("$.message[0]").value("Invalid Vin"));
    }

    @Test
    void apiValidationForEmptyTestName() throws Exception {
        setUpFakeDataForEONV();
        mockMvc.perform(
                        get("/api/evappurge/vin/data")
                                .queryParam("vin", "00000000000000001")
                                .queryParam("testNames", "")
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.message").isArray())
                .andExpect(jsonPath("$.message[0]").value(AppConstants.NO_TESTS_ERROR_MESSAGE_VIN_SPECIFIC));
    }

    @Test
    void apiValidationForInvalidVinAndTestName() throws Exception {
        setUpFakeDataForEONV();
        List<String> message = Arrays.asList("Invalid Vin", "Invalid Test Name");
        mockMvc.perform(
                        get("/api/evappurge/vin/data")
                                .queryParam("vin", "000000")
                                .queryParam("testNames", "")
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.message").isArray())
                .andExpect(jsonPath("$.message[0]").value(message.get(0)));
    }

    @Test
    void apiValidationForNoDataFound() throws Exception {
        setUpFakeDataForEONV();
        List<String> message = Arrays.asList(AppConstants.NO_OUTPUT_MESSAGE);
        mockMvc.perform(
                        get("/api/evappurge/vin/data")
                                .queryParam("vin", "00000000000000001")
                                .queryParam("testNames", "KOFSCT")
                )
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.responseObj.messages").value(message.get(0)));
    }

    @Test
    void apiValidationForNullValueCheckForNoDataFoundTest() throws Exception {
        setUpFakeDataForEONV();
        mockMvc.perform(
                        get("/api/evappurge/vin/data")
                                .queryParam("vin", "00000000000000001")
                                .queryParam("testNames", "EONV", "KOFSCT")
                )
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats").isArray())
                .andExpect(jsonPath("$.responseObj.namePlate").value("F-150"))
                .andExpect(jsonPath("$.responseObj.vin").value("00000000000000001"))
                .andExpect(jsonPath("$.responseObj.engine").value("3.5l V6 Gtdi"))
                .andExpect(jsonPath("$.responseObj.fuelTanksize").value(23.9))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[0].evapPurgeTest").value("EONV"))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[0].healthyReadingsCount").value(2))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[0].maxWarningThreshold").value(1.93))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[0].minWarningThreshold").value(0.8))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[0].averageWarningThreshold").value(2.0))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[0].failureThreshold").value(0.7))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[0].anamaloyTrendIndicator").value("Down"))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[0].bins").isArray())
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[1].evapPurgeTest").value("KOFSCT"))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[1].healthyReadingsCount").isEmpty())
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[1].maxWarningThreshold").isEmpty())
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[1].minWarningThreshold").isEmpty())
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[1].averageWarningThreshold").isEmpty())
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[1].failureThreshold").isEmpty())
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[1].anamaloyTrendIndicator").isEmpty())
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[1].bins").isEmpty());

    }

    @Test
    void apiValidationForTestName() throws Exception {
        setUpFakeDataForEONV();

        mockMvc.perform(
                        get("/api/evappurge/vin/data")
                                .queryParam("vin", "00000000000000001")
                                .queryParam("testNames", "rr")
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.message").isArray())
                .andExpect(jsonPath("$.message[0]").value(AppConstants.INVALID_TEST_NAMES_MESSAGE[0]));
    }

    @Test
    void apiReturnsCorrectDataForVinAndTestNamesForGas() throws Exception {
        setUpFakeDataForEONV();
        setUpFakeDataForML();
        setUpFakeDataForEVL();
        setUpFakeDataForVBVP();
        setUpFakeDataForDPPCV();

        mockMvc.perform(
            get("/api/evappurge/vin/data")
                .queryParam("vin", "00000000000000001")
                .queryParam("testNames", "EONV","ML","EVL","VBVP","DPPCV")
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isMap())
            .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats").isArray())
            .andExpect(jsonPath("$.responseObj.namePlate").value("F-150"))
            .andExpect(jsonPath("$.responseObj.vin").value("00000000000000001"))
            .andExpect(jsonPath("$.responseObj.engine").value("3.5l V6 Gtdi"))
            .andExpect(jsonPath("$.responseObj.fuelTanksize").value(23.9))
            .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[0].evapPurgeTest").value("EONV"))
            .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[0].healthyReadingsCount").value(2))
            .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[0].maxWarningThreshold").value(1.93))
            .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[0].minWarningThreshold").value(0.8))
            .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[0].averageWarningThreshold").value(2.0))
            .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[0].failureThreshold").value(0.7))
            .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[0].anamaloyTrendIndicator").value("Down"))
            .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[0].bins").isArray())
            .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[1].evapPurgeTest").value("ML"))
            .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[1].healthyReadingsCount").value(2))
            .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[1].maxWarningThreshold").value(1.93))
            .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[1].minWarningThreshold").value(0.8))
            .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[1].averageWarningThreshold").value(2.0))
            .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[1].failureThreshold").value(0.7))
            .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[1].anamaloyTrendIndicator").value("Up"))
            .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[1].bins").isArray())
            .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[2].evapPurgeTest").value("EVL"))
            .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[2].healthyReadingsCount").value(2))
            .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[2].maxWarningThreshold").value(1.93))
            .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[2].minWarningThreshold").value(0.8))
            .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[2].averageWarningThreshold").value(2.0))
            .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[2].failureThreshold").value(1.0))
            .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[2].anamaloyTrendIndicator").value("Up"))
            .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[2].bins").isArray())
            .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[3].evapPurgeTest").value("VBVP"))
            .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[3].healthyReadingsCount").value(2))
            .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[3].maxWarningThreshold").value(1.93))
            .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[3].minWarningThreshold").value(0.8))
            .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[3].averageWarningThreshold").value(2.0))
            .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[3].failureThreshold").value(1.0))
            .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[3].anamaloyTrendIndicator").value("Down"))
            .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[3].bins").isArray())
            .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[4].evapPurgeTest").value("DPPCV"))
            .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[4].healthyReadingsCount").value(2))
            .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[4].anomalousReadingsCount").value(1))
            .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[4].maxWarningThreshold").value(1.93))
            .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[4].minWarningThreshold").value(0.8))
            .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[4].averageWarningThreshold").value(2.0))
            .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[4].failureThreshold").value(1.0))
            .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[4].anamaloyTrendIndicator").value("Down"))
            .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[4].bins").isArray());
    }

    @Test
    void apiReturnsCorrectDataForVinAndTestNamesForHev() throws Exception {
        setUpFakeDataForKobl();
        setUpFakeDataForKofsct();
        setUpFakeDataForVkonsct();
        setUpFakeDataForPkonsct();

        mockMvc.perform(
                        get("/api/evappurge/vin/data")
                                .queryParam("vin", "00000000000000001")
                                .queryParam("testNames", "KOBL","KOFSCT","VKONSCT","PKONSCT")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats").isArray())
                .andExpect(jsonPath("$.responseObj.namePlate").value("F-150"))
                .andExpect(jsonPath("$.responseObj.vin").value("00000000000000001"))
                .andExpect(jsonPath("$.responseObj.engine").value("3.5l V6 Gtdi"))
                .andExpect(jsonPath("$.responseObj.fuelTanksize").value(23.9))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[0].evapPurgeTest").value("KOBL"))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[0].healthyReadingsCount").value(2))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[0].maxWarningThreshold").value(1.93))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[0].minWarningThreshold").value(0.8))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[0].averageWarningThreshold").value(2.0))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[0].failureThreshold").value(1.0))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[0].anamaloyTrendIndicator").value("Up"))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[0].bins").isArray())
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[1].evapPurgeTest").value("KOFSCT"))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[1].healthyReadingsCount").value(2))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[1].maxWarningThreshold").value(1.93))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[1].minWarningThreshold").value(0.8))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[1].averageWarningThreshold").value(2.0))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[1].failureThreshold").value(1.0))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[1].anamaloyTrendIndicator").value("Down"))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[1].bins").isArray())
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[2].evapPurgeTest").value("VKONSCT"))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[2].healthyReadingsCount").value(2))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[2].maxWarningThreshold").value(1.93))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[2].minWarningThreshold").value(0.8))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[2].averageWarningThreshold").value(2.0))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[2].failureThreshold").value(1.0))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[2].anamaloyTrendIndicator").value("Down"))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[2].bins").isArray())
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[3].evapPurgeTest").value("PKONSCT"))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[3].healthyReadingsCount").value(2))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[3].maxWarningThreshold").value(1.93))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[3].minWarningThreshold").value(0.8))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[3].averageWarningThreshold").value(2.0))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[3].failureThreshold").value(1.0))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[3].anamaloyTrendIndicator").value("Down"))
                .andExpect(jsonPath("$.responseObj.evapPurgeTestsStats[3].bins").isArray());

    }

    @Test
    void apiReturnsCorrectDataForPowerpacks() throws Exception {
         setUpFakeDataForPowerpacks();

        mockMvc.perform(
            get("/api/evappurge/powerpacks"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isMap())
            .andExpect(jsonPath("$.responseObj.namePlates").isArray())
            .andExpect(jsonPath("$.responseObj.namePlates[0].nameplate").value("BRONCO"))
            .andExpect(jsonPath("$.responseObj.namePlates[0].modelYear").isArray())
            .andExpect(jsonPath("$.responseObj.namePlates[0].modelYear[0].modelyear").value(2022))
            .andExpect(jsonPath("$.responseObj.namePlates[0].modelYear[0].engines").isArray())
            .andExpect(jsonPath("$.responseObj.namePlates[0].modelYear[0].engines[0].engine").value("3.5l V6 Gtdi"))
            .andExpect(jsonPath("$.responseObj.namePlates[0].modelYear[0].engines[0].fuelTankSize").isArray())
            .andExpect(jsonPath("$.responseObj.namePlates[0].modelYear[0].engines[0].fuelTankSize[0].fuelTankSize").value(27.0))
            .andExpect(jsonPath("$.responseObj.namePlates[1].nameplate").value("F-150"))
            .andExpect(jsonPath("$.responseObj.namePlates[1].modelYear").isArray())
            .andExpect(jsonPath("$.responseObj.namePlates[1].modelYear[0].modelyear").value(2019))
            .andExpect(jsonPath("$.responseObj.namePlates[1].modelYear[0].engines").isArray())
            .andExpect(jsonPath("$.responseObj.namePlates[1].modelYear[0].engines[0].engine").value("3.5l V6 Gtdi"))
            .andExpect(jsonPath("$.responseObj.namePlates[1].modelYear[0].engines[0].fuelTankSize").isArray());
    }

    private void setUpFakeDataForPowerpacks(){
        var fuelTankSize1 = testEntityManager.persistAndFlush(new FuelTank(23.9, "Gal"));
        var fuelTankSize2 = testEntityManager.persistAndFlush(new FuelTank(37.0, "Gal"));
        var fuelTankSize3 = testEntityManager.persistAndFlush(new FuelTank(27.0, "Gal"));

        var powerpack1 = testEntityManager.persistAndFlush(new EvapPowerpack(
            "F-150",
            2019,
            "3.5l V6 Gtdi",
            fuelTankSize1
        ));

        var powerpack2 = testEntityManager.persistAndFlush(new EvapPowerpack(
            "F-150",
            2019,
            "3.5l V6 Gtdi",
            fuelTankSize2
        ));

        var powerpack3 = testEntityManager.persistAndFlush(new EvapPowerpack(
            "F-150",
            2019,
            "3.5l V6 Gtdi",
            fuelTankSize3
        ));

        var powerpack4 = testEntityManager.persistAndFlush(new EvapPowerpack(
            "BRONCO",
            2022,
            "3.5l V6 Gtdi",
            fuelTankSize3
        ));
    }
    private void setUpFakeDataForEONV() {

        var fuelTankSize1 = testEntityManager.persistAndFlush(new FuelTank(23.9, "Gal"));


        var powerpack = testEntityManager.persistAndFlush(new EvapPowerpack(
                "F-150",
                2019,
                "3.5l V6 Gtdi",
                fuelTankSize1
        ));


        var eonvPowerpackData = testEntityManager.persistAndFlush(new EvapEonvPowerpackData(powerpack));

        EvapEonvPowerpackDataBin eonvPowerpackDataBinMIS1 = new EvapEonvPowerpackDataBin(
                eonvPowerpackData, IndependentVariable.MILES_IN_SERVICE, 0, 20000, 0.8
        );
        EvapEonvPowerpackDataBin eonvPowerpackDataBinMIS2 = new EvapEonvPowerpackDataBin(
                eonvPowerpackData, IndependentVariable.MILES_IN_SERVICE, 20000, 40000, 1.93
        );

        EvapEonvPowerpackDataBin eonvPowerpackDataBinMIS3 = new EvapEonvPowerpackDataBin(
                eonvPowerpackData, IndependentVariable.MILES_IN_SERVICE, 40000, 60000, 1.53
        );

        testEntityManager.persistAndFlush(eonvPowerpackDataBinMIS1);
        testEntityManager.persistAndFlush(eonvPowerpackDataBinMIS2);
        testEntityManager.persistAndFlush(eonvPowerpackDataBinMIS3);

        eonvPowerpackData.setBins(List.of(eonvPowerpackDataBinMIS1, eonvPowerpackDataBinMIS2, eonvPowerpackDataBinMIS3));
        testEntityManager.persistAndFlush(eonvPowerpackData);

        EvapEonvVehicle vehicle1 = new EvapEonvVehicle(
                "vehicle1", "00000000000000001",
                LocalDate.of(2019, 8, 1),
                LocalDate.now().minusDays(394),
                "Gasoline",
                "Company Car",
                eonvPowerpackData
        );


        testEntityManager.persistAndFlush(vehicle1);

        List.of(
                new EvapPurgeEonvMeasurement(vehicle1,
                        0.6,
                        null,
                        null,
                        null,
                        LocalDateTime.of(LocalDate.now().minusDays(3), LocalTime.MIDNIGHT),
                        85,
                        500,
                        null,
                        2,
                        2,
                        true
                ),
                new EvapPurgeEonvMeasurement(vehicle1,
                        0.7,
                        null,
                        null,
                        null,
                        LocalDateTime.of(LocalDate.now().minusDays(3), LocalTime.MIDNIGHT),
                        85,
                        500,
                        null,
                        2,
                        2,
                        true
                ),
                new EvapPurgeEonvMeasurement(vehicle1,
                        0.6,
                        null,
                        null,
                        null,
                        LocalDateTime.of(LocalDate.now().minusDays(3), LocalTime.MIDNIGHT),
                        85,
                        5000,
                        null,
                        1,
                        1,
                        true
                ),
                new EvapPurgeEonvMeasurement(vehicle1,
                        0.5,
                        null,
                        null,
                        null,
                        LocalDateTime.of(LocalDate.now().minusDays(3), LocalTime.MIDNIGHT),
                        85,
                        4000,
                        null,
                        0,
                        0,
                        true
                )
        ).forEach(testEntityManager::persistAndFlush);

        EvapPowerpackData evapPowerpackData = testEntityManager.persistAndFlush(new EvapPowerpackData(powerpack, 1));
        testEntityManager.persistAndFlush(evapPowerpackData);
    }

    private void setUpFakeDataForML() {

        var fuelTankSize1 = testEntityManager.persistAndFlush(new FuelTank(23.9, "Gal"));


        var powerpack = testEntityManager.persistAndFlush(new EvapPowerpack(
            "F-150",
            2019,
            "3.5l V6 Gtdi",
            fuelTankSize1
        ));


        var mlPowerpackData = testEntityManager.persistAndFlush(new EvapMLPowerpackData(powerpack));

        EvapMLPowerpackDataBin mlPowerpackDataBinMIS1 = new EvapMLPowerpackDataBin(
            mlPowerpackData, IndependentVariable.MILES_IN_SERVICE, 0, 20000, 0.8
        );
        EvapMLPowerpackDataBin mlPowerpackDataBinMIS2 = new EvapMLPowerpackDataBin(
            mlPowerpackData, IndependentVariable.MILES_IN_SERVICE, 20000, 40000, 1.93
        );

        EvapMLPowerpackDataBin mlPowerpackDataBinMIS3 = new EvapMLPowerpackDataBin(
            mlPowerpackData, IndependentVariable.MILES_IN_SERVICE, 40000, 60000, 1.53
        );

        testEntityManager.persistAndFlush(mlPowerpackDataBinMIS1);
        testEntityManager.persistAndFlush(mlPowerpackDataBinMIS2);
        testEntityManager.persistAndFlush(mlPowerpackDataBinMIS3);

        mlPowerpackData.setBins(List.of(mlPowerpackDataBinMIS1, mlPowerpackDataBinMIS2, mlPowerpackDataBinMIS3));
        testEntityManager.persistAndFlush(mlPowerpackData);

        EvapMLVehicle vehicle1 = new EvapMLVehicle(
            "vehicle1", "00000000000000001",
            LocalDate.of(2019, 8, 1),
            LocalDate.now().minusDays(394),
            "Gasoline",
            "Company Car",
            mlPowerpackData
        );


        testEntityManager.persistAndFlush(vehicle1);

        List.of(
            new EvapPurgeMLMeasurement(vehicle1,
                0.6,
                null,
                null,
                null,
                LocalDateTime.of(LocalDate.now().minusDays(3), LocalTime.MIDNIGHT),
                85,
                500,
                null,
                2,
                2,
                true
            ),
            new EvapPurgeMLMeasurement(vehicle1,
                0.7,
                null,
                null,
                null,
                LocalDateTime.of(LocalDate.now().minusDays(3), LocalTime.MIDNIGHT),
                85,
                500,
                null,
                2,
                2,
                true
            ),
            new EvapPurgeMLMeasurement(vehicle1,
                0.6,
                null,
                null,
                null,
                LocalDateTime.of(LocalDate.now().minusDays(3), LocalTime.MIDNIGHT),
                85,
                5000,
                null,
                1,
                1,
                true
            ),
            new EvapPurgeMLMeasurement(vehicle1,
                0.5,
                null,
                null,
                null,
                LocalDateTime.of(LocalDate.now().minusDays(3), LocalTime.MIDNIGHT),
                85,
                4000,
                null,
                0,
                0,
                true
            )
        ).forEach(testEntityManager::persistAndFlush);

        EvapPowerpackData evapPowerpackData = testEntityManager.persistAndFlush(new EvapPowerpackData(powerpack, 1));
        testEntityManager.persistAndFlush(evapPowerpackData);
    }

    private void setUpFakeDataForEVL() {

        var fuelTankSize1 = testEntityManager.persistAndFlush(new FuelTank(23.9, "Gal"));


        var powerpack = testEntityManager.persistAndFlush(new EvapPowerpack(
            "F-150",
            2019,
            "3.5l V6 Gtdi",
            fuelTankSize1
        ));


        var evlPowerpackData = testEntityManager.persistAndFlush(new EvapEvlPowerpackData(powerpack));

        EvapEvlPowerpackDataBin evlPowerpackDataBinMIS1 = new EvapEvlPowerpackDataBin(
            evlPowerpackData, IndependentVariable.MILES_IN_SERVICE, 0, 20000, 0.8
        );
        EvapEvlPowerpackDataBin evlPowerpackDataBinMIS2 = new EvapEvlPowerpackDataBin(
            evlPowerpackData, IndependentVariable.MILES_IN_SERVICE, 20000, 40000, 1.93
        );

        EvapEvlPowerpackDataBin evlPowerpackDataBinMIS3 = new EvapEvlPowerpackDataBin(
            evlPowerpackData, IndependentVariable.MILES_IN_SERVICE, 40000, 60000, 1.53
        );

        testEntityManager.persistAndFlush(evlPowerpackDataBinMIS1);
        testEntityManager.persistAndFlush(evlPowerpackDataBinMIS2);
        testEntityManager.persistAndFlush(evlPowerpackDataBinMIS3);

        evlPowerpackData.setBins(List.of(evlPowerpackDataBinMIS1, evlPowerpackDataBinMIS2, evlPowerpackDataBinMIS3));
        testEntityManager.persistAndFlush(evlPowerpackData);

        EvapEvlVehicle vehicle1 = new EvapEvlVehicle(
            "vehicle1", "00000000000000001",
            LocalDate.of(2019, 8, 1),
            LocalDate.now().minusDays(394),
            "Gasoline",
            "Company Car",
            evlPowerpackData
        );


        testEntityManager.persistAndFlush(vehicle1);

        List.of(
            new EvapPurgeEvlMeasurement(vehicle1,
                0.6,
                null,
                null,
                null,
                LocalDateTime.of(LocalDate.now().minusDays(3), LocalTime.MIDNIGHT),
                85,
                500,
                null,
                2,
                2,
                true
            ),
            new EvapPurgeEvlMeasurement(vehicle1,
                0.7,
                null,
                null,
                null,
                LocalDateTime.of(LocalDate.now().minusDays(3), LocalTime.MIDNIGHT),
                85,
                500,
                null,
                2,
                2,
                true
            ),
            new EvapPurgeEvlMeasurement(vehicle1,
                0.6,
                null,
                null,
                null,
                LocalDateTime.of(LocalDate.now().minusDays(3), LocalTime.MIDNIGHT),
                85,
                5000,
                null,
                1,
                1,
                true
            ),
            new EvapPurgeEvlMeasurement(vehicle1,
                0.5,
                null,
                null,
                null,
                LocalDateTime.of(LocalDate.now().minusDays(3), LocalTime.MIDNIGHT),
                85,
                4000,
                null,
                0,
                0,
                true
            )
        ).forEach(testEntityManager::persistAndFlush);

        EvapPowerpackData evapPowerpackData = testEntityManager.persistAndFlush(new EvapPowerpackData(powerpack, 1));
        testEntityManager.persistAndFlush(evapPowerpackData);
    }

    private void setUpFakeDataForVBVP() {

        var fuelTankSize1 = testEntityManager.persistAndFlush(new FuelTank(23.9, "Gal"));


        var powerpack = testEntityManager.persistAndFlush(new EvapPowerpack(
            "F-150",
            2019,
            "3.5l V6 Gtdi",
            fuelTankSize1
        ));


        var vbvpPowerpackData = testEntityManager.persistAndFlush(new EvapVbvpPowerpackData(powerpack));

        EvapVbvpPowerpackDataBin vbvpPowerpackDataBinMIS1 = new EvapVbvpPowerpackDataBin(
            vbvpPowerpackData, IndependentVariable.MILES_IN_SERVICE, 0, 20000, 0.8
        );
        EvapVbvpPowerpackDataBin vbvpPowerpackDataBinMIS2 = new EvapVbvpPowerpackDataBin(
            vbvpPowerpackData, IndependentVariable.MILES_IN_SERVICE, 20000, 40000, 1.93
        );

        EvapVbvpPowerpackDataBin vbvpPowerpackDataBinMIS3 = new EvapVbvpPowerpackDataBin(
            vbvpPowerpackData, IndependentVariable.MILES_IN_SERVICE, 40000, 60000, 1.53
        );

        testEntityManager.persistAndFlush(vbvpPowerpackDataBinMIS1);
        testEntityManager.persistAndFlush(vbvpPowerpackDataBinMIS2);
        testEntityManager.persistAndFlush(vbvpPowerpackDataBinMIS3);

        vbvpPowerpackData.setBins(List.of(vbvpPowerpackDataBinMIS1, vbvpPowerpackDataBinMIS2, vbvpPowerpackDataBinMIS3));
        testEntityManager.persistAndFlush(vbvpPowerpackData);

        EvapVbvpVehicle vehicle1 = new EvapVbvpVehicle(
            "vehicle1", "00000000000000001",
            LocalDate.of(2019, 8, 1),
            LocalDate.now().minusDays(394),
            "Gasoline",
            "Company Car",
            vbvpPowerpackData
        );


        testEntityManager.persistAndFlush(vehicle1);

        List.of(
            new EvapPurgeVbvpMeasurement(vehicle1,
                0.6,
                null,
                null,
                null,
                LocalDateTime.of(LocalDate.now().minusDays(3), LocalTime.MIDNIGHT),
                85,
                500,
                null,
                2,
                2,
                true
            ),
            new EvapPurgeVbvpMeasurement(vehicle1,
                0.7,
                null,
                null,
                null,
                LocalDateTime.of(LocalDate.now().minusDays(3), LocalTime.MIDNIGHT),
                85,
                500,
                null,
                2,
                2,
                true
            ),
            new EvapPurgeVbvpMeasurement(vehicle1,
                0.6,
                null,
                null,
                null,
                LocalDateTime.of(LocalDate.now().minusDays(3), LocalTime.MIDNIGHT),
                85,
                5000,
                null,
                1,
                1,
                true
            ),
            new EvapPurgeVbvpMeasurement(vehicle1,
                0.5,
                null,
                null,
                null,
                LocalDateTime.of(LocalDate.now().minusDays(3), LocalTime.MIDNIGHT),
                85,
                4000,
                null,
                0,
                0,
                true
            )
        ).forEach(testEntityManager::persistAndFlush);

        EvapPowerpackData evapPowerpackData = testEntityManager.persistAndFlush(new EvapPowerpackData(powerpack, 1));
        testEntityManager.persistAndFlush(evapPowerpackData);
    }

    private void setUpFakeDataForDPPCV() {

        var fuelTankSize1 = testEntityManager.persistAndFlush(new FuelTank(23.9, "Gal"));


        var powerpack = testEntityManager.persistAndFlush(new EvapPowerpack(
            "F-150",
            2019,
            "3.5l V6 Gtdi",
            fuelTankSize1
        ));


        var dppcvPowerpackData = testEntityManager.persistAndFlush(new EvapDppcvPowerpackData(powerpack));

        EvapDppcvPowerpackDataBin dppcvPowerpackDataBinMIS1 = new EvapDppcvPowerpackDataBin(
            dppcvPowerpackData, IndependentVariable.MILES_IN_SERVICE, 0, 20000, 0.8
        );
        EvapDppcvPowerpackDataBin dppcvPowerpackDataBinMIS2 = new EvapDppcvPowerpackDataBin(
            dppcvPowerpackData, IndependentVariable.MILES_IN_SERVICE, 20000, 40000, 1.93
        );

        EvapDppcvPowerpackDataBin dppcvPowerpackDataBinMIS3 = new EvapDppcvPowerpackDataBin(
            dppcvPowerpackData, IndependentVariable.MILES_IN_SERVICE, 40000, 60000, 1.53
        );

        testEntityManager.persistAndFlush(dppcvPowerpackDataBinMIS1);
        testEntityManager.persistAndFlush(dppcvPowerpackDataBinMIS2);
        testEntityManager.persistAndFlush(dppcvPowerpackDataBinMIS3);

        dppcvPowerpackData.setBins(List.of(dppcvPowerpackDataBinMIS1, dppcvPowerpackDataBinMIS2, dppcvPowerpackDataBinMIS3));
        testEntityManager.persistAndFlush(dppcvPowerpackData);

        EvapDppcvVehicle vehicle1 = new EvapDppcvVehicle(
            "vehicle1", "00000000000000001",
            LocalDate.of(2019, 8, 1),
            LocalDate.now().minusDays(394),
            "Gasoline",
            "Company Car",
            dppcvPowerpackData
        );


        testEntityManager.persistAndFlush(vehicle1);

        List.of(
            new EvapPurgeDppcvMeasurement(vehicle1,
                0.6,
                null,
                null,
                null,
                LocalDateTime.of(LocalDate.now().minusDays(3), LocalTime.MIDNIGHT),
                85,
                500,
                null,
                2,
                2,
                true
            ),
            new EvapPurgeDppcvMeasurement(vehicle1,
                0.6,
                null,
                null,
                null,
                LocalDateTime.of(LocalDate.now().minusDays(3), LocalTime.MIDNIGHT),
                85,
                5000,
                null,
                1,
                1,
                true
            ),
            new EvapPurgeDppcvMeasurement(vehicle1,
                0.5,
                null,
                null,
                null,
                LocalDateTime.of(LocalDate.now().minusDays(3), LocalTime.MIDNIGHT),
                85,
                4000,
                null,
                0,
                0,
                true
            )
        ).forEach(testEntityManager::persistAndFlush);

        EvapPowerpackData evapPowerpackData = testEntityManager.persistAndFlush(new EvapPowerpackData(powerpack, 1));
        testEntityManager.persistAndFlush(evapPowerpackData);
    }

    private void setUpFakeDataForKobl() {

        var fuelTankSize1 = testEntityManager.persistAndFlush(new FuelTank(23.9, "Gal"));


        var powerpack = testEntityManager.persistAndFlush(new EvapPowerpack(
                "F-150",
                2019,
                "3.5l V6 Gtdi",
                fuelTankSize1
        ));


        var koblPowerpackData = testEntityManager.persistAndFlush(new EvapKoblPowerpackData(powerpack));

        EvapKoblPowerpackDataBin koblPowerpackDataBinMIS1 = new EvapKoblPowerpackDataBin(
                koblPowerpackData, IndependentVariable.MILES_IN_SERVICE, 0, 20000, 0.8
        );
        EvapKoblPowerpackDataBin koblPowerpackDataBinMIS2 = new EvapKoblPowerpackDataBin(
                koblPowerpackData, IndependentVariable.MILES_IN_SERVICE, 20000, 40000, 1.93
        );

        EvapKoblPowerpackDataBin koblPowerpackDataBinMIS3 = new EvapKoblPowerpackDataBin(
                koblPowerpackData, IndependentVariable.MILES_IN_SERVICE, 40000, 60000, 1.53
        );

        testEntityManager.persistAndFlush(koblPowerpackDataBinMIS1);
        testEntityManager.persistAndFlush(koblPowerpackDataBinMIS2);
        testEntityManager.persistAndFlush(koblPowerpackDataBinMIS3);

        koblPowerpackData.setBins(List.of(koblPowerpackDataBinMIS1, koblPowerpackDataBinMIS2, koblPowerpackDataBinMIS3));
        testEntityManager.persistAndFlush(koblPowerpackData);

        EvapKoblVehicle vehicle1 = new EvapKoblVehicle(
                "vehicle1", "00000000000000001",
                LocalDate.of(2019, 8, 1),
                LocalDate.now().minusDays(394),
                "Gasoline",
                "Company Car",
                koblPowerpackData
        );


        testEntityManager.persistAndFlush(vehicle1);

        List.of(
                new EvapPurgeKoblMeasurement(vehicle1,
                        0.6,
                        null,
                        null,
                        null,
                        LocalDateTime.of(LocalDate.now().minusDays(3), LocalTime.MIDNIGHT),
                        85,
                        500,
                        null,
                        2,
                        2,
                        true
                ),
                new EvapPurgeKoblMeasurement(vehicle1,
                        0.6,
                        null,
                        null,
                        null,
                        LocalDateTime.of(LocalDate.now().minusDays(3), LocalTime.MIDNIGHT),
                        85,
                        5000,
                        null,
                        1,
                        1,
                        true
                ),
                new EvapPurgeKoblMeasurement(vehicle1,
                        0.5,
                        null,
                        null,
                        null,
                        LocalDateTime.of(LocalDate.now().minusDays(3), LocalTime.MIDNIGHT),
                        85,
                        4000,
                        null,
                        0,
                        0,
                        true
                )
        ).forEach(testEntityManager::persistAndFlush);

        EvapPowerpackData evapPowerpackData = testEntityManager.persistAndFlush(new EvapPowerpackData(powerpack, 1));
        testEntityManager.persistAndFlush(evapPowerpackData);
    }

    private void setUpFakeDataForKofsct() {

        var fuelTankSize1 = testEntityManager.persistAndFlush(new FuelTank(23.9, "Gal"));


        var powerpack = testEntityManager.persistAndFlush(new EvapPowerpack(
                "F-150",
                2019,
                "3.5l V6 Gtdi",
                fuelTankSize1
        ));


        var kofsctPowerpackData = testEntityManager.persistAndFlush(new EvapKofsctPowerpackData(powerpack));

        EvapKofsctPowerpackDataBin kofsctPowerpackDataBinMIS1 = new EvapKofsctPowerpackDataBin(
                kofsctPowerpackData, IndependentVariable.MILES_IN_SERVICE, 0, 20000, 0.8
        );
        EvapKofsctPowerpackDataBin kofsctPowerpackDataBinMIS2 = new EvapKofsctPowerpackDataBin(
                kofsctPowerpackData, IndependentVariable.MILES_IN_SERVICE, 20000, 40000, 1.93
        );

        EvapKofsctPowerpackDataBin kofsctPowerpackDataBinMIS3 = new EvapKofsctPowerpackDataBin(
                kofsctPowerpackData, IndependentVariable.MILES_IN_SERVICE, 40000, 60000, 1.53
        );

        testEntityManager.persistAndFlush(kofsctPowerpackDataBinMIS1);
        testEntityManager.persistAndFlush(kofsctPowerpackDataBinMIS2);
        testEntityManager.persistAndFlush(kofsctPowerpackDataBinMIS3);

        kofsctPowerpackData.setBins(List.of(kofsctPowerpackDataBinMIS1, kofsctPowerpackDataBinMIS2, kofsctPowerpackDataBinMIS3));
        testEntityManager.persistAndFlush(kofsctPowerpackData);

        EvapKofsctVehicle vehicle1 = new EvapKofsctVehicle(
                "vehicle1", "00000000000000001",
                LocalDate.of(2019, 8, 1),
                LocalDate.now().minusDays(394),
                "Gasoline",
                "Company Car",
                kofsctPowerpackData
        );


        testEntityManager.persistAndFlush(vehicle1);

        List.of(
                new EvapPurgeKofsctMeasurement(vehicle1,
                        0.6,
                        null,
                        null,
                        null,
                        LocalDateTime.of(LocalDate.now().minusDays(3), LocalTime.MIDNIGHT),
                        85,
                        500,
                        null,
                        2,
                        2,
                        true
                ),
                new EvapPurgeKofsctMeasurement(vehicle1,
                        0.6,
                        null,
                        null,
                        null,
                        LocalDateTime.of(LocalDate.now().minusDays(3), LocalTime.MIDNIGHT),
                        85,
                        5000,
                        null,
                        1,
                        1,
                        true
                ),
                new EvapPurgeKofsctMeasurement(vehicle1,
                        0.5,
                        null,
                        null,
                        null,
                        LocalDateTime.of(LocalDate.now().minusDays(3), LocalTime.MIDNIGHT),
                        85,
                        4000,
                        null,
                        0,
                        0,
                        true
                )
        ).forEach(testEntityManager::persistAndFlush);

        EvapPowerpackData evapPowerpackData = testEntityManager.persistAndFlush(new EvapPowerpackData(powerpack, 1));
        testEntityManager.persistAndFlush(evapPowerpackData);
    }

    private void setUpFakeDataForVkonsct() {

        var fuelTankSize1 = testEntityManager.persistAndFlush(new FuelTank(23.9, "Gal"));


        var powerpack = testEntityManager.persistAndFlush(new EvapPowerpack(
                "F-150",
                2019,
                "3.5l V6 Gtdi",
                fuelTankSize1
        ));


        var vkonsctPowerpackData = testEntityManager.persistAndFlush(new EvapVkonsctPowerpackData(powerpack));

        EvapVkonsctPowerpackDataBin vkonsctPowerpackDataBinMIS1 = new EvapVkonsctPowerpackDataBin(
                vkonsctPowerpackData, IndependentVariable.MILES_IN_SERVICE, 0, 20000, 0.8
        );
        EvapVkonsctPowerpackDataBin vkonsctPowerpackDataBinMIS2 = new EvapVkonsctPowerpackDataBin(
                vkonsctPowerpackData, IndependentVariable.MILES_IN_SERVICE, 20000, 40000, 1.93
        );

        EvapVkonsctPowerpackDataBin vkonsctPowerpackDataBinMIS3 = new EvapVkonsctPowerpackDataBin(
                vkonsctPowerpackData, IndependentVariable.MILES_IN_SERVICE, 40000, 60000, 1.53
        );

        testEntityManager.persistAndFlush(vkonsctPowerpackDataBinMIS1);
        testEntityManager.persistAndFlush(vkonsctPowerpackDataBinMIS2);
        testEntityManager.persistAndFlush(vkonsctPowerpackDataBinMIS3);

        vkonsctPowerpackData.setBins(List.of(vkonsctPowerpackDataBinMIS1, vkonsctPowerpackDataBinMIS2, vkonsctPowerpackDataBinMIS3));
        testEntityManager.persistAndFlush(vkonsctPowerpackData);

        EvapVkonsctVehicle vehicle1 = new EvapVkonsctVehicle(
                "vehicle1", "00000000000000001",
                LocalDate.of(2019, 8, 1),
                LocalDate.now().minusDays(394),
                "Gasoline",
                "Company Car",
                vkonsctPowerpackData
        );


        testEntityManager.persistAndFlush(vehicle1);

        List.of(
                new EvapPurgeVkonsctMeasurement(vehicle1,
                        0.6,
                        null,
                        null,
                        null,
                        LocalDateTime.of(LocalDate.now().minusDays(3), LocalTime.MIDNIGHT),
                        85,
                        500,
                        null,
                        2,
                        2,
                        true
                ),
                new EvapPurgeVkonsctMeasurement(vehicle1,
                        0.6,
                        null,
                        null,
                        null,
                        LocalDateTime.of(LocalDate.now().minusDays(3), LocalTime.MIDNIGHT),
                        85,
                        5000,
                        null,
                        1,
                        1,
                        true
                ),
                new EvapPurgeVkonsctMeasurement(vehicle1,
                        0.5,
                        null,
                        null,
                        null,
                        LocalDateTime.of(LocalDate.now().minusDays(3), LocalTime.MIDNIGHT),
                        85,
                        4000,
                        null,
                        0,
                        0,
                        true
                )
        ).forEach(testEntityManager::persistAndFlush);

        EvapPowerpackData evapPowerpackData = testEntityManager.persistAndFlush(new EvapPowerpackData(powerpack, 1));
        testEntityManager.persistAndFlush(evapPowerpackData);
    }

    private void setUpFakeDataForPkonsct() {

        var fuelTankSize1 = testEntityManager.persistAndFlush(new FuelTank(23.9, "Gal"));


        var powerpack = testEntityManager.persistAndFlush(new EvapPowerpack(
                "F-150",
                2019,
                "3.5l V6 Gtdi",
                fuelTankSize1
        ));


        var pkonsctPowerpackData = testEntityManager.persistAndFlush(new EvapPkonsctPowerpackData(powerpack));

        EvapPkonsctPowerpackDataBin pkonsctPowerpackDataBinMIS1 = new EvapPkonsctPowerpackDataBin(
                pkonsctPowerpackData, IndependentVariable.MILES_IN_SERVICE, 0, 20000, 0.8
        );
        EvapPkonsctPowerpackDataBin pkonsctPowerpackDataBinMIS2 = new EvapPkonsctPowerpackDataBin(
                pkonsctPowerpackData, IndependentVariable.MILES_IN_SERVICE, 20000, 40000, 1.93
        );

        EvapPkonsctPowerpackDataBin pkonsctPowerpackDataBinMIS3 = new EvapPkonsctPowerpackDataBin(
                pkonsctPowerpackData, IndependentVariable.MILES_IN_SERVICE, 40000, 60000, 1.53
        );

        testEntityManager.persistAndFlush(pkonsctPowerpackDataBinMIS1);
        testEntityManager.persistAndFlush(pkonsctPowerpackDataBinMIS2);
        testEntityManager.persistAndFlush(pkonsctPowerpackDataBinMIS3);

        pkonsctPowerpackData.setBins(List.of(pkonsctPowerpackDataBinMIS1, pkonsctPowerpackDataBinMIS2, pkonsctPowerpackDataBinMIS3));
        testEntityManager.persistAndFlush(pkonsctPowerpackData);

        EvapPkonsctVehicle vehicle1 = new EvapPkonsctVehicle(
                "vehicle1", "00000000000000001",
                LocalDate.of(2019, 8, 1),
                LocalDate.now().minusDays(394),
                "Gasoline",
                "Company Car",
                pkonsctPowerpackData
        );


        testEntityManager.persistAndFlush(vehicle1);

        List.of(
                new EvapPurgePkonsctMeasurement(vehicle1,
                        0.6,
                        null,
                        null,
                        null,
                        LocalDateTime.of(LocalDate.now().minusDays(3), LocalTime.MIDNIGHT),
                        85,
                        500,
                        null,
                        2,
                        2,
                        true
                ),
                new EvapPurgePkonsctMeasurement(vehicle1,
                        0.6,
                        null,
                        null,
                        null,
                        LocalDateTime.of(LocalDate.now().minusDays(3), LocalTime.MIDNIGHT),
                        85,
                        5000,
                        null,
                        1,
                        1,
                        true
                ),
                new EvapPurgePkonsctMeasurement(vehicle1,
                        0.5,
                        null,
                        null,
                        null,
                        LocalDateTime.of(LocalDate.now().minusDays(3), LocalTime.MIDNIGHT),
                        85,
                        4000,
                        null,
                        0,
                        0,
                        true
                )
        ).forEach(testEntityManager::persistAndFlush);

        EvapPowerpackData evapPowerpackData = testEntityManager.persistAndFlush(new EvapPowerpackData(powerpack, 1));
        testEntityManager.persistAndFlush(evapPowerpackData);
    }
}
