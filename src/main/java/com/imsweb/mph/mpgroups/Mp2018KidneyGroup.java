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


    // KIDNEY 2018 - See MphConstants for AS OF date.

    /*
    Kidney Multiple Primary Rules - Text
    C649
    (Excludes lymphoma and leukemia – M9590-M9992 and Kaposi sarcoma M9140)

    Rule M3	Abstract multiple primaries  when multiple tumors are present in sites with ICD-O site codes that differ at the second (CXxx), third (CxXx) and/or fourth characters (CxxX).
        Note: When codes differ at the second, third, or fourth characters, the tumors are in different primary sites.

    Rule M4	Abstract a single primary when there are bilateral nephroblastomas (previously called Wilms tumors).
        Note:	Timing is irrelevant; the tumors may occur simultaneously OR the contralateral tumor may be diagnosed later (no time limit)

    Rule M5	Abstract multiple primaries when there are tumors in both the right kidney and in the left kidney.  There may be:
        •	A single tumor in each kidney
        •	A single tumor in one kidney and multiple tumors in the contralateral kidney
        •	Multiple tumors in both kidneys
        Note 1:	The rules are hierarchical. Only use this rule when none of the previous rules apply.
        Note 2:	ONLY abstract a single primary when pathology proves the tumor(s) in one kidney is/are metastatic from the other kidney.

    Rule M6	Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 1 in the Equivalent Terms and Definitions. Tumors must be in same kidney and timing is irrelevant.
        Note: The tumors may be subtypes/variants of the same or different NOS histologies.
        •	Same NOS: Clear cell renal cell carcinoma (ccRCC) 8310/3 and papillary renal cell carcinoma 8260/3 are both subtypes of renal cell carcinoma NOS 8312/3 but are distinctly different histologies. Abstract multiple primaries.
        •	Different NOS: Pleomorphic rhabdomyosarcoma 8901/3 is a subtype/variant of rhabdomyosarcoma 8900/3; large cell neuroendocrine carcinoma 8013/3 is a subtype of small cell neuroendocrine tumor 8041/3. They are distinctly different histologies. Abstract multiple primaries.

    Rule M7	Abstract a single primary when separate/non-contiguous tumors are on the same row in Table 1 in the Equivalent Terms and Definitions. Tumors must be in the same kidney and timing is irrelevant.
        Note 1:	The tumors must be the same behavior.  When one tumor is in situ and the other invasive, continue through the rules.
        Note 2:	The same row means the tumors are:
        •	The same histology (same four-digit ICD-O code) OR
        •	One is the preferred term (column 1) and the other is a synonym for the preferred term (column 2) OR
        •	A NOS (column 1/column 2) and the other is a subtype/variant of that NOS (column 3)

    Rule M8	Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 1 in the Equivalent Terms and Definitions. Tumors must be in the same kidney and timing is irrelevant.
        Note:	 Each row in the table is a distinctly different histology.

    Rule M9	Abstract a single primary when an in situ tumor is diagnosed after an invasive tumor AND tumors occur in the same kidney.
        Note 1:	The rules are hierarchical. Only use this rule when none of the previous rules apply.
        Note 2:	The tumors may be a NOS and a subtype/variant of that NOS. See Table 1 in the Equivalent Terms and Definitions for listings of NOS and subtype/variants.
        Note 3:	Once the patient has an invasive tumor, the in situ is recorded as a recurrence for those registrars who collect recurrence data.

    Rule M10	Abstract a single primary (recurrence) when tumors recur less than or equal to three years apart.
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

    Rule M11	Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than three years after the original diagnosis or last recurrence.
        Note 1:	Clinically disease-free means that there was no evidence of recurrence on follow-up.
        •	Scans are NED
        •	Urine cytology is negative
        •	All other work-up is NED
        Note 2:	When there is a recurrence less than or equal to three years of diagnosis, the “clock” starts over. The time interval is calculated from the date of last recurrence. In other words, the patient must have been disease-free for greater than three years from the date of the last recurrence.
        Note 3:	When it is unknown/not documented whether the patient had a recurrence, default to date of diagnosis to compute the time interval.
        Note 4:	The physician may state this is a recurrence, meaning the patient had a previous kidney tumor and now has another kidney tumor. Follow the rules; do not attempt to interpret the physician’s statement.
        Note 5:	The location and histology of the subsequent tumor is irrelevant. Kidney tumors that occur more than 3 years apart are always multiple primaries.

    Rule M12	Abstract a single primary when there are multiple tumors that do not meet any of the above criteria.
        Note:	Use caution when applying this default rule.  Please confirm that you have not overlooked an applicable rule.

    */


    // KIDNEY 2019 - See MphConstants for AS OF date.

    /*
    Kidney Multiple Primary Rules
    C649
    (Excludes lymphoma and leukemia M9590 – M9992 and Kaposi sarcoma M9140)


    Rule M3	Abstract multiple primaries  when multiple tumors are present in sites with ICD-O site codes that differ at the second (CXxx), third (CxXx) and/or fourth characters (CxxX).
        Note: When codes differ at the second, third, or fourth characters, the tumors are in different primary sites.

    Rule M4	Abstract a single primaryi when there are bilateral nephroblastomas (previously called Wilms tumors).
        Note:	Timing is irrelevant; the tumors may occur simultaneously OR the contralateral tumor may be diagnosed later (no time limit).

    Rule M5	Abstract multiple primariesii when there are tumors in both the right kidney and in the left kidney.  There may be:
        •	A single tumor in each kidney
        •	A single tumor in one kidney and multiple tumors in the contralateral kidney
        •	Multiple tumors in both kidneys
        Note 1:	The rules are hierarchical. Only use this rule when none of the previous rules apply.
        Note 2:	ONLY abstract a single primary when pathology proves the tumor(s) in one kidney is/are metastatic from the other kidney.

    Rule M6	Abstract multiple primariesii when the patient has a subsequent tumor after being clinically disease-free for greater than three years after the original diagnosis or last recurrence.
        Note 1:	Clinically disease-free means that there was no evidence of recurrence on follow-up.
            •	Scans are NED
            •	Urine cytology is negative
            •	All other work-up is NED
        Note 2:	When there is a recurrence less than or equal to three years of diagnosis, the “clock” starts over. The time interval is calculated from the date of last recurrence. In other words, the patient must have been disease-free for greater than three years from the date of the last recurrence.
        Note 3:	When it is unknown/not documented whether the patient had a recurrence, default to date of diagnosis to compute the time interval.
        Note 4:	The physician may state this is a recurrence, meaning the patient had a previous kidney tumor and now has another kidney tumor. Follow the rules; do not attempt to interpret the physician’s statement.
        Note 5:	The location and histology of the subsequent tumor is irrelevant. Kidney tumors that occur more than 3 years apart are always multiple primaries.

    Rule M7	Abstract multiple primariesii when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 1 in the Equivalent Terms and Definitions. Tumors must be in same kidney.
        Note: The tumors may be subtypes/variants of the same or different NOS histologies.
            •	Same NOS: Clear cell renal cell carcinoma (ccRCC) 8310/3 and papillary renal cell carcinoma 8260/3 are both subtypes of renal cell carcinoma NOS 8312/3 but are distinctly different histologies. Abstract multiple primaries.
            •	Different NOS: Pleomorphic rhabdomyosarcoma 8901/3 is a subtype/variant of rhabdomyosarcoma 8900/3; large cell neuroendocrine carcinoma 8013/3 is a subtype of small cell neuroendocrine tumor 8041/3. They are distinctly different histologies. Abstract multiple primaries.

    Rule M8	Abstract a single primaryi when synchronous, separate/non-contiguous tumors are on the same row in Table 1 in the Equivalent Terms and Definitions. Tumors must be in the same kidney.
        Note 1:	The tumors must be the same behavior.  When one tumor is in situ and the other invasive, continue through the rules.
        Note 2:	The same row means the tumors are:
            •	The same histology (same four-digit ICD-O code) OR
            •	One is the preferred term (column 1) and the other is a synonym for the preferred term (column 2) OR
            •	A NOS (column 1/column 2) and the other is a subtype/variant of that NOS (column 3)

    Rule M9	Abstract multiple primariesii when separate/non-contiguous tumors are on different rows in Table 1 in the Equivalent Terms and Definitions. Tumors must be in the same kidney.
        Note:	 Each row in the table is a distinctly different histology.

    Rule M10	Abstract a single primaryi when an in situ tumor is diagnosed after an invasive tumor AND tumors occur in the same kidney.
        Note 1:	The rules are hierarchical. Only use this rule when none of the previous rules apply.
        Note 2:	The tumors may be a NOS and a subtype/variant of that NOS. See Table 1 in the Equivalent Terms and Definitions for listings of NOS and subtype/variants.
        Note 3:	Once the patient has an invasive tumor, the in situ is recorded as a recurrence for those registrars who collect recurrence data.

    Rule M11	Abstract a single primaryi (the invasive) when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor in the same kidney.
        Note 1:	The rules are hierarchical. Only use this rule when none of the previous rules apply.
        Note 2:	The tumors may be a NOS and a subtype/variant of that NOS.
        Note 3:	When the case has been abstracted, change behavior code on original abstract from /2 to /3.
        Note 4:	Do not change date of diagnosis.
        Note 5:	If the case has already been submitted to the central registry, report all changes.
        Note 6:	The physician may stage both tumors because staging and determining multiple primaries are done for different reasons. Staging determines which treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).
        Note 7:	See the COC and SEER manuals for instructions on coding other data items such as Date of Diagnosis, Accession Year and Sequence Number.

    Rule M12	Abstract multiple primariesii when an invasive tumor occurs more than 60 days after an in situ tumor in the same kidney.
        Note 1:	The rules are hierarchical. Only use this rule when none of the previous rules apply.
        Note 2:	Abstract both the invasive and in situ tumors.
        Note 3:	Abstract as multiple primaries even if physician states the invasive tumor is disease recurrence or progression.

    Rule M13	Abstract a single primaryi when there are multiple tumors that do not meet any of the above criteria.
        Note:	Use this rule as a last resort.  Please confirm that you have not overlooked an applicable rule.
        Example 1:	Patient presents in 2018 with renal cell carcinoma in the right kidney.  Patient has a history of a previous renal cell carcinoma in the right kidney diagnosed in 2016.  This is a single primary because it is the same primary site and the same histology.
        Example 2:	Patient presents in 2020 with a clear cell renal cell carcinoma 8310/3 in the left kidney.  The patient was diagnosed with renal cell carcinoma 8312/3 in 2018.  This is a single primary because it is the same primary site and a NOS and subtype/variant of that NOS.
    */





    // Kidney Multiple Primary Rules - Text
    // C649
    // (Excludes lymphoma and leukemia – M9590-M9992 and Kaposi sarcoma M9140)
    public Mp2018KidneyGroup() {
        super(MphConstants.MP_2018_KIDNEY_GROUP_ID, MphConstants.MP_2018_KIDNEY_GROUP_NAME, "C649", null, null,
                "9590-9992, 9140", "2-3,6", "2018-9999");

        // Rule M3	Abstract multiple primaries when multiple tumors are present in sites with ICD-O site codes that differ at the second (CXxx), third (CxXx) and/or fourth characters (CxxX).
        MphRule rule = new MphRuleTopography234Code(MphConstants.MP_2018_KIDNEY_GROUP_ID, "M3");
        rule.getNotes().add("When codes differ at the second, third, or fourth characters, the tumors are in different primary sites.");
        _rules.add(rule);

        // Rule M4	Abstract a single primary when there are bilateral nephroblastomas (previously called Wilms tumors).
        rule = new MphRule(MphConstants.MP_2018_KIDNEY_GROUP_ID, "M4") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (MphConstants.MALIGNANT.equals(i1.getBehavior()) && MphConstants.MALIGNANT.equals(i2.getBehavior()) &&
                    MphConstants.WILMS.equals(i1.getHistology()) && MphConstants.WILMS.equals(i2.getHistology()) &&
                    GroupUtility.areOppositeSides(i1.getLaterality(), i2.getLaterality())) {
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                }
                return result;
            }
        };
        rule.setQuestion("Are there bilateral nephroblastomas (previously called Wilms tumors)?");
        rule.setReason("Bilateral nephroblastomas are a single primary.");
        rule.getNotes().add("Timing is irrelevant; the tumors may occur simultaneously OR the contralateral tumor may be diagnosed later (no time limit)");
        _rules.add(rule);

        // Rule M5	Abstract multiple primaries when there are tumors in both the right kidney and in the left kidney.
        rule = new MphRuleLeftAndRight(MphConstants.MP_2018_KIDNEY_GROUP_ID, "M5", null, null);
        rule.setQuestion("Are there tumors in both the left and right kidney?");
        rule.setReason("Tumors in both the right kidney and in the left kidney are multiple primaries.");
        rule.getNotes().add("The rules are hierarchical. Only use this rule when none of the previous rules apply.");
        rule.getNotes().add("ONLY abstract a single primary when pathology proves the tumor(s) in one kidney is/are metastatic from the other kidney.");
        _rules.add(rule);

        // Rule M6	Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 1 in the Equivalent Terms and Definitions. Tumors must be in same kidney and timing is irrelevant.
        rule = new MphRuleTwoOrMoreDifferentSubTypesInTable(MphConstants.MP_2018_KIDNEY_GROUP_ID, "M6", MphConstants.KIDNEY_2018_TABLE1_SUBTYPES, MphConstants.KIDNEY_2018_SUBTYPE_NOS, true);
        rule.setQuestion("Are separate/non-contiguous tumors two or more different subtypes/variants in Column 3, Table 1 in the Equivalent Terms and Definitions (Tumors must be in the same kidney)?");
        rule.setReason("Separate/non-contiguous tumors that are two or more different subtypes/variants in Column 3, Table 1 in the Equivalent Terms and Definitions (Tumors must be in the same kidney), are multiple primaries.");
        rule.getNotes().add("Note: The tumors may be subtypes/variants of the same or different NOS histologies.");
        rule.getNotes().add("•	Same NOS: Clear cell renal cell carcinoma (ccRCC) 8310/3 and papillary renal cell carcinoma 8260/3 are both subtypes of renal cell carcinoma NOS 8312/3 but are distinctly different histologies. Abstract multiple primaries.");
        rule.getNotes().add("•	Different NOS: Pleomorphic rhabdomyosarcoma 8901/3 is a subtype/variant of rhabdomyosarcoma 8900/3; large cell neuroendocrine carcinoma 8013/3 is a subtype of small cell neuroendocrine tumor 8041/3. They are distinctly different histologies. Abstract multiple primaries.");
        _rules.add(rule);

        // Rule M7	Abstract a single primary when separate/non-contiguous tumors are on the same row in Table 1 in the Equivalent Terms and Definitions. Tumors must be in the same kidney and timing is irrelevant.
        rule = new MphRuleSameRowInTable(MphConstants.MP_2018_KIDNEY_GROUP_ID, "M7", MphConstants.KIDNEY_2018_TABLE1_ROWS, MphConstants.KIDNEY_2018_SUBTYPE_NOS, true, true, false);
        rule.setQuestion("Are separate/non-contiguous tumors on the same row in Table 1 in the Equivalent Terms and Definitions (Tumors must be in the same kidney)?");
        rule.setReason("Separate/non-contiguous tumors that are on the same row in Table 1 in the Equivalent Terms and Definitions (Tumors must be in the same kidney), are multiple primaries.");
        rule.getNotes().add("The tumors must be the same behavior.  When one tumor is in situ and the other invasive, continue through the rules.");
        rule.getNotes().add("The same row means the tumors are:");
        rule.getNotes().add("  • The same histology (same four-digit ICD-O code) OR");
        rule.getNotes().add("  • One is the preferred term (column 1) and the other is a synonym for the preferred term (column 2) OR");
        rule.getNotes().add("  • A NOS (column 1/column 2) and the other is a subtype/variant of that NOS (column 3)");
        _rules.add(rule);

        // Rule M8	Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 1 in the Equivalent Terms and Definitions. Tumors must be in the same kidney and timing is irrelevant.
        rule = new MphRuleDifferentRowsInTable(MphConstants.MP_2018_KIDNEY_GROUP_ID, "M8", MphConstants.KIDNEY_2018_TABLE1_ROWS, true);
        rule.setQuestion("Are separate/non-contiguous tumors on different rows in Table 1 in the Equivalent Terms and Definitions (Tumors must be in the same kidney)?");
        rule.setReason("Separate/non-contiguous tumors that are on different rows in Table 1 in the Equivalent Terms and Definitions (Tumors must be in the same kidney), are multiple primaries.");
        rule.getNotes().add("Each row in the table is a distinctly different histology.");
        _rules.add(rule);

        // Rule M9	Abstract a single primary when an in situ tumor is diagnosed after an invasive tumor AND tumors occur in the same kidney.
        rule = new MphRuleInSituAfterInvasive(MphConstants.MP_2018_KIDNEY_GROUP_ID, "M9", true);
        rule.setQuestion("Is there an in situ tumor following an invasive tumor and tumors are in the same kidney?");
        rule.setReason("An in situ tumor diagnosed following an invasive tumor and tumors are in the same kidney is a single primary.");
        rule.getNotes().add("The rules are hierarchical. Only use this rule when none of the previous rules apply.");
        rule.getNotes().add("The tumors may be a NOS and a subtype/variant of that NOS. See Table 1 in the Equivalent Terms and Definitions for listings of NOS and subtype/variants.");
        rule.getNotes().add("Once the patient has an invasive tumor, the in situ is recorded as a recurrence for those registrars who collect recurrence data.");
        _rules.add(rule);

        // Rule M10	Abstract a single primary (recurrence) when tumors recur less than or equal to 3 years apart.
        rule = new MphRuleDiagnosisDateLess3Years(MphConstants.MP_2018_KIDNEY_GROUP_ID, "M10");
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

        // Rule M11	Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than three years after the original diagnosis or last recurrence.
        rule = new MphRuleDiagnosisDateGreaterThan3Years(MphConstants.MP_2018_KIDNEY_GROUP_ID, "M11");
        rule.getNotes().add("Clinically disease-free means that there was no evidence of recurrence on follow-up.");
        rule.getNotes().add("  • Scans are NED");
        rule.getNotes().add("  • Urine cytology is negative");
        rule.getNotes().add("  • All other work-up is NED");
        rule.getNotes().add("When there is a recurrence less than or equal to three years of diagnosis, the “clock” starts over. The time interval is calculated from the date of last recurrence. In other words, the patient must have been disease-free for greater than three years from the date of the last recurrence.");
        rule.getNotes().add("When it is unknown/not documented whether the patient had a recurrence, default to date of diagnosis to compute the time interval.");
        rule.getNotes().add("The physician may state this is a recurrence, meaning the patient had a previous kidney tumor and now has another kidney tumor. Follow the rules; do not attempt to interpret the physician’s statement.");
        rule.getNotes().add("The location and histology of the subsequent tumor is irrelevant. Kidney tumors that occur more than 3 years apart are always multiple primaries.");
        _rules.add(rule);

        // Rule M12	Abstract a single primary when there are multiple tumors that do not meet any of the above criteria.
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_KIDNEY_GROUP_ID, "M11");
        rule.getNotes().add("Use caution when applying this default rule.  Please confirm that you have not overlooked an applicable rule.");
        _rules.add(rule);
    }
}


