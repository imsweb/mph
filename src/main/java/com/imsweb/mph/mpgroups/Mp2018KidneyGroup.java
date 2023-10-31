/*
 * Copyright (C) 2018 Information Management Services, Inc.
 */
package com.imsweb.mph.mpgroups;

import com.imsweb.mph.MphConstants;
import com.imsweb.mph.MphGroup;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphUtils;
import com.imsweb.mph.MphUtils.MpResult;
import com.imsweb.mph.RuleExecutionContext;
import com.imsweb.mph.internal.TempRuleResult;
import com.imsweb.mph.mprules.MpRuleDifferentRowInTable;
import com.imsweb.mph.mprules.MpRuleInsituAfterInvasiveSameSide;
import com.imsweb.mph.mprules.MpRuleInvasiveAfterInsituGreaterThan60Days;
import com.imsweb.mph.mprules.MpRuleInvasiveAfterInsituLessThan60DaysSameSide;
import com.imsweb.mph.mprules.MpRuleLaterality;
import com.imsweb.mph.mprules.MpRuleNoCriteriaSatisfied;
import com.imsweb.mph.mprules.MpRuleYearsApart;

public class Mp2018KidneyGroup extends MphGroup {

    // Kidney Multiple Primary Rules - Text
    // C649
    // (Excludes lymphoma and leukemia M9590 – M9992 and Kaposi sarcoma M9140)
    public Mp2018KidneyGroup() {
        super(MphConstants.STR_2018_AND_LATER_KIDNEY, MphConstants.SOLID_TUMOR_2018_KIDNEY, "C649", null, null,
                "9590-9993, 9140", "2-3,6", "2018-9999");

        // Rule M3 Abstract multiple primaries when multiple tumors are present in sites with ICD-O site codes that differ at the second (CXxx), third (CxXx) and/or fourth characters (CxxX).
        MphRule rule = new MphRule(MphConstants.SOLID_TUMOR_2018_KIDNEY, "M3") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
                TempRuleResult result = new TempRuleResult();
                if (!i1.getPrimarySite().equals(i2.getPrimarySite()))
                    result.setFinalResult(MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Are there multiple tumors are present in sites with ICD-O site codes that differ at the second (CXxx), third (CxXx) and/or fourth characters (CxxX)?");
        rule.setReason("When multiple tumors are present in sites with ICD-O site codes that differ at the second (CXxx), third (CxXx) and/or fourth characters (CxxX), abstract multiple primaries.");
        rule.getNotes().add("When codes differ at the second, third, or fourth characters, the tumors are in different primary sites.");
        _rules.add(rule);

        // Rule M4 Abstract a single primary when there are bilateral nephroblastomas (previously called Wilms tumors).
        rule = new MphRule(MphConstants.SOLID_TUMOR_2018_KIDNEY, "M4") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
                TempRuleResult result = new TempRuleResult();
                if (MphConstants.MALIGNANT.equals(i1.getBehavior()) && MphConstants.MALIGNANT.equals(i2.getBehavior()) &&
                        MphConstants.WILMS.equals(i1.getHistology()) && MphConstants.WILMS.equals(i2.getHistology()))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                return result;
            }
        };
        rule.setQuestion("Are there bilateral nephroblastomas (previously called Wilms tumors)?");
        rule.setReason("Bilateral nephroblastomas are a single primary.");
        rule.getNotes().add("Timing is irrelevant; the tumors may occur simultaneously OR the contralateral tumor may be diagnosed later (no time limit)");
        _rules.add(rule);

        // Rule M5 Abstract multiple primaries when there are tumors in both the right kidney and in the left kidney.
        rule = new MpRuleLaterality(MphConstants.SOLID_TUMOR_2018_KIDNEY, "M5");
        rule.setQuestion("Are there tumors in both the left and right kidney?");
        rule.setReason("Tumors in both the right kidney and in the left kidney are multiple primaries.");
        rule.getNotes().add("The rules are hierarchical. Only use this rule when none of the previous rules apply.");
        rule.getNotes().add("ONLY abstract a single primary when pathology proves the tumor(s) in one kidney is/are metastatic from the other kidney.");
        _rules.add(rule);

