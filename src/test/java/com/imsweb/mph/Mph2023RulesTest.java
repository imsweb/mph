/*
 * Copyright (C) 2023 Information Management Services, Inc.
 */
package com.imsweb.mph;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.imsweb.mph.MphUtils.MpResult;

public class Mph2023RulesTest {

    private MphUtils _utils = MphUtils.getInstance();

    @BeforeClass
    public static void setUp() {
        MphUtils.initialize(new DefaultHematoDbUtilsProvider());
    }

    @Test
    public void test2023OtherSites() {
        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;

        //M3- Adenocarcinoma of the prostate is always a single primary. (8140)
        i1.setPrimarySite("C619");
        i2.setPrimarySite("C619");
        i1.setHistologyIcdO3("8140");
        i2.setHistologyIcdO3("8140");
        i1.setBehaviorIcdO3("2");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2023");
        i2.setDateOfDiagnosisYear("2023");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2023_OTHER_SITES_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals("M3", output.getStep());

        //M4 Abstract multiple primaries when the patient has a subsequent small cell carcinoma of the prostate more than 1 year following a diagnosis of acinar adenocarcinoma and/or subtype/variant of acinar adenocarcinoma of prostate (Table 3).
        i1.setDateOfDiagnosisYear("2019");
        i2.setHistologyIcdO3("8041");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2023_OTHER_SITES_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals("M4", output.getStep());

        //Questionable at M4 if not sure year apart
        i1.setDateOfDiagnosisYear("2023");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2023_OTHER_SITES_GROUP_ID, output.getGroupId());
        Assert.assertNotEquals("M4", output.getStep());

        //M5- Retinoblastoma is always a single primary (unilateral or bilateral). (9510, 9511, 9512, 9513)
        i1.setHistologyIcdO3("9510");
        i2.setHistologyIcdO3("9513");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2023_OTHER_SITES_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals("M5", output.getStep());

        //M6- Kaposi sarcoma (any site or sites) is always a single primary.
        i1.setPrimarySite("C400");
        i2.setPrimarySite("C619");
        i1.setHistologyIcdO3("9140");
        i2.setHistologyIcdO3("9140");
        i1.setBehaviorIcdO3("2");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2023_OTHER_SITES_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals("M6", output.getStep());
        Assert.assertTrue(output.getReason().contains("Kaposi sarcoma"));

        //M7- Follicular and papillary tumors in the thyroid within 60 days of diagnosis are a single primary. (C739, 8340)
        i1.setPrimarySite(MphConstants.THYROID);
        i2.setPrimarySite(MphConstants.THYROID);
        i1.setHistologyIcdO3("8340");
        i2.setHistologyIcdO3("8340");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2023");
        i2.setDateOfDiagnosisYear("2023");
        i1.setDateOfDiagnosisMonth("01");
        i2.setDateOfDiagnosisMonth("02"); // within 60 days definitely
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2023_OTHER_SITES_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals("M7", output.getStep());
        Assert.assertTrue(output.getReason().contains("thyroid"));

        //M8 Abstract multiple primaries when separate/non-contiguous tumors are anaplastic carcinoma and any other histologies in the thyroid.
        i1.setHistologyIcdO3("8021");
        i2.setHistologyIcdO3("8343");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2023_OTHER_SITES_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals("M8", output.getStep());
        Assert.assertTrue(output.getReason().contains("anaplastic"));

        //M9- Bilateral epithelial tumors (8000-8799) of the ovary within 60 days are a single primary. Ovary = C569
        i1.setPrimarySite(MphConstants.OVARY);
        i2.setPrimarySite(MphConstants.OVARY);
        i1.setHistologyIcdO3("8001");
        i2.setHistologyIcdO3("8001");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2023_OTHER_SITES_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals("M9", output.getStep());
        Assert.assertTrue(output.getReason().contains("ovary"));

