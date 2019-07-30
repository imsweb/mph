/*
 * Copyright (C) 2018 Information Management Services, Inc.
 */
package com.imsweb.mph.mpgroups;

import java.util.Arrays;
import java.util.Map;

import com.imsweb.mph.MphComputeOptions;
import com.imsweb.mph.MphConstants;
import com.imsweb.mph.MphGroup;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphUtils;
import com.imsweb.mph.MphUtils.MpResult;
import com.imsweb.mph.internal.TempRuleResult;
import com.imsweb.mph.mprules.MpRuleFiveYearsApart;
import com.imsweb.mph.mprules.MpRuleInsituAfterInvasive;
import com.imsweb.mph.mprules.MpRuleInvasiveAfterInsituGreaterThan60Days;
import com.imsweb.mph.mprules.MpRuleInvasiveAfterInsituLessThan60Days;
import com.imsweb.mph.mprules.MpRuleNoCriteriaSatisfied;
import com.imsweb.mph.mprules.MpRulePrimarySite;

public class Mp2018HeadAndNeckGroup extends MphGroup {

    // Head and Neck Histology Coding Rules
    // C000-C148, C300-C339, C410, C411, C442, C479
    // (Excludes lymphoma and leukemia M9590 – M9992 and Kaposi sarcoma M9140)
    public Mp2018HeadAndNeckGroup() {
        super(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, MphConstants.MP_2018_HEAD_AND_NECK_GROUP_NAME, "C000-C148, C300-C339, C410, C411, C442, C479", null, null,
                "9590-9992, 9140", "2-3,6", "2018-9999");

        // Rule M3 Abstract multiple primaries when there are separate/non-contiguous tumors in any two of the following sites:
        //   • Hard palate C050 AND/OR soft palate C051 AND/OR uvula C052
        //   • Maxillary sinus C310 AND/OR ethmoid sinus C311 AND/OR frontal sinus C312 AND/OR sphenoid sinus C313
        //   • Nasal cavity C300 AND middle ear C301
        //   • Submandibular gland C080 AND sublingual gland C081
        //   • Upper gum C030 AND lower gum C031
        //   • Upper lip C000 or C003 AND lower lip C001 or C004
        MphRule rule = new MphRule(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M3") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                String s1 = i1.getPrimarySite(), s2 = i2.getPrimarySite();
                //   • Upper lip C000 or C003 AND lower lip C001 or C004
                if (GroupUtility.differentCategory(s1, s2, MphConstants.UPPER_LIP, MphConstants.LOWER_LIP))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    //   • Upper gum C030 AND lower gum C031
                else if (GroupUtility.differentCategory(s1, s2, MphConstants.UPPER_GUM, MphConstants.LOWER_GUM))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    //   • Nasal cavity C300 AND middle ear C301
                else if (GroupUtility.differentCategory(s1, s2, MphConstants.NASAL_CAVITY, MphConstants.MIDDLE_EAR))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    //   • Hard palate C050 AND/OR soft palate C051 AND/OR uvula C052
                else if ((GroupUtility.differentCategory(s1, s2, MphConstants.HARD_PALATE, MphConstants.SOFT_PALATE)) ||
                        (GroupUtility.differentCategory(s1, s2, MphConstants.HARD_PALATE, MphConstants.UVULA)) ||
                        (GroupUtility.differentCategory(s1, s2, MphConstants.SOFT_PALATE, MphConstants.UVULA)))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    //   • Maxillary sinus C310 AND/OR ethmoid sinus C311 AND/OR frontal sinus C312 AND/OR sphenoid sinus C313
                else if ((GroupUtility.differentCategory(s1, s2, MphConstants.MAXILLARY_SINUS, MphConstants.ETHMOID_SINUS)) ||
                        (GroupUtility.differentCategory(s1, s2, MphConstants.MAXILLARY_SINUS, MphConstants.FRONTAL_SINUS)) ||
                        (GroupUtility.differentCategory(s1, s2, MphConstants.MAXILLARY_SINUS, MphConstants.SPHENOID_SINUS)) ||
                        (GroupUtility.differentCategory(s1, s2, MphConstants.ETHMOID_SINUS, MphConstants.FRONTAL_SINUS)) ||
                        (GroupUtility.differentCategory(s1, s2, MphConstants.ETHMOID_SINUS, MphConstants.SPHENOID_SINUS)) ||
                        (GroupUtility.differentCategory(s1, s2, MphConstants.FRONTAL_SINUS, MphConstants.SPHENOID_SINUS)))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    //   • Submandibular gland C080 AND sublingual gland C081
                else if (GroupUtility.differentCategory(s1, s2, MphConstants.SUBMANDIBULAR_GLAND, MphConstants.SUBLINGUAL_GLAND))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    //Glottis C320 AND/OR supraglottis C321 AND/OR subglottis C322 AND/OR laryngeal cartilage C323
                else if (MphConstants.GLOTTIS_AND_LARYNGEAL_SITES.containsAll(Arrays.asList(s1, s2)) && !s1.equals(s2))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    //Maxilla C410 AND Mandible C411
                else if (GroupUtility.differentCategory(s1, s2, MphConstants.MAXILLA, MphConstants.MANDIBLE))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    // Postcricoid C130 AND/OR hypopharyngeal aspect of aryepiglottic fold C131 AND/OR posterior wall of hypopharynx C132
                else if ((GroupUtility.differentCategory(s1, s2, MphConstants.POSTCRICOID, MphConstants.HYPOPHARYNGEAL_ASPECT_OF_ARYEPIGLOTTIC_FOLD)) ||
                        (GroupUtility.differentCategory(s1, s2, MphConstants.POSTCRICOID, MphConstants.POSTERIOR_WALL_OF_HYPOPHARYNX)) ||
                        (GroupUtility.differentCategory(s1, s2, MphConstants.HYPOPHARYNGEAL_ASPECT_OF_ARYEPIGLOTTIC_FOLD, MphConstants.POSTERIOR_WALL_OF_HYPOPHARYNX)))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Are there tumors on:\n" +
                "Glottis C320 AND/OR supraglottis C321 AND/OR subglottis C322 AND/OR laryngeal cartilage C323, \n" +
                "Hard palate C050 AND/OR soft palate C051 AND/OR uvula C052, \n" +
                "Maxilla C410 AND Mandible C411, \n" +
                "Maxillary sinus C310 AND/OR ethmoid sinus C311 AND/OR frontal sinus C312 AND/OR sphenoid sinus C313, \n" +
                "Nasal cavity C300 AND middle ear C301, \n" +
                "Postcricoid C130 AND/OR hypopharyngeal aspect of aryepiglottic fold C131 AND/OR posterior wall of hypopharynx C132, \n" +
                "Submandibular gland C080 AND sublingual gland C081, \n" +
                "Upper gum C030 AND lower gum C031, \n" +
                "Upper lip C000 or C003 AND lower lip C001 or C004?");
        rule.setReason("Tumors on:\n" +
                "Glottis C320 AND/OR supraglottis C321 AND/OR subglottis C322 AND/OR laryngeal cartilage C323, \n" +
                "Hard palate C050 AND/OR soft palate C051 AND/OR uvula C052, \n" +
                "Maxilla C410 AND Mandible C411, \n" +
                "Maxillary sinus C310 AND/OR ethmoid sinus C311 AND/OR frontal sinus C312 AND/OR sphenoid sinus C313, \n" +
                "Nasal cavity C300 AND middle ear C301, \n" +
                "Postcricoid C130 AND/OR hypopharyngeal aspect of aryepiglottic fold C131 AND/OR posterior wall of hypopharynx C132, \n" +
                "Submandibular gland C080 AND sublingual gland C081, \n" +
                "Upper gum C030 AND lower gum C031, \n" +
                "Upper lip C000 or C003 AND lower lip C001 or C004, are multiple primaries.");
        rule.getNotes().add("Use this rule only for multiple tumors.");
        rule.getNotes().add("Timing is irrelevant.");
        rule.getNotes().add("Histology is irrelevant.");
        rule.getNotes().add("These primary sites differ at the fourth character of the site code CxxX. Use this rule ONLY for the primary sites listed.");
        _rules.add(rule);

        // Rule M4 Abstract multiple primaries when separate/non-contiguous tumors are present in sites with ICD-O site codes that differ at the second CXxx, and/or third characters CxXx.
        rule = new MpRulePrimarySite(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M4");
        rule.getNotes().add("Use this rule only for multiple tumors.");
        rule.getNotes().add("Timing is irrelevant.");
        rule.getNotes().add("Histology is irrelevant.");
        _rules.add(rule);

        // Rule M5 Abstract multiple primaries when there are separate/non-contiguous tumors on both the right side and the left side of a paired site.
        rule = new MphRule(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M5") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (GroupUtility.isPairedSites(i1.getPrimarySite(), i2.getPrimarySite(), MphConstants.HEAD_AND_NECK_2018_PAIRED_SITES)) {
                    if (!GroupUtility.validPairedSiteLaterality(i1.getLaterality(), i2.getLaterality())) {
                        result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                        result.setMessageUnknownLaterality(this.getStep(), this.getGroupId());
                    }
                    else if (GroupUtility.areOppositeSides(i1.getLaterality(), i2.getLaterality()))
                        result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                }
                return result;
            }
        };
        rule.setQuestion("Are there separate, non-contiguous tumors on both the right side and the left side of a paired site?");
        rule.setReason("Separate, non-contiguous tumors on the right side and the left side of a paired site are multiple primaries.");
        rule.getNotes().add("See Table 10 for a list of paired sites.");
        rule.getNotes().add("Use this rule only for multiple tumors.");
        rule.getNotes().add("Timing is irrelevant.");
        rule.getNotes().add("Histology is irrelevant.");
        _rules.add(rule);

