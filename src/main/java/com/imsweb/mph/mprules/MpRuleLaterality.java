/*
 * Copyright (C) 2019 Information Management Services, Inc.
 */
package com.imsweb.mph.mprules;

import com.imsweb.mph.HematoDataProvider;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphUtils;
import com.imsweb.mph.internal.TempRuleResult;
import com.imsweb.mph.mpgroups.GroupUtility;

public class MpRuleLaterality extends MphRule {

    public MpRuleLaterality(String groupName, String step) {
        super(groupName, step);
    }

    @Override
    public TempRuleResult apply(MphInput i1, MphInput i2, HematoDataProvider provider) {
        TempRuleResult result = new TempRuleResult();
        if (!GroupUtility.validPairedSiteLaterality(i1.getLaterality(), i2.getLaterality())) {
            result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
            result.setMessageUnknownLaterality(this.getStep(), this.getGroupName());
        }
        else if (GroupUtility.areOppositeSides(i1.getLaterality(), i2.getLaterality()))
            result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);

        return result;
    }
}
