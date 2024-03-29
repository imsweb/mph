/*
 * Copyright (C) 2013 Information Management Services, Inc.
 */
package com.imsweb.mph.mpgroups;

import java.util.Arrays;

import com.imsweb.mph.MphConstants;
import com.imsweb.mph.MphGroup;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphUtils;
import com.imsweb.mph.RuleExecutionContext;
import com.imsweb.mph.internal.TempRuleResult;
import com.imsweb.mph.mprules.MpRuleHistology;
import com.imsweb.mph.mprules.MpRuleInvasiveAfterInsituGreaterThan60Days;
import com.imsweb.mph.mprules.MpRuleNoCriteriaSatisfied;

public class Mp2007MelanomaGroup extends MphGroup {

    public Mp2007MelanomaGroup() {
        super(MphConstants.MPH_2007_MELANOMA_GROUP_ID, MphConstants.MPH_2007_2020_MELANOMA, "C440-C449", null, "8720-8780",
                null, "2-3,6", "2007-2020");

        //M3- Melanomas in sites with ICD-O-3 topography codes that are different at the second (C?xx), third (Cx?x) or fourth (C44?) character are multiple primaries.
        MphRule rule = new MphRule(MphConstants.MPH_2007_2020_MELANOMA, "M3") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
                TempRuleResult result = new TempRuleResult();
                if (!i1.getPrimarySite().equals(i2.getPrimarySite()))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Are there melanomas in sites with ICD-O-3 topography codes that are different at the second (C?xx) , third (Cx?x) and/or fourth (C44?) character?");
        rule.setReason("Melanomas in sites with ICD-O-3 topography codes that are different at the second (C?xx), third (Cx?x) or fourth (C44?) character are multiple primaries.");
        _rules.add(rule);

        //M4- Melanomas with different laterality are multiple primaries. 
        rule = new MphRule(MphConstants.MPH_2007_2020_MELANOMA, "M4") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
                TempRuleResult result = new TempRuleResult();
                // mid-line (5) is considered (look the example)
                if (!Arrays.asList(MphConstants.RIGHT, MphConstants.LEFT, MphConstants.MID_LINE).containsAll(Arrays.asList(i1.getLaterality(), i2.getLaterality()))) {
                    result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    result.setMessageUnknownLaterality(this.getStep(), this.getGroupName());
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
        rule = new MpRuleHistology(MphConstants.MPH_2007_2020_MELANOMA, "M5");
        rule.setQuestion("Do the melanomas haveICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number?");
        rule.setReason("Melanomas with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.");
        _rules.add(rule);

        //M6- An invasive melanoma that occurs more than 60 days after an in situ melanoma is a multiple primary.
        rule = new MpRuleInvasiveAfterInsituGreaterThan60Days(MphConstants.MPH_2007_2020_MELANOMA, "M6");
        rule.setQuestion("Is there an invasive melanoma following an in situ tumor more than 60 days after diagnosis?");
        rule.setReason("An invasive melanoma that occurs more than 60 days after an in situ melanoma is a multiple primary.");
        rule.getNotes().add("The purpose of this rule is to ensure that the case is counted as an incident (invasive) case when incidence data are analyzed.");
        rule.getNotes().add("Abstract as multiple primaries even if the medical record/physician states it is recurrence or progression of disease.");
        _rules.add(rule);

        //M7- Melanomas diagnosed more than 60 days apart are multiple primaries. 
        rule = new MphRule(MphConstants.MPH_2007_2020_MELANOMA, "M7") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
                TempRuleResult result = new TempRuleResult();
                int sixtyDaysApart = GroupUtility.verifyDaysApart(i1, i2, 60);
                if (MphConstants.DATE_VERIFY_UNKNOWN == sixtyDaysApart) {
                    result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupName());
                }
                else if (MphConstants.DATE_VERIFY_APART == sixtyDaysApart)
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Are there melanomas diagnosed more than 60 days apart?");
        rule.setReason("Melanomas diagnosed more than 60 days apart are multiple primaries.");
        _rules.add(rule);

        //M8- Melanomas that do not meet any of the above criteria are abstracted as a single primary.
        rule = new MpRuleNoCriteriaSatisfied(MphConstants.MPH_2007_2020_MELANOMA, "M8");
        rule.setReason("Melanomas that do not meet any of the above criteria are abstracted as a single primary.");
        rule.getNotes().add("Use the data item \"Multiplicity Counter\" to record the number of melanomas abstracted as a single primary.");
        rule.getNotes().add("When an invasive melanoma follows an in situ melanoma within 60 days, abstract as a single primary.");
        rule.getNotes().add("All cases covered by this rule are the same site and histology.");
        _rules.add(rule);
    }
}
