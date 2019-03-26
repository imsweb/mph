/*
 * Copyright (C) 2019 Information Management Services, Inc.
 */
package com.imsweb.mph.mprules;

import java.util.Collections;

import com.imsweb.mph.MphComputeOptions;
import com.imsweb.mph.MphConstants;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphUtils;
import com.imsweb.mph.MphUtils.MpResult;
import com.imsweb.mph.internal.TempRuleResult;
import com.imsweb.mph.mpgroups.GroupUtility;

public class MpRuleInvasiveAfterInsituLessThan60DaysSameSide extends MphRule {

    public MpRuleInvasiveAfterInsituLessThan60DaysSameSide(String groupId, String step) {
        super(groupId, step);
        setQuestion("Is there an invasive tumor following an in situ tumor less than or equal to 60 days after diagnosis?");
        setReason("An invasive tumor following an in situ tumor less than or equal to 60 days after diagnosis is a single primary.");
    }

    @Override
    public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
        TempRuleResult result = new TempRuleResult();
        String beh1 = i1.getBehavior(), beh2 = i2.getBehavior();
        if (!GroupUtility.areOppositeSides(i1.getLaterality(), i2.getLaterality()) && GroupUtility.differentCategory(beh1, beh2, Collections.singletonList(MphConstants.INSITU),
                Collections.singletonList(MphConstants.MALIGNANT))) {
            int latestDx = GroupUtility.compareDxDate(i1, i2);
            int sixtyDaysApart = GroupUtility.verifyDaysApart(i1, i2, 60);
            //If they are diagnosed after 60 days or invasive is not following insitu
            if (1 == sixtyDaysApart || (1 == latestDx && !"3".equals(beh1)) || (2 == latestDx && !"3".equals(beh2)))
                return result;
            else {
                if (!GroupUtility.validPairedSiteLaterality(i1.getLaterality(), i2.getLaterality())) {
                    result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                    if (-1 == sixtyDaysApart)
                        result.setMessageUnknownLatAndDate(this.getStep(), this.getGroupId());
                    else
                        result.setMessageUnknownLaterality(this.getStep(), this.getGroupId());
                }
                if (-1 == sixtyDaysApart) {
                    result.setPotentialResult(MpResult.SINGLE_PRIMARY);
                    result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupId());
                }
                else if (0 == sixtyDaysApart)
                    result.setFinalResult(MpResult.SINGLE_PRIMARY);
            }
        }
        return result;
    }
}
