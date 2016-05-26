/*
 * Copyright (C) 2016 Information Management Services, Inc.
 */
package com.imsweb.mph;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import au.com.bytecode.opencsv.CSVReader;

public class DefaultHematoDbUtilsProvider implements HematoDbUtilsProvider {

    private static Map<String, List<HematoSamePrimaryDTO>> _SAME_PRIMARY_DTO = new HashMap<>();
    private static Pattern _MORPHOLOGY = Pattern.compile("^(\\d{4}/\\d)");

    private DefaultHematoDbUtilsProvider() {
        initializeLookup();
    }

    public static DefaultHematoDbUtilsProvider getInstance() {
        return new DefaultHematoDbUtilsProvider();
    }

    public boolean isSamePrimary(String leftCode, String rightCode, int year) {
        if (leftCode == null || rightCode == null || !_MORPHOLOGY.matcher(leftCode).matches() || !_MORPHOLOGY.matcher(rightCode).matches())
            return false;
        else if (leftCode.equals(rightCode))
            return true;
        else if (_SAME_PRIMARY_DTO.containsKey(leftCode)) {
            for (HematoSamePrimaryDTO dto : _SAME_PRIMARY_DTO.get(leftCode))
                if (dto.matches(rightCode, year))
                    return true;
        }
        else if (_SAME_PRIMARY_DTO.containsKey(rightCode)) {
            for (HematoSamePrimaryDTO dto : _SAME_PRIMARY_DTO.get(rightCode))
                if (dto.matches(leftCode, year))
                    return true;
        }
        return false;
    }

    private static synchronized void initializeLookup() {
        if (_SAME_PRIMARY_DTO.isEmpty()) {
            try {
                Reader reader = new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("Hematopoietic2010MorphologyPairs.csv"), "US-ASCII");
                List<String[]> rows = new CSVReader(reader, ',', '\"', 1).readAll();
                for (String[] row : rows) {
                    if (_SAME_PRIMARY_DTO.containsKey(row[0]))
                        _SAME_PRIMARY_DTO.get(row[0]).add(new HematoSamePrimaryDTO(Short.valueOf(row[1]), Short.valueOf(row[2]), row[3]));
                    else {
                        List<HematoSamePrimaryDTO> list = new ArrayList<>();
                        list.add(new HematoSamePrimaryDTO(Short.valueOf(row[1]), Short.valueOf(row[2]), row[3]));
                        _SAME_PRIMARY_DTO.put(row[0], list);
                    }
                }
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

}
