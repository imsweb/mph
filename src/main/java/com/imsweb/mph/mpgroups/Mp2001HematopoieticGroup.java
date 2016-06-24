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

import com.imsweb.mph.MphConstants;
import com.imsweb.mph.MphGroup;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphRuleResult;
import com.imsweb.mph.MphUtils;

public class Mp2001HematopoieticGroup extends MphGroup {

    private static List<String[]> _2001_HEMATOPOIETIC_GROUPS = new ArrayList<>();
    private static List<String[]> _2001_HEMATOPOIETIC_GROUP_PAIRS = new ArrayList<>();

    public Mp2001HematopoieticGroup() {
        super(MphConstants.MP_2001_HEMATO_GROUP_ID, MphConstants.MP_2001_HEMATO_GROUP_NAME, "C000-C809", null, "9590-9989", null, "2-3,6", "2001-2009");

        MphRule rule = new MphRule(MphConstants.MP_2001_HEMATO_GROUP_ID, "M1", MphUtils.MPResult.SINGLE_PRIMARY) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                initializeLookups();
                this.setResult(MphUtils.MPResult.SINGLE_PRIMARY);
                MphRuleResult result = new MphRuleResult();
                result.setResult(MphUtils.RuleResult.TRUE);
                String hist1 = i1.getHistology(), hist2 = i2.getHistology();
                //find the group for both histologies
                String group1 = null, group2 = null;
                for (String[] row : _2001_HEMATOPOIETIC_GROUPS) {
                    if (group1 == null && hist1.compareTo(row[1]) >= 0 && hist1.compareTo(row[2]) <= 0)
                        group1 = row[0];
                    if (group2 == null && hist2.compareTo(row[1]) >= 0 && hist2.compareTo(row[2]) <= 0)
                        group2 = row[0];
                    if (group1 != null && group2 != null)
                        break;
                }
                //If we found both groups, let's check if they are same primaries
                if (group1 != null && group2 != null) {
                    int laterDx = GroupUtility.compareDxDate(i1, i2);
                    if (laterDx == -1) {
                        result.setResult(MphUtils.RuleResult.UNKNOWN);
                        result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". Valid and known diagnosis date should be provided.");
                    }
                    String firstDx = laterDx == 1 ? group2 : group1, secondDx = laterDx == 1 ? group1 : group2;
                    for (String[] row : _2001_HEMATOPOIETIC_GROUP_PAIRS)
                        if ((firstDx.equals(row[0]) && secondDx.equals(row[1])) || (laterDx == 0 && secondDx.equals(row[0]) && firstDx.equals(row[1])))
                            return result;
                }

                //If they don't match
                this.setResult(MphUtils.MPResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        _rules.add(rule);
    }

    private static synchronized void initializeLookups() {
        if (_2001_HEMATOPOIETIC_GROUPS.isEmpty() || _2001_HEMATOPOIETIC_GROUP_PAIRS.isEmpty()) {
            try {
                Reader reader = new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("Hematopoietic2001HistologyGroups.csv"), "US-ASCII");
                _2001_HEMATOPOIETIC_GROUPS.addAll(new CSVReader(reader, ',', '\"', 1).readAll());
                reader = new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("Hematopoietic2001HistologyGroupPairs.csv"), "US-ASCII");
                _2001_HEMATOPOIETIC_GROUP_PAIRS.addAll(new CSVReader(reader, ',', '\"', 1).readAll());
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
