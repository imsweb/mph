/*
 * Copyright (C) 2016 Information Management Services, Inc.
 */
package com.imsweb.mph.mpgroups;

import java.util.ArrayList;
import java.util.List;

import com.imsweb.mph.MphConstants;
import com.imsweb.mph.MphGroup;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphUtils;
import com.imsweb.mph.RuleExecutionContext;
import com.imsweb.mph.internal.CsvUtils;
import com.imsweb.mph.internal.TempRuleResult;

public class Mp2001HematopoieticGroup extends MphGroup {

    private static final List<String[]> _2001_HEMATOPOIETIC_GROUPS = new ArrayList<>();
    private static final List<String[]> _2001_HEMATOPOIETIC_GROUP_PAIRS = new ArrayList<>();

    public Mp2001HematopoieticGroup() {
        super(MphConstants.HEMATO_2001_TO_2009, MphConstants.HEMATOPOIETIC_AND_LYMPHOID_2001_2009, "C000-C809", null, "9590-9993", null, "2-3,6", "2001-2009");
        initializeLookups();

        MphRule rule = new MphRule(MphConstants.HEMATOPOIETIC_AND_LYMPHOID_2001_2009, "") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
                TempRuleResult result = new TempRuleResult();
                String hist1 = i1.getHistology();
                String hist2 = i2.getHistology();
                if (hist1.equals(hist2)) {
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                    result.setMessage("Single primary based on SEER 2001 multiple primary rules for hematopoietic cancer.");
                    return result;
                }
                //find the group for both histologies
                String group1 = null;
                String group2 = null;
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
                    if (MphConstants.COMPARE_DX_UNKNOWN == laterDx) {
                        result.setFinalResult(MphUtils.MpResult.QUESTIONABLE);
                        result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupName());
                        return result;
                    }
                    String firstDx = MphConstants.COMPARE_DX_FIRST_LATEST == laterDx ? group2 : group1;
                    String secondDx = MphConstants.COMPARE_DX_FIRST_LATEST == laterDx ? group1 : group2;
                    for (String[] row : _2001_HEMATOPOIETIC_GROUP_PAIRS)
                        if ((firstDx.equals(row[0]) && secondDx.equals(row[1])) || (MphConstants.COMPARE_DX_EQUAL == laterDx && secondDx.equals(row[0]) && firstDx.equals(row[1]))) {
                            result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                            result.setMessage("Single primary based on SEER 2001 multiple primary rules for hematopoietic cancer.");
                            return result;
                        }
                }
                result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                result.setMessage("Multiple primaries based on SEER 2001 multiple primary rules for hematopoietic cancer.");
                return result;
            }
        };
        _rules.add(rule);
    }

    private static synchronized void initializeLookups() {
        if (_2001_HEMATOPOIETIC_GROUPS.isEmpty())
            _2001_HEMATOPOIETIC_GROUPS.addAll(CsvUtils.parseGroupCsvFile("Hematopoietic2001HistologyGroups.csv"));
        if (_2001_HEMATOPOIETIC_GROUP_PAIRS.isEmpty())
            _2001_HEMATOPOIETIC_GROUP_PAIRS.addAll(CsvUtils.parseGroupCsvFile("Hematopoietic2001HistologyGroupPairs.csv"));
    }
}
