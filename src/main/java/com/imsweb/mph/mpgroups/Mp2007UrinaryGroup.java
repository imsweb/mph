/*
 * Copyright (C) 2013 Information Management Services, Inc.
 */
package com.imsweb.mph.mpgroups;

import java.util.Arrays;
import java.util.List;

import com.imsweb.mph.MphConstants;
import com.imsweb.mph.MphGroup;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphRuleResult;
import com.imsweb.mph.MphUtils;

public class Mp2007UrinaryGroup extends MphGroup {

    public Mp2007UrinaryGroup() {
        super(MphConstants.MP_2007_URINARY_GROUP_ID, MphConstants.MP_2007_URINARY_GROUP_NAME, "C659, C669, C670-C679, C680-C689", null, null, "9590-9989, 9140", "2-3,6", "2007-9999");

        // M3 - When no other urinary sites are involved, tumor(s) in the right renal pelvis AND tumor(s) in the left renal pelvis are multiple primaries. (C659) 
        MphRule rule = new MphRule(MphConstants.MP_2007_URINARY_GROUP_ID, "M3", MphUtils.MPResult.MULTIPLE_PRIMARIES) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                if (!"C659".equalsIgnoreCase(i1.getPrimarySite()) || !"C659".equalsIgnoreCase(i2.getPrimarySite())) {
                    result.setResult(MphUtils.RuleResult.FALSE);
                }
                else if (!Arrays.asList("1", "2").containsAll(Arrays.asList(i1.getLaterality(), i2.getLaterality()))) {
                    result.setResult(MphUtils.RuleResult.UNKNOWN);
                    result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". Valid and known laterality for renal pelvis tumors should be provided.");
                }
                else
                    result.setResult(!i1.getLaterality().equals(i2.getLaterality()) ? MphUtils.RuleResult.TRUE : MphUtils.RuleResult.FALSE);

