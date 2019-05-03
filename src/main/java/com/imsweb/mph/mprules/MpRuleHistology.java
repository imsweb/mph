/*
 * Copyright (C) 2019 Information Management Services, Inc.
 */
package com.imsweb.mph.mprules;

import com.imsweb.mph.MphComputeOptions;
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
    public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
        TempRuleResult result = new TempRuleResult();
        String hist1 = i1.getHistology(), hist2 = i2.getHistology();
        //If lenient mode is on 8000 is considered as same histology as 8nnn histologies
        if (MphComputeOptions.MpHistologyMatching.LENIENT.equals(options.getHistologyMatchingMode()) && (("8000".equals(hist1) && hist2.startsWith("8")) || ("8000".equals(hist2) && hist1
                .startsWith("8"))))
            return result;
        if (!hist1.substring(0, 3).equals(hist2.substring(0, 3)))
            result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
        return result;
    }
}
