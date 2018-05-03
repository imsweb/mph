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
    C000-C148, C300-C329
    (Excludes lymphoma and leukemia M9590 – M9992 and Kaposi sarcoma M9140)

    Rule M3     Abstract multiple primaries when there are tumors on both the:
                • Upper lip C000 or C005 AND lower lip C001 or C003 OR
                • Upper gum C030 AND lower gum C031 OR
                • Nasal cavity C300 AND middle ear C301
                Note 1: Use this rule only for multiple tumors.
                Note 2: The tumors are multiple primaries whether they are synchronous or occur at different times.
                Note 3: The tumors are multiple primaries regardless of histology (may be the same histology or different histologies).

    Rule M4     Abstract multiple primaries when tumors are diagnosed more than five years apart. Tumors may be:
                • The same histology (same at the first, second, third, and the fourth number XXXX) OR
                • An NOS and more specific tumor (See Tables 2-10 in the Equivalent terms and Definitions) OR
                • Different histologies (different at the first, second, or third number XXXx)
                Note 1: The time interval means the patient has been clinically disease-free (for more than five years). Clinically disease-free means there are no clinical signs of recurrent or metastatic disease.
                Note 2: When the patient has a recurrence within five years of diagnosis, the “clock” starts over. The five-year disease-free interval is no longer computed from the date of diagnosis, it starts from the date of the last known recurrence. In other words, the patient must have been disease-free for at more than five years following the last recurrence.
                Note 3: When it is unknown whether there was a recurrence, default to date of diagnosis to compute the one-year interval.
                Note 4: Recurrent tumors may be either malignant /3 or in situ /2.
                Note 5: The physician specialists used surveillance data to determine the time interval which statistically confirmed a new primary.

    Rule M5     Abstract multiple primaries when there are separate tumors in sites with ICD-O site/topography codes that differ at the second CXxx and/or third CxXx character.

    Rule M6     Abstract multiple primaries when there are tumors on both the right side and the left side of a paired site.
                Note 1: See Equivalent Terms and Definitions Table 11 for list of paired sites.
                Note 2: Use this rule only for separate, non-contiguous tumors.

    Rule M7     Abstract a single primary when there are multiple tumors, at least one NOS and at least one subtype/variant (specific histology).
                Note 1: Go to the site-specific table (primary site being abstracted) in Tables 2-10 in the Equivalent Terms and Definitions to find the subtypes/variants for the NOS histology.
                Note 2: Subtypes/variants do not have the same ICD-O histology/morphology code as the NOS. Examples include:
                    • Ameloblastic carcinoma 9270 and a subtype/variant of ameloblastic carcinoma OR
                    • Cancer NOS 8000 and a subtype/variant of cancer NOS OR
                        Note: All histologies are subtypes/variants of cancer NOS. Sarcoma is the exception because it is not a cancer
                    • Carcinoma NOS 8010 and a subtype/variant of carcinoma NOS OR
                        Note: All histologies are subtypes/variants of carcinoma NOS. Sarcoma is the exception because it is not a carcinoma.
                    • Adenocarcinoma NOS 8140 and a subtype/variant of the NOS OR
                    • Angiosarcoma 9120 and a subtype/variant of angiosarcoma OR
                    • Chondrosarcoma 9220 and a subtype/variant of chondrosarcoma OR
                    • Melanoma NOS 87203 and a subtype/variant of melanoma OR
                    • Rhabdomyosarcoma 8900 and a subtype/variant of rhabdomyosarcoma OR
                    • Small cell carcinoma neuroendocrine type 8041 and a subtype/variant of small cell carcinoma neuroendocrine type OR
                    • Squamous cell carcinoma 8070 and a subtype/variant of squamous cell carcinoma OR
                    • Sarcoma NOS 8800/3 and a subtype/variant of sarcoma

    Rule M8     Abstract a single primary (the invasive) when the patient has an invasive tumor followed by an in-situ tumor.
                Note 1: Both the invasive and in situ must be:
                    • The same ICD-O histology/morphology code XXXX OR
                    • NOS and subtype/variant of the NOS
                Note 2: The rules are hierarchical so both the invasive and in situ must be in the same primary site.
                Note 3: Timing is irrelevant. Only the invasive is reported.

    Rule M9     Abstract a single primary (the invasive) when an invasive tumor occurs less than or equal to 60 days after the diagnosis of an in-situ tumor.
                Note 1: Only the invasive tumor is reported. If the case has been abstracted, change the behavior code on the original abstract from in situ /2 to invasive /3. Report changes to the central registry.
                Note 2: Both the invasive and in situ must be
                    • The same ICD-O histology/morphology code XXXX OR
                    • NOS and subtype/variant of the NOS
                Note 3: The rules are hierarchical so both the invasive and in situ must be in the same primary site
                Note 4: When an invasive component is diagnosed within 60 days of the in situ diagnosis, it was probably present at diagnosis. If the invasive was not present at diagnosis, the time interval is so short that it is still reported as one primary (the invasive).
                Note 5: The physician may stage both tumors because staging and determining multiple primaries are done for different reasons. Staging is done to determine which course of treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).

    Rule M10    Abstract multiple primaries when an invasive tumor occurs more than 60 days after the diagnosis of an in-situ tumor.
                Note 1: The purpose of this rule is to ensure that both the invasive and in situ malignancies are abstracted and counted as an incidence case when data are analyzed.
                Note 2: Follow the rules and abstract as multiple primaries when the physician states the invasive tumor is a recurrence or disease progression. It is disease progression, but the invasive must be reported for two reasons:
                    • So the disease will be counted as an incidence case AND
                    • Mortality data will not show a cause of death as in situ disease
                Note 3: The invasive and in situ must be
                    • The same ICD-O histology/morphology code XXXX OR
                    • NOS and subtype/variant of the NOS
                Note 4: The rules are hierarchical so both the invasive and in situ must be in the same primary site

    Rule M11    Abstract multiple primaries when there are multiple tumors with ICD-O histology codes that are different at the first Xxxx, second xXxx or third xxXx number.

    */

    public Mp2018HeadAndNeckGroup() {
        super(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, MphConstants.MP_2018_HEAD_AND_NECK_GROUP_NAME, "C000-C148, C300-C329", null, null,
                "9590-9992, 9140", "2-3,6", "2018-9999");

        // Rule M3 - Abstract multiple primaries when there are tumors on both the:
        // • Upper lip C000 or C005 AND lower lip C001 or C003 OR
        // • Upper gum C030 AND lower gum C031 OR
        // • Nasal cavity C300 AND middle ear C301
        // TODO
        MphRule rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M3");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("Use this rule only for multiple tumors.");
        rule.getNotes().add("The tumors are multiple primaries whether they are synchronous or occur at different times.");
        rule.getNotes().add("The tumors are multiple primaries regardless of histology (may be the same histology or different histologies).");
        _rules.add(rule);

        // Rule M4 - Abstract multiple primaries when tumors are diagnosed more than five years apart. Tumors may be:
        // • The same histology (same at the first, second, third, and the fourth number XXXX) OR
        // • An NOS and more specific tumor (See Tables 2-10 in the Equivalent terms and Definitions) OR
        // • Different histologies (different at the first, second, or third number XXXx)
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M4");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The time interval means the patient has been clinically disease-free (for more than five years). Clinically disease-free means there are no clinical signs of recurrent or metastatic disease.");
        rule.getNotes().add("When the patient has a recurrence within five years of diagnosis, the “clock” starts over. The five-year disease-free interval is no longer computed from the date of diagnosis, it starts from the date of the last known recurrence. In other words, the patient must have been disease-free for at more than five years following the last recurrence.");
        rule.getNotes().add("When it is unknown whether there was a recurrence, default to date of diagnosis to compute the one-year interval.");
        rule.getNotes().add("Recurrent tumors may be either malignant /3 or in situ /2.");
        rule.getNotes().add("The physician specialists used surveillance data to determine the time interval which statistically confirmed a new primary.");
        _rules.add(rule);

        // Rule M5 - Abstract multiple primaries when there are separate tumors in sites with ICD-O site/topography codes that differ at the second CXxx and/or third CxXx character.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M5");
        rule.setQuestion("");
        rule.setReason("");
        _rules.add(rule);

        // Rule M6 - Abstract multiple primaries when there are tumors on both the right side and the left side of a paired site.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M6");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("See Equivalent Terms and Definitions Table 11 for list of paired sites.");
        rule.getNotes().add("Use this rule only for separate, non-contiguous tumors.");
        _rules.add(rule);

        // Rule M7 - Abstract a single primary when there are multiple tumors, at least one NOS and at least one subtype/variant (specific histology).
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M7");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("Go to the site-specific table (primary site being abstracted) in Tables 2-10 in the Equivalent Terms and Definitions to find the subtypes/variants for the NOS histology.");
        rule.getNotes().add("Subtypes/variants do not have the same ICD-O histology/morphology code as the NOS. Examples include:");
        rule.getNotes().add("  • Ameloblastic carcinoma 9270 and a subtype/variant of ameloblastic carcinoma OR");
        rule.getNotes().add("  • Cancer NOS 8000 and a subtype/variant of cancer NOS OR");
        rule.getNotes().add("All histologies are subtypes/variants of cancer NOS. Sarcoma is the exception because it is not a cancer");
        rule.getNotes().add("  • Carcinoma NOS 8010 and a subtype/variant of carcinoma NOS OR");
        rule.getNotes().add("All histologies are subtypes/variants of carcinoma NOS. Sarcoma is the exception because it is not a carcinoma.");
        rule.getNotes().add("  • Adenocarcinoma NOS 8140 and a subtype/variant of the NOS OR");
        rule.getNotes().add("  • Angiosarcoma 9120 and a subtype/variant of angiosarcoma OR");
        rule.getNotes().add("  • Chondrosarcoma 9220 and a subtype/variant of chondrosarcoma OR");
        rule.getNotes().add("  • Melanoma NOS 87203 and a subtype/variant of melanoma OR");
        rule.getNotes().add("  • Rhabdomyosarcoma 8900 and a subtype/variant of rhabdomyosarcoma OR");
        rule.getNotes().add("  • Small cell carcinoma neuroendocrine type 8041 and a subtype/variant of small cell carcinoma neuroendocrine type OR");
        rule.getNotes().add("  • Squamous cell carcinoma 8070 and a subtype/variant of squamous cell carcinoma OR");
        rule.getNotes().add("  • Sarcoma NOS 8800/3 and a subtype/variant of sarcoma");
        _rules.add(rule);

        // Rule M8 - Abstract a single primary (the invasive) when the patient has an invasive tumor followed by an in-situ tumor.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M8");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("Both the invasive and in situ must be:");
        rule.getNotes().add("  • The same ICD-O histology/morphology code XXXX OR");
        rule.getNotes().add("  • NOS and subtype/variant of the NOS");
        rule.getNotes().add("The rules are hierarchical so both the invasive and in situ must be in the same primary site.");
        rule.getNotes().add("Timing is irrelevant. Only the invasive is reported.");
        _rules.add(rule);

        // Rule M9 - Abstract a single primary (the invasive) when an invasive tumor occurs less than or equal to 60 days after the diagnosis of an in-situ tumor.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M9");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("Only the invasive tumor is reported. If the case has been abstracted, change the behavior code on the original abstract from in situ /2 to invasive /3. Report changes to the central registry.");
        rule.getNotes().add("Both the invasive and in situ must be");
        rule.getNotes().add("  • The same ICD-O histology/morphology code XXXX OR");
        rule.getNotes().add("  • NOS and subtype/variant of the NOS");
        rule.getNotes().add("The rules are hierarchical so both the invasive and in situ must be in the same primary site");
        rule.getNotes().add("When an invasive component is diagnosed within 60 days of the in situ diagnosis, it was probably present at diagnosis. If the invasive was not present at diagnosis, the time interval is so short that it is still reported as one primary (the invasive).");
        rule.getNotes().add("The physician may stage both tumors because staging and determining multiple primaries are done for different reasons. Staging is done to determine which course of treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).");
        _rules.add(rule);

        // Rule M10 - Abstract multiple primaries when an invasive tumor occurs more than 60 days after the diagnosis of an in-situ tumor.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M10");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The purpose of this rule is to ensure that both the invasive and in situ malignancies are abstracted and counted as an incidence case when data are analyzed.");
        rule.getNotes().add("Follow the rules and abstract as multiple primaries when the physician states the invasive tumor is a recurrence or disease progression. It is disease progression, but the invasive must be reported for two reasons:");
        rule.getNotes().add("  • So the disease will be counted as an incidence case AND");
        rule.getNotes().add("  • Mortality data will not show a cause of death as in situ disease");
        rule.getNotes().add("The invasive and in situ must be");
        rule.getNotes().add("  • The same ICD-O histology/morphology code XXXX OR");
        rule.getNotes().add("  • NOS and subtype/variant of the NOS");
        rule.getNotes().add("The rules are hierarchical so both the invasive and in situ must be in the same primary site");
        _rules.add(rule);

        // Rule M11 - Abstract multiple primaries when there are multiple tumors with ICD-O histology codes that are different at the first Xxxx, second xXxx or third xxXx number.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M11");
        rule.setQuestion("");
        rule.setReason("");
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

