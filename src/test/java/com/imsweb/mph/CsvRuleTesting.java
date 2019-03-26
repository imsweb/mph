/*
 * Copyright (C) 2018 Information Management Services, Inc.
 */
package com.imsweb.mph;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.opencsv.CSVReaderBuilder;

import com.imsweb.mph.MphUtils.MpResult;

public class CsvRuleTesting {

    private MphUtils _utils = MphUtils.getInstance();

    @BeforeClass
    public static void setUp() {
        // Create the MphUtils
        MphUtils.initialize(new DefaultHematoDbUtilsProvider());

    }

    @Test
    public void testCsvCases() {
        try (Reader reader = new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("MPH_test_cases.csv"), StandardCharsets.US_ASCII)) {
            int line = 2;
            for (String[] row : new CSVReaderBuilder(reader).withSkipLines(1).build().readAll()) {
                if (row[0].length() > 0) {
                    MphInput i1 = new MphInput(), i2 = new MphInput();
                    i1.setPrimarySite(row[0].trim());
                    i1.setHistologyIcdO3(row[1].trim());
                    i1.setBehaviorIcdO3(row[2].trim());
                    i1.setLaterality(row[3].trim());
                    i1.setDateOfDiagnosisYear(row[4].trim());
                    i1.setDateOfDiagnosisMonth(row[5].trim());
                    i1.setDateOfDiagnosisDay(row[6].trim());

                    i2.setPrimarySite(row[7].trim());
                    i2.setHistologyIcdO3(row[8].trim());
                    i2.setBehaviorIcdO3(row[9].trim());
                    i2.setLaterality(row[10].trim());
                    i2.setDateOfDiagnosisYear(row[11].trim());
                    i2.setDateOfDiagnosisMonth(row[12].trim());
                    i2.setDateOfDiagnosisDay(row[13].trim());
                    MphOutput output = _utils.computePrimaries(i1, i2);

                    if (!resToStr(output.getResult()).equals(row[14].trim()) || !row[16].trim().equals(output.getStep()))
                        Assert.fail("Result is not the same for line : " + line);
                }
                line++;
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String resToStr(MpResult result) {
        if (MphUtils.MpResult.SINGLE_PRIMARY.equals(result))
            return "Single Primary";
        if (MphUtils.MpResult.MULTIPLE_PRIMARIES.equals(result))
            return "Multiple Primaries";
        if (MphUtils.MpResult.QUESTIONABLE.equals(result))
            return "Questionable";
        return "";
    }
}



