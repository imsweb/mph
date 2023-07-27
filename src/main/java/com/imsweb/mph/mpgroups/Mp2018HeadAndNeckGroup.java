/*
 * Copyright (C) 2018 Information Management Services, Inc.
 */
package com.imsweb.mph.mpgroups;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;

import com.imsweb.mph.MphConstants;
import com.imsweb.mph.MphGroup;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphUtils;
import com.imsweb.mph.MphUtils.MpResult;
import com.imsweb.mph.RuleExecutionContext;
import com.imsweb.mph.internal.TempRuleResult;
import com.imsweb.mph.mprules.MpRuleInsituAfterInvasive;
import com.imsweb.mph.mprules.MpRuleInvasiveAfterInsituGreaterThan60Days;
import com.imsweb.mph.mprules.MpRuleInvasiveAfterInsituLessThan60Days;
import com.imsweb.mph.mprules.MpRuleNoCriteriaSatisfied;
import com.imsweb.mph.mprules.MpRulePrimarySite;
import com.imsweb.mph.mprules.MpRuleYearsApart;

import static com.imsweb.mph.MphConstants.AND_CONNECTOR;
import static com.imsweb.mph.MphConstants.HIERARCHICAL_RULES;
import static com.imsweb.mph.MphConstants.HISTOLOGY_IS_IRRELEVANT;
import static com.imsweb.mph.MphConstants.TIMING_IS_IRRELEVANT;
import static com.imsweb.mph.MphConstants.USE_FOR_MULTIPLE_RULES;

//S3776 - Cognitive Complexity of methods should not be too high => some of the rules are complicated by definition
@SuppressWarnings("java:S3776")
public class Mp2018HeadAndNeckGroup extends MphGroup {

