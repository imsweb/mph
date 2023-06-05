/*
 * Copyright (C) 2019 Information Management Services, Inc.
 */
package com.imsweb.mph.mprules;

import java.util.Collections;

import com.imsweb.mph.MphConstants;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphUtils;
import com.imsweb.mph.internal.TempRuleResult;
import com.imsweb.mph.mpgroups.GroupUtility;

public class MpRuleInvasiveAfterInsituGreaterThan60Days extends MphRule {

    public MpRuleInvasiveAfterInsituGreaterThan60Days(String groupName, String step) {
        super(groupName, step);
        setQuestion("Is there an invasive tumor following an in situ tumor more than 60 days after diagnosis?");
        setReason("An invasive tumor following an in situ tumor more than 60 days after diagnosis are multiple primaries.");
    }

    @Override
    public TempRuleResult apply(MphInput i1, MphInput i2) {
        TempRuleResult result = new TempRuleResult();
        String beh1 = i1.getBehavior();
        String beh2 = i2.getBehavior();
        if (GroupUtility.differentCategory(beh1, beh2, Collections.singletonList(MphConstants.INSITU), Collections.singletonList(MphConstants.MALIGNANT))) {
            int latestDx = GroupUtility.compareDxDate(i1, i2);
            //If they are diagnosed at same date or invasive is not following insitu
            if (MphConstants.COMPARE_DX_EQUAL == latestDx || (MphConstants.COMPARE_DX_FIRST_LATEST == latestDx && !"3".equals(beh1)) || (MphConstants.COMPARE_DX_SECOND_LATEST == latestDx && !"3"
                    .equals(beh2)))
                return result;
            else {
                int sixtyDaysApart = GroupUtility.verifyDaysApart(i1, i2, 60);
                if (MphConstants.DATE_VERIFY_UNKNOWN == sixtyDaysApart) {
                    result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupName() + ". There is not enough diagnosis date information.");
                }
                else if (MphConstants.DATE_VERIFY_APART == sixtyDaysApart)
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
            }
        }
        return result;
    }
}
