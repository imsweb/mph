/*
 * Copyright (C) 2003 Information Management Services, Inc.
 */
package com.imsweb.mph;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.imsweb.mph.mpgroups.GroupUtility;

public class GroupUtilityTest {

    @Test
    public void testSameHistologies() {
        Assert.assertFalse(GroupUtility.sameHistologies(null, null));
        Assert.assertTrue(GroupUtility.sameHistologies("", ""));
        Assert.assertTrue(GroupUtility.sameHistologies("8800", "8800"));
        Assert.assertFalse(GroupUtility.sameHistologies("8800", "8801"));
        Assert.assertTrue(GroupUtility.sameHistologies("8800/3", "8800/3"));
        Assert.assertFalse(GroupUtility.sameHistologies("8800/3", "8800/2"));
        Assert.assertFalse(GroupUtility.sameHistologies("8000", "8000"));
        Assert.assertFalse(GroupUtility.sameHistologies("8010", "8010"));
        Assert.assertFalse(GroupUtility.sameHistologies("8000/3", "8000/3"));
    }

    @Test
    public void testSameKnownDateParts() {
        MphInput i1 = new MphInput();
        MphInput i2 = new MphInput();
        Assert.assertFalse(GroupUtility.sameKnownDateParts(i1, i2));
        i1.setDateOfDiagnosisYear("2010");
        Assert.assertFalse(GroupUtility.sameKnownDateParts(i1, i2));
        i2.setDateOfDiagnosisYear("2011");
        Assert.assertFalse(GroupUtility.sameKnownDateParts(i1, i2));
        i2.setDateOfDiagnosisYear("2010");
        Assert.assertTrue(GroupUtility.sameKnownDateParts(i1, i2));
        i1.setDateOfDiagnosisMonth("02");
        Assert.assertTrue(GroupUtility.sameKnownDateParts(i1, i2));
        i2.setDateOfDiagnosisMonth("99");
        Assert.assertTrue(GroupUtility.sameKnownDateParts(i1, i2));
        i2.setDateOfDiagnosisMonth("03");
        Assert.assertFalse(GroupUtility.sameKnownDateParts(i1, i2));
        i2.setDateOfDiagnosisMonth("02");
        Assert.assertTrue(GroupUtility.sameKnownDateParts(i1, i2));
        i1.setDateOfDiagnosisDay("03");
        Assert.assertTrue(GroupUtility.sameKnownDateParts(i1, i2));
        i2.setDateOfDiagnosisDay("AA");
        Assert.assertTrue(GroupUtility.sameKnownDateParts(i1, i2));
        i2.setDateOfDiagnosisDay("03");
        Assert.assertTrue(GroupUtility.sameKnownDateParts(i1, i2));
        i2.setDateOfDiagnosisDay("04");
        Assert.assertFalse(GroupUtility.sameKnownDateParts(i1, i2));
    }

    @Test
    public void testValidateProperties() {
        Assert.assertFalse(GroupUtility.validateProperties(null, "8100", "1", 2007));
        Assert.assertFalse(GroupUtility.validateProperties("123", "8100", "1", 2007));
        Assert.assertFalse(GroupUtility.validateProperties("D189", "8100", "1", 2007));
        Assert.assertFalse(GroupUtility.validateProperties("C18X", "8100", "1", 2007));
        Assert.assertFalse(GroupUtility.validateProperties("C809", "8100", "1", 2007));

        Assert.assertFalse(GroupUtility.validateProperties("C189", "ABCD", "1", 2007));
        Assert.assertFalse(GroupUtility.validateProperties("C189", "7999", "1", 2007));
        Assert.assertFalse(GroupUtility.validateProperties("C189", "80003", "1", 2007));

        Assert.assertFalse(GroupUtility.validateProperties("C189", "8000", "A", 2007));
        Assert.assertFalse(GroupUtility.validateProperties("C189", "8000", "8", 2007));
        Assert.assertFalse(GroupUtility.validateProperties("C189", "8000", "01", 2007));

        Assert.assertFalse(GroupUtility.validateProperties("C189", "8000", "3", -1));
        Assert.assertFalse(GroupUtility.validateProperties("C189", "8000", "3", LocalDate.now().getYear() + 1));

        Assert.assertTrue(GroupUtility.validateProperties("C189", "8000", "1", 2007));
        Assert.assertTrue(GroupUtility.validateProperties("C729", "9500", "3", 1998));
        Assert.assertTrue(GroupUtility.validateProperties("C000", "8723", "6", LocalDate.now().getYear()));
    }

    @Test
    public void testSameValidDate() {
        Assert.assertTrue(GroupUtility.sameValidDates(2010, 1, 1, 2010, 1, 1));
        Assert.assertFalse(GroupUtility.sameValidDates(2010, 1, 1, 2010, 1, 2));
        Assert.assertFalse(GroupUtility.sameValidDates(2010, 13, 1, 2010, 13, 1));
    }

