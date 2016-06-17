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

public class Mp2007LungGroup extends MphGroup {

    public Mp2007LungGroup() {
        super("lung-2007", "Lung 2007", "C340-C349", null, null, "9590-9989, 9140", "2-3,6", "2007-9999");

        // M3- Tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx) and/or third (Cx?x) character are multiple primaries.
        MphRule rule = new MphRulePrimarySiteCode("lung-2007", "M3");
        rule.getNotes().add("This is a change in rules; tumors in the trachea (C33) and in the lung (C34) were a single lung primary in the previous rules.");
        _rules.add(rule);

        // M4- At least one tumor that is non-small cell carcinoma (8046) and another tumor that is small cell carcinoma (8041-8045) are multiple primaries.
        rule = new MphRule("lung-2007", "M4", MphUtils.MPResult.MULTIPLE_PRIMARIES) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                result.setResult(differentCategory(i1.getHistology(), i2.getHistology(), Collections.singletonList("8046"), Arrays.asList("8041", "8042", "8043", "8044",
                        "8045")) ? MphUtils.RuleResult.TRUE : MphUtils.RuleResult.FALSE);
                return result;
            }
        };
        rule.setQuestion("Is at least one tumor non-small cell carcinoma (8046) and another tumor small cell carcinoma (8041-8045)?");
        rule.setReason("At least one tumor that is non-small cell carcinoma (8046) and another tumor that is small cell carcinoma (8041-8045) are multiple primaries.");
        _rules.add(rule);

        // M5- A tumor that is adenocarcinoma with mixed subtypes (8255) and another that is bronchioloalveolar (8250-8254) are multiple primaries.
        rule = new MphRule("lung-2007", "M5", MphUtils.MPResult.MULTIPLE_PRIMARIES) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                result.setResult(differentCategory(i1.getHistology(), i2.getHistology(), Collections.singletonList("8255"), Arrays.asList("8250", "8251", "8252", "8253",
                        "8254")) ? MphUtils.RuleResult.TRUE : MphUtils.RuleResult.FALSE);
                return result;
            }
        };
        rule.setQuestion("Is there a tumor that is adenocarcinoma with mixed subtypes (8255) and another that is bronchioalveolar (8250-8254)?");
        rule.setReason("A tumor that is adenocarcinoma with mixed subtypes (8255) and another that is bronchioloalveolar (8250-8254) are multiple primaries.");
        _rules.add(rule);

        // M6- A single tumor in each lung is multiple primaries.
        rule = new MphRule("lung-2007", "M6", MphUtils.MPResult.MULTIPLE_PRIMARIES) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                if (!Arrays.asList("1", "2", "4").containsAll(Arrays.asList(i1.getLaterality(), i2.getLaterality()))) {
                    result.setResult(MphUtils.RuleResult.UNKNOWN);
                    result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". Valid and known laterality for lung cancer should be provided.");
                }
                else
                    result.setResult(("1".equals(i1.getLaterality()) && "2".equals(i2.getLaterality())) || ("2".equals(i1.getLaterality()) && "1".equals(i2.getLaterality())) ? MphUtils.RuleResult.TRUE : MphUtils.RuleResult.FALSE);

                return result;
            }
        };
        rule.setQuestion("Is there a single tumor in each lung?");
        rule.setReason("A single tumor in each lung is multiple primaries.");
        rule.getNotes().add("When there is a single tumor in each lung abstract as multiple primaries unless stated or proven to be metastatic.");
        _rules.add(rule);

        // M7- Multiple tumors in both lungs with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.
        rule = new MphRule("lung-2007", "M7", MphUtils.MPResult.MULTIPLE_PRIMARIES) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                //if they are on the same lung, don't apply this
                if (i1.getLaterality().equals(i2.getLaterality()) && !"4".equals(i1.getLaterality()))
                    result.setResult(MphUtils.RuleResult.FALSE);
                else {
                    String hist1 = i1.getHistology(), hist2 = i2.getHistology();
                    result.setResult(((hist1.charAt(0) != hist2.charAt(0)) || (hist1.charAt(1) != hist2.charAt(1)) || (hist1.charAt(2) != hist2.charAt(
                            2))) ? MphUtils.RuleResult.TRUE : MphUtils.RuleResult.FALSE);
                }
                return result;
            }
        };
        rule.setQuestion("Are there multiple tumors in both lungs with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number?");
        rule.setReason("Multiple tumors in both lungs with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (x?xx) number are multiple primaries.");
        _rules.add(rule);

        // M8- Tumors diagnosed more than three (3) years apart are multiple primaries.
        rule = new MphRule("lung-2007", "M8", MphUtils.MPResult.MULTIPLE_PRIMARIES) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                int diff = verifyYearsApart(i1, i2, 3);
                if (-1 == diff) {
                    result.setResult(MphUtils.RuleResult.UNKNOWN);
                    result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". There is no enough diagnosis date information.");
                }
                else
                    result.setResult(1 == diff ? MphUtils.RuleResult.TRUE : MphUtils.RuleResult.FALSE);

                return result;
            }
        };
        rule.setQuestion("Are there tumors diagnosed more than three (3) years apart?");
        rule.setReason("Tumors diagnosed more than three (3) years apart are multiple primaries.");
        _rules.add(rule);

        // M9- An invasive tumor following an in situ tumor more than 60 days after diagnosis are multiple primaries.
        rule = new MphRuleBehavior("lung-2007", "M9");
        _rules.add(rule);

        // M10- Tumors with non-small cell carcinoma, NOS (8046) and a more specific non-small cell carcinoma type (chart 1) are a single primary.
        rule = new MphRule("lung-2007", "M10", MphUtils.MPResult.SINGLE_PRIMARY) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                List<String> specificNonSmall = Arrays.asList("8033", "8980", "8031", "8022", "8972", "8032", "8012", "8140", "8200", "8430", "8560", "8070", "8550", "8255", "8251", "8250", "8252",
                        "8253", "8254", "8310", "8470", "8480", "8481", "8260", "8490", "8230", "8333", "8013", "8014", "8082", "8123", "8310", "8083", "8052", "8084", "8071", "8072", "8073");
                result.setResult(differentCategory(i1.getHistology(), i2.getHistology(), Collections.singletonList("8046"), specificNonSmall) ? MphUtils.RuleResult.TRUE : MphUtils.RuleResult.FALSE);
                return result;
            }
        };
        rule.setQuestion("Are there tumors with non-small cell carcinoma (8046) and a more specific non-small cell carcinoma type (chart 1)?");
        rule.setReason("Tumors with non-small cell carcinoma, NOS (8046) and a more specific non-small cell carcinoma type (chart 1) are a single primary.");
        _rules.add(rule);

        // M11- Tumors with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.
        rule = new MphRuleHistologyCode("lung-2007", "M11");
        rule.getNotes().add("Adenocarcinoma in one tumor and squamous cell carcinoma in another tumor are multiple primaries.");
        _rules.add(rule);

        // M12- Tumors that do not meet any of the criteria are abstracted as a single primary.
        rule = new MphRuleNoCriteriaSatisfied("lung-2007", "M12");
        rule.getNotes().add("When an invasive tumor follows an in situ tumor within 60 days, abstract as a single primary.");
        rule.getNotes().add("All cases covered by this rule are the same histology.");
        _rules.add(rule);
    }
}
