/*
 * Copyright (C) 2018 Information Management Services, Inc.
 */
package com.imsweb.mph.mpgroups;

import java.util.Arrays;
import java.util.List;

import com.imsweb.mph.MphComputeOptions;
import com.imsweb.mph.MphConstants;
import com.imsweb.mph.MphGroup;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphUtils;
import com.imsweb.mph.internal.TempRuleResult;

public class Mp2018KidneyGroup extends MphGroup {

    /*
    Kidney Multiple Primary Rules
    C649
    (Excludes lymphoma and leukemia – M9590-M9992 and Kaposi sarcoma M9140)

    Rule M3     Abstract multiple primaries when multiple tumors are present in sites with ICD-O site/topography codes that differ at the second (CXxx), third (CxxX) and/or fourth characters (CxxX).
                Note: When codes differ at the second, third, or fourth characters, the tumors are in different primary sites.

    Rule M4     Abstract a single primary when there are bilateral nephroblastomas (previously called Wilms tumors).
                Note: Timing is irrelevant; The tumors may occur simultaneously OR the contralateral tumor may be diagnosed later (no time limit)

    Rule M5     Abstract multiple primaries when there are tumors in both the right kidney and in the left kidney.
                Note 1: The rules are hierarchical. Do not use this rule if any of the rules M3-M4 apply.
                Note 2: ONLY abstract a single primary when pathology proves the tumor(s) in one kidney is/are metastatic from the other kidney.

    Rule M6     Abstract a single primary when there is a NOS and a subtype/variant of that NOS in the same kidney.
                • Renal cell carcinoma and a subtype/variant of renal cell OR
                • Rhabdomyosarcoma and a subtype/variant of rhabdomyosarcoma OR
                • Small cell neuroendocrine tumor and a subtype/variant of small cell neuroendocrine tumor
                    Note 1: See Table 1 in Equivalent Terms and Definitions for NOS and subtypes/variants
                    Note 2: The rules are hierarchical; do not use this rule if any of the rules M3-M5 apply

    Rule M7     Abstract multiple primaries when there are multiple tumors with ICD-O histology codes that differ at the first (Xxxx), second (xXxx) AND/OR third (xxXx) number.
                Note: These rules are hierarchical. Do not use this rule if rules M3-M6 apply.

    Rule M8     Abstract a single primary (recurrence) when tumors recur less than or equal to 3 years apart.
                Note 1: These rules are hierarchical. Do not use this rule if rules M3-M7 apply
                Note 2: Using the previous rules, the recurrence must be
                    • In the same kidney AND
                    • Either a NOS and subtype/variant OR
                    • The same four-digit ICD-O histology/morphology

    Rule M9     Abstract multiple primaries when tumors are diagnosed more than 3 years apart.
                Note 1: The rules are hierarchical. This rule only applies when there is a subsequent kidney tumor.
                Note 2: The time interval means the patient has been clinically disease-free for more than 3 years
                    Definition clinically free: There has been no clinical evidence of disease greater than 3 years from date of original diagnosis. Scans, urine cytology, and all other work-ups show no evidence of disease (NED). No subsequent kidney tumor and/or any metastases from the kidney tumor
                Note 3: When the patient has a recurrence less than or equal to 3 years from the date of initial diagnosis
                    • The “clock” starts over
                        o The disease-free interval (greater than 3 years) is now computed from the date of the last known recurrence
                        o The patient must have been disease-free for more than 3 years after the last recurrence of
                             A kidney tumor OR
                             Metastasis from the kidney tumor
                Note 4: Default to date of diagnosis to compute the greater than 3-year time interval when
                    • There was no known recurrence AND/OR
                    • Documentation not available to confirm whether or not there was or metastasis
                Note 5: The location and histology of the subsequent tumor is irrelevant. Kidney tumors that occur more than 3 years apart are always multiple primaries
                Note 6: Code a new primary even if the physician states “recurrence”

    Rule M10    Abstract a single primary when there are multiple tumors that do not meet any of the criteria in M3-M9.



    */

    // TODO
    public Mp2018KidneyGroup() {
        super(MphConstants.MP_2018_KIDNEY_GROUP_ID, MphConstants.MP_2018_KIDNEY_GROUP_NAME, "C649", null, null, "9590-9989, 9140", "2-3,6", "2018-9999");

        // Rule M3 - Abstract multiple primaries when multiple tumors are present in sites with ICD-O site/topography codes that differ at the second (CXxx), third (CxxX) and/or fourth characters (CxxX).
        // TODO
        MphRule rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_KIDNEY_GROUP_ID, "M3");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("When codes differ at the second, third, or fourth characters, the tumors are in different primary sites.");
        _rules.add(rule);

