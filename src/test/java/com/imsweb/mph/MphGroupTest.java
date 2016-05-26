/*
 * Copyright (C) 2003 Information Management Services, Inc.
 */
package com.imsweb.mph;

import java.util.Arrays;
import java.util.Collections;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;

public class MphGroupTest {

    @Test
    public void testExpandList() {
        Assert.assertNull(MphGroup.expandList(null));
        Assert.assertEquals(Arrays.asList("8100", "8250"), MphGroup.expandList(Arrays.asList("8100", "8250")));
        Assert.assertEquals(Arrays.asList("8100", "8250"), MphGroup.expandList(Collections.singletonList("8100,8250")));
        Assert.assertEquals(Arrays.asList("8100", "8250", "8251", "8252", "8255"), MphGroup.expandList(Arrays.asList("8100", "8250-8252", "8255")));
        Assert.assertEquals(Arrays.asList("8100", "8250", "8251", "8252", "8255"), MphGroup.expandList(Arrays.asList("8100,8250-8252", "8255")));
        Assert.assertEquals(Arrays.asList("8100", "8250", "8251", "8252", "8255"), MphGroup.expandList(Collections.singletonList("8100,8250-8252,8255")));
    }

    @Test
    public void testVerifyDaysApart() {
        int unknown = -1, apart = 1, within = 0;

        MphInput i1 = new MphInput(), i2 = new MphInput();
        //if one of the diagnosis year is unknown or invalid or future year, unknown
        Assert.assertEquals(unknown, MphGroup.verifyDaysApart(i1, i2, 60));
        i1.setDateOfDiagnosisYear("Invalid");
        i2.setDateOfDiagnosisYear("2002");
        Assert.assertEquals(unknown, MphGroup.verifyDaysApart(i1, i2, 60));
        i1.setDateOfDiagnosisYear(String.valueOf(LocalDate.now().getYear() + 1));
        Assert.assertEquals(unknown, MphGroup.verifyDaysApart(i1, i2, 60));

        i1 = new MphInput();
        i2 = new MphInput();
        i1.setDateOfDiagnosisYear("2000");
        i2.setDateOfDiagnosisYear("2002");
        Assert.assertEquals(apart, MphGroup.verifyDaysApart(i1, i2, 60));
        i1.setDateOfDiagnosisYear("2004");
        i2.setDateOfDiagnosisYear("2002");
        Assert.assertEquals(apart, MphGroup.verifyDaysApart(i1, i2, 21));

        i1 = new MphInput();
        i2 = new MphInput();
        i1.setDateOfDiagnosisYear("2001");
        i2.setDateOfDiagnosisYear("2002");
        Assert.assertEquals(unknown, MphGroup.verifyDaysApart(i1, i2, 60));
        Assert.assertEquals(unknown, MphGroup.verifyDaysApart(i1, i2, 21));

        i1 = new MphInput();
        i2 = new MphInput();
        i1.setDateOfDiagnosisYear("2001");
        i1.setDateOfDiagnosisMonth("10");
        i2.setDateOfDiagnosisYear("2002");
        Assert.assertEquals(apart, MphGroup.verifyDaysApart(i1, i2, 60));
        Assert.assertEquals(apart, MphGroup.verifyDaysApart(i1, i2, 21));

        i1 = new MphInput();
        i2 = new MphInput();
        i1.setDateOfDiagnosisYear("2001");
        i1.setDateOfDiagnosisMonth("11");
        i2.setDateOfDiagnosisYear("2002");
        Assert.assertEquals(unknown, MphGroup.verifyDaysApart(i1, i2, 60));
        Assert.assertEquals(apart, MphGroup.verifyDaysApart(i1, i2, 21));

        i1 = new MphInput();
        i2 = new MphInput();
        i1.setDateOfDiagnosisYear("2001");
        i1.setDateOfDiagnosisMonth("12");
        i2.setDateOfDiagnosisYear("2002");
        Assert.assertEquals(unknown, MphGroup.verifyDaysApart(i1, i2, 60));
        Assert.assertEquals(unknown, MphGroup.verifyDaysApart(i1, i2, 21));

        i1 = new MphInput();
        i2 = new MphInput();
        i1.setDateOfDiagnosisYear("2001");
        i1.setDateOfDiagnosisMonth("12");
        i2.setDateOfDiagnosisYear("2002");
        i2.setDateOfDiagnosisMonth("01");
        Assert.assertEquals(unknown, MphGroup.verifyDaysApart(i1, i2, 60));
        Assert.assertEquals(unknown, MphGroup.verifyDaysApart(i1, i2, 21));

        i1 = new MphInput();
        i2 = new MphInput();
        i1.setDateOfDiagnosisYear("2001");
        i1.setDateOfDiagnosisMonth("12");
        i2.setDateOfDiagnosisYear("2002");
        i2.setDateOfDiagnosisMonth("01");
        i2.setDateOfDiagnosisDay("01");
        Assert.assertEquals(within, MphGroup.verifyDaysApart(i1, i2, 60));
        Assert.assertEquals(unknown, MphGroup.verifyDaysApart(i1, i2, 21));

        i1 = new MphInput();
        i2 = new MphInput();
        i1.setDateOfDiagnosisYear("2001");
        i1.setDateOfDiagnosisMonth("08");
        i2.setDateOfDiagnosisYear("2002");
        Assert.assertEquals(apart, MphGroup.verifyDaysApart(i1, i2, 60));
        Assert.assertEquals(apart, MphGroup.verifyDaysApart(i1, i2, 21));

        i1 = new MphInput();
        i2 = new MphInput();
        i1.setDateOfDiagnosisYear("2001");
        i1.setDateOfDiagnosisMonth("12");
        i2.setDateOfDiagnosisYear("2001");
        i2.setDateOfDiagnosisMonth("12");
        Assert.assertEquals(within, MphGroup.verifyDaysApart(i1, i2, 60));
        Assert.assertEquals(unknown, MphGroup.verifyDaysApart(i1, i2, 21));

        i1 = new MphInput();
        i2 = new MphInput();
        i1.setDateOfDiagnosisYear("2001");
        i1.setDateOfDiagnosisMonth("12");
        i2.setDateOfDiagnosisYear("2001");
        i2.setDateOfDiagnosisMonth("12");
        i2.setDateOfDiagnosisDay("15");
        Assert.assertEquals(within, MphGroup.verifyDaysApart(i1, i2, 60));
        Assert.assertEquals(within, MphGroup.verifyDaysApart(i1, i2, 21));

    }