                return result;
            }
        };
        rule.setQuestion("Are there tumors in both the right renal pelvis and the left renal pelvis and no other urinary sites are involved?");
        rule.setReason("When no other urinary sites are involved, tumor(s) in the right renal pelvis AND tumor(s) in the left renal pelvis are multiple primaries.");
        rule.getNotes().add("Use this rule and abstract as a multiple primary unless documented to be metastatic.");
        _rules.add(rule);

        // M4 - When no other urinary sites are involved, tumor(s) in both the right ureter AND tumor(s) in the left ureter are multiple primaries. (C669) 
        rule = new MphRule(MphConstants.MP_2007_URINARY_GROUP_ID, "M4", MphUtils.MPResult.MULTIPLE_PRIMARIES) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                if (!"C669".equalsIgnoreCase(i1.getPrimarySite()) || !"C669".equalsIgnoreCase(i2.getPrimarySite())) {
                    result.setResult(MphUtils.RuleResult.FALSE);
                }
                else if (!Arrays.asList("1", "2").containsAll(Arrays.asList(i1.getLaterality(), i2.getLaterality()))) {
                    result.setResult(MphUtils.RuleResult.UNKNOWN);
                    result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". Valid and known laterality for ureter tumors should be provided.");
                }
                else
                    result.setResult(!i1.getLaterality().equals(i2.getLaterality()) ? MphUtils.RuleResult.TRUE : MphUtils.RuleResult.FALSE);

                return result;
            }
        };
        rule.setQuestion("Are there tumors in both the right ureter and the left ureter and no other urinary sites are involved?");
        rule.setReason("When no other urinary sites are involved, tumor(s) in both the right ureter AND tumor(s) in the left ureter are multiple primaries.");
        rule.getNotes().add("Use this rule and abstract as a multiple primary unless documented to be metastatic.");
        _rules.add(rule);

        // M5- An invasive tumor following an in situ tumor more than 60 days after diagnosis are multiple primaries.
        rule = new MphRuleBehavior(MphConstants.MP_2007_URINARY_GROUP_ID, "M5");
        _rules.add(rule);

        // M6 - Bladder tumors with any combination of the following histologies: papillary carcinoma (8050), transitional cell carcinoma (8120-8124), 
        // or papillary transitional cell carcinoma (8130-8131), are a single primary.       
        rule = new MphRule(MphConstants.MP_2007_URINARY_GROUP_ID, "M6", MphUtils.MPResult.SINGLE_PRIMARY) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                List<String> carcinomaHist = Arrays.asList("8050", "8120", "8121", "8122", "8123", "8124", "8130", "8131");
                if (!i1.getPrimarySite().toLowerCase().startsWith("c67") || !i2.getPrimarySite().toLowerCase().startsWith("c67")) {
                    result.setResult(MphUtils.RuleResult.FALSE);
                }
                else
                    result.setResult(carcinomaHist.containsAll(Arrays.asList(i1.getHistology(), i2.getHistology())) ? MphUtils.RuleResult.TRUE : MphUtils.RuleResult.FALSE);

                return result;
            }
        };
        rule.setQuestion("Are there bladder tumors with any combination of the following histologies:\n" +
                "- papillary carcinoma (8050)\n" +
                "- transitional cell carcinoma (8120-8124)\n" +
                "- papillary tansitional cell carcinoma (8130-8131)?");
        rule.setReason(
                "Bladder tumors with any combination of the following histologies: papillary carcinoma (8050), transitional cell carcinoma (8120-8124), or papillary transitional cell carcinoma (8130-8131), are a single primary.");
        _rules.add(rule);

        // M7 - Tumors diagnosed more than three (3) years apart are multiple primaries.
        rule = new MphRule(MphConstants.MP_2007_URINARY_GROUP_ID, "M7", MphUtils.MPResult.MULTIPLE_PRIMARIES) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                int diff = GroupUtility.verifyYearsApart(i1, i2, 3);
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

        // M8 - Urothelial tumors in two or more of the following sites are a single primary* (See Table 1 of pdf)
        // Renal pelvis (C659), Ureter(C669), Bladder (C670-C679), Urethra /prostatic urethra (C680)
        rule = new MphRule(MphConstants.MP_2007_URINARY_GROUP_ID, "M8", MphUtils.MPResult.SINGLE_PRIMARY) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                List<String> urothelialTumors = Arrays.asList("8120", "8130", "8131", "8082", "8122", "8031", "8020");
                //The only sites not included are those from C681-C689
                if (i1.getPrimarySite().toLowerCase().startsWith("c68") && !"c680".equals(i1.getPrimarySite().toLowerCase()) || i2.getPrimarySite().toLowerCase().startsWith("c68") && !i2
                        .getPrimarySite().toLowerCase().equals("c680"))
                    result.setResult(MphUtils.RuleResult.FALSE);
                else
                    result.setResult(urothelialTumors.containsAll(Arrays.asList(i1.getHistology(), i2.getHistology())) ? MphUtils.RuleResult.TRUE : MphUtils.RuleResult.FALSE);
                return result;
            }
        };
        rule.setQuestion("Are there urothelial tumors in two or more of the following sites:\n" +
                "- Renal pelvis (C659)\n" +
                "- Ureter(C669)\n" +
                "- Bladder (C670-C679)\n" +
                "- Urethra /prostatic urethra (C680)");
        rule.setReason("Urothelial tumors in two or more of the following sites are a single primary.\n" +
                "- Renal pelvis (C659)\n" +
                "- Ureter(C669)\n" +
                "- Bladder (C670-C679)\n" +
                "- Urethra /prostatic urethra (C680)");
        _rules.add(rule);

        // M9- Tumors with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.        
        rule = new MphRuleHistologyCode(MphConstants.MP_2007_URINARY_GROUP_ID, "M9");
        _rules.add(rule);

        // M10- Tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx) and/or third (Cx?x) character are multiple primaries.
        rule = new MphRulePrimarySiteCode(MphConstants.MP_2007_URINARY_GROUP_ID, "M10");
        _rules.add(rule);

        // M11- Tumors that do not meet any of the criteria are abstracted as a single primary.
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2007_URINARY_GROUP_ID, "M11");
        rule.getNotes().add("When an invasive tumor follows an in situ tumor within 60 days, abstract as a single primary.");
        _rules.add(rule);
    }
}
