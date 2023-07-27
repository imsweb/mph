/*
 * Copyright (C) 2019 Information Management Services, Inc.
 */
package com.imsweb.mph.mprules;

import java.util.Arrays;
import java.util.Set;

import com.imsweb.mph.MphConstants;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphUtils;
import com.imsweb.mph.RuleExecutionContext;
import com.imsweb.mph.internal.TempRuleResult;
import com.imsweb.mph.mpgroups.GroupUtility;

public class MpRuleThyroidFollicularPapillary extends MphRule {

    private Set<String> _follicularAndPapillary;

    public MpRuleThyroidFollicularPapillary(String groupName, String step, Set<String> follicularAndPapillary) {
        super(groupName, step);
        _follicularAndPapillary = follicularAndPapillary;
    }

    @Override
    public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
        TempRuleResult result = new TempRuleResult();
        String site1 = i1.getPrimarySite();
        String site2 = i2.getPrimarySite();
        String icd1 = i1.getIcdCode();
        String icd2 = i2.getIcdCode();
        if (MphConstants.THYROID.equals(site1) && MphConstants.THYROID.equals(site2) && _follicularAndPapillary.containsAll(Arrays.asList(icd1, icd2))) {
            int sixtyDaysApart = GroupUtility.verifyDaysApart(i1, i2, 60);
            if (MphConstants.DATE_VERIFY_UNKNOWN == sixtyDaysApart) {
                result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupName());
            }
            else if (MphConstants.DATE_VERIFY_WITHIN == sixtyDaysApart)
                result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
        }
        return result;
    }
}
