/*
 * Copyright (C) 2018 Information Management Services, Inc.
 */
package com.imsweb.mph.mpgroups;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.imsweb.mph.MphComputeOptions;
import com.imsweb.mph.MphConstants;
import com.imsweb.mph.MphGroup;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphUtils;
import com.imsweb.mph.internal.TempRuleResult;

public class Mp2018BreastGroup extends MphGroup {

    // BREAST 2018 - AS OF 11/02/2018


    /*
    Breast Histology Coding Rules
    C500-C506, C508-C509
    (Excludes lymphoma and leukemia M9590 – M9992 and Kaposi sarcoma M9140)

    Rule M4	Abstract a single primary when there is inflammatory carcinoma in:
        •	Multiple quadrants of same breast OR
        •	Bilateral breasts

    Rule M5	Abstract multiple primaries when there are separate, non-contiguous tumors in sites with ICD-O site codes (C50¬_) that differ at the second (CXxx) and/or third characters (CxXx).
        Note 1:	Tumors with site codes that differ at the second or third character are in different primary sites; for example, a breast tumor C50x and a colon tumor C18x differ at the second and third character.
        Note 2:	This rule does not include metastases. Metastatic tumors are not used to determine multiple primaries; for example, liver metastases from the breast cancer would not be counted as a second primary.

    Rule M6	Abstract multiple primaries when there is bilateral breast cancer (both right and left breast).
        Note 1:	Physician statement “bilateral breast cancer” should not be interpreted as meaning a single primary. The term is descriptive and not used consistently.  The literal definition of bilateral is “cancer in both breasts”.
        Note 2:	It is irrelevant how many tumors are in each breast. Abstract as separate primaries.
        Note 3:	The histologies within each breast may be the same or different.

    Rule M7	Abstract a single primary when the diagnosis is Paget disease with underlying in situ or invasive carcinoma NST (duct/ductal).
    
    Rule M8	Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than five years after the original diagnosis or last recurrence.
        Note 1:	The rules are hierarchical. This rule only applies when there is a subsequent breast tumor.
        Note 2:	Clinically disease-free means that there was no evidence of recurrence on follow-up.
        •	Mammograms are NED
        •	Scans are NED
        •	Tumor biomarkers are NED
        Note 3:	When there is a recurrence less than or equal to five years of diagnosis, the “clock” starts over. The time interval is calculated from the date of last recurrence. In other words, the patient must have been disease-free for greater than five years from the date of the last recurrence.
        Note 4:	When it is unknown/not documented whether the patient had a recurrence, default to date of diagnosis to compute the time interval.
        Note 5:	The physician may state this is a recurrence, meaning the patient had a previous breast tumor and now has another breast tumor. Follow the rules; do not attempt to interpret the physician’s statement.

    Rule M9	 Abstract a single primary when simultaneous multiple tumors are carcinoma NST/duct and lobular.
        •	Both/all tumors may be a mixture of carcinoma NST/duct and lobular OR
        •	One tumor may be duct and another tumor lobular
        Note 1:	Tumors must be in the same breast.
        Note 2:	Histologies must be the same behavior.
        Note 3:	Carcinoma NST/duct includes:
        •	DCIS 8500/2
        •	Carcinoma NST 8500/3
        •	Carcinoma with osteoclastic-like stromal giant cells 8035/3 (subtype/variant of carcinoma NST)
        Note 4:	Lobular carcinoma includes:
        •	In situ lobular carcinoma 8520/2
        •	In situ pleomorphic lobular carcinoma 8519/2
        •	Invasive lobular carcinoma 8520/3

    Rule M10	Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3 of Table 3 in the Equivalent Terms and Definitions. Timing is irrelevant.
        Note: 	The tumors may be subtypes/variants of the same or different NOS histologies.
        •	Same NOS: Encapsulated papillary carcinoma with invasion 8504/3 and solid papillary carcinoma with invasion 8509/3 are both subtypes of invasive papillary carcinoma 8503/3 but are distinctly different histologies. Abstract multiple primaries.
        •	Different NOS: Encapsulated papillary carcinoma 8504/2 is a subtype/variant of in situ papillary carcinoma 8503/2.  Pleomorphic lobular carcinoma in situ 8519/2 is a subtype/variant of lobular carcinoma in situ 8520/2. They are distinctly different histologies. Abstract multiple primaries.

    Rule M11	Abstract a single primary when separate/non-contiguous tumors are on the same row in Table 3 in the Equivalent Terms and Definitions. Timing is irrelevant.
        Note 1:	The tumors must be the same behavior.  When one tumor is in situ and the other invasive, continue through the rules.
        Note 2:	The same row means the tumors are:
        •	The same histology (same four-digit ICD-O code) OR
        •	One is the preferred term (column 1) and the other is a synonym for the preferred term (column 2) OR
        •	A NOS (column 1/column 2) and the other is a subtype/variant of that NOS (column 3)

    Rule M12	Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 3 in the Equivalent Terms and Definitions. Timing is irrelevant.
        Note:	Each row in the table is a distinctly different histology.

    Rule M13    Abstract a single primary (the invasive) when there are separate/non-contiguous synchronous invasive AND in situ tumors.
        Note 1: The rules are hierarchical.  Both tumors must be the same histology.
        Note 2: Both tumors must be in the same breast.
        Example: One tumor is invasive carcinoma NST/ductal 8500/3 and a separate non-contiguous tumor in the same breast is DCIS 8500/2.  Abstract a single primary: invasive carcinoma NST/ductal 8500/3.

    Rule M14	Abstract a single primary (the invasive) when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor in the same breast.
        Note 1:	The rules are hierarchical. Only use this rule when none of the previous rules apply.
        Note 2:	The tumors may be a NOS and a subtype/variant of that NOS.
        Note 3:	When the case has been abstracted, change behavior code on original abstract from /2 to /3.
        Note 4:	Do not change date of diagnosis.
        Note 5:	If the case has already been submitted to the central registry, report all changes.
        Note 6:	The physician may stage both tumors because staging and determining multiple primaries are done for different reasons. Staging determines which treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).
        Note 7:	See the COC and SEER manuals for instructions on coding other data items such as Date of Diagnosis, Accession Year and Sequence Number.

    Rule M15	Abstract multiple primaries when an invasive tumor occurs more than 60 days after an in situ tumor in the same breast.
        Note 1:	The rules are hierarchical. Only use this rule when none of the previous rules apply.
        Note 2:	Abstract both the invasive and in situ tumors.
        Note 3:	Abstract as multiple primaries even if physician states the invasive tumor is disease recurrence or progression.
        Note 4:	This rule is based on long-term epidemiologic studies of recurrence intervals. The specialty medical experts (SMEs) reviewed and approved these rules.  Many of the SMEs were also authors, co-authors, or editors of the AJCC Staging Manual.

    Rule M16	Abstract a single primary when none of the previous rules apply.
        Note:	Use caution when applying this default rule.  Please confirm that you have not overlooked an applicable rule.











Rule M4	Abstract a single primaryi when there is inflammatory carcinoma in:
•	Multiple quadrants of same breast OR
•	Bilateral breasts

Rule M5	Abstract multiple primariesii when there are separate, non-contiguous tumors in sites with ICD-O site codes (C50¬_) that differ at the second (CXxx) and/or third characters (CxXx).
Note 1:	Tumors with site codes that differ at the second or third character are in different primary sites; for example, a breast tumor C50x and a colon tumor C18x differ at the second and third character.
Note 2:	This rule does not include metastases. Metastatic tumors are not used to determine multiple primaries; for example, liver metastases from the breast cancer would not be counted as a second primary.

Rule M6	Abstract multiple primariesii when there is bilateral breast cancer (both right and left breast).
Note 1:	Physician statement “bilateral breast cancer” should not be interpreted as meaning a single primary. The term is descriptive and not used consistently.  The literal definition of bilateral is “cancer in both breasts”.
Note 2:	It is irrelevant how many tumors are in each breast. Abstract as separate primaries.
Note 3:	The histologies within each breast may be the same or different.


Rule M7	Abstract a single primaryi when the diagnosis is Paget disease with underlying in situ or invasive carcinoma NST (duct/ductal).

Rule M8	Abstract multiple primariesii when the patient has a subsequent tumor after being clinically disease-free for greater than five years after the original diagnosis or last recurrence.
Note 1:	The rules are hierarchical. This rule only applies when there is a subsequent breast tumor.
Note 2:	Clinically disease-free means that there was no evidence of recurrence on follow-up.
•	Mammograms are NED
•	Scans are NED
•	Tumor biomarkers are NED
Note 3:	When there is a recurrence less than or equal to five years of diagnosis, the “clock” starts over. The time interval is calculated from the date of last recurrence. In other words, the patient must have been disease-free for greater than five years from the date of the last recurrence.
Note 4:	When it is unknown/not documented whether the patient had a recurrence, use date of diagnosis to compute the time interval.
Note 5:	The physician may state this is a recurrence, meaning the patient had a previous breast tumor and now has another breast tumor. Follow the rules; do not attempt to interpret the physician’s statement.

Rule M9	Abstract a single primaryi when simultaneous multiple tumors are carcinoma NST/duct and lobular.
•	Both/all tumors may be a mixture of carcinoma NST/duct and lobular OR
•	One tumor may be duct and another tumor lobular
Note 1:	Tumors must be in the same breast.
Note 3:	Histologies must be the same behavior.
Note 4:	Carcinoma NST/duct includes:
•	DCIS 8500/2
•	Carcinoma NST 8500/3
•	Carcinoma with osteoclastic-like stromal giant cells 8035/3 (subtype/variant of carcinoma NST)
Note 5:	Lobular carcinoma includes:
•	In situ lobular carcinoma 8520/2
•	In situ pleomorphic lobular carcinoma 8519/2
•	Invasive lobular carcinoma 8520/3

Rule M10	Abstract multiple primariesii when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3 of Table 3 in the Equivalent Terms and Definitions. Timing is irrelevant.
Note: 	The tumors may be subtypes/variants of the same or different NOS histologies.
•	Same NOS: Encapsulated papillary carcinoma with invasion 8504/3 and solid papillary carcinoma with invasion 8509/3 are both subtypes of invasive papillary carcinoma 8503/3 but are distinctly different histologies. Abstract multiple primaries.
•	Different NOS: Encapsulated papillary carcinoma 8504/2 is a subtype/variant of in situ papillary carcinoma 8503/2.  Pleomorphic lobular carcinoma in situ 8519/2 is a subtype/variant of lobular carcinoma in situ 8520/2. They are distinctly different histologies. Abstract multiple primaries.

Rule M11	Abstract a single primaryi when synchronous, separate/non-contiguous tumors are on the same row in Table 3 in the Equivalent Terms and Definitions.
Note 1:	The tumors must be the same behavior.  When one tumor is in situ and the other invasive, continue through the rules.
Note 2:	The same row means the tumors are:
•	The same histology (same four-digit ICD-O code) OR
•	One is the preferred term (column 1) and the other is a synonym for the preferred term (column 2) OR
•	A NOS (column 1/column 2) and the other is a subtype/variant of that NOS (column 3)

Rule M12	Abstract a single primaryi when any of the following conditions are met:
•	DCIS subsequent to a diagnosis of:
	DCIS and lobular carcinoma in situ 8522/2
	DCIS and in situ Paget 8543/2
	DCIS and invasive Paget 8543/3
•	Invasive carcinoma NST/duct subsequent to a diagnosis of:
	Invasive carcinoma NST/duct and invasive lobular 8522/3
	Invasive carcinoma NST/duct and invasive Paget 8541/3

Rule M13	Abstract multiple primariesii when separate/non-contiguous tumors are on different rows in Table 3 in the Equivalent Terms and Definitions. Timing is irrelevant.
Note:	Each row in the table is a distinctly different histology.

Rule M14	Abstract a single primaryi (the invasive) when there are separate/non-contiguous synchronous invasive AND in situ tumors.
Note 1:	The rules are hierarchical.  Both tumors must be the same histology.
Note 2:	Both tumors must be in the same breast.
Example:	One tumor is invasive carcinoma NST/ductal 8500/3 and a separate non-contiguous tumor in the same breast is DCIS 8500/2.  Abstract a single primary: invasive carcinoma NST/ductal 8500/3.

Rule M15	Abstract a single primaryi (the invasive) when an in situ tumor is diagnosed after an invasive tumor in the same breast.
Note 1:	Once the patient has an invasive tumor, the in situ is recorded as a recurrence for those registrars who collect recurrence data.
Note 2:	The rules are hierarchical. Only use this rule when none of the previous rules apply.
Note 3:	The tumors may be a NOS and a subtype/variant of that NOS.

Rule M16	Abstract multiple primariesii when a tumor is diagnosed after a diagnosis of:
•	Duct and other 8523
•	Lobular and other 8524
•	Adenocarcinoma with mixed subtypes 8255

Rule M17	Abstract a single primaryi (the invasive) when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor in the same breast.
Note 1:	The rules are hierarchical. Only use this rule when none of the previous rules apply.
Note 2:	The tumors may be a NOS and a subtype/variant of that NOS.
Note 3:	When the case has been abstracted, change behavior code on original abstract from /2 to /3.
Note 4:	Do not change date of diagnosis.
Note 5:	If the case has already been submitted to the central registry, report all changes.
Note 6:	The physician may stage both tumors because staging and determining multiple primaries are done for different reasons. Staging determines which treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).
Note 7:	See the COC and SEER manuals for instructions on coding other data items such as Date of Diagnosis, Accession Year and Sequence Number.

Rule M18	Abstract multiple primariesii when an invasive tumor occurs more than 60 days after an in situ tumor in the same breast.
Note 1:	The rules are hierarchical. Only use this rule when none of the previous rules apply.
Note 2:	Abstract both the invasive and in situ tumors.
Note 3:	Abstract as multiple primaries even if physician states the invasive tumor is disease recurrence or progression.
Note 4:	This rule is based on long-term epidemiologic studies of recurrence intervals. The specialty medical experts (SMEs) reviewed and approved these rules.  Many of the SMEs were also authors, co-authors, or editors of the AJCC Staging Manual.

Rule M19	Abstract a single primaryi when none of the previous rules apply.
Note:	Use this rule as a last resort.  Please confirm that you have not overlooked an applicable rule.


















    */


