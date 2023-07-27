/*
 * Copyright (C) 2019 Information Management Services, Inc.
 */
package com.imsweb.mph.mprules;

import com.imsweb.mph.HematoDataProvider;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphUtils;
import com.imsweb.mph.internal.TempRuleResult;

public class MpRulePrimarySite extends MphRule {

    public MpRulePrimarySite(String groupName, String step) {
        super(groupName, step);
        setQuestion("Are there tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx) and/or third character (Cx?x)?");
        setReason("Tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx) and/or third (Cx?x) character are multiple primaries.");
    }

    @Override
    public TempRuleResult apply(MphInput i1, MphInput i2, HematoDataProvider provider) {
        TempRuleResult result = new TempRuleResult();
        if (!i1.getPrimarySite().substring(1, 3).equals(i2.getPrimarySite().substring(1, 3)))
            result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
        return result;
    }
}