    @Test
    public void testVerifyYearsApart() {
        int unknown = -1, yes = 1, no = 0;
        MphInput i1 = new MphInput(), i2 = new MphInput();
        //if one of the diagnosis year is unknown or invalid or future year, unknown
        Assert.assertEquals(unknown, MphGroup.verifyYearsApart(i1, i2, 3));
        i1.setDateOfDiagnosisYear("Invalid");
        i2.setDateOfDiagnosisYear("2002");
        Assert.assertEquals(unknown, MphGroup.verifyYearsApart(i1, i2, 3));
        i1.setDateOfDiagnosisYear(String.valueOf(LocalDate.now().getYear() + 1));
        Assert.assertEquals(unknown, MphGroup.verifyYearsApart(i1, i2, 3));
        i1.setDateOfDiagnosisYear("2000");
        Assert.assertEquals(no, MphGroup.verifyYearsApart(i1, i2, 3));
        i1.setDateOfDiagnosisYear("2006");
        Assert.assertEquals(yes, MphGroup.verifyYearsApart(i1, i2, 3));
        i1.setDateOfDiagnosisYear("2005");
        Assert.assertEquals(unknown, MphGroup.verifyYearsApart(i1, i2, 3));
        i1.setDateOfDiagnosisMonth("8");
        i2.setDateOfDiagnosisMonth("9");
        Assert.assertEquals(no, MphGroup.verifyYearsApart(i1, i2, 3));
        i2.setDateOfDiagnosisMonth("7");
        Assert.assertEquals(yes, MphGroup.verifyYearsApart(i1, i2, 3));
        i2.setDateOfDiagnosisMonth("8");
        Assert.assertEquals(unknown, MphGroup.verifyYearsApart(i1, i2, 3));
        i1.setDateOfDiagnosisDay("3");
        i2.setDateOfDiagnosisDay("4");
        Assert.assertEquals(no, MphGroup.verifyYearsApart(i1, i2, 3));
        i2.setDateOfDiagnosisDay("1");
        Assert.assertEquals(yes, MphGroup.verifyYearsApart(i1, i2, 3));
    }