        // Rule M6 Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than three years after the original diagnosis or last recurrence.
        rule = new MpRuleYearsApart(MphConstants.SOLID_TUMOR_2018_KIDNEY, "M6", 3);
        rule.getNotes().add("Clinically disease-free means that there was no evidence of recurrence on follow-up.");
        rule.getNotes().add("  - Scans are NED");
        rule.getNotes().add("  - Urine cytology is negative");
        rule.getNotes().add("  - All other work-up is NED");
        rule.getNotes().add(
                "When there is a recurrence less than or equal to three years of diagnosis, the \"clock\" starts over. The time interval is calculated from the date of last recurrence. In other words, the patient must have been disease-free for greater than three years from the date of the last recurrence.");
        rule.getNotes().add("When it is unknown/not documented whether the patient had a recurrence, default to date of diagnosis to compute the time interval.");
        rule.getNotes().add(
                "The physician may state this is a recurrence, meaning the patient had a previous kidney tumor and now has another kidney tumor. Follow the rules; do not attempt to interpret the physician’s statement.");
        rule.getNotes().add("The location and histology of the subsequent tumor is irrelevant. Kidney tumors that occur more than 3 years apart are always multiple primaries.");
        _rules.add(rule);

        // Rule M7 Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 1 in the Equivalent Terms and Definitions. Tumors must be in same kidney and timing is irrelevant.
        rule = new MphRule(MphConstants.SOLID_TUMOR_2018_KIDNEY, "M7") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
                TempRuleResult result = new TempRuleResult();
                String h1 = i1.getHistology();
                String icd1 = i1.getIcdCode();
                String h2 = i2.getHistology();
                String icd2 = i2.getIcdCode();
                String subtype1 = MphConstants.KIDNEY_2018_TABLE1_SUBTYPES.containsKey(h1) ? MphConstants.KIDNEY_2018_TABLE1_SUBTYPES.get(h1) : MphConstants.KIDNEY_2018_TABLE1_SUBTYPES.get(icd1);
                String subtype2 = MphConstants.KIDNEY_2018_TABLE1_SUBTYPES.containsKey(h2) ? MphConstants.KIDNEY_2018_TABLE1_SUBTYPES.get(h2) : MphConstants.KIDNEY_2018_TABLE1_SUBTYPES.get(icd2);
                if (subtype1 != null && subtype2 != null && !subtype1.contains(subtype2) && !subtype2.contains(subtype1))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion(
                "Are separate/non-contiguous tumors two or more different subtypes/variants in Column 3, Table 1 in the Equivalent Terms and Definitions (Tumors must be in the same kidney)?");
        rule.setReason(
                "Separate/non-contiguous tumors that are two or more different subtypes/variants in Column 3, Table 1 in the Equivalent Terms and Definitions (Tumors must be in the same kidney), are multiple primaries.");
        rule.getNotes().add("Note: The tumors may be subtypes/variants of the same or different NOS histologies.");
        rule.getNotes().add(
                "- Same NOS: Clear cell renal cell carcinoma (ccRCC) 8310/3 and papillary renal cell carcinoma 8260/3 are both subtypes of renal cell carcinoma NOS 8312/3 but are distinctly different histologies. Abstract multiple primaries.");
        rule.getNotes().add(
                "- Different NOS: Pleomorphic rhabdomyosarcoma 8901/3 is a subtype/variant of rhabdomyosarcoma 8900/3; large cell neuroendocrine carcinoma 8013/3 is a subtype of small cell neuroendocrine tumor 8041/3. They are distinctly different histologies. Abstract multiple primaries.");
        _rules.add(rule);

        // Rule M8 Abstract a single primary when synchronous, separate/non-contiguous tumors are on the same row in Table 1 in the Equivalent Terms and Definitions. Tumors must be in the same kidney.
        rule = new MphRule(MphConstants.SOLID_TUMOR_2018_KIDNEY, "M8") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
                TempRuleResult result = new TempRuleResult();

