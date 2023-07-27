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
import com.imsweb.mph.RuleExecutionContext;
import com.imsweb.mph.internal.TempRuleResult;
import com.imsweb.mph.mpgroups.GroupUtility;

public class MpRuleInsituAfterInvasive extends MphRule {

    public MpRuleInsituAfterInvasive(String groupName, String step) {
        super(groupName, step);
        setQuestion("Is there an in situ tumor following an invasive tumor?");
        setReason("An in situ tumor diagnosed following an invasive tumor is a single primary.");
    }

    @Override
    public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
        TempRuleResult result = new TempRuleResult();
        String beh1 = i1.getBehavior();
        String beh2 = i2.getBehavior();
        if (GroupUtility.differentCategory(beh1, beh2, Collections.singletonList(MphConstants.INSITU), Collections.singletonList(MphConstants.MALIGNANT))) {
            int latestDx = GroupUtility.compareDxDate(i1, i2);
            if (MphConstants.COMPARE_DX_UNKNOWN == latestDx) {
                result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupName());
            }
            else if ((MphConstants.COMPARE_DX_FIRST_LATEST == latestDx && MphConstants.INSITU.equals(beh1)) || (MphConstants.COMPARE_DX_SECOND_LATEST == latestDx && MphConstants.INSITU.equals(beh2)))
                result.setFinalResult(MpResult.SINGLE_PRIMARY);
        }
        return result;
    }
}
