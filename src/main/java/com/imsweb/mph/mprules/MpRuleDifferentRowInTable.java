/*
 * Copyright (C) 2019 Information Management Services, Inc.
 */
package com.imsweb.mph.mprules;

import java.util.Map;

import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphUtils.MpResult;
import com.imsweb.mph.RuleExecutionContext;
import com.imsweb.mph.internal.TempRuleResult;

public class MpRuleDifferentRowInTable extends MphRule {

    private Map<String, String> _table;

    public MpRuleDifferentRowInTable(String groupName, String step, Map<String, String> table) {
        super(groupName, step);
        _table = table;
    }

    @Override
    public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
        TempRuleResult result = new TempRuleResult();
        String h1 = i1.getHistology();
        String icd1 = i1.getIcdCode();
        String h2 = i2.getHistology();
        String icd2 = i2.getIcdCode();
        //If they are same code, no need to check if they are in different rows.
        if (icd1.equals(icd2))
            return result;
        String row1 = _table.containsKey(h1) ? _table.get(h1) : _table.get(icd1);
        String row2 = _table.containsKey(h2) ? _table.get(h2) : _table.get(icd2);
        if (row1 == null || row2 == null) {
            result.setFinalResult(MpResult.QUESTIONABLE);
            result.setMessageNotInTable(this.getStep(), this.getGroupName(), row1, row2, icd1, icd2);
        }
        else if (!row1.equals(row2))
            result.setFinalResult(MpResult.MULTIPLE_PRIMARIES);
        return result;
    }
}
