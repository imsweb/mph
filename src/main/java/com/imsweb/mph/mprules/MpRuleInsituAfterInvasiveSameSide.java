/*
 * Copyright (C) 2019 Information Management Services, Inc.
 */
package com.imsweb.mph.mprules;

import java.util.Collections;

import com.imsweb.mph.HematoDataProvider;
import com.imsweb.mph.MphConstants;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphUtils;
import com.imsweb.mph.MphUtils.MpResult;
import com.imsweb.mph.internal.TempRuleResult;
import com.imsweb.mph.mpgroups.GroupUtility;

public class MpRuleInsituAfterInvasiveSameSide extends MphRule {

    public MpRuleInsituAfterInvasiveSameSide(String groupName, String step) {
        super(groupName, step);
        setQuestion("Is there an in situ tumor following an invasive tumor?");
        setReason("An in situ tumor diagnosed following an invasive tumor is a single primary.");
    }

    @Override
    public TempRuleResult apply(MphInput i1, MphInput i2, HematoDataProvider provider) {
        TempRuleResult result = new TempRuleResult();
        String beh1 = i1.getBehavior();
        String beh2 = i2.getBehavior();
        if (!GroupUtility.areOppositeSides(i1.getLaterality(), i2.getLaterality()) && GroupUtility.differentCategory(beh1, beh2, Collections.singletonList(MphConstants.INSITU),
                Collections.singletonList(MphConstants.MALIGNANT))) {
            int latestDx = GroupUtility.compareDxDate(i1, i2);
            //if invasive is after insitu, skip
            if ((MphConstants.COMPARE_DX_FIRST_LATEST == latestDx && MphConstants.MALIGNANT.equals(beh1)) || (MphConstants.COMPARE_DX_SECOND_LATEST == latestDx && MphConstants.MALIGNANT.equals(beh2)))
                return result;
            if (!GroupUtility.validPairedSiteLaterality(i1.getLaterality(), i2.getLaterality())) {
                result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                if (MphConstants.COMPARE_DX_UNKNOWN == latestDx)
                    result.setMessageUnknownLatAndDate(this.getStep(), this.getGroupName());
                else
                    result.setMessageUnknownLaterality(this.getStep(), this.getGroupName());
            }
            else if (MphConstants.COMPARE_DX_UNKNOWN == latestDx) {
                result.setPotentialResult(MpResult.SINGLE_PRIMARY);
                result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupName());
            }
            else if ((MphConstants.COMPARE_DX_FIRST_LATEST == latestDx && MphConstants.INSITU.equals(beh1)) || (MphConstants.COMPARE_DX_SECOND_LATEST == latestDx && MphConstants.INSITU.equals(beh2)))
                result.setFinalResult(MpResult.SINGLE_PRIMARY);
        }
        return result;
    }
}