    // Head and Neck
    // C000-C148, C300-C339, C410, C411, C442, C479
    // (Excludes lymphoma and leukemia M9590 – M9992 and Kaposi sarcoma M9140)
    public Mp2018HeadAndNeckGroup() {
        super(MphConstants.STR_2018_AND_LATER_HEAD_AND_NECK, MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, "C000-C148, C300-C339, C410, C411, C479", null, null,
                "9590-9993, 9140", "2-3,6", "2018-9999");

        // Rule M3 Abstract multiple primaries when there are separate/non-contiguous tumors in any two of the following sites:
        //   - Hard palate C050 AND/OR soft palate C051 AND/OR uvula C052
        //   - Maxillary sinus C310 AND/OR ethmoid sinus C311 AND/OR frontal sinus C312 AND/OR sphenoid sinus C313
        //   - Nasal cavity C300 AND middle ear C301
        //   - Submandibular gland C080 AND sublingual gland C081
        //   - Upper gum C030 AND lower gum C031
        //   - Upper lip C000 or C003 AND lower lip C001 or C004
        MphRule rule = new MphRule(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, "M3") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
                TempRuleResult result = new TempRuleResult();
                String s1 = i1.getPrimarySite();
                String s2 = i2.getPrimarySite();
                if (GroupUtility.differentCategory(s1, s2, MphConstants.UPPER_LIP, MphConstants.LOWER_LIP) || GroupUtility.differentCategory(s1, s2, MphConstants.UPPER_GUM, MphConstants.LOWER_GUM)
                        || GroupUtility.differentCategory(s1, s2, MphConstants.NASAL_CAVITY, MphConstants.MIDDLE_EAR) || GroupUtility.differentCategory(s1, s2, MphConstants.HARD_PALATE,
                        MphConstants.SOFT_PALATE) || GroupUtility.differentCategory(s1, s2, MphConstants.HARD_PALATE, MphConstants.UVULA) || GroupUtility.differentCategory(s1, s2,
                        MphConstants.SOFT_PALATE, MphConstants.UVULA) || (GroupUtility.differentCategory(s1, s2, MphConstants.MAXILLARY_SINUS, MphConstants.ETHMOID_SINUS)) ||
                        (GroupUtility.differentCategory(s1, s2, MphConstants.MAXILLARY_SINUS, MphConstants.FRONTAL_SINUS)) ||
                        (GroupUtility.differentCategory(s1, s2, MphConstants.MAXILLARY_SINUS, MphConstants.SPHENOID_SINUS)) ||
                        (GroupUtility.differentCategory(s1, s2, MphConstants.ETHMOID_SINUS, MphConstants.FRONTAL_SINUS)) ||
                        (GroupUtility.differentCategory(s1, s2, MphConstants.ETHMOID_SINUS, MphConstants.SPHENOID_SINUS)) ||
                        (GroupUtility.differentCategory(s1, s2, MphConstants.FRONTAL_SINUS, MphConstants.SPHENOID_SINUS)) || GroupUtility.differentCategory(s1, s2, MphConstants.SUBMANDIBULAR_GLAND,
                        MphConstants.SUBLINGUAL_GLAND) || (new HashSet<>(MphConstants.GLOTTIS_AND_LARYNGEAL_SITES).containsAll(Arrays.asList(s1, s2)) && !s1.equals(s2))
                        || GroupUtility.differentCategory(s1, s2,
                        MphConstants.MAXILLA, MphConstants.MANDIBLE) || (GroupUtility.differentCategory(s1, s2, MphConstants.POSTCRICOID, MphConstants.HYPOPHARYNGEAL_ASPECT_OF_ARYEPIGLOTTIC_FOLD)) ||
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
        rule.getNotes().add(USE_FOR_MULTIPLE_RULES);
        rule.getNotes().add(TIMING_IS_IRRELEVANT);
        rule.getNotes().add(HISTOLOGY_IS_IRRELEVANT);
        rule.getNotes().add("These primary sites differ at the fourth character of the site code CxxX. Use this rule ONLY for the primary sites listed.");
        _rules.add(rule);

        // Rule M4 Abstract multiple primaries when separate/non-contiguous tumors are present in sites with ICD-O site codes that differ at the second CXxx, and/or third characters CxXx.
        rule = new MpRulePrimarySite(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, "M4");
        rule.getNotes().add(USE_FOR_MULTIPLE_RULES);
        rule.getNotes().add(TIMING_IS_IRRELEVANT);
        rule.getNotes().add(HISTOLOGY_IS_IRRELEVANT);
        _rules.add(rule);

        // Rule M5 Abstract multiple primaries when there are separate/non-contiguous tumors on both the right side and the left side of a paired site.
        rule = new MphRule(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, "M5") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
                TempRuleResult result = new TempRuleResult();
                if (GroupUtility.isPairedSites(i1.getPrimarySite(), i2.getPrimarySite(), MphConstants.HEAD_AND_NECK_2018_PAIRED_SITES)) {
                    if (!GroupUtility.validPairedSiteLaterality(i1.getLaterality(), i2.getLaterality())) {
                        result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                        result.setMessageUnknownLaterality(this.getStep(), this.getGroupName());
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
        rule.getNotes().add(USE_FOR_MULTIPLE_RULES);
        rule.getNotes().add(TIMING_IS_IRRELEVANT);
        rule.getNotes().add(HISTOLOGY_IS_IRRELEVANT);
        _rules.add(rule);

        // Rule M6 Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than five years after the original diagnosis or last recurrence.
        rule = new MpRuleYearsApart(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, "M6", 5);
        rule.getNotes().add("Clinically disease-free means that there was no evidence of recurrence on follow-up.");
        rule.getNotes().add("  - Scopes are NED");
        rule.getNotes().add("  - Scans are NED");
        rule.getNotes().add("  - Biomarkers are NED");
        rule.getNotes().add(
                "When there is a recurrence less than or equal to five years of diagnosis, the \"clock\" starts over. The time interval is calculated from the date of last recurrence. In other words, the patient must have been disease-free for greater than five years from the date of the last recurrence.");
        rule.getNotes().add("When it is unknown/not documented whether the patient had a recurrence, default to date of diagnosis to compute the time interval.");
        rule.getNotes().add(
                "The physician may state this is a recurrence, meaning the patient had a previous head and neck tumor and now has another head and neck tumor. Follow the rules; do not attempt to interpret the physician’s statement.");
        _rules.add(rule);

        // Rule M7 Abstract multiple primaries when separate, non-contiguous tumors are two or more different subtypes/variants in Column 3 of the appropriate site table (Tables 1-9) in the Equivalent Terms and Definitions. Timing is irrelevant.
        rule = new MphRule(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, "M7") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
                TempRuleResult result = new TempRuleResult();
                //Check if the two tumors are in the tables first
                Map<String, String> map1 = MphConstants.HEAD_AND_NECK_2018_TABLE_FOR_SITE.get(i1.getPrimarySite());
                Map<String, String> map2 = MphConstants.HEAD_AND_NECK_2018_TABLE_FOR_SITE.get(i2.getPrimarySite());

                //If there is no table for the primary site
                if (map1 == null) {
                    result.setFinalResult(MpResult.QUESTIONABLE);
                    result.setMessage("No histology table exists for " + i1.getPrimarySite() + " in Terms and Definitions.");
                    return result;
                }
                else if (map2 == null) {
                    result.setFinalResult(MpResult.QUESTIONABLE);
                    result.setMessage("No histology table exists for " + i2.getPrimarySite() + " in Terms and Definitions.");
                    return result;
                }
                //If the two sites have two different tables, use the next rule
                else if (!map1.equals(map2))
                    return result;
                else {
                    //If there is a table for the site, lets check if the histology is in that table
                    String h1 = i1.getHistology();
                    String icd1 = i1.getIcdCode();
                    String h2 = i2.getHistology();
                    String icd2 = i2.getIcdCode();
                    String row1 = map1.containsKey(h1) ? map1.get(h1) : map1.get(icd1);
                    String row2 = map2.containsKey(h2) ? map2.get(h2) : map2.get(icd2);
                    if (row1 == null || row2 == null) {
                        //if the two histologies are same, skip this rule even if the histology is not in the table
                        if (!h1.equals(h2)) {
                            result.setFinalResult(MpResult.QUESTIONABLE);
                            result.setMessageNotInTable(this.getStep(), this.getGroupName(), row1, row2, icd1, icd2);
                        }
                        return result;
                    }
                }

                map1 = MphConstants.HEAD_AND_NECK_2018_SUBTYPES_FOR_SITE.get(i1.getPrimarySite());
                map2 = MphConstants.HEAD_AND_NECK_2018_SUBTYPES_FOR_SITE.get(i2.getPrimarySite());
                if (map1 != null && map1.equals(map2)) {
                    String h1 = i1.getHistology();
                    String icd1 = i1.getIcdCode();
                    String h2 = i2.getHistology();
                    String icd2 = i2.getIcdCode();
                    String subtype1 = map1.containsKey(h1) ? map1.get(h1) : map1.get(icd1);
                    String subtype2 = map2.containsKey(h2) ? map2.get(h2) : map2.get(icd2);
                    if (subtype1 != null && subtype2 != null && !subtype1.contains(subtype2) && !subtype2.contains(subtype1))
                        result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                }

                return result;
            }
        };
        rule.setQuestion(
                "Are separate, non-contiguous tumors different subtypes/variants in Column 3 of the appropriate site table (Tables 1-9) in the Equivalent Terms and Definitions?");
        rule.setReason(
                "Separate, non-contiguous tumors are different subtypes/variants in Column 3 of the appropriate site table (Tables 1-9) in the Equivalent Terms and Definitions are multiple primaries.");
        rule.getNotes().add("The tumors may be subtypes/variants of the same or different NOS histologies.");
        rule.getNotes().add(
                "  - Same NOS: Alveolar rhabdomyosarcoma 8920/3 and embryonal rhabdomyosarcoma 8910/3 are both subtypes of rhabdomyosarcoma 8900/3 but are distinctly different histologies. Abstract multiple primaries.");
        rule.getNotes().add(
                "  - Different NOS: Colloid-type adenocarcinoma 8144 is a subtype of adenocarcinoma NOS 8140; Sarcomatoid carcinoma 8074 is a subtype of squamous cell carcinoma 8070. They are distinctly different histologies. Abstract multiple primaries.");
        _rules.add(rule);

        // Rule M8 Abstract multiple primaries when separate, non-contiguous tumors are on different rows in the appropriate site table (Tables 1-9) in the Equivalent Terms and Definitions. Timing is irrelevant.
        rule = new MphRule(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, "M8") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
                TempRuleResult result = new TempRuleResult();
                Map<String, String> map1 = MphConstants.HEAD_AND_NECK_2018_TABLE_FOR_SITE.get(i1.getPrimarySite());
                Map<String, String> map2 = MphConstants.HEAD_AND_NECK_2018_TABLE_FOR_SITE.get(i2.getPrimarySite());
                if (!map1.equals(map2))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                else {
                    String h1 = i1.getHistology();
                    String icd1 = i1.getIcdCode();
                    String h2 = i2.getHistology();
                    String icd2 = i2.getIcdCode();
                    //Special case for 8690 and 8693 of Table 9
                    if (new HashSet<>(MphConstants.HEAD_AND_NECK_2018_TABLE9_SITES).containsAll(Arrays.asList(i1.getPrimarySite(), i2.getPrimarySite())) && GroupUtility.differentCategory(h1, h2,
                            Collections.singletonList("8690"), Arrays.asList("8690", "8693"))) {
                        result.setFinalResult(MpResult.QUESTIONABLE);
                        result.setMessage("Manual review is required for " + h1 + AND_CONNECTOR + h2 + " of Table 9.");
                        return result;
                    }
                    String row1 = map1.containsKey(h1) ? map1.get(h1) : map1.get(icd1);
                    String row2 = map2.containsKey(h2) ? map2.get(h2) : map2.get(icd2);
                    if (row1 != null && row2 != null && !row1.contains(row2) && !row2.contains(row1))
                        result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                }
                return result;
            }
        };
        rule.setQuestion("Are separate, non-contiguous tumors on different rows in the appropriate site table (Tables 1-9) in the Equivalent Terms and Definitions?");
        rule.setReason("Separate, non-contiguous tumors on different rows in the appropriate site table (Tables 1-9) in the Equivalent Terms and Definitions are multiple primaries.");
        rule.getNotes().add("Each row in the table is a distinctly different histology.");
        _rules.add(rule);

