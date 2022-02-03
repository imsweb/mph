/*
 * Copyright (C) 2022 Information Management Services, Inc.
 */
package com.imsweb.mph;

import org.junit.Assert;
import org.junit.Test;

import com.imsweb.mph.MphUtils.MpResult;

public class Mph2022RuleTests {

    private final MphUtils _utils = MphUtils.getInstance();

    @Test
    public void test2022Breast() {
        // Rule M12 Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3 of Table 3 in the Equivalent Terms and Definitions. Timing is irrelevant.
        String ruleStepToTest = "M12";
        int ruleCountToTest = 7;
        MphInput i1 = new MphInput();
        MphInput i2 = new MphInput();
        MphOutput output;
        i1.setPrimarySite("C500");
        i1.setHistologyIcdO3("8230");
        i1.setBehaviorIcdO3("2");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2016");
        i1.setDateOfDiagnosisMonth("");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8230");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        i1.setDateOfDiagnosisYear("2020");
        i2.setDateOfDiagnosisYear("2022");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertTrue(output.getReason().contains("subtypes/variants in Column 3, Table 3"));

        // Rule M13 Abstract a single primary when synchronous, separate/non-contiguous tumors are on the same row in Table 3 in the Equivalent Terms and Definitions. Timing is irrelevant.
        ruleStepToTest = "M13";
        ruleCountToTest = 8;
        i1.setPrimarySite("C500");
        i1.setHistologyIcdO3("8500");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("1");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8230");
        i2.setBehaviorIcdO3("2");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertNotEquals(ruleStepToTest, output.getStep());

        i1.setDateOfDiagnosisYear("2022");
        i2.setDateOfDiagnosisYear("2022");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertTrue(output.getReason().contains("same row in Table 3"));
    }

    @Test
    public void test2022HeadAndNeck() {

        MphInput i1 = new MphInput();
        MphInput i2 = new MphInput();
        MphOutput output;
        String ruleStepToTest = "M5";
        int ruleCountToTest = 3;
        //Tonsil changed to "C098, C099" from "C090,C091,C098,C099"
        i1.setPrimarySite("C090");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("1");
        i1.setHistologyIcdO3("8072");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i2.setPrimarySite("C099");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("1");
        i2.setHistologyIcdO3("8074");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, output.getGroupId());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        i1.setDateOfDiagnosisYear("2022");
        i2.setDateOfDiagnosisYear("2022");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2022_HEAD_AND_NECK_GROUP_ID, output.getGroupId());
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        Assert.assertNotEquals(ruleCountToTest, output.getAppliedRules().size());

        //Table 1: 8072 row deleted, 8072 moved to the subtype/variant column of the 8070 row
        ruleStepToTest = "M7";
        ruleCountToTest = 5;
        i1.setPrimarySite("C312");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("1");
        i1.setHistologyIcdO3("8072");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C319");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("1");
        i2.setHistologyIcdO3("8074");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, output.getGroupId());
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
        Assert.assertNotEquals(ruleCountToTest, output.getAppliedRules().size());
        i1.setDateOfDiagnosisYear("2022");
        i2.setDateOfDiagnosisYear("2022");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2022_HEAD_AND_NECK_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());

        //8082 and 8020 added as subtypes of 8070 for table 2 in 2022
        i1.setPrimarySite("C110");
        i2.setPrimarySite("C119");
        i1.setHistologyIcdO3("8082");
        i2.setHistologyIcdO3("8020");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2022_HEAD_AND_NECK_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());

        //C024 moved to table 5 from table 4
        i1.setPrimarySite("C024");
        i2.setPrimarySite("C024");
        i1.setHistologyIcdO3("8075");
        i2.setHistologyIcdO3("8071");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setDateOfDiagnosisYear("2022");
        i2.setDateOfDiagnosisYear("2022");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2022_HEAD_AND_NECK_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        //Table 5 histologies
        i1.setHistologyIcdO3("8085");
        i2.setHistologyIcdO3("8071");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2022_HEAD_AND_NECK_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());

        ruleStepToTest = "M8";
        ruleCountToTest = 6;
        //table 1, 8070 and 8072 were in different row in 2018
        i1.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisYear("2018");
        i1.setPrimarySite("C312");
        i2.setPrimarySite("C319");
        i1.setHistologyIcdO3("8072");
        i2.setHistologyIcdO3("8070");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleCountToTest, output.getAppliedRules().size());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        //they are in the same row in 2022
        i1.setDateOfDiagnosisYear("2022");
        i2.setDateOfDiagnosisYear("2022");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertTrue(output.getAppliedRules().size() > ruleCountToTest);
        Assert.assertNotEquals(ruleStepToTest, output.getStep());
    }

    @Test
    public void test2022Kidney() {
        // 8311
        MphInput i1 = new MphInput();
        MphInput i2 = new MphInput();
        MphOutput output;
        String ruleStepToTest = "M8";
        i1.setPrimarySite("C649");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2018");
        i1.setDateOfDiagnosisMonth("1");
        i2.setPrimarySite("C649");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisMonth("1");

        i1.setHistologyIcdO3("8311");
        i2.setHistologyIcdO3("8311");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());

        i1.setDateOfDiagnosisYear("2022");
        i2.setDateOfDiagnosisYear("2022");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
    }

    @Test
    public void test2022MalignantCNSAndPeripheralNerves() {
        //Table 3: 9540 row: Added as a subtype/variant: "Epithelioid malignant peripheral nerve sheath tumor 9542"
        MphInput i1 = new MphInput();
        MphInput i2 = new MphInput();
        MphOutput output;
        String ruleStepToTest = "M9";
        i1.setPrimarySite("C728");
        i1.setHistologyIcdO3("9542");
        i1.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2022");
        i2.setPrimarySite("C729");
        i2.setHistologyIcdO3("9538");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2022");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());

        ruleStepToTest = "M10";
        i1.setDateOfDiagnosisYear("2022");
        i2.setDateOfDiagnosisYear("2022");
        i2.setHistologyIcdO3("9540");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(ruleStepToTest, output.getStep());
        i1.setDateOfDiagnosisYear("2018");
        i2.setDateOfDiagnosisYear("2018");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());
    }
}