    @Test
    public void testComputeRange() {
        Assert.assertFalse(GroupUtility.isContained(null, 700));
        Assert.assertTrue(GroupUtility.isContained(GroupUtility.computeRange("C700-C718,C724,C726-C729", true), 700));
        Assert.assertTrue(GroupUtility.isContained(GroupUtility.computeRange(" C700-C718, C724,C726 - C729", true), 700));
        Assert.assertTrue(GroupUtility.isContained(GroupUtility.computeRange(" C700-C718, C724,C726 - C729", true), 714));
        Assert.assertTrue(GroupUtility.isContained(GroupUtility.computeRange(" C700-C718, C724,C726 - C729", true), 718));
        Assert.assertTrue(GroupUtility.isContained(GroupUtility.computeRange(" C700-C718, C724,C726 - C729", true), 724));
        Assert.assertTrue(GroupUtility.isContained(GroupUtility.computeRange(" C700-C718, C724,C726 - C729", true), 726));
        Assert.assertTrue(GroupUtility.isContained(GroupUtility.computeRange(" C700-C718, C724,C726 - C729", true), 728));
        Assert.assertTrue(GroupUtility.isContained(GroupUtility.computeRange(" C700-C718, C724,C726 - C729", true), 729));
        Assert.assertFalse(GroupUtility.isContained(GroupUtility.computeRange(" C700-C718, C724,C726 - C729", true), 699));
        Assert.assertFalse(GroupUtility.isContained(GroupUtility.computeRange(" C700-C718, C724,C726 - C729", true), 719));
        Assert.assertFalse(GroupUtility.isContained(GroupUtility.computeRange(" C700-C718, C724,C726 - C729", true), 725));
        Assert.assertFalse(GroupUtility.isContained(GroupUtility.computeRange(" C700-C718, C724,C726 - C729", true), 750));
        Assert.assertTrue(GroupUtility.isContained(GroupUtility.computeRange("8000-8523,8527,8536,9000-9002", false), 8400));
        Assert.assertTrue(GroupUtility.isContained(GroupUtility.computeRange(" 8000 - 8523 , 8527 , 8536 , 9000 -  9002  ", false), 8400));
        Assert.assertTrue(GroupUtility.isContained(GroupUtility.computeRange(" 8000 - 8523 , 8527 , 8536 , 9000 -  9002  ", false), 8523));
        Assert.assertTrue(GroupUtility.isContained(GroupUtility.computeRange(" 8000 - 8523 , 8527 , 8536 , 9000 -  9002  ", false), 8527));
        Assert.assertTrue(GroupUtility.isContained(GroupUtility.computeRange(" 8000 - 8523 , 8527 , 8536 , 9000 -  9002  ", false), 8536));
        Assert.assertTrue(GroupUtility.isContained(GroupUtility.computeRange(" 8000 - 8523 , 8527 , 8536 , 9000 -  9002  ", false), 9000));
        Assert.assertTrue(GroupUtility.isContained(GroupUtility.computeRange(" 8000 - 8523 , 8527 , 8536 , 9000 -  9002  ", false), 9001));
        Assert.assertTrue(GroupUtility.isContained(GroupUtility.computeRange(" 8000 - 8523 , 8527 , 8536 , 9000 -  9002  ", false), 9002));
        Assert.assertFalse(GroupUtility.isContained(GroupUtility.computeRange(" 8000 - 8523 , 8527 , 8536 , 9000 -  9002  ", false), 7999));
        Assert.assertFalse(GroupUtility.isContained(GroupUtility.computeRange(" 8000 - 8523 , 8527 , 8536 , 9000 -  9002  ", false), 8525));
        Assert.assertFalse(GroupUtility.isContained(GroupUtility.computeRange(" 8000 - 8523 , 8527 , 8536 , 9000 -  9002  ", false), 8530));
        Assert.assertFalse(GroupUtility.isContained(GroupUtility.computeRange(" 8000 - 8523 , 8527 , 8536 , 9000 -  9002  ", false), 8550));
        Assert.assertFalse(GroupUtility.isContained(GroupUtility.computeRange(" 8000 - 8523 , 8527 , 8536 , 9000 -  9002  ", false), 9003));
    }

    @Test
    public void testIsSiteContained() {
        Assert.assertTrue(GroupUtility.isSiteContained("C370-C384,C388", "C388"));
        Assert.assertTrue(GroupUtility.isSiteContained("C370-C384,C388", "C384"));
        Assert.assertTrue(GroupUtility.isSiteContained("C370-C384,C388", "C380"));
        Assert.assertTrue(GroupUtility.isSiteContained("C370-C384,C388", "C370"));
        Assert.assertFalse(GroupUtility.isSiteContained("C370-C384,C388", "C360"));
        Assert.assertFalse(GroupUtility.isSiteContained("C370-C384,C388", "C387"));
    }

