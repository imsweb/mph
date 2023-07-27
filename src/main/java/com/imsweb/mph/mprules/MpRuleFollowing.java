/*
 * Copyright (C) 2019 Information Management Services, Inc.
 */
package com.imsweb.mph.mprules;

import java.util.Collections;

import com.imsweb.mph.MphConstants;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphUtils;
import com.imsweb.mph.RuleExecutionContext;
import com.imsweb.mph.internal.TempRuleResult;
import com.imsweb.mph.mpgroups.GroupUtility;

public class MpRuleFollowing extends MphRule {

    private String _laterMorph;
    private String _earlierMorph;

    public MpRuleFollowing(String groupName, String step, String laterMorph, String earlierMorph) {
        super(groupName, step);
        _laterMorph = laterMorph;
        _earlierMorph = earlierMorph;
    }

    @Override
    public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
        TempRuleResult result = new TempRuleResult();
        String icd1 = i1.getIcdCode();
        String icd2 = i2.getIcdCode();
        if (GroupUtility.differentCategory(icd1, icd2, Collections.singletonList(_laterMorph), Collections.singletonList(_earlierMorph))) {
            int laterDiagnosedTumor = GroupUtility.compareDxDate(i1, i2);
            //If impossible to decide which tumor is diagnosed later
            if (MphConstants.COMPARE_DX_UNKNOWN == laterDiagnosedTumor) {
                result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupName());
            }
            else if ((MphConstants.COMPARE_DX_FIRST_LATEST == laterDiagnosedTumor && icd1.equals(_laterMorph)) || (MphConstants.COMPARE_DX_SECOND_LATEST == laterDiagnosedTumor && icd2.equals(
                    _laterMorph)))
                result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
        }
        return result;
    }
}
