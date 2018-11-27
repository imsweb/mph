/*
 * Copyright (C) 2018 Information Management Services, Inc.
 */
package com.imsweb.mph;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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


    private static final String FIELD_T1_PRIMARY_SITE = "Tumor 1 Primary Site";
    private static final String FIELD_T1_HISTOLOGY = "Tumor 1 Histology";
    private static final String FIELD_T1_BEHAVIOR = "Tumor 1 Behavior";
    private static final String FIELD_T1_LATERALITY = "Tumor 1 Laterality";
    private static final String FIELD_T1_DX_DATE_YEAR = "Tumor 1 Diagnosis Date Year";
    private static final String FIELD_T1_DX_DATE_MONTH = "Tumor 1 Diagnosis Date Month";
    private static final String FIELD_T1_DX_DATE_DAY = "Tumor 1 Diagnosis Date Day";
    private static final String FIELD_T2_PRIMARY_SITE = "Tumor 2 Primary Site";
    private static final String FIELD_T2_HISTOLOGY = "Tumor 2 Histology";
    private static final String FIELD_T2_BEHAVIOR = "Tumor 2 Behavior";
    private static final String FIELD_T2_LATERALITY = "Tumor 2 Laterality";
    private static final String FIELD_T2_DX_DATE_YEAR = "Tumor 2 Diagnosis Date Year";
    private static final String FIELD_T2_DX_DATE_MONTH = "Tumor 2 Diagnosis Date Month";
    private static final String FIELD_T2_DX_DATE_DAY = "Tumor 2 Diagnosis Date Day";
    private static final String FIELD_EXPECTED_RESULT = "Expected Result";
    private static final String FIELD_EXPECTED_GROUP = "Expected Group";
    private static final String FIELD_EXPECTED_RULE_NUM = "Expected Rule #";


    private static Map<String, String> GROUP_NAMES;
    private static Map<MphUtils.MpResult, String> EXPECTED_RESULTS;
    private static Map<String, String> RULES_UPDATED;

    private static void loadMappings() {
        GROUP_NAMES = new HashMap<String, String>();
        GROUP_NAMES.put("2018 Breast", MphConstants.MP_2018_BREAST_GROUP_ID);
        GROUP_NAMES.put("2018 Colon", MphConstants.MP_2018_COLON_GROUP_ID);
        GROUP_NAMES.put("2018 Cutaneous Melanoma", MphConstants.MP_2018_CUTANEOUS_MELANOMA_GROUP_ID);
        GROUP_NAMES.put("2018 Head And Neck", MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID);
        GROUP_NAMES.put("2018 Kidney", MphConstants.MP_2018_KIDNEY_GROUP_ID);
        GROUP_NAMES.put("2018 Lung", MphConstants.MP_2018_LUNG_GROUP_ID);
        GROUP_NAMES.put("2018 MalignantCNSAndPeripheralNerves", MphConstants.MP_2018_MALIGNANT_CNS_AND_PERIPHERAL_NERVES_GROUP_ID);
        GROUP_NAMES.put("2018 NonMalignantCNSTumors", MphConstants.MP_2018_NON_MALIGNANT_CNS_TUMORS_GROUP_ID);
        GROUP_NAMES.put("2018 Other Sites", MphConstants.MP_2018_OTHER_SITES_GROUP_ID);
        GROUP_NAMES.put("2018 Urinary", MphConstants.MP_2018_URINARY_GROUP_ID);

        EXPECTED_RESULTS = new HashMap<MphUtils.MpResult, String>();
        EXPECTED_RESULTS.put(MphUtils.MpResult.SINGLE_PRIMARY, "Single Primary");
        EXPECTED_RESULTS.put(MphUtils.MpResult.MULTIPLE_PRIMARIES, "Multiple Primaries");
        EXPECTED_RESULTS.put(MphUtils.MpResult.QUESTIONABLE, "Questionable");
        EXPECTED_RESULTS.put(MphUtils.MpResult.INVALID_INPUT, "Invalid Input");

        RULES_UPDATED = new HashMap<String, String>();
        RULES_UPDATED.put(MphConstants.MP_2018_BREAST_GROUP_ID, MphConstants.BREAST_2018_AS_OF_DATE);
        RULES_UPDATED.put(MphConstants.MP_2018_COLON_GROUP_ID, MphConstants.COLON_2018_AS_OF_DATE);
        RULES_UPDATED.put(MphConstants.MP_2018_CUTANEOUS_MELANOMA_GROUP_ID, MphConstants.CUT_MELANOMA_2018_AS_OF_DATE);
        RULES_UPDATED.put(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, MphConstants.HEAD_AND_NECK_2018_AS_OF_DATE);
        RULES_UPDATED.put(MphConstants.MP_2018_KIDNEY_GROUP_ID, MphConstants.KIDNEY_2018_AS_OF_DATE);
        RULES_UPDATED.put(MphConstants.MP_2018_LUNG_GROUP_ID, MphConstants.LUNG_2018_AS_OF_DATE);
        RULES_UPDATED.put(MphConstants.MP_2018_MALIGNANT_CNS_AND_PERIPHERAL_NERVES_GROUP_ID, MphConstants.MALIGNANT_CNS_2018_AS_OF_DATE);
        RULES_UPDATED.put(MphConstants.MP_2018_NON_MALIGNANT_CNS_TUMORS_GROUP_ID, MphConstants.NON_MALIGNANT_CNS_2018_AS_OF_DATE);
        RULES_UPDATED.put(MphConstants.MP_2018_OTHER_SITES_GROUP_ID, MphConstants.OTHER_STIES_2018_AS_OF_DATE);
        RULES_UPDATED.put(MphConstants.MP_2018_URINARY_GROUP_ID, MphConstants.URINARY_2018_AS_OF_DATE);

    }




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
                    newMap.put(FIELD_T1_PRIMARY_SITE, row[0].trim());
                    newMap.put(FIELD_T1_HISTOLOGY, row[1].trim());
                    newMap.put(FIELD_T1_BEHAVIOR, row[2].trim());
                    newMap.put(FIELD_T1_LATERALITY, row[3].trim());
                    newMap.put(FIELD_T1_DX_DATE_YEAR, row[4].trim());
                    newMap.put(FIELD_T1_DX_DATE_MONTH, row[5].trim());
                    newMap.put(FIELD_T1_DX_DATE_DAY, row[6].trim());
                    newMap.put(FIELD_T2_PRIMARY_SITE, row[7].trim());
                    newMap.put(FIELD_T2_HISTOLOGY, row[8].trim());
                    newMap.put(FIELD_T2_BEHAVIOR, row[9].trim());
                    newMap.put(FIELD_T2_LATERALITY, row[10].trim());
                    newMap.put(FIELD_T2_DX_DATE_YEAR, row[11].trim());
                    newMap.put(FIELD_T2_DX_DATE_MONTH, row[12].trim());
                    newMap.put(FIELD_T2_DX_DATE_DAY, row[13].trim());
                    newMap.put(FIELD_EXPECTED_RESULT, row[14].trim());
                    newMap.put(FIELD_EXPECTED_GROUP, row[15].trim());
                    newMap.put(FIELD_EXPECTED_RULE_NUM, row[16].trim());

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
        i1.setPrimarySite(record.get(FIELD_T1_PRIMARY_SITE));
        i1.setHistologyIcdO3(record.get(FIELD_T1_HISTOLOGY));
        i1.setBehaviorIcdO3(record.get(FIELD_T1_BEHAVIOR));
        i1.setLaterality(record.get(FIELD_T1_LATERALITY));
        i1.setDateOfDiagnosisYear(record.get(FIELD_T1_DX_DATE_YEAR));
        i1.setDateOfDiagnosisMonth(record.get(FIELD_T1_DX_DATE_MONTH));
        i1.setDateOfDiagnosisDay(record.get(FIELD_T1_DX_DATE_DAY));

        i2.setPrimarySite(record.get(FIELD_T2_PRIMARY_SITE));
        i2.setHistologyIcdO3(record.get(FIELD_T2_HISTOLOGY));
        i2.setBehaviorIcdO3(record.get(FIELD_T2_BEHAVIOR));
        i2.setLaterality(record.get(FIELD_T2_LATERALITY));
        i2.setDateOfDiagnosisYear(record.get(FIELD_T2_DX_DATE_YEAR));
        i2.setDateOfDiagnosisMonth(record.get(FIELD_T2_DX_DATE_MONTH));
        i2.setDateOfDiagnosisDay(record.get(FIELD_T2_DX_DATE_DAY));
    }

    private boolean testCsvFile(String fileName) {
        boolean fileRetval = true;
        boolean rowRetval = true;
        List<Map<String, String>> listRecs = loadCsvTestFile(fileName);
        List<String> outputLines = new ArrayList<String>();
        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;
        String outLine = "";

        //outputLines.add("File: " + fileName + " ------------------------------");
        //outputLines.add("Row,Expected Result,Expected Group,Expected Rule #,Actual Result,Actual Group,Actual Rule #,Match,Reason");

        outLine = "Tumor 1 Primary Site,";
        outLine += "Tumor 1 HistologyIcd03,";
        outLine += "Tumor 1 BehaviorIcd03,";
        outLine += "Tumor 1 Laterality,";
        outLine += "Tumor 1 Date of Diagnosis ? Year,";
        outLine += "Tumor 1 Date of Diagnosis ? Month,";
        outLine += "Tumor 1 Date of Diagnosis ? Day,";
        outLine += "Tumor 2 Primary Site,";
        outLine += "Tumor 2 HistologyIcd03,";
        outLine += "Tumor 2 BehaviorIcd03,";
        outLine += "Tumor 2 Laterality,";
        outLine += "Tumor 2 Date of Diagnosis ? Year,";
        outLine += "Tumor 2 Date of Diagnosis ? Month,";
        outLine += "Tumor 2 Date of Diagnosis ? Day,";
        outLine += "Expected Result,";
        outLine += "Expected Group,";
        outLine += "Expected Rule #,";
        outLine += ",";
        outLine += "Actual Result,";
        outLine += "Actual Group,";
        outLine += "Last Updated,";
        outLine += "Actual Rule #,";
        outLine += "Match,";
        outLine += "Reason";
        outputLines.add(outLine);


        for (Map<String, String> record : listRecs) {
            setInputs(record, i1, i2);
            output = _utils.computePrimaries(i1, i2);

            String expResult = record.get(FIELD_EXPECTED_RESULT);
            String expGroup = GROUP_NAMES.get(record.get(FIELD_EXPECTED_GROUP));
            String expRuleNum = record.get(FIELD_EXPECTED_RULE_NUM);

            String outputResult = EXPECTED_RESULTS.get(output.getResult());
            if (outputResult == null) outputResult = "";

            rowRetval = true;
            if ((!expResult.equals(outputResult)) ||
                (!expRuleNum.equals(output.getStep())) ||
                (!expGroup.equals(output.getGroupId()))) {
                rowRetval = false;
            }

            String strReason = output.getReason();
            String matchText = "Yes";
            if (!rowRetval) matchText = "No";

            outLine = record.get(FIELD_T1_PRIMARY_SITE) + ",";
            outLine += record.get(FIELD_T1_HISTOLOGY) + ",";
            outLine += record.get(FIELD_T1_BEHAVIOR) + ",";
            outLine += record.get(FIELD_T1_LATERALITY) + ",";
            outLine += record.get(FIELD_T1_DX_DATE_YEAR) + ",";
            outLine += record.get(FIELD_T1_DX_DATE_MONTH) + ",";
            outLine += record.get(FIELD_T1_DX_DATE_DAY) + ",";
            outLine += record.get(FIELD_T2_PRIMARY_SITE) + ",";
            outLine += record.get(FIELD_T2_HISTOLOGY) + ",";
            outLine += record.get(FIELD_T2_BEHAVIOR) + ",";
            outLine += record.get(FIELD_T2_LATERALITY) + ",";
            outLine += record.get(FIELD_T2_DX_DATE_YEAR) + ",";
            outLine += record.get(FIELD_T2_DX_DATE_MONTH) + ",";
            outLine += record.get(FIELD_T2_DX_DATE_DAY) + ",";
            outLine += expResult + ",";
            outLine += expGroup + ",";
            outLine += expRuleNum + ",";
            outLine += ",";
            outLine += outputResult + ",";
            outLine += output.getGroupId() + ",";
            outLine += RULES_UPDATED.get(output.getGroupId()) + ",";
            outLine += output.getStep() + ",";
            outLine += matchText + ",";
            outLine += strReason;

            outputLines.add(outLine);

            if (!rowRetval) fileRetval = false;
        }

        WriteComparisonFile(fileName, outputLines);

        System.out.println("File: " + fileName + "     Result: " + (fileRetval ? "Passed" : "Failed"));


        return fileRetval;
    }

    private void WriteComparisonFile(String inputFileName, List<String> outputLines) {

        BufferedWriter outputWriter = null;
        try {
            String outputFileName = "src\\test\\resources\\" + inputFileName;
            /*
            int sepPos = inputFileName.lastIndexOf(".csv");
            if (sepPos >= 0) {
                outputFileName = inputFileName.substring(0, sepPos);
            }
            outputFileName = "src\\test\\resources\\" + outputFileName + "_results.csv";
            */

            File outputFile = new File(outputFileName);
            outputWriter = new BufferedWriter(new FileWriter(outputFile));

            for (int i=0; i < outputLines.size(); i++) {
                outputWriter.write(outputLines.get(i));
                outputWriter.newLine();
            }

            outputWriter.close();

        } catch (IOException e) {
        }
    }



    @Test
    public void testCsvCases() {
        boolean retval = true;
        final boolean PERFORM_LOOP = false;

        loadMappings();

        if (!testCsvFile("MPH_test_cases.csv")) retval = false;

        Assert.assertEquals("CSV Tests Failed", true, retval);

    }


    /*
    @Test
    public void testCsvCases() {
        boolean retval = true;
        final boolean PERFORM_LOOP = false;

        loadMappings();

        int iLoopCount = 1;
        if (PERFORM_LOOP) iLoopCount = 150000;

        for (int i=0; i < iLoopCount; i++) {
            if (!testCsvFile("breast_test_cases_02.csv")) retval = false;
            //if (!testCsvFile("lung_test_cases_01.csv")) retval = false;
            //if (!testCsvFile("urinary_test_cases_01.csv")) retval = false;

            "MPH_test_cases.csv""


            if (PERFORM_LOOP) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {}
            }
        }

        Assert.assertEquals("CSV Tests Failed", true, retval);

    }
    */


}