    @Test
    public void testExpandList() {
        Assert.assertTrue(GroupUtility.expandList("").isEmpty());
        Assert.assertEquals(Arrays.asList("8100", "8250"), GroupUtility.expandList(Arrays.asList("8100", "8250")));
        Assert.assertEquals(Arrays.asList("8100", "8250"), GroupUtility.expandList(Collections.singletonList("8100,8250")));
        Assert.assertEquals(Arrays.asList("8100", "8250", "8251", "8252", "8255"), GroupUtility.expandList(Arrays.asList("8100", "8250-8252", "8255")));
        Assert.assertEquals(Arrays.asList("8100", "8250", "8251", "8252", "8255"), GroupUtility.expandList(Arrays.asList("8100,8250-8252", "8255")));
        Assert.assertEquals(Arrays.asList("8100", "8250", "8251", "8252", "8255"), GroupUtility.expandList(Collections.singletonList("8100,8250-8252,8255")));
        Assert.assertEquals(Arrays.asList("8100", "8250", "8251", "8252", "8255"), GroupUtility.expandList("8100,8250-8252,8255"));
    }

    @Test
    public void testIsPairedSites() {
        List<String> pairedSites = Arrays.asList("C700-C702,C705", "C710,C712", "C715");
        Assert.assertTrue(GroupUtility.isPairedSites("C700", "C701", pairedSites));
        Assert.assertTrue(GroupUtility.isPairedSites("C700", "C702", pairedSites));
        Assert.assertTrue(GroupUtility.isPairedSites("C700", "C705", pairedSites));
        Assert.assertTrue(GroupUtility.isPairedSites("C702", "C705", pairedSites));
        Assert.assertTrue(GroupUtility.isPairedSites("C705", "C705", pairedSites));
        Assert.assertTrue(GroupUtility.isPairedSites("C710", "C710", pairedSites));
        Assert.assertTrue(GroupUtility.isPairedSites("C710", "C712", pairedSites));
        Assert.assertTrue(GroupUtility.isPairedSites("C712", "C710", pairedSites));
        Assert.assertTrue(GroupUtility.isPairedSites("C715", "C715", pairedSites));
        Assert.assertFalse(GroupUtility.isPairedSites("C716", "C716", pairedSites));
        Assert.assertFalse(GroupUtility.isPairedSites("C712", "C715", pairedSites));
        Assert.assertFalse(GroupUtility.isPairedSites("C700", "C710", pairedSites));
        Assert.assertFalse(GroupUtility.isPairedSites("C700", "C706", pairedSites));
    }

    @Test
    public void testValidPairedSiteLaterality() {
        Assert.assertTrue(GroupUtility.validPairedSiteLaterality("1", "1"));
        Assert.assertTrue(GroupUtility.validPairedSiteLaterality("1", "2"));
        Assert.assertTrue(GroupUtility.validPairedSiteLaterality("2", "1"));
        Assert.assertTrue(GroupUtility.validPairedSiteLaterality("2", "2"));
        Assert.assertFalse(GroupUtility.validPairedSiteLaterality("1", "0"));
        Assert.assertFalse(GroupUtility.validPairedSiteLaterality("2", "3"));
    }

    @Test
    public void testValidateLaterality() {
        Assert.assertFalse(GroupUtility.validateLaterality(null));
        Assert.assertFalse(GroupUtility.validateLaterality(""));
        Assert.assertFalse(GroupUtility.validateLaterality("x"));
        Assert.assertTrue(GroupUtility.validateLaterality("1"));
        Assert.assertTrue(GroupUtility.validateLaterality("2"));
        Assert.assertTrue(GroupUtility.validateLaterality("3"));
        Assert.assertTrue(GroupUtility.validateLaterality("4"));
        Assert.assertTrue(GroupUtility.validateLaterality("5"));
        Assert.assertFalse(GroupUtility.validateLaterality("6"));
        Assert.assertTrue(GroupUtility.validateLaterality("9"));
    }

    @Test
    public void testAreOppositeSides() {
        Assert.assertTrue(GroupUtility.areOppositeSides("1", "2"));
        Assert.assertTrue(GroupUtility.areOppositeSides("2", "1"));
        Assert.assertFalse(GroupUtility.areOppositeSides("1", "1"));
        Assert.assertFalse(GroupUtility.areOppositeSides("2", "2"));
        Assert.assertFalse(GroupUtility.areOppositeSides("1", "0"));
        Assert.assertFalse(GroupUtility.areOppositeSides("2", "3"));
    }

    @Test
    public void testAreSameSide() {
        Assert.assertFalse(GroupUtility.areSameSide("1", "2"));
        Assert.assertFalse(GroupUtility.areSameSide("2", "1"));
        Assert.assertTrue(GroupUtility.areSameSide("1", "1"));
        Assert.assertTrue(GroupUtility.areSameSide("2", "2"));
        Assert.assertFalse(GroupUtility.areSameSide("1", "0"));
        Assert.assertFalse(GroupUtility.areSameSide("2", "3"));
        Assert.assertFalse(GroupUtility.areSameSide("0", "1"));
        Assert.assertFalse(GroupUtility.areSameSide("3", "2"));
        Assert.assertFalse(GroupUtility.areSameSide("4", "1"));
        Assert.assertFalse(GroupUtility.areSameSide("4", "2"));
        Assert.assertFalse(GroupUtility.areSameSide("1", "4"));
        Assert.assertFalse(GroupUtility.areSameSide("2", "4"));
    }

