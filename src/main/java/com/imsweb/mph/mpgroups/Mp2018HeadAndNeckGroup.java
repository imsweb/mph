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

public class Mp2018HeadAndNeckGroup extends MphGroup {

    /*
    Head and Neck Multiple Primary Rules
    C000-C148, C300-C339, C410, C411, C442
    (Excludes lymphoma and leukemia M9590 – M9992 and Kaposi sarcoma M9140)

    Note: Multiple tumors may be a single primary or multiple primaries.

    Rule M3	Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Tables 2-10 in the Equivalent Terms and Definitions. Tumors may be
        •	Simultaneous OR
        •	Original and subsequent
        Note:  Each row in the table is a distinctly different histology.

    Rule M4	Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3, Tables 2-10 in the Equivalent Terms and Definitions. Tumors may be
        •	Simultaneous OR
        •	Original and subsequent
        Note: The tumors may be subtypes/variants of the same or different NOS histologies.
        •	Same NOS: Alveolar rhabdomyosarcoma 8920/3 and embryonal rhabdomyosarcoma 8910/3 are both subtypes of Rhabdomyosarcoma 8900/3 but are distinctly different histologies. Abstract multiple primaries.
        •	Different NOS: Colloid-type adenocarcinoma 8144 is a subtype of adenocarcinoma NOS 8140; Sarcomatoid carcinoma 8074 is a subtype of squamous cell carcinoma 8070. They are distinctly different histologies. Abstract multiple primaries.

    Rule M5	Abstract multiple primaries when there are tumors on both the:
        •	Upper lip C000 or C005 AND lower lip C001 or C003 OR
        •	Upper gum C030 AND lower gum C031 OR
        •	Nasal cavity C300 AND middle ear C301
        Note 1:	Use this rule only for multiple tumors.
        Note 2:	The tumors are multiple primaries whether they are synchronous or occur at different times.
        Note 3:	The tumors are multiple primaries regardless of histology (may be the same histology or different histologies).

    Rule M6	Abstract multiple primaries when separate, non-contiguous tumors are present in sites with ICD-O site codes that differ at the second CXxx, and/or third characters CxXx.

    Rule M7	Abstract multiple primaries when there are separate, non-contiguous tumors on both the right side and the left side of a paired site.
        Note:	See Equivalent Terms and Definitions Table 11 for list of paired sites.

    Rule M8	Abstract a single primary when an in situ tumor is diagnosed after an invasive tumor.
        Note 1:	The rules are hierarchical. Only use this rule when none of the previous rules apply.
        Note 2:	The tumors may be a NOS and a subtype/variant of that NOS. See Tables 2-10 in the Equivalent Terms and Definitions for listings of NOS and subtype/variants.
        Note 3:	Once the patient has an invasive tumor, the in situ is recorded as a recurrence for those registrars who collect recurrence data.

    Rule M9	Abstract a single primary when there is a NOS and a subtype/variant of that NOS such as the following:
        •	Adenocarcinoma 8140 and subtypes/variants of adenocarcinoma
        •	Ameloblastic carcinoma-primary type 9270/3 and subtypes/variants of ameloblastic carcinoma-primary type
        •	Ceruminous adenocarcinoma 8420 and subtypes/variants of ceruminous adenocarcinoma
        •	Chondrosarcoma grade 2/3 9220/3 and subtypes/variants of chondrosarcoma grade 2/3
        •	Neuroendocrine carcinoma 8246 and subtypes/variants of neuroendocrine carcinoma
        •	Odontogenic carcinosarcoma 8980/3 and subtypes/variants of odontogenic carcinosarcoma
        •	Osteosarcoma 9180/3 and subtypes/variants of osteosarcoma
        •	Rhabdomyosarcoma 8900/3 and subtypes/variants of rhabdomyosarcoma
        •	Sarcoma 8800/3 and subtypes/variants of sarcoma
        •	Small cell neuroendocrine carcinoma 8041 and subtypes/variants of small cell neuroendocrine carcinoma
        •	Squamous cell carcinoma 8070 and subtypes/variants of squamous cell carcinoma
        •	Well-differentiated neuroendocrine carcinoma 8240 and subtypes/variants of well-differentiated neuroendocrine carcinoma

    Rule M10	Abstract a single primary (the invasive) when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor.
        Note 1:	The rules are hierarchical. Only use this rule when none of the previous rules apply.
        Note 2:	The tumors may be an NOS and a subtype/variant of that NOS
        Note 3:	When the case has been abstracted, change behavior code on original abstract from /2 to /3. Do not change date of diagnosis.
        Note 4:	If the case has already been submitted to the central registry, report all changes.
        Note 5:	The physician may stage both tumors because staging and determining multiple primaries are done for different reasons. Staging determines which treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).
        Note 6:	See the COC and SEER manuals for instructions on coding other data items such as Date of Diagnosis, Accession Year and Sequence Number.

    Rule M11	Abstract multiple primaries when an invasive tumor occurs more than 60 days after an in situ tumor.
        Note 1:	The rules are hierarchical. Only use this rule when none of the previous rules apply.
        Note 2:	Abstract both the invasive and in situ tumors.
        Note 3:	Abstract as multiple primaries even if physician states the invasive tumor is disease recurrence or progression.
        Note 4:	This rule is based on long-term epidemiologic studies of recurrence intervals. The specialty medical experts (SMEs) reviewed and approved these rules.  Many of the SMEs were also authors, co-authors, or editors of the AJCC Staging Manual.

    Rule M12	Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than five years after the original diagnosis or last recurrence.
        Note 1:	Clinically disease-free means that there was no evidence of recurrence on follow-up.
        •	Scopes are NED
        •	Scans are NED
        Note 2:	When there is a recurrence less than or equal to three years of diagnosis, the “clock” starts over. The time interval is calculated from the date of last recurrence. In other words, the patient must have been disease-free for greater than three years from the date of the last recurrence.
        Note 3:	When it is unknown/not documented whether the patient had a recurrence, default to date of diagnosis to compute the time interval.
        Note 4:	The physician may state this is a recurrence, meaning the patient had a previous head and neck tumor and now has another head and neck tumor. Follow the rules; do not attempt to interpret the physician’s statement.

    */


