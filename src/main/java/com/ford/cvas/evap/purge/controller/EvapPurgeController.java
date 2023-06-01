package com.ford.cvas.evap.purge.controller;

import com.ford.cloudnative.base.api.StandardErrorResponse;
import com.ford.cloudnative.base.app.web.swagger.ApiSecurityScheme;
import com.ford.cloudnative.base.app.web.swagger.springfox.annotation.ApiOperationSecurity;
import com.ford.cvas.evap.purge.common.AppConstants;
import com.ford.cvas.evap.purge.exception.ApiRequestException;
import com.ford.cvas.evap.purge.response.finalResponse;
import com.ford.cvas.evap.purge.service.EvapPurgeService;
import com.ford.cvas.evap.purge.service.EvapVehicleStatService;
import com.ford.cvas.evap.purge.service.EvapVinListByTestNameService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.Explode;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/evappurge")
@Validated
public class EvapPurgeController {

    private final EvapPurgeService evapPurgeService;
    private final EvapVehicleStatService vehicleStatService;
    private final EvapVinListByTestNameService evapVinListByTestNameService;


    public EvapPurgeController(EvapPurgeService evapPurgeService,
                               EvapVehicleStatService vehicleStatService,
                               EvapVinListByTestNameService evapVinListByTestNameService) {
        this.evapPurgeService = evapPurgeService;
        this.vehicleStatService = vehicleStatService;
        this.evapVinListByTestNameService = evapVinListByTestNameService;

    }

