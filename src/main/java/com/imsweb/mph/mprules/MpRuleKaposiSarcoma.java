/*
 * Copyright (C) 2019 Information Management Services, Inc.
 */
package com.imsweb.mph.mprules;

import com.imsweb.mph.HematoDataProvider;
import com.imsweb.mph.MphConstants;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphUtils;
import com.imsweb.mph.internal.TempRuleResult;

public class MpRuleKaposiSarcoma extends MphRule {

    public MpRuleKaposiSarcoma(String groupName, String step) {
        super(groupName, step);
        setQuestion("Is the diagnosis Kaposi sarcoma (any site or sites)?");
        setReason("Kaposi sarcoma (any site or sites) is always a single primary.");
    }

    @Override
    public TempRuleResult apply(MphInput i1, MphInput i2, HematoDataProvider provider) {
        TempRuleResult result = new TempRuleResult();
        if (MphConstants.KAPOSI_SARCOMA.equals(i1.getHistology()) && MphConstants.KAPOSI_SARCOMA.equals(i2.getHistology()))
            result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
        return result;
    }
}
