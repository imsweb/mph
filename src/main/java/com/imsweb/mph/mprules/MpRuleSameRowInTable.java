/*
 * Copyright (C) 2019 Information Management Services, Inc.
 */
package com.imsweb.mph.mprules;

import java.util.Map;

import com.imsweb.mph.MphConstants;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphUtils;
import com.imsweb.mph.MphUtils.MpResult;
import com.imsweb.mph.RuleExecutionContext;
import com.imsweb.mph.internal.TempRuleResult;
import com.imsweb.mph.mpgroups.GroupUtility;

public class MpRuleSameRowInTable extends MphRule {

    private Map<String, String> _table;
    private boolean _synchronous;

    public MpRuleSameRowInTable(String groupName, String step, Map<String, String> table, boolean synchronous) {
        super(groupName, step);
        _table = table;
        _synchronous = synchronous;
    }

    @Override
    public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
        TempRuleResult result = new TempRuleResult();
        String h1 = i1.getHistology();
        String icd1 = i1.getIcdCode();
        String h2 = i2.getHistology();
        String icd2 = i2.getIcdCode();
        String row1 = _table.containsKey(h1) ? _table.get(h1) : _table.get(icd1);
        String row2 = _table.containsKey(h2) ? _table.get(h2) : _table.get(icd2);
        if (!GroupUtility.sameHistologies(icd1, icd2) && (row1 == null || row2 == null)) {
            result.setFinalResult(MpResult.QUESTIONABLE);
            result.setMessageNotInTable(this.getStep(), this.getGroupName(), row1, row2, icd1, icd2);
        }
        else if (GroupUtility.sameHistologies(icd1, icd2) || row1.equals(row2)) {
            if (_synchronous) {
                int diff = GroupUtility.verifyDaysApart(i1, i2, 60);
                if (MphConstants.DATE_VERIFY_UNKNOWN == diff) {
                    result.setPotentialResult(MpResult.SINGLE_PRIMARY);
                    result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupName());
                }
                else if (MphConstants.DATE_VERIFY_WITHIN == diff)
                    result.setFinalResult(MpResult.SINGLE_PRIMARY);
            }
            else
                result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
        }

        return result;
    }
}
