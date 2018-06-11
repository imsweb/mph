/*
 * Copyright (C) 2018 Information Management Services, Inc.
 */
package com.imsweb.mph.mpgroups;

import java.util.Arrays;

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

    Rule M3	Abstract multiple primaries when multiple tumors are present in sites with site codes that differ at the second CXxx and/or third character CxXx.
        Note: When codes differ at the second or third characters, the tumors are in different primary sites.

    Rule M4	Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than three years after the original diagnosis or last recurrence.
        Note 1:	Clinically disease-free means that there was no evidence of recurrence in the same lung on follow-up.
        Note 2:	When there is a recurrence less than or equal to three years of diagnosis, the “clock” starts over. The time interval is calculated from the date of last recurrence. In other words, the patient must have been disease-free for greater than three years from the date of the last recurrence.
        Note 3:	When it is unknown/not documented whether the patient had a recurrence, default to date of diagnosis to compute the time interval.
        Note 4:	The physician may state this is a recurrence, meaning the patient had a previous lung tumor and now has another lung site tumor. Follow the rules; do not attempt to interpret the physician’s statement.

    Rule M5	Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 3 in the Equivalent Terms and Definitions. Tumors may be:
        •	Simultaneous OR
        •	Original and subsequent
        Note:	Each row in the table is a distinctly different histology.

    Rule M6	Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 3 in the Equivalent Terms and Definitions. Tumors may be
        •	Simultaneous OR
        •	Original and subsequent.
        Note: The tumors may be subtypes/variants of the same or different NOS histologies.
        •	Same NOS: Colloid adenocarcinoma 8480/3 and lepidic adenocarcinoma 8250/3 are both subtypes of adenocarcinoma NOS 8140/3 but are distinctly different histologies. Abstract multiple primaries
        •	Different NOS: Keratinizing squamous cell carcinoma 8071/3 is a subtype of squamous cell carcinoma NOS 8070; Typical carcinoid 8240/3 is a subtype of small cell carcinoma/neuroendocrine tumors (NET Tumors) 8041/3. They are distinctly different histologies. Abstract multiple primaries.

    Rule M7	Abstract a single primary when there are simultaneous multiple tumors in the same lung that are
        •	In situ and invasive OR
        Example: One tumor is invasive adenocarcinoma 8140/3 AND another tumor is adenocarcinoma in situ 8140/2.
        •	A NOS and a subtype/variant of that NOS.
        Note 1: See Table 3 in the Equivalent Terms and Definitions for NOS and subtypes/variants.
        Note 2: NOS and subtypes/variants are:
        •	Adenocarcinoma 8140 and any subtype/variant of adenocarcinoma
        •	Mucinous adenocarcinoma, invasive 8253 and any subtype/variant of invasive mucinous adenocarcinoma
        •	Non-mucinous adenocarcinoma, invasive 8140 and any subtype/variant of non-mucinous adenocarcinoma
        •	Non-small cell carcinoma 8046 and any subtype/variant of non-small cell carcinoma
        •	Pleomorphic carcinoma 8022 and any subtype/variant of pleomorphic carcinoma
        •	Small cell neuroendocrine tumors/NET 8041 and any subtype/variant of small cell neuroendocrine tumor/NET
        •	Squamous cell carcinoma 8070 and any subtype/variant of squamous cell carcinoma
        •	Synovial sarcoma 9040 and any subtype/variant of synovial sarcoma

    Rule M8	Abstract a single primary when an in situ tumor is diagnosed after an invasive tumor AND tumors occur in the same lung.
        Note 1:	The rules are hierarchical. Only use this rule when none of the previous rules apply.
        Note 2:	The tumors may be a NOS and a subtype/variant of that NOS. See Table 3 in the Equivalent Terms and Definitions for listings of NOS and subtype/variants.
        Note 3:	Once the patient has an invasive tumor, the in situ is recorded as a recurrence for those registrars who collect recurrence data.

    Rule M9	Abstract multiple primaries when there is at least one tumor that is small cell carcinoma 8041 or any small cell subtypes/variants and another tumor that is non-small cell carcinoma 8046 or any of the non-small cell carcinoma subtypes/variants.
        Note 1:	Small cell carcinoma and non-small cell carcinoma are the two major classifications/divisions for lung cancer.
        •	See Table 3 in Equivalent Terms and Definitions for terms and codes for small cell carcinoma and all of the subtypes/variants
        •	With the exception of small cell/neuroendocrine carcinoma, all other histologies listed in Table 3 in Equivalent Terms and Definitions are non-small cell carcinoma
        Note 2:	It is irrelevant whether the tumors are in the ipsilateral (same) lung or are bilateral (both lungs).

    Rule M10	Abstract multiple primaries when there is a single tumor in each lung (one tumor in the right lung and one tumor in the left lung).
        Note 1:	The only exception is when there is proof that one tumor is metastatic. Proof is any one of the following:
        •	Tissue from both tumors is compared and the pathologic diagnoses definitively says one tumor is metastatic
        •	Attending physician, oncologist, or pulmonologist state unequivocally that the tumor in the contralateral lung is metastatic
            	Unequivocal means that no words such as “probably possibly, most likely, etc.” are used in the statement. Terms which are on the “ambiguous terms” list make the statement equivocal (cannot be used to prove metastases)
        Note 2:	Lung metastases usually present as multiple tumors/masses. A single tumor in each lung is unlikely to be a single primary (e.g. metastatic to the contralateral lung).
        Note 3:	The term “bilateral” is not a synonym for a single primary. It is simply a statement that there are tumors in both lungs.
        Note 4:	This rule is based on long-term epidemiologic studies of multiple primaries. The specialty medical experts (SME) and the CoC site physician teams reviewed and approved these rules.  Many of the CoC site team physicians were also authors, co-authors, or editors of the AJCC Staging Manual.
        Note 5:	Lymph node involvement is recorded in staging criteria.

    Rule M11	Abstract a single primary when:
        •	There is simultaneously a single tumor in one lung and multiple tumors in the contralateral lung OR
        •	Simultaneous multiple tumors in both lungs OR
        •	Simultaneous multiple tumors in the same lung
        Note 1:	When there are multiple tumors in one or both lungs, the physician usually biopsies only one mass/tumor. They treat the patient based on that single biopsy, assuming all of the masses/tumors are the same histology.
        Note 2:	Code multiple primaries only when there is proof that one of the tumors is a different histology. Proof is any one of the following.
        •	Pathology from a biopsy or resection proves tumors are different histologies
        •	Attending, oncologist, or pulmonologist state unequivocally that the tumors are different primaries
            	Unequivocal means that no words such as “probable” are used in the statement. Terms which are on the “ambiguous terms” list such as “probable” cannot be used to prove metastasis.

    Rule M12	Abstract a single primary (the invasive) when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor in the same lung.
        Note 1:	The rules are hierarchical. Only use this rule when none of the previous rules apply.
        Note 2:	The tumors may be an NOS and a subtype/variant of that NOS
        Note 3:	When the case has been abstracted, change behavior code on original abstract from /2 to /3. Do not change date of diagnosis.
        Note 4:	If the case has already been submitted to the central registry, report all changes.
        Note 5:	The physician may stage both tumors because staging and determining multiple primaries are done for different reasons. Staging determines which treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).
        Note 6:	See the COC and SEER manuals for instructions on coding other data items such as Date of Diagnosis, Accession Year and Sequence Number.

    Rule M13	Abstract multiple primaries when an invasive tumor occurs more than 60 days after an in situ tumor in the same lung.
        Note 1:	The rules are hierarchical. Only use this rule when none of the previous rules apply.
        Note 2:	Abstract both the invasive and in situ tumors.
        Note 3:	Abstract as multiple primaries even if physician states the invasive tumor is disease recurrence or progression.
        Note 4:	This rule is based on long-term epidemiologic studies of recurrence intervals. The specialty medical experts (SMEs) reviewed and approved these rules.  Many of the SMEs were also authors, co-authors, or editors of the AJCC Staging Manual.

    Rule M14	Abstract a single primary when none of the previous rules apply.
        Note:	This rule is simply a “safety net” or default rule. If none of the previous rules applied to the case you were abstracting, default to this rule.

    */



    // Lung Multiple Primary Rules
    // C340-C343, C348, C349
    // (Excludes lymphoma and leukemia M9590–M9992 and Kaposi sarcoma M9140)
    public Mp2018LungGroup() {
        super(MphConstants.MP_2018_LUNG_GROUP_ID, MphConstants.MP_2018_LUNG_GROUP_NAME, "C340-C343, C348, C349", null, null,
                "9590-9992, 9140", "2-3,6", "2018-9999");

        // Rule M3	Abstract multiple primaries when multiple tumors are present in sites with site codes that differ at the second CXxx and/or third character CxXx.
        // TODO
        MphRule rule = new MphRulePrimarySiteCode(MphConstants.MP_2018_LUNG_GROUP_ID, "M3");
        rule.getNotes().add("When codes differ at the second or third characters, the tumors are in different primary sites.");
        _rules.add(rule);

        // Rule M4	Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than three years after the original diagnosis or last recurrence.
        // TODO
        rule = new MphRule(MphConstants.MP_2018_LUNG_GROUP_ID, "M4") {
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
        rule.getNotes().add("Clinically disease-free means that there was no evidence of recurrence in the same lung on follow-up.");
        rule.getNotes().add("When there is a recurrence less than or equal to three years of diagnosis, the “clock” starts over. The time interval is calculated from the date of last recurrence. In other words, the patient must have been disease-free for greater than three years from the date of the last recurrence.");
        rule.getNotes().add("When it is unknown/not documented whether the patient had a recurrence, default to date of diagnosis to compute the time interval.");
        rule.getNotes().add("The physician may state this is a recurrence, meaning the patient had a previous lung tumor and now has another lung site tumor. Follow the rules; do not attempt to interpret the physician’s statement.");
        _rules.add(rule);

        // Rule M5	Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 3 in the Equivalent Terms and Definitions. Tumors may be:
        // •	Simultaneous OR
        // •	Original and subsequent
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_LUNG_GROUP_ID, "M5");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("Each row in the table is a distinctly different histology.");
        _rules.add(rule);

        // Rule M6	Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 3 in the Equivalent Terms and Definitions. Tumors may be
        // •	Simultaneous OR
        // •	Original and subsequent.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_LUNG_GROUP_ID, "M6");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The tumors may be subtypes/variants of the same or different NOS histologies.");
        rule.getNotes().add("  •	 Same NOS: Colloid adenocarcinoma 8480/3 and lepidic adenocarcinoma 8250/3 are both subtypes of adenocarcinoma NOS 8140/3 but are distinctly different histologies. Abstract multiple primaries");
        rule.getNotes().add("  • Different NOS: Keratinizing squamous cell carcinoma 8071/3 is a subtype of squamous cell carcinoma NOS 8070; Typical carcinoid 8240/3 is a subtype of small cell carcinoma/neuroendocrine tumors (NET Tumors) 8041/3. They are distinctly different histologies. Abstract multiple primaries.");
        _rules.add(rule);

        // Rule M7	Abstract a single primary when there are simultaneous multiple tumors in the same lung that are
        // •	In situ and invasive OR
        // •	A NOS and a subtype/variant of that NOS.
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_LUNG_GROUP_ID, "M16");
        // TODO
        rule.setQuestion("");
        rule.setReason("");
        rule.getExamples().add("One tumor is invasive adenocarcinoma 8140/3 AND another tumor is adenocarcinoma in situ 8140/2.");
        rule.getNotes().add("See Table 3 in the Equivalent Terms and Definitions for NOS and subtypes/variants.");
        rule.getNotes().add("NOS and subtypes/variants are:");
        rule.getNotes().add("  • Adenocarcinoma 8140 and any subtype/variant of adenocarcinoma");
        rule.getNotes().add("  • Mucinous adenocarcinoma, invasive 8253 and any subtype/variant of invasive mucinous adenocarcinoma");
        rule.getNotes().add("  • Non-mucinous adenocarcinoma, invasive 8140 and any subtype/variant of non-mucinous adenocarcinoma");
        rule.getNotes().add("  • Non-small cell carcinoma 8046 and any subtype/variant of non-small cell carcinoma");
        rule.getNotes().add("  • Pleomorphic carcinoma 8022 and any subtype/variant of pleomorphic carcinoma");
        rule.getNotes().add("  • Small cell neuroendocrine tumors/NET 8041 and any subtype/variant of small cell neuroendocrine tumor/NET");
        rule.getNotes().add("  • Squamous cell carcinoma 8070 and any subtype/variant of squamous cell carcinoma");
        rule.getNotes().add("  • Synovial sarcoma 9040 and any subtype/variant of synovial sarcoma");
        _rules.add(rule);

        // Rule M8	Abstract a single primary when an in situ tumor is diagnosed after an invasive tumor AND tumors occur in the same lung.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_LUNG_GROUP_ID, "M8");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The rules are hierarchical. Only use this rule when none of the previous rules apply.");
        rule.getNotes().add("The tumors may be a NOS and a subtype/variant of that NOS. See Table 3 in the Equivalent Terms and Definitions for listings of NOS and subtype/variants.");
        rule.getNotes().add("Once the patient has an invasive tumor, the in situ is recorded as a recurrence for those registrars who collect recurrence data.");
        _rules.add(rule);

        // Rule M9	Abstract multiple primaries when there is at least one tumor that is small cell carcinoma 8041 or any small cell subtypes/variants and another tumor that is non-small cell carcinoma 8046 or any of the non-small cell carcinoma subtypes/variants.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_LUNG_GROUP_ID, "M9");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("Small cell carcinoma and non-small cell carcinoma are the two major classifications/divisions for lung cancer.");
        rule.getNotes().add("  • See Table 3 in Equivalent Terms and Definitions for terms and codes for small cell carcinoma and all of the subtypes/variants");
        rule.getNotes().add("  • With the exception of small cell/neuroendocrine carcinoma, all other histologies listed in Table 3 in Equivalent Terms and Definitions are non-small cell carcinoma");
        rule.getNotes().add("It is irrelevant whether the tumors are in the ipsilateral (same) lung or are bilateral (both lungs).");
        _rules.add(rule);

        // Rule M10	Abstract multiple primaries when there is a single tumor in each lung (one tumor in the right lung and one tumor in the left lung).
        // TODO
        rule = new MphRule(MphConstants.MP_2018_LUNG_GROUP_ID, "M10") {
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
        rule.getNotes().add("    ο Unequivocal means that no words such as “probably possibly, most likely, etc.” are used in the statement. Terms which are on the “ambiguous terms” list make the statement equivocal (cannot be used to prove metastases)");
        rule.getNotes().add("Lung metastases usually present as multiple tumors/masses. A single tumor in each lung is unlikely to be a single primary (e.g. metastatic to the contralateral lung).");
        rule.getNotes().add("The term “bilateral” is not a synonym for a single primary. It is simply a statement that there are tumors in both lungs.");
        rule.getNotes().add("This rule is based on long-term epidemiologic studies of multiple primaries. The specialty medical experts (SME) and the CoC site physician teams reviewed and approved these rules.  Many of the CoC site team physicians were also authors, co-authors, or editors of the AJCC Staging Manual.");
        rule.getNotes().add("Lymph node involvement is recorded in staging criteria.");
        _rules.add(rule);

        // Rule M11	Abstract a single primary when:
        // •	There is simultaneously a single tumor in one lung and multiple tumors in the contralateral lung OR
        // •	Simultaneous multiple tumors in both lungs OR
        // •	Simultaneous multiple tumors in the same lung
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_LUNG_GROUP_ID, "M11");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("When there are multiple tumors in one or both lungs, the physician usually biopsies only one mass/tumor. They treat the patient based on that single biopsy, assuming all of the masses/tumors are the same histology.");
        rule.getNotes().add("Code multiple primaries only when there is proof that one of the tumors is a different histology. Proof is any one of the following.");
        rule.getNotes().add("  • Pathology from a biopsy or resection proves tumors are different histologies");
        rule.getNotes().add("  • Attending, oncologist, or pulmonologist state unequivocally that the tumors are different primaries");
        rule.getNotes().add("     Unequivocal means that no words such as “probable” are used in the statement. Terms which are on the “ambiguous terms” list such as “probable” cannot be used to prove metastasis.");
        _rules.add(rule);

        // Rule M12	Abstract a single primary (the invasive) when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor in the same lung.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_LUNG_GROUP_ID, "M12");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The rules are hierarchical. Only use this rule when none of the previous rules apply.");
        rule.getNotes().add("The tumors may be an NOS and a subtype/variant of that NOS");
        rule.getNotes().add("When the case has been abstracted, change behavior code on original abstract from /2 to /3. Do not change date of diagnosis.");
        rule.getNotes().add("If the case has already been submitted to the central registry, report all changes.");
        rule.getNotes().add("The physician may stage both tumors because staging and determining multiple primaries are done for different reasons. Staging determines which treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).");
        rule.getNotes().add("See the COC and SEER manuals for instructions on coding other data items such as Date of Diagnosis, Accession Year and Sequence Number.");
        _rules.add(rule);

        // Rule M13	Abstract multiple primaries when an invasive tumor occurs more than 60 days after an in situ tumor in the same lung.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_LUNG_GROUP_ID, "M13");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The rules are hierarchical. Only use this rule when none of the previous rules apply.");
        rule.getNotes().add("Abstract both the invasive and in situ tumors.");
        rule.getNotes().add("Abstract as multiple primaries even if physician states the invasive tumor is disease recurrence or progression.");
        rule.getNotes().add("This rule is based on long-term epidemiologic studies of recurrence intervals. The specialty medical experts (SMEs) reviewed and approved these rules.  Many of the SMEs were also authors, co-authors, or editors of the AJCC Staging Manual.");
        _rules.add(rule);

        // Rule M14	Abstract a single primary when none of the previous rules apply.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_LUNG_GROUP_ID, "M14");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("This rule is simply a “safety net” or default rule. If none of the previous rules applied to the case you were abstracting, default to this rule.");
        _rules.add(rule);





        /*
        // M4- At least one tumor that is non-small cell carcinoma (8046) and another tumor that is small cell carcinoma (8041-8045) are multiple primaries.
        rule = new MphRule(MphConstants.MP_2018_LUNG_GROUP_ID, "M4") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (GroupUtility.differentCategory(i1.getHistology(), i2.getHistology(), MphConstants.NON_SMALL_CELL_CARCINOMA, MphConstants.SMALL_CELL_CARCINOMA))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Is at least one tumor non-small cell carcinoma (8046) and another tumor small cell carcinoma (8041-8045)?");
        rule.setReason("At least one tumor that is non-small cell carcinoma (8046) and another tumor that is small cell carcinoma (8041-8045) are multiple primaries.");
        _rules.add(rule);

        // M5- A tumor that is adenocarcinoma with mixed subtypes (8255) and another that is bronchioloalveolar (8250-8254) are multiple primaries.
        rule = new MphRule(MphConstants.MP_2018_LUNG_GROUP_ID, "M5") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (GroupUtility.differentCategory(i1.getHistology(), i2.getHistology(), MphConstants.ADENOCARCINOMA_WITH_MIXED_SUBTYPES, MphConstants.BRONCHIOALVEOLAR))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Is there a tumor that is adenocarcinoma with mixed subtypes (8255) and another that is bronchioalveolar (8250-8254)?");
        rule.setReason("A tumor that is adenocarcinoma with mixed subtypes (8255) and another that is bronchioloalveolar (8250-8254) are multiple primaries.");
        _rules.add(rule);

        // M6- A single tumor in each lung is multiple primaries.
        rule = new MphRule(MphConstants.MP_2018_LUNG_GROUP_ID, "M6") {
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
        rule.getNotes().add("When there is a single tumor in each lung abstract as multiple primaries unless stated or proven to be metastatic.");
        _rules.add(rule);

        // M7- Multiple tumors in both lungs with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.
        rule = new MphRule(MphConstants.MP_2018_LUNG_GROUP_ID, "M7") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                String hist1 = i1.getHistology(), hist2 = i2.getHistology();
                //if they are on the same lung, don't apply this
                if ((MphConstants.BOTH.equals(i2.getLaterality()) || MphConstants.BOTH.equals(i1.getLaterality())) && (!hist1.substring(0, 3).equals(hist2.substring(0, 3))))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Are there multiple tumors in both lungs with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number?");
        rule.setReason("Multiple tumors in both lungs with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (x?xx) number are multiple primaries.");
        _rules.add(rule);

        // M8- Tumors diagnosed more than three (3) years apart are multiple primaries.
        rule = new MphRule(MphConstants.MP_2018_LUNG_GROUP_ID, "M8") {
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

        // M9- An invasive tumor following an in situ tumor more than 60 days after diagnosis are multiple primaries.
        rule = new MphRuleBehavior(MphConstants.MP_2018_LUNG_GROUP_ID, "M9");
        _rules.add(rule);

        // M10- Tumors with non-small cell carcinoma, NOS (8046) and a more specific non-small cell carcinoma type (chart 1) are a single primary.
        rule = new MphRule(MphConstants.MP_2007_LUNG_GROUP_ID, "M10") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (GroupUtility.differentCategory(i1.getHistology(), i2.getHistology(), MphConstants.NON_SMALL_CELL_CARCINOMA, MphConstants.SPECIFIC_NON_SMALL_CELL_CARCINOMA))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                return result;
            }
        };
        rule.setQuestion("Are there tumors with non-small cell carcinoma (8046) and a more specific non-small cell carcinoma type (chart 1)?");
        rule.setReason("Tumors with non-small cell carcinoma, NOS (8046) and a more specific non-small cell carcinoma type (chart 1) are a single primary.");
        _rules.add(rule);

        // M11- Tumors with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.
        rule = new MphRuleHistologyCode(MphConstants.MP_2018_LUNG_GROUP_ID, "M11");
        rule.getNotes().add("Adenocarcinoma in one tumor and squamous cell carcinoma in another tumor are multiple primaries.");
        _rules.add(rule);

        // M12- Tumors that do not meet any of the criteria are abstracted as a single primary.
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_LUNG_GROUP_ID, "M12");
        rule.getNotes().add("When an invasive tumor follows an in situ tumor within 60 days, abstract as a single primary.");
        rule.getNotes().add("All cases covered by this rule are the same histology.");
        _rules.add(rule);
        */
    }
}

