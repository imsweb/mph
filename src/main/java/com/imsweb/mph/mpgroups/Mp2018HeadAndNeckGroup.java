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
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M5");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("Use this rule only for multiple tumors.");
        rule.getNotes().add("The tumors are multiple primaries whether they are synchronous or occur at different times.");
        rule.getNotes().add("The tumors are multiple primaries regardless of histology (may be the same histology or different histologies).");
        _rules.add(rule);

        // Rule M6	Abstract multiple primaries when separate, non-contiguous tumors are present in sites with ICD-O site codes that differ at the second CXxx, and/or third characters CxXx.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M6");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("See Equivalent Terms and Definitions Table 11 for list of paired sites.");
        rule.getNotes().add("Use this rule only for separate, non-contiguous tumors.");
        _rules.add(rule);

        // Rule M7	Abstract multiple primaries when there are separate, non-contiguous tumors on both the right side and the left side of a paired site.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M7");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("See Equivalent Terms and Definitions Table 11 for list of paired sites.");
        _rules.add(rule);

        // Rule M8	Abstract a single primary when an in situ tumor is diagnosed after an invasive tumor.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M8");
        rule.setQuestion("");
        rule.setReason("");
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
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M10");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The rules are hierarchical. Only use this rule when none of the previous rules apply.");
        rule.getNotes().add("The tumors may be an NOS and a subtype/variant of that NOS");
        rule.getNotes().add("When the case has been abstracted, change behavior code on original abstract from /2 to /3. Do not change date of diagnosis.");
        rule.getNotes().add("If the case has already been submitted to the central registry, report all changes.");
        rule.getNotes().add("The physician may stage both tumors because staging and determining multiple primaries are done for different reasons. Staging determines which treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).");
        rule.getNotes().add("See the COC and SEER manuals for instructions on coding other data items such as Date of Diagnosis, Accession Year and Sequence Number.");
        _rules.add(rule);

        // Rule M11	Abstract multiple primaries when an invasive tumor occurs more than 60 days after an in situ tumor.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M11");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The rules are hierarchical. Only use this rule when none of the previous rules apply.");
        rule.getNotes().add("Abstract both the invasive and in situ tumors.");
        rule.getNotes().add("Abstract as multiple primaries even if physician states the invasive tumor is disease recurrence or progression.");
        rule.getNotes().add("This rule is based on long-term epidemiologic studies of recurrence intervals. The specialty medical experts (SMEs) reviewed and approved these rules.  Many of the SMEs were also authors, co-authors, or editors of the AJCC Staging Manual.");
        _rules.add(rule);

        // Rule M12	Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than five years after the original diagnosis or last recurrence.
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M12");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("Clinically disease-free means that there was no evidence of recurrence on follow-up.");
        rule.getNotes().add("•	Scopes are NED");
        rule.getNotes().add("•	Scans are NED");
        rule.getNotes().add("When there is a recurrence less than or equal to three years of diagnosis, the “clock” starts over. The time interval is calculated from the date of last recurrence. In other words, the patient must have been disease-free for greater than three years from the date of the last recurrence.");
        rule.getNotes().add("When it is unknown/not documented whether the patient had a recurrence, default to date of diagnosis to compute the time interval.");
        rule.getNotes().add("The physician may state this is a recurrence, meaning the patient had a previous head and neck tumor and now has another head and neck tumor. Follow the rules; do not attempt to interpret the physician’s statement.");
        _rules.add(rule);




        /*
        // M3 - Tumors on the right side and the left side of a paired site are multiple primaries.
        MphRule rule = new MphRule(MphConstants.MP_2007_HEAD_AND_NECK_GROUP_ID, "M3") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                List<String> pairedSites = Arrays.asList("C079", "C080,C081", "C090,C091,C098,C099", "C300", "C310,C312", "C301");
                if (GroupUtility.isPairedSites(i1.getPrimarySite(), i2.getPrimarySite(), pairedSites)) {
                    if (!GroupUtility.validPairedSiteLaterality(i1.getLaterality(), i2.getLaterality())) {
                        result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                        result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". Valid and known laterality for paired sites of head and neck should be provided.");
                    }
                    else if (GroupUtility.areOppositeSides(i1.getLaterality(), i2.getLaterality()))
                        result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                }
                return result;
            }
        };
        rule.setQuestion("Are there tumors in both the left and right sides of a paired site?");
        rule.setReason("Tumors on the right side and the left side of a paired site are multiple primaries.");
        _rules.add(rule);

        //M4- Tumors on the upper lip (C000 or C003) and the lower lip (C001 or C004) are multiple primaries.
        rule = new MphRule(MphConstants.MP_2007_HEAD_AND_NECK_GROUP_ID, "M4") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (GroupUtility.differentCategory(i1.getPrimarySite(), i2.getPrimarySite(), MphConstants.UPPER_LIP, MphConstants.LOWER_LIP))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Are there tumors on the upper lip (C000 or C003) and the lower lip (C001 or C004)?");
        rule.setReason("Tumors on the upper lip (C000 or C003) and the lower lip (C001 or C004) are multiple primaries.");
        _rules.add(rule);

        //M5- Tumors on the upper gum (C030) and the lower gum (C031) are multiple primaries.
        rule = new MphRule(MphConstants.MP_2007_HEAD_AND_NECK_GROUP_ID, "M5") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (GroupUtility.differentCategory(i1.getPrimarySite(), i2.getPrimarySite(), MphConstants.UPPER_GUM, MphConstants.LOWER_GUM))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Are there tumors on the upper gum (C030) and the lower gum (C031)?");
        rule.setReason("Tumors on the upper gum (C030) and the lower gum (C031) are multiple primaries.");
        _rules.add(rule);

        //M6- Tumors in the nasal cavity (C300) and the middle ear (C301) are multiple primaries.
        rule = new MphRule(MphConstants.MP_2007_HEAD_AND_NECK_GROUP_ID, "M6") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (GroupUtility.differentCategory(i1.getPrimarySite(), i2.getPrimarySite(), MphConstants.NASAL_CAVITY, MphConstants.MIDDLE_EAR))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Are there tumors in the nasal cavity (C300) and the middle ear (C301)?");
        rule.setReason("Tumors in the nasal cavity (C300) and the middle ear (C301) are multiple primaries.");
        _rules.add(rule);

        //M7- Tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx) and/or third (Cx?x) character are multiple primaries.
        rule = new MphRulePrimarySiteCode(MphConstants.MP_2007_HEAD_AND_NECK_GROUP_ID, "M7");
        _rules.add(rule);

        //M8- An invasive tumor following an insitu tumor more than 60 days after diagnosis are multiple primaries.
        rule = new MphRuleBehavior(MphConstants.MP_2007_HEAD_AND_NECK_GROUP_ID, "M8");
        _rules.add(rule);

        //M9- Tumors diagnosed more than five (5) years apart are multiple primaries.
        rule = new MphRuleDiagnosisDate(MphConstants.MP_2007_HEAD_AND_NECK_GROUP_ID, "M9");
        _rules.add(rule);

        //M10 -
        rule = new MphRule(MphConstants.MP_2007_HEAD_AND_NECK_GROUP_ID, "M10") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                String hist1 = i1.getHistology(), hist2 = i2.getHistology();
                List<String> nosList = Arrays.asList("8000", "8010", "8140", "8070", "8720", "8800");
                if ((nosList.contains(hist1) && MphConstants.NOS_VS_SPECIFIC.containsKey(hist1) && MphConstants.NOS_VS_SPECIFIC.get(hist1).contains(hist2)) || (nosList.contains(hist2)
                        && MphConstants.NOS_VS_SPECIFIC.containsKey(hist2) && MphConstants.NOS_VS_SPECIFIC.get(hist2).contains(hist1)))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);

                return result;
            }
        };
        rule.setQuestion("Is there cancer/malignant neoplasm, NOS (8000) and another is a specific histology? or\n" +
                "Is there carcinoma, NOS (8010) and another is a specific carcinoma? or\n" +
                "Is there adenocarcinoma, NOS (8140) and another is a specific adenocarcinoma? or\n" +
                "Is there squamous cell carcinoma, NOS (8070) and another is a specific squamous cell carcinoma? or\n" +
                "Is there melanoma, NOS (8720) and another is a specific melanoma? or\n" +
                "Is there sarcoma, NOS (8800) and another is a specific sarcoma?");
        rule.setReason("Abstract as a single primary* when one tumor is:\n" +
                "- Cancer/malignant neoplasm, NOS (8000) and another is a specific histology or\n" +
                "- Carcinoma, NOS (8010) and another is a specific carcinoma or\n" +
                "- Adenocarcinoma, NOS (8140) and another is a specific adenocarcinoma or\n" +
                "- Squamous cell carcinoma, NOS (8070) and another is specific squamous cell carcinoma or\n" +
                "- Melanoma, NOS (8720) and another is a specific melanoma or\n" +
                "- Sarcoma, NOS (8800) and another is a specific sarcoma");
        _rules.add(rule);

        //M11- Tumors with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.
        rule = new MphRuleHistologyCode(MphConstants.MP_2007_HEAD_AND_NECK_GROUP_ID, "M11");
        _rules.add(rule);

        //M12- Tumors that do not meet any of the criteria are abstracted as a single primary.
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2007_HEAD_AND_NECK_GROUP_ID, "M12");
        rule.getNotes().add("When an invasive tumor follows an in situ tumor within 60 days, abstract as a single primary.");
        rule.getNotes().add("All cases covered by Rule M12 have the same first 3 numbers in ICD-O-3 histology code.");
        rule.getExamples().add("Multifocal tumors in floor of mouth.");
        rule.getExamples().add("An in situ and invasive tumor diagnosed within60 days.");
        rule.getExamples().add("In situ following an invasive tumor more than 60 days apart.");
        _rules.add(rule);
        */
    }
}

