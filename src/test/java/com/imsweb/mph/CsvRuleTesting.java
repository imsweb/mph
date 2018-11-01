/*
 * Copyright (C) 2018 Information Management Services, Inc.
 */
package com.imsweb.mph;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.opencsv.CSVReaderBuilder;

public class CsvRuleTesting {

    private MphUtils _utils = MphUtils.getInstance();

    @BeforeClass
    public static void setUp() {

        // Create the MphUtils
        MphUtils.initialize(new DefaultHematoDbUtilsProvider());

    }


    private static final String T1_PRIMARY_SITE = "Tumor 1 Primary Site";
    private static final String T1_HISTOLOGY = "Tumor 1 Histology";
    private static final String T1_BEHAVIOR = "Tumor 1 Behavior";
    private static final String T1_LATERALITY = "Tumor 1 Laterality";
    private static final String T1_DX_DATE_YEAR = "Tumor 1 Diagnosis Date Year";
    private static final String T1_DX_DATE_MONTH = "Tumor 1 Diagnosis Date Month";
    private static final String T1_DX_DATE_DAY = "Tumor 1 Diagnosis Date Day";
    private static final String T2_PRIMARY_SITE = "Tumor 2 Primary Site";
    private static final String T2_HISTOLOGY = "Tumor 2 Histology";
    private static final String T2_BEHAVIOR = "Tumor 2 Behavior";
    private static final String T2_LATERALITY = "Tumor 2 Laterality";
    private static final String T2_DX_DATE_YEAR = "Tumor 2 Diagnosis Date Year";
    private static final String T2_DX_DATE_MONTH = "Tumor 2 Diagnosis Date Month";
    private static final String T2_DX_DATE_DAY = "Tumor 2 Diagnosis Date Day";
    private static final String EXPECTED_RESULT = "Expected Result";
    private static final String EXPECTED_GROUP = "Expected Group";
    private static final String EXPECTED_RULE_NUM = "Expected Rule #";

