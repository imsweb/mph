/*
 * Copyright (C) 2019 Information Management Services, Inc.
 */
package com.imsweb.mph.mprules;

import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphUtils;
import com.imsweb.mph.internal.TempRuleResult;

public class MpRuleHistology extends MphRule {

    public MpRuleHistology(String groupId, String step) {
        super(groupId, step);
        setQuestion("Do the tumors have ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number?");
        setReason("Tumors with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.");
    }

    @Override
    public TempRuleResult apply(MphInput i1, MphInput i2) {
        TempRuleResult result = new TempRuleResult();
        if (!i1.getHistology().substring(0, 3).equals(i2.getHistology().substring(0, 3)))
            result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
        return result;
    }
}
