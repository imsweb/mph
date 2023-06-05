/*
 * Copyright (C) 2019 Information Management Services, Inc.
 */
package com.imsweb.mph.mprules;

import java.util.Collections;

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
    public TempRuleResult apply(MphInput i1, MphInput i2) {
        TempRuleResult result = new TempRuleResult();
        String beh1 = i1.getBehavior();
        String beh2 = i2.getBehavior();
        if (!GroupUtility.areOppositeSides(i1.getLaterality(), i2.getLaterality()) && GroupUtility.differentCategory(beh1, beh2, Collections.singletonList(MphConstants.INSITU),
                Collections.singletonList(MphConstants.MALIGNANT))) {
            int latestDx = GroupUtility.compareDxDate(i1, i2);
            int sixtyDaysApart = GroupUtility.verifyDaysApart(i1, i2, 60);
            //If they are diagnosed after 60 days or invasive is not following insitu
            if (MphConstants.DATE_VERIFY_APART == sixtyDaysApart || (MphConstants.COMPARE_DX_FIRST_LATEST == latestDx && !"3".equals(beh1)) || (MphConstants.COMPARE_DX_SECOND_LATEST == latestDx
                    && !"3".equals(beh2)))
                return result;
            else {
                if (!GroupUtility.validPairedSiteLaterality(i1.getLaterality(), i2.getLaterality())) {
                    result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                    if (MphConstants.DATE_VERIFY_UNKNOWN == sixtyDaysApart)
                        result.setMessageUnknownLatAndDate(this.getStep(), this.getGroupName());
                    else
                        result.setMessageUnknownLaterality(this.getStep(), this.getGroupName());
                }
                if (MphConstants.DATE_VERIFY_UNKNOWN == sixtyDaysApart) {
                    result.setPotentialResult(MpResult.SINGLE_PRIMARY);
                    result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupName());
                }
                else if (MphConstants.DATE_VERIFY_WITHIN == sixtyDaysApart)
                    result.setFinalResult(MpResult.SINGLE_PRIMARY);
            }
        }
        return result;
    }
}
