/*
 * Copyright (C) 2019 Information Management Services, Inc.
 */
package com.imsweb.mph.mprules;

import java.util.List;

import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphUtils;
import com.imsweb.mph.internal.TempRuleResult;
import com.imsweb.mph.mpgroups.GroupUtility;

public class MpRuleLateralityPairedSites extends MphRule {

    private List<String> _pairedSites;

    public MpRuleLateralityPairedSites(String groupName, String step, List<String> pairedSites) {
        super(groupName, step);
        _pairedSites = pairedSites;
    }

    @Override
    public TempRuleResult apply(MphInput i1, MphInput i2) {
        TempRuleResult result = new TempRuleResult();
        if (GroupUtility.isPairedSites(i1.getPrimarySite(), i2.getPrimarySite(), _pairedSites)) {
            if (!GroupUtility.validPairedSiteLaterality(i1.getLaterality(), i2.getLaterality())) {
                result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                result.setMessageUnknownLaterality(this.getStep(), this.getGroupName());
            }
            else if (GroupUtility.areOppositeSides(i1.getLaterality(), i2.getLaterality()))
                result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
        }
        return result;
    }
}