    private static List<Map<String, String>> loadCsvTestFile(String fileName) {

        // Tumor 1 Primary Site,
        // Tumor 1 HistologyIcd03,
        // Tumor 1 BehaviorIcd03,
        // Tumor 1 Laterality ,
        // Tumor 1 Date of Diagnosis Year,
        // Tumor 1 Date of Diagnosis Month,
        // Tumor 1 Date of Diagnosis Day,
        // Tumor 2 Primary Site,
        // Tumor 2 HistologyIcd03,
        // Tumor 2 BehaviorIcd03,
        // Tumor 2 Laterality,
        // Tumor 2 Date of Diagnosis Year,
        // Tumor 2 Date of Diagnosis Month,
        // Tumor 2 Date of Diagnosis Day,
        // Expected Result,
        // Expected Group,
        // Expected Rule #

        // C509,8530,3,1,2018,1,1,C509,8530,3,1,2028,1,1,Single Primary,2018 Breast,M4

        List<Map<String, String>> retval = new ArrayList<>(); 

        try (Reader reader = new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName), StandardCharsets.US_ASCII)) {
            for (String[] row : new CSVReaderBuilder(reader).withSkipLines(1).build().readAll()) {
                if (row[0].length() > 0) {

                    Map<String, String> newMap = new HashMap<String, String>();
                    newMap.put(T1_PRIMARY_SITE, row[0].trim());  
                    newMap.put(T1_HISTOLOGY, row[1].trim());  
                    newMap.put(T1_BEHAVIOR, row[2].trim());  
                    newMap.put(T1_LATERALITY, row[3].trim()); 
                    newMap.put(T1_DX_DATE_YEAR, row[4].trim());
                    newMap.put(T1_DX_DATE_MONTH, row[5].trim()); 
                    newMap.put(T1_DX_DATE_DAY, row[6].trim());  
                    newMap.put(T2_PRIMARY_SITE, row[7].trim()); 
                    newMap.put(T2_HISTOLOGY, row[8].trim());
                    newMap.put(T2_BEHAVIOR, row[9].trim());  
                    newMap.put(T2_LATERALITY, row[10].trim()); 
                    newMap.put(T2_DX_DATE_YEAR, row[11].trim());
                    newMap.put(T2_DX_DATE_MONTH, row[12].trim());
                    newMap.put(T2_DX_DATE_DAY, row[13].trim());  
                    newMap.put(EXPECTED_RESULT, row[14].trim()); 
                    newMap.put(EXPECTED_GROUP, row[15].trim());  
                    newMap.put(EXPECTED_RULE_NUM, row[16].trim());

                    retval.add(newMap);
                }
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        return retval;
    }


    private static void setInputs(Map<String, String> record, MphInput i1, MphInput i2) {
        i1.setPrimarySite(record.get(T1_PRIMARY_SITE));
        i1.setHistologyIcdO3(record.get(T1_HISTOLOGY));
        i1.setBehaviorIcdO3(record.get(T1_BEHAVIOR));
        i1.setLaterality(record.get(T1_LATERALITY));
        i1.setDateOfDiagnosisYear(record.get(T1_DX_DATE_YEAR));
        i1.setDateOfDiagnosisMonth(record.get(T1_DX_DATE_MONTH));
        i1.setDateOfDiagnosisDay(record.get(T1_DX_DATE_DAY));

        i2.setPrimarySite(record.get(T2_PRIMARY_SITE));
        i2.setHistologyIcdO3(record.get(T2_HISTOLOGY));
        i2.setBehaviorIcdO3(record.get(T2_BEHAVIOR));
        i2.setLaterality(record.get(T2_LATERALITY));
        i2.setDateOfDiagnosisYear(record.get(T2_DX_DATE_YEAR));
        i2.setDateOfDiagnosisMonth(record.get(T2_DX_DATE_MONTH));
        i2.setDateOfDiagnosisDay(record.get(T2_DX_DATE_DAY));
    }

    private static MphUtils.MpResult getExpectedResult(Map<String, String> record, String fileName, int iLineCount) {
        MphUtils.MpResult retval = MphUtils.MpResult.INVALID_INPUT;
        if (record.get(EXPECTED_RESULT).equals("Single Primary")) {
            retval = MphUtils.MpResult.SINGLE_PRIMARY;
        } else if (record.get(EXPECTED_RESULT).equals("Multiple Primaries")) {
            retval = MphUtils.MpResult.MULTIPLE_PRIMARIES;
        } else if (record.get(EXPECTED_RESULT).equals("Questionable")) {
            retval = MphUtils.MpResult.QUESTIONABLE;
        } else {
            System.out.println("ERROR in file " + fileName + " - Expected Result Row " + iLineCount);
        }
        return retval;
    }


    private void testCsvFile(String fileName) {
        List<Map<String, String>> listRecs = loadCsvTestFile(fileName);
        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;
        int iLineCount = 0;

        for (Map<String, String> record : listRecs) {

            setInputs(record, i1, i2);
            output = _utils.computePrimaries(i1, i2);

            if (output.getResult() == MphUtils.MpResult.INVALID_INPUT) {
                Assert.fail(fileName + " - Invalid Input: " + output.getReason());
            }

            // C509,8530,3,1,2018,1,1,C509,8530,3,1,2028,1,1,Single Primary,2018 Breast,M4

            MphUtils.MpResult expResult = getExpectedResult(record, fileName, iLineCount);

            String msg = fileName + " - Expected Results Differ";
            Assert.assertEquals(msg, expResult, output.getResult());
            Assert.assertEquals(record.get(EXPECTED_RULE_NUM), output.getStep());
            Assert.assertEquals(record.get(EXPECTED_GROUP), output.getGroupId());

            iLineCount++;
        }
    }

    @Test
    public void testCsvCases() {

        testCsvFile("breast_test_cases.csv");
        testCsvFile("urinary_test_cases.csv");

    }


}