        // M10 - Tumors on both sides (right and left) of a site listed in Table 1 are multiple primaries.
        i1.setPrimarySite("C622");
        i2.setPrimarySite("C629");
        i1.setHistologyIcdO3("8001");
        i2.setHistologyIcdO3("8799");
        i1.setLaterality("1");
        i2.setLaterality("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2023_OTHER_SITES_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals("M10", output.getStep());
        Assert.assertTrue(output.getReason().contains("both sides"));
        i1.setPrimarySite("C740");
        i2.setPrimarySite("C749");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2023_OTHER_SITES_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals("M10", output.getStep());
        Assert.assertTrue(output.getReason().contains("both sides"));
        i1.setPrimarySite("C630");
        i2.setPrimarySite("C630");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2023_OTHER_SITES_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals("M10", output.getStep());
        Assert.assertTrue(output.getReason().contains("both sides"));

        //M11 - Adenocarcinoma in adenomatous polyposis coli (familial polyposis) with one or more in situ or malignant polyps is a single primary.
        //This will never hit because the sites for this rule are in 2022 colon group after 2022.


        //M12 - Tumors diagnosed more than one (1) year apart are multiple primaries.
        i1.setPrimarySite("C400");
        i2.setPrimarySite("C490");
        i1.setHistologyIcdO3("8220");
        i2.setHistologyIcdO3("8262");
        i1.setBehaviorIcdO3("2");
        i2.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2023");
        i2.setDateOfDiagnosisYear("2021");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2023_OTHER_SITES_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals("M12", output.getStep());
        Assert.assertTrue(output.getReason().contains("one"));

        //M13 - Tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx) and/or third (Cx?x) character are multiple primaries.
        i1.setPrimarySite("C400");
        i2.setPrimarySite("C490");
        i1.setDateOfDiagnosisYear("2023");
        i2.setDateOfDiagnosisYear("2023");
        i1.setDateOfDiagnosisMonth("2");
        i2.setDateOfDiagnosisMonth("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2023_OTHER_SITES_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals("M13", output.getStep());
        Assert.assertTrue(output.getReason().contains("topography"));

        //M14 - Tumors with ICD-O-3 topography codes that differ only at the fourth character (Cxx?) and are in any one of the following primary sites are multiple primaries. ** Anus and anal canal (C21_) Bones, joints, and articular cartilage (C40_- C41_) Peripheral nerves and autonomic nervous system (C47_) Connective subcutaneous and other soft tissues (C49_) Skin (C44_)
        i1.setPrimarySite("C401");
        i2.setPrimarySite("C402");
        i1.setHistologyIcdO3("8140");
        i2.setHistologyIcdO3("8140");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2023_OTHER_SITES_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals("M14", output.getStep());
        Assert.assertTrue(output.getReason().contains("topography"));
        i2.setPrimarySite("C401"); // not differ at the 4th character
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M14", output.getStep());
        i1.setPrimarySite("C461");
        i2.setPrimarySite("C462"); // not in the above list
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals("M14", output.getStep());

        //M15 - A frank in situ or malignant adenocarcinoma and an in situ or malignant tumor in a polyp are a single primary.
        i1.setHistologyIcdO3("8220"); //polyp
        i2.setHistologyIcdO3("8141"); //adenocarcinoma
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2023_OTHER_SITES_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals("M15", output.getStep());
        Assert.assertTrue(output.getReason().contains("polyp"));

        //M16 - Multiple in situ and/or malignant polyps are a single primary.
        i2.setHistologyIcdO3("8211");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2023_OTHER_SITES_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals("M16", output.getStep());
        Assert.assertTrue(output.getReason().contains("polyps"));

        //M17 -Abstract a single primary when synchronous, separate/non-contiguous tumors are on the same row in Table 3-21.
        i1.setPrimarySite("C540");
        i2.setPrimarySite("C543");
        i1.setHistologyIcdO3("8380");
        i2.setHistologyIcdO3("8570");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2023_OTHER_SITES_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals("M17", output.getStep());
        Assert.assertTrue(output.getReason().contains("Table 3-21"));
        i1.setPrimarySite("C490");
        i2.setPrimarySite("C490");
        i1.setHistologyIcdO3("9367");
        i2.setHistologyIcdO3("8800");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2023_OTHER_SITES_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals("M17", output.getStep());
        Assert.assertTrue(output.getReason().contains("Table 3-21"));

        //M18 - Abstract multiple primaries when separate/non-contiguous tumors are on multiple rows in Table 2-21.
        i1.setHistologyIcdO3("8890");
        i2.setHistologyIcdO3("8850");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2023_OTHER_SITES_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals("M18", output.getStep());
        Assert.assertTrue(output.getReason().contains("Table 2-21"));

        i1.setPrimarySite("C384");
        i2.setPrimarySite("C384");
        i1.setHistologyIcdO3("8045");
        i2.setHistologyIcdO3("8013");
        i1.setLaterality("1");
        i2.setLaterality("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2023_OTHER_SITES_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals("M18", output.getStep());
        Assert.assertTrue(output.getReason().contains("Table 2-21"));

        i1.setPrimarySite("C250");
        i2.setPrimarySite("C250");
        i1.setHistologyIcdO3("8470");
        i2.setHistologyIcdO3("8470");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2023_OTHER_SITES_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals("M18", output.getStep());
        Assert.assertTrue(output.getReason().contains("Table 2-21"));

        //M19 - An invasive tumor following an in situ tumor more than 60 days after diagnosis is a multiple primary.
        i1.setPrimarySite("C384");
        i2.setPrimarySite("C384");
        i1.setDateOfDiagnosisYear("2023");
        i2.setDateOfDiagnosisYear("2023");
        i1.setDateOfDiagnosisMonth("07");
        i2.setDateOfDiagnosisMonth("01");
        i1.setHistologyIcdO3("8013");
        i2.setHistologyIcdO3("8013");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2023_OTHER_SITES_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals("M19", output.getStep());
        Assert.assertTrue(output.getReason().contains("invasive"));

        //M20- Tumors that do not meet any of the criteria are abstracted as a single primary.
        i1.setBehaviorIcdO3("2");
        i2.setBehaviorIcdO3("3"); //isitu is following invasive, go to next step
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphConstants.MP_2023_OTHER_SITES_GROUP_ID, output.getGroupId());
        Assert.assertEquals(MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals("M20", output.getStep());
        Assert.assertTrue(output.getReason().contains("criteria"));
    }
    
}