        // Rule M9 Abstract a single primary (the invasive) when an in situ tumor is diagnosed after an invasive tumor in the same primary site.
        rule = new MpRuleInsituAfterInvasive(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, "M9");
        rule.getNotes().add(HIERARCHICAL_RULES);
        rule.getNotes().add("The tumors may be a NOS and a subtype/variant of that NOS. See Tables 1-9 in the Equivalent Terms and Definitions for listings of NOS and subtype/variants.");
        rule.getNotes().add("The in situ is recorded as a recurrence for those registrars who collect recurrence data.");
        _rules.add(rule);

        // Rule M10 Abstract a single primary (the invasive) when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor in the same primary site.
        rule = new MpRuleInvasiveAfterInsituLessThan60Days(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, "M10");
        rule.getNotes().add(HIERARCHICAL_RULES);
        rule.getNotes().add("The tumors may be an NOS and a subtype/variant of that NOS");
        rule.getNotes().add("When the case has been abstracted, change behavior code on original abstract from /2 to /3. Do not change date of diagnosis.");
        rule.getNotes().add("If the case has already been submitted to the central registry, report all changes.");
        rule.getNotes().add(
                "The physician may stage both tumors because staging and determining multiple primaries are done for different reasons. Staging determines which treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).");
        rule.getNotes().add("See the COC and SEER manuals for instructions on coding other data items such as Date of Diagnosis, Accession Year and Sequence Number.");
        _rules.add(rule);

