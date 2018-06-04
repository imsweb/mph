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
    Kidney Multiple Primary Rules - Text
    C649
    (Excludes lymphoma and leukemia – M9590-M9992 and Kaposi sarcoma M9140)

    Rule M3	Abstract multiple primaries  when multiple tumors are present in sites with ICD-O site codes that differ at the second (CXxx), third (CxXx) and/or fourth characters (CxxX).
        Note: When codes differ at the second, third, or fourth characters, the tumors are in different primary sites.

    Rule M4	Abstract a single primary when there are bilateral nephroblastomas (previously called Wilms tumors).
        Note:	Timing is irrelevant; the tumors may occur simultaneously OR the contralateral tumor may be diagnosed later (no time limit)

    Rule M5	Abstract multiple primaries when there are tumors in both the right kidney and in the left kidney.
        Note 1:	The rules are hierarchical. Only use this rule when none of the previous rules apply.
        Note 2:	ONLY abstract a single primary when pathology proves the tumor(s) in one kidney is/are metastatic from the other kidney.

    Rule M6	Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 1 in the Equivalent Terms and Definitions. Tumors must be in the same kidney.
        Note:	 Each row in the table is a distinctly different histology.

    Rule M7	Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 1 in the Equivalent Terms and Definitions. Tumors must be in same kidney.
        Note: The tumors may be subtypes/variants of the same or different NOS histologies.
        •	Same NOS: Clear cell renal cell carcinoma (ccRCC) 8310/3 and papillary renal cell carcinoma 8260/3 are both subtypes of renal cell carcinoma NOS 8312/3 but are distinctly different histologies. Abstract multiple primaries.
        •	Different NOS: Pleomorphic rhabdomyosarcoma 8901/3 is a subtype/variant of rhabdomyosarcoma 8900/3; large cell neuroendocrine carcinoma 8013/3 is a subtype of small cell neuroendocrine tumor 8041/3. They are distinctly different histologies. Abstract multiple primaries.

    Rule M8	Abstract a single primary when an in situ tumor is diagnosed after an invasive tumor AND tumors occur in the same kidney.
        Note 1:	The rules are hierarchical. Only use this rule when none of the previous rules apply.
        Note 2:	The tumors may be a NOS and a subtype/variant of that NOS. See Table 1 in the Equivalent Terms and Definitions for listings of NOS and subtype/variants.
        Note 3:	Once the patient has an invasive tumor, the in situ is recorded as a recurrence for those registrars who collect recurrence data.

    Rule M9	Abstract a single primary (recurrence) when tumors recur less than or equal to 3 years apart.
        Note 1:	These rules are hierarchical. Only use this rule when none of the previous rules apply.
        Note 2:	Using the previous rules, the recurrence must be
        •	In the same kidney AND
        •	The histology must be on the same row in Table 1
            	Identical histologies
            	A histology (column 1) and a synonym (column 2)
        •	NOS and a subtype/variant
        Note 3:	Examples of NOS and subtypes/variants include:
        •	Renal cell carcinoma 8312 and a subtype/variant of renal cell
        •	Rhabdomyosarcoma 8900 and a subtype/variant of rhabdomyosarcoma
        •	Sarcoma 8800 and a subtype/variant of sarcoma
        •	Small cell neuroendocrine tumor 8041 and a subtype/variant of small cell neuroendocrine tumor

    Rule M10	Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than three years after the original diagnosis or last recurrence.
        Note 1:	Clinically disease-free means that there was no evidence of recurrence on follow-up.
        •	Scans are NED
        •	Urine cytology is negative
        •	All other work-up is NED
        Note 2:	When there is a recurrence less than or equal to three years of diagnosis, the “clock” starts over. The time interval is calculated from the date of last recurrence. In other words, the patient must have been disease-free for greater than three years from the date of the last recurrence.
        Note 3:	When it is unknown/not documented whether the patient had a recurrence, default to date of diagnosis to compute the time interval.
        Note 4:	The physician may state this is a recurrence, meaning the patient had a previous kidney tumor and now has another kidney tumor. Follow the rules; do not attempt to interpret the physician’s statement.
        Note 5:	The location and histology of the subsequent tumor is irrelevant. Kidney tumors that occur more than 3 years apart are always multiple primaries.

    Rule M11	Abstract a single primary when there are multiple tumors that do not meet any of the above criteria.


    */
    // Kidney Multiple Primary Rules - Text
    // C649
    // (Excludes lymphoma and leukemia – M9590-M9992 and Kaposi sarcoma M9140)
    // TODO
    public Mp2018KidneyGroup() {
        super(MphConstants.MP_2018_KIDNEY_GROUP_ID, MphConstants.MP_2018_KIDNEY_GROUP_NAME, "C649", null, null,
                "9590-9992, 9140", "2-3,6", "2018-9999");

        // Rule M3	Abstract multiple primaries  when multiple tumors are present in sites with ICD-O site codes that differ at the second (CXxx), third (CxXx) and/or fourth characters (CxxX).
        // TODO
        MphRule rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_KIDNEY_GROUP_ID, "M3");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("When codes differ at the second, third, or fourth characters, the tumors are in different primary sites.");
        _rules.add(rule);

        // Rule M4	Abstract a single primary when there are bilateral nephroblastomas (previously called Wilms tumors).
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_KIDNEY_GROUP_ID, "M4");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("Timing is irrelevant; the tumors may occur simultaneously OR the contralateral tumor may be diagnosed later (no time limit)");
        _rules.add(rule);

        // Rule M5	Abstract multiple primaries when there are tumors in both the right kidney and in the left kidney.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_KIDNEY_GROUP_ID, "M5");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The rules are hierarchical. Only use this rule when none of the previous rules apply.");
        rule.getNotes().add("ONLY abstract a single primary when pathology proves the tumor(s) in one kidney is/are metastatic from the other kidney.");
        _rules.add(rule);

        // Rule M6	Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 1 in the Equivalent Terms and Definitions. Tumors must be in the same kidney.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_KIDNEY_GROUP_ID, "M6");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("Each row in the table is a distinctly different histology.");
        _rules.add(rule);

        // Rule M7	Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 1 in the Equivalent Terms and Definitions. Tumors must be in same kidney.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_KIDNEY_GROUP_ID, "M7");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The tumors may be subtypes/variants of the same or different NOS histologies.");
        rule.getNotes().add(
                "  • Same NOS: Clear cell renal cell carcinoma (ccRCC) 8310/3 and papillary renal cell carcinoma 8260/3 are both subtypes of renal cell carcinoma NOS 8312/3 but are distinctly different histologies. Abstract multiple primaries.");
        rule.getNotes().add(
                "  • Different NOS: Pleomorphic rhabdomyosarcoma 8901/3 is a subtype/variant of rhabdomyosarcoma 8900/3; large cell neuroendocrine carcinoma 8013/3 is a subtype of small cell neuroendocrine tumor 8041/3. They are distinctly different histologies. Abstract multiple primaries.");
        _rules.add(rule);

        // Rule M8	Abstract a single primary when an in situ tumor is diagnosed after an invasive tumor AND tumors occur in the same kidney.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_KIDNEY_GROUP_ID, "M8");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The rules are hierarchical. Only use this rule when none of the previous rules apply.");
        rule.getNotes().add("The tumors may be a NOS and a subtype/variant of that NOS. See Table 1 in the Equivalent Terms and Definitions for listings of NOS and subtype/variants.");
        rule.getNotes().add("Once the patient has an invasive tumor, the in situ is recorded as a recurrence for those registrars who collect recurrence data.");
        _rules.add(rule);

        // Rule M9	Abstract a single primary (recurrence) when tumors recur less than or equal to 3 years apart.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_KIDNEY_GROUP_ID, "M9");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("These rules are hierarchical. Only use this rule when none of the previous rules apply.");
        rule.getNotes().add("Using the previous rules, the recurrence must be");
        rule.getNotes().add("  • In the same kidney AND");
        rule.getNotes().add("  • The histology must be on the same row in Table 1");
        rule.getNotes().add("     Identical histologies");
        rule.getNotes().add("     A histology (column 1) and a synonym (column 2)");
        rule.getNotes().add("  • NOS and a subtype/variant");
        rule.getNotes().add("Examples of NOS and subtypes/variants include:");
        rule.getNotes().add("  • Renal cell carcinoma 8312 and a subtype/variant of renal cell");
        rule.getNotes().add("  • Rhabdomyosarcoma 8900 and a subtype/variant of rhabdomyosarcoma");
        rule.getNotes().add("  • Sarcoma 8800 and a subtype/variant of sarcoma");
        rule.getNotes().add("  • Small cell neuroendocrine tumor 8041 and a subtype/variant of small cell neuroendocrine tumor");
        _rules.add(rule);

        // Rule M10	Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than three years after the original diagnosis or last recurrence.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_KIDNEY_GROUP_ID, "M10");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("Clinically disease-free means that there was no evidence of recurrence on follow-up.");
        rule.getNotes().add("  • Scans are NED");
        rule.getNotes().add("  • Urine cytology is negative");
        rule.getNotes().add("  • All other work-up is NED");
        rule.getNotes().add(
                "When there is a recurrence less than or equal to three years of diagnosis, the “clock” starts over. The time interval is calculated from the date of last recurrence. In other words, the patient must have been disease-free for greater than three years from the date of the last recurrence.");
        rule.getNotes().add("When it is unknown/not documented whether the patient had a recurrence, default to date of diagnosis to compute the time interval.");
        rule.getNotes().add(
                "The physician may state this is a recurrence, meaning the patient had a previous kidney tumor and now has another kidney tumor. Follow the rules; do not attempt to interpret the physician’s statement.");
        rule.getNotes().add("The location and histology of the subsequent tumor is irrelevant. Kidney tumors that occur more than 3 years apart are always multiple primaries.");
        _rules.add(rule);

        // Rule M11	Abstract a single primary when there are multiple tumors that do not meet any of the above criteria.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_KIDNEY_GROUP_ID, "M11");
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


