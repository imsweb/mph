/*
 * Copyright (C) 2021 Information Management Services, Inc.
 */
package com.imsweb.mph;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.imsweb.mph.MphUtils.MpResult;

public class Mph2021RuleTests {

    private MphUtils _utils = MphUtils.getInstance();

    @BeforeClass
    public static void setUp() {
        MphUtils.initialize(new DefaultHematoDbUtilsProvider());
    }

    @Test
    public void test2021Breast() {
        // Breast Histology Coding Rules
        // C500-C506, C508-C509
        // (Excludes lymphoma and leukemia M9590 – M9992 and Kaposi sarcoma M9140)

        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;
        String ruleStepToTest;
        int ruleCountToTest;

        // Rule M4 Abstract multiple primaries when there are separate, non-contiguous tumors in sites with ICD-O site codes that differ at the
        // second (CXxx) and/or third characters (CxXx).
        // This cannot be tested. The Breast rule is only run on sites with C50_.
        /*
        ruleStepToTest = "M4";
        ruleCountToTest = 1;
        i1.setPrimarySite("C500");
        i1.setHistologyIcdO3("8530");
        i1.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2021");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8530");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2021");
        // Questionable
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setDateOfDiagnosisMonth("4");
        i2.setDateOfDiagnosisMonth("5");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertTrue(output.getReason().contains("second (C?xx) and/or third (Cx?x) character"));
        // Does not apply
        i1.setPrimarySite("C500");
        i2.setPrimarySite("C500");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        */

        // Rule M5 Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than five years after the
        // original diagnosis or last recurrence.
        ruleStepToTest = "M5";
        ruleCountToTest = 2;
        i1.setPrimarySite("C500");
        i1.setHistologyIcdO3("8720");
        i1.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2014");
        i1.setLaterality("1");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8780");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2021");
        i2.setLaterality("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2021_BREAST_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply
        i1.setDateOfDiagnosisYear("2021");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2021_BREAST_GROUP_ID, output.getGroupId());
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M6 Abstract a single primary when there is inflammatory carcinoma in:
        // - Multiple quadrants of same breast OR
        // - Bilateral breasts
        ruleStepToTest = "M6";
        ruleCountToTest = 3;
        i1.setPrimarySite("C500");
        i1.setHistologyIcdO3("8530");
        i1.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2021");
        i1.setLaterality("1");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8530");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2018");
        i2.setLaterality("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2021_BREAST_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertTrue(output.getReason().contains("carcinoma"));
        i1.setLaterality("4");
        i2.setLaterality("4");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2021_BREAST_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertTrue(output.getReason().contains("carcinoma"));
        // Does not apply
        i2.setBehaviorIcdO3("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2021_BREAST_GROUP_ID, output.getGroupId());
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M7 Abstract multiple primaries when there is bilateral breast cancer (both right and left breast).
        ruleStepToTest = "M7";
        ruleCountToTest = 4;
        i1.setPrimarySite("C500");
        i1.setHistologyIcdO3("8730");
        i1.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2021");
        i1.setLaterality("1");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8730");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2021");
        i2.setLaterality("2");
        // Multiple at M6
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2021_BREAST_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertTrue(output.getReason().contains("Tumors on both sides"));

        // Rule M8 Abstract a single primary when the diagnosis is Paget disease with underlying in situ or invasive carcinoma NST (duct/ductal).
        // 8541/3, 8543/2, 8543/3 AND 8541/3, 8543/2, 8543/3
        //        ruleStepToTest = "M8";
        //        ruleCountToTest = 5;
        //        i1.setPrimarySite("C500");
        //        i1.setHistologyIcdO3("8543");
        //        i1.setBehaviorIcdO3("2");
        //        i1.setDateOfDiagnosisYear("2021");
        //        i1.setDateOfDiagnosisMonth("1");
        //        i1.setLaterality("1");
        //        i2.setPrimarySite("C509");
        //        i2.setHistologyIcdO3("8541");
        //        i2.setBehaviorIcdO3("3");
        //        i2.setDateOfDiagnosisYear("2021");
        //        i2.setDateOfDiagnosisMonth("1");
        //        i2.setLaterality("1");
        //        output = _utils.computePrimaries(i1, i2);
        //        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        //        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        //        Assert.assertTrue(output.getReason().contains("Paget"));
        //        Assert.assertEquals(ruleStepToTest, output.getStep());
        //
        //        //Rule M9 Abstract multiple primaries when the diagnosis is Paget disease with synchronous/simultaneous underlying tumor which is NOT duct.
        //        // 8541/3, 8543/2, 8543/3 AND 8541/3, 8543/2, 8543/3
        //        ruleStepToTest = "M9";
        //        ruleCountToTest = 6;
        //        i1.setPrimarySite("C500");
        //        i1.setHistologyIcdO3("8543");
        //        i1.setBehaviorIcdO3("2");
        //        i1.setDateOfDiagnosisYear("2021");
        //        i1.setDateOfDiagnosisMonth("1");
        //        i1.setLaterality("1");
        //        i2.setPrimarySite("C509");
        //        i2.setHistologyIcdO3("8520");
        //        i2.setBehaviorIcdO3("3");
        //        i2.setDateOfDiagnosisYear("2021");
        //        i2.setDateOfDiagnosisMonth("1");
        //        i2.setLaterality("1");
        //        output = _utils.computePrimaries(i1, i2);
        //        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        //        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        //        Assert.assertTrue(output.getReason().contains("Paget"));
        //        Assert.assertEquals(ruleStepToTest, output.getStep());

        //Rule M10 Abstract a single primary when multiple tumors of the same behavior are carcinoma NST/duct and lobular
        //- Both/all tumors may be a mixture of carcinoma NST/duct and lobular 8522 OR
        //- One tumor may be duct and another tumor lobular OR
        //- One tumor may be mixed duct and lobular 8522, the other tumor either duct or lobular
        ruleStepToTest = "M10";
        ruleCountToTest = 5;
        i1.setPrimarySite("C500");
        i1.setHistologyIcdO3("8500");
        i1.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("1");
        i1.setLaterality("1");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8520");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("2");
        i2.setLaterality("1");
        output = _utils.computePrimaries(i1, i2);
        //2018 rule doesn't require same behavior
        Assert.assertEquals(MphConstants.MP_2018_BREAST_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisYear("2021");
        output = _utils.computePrimaries(i1, i2);
        //2021 rule requires same behavior
        Assert.assertEquals(MphConstants.MP_2021_BREAST_GROUP_ID, output.getGroupId());
        Assert.assertTrue(output.getAppliedRules().size() > ruleCountToTest);
        i1.setBehaviorIcdO3("2");
        i2.setBehaviorIcdO3("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2021_BREAST_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());

        // Rule M11 Abstract a single primary when any of the following conditions are met in the same breast:
        // - DCIS subsequent to a diagnosis of mixed DCIS AND:
        //  Lobular carcinoma in situ 8522/2 OR
        //  In situ Paget 8543/2 OR
        //  Invasive Paget 8543/3 OR
        //  Other in situ 8500/2 (prior to 2018, DCIS and other in situ was coded 8523/2)
        //  Invasive carcinoma NST/duct subsequent to a diagnosis of mixed invasive carcinoma NST/duct AND:
        //  Invasive lobular 8522/3 OR
        //  Invasive Paget 8541/3 OR
        //  Other invasive carcinoma 8523/3
        ruleStepToTest = "M11";
        ruleCountToTest = 6;
        i1.setPrimarySite("C504");
        i1.setHistologyIcdO3("8543");
        i1.setBehaviorIcdO3("2");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2019");
        i1.setDateOfDiagnosisMonth("1");
        i2.setPrimarySite("C506");
        i2.setHistologyIcdO3("8500");
        i2.setBehaviorIcdO3("2");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisMonth("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2021_BREAST_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());

        // Rule M12 Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3 of Table 3 in the Equivalent Terms and Definitions. Timing is irrelevant.
        ruleStepToTest = "M12";
        ruleCountToTest = 7;
        i1.setPrimarySite("C500");
        i1.setHistologyIcdO3("8519");
        i1.setBehaviorIcdO3("2");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8512");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("");
        output = _utils.computePrimaries(i1, i2);
        //8512 was not in table 3
        Assert.assertEquals(MphConstants.MP_2018_BREAST_GROUP_ID, output.getGroupId());
        Assert.assertTrue(output.getAppliedRules().size() > ruleCountToTest);
        i2.setDateOfDiagnosisYear("2021");
        output = _utils.computePrimaries(i1, i2);
        //8512 is in table 3 after 2021
        Assert.assertEquals(MphConstants.MP_2021_BREAST_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());

        //9130/3 was not in table 3 in 2018 rules
        i1.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisYear("2018");
        i1.setHistologyIcdO3("9130");
        i1.setBehaviorIcdO3("3");
        i2.setHistologyIcdO3("8850");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2018_BREAST_GROUP_ID, output.getGroupId());
        Assert.assertTrue(output.getAppliedRules().size() > ruleCountToTest);
        //it is added in table 3 in 2021
        i1.setDateOfDiagnosisYear("2021");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2021_BREAST_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());

        // Rule M13 Abstract a single primary when synchronous, separate/non-contiguous tumors are on the same row in Table 3 in the Equivalent Terms and Definitions. Timing is irrelevant.
        ruleStepToTest = "M13";
        ruleCountToTest = 8;
        i1.setPrimarySite("C500");
        i1.setHistologyIcdO3("8512");
        i1.setBehaviorIcdO3("2");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("2");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8510");
        i2.setBehaviorIcdO3("2");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("2");
        //8512 was not in the row of 8510 in 2018
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2018_BREAST_GROUP_ID, output.getGroupId());
        Assert.assertTrue(output.getAppliedRules().size() > ruleCountToTest);
        i1.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisYear("2021");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2021_BREAST_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertTrue(output.getReason().contains("same row in Table 3"));

        //9130/3 was not in table 3 for 8800 row
        i1.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisYear("2018");
        i1.setHistologyIcdO3("9130");
        i1.setBehaviorIcdO3("3");
        i2.setHistologyIcdO3("8800");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2018_BREAST_GROUP_ID, output.getGroupId());
        Assert.assertTrue(output.getAppliedRules().size() > ruleCountToTest);
        i1.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisYear("2021");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2021_BREAST_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertTrue(output.getReason().contains("same row in Table 3"));

        // Rule M14 Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 3 in the Equivalent Terms and Definitions. Timing is irrelevant.
        ruleStepToTest = "M14";
        ruleCountToTest = 9;
        i1.setPrimarySite("C500");
        i1.setHistologyIcdO3("8510");
        i1.setBehaviorIcdO3("2");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2019");
        i1.setDateOfDiagnosisMonth("");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8575");
        i2.setBehaviorIcdO3("2");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisMonth("");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2021_BREAST_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());

        // Rule M15 Abstract a single primary (the invasive) when an in situ tumor is diagnosed after an invasive tumor in the same breast.
        ruleStepToTest = "M15";
        ruleCountToTest = 10;
        i1.setPrimarySite("C500");
        i1.setHistologyIcdO3("8401");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2019");
        i1.setDateOfDiagnosisMonth("");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8401");
        i2.setBehaviorIcdO3("2");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisMonth("");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertTrue(output.getReason().contains("in situ tumor diagnosed following an invasive tumor"));
        // Does not apply
        i1.setBehaviorIcdO3("2");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2019");
        i1.setDateOfDiagnosisMonth("");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisMonth("");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M16 Abstract a single primary (the invasive) when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor in the same breast.
        ruleStepToTest = "M16";
        ruleCountToTest = 11;
        i1.setPrimarySite("C500");
        i1.setHistologyIcdO3("8523");
        i1.setBehaviorIcdO3("2");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2021");
        i1.setDateOfDiagnosisMonth("2");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8255");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisMonth("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertTrue(output.getReason().contains("invasive tumor following an in situ tumor less than or equal to 60 days"));

        // Rule M17 Abstract multiple primaries when an invasive tumor occurs more than 60 days after an in situ tumor in the same breast.
        ruleStepToTest = "M17";
        ruleCountToTest = 12;
        i1.setPrimarySite("C500");
        i1.setHistologyIcdO3("8401");
        i1.setBehaviorIcdO3("2");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2021");
        i1.setDateOfDiagnosisMonth("1");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8401");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisMonth("5");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertTrue(output.getReason().contains("60 days"));
        i1.setLaterality("1");
        i2.setLaterality("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M18 Abstract a single primary when none of the previous rules apply.
        // Unable to trigger this rule.
        /*
        ruleStepToTest = "M18";
        ruleCountToTest = 15;
        i1.setPrimarySite("C500");
        i1.setHistologyIcdO3("8529");
        i1.setBehaviorIcdO3("2");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2019");
        i1.setDateOfDiagnosisMonth("8");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8533");
        i2.setBehaviorIcdO3("6");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisMonth("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertTrue(output.getReason().contains("criteria"));
        */
    }

    @Test
    public void test2021Lung() {
        // Lung Multiple Primary Rules
        // C340-C343, C348, C349
        // (Excludes lymphoma and leukemia M9590–M9992 and Kaposi sarcoma M9140)

        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;
        String ruleStepToTest;
        int ruleCountToTest;

        // Rule M6 Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 3 in the Equivalent Terms and Definitions.  Timing is irrelevant.
        ruleStepToTest = "M6";
        ruleCountToTest = 4;
        i1.setPrimarySite("C342");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("09");
        i1.setHistologyIcdO3("8013");
        i1.setBehaviorIcdO3("3");

        i2.setPrimarySite("C342");
        i2.setDateOfDiagnosisYear("2016");
        i2.setDateOfDiagnosisMonth("08");
        i2.setHistologyIcdO3("8045");
        i2.setBehaviorIcdO3("3");

        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2018_LUNG_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("two or more different subtypes/variants in Column 3, Table 3"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        //8013 is removed from sarcoma row and added to its own row after 2021
        i1.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisYear("2019");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2021_LUNG_GROUP_ID, output.getGroupId());
        Assert.assertTrue(output.getAppliedRules().size() > ruleCountToTest);

        // Rule M7 Abstract a single primary when synchronous, separate/non-contiguous tumors in the same lung are on the same row in Table 3 in the Equivalent Terms and Definitions.
        ruleStepToTest = "M7";
        ruleCountToTest = 5;
        i1.setPrimarySite("C342");
        i1.setHistologyIcdO3("8041");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("2");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("09");
        i2.setPrimarySite("C349");
        i2.setHistologyIcdO3("8013");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("2");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("08");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2018_LUNG_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("same row in Table 3"));
        Assert.assertEquals(ruleStepToTest, output.getStep());

        //8013 is moved to its row for 2021+
        i1.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisYear("2021");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2021_LUNG_GROUP_ID, output.getGroupId());
        Assert.assertTrue(output.getAppliedRules().size() > ruleCountToTest);
    }

    @Test
    public void test2021HeadAndNeck() {
        //C442 removed from the group
        MphInput i1 = new MphInput(), i2 = new MphInput();
        i1.setPrimarySite("C442");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("11");
        i1.setHistologyIcdO3("8000");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C442");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("11");
        i2.setHistologyIcdO3("8000");
        i2.setBehaviorIcdO3("3");
        MphOutput output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, output.getGroupId());
        i1.setDateOfDiagnosisYear("2021");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2018_OTHER_SITES_GROUP_ID, output.getGroupId());

        //Table 1: 8072 row:  New subtype/variant in column 3: Schneiderian carcinoma/cylindrical cell carcinoma 8121
        // Rule M7 Abstract multiple primaries when separate, non-contiguous tumors are two or more different subtypes/variants in Column 3 of the appropriate site table (Tables 1-9) in the Equivalent Terms and Definitions. Timing is irrelevant.
        String ruleStepToTest = "M7";
        int ruleCountToTest = 5;
        i1.setPrimarySite("C312");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("11");
        i1.setHistologyIcdO3("8121");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C319");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("11");
        i2.setHistologyIcdO3("8144");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        i1.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisYear("2021");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2021_HEAD_AND_NECK_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());

        //Table 3: 8070 row: New subtype/variant in column 3: Keratinizing squamous cell carcinoma 8071; Non-keratinizing squamous cell carcinoma 8072
        i1.setPrimarySite("C130");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("11");
        i1.setHistologyIcdO3("8071");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C139");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("11");
        i2.setHistologyIcdO3("8072");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        i1.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisYear("2021");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2021_HEAD_AND_NECK_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        //Table 4: 8070 row: New subtype/variant in column 3: Keratinizing squamous cell carcinoma 8071; Non-keratinizing squamous cell carcinoma 8072
        i1.setPrimarySite("C021");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("11");
        i1.setHistologyIcdO3("8071");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C024");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("11");
        i2.setHistologyIcdO3("8072");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        i1.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisYear("2021");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2021_HEAD_AND_NECK_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());

        // Rule M8 Abstract multiple primaries when separate, non-contiguous tumors are on different rows in the appropriate site table (Tables 1-9) in the Equivalent Terms and Definitions. Timing is irrelevant.
        ruleStepToTest = "M8";
        ruleCountToTest = 6;
        //Table 1: 8072 row:  New subtype/variant in column 3: Schneiderian carcinoma/cylindrical cell carcinoma 8121
        i1.setPrimarySite("C312");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("11");
        i1.setHistologyIcdO3("8121");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C319");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("11");
        i2.setHistologyIcdO3("8082");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals("M7", output.getStep());
        Assert.assertEquals(5, output.getAppliedRules().size());
        i1.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisYear("2021");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2021_HEAD_AND_NECK_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        //Table 3: 8070 row: New subtype/variant in column 3: Keratinizing squamous cell carcinoma 8071; Non-keratinizing squamous cell carcinoma 8072
        i1.setPrimarySite("C130");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("11");
        i1.setHistologyIcdO3("8850");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C139");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("11");
        i2.setHistologyIcdO3("8071");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals("M7", output.getStep());
        Assert.assertEquals(5, output.getAppliedRules().size());
        i1.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisYear("2021");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2021_HEAD_AND_NECK_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        i1.setPrimarySite("C130");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("11");
        i1.setHistologyIcdO3("8850");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C139");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("11");
        i2.setHistologyIcdO3("8072");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals("M7", output.getStep());
        Assert.assertEquals(5, output.getAppliedRules().size());
        i1.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisYear("2021");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2021_HEAD_AND_NECK_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        //Table 4: 8070 row: New subtype/variant in column 3: Keratinizing squamous cell carcinoma 8071; Non-keratinizing squamous cell carcinoma 8072
        i1.setPrimarySite("C021");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("11");
        i1.setHistologyIcdO3("8825");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C024");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("11");
        i2.setHistologyIcdO3("8071");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals("M7", output.getStep());
        Assert.assertEquals(5, output.getAppliedRules().size());
        i1.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisYear("2021");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2021_HEAD_AND_NECK_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        i1.setPrimarySite("C021");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("11");
        i1.setHistologyIcdO3("8825");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C024");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("11");
        i2.setHistologyIcdO3("8072");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals("M7", output.getStep());
        Assert.assertEquals(5, output.getAppliedRules().size());
        i1.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisYear("2021");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2021_HEAD_AND_NECK_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());

        //Table 8: Ceruminous adenocarcinoma 8420 row deleted
        i1.setPrimarySite("C301");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("11");
        i1.setHistologyIcdO3("8140");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i2.setPrimarySite("C301");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("11");
        i2.setHistologyIcdO3("8420");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        i1.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisYear("2021");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2021_HEAD_AND_NECK_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals("M7", output.getStep());
        Assert.assertEquals(5, output.getAppliedRules().size());

        //Table 9: Histologies are given in each row for pre-2021 and post-2021.
        // Treat them as if they are the same row. For example, 8690/3 in 2020 and a 8692/3 in 2021 should be considered same row.
        // Hopefully there will be an edit to enforce the histologies by year. (Don’t hard code the /3 behavior in the rules).
        i1.setPrimarySite("C479");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("11");
        i1.setHistologyIcdO3("8680");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C479");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("11");
        i2.setHistologyIcdO3("8693");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals("M7", output.getStep());
        Assert.assertEquals(5, output.getAppliedRules().size());
        i1.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisYear("2021");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2021_HEAD_AND_NECK_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());

        i1.setPrimarySite("C479");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("11");
        i1.setHistologyIcdO3("8680");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C479");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("11");
        i2.setHistologyIcdO3("8692");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals("M7", output.getStep());
        Assert.assertEquals(5, output.getAppliedRules().size());
        i1.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisYear("2021");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2021_HEAD_AND_NECK_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());

        // Rule M12 Abstract a single primary when separate/non-contiguous tumors in the same primary site are on the same row in the appropriate site table (Tables 1-9) in the Equivalent Terms and Definitions. Timing is irrelevant.
        ruleStepToTest = "M12";
        ruleCountToTest = 10;
        i1.setPrimarySite("C479");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("11");
        i1.setHistologyIcdO3("8690");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C479");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("11");
        i2.setHistologyIcdO3("8693");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals("M7", output.getStep());
        Assert.assertEquals(5, output.getAppliedRules().size());
        i1.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisYear("2021");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2021_HEAD_AND_NECK_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());


        i1.setPrimarySite("C479");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("11");
        i1.setHistologyIcdO3("8690");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C479");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("11");
        i2.setHistologyIcdO3("8692");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals("M7", output.getStep());
        Assert.assertEquals(5, output.getAppliedRules().size());
        i1.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisYear("2021");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2021_HEAD_AND_NECK_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
    }

    @Test
    public void test2021CutaneousMelanoma() {
        // Cutaneous Melanoma Histology Rules
        // C440-C449 with Histology 8720-8780 (Excludes melanoma of any other site)
        // Rules apply to cases diagnosed 1/1/2021+

        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;
        String ruleStepToTest;
        int ruleCountToTest;

        // Rule M3 Melanomas in sites with ICD-O-3 topography codes that are different at the second (Cxxx), third (Cxxx) or fourth (C44x) character are multiple primaries. **
        ruleStepToTest = "M3";
        ruleCountToTest = 1;
        i1.setPrimarySite("C442");
        i1.setHistologyIcdO3("8720");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C447");
        i2.setHistologyIcdO3("8780");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisYear("2021");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(MphConstants.MP_2021_CUTANEOUS_MELANOMA_GROUP_ID, output.getGroupId());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("topography"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        //C449 is treated as unknown
        i2.setPrimarySite("C449");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(MphConstants.MP_2021_CUTANEOUS_MELANOMA_GROUP_ID, output.getGroupId());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setLaterality("1");
        i2.setLaterality("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(MphConstants.MP_2021_CUTANEOUS_MELANOMA_GROUP_ID, output.getGroupId());
        Assert.assertEquals("M4", output.getStep());

        // Rule M4 Melanomas with different laterality are multiple primaries. **
        ruleStepToTest = "M4";
        ruleCountToTest = 2;
        i1.setPrimarySite("C442");
        i1.setHistologyIcdO3("8720");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C442");
        i2.setHistologyIcdO3("8780");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisYear("2021");
        i1.setLaterality("1");
        i2.setLaterality("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2021_CUTANEOUS_MELANOMA_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("lateralities"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        //melanoma mid-line laterality is considered as different laterality of right or left
        i2.setLaterality("5");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2021_CUTANEOUS_MELANOMA_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("lateralities"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        //if one laterality is unknown, continue to the next step
        i2.setLaterality("9");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2021_CUTANEOUS_MELANOMA_GROUP_ID, output.getGroupId());
        Assert.assertTrue(output.getAppliedRules().size() > ruleCountToTest);
        //If site doesn't require laterality, skip
        i1.setPrimarySite("C440");
        i1.setHistologyIcdO3("8720");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C440");
        i2.setHistologyIcdO3("8780");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisYear("2021");
        i1.setLaterality("1");
        i2.setLaterality("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2021_CUTANEOUS_MELANOMA_GROUP_ID, output.getGroupId());
        Assert.assertTrue(output.getAppliedRules().size() > ruleCountToTest);


        // Rule M5 Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 2
        ruleStepToTest = "M5";
        ruleCountToTest = 3;
        i1.setPrimarySite("C442");
        i1.setHistologyIcdO3("8744");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C442");
        i2.setHistologyIcdO3("8745");
        i2.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i2.setLaterality("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2021_CUTANEOUS_MELANOMA_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Column 3"));
        Assert.assertEquals(ruleStepToTest, output.getStep());

        // Rule M6 Abstract a single primary when synchronous, separate/non-contiguous tumors are on the same row in Table 2
        ruleStepToTest = "M6";
        ruleCountToTest = 4;
        i1.setPrimarySite("C442");
        i2.setPrimarySite("C442");
        i1.setHistologyIcdO3("8745");
        i2.setHistologyIcdO3("8720");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        i1.setLaterality("5");
        i2.setLaterality("5");
        i1.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisYear("2021");
        i1.setDateOfDiagnosisMonth("1");
        i2.setDateOfDiagnosisMonth("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2021_CUTANEOUS_MELANOMA_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setLaterality("9");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2021_CUTANEOUS_MELANOMA_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());

        i1.setPrimarySite("C440");
        i2.setPrimarySite("C440");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2021_CUTANEOUS_MELANOMA_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i2.setDateOfDiagnosisMonth("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2021_CUTANEOUS_MELANOMA_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());

        // Rule M7 Melanomas diagnosed more than 60 days apart are multiple primaries. **
        ruleStepToTest = "M7";
        ruleCountToTest = 5;
        i1.setPrimarySite("C442");
        i2.setPrimarySite("C442");
        i1.setHistologyIcdO3("8745");
        i2.setHistologyIcdO3("8720");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        i1.setLaterality("5");
        i2.setLaterality("5");
        i1.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisYear("2021");
        i1.setDateOfDiagnosisMonth("1");
        i2.setDateOfDiagnosisMonth("11");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Melanomas diagnosed more than 60 days apart are multiple primaries."));
        Assert.assertEquals(ruleStepToTest, output.getStep());

        // Rule M8 Melanomas that do not meet any of the above criteria are abstracted as a single primary. *
        ruleStepToTest = "M8";
        ruleCountToTest = 6;
        i1.setPrimarySite("C442");
        i2.setPrimarySite("C442");
        i1.setHistologyIcdO3("8725");
        i2.setHistologyIcdO3("8720");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        i1.setLaterality("5");
        i2.setLaterality("5");
        i1.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisYear("2021");
        i1.setDateOfDiagnosisMonth("01");
        i2.setDateOfDiagnosisMonth("01");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("criteria"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
    }
}
