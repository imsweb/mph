/*
 * Copyright (C) 2018 Information Management Services, Inc.
 */
package com.imsweb.mph.mpgroups;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
    C000-C148, C300-C339, C410, C411, C442, C479
    (Excludes lymphoma and leukemia M9590 – M9992 and Kaposi sarcoma M9140)

    Rule M3	Abstract multiple primaries when there are separate, non-contiguous tumors on both the:
        •	Upper lip C000 or C003 AND lower lip C001 or C004 OR
        •	Upper gum C030 AND lower gum C031 OR
        •	Nasal cavity C300 AND middle ear C301
        Note 1:	Use this rule only for multiple tumors.
        Note 2:	Timing is irrelevant.
        Note 3:	Histology is irrelevant.
        Note 4:	These primary sites differ at the fourth digit of the site code CxxX. Use this rule ONLY for the primary sites listed.

    Rule M4	Abstract multiple primaries when separate, non-contiguous tumors are present in sites with ICD-O site codes that differ at the second CXxx, and/or third characters CxXx.
        Note 1:	Use this rule only for multiple tumors.
        Note 2:	Timing is irrelevant.
        Note 3:	Histology is irrelevant.

    Rule M5	Abstract multiple primaries when there are separate, non-contiguous tumors on both the right side and the left side of a paired site.
        Note 1:	See Table 11 for a list of paired sites.
        Note 2:	Use this rule only for multiple tumors.
        Note 3:	Timing is irrelevant.
        Note 4:	Histology is irrelevant.

    Rule M6	Abstract multiple primaries when separate, non-contiguous tumors are two or more different subtypes/variants in Column 3 of the appropriate site table (Tables 2-10) in the Equivalent Terms and Definitions. Timing is irrelevant.
        Note:	The tumors may be subtypes/variants of the same or different NOS histologies.
        •	Same NOS: Alveolar rhabdomyosarcoma 8920/3 and embryonal rhabdomyosarcoma 8910/3 are both subtypes of rhabdomyosarcoma 8900/3 but are distinctly different histologies. Abstract multiple primaries.
        •	Different NOS: Colloid-type adenocarcinoma 8144 is a subtype of adenocarcinoma NOS 8140; Sarcomatoid carcinoma 8074 is a subtype of squamous cell carcinoma 8070. They are distinctly different histologies. Abstract multiple primaries.

    Rule M7	Abstract multiple primaries when separate, non-contiguous tumors are on different rows in the appropriate site table (Tables 2-10) in the Equivalent Terms and Definitions. Timing is irrelevant.
        Note:	Each row in the table is a distinctly different histology.
     
    Rule M8	Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than five years after the original diagnosis or last recurrence.
        Note 1:	Clinically disease-free means that there was no evidence of recurrence on follow-up.
        •	Scopes are NED
        •	Scans are NED
        •	Biomarkers are NED
        Note 2:	The rules are hierarchical.  The tumors must be in the same site.
        Note 3:	When there is a recurrence less than or equal to five years of diagnosis, the “clock” starts over. The time interval is calculated from the date of last recurrence. In other words, the patient must have been disease-free for greater than five years from the date of the last recurrence.
        Note 4:	When it is unknown/not documented whether the patient had a recurrence, default to date of diagnosis to compute the time interval.
        Note 5:	The physician may state this is a recurrence, meaning the patient had a previous head and neck tumor and now has another head and neck tumor. Follow the rules; do not attempt to interpret the physician’s statement.

    Rule M9	Abstract a single primary (the invasive)when an in situ tumor is diagnosed after an invasive tumor.
        Note 1:	The rules are hierarchical. Only use this rule when none of the previous rules apply.
        Note 2:	The tumors may be a NOS and a subtype/variant of that NOS. See Tables 2-10 in the Equivalent Terms and Definitions for listings of NOS and subtype/variants.
        Note 3:	The in situ is recorded as a recurrence for those registrars who collect recurrence data.

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

    Rule M12	Abstract a single primary when separate, non-contiguous tumors are on the same row in the appropriate site table (Tables 2-10) in the Equivalent Terms and Definitions. Timing is irrelevant.
        Note:	The same row means the tumors are:
        •	The same histology (same four-digit ICD-O code) OR
        •	One is the preferred term (column 1) and the other is a synonym for the preferred term (column 2) OR
        •	A NOS (column 1/column 2) and the other is a subtype/variant of that NOS (column 3)

    Rule M13	Abstract a single primary  when none of the previous rules apply.
        Note: Use caution when applying this default rule. Please confirm that you have not overlooked an applicable rule.
    */

    // Head and Neck Multiple Primary Rules
    // C000-C148, C300-C339, C410, C411, C442, C479
    // (Excludes lymphoma and leukemia M9590 – M9992 and Kaposi sarcoma M9140)
    public Mp2018HeadAndNeckGroup() {
        super(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, MphConstants.MP_2018_HEAD_AND_NECK_GROUP_NAME, "C000-C148, C300-C339, C410, C411, C442, C479", null, null,
                "9590-9992, 9140", "2-3,6", "2018-9999");

        // Rule M3	Abstract multiple primaries when there are separate, non-contiguous tumors on both the:
        // •	Upper lip C000 or C003 AND lower lip C001 or C004 OR
        // •	Upper gum C030 AND lower gum C031 OR
        // •	Nasal cavity C300 AND middle ear C301
        MphRule rule = new MphRule(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M3") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (GroupUtility.differentCategory(i1.getPrimarySite(), i2.getPrimarySite(), MphConstants.UPPER_LIP, MphConstants.LOWER_LIP))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                else if (GroupUtility.differentCategory(i1.getPrimarySite(), i2.getPrimarySite(), MphConstants.UPPER_GUM, MphConstants.LOWER_GUM))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                else if (GroupUtility.differentCategory(i1.getPrimarySite(), i2.getPrimarySite(), MphConstants.NASAL_CAVITY, MphConstants.MIDDLE_EAR))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Are there tumors on the upper lip (C000, C003) and the lower lip (C001, C004), the upper gum (C030) and the lower gum (C031), or the nasal cavity (C300) and the middle ear (C301)?");
        rule.setReason("Tumors on the upper lip (C000, C003) and the lower lip (C001, C004), the upper gum (C030) and the lower gum (C031), or the nasal cavity (C300) and the middle ear (C301) are multiple primaries.");
        rule.getNotes().add("Use this rule only for multiple tumors.");
        rule.getNotes().add("Timing is irrelevant.");
        rule.getNotes().add("Histology is irrelevant.");
        rule.getNotes().add("These primary sites differ at the fourth digit of the site code CxxX. Use this rule ONLY for the primary sites listed.");
        _rules.add(rule);

        // Rule M4	Abstract multiple primaries when separate, non-contiguous tumors are present in sites with ICD-O site codes that differ at the second CXxx, and/or third characters CxXx.
        rule = new MphRulePrimarySiteCode(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M4");
        rule.getNotes().add("Use this rule only for multiple tumors.");
        rule.getNotes().add("Timing is irrelevant.");
        rule.getNotes().add("Histology is irrelevant.");
        _rules.add(rule);

        // Rule M5	Abstract multiple primaries when there are separate, non-contiguous tumors on both the right side and the left side of a paired site.
        rule = new MphRuleLeftAndRight(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M5", MphConstants.HEAD_AND_NECK_2018_PAIRED_SITES, null);
        rule.setQuestion("Are there separate, non-contiguous tumors on both the right side and the left side of a paired site?");
        rule.setReason("Separate, non-contiguous tumors on the right side and the left side of a paired site are multiple primaries.");
        rule.getNotes().add("See Table 11 for a list of paired sites.");
        rule.getNotes().add("Use this rule only for multiple tumors.");
        rule.getNotes().add("Timing is irrelevant.");
        rule.getNotes().add("Histology is irrelevant.");
        _rules.add(rule);

        // Rule M6	Abstract multiple primaries when separate, non-contiguous tumors are two or more different subtypes/variants in Column 3 of the appropriate site table (Tables 2-10) in the Equivalent Terms and Definitions. Timing is irrelevant.
        rule = new MphRule(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M6") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                List<String> list1 = MphConstants.HEAD_AND_NECK_2018_SUBTYPES_FOR_SITE.getOrDefault(i1.getPrimarySite(), null);
                List<String> list2 = MphConstants.HEAD_AND_NECK_2018_SUBTYPES_FOR_SITE.getOrDefault(i2.getPrimarySite(), null);

                if ((list1 != null) && (list2 != null)) {
                    String icd1 = i1.getHistology() + "/" + i1.getBehavior(), icd2 = i2.getHistology() + "/" + i2.getBehavior();

                    if (!i1.getHistology().equals(i2.getHistology())) {
                        boolean isPresent1 = list1.contains(icd1);
                        if (!isPresent1) isPresent1 = list1.contains(i1.getHistology());
                        boolean isPresent2 = list2.contains(icd2);
                        if (!isPresent2) isPresent2 = list2.contains(i2.getHistology());

                        if (isPresent1 && isPresent2) {
                            result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                        }
                    }
                }
                return result;
            }
        };
        rule.setQuestion("Are separate, non-contiguous tumors are two or more different subtypes/variants in Column 3 of the appropriate site table (Tables 2-10) in the Equivalent Terms and Definitions?");
        rule.setReason("Separate, non-contiguous tumors are two or more different subtypes/variants in Column 3 of the appropriate site table (Tables 2-10) in the Equivalent Terms and Definitions are multiple primaries.");
        rule.getNotes().add("The tumors may be subtypes/variants of the same or different NOS histologies.");
        rule.getNotes().add("  • Same NOS: Alveolar rhabdomyosarcoma 8920/3 and embryonal rhabdomyosarcoma 8910/3 are both subtypes of rhabdomyosarcoma 8900/3 but are distinctly different histologies. Abstract multiple primaries.");
        rule.getNotes().add("  • Different NOS: Colloid-type adenocarcinoma 8144 is a subtype of adenocarcinoma NOS 8140; Sarcomatoid carcinoma 8074 is a subtype of squamous cell carcinoma 8070. They are distinctly different histologies. Abstract multiple primaries.");
        _rules.add(rule);

        // Rule M7	Abstract multiple primaries when separate, non-contiguous tumors are on different rows in the appropriate site table (Tables 2-10) in the Equivalent Terms and Definitions. Timing is irrelevant.
        rule = new MphRule(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M7") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                Map<String, List<String>> table1 = MphConstants.HEAD_AND_NECK_2018_TABLE_FOR_SITE.getOrDefault(i1.getPrimarySite(), null);
                Map<String, List<String>> table2 = MphConstants.HEAD_AND_NECK_2018_TABLE_FOR_SITE.getOrDefault(i2.getPrimarySite(), null);

                if (table1 == null && table2 == null) {
                    result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". Could not find both histologies in one of the tables.");
                }
                else {
                    // Different rows in any column. So a histology in column 1 and a histology on a different row in column 3 would be a situation where this rule would apply.
                    // A histology not on the table counts as a different row.
                    String icd1 = i1.getHistology() + "/" + i1.getBehavior(), icd2 = i2.getHistology() + "/" + i2.getBehavior();
                    int foundRow1 = -1, rowIndex = 0;
                    if (table1 != null) {
                        for (Map.Entry<String, List<String>> entry : table1.entrySet()) {
                            if (entry.getKey().equals(icd1) || entry.getValue().contains(icd1) || entry.getKey().equals(i1.getHistology()) || entry.getValue().contains(i1.getHistology())) {
                                foundRow1 = rowIndex;
                            }
                            if (foundRow1 >= 0) break;
                            rowIndex++;
                        }
                    }
                    int foundRow2 = -1;
                    if (table2 != null) {
                        rowIndex = 0;
                        for (Map.Entry<String, List<String>> entry : table2.entrySet()) {
                            if (entry.getKey().equals(icd2) || entry.getValue().contains(icd2) || entry.getKey().equals(i2.getHistology()) || entry.getValue().contains(i2.getHistology())) {
                                foundRow2 = rowIndex;
                            }
                            if (foundRow2 >= 0) break;
                            rowIndex++;
                        }
                    }

                    // Both histologies not in the table is a manual review.
                    if (foundRow1 == -1 && foundRow2 == -1) {
                        result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                        result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". Could not find both histologies in one of the tables.");
                    }
                    // One histology not in the table counts as a different row.
                    if ((foundRow1 >= 0 && foundRow2 == -1) || (foundRow1 == -1 && foundRow2 >= 0)) {
                        result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    }
                    // Different rows.
                    else if (foundRow1 != foundRow2) {
                        result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    }
                }
                return result;
            }
        };
        rule.setQuestion("Are separate, non-contiguous tumors on different rows in the appropriate site table (Tables 2-10) in the Equivalent Terms and Definitions?");
        rule.setReason("Separate, non-contiguous tumors on different rows in the appropriate site table (Tables 2-10) in the Equivalent Terms and Definitions is multiple primaries.");
        rule.getNotes().add("Each row in the table is a distinctly different histology.");
        _rules.add(rule);

        // Rule M8	Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than five years after the original diagnosis or last recurrence.
        rule = new MphRuleDiagnosisDate(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M8");
        rule.getNotes().add("Clinically disease-free means that there was no evidence of recurrence on follow-up.");
        rule.getNotes().add("  • Scopes are NED");
        rule.getNotes().add("  • Scans are NED");
        rule.getNotes().add("  • Biomarkers are NED");
        rule.getNotes().add("The rules are hierarchical.  The tumors must be in the same site.");
        rule.getNotes().add("When there is a recurrence less than or equal to five years of diagnosis, the “clock” starts over. The time interval is calculated from the date of last recurrence. In other words, the patient must have been disease-free for greater than five years from the date of the last recurrence.");
        rule.getNotes().add("When it is unknown/not documented whether the patient had a recurrence, default to date of diagnosis to compute the time interval.");
        rule.getNotes().add("The physician may state this is a recurrence, meaning the patient had a previous head and neck tumor and now has another head and neck tumor. Follow the rules; do not attempt to interpret the physician’s statement.");
        _rules.add(rule);

        // Rule M9	Abstract a single primary (the invasive)when an in situ tumor is diagnosed after an invasive tumor.
        rule = new MphRuleInSituAfterInvasive(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M9", false);
        rule.getNotes().add("The rules are hierarchical. Only use this rule when none of the previous rules apply.");
        rule.getNotes().add("The tumors may be a NOS and a subtype/variant of that NOS. See Tables 2-10 in the Equivalent Terms and Definitions for listings of NOS and subtype/variants.");
        rule.getNotes().add("The in situ is recorded as a recurrence for those registrars who collect recurrence data.");
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
        rule = new MphRuleInvasiveAfterInSituGreaterThan60Days(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M11", false);
        rule.getNotes().add("The rules are hierarchical. Only use this rule when none of the previous rules apply.");
        rule.getNotes().add("Abstract both the invasive and in situ tumors.");
        rule.getNotes().add("Abstract as multiple primaries even if physician states the invasive tumor is disease recurrence or progression.");
        rule.getNotes().add("This rule is based on long-term epidemiologic studies of recurrence intervals. The specialty medical experts (SMEs) reviewed and approved these rules.  Many of the SMEs were also authors, co-authors, or editors of the AJCC Staging Manual.");
        _rules.add(rule);

        // Rule M12	Abstract a single primary when separate, non-contiguous tumors are on the same row in the appropriate site table (Tables 2-10) in the Equivalent Terms and Definitions. Timing is irrelevant.
        rule = new MphRule(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M12") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                Map<String, List<String>> table1 = MphConstants.HEAD_AND_NECK_2018_TABLE_FOR_SITE.getOrDefault(i1.getPrimarySite(), null);
                Map<String, List<String>> table2 = MphConstants.HEAD_AND_NECK_2018_TABLE_FOR_SITE.getOrDefault(i2.getPrimarySite(), null);

                if (table1 != null && table2 != null) {
                    if (table1.equals(table2)) {
                        // Same histology for both tumors,OR
                        // In the same row, one tumor is in Column 1, and one tumor is in Column 3.

                        String icd1 = i1.getHistology() + "/" + i1.getBehavior(), icd2 = i2.getHistology() + "/" + i2.getBehavior();
                        List<String> subTypes1 = table1.get(icd1);
                        if (subTypes1 == null) subTypes1 = table1.get(i1.getHistology());
                        List<String> subTypes2 = table1.get(icd2);
                        if (subTypes2 == null) subTypes2 = table1.get(i2.getHistology());

                        // Same histology and both in the table.
                        if (subTypes1 != null && subTypes2 != null && i1.getHistology().equals(i2.getHistology())) {
                            result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                            // One in Column 1, and one in Column 3.
                        } else if (subTypes1 != null && subTypes2 == null && (subTypes1.contains(icd2) || subTypes1.contains(i2.getHistology()))) {
                            result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                            // One in Column 1, and one in Column 3.
                        } else if (subTypes1 == null && subTypes2 != null && (subTypes2.contains(icd1) || subTypes2.contains(i1.getHistology()))) {
                            result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        }
                    }
                }
                return result;
            }
        };

        rule.setQuestion("Are separate, non-contiguous tumors on the same row in the appropriate site table (Tables 2-10) in the Equivalent Terms and Definitions?");
        rule.setReason("Separate, non-contiguous tumors on the same row in the appropriate site table (Tables 2-10) in the Equivalent Terms and Definitions is a single primary.");
        rule.getNotes().add("The same row means the tumors are:");
        rule.getNotes().add("  • The same histology (same four-digit ICD-O code) OR");
        rule.getNotes().add("  • One is the preferred term (column 1) and the other is a synonym for the preferred term (column 2) OR");
        rule.getNotes().add("  • A NOS (column 1/column 2) and the other is a subtype/variant of that NOS (column 3)");
        _rules.add(rule);

        // Rule M13	Abstract a single primary  when none of the previous rules apply.
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_HEAD_AND_NECK_GROUP_ID, "M13");
        rule.getNotes().add("Use caution when applying this default rule. Please confirm that you have not overlooked an applicable rule.");
        _rules.add(rule);
    }
}