    // TODO - Question M3, M4 - Does "Tables 2-10" means a union of tables 2, 3, 4, 5, 6, 7, 8, 9, and 10?
    // TODO - Question M3, M4 - Table 2 contains 4 columns, are the last two considered sub-types (union)? How does this work with M4 (with tables of different numbers of columns)?
    // TODO - Question M3, M4 - Table 10 does not have a sub-type column. How do we use this?
    // TODO - Question M7 - Table 11 - This table doesn't say anything about paired sites. Is this the correct table? Is each site separate, or they are all considered paired with each other?
    // TODO - Question M9 - In the wording "of that NOS such as the following:", does this mean only the histologies listed here, or use all of the histologies from Tables 2 through 10?
    // TODO - Question M9 - "Chondrosarcoma grade 2/3 9220/3 and subtypes/variants of chondrosarcoma grade 2/3", looking in Table 4, this histology does not have any subtypes?


    // Head and Neck Multiple Primary Rules
    // C000-C148, C300-C339, C410, C411, C442
    // (Excludes lymphoma and leukemia M9590 – M9992 and Kaposi sarcoma M9140)
    public Mp2018HeadAndNeckGroup() {
        super(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, MphConstants.MP_2018_HEAD_AND_NECK_GROUP_NAME, "C000-C148, C300-C339, C410, C411, C442", null, null,
                "9590-9992, 9140", "2-3,6", "2018-9999");

        // Rule M3	Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Tables 2-10 in the Equivalent Terms and Definitions. Tumors may be
        // •	Simultaneous OR
        // •	Original and subsequent
        // TODO
        MphRule rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M3");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("Each row in the table is a distinctly different histology.");
        _rules.add(rule);

        // Rule M4	Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3, Tables 2-10 in the Equivalent Terms and Definitions. Tumors may be
        // •	Simultaneous OR
        // •	Original and subsequent
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M4");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The tumors may be subtypes/variants of the same or different NOS histologies.");
        rule.getNotes().add("  • Same NOS: Alveolar rhabdomyosarcoma 8920/3 and embryonal rhabdomyosarcoma 8910/3 are both subtypes of Rhabdomyosarcoma 8900/3 but are distinctly different histologies. Abstract multiple primaries.");
        rule.getNotes().add("  • Different NOS: Colloid-type adenocarcinoma 8144 is a subtype of adenocarcinoma NOS 8140; Sarcomatoid carcinoma 8074 is a subtype of squamous cell carcinoma 8070. They are distinctly different histologies. Abstract multiple primaries.");
        _rules.add(rule);

