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
import com.imsweb.mph.MphUtils;
import com.imsweb.mph.internal.TempRuleResult;

public class Mp2007KidneyGroup extends MphGroup {

    public Mp2007KidneyGroup() {
        super(MphConstants.MP_2007_KIDNEY_GROUP_ID, MphConstants.MP_2007_KIDNEY_GROUP_NAME, "C649", null, null, "9590-9989, 9140", "2-3,6", "2007-9999");

        // M3 - Wilms tumors are a single primary. (8960/3)
        MphRule rule = new MphRule(MphConstants.MP_2007_KIDNEY_GROUP_ID, "M3") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                if (MphConstants.MALIGNANT.equals(i1.getBehavior()) && MphConstants.MALIGNANT.equals(i2.getBehavior()) && MphConstants.WILMS.equals(i1.getHistology()) && MphConstants.WILMS.equals(
                        i2.getHistology()))
                    result.setResult(MphUtils.MpResult.SINGLE_PRIMARY);
                return result;
            }
        };
        rule.setQuestion("Is the diagnosisWilms tumor?");
        rule.setReason("Wilms tumors are a single primary.");
        _rules.add(rule);

        // M4 - Tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx) and/or third (Cx?x) character are multiple primaries.
        rule = new MphRulePrimarySiteCode(MphConstants.MP_2007_KIDNEY_GROUP_ID, "M4");
        _rules.add(rule);

        // M5 - Tumors in both the right kidney and in the left kidney are multiple primaries.
        rule = new MphRule(MphConstants.MP_2007_KIDNEY_GROUP_ID, "M5") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                if (!GroupUtility.validLaterality(i1.getLaterality(), i2.getLaterality())) {
                    result.setResult(MphUtils.MpResult.QUESTIONABLE);
                    result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". Valid and known laterality should be provided.");
                }
                else if (GroupUtility.areOppositeSides(i1.getLaterality(), i2.getLaterality()))
                    result.setResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);

                return result;
            }
        };
        rule.setQuestion("Are there tumors in both the left and right kidney?");
        rule.setReason("Tumors in both the right kidney and in the left kidney are multiple primaries.");
        rule.getNotes().add("Abstract as a single primary when the tumors in one kidney are documented to be metastatic from the other kidney.");
        _rules.add(rule);

        // M6 - Tumors diagnosed more than three (3) years apart are multiple primaries.
        rule = new MphRule(MphConstants.MP_2007_KIDNEY_GROUP_ID, "M6") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                int diff = GroupUtility.verifyYearsApart(i1, i2, 3);
                if (-1 == diff) {
                    result.setResult(MphUtils.MpResult.QUESTIONABLE);
                    result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". There is no enough diagnosis date information.");
                }
                else if (1 == diff)
                    result.setResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);

                return result;
            }
        };
        rule.setQuestion("Are there tumors diagnosed more than three (3) years apart?");
        rule.setReason("Tumors diagnosed more than three (3) years apart are multiple primaries.");
        _rules.add(rule);

        // M7 - An invasive tumor following an in situ tumor more than 60 days after diagnosis are multiple primaries.
        rule = new MphRuleBehavior(MphConstants.MP_2007_KIDNEY_GROUP_ID, "M7");
        _rules.add(rule);

        // M8 - One tumor with a specific renal cell type and another tumor with a different specific renal cell type are multiple primaries (table 1 in pdf).
        rule = new MphRule(MphConstants.MP_2007_KIDNEY_GROUP_ID, "M8") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                String hist1 = i1.getHistology(), hist2 = i2.getHistology();
                if (MphConstants.SPECIFIC_RENAL_CELL_HISTOLOGIES.containsAll(Arrays.asList(hist1, hist2)) && !hist1.equals(hist2))
                    result.setResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Is there one tumor with a specific renal cell type and another tumor with a different specific renal cell type?");
        rule.setReason("One tumor with a specific renal cell type and another tumor with a different specific renal cell type are multiple primaries.");
        _rules.add(rule);

        // M9 -
        rule = new MphRule(MphConstants.MP_2007_KIDNEY_GROUP_ID, "M9") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                String hist1 = i1.getHistology(), hist2 = i2.getHistology();
                List<String> nosList = Arrays.asList("8000", "8010", "8140", "8312");
                if ((nosList.contains(hist1) && MphConstants.NOS_VS_SPECIFIC.containsKey(hist1) && MphConstants.NOS_VS_SPECIFIC.get(hist1).contains(hist2)) || (nosList.contains(hist2)
                        && MphConstants.NOS_VS_SPECIFIC.containsKey(hist2) && MphConstants.NOS_VS_SPECIFIC.get(hist2).contains(hist1)))
                    result.setResult(MphUtils.MpResult.SINGLE_PRIMARY);

                return result;
            }
        };
        rule.setQuestion("Is there cancer/malignant neoplasm, NOS (8000) and another is a specific histology? or\n" +
                "Is there carcinoma, NOS (8010) and another is a specific carcinoma? or\n" +
                "Is there adenocarcinoma, NOS (8140) and another is a specific adenocarcinoma? or\n" +
                "Is there renal cell carcinoma, NOS (8312) and the other is a single renal cell type?");
        rule.setReason("Abstract as a single primary* when one tumor is:\n" +
                "- Cancer/malignant neoplasm, NOS (8000) and another is a specific histology or\n" +
                "- Carcinoma, NOS (8010) and another is a specific carcinoma or\n" +
                "- Adenocarcinoma, NOS (8140) and another is a specific adenocarcinoma or\n" +
                "- Renal cell carcinoma, NOS (8312) and the other is a single renal cell type");
        rule.getNotes().add("The specific histology for in situ tumors may be identified as pattern, architecture, type, subtype, predominantly, with features of, major, or with ____differentiation");
        rule.getNotes().add("The specific histology for invasive tumors may be identified as type, subtype, predominantly, with features of, major, or with ____differentiation.");
        _rules.add(rule);

        // M10- Tumors with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.  
        rule = new MphRuleHistologyCode(MphConstants.MP_2007_KIDNEY_GROUP_ID, "M10");
        _rules.add(rule);

        //M11- Tumors that do not meet any of the criteria are abstracted as a single primary.
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2007_KIDNEY_GROUP_ID, "M11");
        rule.getNotes().add("When an invasive tumor follows an in situ tumor within 60 days, abstract as a single primary.");
        _rules.add(rule);
    }
}