                String h1 = i1.getHistology();
                String icd1 = i1.getIcdCode();
                String h2 = i2.getHistology();
                String icd2 = i2.getIcdCode();
                String row1 = MphConstants.KIDNEY_2018_TABLE1_ROWS.containsKey(h1) ? MphConstants.KIDNEY_2018_TABLE1_ROWS.get(h1) : MphConstants.KIDNEY_2018_TABLE1_ROWS.get(icd1);
                String row2 = MphConstants.KIDNEY_2018_TABLE1_ROWS.containsKey(h2) ? MphConstants.KIDNEY_2018_TABLE1_ROWS.get(h2) : MphConstants.KIDNEY_2018_TABLE1_ROWS.get(icd2);
                if (!GroupUtility.sameHistologies(icd1, icd2) && (row1 == null || row2 == null)) {
                    result.setFinalResult(MpResult.QUESTIONABLE);
                    result.setMessageNotInTable(this.getStep(), this.getGroupName(), row1, row2, icd1, icd2);
                }
                else if (GroupUtility.sameHistologies(icd1, icd2) || row1.equals(row2)) {
                    int sixtyDaysApart = GroupUtility.verifyDaysApart(i1, i2, 60);
                    if (MphConstants.DATE_VERIFY_APART == sixtyDaysApart)
                        return result;
                    if (!GroupUtility.validPairedSiteLaterality(i1.getLaterality(), i2.getLaterality())) {
                        result.setPotentialResult(MpResult.SINGLE_PRIMARY);
                        if (MphConstants.DATE_VERIFY_UNKNOWN == sixtyDaysApart)
                            result.setMessageUnknownLatAndDate(this.getStep(), this.getGroupName());
                        else
                            result.setMessageUnknownLaterality(this.getStep(), this.getGroupName());

                    }
                    else if (MphConstants.DATE_VERIFY_UNKNOWN == sixtyDaysApart) {
                        result.setPotentialResult(MpResult.SINGLE_PRIMARY);
                        result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupName());
                    }
                    else if (GroupUtility.areSameSide(i1.getLaterality(), i2.getLaterality())) {
                        if ("8311".equals(h1) && h1.equals(h2)) {
                            result.setFinalResult(MpResult.QUESTIONABLE);
                            result.setMessage("8311 can be abstracted as multiple primaries if you have any of the following combinations (all coded 8311):\n"
                                    + "- MiT family translocation renal cell carcinoma and Hereditary leiomyomatosis\n"
                                    + "- MiT family translocation renal cell carcinoma and Renal cell carcinoma-associated renal cell carcinoma\n"
                                    + "- MiT family translocation renal cell carcinoma and Succinate dehydrogenase-deficient renal cell carcinoma (SDHS)\n"
                                    + "- Hereditary leiomyomatosis and Succinate dehydrogenase-deficient renal cell carcinoma (SDHS)\n"
                                    + "- Renal cell carcinoma-associated renal cell carcinoma and Succinate dehydrogenase-deficient renal cell carcinoma (SDHS)");
                        }
                        else
                            result.setFinalResult(MpResult.SINGLE_PRIMARY);
                    }
                }

                return result;
            }
        };
        rule.setQuestion("Are synchronous, separate/non-contiguous tumors on the same row in Table 1 in the Equivalent Terms and Definitions (Tumors must be in the same kidney)?");
        rule.setReason(
                "Synchronous, separate/non-contiguous tumors that are on the same row in Table 1 in the Equivalent Terms and Definitions (Tumors must be in the same kidney), are a single primary.");
        rule.getNotes().add("The same row means the tumors are:");
        rule.getNotes().add("  - The same histology (same four-digit ICD-O code) OR");
        rule.getNotes().add("  - One is the preferred term (column 1) and the other is a synonym for the preferred term (column 2) OR");
        rule.getNotes().add("  - A NOS (column 1/column 2) and the other is a subtype/variant of that NOS (column 3)");
        _rules.add(rule);

        // Rule M9 Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 1 in the Equivalent Terms and Definitions. Tumors must be in the same kidney and timing is irrelevant.
        rule = new MpRuleDifferentRowInTable(MphConstants.SOLID_TUMOR_2018_KIDNEY, "M9", MphConstants.KIDNEY_2018_TABLE1_ROWS);
        rule.setQuestion("Are separate/non-contiguous tumors on different rows in Table 1 in the Equivalent Terms and Definitions (Tumors must be in the same kidney)?");
        rule.setReason("Separate/non-contiguous tumors that are on different rows in Table 1 in the Equivalent Terms and Definitions (Tumors must be in the same kidney), are multiple primaries.");
        rule.getNotes().add("Each row in the table is a distinctly different histology.");
        _rules.add(rule);

        // Rule M10 Abstract a single primary when an in situ tumor is diagnosed after an invasive tumor AND tumors occur in the same kidney.
        rule = new MpRuleInsituAfterInvasiveSameSide(MphConstants.SOLID_TUMOR_2018_KIDNEY, "M10");
        rule.setQuestion("Is there an in situ tumor following an invasive tumor and tumors are in the same kidney?");
        rule.setReason("An in situ tumor diagnosed following an invasive tumor and tumors are in the same kidney is a single primary.");
        rule.getNotes().add("The rules are hierarchical. Only use this rule when none of the previous rules apply.");
        rule.getNotes().add("The tumors may be a NOS and a subtype/variant of that NOS. See Table 1 in the Equivalent Terms and Definitions for listings of NOS and subtype/variants.");
        rule.getNotes().add("Once the patient has an invasive tumor, the in situ is recorded as a recurrence for those registrars who collect recurrence data.");
        _rules.add(rule);

        // Rule M11 Abstract a single primary (the invasive) when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor in the same kidney.
        rule = new MpRuleInvasiveAfterInsituLessThan60DaysSameSide(MphConstants.SOLID_TUMOR_2018_KIDNEY, "M11");
        rule.getNotes().add("The rules are hierarchical. Only use this rule when none of the previous rules apply.");
        rule.getNotes().add("The tumors may be a NOS and a subtype/variant of that NOS.");
        rule.getNotes().add("When the case has been abstracted, change behavior code on original abstract from /2 to /3.");
        rule.getNotes().add("Do not change date of diagnosis.");
        rule.getNotes().add("If the case has already been submitted to the central registry, report all changes.");
        rule.getNotes().add(
                "The physician may stage both tumors because staging and determining multiple primaries are done for different reasons. Staging determines which treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).");
        rule.getNotes().add("See the COC and SEER manuals for instructions on coding other data items such as Date of Diagnosis, Accession Year and Sequence Number.");
        _rules.add(rule);

        // Rule M12 Abstract multiple primaries when an invasive tumor occurs more than 60 days after an in situ tumor in the same kidney.
        rule = new MpRuleInvasiveAfterInsituGreaterThan60Days(MphConstants.SOLID_TUMOR_2018_KIDNEY, "M12");
        rule.getNotes().add("The rules are hierarchical. Only use this rule when none of the previous rules apply.");
        rule.getNotes().add("Abstract both the invasive and in situ tumors.");
        rule.getNotes().add("Abstract as multiple primaries even if physician states the invasive tumor is disease recurrence or progression.");
        _rules.add(rule);

        // Rule M13 Abstract a single primary when there are multiple tumors that do not meet any of the above criteria.
        rule = new MpRuleNoCriteriaSatisfied(MphConstants.SOLID_TUMOR_2018_KIDNEY, "M13");
        rule.getNotes().add("Use this rule as a last resort.  Please confirm that you have not overlooked an applicable rule.");
        rule.getExamples().add(
                "Patient presents in 2018 with renal cell carcinoma in the right kidney.  Patient has a history of a previous renal cell carcinoma in the right kidney diagnosed in 2016.  This is a single primary because it is the same primary site and the same histology.");
        rule.getExamples().add(
                "Patient presents in 2020 with a clear cell renal cell carcinoma 8310/3 in the left kidney.  The patient was diagnosed with renal cell carcinoma 8312/3 in 2018.  This is a single primary because it is the same primary site and a NOS and subtype/variant of that NOS.");
        _rules.add(rule);
    }
}


