/*
 * Copyright (C) 2025 Information Management Services, Inc.
 */
package com.imsweb.mph.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class CsvUtils {

    private CsvUtils() {
        // static utility class
    }

    public static String writeCsvValues(String[] values) {
        StringBuilder buf = new StringBuilder();
        for (String value : values) {
            if (buf.length() > 0)
                buf.append(',');
            if (value != null && value.contains(","))
                buf.append("\"").append(value.replace("\"", "\"\"")).append("\"");
            else if (value != null)
                buf.append(value);
        }
        return buf.toString();
    }

    public static Map<String, List<HematoDTO>> parseHematoCsvFile(String filename) {
        Map<String, List<HematoDTO>> result = new LinkedHashMap<>();

        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename)) {
            if (is == null)
                throw new IllegalStateException("Unable to read " + filename + "; unable to find data file");
            int expectedColumns = -1;
            try (LineNumberReader reader = new LineNumberReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                String line = reader.readLine();
                while (line != null) {
                    if (reader.getLineNumber() == 1)
                        expectedColumns = CsvUtils.parseCsvLine(reader.getLineNumber(), line).size();
                    else {
                        List<String> fields = CsvUtils.parseCsvLine(reader.getLineNumber(), line);

                        if (expectedColumns != -1 && fields.size() != expectedColumns)
                            throw new IOException("Line " + reader.getLineNumber() + ": expected " + expectedColumns + " columns, but found " + fields.size() + " columns");

                        Short validStartYear = fields.get(1) != null && !fields.get(1).trim().isEmpty() ? Short.valueOf(fields.get(1)) : null;
                        Short validEndYear = fields.get(1) != null && !fields.get(2).trim().isEmpty() ? Short.valueOf(fields.get(2)) : null;
                        Short startYear = fields.get(1) != null && !fields.get(3).trim().isEmpty() ? Short.valueOf(fields.get(3)) : null;
                        Short endYear = fields.get(1) != null && !fields.get(4).trim().isEmpty() ? Short.valueOf(fields.get(4)) : null;
                        if (result.containsKey(fields.get(0)))
                            result.get(fields.get(0)).add(new HematoDTO(validStartYear, validEndYear, startYear, endYear, fields.get(5)));
                        else {
                            List<HematoDTO> list = new ArrayList<>();
                            list.add(new HematoDTO(validStartYear, validEndYear, startYear, endYear, fields.get(5)));
                            result.put(fields.get(0), list);
                        }

                    }
                    line = reader.readLine();
                }
            }
        }
        catch (IOException e) {
            throw new IllegalStateException(e);
        }

        return result;
    }

    public static List<String[]> parseGroupCsvFile(String filename) {
        List<String[]> result = new ArrayList<>();

        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename)) {
            if (is == null)
                throw new IllegalStateException("Unable to read " + filename + "; unable to find data file");
            int expectedColumns = -1;
            try (LineNumberReader reader = new LineNumberReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                String line = reader.readLine();
                while (line != null) {
                    if (reader.getLineNumber() == 1)
                        expectedColumns = CsvUtils.parseCsvLine(reader.getLineNumber(), line).size();
                    else {
                        List<String> fields = CsvUtils.parseCsvLine(reader.getLineNumber(), line);
                        if (expectedColumns != -1 && fields.size() != expectedColumns)
                            throw new IOException("Line " + reader.getLineNumber() + ": expected " + expectedColumns + " columns, but found " + fields.size() + " columns");
                        result.add(fields.toArray(new String[0]));
                    }
                    line = reader.readLine();
                }
            }
        }
        catch (IOException e) {
            throw new IllegalStateException(e);
        }

        return result;
    }

    public static List<String> parseCsvLine(int lineNumber, String line) throws IOException {
        List<String> result = new ArrayList<>();

        char cQuote = '"';
        char cDelimiter = ',';
        int curIndex = 0;
        int nextQuote;
        int nextDelimiter;

        StringBuilder buf = new StringBuilder();
        buf.append(cQuote);
        String singleQuotes = buf.toString();
        buf.append(cQuote);
        String doubleQuotes = buf.toString();

        String value;
        while (curIndex < line.length()) {
            if (line.charAt(curIndex) == cQuote) {
                // handle quoted value
                nextQuote = getNextSingleQuote(line, cQuote, curIndex);
                if (nextQuote < 0)
                    throw new IOException("Line " + lineNumber + ": found an unmatched quote");
                else {
                    result.add(line.substring(curIndex + 1, nextQuote).replace(doubleQuotes, singleQuotes));
                    // update the current index to be after delimiter, after the ending quote
                    curIndex = nextQuote;
                    if (curIndex + 1 < line.length()) {
                        // if there is a next value, set current index to be after delimiter
                        if (line.charAt(curIndex + 1) == cDelimiter) {
                            curIndex += 2;
                            // handle case where last value is empty
                            if (curIndex == line.length())
                                result.add("");
                        }
                        // else character after ending quote is not EOL and not delimiter, stop parsing
                        else
                            throw new IOException("Line " + lineNumber + ": expected a delimiter after the quote");
                    }
                    else
                        // end of line is after ending quote, stop parsing
                        curIndex++;
                }
            }
            else {
                // handle unquoted value
                nextDelimiter = getNextDelimiter(line, cDelimiter, curIndex);
                value = line.substring(curIndex, nextDelimiter).replace(doubleQuotes, singleQuotes);
                // unquoted values should not contain any quotes
                if (value.contains(singleQuotes))
                    throw new IOException("Line " + lineNumber + ": value contains some quotes but does not start with a quote");
                else {
                    result.add(value);
                    curIndex = nextDelimiter + 1;
                    // handle case where last value is empty
                    if (curIndex == line.length())
                        result.add("");
                }
            }
        }

        return result;
    }

    private static int getNextSingleQuote(String line, char quote, int from) {
        if (from >= line.length())
            return -1;

        int index = from + 1;
        boolean found = false;
        while ((index < line.length()) && !found) {
            if (line.charAt(index) != quote)
                index++;
            else {
                if ((index + 1 == line.length()) || (line.charAt(index + 1) != quote))
                    found = true;
                else
                    index += 2;
            }

        }

        index = (index == line.length()) ? -1 : index;

        return index;
    }

    private static int getNextDelimiter(String line, char delimiter, int from) {
        if (from >= line.length())
            return line.length();

        int index = from;
        while ((index < line.length()) && (line.charAt(index) != delimiter))
            index++;

        return index;
    }

}