        // Rule M11 Abstract multiple primaries when an invasive tumor occurs more than 60 days after an in situ tumor.
        rule = new MpRuleInvasiveAfterInsituGreaterThan60Days(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, "M11");
        rule.getNotes().add(HIERARCHICAL_RULES);
        rule.getNotes().add("Abstract both the invasive and in situ tumors.");
        rule.getNotes().add("Abstract as multiple primaries even if physician states the invasive tumor is disease recurrence or progression.");
        rule.getNotes().add(
                "This rule is based on long-term epidemiologic studies of recurrence intervals. The specialty medical experts (SMEs) reviewed and approved these rules.  Many of the SMEs were also authors, co-authors, or editors of the AJCC Staging Manual.");
        _rules.add(rule);

        // Rule M12 Abstract a single primary when separate/non-contiguous tumors in the same primary site are on the same row in the appropriate site table (Tables 1-9) in the Equivalent Terms and Definitions. Timing is irrelevant.
        rule = new MphRule(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, "M12") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
                TempRuleResult result = new TempRuleResult();
                String h1 = i1.getHistology();
                String icd1 = i1.getIcdCode();
                String h2 = i2.getHistology();
                String icd2 = i2.getIcdCode();
                Map<String, String> map1 = MphConstants.HEAD_AND_NECK_2018_TABLE_FOR_SITE.get(i1.getPrimarySite());
                Map<String, String> map2 = MphConstants.HEAD_AND_NECK_2018_TABLE_FOR_SITE.get(i2.getPrimarySite());
                if (map1 == null || map2 == null) {
                    result.setFinalResult(MpResult.QUESTIONABLE);
                    result.setMessage("Two separate lesions of lip is rare; no histology tables exist for lip in Terms and Definitions.");
                }
                else if (map1.equals(map2)) {
                    String row1 = map1.containsKey(h1) ? map1.get(h1) : map1.get(icd1);
                    String row2 = map2.containsKey(h2) ? map2.get(h2) : map2.get(icd2);
                    if (row1 == null || row2 == null) {
                        if (icd1.equals(icd2))
                            result.setFinalResult(MpResult.SINGLE_PRIMARY);
                        else {
                            result.setFinalResult(MpResult.QUESTIONABLE);
                            result.setMessageNotInTable(this.getStep(), this.getGroupName(), row1, row2, icd1, icd2);
                        }
                    }
                    else if (row1.contains(row2) || row2.contains(row1))
                        result.setFinalResult(MpResult.SINGLE_PRIMARY);
                }
                return result;
            }
        };
        rule.setQuestion("Are separate, non-contiguous tumors in the same primary site and on the same row in the appropriate site table (Tables 1-9) in the Equivalent Terms and Definitions?");
        rule.setReason(
                "Separate, non-contiguous tumors in the same primary site and on the same row in the appropriate site table (Tables 1-9) in the Equivalent Terms and Definitions are a single primary.");
        rule.getNotes().add("The same row means the tumors are:");
        rule.getNotes().add("  - The same histology (same four-digit ICD-O code) OR");
        rule.getNotes().add("  - One is the preferred term (column 1) and the other is a synonym for the preferred term (column 2) OR");
        rule.getNotes().add("  - A NOS (column 1/column 2) and the other is a subtype/variant of that NOS (column 3)");
        _rules.add(rule);

        // Rule M13 Abstract a single primary  when none of the previous rules apply.
        rule = new MpRuleNoCriteriaSatisfied(MphConstants.SOLID_TUMOR_2018_HEAD_AND_NECK, "M13");
        rule.getNotes().add("Use caution when applying this default rule. Please confirm that you have not overlooked an applicable rule.");
        _rules.add(rule);
    }

    @Override
    public boolean isApplicable(String primarySite, String histology, String behavior, int year) {
        if (super.isApplicable(primarySite, histology, behavior, year))
            return true;
        return year >= 2019 && year < 2022 && ("C754".equals(primarySite) || "C755".equals(primarySite)) && Arrays.asList("8680", "8690", "8692", "8693").contains(histology) && "3".equals(behavior);
    }
}

