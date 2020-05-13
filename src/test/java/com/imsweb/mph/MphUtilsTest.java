/*
 * Copyright (C) 2013 Information Management Services, Inc.
 */
package com.imsweb.mph;

import java.time.LocalDate;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.imsweb.mph.MphUtils.MpResult;
import com.imsweb.mph.internal.TempRuleResult;
import com.imsweb.mph.mpgroups.Mp1998HematopoieticGroup;
import com.imsweb.mph.mpgroups.Mp2001HematopoieticGroup;
import com.imsweb.mph.mpgroups.Mp2004BenignBrainGroup;
import com.imsweb.mph.mpgroups.Mp2004SolidMalignantGroup;
import com.imsweb.mph.mpgroups.Mp2007BenignBrainGroup;
import com.imsweb.mph.mpgroups.Mp2007BreastGroup;
import com.imsweb.mph.mpgroups.Mp2007ColonGroup;
import com.imsweb.mph.mpgroups.Mp2007HeadAndNeckGroup;
import com.imsweb.mph.mpgroups.Mp2007KidneyGroup;
import com.imsweb.mph.mpgroups.Mp2007LungGroup;
import com.imsweb.mph.mpgroups.Mp2007MalignantBrainGroup;
import com.imsweb.mph.mpgroups.Mp2007OtherSitesGroup;
import com.imsweb.mph.mpgroups.Mp2007UrinaryGroup;
import com.imsweb.mph.mpgroups.Mp2010HematopoieticGroup;
import com.imsweb.mph.mpgroups.Mp2018BreastGroup;
import com.imsweb.mph.mpgroups.Mp2018ColonGroup;
import com.imsweb.mph.mpgroups.Mp2018CutaneousMelanomaGroup;
import com.imsweb.mph.mpgroups.Mp2018HeadAndNeckGroup;
import com.imsweb.mph.mpgroups.Mp2018KidneyGroup;
import com.imsweb.mph.mpgroups.Mp2018LungGroup;
import com.imsweb.mph.mpgroups.Mp2018MalignantCNSAndPeripheralNervesGroup;
import com.imsweb.mph.mpgroups.Mp2018NonMalignantCNSTumorsGroup;
import com.imsweb.mph.mpgroups.Mp2018OtherSitesGroup;
import com.imsweb.mph.mpgroups.Mp2018UrinarySitesGroup;


public class MphUtilsTest {

    private MphUtils _utils = MphUtils.getInstance();

    @BeforeClass
    public static void setUp() {
        MphUtils.initialize(new DefaultHematoDbUtilsProvider());
    }

    @Test
    public void testFindCancerGroup() {
        //Invalid and unknown primary site, histology, behavior or year ----> Undefined group
        Assert.assertNull(_utils.findCancerGroup(null, "8100", "1", 2007));
        Assert.assertNull(_utils.findCancerGroup("123", "8100", "2", 2010));
        Assert.assertNull(_utils.findCancerGroup("C4567", "8100", "0", 2009));
        Assert.assertNull(_utils.findCancerGroup("D456", "8100", "0", 2000));
        Assert.assertNull(_utils.findCancerGroup("C809", "8100", "3", 2000));
        Assert.assertNull(_utils.findCancerGroup("C329", null, "0", 2000));
        Assert.assertNull(_utils.findCancerGroup("C000", "10", "0", 2000));
        Assert.assertNull(_utils.findCancerGroup("C005", "8100", "01", 2000));
        Assert.assertNull(_utils.findCancerGroup("C005", "8100", "5", 2000));
        Assert.assertNull(_utils.findCancerGroup("C005", "8100", "A", 2000));
        Assert.assertNull(_utils.findCancerGroup("C005", "8100", "3", LocalDate.now().getYear() + 1));
        Assert.assertNull(_utils.findCancerGroup("C005", "8100", "3", -1));
        // non reportable
        Assert.assertNull(_utils.findCancerGroup("C180", "8100", "1", 2000));
        Assert.assertNull(_utils.findCancerGroup("C440", "8725", "0", 2010));
        Assert.assertNull(_utils.findCancerGroup("C009", "9000", "1", 2000));

        //2007 Head and Neck
        Assert.assertEquals(new Mp2007HeadAndNeckGroup(), _utils.findCancerGroup("C005", "8100", "3", 2007));

        //2007 Colon
        Assert.assertEquals(new Mp2007ColonGroup(), _utils.findCancerGroup("C180", "8100", "3", 2008));

        //2007 Lung
        Assert.assertEquals(new Mp2007LungGroup(), _utils.findCancerGroup("C340", "8100", "3", 2009));

        //2007 Melanoma - Removed, only use 2018 rule now.
        //Assert.assertEquals(new Mp2007MelanomaGroup(), _utils.findCancerGroup("C440", "8725", "3", 2010));

        //2007 Breast
        Assert.assertEquals(new Mp2007BreastGroup(), _utils.findCancerGroup("C500", "8100", "3", 2011));

        //2007 Kidney
        Assert.assertEquals(new Mp2007KidneyGroup(), _utils.findCancerGroup("C649", "8100", "3", 2012));

        //2007 Urinary
        Assert.assertEquals(new Mp2007UrinaryGroup(), _utils.findCancerGroup("C672", "8100", "3", 2017));

        //2007 Benign Brain 
        Assert.assertEquals(new Mp2007BenignBrainGroup(), _utils.findCancerGroup("C751", "8100", "0", 2007));

        //2007 Malignant Brain 
        Assert.assertEquals(new Mp2007MalignantBrainGroup(), _utils.findCancerGroup("C751", "8100", "3", 2009));

        //2007 Other Sites        
        Assert.assertEquals(new Mp2007OtherSitesGroup(), _utils.findCancerGroup("C887", "8200", "3", 2007)); //primary site not in groups
        Assert.assertEquals(new Mp2007OtherSitesGroup(), _utils.findCancerGroup("C445", "8800", "3", 2007)); //melanoma with excluded histology
        Assert.assertEquals(new Mp2007OtherSitesGroup(), _utils.findCancerGroup("C180", "9140", "3", 2007)); //Kaposi sarcoma
        Assert.assertEquals(new Mp2007OtherSitesGroup(), _utils.findCancerGroup("C751", "8100", "2", 2007)); //Brain which is neither malignant nor benign

        //1998 Hematopoietic
        Assert.assertEquals(new Mp1998HematopoieticGroup(), _utils.findCancerGroup("C445", "9590", "3", 2000));
        Assert.assertEquals(new Mp1998HematopoieticGroup(), _utils.findCancerGroup("C009", "9989", "2", 1950));
        Assert.assertEquals(new Mp1998HematopoieticGroup(), _utils.findCancerGroup("C709", "9700", "2", 1998));

        //2001 Hematopoietic
        Assert.assertEquals(new Mp2001HematopoieticGroup(), _utils.findCancerGroup("C445", "9590", "3", 2001));
        Assert.assertEquals(new Mp2001HematopoieticGroup(), _utils.findCancerGroup("C009", "9989", "2", 2005));
        Assert.assertEquals(new Mp2001HematopoieticGroup(), _utils.findCancerGroup("C709", "9700", "2", 2009));

        //2010 Hematopoietic
        Assert.assertEquals(new Mp2010HematopoieticGroup(), _utils.findCancerGroup("C445", "9590", "3", 2010));
        Assert.assertEquals(new Mp2010HematopoieticGroup(), _utils.findCancerGroup("C009", "9989", "2", 2015));
        Assert.assertEquals(new Mp2010HematopoieticGroup(), _utils.findCancerGroup("C709", "9700", "2", 2017));

        //2004 Benign Brain
        Assert.assertEquals(new Mp2004BenignBrainGroup(), _utils.findCancerGroup("C700", "8000", "0", 2000));
        Assert.assertEquals(new Mp2004BenignBrainGroup(), _utils.findCancerGroup("C718", "8700", "1", 2006));
        Assert.assertEquals(new Mp2004BenignBrainGroup(), _utils.findCancerGroup("C752", "9000", "1", 1980));

        //2004 Solid tumor
        Assert.assertEquals(new Mp2004SolidMalignantGroup(), _utils.findCancerGroup("C700", "8000", "3", 2000));
        Assert.assertEquals(new Mp2004SolidMalignantGroup(), _utils.findCancerGroup("C718", "8700", "3", 2006));
        Assert.assertEquals(new Mp2004SolidMalignantGroup(), _utils.findCancerGroup("C752", "9000", "3", 1980));
        Assert.assertEquals(new Mp2004SolidMalignantGroup(), _utils.findCancerGroup("C009", "9000", "3", 2000));

        //2018 Breast
        Assert.assertEquals(new Mp2018BreastGroup(), _utils.findCancerGroup("C500", "8100", "3", 2018));

        //2018 Colon
        Assert.assertEquals(new Mp2018ColonGroup(), _utils.findCancerGroup("C180", "8100", "3", 2018));

        //2018 Cutaneous Melanoma
        Assert.assertEquals(new Mp2018CutaneousMelanomaGroup(), _utils.findCancerGroup("C440", "8725", "3", 2018));

        //2018 Head and Neck
        Assert.assertEquals(new Mp2018HeadAndNeckGroup(), _utils.findCancerGroup("C005", "8100", "3", 2018));

        //2018 Kidney
        Assert.assertEquals(new Mp2018KidneyGroup(), _utils.findCancerGroup("C649", "8100", "3", 2018));

        //2018 Lung
        Assert.assertEquals(new Mp2018LungGroup(), _utils.findCancerGroup("C340", "8100", "3", 2018));

        //2018 Malignant CNS And Peripheral Nerves
        Assert.assertEquals(new Mp2018MalignantCNSAndPeripheralNervesGroup(), _utils.findCancerGroup("C751", "8100", "3", 2018));

        //2018 Non-Malignant CNS Tumor
        Assert.assertEquals(new Mp2018NonMalignantCNSTumorsGroup(), _utils.findCancerGroup("C751", "8100", "0", 2018));

        //2018 Other Sites
        Assert.assertEquals(new Mp2018OtherSitesGroup(), _utils.findCancerGroup("C887", "8200", "3", 2018)); //primary site not in groups
        Assert.assertEquals(new Mp2018OtherSitesGroup(), _utils.findCancerGroup("C445", "8800", "3", 2018)); //melanoma with excluded histology
        Assert.assertEquals(new Mp2018OtherSitesGroup(), _utils.findCancerGroup("C180", "9140", "3", 2018)); //Kaposi sarcoma
        Assert.assertEquals(new Mp2018OtherSitesGroup(), _utils.findCancerGroup("C751", "8100", "2", 2018)); //Brain which is neither malignant nor benign

        //2018 Urinary Sites
        Assert.assertEquals(new Mp2018UrinarySitesGroup(), _utils.findCancerGroup("C672", "8100", "3", 2018));

    }

