package com.ford.cvas.evap.purge.common;

public class AppConstants {
    public static final String NO_OUTPUT_MESSAGE = "There were no results found for the search criteria provided.";
    public static final String NO_TESTS_ERROR_MESSAGE_VIN_SPECIFIC = "Test name is empty. Please enter at least one test name";
    public static final String NO_TESTS_ERROR_MESSAGE = "At least one Test name is mandatory with model year, nameplate, engine, fuelTankSize, No.OfDays";
    public static final String[] AVAILABLE_TEST_NAMES = new String[] {"EONV","DPPCV","VBVP","EVL","KOFSCT","KOBL","VKONSCT","PKONSCT","ML"};
    public static final String[] INVALID_TEST_NAMES_MESSAGE =  {"Invalid Test Name. Valid Test Name Codes are:" ,
            "1. EONV - (Engine Off Natural Vacuum Test)" ,
            "2. ML - (Medium Leak Test)" ,
            "3. VBVP - Vapor Block Vapor Performance Test" ,
            "4. DPPCV - Dual Path Purge Check Valve Test" ,
            "5. EVL - Excessive Vacuum Limit Test" ,
            "6. KOFSCT- Key Off Stuck Closed TPC Test" ,
            "7. KOBL - Key Off Blocked Line Test"  ,
            "8. VKONSCT - Vacuum Key-On Stuck Closed TPC Test" ,
            "9. PKONSCT - Pressure Key-On Stuck Closed TPC Test"};
}