    //    @ApiOperationSecurity({ApiSecurityScheme.CAT_AUTH_TOKEN, ApiSecurityScheme.APPLICATION_ID}) // AND within
    //  @ApiOperationSecurity({ApiSecurityScheme.AZURE_AD, ApiSecurityScheme.APPLICATION_ID})
    @ApiOperationSecurity({ApiSecurityScheme.ADFS, ApiSecurityScheme.APPLICATION_ID})
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = finalResponse.class),
            @ApiResponse(code = 401 /*default*/, message = "All Errors", response = StandardErrorResponse.class)
    })
    @GetMapping("/vin/data")
    public Object getVinData(
            @RequestParam @Size(min = 17, max = 17, message = "Invalid Vin")  String vin,

            @Parameter(
                    explode = Explode.TRUE,
                    name = "testNames",
                    in = ParameterIn.QUERY,
                    description = "Status values that need to be considered for filter",
                    style = ParameterStyle.FORM,
                    schema = @Schema(
                            type = "string", defaultValue = ""
                            , pattern = "/^[A-Z]+$/i"
                    ),
                    array = @ArraySchema(maxItems = 10)
            )
            @RequestParam(required = false) @Size(max =10)   @Schema(minimum = "1", maximum = "10") List<String> testNames) {
        return finalResponse.builder().responseObj(evapPurgeService.getDataForVin(vin, testNames)).build();
    }


    //    @ApiOperationSecurity({ApiSecurityScheme.CAT_AUTH_TOKEN, ApiSecurityScheme.APPLICATION_ID}) // AND within
    //  @ApiOperationSecurity({ApiSecurityScheme.AZURE_AD, ApiSecurityScheme.APPLICATION_ID})
    @ApiOperationSecurity({ApiSecurityScheme.ADFS, ApiSecurityScheme.APPLICATION_ID})
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = finalResponse.class),
        @ApiResponse(code = 0 /*default*/, message = "All Errors", response = StandardErrorResponse.class)
    })
    @GetMapping(value="/powerpacks")
    public Object getPowerpacks(){
        return finalResponse.builder().responseObj(evapPurgeService.getPowerpacks()).build();
    }


    //    @ApiOperationSecurity({ApiSecurityScheme.CAT_AUTH_TOKEN, ApiSecurityScheme.APPLICATION_ID}) // AND within
    // @ApiOperationSecurity({ApiSecurityScheme.AZURE_AD, ApiSecurityScheme.APPLICATION_ID})
    @ApiOperationSecurity({ApiSecurityScheme.ADFS, ApiSecurityScheme.APPLICATION_ID})
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = finalResponse.class),
            @ApiResponse(code = 0 /*default*/, message = "All Errors", response = StandardErrorResponse.class)
    })
    @GetMapping(value = "/getvehiclecounts")
    public Object getTotalVinCounts(
            @Parameter(
                    explode = Explode.TRUE,
                    name = "testNames",
                    in = ParameterIn.QUERY,
                    description = "Status values that need to be considered for filter",
                    style = ParameterStyle.FORM,

                    schema = @Schema(
                            type = "string", defaultValue = ""
                            , pattern = "/^[A-Z]+$/i"
                    ),
                    array = @ArraySchema(maxItems = 10)
            )
            @RequestParam(required = false) @Size(min = 1, max = 10)   @Schema(minimum = "1", maximum = "10") List<String> testNames,
            @RequestParam(required = false) @Size(min = 0, max = 8)   @Schema(minimum = "1", maximum = "10") String nameplate,
            @RequestParam(required = false) @Size(min = 0, max = 4)   String modelYear,
            @RequestParam(required = false) @Size(min = 0, max = 50)   String engine,
            @RequestParam(required = false) Double fuelTankSize,
            @RequestParam(required = false)  Integer noOfDays
    ) {
        return finalResponse.builder().responseObj(vehicleStatService.getVehicleStats(testNames, nameplate, modelYear, engine, fuelTankSize, noOfDays)).build();
    }


    //    @ApiOperationSecurity({ApiSecurityScheme.CAT_AUTH_TOKEN, ApiSecurityScheme.APPLICATION_ID}) // AND within
    // @ApiOperationSecurity({ApiSecurityScheme.AZURE_AD, ApiSecurityScheme.APPLICATION_ID})
    @ApiOperationSecurity({ApiSecurityScheme.ADFS, ApiSecurityScheme.APPLICATION_ID})
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = finalResponse.class),
            @ApiResponse(code = 0 /*default*/, message = "All Errors", response = StandardErrorResponse.class)
    })
    @GetMapping("/getlistofanomalousvins")
    public Object getListOfVins(

            @Parameter(
                    explode = Explode.TRUE,
                    name = "testNames",
                    in = ParameterIn.QUERY,
                    description = "Status values that need to be considered for filter",
                    style = ParameterStyle.FORM,

                    schema = @Schema(
                            type = "string", defaultValue = ""
                            , pattern = "/^[A-Z]+$/i"
                    ),
                    array = @ArraySchema(maxItems = 10)
            )

            @RequestParam(required = false) @Size(min = 1, max = 10)   @Schema(minimum = "1", maximum = "10") List<String> testNames,
            @RequestParam(required = false)  @Schema(minimum = "1", maximum = "10") String nameplate,
            @RequestParam(required = false)  @Schema(minimum = "1", maximum = "4") String modelYear,
            @RequestParam(required = false) @Size(min = 0, max = 50)  @Schema(minimum = "1", maximum = "50") String engine,
            @RequestParam(required = false)  Double fuelTankSize) {
        int emptyTestCount = 0;
        LinkedHashMap orderedPowerPackDataMap = new LinkedHashMap();
        orderedPowerPackDataMap.put("namePlate", nameplate);
        orderedPowerPackDataMap.put("modelYear", modelYear);
        orderedPowerPackDataMap.put("engine", engine);
        orderedPowerPackDataMap.put("fuelTankSize", fuelTankSize);
        orderedPowerPackDataMap.put("noofDays", 90);

        if (testNames == null || testNames.isEmpty() || modelYear == null || nameplate == null || engine == null || fuelTankSize == null) {
            throw new ApiRequestException(AppConstants.NO_TESTS_ERROR_MESSAGE);
        }
        for (String testname : testNames) {
            List<String> vinsList = evapVinListByTestNameService.getVinsList(testname, nameplate, modelYear, engine, fuelTankSize);
            orderedPowerPackDataMap.put("" + testname, vinsList);
            if (vinsList == null || vinsList.isEmpty()) {
                emptyTestCount++;
            }
        }

        if (emptyTestCount == testNames.size()) {
            Map<String, Object> map = new HashMap<>();
            map.put("messages", new String[]{AppConstants.NO_OUTPUT_MESSAGE});
            return map;
        }
        return finalResponse.builder().responseObj(orderedPowerPackDataMap).build();
    }
}

