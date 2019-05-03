/*
 * Copyright (C) 2019 Information Management Services, Inc.
 */
package com.imsweb.mph.mprules;

import com.imsweb.mph.MphComputeOptions;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphUtils;
import com.imsweb.mph.internal.TempRuleResult;

public class MpRuleNoCriteriaSatisfied extends MphRule {

    public MpRuleNoCriteriaSatisfied(String groupId, String step) {
        super(groupId, step);
        setQuestion("Does not meet any of the criteria?");
        setReason("Tumors that do not meet any of the criteria are abstracted as a single primary.");
    }

    @Override
    public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
        TempRuleResult result = new TempRuleResult();
        result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
        return result;
    }
}
