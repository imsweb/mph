/*
 * Copyright (C) 2018 Information Management Services, Inc.
 */
package com.imsweb.mph.mpgroups;

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

public class Mp2018LungGroup extends MphGroup {

    /*
    Lung Multiple Primary Rules
    C340-C343, C348, C349
    (Excludes lymphoma and leukemia M9590–M9992 and Kaposi sarcoma M9140)

    Rule M3	Abstract multiple primaries when there are separate, non-contiguous tumors in sites with ICD-O site codes (C34_) that differ at the second CXxx and/or third character CxXx.
        Note: When codes differ at the second or third characters, the tumors are in different primary sites.

    Rule M4	Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than three years after the original diagnosis or last recurrence.
        Note 1:	Clinically disease-free means that there was no evidence of recurrence in the same lung on follow-up.
        •	Scans are NED
        •	Tumor biomarkers are NED
        Note 2:	When there is a recurrence less than or equal to three years of diagnosis, the “clock” starts over. The time interval is calculated from the date of last recurrence. In other words, the patient must have been disease-free for greater than three years from the date of the last recurrence.
        Note 3:	When it is unknown/not documented whether the patient had a recurrence, default to date of diagnosis to compute the time interval.
        Note 4:	The physician may state this is a recurrence, meaning the patient had a previous lung tumor and now has another lung site tumor. Follow the rules; do not attempt to interpret the physician’s statement.

    Rule M5	Abstract multiple primaries when there is at least one tumor that is small cell carcinoma 8041 or any small cell subtypes/variants and another tumor that is non-small cell carcinoma 8046 or any non-small cell carcinoma subtypes/variants.
        Note 1:	Small cell carcinoma and non-small cell carcinoma are the two major classifications/divisions for lung cancer.
        •	See Table 3 in Equivalent Terms and Definitions for terms and codes for small cell carcinoma and all of the subtypes/variants
        •	With the exception of small cell/neuroendocrine carcinoma, all other histologies listed in Table 3 in Equivalent Terms and Definitions are non-small cell carcinoma
        Note 2:	It is irrelevant whether the tumors are in the ipsilateral (same) lung or are bilateral (both lungs).

    Rule M6	Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 3 in the Equivalent Terms and Definitions.  Timing is irrelevant.
        Note:	The tumors may be subtypes/variants of the same or different NOS histologies.
        •	Same NOS: Colloid adenocarcinoma 8480/3 and lepidic adenocarcinoma 8250/3 are both subtypes of adenocarcinoma NOS 8140/3 but are distinctly different histologies. Abstract multiple primaries.
        •	Different NOS: Keratinizing squamous cell carcinoma 8071/3 is a subtype of squamous cell carcinoma NOS 8070; Typical carcinoid 8240/3 is a subtype of small cell carcinoma/neuroendocrine tumors (NET Tumors) 8041/3. They are distinctly different histologies. Abstract multiple primaries.

    Rule M7	Abstract a single primary when separate/non-contiguous tumors are on the same row in Table 3 in the Equivalent Terms and Definitions. Timing is irrelevant.
        Note 1:	The tumors must be the same behavior.  When one tumor is in situ and the other invasive, continue through the rules.
        Note 2:	The same row means the tumors are:
        •	The same histology (same four-digit ICD-O code) OR
        •	One is the preferred term (column 1) and the other is a synonym for the preferred term (column 2) OR
        •	A NOS (column 1/column 2) and the other is a subtype/variant of that NOS (column 3)

    Rule M8	Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 3 in the Equivalent Terms and Definitions. Timing is irrelevant.
        Note:	Each row in the table is a distinctly different histology.

    Rule M9	Abstract a single primary when there are simultaneous multiple tumors:
        •	In both lungs OR
        •	In the same lung OR
        •	Single tumor in one lung; multiple tumors in contralateral lung
        Note 1:	Tumors may be combinations of:
        •	In situ and invasive OR
        •	NOS and subtype/variant (See Table 3 in the Equivalent Terms and Definitions)
        Note 2:	NOS and subtypes/variants are:
        •	Adenocarcinoma 8140 and a subtype/variant of adenocarcinoma
        •	Mucinous adenocarcinoma and a subtype/variant of mucinous adenocarcinoma
        •	Non-mucinous adenocarcinoma and a subtype/variant of non-mucinous adenocarcinoma
        •	Non-small cell carcinoma 8046 and a subtype/variant of non-small cell carcinoma
        •	Sarcoma 8800 and a subtype/variant of sarcoma
        •	Small cell neuroendocrine tumors/NET 8041 and a subtype/variant of small cell neuroendocrine tumor/NET
        •	Squamous cell carcinoma 8070 and a subtype/variant of squamous cell carcinoma
        Note 3:	Code multiple primaries only when there is proof that one of the tumors is a different histology. Proof is any one of the following:
        •	Pathology from a biopsy or resection proves tumors are different histologies
        •	Attending, oncologist, or pulmonologist state unequivocally that the tumors are different primaries
            	Unequivocal means that no words such as “probable” are used in the statement. Terms which are on the “ambiguous terms” list such as “probable” cannot be used to prove metastasis.
        Note 4:	When there are multiple tumors in one or both lungs, the physician usually biopsies only one mass/tumor. They treat the patient based on that single biopsy, assuming all of the masses/tumors are the same histology.

    Rule M10	Abstract a single primary when an in situ tumor is diagnosed after an invasive tumor AND tumors occur in the same lung.
        Note 1:	The rules are hierarchical. Only use this rule when none of the previous rules apply.
        Note 2:	The tumors may be a NOS and a subtype/variant of that NOS. See Table 3 in the Equivalent Terms and Definitions for listings of NOS and subtype/variants.
        Note 3:	The in situ is recorded as a recurrence for those registrars who collect recurrence data.

    Rule M11	Abstract multiple primaries when there is a single tumor in each lung (one tumor in the right lung and one tumor in the left lung).
        Note 1:	The only exception is when there is proof that one tumor is metastatic. Proof is any one of the following:
        •	Tissue from both tumors is compared and the pathologic diagnoses definitively says one tumor is metastatic
        •	Attending physician, oncologist, or pulmonologist state unequivocally that the tumor in the contralateral lung is metastatic
            	Unequivocal means that no words such as “probably possibly, most likely, etc.” are used in the statement. Terms which are on the “ambiguous terms” list make the statement equivocal (cannot be used to prove metastases)
        Note 2:	Lung metastases usually present as multiple tumors/masses. A single tumor in each lung is unlikely to be a single primary (e.g. metastatic to the contralateral lung).
        Note 3:	The term “bilateral” is not a synonym for a single primary. It is simply a statement that there are tumors in both lungs.
        Note 4:	This rule is based on long-term epidemiologic studies of multiple primaries. The specialty medical experts (SME) and the CoC site physician teams reviewed and approved these rules.  Many of the CoC site team physicians were also authors, co-authors, or editors of the AJCC Staging Manual.
        Note 5:	Lymph node involvement is recorded in staging criteria.

    Rule M12	Abstract a single primary (the invasive) when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor in the same lung.
        Note 1:	The rules are hierarchical. Only use this rule when none of the previous rules apply.
        Note 2:	The tumors may be a NOS and a subtype/variant of that NOS.
        Note 3:	When the case has been abstracted, change behavior code on original abstract from /2 to /3.
        Note 4:	Do not change date of diagnosis.
        Note 5:	If the case has already been submitted to the central registry, report all changes.
        Note 6:	The physician may stage both tumors because staging and determining multiple primaries are done for different reasons. Staging determines which treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).
        Note 7:	See the COC and SEER manuals for instructions on coding other data items such as Date of Diagnosis, Accession Year and Sequence Number.

    Rule M13	Abstract multiple primaries when an invasive tumor occurs more than 60 days after an in situ tumor in the same lung.
        Note 1:	The rules are hierarchical. Only use this rule when none of the previous rules apply.
        Note 2:	Abstract both the invasive and in situ tumors.
        Note 3:	Abstract as multiple primaries even if physician states the invasive tumor is disease recurrence or progression.
        Note 4:	This rule is based on long-term epidemiologic studies of recurrence intervals. The specialty medical experts (SMEs) reviewed and approved these rules.  Many of the SMEs were also authors, co-authors, or editors of the AJCC Staging Manual.

    Rule M14	Abstract a single primary when none of the previous rules apply.
        Note:	Use caution when applying this default rule.  Please confirm that you have not overlooked an applicable rule.

     */