    @Test
    public void testCompareDxDate() {
        int tumor1 = 1, tumor2 = 2, sameDay = 0, unknown = -1;
        MphInput i1 = new MphInput(), i2 = new MphInput();
        i1.setDateOfDiagnosisYear("2006");
        i1.setDateOfDiagnosisMonth("01");
        i1.setDateOfDiagnosisDay("01");
        i2.setDateOfDiagnosisYear("2006");
        i2.setDateOfDiagnosisMonth("01");
        i2.setDateOfDiagnosisDay("01");
        Assert.assertEquals(sameDay, MphGroup.compareDxDate(i1, i2));
        i1.setDateOfDiagnosisDay("02");
        Assert.assertEquals(tumor1, MphGroup.compareDxDate(i1, i2));
        i2.setDateOfDiagnosisMonth("02");
        Assert.assertEquals(tumor2, MphGroup.compareDxDate(i1, i2));
        i1.setDateOfDiagnosisYear("2007");
        Assert.assertEquals(tumor1, MphGroup.compareDxDate(i1, i2));
        i1.setDateOfDiagnosisYear("2007");
        i2.setDateOfDiagnosisYear("2006");
        i1.setDateOfDiagnosisMonth("xx");
        i1.setDateOfDiagnosisDay("33");
        i2.setDateOfDiagnosisMonth("18");
        i2.setDateOfDiagnosisDay("01");
        Assert.assertEquals(tumor1, MphGroup.compareDxDate(i1, i2));
        i1.setDateOfDiagnosisYear("2006");
        i2.setDateOfDiagnosisYear("2006");
        i1.setDateOfDiagnosisMonth("01");
        i1.setDateOfDiagnosisDay("xx");
        i2.setDateOfDiagnosisMonth("02");
        i2.setDateOfDiagnosisDay("30");
        Assert.assertEquals(tumor2, MphGroup.compareDxDate(i1, i2));
        i1.setDateOfDiagnosisYear("2006");
        i2.setDateOfDiagnosisYear("2006");
        i1.setDateOfDiagnosisMonth("02");
        i1.setDateOfDiagnosisDay("01");
        i2.setDateOfDiagnosisMonth("02");
        i2.setDateOfDiagnosisDay("30"); //invalid day for February
        Assert.assertEquals(unknown, MphGroup.compareDxDate(i1, i2));
        i1.setDateOfDiagnosisYear("2006");
        i2.setDateOfDiagnosisYear("2006");
        i1.setDateOfDiagnosisMonth("13"); //invalid month
        i1.setDateOfDiagnosisDay("01");
        i2.setDateOfDiagnosisMonth("12");
        i2.setDateOfDiagnosisDay("01");
        Assert.assertEquals(unknown, MphGroup.compareDxDate(i1, i2));
        i1.setDateOfDiagnosisYear("2006");
        i2.setDateOfDiagnosisYear("2006");
        i1.setDateOfDiagnosisMonth("xx"); //invalid month
        i1.setDateOfDiagnosisDay("cc");
        i2.setDateOfDiagnosisMonth("99");
        i2.setDateOfDiagnosisDay("01");
        Assert.assertEquals(unknown, MphGroup.compareDxDate(i1, i2));
        //one is future year, return unknown
        i1.setDateOfDiagnosisYear(String.valueOf(LocalDate.now().getYear()));
        i2.setDateOfDiagnosisYear(String.valueOf(LocalDate.now().getYear() + 1));
        i1.setDateOfDiagnosisMonth("01");
        i1.setDateOfDiagnosisDay("01");
        i2.setDateOfDiagnosisMonth("01");
        i2.setDateOfDiagnosisDay("01");
        Assert.assertEquals(unknown, MphGroup.compareDxDate(i1, i2));
    }
}
