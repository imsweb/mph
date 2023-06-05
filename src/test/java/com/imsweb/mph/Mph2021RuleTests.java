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
        Assert.assertEquals(MphConstants.MP_2021_CUTANEOUS_MELANOMA_GROUP_ID, output.getGroupName());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("topography"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        //C449 is treated as unknown
        i2.setPrimarySite("C449");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(MphConstants.MP_2021_CUTANEOUS_MELANOMA_GROUP_ID, output.getGroupName());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setLaterality("1");
        i2.setLaterality("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(MphConstants.MP_2021_CUTANEOUS_MELANOMA_GROUP_ID, output.getGroupName());
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
        Assert.assertEquals(MphConstants.MP_2021_CUTANEOUS_MELANOMA_GROUP_ID, output.getGroupName());
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("lateralities"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        //melanoma mid-line laterality is considered as different laterality of right or left
        i2.setLaterality("5");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2021_CUTANEOUS_MELANOMA_GROUP_ID, output.getGroupName());
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("lateralities"));
        Assert.assertEquals(ruleStepToTest, output.getStep());
        //if one laterality is unknown, continue to the next step
        i2.setLaterality("9");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2021_CUTANEOUS_MELANOMA_GROUP_ID, output.getGroupName());
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
        Assert.assertEquals(MphConstants.MP_2021_CUTANEOUS_MELANOMA_GROUP_ID, output.getGroupName());
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
        Assert.assertEquals(MphConstants.MP_2021_CUTANEOUS_MELANOMA_GROUP_ID, output.getGroupName());
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
        Assert.assertEquals(MphConstants.MP_2021_CUTANEOUS_MELANOMA_GROUP_ID, output.getGroupName());
        Assert.assertEquals(MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setLaterality("9");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2021_CUTANEOUS_MELANOMA_GROUP_ID, output.getGroupName());
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());

        i1.setPrimarySite("C440");
        i2.setPrimarySite("C440");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2021_CUTANEOUS_MELANOMA_GROUP_ID, output.getGroupName());
        Assert.assertEquals(MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i2.setDateOfDiagnosisMonth("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2021_CUTANEOUS_MELANOMA_GROUP_ID, output.getGroupName());
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