    @Test
    public void testSameAndValidMainFields() {
        MphInput i1 = new MphInput(), i2 = new MphInput();
        Assert.assertFalse(GroupUtility.sameAndValidMainFields(i1, i2));

        i1.setPrimarySite("C569");
        i2.setPrimarySite("C569");
        i1.setHistologyIcdO3("8472");
        i2.setHistologyIcdO3("8472");
        i1.setBehaviorIcdO3("1");
        i2.setBehaviorIcdO3("1");
        i1.setLaterality("9");
        i2.setLaterality("9");
        i1.setDateOfDiagnosisYear("2014");
        i2.setDateOfDiagnosisYear("2014");
        i1.setDateOfDiagnosisMonth("6");
        i2.setDateOfDiagnosisMonth("6");
        i1.setDateOfDiagnosisDay("11");
        i2.setDateOfDiagnosisDay("11");
        Assert.assertTrue(GroupUtility.sameAndValidMainFields(i1, i2));

        i1.setHistologyIcdO3("7999");
        i2.setHistologyIcdO3("7999");
        Assert.assertFalse(GroupUtility.sameAndValidMainFields(i1, i2));
        i1.setHistologyIcdO3("8472");
        i2.setHistologyIcdO3("8472");

        i2.setLaterality(null);
        Assert.assertFalse(GroupUtility.sameAndValidMainFields(i1, i2));
        i2.setLaterality("9");

        i1.setDateOfDiagnosisMonth("1");
        Assert.assertFalse(GroupUtility.sameAndValidMainFields(i1, i2));
        i1.setDateOfDiagnosisMonth("6");
        Assert.assertTrue(GroupUtility.sameAndValidMainFields(i1, i2));

        i1.setDateOfDiagnosisDay("31");
        i2.setDateOfDiagnosisDay("31");
        Assert.assertFalse(GroupUtility.sameAndValidMainFields(i1, i2));

    }

    @Test
    public void testDifferentCategory() {
        Assert.assertTrue(GroupUtility.differentCategory("C700", "C730", Arrays.asList("C700", "C719", "C725"), Arrays.asList("C730", "C740")));
        Assert.assertTrue(GroupUtility.differentCategory("C725", "C740", Arrays.asList("C700", "C719", "C725"), Arrays.asList("C730", "C740")));
        Assert.assertFalse(GroupUtility.differentCategory("C700", "C725", Arrays.asList("C700", "C719", "C725"), Arrays.asList("C730", "C740")));
        Assert.assertTrue(GroupUtility.differentCategory("2", "3", Collections.singletonList("2"), Collections.singletonList("3")));
        Assert.assertFalse(GroupUtility.differentCategory("2", "2", Collections.singletonList("2"), Collections.singletonList("3")));
        Assert.assertTrue(GroupUtility.differentCategory("8000", "8010", Collections.singletonList("8000"), GroupUtility.expandList("8001-8750")));
        Assert.assertTrue(GroupUtility.differentCategory("8225", "9010", GroupUtility.expandList("8001-8750"), GroupUtility.expandList("9001-9750")));
        Assert.assertTrue(GroupUtility.differentCategory("9001", "8750", GroupUtility.expandList("8001-8750"), GroupUtility.expandList("9001-9750")));
    }