    // Lung Multiple Primary Rules
    // C340-C343, C348, C349
    // (Excludes lymphoma and leukemia M9590–M9992 and Kaposi sarcoma M9140)
    public Mp2018LungGroup() {
        super(MphConstants.MP_2018_LUNG_GROUP_ID, MphConstants.MP_2018_LUNG_GROUP_NAME, "C340-C343, C348, C349", null, null,
                "9590-9992, 9140", "2-3,6", "2018-9999");

        // Rule M3	Abstract multiple primaries when there are separate, non-contiguous tumors in sites with ICD-O site codes (C34_) that differ at the second CXxx and/or third character CxXx.
        MphRule rule = new MphRulePrimarySiteCode(MphConstants.MP_2018_LUNG_GROUP_ID, "M3");
        rule.getNotes().add("When codes differ at the second or third characters, the tumors are in different primary sites.");
        _rules.add(rule);

        // Rule M4	Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than three years after the original diagnosis or last recurrence.
        rule = new MphRuleDiagnosisDateGreaterThan3Years(MphConstants.MP_2018_LUNG_GROUP_ID, "M4");
        rule.getNotes().add("Clinically disease-free means that there was no evidence of recurrence in the same lung on follow-up.");
        rule.getNotes().add("  • Scans are NED");
        rule.getNotes().add("  •	 Tumor biomarkers are NED");
        rule.getNotes().add("When there is a recurrence less than or equal to three years of diagnosis, the “clock” starts over. The time interval is calculated from the date of last recurrence. In other words, the patient must have been disease-free for greater than three years from the date of the last recurrence.");
        rule.getNotes().add("When it is unknown/not documented whether the patient had a recurrence, default to date of diagnosis to compute the time interval.");
        rule.getNotes().add("The physician may state this is a recurrence, meaning the patient had a previous lung tumor and now has another lung site tumor. Follow the rules; do not attempt to interpret the physician’s statement.");
        _rules.add(rule);

        // Rule M5	Abstract multiple primaries when there is at least one tumor that is small cell carcinoma 8041 or any small cell subtypes/variants and another tumor that is non-small cell carcinoma 8046 or any non-small cell carcinoma subtypes/variants.
        rule = new MphRule(MphConstants.MP_2018_LUNG_GROUP_ID, "M5") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                List<String> subTypes8041 = MphConstants.LUNG_2018_TABLE3_ROWS.get("8041");
                List<String> subTypes8046 = MphConstants.LUNG_2018_TABLE3_ROWS.get("8046");
                if ((subTypes8041 != null) && (subTypes8046 != null)) {
                    String hist1 = i1.getHistology(), hist2 = i2.getHistology();
                    if (((hist1.equals("8041") || subTypes8041.contains(hist1)) && (hist2.equals("8046") || subTypes8046.contains(hist2))) ||
                            ((hist1.equals("8046") || subTypes8046.contains(hist1)) && (hist2.equals("8041") || subTypes8041.contains(hist2)))) {
                        result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    }
                }
                return result;
            }
        };
        rule.setQuestion("Is one tumor a small cell carcinoma 8041 (or any small cell subtypes/variants), and another tumor a non-small cell carcinoma 8046 (or any non-small cell " +
                "carcinoma subtypes/variants)?");
        rule.setReason("One tumor that is small cell carcinoma 8041 (or any small cell subtypes/variants), and another tumor that is non-small cell carcinoma 8046 (or any non-small cell " +
                "carcinoma subtypes/variants), is multiple primaries.");
        rule.getNotes().add("Small cell carcinoma and non-small cell carcinoma are the two major classifications/divisions for lung cancer.");
        rule.getNotes().add("  • See Table 3 in Equivalent Terms and Definitions for terms and codes for small cell carcinoma and all of the subtypes/variants");
        rule.getNotes().add("  • With the exception of small cell/neuroendocrine carcinoma, all other histologies listed in Table 3 in Equivalent Terms and Definitions are non-small cell carcinoma");
        rule.getNotes().add("It is irrelevant whether the tumors are in the ipsilateral (same) lung or are bilateral (both lungs).");
        _rules.add(rule);

        // Rule M6	Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 3 in the Equivalent Terms and Definitions.  Timing is irrelevant.
        rule = new MphRuleTwoOrMoreDifferentSubTypesInTable(MphConstants.MP_2018_LUNG_GROUP_ID, "M6", MphConstants.LUNG_2018_TABLE3_SUBTYPES, false);
        rule.setQuestion("Are separate/non-contiguous tumors two or more different subtypes/variants in Column 3, Table 3 in the Equivalent Terms and Definitions?");
        rule.setReason("Separate/non-contiguous tumors that are two or more different subtypes/variants in Column 3, Table 3 in the Equivalent Terms and Definitions are multiple primaries.");
        rule.getNotes().add("The tumors may be subtypes/variants of the same or different NOS histologies.");
        rule.getNotes().add("  •	 Same NOS: Colloid adenocarcinoma 8480/3 and lepidic adenocarcinoma 8250/3 are both subtypes of adenocarcinoma NOS 8140/3 but are distinctly different histologies. Abstract multiple primaries.");
        rule.getNotes().add("  • Different NOS: Keratinizing squamous cell carcinoma 8071/3 is a subtype of squamous cell carcinoma NOS 8070; Typical carcinoid 8240/3 is a subtype of small cell carcinoma/neuroendocrine tumors (NET Tumors) 8041/3. They are distinctly different histologies. Abstract multiple primaries.");
        _rules.add(rule);

        // Rule M7	Abstract a single primary when separate/non-contiguous tumors are on the same row in Table 3 in the Equivalent Terms and Definitions. Timing is irrelevant.
        rule = new MphRuleSameRowInTable(MphConstants.MP_2018_LUNG_GROUP_ID, "M7", MphConstants.LUNG_2018_TABLE3_ROWS, true);
        rule.setQuestion("Are separate/non-contiguous tumors on the same rows in Table 3 in the Equivalent Terms and Definitions?");
        rule.setReason("Separate/non-contiguous tumors on the same row in Table 3 in the Equivalent Terms and Definitions is a single primary.");
        rule.getNotes().add("The tumors must be the same behavior.  When one tumor is in situ and the other invasive, continue through the rules.");
        rule.getNotes().add("The same row means the tumors are:");
        rule.getNotes().add("  • The same histology (same four-digit ICD-O code) OR");
        rule.getNotes().add("  • One is the preferred term (column 1) and the other is a synonym for the preferred term (column 2) OR");
        rule.getNotes().add("  • A NOS (column 1/column 2) and the other is a subtype/variant of that NOS (column 3)");
        _rules.add(rule);

        // Rule M8	Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 3 in the Equivalent Terms and Definitions. Timing is irrelevant.
        rule = new MphRuleDifferentRowsInTable(MphConstants.MP_2018_LUNG_GROUP_ID, "M8", MphConstants.LUNG_2018_TABLE3_ROWS, false);
        rule.setQuestion("Are separate/non-contiguous tumors on different rows in Table 3 in the Equivalent Terms and Definitions?");
        rule.setReason("Separate/non-contiguous tumors on different rows in Table 3 in the Equivalent Terms and Definitions is multiple primaries.");
        rule.getNotes().add("Each row in the table is a distinctly different histology.");
        _rules.add(rule);

        // Rule M9	Abstract a single primary when there are simultaneous multiple tumors:
        // •	 In both lungs OR
        // •	 In the same lung OR
        // • Single tumor in one lung; multiple tumors in contralateral lung
        rule = new MphRuleSimultaneousTumors(MphConstants.MP_2018_LUNG_GROUP_ID, "M9");
        rule.getNotes().add("Tumors may be combinations of:");
        rule.getNotes().add("  • In situ and invasive OR");
        rule.getNotes().add("  • NOS and subtype/variant (See Table 3 in the Equivalent Terms and Definitions)");
        rule.getNotes().add("NOS and subtypes/variants are:");
        rule.getNotes().add("  • Adenocarcinoma 8140 and a subtype/variant of adenocarcinoma");
        rule.getNotes().add("  • Mucinous adenocarcinoma and a subtype/variant of mucinous adenocarcinoma");
        rule.getNotes().add("  • Non-mucinous adenocarcinoma and a subtype/variant of non-mucinous adenocarcinoma");
        rule.getNotes().add("  • Non-small cell carcinoma 8046 and a subtype/variant of non-small cell carcinoma");
        rule.getNotes().add("  • Sarcoma 8800 and a subtype/variant of sarcoma");
        rule.getNotes().add("  • Small cell neuroendocrine tumors/NET 8041 and a subtype/variant of small cell neuroendocrine tumor/NET");
        rule.getNotes().add("  • Squamous cell carcinoma 8070 and a subtype/variant of squamous cell carcinoma");
        rule.getNotes().add("Code multiple primaries only when there is proof that one of the tumors is a different histology. Proof is any one of the following:");
        rule.getNotes().add("  • Pathology from a biopsy or resection proves tumors are different histologies");
        rule.getNotes().add("  • Attending, oncologist, or pulmonologist state unequivocally that the tumors are different primaries");
        rule.getNotes().add("     Unequivocal means that no words such as “probable” are used in the statement. Terms which are on the “ambiguous terms” list such as “probable” cannot be used to prove metastasis.");
        rule.getNotes().add("When there are multiple tumors in one or both lungs, the physician usually biopsies only one mass/tumor. They treat the patient based on that single biopsy, assuming all of the masses/tumors are the same histology.");
        _rules.add(rule);

        // Rule M10	Abstract a single primary when an in situ tumor is diagnosed after an invasive tumor AND tumors occur in the same lung.
        rule = new MphRule(MphConstants.MP_2018_LUNG_GROUP_ID, "M10") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (GroupUtility.areSameSide(i1.getLaterality(), i2.getLaterality()))
                    if (GroupUtility.isOneBehaviorBeforeTheOther(i1, i2, MphConstants.MALIGNANT, MphConstants.INSITU))
                        result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);

                return result;
            }
        };
        rule.setQuestion("Is there an in situ tumor following an invasive tumor in the same lung?");
        rule.setReason("An in situ tumor diagnosed following an invasive tumor in the same lung is a single primary.");
        rule.getNotes().add("The rules are hierarchical. Only use this rule when none of the previous rules apply.");
        rule.getNotes().add("The tumors may be a NOS and a subtype/variant of that NOS. See Table 3 in the Equivalent Terms and Definitions for listings of NOS and subtype/variants.");
        rule.getNotes().add("The in situ is recorded as a recurrence for those registrars who collect recurrence data.");
        _rules.add(rule);

        // Rule M11	Abstract multiple primaries when there is a single tumor in each lung (one tumor in the right lung and one tumor in the left lung).
        rule = new MphRule(MphConstants.MP_2018_LUNG_GROUP_ID, "M11") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (!Arrays.asList(MphConstants.RIGHT, MphConstants.LEFT, MphConstants.BOTH).containsAll(Arrays.asList(i1.getLaterality(), i2.getLaterality()))) {
                    result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". Valid and known laterality for lung cancer should be provided.");
                }
                else if (GroupUtility.areOppositeSides(i1.getLaterality(), i2.getLaterality()))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);

                return result;
            }
        };
        rule.setQuestion("Is there a single tumor in each lung?");
        rule.setReason("A single tumor in each lung is multiple primaries.");
        rule.getNotes().add("The only exception is when there is proof that one tumor is metastatic. Proof is any one of the following:");
        rule.getNotes().add("  • Tissue from both tumors is compared and the pathologic diagnoses definitively says one tumor is metastatic");
        rule.getNotes().add("  • Attending physician, oncologist, or pulmonologist state unequivocally that the tumor in the contralateral lung is metastatic");
        rule.getNotes().add("     Unequivocal means that no words such as “probably possibly, most likely, etc.” are used in the statement. Terms which are on the “ambiguous terms” list make the statement equivocal (cannot be used to prove metastases)");
        rule.getNotes().add("Lung metastases usually present as multiple tumors/masses. A single tumor in each lung is unlikely to be a single primary (e.g. metastatic to the contralateral lung).");
        rule.getNotes().add("The term “bilateral” is not a synonym for a single primary. It is simply a statement that there are tumors in both lungs.");
        rule.getNotes().add("This rule is based on long-term epidemiologic studies of multiple primaries. The specialty medical experts (SME) and the CoC site physician teams reviewed and approved these rules.  Many of the CoC site team physicians were also authors, co-authors, or editors of the AJCC Staging Manual.");
        rule.getNotes().add("Lymph node involvement is recorded in staging criteria.");
        _rules.add(rule);

        // Rule M12	Abstract a single primary (the invasive) when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor in the same lung.
        rule = new MphRuleInvasiveAfterInSituLess60Days(MphConstants.MP_2018_LUNG_GROUP_ID, "M12", true);
        rule.setQuestion("Is there an invasive tumor diagnosed less than or equal to 60 days after an in situ tumor in the same lung?");
        rule.setReason("An invasive tumor diagnosed less than or equal to 60 days after an in situ tumor in the same lung is a single primary.");
        rule.getNotes().add("The rules are hierarchical. Only use this rule when none of the previous rules apply.");
        rule.getNotes().add("The tumors may be a NOS and a subtype/variant of that NOS.");
        rule.getNotes().add("When the case has been abstracted, change behavior code on original abstract from /2 to /3.");
        rule.getNotes().add("Do not change date of diagnosis.");
        rule.getNotes().add("If the case has already been submitted to the central registry, report all changes.");
        rule.getNotes().add("The physician may stage both tumors because staging and determining multiple primaries are done for different reasons. Staging determines which treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).");
        rule.getNotes().add("See the COC and SEER manuals for instructions on coding other data items such as Date of Diagnosis, Accession Year and Sequence Number.");
        _rules.add(rule);

        // Rule M13	Abstract multiple primaries when an invasive tumor occurs more than 60 days after an in situ tumor in the same lung.
        rule = new MphRuleInvasiveAfterInSituGreaterThan60Days(MphConstants.MP_2018_LUNG_GROUP_ID, "M13", true);
        rule.setQuestion("Is there an invasive tumor following an in situ tumor in the same lung more than 60 days after diagnosis?");
        rule.setReason("An invasive tumor following an in situ tumor in the same lung more than 60 days after diagnosis are multiple primaries.");
        rule.getNotes().add("The rules are hierarchical. Only use this rule when none of the previous rules apply.");
        rule.getNotes().add("Abstract both the invasive and in situ tumors.");
        rule.getNotes().add("Abstract as multiple primaries even if physician states the invasive tumor is disease recurrence or progression.");
        rule.getNotes().add("This rule is based on long-term epidemiologic studies of recurrence intervals. The specialty medical experts (SMEs) reviewed and approved these rules.  Many of the SMEs were also authors, co-authors, or editors of the AJCC Staging Manual.");
        _rules.add(rule);

        // Rule M14	Abstract a single primary when none of the previous rules apply.
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_LUNG_GROUP_ID, "M14");
        rule.getNotes().add("Use caution when applying this default rule.  Please confirm that you have not overlooked an applicable rule.");
        _rules.add(rule);
    }
}

