/*
 * Copyright (C) 2018 Information Management Services, Inc.
 */
package com.imsweb.mph;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class Mph2018RuleTests {

    private MphUtils _utils = MphUtils.getInstance();

    @BeforeClass
    public static void setUp() {
        MphUtils.initialize(new DefaultHematoDbUtilsProvider());
    }


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
        String ruleStepToTest = "";
        int ruleCountToTest = 0;

        // Rule M4	Abstract a single primary when there is inflammatory carcinoma in:
        // • Multiple quadrants of same breast OR
        // • Bilateral breasts
        ruleStepToTest = "M4";
        ruleCountToTest = 1;
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
        i1.setLaterality("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        // Does not apply
        i1.setLaterality("1");
        i2.setBehaviorIcdO3("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M5	Abstract multiple primaries when there are separate, non-contiguous tumors in sites with ICD-O site codes that differ at the
        // second (CXxx) and/or third characters (CxXx).
        // This cannot be tested. The Breast rule is only run on sites with C50_.
        /*
        ruleStepToTest = "M5";
        ruleCountToTest = 4;
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

        // Rule M6	Abstract multiple primaries when there is bilateral breast cancer (both right and left breast).
        ruleStepToTest = "M6";
        ruleCountToTest = 3;
        i1.setPrimarySite("C500");
        i1.setHistologyIcdO3("8730");
        i1.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2018");
        i1.setLaterality("1");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8730");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2018");
        i2.setLaterality("9");
        // Bad lateralities
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Multiple at M6
        i2.setLaterality("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertTrue(output.getReason().contains("Tumors on both sides"));

        // Rule M7	Abstract a single primary when the diagnosis is Paget disease with underlying in situ or invasive carcinoma NST (duct/ductal).
        // SKIP THIS RULE
        /*
        ruleStepToTest = "M7";
        ruleCountToTest = 6;
        i1.setPrimarySite("C500");
        i1.setHistologyIcdO3("8543");
        i1.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2018");
        i1.setLaterality("1");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8541");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2017");
        i2.setLaterality("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Tumors on both sides"));
        i1.setPrimarySite("C500");
        i1.setHistologyIcdO3("8543");
        i1.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2018");
        i1.setLaterality("1");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8541");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2017");
        i2.setLaterality("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Paget"));
        */

        // Rule M8	Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than five years after the
        // original diagnosis or last recurrence.
        ruleStepToTest = "M8";
        ruleCountToTest = 4;
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

        // Rule M9	 Abstract a single primary when simultaneous multiple tumors are carcinoma NST/duct and lobular:
        // • Both/all tumors may be a mixture of carcinoma NST/duct and lobular OR
        // • One tumor may be duct and another tumor lobular
        // -One tumor = 8500/2 OR 8500/3 OR 8035/3; other tumor = 8520/2 OR 8519/2 OR 8520/3
        // -One tumor= 8500/2 OR 8500/3 OR 8035/3 OR 8520/2 OR 8519/2 OR 8520/3; other tumor = 8522/3 OR 8522/2
        // Behaviors must match.
        ruleStepToTest = "M9";
        ruleCountToTest = 5;
        i1.setPrimarySite("C500");
        i1.setHistologyIcdO3("8500");
        i1.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("1");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8520");
        i2.setBehaviorIcdO3("2");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("");
        // Questionable at M9 with potential unknown specific date.
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        Assert.assertTrue(output.getReason().contains("diagnosis date"));
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Within 60 days.
        i2.setDateOfDiagnosisMonth("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // More than 60 days apart. Does not apply.
        i2.setDateOfDiagnosisMonth("5");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M10	Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3 of Table 3 in the Equivalent Terms and Definitions. Timing is irrelevant.
        ruleStepToTest = "M10";
        ruleCountToTest = 6;
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

        // Rule M11	Abstract a single primary when separate/non-contiguous tumors are on the same row in Table 3 in the Equivalent Terms and Definitions. Timing is irrelevant.
        ruleStepToTest = "M11";
        ruleCountToTest = 7;
        i1.setPrimarySite("C500");
        i1.setHistologyIcdO3("8200");
        i1.setBehaviorIcdO3("2");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2016");
        i1.setDateOfDiagnosisMonth("");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8200");
        i2.setBehaviorIcdO3("2");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertTrue(output.getReason().contains("same row in Table 3"));
        i1.setPrimarySite("C501");
        i1.setHistologyIcdO3("8503");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2016");
        i1.setDateOfDiagnosisMonth("");
        i2.setPrimarySite("C502");
        i2.setHistologyIcdO3("8509");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertTrue(output.getReason().contains("same row in Table 3"));
        i1.setPrimarySite("C503");
        i1.setHistologyIcdO3("8800");
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

        // Rule M12	Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 3 in the Equivalent Terms and Definitions. Timing is irrelevant.
        ruleStepToTest = "M12";
        ruleCountToTest = 8;
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
        i1.setPrimarySite("C500");
        i1.setHistologyIcdO3("8550");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2016");
        i1.setDateOfDiagnosisMonth("");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8212");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertTrue(output.getReason().contains("different rows in Table 3"));
        // Does not apply
        i1.setPrimarySite("C501");
        i1.setHistologyIcdO3("8314");
        i1.setBehaviorIcdO3("2");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2016");
        i1.setDateOfDiagnosisMonth("");
        i2.setPrimarySite("C506");
        i2.setHistologyIcdO3("8314");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        // Does not apply
        i1.setPrimarySite("C502");
        i1.setHistologyIcdO3("8503");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2016");
        i1.setDateOfDiagnosisMonth("");
        i2.setPrimarySite("C505");
        i2.setHistologyIcdO3("8509");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M13	Abstract a single primary when any of the following conditions are met in the same breast:
        // • DCIS subsequent to a diagnosis of:
        //    DCIS and lobular carcinoma in situ 8522/2 OR
        //    DCIS and in situ Paget 8543/2 OR
        //    DCIS and invasive Paget 8543/3
        //    DCIS and other in situ 8523/2 (prior to 2018, DCIS and other in situ was coded 8523/2)
        // • Invasive carcinoma NST/duct subsequent to a diagnosis of:
        //    Invasive carcinoma NST/duct and invasive lobular 8522/3 OR
        //    Invasive carcinoma NST/duct and invasive Paget 8541/3
        //    Invasive carcinoma NST/duct and other invasive 8523/3
        // This will always be captured by M12, because 8500 is in a row, and the others are not.
        /*
        ruleStepToTest = "M13";
        ruleCountToTest = 9;
        i1.setPrimarySite("C504");
        i1.setHistologyIcdO3("8522");
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
        Assert.assertTrue(output.getReason().contains("conditions are single primary"));
        i1.setPrimarySite("C500");
        i1.setHistologyIcdO3("8522");
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
        Assert.assertTrue(output.getReason().contains("conditions are single primary"));
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
        */

        // Rule M14	Abstract a single primary (the invasive) when an in situ tumor is diagnosed after an invasive tumor in the same breast.
        ruleStepToTest = "M14";
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

        // Rule M15	Abstract a single primary (the invasive) when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor in the same breast.
        ruleStepToTest = "M15";
        ruleCountToTest = 11;
        i1.setPrimarySite("C500");
        i1.setHistologyIcdO3("8401");
        i1.setBehaviorIcdO3("2");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("2");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8401");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertTrue(output.getReason().contains("invasive tumor following an in situ tumor less than or equal to 60 days"));
        // Does not apply
        i1.setPrimarySite("C500");
        i1.setHistologyIcdO3("8529");
        i1.setBehaviorIcdO3("6");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2016");
        i1.setDateOfDiagnosisMonth("");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8533");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M16	Abstract multiple primaries when an invasive tumor occurs more than 60 days after an in situ tumor in the same breast.
        ruleStepToTest = "M16";
        ruleCountToTest = 12;
        i1.setPrimarySite("C500");
        i1.setHistologyIcdO3("8529");
        i1.setBehaviorIcdO3("2");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2016");
        i1.setDateOfDiagnosisMonth("");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8533");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertTrue(output.getReason().contains("60 days"));
        i1.setLaterality("1");
        i2.setLaterality("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M17	Abstract a single primary when none of the previous rules apply.
        // Unable to trigger this rule.
        ruleStepToTest = "M17";
        ruleCountToTest = 13;
        i1.setPrimarySite("C504");
        i1.setHistologyIcdO3("8500");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("1");
        i2.setPrimarySite("C506");
        i2.setHistologyIcdO3("8500");
        i2.setBehaviorIcdO3("2");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertTrue(output.getReason().contains("criteria"));
    }

    @Test
    public void test2018Colon() {
        // Colon, Rectosigmoid, and Rectum Multiple Primary Rules
        // C180-C189, C199, C209
        // (Excludes lymphoma and leukemia M9590-M9992 and Kaposi sarcoma M9140)

        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;

        // Rule M3	Abstract a single primary when
        // •	The diagnosis is adenomatous polyposis coli (familial polyposis/FAP) OR
        // •	There is no diagnosis of FAP BUT
        // 	 Greater than 100 polyps are documented AND
        // 	 Adenocarcinoma in situ /2 or invasive /3 is present in at least one polyp
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8220");
        i1.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2016");
        i2.setPrimarySite("C180");
        i2.setHistologyIcdO3("8262");
        i2.setBehaviorIcdO3("2");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(1, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("FAP (8220)"));
        Assert.assertEquals("M3", output.getStep());
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8650");
        i1.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2016");
        i2.setPrimarySite("C180");
        i2.setHistologyIcdO3("8220");
        i2.setBehaviorIcdO3("2");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(1, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("FAP (8220)"));
        Assert.assertEquals("M3", output.getStep());
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
        Assert.assertNotEquals("M3", output.getStep());

        // Rule M4	Abstract multiple primaries when there are separate, non-contiguous tumors in sites with ICD-O site codes that differ at the second CXxx and/or third CxXx character.
        // C180-C189, C199, C209
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
        Assert.assertEquals(2, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("second (C?xx) and/or third (Cx?x) character"));
        Assert.assertEquals("M4", output.getStep());
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
        Assert.assertEquals("M4", output.getStep());
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
        Assert.assertNotEquals("M4", output.getStep());

        // Rule M5	Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 1 in the Equivalent Terms and Definitions. Timing is irrelevant.
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
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("two or more different subtypes/variants in Column 3, Table 1"));
        Assert.assertEquals("M5", output.getStep());
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
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("two or more different subtypes/variants in Column 3, Table 1"));
        Assert.assertEquals("M5", output.getStep());
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
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("two or more different subtypes/variants in Column 3, Table 1"));
        Assert.assertEquals("M5", output.getStep());
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
        Assert.assertNotEquals("M5", output.getStep());
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
        Assert.assertNotEquals("M5", output.getStep());

        // Rule M6	Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 1 in the Equivalent Terms and Definitions. Timing is irrelevant.
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
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertEquals("M6", output.getStep());
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8899");
        i1.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2016");
        i2.setPrimarySite("C180");
        i2.setHistologyIcdO3("8246");
        i2.setBehaviorIcdO3("2");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertTrue(output.getReason().contains("are on different rows in Table 1"));
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertEquals("M6", output.getStep());
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
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertEquals("M6", output.getStep());
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
        Assert.assertNotEquals("M6", output.getStep());
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
        Assert.assertNotEquals("M6", output.getStep());
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
        Assert.assertNotEquals("M6", output.getStep());

        // Rule M7	Abstract multiple primaries when a subsequent tumor arises at the anastomotic site AND:
        // •	 One tumor is a NOS and the other is a subtype/variant of that NOS OR
        // •	 The subsequent tumor occurs greater than 24 months after original tumor resection OR
        // • The subsequent tumor arises in the mucosa
        // ABH 7/19/18 - Revised per https://www.squishlist.com/ims/seerdms_dev/81114/
        // Incoming record is a tumor in a segment of colon/rectal/rectosigmoid.
        // There is a previous diagnosis of a tumor in a different segment of colon/rectum/rectosigmoid,
        // AND there was surgery done (surgery codes 30, 32, 40, 31),
        // ABH 9/14/18 - Disabled now per https://www.squishlist.com/ims/seerdms_dev/81114/
        /*
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
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertEquals("M7", output.getStep());
        */

        // Rule M8	Abstract a single primary when a subsequent tumor arises at the anastomotic site AND:
        // •	 The subsequent tumor occurs less than or equal to 24 months after original tumor resection OR
        // •	 The tumor arises in colon/rectal wall and/or surrounding tissue; there is no involvement of the mucosa OR
        // •	 The pathologist or clinician documents an anastomotic recurrence
        // ABH 9/14/18 - Disabled now per https://www.squishlist.com/ims/seerdms_dev/81114/
        /*
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
        Assert.assertEquals(7, output.getAppliedRules().size());
        Assert.assertEquals("M9", output.getStep());
        */

        // Rule M9	Abstract multiple primaries when there are separate, non-contiguous tumors in sites with ICD-O site codes that differ at the fourth characters C18X.
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
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("differ at the fourth character (C18X)"));
        Assert.assertEquals("M9", output.getStep());
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
        Assert.assertNotEquals("M9", output.getStep());

        // Rule M10	Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than one year after the original diagnosis or last recurrence.
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8221");
        i1.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2016");
        i2.setPrimarySite("C180");
        i2.setHistologyIcdO3("8266");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("more than one (1) year apart"));
        Assert.assertEquals("M10", output.getStep());
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8221");
        i1.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2016");
        i2.setPrimarySite("C180");
        i2.setHistologyIcdO3("8266");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("more than one (1) year apart"));
        Assert.assertEquals("M10", output.getStep());
        // Does not apply
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8221");
        i1.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2018");
        i2.setPrimarySite("C180");
        i2.setHistologyIcdO3("8266");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M10", output.getStep());

        // Rule M11	Abstract a single primary when separate/non-contiguous tumors are on the same row in Table 1 in the Equivalent Terms and Definitions. Timing is irrelevant.
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8140");
        i1.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("2");
        i2.setPrimarySite("C180");
        i2.setHistologyIcdO3("8200");
        i2.setBehaviorIcdO3("2");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("8");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertTrue(output.getReason().contains("are on the same row in Table 1"));
        Assert.assertEquals(7, output.getAppliedRules().size());
        Assert.assertEquals("M11", output.getStep());
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8244");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C180");
        i2.setHistologyIcdO3("8243");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertTrue(output.getReason().contains("are on the same row in Table 1"));
        Assert.assertEquals(7, output.getAppliedRules().size());
        Assert.assertEquals("M11", output.getStep());
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
        Assert.assertNotEquals("M11", output.getStep());
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
        Assert.assertNotEquals("M11", output.getStep());
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8145");
        i1.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2016");
        i2.setPrimarySite("C180");
        i2.setHistologyIcdO3("8013");
        i2.setBehaviorIcdO3("2");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M11", output.getStep());

        // Rule M12	Abstract a single primary (the invasive) when an in situ tumor is diagnosed after an invasive tumor.
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
        Assert.assertEquals(8, output.getAppliedRules().size());
        Assert.assertEquals("M12", output.getStep());
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
        Assert.assertNotEquals("M12", output.getStep());
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
        Assert.assertNotEquals("M12", output.getStep());

        // Rule M13	Abstract a single primary (the invasive) when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor.
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8244");
        i1.setBehaviorIcdO3("2");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("2");
        i2.setPrimarySite("C180");
        i2.setHistologyIcdO3("8243");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertTrue(output.getReason().contains("invasive tumor following an in situ tumor less than or equal to 60 days"));
        Assert.assertEquals(9, output.getAppliedRules().size());
        Assert.assertEquals("M13", output.getStep());
        // Does not apply
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8221");
        i1.setBehaviorIcdO3("6");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("1");
        i2.setPrimarySite("C180");
        i2.setHistologyIcdO3("8266");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("5");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M13", output.getStep());

        // Rule M14	Abstract multiple primaries when an invasive tumor occurs more than 60 days after an in situ tumor.
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
        Assert.assertEquals(10, output.getAppliedRules().size());
        Assert.assertEquals("M14", output.getStep());
        // Does not apply
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("2");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M14", output.getStep());
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
        Assert.assertNotEquals("M14", output.getStep());

        // Rule M15	Abstract a single primary when tumors do not meet any of the above criteria.
        // Could Not Reach.
        /*
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8244");
        i1.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("2");
        i2.setPrimarySite("C180");
        i2.setHistologyIcdO3("8243");
        i2.setBehaviorIcdO3("2");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(11, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("criteria"));
        Assert.assertEquals("M15", output.getStep());
        */
    }

    @Test
    public void test2018CutaneousMelanoma() {
        // Cutaneous Melanoma Multiple Primary Rules – Text
        // C440-C449 with Histology 8720-8780 (Excludes melanoma of any other site)
        // Rules Apply to Cases Diagnosed 1/1/2007 to 12/31/2018

        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;

        // Rule M3	Melanomas in sites with ICD-O-3 topography codes that are different at the second (Cxxx), third (Cxxx) or fourth (C44x) character are multiple primaries. **
        i1.setPrimarySite("C442");
        i1.setHistologyIcdO3("8720");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C447");
        i2.setHistologyIcdO3("8780");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(1, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("topography"));

        // Rule M4	Melanomas with different laterality are multiple primaries. **
        i1.setPrimarySite("C442");
        i1.setHistologyIcdO3("8720");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C442");
        i2.setHistologyIcdO3("8780");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisYear("2018");
        i1.setLaterality("1");
        i2.setLaterality("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(2, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("laterality"));
        //melanoma mid-line laterality is considered as different laterality of right or left
        i2.setLaterality("5");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(2, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("laterality"));
        i2.setLaterality("4");
        //Questionable at M4 with potential Multiple and ended up as multiple at M5 -- MULTIPLE
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertEquals("M5", output.getStep());

        // Rule M5	Melanomas with ICD-O-3 histology codes that are different at the first (Xxxx), second (xXxx) or third number (xxXx) are multiple primaries. **
        i1.setPrimarySite("C442");
        i1.setHistologyIcdO3("8720");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C442");
        i2.setHistologyIcdO3("8780");
        i2.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i2.setLaterality("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("histology"));

        // Rule M6	An invasive melanoma that occurs more than 60 days after an in situ melanoma is a multiple primary. **
        i1.setPrimarySite("C442");
        i2.setPrimarySite("C442");
        i1.setHistologyIcdO3("8725");
        i2.setHistologyIcdO3("8720");
        i1.setBehaviorIcdO3("2");
        i2.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i2.setLaterality("1");
        i1.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisYear("2018"); // same year no month information
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("M6"));
        i1.setDateOfDiagnosisMonth("1"); // invasive on 1/2018, insitu on 5/2018
        i2.setDateOfDiagnosisMonth("5");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(4, output.getAppliedRules().size());
        //Assert.assertTrue(output.getReason().contains("invasive"));
        Assert.assertTrue(output.getReason().contains("60 days after an in situ melanoma"));
        i1.setDateOfDiagnosisMonth("1"); // invasive on 1/2018, insitu on 5/2018
        i2.setDateOfDiagnosisMonth("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("criteria"));

        // Rule M7	Melanomas diagnosed more than 60 days apart are multiple primaries. **
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisMonth("1");
        i2.setDateOfDiagnosisMonth("5");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Melanomas diagnosed more than 60 days apart are multiple primaries."));

        // Rule M8	Melanomas that do not meet any of the above criteria are abstracted as a single primary. *
        i1.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("01");
        i2.setDateOfDiagnosisMonth("01");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("criteria"));
    }

    @Test
    public void test2018HeadAndNeck() {
        // Head and Neck Multiple Primary Rules
        // C000-C148, C300-C339, C410, C411, C442, C479
        // (Excludes lymphoma and leukemia M9590 – M9992 and Kaposi sarcoma M9140)

        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;

        // Rule M3	Abstract multiple primaries when there are separate, non-contiguous tumors on both the:
        // •	Upper lip C000 or C003 AND lower lip C001 or C004 OR
        // •	Upper gum C030 AND lower gum C031 OR
        // •	Nasal cavity C300 AND middle ear C301

        // upper lip (C000 or C003) and the lower lip (C001 or C004).
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
        Assert.assertEquals(1, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("lip"));
        Assert.assertEquals("M3", output.getStep());
        // upper gum (C030) and the lower gum (C031).
        i1.setPrimarySite("C030");
        i2.setPrimarySite("C031");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(1, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("gum"));
        Assert.assertEquals("M3", output.getStep());
        // nasal cavity (C300) and the middle ear (C301).
        i1.setPrimarySite("C300");
        i2.setPrimarySite("C301");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(1, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("nasal"));
        Assert.assertEquals("M3", output.getStep());

        // Rule M4	Abstract multiple primaries when separate, non-contiguous tumors are present in sites with ICD-O site codes that differ at the second CXxx, and/or third characters CxXx.
        i1.setPrimarySite("C000");
        i2.setPrimarySite("C148");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(2, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("topography"));
        Assert.assertEquals("M4", output.getStep());
        i1.setPrimarySite("C138");
        i2.setPrimarySite("C148");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(2, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("topography"));
        Assert.assertEquals("M4", output.getStep());

        // Rule M5	Abstract multiple primaries when there are separate, non-contiguous tumors on both the right side and the left side of a paired site.
        i1.setPrimarySite("C312");
        i1.setHistologyIcdO3("8000");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2015");
        i2.setPrimarySite("C312");
        i2.setHistologyIcdO3("8100");
        i2.setBehaviorIcdO3("2");
        i2.setLaterality("9");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        // Questionable.
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertEquals("M5", output.getStep());
        i2.setLaterality("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("paired"));
        Assert.assertEquals("M5", output.getStep());
        // Does not apply.
        i1.setPrimarySite("C400");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M5", output.getStep());

        // Rule M6	Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than five years after the original diagnosis or last recurrence.
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
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("five"));
        Assert.assertEquals("M6", output.getStep());
        // Does not apply
        i2.setDateOfDiagnosisYear("2015");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M6", output.getStep());

        // Rule M7	Abstract multiple primaries when separate, non-contiguous tumors are two or more different subtypes/variants in Column 3 of the appropriate site table (Tables 2-10) in the Equivalent Terms and Definitions. Timing is irrelevant.
        i1.setPrimarySite("C130");
        i1.setHistologyIcdO3("8052");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C131");
        i2.setHistologyIcdO3("8013");
        i2.setBehaviorIcdO3("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertEquals("M7", output.getStep());
        i1.setPrimarySite("C112");
        i1.setHistologyIcdO3("8071");
        i2.setPrimarySite("C118");
        i2.setHistologyIcdO3("8083");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertEquals("M7", output.getStep());
        // Does not apply.
        i1.setPrimarySite("C131");
        i1.setHistologyIcdO3("8052");
        i2.setPrimarySite("C134");
        i2.setHistologyIcdO3("8100");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M7", output.getStep());

        // Rule M8	Abstract multiple primaries when separate, non-contiguous tumors are on different rows in the appropriate site table (Tables 2-10) in the Equivalent Terms and Definitions. Timing is irrelevant.
        // No Table 1 or 2
        i1.setPrimarySite("C001");
        i1.setHistologyIcdO3("8900");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C002");
        i2.setHistologyIcdO3("8052");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertEquals("M8", output.getStep());
        // No Table 1
        i1.setPrimarySite("C134");
        i1.setHistologyIcdO3("8900");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C132");
        i2.setHistologyIcdO3("8070");
        i2.setBehaviorIcdO3("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertEquals("M8", output.getStep());
        // No Table 2
        i1.setPrimarySite("C130");
        i1.setHistologyIcdO3("8850");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C135");
        i2.setHistologyIcdO3("8052");
        i2.setBehaviorIcdO3("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertEquals("M8", output.getStep());
        // No Entry 1
        i1.setPrimarySite("C130");
        i1.setHistologyIcdO3("9000");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C132");
        i2.setHistologyIcdO3("8070");
        i2.setBehaviorIcdO3("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertEquals("M8", output.getStep());
        // No Entry 2
        i1.setPrimarySite("C130");
        i1.setHistologyIcdO3("8240");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C132");
        i2.setHistologyIcdO3("8500");
        i2.setBehaviorIcdO3("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertEquals("M8", output.getStep());
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
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertEquals("M7", output.getStep());
        */
        // Same Histology - Same Table
        i1.setPrimarySite("C100");
        i1.setHistologyIcdO3("8071");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C100");
        i2.setHistologyIcdO3("8071");
        i2.setBehaviorIcdO3("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M8", output.getStep());
        // Same Row - 1
        i1.setPrimarySite("C100");
        i1.setHistologyIcdO3("8070");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C100");
        i2.setHistologyIcdO3("8071");
        i2.setBehaviorIcdO3("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M8", output.getStep());
        // Same Row - 2
        i1.setPrimarySite("C100");
        i1.setHistologyIcdO3("8071");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C100");
        i2.setHistologyIcdO3("8072");
        i2.setBehaviorIcdO3("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M8", output.getStep());

        // Rule M9	Abstract a single primary (the invasive)when an in situ tumor is diagnosed after an invasive tumor.
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
        Assert.assertEquals(7, output.getAppliedRules().size());
        Assert.assertEquals("M9", output.getStep());
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
        Assert.assertNotEquals("M9", output.getStep());
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
        Assert.assertNotEquals("M9", output.getStep());

        // Rule M10	Abstract a single primary (the invasive) when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor.
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
        Assert.assertEquals(8, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("invasive tumor following an in situ tumor less than or equal to 60"));
        Assert.assertEquals("M10", output.getStep());
        // Does not apply.
        i2.setDateOfDiagnosisMonth("6");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M10", output.getStep());

        // Rule M11	Abstract multiple primaries when an invasive tumor occurs more than 60 days after an in situ tumor.
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
        Assert.assertEquals(9, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("invasive"));
        Assert.assertEquals("M11", output.getStep());
        // Does not apply.
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M11", output.getStep());
        // Questionable
        i2.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisMonth(null);
        i2.setDateOfDiagnosisMonth(null);
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(8, output.getAppliedRules().size());
        Assert.assertEquals("M10", output.getStep());

        // Rule M12	Abstract a single primary when separate, non-contiguous tumors are on the same row in the appropriate site table (Tables 2-10) in the Equivalent Terms and Definitions. Timing is irrelevant.
        // No Table 1 or 2
        i1.setPrimarySite("C134");
        i1.setHistologyIcdO3("8900");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C135");
        i2.setHistologyIcdO3("8052");
        i2.setBehaviorIcdO3("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M12", output.getStep());
        // No Table 1
        i1.setPrimarySite("C134");
        i1.setHistologyIcdO3("8900");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C132");
        i2.setHistologyIcdO3("8070");
        i2.setBehaviorIcdO3("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M12", output.getStep());
        // No Table 2
        i1.setPrimarySite("C130");
        i1.setHistologyIcdO3("8850");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C135");
        i2.setHistologyIcdO3("8052");
        i2.setBehaviorIcdO3("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M12", output.getStep());
        // No Entry 1
        i1.setPrimarySite("C130");
        i1.setHistologyIcdO3("9000");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C132");
        i2.setHistologyIcdO3("8070");
        i2.setBehaviorIcdO3("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M12", output.getStep());
        // No Entry 2
        i1.setPrimarySite("C130");
        i1.setHistologyIcdO3("8240");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C132");
        i2.setHistologyIcdO3("8500");
        i2.setBehaviorIcdO3("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M12", output.getStep());
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
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertEquals("M12", output.getStep());
        */
        // Same Histology - Same Table
        i1.setPrimarySite("C100");
        i1.setHistologyIcdO3("8071");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C100");
        i2.setHistologyIcdO3("8071");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M12", output.getStep());
        // Same Row - 1
        i1.setPrimarySite("C100");
        i1.setHistologyIcdO3("8070");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C100");
        i2.setHistologyIcdO3("8071");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(10, output.getAppliedRules().size());
        Assert.assertEquals("M12", output.getStep());

        // Rule M13	Abstract a single primary  when none of the previous rules apply.
        i1.setPrimarySite("C100");
        i1.setHistologyIcdO3("8071");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C100");
        i2.setHistologyIcdO3("8071");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(11, output.getAppliedRules().size());
        Assert.assertEquals("M13", output.getStep());

    }

    @Test
    public void test2018Kidney() {
        // Kidney Multiple Primary Rules - Text
        // C649
        // (Excludes lymphoma and leukemia – M9590-M9992 and Kaposi sarcoma M9140)

        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;

        // Rule M3	Abstract multiple primaries when multiple tumors are present in sites with ICD-O site codes that differ at the second (CXxx), third (CxXx) and/or fourth characters (CxxX).
        // This will never happen since all kidney tumors are C649

        // Rule M4	Abstract a single primary when there are bilateral nephroblastomas (previously called Wilms tumors).
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
        Assert.assertEquals(2, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Bilateral nephroblastomas"));
        Assert.assertEquals("M4", output.getStep());
        // Does not apply.
        i2.setHistologyIcdO3("8961");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M4", output.getStep());

        // Rule M5	Abstract multiple primaries when there are tumors in both the right kidney and in the left kidney.
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
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("both the right kidney and in the left"));
        Assert.assertEquals("M5", output.getStep());
        // Does not apply.
        i2.setLaterality("9");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M10", output.getStep());
        // Questionable.
        i1.setHistologyIcdO3("8000");
        i2.setHistologyIcdO3("8001");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertEquals("M5", output.getStep());


        // Testing of NOS SubTypes for Rules M6 - M8.
        i1.setPrimarySite("C649");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2018");
        i2.setPrimarySite("C649");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");

        i1.setHistologyIcdO3("8800"); i2.setHistologyIcdO3("8900");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals("M7", output.getStep());

        i1.setHistologyIcdO3("8800"); i2.setHistologyIcdO3("8910");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals("M7", output.getStep());

        i1.setHistologyIcdO3("8800"); i2.setHistologyIcdO3("8890");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals("M7", output.getStep());

        i1.setHistologyIcdO3("8900"); i2.setHistologyIcdO3("8920");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals("M7", output.getStep());

        i1.setHistologyIcdO3("8900"); i2.setHistologyIcdO3("8890");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals("M6", output.getStep());

        i1.setHistologyIcdO3("8920"); i2.setHistologyIcdO3("8912");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals("M6", output.getStep());

        i1.setHistologyIcdO3("8920"); i2.setHistologyIcdO3("9180");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals("M6", output.getStep());

        i1.setHistologyIcdO3("9120"); i2.setHistologyIcdO3("9364");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals("M6", output.getStep());

        i1.setHistologyIcdO3("8312"); i2.setHistologyIcdO3("8800");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals("M8", output.getStep());

        i1.setHistologyIcdO3("8312"); i2.setHistologyIcdO3("8900");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals("M8", output.getStep());

        i1.setHistologyIcdO3("8312"); i2.setHistologyIcdO3("8920");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals("M8", output.getStep());

        i1.setHistologyIcdO3("8312"); i2.setHistologyIcdO3("9180");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals("M8", output.getStep());

        i1.setHistologyIcdO3("8316"); i2.setHistologyIcdO3("8800");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals("M8", output.getStep());

        i1.setHistologyIcdO3("8316"); i2.setHistologyIcdO3("8900");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals("M6", output.getStep());

        i1.setHistologyIcdO3("8316"); i2.setHistologyIcdO3("8920");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals("M6", output.getStep());

        i1.setHistologyIcdO3("8316"); i2.setHistologyIcdO3("9180");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals("M6", output.getStep());

        i1.setHistologyIcdO3("9250"); i2.setHistologyIcdO3("8800");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals("M8", output.getStep());

        i1.setHistologyIcdO3("9250"); i2.setHistologyIcdO3("8900");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals("M8", output.getStep());

        i1.setHistologyIcdO3("9250"); i2.setHistologyIcdO3("8920");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals("M8", output.getStep());

        i1.setHistologyIcdO3("9250"); i2.setHistologyIcdO3("9180");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals("M8", output.getStep());



        // Rule M6	Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 1 in the Equivalent Terms and Definitions. Tumors must be in same kidney and timing is irrelevant.
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
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("two or more different subtypes/variants in Column 3, Table 1"));
        Assert.assertEquals("M6", output.getStep());
        i1.setHistologyIcdO3("9120");
        i2.setHistologyIcdO3("8240");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("two or more different subtypes/variants in Column 3, Table 1"));
        Assert.assertEquals("M6", output.getStep());
        // Does not apply.
        i1.setHistologyIcdO3("8960");
        i2.setHistologyIcdO3("8290");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M6", output.getStep());
        // Does not apply.
        i1.setHistologyIcdO3("8316");
        i2.setHistologyIcdO3("9500");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M6", output.getStep());

        // Rule M7	Abstract a single primary when separate/non-contiguous tumors are on the same row in Table 1 in the Equivalent Terms and Definitions. Tumors must be in the same kidney and timing is irrelevant.
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
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("tumors that are on the same row in Table 1"));
        Assert.assertEquals("M7", output.getStep());
        i1.setHistologyIcdO3("8041");
        i2.setHistologyIcdO3("8240");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("tumors that are on the same row in Table 1"));
        Assert.assertEquals("M7", output.getStep());
        // Does not apply.
        i1.setHistologyIcdO3("8960");
        i2.setHistologyIcdO3("8042");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M7", output.getStep());
        // Does not apply.
        i1.setHistologyIcdO3("8316");
        i2.setHistologyIcdO3("8013");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M7", output.getStep());

        // Rule M8	Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 1 in the Equivalent Terms and Definitions. Tumors must be in the same kidney and timing is irrelevant.
        i1.setPrimarySite("C649");
        i1.setHistologyIcdO3("8960");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2018");
        i2.setPrimarySite("C649");
        i2.setHistologyIcdO3("8042");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("tumors that are on different rows in Table 1"));
        Assert.assertEquals("M8", output.getStep());
        i1.setHistologyIcdO3("8317");
        i2.setHistologyIcdO3("8042");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("tumors that are on different rows in Table 1"));
        Assert.assertEquals("M8", output.getStep());
        // Does not apply.
        i1.setHistologyIcdO3("8312");
        i2.setHistologyIcdO3("8480");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M8", output.getStep());
        // Does not apply.
        i1.setHistologyIcdO3("8800");
        i2.setHistologyIcdO3("8920");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M8", output.getStep());

        // Rule M9	Abstract a single primary when an in situ tumor is diagnosed after an invasive tumor AND tumors occur in the same kidney.
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
        Assert.assertEquals(7, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("in situ tumor diagnosed following an invasive tumor"));
        Assert.assertEquals("M9", output.getStep());
        // Does not apply.
        i1.setLaterality("1");
        i2.setLaterality("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M9", output.getStep());
        // Does not apply.
        i2.setLaterality("1");
        i1.setBehaviorIcdO3("2");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M9", output.getStep());

        // Rule M10	Abstract a single primary (recurrence) when tumors recur less than or equal to 3 years apart.
        i1.setHistologyIcdO3("8312");
        i1.setBehaviorIcdO3("2");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2015");
        i1.setDateOfDiagnosisMonth("");
        i2.setHistologyIcdO3("8317");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(8, output.getAppliedRules().size());
        Assert.assertEquals("M10", output.getStep());
        i1.setDateOfDiagnosisMonth("1");
        i2.setDateOfDiagnosisMonth("3");
        i1.setDateOfDiagnosisYear("2016");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(8, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("less than or equal to three (3) years apart"));
        Assert.assertEquals("M10", output.getStep());
        // Does not apply.
        i1.setDateOfDiagnosisYear("2014");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M10", output.getStep());

        // Rule M11	Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than three years after the original diagnosis or last recurrence.
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
        Assert.assertEquals(9, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("greater than three (3) years apart"));
        Assert.assertEquals("M11", output.getStep());
        // Does not apply.
        i1.setDateOfDiagnosisYear("2017");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M11", output.getStep());

        // Rule M12	Abstract a single primary when there are multiple tumors that do not meet any of the above criteria.
        // Impossible to hit this rule. M10 and M11 cover all possibilities.
    }

    @Test
    public void test2018Lung() {
        // Lung Multiple Primary Rules
        // C340-C343, C348, C349
        // (Excludes lymphoma and leukemia M9590–M9992 and Kaposi sarcoma M9140)

        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;

        // Rule M3	Abstract multiple primaries when there are separate, non-contiguous tumors in sites with ICD-O site codes (C34_) that differ at the second CXxx and/or third character CxXx.
        // This will never be true, lung group is C340-C349, 2nd and 3rd characters are always the same.

        // Rule M4	Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than three years after the original diagnosis or last recurrence.
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
        Assert.assertEquals(2, output.getAppliedRules().size());
        // Applies
        i2.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisMonth("08");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(2, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("three (3) years apart"));
        Assert.assertEquals("M4", output.getStep());
        // Does not apply.
        i2.setDateOfDiagnosisYear("2017");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M4", output.getStep());

        // Rule M5	Abstract multiple primaries when there is at least one tumor that is small cell carcinoma 8041 or any small cell subtypes/variants and another tumor that is non-small cell carcinoma 8046 or any non-small cell carcinoma subtypes/variants.
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
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("small cell carcinoma 8041"));
        Assert.assertEquals("M5", output.getStep());
        i1.setHistologyIcdO3("8041");
        i2.setHistologyIcdO3("8031");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("small cell carcinoma 8041"));
        Assert.assertEquals("M5", output.getStep());
        i1.setHistologyIcdO3("8046");
        i2.setHistologyIcdO3("8045");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("small cell carcinoma 8041"));
        Assert.assertEquals("M5", output.getStep());
        i1.setHistologyIcdO3("8240");
        i2.setHistologyIcdO3("8022");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("small cell carcinoma 8041"));
        Assert.assertEquals("M5", output.getStep());
        // Does not apply
        i1.setHistologyIcdO3("8041");
        i2.setHistologyIcdO3("8045");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M5", output.getStep());
        // Does not apply
        i1.setHistologyIcdO3("8046");
        i2.setHistologyIcdO3("8239");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M5", output.getStep());

        // Rule M6	Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 3 in the Equivalent Terms and Definitions.  Timing is irrelevant.
        i1.setHistologyIcdO3("8480");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("09");
        i2.setHistologyIcdO3("8072");
        i2.setDateOfDiagnosisYear("2016");
        i2.setDateOfDiagnosisMonth("08");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("two or more different subtypes/variants in Column 3, Table 3"));
        Assert.assertEquals("M6", output.getStep());
        i1.setHistologyIcdO3("8333");
        i2.setHistologyIcdO3("8083");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("two or more different subtypes/variants in Column 3, Table 3"));
        Assert.assertEquals("M6", output.getStep());
        // Does not apply.
        i1.setHistologyIcdO3("8041");
        i2.setHistologyIcdO3("9000");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M6", output.getStep());

        // Rule M7	Abstract a single primary when synchronous, separate/non-contiguous tumors in the same lung are on the same row in Table 3 in the Equivalent Terms and Definitions.
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
        i2.setDateOfDiagnosisYear("2016");
        i2.setDateOfDiagnosisMonth("08");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("same row in Table 3"));
        Assert.assertEquals("M7", output.getStep());
        i1.setHistologyIcdO3("8070");
        i2.setHistologyIcdO3("8071");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("same row in Table 3"));
        Assert.assertEquals("M7", output.getStep());
        // Does not apply.
        i1.setHistologyIcdO3("8430");
        i2.setHistologyIcdO3("8022");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M7", output.getStep());
        // Does not apply.
        i1.setHistologyIcdO3("8560");
        i2.setHistologyIcdO3("8083");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M7", output.getStep());

        // Rule M8	Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 3 in the Equivalent Terms and Definitions. Timing is irrelevant.
        i1.setHistologyIcdO3("8200");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("09");
        i2.setHistologyIcdO3("8430");
        i2.setDateOfDiagnosisYear("2016");
        i2.setDateOfDiagnosisMonth("08");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("tumors on different rows in Table 3"));
        Assert.assertEquals("M8", output.getStep());
        i1.setHistologyIcdO3("8560");
        i2.setHistologyIcdO3("8072");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("tumors on different rows in Table 3"));
        Assert.assertEquals("M8", output.getStep());
        // Does not apply.
        i1.setHistologyIcdO3("8140");
        i2.setHistologyIcdO3("8480");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M8", output.getStep());
        // Does not apply.
        i1.setHistologyIcdO3("8041");
        i2.setHistologyIcdO3("8240");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M8", output.getStep());

        // Rule M9	Abstract a single primary when there are simultaneous multiple tumors:
        // •	In both lungs OR
        // •	In the same lung OR
        // •	Single tumor in one lung; multiple tumors in contralateral lung
        i1.setPrimarySite("C342");
        i1.setHistologyIcdO3("8140");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("09");
        i2.setPrimarySite("C341");
        i2.setHistologyIcdO3("8253");
        i2.setBehaviorIcdO3("2");
        i2.setLaterality("2");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("08");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(7, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("tumors diagnosed less than or equal to 60 days"));
        Assert.assertEquals("M9", output.getStep());
        i1.setHistologyIcdO3("8140");
        i1.setBehaviorIcdO3("3");
        i2.setHistologyIcdO3("8253");
        i2.setBehaviorIcdO3("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(7, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("tumors diagnosed less than or equal to 60 days"));
        Assert.assertEquals("M9", output.getStep());
        i1.setHistologyIcdO3("8140");
        i1.setBehaviorIcdO3("3");
        i2.setHistologyIcdO3("8253");
        i2.setBehaviorIcdO3("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(7, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("tumors diagnosed less than or equal to 60 days"));
        Assert.assertEquals("M9", output.getStep());
        // Does not apply.
        i1.setDateOfDiagnosisMonth("09");
        i2.setDateOfDiagnosisMonth("01");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M9", output.getStep());

        // Rule M10	Abstract a single primary when an in situ tumor is diagnosed after an invasive tumor AND tumors occur in the same lung.
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
        Assert.assertEquals(8, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("in situ tumor diagnosed following an invasive tumor in the same lung"));
        Assert.assertEquals("M10", output.getStep());
        // Does not apply.
        i1.setLaterality("3");
        i2.setLaterality("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M10", output.getStep());
        // Does not apply.
        i1.setLaterality("2");
        i2.setLaterality("2");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M10", output.getStep());

        // Rule M11	Abstract multiple primaries when there is a single tumor in each lung (one tumor in the right lung and one tumor in the left lung).
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
        // Questionable.
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(9, output.getAppliedRules().size());
        Assert.assertEquals("M11", output.getStep());
        // Applies.
        i1.setLaterality("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(9, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("single tumor in each lung"));
        Assert.assertEquals("M11", output.getStep());
        // Does not apply.
        i1.setLaterality("2");
        i2.setLaterality("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M11", output.getStep());

        // Rule M12	Abstract a single primary (the invasive) when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor in the same lung.
        i1.setPrimarySite("C342");
        i1.setHistologyIcdO3("8253");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("2");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("07");
        i2.setPrimarySite("C349");
        i2.setHistologyIcdO3("8140");
        i2.setBehaviorIcdO3("2");
        i2.setLaterality("2");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("06");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(10, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("invasive tumor diagnosed less than or equal to 60 days after an in situ "));
        Assert.assertEquals("M12", output.getStep());
        // Does not apply.
        i2.setDateOfDiagnosisMonth("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M12", output.getStep());
        // Does not apply.
        i2.setDateOfDiagnosisMonth("7");
        i1.setLaterality("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M12", output.getStep());

        // Rule M13	Abstract multiple primaries when an invasive tumor occurs more than 60 days after an in situ tumor in the same lung.
        i1.setPrimarySite("C342");
        i1.setHistologyIcdO3("8253");
        i1.setBehaviorIcdO3("2");
        i1.setLaterality("2");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth(null);
        i2.setPrimarySite("C349");
        i2.setHistologyIcdO3("8140");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("11");
        output = _utils.computePrimaries(i1, i2);
        // Questionable.
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(7, output.getAppliedRules().size());
        Assert.assertEquals("M9", output.getStep());
        // Applies.
        i1.setDateOfDiagnosisMonth("7");
        i1.setLaterality("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(11, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("invasive tumor following an in situ tumor in the same lung more than 60 days"));
        Assert.assertEquals("M13", output.getStep());
        // Does not apply.
        i1.setDateOfDiagnosisMonth("10");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M13", output.getStep());

        // Rule M14	Abstract a single primary when none of the previous rules apply.
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
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(12, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("do not meet any of the criteria"));
        Assert.assertEquals("M14", output.getStep());
    }

    @Test
    public void test2018MalignantCNSAndPeripheralNerves() {
        // Malignant CNS and Peripheral Nerves Histology Rules
        // C470-C479, C700, C701, C709, C710-C719, C721-C725, C728, C729, C751-C753
        // (Excludes lymphoma and leukemia M9590-M9992 and Kaposi sarcoma M9140)

        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;
        String ruleStepToTest = "";
        int ruleCountToTest = 0;

        // Rule M5	Abstract multiple primaries when there are multiple CNS tumors, one of which is malignant /3 and the other is non-malignant /0 or /1.
        // •	Original non-malignant tumor followed by malignant tumor
        //     	Patient had a resection of the non-malignant tumor (not the same tumor) OR
        //     	It is unknown/not documented if the patient had a resection
        // •	Simultaneous non-malignant and malignant tumors
        //     	Abstract both the malignant and the non-malignant tumors

        // NOTE: This may not be testable. If one tumor is malignant, and the other is not, you get two different rule sets:
        // Mp2018NonMalignantCNSTumorsGroup and Mp2018MalignantCNSAndPeripheralNervesGroup. Two different rule sets are automatically
        // set to multiple primaries by MphUtuls.computePrimaries().
        /*
        ruleStepToTest = "M5";
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

        // Rule M6	Abstract multiple primaries when a patient has a glial or astrocytic tumor and is subsequently diagnosed with a glioblastoma multiforme 9440 (GBM).
        // GLIAL_TUMOR_2018 = GroupUtility.expandList("9385,9391,9392,9393,9396,9400,9401,9411,9424,9430,9440,9441,9442,9445,9450,9451");
        ruleStepToTest = "M6";
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
        // Does not apply.
        i2.setHistologyIcdO3("9443");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setDateOfDiagnosisYear("2018");
        i2.setHistologyIcdO3("9440");
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

        // Rule M7	Abstract a single primary when there are separate, non-contiguous tumors in the brain (multicentric/multifocal) with the same histology XXXX.  Tumors may be any of the following combinations:
        // • In the same lobe; for example, two tumors in right temporal lobe C712 (same site code)
        // • Different lateralities of the same lobe; for example, left and right frontal lobes C711 (same site code)
        // • In different lobes; for example, parietal lobe C713 and occipital lobe C714 (different site codes)
        ruleStepToTest = "M7";
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

        // Rule M8	Abstract multiple primaries when multiple tumors are present in any of the following sites or subsites:
        // • Any lobe of the brain C710-C719 AND any other part of CNS
        // • Cauda equina C721 AND any other part of CNS
        // • Cerebral meninges C700 AND spinal meninges C701
        // • Cerebral meninges C700 AND any other part of CNS
        // • Any one of the cranial nerves C722-C725 AND any other part of the CNS
        // • Any two or more of the cranial nerves
        //    C722 Olfactory, C723 Optic, C724 Acoustic, C725 Cranial nerves NOS
        // • Meninges of cranial or peripheral nerves C709 AND any other part of the CNS
        // • Spinal cord C720 AND any other part of CNS
        // • Spinal meninges C701 AND any other part of CNS
        ruleStepToTest = "M8";
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

        // Testing of NOS Subtypes for rules M9 - M11.
        i1.setPrimarySite("C728");
        i1.setHistologyIcdO3("9401");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C729");
        i2.setHistologyIcdO3("9396");
        i2.setBehaviorIcdO3("3");

        ruleStepToTest = "M9";
        i1.setHistologyIcdO3("9220"); i2.setHistologyIcdO3("8890");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        i1.setHistologyIcdO3("9220"); i2.setHistologyIcdO3("8896");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        i1.setHistologyIcdO3("9240"); i2.setHistologyIcdO3("8896");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        i1.setHistologyIcdO3("9220"); i2.setHistologyIcdO3("8891"); // ***
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        i1.setHistologyIcdO3("9220"); i2.setHistologyIcdO3("9180");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        i1.setHistologyIcdO3("8891"); i2.setHistologyIcdO3("8896");
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

        ruleStepToTest = "M10";
        i1.setHistologyIcdO3("8800"); i2.setHistologyIcdO3("9220");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        i1.setHistologyIcdO3("8800"); i2.setHistologyIcdO3("9240");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        i1.setHistologyIcdO3("8800"); i2.setHistologyIcdO3("9180");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        ruleStepToTest = "M11";
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
        
        i1.setHistologyIcdO3("9506"); i2.setHistologyIcdO3("8800");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        i1.setHistologyIcdO3("9506"); i2.setHistologyIcdO3("8890");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        i1.setHistologyIcdO3("9506"); i2.setHistologyIcdO3("9240");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());

        i1.setHistologyIcdO3("9506"); i2.setHistologyIcdO3("8802");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(ruleStepToTest, output.getStep());



        // Rule M9	Abstract multiple primaries when separate, non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 3 in the Equivalent Terms and Definitions. Timing is irrelevant.
        ruleStepToTest = "M9";
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
        // Does not apply.
        i1.setHistologyIcdO3("9071");
        i2.setHistologyIcdO3("8892");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M10	Abstract a single primary when separate, non-contiguous tumors are on the same row in Table 3 in the Equivalent Terms and Definitions. Timing is irrelevant.
        ruleStepToTest = "M10";
        ruleCountToTest = 6;
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
        // Does not apply.
        i1.setHistologyIcdO3("9505");
        i2.setHistologyIcdO3("9430");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setHistologyIcdO3("9071");
        i2.setHistologyIcdO3("8888");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M11	Abstract multiple primaries when separate, non-contiguous tumors are on different rows in Table 3 in the Equivalent Terms and Definitions. Timing is irrelevant.
        ruleStepToTest = "M11";
        ruleCountToTest = 7;
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
        i1.setHistologyIcdO3("9071");
        i2.setHistologyIcdO3("8888");
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

        // Rule M12	Abstract a single primary when multiple tumors do not meet any of the above criteria.
        // Can't Reach This.
        /*
        ruleStepToTest = "M12";
        ruleCountToTest = 8;
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
        // C700, C701, C709, C710-C719, C721-C725, C728, C729, C751-C753
        // Peripheral nerves C470, C473, C475, C476 (for nerve roots only)

        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;
        String ruleStepToTest = "";
        int ruleCountToTest = 0;

        // Rule M5	Abstract multiple primaries when a malignant tumor /3 occurs after a non-malignant tumor /0 or /1 AND:
        // •	The patient had a resection of the non-malignant tumor OR
        // •	It is unknown/not documented whether a resection was done

        // NOTE: This may not be testable. If one tumor is malignant, and the other is not, you get two different rule sets:
        // Mp2018NonMalignantCNSTumorsGroup and Mp2018MalignantCNSAndPeripheralNervesGroup. Two different rule sets are automatically
        // set to multiple primaries by MphUtuls.computePrimaries().
        /*
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
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertEquals("M5", output.getStep());
        i1.setPrimarySite("C705");
        i1.setHistologyIcdO3("8840");
        i1.setBehaviorIcdO3("1");
        i1.setDateOfDiagnosisYear("2017");
        i2.setPrimarySite("C706");
        i2.setHistologyIcdO3("8890");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertEquals("M5", output.getStep());
        // Does not apply.
        i1.setDateOfDiagnosisYear("2017");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M5", output.getStep());
        // Does not apply.
        i1.setPrimarySite("C701");
        i1.setHistologyIcdO3("9390");
        i1.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2017");
        i2.setPrimarySite("C701");
        i2.setHistologyIcdO3("9390");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M5", output.getStep());
        */


        // Rule M6	Abstract a single primary when the patient has bilateral:
        // •	Acoustic neuromas/ vestibular schwannomas 9560/0, OR
        // •	Optic gliomas/pilocytic astrocytomas 9421/1
        ruleStepToTest = "M6";
        ruleCountToTest = 2;
        i1.setPrimarySite("C701");
        i1.setHistologyIcdO3("9560");
        i1.setBehaviorIcdO3("0");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2017");
        i2.setPrimarySite("C700");
        i2.setHistologyIcdO3("9560");
        i2.setBehaviorIcdO3("0");
        i2.setLaterality("2");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setHistologyIcdO3("9421");
        i1.setBehaviorIcdO3("1");
        i2.setHistologyIcdO3("9421");
        i2.setBehaviorIcdO3("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i2.setLaterality("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i2.setLaterality("2");
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

        // Rule M7	Abstract multiple primaries when multiple tumors are present in any of the following sites:
        // •	Any lobe(s) of the brain C710-C719 AND any other part of CNS
        // •	Cerebral meninges C700 AND spinal meninges C701
        // •	Cerebral meninges C700 AND any other part of CNS
        // •	Any cranial nerve(s) C721-C725 AND any other part of the CNS
        // •	Meninges of cranial or peripheral nerves C709 AND any other part of the CNS
        // •	Spinal meninges C701 AND any other part of CNS
        // (Any other part of the CNS is any other site in the header...for example "cerebral meninges C700 and any other part of the CNS" equates to C700 and any
        //  other site in the header besides C700 (C701, C709, C710-C719, C721-C725, C728, C729, C751-C753))
        ruleStepToTest = "M7";
        ruleCountToTest = 3;
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
        i1.setHistologyIcdO3("9530");
        i1.setBehaviorIcdO3("0");
        i2.setPrimarySite("C471");
        i2.setHistologyIcdO3("9530");
        i2.setBehaviorIcdO3("0");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M8	Abstract multiple primaries when separate, non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 6 in the Equivalent Terms and Definitions. Timing is irrelevant.
        ruleStepToTest = "M8";
        ruleCountToTest = 4;
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
        // Does not apply.
        i1.setHistologyIcdO3("9493");
        i1.setBehaviorIcdO3("0");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        // Does not apply.
        i1.setHistologyIcdO3("9900");
        i1.setBehaviorIcdO3("0");
        i2.setHistologyIcdO3("9901");
        i2.setBehaviorIcdO3("0");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M9	Abstract a single primary when two or more separate/non-contiguous meningiomas arise in the cranial meninges.  Laterality is irrelevant and may be any of the following combinations:
        // •	The same laterality (left or right) of the cranial meninges
        // •	Bilateral (both left and right) cranial meninges
        // •	The midline AND in either the right or left cranial meninges
        ruleStepToTest = "M9";
        ruleCountToTest = 5;
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

        // Rule M10	Abstract a single primary when there are separate/non-contiguous tumors in the brain (multicentric/multifocal) with the same histology XXXX.  Tumors may be in any of the following locations and/or lateralities:
        // •	Same laterality: In the same lobe; for example, two tumors in right temporal lobe C712 (same site code)
        // •	Different lateralities of the same lobe; for example, left and right frontal lobes C711 (same site code)
        // •	Different lobes; for example, parietal lobe C713 and occipital lobe C714 (different site codes)
        ruleStepToTest = "M10";
        ruleCountToTest = 6;
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
        // Does not apply.
        i1.setPrimarySite("C729");
        i2.setPrimarySite("C714");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        // Rule M11	Abstract a single primary when separate/non-contiguous tumors are on the same row in Table 6 in the Equivalent Terms and Definitions.  Timing is irrelevant.
        ruleStepToTest = "M11";
        ruleCountToTest = 7;
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

        // Rule M12	Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 6 in the Equivalent Terms and Definitions. Timing is irrelevant.
        ruleStepToTest = "M12";
        ruleCountToTest = 8;
        i1.setPrimarySite("C728");
        i1.setHistologyIcdO3("9800");
        i1.setBehaviorIcdO3("1");
        i2.setPrimarySite("C729");
        i2.setHistologyIcdO3("9493");
        i2.setBehaviorIcdO3("0");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setHistologyIcdO3("9444");
        i1.setBehaviorIcdO3("1");
        i2.setHistologyIcdO3("9413");
        i2.setBehaviorIcdO3("0");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setHistologyIcdO3("9444");
        i1.setBehaviorIcdO3("1");
        i2.setHistologyIcdO3("8888");
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

        // Rule M13	Abstract a single primary when the tumors do not meet any of the above criteria.
        // Can't seem to get to this rule.
        /*
        ruleStepToTest = "M13";
        ruleCountToTest = 9;
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
    public void test2018OtherSites() {
        // Other Sites Multiple Primary Rules – Text
        // Excludes Head and Neck, Colon, Lung, Melanoma of Skin, Breast,
        // Kidney, Renal Pelvis, Ureter, Bladder, Brain, Lymphoma and Leukemia

        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;

        //M3- Adenocarcinoma of the prostate is always a single primary. (8140)
        i1.setPrimarySite("C619");
        i2.setPrimarySite("C619");
        i1.setHistologyIcdO3("8140");
        i2.setHistologyIcdO3("8140");
        i1.setBehaviorIcdO3("2");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2000");
        i2.setDateOfDiagnosisYear("2015");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(1, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("prostate"));

        //M4- Retinoblastoma is always a single primary (unilateral or bilateral). (9510, 9511, 9512, 9513)
        i1.setHistologyIcdO3("9510");
        i2.setHistologyIcdO3("9513");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(2, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Retinoblastoma"));

        //M5- Kaposi sarcoma (any site or sites) is always a single primary.
        i1.setPrimarySite("C400");
        i2.setPrimarySite("C619");
        i1.setHistologyIcdO3("9140");
        i2.setHistologyIcdO3("9140");
        i1.setBehaviorIcdO3("2");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Kaposi sarcoma"));

        //M6- Follicular and papillary tumors in the thyroid within 60 days of diagnosis are a single primary. (C739, 8340)
        i1.setPrimarySite("C739");
        i2.setPrimarySite("C739");
        i1.setHistologyIcdO3("8340");
        i2.setHistologyIcdO3("8340");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2011");
        i2.setDateOfDiagnosisYear("2011"); // same year month unknown
        output = _utils.computePrimaries(i1, i2);
        //Questionable at M6 and M7 with potential single and ended up as single at M18 -- SINGLE
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(16, output.getAppliedRules().size());
        Assert.assertEquals("M18", output.getStep());
        //Questionable at M6 and M7 with potential single and questionable at M10 with potential multiple -- Questionable
        i2.setDateOfDiagnosisYear("2010");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertEquals("M6", output.getStep());

        i1.setDateOfDiagnosisYear("2011");
        i2.setDateOfDiagnosisYear("2011");
        i1.setDateOfDiagnosisMonth("01");
        i2.setDateOfDiagnosisMonth("02"); // within 60 days definitely
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("thyroid"));

        //M7- Bilateral epithelial tumors (8000-8799) of the ovary within 60 days are a single primary. Ovary = C569
        i1.setPrimarySite("C569");
        i2.setPrimarySite("C569");
        i1.setHistologyIcdO3("8001");
        i2.setHistologyIcdO3("8799");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("ovary"));

        // M8 - Tumors on both sides (right and left) of a site listed in Table 1 are multiple primaries.
        i1.setPrimarySite("C622");
        i2.setPrimarySite("C629");
        i1.setHistologyIcdO3("8001");
        i2.setHistologyIcdO3("8799");
        output = _utils.computePrimaries(i1, i2);
        //Questionable at M8 with potential multiple and ended up as multiple at M17 -- Multiple
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(15, output.getAppliedRules().size());
        Assert.assertEquals("M17", output.getStep());
        i1.setLaterality("1");
        i2.setLaterality("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("both sides"));
        i1.setPrimarySite("C740");
        i2.setPrimarySite("C749");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("both sides"));
        i1.setPrimarySite("C630");
        i2.setPrimarySite("C630");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("both sides"));

        //M9 - Adenocarcinoma in adenomatous polyposis coli (familial polyposis) with one or more in situ or malignant polyps is a single primary.
        i1.setPrimarySite("C199");
        i2.setPrimarySite("C209");
        i1.setHistologyIcdO3("8220");
        i2.setHistologyIcdO3("8262");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(7, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("adenomatous"));
        i1.setBehaviorIcdO3("2"); // Both are insitu, continue to next step
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(7, output.getAppliedRules().size());

        //M10 - Tumors diagnosed more than one (1) year apart are multiple primaries.
        i1.setPrimarySite("C199");
        i2.setPrimarySite("C209");
        i1.setHistologyIcdO3("8220");
        i2.setHistologyIcdO3("8262");
        i1.setBehaviorIcdO3("2");
        i2.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisYear("2013");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(8, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("one"));
        i1.setDateOfDiagnosisMonth("01");
        i2.setDateOfDiagnosisMonth("01");
        i2.setDateOfDiagnosisYear("2014"); //not enough information
        output = _utils.computePrimaries(i1, i2);
        //Questionable at M10 with potential multiple and ended up as multiple at M11 -- Multiple
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(9, output.getAppliedRules().size());
        Assert.assertEquals("M11", output.getStep());
        i2.setDateOfDiagnosisYear("2015"); //less than a year, continue to the next step
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(9, output.getAppliedRules().size());
        Assert.assertEquals("M11", output.getStep());

        //M11 - Tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx) and/or third (Cx?x) character are multiple primaries.
        i1.setPrimarySite("C199");
        i2.setPrimarySite("C209");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(9, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("topography"));

        //M12 - Tumors with ICD-O-3 topography codes that differ only at the fourth character (Cxx?) and are in any one of the following primary sites are multiple primaries. ** Anus and anal canal (C21_) Bones, joints, and articular cartilage (C40_- C41_) Peripheral nerves and autonomic nervous system (C47_) Connective subcutaneous and other soft tissues (C49_) Skin (C44_)
        i1.setPrimarySite("C471");
        i2.setPrimarySite("C472");
        i1.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisYear("2015");
        i1.setHistologyIcdO3("8140");
        i2.setHistologyIcdO3("8140");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(10, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("topography"));
        i2.setPrimarySite("C471"); // not differ at the 4th character
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(10, output.getAppliedRules().size());
        i1.setPrimarySite("C461");
        i2.setPrimarySite("C462"); // not in the above list
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(10, output.getAppliedRules().size());

        //M13 - A frank in situ or malignant adenocarcinoma and an in situ or malignant tumor in a polyp are a single primary.
        i1.setHistologyIcdO3("8220"); //polyp
        i2.setHistologyIcdO3("8141"); //adenocarcinoma
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(11, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("polyp"));
        i2.setHistologyIcdO3("8211"); //both are polyp, continue to the next step
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(11, output.getAppliedRules().size());

        //M14 - Multiple in situ and/or malignant polyps are a single primary.
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(12, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("polyps"));

        //M15 - An invasive tumor following an in situ tumor more than 60 days after diagnosis is a multiple primary.
        i1.setPrimarySite("C199");
        i2.setPrimarySite("C197");
        i1.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisYear("2015");
        i1.setDateOfDiagnosisMonth("07");
        i2.setDateOfDiagnosisMonth("01");
        i1.setHistologyIcdO3("8140");
        i2.setHistologyIcdO3("8140");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(13, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("invasive"));
        i1.setBehaviorIcdO3("2");
        i2.setBehaviorIcdO3("3"); //isitu is following invasive, go to next step
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(13, output.getAppliedRules().size());

        //M16 - NOS VS Specific
        i1.setHistologyIcdO3("8140");
        i2.setHistologyIcdO3("8147");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(14, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("NOS"));
        i2.setHistologyIcdO3("8313"); //not specific for 8140, go to next step
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(14, output.getAppliedRules().size());

        //M17- Tumors with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.
        i1.setHistologyIcdO3("8140");
        i2.setHistologyIcdO3("8170");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(15, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("histology"));
        i2.setHistologyIcdO3("8149"); //different only on the last digit
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(15, output.getAppliedRules().size());

        //M18- Tumors that do not meet any of the criteria are abstracted as a single primary.
        i1.setHistologyIcdO3("8140");
        i2.setHistologyIcdO3("8149");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(16, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("criteria"));
    }

    @Test
    public void test2018Urinary() {
        // Urinary Sites Multiple Primary Rules
        // C659, C669, C670-C679, C680-C681, C688-C689
        // (Excludes lymphoma and leukemia M9590-M9992 and Kaposi sarcoma M9140)

        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;

        // Rule M3	Abstract multiple primaries when there are:
        // •	Separate/non-contiguous tumors in both the right AND left renal pelvis AND
        // •	No other urinary sites are involved
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
        Assert.assertEquals(1, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("tumor(s) in the right renal pelvis AND tumor(s) in the left renal pelvis"));
        Assert.assertEquals("M3", output.getStep());
        // Does not apply.
        i1.setLaterality("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M3", output.getStep());

        // Rule M4	Abstract multiple primaries when there are:
        // •	Separate/non-contiguous tumors in the right AND left ureter AND
        // •	No other urinary sites are involved
        i1.setPrimarySite("C669");
        i2.setPrimarySite("C669");
        i1.setLaterality("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(2, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("tumor(s) in both the right ureter AND tumor(s) in the left ureter"));
        Assert.assertEquals("M4", output.getStep());
        // Does not apply.
        i1.setLaterality("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M4", output.getStep());

        // Rule M5	Abstract a single primary when tumors are noninvasive in situ /2 urothelial carcinoma (flat tumor) 8120/2 in the following sites:
        //•	Bladder C67_ AND
        //•	One or both ureter(s) C669
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
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("bladder (C670-C679) or ureter (C669)"));
        Assert.assertEquals("M5", output.getStep());
        i1.setPrimarySite("C670");
        i1.setHistologyIcdO3("8120");
        i2.setPrimarySite("C675");
        i2.setHistologyIcdO3("8120");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("bladder (C670-C679) or ureter (C669)"));
        Assert.assertEquals("M5", output.getStep());
        // Does not apply.
        i1.setPrimarySite("C680");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M5", output.getStep());
        // Does not apply.
        i2.setHistologyIcdO3("8131");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M5", output.getStep());

        // Rule M6	Abstract a single primary when the patient has multiple occurrences of invasive tumors in the bladder that are:
        // •	Papillary urothelial carcinoma and a recurrence of papillary urothelial carcinoma 8130/3 OR
        // •	Urothelial carcinoma and a recurrence of urothelial carcinoma 8120/3
        i1.setPrimarySite("C670");
        i1.setHistologyIcdO3("8130");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2015");
        i2.setPrimarySite("C670");
        i2.setHistologyIcdO3("8130");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("2");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("papillary urothelial carcinoma or urothelial carcinoma"));
        Assert.assertEquals("M6", output.getStep());
        // Does not apply.
        i2.setPrimarySite("C680");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M6", output.getStep());
        // Does not apply.
        i2.setPrimarySite("C670");
        i2.setHistologyIcdO3("9500");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M6", output.getStep());

        // Rule M7	Abstract a single primary when the patient has multiple recurrences of in situ papillary urothelial carcinoma 8130/2 OR non-invasive urothelial carcinoma 8120/2 which:
        //•	Occur in the same urinary site OR
        //•	Are multifocal/multicentric tumors in multiple urinary sites
        i1.setPrimarySite("C670");
        i1.setHistologyIcdO3("8130");
        i1.setBehaviorIcdO3("2");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2015");
        i2.setPrimarySite("C670");
        i2.setHistologyIcdO3("8120");
        i2.setBehaviorIcdO3("2");
        i2.setLaterality("2");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("non-invasive papillary urothelial carcinoma 8130/2 OR in situ urothelial carcinoma 8120/2"));
        Assert.assertEquals("M7", output.getStep());
        // Does not apply.
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M7", output.getStep());

        // Rule M8	Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3 of Table 2 in the Equivalent Terms and Definitions. Timing is irrelevant.
        i1.setPrimarySite("C670");
        i1.setHistologyIcdO3("8144");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2015");
        i2.setPrimarySite("C670");
        i2.setHistologyIcdO3("8480");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("2");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("tumors that are two or more different subtypes/variants in Column 3, Table 2"));
        Assert.assertEquals("M8", output.getStep());
        // Does not apply.
        i1.setHistologyIcdO3("8144");
        i2.setHistologyIcdO3("8010");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M8", output.getStep());

        // Rule M9	Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 2 in the Equivalent Terms and Definitions. Timing is irrelevant.
        i1.setPrimarySite("C670");
        i1.setHistologyIcdO3("8070");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2015");
        i2.setPrimarySite("C670");
        i2.setHistologyIcdO3("8010");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("2");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(7, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("tumors that are on different rows in Table 2"));
        Assert.assertEquals("M9", output.getStep());
        i1.setHistologyIcdO3("8140");
        i2.setHistologyIcdO3("8070");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(7, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("tumors that are on different rows in Table 2"));
        Assert.assertEquals("M9", output.getStep());
        // Does not apply.
        i1.setHistologyIcdO3("8131");
        i2.setHistologyIcdO3("8130");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M9", output.getStep());
        // Does not apply.
        i1.setHistologyIcdO3("9500");
        i2.setHistologyIcdO3("9600");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M9", output.getStep());

        // Rule M10	Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than three years after the original diagnosis or last recurrence.
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
        Assert.assertEquals(8, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("greater than three (3) years apart"));
        Assert.assertEquals("M10", output.getStep());
        // Does not apply.
        i1.setDateOfDiagnosisYear("2016");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M10", output.getStep());

        // Rule M11	Abstract multiple primaries when the patient has multiple non-synchronous tumors which are:
        // •	Papillary urothelial/transitional cell NOS 8130/3 AND
        // •	Micropapillary urothelial/transitional cell 8131/3
        i1.setPrimarySite("C670");
        i1.setHistologyIcdO3("8130");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2016");
        i1.setDateOfDiagnosisMonth("2");
        i2.setPrimarySite("C670");
        i2.setHistologyIcdO3("8131");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("2");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(8, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Non-synchronous"));
        Assert.assertEquals("M11", output.getStep());
        // Does not apply.
        i1.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M11", output.getStep());

        // Rule M12	Abstract a single primary when separate/non-contiguous tumors are on the same row in Table 2 in the Equivalent Terms and Definitions.  Timing is irrelevant.
        i1.setPrimarySite("C670");
        i1.setHistologyIcdO3("8010");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("2");
        i2.setPrimarySite("C670");
        i2.setHistologyIcdO3("8380");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("2");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(9, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("tumors that are on the same row in Table 2"));
        Assert.assertEquals("M12", output.getStep());
        i1.setHistologyIcdO3("8800");
        i1.setBehaviorIcdO3("3");
        i2.setHistologyIcdO3("8802");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(9, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("tumors that are on the same row in Table 2"));
        Assert.assertEquals("M12", output.getStep());
        // Does not apply.
        i1.setHistologyIcdO3("8070");
        i2.setHistologyIcdO3("8010");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M12", output.getStep());
        // Does not apply.
        i1.setHistologyIcdO3("8380");
        i2.setHistologyIcdO3("8051");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M12", output.getStep());

        // Rule M13	Abstract a single primary (the invasive) when an in situ tumor is diagnosed after an invasive tumor AND tumors:
        // •	Occur in the same urinary site OR
        // •	The original tumors are multifocal/multicentric and occur in multiple urinary sites; subsequent tumor(s) are in at least one of the previously involved urinary sites
        i1.setPrimarySite("C670");
        i1.setHistologyIcdO3("8010");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2016");
        i2.setPrimarySite("C670");
        i2.setHistologyIcdO3("8310");
        i2.setBehaviorIcdO3("2");
        i2.setLaterality("2");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(11, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("in situ tumor following an invasive tumor"));
        Assert.assertEquals("M13", output.getStep());
        i1.setHistologyIcdO3("8031");
        i2.setHistologyIcdO3("8120");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(11, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("in situ tumor following an invasive tumor"));
        Assert.assertEquals("M13", output.getStep());
        // Does not apply.
        i1.setBehaviorIcdO3("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M13", output.getStep());

        // Rule M14	Abstract a single primary (the invasive) when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor AND tumors:
        // •	Occur in the same urinary site OR
        // •	Original tumor is multifocal/multicentric and involves multiple urinary sites; the subsequent invasive tumor(s) occur in at least one of the previously involved urinary sites
        i1.setPrimarySite("C670");
        i1.setHistologyIcdO3("8010");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("6");
        i2.setPrimarySite("C670");
        i2.setHistologyIcdO3("8310");
        i2.setBehaviorIcdO3("2");
        i2.setLaterality("2");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("5");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(12, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("invasive tumor following an in situ tumor less than or equal to 60"));
        Assert.assertEquals("M14", output.getStep());
        // Does not apply.
        i2.setDateOfDiagnosisMonth("6");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M14", output.getStep());

        // Rule M15	Abstract multiple primaries when an invasive tumor occurs more than 60 days after an in situ tumor AND tumors:
        // •	Occur in the same urinary site OR
        // •	Are multifocal/multicentric tumors in multiple urinary sites
        i1.setPrimarySite("C670");
        i1.setHistologyIcdO3("8000");
        i1.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2016");
        i1.setDateOfDiagnosisMonth("1");
        i1.setLaterality("9");
        i2.setPrimarySite("C670");
        i2.setHistologyIcdO3("8001");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("2");
        i2.setLaterality("9");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(13, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("in situ tumor more than 60 days after"));
        Assert.assertEquals("M15", output.getStep());
        // Does not apply.
        i1.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M15", output.getStep());

        // Rule M16	Abstract a single primary when tumors do not meet any of the above criteria.
        i1.setPrimarySite("C670");
        i1.setHistologyIcdO3("8010");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("6");
        i2.setPrimarySite("C670");
        i2.setHistologyIcdO3("8310");
        i2.setBehaviorIcdO3("2");
        i2.setLaterality("2");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("6");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(14, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("criteria"));
        Assert.assertEquals("M16", output.getStep());
    }


    @Test
    public void test2018TestBed() {
        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;

        i1.setPrimarySite("C509");
        i1.setHistologyIcdO3("8530");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("1");
        i1.setDateOfDiagnosisDay("1");

        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8530");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("2");
        i2.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("1");
        i1.setDateOfDiagnosisDay("1");

        output = _utils.computePrimaries(i1, i2);
        //Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        //Assert.assertEquals("", output.getAppliedRules().size());
        //Assert.assertEquals("", output.getStep());
    }

    @Test
    public void test2018GetWordDocLastModifiedDates() {

        List<String> fileList = new ArrayList<String>();
        fileList.add("\\\\omni\\btp\\csb\\SEER Documentation\\Solid Tumor Rules\\Breast\\Breast_STM.docx");
        fileList.add("\\\\omni\\btp\\csb\\SEER Documentation\\Solid Tumor Rules\\Colon\\Colon_STM.docx");
        fileList.add("\\\\omni\\btp\\csb\\SEER Documentation\\Solid Tumor Rules\\Head and Neck\\Head and Neck_STM.docx");
        //fileList.add("\\\\omni\\btp\\csb\\SEER Documentation\\Solid Tumor Rules\\Kidney\\Kidney_STM.docx");
        fileList.add("\\\\omni\\btp\\csb\\SEER Documentation\\Solid Tumor Rules\\Lung\\Lung_STM.docx");
        fileList.add("\\\\omni\\btp\\csb\\SEER Documentation\\Solid Tumor Rules\\Malignant Brain\\Malignant CNS STM.docx");
        fileList.add("\\\\omni\\btp\\csb\\SEER Documentation\\Solid Tumor Rules\\Melanoma\\Melanoma_STM.docx");
        //fileList.add("\\\\omni\\btp\\csb\\SEER Documentation\\Solid Tumor Rules\\Non-malignant CNS\\Non malignant_CNS_STM_quarterly.docx");
        fileList.add("\\\\omni\\btp\\csb\\SEER Documentation\\Solid Tumor Rules\\Other Sites\\Other_sites_STM.docx");
        fileList.add("\\\\omni\\btp\\csb\\SEER Documentation\\Solid Tumor Rules\\Urinary\\Urinary_STM.docx");

        String modDate = "";
        String outLine = "";

        for (String fileName : fileList) {
            modDate = GetFileLastModified(fileName);

            outLine = "File: ";
            outLine += fileName;
            outLine += StringUtils.repeat(' ', 100 - fileName.length());
            outLine += "  Last Modified: ";
            outLine += modDate;

            System.out.println(outLine);
        }
    }

    public String GetFileLastModified(String fileName)
    {
        File file = new File(fileName);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return sdf.format(file.lastModified());
    }

}