        // Rule M6 Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than five years after the original diagnosis or last recurrence.
        rule = new MpRuleFiveYearsApart(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M6");
        rule.getNotes().add("Clinically disease-free means that there was no evidence of recurrence on follow-up.");
        rule.getNotes().add("  • Scopes are NED");
        rule.getNotes().add("  • Scans are NED");
        rule.getNotes().add("  • Biomarkers are NED");
        rule.getNotes().add(
                "When there is a recurrence less than or equal to five years of diagnosis, the “clock” starts over. The time interval is calculated from the date of last recurrence. In other words, the patient must have been disease-free for greater than five years from the date of the last recurrence.");
        rule.getNotes().add("When it is unknown/not documented whether the patient had a recurrence, default to date of diagnosis to compute the time interval.");
        rule.getNotes().add(
                "The physician may state this is a recurrence, meaning the patient had a previous head and neck tumor and now has another head and neck tumor. Follow the rules; do not attempt to interpret the physician’s statement.");
        _rules.add(rule);

        // Rule M7 Abstract multiple primaries when separate, non-contiguous tumors are two or more different subtypes/variants in Column 3 of the appropriate site table (Tables 1-9) in the Equivalent Terms and Definitions. Timing is irrelevant.
        rule = new MphRule(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M7") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                Map<String, String> map1 = MphConstants.HEAD_AND_NECK_2018_SUBTYPES_FOR_SITE.get(i1.getPrimarySite());
                Map<String, String> map2 = MphConstants.HEAD_AND_NECK_2018_SUBTYPES_FOR_SITE.get(i2.getPrimarySite());
                if (map1 == null || map2 == null) {
                    result.setFinalResult(MpResult.QUESTIONABLE);
                    result.setMessage("Two separate lesions of lip is rare; no histology tables exist for lip in Terms and Definitions.");
                }
                else if (!map1.equals(map2))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                else {
                    String h1 = i1.getHistology(), icd1 = h1 + "/" + i1.getBehavior(), h2 = i2.getHistology(), icd2 = h2 + "/" + i2.getBehavior();
                    String subtype1 = map1.containsKey(h1) ? map1.get(h1) : map1.get(icd1);
                    String subtype2 = map2.containsKey(h2) ? map2.get(h2) : map2.get(icd2);
                    if (subtype1 != null && subtype2 != null && !subtype1.equals(subtype2))
                        result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                }
                return result;
            }
        };
        rule.setQuestion(
                "Are separate, non-contiguous tumors are two or more different subtypes/variants in Column 3 of the appropriate site table (Tables 1-9) in the Equivalent Terms and Definitions?");
        rule.setReason(
                "Separate, non-contiguous tumors are two or more different subtypes/variants in Column 3 of the appropriate site table (Tables 1-9) in the Equivalent Terms and Definitions are multiple primaries.");
        rule.getNotes().add("The tumors may be subtypes/variants of the same or different NOS histologies.");
        rule.getNotes().add(
                "  • Same NOS: Alveolar rhabdomyosarcoma 8920/3 and embryonal rhabdomyosarcoma 8910/3 are both subtypes of rhabdomyosarcoma 8900/3 but are distinctly different histologies. Abstract multiple primaries.");
        rule.getNotes().add(
                "  • Different NOS: Colloid-type adenocarcinoma 8144 is a subtype of adenocarcinoma NOS 8140; Sarcomatoid carcinoma 8074 is a subtype of squamous cell carcinoma 8070. They are distinctly different histologies. Abstract multiple primaries.");
        _rules.add(rule);

