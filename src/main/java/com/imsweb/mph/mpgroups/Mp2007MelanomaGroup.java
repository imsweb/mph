/*
 * Copyright (C) 2013 Information Management Services, Inc.
 */
package com.imsweb.mph.mpgroups;

import java.util.Arrays;

import com.imsweb.mph.MphGroup;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphRuleResult;
import com.imsweb.mph.MphUtils;

public class Mp2007MelanomaGroup extends MphGroup {

    public Mp2007MelanomaGroup() {
        super("melanoma-2007", "Melanoma 2007", "C440-C449", null, "8720-8780", null, "2-3,6", "2007-9999");

        //M3- Melanomas in sites with ICD-O-3 topography codes that are different at the second (C?xx), third (Cx?x) or fourth (C44?) character are multiple primaries.
        MphRule rule = new MphRule("melanoma-2007", "M3", MphUtils.MPResult.MULTIPLE_PRIMARIES) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                String site1 = i1.getPrimarySite(), site2 = i2.getPrimarySite();
                result.setResult(site1.equalsIgnoreCase(site2) ? MphUtils.RuleResult.FALSE : MphUtils.RuleResult.TRUE);
                return result;
            }
        };
        rule.setQuestion("Are there melanomas in sites withICD-O-3 topography codes that are different at the second (C?xx) , third (Cx?x) and/or fourth (C18?) character?");
        rule.setReason("Melanomas in sites with ICD-O-3 topography codes that are different at the second (C?xx), third (Cx?x) or fourth (C44?) character are multiple primaries.");
        _rules.add(rule);

        //M4- Melanomas with different laterality are multiple primaries. 
        rule = new MphRule("melanoma-2007", "M4", MphUtils.MPResult.MULTIPLE_PRIMARIES) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                // mid-line (5) is considered (look the example)
                if (!Arrays.asList("1", "2", "5").containsAll(Arrays.asList(i1.getLaterality(), i2.getLaterality()))) {
                    result.setResult(MphUtils.RuleResult.UNKNOWN);
                    result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". Valid and known laterality should be provided.");
                }
                else
                    result.setResult(!i1.getLaterality().equals(i2.getLaterality()) ? MphUtils.RuleResult.TRUE : MphUtils.RuleResult.FALSE);

                return result;
            }
        };
        rule.setQuestion("Do the melanomas have different lateralities?");
        rule.setReason("Melanomas with different laterality are multiple primaries.");
        rule.getExamples().add("Melanoma of the right side of the chest and a melanoma at midline of the chest are different laterality, multiple primaries.");
        rule.getExamples().add("A melanoma of the right side of the chest and a melanoma of the left side of the chest are multiple primaries.");
        _rules.add(rule);

        //M5- Melanomas with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.        
        rule = new MphRuleHistologyCode("melanoma-2007", "M5");
        rule.setQuestion("Do the melanomas haveICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number?");
        rule.setReason("Melanomas with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.");
        _rules.add(rule);

        //M6- An invasive melanoma that occurs more than 60 days after an in situ melanoma is a multiple primary.
        rule = new MphRuleBehavior("melanoma-2007", "M6");
        rule.setQuestion("Is there an invasive melanoma following an in situ tumor more than 60 days after diagnosis?");
        rule.setReason("An invasive melanoma that occurs more than 60 days after an in situ melanoma is a multiple primary.");
        _rules.add(rule);

        //M7- Melanomas diagnosed more than 60 days apart are multiple primaries. 
        rule = new MphRule("melanoma-2007", "M7", MphUtils.MPResult.MULTIPLE_PRIMARIES) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                int diff = verify60DaysApart(i1, i2, false);
                if (-1 == diff){
                    result.setResult(MphUtils.RuleResult.UNKNOWN);
                    result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". There is no enough diagnosis date information.");
                }
                else
                    result.setResult(0 != diff ? MphUtils.RuleResult.TRUE : MphUtils.RuleResult.FALSE);

                return result;
            }
        };
        rule.setQuestion("Are there melanomas diagnosed more than 60 days apart?");
        rule.setReason("Melanomas diagnosed more than 60 days apart are multiple primaries.");
        _rules.add(rule);

        //M8- Melanomas that do not meet any of the above criteria are abstracted as a single primary.
        rule = new MphRuleNoCriteriaSatisfied("melanoma-2007", "M8");
        rule.setReason("Melanomas that do not meet any of the above criteria are abstracted as a single primary.");
        rule.getNotes().add("Use the data item \"Multiplicity Counter\" to record the number of melanomas abstracted as a single primary.");
        rule.getNotes().add("When an invasive melanoma follows an in situ melanoma within 60 days, abstract as a single primary.");
        rule.getNotes().add("All cases covered by this rule are the same site and histology.");
        _rules.add(rule);
    }
}
