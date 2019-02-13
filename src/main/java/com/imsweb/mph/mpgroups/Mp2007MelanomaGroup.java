/*
 * Copyright (C) 2013 Information Management Services, Inc.
 */
package com.imsweb.mph.mpgroups;

import java.util.Arrays;

import com.imsweb.mph.MphComputeOptions;
import com.imsweb.mph.MphConstants;
import com.imsweb.mph.MphGroup;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphUtils;
import com.imsweb.mph.internal.TempRuleResult;

public class Mp2007MelanomaGroup extends MphGroup {

    public Mp2007MelanomaGroup() {
        super(MphConstants.MP_2007_MELANOMA_GROUP_ID, MphConstants.MP_2007_MELANOMA_GROUP_NAME, "C440-C449", null, "8720-8780",
                null, "2-3,6", "");

        //M3- Melanomas in sites with ICD-O-3 topography codes that are different at the second (C?xx), third (Cx?x) or fourth (C44?) character are multiple primaries.
        MphRule rule = new MphRule(MphConstants.MP_2007_MELANOMA_GROUP_ID, "M3") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (!i1.getPrimarySite().equals(i2.getPrimarySite()))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Are there melanomas in sites withICD-O-3 topography codes that are different at the second (C?xx) , third (Cx?x) and/or fourth (C18?) character?");
        rule.setReason("Melanomas in sites with ICD-O-3 topography codes that are different at the second (C?xx), third (Cx?x) or fourth (C44?) character are multiple primaries.");
        _rules.add(rule);

        //M4- Melanomas with different laterality are multiple primaries. 
        rule = new MphRule(MphConstants.MP_2007_MELANOMA_GROUP_ID, "M4") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                // mid-line (5) is considered (look the example)
                if (!Arrays.asList(MphConstants.RIGHT, MphConstants.LEFT, MphConstants.MID_LINE).containsAll(Arrays.asList(i1.getLaterality(), i2.getLaterality()))) {
                    result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    result.setMessageUnknownLaterality(this.getStep(), this.getGroupId());
                }
                else if (!i1.getLaterality().equals(i2.getLaterality()))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);

                return result;
            }
        };
        rule.setQuestion("Do the melanomas have different lateralities?");
        rule.setReason("Melanomas with different laterality are multiple primaries.");
        rule.getExamples().add("Melanoma of the right side of the chest and a melanoma at midline of the chest are different laterality, multiple primaries.");
        rule.getExamples().add("A melanoma of the right side of the chest and a melanoma of the left side of the chest are multiple primaries.");
        _rules.add(rule);

        //M5- Melanomas with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.        
        rule = new MphRuleHistologyCode(MphConstants.MP_2007_MELANOMA_GROUP_ID, "M5");
        rule.setQuestion("Do the melanomas haveICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number?");
        rule.setReason("Melanomas with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.");
        _rules.add(rule);

        //M6- An invasive melanoma that occurs more than 60 days after an in situ melanoma is a multiple primary.
        rule = new MphRuleBehavior(MphConstants.MP_2007_MELANOMA_GROUP_ID, "M6");
        rule.setQuestion("Is there an invasive melanoma following an in situ tumor more than 60 days after diagnosis?");
        rule.setReason("An invasive melanoma that occurs more than 60 days after an in situ melanoma is a multiple primary.");
        _rules.add(rule);

        //M7- Melanomas diagnosed more than 60 days apart are multiple primaries. 
        rule = new MphRule(MphConstants.MP_2007_MELANOMA_GROUP_ID, "M7") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                int diff = GroupUtility.verifyDaysApart(i1, i2, 60);
                if (-1 == diff) {
                    result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupId());
                }
                else if (1 == diff)
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Are there melanomas diagnosed more than 60 days apart?");
        rule.setReason("Melanomas diagnosed more than 60 days apart are multiple primaries.");
        _rules.add(rule);

        //M8- Melanomas that do not meet any of the above criteria are abstracted as a single primary.
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2007_MELANOMA_GROUP_ID, "M8");
        rule.setReason("Melanomas that do not meet any of the above criteria are abstracted as a single primary.");
        rule.getNotes().add("Use the data item \"Multiplicity Counter\" to record the number of melanomas abstracted as a single primary.");
        rule.getNotes().add("When an invasive melanoma follows an in situ melanoma within 60 days, abstract as a single primary.");
        rule.getNotes().add("All cases covered by this rule are the same site and histology.");
        _rules.add(rule);
    }
}