        // Rule M8 Abstract multiple primaries when separate, non-contiguous tumors are on different rows in the appropriate site table (Tables 1-9) in the Equivalent Terms and Definitions. Timing is irrelevant.
        rule = new MphRule(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M8") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                Map<String, String> map1 = MphConstants.HEAD_AND_NECK_2018_TABLE_FOR_SITE.get(i1.getPrimarySite());
                Map<String, String> map2 = MphConstants.HEAD_AND_NECK_2018_TABLE_FOR_SITE.get(i2.getPrimarySite());
                if (map1 == null || map2 == null) {
                    result.setFinalResult(MpResult.QUESTIONABLE);
                    result.setMessage("Two separate lesions of lip is rare; no histology tables exist for lip in Terms and Definitions.");
                }
                else if (!map1.equals(map2))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                else {
                    String h1 = i1.getHistology(), icd1 = h1 + "/" + i1.getBehavior(), h2 = i2.getHistology(), icd2 = h2 + "/" + i2.getBehavior();
                    String row1 = map1.containsKey(h1) ? map1.get(h1) : map1.get(icd1);
                    String row2 = map2.containsKey(h2) ? map2.get(h2) : map2.get(icd2);
                    if (row1 == null || row2 == null) {
                        result.setFinalResult(MpResult.QUESTIONABLE);
                        result.setMessageNotInTable(this.getStep(), this.getGroupId());
                    }
                    else if (!row1.equals(row2))
                        result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                }
                return result;
            }
        };
        rule.setQuestion("Are separate, non-contiguous tumors on different rows in the appropriate site table (Tables 1-9) in the Equivalent Terms and Definitions?");
        rule.setReason("Separate, non-contiguous tumors on different rows in the appropriate site table (Tables 1-9) in the Equivalent Terms and Definitions is multiple primaries.");
        rule.getNotes().add("Each row in the table is a distinctly different histology.");
        _rules.add(rule);

        // Rule M9 Abstract a single primary (the invasive) when an in situ tumor is diagnosed after an invasive tumor in the same primary site.
        rule = new MpRuleInsituAfterInvasive(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M9");
        rule.getNotes().add("The rules are hierarchical. Only use this rule when none of the previous rules apply.");
        rule.getNotes().add("The tumors may be a NOS and a subtype/variant of that NOS. See Tables 1-9 in the Equivalent Terms and Definitions for listings of NOS and subtype/variants.");
        rule.getNotes().add("The in situ is recorded as a recurrence for those registrars who collect recurrence data.");
        _rules.add(rule);

        // Rule M10 Abstract a single primary (the invasive) when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor in the same primary site.
        rule = new MpRuleInvasiveAfterInsituLessThan60Days(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M10");
        rule.getNotes().add("The rules are hierarchical. Only use this rule when none of the previous rules apply.");
        rule.getNotes().add("The tumors may be an NOS and a subtype/variant of that NOS");
        rule.getNotes().add("When the case has been abstracted, change behavior code on original abstract from /2 to /3. Do not change date of diagnosis.");
        rule.getNotes().add("If the case has already been submitted to the central registry, report all changes.");
        rule.getNotes().add(
                "The physician may stage both tumors because staging and determining multiple primaries are done for different reasons. Staging determines which treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).");
        rule.getNotes().add("See the COC and SEER manuals for instructions on coding other data items such as Date of Diagnosis, Accession Year and Sequence Number.");
        _rules.add(rule);

        // Rule M11 Abstract multiple primaries when an invasive tumor occurs more than 60 days after an in situ tumor.
        rule = new MpRuleInvasiveAfterInsituGreaterThan60Days(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M11");
        rule.getNotes().add("The rules are hierarchical. Only use this rule when none of the previous rules apply.");
        rule.getNotes().add("Abstract both the invasive and in situ tumors.");
        rule.getNotes().add("Abstract as multiple primaries even if physician states the invasive tumor is disease recurrence or progression.");
        rule.getNotes().add(
                "This rule is based on long-term epidemiologic studies of recurrence intervals. The specialty medical experts (SMEs) reviewed and approved these rules.  Many of the SMEs were also authors, co-authors, or editors of the AJCC Staging Manual.");
        _rules.add(rule);

        // Rule M12 Abstract a single primary when separate/non-contiguous tumors in the same primary site are on the same row in the appropriate site table (Tables 1-9) in the Equivalent Terms and Definitions. Timing is irrelevant.
        rule = new MphRule(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M12") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                Map<String, String> map1 = MphConstants.HEAD_AND_NECK_2018_TABLE_FOR_SITE.get(i1.getPrimarySite());
                Map<String, String> map2 = MphConstants.HEAD_AND_NECK_2018_TABLE_FOR_SITE.get(i2.getPrimarySite());
                if (map1 == null || map2 == null) {
                    result.setFinalResult(MpResult.QUESTIONABLE);
                    result.setMessage("Two separate lesions of lip is rare; no histology tables exist for lip in Terms and Definitions.");
                }
                else if (map1.equals(map2)) {
                    String h1 = i1.getHistology(), icd1 = h1 + "/" + i1.getBehavior(), h2 = i2.getHistology(), icd2 = h2 + "/" + i2.getBehavior();
                    String row1 = map1.containsKey(h1) ? map1.get(h1) : map1.get(icd1);
                    String row2 = map2.containsKey(h2) ? map2.get(h2) : map2.get(icd2);
                    if (row1 == null || row2 == null) {
                        result.setFinalResult(MpResult.QUESTIONABLE);
                        result.setMessageNotInTable(this.getStep(), this.getGroupId());
                    }
                    else if (row1.equals(row2))
                        result.setFinalResult(MpResult.SINGLE_PRIMARY);
                }
                return result;
            }
        };
        rule.setQuestion("Are separate, non-contiguous tumors in the same primary site and on the same row in the appropriate site table (Tables 1-9) in the Equivalent Terms and Definitions?");
        rule.setReason(
                "Separate, non-contiguous tumors in the same primary site and on the same row in the appropriate site table (Tables 1-9) in the Equivalent Terms and Definitions is a single primary.");
        rule.getNotes().add("The same row means the tumors are:");
        rule.getNotes().add("  • The same histology (same four-digit ICD-O code) OR");
        rule.getNotes().add("  • One is the preferred term (column 1) and the other is a synonym for the preferred term (column 2) OR");
        rule.getNotes().add("  • A NOS (column 1/column 2) and the other is a subtype/variant of that NOS (column 3)");
        _rules.add(rule);

        // Rule M13 Abstract a single primary  when none of the previous rules apply.
        rule = new MpRuleNoCriteriaSatisfied(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M13");
        rule.getNotes().add("Use caution when applying this default rule. Please confirm that you have not overlooked an applicable rule.");
        _rules.add(rule);
    }
}

