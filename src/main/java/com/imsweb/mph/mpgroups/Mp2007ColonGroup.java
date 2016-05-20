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

public class Mp2007ColonGroup extends MphGroup {

    private static final List<String> _POLYP = MphGroup.expandList(Collections.singletonList("8210-8211,8213,8220-8221,8261-8263"));

    public Mp2007ColonGroup() {
        super("colon-2007", "Colon 2007", "C180-C189", null, null, "9590-9989, 9140", "2-3,6", "2007-9999");

        // M3 - Adenocarcinoma in adenomatous polyposis coli (familial polyposis) with one or more malignant polyps is a single primary.
        MphRule rule = new MphRule("colon-2007", "M3", MphUtils.MPResult.SINGLE_PRIMARY) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                List<String> adenocarcinomaInAdenomatous = Arrays.asList("8220", "8221");
                if (!("3".equals(i1.getBehaviorIcdO3()) || "3".equals(i2.getBehaviorIcdO3())))
                    result.setResult(MphUtils.RuleResult.FALSE);
                else if (MphGroup.differentCategory(i1.getHistologyIcdO3(), i2.getHistologyIcdO3(), adenocarcinomaInAdenomatous, _POLYP))
                    result.setResult(MphUtils.RuleResult.TRUE);
                else
                    result.setResult(MphUtils.RuleResult.FALSE);
                return result;
            }
        };
        rule.setQuestion("Is there adenocarcinoma in adenomatous polyposis coli (familialpolyposis) with one or more malignant polyps?");
        rule.setReason("Adenocarcinoma in adenomatous polyposis coli (familial polyposis) with one or more malignant polyps is a single primary.");
        rule.getNotes().add("Tumors may be present in multiple segments of the colon or in a single segment of the colon.");
        _rules.add(rule);

        //M4- Tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx), third (Cx?x) and/or fourth (C18?) character are multiple primaries.
        rule = new MphRule("colon-2007", "M4", MphUtils.MPResult.MULTIPLE_PRIMARIES) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                String site1 = i1.getPrimarySite(), site2 = i2.getPrimarySite();
                result.setResult(site1.equalsIgnoreCase(site2) ? MphUtils.RuleResult.FALSE : MphUtils.RuleResult.TRUE);
                return result;
            }
        };
        rule.setQuestion("Are there tumors in sites withICD-O-3 topography codes that are different at the second (C?xx) , third (Cx?x) and/or fourth (C18?) character?");
        rule.setReason("Tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx), third (Cx?x) and/or fourth (C18?) character are multiple primaries.");
        _rules.add(rule);

        //M5- Tumors diagnosed more than one (1) year apart are multiple primaries.
        rule = new MphRule("colon-2007", "M5", MphUtils.MPResult.MULTIPLE_PRIMARIES) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                int diff = verifyYearsApart(i1, i2, 1);
                if (-1 == diff) {
                    result.setResult(MphUtils.RuleResult.UNKNOWN);
                    result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". There is no enough diagnosis date information.");
                }
                else
                    result.setResult(1 == diff ? MphUtils.RuleResult.TRUE : MphUtils.RuleResult.FALSE);

                return result;
            }
        };
        rule.setQuestion("Are there tumors diagnosed more than one (1) year apart?");
        rule.setReason("Tumors diagnosed more than one (1) year apart are multiple primaries.");
        _rules.add(rule);

        //M6- An invasive tumor following an insitu tumor more than 60 days after diagnosis is a multiple primary.
        rule = new MphRuleBehavior("colon-2007", "M6");
        _rules.add(rule);

        //M7- A frank malignant or in situ adenocarcinoma and an insitu or malignant tumor in a polyp are a single primary.
        rule = new MphRule("colon-2007", "M7", MphUtils.MPResult.SINGLE_PRIMARY) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                List<String> adenocarcinoma = MphGroup.expandList(Arrays.asList(
                        "8140,8000-8005,8010-8011,8020-8022,8046,8141-8148,8154,8160-8162,8190,8200-8201,8210-8211,8214-8215,8220-8221,8230-8231,8244-8245,8250-8255,8260-8263,8270-8272,8280-8281,8290,8300,8310,8312-8320,8322-8323,8330-8333,8335,8337,8350,8370,8380-8384,8390,8400-8403,8407-8409,8410,8413,8420,8440-8442,8450-8453,8460-8462,8470-8473,8480-8482,8490,8500-8504,8507-8508,8510,8512-8514,8520-8525,8530,8540-8543,8550-8551,8561-8562,8570-8576"));
                result.setResult(MphGroup.differentCategory(i1.getHistologyIcdO3(), i2.getHistologyIcdO3(), adenocarcinoma, _POLYP) ? MphUtils.RuleResult.TRUE : MphUtils.RuleResult.FALSE);
                return result;
            }
        };
        rule.setQuestion("Is there a frank malignant or in situ adenocarcinoma and an in situ ormalignant tumor in a polyp?");
        rule.setReason("A frank malignant or in situ adenocarcinoma and an in situ or malignant tumor in a polyp are a single primary.");
        _rules.add(rule);

        //M8 -
        rule = new MphRule("colon-2007", "M8", MphUtils.MPResult.SINGLE_PRIMARY) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                String hist1 = i1.getHistologyIcdO3(), hist2 = i2.getHistologyIcdO3();
                List<String> nosList = Arrays.asList("8000", "8010", "8140", "8800");
                if ((nosList.contains(hist1) && getNosVsSpecificMap().containsKey(hist1) && getNosVsSpecificMap().get(hist1).contains(hist2)) || (nosList.contains(hist2) && getNosVsSpecificMap()
                        .containsKey(hist2) && getNosVsSpecificMap().get(hist2).contains(hist1)))
                    result.setResult(MphUtils.RuleResult.TRUE);
                else
                    result.setResult(MphUtils.RuleResult.FALSE);
                return result;
            }
        };
        rule.setQuestion("Is there cancer/malignant neoplasm, NOS (8000) and another is a specific histology? or\n" +
                "Is there carcinoma, NOS (8010) and another is a specific carcinoma? or\n" +
                "Is there adenocarcinoma, NOS (8140) and another is a specific adenocarcinoma? or\n" +
                "Is there sarcoma, NOS (8800) and another is a specific sarcoma?");
        rule.setReason("Abstract as a single primary* when one tumor is:\n" +
                "- Cancer/malignant neoplasm, NOS (8000) and another is a specific histology or\n" +
                "- Carcinoma, NOS (8010) and another is a specific carcinoma or\n" +
                "- Adenocarcinoma, NOS (8140) and another is a specific adenocarcinoma or\n" +
                "- Sarcoma, NOS (8800) and another is a specific sarcoma");
        _rules.add(rule);

        //M9- Multiple insitu and/or malignant polyps are a single primary.
        rule = new MphRule("colon-2007", "M9", MphUtils.MPResult.SINGLE_PRIMARY) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                result.setResult(_POLYP.containsAll(Arrays.asList(i1.getHistologyIcdO3(), i2.getHistologyIcdO3())) ? MphUtils.RuleResult.TRUE : MphUtils.RuleResult.FALSE);
                return result;
            }
        };
        rule.setQuestion("Are there multiple in situ and /or malignant polyps?");
        rule.setReason("Multiple in situ and/or malignant polyps are a single primary.");
        rule.getNotes().add("Includes all combinations of adenomatous, tubular, villous, and tubulovillous adenomas or polyps.");
        _rules.add(rule);

        //M10- Tumors with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.        
        rule = new MphRuleHistologyCode("colon-2007", "M10");
        _rules.add(rule);

        //M11- Tumors that do not meet any of the criteria are abstracted as a single primary.
        rule = new MphRuleNoCriteriaSatisfied("colon-2007", "M11");
        rule.getNotes().add("When an invasive tumor follows an in situ tumor within 60 days, abstract as a single primary.");
        rule.getNotes().add("All cases covered by Rule M11 are in the same segment of the colon.");
        _rules.add(rule);
    }
}