        // Rule M4 - Abstract a single primary when there are bilateral nephroblastomas (previously called Wilms tumors).
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_KIDNEY_GROUP_ID, "M4");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("Timing is irrelevant; The tumors may occur simultaneously OR the contralateral tumor may be diagnosed later (no time limit)");
        _rules.add(rule);

        // Rule M5 - Abstract multiple primaries when there are tumors in both the right kidney and in the left kidney.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_KIDNEY_GROUP_ID, "M5");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The rules are hierarchical. Do not use this rule if any of the rules M3-M4 apply.");
        rule.getNotes().add("ONLY abstract a single primary when pathology proves the tumor(s) in one kidney is/are metastatic from the other kidney.");
        _rules.add(rule);

        // Rule M6 - Abstract a single primary when there is a NOS and a subtype/variant of that NOS in the same kidney.
        // • Renal cell carcinoma and a subtype/variant of renal cell OR
        // • Rhabdomyosarcoma and a subtype/variant of rhabdomyosarcoma OR
        // • Small cell neuroendocrine tumor and a subtype/variant of small cell neuroendocrine tumor
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_KIDNEY_GROUP_ID, "M6");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("See Table 1 in Equivalent Terms and Definitions for NOS and subtypes/variants");
        rule.getNotes().add("The rules are hierarchical; do not use this rule if any of the rules M3-M5 apply");
        _rules.add(rule);

        // Rule M7 - Abstract multiple primaries when there are multiple tumors with ICD-O histology codes that differ at the first (Xxxx), second (xXxx) AND/OR third (xxXx) number.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_KIDNEY_GROUP_ID, "M7");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("These rules are hierarchical. Do not use this rule if rules M3-M6 apply.");
        _rules.add(rule);

        // Rule M8 - Abstract a single primary (recurrence) when tumors recur less than or equal to 3 years apart.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_KIDNEY_GROUP_ID, "M8");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("These rules are hierarchical. Do not use this rule if rules M3-M7 apply");
        rule.getNotes().add("Using the previous rules, the recurrence must be");
        rule.getNotes().add("  • In the same kidney AND");
        rule.getNotes().add("  • Either a NOS and subtype/variant OR");
        rule.getNotes().add("  • The same four-digit ICD-O histology/morphology");
        _rules.add(rule);

        // Rule M9 - Abstract multiple primaries when tumors are diagnosed more than 3 years apart.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_KIDNEY_GROUP_ID, "M9");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The rules are hierarchical. This rule only applies when there is a subsequent kidney tumor.");
        rule.getNotes().add("The time interval means the patient has been clinically disease-free for more than 3 years");
        rule.getNotes().add("  Definition clinically free: There has been no clinical evidence of disease greater than 3 years from date of original diagnosis. Scans, urine cytology, and all other work-ups show no evidence of disease (NED). No subsequent kidney tumor and/or any metastases from the kidney tumor");
        rule.getNotes().add("When the patient has a recurrence less than or equal to 3 years from the date of initial diagnosis");
        rule.getNotes().add("  • The “clock” starts over");
        rule.getNotes().add("    o The disease-free interval (greater than 3 years) is now computed from the date of the last known recurrence");
        rule.getNotes().add("    o The patient must have been disease-free for more than 3 years after the last recurrence of");
        rule.getNotes().add("       A kidney tumor OR");
        rule.getNotes().add("       Metastasis from the kidney tumor");
        rule.getNotes().add("Default to date of diagnosis to compute the greater than 3-year time interval when");
        rule.getNotes().add("  • There was no known recurrence AND/OR");
        rule.getNotes().add("  • Documentation not available to confirm whether or not there was or metastasis");
        rule.getNotes().add("The location and histology of the subsequent tumor is irrelevant. Kidney tumors that occur more than 3 years apart are always multiple primaries");
        rule.getNotes().add("Code a new primary even if the physician states “recurrence”");
        _rules.add(rule);

        // Rule M10 - Abstract a single primary when there are multiple tumors that do not meet any of the criteria in M3-M9.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_KIDNEY_GROUP_ID, "M10");
        rule.setQuestion("");
        rule.setReason("");
        _rules.add(rule);

        /*
        // M3 - Wilms tumors are a single primary. (8960/3)
        MphRule rule = new MphRule(MphConstants.MP_2007_KIDNEY_GROUP_ID, "M3") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (MphConstants.MALIGNANT.equals(i1.getBehavior()) && MphConstants.MALIGNANT.equals(i2.getBehavior()) && MphConstants.WILMS.equals(i1.getHistology()) && MphConstants.WILMS.equals(
                        i2.getHistology()))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
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
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (!GroupUtility.validPairedSiteLaterality(i1.getLaterality(), i2.getLaterality())) {
                    result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". Valid and known laterality should be provided.");
                }
                else if (GroupUtility.areOppositeSides(i1.getLaterality(), i2.getLaterality()))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);

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
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                int diff = GroupUtility.verifyYearsApart(i1, i2, 3);
                if (-1 == diff) {
                    result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". There is no enough diagnosis date information.");
                }
                else if (1 == diff)
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);

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
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                String hist1 = i1.getHistology(), hist2 = i2.getHistology();
                if (MphConstants.SPECIFIC_RENAL_CELL_HISTOLOGIES.containsAll(Arrays.asList(hist1, hist2)) && !hist1.equals(hist2))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Is there one tumor with a specific renal cell type and another tumor with a different specific renal cell type?");
        rule.setReason("One tumor with a specific renal cell type and another tumor with a different specific renal cell type are multiple primaries.");
        _rules.add(rule);

        // M9 -
        rule = new MphRule(MphConstants.MP_2007_KIDNEY_GROUP_ID, "M9") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                String hist1 = i1.getHistology(), hist2 = i2.getHistology();
                List<String> nosList = Arrays.asList("8000", "8010", "8140", "8312");
                if ((nosList.contains(hist1) && MphConstants.NOS_VS_SPECIFIC.containsKey(hist1) && MphConstants.NOS_VS_SPECIFIC.get(hist1).contains(hist2)) || (nosList.contains(hist2)
                        && MphConstants.NOS_VS_SPECIFIC.containsKey(hist2) && MphConstants.NOS_VS_SPECIFIC.get(hist2).contains(hist1)))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);

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
        */

    }
}