    // Breast Multiple Primary Rules - Text
    // C500-C506, C508-C509
    // (Excludes lymphoma and leukemia M9590 – M9992 and Kaposi sarcoma M9140)
    public Mp2018BreastGroup() {
        super(MphConstants.MP_2018_BREAST_GROUP_ID, MphConstants.MP_2018_BREAST_GROUP_NAME, "C500-C506, C508-C509", null, null,
              "9590-9992, 9140", "2-3,6", "2018-9999");

        // Rule M4	Abstract a single primary when there is inflammatory carcinoma in:
        // • Multiple quadrants of same breast OR
        // • Bilateral breasts
        MphRule rule = new MphRule(MphConstants.MP_2018_BREAST_GROUP_ID, "M4") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if ((i1.getLaterality().equals(MphConstants.LEFT) && i2.getLaterality().equals(MphConstants.LEFT)) ||
                    (i1.getLaterality().equals(MphConstants.RIGHT) && i2.getLaterality().equals(MphConstants.RIGHT)) ||
                    (i1.getLaterality().equals(MphConstants.BOTH) && i2.getLaterality().equals(MphConstants.BOTH))) {
                    if (MphConstants.MALIGNANT.equals(i1.getBehavior()) && MphConstants.MALIGNANT.equals(i2.getBehavior()) && MphConstants.INFLAMMATORY_CARCINOMA.equals(i1.getHistology()) &&
                            MphConstants.INFLAMMATORY_CARCINOMA.equals(i2.getHistology())) {
                        result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                    }
                }
                return result;
            }
        };
        rule.setQuestion("Is there inflammatory carcinoma in multiple quadrants of the same breast or in bilateral breasts?");
        rule.setReason("Inflammatory carcinoma in multiple quadrants of the same breast or in bilateral breasts is a single primary.");
        _rules.add(rule);

        // Rule M5	Abstract multiple primaries when there are separate, non-contiguous tumors in sites with ICD-O site codes (C50¬_) that differ at the second (CXxx) and/or third characters (CxXx).
        rule = new MphRule(MphConstants.MP_2018_BREAST_GROUP_ID, "M5") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if ((i1.getPrimarySite().substring(0, 2).equals("C50")) && (i2.getPrimarySite().substring(0, 2).equals("C50")))
                    if (!i1.getPrimarySite().substring(1, 3).equals(i2.getPrimarySite().substring(1, 3)))
                        result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Are there tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx) and/or third character (Cx?x)?");
        rule.setReason("Tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx) and/or third (Cx?x) character are multiple primaries.");
        rule.getNotes().add("Tumors with site codes that differ at the second or third character are in different primary sites; for example, a breast tumor C50x and a colon tumor C18x differ at the second and third character.");
        rule.getNotes().add("This rule does not include metastases. Metastatic tumors are not used to determine multiple primaries; for example, liver metastases from the breast cancer would not be counted as a second primary.");
        _rules.add(rule);

        // Rule M6	Abstract multiple primaries when there is bilateral breast cancer (both right and left breast).
        rule = new MphRuleLeftAndRight(MphConstants.MP_2018_BREAST_GROUP_ID, "M6", null, null);
        rule.setQuestion("Is there a tumor(s) in each breast?");
        rule.setReason("Tumors on both sides (right and left breast) are multiple primaries.");
        rule.getNotes().add("Physician statement “bilateral breast cancer” should not be interpreted as meaning a single primary. The term is descriptive and not used consistently.  The literal definition of bilateral is “cancer in both breasts”.");
        rule.getNotes().add("It is irrelevant how many tumors are in each breast. Abstract as separate primaries.");
        rule.getNotes().add("The histologies within each breast may be the same or different.");
        _rules.add(rule);

        // Rule M7	Abstract a single primary when the diagnosis is Paget disease with underlying in situ or invasive carcinoma NST (duct/ductal).
        rule = new MphRule(MphConstants.MP_2018_BREAST_GROUP_ID, "M7") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                String icd1 = i1.getHistology() + "/" + i1.getBehavior(), icd2 = i2.getHistology() + "/" + i2.getBehavior();
                if ((icd1.equals("8541/3") || icd1.equals("8543/2") || icd1.equals("8543/3")) &&
                   (icd2.equals("8541/3") || icd2.equals("8543/2") || icd2.equals("8543/3"))) {
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                }
                return result;
            }
        };
        rule.setQuestion("Is Paget disease with underlying in situ or invasive carcinoma NST (duct/ductal)?");
        rule.setReason("Paget disease with underlying in situ or invasive carcinoma NST (duct/ductal) is single primary.");
        rule.getNotes().add("The underlying tumor may be either in situ or invasive.");
        _rules.add(rule);

        // Rule M8	Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than five years after the
        // original diagnosis or last recurrence.
        rule = new MphRule(MphConstants.MP_2018_BREAST_GROUP_ID, "M8") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                int diff = GroupUtility.verifyYearsApart(i1, i2, 5);
                if (-1 == diff) {
                    result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". There is not enough diagnosis date information.");
                }
                else if (1 == diff)
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);

                return result;
            }
        };
        rule.setQuestion("Has patient been clinically disease-free for greater than five years?");
        rule.setReason("Patient has a subsequent tumor after being clinically disease-free for greater than five years are multiple primaries.");
        rule.getNotes().add("The rules are hierarchical. This rule only applies when there is a subsequent breast tumor.");
        rule.getNotes().add("Clinically disease-free means that there was no evidence of recurrence on follow-up.");
        rule.getNotes().add("  • Mammograms are NED");
        rule.getNotes().add("  • Scans are NED");
        rule.getNotes().add("  • Tumor biomarkers are NED");
        rule.getNotes().add("When there is a recurrence less than or equal to five years of diagnosis, the “clock” starts over. The time interval is calculated from the date of last recurrence. In other words, the patient must have been disease-free for greater than five years from the date of the last recurrence.");
        rule.getNotes().add("When it is unknown/not documented whether the patient had a recurrence, default to date of diagnosis to compute the time interval.");
        rule.getNotes().add("The physician may state this is a recurrence, meaning the patient had a previous breast tumor and now has another breast tumor. Follow the rules; do not attempt to interpret the physician’s statement.");
        _rules.add(rule);

        // Rule M9	 Abstract a single primary when simultaneous multiple tumors are carcinoma NST/duct and lobular:
            // • Both/all tumors may be a mixture of carcinoma NST/duct and lobular OR
            // • One tumor may be duct and another tumor lobular
        rule = new MphRule(MphConstants.MP_2018_BREAST_GROUP_ID, "M9") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                String icd1 = i1.getHistology() + "/" + i1.getBehavior(), icd2 = i2.getHistology() + "/" + i2.getBehavior();
                if ((MphConstants.NST_DUCT_CARCINOMA_2018.contains(icd1) || MphConstants.LOBULAR_CARCINOMA_2018.contains(icd1)) &&
                    (MphConstants.NST_DUCT_CARCINOMA_2018.contains(icd2) || MphConstants.LOBULAR_CARCINOMA_2018.contains(icd2))) {
                    int sixtyDaysApart = GroupUtility.verifyDaysApart(i1, i2, 60);
                    if (-1 == sixtyDaysApart) {
                        result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". There is not enough diagnosis date information.");
                    }
                    else if (0 == sixtyDaysApart) {
                        result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                    }
                }
                return result;
            }
        };
        rule.setQuestion("Is this a simultaneous carcinoma NST/duct and lobular carcinoma?");
        rule.setReason("Simultaneous carcinoma NST/duct and lobular carcinoma is a single primary.");
        rule.getNotes().add("Tumors must be in the same breast.");
        rule.getNotes().add("Histologies must be the same behavior.");
        rule.getNotes().add("Carcinoma NST/duct includes:");
        rule.getNotes().add("  • DCIS 8500/2");
        rule.getNotes().add("  • Carcinoma NST 8500/3");
        rule.getNotes().add("  • Carcinoma with osteoclastic-like stromal giant cells 8035/3 (subtype/variant of carcinoma NST)");
        rule.getNotes().add("Lobular carcinoma includes:");
        rule.getNotes().add("  • In situ lobular carcinoma 8520/2");
        rule.getNotes().add("  • In situ pleomorphic lobular carcinoma 8519/2");
        rule.getNotes().add("  • Invasive lobular carcinoma 8520/3");
        _rules.add(rule);

        // Rule M10	Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3 of Table 3 in the Equivalent Terms and Definitions. Timing is irrelevant.
        rule = new MphRuleTwoOrMoreDifferentSubTypesInTable(MphConstants.MP_2018_BREAST_GROUP_ID, "M10", MphConstants.BREAST_2018_TABLE3_SUBTYPES, MphConstants.BREAST_2018_SUBTYPE_NOS, false);
        rule.setQuestion("Are separate/non-contiguous tumors two or more different subtypes/variants in Column 3, Table 3 in the Equivalent Terms and Definitions?");
        rule.setReason("Separate/non-contiguous tumors that are two or more different subtypes/variants in Column 3, Table 3 in the Equivalent Terms and Definitions are multiple primaries.");
        rule.getNotes().add("The tumors may be subtypes/variants of the same or different NOS histologies.");
        rule.getNotes().add("  • Same NOS: Encapsulated papillary carcinoma with invasion 8504/3 and solid papillary carcinoma with invasion 8509/3 are both subtypes of invasive papillary carcinoma 8503/3 but are distinctly different histologies. Abstract multiple primaries.");
        rule.getNotes().add("  • Different NOS: Encapsulated papillary carcinoma 8504/2 is a subtype/variant of in situ papillary carcinoma 8503/2.  Pleomorphic lobular carcinoma in situ 8519/2 is a subtype/variant of lobular carcinoma in situ 8520/2. They are distinctly different histologies. Abstract multiple primaries.");
        _rules.add(rule);

        // Rule M11	Abstract a single primary when separate/non-contiguous tumors are on the same row in Table 3 in the Equivalent Terms and Definitions. Timing is irrelevant.
        rule = new MphRuleSameRowInTable(MphConstants.MP_2018_BREAST_GROUP_ID, "M11", MphConstants.BREAST_2018_TABLE3_ROWS, MphConstants.BREAST_2018_SUBTYPE_NOS, true, false, false);
        rule.setQuestion("Are separate/non-contiguous tumors on the same row in Table 3 in the Equivalent Terms and Definitions?");
        rule.setReason("Separate/non-contiguous tumors on the same row in Table 3 in the Equivalent Terms and Definitions is a single primary.");
        rule.getNotes().add("The tumors must be the same behavior.  When one tumor is in situ and the other invasive, continue through the rules.");
        rule.getNotes().add("The same row means the tumors are:");
        rule.getNotes().add("  • The same histology (same four-digit ICD-O code) OR");
        rule.getNotes().add("  • One is the preferred term (column 1) and the other is a synonym for the preferred term (column 2) OR");
        rule.getNotes().add("  • A NOS (column 1/column 2) and the other is a subtype/variant of that NOS (column 3)");
        _rules.add(rule);

        // Rule M12	Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 3 in the Equivalent Terms and Definitions. Timing is irrelevant.
        rule = new MphRuleDifferentRowsInTable(MphConstants.MP_2018_BREAST_GROUP_ID, "M12", MphConstants.BREAST_2018_TABLE3_ROWS, false);
        rule.setQuestion("Are separate/non-contiguous tumors on different rows in Table 3 in the Equivalent Terms and Definitions?");
        rule.setReason("Separate/non-contiguous tumors on different rows in Table 3 in the Equivalent Terms and Definitions are multiple primaries.");
        rule.getNotes().add("Each row in the table is a distinctly different histology.");
        _rules.add(rule);

        // Rule M13 Abstract a single primary (the invasive) when there are separate/non-contiguous synchronous invasive AND in situ tumors.
        rule = new MphRuleInSituAfterInvasive(MphConstants.MP_2018_BREAST_GROUP_ID, "M13", true);
        rule.getNotes().add("The rules are hierarchical.  Both tumors must be the same histology.");
        rule.getNotes().add("Both tumors must be in the same breast.");
        rule.getExamples().add("One tumor is invasive carcinoma NST/ductal 8500/3 and a separate non-contiguous tumor in the same breast is DCIS 8500/2.  Abstract a single primary: invasive carcinoma NST/ductal 8500/3.");
        _rules.add(rule);

        // Rule M14	Abstract a single primary (the invasive) when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor in the same breast.
        rule = new MphRuleInvasiveAfterInSituLess60Days(MphConstants.MP_2018_BREAST_GROUP_ID, "M14", true);
        rule.getNotes().add("The rules are hierarchical. Only use this rule when none of the previous rules apply.");
        rule.getNotes().add("The tumors may be a NOS and a subtype/variant of that NOS.");
        rule.getNotes().add("When the case has been abstracted, change behavior code on original abstract from /2 to /3.");
        rule.getNotes().add("Do not change date of diagnosis.");
        rule.getNotes().add("If the case has already been submitted to the central registry, report all changes.");
        rule.getNotes().add("The physician may stage both tumors because staging and determining multiple primaries are done for different reasons. Staging determines which treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).");
        rule.getNotes().add("See the COC and SEER manuals for instructions on coding other data items such as Date of Diagnosis, Accession Year and Sequence Number.");
        _rules.add(rule);

        // Rule M15	Abstract multiple primaries when an invasive tumor occurs more than 60 days after an in situ tumor in the same breast.
        rule = new MphRuleInvasiveAfterInSituGreaterThan60Days(MphConstants.MP_2018_BREAST_GROUP_ID, "M15", true);
        rule.getNotes().add("The rules are hierarchical. Only use this rule when none of the previous rules apply.");
        rule.getNotes().add("Abstract both the invasive and in situ tumors.");
        rule.getNotes().add("Abstract as multiple primaries even if physician states the invasive tumor is disease recurrence or progression.");
        rule.getNotes().add("This rule is based on long-term epidemiologic studies of recurrence intervals. The specialty medical experts (SMEs) reviewed and approved these rules.  Many of the SMEs were also authors, co-authors, or editors of the AJCC Staging Manual.");
        _rules.add(rule);

        // Rule M16	Abstract a single primary when none of the previous rules apply.
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_BREAST_GROUP_ID, "M16");
        rule.getNotes().add("Use caution when applying this default rule.  Please confirm that you have not overlooked an applicable rule.");
        _rules.add(rule);
    }
}
