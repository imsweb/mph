/*
 * Copyright (C) 2019 Information Management Services, Inc.
 */
package com.imsweb.mph.mprules;

import java.util.Arrays;
import java.util.HashSet;

import com.imsweb.mph.MphConstants;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphUtils;
import com.imsweb.mph.internal.TempRuleResult;

public class MpRuleRetinoblastoma extends MphRule {

    public MpRuleRetinoblastoma(String groupName, String step) {
        super(groupName, step);
        setQuestion("Is the diagnosis retinoblastoma (unilateral or bilateral)?");
        setReason("Retinoblastoma is always a single primary (unilateral or bilateral).");
    }

    @Override
    public TempRuleResult apply(MphInput i1, MphInput i2) {
        TempRuleResult result = new TempRuleResult();
        if (new HashSet<>(MphConstants.RETINO_BLASTOMA).containsAll(Arrays.asList(i1.getHistology(), i2.getHistology())))
            result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
        return result;
    }
}