    @Test
    public void testGetAllGroups() {
        Map<String, MphGroup> testMap = _utils.getAllGroups();

        Assert.assertEquals(MphConstants.MP_1998_HEMATO_GROUP_ID, testMap.get(MphConstants.MP_1998_HEMATO_GROUP_ID).getId());
        Assert.assertEquals(MphConstants.MP_2001_HEMATO_GROUP_ID, testMap.get(MphConstants.MP_2001_HEMATO_GROUP_ID).getId());
        Assert.assertEquals(MphConstants.MP_2010_HEMATO_GROUP_ID, testMap.get(MphConstants.MP_2010_HEMATO_GROUP_ID).getId());
        Assert.assertEquals(MphConstants.MP_2004_SOLID_MALIGNANT_GROUP_ID, testMap.get(MphConstants.MP_2004_SOLID_MALIGNANT_GROUP_ID).getId());
        Assert.assertEquals(MphConstants.MP_2004_BENIGN_BRAIN_GROUP_ID, testMap.get(MphConstants.MP_2004_BENIGN_BRAIN_GROUP_ID).getId());
        Assert.assertEquals(MphConstants.MP_2007_BENIGN_BRAIN_GROUP_ID, testMap.get(MphConstants.MP_2007_BENIGN_BRAIN_GROUP_ID).getId());
        Assert.assertEquals(MphConstants.MP_2007_BREAST_GROUP_ID, testMap.get(MphConstants.MP_2007_BREAST_GROUP_ID).getId());
        Assert.assertEquals(MphConstants.MP_2007_COLON_GROUP_ID, testMap.get(MphConstants.MP_2007_COLON_GROUP_ID).getId());
        Assert.assertEquals(MphConstants.MP_2007_HEAD_AND_NECK_GROUP_ID, testMap.get(MphConstants.MP_2007_HEAD_AND_NECK_GROUP_ID).getId());
        Assert.assertEquals(MphConstants.MP_2007_KIDNEY_GROUP_ID, testMap.get(MphConstants.MP_2007_KIDNEY_GROUP_ID).getId());
        Assert.assertEquals(MphConstants.MP_2007_LUNG_GROUP_ID, testMap.get(MphConstants.MP_2007_LUNG_GROUP_ID).getId());
        Assert.assertEquals(MphConstants.MP_2007_MALIGNANT_BRAIN_GROUP_ID, testMap.get(MphConstants.MP_2007_MALIGNANT_BRAIN_GROUP_ID).getId());
        Assert.assertEquals(MphConstants.MP_2007_MELANOMA_GROUP_ID, testMap.get(MphConstants.MP_2007_MELANOMA_GROUP_ID).getId());
        Assert.assertEquals(MphConstants.MP_2007_OTHER_SITES_GROUP_ID, testMap.get(MphConstants.MP_2007_OTHER_SITES_GROUP_ID).getId());
        Assert.assertEquals(MphConstants.MP_2007_URINARY_GROUP_ID, testMap.get(MphConstants.MP_2007_URINARY_GROUP_ID).getId());
    }

