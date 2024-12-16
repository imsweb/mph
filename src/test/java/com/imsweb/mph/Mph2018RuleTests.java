/*
 * Copyright (C) 2018 Information Management Services, Inc.
 */
package com.imsweb.mph;

import org.junit.Assert;
import org.junit.Test;

import com.imsweb.mph.MphUtils.MpResult;

//Suppressing sonar warnings for method complexity
@SuppressWarnings("java:S5961")
public class Mph2018RuleTests {

    private MphUtils _utils = new MphUtils();


    //==================================================================================================================
    // 2018 Rule Tests
    //==================================================================================================================

    @Test
    public void test2018Breast() {
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
        i1.setDateOfDiagnosisYear("2018");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8530");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2018");
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
        i1.setDateOfDiagnosisYear("2009");
        i1.setLaterality("1");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8780");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2018");
        i2.setLaterality("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply
        i1.setDateOfDiagnosisYear("2014");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M6 Abstract a single primary when there is inflammatory carcinoma in:
        // - Multiple quadrants of same breast OR
        // - Bilateral breasts
        ruleStepToTest = "M6";
        ruleCountToTest = 3;
        i1.setPrimarySite("C500");
        i1.setHistologyIcdO3("8530");
        i1.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2018");
        i1.setLaterality("1");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8530");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2018");
        i2.setLaterality("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertTrue(output.getReason().contains("carcinoma"));
        i1.setLaterality("4");
        i2.setLaterality("4");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertTrue(output.getReason().contains("carcinoma"));
        // Does not apply
        i2.setBehaviorIcdO3("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M7 Abstract multiple primaries when there is bilateral breast cancer (both right and left breast).
        ruleStepToTest = "M7";
        ruleCountToTest = 4;
        i1.setPrimarySite("C500");
        i1.setHistologyIcdO3("8730");
        i1.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2018");
        i1.setLaterality("1");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8730");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2018");
        i2.setLaterality("2");
        // Multiple at M6
        output = _utils.computePrimaries(i1, i2);
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
        //        i1.setDateOfDiagnosisYear("2018");
        //        i1.setDateOfDiagnosisMonth("1");
        //        i1.setLaterality("1");
        //        i2.setPrimarySite("C509");
        //        i2.setHistologyIcdO3("8541");
        //        i2.setBehaviorIcdO3("3");
        //        i2.setDateOfDiagnosisYear("2018");
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
        //        i1.setDateOfDiagnosisYear("2018");
        //        i1.setDateOfDiagnosisMonth("1");
        //        i1.setLaterality("1");
        //        i2.setPrimarySite("C509");
        //        i2.setHistologyIcdO3("8520");
        //        i2.setBehaviorIcdO3("3");
        //        i2.setDateOfDiagnosisYear("2018");
        //        i2.setDateOfDiagnosisMonth("1");
        //        i2.setLaterality("1");
        //        output = _utils.computePrimaries(i1, i2);
        //        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        //        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        //        Assert.assertTrue(output.getReason().contains("Paget"));
        //        Assert.assertEquals(ruleStepToTest, output.getStep());

        //Rule M10 Abstract a single primary when simultaneous multiple tumors are carcinoma NST/duct and lobular.
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
        i2.setBehaviorIcdO3("2");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("2");
        i2.setLaterality("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Date restriction is removed in July 2019 update
        i2.setDateOfDiagnosisMonth("5");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
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
        //2023 update behavior restriction removed
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_BREAST, output.getGroupName());
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setBehaviorIcdO3("2");
        i2.setBehaviorIcdO3("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_BREAST, output.getGroupName());
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
        i1.setDateOfDiagnosisYear("2016");
        i1.setDateOfDiagnosisMonth("1");
        i2.setPrimarySite("C506");
        i2.setHistologyIcdO3("8500");
        i2.setBehaviorIcdO3("2");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertTrue(output.getReason().contains("conditions are a single primary"));
        i1.setPrimarySite("C500");
        i1.setHistologyIcdO3("8541");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2016");
        i1.setDateOfDiagnosisMonth("");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8500");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertTrue(output.getReason().contains("conditions are a single primary"));
        // Does not apply
        i1.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisYear("2016");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        // Does not apply
        i1.setPrimarySite("C500");
        i1.setHistologyIcdO3("8522");
        i1.setBehaviorIcdO3("2");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2016");
        i1.setDateOfDiagnosisMonth("");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8500");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M12 Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3 of Table 3 in the Equivalent Terms and Definitions. Timing is irrelevant.
        ruleStepToTest = "M12";
        ruleCountToTest = 7;
        i1.setPrimarySite("C500");
        i1.setHistologyIcdO3("8035");
        i1.setBehaviorIcdO3("2");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2016");
        i1.setDateOfDiagnosisMonth("");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8032");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertTrue(output.getReason().contains("subtypes/variants in Column 3, Table 3"));
        i1.setPrimarySite("C501");
        i1.setHistologyIcdO3("8310");
        i1.setBehaviorIcdO3("2");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2016");
        i1.setDateOfDiagnosisMonth("");
        i2.setPrimarySite("C502");
        i2.setHistologyIcdO3("8574");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertTrue(output.getReason().contains("subtypes/variants in Column 3, Table 3"));
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setPrimarySite("C508");
        i1.setHistologyIcdO3("8980");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2016");
        i1.setDateOfDiagnosisMonth("");
        i2.setPrimarySite("C506");
        i2.setHistologyIcdO3("8901");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertTrue(output.getReason().contains("subtypes/variants in Column 3, Table 3"));

        i1.setHistologyIcdO3("9170");
        i2.setHistologyIcdO3("9180");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertTrue(output.getReason().contains("subtypes/variants in Column 3, Table 3"));
        //Does not apply
        i1.setHistologyIcdO3("9170");
        i2.setHistologyIcdO3("9120");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        // Does not apply
        i1.setPrimarySite("C508");
        i1.setHistologyIcdO3("8550");
        i1.setBehaviorIcdO3("2");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2016");
        i1.setDateOfDiagnosisMonth("");
        i2.setPrimarySite("C506");
        i2.setHistologyIcdO3("8983");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        // Does not apply
        i1.setPrimarySite("C508");
        i1.setHistologyIcdO3("8310");
        i1.setBehaviorIcdO3("2");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2016");
        i1.setDateOfDiagnosisMonth("");
        i2.setPrimarySite("C506");
        i2.setHistologyIcdO3("8315");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        // Does not apply
        i1.setPrimarySite("C508");
        i1.setHistologyIcdO3("8504");
        i1.setBehaviorIcdO3("2");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2016");
        i1.setDateOfDiagnosisMonth("");
        i2.setPrimarySite("C506");
        i2.setHistologyIcdO3("8504");
        i2.setBehaviorIcdO3("2");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        //8512 is added to the table in the 2021 update, but it is removed again in 2024 update :)
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
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_BREAST, output.getGroupName());
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());

        //9130/3 added to the table in the 2021 update
        i1.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisYear("2018");
        i1.setHistologyIcdO3("9130");
        i1.setBehaviorIcdO3("3");
        i2.setHistologyIcdO3("8850");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_BREAST, output.getGroupName());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setDateOfDiagnosisYear("2021");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_BREAST, output.getGroupName());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setPrimarySite("C509");
        i2.setPrimarySite("C509");
        i1.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisYear("2018");
        i1.setHistologyIcdO3("9130");
        i1.setBehaviorIcdO3("3");
        i2.setHistologyIcdO3("9170");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_BREAST, output.getGroupName());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        //8574/3, 8013/3 and 8041/3 are subtypes of 8246/3 (2025 update)
        i1.setHistologyIcdO3("8574");
        i1.setBehaviorIcdO3("3");
        i2.setHistologyIcdO3("8013");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_BREAST, output.getGroupName());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setHistologyIcdO3("8041");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_BREAST, output.getGroupName());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());

        // Rule M13 Abstract a single primary when synchronous, separate/non-contiguous tumors are on the same row in Table 3 in the Equivalent Terms and Definitions. Timing is irrelevant.
        ruleStepToTest = "M13";
        ruleCountToTest = 8;
        i1.setPrimarySite("C500");
        i1.setHistologyIcdO3("8200");
        i1.setBehaviorIcdO3("2");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("1");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8200");
        i2.setBehaviorIcdO3("2");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertTrue(output.getReason().contains("same row in Table 3"));
        //8562 added to row 8983 on 2025 update
        i1.setHistologyIcdO3("8562");
        i2.setHistologyIcdO3("8983");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertTrue(output.getReason().contains("same row in Table 3"));
        i1.setPrimarySite("C501");
        i1.setHistologyIcdO3("8503");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("3");
        i2.setPrimarySite("C502");
        i2.setHistologyIcdO3("8509");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("4");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertTrue(output.getReason().contains("same row in Table 3"));
        //8574/3, 8013/3 and 8041/3 are subtypes of 8246/3 (2025 update)
        i1.setHistologyIcdO3("8574");
        i1.setBehaviorIcdO3("3");
        i2.setHistologyIcdO3("8246");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        i1.setHistologyIcdO3("8013");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        i1.setHistologyIcdO3("8041");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertTrue(output.getReason().contains("same row in Table 3"));

        i1.setPrimarySite("C503");
        i1.setHistologyIcdO3("8800");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("7");
        i1.setDateOfDiagnosisDay("24");
        i2.setPrimarySite("C506");
        i2.setHistologyIcdO3("8901");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("9");
        i2.setDateOfDiagnosisDay("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertTrue(output.getReason().contains("same row in Table 3"));
        i1.setPrimarySite("C504");
        i1.setHistologyIcdO3("8800");
        i1.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("10");
        i1.setDateOfDiagnosisDay("28");
        i1.setLaterality("2");
        i2.setPrimarySite("C504");
        i2.setHistologyIcdO3("9170");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("09");
        i2.setDateOfDiagnosisDay("14");
        i2.setLaterality("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertTrue(output.getReason().contains("same row in Table 3"));
        // Does not apply
        i1.setPrimarySite("C500");
        i1.setHistologyIcdO3("8550");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2016");
        i1.setDateOfDiagnosisMonth("");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8550");
        i2.setBehaviorIcdO3("2");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        // Does not apply
        i1.setPrimarySite("C501");
        i1.setHistologyIcdO3("8503");
        i1.setBehaviorIcdO3("2");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2016");
        i1.setDateOfDiagnosisMonth("");
        i2.setPrimarySite("C502");
        i2.setHistologyIcdO3("8503");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        // Does not apply
        i1.setPrimarySite("C503");
        i1.setHistologyIcdO3("8530");
        i1.setBehaviorIcdO3("2");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2016");
        i1.setDateOfDiagnosisMonth("");
        i2.setPrimarySite("C506");
        i2.setHistologyIcdO3("8519");
        i2.setBehaviorIcdO3("2");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        //8512 is added to the row of 8510 in the 2021 update and removed again in 2024
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
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_BREAST, output.getGroupName());
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());

        //9130/3 is added for 8800 row in the 2021 update
        i1.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisYear("2018");
        i1.setHistologyIcdO3("9130");
        i1.setBehaviorIcdO3("3");
        i2.setHistologyIcdO3("8800");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_BREAST, output.getGroupName());
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertTrue(output.getReason().contains("same row in Table 3"));
        i1.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisYear("2021");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_BREAST, output.getGroupName());
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertTrue(output.getReason().contains("same row in Table 3"));
        //even thou hists are in the table, consider same row if they are the same code except 8000 and 8010
        i1.setHistologyIcdO3("8001");
        i2.setHistologyIcdO3("8001");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_BREAST, output.getGroupName());
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertTrue(output.getReason().contains("same row in Table 3"));

        // Rule M14 Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 3 in the Equivalent Terms and Definitions. Timing is irrelevant.
        ruleStepToTest = "M14";
        ruleCountToTest = 9;
        i1.setPrimarySite("C500");
        i1.setHistologyIcdO3("8315");
        i1.setBehaviorIcdO3("2");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2016");
        i1.setDateOfDiagnosisMonth("");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8575");
        i2.setBehaviorIcdO3("2");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertTrue(output.getReason().contains("different rows in Table 3"));
        i1.setPrimarySite("C501");
        i1.setHistologyIcdO3("8500");
        i1.setBehaviorIcdO3("2");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2016");
        i1.setDateOfDiagnosisMonth("");
        i2.setPrimarySite("C506");
        i2.setHistologyIcdO3("8982");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertTrue(output.getReason().contains("different rows in Table 3"));
        i1.setPrimarySite("C502");
        i1.setHistologyIcdO3("8510");
        i1.setBehaviorIcdO3("2");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2016");
        i1.setDateOfDiagnosisMonth("");
        i2.setPrimarySite("C505");
        i2.setHistologyIcdO3("8211");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertTrue(output.getReason().contains("different rows in Table 3"));
        //table 2 and table 3
        i1.setHistologyIcdO3("8510");
        i2.setHistologyIcdO3("8255");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertTrue(output.getReason().contains("different rows in Table 3"));
        //table 2 and table 3
        i1.setHistologyIcdO3("8540");
        i1.setBehaviorIcdO3("2");
        i2.setHistologyIcdO3("8523");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertTrue(output.getReason().contains("different rows in Table 3"));

        // Rule M15 Abstract a single primary (the invasive) when an in situ tumor is diagnosed after an invasive tumor in the same breast.
        ruleStepToTest = "M15";
        ruleCountToTest = 10;
        i1.setPrimarySite("C500");
        i1.setHistologyIcdO3("8401");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2016");
        i1.setDateOfDiagnosisMonth("");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8401");
        i2.setBehaviorIcdO3("2");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertTrue(output.getReason().contains("in situ tumor diagnosed following an invasive tumor"));
        // Does not apply
        i1.setBehaviorIcdO3("2");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2016");
        i1.setDateOfDiagnosisMonth("");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
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
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("2");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8255");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
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
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("1");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8401");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
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
        i1.setDateOfDiagnosisYear("2016");
        i1.setDateOfDiagnosisMonth("8");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8533");
        i2.setBehaviorIcdO3("6");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertTrue(output.getReason().contains("criteria"));
        */
    }

    @Test
    public void test2018Colon() {
        // Colon, Rectosigmoid, and Rectum Multiple Primary Rules
        // C180-C189, C199, C209
        // (Excludes lymphoma and leukemia M9590-M9992 and Kaposi sarcoma M9140)

        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;
        String ruleStepToTest;
        int ruleCountToTest;

        // Rule M3 Abstract a single primary when there is adenocarcinoma in situ and/or invasive in at least one polyp AND
        //  - There is a clinical diagnosis of familial polyposis (FAP) OR
        //  - Greater than 100 polyps are documented (no diagnosis of FAP)
        ruleStepToTest = "M3";
        ruleCountToTest = 1;
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8220");
        i1.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2016");
        i2.setPrimarySite("C180");
        i2.setHistologyIcdO3("8220");
        i2.setBehaviorIcdO3("2");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("(FAP)"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setPrimarySite("C181");
        i1.setHistologyIcdO3("8220");
        i1.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2016");
        i2.setPrimarySite("C180");
        i2.setHistologyIcdO3("8220");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("(FAP)"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8650");
        i1.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2016");
        i2.setPrimarySite("C180");
        i2.setHistologyIcdO3("8221");
        i2.setBehaviorIcdO3("2");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M4 Abstract multiple primaries when there are separate, non-contiguous tumors in sites with ICD-O site codes that differ at the second CXxx and/or third CxXx character.
        // C180-C189, C199, C209
        ruleStepToTest = "M4";
        ruleCountToTest = 2;
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8221");
        i1.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2016");
        i2.setPrimarySite("C209");
        i2.setHistologyIcdO3("8265");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("second (C?xx) and/or third (Cx?x) character"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setPrimarySite("C189");
        i1.setHistologyIcdO3("8221");
        i1.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2018");
        i2.setPrimarySite("C199");
        i2.setHistologyIcdO3("8265");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(2, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("second (C?xx) and/or third (Cx?x) character"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8221");
        i1.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2018");
        i2.setPrimarySite("C180"); //not different, continue to the next step
        i2.setHistologyIcdO3("8265");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M5 Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 1 in the Equivalent Terms and Definitions. Timing is irrelevant.
        ruleStepToTest = "M5";
        ruleCountToTest = 3;
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8200");
        i1.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2016");
        i2.setPrimarySite("C180");
        i2.setHistologyIcdO3("8249");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("two or more different subtypes/variants in Column 3, Table 1"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8020");
        i1.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2016");
        i2.setPrimarySite("C180");
        i2.setHistologyIcdO3("8890");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("two or more different subtypes/variants in Column 3, Table 1"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8142");
        i1.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2016");
        i2.setPrimarySite("C180");
        i2.setHistologyIcdO3("8241");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("two or more different subtypes/variants in Column 3, Table 1"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8023");
        i1.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2016");
        i2.setPrimarySite("C180");
        i2.setHistologyIcdO3("8241");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        // Does not apply
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8201");
        i1.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2016");
        i2.setPrimarySite("C180");
        i2.setHistologyIcdO3("9120");
        i2.setBehaviorIcdO3("2");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M6 Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 1 in the Equivalent Terms and Definitions. Timing is irrelevant.
        ruleStepToTest = "M6";
        ruleCountToTest = 4;
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8140");
        i1.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2016");
        i2.setPrimarySite("C180");
        i2.setHistologyIcdO3("8246");
        i2.setBehaviorIcdO3("2");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertTrue(output.getReason().contains("are on different rows in Table 1"));
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8899");
        i1.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2016");
        i2.setPrimarySite("C180");
        i2.setHistologyIcdO3("8246");
        i2.setBehaviorIcdO3("2");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());
        Assert.assertTrue(output.getReason().contains("not in the table."));
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8560");
        i1.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2016");
        i2.setPrimarySite("C180");
        i2.setHistologyIcdO3("8241");
        i2.setBehaviorIcdO3("2");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertTrue(output.getReason().contains("are on different rows in Table 1"));
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8240");
        i1.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2016");
        i2.setPrimarySite("C180");
        i2.setHistologyIcdO3("8241");
        i2.setBehaviorIcdO3("2");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        // Does not apply
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8901");
        i1.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2016");
        i2.setPrimarySite("C180");
        i2.setHistologyIcdO3("8902");
        i2.setBehaviorIcdO3("2");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());
        Assert.assertTrue(output.getReason().contains("not in the table."));
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8140");
        i1.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2016");
        i2.setPrimarySite("C180");
        i2.setHistologyIcdO3("8265");
        i2.setBehaviorIcdO3("2");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M7 Abstract multiple primaries when a subsequent tumor arises at the anastomotic site AND:
        // -  One tumor is a NOS and the other is a subtype/variant of that NOS OR
        // -  The subsequent tumor occurs greater than 24 months after original tumor resection OR
        // - The subsequent tumor arises in the mucosa
        // ABH 7/19/18 - Revised per https://www.squishlist.com/ims/seerdms_dev/81114/
        // Incoming record is a tumor in a segment of colon/rectal/rectosigmoid.
        // There is a previous diagnosis of a tumor in a different segment of colon/rectum/rectosigmoid,
        // AND there was surgery done (surgery codes 30, 32, 40, 31),
        // ABH 9/14/18 - Disabled now per https://www.squishlist.com/ims/seerdms_dev/81114/
        /*
        ruleStepToTest = "M7";
        ruleCountToTest = 5;
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8561");
        i1.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2016");
        i2.setPrimarySite("C181");
        i2.setHistologyIcdO3("8212");
        i2.setBehaviorIcdO3("2");
        i2.setDateOfDiagnosisYear("2018");
        i2.setSurgeryOfPrimarySite("30");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        */

        // Rule M8 Abstract a single primary when a subsequent tumor arises at the anastomotic site AND:
        // -  The subsequent tumor occurs less than or equal to 24 months after original tumor resection OR
        // -  The tumor arises in colon/rectal wall and/or surrounding tissue; there is no involvement of the mucosa OR
        // -  The pathologist or clinician documents an anastomotic recurrence
        // ABH 9/14/18 - Disabled now per https://www.squishlist.com/ims/seerdms_dev/81114/
        /*
        ruleStepToTest = "M8";
        ruleCountToTest = 6;
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8561");
        i1.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2016");
        i2.setPrimarySite("C180");
        i2.setHistologyIcdO3("8212");
        i2.setBehaviorIcdO3("2");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        */

        // Rule M9 Abstract multiple primaries when there are separate, non-contiguous tumors in sites with ICD-O site codes that differ at the fourth characters C18X.
        ruleStepToTest = "M9";
        ruleCountToTest = 5;
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8140");
        i1.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2016");
        i2.setPrimarySite("C185");
        i2.setHistologyIcdO3("8265");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("differ at the fourth character (C18X)"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8140");
        i1.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2018");
        i2.setPrimarySite("C180"); //not different, continue to the next step
        i2.setHistologyIcdO3("8265");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M10 Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than one year after the original diagnosis or last recurrence.
        ruleStepToTest = "M10";
        ruleCountToTest = 6;
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8140");
        i1.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2016");
        i2.setPrimarySite("C180");
        i2.setHistologyIcdO3("8265");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("1"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply
        i1.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M11 Abstract a single primary when synchronous, separate/non-contiguous tumors are on the same row in Table 1 in the Equivalent Terms and Definitions. Timing is irrelevant.
        ruleStepToTest = "M11";
        ruleCountToTest = 7;
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8140");
        i1.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("2");
        i2.setPrimarySite("C180");
        i2.setHistologyIcdO3("8200");
        i2.setBehaviorIcdO3("2");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertTrue(output.getReason().contains("are on the same row in Table 1"));
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8244");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C180");
        i2.setHistologyIcdO3("8243");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertTrue(output.getReason().contains("are on the same row in Table 1"));
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setHistologyIcdO3("8154");
        i2.setHistologyIcdO3("8154");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertTrue(output.getReason().contains("are on the same row in Table 1"));
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8140");
        i1.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2016");
        i2.setPrimarySite("C180");
        i2.setHistologyIcdO3("8140");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        // Does not apply
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8244");
        i1.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2016");
        i2.setPrimarySite("C180");
        i2.setHistologyIcdO3("8243");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8145");
        i1.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2016");
        i2.setPrimarySite("C180");
        i2.setHistologyIcdO3("8013");
        i2.setBehaviorIcdO3("2");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M12 Abstract a single primary (the invasive) when an in situ tumor is diagnosed after an invasive tumor.
        ruleStepToTest = "M12";
        ruleCountToTest = 8;
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8244");
        i1.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("1");
        i2.setPrimarySite("C180");
        i2.setHistologyIcdO3("8243");
        i2.setBehaviorIcdO3("2");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertTrue(output.getReason().contains("situ tumor diagnosed following an invasive tumor"));
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8221");
        i1.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("1");
        i2.setPrimarySite("C180");
        i2.setHistologyIcdO3("8266");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        // Does not apply
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8221");
        i1.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("1");
        i2.setPrimarySite("C180");
        i2.setHistologyIcdO3("8266");
        i2.setBehaviorIcdO3("6");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M13 Abstract a single primary (the invasive) when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor.
        //This is not going to hit

        // Rule M14 Abstract multiple primaries when an invasive tumor occurs more than 60 days after an in situ tumor.
        ruleStepToTest = "M14";
        ruleCountToTest = 10;
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8244");
        i1.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("1");
        i2.setPrimarySite("C180");
        i2.setHistologyIcdO3("8243");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("5");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertTrue(output.getReason().contains("invasive tumor following an in situ tumor more than 60 days"));
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("2");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        // Does not apply
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8529");
        i1.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2018");
        i2.setPrimarySite("C180");
        i2.setHistologyIcdO3("8533");
        i2.setBehaviorIcdO3("2");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M15 Abstract a single primary when tumors do not meet any of the above criteria.
        ruleStepToTest = "M15";
        ruleCountToTest = 11;
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8244");
        i1.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("1");
        i2.setPrimarySite("C180");
        i2.setHistologyIcdO3("8243");
        i2.setBehaviorIcdO3("6");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("5");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("criteria"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
    }

    @Test
    public void test2018HeadAndNeck() {
        // Head and Neck Multiple Primary Rules
        // C000-C148, C300-C339, C410, C411, C442, C479
        // (Excludes lymphoma and leukemia M9590 – M9992 and Kaposi sarcoma M9140)

        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;
        String ruleStepToTest;
        int ruleCountToTest;

        //C442 removed from the group
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
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MPH_2007_2022_OTHER_SITES, output.getGroupName());

        // Rule M3 Abstract multiple primaries when there are separate/non-contiguous tumors in any two of the following sites:
        //   - Hard palate C050 AND/OR soft palate C051 AND/OR uvula C052
        //   - Maxillary sinus C310 AND/OR ethmoid sinus C311 AND/OR frontal sinus C312 AND/OR sphenoid sinus C313
        //   - Nasal cavity C300 AND middle ear C301
        //   - Submandibular gland C080 AND sublingual gland C081
        //   - Upper gum C030 AND lower gum C031
        //   - Upper lip C000 or C003 AND lower lip C001 or C004

        ruleStepToTest = "M3";
        ruleCountToTest = 1;
        //   - Upper lip C000 or C003 AND lower lip C001 or C004
        i1.setPrimarySite("C000");
        i1.setHistologyIcdO3("8000");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C001");
        i2.setHistologyIcdO3("8100");
        i2.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("lip"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        //   - Upper gum C030 AND lower gum C031
        i1.setPrimarySite("C030");
        i2.setPrimarySite("C031");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("gum"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        //   - Nasal cavity C300 AND middle ear C301
        i1.setPrimarySite("C300");
        i2.setPrimarySite("C301");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Nasal"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        //   - Hard palate C050 AND/OR soft palate C051 AND/OR uvula C052
        i1.setPrimarySite("C050");
        i2.setPrimarySite("C051");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("palate"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setPrimarySite("C051");
        i2.setPrimarySite("C052");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("palate"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        //   - Maxillary sinus C310 AND/OR ethmoid sinus C311 AND/OR frontal sinus C312 AND/OR sphenoid sinus C313
        i1.setPrimarySite("C310");
        i2.setPrimarySite("C311");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("ethmoid"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setPrimarySite("C312");
        i2.setPrimarySite("C313");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("ethmoid"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setPrimarySite("C313");
        i2.setPrimarySite("C310");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("ethmoid"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        //   - Submandibular gland C080 AND sublingual gland C081
        i1.setPrimarySite("C080");
        i2.setPrimarySite("C081");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Submandibular"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        //Glottis C320 AND/OR supraglottis C321 AND/OR subglottis C322 AND/OR laryngeal cartilage C323
        i1.setPrimarySite("C320");
        i2.setPrimarySite("C322");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Glottis"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setPrimarySite("C321");
        i2.setPrimarySite("C323");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setPrimarySite("C320");
        i2.setPrimarySite("C323");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setPrimarySite("C321");
        i2.setPrimarySite("C322");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setPrimarySite("C321");
        i2.setPrimarySite("C321");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        //Maxilla C410 AND Mandible C411
        i1.setPrimarySite("C410");
        i2.setPrimarySite("C411");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Postcricoid C130 AND/OR hypopharyngeal aspect of aryepiglottic fold C131 AND/OR posterior wall of hypopharynx C132
        i1.setPrimarySite("C130");
        i2.setPrimarySite("C132");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setPrimarySite("C131");
        i2.setPrimarySite("C130");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        // Rule M4 Abstract multiple primaries when separate, non-contiguous tumors are present in sites with ICD-O site codes that differ at the second CXxx, and/or third characters CxXx.
        ruleStepToTest = "M4";
        ruleCountToTest = 2;
        i1.setPrimarySite("C000");
        i2.setPrimarySite("C148");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("topography"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setPrimarySite("C138");
        i2.setPrimarySite("C148");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("topography"));
        Assert.assertEquals(ruleStepToTest, output.getStep());

        // Rule M5 Abstract multiple primaries when there are separate, non-contiguous tumors on both the right side and the left side of a paired site.
        ruleStepToTest = "M5";
        ruleCountToTest = 3;
        i1.setPrimarySite("C312");
        i1.setHistologyIcdO3("8000");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2015");
        i2.setPrimarySite("C312");
        i2.setHistologyIcdO3("8100");
        i2.setBehaviorIcdO3("2");
        i2.setLaterality("2");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("paired"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setPrimarySite("C400");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M6 Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than five years after the original diagnosis or last recurrence.
        ruleStepToTest = "M6";
        ruleCountToTest = 4;
        i1.setPrimarySite("C147");
        i1.setBehaviorIcdO3("3");
        i1.setHistologyIcdO3("8000");
        i1.setDateOfDiagnosisYear("2018");
        i2.setPrimarySite("C148");
        i2.setBehaviorIcdO3("3");
        i2.setHistologyIcdO3("8000");
        i2.setDateOfDiagnosisYear("2009");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("5"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply
        i2.setDateOfDiagnosisYear("2015");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M7 Abstract multiple primaries when separate, non-contiguous tumors are two or more different subtypes/variants in Column 3 of the appropriate site table (Tables 1-9) in the Equivalent Terms and Definitions. Timing is irrelevant.
        ruleStepToTest = "M7";
        ruleCountToTest = 5;

        //no histology table for site, questionable
        i1.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisYear("2018");
        i1.setPrimarySite("C001");
        i1.setHistologyIcdO3("8900");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C002");
        i2.setHistologyIcdO3("8052");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        //no histology table for one of the sites, questionable
        i1.setPrimarySite("C130");
        i1.setHistologyIcdO3("8850");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C135");
        i2.setHistologyIcdO3("8052");
        i2.setBehaviorIcdO3("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());

        //table 2, 8071 and 8083 are different subtypes
        i1.setPrimarySite("C112");
        i1.setHistologyIcdO3("8071");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C118");
        i2.setHistologyIcdO3("8083");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // histology not in table 2, 8052 not in table
        i1.setHistologyIcdO3("8052");
        i2.setHistologyIcdO3("8071");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // histology not in table 2 but same histology, skip this step
        i1.setHistologyIcdO3("8052");
        i2.setHistologyIcdO3("8052");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertTrue(output.getAppliedRules().size() > ruleCountToTest);

        //Table 1: 8072 row:  New subtype/variant in column 3: Schneiderian carcinoma/cylindrical cell carcinoma 8121
        // Rule M7 Abstract multiple primaries when separate, non-contiguous tumors are two or more different subtypes/variants in Column 3 of the appropriate site table (Tables 1-9) in the Equivalent Terms and Definitions. Timing is irrelevant.

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
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, output.getGroupName());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        i1.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisYear("2021");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, output.getGroupName());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());

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
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, output.getGroupName());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        i1.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisYear("2021");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, output.getGroupName());
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
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, output.getGroupName());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        i1.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisYear("2021");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, output.getGroupName());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        //Table 4: 8070 row: New subtype/variant in column 3: Keratinizing squamous cell carcinoma 8071; Non-keratinizing squamous cell carcinoma 8072
        i1.setPrimarySite("C021");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("11");
        i1.setHistologyIcdO3("8071");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C023");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("11");
        i2.setHistologyIcdO3("8072");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, output.getGroupName());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        i1.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisYear("2021");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, output.getGroupName());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());


        // Rule M8 Abstract multiple primaries when separate, non-contiguous tumors are on different rows in the appropriate site table (Tables 1-9) in the Equivalent Terms and Definitions. Timing is irrelevant.
        ruleStepToTest = "M8";
        ruleCountToTest = 6;
        //table 3 in different rows
        i1.setPrimarySite("C130");
        i2.setPrimarySite("C139");
        i1.setHistologyIcdO3("8041");
        i2.setHistologyIcdO3("8070");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        //table 3 same row
        i1.setHistologyIcdO3("8560");
        i2.setHistologyIcdO3("8070");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertTrue(output.getAppliedRules().size() > ruleCountToTest);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        //table 3 different histologies; one or both not in table (questionable at M7)
        i1.setHistologyIcdO3("8075");
        i2.setHistologyIcdO3("8076");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals("M7", output.getStep());
        //table 3 same histology not in the table, continue
        i1.setHistologyIcdO3("8075");
        i2.setHistologyIcdO3("8075");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertTrue(output.getAppliedRules().size() > ruleCountToTest);
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
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, output.getGroupName());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        i1.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisYear("2021");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, output.getGroupName());
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
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, output.getGroupName());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        i1.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisYear("2021");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, output.getGroupName());
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
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, output.getGroupName());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        i1.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisYear("2021");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, output.getGroupName());
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
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, output.getGroupName());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        i1.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisYear("2021");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, output.getGroupName());
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
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, output.getGroupName());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        i1.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisYear("2021");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, output.getGroupName());
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
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, output.getGroupName());
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals("M7", output.getStep());
        Assert.assertEquals(5, output.getAppliedRules().size());
        i1.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisYear("2021");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, output.getGroupName());
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
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, output.getGroupName());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        i1.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisYear("2021");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, output.getGroupName());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());

        i1.setPrimarySite("C754");
        i1.setDateOfDiagnosisYear("2019");
        i1.setDateOfDiagnosisMonth("11");
        i1.setHistologyIcdO3("8680");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C754");
        i2.setDateOfDiagnosisYear("2019");
        i2.setDateOfDiagnosisMonth("11");
        i2.setHistologyIcdO3("8692");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, output.getGroupName());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        i1.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisYear("2021");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, output.getGroupName());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());

        i1.setPrimarySite("C479");
        i1.setHistologyIcdO3("8680");
        i2.setPrimarySite("C479");
        i2.setHistologyIcdO3("8690");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, output.getGroupName());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());

        i1.setHistologyIcdO3("8692");
        i2.setHistologyIcdO3("8693");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, output.getGroupName());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());

        i1.setHistologyIcdO3("8690");
        i2.setHistologyIcdO3("8692");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, output.getGroupName());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());

        i1.setHistologyIcdO3("8690");
        i2.setHistologyIcdO3("8693");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, output.getGroupName());
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());

        i1.setHistologyIcdO3("8690");
        i2.setHistologyIcdO3("8690");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, output.getGroupName());
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());

        i1.setHistologyIcdO3("8692");
        i2.setHistologyIcdO3("8692");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, output.getGroupName());
        Assert.assertTrue(output.getAppliedRules().size() > ruleCountToTest);

        i1.setHistologyIcdO3("8693");
        i2.setHistologyIcdO3("8693");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, output.getGroupName());
        Assert.assertTrue(output.getAppliedRules().size() > ruleCountToTest);

        i1.setHistologyIcdO3("8680");
        i2.setHistologyIcdO3("8680");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, output.getGroupName());
        Assert.assertTrue(output.getAppliedRules().size() > ruleCountToTest);

        // Rule M9 Abstract a single primary (the invasive)when an in situ tumor is diagnosed after an invasive tumor.
        ruleStepToTest = "M9";
        ruleCountToTest = 7;
        i1.setPrimarySite("C100");
        i1.setHistologyIcdO3("8071");
        i1.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("1");
        i2.setPrimarySite("C100");
        i2.setHistologyIcdO3("8071");
        i2.setBehaviorIcdO3("2");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply
        i1.setPrimarySite("C100");
        i1.setHistologyIcdO3("8071");
        i1.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("1");
        i2.setPrimarySite("C100");
        i2.setHistologyIcdO3("8071");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        // Does not apply
        i1.setPrimarySite("C100");
        i1.setHistologyIcdO3("8071");
        i1.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("1");
        i2.setPrimarySite("C100");
        i2.setHistologyIcdO3("8071");
        i2.setBehaviorIcdO3("6");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M10 Abstract a single primary (the invasive) when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor.
        ruleStepToTest = "M10";
        ruleCountToTest = 8;
        i1.setPrimarySite("C100");
        i1.setHistologyIcdO3("8070");
        i1.setBehaviorIcdO3("3");
        i1.setBehaviorIcdO3("2");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("1");
        i2.setPrimarySite("C100");
        i2.setHistologyIcdO3("8071");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("2");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("invasive tumor following an in situ tumor less than or equal to 60"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i2.setDateOfDiagnosisMonth("6");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M11 Abstract multiple primaries when an invasive tumor occurs more than 60 days after an in situ tumor.
        ruleStepToTest = "M11";
        ruleCountToTest = 9;
        i1.setPrimarySite("C100");
        i1.setHistologyIcdO3("8070");
        i1.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("6");
        i2.setPrimarySite("C100");
        i2.setHistologyIcdO3("8071");
        i2.setBehaviorIcdO3("2");
        i2.setDateOfDiagnosisYear("2017");
        i2.setDateOfDiagnosisMonth("7");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("invasive"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        // Questionable
        i2.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisMonth(null);
        i2.setDateOfDiagnosisMonth(null);
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(8, output.getAppliedRules().size());
        Assert.assertEquals("M10", output.getStep());

        // Rule M12 Abstract a single primary when separate/non-contiguous tumors in the same primary site are on the same row in the appropriate site table (Tables 1-9) in the Equivalent Terms and Definitions. Timing is irrelevant.
        // No Table 1 or 2
        ruleStepToTest = "M12";
        ruleCountToTest = 10;
        i1.setPrimarySite("C134");
        i1.setHistologyIcdO3("8900");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C135");
        i2.setHistologyIcdO3("8052");
        i2.setBehaviorIcdO3("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        // No Table 1
        i1.setPrimarySite("C134");
        i1.setHistologyIcdO3("8900");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C132");
        i2.setHistologyIcdO3("8070");
        i2.setBehaviorIcdO3("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        // No Table 2
        i1.setPrimarySite("C130");
        i1.setHistologyIcdO3("8850");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C135");
        i2.setHistologyIcdO3("8052");
        i2.setBehaviorIcdO3("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        // No Entry 1
        i1.setPrimarySite("C130");
        i1.setHistologyIcdO3("9000");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C132");
        i2.setHistologyIcdO3("8070");
        i2.setBehaviorIcdO3("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        // No Entry 2
        i1.setPrimarySite("C130");
        i1.setHistologyIcdO3("8240");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C132");
        i2.setHistologyIcdO3("8500");
        i2.setBehaviorIcdO3("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        // Same Histology - Diff Tables
        /* Caught by M3
        i1.setPrimarySite("C300");
        i1.setHistologyIcdO3("8144");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C301");
        i2.setHistologyIcdO3("8144");
        i2.setBehaviorIcdO3("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        */
        // Same Histology - Same Table
        i1.setPrimarySite("C100");
        i1.setHistologyIcdO3("8071");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C100");
        i2.setHistologyIcdO3("8071");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Same Row - 1
        i1.setPrimarySite("C479");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("11");
        i1.setHistologyIcdO3("8692");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C479");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("11");
        i2.setHistologyIcdO3("8692");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, output.getGroupName());
        Assert.assertEquals(MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        i1.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisYear("2021");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, output.getGroupName());
        Assert.assertEquals(MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());

        // Rule M13 Abstract a single primary  when none of the previous rules apply.
        //No case will reach here

        //9222/3 is added on the 2024 update, 8851 and 9222/3 multiple
        i1.setPrimarySite("C321");
        i1.setDateOfDiagnosisYear("2022");
        i1.setDateOfDiagnosisMonth("11");
        i1.setHistologyIcdO3("8851");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C328");
        i2.setDateOfDiagnosisYear("2022");
        i2.setDateOfDiagnosisMonth("11");
        i2.setHistologyIcdO3("9222");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, output.getGroupName());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals("M7", output.getStep());
        //9222/3 and 9220/3 same row
        i1.setHistologyIcdO3("9220");
        i2.setDateOfDiagnosisYear("2022");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, output.getGroupName());
        Assert.assertEquals(MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals("M12", output.getStep());
    }

    @Test
    public void test2018Kidney() {
        // Kidney Multiple Primary Rules - Text
        // C649
        // (Excludes lymphoma and leukemia – M9590-M9992 and Kaposi sarcoma M9140)

        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;
        String ruleStepToTest;
        int ruleCountToTest;

        // Rule M3 Abstract multiple primaries when multiple tumors are present in sites with ICD-O site codes that differ at the second (CXxx), third (CxXx) and/or fourth characters (CxxX).
        // This will never happen since all kidney tumors are C649

        // Rule M4 Abstract a single primary when there are bilateral nephroblastomas (previously called Wilms tumors).
        ruleStepToTest = "M4";
        ruleCountToTest = 2;
        i1.setPrimarySite("C649");
        i1.setHistologyIcdO3("8960");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2016");
        i2.setPrimarySite("C649");
        i2.setHistologyIcdO3("8960");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("2");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Bilateral nephroblastomas"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i2.setHistologyIcdO3("8961");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M5 Abstract multiple primaries when there are tumors in both the right kidney and in the left kidney.
        ruleStepToTest = "M5";
        ruleCountToTest = 3;
        i1.setPrimarySite("C649");
        i1.setHistologyIcdO3("8060");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2018");
        i2.setPrimarySite("C649");
        i2.setHistologyIcdO3("8960");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("2");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("both the right kidney and in the left"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i2.setLaterality("9");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());

        // Rule M6 Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than three years after the original diagnosis or last recurrence.
        ruleStepToTest = "M6";
        ruleCountToTest = 4;
        i1.setHistologyIcdO3("8400");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2014");
        i1.setDateOfDiagnosisMonth("1");
        i2.setHistologyIcdO3("8401");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("3"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setDateOfDiagnosisYear("2017");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());



        // Testing of NOS SubTypes for Rules M7 - M9.
        i1.setPrimarySite("C649");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("1");
        i2.setPrimarySite("C649");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("2");

        ruleStepToTest = "M7";

        i1.setHistologyIcdO3("8920");
        i2.setHistologyIcdO3("9180");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        i1.setHistologyIcdO3("9120"); i2.setHistologyIcdO3("9364");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        i1.setHistologyIcdO3("8316"); i2.setHistologyIcdO3("8900");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        i1.setHistologyIcdO3("8316"); i2.setHistologyIcdO3("8920");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        i1.setHistologyIcdO3("8316"); i2.setHistologyIcdO3("9180");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        i1.setHistologyIcdO3("8900"); i2.setHistologyIcdO3("8890");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        ruleStepToTest = "M8";

        i1.setHistologyIcdO3("8800"); i2.setHistologyIcdO3("8900");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        i1.setHistologyIcdO3("8800"); i2.setHistologyIcdO3("8910");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        i1.setHistologyIcdO3("8800"); i2.setHistologyIcdO3("8890");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        i1.setHistologyIcdO3("8900"); i2.setHistologyIcdO3("8920");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        //Not in the table but same hist -> same row
        i1.setHistologyIcdO3("8001");
        i2.setHistologyIcdO3("8001");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(MpResult.SINGLE_PRIMARY, output.getResult());

        i1.setHistologyIcdO3("8311"); i2.setHistologyIcdO3("8311");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());

        ruleStepToTest = "M9";

        i1.setHistologyIcdO3("8312"); i2.setHistologyIcdO3("8800");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        i1.setHistologyIcdO3("8312"); i2.setHistologyIcdO3("8900");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        i1.setHistologyIcdO3("8312"); i2.setHistologyIcdO3("8920");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        i1.setHistologyIcdO3("8312"); i2.setHistologyIcdO3("9180");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        i1.setHistologyIcdO3("8316"); i2.setHistologyIcdO3("8800");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        // Rule M7 Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 1 in the Equivalent Terms and Definitions. Tumors must be in same kidney and timing is irrelevant.
        ruleStepToTest = "M7";
        ruleCountToTest = 5;
        i1.setPrimarySite("C649");
        i1.setHistologyIcdO3("8310");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2018");
        i2.setPrimarySite("C649");
        i2.setHistologyIcdO3("8311");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("two or more different subtypes/variants in Column 3, Table 1"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setHistologyIcdO3("9120");
        i2.setHistologyIcdO3("8041");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("two or more different subtypes/variants in Column 3, Table 1"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setHistologyIcdO3("8960");
        i2.setHistologyIcdO3("8290");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setHistologyIcdO3("8316");
        i2.setHistologyIcdO3("9500");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M8 Abstract a single primary when synchronous, separate/non-contiguous tumors are on the same row in Table 1 in the Equivalent Terms and Definitions. Tumors must be in the same kidney.
        ruleStepToTest = "M8";
        ruleCountToTest = 6;
        i1.setPrimarySite("C649");
        i1.setHistologyIcdO3("8312");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2018");
        i2.setPrimarySite("C649");
        i2.setHistologyIcdO3("8310");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("tumors that are on the same row in Table 1"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setHistologyIcdO3("8041");
        i2.setHistologyIcdO3("8240");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("tumors that are on the same row in Table 1"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setHistologyIcdO3("8960");
        i2.setHistologyIcdO3("8042");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());

        // Rule M9 Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 1 in the Equivalent Terms and Definitions. Tumors must be in the same kidney and timing is irrelevant.
        ruleStepToTest = "M9";
        ruleCountToTest = 7;
        i1.setPrimarySite("C649");
        i1.setHistologyIcdO3("8240");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2018");
        i2.setPrimarySite("C649");
        i2.setHistologyIcdO3("8317");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("tumors that are on different rows in Table 1"));
        Assert.assertEquals(ruleStepToTest, output.getStep());

        // Rule M10 Abstract a single primary when an in situ tumor is diagnosed after an invasive tumor AND tumors occur in the same kidney.
        ruleStepToTest = "M10";
        ruleCountToTest = 8;
        i1.setHistologyIcdO3("8312");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2016");
        i1.setDateOfDiagnosisMonth("1");
        i2.setHistologyIcdO3("8317");
        i2.setBehaviorIcdO3("2");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("in situ tumor diagnosed following an invasive tumor"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setLaterality("1");
        i2.setLaterality("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i2.setLaterality("1");
        i1.setBehaviorIcdO3("2");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M11 Abstract a single primary (the invasive) when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor in the same kidney.
        //This doesn't hit anymore

        // Rule M12 Abstract multiple primaries when an invasive tumor occurs more than 60 days after an in situ tumor in the same kidney.
        ruleStepToTest = "M12";
        ruleCountToTest = 10;
        i1.setHistologyIcdO3("8312");
        i1.setBehaviorIcdO3("2");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2016");
        i1.setDateOfDiagnosisMonth("1");
        i2.setHistologyIcdO3("8317");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("invasive tumor following an in situ tumor more than 60 days after"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setLaterality("1");
        i2.setLaterality("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M13 Abstract a single primary when there are multiple tumors that do not meet any of the above criteria.
        ruleStepToTest = "M13";
        ruleCountToTest = 11;
        i1.setHistologyIcdO3("8312");
        i1.setBehaviorIcdO3("2");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2016");
        i1.setDateOfDiagnosisMonth("1");
        i2.setHistologyIcdO3("8317");
        i2.setBehaviorIcdO3("6");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
    }

    @Test
    public void test2018Lung() {
        // Lung Multiple Primary Rules
        // C340-C343, C348, C349
        // (Excludes lymphoma and leukemia M9590–M9992 and Kaposi sarcoma M9140)

        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;
        String ruleStepToTest;
        int ruleCountToTest;

        // Rule M3 Abstract multiple primaries when there are separate, non-contiguous tumors in sites with ICD-O site codes (C34_) that differ at the second CXxx and/or third character CxXx.
        // This will never be true, lung group is C340-C349, 2nd and 3rd characters are always the same.

        // Rule M4 Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than three years after the original diagnosis or last recurrence.
        ruleStepToTest = "M4";
        ruleCountToTest = 2;
        i1.setPrimarySite("C342");
        i1.setHistologyIcdO3("8046");
        i1.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("09");
        i2.setPrimarySite("C349");
        i2.setHistologyIcdO3("8046");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2015");
        //i2.setDateOfDiagnosisMonth(null);
        // Questionable
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        // Applies
        i2.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisMonth("08");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("3"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i2.setDateOfDiagnosisYear("2017");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M5 Abstract multiple primaries when there is at least one tumor that is small cell carcinoma 8041 or any small cell subtypes/variants and another tumor that is non-small cell carcinoma 8046 or any non-small cell carcinoma subtypes/variants.
        ruleStepToTest = "M5";
        ruleCountToTest = 3;
        i1.setPrimarySite("C342");
        i1.setHistologyIcdO3("8041");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("09");
        i2.setPrimarySite("C349");
        i2.setHistologyIcdO3("8046");
        i2.setDateOfDiagnosisYear("2016");
        i2.setDateOfDiagnosisMonth("08");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("small cell carcinoma 8041"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setHistologyIcdO3("8041");
        i2.setHistologyIcdO3("8031");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("small cell carcinoma 8041"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setHistologyIcdO3("8046");
        i2.setHistologyIcdO3("8045");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("small cell carcinoma 8041"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setHistologyIcdO3("8240");
        i2.setHistologyIcdO3("8022");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("small cell carcinoma 8041"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply
        i1.setHistologyIcdO3("8041");
        i2.setHistologyIcdO3("8045");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        // Does not apply
        i1.setHistologyIcdO3("8046");
        i2.setHistologyIcdO3("8239");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M6 Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 3 in the Equivalent Terms and Definitions.  Timing is irrelevant.
        ruleStepToTest = "M6";
        ruleCountToTest = 4;
        i1.setHistologyIcdO3("8480");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("09");
        i2.setHistologyIcdO3("8072");
        i2.setDateOfDiagnosisYear("2016");
        i2.setDateOfDiagnosisMonth("08");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("two or more different subtypes/variants in Column 3, Table 3"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setHistologyIcdO3("8333");
        i2.setHistologyIcdO3("8083");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("two or more different subtypes/variants in Column 3, Table 3"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setHistologyIcdO3("8045");
        i2.setHistologyIcdO3("8041");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("two or more different subtypes/variants in Column 3, Table 3"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        //
        i1.setHistologyIcdO3("8250");
        i1.setBehaviorIcdO3("2");
        i2.setHistologyIcdO3("8256");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("two or more different subtypes/variants in Column 3, Table 3"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setHistologyIcdO3("8041");
        i2.setHistologyIcdO3("9000");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        //8013 is removed from sarcoma row and added to its own row
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
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_LUNG, output.getGroupName());
        Assert.assertTrue(output.getAppliedRules().size() > ruleCountToTest);
        i1.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisYear("2019");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_LUNG, output.getGroupName());
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
        i2.setHistologyIcdO3("8041");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("2");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("08");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("same row in Table 3"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setHistologyIcdO3("8070");
        i2.setHistologyIcdO3("8071");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("same row in Table 3"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setHistologyIcdO3("8430");
        i2.setHistologyIcdO3("8022");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setHistologyIcdO3("8560");
        i2.setHistologyIcdO3("8083");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        // Small cell carcinoma 8041/3 row: “Neuroendocrine carcinoma, NOS” removed from beneath Typical Carcinoid 8240/3 and added as its own subtype/variant with the code 8246/3
        i1.setHistologyIcdO3("8041");
        i2.setHistologyIcdO3("8240");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        //Histologies not in table->
        i1.setHistologyIcdO3("8000");
        i2.setHistologyIcdO3("8002");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        //8013 is moved to its row
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
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_LUNG, output.getGroupName());
        Assert.assertTrue(output.getAppliedRules().size() > ruleCountToTest);
        i1.setDateOfDiagnosisYear("2021");
        i2.setDateOfDiagnosisYear("2021");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_LUNG, output.getGroupName());
        Assert.assertTrue(output.getAppliedRules().size() > ruleCountToTest);

        // Rule M8 Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 3 in the Equivalent Terms and Definitions or a combination code in Table 2 and a code from Table 3. Timing is irrelevant.
        ruleStepToTest = "M8";
        ruleCountToTest = 6;
        i1.setHistologyIcdO3("8200");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("09");
        i2.setHistologyIcdO3("8430");
        i2.setDateOfDiagnosisYear("2016");
        i2.setDateOfDiagnosisMonth("08");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("tumors on different rows in Table 3"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setHistologyIcdO3("8560");
        i2.setHistologyIcdO3("8072");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("tumors on different rows in Table 3"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        //Table 2 and table 3
        i1.setHistologyIcdO3("8072");
        i2.setHistologyIcdO3("8033");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("tumors on different rows in Table 3"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setHistologyIcdO3("8140");
        i2.setHistologyIcdO3("8480");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setHistologyIcdO3("8041");
        i2.setHistologyIcdO3("8246");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M9 Abstract a single primaryi when there are simultaneous multiple tumors:
        //- In both lungs (multiple in right and multiple in left) OR
        //- In the same lung OR
        //- Single tumor in one lung; multiple tumors in contralateral lung
        ruleStepToTest = "M9";
        ruleCountToTest = 7;
        i1.setPrimarySite("C342");
        i1.setHistologyIcdO3("8041");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("09");
        i2.setPrimarySite("C341");
        i2.setHistologyIcdO3("8246");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("2");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("08");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Unknown if multiple tumors exist."));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setLaterality("4");
        i2.setLaterality("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("both lungs, the same lung, or opposite lungs"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setLaterality("4");
        i2.setLaterality("4");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("both lungs, the same lung, or opposite lungs"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setLaterality("2");
        i2.setLaterality("4");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("both lungs, the same lung, or opposite lungs"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setLaterality("5");
        i2.setLaterality("4");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("both lungs, the same lung, or opposite lungs"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setLaterality("4");
        i2.setLaterality("9");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("both lungs, the same lung, or opposite lungs"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setLaterality("2");
        i2.setLaterality("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("M9"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setDateOfDiagnosisMonth("09");
        i2.setDateOfDiagnosisMonth("01");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());


        // Rule M10 Abstract a single primary when an in situ tumor is diagnosed after an invasive tumor AND tumors occur in the same lung.
        ruleStepToTest = "M10";
        ruleCountToTest = 8;
        i1.setPrimarySite("C342");
        i1.setHistologyIcdO3("8140");
        i1.setBehaviorIcdO3("2");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("09");
        i2.setPrimarySite("C349");
        i2.setHistologyIcdO3("8253");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("05");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("in situ tumor diagnosed following an invasive tumor in the same lung"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setLaterality("2");
        i2.setLaterality("2");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M11 Abstract multiple primaries when there is a single tumor in each lung (one tumor in the right lung and one tumor in the left lung).
        ruleStepToTest = "M11";
        ruleCountToTest = 9;
        i1.setPrimarySite("C342");
        i1.setHistologyIcdO3("8140");
        i1.setBehaviorIcdO3("2");
        i1.setLaterality("0");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("09");
        i2.setPrimarySite("C349");
        i2.setHistologyIcdO3("8253");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("2");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("06");
        // Applies.
        i1.setLaterality("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("single tumor in each lung"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setLaterality("2");
        i2.setLaterality("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M12 Abstract a single primary (the invasive) when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor in the same lung.
        // This cannot be triggered. Rule M9 will catch all of these cases. This is an intentional backup rule for "humans" who don't use M9 correctly.
        /*
        ruleStepToTest = "M12";
        ruleCountToTest = 10;
        i1.setPrimarySite("C342");
        i1.setHistologyIcdO3("8253");
        i1.setBehaviorIcdO3("2");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("07");
        i1.setDateOfDiagnosisDay("01");
        i2.setPrimarySite("C349");
        i2.setHistologyIcdO3("8140");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("08");
        i2.setDateOfDiagnosisDay("02");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("invasive tumor diagnosed less than or equal to 60 days after an in situ "));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i2.setDateOfDiagnosisMonth("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i2.setDateOfDiagnosisMonth("7");
        i1.setLaterality("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        */

        // Rule M13 Abstract multiple primaries when an invasive tumor occurs more than 60 days after an in situ tumor in the same lung.
        ruleStepToTest = "M13";
        ruleCountToTest = 11;
        i1.setPrimarySite("C342");
        i1.setHistologyIcdO3("8253");
        i1.setBehaviorIcdO3("2");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("3");
        i2.setPrimarySite("C349");
        i2.setHistologyIcdO3("8140");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("11");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("invasive tumor following an in situ tumor in the same lung more than 60 days"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setDateOfDiagnosisMonth("10");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M14 Abstract a single primary when none of the previous rules apply.
        // Cannot be tested.
        /*
        ruleStepToTest = "M14";
        ruleCountToTest = 12;
        i1.setPrimarySite("C342");
        i1.setHistologyIcdO3("8253");
        i1.setBehaviorIcdO3("2");
        i1.setLaterality("4");
        i1.setDateOfDiagnosisYear("2017");
        i1.setDateOfDiagnosisMonth("1");
        i2.setPrimarySite("C348");
        i2.setHistologyIcdO3("8140");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("2");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("11");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("do not meet any of the criteria"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        */
    }

    @Test
    public void test2018MalignantCNSAndPeripheralNerves() {
        // Malignant CNS and Peripheral Nerves Histology Rules
        // C470-C479, C700, C701, C709, C710-C719, C721-C725, C728, C729, C751-C753
        // (Excludes lymphoma and leukemia M9590-M9992 and Kaposi sarcoma M9140)

        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;
        String ruleStepToTest;
        int ruleCountToTest;

        // Rule M6 Abstract multiple primaries when there are multiple CNS tumors, one of which is malignant /3 and the other is non-malignant /0 or /1.
        // - Original non-malignant tumor followed by malignant tumor
        //      Patient had a resection of the non-malignant tumor (not the same tumor) OR
        //      It is unknown/not documented if the patient had a resection
        // - Simultaneous non-malignant and malignant tumors
        //      Abstract both the malignant and the non-malignant tumors

        // NOTE: This may not be testable. If one tumor is malignant, and the other is not, you get two different rule sets:
        // Mp2018NonMalignantCNSTumorsGroup and Mp2018MalignantCNSAndPeripheralNervesGroup. Two different rule sets are automatically
        // set to multiple primaries by MphUtuls.computePrimaries().
        /*
        ruleStepToTest = "M6";
        ruleCountToTest = 1;
        i1.setPrimarySite("C700");
        i1.setHistologyIcdO3("9440");
        i1.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("09");
        i2.setPrimarySite("C725");
        i2.setHistologyIcdO3("8050");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2016");
        i2.setDateOfDiagnosisMonth("08");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setBehaviorIcdO3("1");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        */

        // Rule M7 Abstract multiple primaries when a patient has a glial or astrocytic tumor and is subsequently diagnosed with a glioblastoma multiforme 9440 (GBM).
        // GLIAL_TUMOR_2018 = GroupUtility.expandList("9385,9391,9392,9393,9396,9400,9401,9411,9424,9430,9440,9441,9442,9445,9450,9451");
        ruleStepToTest = "M7";
        ruleCountToTest = 2;
        i1.setPrimarySite("C700");
        i1.setHistologyIcdO3("9385");
        i1.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("06");
        i2.setPrimarySite("C725");
        i2.setHistologyIcdO3("9440");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("08");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setPrimarySite("C700");
        i1.setHistologyIcdO3("9430");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C725");
        i2.setHistologyIcdO3("9440");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        //9440/3 followed by 9440/3 should not apply
        i1.setHistologyIcdO3("9440");
        i2.setHistologyIcdO3("9440");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Does not apply.
        i1.setHistologyIcdO3("9385");
        i2.setHistologyIcdO3("9440");
        i1.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisYear("2017");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setHistologyIcdO3("9443");
        i1.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2017");
        i2.setHistologyIcdO3("9440");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M8 Abstract a single primary when there are separate, non-contiguous tumors in the brain (multicentric/multifocal) with the same histology XXXX.  Tumors may be any of the following combinations:
        // - In the same lobe; for example, two tumors in right temporal lobe C712 (same site code)
        // - Different lateralities of the same lobe; for example, left and right frontal lobes C711 (same site code)
        // - In different lobes; for example, parietal lobe C713 and occipital lobe C714 (different site codes)
        ruleStepToTest = "M8";
        ruleCountToTest = 3;
        i1.setPrimarySite("C712");
        i1.setHistologyIcdO3("9443");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C712");
        i2.setHistologyIcdO3("9443");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setPrimarySite("C711");
        i1.setLaterality("0");
        i2.setPrimarySite("C711");
        i2.setLaterality("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setPrimarySite("C713");
        i2.setPrimarySite("C714");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setPrimarySite("C729");
        i2.setPrimarySite("C714");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M9 Abstract multiple primaries when multiple tumors are present in any of the following sites or subsites:
        // - Any lobe of the brain C710-C719 AND any other part of CNS
        // - Cauda equina C721 AND any other part of CNS
        // - Cerebral meninges C700 AND spinal meninges C701
        // - Cerebral meninges C700 AND any other part of CNS
        // - Any one of the cranial nerves C722-C725 AND any other part of the CNS
        // - Any two or more of the cranial nerves
        //    C722 Olfactory, C723 Optic, C724 Acoustic, C725 Cranial nerves NOS
        // - Meninges of cranial or peripheral nerves C709 AND any other part of the CNS
        // - Spinal cord C720 AND any other part of CNS
        // - Spinal meninges C701 AND any other part of CNS
        ruleStepToTest = "M9";
        ruleCountToTest = 4;
        i1.setPrimarySite("C710");
        i1.setHistologyIcdO3("9440");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C729");
        i2.setHistologyIcdO3("8050");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setPrimarySite("C700");
        i2.setPrimarySite("C701");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setPrimarySite("C700");
        i2.setPrimarySite("C729");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setPrimarySite("C724");
        i2.setPrimarySite("C476");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setPrimarySite("C709");
        i2.setPrimarySite("C476");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setPrimarySite("C701");
        i2.setPrimarySite("C476");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setPrimarySite("C728");
        i2.setPrimarySite("C471");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Testing of NOS Subtypes for rules M10 - M13.
        i1.setPrimarySite("C728");
        i1.setHistologyIcdO3("9401");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C729");
        i2.setHistologyIcdO3("9396");
        i2.setBehaviorIcdO3("3");

        ruleStepToTest = "M10";
        i1.setHistologyIcdO3("9220"); i2.setHistologyIcdO3("8890");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        i1.setHistologyIcdO3("9220"); i2.setHistologyIcdO3("8896");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        i1.setHistologyIcdO3("9240"); i2.setHistologyIcdO3("8896");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        i1.setHistologyIcdO3("9220"); i2.setHistologyIcdO3("8891");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        i1.setHistologyIcdO3("9220"); i2.setHistologyIcdO3("9180");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        i1.setHistologyIcdO3("8896"); i2.setHistologyIcdO3("8802");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        i1.setHistologyIcdO3("9180"); i2.setHistologyIcdO3("8802");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        i1.setHistologyIcdO3("9401"); i2.setHistologyIcdO3("8890");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        i1.setHistologyIcdO3("9401"); i2.setHistologyIcdO3("9240");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        i1.setHistologyIcdO3("9401"); i2.setHistologyIcdO3("8802");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        ruleStepToTest = "M11";
        i1.setHistologyIcdO3("9380"); i2.setHistologyIcdO3("9430");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        i1.setHistologyIcdO3("9380"); i2.setHistologyIcdO3("9385");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        i1.setHistologyIcdO3("9380"); i2.setHistologyIcdO3("9396");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        ruleStepToTest = "M12";
        i1.setHistologyIcdO3("8800"); i2.setHistologyIcdO3("9220");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        i1.setHistologyIcdO3("8800"); i2.setHistologyIcdO3("9240");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        i1.setHistologyIcdO3("8800"); i2.setHistologyIcdO3("9180");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        ruleStepToTest = "M13";
        i1.setHistologyIcdO3("9390"); i2.setHistologyIcdO3("8800");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        i1.setHistologyIcdO3("9390"); i2.setHistologyIcdO3("8890");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        i1.setHistologyIcdO3("9390"); i2.setHistologyIcdO3("9240");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        i1.setHistologyIcdO3("9390"); i2.setHistologyIcdO3("8802");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        i1.setHistologyIcdO3("9401"); i2.setHistologyIcdO3("8800");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        // Rule M10 Abstract multiple primaries when separate, non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 3 in the Equivalent Terms and Definitions. Timing is irrelevant.
        ruleStepToTest = "M10";
        ruleCountToTest = 5;
        i1.setPrimarySite("C728");
        i1.setHistologyIcdO3("9401");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C729");
        i2.setHistologyIcdO3("9396");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setHistologyIcdO3("9471");
        i2.setHistologyIcdO3("8802");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        //8859, 9366, 9268 added as subtypes for 8800
        i1.setHistologyIcdO3("8859");
        i2.setHistologyIcdO3("9366");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        i1.setHistologyIcdO3("8859");
        i2.setHistologyIcdO3("9268");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setHistologyIcdO3("9071");
        i2.setHistologyIcdO3("8892");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M12 Abstract a single primary when separate, non-contiguous tumors are on the same row in Table 3 in the Equivalent Terms and Definitions. Timing is irrelevant.
        ruleStepToTest = "M12";
        ruleCountToTest = 7;
        i1.setPrimarySite("C728");
        i1.setHistologyIcdO3("9490");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C729");
        i2.setHistologyIcdO3("9473");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setHistologyIcdO3("9070");
        i2.setHistologyIcdO3("9071");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        //8728 is not subtype of 8720 anymore
        i1.setHistologyIcdO3("8720");
        i2.setHistologyIcdO3("8728");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());
        //8859, 9366, 9268 added as subtypes for 8800
        i1.setHistologyIcdO3("8800");
        i2.setHistologyIcdO3("8859");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        i1.setHistologyIcdO3("8800");
        i2.setHistologyIcdO3("9366");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        i1.setHistologyIcdO3("8800");
        i2.setHistologyIcdO3("9268");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setHistologyIcdO3("9505");
        i2.setHistologyIcdO3("9430");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        // 8815 row behavior added 8815/3
        i1.setHistologyIcdO3("8815");
        i1.setBehaviorIcdO3("3");
        i2.setHistologyIcdO3("8815");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i2.setBehaviorIcdO3("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertNotEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertNotEquals(ruleStepToTest, output.getStep());


        // Rule M13 Abstract multiple primaries when separate, non-contiguous tumors are on different rows in Table 3 in the Equivalent Terms and Definitions. Timing is irrelevant.
        ruleStepToTest = "M13";
        ruleCountToTest = 8;
        i1.setPrimarySite("C728");
        i1.setHistologyIcdO3("9505");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C729");
        i2.setHistologyIcdO3("9430");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setHistologyIcdO3("9490");
        i2.setHistologyIcdO3("9473");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setHistologyIcdO3("9070");
        i2.setHistologyIcdO3("9071");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        // 8000/3
        i1.setHistologyIcdO3("8000");
        i2.setHistologyIcdO3("8693");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertTrue(output.getReason().contains("8000/3"));

        // Rule M14 Abstract a single primary when multiple tumors do not meet any of the above criteria.
        // Can't Reach This.
        /*
        ruleStepToTest = "M14";
        ruleCountToTest = 9;
        i1.setHistologyIcdO3("9071");
        i2.setHistologyIcdO3("8888");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setHistologyIcdO3("9397");
        i2.setHistologyIcdO3("9397");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        */
    }

    @Test
    public void test2018NonMalignantCNSTumors() {
        // Non-Malignant CNS Multiple Primary Rules
        // C700, C701, C709, C710-C719, C720-C725, C728, C729, C751-C753
        // (Excludes lymphoma and leukemia M9590 – M9992 and Kaposi sarcoma M9140)

        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;
        // Rule M5 Abstract a single primary when a neoplasm is originally diagnosed as low-grade glioma and subsequently recurs in residual tumor with a more specific histology.
        // - Glioma=9380/1; any other histology in table 6 is a more specific histology
        String ruleStepToTest = "M5";
        int ruleCountToTest = 1;
        i1.setPrimarySite("C701");
        i1.setHistologyIcdO3("9380");
        i1.setBehaviorIcdO3("1");
        i1.setDateOfDiagnosisYear("2018");
        i2.setPrimarySite("C701");
        i2.setHistologyIcdO3("9493");
        i2.setBehaviorIcdO3("0");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i2.setHistologyIcdO3("8825");
        i2.setBehaviorIcdO3("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());

        // Rule M6 Abstract multiple primaries when a malignant tumor /3 occurs after a non-malignant tumor /0 or /1 AND:
        // - The patient had a resection of the non-malignant tumor OR
        // - It is unknown/not documented whether a resection was done

        // NOTE: This may not be testable. If one tumor is malignant, and the other is not, you get two different rule sets:
        // Mp2018NonMalignantCNSTumorsGroup and Mp2018MalignantCNSAndPeripheralNervesGroup. Two different rule sets are automatically
        // set to multiple primaries by MphUtuls.computePrimaries().
        i1.setPrimarySite("C701");
        i1.setHistologyIcdO3("9390");
        i1.setBehaviorIcdO3("0");
        i1.setDateOfDiagnosisYear("2018");
        i2.setPrimarySite("C701");
        i2.setHistologyIcdO3("9390");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());

        // Rule M7 Abstract a single primary when the patient has bilateral:
        // - Acoustic neuromas/ vestibular schwannomas 9560/0, OR
        // - Optic gliomas/pilocytic astrocytomas 9421/1
        ruleStepToTest = "M7";
        ruleCountToTest = 3;
        i1.setPrimarySite("C700");
        i1.setHistologyIcdO3("9560");
        i1.setBehaviorIcdO3("0");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2017");
        i2.setPrimarySite("C700");
        i2.setHistologyIcdO3("9560");
        i2.setBehaviorIcdO3("0");
        i2.setLaterality("2");
        i2.setDateOfDiagnosisYear("2018");
        //C700-1/9560/0 && //C700-2/9560/0
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        //C700-1/9560/0 && //C700-1/9560/0 (unilateral should go to the next rule)
        i2.setLaterality("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertNotEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        //9421/1
        i1.setPrimarySite("C725");
        i1.setHistologyIcdO3("9421");
        i1.setBehaviorIcdO3("1");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2017");
        i2.setPrimarySite("C725");
        i2.setHistologyIcdO3("9421");
        i2.setBehaviorIcdO3("1");
        i2.setLaterality("2");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i2.setBehaviorIcdO3("0");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setHistologyIcdO3("9560");
        i1.setBehaviorIcdO3("0");
        i2.setHistologyIcdO3("9421");
        i2.setBehaviorIcdO3("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        //Example from a registry
        //C725 a paired and C720 not paired
        i1.setPrimarySite("C725");
        i1.setHistologyIcdO3("9560");
        i1.setBehaviorIcdO3("0");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2018");
        i2.setPrimarySite("C720");
        i2.setHistologyIcdO3("9560");
        i2.setBehaviorIcdO3("0");
        i2.setLaterality("0");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());

        // Rule M8 Abstract multiple primaries when multiple tumors are present in any of the following sites:
        // - Any lobe(s) of the brain C710-C719 AND any other part of CNS
        // - Cerebral meninges C700 AND spinal meninges C701
        // - Cerebral meninges C700 AND any other part of CNS
        // - Any cranial nerve(s) C721-C725 AND any other part of the CNS
        // - Meninges of cranial nerves C709 AND any other part of the CNS
        // - Spinal meninges C701 AND any other part of CNS
        // (Any other part of the CNS is any other site in the header...for example "cerebral meninges C700 and any other part of the CNS" equates to C700 and any
        //  other site in the header besides C700 (C701, C709, C710-C719, C721-C725, C728, C729, C751-C753))
        ruleStepToTest = "M8";
        ruleCountToTest = 4;
        i1.setPrimarySite("C710");
        i1.setHistologyIcdO3("9440");
        i1.setBehaviorIcdO3("0");
        i2.setPrimarySite("C729");
        i2.setHistologyIcdO3("8050");
        i2.setBehaviorIcdO3("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setPrimarySite("C700");
        i2.setPrimarySite("C701");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setPrimarySite("C700");
        i2.setPrimarySite("C729");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setPrimarySite("C724");
        i2.setPrimarySite("C751");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setPrimarySite("C709");
        i2.setPrimarySite("C753");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setPrimarySite("C701");
        i2.setPrimarySite("C714");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setPrimarySite("C721");
        i2.setPrimarySite("C722");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setPrimarySite("C728");
        i1.setHistologyIcdO3("9530");
        i1.setBehaviorIcdO3("0");
        i2.setPrimarySite("C471");
        i2.setHistologyIcdO3("9530");
        i2.setBehaviorIcdO3("0");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M9 Abstract multiple primaries when separate, non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 6 in the Equivalent Terms and Definitions. Timing is irrelevant.
        ruleStepToTest = "M9";
        ruleCountToTest = 5;
        i1.setPrimarySite("C701");
        i1.setHistologyIcdO3("9534");
        i1.setBehaviorIcdO3("0");
        i2.setPrimarySite("C701");
        i2.setHistologyIcdO3("9390");
        i2.setBehaviorIcdO3("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setHistologyIcdO3("8825");
        i1.setBehaviorIcdO3("1");
        i2.setHistologyIcdO3("9493");
        i2.setBehaviorIcdO3("0");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setHistologyIcdO3("8825");
        i1.setBehaviorIcdO3("1");
        i2.setHistologyIcdO3("9121");
        i2.setBehaviorIcdO3("0");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setHistologyIcdO3("9493");
        i1.setBehaviorIcdO3("0");
        i2.setHistologyIcdO3("9493");
        i2.setBehaviorIcdO3("0");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setHistologyIcdO3("9900");
        i1.setBehaviorIcdO3("0");
        i2.setHistologyIcdO3("9901");
        i2.setBehaviorIcdO3("0");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M10 Abstract a single primary when two or more separate/non-contiguous meningiomas arise in the cranial meninges.  Laterality is irrelevant and may be any of the following combinations:
        // - The same laterality (left or right) of the cranial meninges
        // - Bilateral (both left and right) cranial meninges
        // - The midline AND in either the right or left cranial meninges
        ruleStepToTest = "M10";
        ruleCountToTest = 6;
        i1.setPrimarySite("C700");
        i1.setHistologyIcdO3("9530");
        i1.setBehaviorIcdO3("0");
        i1.setDateOfDiagnosisYear("2017");
        i2.setPrimarySite("C700");
        i2.setHistologyIcdO3("9534");
        i2.setBehaviorIcdO3("0");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setHistologyIcdO3("9530");
        i1.setBehaviorIcdO3("0");
        i2.setHistologyIcdO3("9537");
        i2.setBehaviorIcdO3("0");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setPrimarySite("C726");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M11 Abstract a single primary when there are separate/non-contiguous tumors in the brain (multicentric/multifocal) with the same histology XXXX.  Tumors may be in any of the following locations and/or lateralities:
        // - Same laterality: In the same lobe; for example, two tumors in right temporal lobe C712 (same site code)
        // - Different lateralities of the same lobe; for example, left and right frontal lobes C711 (same site code)
        // - Different lobes; for example, parietal lobe C713 and occipital lobe C714 (different site codes)
        ruleStepToTest = "M11";
        ruleCountToTest = 7;
        i1.setPrimarySite("C712");
        i1.setHistologyIcdO3("8050");
        i1.setBehaviorIcdO3("0");
        i2.setPrimarySite("C712");
        i2.setHistologyIcdO3("8050");
        i2.setBehaviorIcdO3("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setPrimarySite("C711");
        i1.setLaterality("0");
        i2.setPrimarySite("C711");
        i2.setLaterality("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        //Special case 9413 and 9509
        i1.setHistologyIcdO3("9413");
        i1.setBehaviorIcdO3("0");
        i2.setHistologyIcdO3("9413");
        i2.setBehaviorIcdO3("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setHistologyIcdO3("9413");
        i1.setBehaviorIcdO3("1");
        i2.setHistologyIcdO3("9413");
        i2.setBehaviorIcdO3("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setHistologyIcdO3("9509");
        i1.setBehaviorIcdO3("0");
        i2.setHistologyIcdO3("9509");
        i2.setBehaviorIcdO3("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setHistologyIcdO3("9509");
        i1.setBehaviorIcdO3("1");
        i2.setHistologyIcdO3("9509");
        i2.setBehaviorIcdO3("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setPrimarySite("C729");
        i2.setPrimarySite("C714");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M12 Abstract a single primary when separate/non-contiguous tumors are on the same row in Table 6 in the Equivalent Terms and Definitions.  Timing is irrelevant.
        ruleStepToTest = "M12";
        ruleCountToTest = 8;
        i1.setPrimarySite("C728");
        i1.setHistologyIcdO3("9300");
        i1.setBehaviorIcdO3("1");
        i2.setPrimarySite("C729");
        i2.setHistologyIcdO3("9493");
        i2.setBehaviorIcdO3("0");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        i1.setPrimarySite("C728");
        i1.setHistologyIcdO3("9350");
        i1.setBehaviorIcdO3("1");
        i2.setPrimarySite("C729");
        i2.setHistologyIcdO3("9351");
        i2.setBehaviorIcdO3("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setHistologyIcdO3("9492");
        i1.setBehaviorIcdO3("0");
        i2.setHistologyIcdO3("9493");
        i2.setBehaviorIcdO3("0");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setHistologyIcdO3("9431");
        i1.setBehaviorIcdO3("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setHistologyIcdO3("9444");
        i1.setBehaviorIcdO3("1");
        i2.setHistologyIcdO3("9413");
        i2.setBehaviorIcdO3("0");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        //9122/0 added to the table
        i1.setHistologyIcdO3("9122");
        i1.setBehaviorIcdO3("0");
        i2.setHistologyIcdO3("9120");
        i2.setBehaviorIcdO3("0");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_NON_MALIGNANT_CNS, output.getGroupName());
        //9421/1 and 9431/1 are in a same row
        i1.setHistologyIcdO3("9421");
        i1.setBehaviorIcdO3("1");
        i2.setHistologyIcdO3("9431");
        i2.setBehaviorIcdO3("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());


        //8000/0 and 8000/1
        i1.setHistologyIcdO3("8000");
        i1.setBehaviorIcdO3("0");
        i2.setHistologyIcdO3("8000");
        i2.setBehaviorIcdO3("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(MphConstants.SOLID_TUMOR_2018_NON_MALIGNANT_CNS, output.getGroupName());
        i1.setHistologyIcdO3("8825");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());
        Assert.assertTrue(output.getReason().contains("8000/1"));
        i1.setHistologyIcdO3("8000");
        i2.setHistologyIcdO3("9539");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());
        Assert.assertTrue(output.getReason().contains("8000/0"));

        //9380/1 added to the table
        i1.setPrimarySite("C728");
        i1.setHistologyIcdO3("9380");
        i1.setBehaviorIcdO3("1");
        i2.setPrimarySite("C729");
        i2.setHistologyIcdO3("9380");
        i2.setBehaviorIcdO3("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());

        //8815/0 removed
        i1.setPrimarySite("C728");
        i1.setHistologyIcdO3("8815");
        i1.setBehaviorIcdO3("0");
        i2.setPrimarySite("C729");
        i2.setHistologyIcdO3("8815");
        i2.setBehaviorIcdO3("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());


        // Rule M13 Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 6 in the Equivalent Terms and Definitions. Timing is irrelevant.
        ruleStepToTest = "M13";
        ruleCountToTest = 9;
        i1.setHistologyIcdO3("9444");
        i1.setBehaviorIcdO3("1");
        i2.setHistologyIcdO3("9413");
        i2.setBehaviorIcdO3("0");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setHistologyIcdO3("9390");
        i1.setBehaviorIcdO3("0");
        i2.setHistologyIcdO3("9390");
        i2.setBehaviorIcdO3("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setHistologyIcdO3("9492");
        i1.setBehaviorIcdO3("0");
        i2.setHistologyIcdO3("9493");
        i2.setBehaviorIcdO3("0");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M14 Abstract a single primary when the tumors do not meet any of the above criteria.
        // Can't seem to get to this rule.
        /*
        ruleStepToTest = "M14";
        ruleCountToTest = 10;
        i1.setPrimarySite("C728");
        i1.setHistologyIcdO3("9440");
        i1.setBehaviorIcdO3("0");
        i2.setPrimarySite("C470");
        i2.setHistologyIcdO3("8050");
        i2.setBehaviorIcdO3("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        */
    }

    @Test
    public void test2018Urinary() {
        // Renal Pelvis, Ureter, Bladder, and Other Urinary Multiple Primary Rules
        // C659, C669, C670-C679, C680-C689
        // (Excludes lymphoma and leukemia M9590 – M9992 and Kaposi sarcoma M9140)

        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;
        String ruleStepToTest;
        int ruleCountToTest;

        // Rule M3 Abstract multiple primaries when there are:
        // - Separate/non-contiguous tumors in both the right AND left renal pelvis AND
        // - No other urinary sites are involved with separate/non-contiguous tumors
        ruleStepToTest = "M3";
        ruleCountToTest = 1;
        i1.setPrimarySite("C659");
        i1.setHistologyIcdO3("8720");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2015");
        i2.setPrimarySite("C659");
        i2.setHistologyIcdO3("8780");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("2");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("right renal pelvis AND tumor(s) in the left renal pelvis"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setLaterality("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M4 Abstract multiple primaries when there are:
        // - Separate/non-contiguous tumors in the right AND left ureter AND
        // - No other urinary sites are involved with separate/non-contiguous tumors
        ruleStepToTest = "M4";
        ruleCountToTest = 2;
        i1.setPrimarySite("C669");
        i2.setPrimarySite("C669");
        i1.setLaterality("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("right ureter AND tumor(s) in the left ureter"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setLaterality("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M5 Abstract a single primary when tumors are noninvasive in situ /2 urothelial carcinoma (flat tumor) 8120/2 in the following sites:
        //- Bladder C67_ AND
        //- One or both ureter(s) C669
        ruleStepToTest = "M5";
        ruleCountToTest = 3;
        i1.setPrimarySite("C670");
        i1.setHistologyIcdO3("8120");
        i1.setBehaviorIcdO3("2");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("2");
        i2.setPrimarySite("C669");
        i2.setHistologyIcdO3("8120");
        i2.setBehaviorIcdO3("2");
        i2.setLaterality("2");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("bladder (C670-C679) and ureter (C669)"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setPrimarySite("C669");
        i2.setPrimarySite("C675");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("bladder (C670-C679) and ureter (C669)"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setPrimarySite("C680");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i2.setHistologyIcdO3("8131");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M6 Abstract multiple primaries when an invasive tumor occurs more than 60 days after an in situ tumor.
        ruleStepToTest = "M6";
        ruleCountToTest = 4;
        i1.setPrimarySite("C670");
        i1.setHistologyIcdO3("8070");
        i1.setBehaviorIcdO3("2");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("1");
        i2.setPrimarySite("C670");
        i2.setHistologyIcdO3("8051");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("2");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("7");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("in situ tumor more than 60 days after"));
        Assert.assertEquals(ruleStepToTest, output.getStep());

        // Rule M7 Abstract a single primary when the patient has multiple occurrences of /2 urothelial carcinoma in the bladder. Tumors may be any combination of:
        //-In situ urothelial carcinoma 8120/2 AND/OR
        //-Papillary urothelial carcinoma noninvasive 8130/2 (does not include micropapillary subtype)
        ruleStepToTest = "M7";
        ruleCountToTest = 5;
        i1.setPrimarySite("C670");
        i1.setHistologyIcdO3("8120");
        i1.setBehaviorIcdO3("2");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("2");
        i2.setPrimarySite("C671");
        i2.setHistologyIcdO3("8120");
        i2.setBehaviorIcdO3("2");
        i2.setLaterality("2");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("noninvasive in situ /2 urothelial carcinoma (flat tumor) 8120/2"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setHistologyIcdO3("8130");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("noninvasive in situ /2 urothelial carcinoma (flat tumor) 8120/2"));
        Assert.assertEquals(ruleStepToTest, output.getStep());

        //Rule M8 Abstract multiple primariesii when the patient has micropapillary urothelial carcinoma 8131/3 of the bladder AND a
        //urothelial carcinoma 8120/3 (including papillary 8130/3) of the bladder.
        ruleStepToTest = "M8";
        ruleCountToTest = 6;
        i1.setPrimarySite("C670");
        i1.setHistologyIcdO3("8131");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("2");
        i2.setPrimarySite("C671");
        i2.setHistologyIcdO3("8120");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("2");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Micropapillary urothelial carcinoma"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i2.setHistologyIcdO3("8130");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Micropapillary urothelial carcinoma"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i2.setHistologyIcdO3("8082");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Micropapillary urothelial carcinoma"));
        Assert.assertEquals(ruleStepToTest, output.getStep());

        //Rule M9 Abstract a single primary when the patient has multiple invasive urothelial cell carcinomas in the bladder. All tumors are either:
        //-Multiple occurrences of urothelial or urothelial subtypes (with exception of micropapillary) OR
        //-Multiple occurrences of micropapillary
        ruleStepToTest = "M9";
        ruleCountToTest = 7;
        i1.setPrimarySite("C670");
        i1.setHistologyIcdO3("8020");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("2");
        i2.setPrimarySite("C671");
        i2.setHistologyIcdO3("8082");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("2");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("invasive urothelial cell carcinomas"));
        Assert.assertEquals(ruleStepToTest, output.getStep());

        // Rule M10 Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than three years after the original diagnosis or last recurrence.
        ruleStepToTest = "M10";
        ruleCountToTest = 8;
        i1.setPrimarySite("C679");
        i1.setHistologyIcdO3("8610");
        i1.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2014");
        i2.setPrimarySite("C679");
        i2.setHistologyIcdO3("8611");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("clinically disease-free for greater than three years"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setDateOfDiagnosisYear("2016");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        //This rule does not apply when both/all tumors are urothelial carcinoma of the bladder.
        i1.setPrimarySite("C679");
        i1.setHistologyIcdO3("8130");
        i1.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2021");
        i2.setPrimarySite("C679");
        i2.setHistologyIcdO3("8130");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2016");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        //don't skip if it is not bladder
        i1.setPrimarySite("C659");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        // Rule M11 Abstract a single primary when there are rothelial carcinomas in multiple urinary organs.
        //"8120", "8031", "8082", "8130", "8131", "8020", "8122"
        ruleStepToTest = "M11";
        ruleCountToTest = 9;
        i1.setPrimarySite("C659");
        i1.setHistologyIcdO3("8031");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("01");
        i2.setPrimarySite("C670");
        i2.setHistologyIcdO3("8031");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("2");
        i2.setDateOfDiagnosisYear("2016");
        i2.setDateOfDiagnosisMonth("02");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Urothelial carcinomas in multiple urinary organs"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setHistologyIcdO3("8035");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M12 Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3 of Table 2 in the Equivalent Terms and Definitions. Timing is irrelevant.
        ruleStepToTest = "M12";
        ruleCountToTest = 10;
        i1.setPrimarySite("C670");
        i1.setHistologyIcdO3("8144");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2016");
        i2.setPrimarySite("C670");
        i2.setHistologyIcdO3("8480");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("2");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("tumors that are two or more different subtypes/variants in Column 3, Table 2"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setHistologyIcdO3("8144");
        i2.setHistologyIcdO3("8010");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M13 Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 2 in the Equivalent Terms and Definitions. Timing is irrelevant.
        ruleStepToTest = "M13";
        ruleCountToTest = 11;
        i1.setPrimarySite("C670");
        i1.setHistologyIcdO3("8070");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2016");
        i2.setPrimarySite("C670");
        i2.setHistologyIcdO3("8310");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("2");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("tumors that are on different rows in Table 2"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setHistologyIcdO3("8140");
        i2.setHistologyIcdO3("8070");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("tumors that are on different rows in Table 2"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setHistologyIcdO3("8131");
        i2.setHistologyIcdO3("8130");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setHistologyIcdO3("9500");
        i2.setHistologyIcdO3("9600");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // M14- Tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx) and/or third (Cx?x) character are multiple primaries.
        ruleStepToTest = "M14";
        ruleCountToTest = 12;
        i1.setPrimarySite("C670");
        i1.setHistologyIcdO3("8051");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2016");
        i2.setPrimarySite("C669");
        i2.setHistologyIcdO3("8070");
        i2.setBehaviorIcdO3("2");
        i2.setLaterality("2");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Tumors in sites with ICD-O-3 topography codes"));
        Assert.assertEquals(ruleStepToTest, output.getStep());

        // Rule M15 Abstract a single primary when synchronous, separate/non-contiguous tumors are on the same row in Table 2 in the Equivalent Terms and Definitions.
        ruleStepToTest = "M15";
        ruleCountToTest = 13;
        i1.setPrimarySite("C670");
        i1.setHistologyIcdO3("8070");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("2");
        i2.setPrimarySite("C670");
        i2.setHistologyIcdO3("8051");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("2");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("tumors that are on the same row in Table 2"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setHistologyIcdO3("8144");
        i1.setBehaviorIcdO3("2");
        i2.setHistologyIcdO3("8140");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("tumors that are on the same row in Table 2"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setHistologyIcdO3("8070");
        i2.setHistologyIcdO3("8010");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setHistologyIcdO3("8380");
        i2.setHistologyIcdO3("8051");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M16 Abstract a single primary (the invasive) when an in situ tumor is diagnosed after an invasive tumor AND tumors:
        // - Occur in the same urinary site OR
        // - The original tumors are multifocal/multicentric and occur in multiple urinary sites; subsequent tumor(s) are in at least one of the previously involved urinary sites
        ruleStepToTest = "M16";
        ruleCountToTest = 14;
        i1.setPrimarySite("C670");
        i1.setHistologyIcdO3("8070");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2016");
        i1.setDateOfDiagnosisMonth("2");
        i2.setPrimarySite("C670");
        i2.setHistologyIcdO3("8051");
        i2.setBehaviorIcdO3("2");
        i2.setLaterality("2");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("in situ tumor following an invasive tumor"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setHistologyIcdO3("8031");
        i2.setHistologyIcdO3("8120");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("in situ tumor following an invasive tumor"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setBehaviorIcdO3("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M17 Abstract a single primary (the invasive) when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor AND tumors:
        // - Occur in the same urinary site OR
        // - Original tumor is multifocal/multicentric and involves multiple urinary sites; the subsequent invasive tumor(s) occur in at least one of the previously involved urinary sites
        //This rule will never hit

        // Rule M18 Abstract a single primary when tumors do not meet any of the above criteria.
        ruleStepToTest = "M18";
        ruleCountToTest = 16;
        i1.setPrimarySite("C670");
        i1.setHistologyIcdO3("8070");
        i1.setBehaviorIcdO3("2");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("1");
        i2.setPrimarySite("C670");
        i2.setHistologyIcdO3("8051");
        i2.setBehaviorIcdO3("2");
        i2.setLaterality("2");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("7");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("criteria"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
    }
}
