/*
 * Copyright (C) 2019 Information Management Services, Inc.
 */
package com.imsweb.mph.mprules;

import com.imsweb.mph.MphComputeOptions;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphUtils;
import com.imsweb.mph.internal.TempRuleResult;
import com.imsweb.mph.mpgroups.GroupUtility;

public class MpRuleFiveYearsApart extends MphRule {

    public MpRuleFiveYearsApart(String groupId, String step) {
        super(groupId, step);
        setQuestion("Are there tumors diagnosed more than five (5) years apart?");
        setReason("Tumors diagnosed more than five (5) years apart are multiple primaries.");
    }

    @Override
    public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
        TempRuleResult result = new TempRuleResult();
        int diff = GroupUtility.verifyYearsApart(i1, i2, 5);
        if (-1 == diff) {
            result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
            result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupId());
        }
        else if (1 == diff)
            result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);

        return result;
    }
}