    @Test
    public void testSpecialCases() {
        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;

        //Invalid properties
        i1.setPrimarySite("C809");
        i2.setPrimarySite("C080");
        i1.setHistologyIcdO3("8000");
        i1.setBehaviorIcdO3("3");
        i2.setHistologyIcdO3("8100");
        i2.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisYear("2015");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.INVALID_INPUT, output.getResult());
        Assert.assertTrue(output.getAppliedRules().isEmpty());
        i1.setPrimarySite("C080");
        i2.setPrimarySite("C080");
        i1.setHistologyIcdO3("8000");
        i1.setBehaviorIcdO3("3");
        i2.setHistologyIcdO3("8100");
        i2.setBehaviorIcdO3("x");
        i1.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisYear("2015");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.INVALID_INPUT, output.getResult());
        i1.setPrimarySite("C080");
        i2.setPrimarySite("D080");
        i1.setHistologyIcdO3("8000");
        i1.setBehaviorIcdO3("3");
        i2.setHistologyIcdO3("8100");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisYear("2015");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.INVALID_INPUT, output.getResult());

        //Different group
        i1.setPrimarySite("C080");
        i2.setPrimarySite("C080");
        i1.setHistologyIcdO3("9590");
        i1.setBehaviorIcdO3("3");
        i2.setHistologyIcdO3("8100");
        i2.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisYear("2015");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertTrue(output.getAppliedRules().isEmpty());

        i1.setPrimarySite("C080");
        i2.setPrimarySite("C342");
        i1.setHistologyIcdO3("8000");
        i1.setBehaviorIcdO3("3");
        i2.setHistologyIcdO3("8100");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisYear("2015");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertTrue(output.getAppliedRules().isEmpty());

        //unknown groups but same values for the main fields
        i1.setPrimarySite("C569");
        i2.setPrimarySite("C569");
        i1.setHistologyIcdO3("8472");
        i2.setHistologyIcdO3("8472");
        i1.setBehaviorIcdO3("1");
        i2.setBehaviorIcdO3("1");
        i1.setLaterality("1");
        i2.setLaterality("1");
        i1.setDateOfDiagnosisYear("2014");
        i2.setDateOfDiagnosisYear("2014");
        i1.setDateOfDiagnosisMonth("2");
        i2.setDateOfDiagnosisMonth("2");
        i1.setDateOfDiagnosisDay("11");
        i2.setDateOfDiagnosisDay("11");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertTrue(output.getAppliedRules().isEmpty());

        i1.setDateOfDiagnosisDay("30");
        i2.setDateOfDiagnosisDay("30"); //February 30
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());
        Assert.assertTrue(output.getAppliedRules().isEmpty());
    }

    @Test
    public void test2007BenignBrain() {
        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;

        // M3 - An invasive brain tumor (/3) and either a benign brain tumor (/0) or an uncertain/borderline brain tumor (/1) are always multiple primaries.
        //This will never happen, since the two conditions belong to different cancer group.

        // M4 - Tumors with ICD-O-3 topography codes that are different at the second (C?xx) and/or third characters (Cx?x), or fourth (Cxx?) are multiple primaries.
        i1.setPrimarySite("C701");
        i2.setPrimarySite("C700");
        i1.setHistologyIcdO3("8050");
        i2.setHistologyIcdO3("8123");
        i1.setBehaviorIcdO3("0");
        i2.setBehaviorIcdO3("0");
        i1.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisYear("2015");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(2, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("topography"));

        // M5 - Tumors on both sides (left and right) of a paired site (Table 1) are multiple primaries.
        i1.setPrimarySite("C714");
        i2.setPrimarySite("C714");
        i1.setHistologyIcdO3("9540");
        i2.setHistologyIcdO3("9540");
        i1.setBehaviorIcdO3("0");
        i2.setBehaviorIcdO3("1");
        i1.setLaterality("1");
        i2.setLaterality("4");
        output = _utils.computePrimaries(i1, i2);
        //questionable at M5 with potential multiple and questionable at M7 potential single -- QUESTIONABLE
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("laterality"));
        //questionable at M5 with potential multiple and definite single at M7-- QUESTIONABLE
        i1.setDateOfDiagnosisYear("2012");
        i2.setDateOfDiagnosisYear("2015");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("laterality"));
        //questionable at M5 with potential multiple and definite multiple at M9-- MULTIPLE
        i2.setHistologyIcdO3("9442");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(7, output.getAppliedRules().size());
        //Multiple primary at M5
        i2.setLaterality("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("paired"));

        // M6 - An atypical choroid plexus papilloma (9390/1) following a choroid plexus papilloma, NOS (9390/0) is a single primary.
        i1.setPrimarySite("C720");
        i2.setPrimarySite("C720");
        i1.setHistologyIcdO3("9390");
        i2.setHistologyIcdO3("9390");
        i1.setBehaviorIcdO3("1");
        i2.setBehaviorIcdO3("0");
        i1.setLaterality("1");
        i2.setLaterality("1");
        i1.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisYear("2015");
        output = _utils.computePrimaries(i1, i2);
        //Questionable at M6 with potential Single primary, and ended up single at M12 -- SINGLE
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(10, output.getAppliedRules().size());
        Assert.assertEquals("M12", output.getStep());
        i1.setDateOfDiagnosisMonth("1");
        i2.setDateOfDiagnosisMonth("2"); //9390/0 is following 9390/1 -- continue to the next rule
        i1.setDateOfDiagnosisDay("1");
        i2.setDateOfDiagnosisDay("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(4, output.getAppliedRules().size());
        i1.setDateOfDiagnosisMonth("1");
        i2.setDateOfDiagnosisMonth("1"); //9390/0 and 9390/1 diagnosed same day -- continue to the next rule
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(4, output.getAppliedRules().size());
        i1.setDateOfDiagnosisMonth("2");
        i2.setDateOfDiagnosisMonth("1"); //9390/1 is following 9390/0 Single primary
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("choroid"));

        // M7 - A neurofibromatosis, NOS (9540/1) following a neurofibroma, NOS (9540/0) is a single primary.
        i1.setHistologyIcdO3("9540");
        i2.setHistologyIcdO3("9540");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("neurofibroma"));

        // M8 - Tumors with two or more histologic types on the same branch in Chart 1 are a single primary.
        i1.setHistologyIcdO3("9383");
        i2.setHistologyIcdO3("9444");
        i1.setBehaviorIcdO3("1");
        i2.setBehaviorIcdO3("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Chart 1"));

        // M9 - Tumors with multiple histologic types on different branches in Chart 1 are multiple primaries.
        i1.setPrimarySite("C720");
        i2.setPrimarySite("C720");
        i1.setHistologyIcdO3("9383");
        i2.setHistologyIcdO3("9562");
        i1.setBehaviorIcdO3("1");
        i2.setBehaviorIcdO3("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(7, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Chart 1"));

        // M10 - Tumors with two or more histologic types and at least one of the histologies is not listed in Chart 1 are multiple primaries.
        i1.setPrimarySite("C720");
        i2.setPrimarySite("C720");
        i1.setHistologyIcdO3("9383");
        i2.setHistologyIcdO3("9562");
        i1.setBehaviorIcdO3("0");
        i2.setBehaviorIcdO3("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(8, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Chart 1"));

        //M11- Tumors with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.
        i1.setPrimarySite("C720");
        i2.setPrimarySite("C720");
        i1.setHistologyIcdO3("8740");
        i2.setHistologyIcdO3("8730");
        i1.setBehaviorIcdO3("1");
        i2.setBehaviorIcdO3("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(9, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("histology"));

        //M12- Tumors that do not meet any of the criteria are abstracted as a single primary.
        i1.setPrimarySite("C720");
        i2.setPrimarySite("C720");
        i1.setHistologyIcdO3("8746");
        i2.setHistologyIcdO3("8740");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(10, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("criteria"));
    }

    @Test
    public void test2007Breast() {

        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;
        // M4- Tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx) and/or third (Cx?x) character are multiple primaries.
        //This never happens. Breast is C500-C509,
        //M5- Tumors diagnosed more than five (5) years apart are multiple primaries.
        i1.setPrimarySite("C500");
        i1.setHistologyIcdO3("8720");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8780");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2009");
        i2.setDateOfDiagnosisYear("2014");
        output = _utils.computePrimaries(i1, i2);
        //Questionable at M5 with potential multiple, questionable at M7 with potential multiple, ended up as MULTIPLE at M12
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(9, output.getAppliedRules().size());
        Assert.assertEquals("M12", output.getStep());
        i2.setDateOfDiagnosisYear("2015");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(2, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("five"));

        //M6- Inflammatory carcinoma in one or both breasts is a single primary. (8530/3)
        i1.setPrimarySite("C500");
        i1.setHistologyIcdO3("8530");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8530");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2010");
        i2.setDateOfDiagnosisYear("2010");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("carcinoma"));

        //M7- Tumors on both sides (right and left breast) are multiple primaries.
        i1.setPrimarySite("C500");
        i1.setHistologyIcdO3("8530");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8730");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2010");
        i2.setDateOfDiagnosisYear("2010");
        i1.setLaterality("1");
        i2.setLaterality("9");
        output = _utils.computePrimaries(i1, i2);
        //Questionable at M7 with potential multiple primary and ended up as Multiple at M12
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(9, output.getAppliedRules().size());
        Assert.assertEquals("M12", output.getStep());
        //Questionable at M7 with potential multiple primary and ended up as Single at M13 - QUESTIONABLE
        i1.setHistologyIcdO3("8730");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertEquals("M7", output.getStep());
        //Multiple at M7
        i2.setLaterality("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("both sides"));

        //M8- An invasive tumor following an in situ tumor more than 60 days after diagnosis are multiple primaries.
        i1.setPrimarySite("C500");
        i1.setHistologyIcdO3("8530");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8530");
        i2.setBehaviorIcdO3("2");
        i1.setLaterality("1");
        i2.setLaterality("1");
        i1.setDateOfDiagnosisYear("2009");
        i2.setDateOfDiagnosisYear("2009");
        output = _utils.computePrimaries(i1, i2);
        //Questionable at M8 with potential multiple, and ended up as Single at M13 -- QUESTIONABLE
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("There is not enough diagnosis date information.")); //not sure if they are 60 days apart
        i2.setDateOfDiagnosisYear("2007");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("60 days"));

        //M9- Tumors that are intraductal or duct and Paget Disease are a single primary.
        i1.setDateOfDiagnosisYear("2008");
        i1.setBehaviorIcdO3("2");
        i1.setHistologyIcdO3("8401"); //intraductal
        i2.setDateOfDiagnosisYear("2007");
        i2.setBehaviorIcdO3("3");
        i2.setHistologyIcdO3("8542"); //paget
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Paget"));

        //M10- Tumors that are lobular (8520) and intraductal or duct are a single primary.
        i2.setHistologyIcdO3("8520"); //lobular
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(7, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("lobular"));

        //M11- Multiple intraductal and/or duct carcinomas are a single primary.
        i2.setHistologyIcdO3("8500"); //duct
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(8, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("duct"));
        i2.setHistologyIcdO3("8230"); //another intraductal
        i2.setBehaviorIcdO3("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(8, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("duct"));

        //M12- Tumors with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        i1.setHistologyIcdO3("8500");
        i2.setHistologyIcdO3("8510");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(9, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("histology"));

        //M13- Tumors that do not meet any of the criteria are abstracted as a single primary.
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        i1.setHistologyIcdO3("8506");
        i2.setHistologyIcdO3("8508");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(10, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("criteria"));
    }

    @Test
    public void test2007Colon() {
        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;
        // M3 - Adenocarcinoma in adenomatous polyposis coli (familial polyposis) with one or more malignant polyps is a single primary.
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8220");
        i1.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2015");
        i2.setPrimarySite("C180");
        i2.setHistologyIcdO3("8262");
        i2.setBehaviorIcdO3("2");
        i2.setDateOfDiagnosisYear("2015");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(1, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("polyps"));
        i1.setBehaviorIcdO3("2"); //at least one should be malignant
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(1, output.getAppliedRules().size());
        i2.setBehaviorIcdO3("3");
        i2.setHistologyIcdO3("8220"); //polyp
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(1, output.getAppliedRules().size());
        i2.setPrimarySite("C185"); //not same site, no problem as long as it is colon
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(1, output.getAppliedRules().size());
        i2.setHistologyIcdO3("8265"); // not in polyp group, continue to the next rule
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(1, output.getAppliedRules().size());

        //M4- Tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx), third (Cx?x) and/or fourth (C18?) character are multiple primaries.
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8220");
        i1.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2015");
        i2.setPrimarySite("C185");
        i2.setHistologyIcdO3("8265");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2015");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(2, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("topography"));
        i2.setPrimarySite("C180"); //not different, continue to the next step
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(2, output.getAppliedRules().size());

        //M5- Tumors diagnosed more than one (1) year apart are multiple primaries.
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8220");
        i1.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2015");
        i2.setPrimarySite("C180");
        i2.setHistologyIcdO3("8265");
        i2.setBehaviorIcdO3("3");
        i2.setDateOfDiagnosisYear("2013"); //definitely more than a year apart
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("one"));
        i2.setDateOfDiagnosisYear("2014"); //not enough information
        //Questionable at M5 with potential multiple and definite multiple at M10-- MULTIPLE
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(8, output.getAppliedRules().size());
        Assert.assertEquals("M10", output.getStep());
        i2.setDateOfDiagnosisYear("2015"); //definitely less than a year apart, continue to next step
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(3, output.getAppliedRules().size());

        //M6- An invasive tumor following an insitu tumor more than 60 days after diagnosis is a multiple primary.
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8000");
        i1.setBehaviorIcdO3("2");
        i2.setPrimarySite("C180");
        i2.setHistologyIcdO3("8000");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisYear("2015");
        i1.setDateOfDiagnosisMonth("01");
        i2.setDateOfDiagnosisMonth("04"); // Not sure if they are 60 days apart
        //Questionable at M6 with potential multiple ended up as SINGLE at M13 -- Questionable
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("M6"));
        i1.setDateOfDiagnosisMonth("02");
        i2.setDateOfDiagnosisMonth("05"); //April + May. definitely greater than 60 days
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("60"));
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("2"); //insitu is following invasive, continue to next step
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(4, output.getAppliedRules().size());

        //M7- A frank malignant or in situ adenocarcinoma and an insitu or malignant tumor in a polyp are a single primary.
        i1.setHistologyIcdO3("8220");
        i2.setHistologyIcdO3("8003");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("adenocarcinoma"));
        i1.setHistologyIcdO3("8222"); //not polyp or adenocarcinoma, continue to next step
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(5, output.getAppliedRules().size());

        //M8 NOS vs Specific
        i1.setHistologyIcdO3("8140");
        i2.setHistologyIcdO3("8148");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("NOS"));
        i1.setHistologyIcdO3("8130"); //not in the NOS list
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(6, output.getAppliedRules().size());

        //M9- Multiple insitu and/or malignant polyps are a single primary.
        i1.setHistologyIcdO3("8213");
        i2.setHistologyIcdO3("8213");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(7, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("polyps"));
        i2.setHistologyIcdO3("8265");//not polyp, continue to next step
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(7, output.getAppliedRules().size());

        //M10- Tumors with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.
        i1.setHistologyIcdO3("8213");
        i2.setHistologyIcdO3("8265");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(8, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("histology"));
        i1.setHistologyIcdO3("8265");
        i2.setHistologyIcdO3("8265"); //not different in histology, continue to next step
        //M11- Tumors that do not meet any of the criteria are abstracted as a single primary.
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(9, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("criteria"));
    }

    @Test
    public void test2007HeadAndNeck() {

        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;

        // M3 - Tumors on the right side and the left side of a paired site are multiple primaries.
        i1.setPrimarySite("C090");
        i1.setHistologyIcdO3("8000");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i2.setPrimarySite("C098");
        i2.setHistologyIcdO3("8100");
        i2.setBehaviorIcdO3("2");
        i2.setLaterality("9");
        i1.setDateOfDiagnosisYear("2000");
        i2.setDateOfDiagnosisYear("2015");
        output = _utils.computePrimaries(i1, i2);
        //Questionable at M3 with potential Multiple and ended up as Multiple at M9 -- MULTIPLE
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(7, output.getAppliedRules().size());
        Assert.assertEquals("M9", output.getStep());
        i2.setLaterality("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(1, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("paired"));
        Assert.assertEquals("M3", output.getStep());

        //M4- Tumors on the upper lip (C000 or C003) and the lower lip (C001 or C004) are multiple primaries.
        i1 = new MphInput();
        i2 = new MphInput();
        i1.setPrimarySite("C000");
        i1.setHistologyIcdO3("8000");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C001");
        i2.setHistologyIcdO3("8100");
        i2.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisYear("2015");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(2, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("lip"));

        //M5- Tumors on the upper gum (C030) and the lower gum (C031) are multiple primaries.
        i1.setPrimarySite("C030");
        i2.setPrimarySite("C031");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("gum"));

        //M6- Tumors in the nasal cavity (C300) and the middle ear (C301) are multiple primaries.
        i1.setPrimarySite("C300");
        i2.setPrimarySite("C301");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("nasal"));

        //M7- Tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx) and/or third (Cx?x) character are multiple primaries.
        i1.setPrimarySite("C000");
        i2.setPrimarySite("C148");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("topography"));
        i1.setPrimarySite("C138");
        i2.setPrimarySite("C148");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("topography"));

        //M8- An invasive tumor following an insitu tumor more than 60 days after diagnosis are multiple primaries.
        i1.setPrimarySite("C147");
        i1.setHistologyIcdO3("8005");
        i2.setPrimarySite("C148");
        i2.setHistologyIcdO3("8100");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2011");
        i2.setDateOfDiagnosisYear("2010");
        i1.setDateOfDiagnosisMonth(null);
        i2.setDateOfDiagnosisMonth("7");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("invasive"));
        //Questionable at M8 with potential multiple and ended up as multiple at M11 -- MULTIPLE
        i2.setDateOfDiagnosisMonth("11");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(9, output.getAppliedRules().size());
        Assert.assertEquals("M11", output.getStep());

        //M9- Tumors diagnosed more than five (5) years apart are multiple primaries.
        i1 = new MphInput();
        i2 = new MphInput();
        i1.setPrimarySite("C147");
        i2.setPrimarySite("C148");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        i1.setHistologyIcdO3("8000");
        i2.setHistologyIcdO3("8000");
        i1.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisYear("2009");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(7, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("five"));
        i2.setDateOfDiagnosisYear("2010");
        //Questionable at M9 with potential multiple and ended up as single at M12 -- QUESTIONABLE
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(7, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("M9"));

        //M10 -
        i1 = new MphInput();
        i2 = new MphInput();
        i1.setPrimarySite("C147");
        i2.setPrimarySite("C148");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        i1.setHistologyIcdO3("8000");
        i2.setHistologyIcdO3("9500");
        i1.setDateOfDiagnosisYear("2013");
        i2.setDateOfDiagnosisYear("2010");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(8, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("NOS"));
        i1.setHistologyIcdO3("8070");
        i2.setHistologyIcdO3("8323");
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(8, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("NOS"));

        //M11- Tumors with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.
        i1 = new MphInput();
        i2 = new MphInput();
        i1.setPrimarySite("C147");
        i2.setPrimarySite("C148");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        i1.setHistologyIcdO3("8900");
        i2.setHistologyIcdO3("8910");
        i1.setDateOfDiagnosisYear("2013");
        i2.setDateOfDiagnosisYear("2010");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(9, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("histology"));

        //M12- Tumors that do not meet any of the criteria are abstracted as a single primary.
        i2.setHistologyIcdO3("8904");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(10, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("criteria"));
    }

    @Test
    public void test2007Kidney() {
        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;

        // M3 - Wilms tumors are a single primary. (8960/3)
        i1.setPrimarySite("C649");
        i1.setHistologyIcdO3("8960");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C649");
        i2.setHistologyIcdO3("8960");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2000");
        i2.setDateOfDiagnosisYear("2015");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(1, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Wilms"));

        // M4 - Tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx) and/or third (Cx?x) character are multiple primaries.
        // This will never happen since all kidney tumors are C649

        // M5 - Tumors in both the right kidney and in the left kidney are multiple primaries.
        i1.setPrimarySite("C649");
        i1.setHistologyIcdO3("8060");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2015");
        i2.setPrimarySite("C649");
        i2.setHistologyIcdO3("8960");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("2");
        i2.setDateOfDiagnosisYear("2015");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("both the right kidney and in the left"));
        //Questionable at M5, M6 and M7 with potential multiple and ended up as Multiple at M10
        i2.setLaterality("9");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(8, output.getAppliedRules().size());
        Assert.assertEquals("M10", output.getStep());
        //Questionable at M5, M6 and M7 with potential multiple and ended up as single at M9 -- QUESTIONABLE
        i1.setHistologyIcdO3("8000");
        i2.setHistologyIcdO3("8001");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertEquals("M5", output.getStep());

        // M6 - Tumors diagnosed more than three (3) years apart are multiple primaries.
        i1.setHistologyIcdO3("8060");
        i1.setBehaviorIcdO3("3");
        i2.setHistologyIcdO3("8960");
        i2.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i2.setLaterality("1");
        i1.setDateOfDiagnosisYear("2009");
        i1.setDateOfDiagnosisMonth("1");
        i2.setDateOfDiagnosisYear("2012");
        i2.setDateOfDiagnosisMonth("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("three"));

        // M7 - An invasive tumor following an in situ tumor more than 60 days after diagnosis are multiple primaries.
        i1.setHistologyIcdO3("8060");
        i1.setBehaviorIcdO3("2");
        i2.setHistologyIcdO3("8960");
        i2.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i2.setLaterality("1");
        i1.setDateOfDiagnosisYear("2009");
        i1.setDateOfDiagnosisMonth("1");
        i2.setDateOfDiagnosisYear("2011");
        i2.setDateOfDiagnosisMonth("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("invasive"));

        // M8 - One tumor with a specific renal cell type and another tumor with a different specific renal cell type are multiple primaries (table 1 in pdf).
        i1.setHistologyIcdO3("8510"); //Medullary carcinoma
        i1.setBehaviorIcdO3("3");
        i2.setHistologyIcdO3("8260"); //Papillary (Chromophil)
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("renal cell type"));

        // M9
        i1.setHistologyIcdO3("8312");
        i2.setHistologyIcdO3("8317");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(7, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("NOS"));

        // M10- Tumors with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.
        i1.setHistologyIcdO3("8312");
        i2.setHistologyIcdO3("8370");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(8, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("histology"));

        //M11- Tumors that do not meet any of the criteria are abstracted as a single primary.
        i1.setHistologyIcdO3("8300");
        i2.setHistologyIcdO3("8305");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(9, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("criteria"));
    }

    @Test
    public void test2007Lung() {

        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;

        // M3- Tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx) and/or third (Cx?x) character are multiple primaries.
        //This will never be true, lung group is C340-C349, 2nd and 3rd characters are always the same.

        //M4- At least one tumor that is non-small cell carcinoma (8046) and another tumor that is small cell carcinoma (8041-8045) are multiple primaries.
        i1.setPrimarySite("C342");
        i1.setHistologyIcdO3("8046");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C349");
        i2.setHistologyIcdO3("8043");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2000");
        i2.setDateOfDiagnosisYear("2015");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(2, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("carcinoma"));

        //M5- A tumor that is adenocarcinoma with mixed subtypes (8255) and another that is bronchioloalveolar (8250-8254) are multiple primaries.
        i1.setPrimarySite("C342");
        i1.setHistologyIcdO3("8253");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C349");
        i2.setHistologyIcdO3("8255");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2000");
        i2.setDateOfDiagnosisYear("2015");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("adenocarcinoma"));

        //M6- A single tumor in each lung is multiple primaries.
        i1.setPrimarySite("C342");
        i1.setHistologyIcdO3("8155");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C349");
        i2.setHistologyIcdO3("8153");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2000");
        i2.setDateOfDiagnosisYear("2015");
        i1.setLaterality("2");
        i2.setLaterality("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("each lung"));
        i1.setLaterality("9");
        output = _utils.computePrimaries(i1, i2);
        //Questionable at M6 with potential multiple and ended up as multiple at M8 -- MULTIPLE
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertEquals("M8", output.getStep());
        i1.setLaterality("4");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertTrue(output.getAppliedRules().size() > 4);

        //M7- Multiple tumors in both lungs with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.
        i1.setHistologyIcdO3("8150");
        i2.setHistologyIcdO3("8165");
        i1.setLaterality(null);
        i2.setLaterality("4");
        i1.setPrimarySite("C342");
        i2.setPrimarySite("C349");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("histology"));
        //If one laterality is both, that means the tumors are on both lungs
        i1.setLaterality("4");
        i2.setLaterality("9");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("histology"));
        //if they are on the same lung, dont apply this rule
        i1.setLaterality("1");
        i2.setLaterality("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertTrue(output.getAppliedRules().size() > 5);

        //M8- Tumors diagnosed more than three (3) years apart are multiple primaries.
        i1.setHistologyIcdO3("8160");
        i2.setHistologyIcdO3("8165");
        i1.setDateOfDiagnosisYear("2013");
        i1.setDateOfDiagnosisMonth("09");
        i2.setDateOfDiagnosisYear("2010");
        i2.setDateOfDiagnosisMonth("08");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("years"));
        i2.setDateOfDiagnosisMonth("09");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(6, output.getAppliedRules().size());

        //M9- An invasive tumor following an in situ tumor more than 60 days after diagnosis are multiple primaries.
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2011");
        i2.setDateOfDiagnosisYear("2010");
        i2.setDateOfDiagnosisMonth("7");
        i1.setDateOfDiagnosisMonth(null);
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(7, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("invasive"));
        i2.setDateOfDiagnosisMonth("11");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(7, output.getAppliedRules().size());

        //M10- Tumors with non-small cell carcinoma, NOS (8046) and a more specific non-small cell carcinoma type (chart 1) are a single primary.
        i2.setBehaviorIcdO3("3");
        i1.setHistologyIcdO3("8046");
        i2.setHistologyIcdO3("8310");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(8, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("8046"));

        //M11- Tumors with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.
        i1.setHistologyIcdO3("8046");
        i2.setHistologyIcdO3("8021");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(9, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("histology codes"));

        //M12- Tumors that do not meet any of the criteria are abstracted as a single primary.
        i1.setHistologyIcdO3("8045");
        i2.setHistologyIcdO3("8041");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(10, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("criteria"));
    }

    @Test
    public void test2007MalignantBrain() {

        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;
        // M4 - An invasive brain tumor (/3) and either a benign brain tumor (/0) or an uncertain/borderline brain tumor (/1) are always multiple primaries.
        //This will never happen, since the two conditions belong to different cancer group.

        // M5- Tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx) and/or third (Cx?x) character are multiple primaries.
        i1.setPrimarySite("C700");
        i2.setPrimarySite("C725");
        i1.setHistologyIcdO3("8050");
        i2.setHistologyIcdO3("8123");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2000");
        i2.setDateOfDiagnosisYear("2015");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(2, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("topography"));

        // M6 - A glioblastoma or glioblastoma multiforme (9440) following a glial tumor is a single primary.
        i1.setPrimarySite("C710");
        i2.setPrimarySite("C714");
        i1.setHistologyIcdO3("9440");
        i2.setHistologyIcdO3("9380");
        i1.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisYear("2015");
        output = _utils.computePrimaries(i1, i2);
        // can't tell which tumor follows which
        //Questionable at M6 as potential single and ended up as SINGLE at M7 -- SINGLE
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertEquals("M7", output.getStep());

        i1.setDateOfDiagnosisYear("2016"); //i1, 9440 is following glial
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("glial"));
        i1.setDateOfDiagnosisYear("2014"); //glial is following i1 (9440), continue to the next step
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(3, output.getAppliedRules().size());

        // M7 - Tumors with ICD-O-3 histology codes on the same branch in Chart 1 or Chart 2 are a single primary.
        i1.setPrimarySite("C710");
        i2.setPrimarySite("C714");
        i1.setHistologyIcdO3("9380");
        i2.setHistologyIcdO3("9411");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Chart 1"));
        i1.setHistologyIcdO3("9503");
        i2.setHistologyIcdO3("9410");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Chart 1"));
        i1.setHistologyIcdO3("9100");
        i2.setHistologyIcdO3("9071");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Chart 2"));
        i1.setHistologyIcdO3("9392");
        i2.setHistologyIcdO3("9501");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(4, output.getAppliedRules().size());
        i1.setHistologyIcdO3("9392");
        i2.setHistologyIcdO3("9391");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());

        // M8 - Tumors with ICD-O-3 histology codes on different branches in Chart 1 or Chart 2 are multiple primaries.
        i1.setHistologyIcdO3("9505");
        i2.setHistologyIcdO3("9523");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Chart 1"));
        i1.setHistologyIcdO3("9420");
        i2.setHistologyIcdO3("9421");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Chart 2"));
        i1.setHistologyIcdO3("9392");
        i2.setHistologyIcdO3("9392");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        i1.setHistologyIcdO3("9392");
        i2.setHistologyIcdO3("9505");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());

        // M9- Tumors with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.
        i1.setHistologyIcdO3("8230");
        i2.setHistologyIcdO3("8240");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("histology"));

        // M10- Tumors that do not meet any of the criteria are abstracted as a single primary.
        i1.setHistologyIcdO3("8230");
        i2.setHistologyIcdO3("8235");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(7, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("criteria"));
        i1.setHistologyIcdO3("9397");
        i2.setHistologyIcdO3("9397");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(7, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("criteria"));
    }

    @Test
    public void test2007Melanoma() {

        /*
        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;

        //M3- Melanomas in sites with ICD-O-3 topography codes that are different at the second (C?xx), third (Cx?x) or fourth (C44?) character are multiple primaries.
        i1.setPrimarySite("C442");
        i1.setHistologyIcdO3("8720");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C447");
        i2.setHistologyIcdO3("8780");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2000");
        i2.setDateOfDiagnosisYear("2015");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(1, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("topography"));

        //M4- Melanomas with different laterality are multiple primaries.
        i1.setPrimarySite("C442");
        i1.setHistologyIcdO3("8720");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C442");
        i2.setHistologyIcdO3("8780");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2000");
        i2.setDateOfDiagnosisYear("2015");
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

        //M5- Melanomas with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.
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

        //M6- An invasive melanoma that occurs more than 60 days after an in situ melanoma is a multiple primary.
        i1.setPrimarySite("C442");
        i2.setPrimarySite("C442");
        i1.setHistologyIcdO3("8725");
        i2.setHistologyIcdO3("8720");
        i1.setBehaviorIcdO3("2");
        i2.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i2.setLaterality("1");
        i1.setDateOfDiagnosisYear("2009");
        i2.setDateOfDiagnosisYear("2009"); // same year no month information
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("M6"));
        i2.setDateOfDiagnosisYear("2011"); // invasive on 2006, insitu on 2004
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("invasive"));

        //M7- Melanomas diagnosed more than 60 days apart are multiple primaries.
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("60"));

        //M8- Melanomas that do not meet any of the above criteria are abstracted as a single primary.
        i1.setDateOfDiagnosisYear("2011");
        i2.setDateOfDiagnosisYear("2011");
        i1.setDateOfDiagnosisMonth("01");
        i2.setDateOfDiagnosisMonth("01");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("criteria"));
        */
    }

    @Test
    public void test2007OtherSites() {
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
        i2.setHistologyIcdO3("8001");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("ovary"));
        i2.setHistologyIcdO3("8002");
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
    public void test2007Urinary() {

        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;

        // M3 - When no other urinary sites are involved, tumor(s) in the right renal pelvis AND tumor(s) in the left renal pelvis are multiple primaries. (C659)
        i1.setPrimarySite("C659");
        i2.setPrimarySite("C659");
        i1.setHistologyIcdO3("8720");
        i2.setHistologyIcdO3("8780");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i2.setLaterality("2");
        i1.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisYear("2015");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(1, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("renal"));

        // M4 - When no other urinary sites are involved, tumor(s) in both the right ureter AND tumor(s) in the left ureter are multiple primaries. (C669)
        i1.setPrimarySite("C669");
        i2.setPrimarySite("C669");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(2, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("ureter"));

        // M5- An invasive tumor following an in situ tumor more than 60 days after diagnosis are multiple primaries.
        i1.setPrimarySite("C659");
        i2.setPrimarySite("C679");
        i1.setHistologyIcdO3("8000");
        i2.setHistologyIcdO3("8000");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2007");
        i2.setDateOfDiagnosisYear("2007");
        i1.setLaterality("9");
        i2.setLaterality("9");
        output = _utils.computePrimaries(i1, i2);
        //Questionable at M5 and M7 with potential multiple and ended up as multiple at M10
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(8, output.getAppliedRules().size());
        Assert.assertEquals("M10", output.getStep());
        //Questionable at M5 and M7 with potential multiple and ended up as single at M8 -- Questionable
        i1.setHistologyIcdO3("8120");
        i2.setHistologyIcdO3("8120");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertEquals("M5", output.getStep());

        //Multiple at M5
        i1.setDateOfDiagnosisMonth("05");
        i2.setDateOfDiagnosisMonth("01");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("invasive"));

        // M6 - Bladder tumors with any combination of the following histologies: papillary carcinoma (8050), transitional cell carcinoma (8120-8124),
        // or papillary transitional cell carcinoma (8130-8131), are a single primary.
        i1.setPrimarySite("C672");
        i2.setPrimarySite("C679");
        i1.setHistologyIcdO3("8050");
        i2.setHistologyIcdO3("8123");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Bladder"));

        // M7 - Tumors diagnosed more than three (3) years apart are multiple primaries.
        i1.setPrimarySite("C659");
        i2.setPrimarySite("C679");
        i1.setHistologyIcdO3("8720");
        i2.setHistologyIcdO3("8180");
        i1.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisYear("2001");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("three"));

        // M8 - Urothelial tumors in two or more of the following sites are a single primary* (See Table 1 of pdf)
        // Renal pelvis (C659), Ureter(C669), Bladder (C670-C679), Urethra /prostatic urethra (C680)
        i1.setPrimarySite("C659");
        i2.setPrimarySite("C680");
        i1.setHistologyIcdO3("8131");
        i2.setHistologyIcdO3("8020");
        i1.setDateOfDiagnosisYear("2008");
        i2.setDateOfDiagnosisYear("2007");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Urothelial"));

        // M9- Tumors with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.
        i1.setPrimarySite("C659");
        i2.setPrimarySite("C680");
        i1.setHistologyIcdO3("8130");
        i2.setHistologyIcdO3("8150");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(7, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("histology"));

        // M10- Tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx) and/or third (Cx?x) character are multiple primaries.
        i1.setPrimarySite("C659");
        i2.setPrimarySite("C680");
        i1.setHistologyIcdO3("8630");
        i2.setHistologyIcdO3("8630");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(8, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("topography"));

        // M11- Tumors that do not meet any of the criteria are abstracted as a single primary.
        i1.setPrimarySite("C670");
        i2.setPrimarySite("C675");
        i1.setHistologyIcdO3("8630");
        i2.setHistologyIcdO3("8630");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(9, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("criteria"));
    }

    @Test
    public void test1998Hematopoietic() {
        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;
        i1.setPrimarySite("C001");
        i2.setPrimarySite("C425");
        i1.setHistologyIcdO3("9594");
        i2.setHistologyIcdO3("9803");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("1990");
        i2.setDateOfDiagnosisYear("2000");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        //if year is before 2001, if icd02 is valid, use it
        i1.setHistologyIcdO2("9594");
        i2.setHistologyIcdO2("9900");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        i1.setBehaviorIcdO2("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        i1.setBehaviorIcdO2("3");
        i1.setHistologyIcdO2("9740");
        i2.setHistologyIcdO2("9801");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        i1.setHistologyIcdO2("9661");
        i2.setHistologyIcdO2("9590");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        i1.setHistologyIcdO2("9667");
        i2.setHistologyIcdO2("9850");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        i1.setHistologyIcdO2("9590");
        i2.setHistologyIcdO2("9686");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        i1.setHistologyIcdO2("9590");
        i2.setHistologyIcdO2("9687");
        output = _utils.computePrimaries(i1, i2); //9687 following 9590 is multiple primary
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        i1.setDateOfDiagnosisYear("2000");
        i2.setDateOfDiagnosisYear("2000");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        i1.setDateOfDiagnosisMonth("02");
        i2.setDateOfDiagnosisMonth("01");
        output = _utils.computePrimaries(i1, i2); //9590 following 9687 is single primary
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        i1.setDateOfDiagnosisMonth("01");
        i2.setDateOfDiagnosisMonth("01");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        i1.setDateOfDiagnosisDay("19");
        i2.setDateOfDiagnosisDay("20");
        output = _utils.computePrimaries(i1, i2); //9687 following 9590 is multiple primary
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        i1.setDateOfDiagnosisDay("20");
        i2.setDateOfDiagnosisDay("20");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        i1.setHistologyIcdO2("9687");
        i2.setHistologyIcdO2("9590");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());

    }

    @Test
    public void test2001Hematopoietic() {
        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;
        i1.setPrimarySite("C001");
        i2.setPrimarySite("C425");
        i1.setHistologyIcdO3("9590");
        i2.setHistologyIcdO3("9940"); //9940 after 9590 is single
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2001");
        i2.setDateOfDiagnosisYear("2009");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        i1.setDateOfDiagnosisYear("2001");
        i2.setDateOfDiagnosisYear("2000"); //9590 after 9940 is multiple
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        i1.setDateOfDiagnosisYear("2000");
        i2.setDateOfDiagnosisYear("2000");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        i1.setDateOfDiagnosisYear("1990");
        i2.setDateOfDiagnosisYear("2005");
        i1.setHistologyIcdO3("9836");
        i2.setHistologyIcdO3("9833");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        i1.setHistologyIcdO3("9963");
        i2.setHistologyIcdO3("9844");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        i1.setHistologyIcdO3("9909");
        i2.setHistologyIcdO3("9940");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        i1.setHistologyIcdO3("9909");
        i2.setHistologyIcdO3("9945");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
    }

    @Test
    public void test2010Hematopoietic() {
        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;

        //M1 TODO

        //M2
        i1.setPrimarySite("C779");
        i2.setPrimarySite("C189");
        i1.setHistologyIcdO3("9740");
        i2.setHistologyIcdO3("9740");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisYear("2010");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(2, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("single histology"));
        //Exception
        i1.setHistologyIcdO3("9699");
        i2.setHistologyIcdO3("9699");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(2, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("single histology"));
        i1.setPrimarySite("C779");
        i2.setPrimarySite("C770");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(2, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("single histology"));

        //M3 Abstract a single primary* when a sarcoma is diagnosed simultaneously or after a leukemia of the same lineage
        i1.setPrimarySite("C408");
        i2.setPrimarySite("C189");
        i1.setHistologyIcdO3("9740"); //9740 after 9742
        i2.setHistologyIcdO3("9742");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisYear("2010");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("sarcoma"));
        i1.setHistologyIcdO3("9930"); //9930 after 9866
        i2.setHistologyIcdO3("9866");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(3, output.getAppliedRules().size());
        i1.setDateOfDiagnosisYear("2010");
        i2.setDateOfDiagnosisYear("2015"); //9930 before 9866
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(3, output.getAppliedRules().size());
        i1.setDateOfDiagnosisYear("2010");
        i2.setDateOfDiagnosisYear("2010");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(3, output.getAppliedRules().size());
        i1.setDateOfDiagnosisMonth("01");
        i2.setDateOfDiagnosisMonth("01");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(3, output.getAppliedRules().size());
        i1.setDateOfDiagnosisDay("15");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(3, output.getAppliedRules().size());

        //M4 Abstract a single primary when two or more types of non-Hodgkin lymphoma are simultaneously present in the same anatomic location(s), such
        //as the same lymph node or lymph node region(s), the same organ(s), and/or the same tissue(s).
        i1 = new MphInput();
        i2 = new MphInput();
        i1.setPrimarySite("C771");
        i2.setPrimarySite("C771");
        i1.setHistologyIcdO3("9590");
        i2.setHistologyIcdO3("9738");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2010");
        i2.setDateOfDiagnosisYear("2010");
        i1.setDateOfDiagnosisMonth("01");
        i2.setDateOfDiagnosisMonth("01");
        i1.setDateOfDiagnosisDay("08");
        i2.setDateOfDiagnosisDay("20");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("non-Hodgkin"));
        i2.setDateOfDiagnosisYear("2011"); //not simultaneous
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(4, output.getAppliedRules().size());

        //M5 Abstract a single primary* when both Hodgkin and non-Hodgkin lymphoma are simultaneously present in the same anatomic location(s), such as
        //the same lymph node or same lymph node region(s), the same organ(s), and/or the same tissue(s).
        i1 = new MphInput();
        i2 = new MphInput();
        i1.setPrimarySite("C771");
        i2.setPrimarySite("C771");
        i1.setHistologyIcdO3("9653");
        i2.setHistologyIcdO3("9738");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2010");
        i2.setDateOfDiagnosisYear("2010");
        i1.setDateOfDiagnosisMonth("01");
        i2.setDateOfDiagnosisMonth("01");
        i1.setDateOfDiagnosisDay("08");
        i2.setDateOfDiagnosisDay("20");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Hodgkin"));

        //M6 Abstract as multiple primaries** when Hodgkin lymphoma is diagnosed in one anatomic location and non-Hodgkin lymphoma is diagnosed in
        //another anatomic location.
        i1 = new MphInput();
        i2 = new MphInput();
        i1.setPrimarySite("C771");
        i2.setPrimarySite("C772");
        i1.setHistologyIcdO3("9653");
        i2.setHistologyIcdO3("9700");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2010");
        i2.setDateOfDiagnosisYear("2010");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("another anatomic location"));
        i1.setPrimarySite("C421");
        i2.setPrimarySite("C422");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(6, output.getAppliedRules().size());

        //M7 Abstract as a single primary* when a more specific histology is diagnosed after an NOS ONLY when the Heme DB Multiple Primaries Calculator
        //confirms that the NOS and the more specific histology are the same primary.
        //This is Skipped on the automated process


        //M8 TODO

        //M9 TODO

        //M10 Abstract as multiple primaries** when a neoplasm is originally diagnosed as a chronic neoplasm AND there is a second diagnosis of an acute
        //neoplasm more than 21 days after the chronic diagnosis.
        i1 = new MphInput();
        i2 = new MphInput();
        i1.setPrimarySite("C001");
        i2.setPrimarySite("C772");
        i1.setHistologyIcdO3("9875");
        i2.setHistologyIcdO3("9767"); //No transformation
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2010");
        i2.setDateOfDiagnosisYear("2010");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(10, output.getAppliedRules().size());
        i1.setHistologyIcdO3("9875");
        i2.setHistologyIcdO3("9867");//9875 (chronic) transforms to 9867(acute)
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(8, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        i1.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisYear("2010"); // acute was diagnosed before chronic
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(10, output.getAppliedRules().size());
        i1.setDateOfDiagnosisYear("2010");
        i2.setDateOfDiagnosisYear("2015"); // acute after chronic
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(10, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        //Exception for plasmacytoma (9731, 9734) and plasma cell myeloma (9732)
        i1.setHistologyIcdO3("9731");
        i2.setHistologyIcdO3("9732");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(10, output.getAppliedRules().size());
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());

        //M11 TODO

        //M12 Abstract a single primary* when a neoplasm is originally diagnosed as acute AND reverts to a chronic neoplasm AND there is no confirmation
        //available that the patient has been treated for the acute neoplasm.
        //M12 and M13 are returning manual review
        i1 = new MphInput();
        i2 = new MphInput();
        i1.setPrimarySite("C001");
        i2.setPrimarySite("C772");
        i1.setHistologyIcdO3("9867");
        i2.setHistologyIcdO3("9875"); //i1 acute, i2 chronic
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2010");
        i2.setDateOfDiagnosisYear("2015"); //acute before chrnoic
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(12, output.getAppliedRules().size());
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());

        //M13 Abstract multiple primaries** when a neoplasm is originally diagnosed as acute AND reverts to a chronic neoplasm after treatment
        i1.setTxStatus("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(12, output.getAppliedRules().size());
        Assert.assertEquals(MpResult.QUESTIONABLE, output.getResult());

        //M14 Abstract a single primary* when post-transplant lymphoproliferative disorder is diagnosed simultaneously with any B-cell lymphoma, T-cell
        //lymphoma, Hodgkin lymphoma or plasmacytoma/myeloma
        i1 = new MphInput();
        i2 = new MphInput();
        i1.setPrimarySite("C001");
        i2.setPrimarySite("C772");
        i1.setHistologyIcdO3("9971"); //ptld
        i2.setHistologyIcdO3("9684"); //Bcell
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2010");
        i2.setDateOfDiagnosisYear("2010");
        i1.setDateOfDiagnosisMonth("01");
        i2.setDateOfDiagnosisMonth("01");
        i1.setDateOfDiagnosisDay("20");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(14, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        i2.setHistologyIcdO3("9718"); //Tcell
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(14, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        i2.setHistologyIcdO3("9653"); //Hodgkin
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(14, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        i2.setHistologyIcdO3("9732"); //plasmacytoma
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(14, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        i2.setDateOfDiagnosisMonth("02"); //unknown if they are simultaneous
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(14, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        i2.setDateOfDiagnosisMonth("03"); //definitely not simultaneous
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(14, output.getAppliedRules().size());

        //M15 Use the Heme DB Multiple Primaries Calculator to determine the number of primaries
        Assert.assertEquals(15, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());//9732 and 9971 are not same primaries
        i1.setHistologyIcdO3("9732");
        i2.setHistologyIcdO3("9733");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(15, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        i1.setHistologyIcdO3("9801");
        i2.setHistologyIcdO3("9837");
        i1.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisYear("2001");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(15, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        i1.setHistologyIcdO3("9801");
        i2.setHistologyIcdO3("9837");
        i1.setDateOfDiagnosisYear("2001");
        i2.setDateOfDiagnosisYear("2015");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(15, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
    }

    @Test
    public void test2004BenignBrain() {
        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;

        //Rule 1: Multiple non-malignant tumors of the same histology that recur in the same site and same side (laterality) as the original tumor are recurrences (single primary) even after 20 years.
        i1.setPrimarySite("C728");
        i2.setPrimarySite("C721");
        i1.setHistologyIcdO3("9384");
        i2.setHistologyIcdO3("9506");
        i1.setBehaviorIcdO3("0");
        i2.setBehaviorIcdO3("1");
        i1.setLaterality("1");
        i2.setLaterality("1");
        i1.setDateOfDiagnosisYear("2006");
        i2.setDateOfDiagnosisYear("2000");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(1, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("same side"));
        //9560/0 and 9560/1 are different histologies
        i1.setHistologyIcdO3("9560");
        i2.setHistologyIcdO3("9560");
        i1.setBehaviorIcdO3("0");
        i2.setBehaviorIcdO3("1");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(1, output.getAppliedRules().size());
        //8670 and 8679 are same histologies, since i2 is diagnosed before 2001, icd02 will be used if it is valid
        i1.setHistologyIcdO2("8234");
        i1.setHistologyIcdO3("8670");
        i2.setHistologyIcdO3("9000");
        i2.setHistologyIcdO2("8679");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(1, output.getAppliedRules().size());

        //Rule 2: Multiple non-malignant tumors of the same histology that recur in the same site and it is unknown if it is the same side (laterality) as the original tumor are
        // recurrences (single primary) even after 20 years.
        i1.setLaterality(null);
        i2.setLaterality(null);
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(2, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("unknown"));
        i1.setLaterality("4");
        i2.setLaterality("9");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(2, output.getAppliedRules().size());
        i1.setLaterality("5");
        i2.setLaterality("0");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(2, output.getAppliedRules().size());

        //Rule 3: Multiple non-malignant tumors of the same histology in different sites of the CNS are separate (multiple) primaries
        i1 = new MphInput();
        i2 = new MphInput();
        i1.setPrimarySite("C728");
        i2.setPrimarySite("C753"); //different site
        i1.setHistologyIcdO3("9505");
        i2.setHistologyIcdO3("9412"); // same histology 9505/1 && 9412
        i1.setBehaviorIcdO3("1");
        i2.setBehaviorIcdO3("0");
        i1.setDateOfDiagnosisYear("2006");
        i2.setDateOfDiagnosisYear("2000");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("different sites"));
        i1.setBehaviorIcdO3("0"); //9505/0 and 9412 are NOT same histologies
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(3, output.getAppliedRules().size());
        i2.setHistologyIcdO3("9500");//9505 and 9500 are same histologies
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(3, output.getAppliedRules().size());

        //Rule 4: Multiple non-malignant tumors of the same histology in different sides (laterality) of the CNS are separate (multiple) primaries.
        i1 = new MphInput();
        i2 = new MphInput();
        i1.setPrimarySite("C728");
        i2.setPrimarySite("C721");
        i1.setHistologyIcdO3("9540");
        i2.setHistologyIcdO3("9560");
        i1.setBehaviorIcdO3("0");
        i2.setBehaviorIcdO3("0"); //9540 and 9560/0 are same histologies
        i1.setLaterality("1");
        i2.setLaterality("2");
        i1.setDateOfDiagnosisYear("2006");
        i2.setDateOfDiagnosisYear("2000");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("different sides"));
        i2.setBehaviorIcdO3("1"); //9540 and 9560/1 are NOT same histologies
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(4, output.getAppliedRules().size());

        //Rule 5: Multiple non-malignant tumors of different histologies are separate (multiple) primaries)
        i2.setBehaviorIcdO3("1"); //9540 and 9560/1 are NOT same histologies
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("different histologies"));
        i1 = new MphInput();
        i2 = new MphInput();
        i1.setPrimarySite("C728");
        i2.setPrimarySite("C753");
        i1.setHistologyIcdO3("8000");
        i2.setHistologyIcdO3("8010");
        i1.setBehaviorIcdO3("0");
        i2.setBehaviorIcdO3("0");
        i1.setDateOfDiagnosisYear("2006");
        i2.setDateOfDiagnosisYear("2000");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(5, output.getAppliedRules().size());
    }

    @Test
    public void test2004SolidMalignant() {
        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;

        //Rule 1 TODO

        //Rule 2 TODO

        //Rule 3: Simultaneous multiple lesions of the same histologic type within the same site (i.e., multifocal tumors in a single organ or site) are a single primary.
        //If a new cancer of the same histology as an earlier one is diagnosed in the same site within two months, this is a single primary cancer
        i1.setPrimarySite("C180");
        i2.setPrimarySite("C181"); //not same sites
        i1.setHistologyIcdO3("9384");
        i2.setHistologyIcdO3("9380");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i2.setLaterality("1");
        i1.setDateOfDiagnosisYear("2006");
        i2.setDateOfDiagnosisYear("2006");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(3, output.getAppliedRules().size());
        i1.setPrimarySite("C515");
        i2.setPrimarySite("C579"); //same sites
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult()); // not sure if they are simultaneous
        i1.setDateOfDiagnosisMonth("07");
        i2.setDateOfDiagnosisMonth("08"); //if it is july 1st and aug 31, more than 60 days, not sure if they are simultaneous
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        i1.setDateOfDiagnosisMonth("06");
        i2.setDateOfDiagnosisMonth("09"); //not simultaneous
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(3, output.getAppliedRules().size());
        i1.setDateOfDiagnosisMonth("01");
        i2.setDateOfDiagnosisMonth("02"); // maximum of < 60 days between january 1st and Feb 29
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        i1.setHistologyIcdO3("8041");
        i2.setHistologyIcdO3("8046"); //not same histology
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(3, output.getAppliedRules().size());

        //Rule 4 If both sides of a paired organ are involved with the same histologic type within two months of the initial diagnosis
        i1 = new MphInput();
        i2 = new MphInput();
        i1.setPrimarySite("C080");
        i2.setPrimarySite("C081"); //paired sites
        i1.setHistologyIcdO3("9384");
        i2.setHistologyIcdO3("9380");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i2.setLaterality("2");
        i1.setDateOfDiagnosisYear("2006");
        i2.setDateOfDiagnosisYear("2006");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        i1.setDateOfDiagnosisMonth("06");
        i2.setDateOfDiagnosisMonth("06");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        //Exceptions
        i1.setPrimarySite("C569");
        i2.setPrimarySite("C569"); //ovary
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        i1.setPrimarySite("C080");
        i2.setPrimarySite("C081"); //paired sites
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        i1.setHistologyIcdO3("9510");
        i2.setHistologyIcdO3("9513"); //retinoblastoma
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        i1.setHistologyIcdO3("8960");
        i2.setHistologyIcdO3("8960"); //wilms
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        i1.setDateOfDiagnosisYear("2007");
        i2.setDateOfDiagnosisYear("2006"); //not simultaneous
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(4, output.getAppliedRules().size());

        //Rule 5: If a tumor with the same histology is identified in the same site at least two months after the
        //initial/original diagnosis (metachronous), this is a separate primary.
        i1 = new MphInput();
        i2 = new MphInput();
        i1.setPrimarySite("C380");
        i2.setPrimarySite("C371"); //same
        i1.setHistologyIcdO3("9384");
        i2.setHistologyIcdO3("9380");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2006");
        i2.setDateOfDiagnosisYear("2004");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        i1.setPrimarySite("C600");
        i2.setPrimarySite("C638"); //same
        i1.setPrimarySite("C384");
        i2.setPrimarySite("C371"); //not same sites
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(5, output.getAppliedRules().size());
        i1.setPrimarySite("C619");
        i2.setPrimarySite("C619"); //Prostate
        i1.setHistologyIcdO3("8330");
        i2.setHistologyIcdO3("8333"); //adenocarcinoma
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        i1.setPrimarySite("C679");
        i2.setPrimarySite("C672"); //Bladder
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        i1.setHistologyIcdO3("8131");
        i2.setHistologyIcdO3("8130"); //carcinoma of bladder
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        i1.setPrimarySite("C230");
        i2.setPrimarySite("C249");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        i1.setHistologyIcdO3("9140");
        i2.setHistologyIcdO3("9140"); //kaposi sarcoma
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());

        //Rule 6: Multiple synchronous lesions of different histologic types within a single paired
        //or unpaired organ are separate primaries.
        i1 = new MphInput();
        i2 = new MphInput();
        i1.setPrimarySite("C380");
        i2.setPrimarySite("C371"); //same site, unpaired
        i1.setHistologyIcdO3("8045");
        i2.setHistologyIcdO3("8046"); //different histology
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2004");
        i2.setDateOfDiagnosisYear("2004");
        i1.setDateOfDiagnosisMonth("04");
        i2.setDateOfDiagnosisMonth("04");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        i1.setPrimarySite("C080");
        i2.setPrimarySite("C081"); //same site, paired, laterality should be same
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.QUESTIONABLE, output.getResult());
        i1.setLaterality("1");
        i2.setLaterality("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(6, output.getAppliedRules().size());
        i1.setLaterality("2");
        i2.setLaterality("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        //Exceptions
        i1.setHistologyIcdO3("8010");
        i2.setHistologyIcdO3("8243"); //Carcinoma Nos vs Specific
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        i1.setHistologyIcdO3("8140");
        i2.setHistologyIcdO3("8021"); //adenocarcinoma Nos vs Specific
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        i1.setHistologyIcdO3("8720");
        i2.setHistologyIcdO3("8789"); //melanoma Nos vs Specific
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        i1.setHistologyIcdO3("8800");
        i2.setHistologyIcdO3("8001"); //sarcoma nos vs specific
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        i1.setHistologyIcdO3("8220");
        i2.setHistologyIcdO3("8220"); //Familial adenomatous polyposis
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(6, output.getAppliedRules().size()); //Since histologies are the same, this will be caught earlier at rule 3
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        i1.setHistologyIcdO3("8331");
        i2.setHistologyIcdO3("8052"); //Follicular and Papillary, not thyroid tho
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        i1.setPrimarySite("C739");
        i2.setPrimarySite("C739"); //thyroid
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        i1.setHistologyIcdO3("8120");
        i2.setHistologyIcdO3("8130"); //transitional cell carcinoma and papillary transitional cell carcinoma, not bladder
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        i1.setPrimarySite("C671");
        i2.setPrimarySite("C679"); //bladder
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        i1.setHistologyIcdO3("8035");
        i2.setHistologyIcdO3("8522"); //duct and lobular
        i1.setPrimarySite("C501");
        i2.setPrimarySite("C509"); //breast
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        i1.setHistologyIcdO3("8503");
        i2.setHistologyIcdO3("8542"); //intraductal and paget
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        i1.setDateOfDiagnosisYear("2006"); // not synchronous
        output = _utils.computePrimaries(i1, i2);
        Assert.assertNotEquals(6, output.getAppliedRules().size());

        //Rule 7: Multiple synchronous lesions of different histologic types in paired organs are multiple primaries.
        i1 = new MphInput();
        i2 = new MphInput();
        i1.setPrimarySite("C080");
        i2.setPrimarySite("C081"); //same site, paired
        i1.setHistologyIcdO3("8045");
        i2.setHistologyIcdO3("8046"); //different histology
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2004");
        i2.setDateOfDiagnosisYear("2004");
        i1.setDateOfDiagnosisMonth("04");
        i2.setDateOfDiagnosisMonth("04");
        i1.setLaterality("1");
        i2.setLaterality("2");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(7, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());

        //Rule 8: Multiple metachronous lesions of different histologic types within a single site are separate primaries.
        i1.setDateOfDiagnosisYear("2006");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(8, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());

        //Rule 9: Multiple lesions of different histologic types occurring in different sites are separate primaries whether occurring simultaneously or at different times.
        i1.setPrimarySite("C180");
        i2.setPrimarySite("C181"); //different site
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(9, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        i1.setDateOfDiagnosisYear("2004");
        i2.setDateOfDiagnosisYear("2004");
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(9, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());

        //Rule 10: Multiple lesions of the same histologic type occurring in different sites are separate primaries unless stated to be metastatic.
        i1.setHistologyIcdO3("8045");
        i2.setHistologyIcdO3("8045"); //same histology
        output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(10, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
    }

    @Test
    public void testLenientMode() {
        MphInput i1 = new MphInput();
        MphInput i2 = new MphInput();
        i1.setPrimarySite("C502");
        i1.setHistologyIcdO3("8500");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2015");
        i1.setDateOfDiagnosisMonth("08");
        i1.setDateOfDiagnosisDay("17");

        i2.setPrimarySite("C502");
        i2.setHistologyIcdO3("8000");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("1");
        i2.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisMonth("10");
        i2.setDateOfDiagnosisDay("28");
        MphOutput output = _utils.computePrimaries(i1, i2);
        Assert.assertEquals(9, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals("M12", output.getStep());

        MphComputeOptions options = new MphComputeOptions();
        options.setHistologyMatchingMode(MphComputeOptions.MpHistologyMatching.LENIENT);
        output = _utils.computePrimaries(i1, i2, options);
        Assert.assertEquals(10, output.getAppliedRules().size());
        Assert.assertEquals(MphUtils.MpResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals("M13", output.getStep());

        options.setHistologyMatchingMode(MphComputeOptions.MpHistologyMatching.STRICT);
        output = _utils.computePrimaries(i1, i2, options);
        Assert.assertEquals(MphUtils.MpResult.MULTIPLE_PRIMARIES, output.getResult());
    }

    @Test
    public void testUnableToApplyRule() {

        // Unknown Laterality
        MphInput i1 = new MphInput();
        MphInput i2 = new MphInput();
        i1.setPrimarySite("C090");
        i1.setHistologyIcdO3("8000");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2015");
        i1.setDateOfDiagnosisMonth("08");
        i1.setDateOfDiagnosisDay("17");
        i2.setPrimarySite("C098");
        i2.setHistologyIcdO3("8100");
        i2.setBehaviorIcdO3("2");
        i2.setLaterality("9");
        i2.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisMonth("10");
        i2.setDateOfDiagnosisDay("28");
        MphOutput output = _utils.computePrimaries(i1, i2);
        Assert.assertTrue(output.getReason().contains("Valid and known laterality should"));

        // Unknown days apart
        i1 = new MphInput();
        i2 = new MphInput();
        i1.setPrimarySite("C659");
        i1.setHistologyIcdO3("9590");
        i1.setBehaviorIcdO3("2");
        i1.setLaterality("1");
        i1.setDateOfDiagnosisYear("2015");
        i1.setDateOfDiagnosisMonth("08");
        i2.setPrimarySite("C669");
        i2.setHistologyIcdO3("9590");
        i2.setBehaviorIcdO3("3");
        i2.setLaterality("2");
        i2.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisMonth("10");

        Mp2007UrinaryGroup group = new Mp2007UrinaryGroup();
        MphComputeOptions options = new MphComputeOptions();

        for (MphRule rule : group.getRules()) {
            if (rule.getStep().equals("M5")) {
                TempRuleResult result = rule.apply(i1, i2, options);
                Assert.assertTrue(result.getMessage().contains("There is not enough diagnosis date information."));
            }
        }

        // Unknown laterality and days apart
        i1 = new MphInput();
        i2 = new MphInput();
        i1.setPrimarySite("C090");
        i1.setHistologyIcdO3("8100");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("0");
        i1.setDateOfDiagnosisYear("2004");
        i2.setPrimarySite("C090");
        i2.setHistologyIcdO3("8100");
        i2.setBehaviorIcdO3("2");
        i2.setLaterality("0");
        i2.setDateOfDiagnosisYear("2004");

        Mp2004SolidMalignantGroup malgroup = new Mp2004SolidMalignantGroup();
        options = new MphComputeOptions();

        for (MphRule rule : malgroup.getRules()) {
            if (rule.getStep().equals("M3")) {
                TempRuleResult result = rule.apply(i1, i2, options);
                Assert.assertTrue(result.getMessage().contains("Valid and known laterality and diagnosis date"));
            }
        }
    }
}
