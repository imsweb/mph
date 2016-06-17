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
        MphRule rule = new MphRule("hematopoietic-1998", "M1", MphUtils.MPResult.SINGLE_PRIMARY) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                initializeLookup();
                MphRuleResult result = new MphRuleResult();
                result.setResult(MphUtils.RuleResult.TRUE);
                String hist1 = i1.getHistology(), hist2 = i2.getHistology();
                for (String[] row : _1998_HEMATOPOIETIC)
                    if ((hist1.compareTo(row[0]) >= 0 && hist1.compareTo(row[1]) <= 0 && hist2.compareTo(row[2]) >= 0 && hist2.compareTo(row[3]) <= 0) ||
                            (hist2.compareTo(row[0]) >= 0 && hist2.compareTo(row[1]) <= 0 && hist1.compareTo(row[2]) >= 0 && hist1.compareTo(row[3]) <= 0))
                        return result;

                //if they don't match
                this.setResult(MphUtils.MPResult.MULTIPLE_PRIMARIES);
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