    //Suppressing sonar warnings for method complexity
    @SuppressWarnings("java:S5961")
    @Test
    public void testVerifyDaysApart() {

        MphInput i1 = new MphInput(), i2 = new MphInput();
        //if one of the diagnosis year is unknown or invalid or future year, unknown
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyDaysApart(i1, i2, 60));
        i1.setDateOfDiagnosisYear("Invalid");
        i2.setDateOfDiagnosisYear("2002");
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyDaysApart(i1, i2, 60));
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyDaysApart(i1, i2, 21));
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyDaysApart(i2, i1, 60));
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyDaysApart(i2, i1, 21));
        i1.setDateOfDiagnosisYear(String.valueOf(LocalDate.now().getYear() + 1));
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyDaysApart(i1, i2, 60));
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyDaysApart(i1, i2, 21));
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyDaysApart(i2, i1, 60));
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyDaysApart(i2, i1, 21));

        i1 = new MphInput();
        i2 = new MphInput();
        i1.setDateOfDiagnosisYear("2000");
        i2.setDateOfDiagnosisYear("2002");
        Assert.assertEquals(MphConstants.DATE_VERIFY_APART, GroupUtility.verifyDaysApart(i1, i2, 60));
        Assert.assertEquals(MphConstants.DATE_VERIFY_APART, GroupUtility.verifyDaysApart(i1, i2, 21));
        Assert.assertEquals(MphConstants.DATE_VERIFY_APART, GroupUtility.verifyDaysApart(i2, i1, 60));
        Assert.assertEquals(MphConstants.DATE_VERIFY_APART, GroupUtility.verifyDaysApart(i2, i1, 21));

        i1 = new MphInput();
        i2 = new MphInput();
        i1.setDateOfDiagnosisYear("2001");
        i2.setDateOfDiagnosisYear("2002");
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyDaysApart(i1, i2, 60));
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyDaysApart(i1, i2, 21));
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyDaysApart(i2, i1, 60));
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyDaysApart(i2, i1, 21));

        i1 = new MphInput();
        i2 = new MphInput();
        i1.setDateOfDiagnosisYear("2001");
        i1.setDateOfDiagnosisMonth("10");
        i2.setDateOfDiagnosisYear("2002");
        Assert.assertEquals(MphConstants.DATE_VERIFY_APART, GroupUtility.verifyDaysApart(i1, i2, 60));
        Assert.assertEquals(MphConstants.DATE_VERIFY_APART, GroupUtility.verifyDaysApart(i1, i2, 21));
        Assert.assertEquals(MphConstants.DATE_VERIFY_APART, GroupUtility.verifyDaysApart(i2, i1, 60));
        Assert.assertEquals(MphConstants.DATE_VERIFY_APART, GroupUtility.verifyDaysApart(i2, i1, 21));

        i1 = new MphInput();
        i2 = new MphInput();
        i1.setDateOfDiagnosisYear("2001");
        i1.setDateOfDiagnosisMonth("11");
        i2.setDateOfDiagnosisYear("2002");
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyDaysApart(i1, i2, 60));
        Assert.assertEquals(MphConstants.DATE_VERIFY_APART, GroupUtility.verifyDaysApart(i1, i2, 21));
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyDaysApart(i2, i1, 60));
        Assert.assertEquals(MphConstants.DATE_VERIFY_APART, GroupUtility.verifyDaysApart(i2, i1, 21));

        i1 = new MphInput();
        i2 = new MphInput();
        i1.setDateOfDiagnosisYear("2001");
        i1.setDateOfDiagnosisMonth("12");
        i2.setDateOfDiagnosisYear("2002");
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyDaysApart(i1, i2, 60));
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyDaysApart(i1, i2, 21));
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyDaysApart(i2, i1, 60));
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyDaysApart(i2, i1, 21));

        i1 = new MphInput();
        i2 = new MphInput();
        i1.setDateOfDiagnosisYear("2001");
        i1.setDateOfDiagnosisMonth("12");
        i2.setDateOfDiagnosisYear("2002");
        i2.setDateOfDiagnosisMonth("01");
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyDaysApart(i1, i2, 60));
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyDaysApart(i1, i2, 21));
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyDaysApart(i2, i1, 60));
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyDaysApart(i2, i1, 21));

        i1 = new MphInput();
        i2 = new MphInput();
        i1.setDateOfDiagnosisYear("2001");
        i1.setDateOfDiagnosisMonth("12");
        i2.setDateOfDiagnosisYear("2002");
        i2.setDateOfDiagnosisMonth("01");
        i2.setDateOfDiagnosisDay("01");
        Assert.assertEquals(MphConstants.DATE_VERIFY_WITHIN, GroupUtility.verifyDaysApart(i1, i2, 60));
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyDaysApart(i1, i2, 21));
        Assert.assertEquals(MphConstants.DATE_VERIFY_WITHIN, GroupUtility.verifyDaysApart(i2, i1, 60));
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyDaysApart(i2, i1, 21));

        i1 = new MphInput();
        i2 = new MphInput();
        i1.setDateOfDiagnosisYear("2001");
        i1.setDateOfDiagnosisMonth("08");
        i2.setDateOfDiagnosisYear("2002");
        Assert.assertEquals(MphConstants.DATE_VERIFY_APART, GroupUtility.verifyDaysApart(i1, i2, 60));
        Assert.assertEquals(MphConstants.DATE_VERIFY_APART, GroupUtility.verifyDaysApart(i1, i2, 21));
        Assert.assertEquals(MphConstants.DATE_VERIFY_APART, GroupUtility.verifyDaysApart(i2, i1, 60));
        Assert.assertEquals(MphConstants.DATE_VERIFY_APART, GroupUtility.verifyDaysApart(i2, i1, 21));

        i1 = new MphInput();
        i2 = new MphInput();
        i1.setDateOfDiagnosisYear("2001");
        i1.setDateOfDiagnosisMonth("12");
        i2.setDateOfDiagnosisYear("2001");
        i2.setDateOfDiagnosisMonth("12");
        Assert.assertEquals(MphConstants.DATE_VERIFY_WITHIN, GroupUtility.verifyDaysApart(i1, i2, 60));
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyDaysApart(i1, i2, 21));
        Assert.assertEquals(MphConstants.DATE_VERIFY_WITHIN, GroupUtility.verifyDaysApart(i2, i1, 60));
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyDaysApart(i2, i1, 21));

        i1 = new MphInput();
        i2 = new MphInput();
        i1.setDateOfDiagnosisYear("2001");
        i1.setDateOfDiagnosisMonth("12");
        i2.setDateOfDiagnosisYear("2001");
        i2.setDateOfDiagnosisMonth("12");
        i1.setDateOfDiagnosisDay("15");
        Assert.assertEquals(MphConstants.DATE_VERIFY_WITHIN, GroupUtility.verifyDaysApart(i1, i2, 60));
        Assert.assertEquals(MphConstants.DATE_VERIFY_WITHIN, GroupUtility.verifyDaysApart(i1, i2, 21));
        Assert.assertEquals(MphConstants.DATE_VERIFY_WITHIN, GroupUtility.verifyDaysApart(i2, i1, 60));
        Assert.assertEquals(MphConstants.DATE_VERIFY_WITHIN, GroupUtility.verifyDaysApart(i2, i1, 21));

        i1 = new MphInput();
        i2 = new MphInput();
        i1.setDateOfDiagnosisYear("2001");
        i1.setDateOfDiagnosisMonth("12");
        i2.setDateOfDiagnosisYear("2001");
        i2.setDateOfDiagnosisMonth("12");
        i1.setDateOfDiagnosisDay("01");
        Assert.assertEquals(MphConstants.DATE_VERIFY_WITHIN, GroupUtility.verifyDaysApart(i1, i2, 60));
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyDaysApart(i1, i2, 21));
        Assert.assertEquals(MphConstants.DATE_VERIFY_WITHIN, GroupUtility.verifyDaysApart(i2, i1, 60));
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyDaysApart(i2, i1, 21));

        i1 = new MphInput();
        i2 = new MphInput();
        i1.setDateOfDiagnosisYear("2001");
        i1.setDateOfDiagnosisMonth("12");
        i2.setDateOfDiagnosisYear("2001");
        i2.setDateOfDiagnosisMonth("12");
        i1.setDateOfDiagnosisDay("03");
        Assert.assertEquals(MphConstants.DATE_VERIFY_WITHIN, GroupUtility.verifyDaysApart(i1, i2, 60));
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyDaysApart(i1, i2, 21));
        Assert.assertEquals(MphConstants.DATE_VERIFY_WITHIN, GroupUtility.verifyDaysApart(i2, i1, 60));
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyDaysApart(i2, i1, 21));

        i1 = new MphInput();
        i2 = new MphInput();
        i1.setDateOfDiagnosisYear("2001");
        i1.setDateOfDiagnosisMonth("12");
        i2.setDateOfDiagnosisYear("2001");
        i2.setDateOfDiagnosisMonth("12");
        i1.setDateOfDiagnosisDay("29");
        Assert.assertEquals(MphConstants.DATE_VERIFY_WITHIN, GroupUtility.verifyDaysApart(i1, i2, 60));
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyDaysApart(i1, i2, 21));
        Assert.assertEquals(MphConstants.DATE_VERIFY_WITHIN, GroupUtility.verifyDaysApart(i2, i1, 60));
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyDaysApart(i2, i1, 21));

        i1 = new MphInput();
        i2 = new MphInput();
        i1.setDateOfDiagnosisYear("2001");
        i1.setDateOfDiagnosisMonth("12");
        i2.setDateOfDiagnosisYear("2001");
        i2.setDateOfDiagnosisMonth("12");
        i1.setDateOfDiagnosisDay("31");
        Assert.assertEquals(MphConstants.DATE_VERIFY_WITHIN, GroupUtility.verifyDaysApart(i1, i2, 60));
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyDaysApart(i1, i2, 21));
        Assert.assertEquals(MphConstants.DATE_VERIFY_WITHIN, GroupUtility.verifyDaysApart(i2, i1, 60));
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyDaysApart(i2, i1, 21));

        i1 = new MphInput();
        i2 = new MphInput();
        i1.setDateOfDiagnosisYear("2001");
        i1.setDateOfDiagnosisMonth("12");
        i2.setDateOfDiagnosisYear("2001");
        i2.setDateOfDiagnosisMonth("12");
        i1.setDateOfDiagnosisDay("08"); // If one is December 8 and the other is December unknown, may not be within 21 days
        Assert.assertEquals(MphConstants.DATE_VERIFY_WITHIN, GroupUtility.verifyDaysApart(i1, i2, 60));
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyDaysApart(i1, i2, 21));
        Assert.assertEquals(MphConstants.DATE_VERIFY_WITHIN, GroupUtility.verifyDaysApart(i2, i1, 60));
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyDaysApart(i2, i1, 21));
        i1.setDateOfDiagnosisMonth("02");
        i2.setDateOfDiagnosisMonth("02");// If one is February 8 and the other is February unknown, definitely within 21 days
        Assert.assertEquals(MphConstants.DATE_VERIFY_WITHIN, GroupUtility.verifyDaysApart(i1, i2, 60));
        Assert.assertEquals(MphConstants.DATE_VERIFY_WITHIN, GroupUtility.verifyDaysApart(i1, i2, 21));
        Assert.assertEquals(MphConstants.DATE_VERIFY_WITHIN, GroupUtility.verifyDaysApart(i2, i1, 60));
        Assert.assertEquals(MphConstants.DATE_VERIFY_WITHIN, GroupUtility.verifyDaysApart(i2, i1, 21));

        //Invalid month
        i1 = new MphInput();
        i2 = new MphInput();
        i1.setDateOfDiagnosisYear("2001");
        i1.setDateOfDiagnosisMonth("13");
        i1.setDateOfDiagnosisDay("08");
        i2.setDateOfDiagnosisYear("2001");
        i2.setDateOfDiagnosisMonth("12");
        i2.setDateOfDiagnosisDay("08");
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyDaysApart(i2, i1, 60));

        //Invalid day
        i1 = new MphInput();
        i2 = new MphInput();
        i1.setDateOfDiagnosisYear("2001");
        i1.setDateOfDiagnosisMonth("08");
        i1.setDateOfDiagnosisDay("77");
        i2.setDateOfDiagnosisYear("2001");
        i2.setDateOfDiagnosisMonth("12");
        i2.setDateOfDiagnosisDay("08");
        Assert.assertEquals(MphConstants.DATE_VERIFY_APART, GroupUtility.verifyDaysApart(i2, i1, 60));
        i1.setDateOfDiagnosisMonth("10");
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyDaysApart(i2, i1, 60));
        Assert.assertEquals(MphConstants.DATE_VERIFY_APART, GroupUtility.verifyDaysApart(i2, i1, 21));
        //Day 0 is invalid too
        i1.setDateOfDiagnosisYear("2001");
        i1.setDateOfDiagnosisMonth("08");
        i1.setDateOfDiagnosisDay("0");
        i2.setDateOfDiagnosisYear("2001");
        i2.setDateOfDiagnosisMonth("12");
        i2.setDateOfDiagnosisDay("08");
        Assert.assertEquals(MphConstants.DATE_VERIFY_APART, GroupUtility.verifyDaysApart(i2, i1, 60));
        i1.setDateOfDiagnosisMonth("10");
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyDaysApart(i2, i1, 60));
        Assert.assertEquals(MphConstants.DATE_VERIFY_APART, GroupUtility.verifyDaysApart(i2, i1, 21));

    }

    @Test
    public void testVerifyYearsApart() {

        MphInput i1 = new MphInput(), i2 = new MphInput();
        //if one of the diagnosis year is unknown or invalid or future year, unknown
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyYearsApart(i1, i2, 3));
        i1.setDateOfDiagnosisYear("Invalid");
        i2.setDateOfDiagnosisYear("2002");
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyYearsApart(i1, i2, 3));
        i1.setDateOfDiagnosisYear(String.valueOf(LocalDate.now().getYear() + 1));
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyYearsApart(i1, i2, 3));
        i1.setDateOfDiagnosisYear("2000");
        Assert.assertEquals(MphConstants.DATE_VERIFY_WITHIN, GroupUtility.verifyYearsApart(i1, i2, 3));
        i1.setDateOfDiagnosisYear("2006");
        Assert.assertEquals(MphConstants.DATE_VERIFY_APART, GroupUtility.verifyYearsApart(i1, i2, 3));
        i1.setDateOfDiagnosisYear("2005");
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyYearsApart(i1, i2, 3));
        i1.setDateOfDiagnosisMonth("8");
        i2.setDateOfDiagnosisMonth("9");
        Assert.assertEquals(MphConstants.DATE_VERIFY_WITHIN, GroupUtility.verifyYearsApart(i1, i2, 3));
        i2.setDateOfDiagnosisMonth("7");
        Assert.assertEquals(MphConstants.DATE_VERIFY_APART, GroupUtility.verifyYearsApart(i1, i2, 3));
        i2.setDateOfDiagnosisMonth("8");
        Assert.assertEquals(MphConstants.DATE_VERIFY_UNKNOWN, GroupUtility.verifyYearsApart(i1, i2, 3));
        i1.setDateOfDiagnosisDay("3");
        i2.setDateOfDiagnosisDay("4");
        Assert.assertEquals(MphConstants.DATE_VERIFY_WITHIN, GroupUtility.verifyYearsApart(i1, i2, 3));
        i2.setDateOfDiagnosisDay("1");
        Assert.assertEquals(MphConstants.DATE_VERIFY_APART, GroupUtility.verifyYearsApart(i1, i2, 3));
    }

    @Test
    public void testCompareDxDate() {
        MphInput i1 = new MphInput(), i2 = new MphInput();
        i1.setDateOfDiagnosisYear("2006");
        i1.setDateOfDiagnosisMonth("01");
        i1.setDateOfDiagnosisDay("01");
        i2.setDateOfDiagnosisYear("2006");
        i2.setDateOfDiagnosisMonth("01");
        i2.setDateOfDiagnosisDay("01");
        Assert.assertEquals(MphConstants.COMPARE_DX_EQUAL, GroupUtility.compareDxDate(i1, i2));
        i1.setDateOfDiagnosisDay("02");
        Assert.assertEquals(MphConstants.COMPARE_DX_FIRST_LATEST, GroupUtility.compareDxDate(i1, i2));
        i2.setDateOfDiagnosisMonth("02");
        Assert.assertEquals(MphConstants.COMPARE_DX_SECOND_LATEST, GroupUtility.compareDxDate(i1, i2));
        i1.setDateOfDiagnosisYear("2007");
        Assert.assertEquals(MphConstants.COMPARE_DX_FIRST_LATEST, GroupUtility.compareDxDate(i1, i2));
        i1.setDateOfDiagnosisYear("2007");
        i2.setDateOfDiagnosisYear("2006");
        i1.setDateOfDiagnosisMonth("xx");
        i1.setDateOfDiagnosisDay("33");
        i2.setDateOfDiagnosisMonth("18");
        i2.setDateOfDiagnosisDay("01");
        Assert.assertEquals(MphConstants.COMPARE_DX_FIRST_LATEST, GroupUtility.compareDxDate(i1, i2));
        i1.setDateOfDiagnosisYear("2006");
        i2.setDateOfDiagnosisYear("2006");
        i1.setDateOfDiagnosisMonth("01");
        i1.setDateOfDiagnosisDay("xx");
        i2.setDateOfDiagnosisMonth("02");
        i2.setDateOfDiagnosisDay("30");
        Assert.assertEquals(MphConstants.COMPARE_DX_SECOND_LATEST, GroupUtility.compareDxDate(i1, i2));
        i1.setDateOfDiagnosisYear("2006");
        i2.setDateOfDiagnosisYear("2006");
        i1.setDateOfDiagnosisMonth("02");
        i1.setDateOfDiagnosisDay("01");
        i2.setDateOfDiagnosisMonth("02");
        i2.setDateOfDiagnosisDay("30"); //invalid day for February
        Assert.assertEquals(MphConstants.COMPARE_DX_UNKNOWN, GroupUtility.compareDxDate(i1, i2));
        i1.setDateOfDiagnosisYear("2006");
        i2.setDateOfDiagnosisYear("2006");
        i1.setDateOfDiagnosisMonth("13"); //invalid month
        i1.setDateOfDiagnosisDay("01");
        i2.setDateOfDiagnosisMonth("12");
        i2.setDateOfDiagnosisDay("01");
        Assert.assertEquals(MphConstants.COMPARE_DX_UNKNOWN, GroupUtility.compareDxDate(i1, i2));
        i1.setDateOfDiagnosisYear("2006");
        i2.setDateOfDiagnosisYear("2006");
        i1.setDateOfDiagnosisMonth("xx"); //invalid month
        i1.setDateOfDiagnosisDay("cc");
        i2.setDateOfDiagnosisMonth("99");
        i2.setDateOfDiagnosisDay("01");
        Assert.assertEquals(MphConstants.COMPARE_DX_UNKNOWN, GroupUtility.compareDxDate(i1, i2));
        //one is future year, return unknown
        i1.setDateOfDiagnosisYear(String.valueOf(LocalDate.now().getYear()));
        i2.setDateOfDiagnosisYear(String.valueOf(LocalDate.now().getYear() + 1));
        i1.setDateOfDiagnosisMonth("01");
        i1.setDateOfDiagnosisDay("01");
        i2.setDateOfDiagnosisMonth("01");
        i2.setDateOfDiagnosisDay("01");
        Assert.assertEquals(MphConstants.COMPARE_DX_UNKNOWN, GroupUtility.compareDxDate(i1, i2));
    }

    @Test
    public void testGetSiteHistInfo() {
        Assert.assertEquals("Unknown Site, Unknown Histology/Unknown Behavior with unknown year of diagnosis", GroupUtility.getSiteHistInfo(null, null, null, 9999));
        Assert.assertEquals("Unknown Site, Unknown Histology/Unknown Behavior with year of diagnosis 2020", GroupUtility.getSiteHistInfo("", " ", "  ", 2020));
        Assert.assertEquals("C809, Unknown Histology/Unknown Behavior with unknown year of diagnosis", GroupUtility.getSiteHistInfo("C809", " ", "  ", -1));
        Assert.assertEquals("C249, 8000/Unknown Behavior with year of diagnosis 2020", GroupUtility.getSiteHistInfo("C249", "8000", "  ", 2020));
        Assert.assertEquals("C249, 8000/3 with year of diagnosis 2000", GroupUtility.getSiteHistInfo("C249", "8000", "3", 2000));
    }
}