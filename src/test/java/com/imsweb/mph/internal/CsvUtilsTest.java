/*
 * Copyright (C) 2025 Information Management Services, Inc.
 */
package com.imsweb.mph.internal;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class CsvUtilsTest {

    @Test
    public void textWriteCsvValues() {
        Assert.assertEquals("1", CsvUtils.writeCsvValues(new String[] {"1"}));
        Assert.assertEquals("1,2,3", CsvUtils.writeCsvValues(new String[] {"1", "2", "3"}));
        Assert.assertEquals("1,,3", CsvUtils.writeCsvValues(new String[] {"1", null, "3"}));
        Assert.assertEquals("\"1,2,3\"", CsvUtils.writeCsvValues(new String[] {"1,2,3"}));
        Assert.assertEquals("\"1,\"\"2\"\",3\"", CsvUtils.writeCsvValues(new String[] {"1,\"2\",3"}));
    }

    @Test
    public void testParseHematoCsvFile() {
        Map<String, List<HematoDTO>> result = CsvUtils.parseHematoCsvFile("hematopoietic-pairs-test.csv");
        Assert.assertEquals(2, result.size());
        Assert.assertEquals(1, result.get("9742/3").size());
        Assert.assertEquals(2, result.get("9741/3").size());

        try {
            CsvUtils.parseHematoCsvFile("hematopoietic-pairs-test-invalid.csv");
            Assert.fail("Should have been an exception!");
        }
        catch (RuntimeException e) {
            // expected
        }

        try {
            CsvUtils.parseHematoCsvFile("UNKNOWN");
            Assert.fail("Should have been an exception!");
        }
        catch (RuntimeException e) {
            // expected
        }
    }

    @Test
    public void testParseGroupCsvFile() {
        List<String[]> result = CsvUtils.parseGroupCsvFile("hematopoietic-groups-test.csv");
        Assert.assertEquals(2, result.size());

        try {
            CsvUtils.parseGroupCsvFile("hematopoietic-groups-test-invalid.csv");
            Assert.fail("Should have been an exception!");
        }
        catch (RuntimeException e) {
            // expected
        }

        try {
            CsvUtils.parseGroupCsvFile("UNKNOWN");
            Assert.fail("Should have been an exception!");
        }
        catch (RuntimeException e) {
            // expected
        }
    }

    @Test
    public void testParseCsvLine() throws IOException {
        Assert.assertEquals(Collections.singletonList("1"), CsvUtils.parseCsvLine(1, "1"));
        Assert.assertEquals(Arrays.asList("1", "2", "3"), CsvUtils.parseCsvLine(1, "1,2,3"));
        Assert.assertEquals(Arrays.asList("1", "", "3"), CsvUtils.parseCsvLine(1, "1,,3"));
        Assert.assertEquals(Collections.singletonList("1,2,3"), CsvUtils.parseCsvLine(1, "\"1,2,3\""));
        Assert.assertEquals(Collections.singletonList("1,\"2\",3"), CsvUtils.parseCsvLine(1, "\"1,\"\"2\"\",3\""));

        assertFailedParsing(1, "\"1,2,3");
        assertFailedParsing(2, "1,2,3\"");
        assertFailedParsing(3, "1,\"2\"x,3");
    }

    private void assertFailedParsing(int lineNumber, String line) {
        try {
            CsvUtils.parseCsvLine(lineNumber, line);
            Assert.fail("Should have been an exception!");
        }
        catch (IOException e) {
            Assert.assertTrue(e.getMessage().contains("Line " + lineNumber));
        }
    }
}
