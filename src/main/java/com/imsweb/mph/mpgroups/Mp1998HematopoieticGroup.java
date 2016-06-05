/*
 * Copyright (C) 2016 Information Management Services, Inc.
 */
package com.imsweb.mph.mpgroups;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

import com.imsweb.mph.MphGroup;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphRuleResult;
import com.imsweb.mph.MphUtils;

public class Mp1998HematopoieticGroup extends MphGroup {

    private static List<String[]> _1998_HEMATOPOIETIC = new ArrayList<>();

    public Mp1998HematopoieticGroup() {
        super("hematopoietic-1998", "Hematopoietic 1998", "C000-C809", null, "9590-9989", null, "2-3,6", "0000-2000");

        // If the two histologies are considered same primary
        MphRule rule = new MphRule("hematopoietic-1998", null, MphUtils.MPResult.SINGLE_PRIMARY) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                initializeLookup();
                MphRuleResult result = new MphRuleResult();
                String hist1 = i1.getHistologyIcdO2(), hist2 = i2.getHistologyIcdO2();
                for (String[] row : _1998_HEMATOPOIETIC) {
                    if ((hist1.compareTo(row[0]) >= 0 && hist1.compareTo(row[1]) <= 0 && hist2.compareTo(row[2]) >= 0 && hist2.compareTo(row[3]) <= 0) ||
                            (hist2.compareTo(row[0]) >= 0 && hist2.compareTo(row[1]) <= 0 && hist1.compareTo(row[2]) >= 0 && hist1.compareTo(row[3]) <= 0)) {
                        result.setResult(MphUtils.RuleResult.TRUE);
                        return result;
                    }
                }
                result.setResult(MphUtils.RuleResult.FALSE);
                return result;
            }
        };
        _rules.add(rule);

        //Other wise
        rule = new MphRule("hematopoietic-1998", null, MphUtils.MPResult.MULTIPLE_PRIMARIES) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                result.setResult(MphUtils.RuleResult.TRUE);
                return result;
            }
        };
        _rules.add(rule);
    }

    protected static synchronized void initializeLookup() {
        if (_1998_HEMATOPOIETIC.isEmpty()) {
            try {
                Reader reader = new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("Hematopoietic1998HistologyPairs.csv"), "US-ASCII");
                _1998_HEMATOPOIETIC.addAll(new CSVReader(reader, ',', '\"', 1).readAll());
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
