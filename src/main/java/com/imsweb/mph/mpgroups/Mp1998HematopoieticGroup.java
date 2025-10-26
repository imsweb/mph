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

public class Mp1998HematopoieticGroup extends MphGroup {

    private static final List<String[]> _HEMATOPOIETIC_1998 = new ArrayList<>();

    public Mp1998HematopoieticGroup() {
        super(MphConstants.HEMATO_2000_AND_EARLIER, MphConstants.HEMATOPOIETIC_AND_LYMPHOID_2000_AND_EARLIER, "C000-C809", null, "9590-9993", null, "2-3,6", "0000-2000");
        initializeLookup();

        MphRule rule = new MphRule(MphConstants.HEMATOPOIETIC_AND_LYMPHOID_2000_AND_EARLIER, "") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
                TempRuleResult result = new TempRuleResult();
                if (i1.getHistology().equals(i2.getHistology())) {
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                    result.setMessage("Single primary based on SEER 1998 multiple primary rules for hematopoietic cancer.");
                    return result;
                }

                int laterDx = GroupUtility.compareDxDate(i1, i2);
                if (MphConstants.COMPARE_DX_UNKNOWN == laterDx) {
                    result.setFinalResult(MphUtils.MpResult.QUESTIONABLE);
                    result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupName());
                    return result;
                }
                String firstDx = MphConstants.COMPARE_DX_FIRST_LATEST == laterDx ? i2.getHistology() : i1.getHistology();
                String secondDx = MphConstants.COMPARE_DX_FIRST_LATEST == laterDx ? i1.getHistology() : i2.getHistology();
                for (String[] row : _HEMATOPOIETIC_1998)
                    if ((firstDx.compareTo(row[0]) >= 0 && firstDx.compareTo(row[1]) <= 0 && secondDx.compareTo(row[2]) >= 0 && secondDx.compareTo(row[3]) <= 0) ||
                            (MphConstants.COMPARE_DX_EQUAL == laterDx && (secondDx.compareTo(row[0]) >= 0 && secondDx.compareTo(row[1]) <= 0 && firstDx.compareTo(row[2]) >= 0 && firstDx.compareTo(
                                    row[3]) <= 0))) {
                        result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        result.setMessage("Single primary based on SEER 1998 multiple primary rules for hematopoietic cancer.");
                        return result;
                    }

                //if they don't match
                result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                result.setMessage("Multiple primaries based on SEER 1998 multiple primary rules for hematopoietic cancer.");
                return result;
            }
        };
        _rules.add(rule);
    }

    private static synchronized void initializeLookup() {
        if (_HEMATOPOIETIC_1998.isEmpty())
            _HEMATOPOIETIC_1998.addAll(CsvUtils.parseGroupCsvFile("Hematopoietic1998HistologyPairs.csv"));
    }
}