        // Rule M5	Abstract multiple primaries when there are tumors on both the:
        // •	Upper lip C000 or C005 AND lower lip C001 or C003 OR
        // •	Upper gum C030 AND lower gum C031 OR
        // •	Nasal cavity C300 AND middle ear C301
        rule = new MphRule(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M5") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (GroupUtility.differentCategory(i1.getPrimarySite(), i2.getPrimarySite(), MphConstants.UPPER_LIP_2018, MphConstants.LOWER_LIP_2018))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                else if (GroupUtility.differentCategory(i1.getPrimarySite(), i2.getPrimarySite(), MphConstants.UPPER_GUM, MphConstants.LOWER_GUM))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                else if (GroupUtility.differentCategory(i1.getPrimarySite(), i2.getPrimarySite(), MphConstants.NASAL_CAVITY, MphConstants.MIDDLE_EAR))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Are there tumors on the upper lip (C000, C005) and the lower lip (C001, C003), the upper gum (C030) and the lower gum (C031), or the nasal cavity (C300) and the middle ear (C301)?");
        rule.setReason("Tumors on the upper lip (C000, C005) and the lower lip (C001, C003), the upper gum (C030) and the lower gum (C031), or the nasal cavity (C300) and the middle ear (C301) are multiple primaries.");
        rule.getNotes().add("Use this rule only for multiple tumors.");
        rule.getNotes().add("The tumors are multiple primaries whether they are synchronous or occur at different times.");
        rule.getNotes().add("The tumors are multiple primaries regardless of histology (may be the same histology or different histologies).");
        _rules.add(rule);

        // Rule M6	Abstract multiple primaries when separate, non-contiguous tumors are present in sites with ICD-O site codes that differ at the second CXxx, and/or third characters CxXx.
        // TODO
        rule = new MphRule(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M6") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (!i1.getPrimarySite().substring(1, 3).equals(i2.getPrimarySite().substring(1, 3)))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Are there separate, non-contiguous tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx) and/or third character (Cx?x)?");
        rule.setReason("Separate, non-contiguous tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx) and/or third (Cx?x) character are multiple primaries.");
        rule.getNotes().add("See Equivalent Terms and Definitions Table 11 for list of paired sites.");
        rule.getNotes().add("Use this rule only for separate, non-contiguous tumors.");
        _rules.add(rule);

        // Rule M7	Abstract multiple primaries when there are separate, non-contiguous tumors on both the right side and the left side of a paired site.
        // TODO - Is Paired site list correct?
        rule = new MphRuleLeftAndRight(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M7",
                        Arrays.asList("C312,C310,C301,C300,C098,C079,C081,C080,C090,C091,C099"), null);
        rule.setQuestion("Are there separate, non-contiguous tumors on both the right side and the left side of a paired site?");
        rule.setReason("Separate, non-contiguous tumors on the right side and the left side of a paired site are multiple primaries.");
        rule.getNotes().add("See Equivalent Terms and Definitions Table 11 for list of paired sites.");
        _rules.add(rule);

