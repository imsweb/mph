/*
 * Copyright (C) 2013 Information Management Services, Inc.
 */
package com.imsweb.mph.mpgroups;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
import com.imsweb.mph.mprules.MpRulePrimarySite;
import com.imsweb.mph.mprules.MpRuleYearsApart;

public class Mp2007BreastGroup extends MphGroup {

    public Mp2007BreastGroup() {
        super(MphConstants.MPH_2007_BREAST_GROUP_ID, MphConstants.MPH_2007_2017_BREAST, "C500-C509", null, null, "9590-9993,9140", "2-3,6", "2007-2017");

        // M4- Tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx) and/or third (Cx?x) character are multiple primaries.
        MphRule rule = new MpRulePrimarySite(MphConstants.MPH_2007_2017_BREAST, "M4");
        _rules.add(rule);

        //M5- Tumors diagnosed more than five (5) years apart are multiple primaries.
        rule = new MpRuleYearsApart(MphConstants.MPH_2007_2017_BREAST, "M5", 5);
        _rules.add(rule);

        //M6- Inflammatory carcinoma in one or both breasts is a single primary. (8530/3)
        rule = new MphRule(MphConstants.MPH_2007_2017_BREAST, "M6") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
                TempRuleResult result = new TempRuleResult();
                if (MphConstants.MALIGNANT.equals(i1.getBehavior()) && MphConstants.MALIGNANT.equals(i2.getBehavior()) && MphConstants.INFLAMMATORY_CARCINOMA.equals(i1.getHistology())
                        && MphConstants.INFLAMMATORY_CARCINOMA.equals(i2.getHistology()))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                return result;
            }
        };
        rule.setQuestion("Is there inflammatory carcinoma in one or both breasts?");
        rule.setReason("Inflammatory carcinoma in one or both breasts is a single primary.");
        _rules.add(rule);

        //M7- Tumors on both sides (right and left breast) are multiple primaries.
        rule = new MphRule(MphConstants.MPH_2007_2017_BREAST, "M7") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
                TempRuleResult result = new TempRuleResult();
                if (!GroupUtility.validPairedSiteLaterality(i1.getLaterality(), i2.getLaterality())) {
                    result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    result.setMessageUnknownLaterality(this.getStep(), this.getGroupName());
                }
                else if (GroupUtility.areOppositeSides(i1.getLaterality(), i2.getLaterality()))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);

                return result;
            }
        };
        rule.setQuestion("Is there a tumor(s) in each breast?");
        rule.setReason("Tumors on both sides (right and left breast) are multiple primaries.");
        rule.getNotes().add("Lobular carcinoma in both breasts (\"mirror image\") is a multiple primary.");
        _rules.add(rule);

        //M8- An invasive tumor following an in situ tumor more than 60 days after diagnosis are multiple primaries.
        rule = new MpRuleInvasiveAfterInsituGreaterThan60Days(MphConstants.MPH_2007_2017_BREAST, "M8");
        rule.getNotes().add("The purpose of this rule is to ensure that the case is counted as an incident (invasive) case when incidence data are analyzed.");
        rule.getNotes().add("Abstract as multiple primaries even if the medical record/physician states it is recurrence or progression of disease.");
        _rules.add(rule);

        //M9- Tumors that are intraductal or duct and Paget Disease are a single primary.
        rule = new MphRule(MphConstants.MPH_2007_2017_BREAST, "M9") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
                TempRuleResult result = new TempRuleResult();
                List<String> intraductalOrDuct = new ArrayList<>(MphConstants.INTRADUCTAL_CARCINOMA);
                intraductalOrDuct.addAll(MphConstants.DUCT_CARCINOMA);
                if (GroupUtility.differentCategory(i1.getHistology(), i2.getHistology(), MphConstants.PAGET_DISEASE, intraductalOrDuct))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                return result;
            }
        };
        rule.setQuestion("Are the tumors intraductal or duct and Paget Disease?");
        rule.setReason("Tumors that are intraductal or duct and Paget Disease are a single primary.");
        rule.getNotes().add("Use Table 1 and Table 2 to identify intraductal and duct carcinomas.");
        _rules.add(rule);

        //M10- Tumors that are lobular (8520) and intraductal or duct are a single primary.
        rule = new MphRule(MphConstants.MPH_2007_2017_BREAST, "M10") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
                TempRuleResult result = new TempRuleResult();
                List<String> lobular = Collections.singletonList("8520");
                List<String> intraductalOrDuct = new ArrayList<>(MphConstants.INTRADUCTAL_CARCINOMA);
                intraductalOrDuct.addAll(MphConstants.DUCT_CARCINOMA);
                if (GroupUtility.differentCategory(i1.getHistology(), i2.getHistology(), lobular, intraductalOrDuct))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                return result;
            }
        };
        rule.setQuestion("Are the tumors lobular (8520) and intraductal or duct?");
        rule.setReason("Tumors that are lobular (8520) and intraductal or duct are a single primary.");
        rule.getNotes().add("Use Table 1 and Table 2 to identify intraductal and duct carcinomas.");
        _rules.add(rule);

        //M11- Multiple intraductal and/or duct carcinomas are a single primary.
        rule = new MphRule(MphConstants.MPH_2007_2017_BREAST, "M11") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
                TempRuleResult result = new TempRuleResult();
                List<String> intraductalOrDuct = new ArrayList<>(MphConstants.INTRADUCTAL_CARCINOMA);
                intraductalOrDuct.addAll(MphConstants.DUCT_CARCINOMA);
                if (intraductalOrDuct.containsAll(Arrays.asList(i1.getHistology(), i2.getHistology())))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                return result;
            }
        };
        rule.setQuestion("Are there multiple intraductal and/or duct carcinomas?");
        rule.setReason("Multiple intraductal and/or duct carcinomas are a single primary.");
        rule.getNotes().add("Use Table 1 and Table 2 to identify intraductal and duct carcinomas.");
        _rules.add(rule);

        //M12- Tumors with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.        
        rule = new MpRuleHistology(MphConstants.MPH_2007_2017_BREAST, "M12");
        _rules.add(rule);

        //M13- Tumors that do not meet any of the criteria are abstracted as a single primary.
        rule = new MpRuleNoCriteriaSatisfied(MphConstants.MPH_2007_2017_BREAST, "M13");
        rule.getNotes().add("When an invasive tumor follows an in situ tumor within 60 days, abstract as a single primary.");
        rule.getNotes().add("All cases covered by Rule M13 have the same first 3 numbers in ICD-O-3 histology code.");
        rule.getExamples().add("Invasive duct and intraductal carcinoma in the same breast.");
        rule.getExamples().add("Multi-centric lobular carcinoma, left breast.");
        _rules.add(rule);
    }
}
