/*
 * Copyright (C) 2013 Information Management Services, Inc.
 */
package com.imsweb.mph.mpgroups;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.imsweb.mph.MphGroup;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphRuleResult;
import com.imsweb.mph.MphUtils;

public class Mp2007BreastGroup extends MphGroup {

    private static final List<String> _INTRADUCTAL_OR_DUCT = Arrays.asList("8022", "8035", "8201", "8230", "8401", "8500", "8501", "8502", "8503", "8504", "8507", "8508");

    public Mp2007BreastGroup() {
        super("breast-2007", "Breast 2007", "C500-C509", null, null, "9590-9989,9140", "2-3,6", "2007-9999");

        // M4- Tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx) and/or third (Cx?x) character are multiple primaries.
        MphRule rule = new MphRulePrimarySiteCode("breast-2007", "M4");
        _rules.add(rule);

        //M5- Tumors diagnosed more than five (5) years apart are multiple primaries.
        rule = new MphRuleDiagnosisDate("breast-2007", "M5");
        _rules.add(rule);

        //M6- Inflammatory carcinoma in one or both breasts is a single primary. (8530/3)
        rule = new MphRule("breast-2007", "M6", MphUtils.MPResult.SINGLE_PRIMARY) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                if ("3".equals(i1.getBehavior()) && "3".equals(i2.getBehavior()) && "8530".equals(i1.getHistology()) && "8530".equals(i2.getHistology()))
                    result.setResult(MphUtils.RuleResult.TRUE);
                else
                    result.setResult(MphUtils.RuleResult.FALSE);
                return result;
            }
        };
        rule.setQuestion("Is there inflammatory carcinoma in one or both breasts?");
        rule.setReason("Inflammatory carcinoma in one or both breasts is a single primary.");
        _rules.add(rule);

        //M7- Tumors on both sides (right and left breast) are multiple primaries.
        rule = new MphRule("breast-2007", "M7", MphUtils.MPResult.MULTIPLE_PRIMARIES) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                if (!Arrays.asList("1", "2").containsAll(Arrays.asList(i1.getLaterality(), i2.getLaterality()))) {
                    result.setResult(MphUtils.RuleResult.UNKNOWN);
                    result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". Valid and known laterality should be provided.");
                }
                else
                    result.setResult(!i1.getLaterality().equals(i2.getLaterality()) ? MphUtils.RuleResult.TRUE : MphUtils.RuleResult.FALSE);

                return result;
            }
        };
        rule.setQuestion("Is there a tumor(s) in each breast?");
        rule.setReason("Tumors on both sides (right and left breast) are multiple primaries.");
        rule.getNotes().add("Lobular carcinoma in both breasts (\"mirror image\") is a multiple primary.");
        _rules.add(rule);

        //M8- An invasive tumor following an in situ tumor more than 60 days after diagnosis are multiple primaries.
        rule = new MphRuleBehavior("breast-2007", "M8");
        _rules.add(rule);

        //M9- Tumors that are intraductal or duct and Paget Disease are a single primary.
        rule = new MphRule("breast-2007", "M9", MphUtils.MPResult.SINGLE_PRIMARY) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                List<String> paget = Arrays.asList("8540", "8541", "8542", "8543");
                MphRuleResult result = new MphRuleResult();
                result.setResult(differentCategory(i1.getHistology(), i2.getHistology(), paget, _INTRADUCTAL_OR_DUCT) ? MphUtils.RuleResult.TRUE : MphUtils.RuleResult.FALSE);
                return result;
            }
        };
        rule.setQuestion("Are the tumors intraductal or duct and Paget Disease?");
        rule.setReason("Tumors that are intraductal or duct and Paget Disease are a single primary.");
        rule.getNotes().add("Use Table 1 and Table 2 to identify intraductal and duct carcinomas.");
        _rules.add(rule);

        //M10- Tumors that are lobular (8520) and intraductal or duct are a single primary.
        rule = new MphRule("breast-2007", "M10", MphUtils.MPResult.SINGLE_PRIMARY) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                List<String> lobular = Collections.singletonList("8520");
                MphRuleResult result = new MphRuleResult();
                result.setResult(differentCategory(i1.getHistology(), i2.getHistology(), lobular, _INTRADUCTAL_OR_DUCT) ? MphUtils.RuleResult.TRUE : MphUtils.RuleResult.FALSE);
                return result;
            }
        };
        rule.setQuestion("Are the tumors lobular (8520) and intraductal or duct?");
        rule.setReason("Tumors that are lobular (8520) and intraductal or duct are a single primary.");
        rule.getNotes().add("Use Table 1 and Table 2 to identify intraductal and duct carcinomas.");
        _rules.add(rule);

        //M11- Multiple intraductal and/or duct carcinomas are a single primary.
        rule = new MphRule("breast-2007", "M11", MphUtils.MPResult.SINGLE_PRIMARY) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                if (_INTRADUCTAL_OR_DUCT.containsAll(Arrays.asList(i1.getHistology(), i2.getHistology())))
                    result.setResult(MphUtils.RuleResult.TRUE);
                else
                    result.setResult(MphUtils.RuleResult.FALSE);
                return result;
            }
        };
        rule.setQuestion("Are there multiple intraductal and/or duct carcinomas?");
        rule.setReason("Multiple intraductal and/or duct carcinomas are a single primary.");
        rule.getNotes().add("Use Table 1 and Table 2 to identify intraductal and duct carcinomas.");
        _rules.add(rule);

        //M12- Tumors with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.        
        rule = new MphRuleHistologyCode("breast-2007", "M12");
        _rules.add(rule);

        //M13- Tumors that do not meet any of the criteria are abstracted as a single primary.
        rule = new MphRuleNoCriteriaSatisfied("breast-2007", "M13");
        rule.getNotes().add("When an invasive tumor follows an in situ tumor within 60 days, abstract as a single primary.");
        rule.getNotes().add("All cases covered by Rule M13 have the same first 3 numbers in ICD-O-3 histology code.");
        rule.getExamples().add("Invasive duct and intraductal carcinoma in the same breast.");
        rule.getExamples().add("Multi-centric lobular carcinoma, left breast.");
        _rules.add(rule);
    }
}
