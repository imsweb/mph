/*
 * Copyright (C) 2019 Information Management Services, Inc.
 */
package com.imsweb.mph.mprules;

import com.imsweb.mph.MphConstants;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphUtils;
import com.imsweb.mph.RuleExecutionContext;
import com.imsweb.mph.internal.TempRuleResult;
import com.imsweb.mph.mpgroups.GroupUtility;

public class MpRuleYearsApart extends MphRule {

    private int _year = 0;

    public MpRuleYearsApart(String groupName, String step, int year) {
        super(groupName, step);
        _year = year;
        setQuestion("Are there tumors diagnosed more than " + year + " years apart?");
        setReason("Tumors diagnosed more than " + year + " years apart are multiple primaries.");
    }

    @Override
    public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
        TempRuleResult result = new TempRuleResult();
        int diff = GroupUtility.verifyYearsApart(i1, i2, _year);
        if (MphConstants.DATE_VERIFY_UNKNOWN == diff) {
            result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
            result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupName());
        }
        else if (MphConstants.DATE_VERIFY_APART == diff)
            result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);

        return result;
    }
}