        // Rule M8	Abstract a single primary when an in situ tumor is diagnosed after an invasive tumor.
        rule = new MphRule(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M8") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (GroupUtility.isOneBehaviorBeforeTheOther(i1, i2, MphConstants.MALIGNANT, MphConstants.INSITU))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                return result;
            }
        };
        rule.setQuestion("Is there an in situ tumor diagnosed after an in invasive tumor?");
        rule.setReason("An in situ tumor diagnosed after an invasive tumor is a single primary.");
        rule.getNotes().add("The rules are hierarchical. Only use this rule when none of the previous rules apply.");
        rule.getNotes().add("The tumors may be a NOS and a subtype/variant of that NOS. See Tables 2-10 in the Equivalent Terms and Definitions for listings of NOS and subtype/variants.");
        rule.getNotes().add("Once the patient has an invasive tumor, the in situ is recorded as a recurrence for those registrars who collect recurrence data.");
        _rules.add(rule);

        // Rule M9	Abstract a single primary when there is a NOS and a subtype/variant of that NOS such as the following:
        // •	Adenocarcinoma 8140 and subtypes/variants of adenocarcinoma
        // •	Ameloblastic carcinoma-primary type 9270/3 and subtypes/variants of ameloblastic carcinoma-primary type
        // •	Ceruminous adenocarcinoma 8420 and subtypes/variants of ceruminous adenocarcinoma
        // •	Chondrosarcoma grade 2/3 9220/3 and subtypes/variants of chondrosarcoma grade 2/3
        // •	Neuroendocrine carcinoma 8246 and subtypes/variants of neuroendocrine carcinoma
        // •	Odontogenic carcinosarcoma 8980/3 and subtypes/variants of odontogenic carcinosarcoma
        // •	Osteosarcoma 9180/3 and subtypes/variants of osteosarcoma
        // •	Rhabdomyosarcoma 8900/3 and subtypes/variants of rhabdomyosarcoma
        // •	Sarcoma 8800/3 and subtypes/variants of sarcoma
        // •	Small cell neuroendocrine carcinoma 8041 and subtypes/variants of small cell neuroendocrine carcinoma
        // •	Squamous cell carcinoma 8070 and subtypes/variants of squamous cell carcinoma
        // •	Well-differentiated neuroendocrine carcinoma 8240 and subtypes/variants of well-differentiated neuroendocrine carcinoma
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M9");
        rule.setQuestion("");
        rule.setReason("");
        _rules.add(rule);

        // Rule M10	Abstract a single primary (the invasive) when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor.
        rule = new MphRuleInvasiveAfterInSituLess60Days(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M10", false);
        rule.getNotes().add("The rules are hierarchical. Only use this rule when none of the previous rules apply.");
        rule.getNotes().add("The tumors may be an NOS and a subtype/variant of that NOS");
        rule.getNotes().add("When the case has been abstracted, change behavior code on original abstract from /2 to /3. Do not change date of diagnosis.");
        rule.getNotes().add("If the case has already been submitted to the central registry, report all changes.");
        rule.getNotes().add("The physician may stage both tumors because staging and determining multiple primaries are done for different reasons. Staging determines which treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).");
        rule.getNotes().add("See the COC and SEER manuals for instructions on coding other data items such as Date of Diagnosis, Accession Year and Sequence Number.");
        _rules.add(rule);

        // Rule M11	Abstract multiple primaries when an invasive tumor occurs more than 60 days after an in situ tumor.
        rule = new MphRuleBehavior(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M11");
        rule.getNotes().add("The rules are hierarchical. Only use this rule when none of the previous rules apply.");
        rule.getNotes().add("Abstract both the invasive and in situ tumors.");
        rule.getNotes().add("Abstract as multiple primaries even if physician states the invasive tumor is disease recurrence or progression.");
        rule.getNotes().add("This rule is based on long-term epidemiologic studies of recurrence intervals. The specialty medical experts (SMEs) reviewed and approved these rules.  Many of the SMEs were also authors, co-authors, or editors of the AJCC Staging Manual.");
        _rules.add(rule);

        // Rule M12	Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than five years after the original diagnosis or last recurrence.
        rule = new MphRuleDiagnosisDate(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M12");
        rule.getNotes().add("Clinically disease-free means that there was no evidence of recurrence on follow-up.");
        rule.getNotes().add("•	Scopes are NED");
        rule.getNotes().add("•	Scans are NED");
        rule.getNotes().add("When there is a recurrence less than or equal to three years of diagnosis, the “clock” starts over. The time interval is calculated from the date of last recurrence. In other words, the patient must have been disease-free for greater than three years from the date of the last recurrence.");
        rule.getNotes().add("When it is unknown/not documented whether the patient had a recurrence, default to date of diagnosis to compute the time interval.");
        rule.getNotes().add("The physician may state this is a recurrence, meaning the patient had a previous head and neck tumor and now has another head and neck tumor. Follow the rules; do not attempt to interpret the physician’s statement.");
        _rules.add(rule);
    }
}

