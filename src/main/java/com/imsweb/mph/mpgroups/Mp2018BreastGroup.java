/*
 * Copyright (C) 2018 Information Management Services, Inc.
 */
package com.imsweb.mph.mpgroups;

import java.util.Arrays;
import java.util.Collections;

import com.imsweb.mph.MphConstants;
import com.imsweb.mph.MphGroup;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphUtils;
import com.imsweb.mph.MphUtils.MpResult;
import com.imsweb.mph.internal.TempRuleResult;
import com.imsweb.mph.mprules.MpRuleFiveYearsApart;
import com.imsweb.mph.mprules.MpRuleInsituAfterInvasiveSameSide;
import com.imsweb.mph.mprules.MpRuleInvasiveAfterInsituGreaterThan60Days;
import com.imsweb.mph.mprules.MpRuleInvasiveAfterInsituLessThan60DaysSameSide;
import com.imsweb.mph.mprules.MpRuleLaterality;
import com.imsweb.mph.mprules.MpRuleNoCriteriaSatisfied;
import com.imsweb.mph.mprules.MpRulePrimarySite;

public class Mp2018BreastGroup extends MphGroup {

    // Breast Multiple Primary Rules - Text
    // C500-C506, C508-C509
    // (Excludes lymphoma and leukemia M9590 – M9992 and Kaposi sarcoma M9140)
    public Mp2018BreastGroup() {
        super(MphConstants.MP_2018_BREAST_GROUP_ID, MphConstants.MP_2018_BREAST_GROUP_NAME, "C500-C506, C508-C509", null, null,
                "9590-9992, 9140", "2-4,6", "2018-2021");

        // Rule M4 Abstract multiple primaries when there are separate, non-contiguous tumors in sites with ICD-O site codes that differ at the second (CXxx) and/or third characters (CxXx).
        MphRule rule = new MpRulePrimarySite(MphConstants.MP_2018_BREAST_GROUP_ID, "M4");
        rule.getNotes().add(
                "Tumors with site codes that differ at the second or third character are in different primary sites; for example, a breast tumor C50x and a colon tumor C18x differ at the second and third character.");
        rule.getNotes().add(
                "This rule does not include metastases. Metastatic tumors are not used to determine multiple primaries; for example, liver metastases from the breast cancer would not be counted as a second primary.");
        _rules.add(rule);

        // Rule M5 Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than five years after the
        // original diagnosis or last recurrence.
        rule = new MpRuleFiveYearsApart(MphConstants.MP_2018_BREAST_GROUP_ID, "M5");
        rule.getNotes().add("The rules are hierarchical. This rule only applies when there is a subsequent breast tumor.");
        rule.getNotes().add("Clinically disease-free means that there was no evidence of recurrence on follow-up.");
        rule.getNotes().add("  - Mammograms are NED");
        rule.getNotes().add("  - Scans are NED");
        rule.getNotes().add("  - Tumor biomarkers are NED");
        rule.getNotes().add(
                "When there is a recurrence less than or equal to five years of diagnosis, the \"clock\" starts over. The time interval is calculated from the date of last recurrence. In other words, the patient must have been disease-free for greater than five years from the date of the last recurrence.");
        rule.getNotes().add("When it is unknown/not documented whether the patient had a recurrence, use date of diagnosis to compute the time interval.");
        rule.getNotes().add(
                "The physician may state this is a recurrence, meaning the patient had a previous breast tumor and now has another breast tumor. Follow the rules; do not attempt to interpret the physician’s statement.");
        _rules.add(rule);

        // Rule M6 Abstract a single primary when there is inflammatory carcinoma in:
        // - Multiple quadrants of same breast OR
        // - Bilateral breasts
        rule = new MphRule(MphConstants.MP_2018_BREAST_GROUP_ID, "M6") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                if (MphConstants.MALIGNANT.equals(i1.getBehavior()) && MphConstants.MALIGNANT.equals(i2.getBehavior()) && MphConstants.INFLAMMATORY_CARCINOMA.equals(i1.getHistology())
                        && MphConstants.INFLAMMATORY_CARCINOMA.equals(i2.getHistology()))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                return result;
            }
        };
        rule.setQuestion("Is there inflammatory carcinoma in multiple quadrants of the same breast or in bilateral breasts?");
        rule.setReason("Inflammatory carcinoma in multiple quadrants of the same breast or in bilateral breasts is a single primary.");
        _rules.add(rule);

        // Rule M7 Abstract multiple primaries when there is bilateral breast cancer (both right and left breast).
        rule = new MpRuleLaterality(MphConstants.MP_2018_BREAST_GROUP_ID, "M7");
        rule.setQuestion("Is there a tumor(s) in each breast?");
        rule.setReason("Tumors on both sides (right and left breast) are multiple primaries.");
        rule.getNotes().add(
                "Physician statement \"bilateral breast cancer\" should not be interpreted as meaning a single primary. The term is descriptive and not used consistently.  The literal definition of bilateral is \"cancer in both breasts\".");
        rule.getNotes().add("It is irrelevant how many tumors are in each breast. Abstract as separate primaries.");
        rule.getNotes().add("The histologies within each breast may be the same or different.");
        _rules.add(rule);

        /*Suzanne Adams confirms that we are skipping M8 and M9

        // Rule M8 Abstract a single primary when the diagnosis is Paget disease with synchronous underlying in situ or invasive carcinoma NST (duct/ductal).
        rule = new MphRule(MphConstants.MP_2018_BREAST_GROUP_ID, "M8") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                String icd1 = i1.getHistology() + "/" + i1.getBehavior(), icd2 = i2.getHistology() + "/" + i2.getBehavior();
                if (Arrays.asList("8541/3", "8543/2", "8543/3").containsAll(Arrays.asList(icd1, icd2))) {
                    int sixtyDaysApart = GroupUtility.verifyDaysApart(i1, i2, 60);
                    if (-1 == sixtyDaysApart) {
                        result.setPotentialResult(MphUtils.MpResult.QUESTIONABLE);
                        result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". There is not enough diagnosis date information.");
                    }
                    else if (0 == sixtyDaysApart)
                        result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                }
                return result;
            }
        };
        rule.setQuestion("Is Paget disease with underlying in situ or invasive carcinoma NST (duct/ductal)?");
        rule.setReason("Paget disease with underlying in situ or invasive carcinoma NST (duct/ductal) is single primary.");
        rule.getNotes().add("The underlying tumor may be either in situ or invasive.");
        _rules.add(rule);

        //Rule M9 Abstract multiple primaries when the diagnosis is Paget disease with synchronous/simultaneous underlying tumor which is NOT duct.
        rule = new MphRule(MphConstants.MP_2018_BREAST_GROUP_ID, "M9") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                String icd1 = i1.getHistology() + "/" + i1.getBehavior(), icd2 = i2.getHistology() + "/" + i2.getBehavior();
                if ((Arrays.asList("8541/3", "8543/2", "8543/3").contains(icd1) || Arrays.asList("8541/3", "8543/2", "8543/3").contains(icd2)) && !Arrays.asList("8541/3", "8543/2", "8543/3")
                        .containsAll(Arrays.asList(icd1, icd2)))
                    result.setFinalResult(MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Is Paget disease present with synchronous/simultaneous underlying tumor which is NOT duct?");
        rule.setReason("Paget disease with synchronous/simultaneous underlying tumor which is NOT duct are multiple primaries.");
        rule.getExamples().add("Paget disease of the nipple with underlying lobular carcinoma are multiple primaries.");
        _rules.add(rule); */

        //Rule M10 Abstract a single primary when multiple tumors are carcinoma NST/duct and lobular.
        //- Both/all tumors may be a mixture of carcinoma NST/duct and lobular 8522 OR
        //- One tumor may be duct and another tumor lobular OR
        //- One tumor may be mixed duct and lobular 8522, the other tumor either duct or lobular
        rule = new MphRule(MphConstants.MP_2018_BREAST_GROUP_ID, "M10") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                String icd1 = i1.getHistology() + "/" + i1.getBehavior(), icd2 = i2.getHistology() + "/" + i2.getBehavior();
                // -One tumor = 8500/2 OR 8500/3 OR 8035/3; other tumor = 8520/2 OR 8519/2 OR 8520/3
                // -One tumor= 8500/2 OR 8500/3 OR 8035/3 OR 8520/2 OR 8519/2 OR 8520/3; other tumor = 8522/3 OR 8522/2
                // -One tumor= 8522; other tumor = 8522
                if ((MphConstants.BREAST_NST_DUCT_CARCINOMA_2018.contains(icd1) && MphConstants.BREAST_LOBULAR_CARCINOMA_2018.contains(icd2)) ||
                        (MphConstants.BREAST_NST_DUCT_CARCINOMA_2018.contains(icd2) && MphConstants.BREAST_LOBULAR_CARCINOMA_2018.contains(icd1)) ||
                        (MphConstants.BREAST_DUCT_2018.contains(icd1) && MphConstants.BREAST_LOBULAR_2018.contains(icd2)) ||
                        (MphConstants.BREAST_DUCT_2018.contains(icd2) && MphConstants.BREAST_LOBULAR_2018.contains(icd1)) ||
                        (MphConstants.BREAST_LOBULAR_2018.contains(icd1) && MphConstants.BREAST_LOBULAR_2018.contains(icd2)))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                return result;
            }
        };
        rule.setQuestion("Is this a simultaneous carcinoma NST/duct and lobular carcinoma?");
        rule.setReason("Simultaneous carcinoma NST/duct and lobular carcinoma is a single primary.");
        rule.getNotes().add("Tumors must be in the same breast.");
        rule.getNotes().add("Carcinoma NST/duct includes:");
        rule.getNotes().add("  - DCIS 8500/2");
        rule.getNotes().add("  - Carcinoma NST 8500/3");
        rule.getNotes().add("  - Carcinoma with osteoclastic-like stromal giant cells 8035/3 (subtype/variant of carcinoma NST)");
        rule.getNotes().add("Lobular carcinoma includes:");
        rule.getNotes().add("  - In situ lobular carcinoma 8520/2");
        rule.getNotes().add("  - In situ pleomorphic lobular carcinoma 8519/2");
        rule.getNotes().add("  - Invasive lobular carcinoma 8520/3");
        rule.getNotes().add("One or more tumors with combination duct and lobular histology 8522 AND a separate tumor with any other histology in Table 3 are multiple primaries.");
        rule.getExamples().add("Two tumors right breast. One tumor is invasive mixed duct and lobular 8522/3 and the second tumor is tubular 8211/3. Abstract two primaries: 8522/3 and 8211/3.");
        _rules.add(rule);

        //Rule M11 - Abstract a single primaryi when a ductal carcinoma occurs after a combination code in the same breast. See the
        //        following list:
        //- DCIS following a diagnosis of:
        //        - DCIS + lobular carcinoma in situ 8522/2 OR
        //        - DCIS + in situ Paget 8543/2 OR
        //        - DCIS + Invasive Paget 8543/3 OR
        //        - DCIS mixed with other in situ 8523/2 (code used for cases diagnosed prior to 1/1/2018)
        //- Invasive carcinoma NST/duct following a diagnosis of:
        //        - Invasive duct + invasive lobular 8522/3 OR
        //        - Invasive duct + invasive Paget 8541/3 OR
        //        - Invasive duct + other invasive carcinoma 8523/3
        rule = new MphRule(MphConstants.MP_2018_BREAST_GROUP_ID, "M11") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                String icd1 = i1.getHistology() + "/" + i1.getBehavior(), icd2 = i2.getHistology() + "/" + i2.getBehavior();
                if (GroupUtility.differentCategory(icd1, icd2, Collections.singletonList("8500/2"), Arrays.asList("8522/2", "8543/2", "8543/3", "8523/2"))
                        || GroupUtility.differentCategory(icd1, icd2, Collections.singletonList("8500/3"), Arrays.asList("8522/3", "8541/3", "8523/3"))) {
                    int latestDx = GroupUtility.compareDxDate(i1, i2);
                    if (MphConstants.COMPARE_DX_UNKNOWN == latestDx) { //If impossible to decide which tumor is diagnosed later
                        result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupId());
                    }
                    else if (MphConstants.COMPARE_DX_EQUAL == latestDx || (MphConstants.COMPARE_DX_FIRST_LATEST == latestDx && "8500".equals(i1.getHistology())) || (
                            MphConstants.COMPARE_DX_SECOND_LATEST == latestDx && "8500".equals(i2.getHistology())))
                        result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);

                }
                return result;
            }
        };
        rule.setQuestion("Are there tumors where:\n" +
                "- DCIS following a diagnosis of: \n" +
                "       - DCIS + lobular carcinoma in situ 8522/2 OR\n" +
                "       - DCIS + in situ Paget 8543/2 OR\n" +
                "       - DCIS + Invasive Paget 8543/3 OR\n" +
                "       - DCIS mixed with other in situ 8523/2 (code used for cases diagnosed prior to 1/1/2018)\n" +
                "- Invasive carcinoma NST/duct following a diagnosis of:\n" +
                "       - Invasive duct + invasive lobular 8522/3 OR\n" +
                "       - Invasive duct + invasive Paget 8541/3 OR\n" +
                "       - Invasive duct + other invasive carcinoma 8523/3?");
        rule.setReason("The following conditions are a single primary:\n" +
                "- DCIS following a diagnosis of: \n" +
                "       - DCIS + lobular carcinoma in situ 8522/2 OR\n" +
                "       - DCIS + in situ Paget 8543/2 OR\n" +
                "       - DCIS + Invasive Paget 8543/3 OR\n" +
                "       - DCIS mixed with other in situ 8523/2 (code used for cases diagnosed prior to 1/1/2018)\n" +
                "- Invasive carcinoma NST/duct following a diagnosis of:\n" +
                "       - Invasive duct + invasive lobular 8522/3 OR\n" +
                "       - Invasive duct + invasive Paget 8541/3 OR\n" +
                "       - Invasive duct + other invasive carcinoma 8523/3");
        _rules.add(rule);

        // Rule M12 Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3 of Table 3 in the Equivalent Terms and Definitions. Timing is irrelevant.
        rule = new MphRule(MphConstants.MP_2018_BREAST_GROUP_ID, "M12") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                String h1 = i1.getHistology(), icd1 = h1 + "/" + i1.getBehavior(), h2 = i2.getHistology(), icd2 = h2 + "/" + i2.getBehavior();
                String subtype1 = MphConstants.BREAST_2018_TABLE3_SUBTYPES.containsKey(h1) ? MphConstants.BREAST_2018_TABLE3_SUBTYPES.get(h1) : MphConstants.BREAST_2018_TABLE3_SUBTYPES.get(icd1);
                String subtype2 = MphConstants.BREAST_2018_TABLE3_SUBTYPES.containsKey(h2) ? MphConstants.BREAST_2018_TABLE3_SUBTYPES.get(h2) : MphConstants.BREAST_2018_TABLE3_SUBTYPES.get(icd2);
                if (subtype1 != null && subtype2 != null && !subtype1.contains(subtype2) && !subtype2.contains(subtype1))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Are separate/non-contiguous tumors two or more different subtypes/variants in Column 3, Table 3 in the Equivalent Terms and Definitions?");
        rule.setReason("Separate/non-contiguous tumors that are two or more different subtypes/variants in Column 3, Table 3 in the Equivalent Terms and Definitions are multiple primaries.");
        rule.getNotes().add("The tumors may be subtypes/variants of the same or different NOS histologies.");
        rule.getNotes().add(
                "  - Same NOS: Encapsulated papillary carcinoma with invasion 8504/3 and solid papillary carcinoma with invasion 8509/3 are both subtypes of invasive papillary carcinoma 8503/3 but are distinctly different histologies. Abstract multiple primaries.");
        rule.getNotes().add(
                "  - Different NOS: Encapsulated papillary carcinoma 8504/2 is a subtype/variant of in situ papillary carcinoma 8503/2.  Pleomorphic lobular carcinoma in situ 8519/2 is a subtype/variant of lobular carcinoma in situ 8520/2. They are distinctly different histologies. Abstract multiple primaries.");
        _rules.add(rule);

        // Rule M13 Abstract a single primary when synchronous, separate/non-contiguous tumors are on the same row in Table 3 in the Equivalent Terms and Definitions.
        rule = new MphRule(MphConstants.MP_2018_BREAST_GROUP_ID, "M13") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                String h1 = i1.getHistology(), icd1 = h1 + "/" + i1.getBehavior(), h2 = i2.getHistology(), icd2 = h2 + "/" + i2.getBehavior();
                String row1 = MphConstants.BREAST_2018_TABLE3_ROWS.containsKey(h1) ? MphConstants.BREAST_2018_TABLE3_ROWS.get(h1) : MphConstants.BREAST_2018_TABLE3_ROWS.get(icd1);
                String row2 = MphConstants.BREAST_2018_TABLE3_ROWS.containsKey(h2) ? MphConstants.BREAST_2018_TABLE3_ROWS.get(h2) : MphConstants.BREAST_2018_TABLE3_ROWS.get(icd2);
                if (row1 != null && row1.equals(row2)) {
                    int sixtyDaysApart = GroupUtility.verifyDaysApart(i1, i2, 60);
                    if (MphConstants.DATE_VERIFY_UNKNOWN == sixtyDaysApart) {
                        result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupId());
                    }
                    else if (MphConstants.DATE_VERIFY_WITHIN == sixtyDaysApart)
                        result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                }
                return result;
            }
        };
        rule.setQuestion("Are synchronous, separate/non-contiguous tumors on the same row in Table 3 in the Equivalent Terms and Definitions?");
        rule.setReason("Synchronous, separate/non-contiguous tumors on the same row in Table 3 in the Equivalent Terms and Definitions is a single primary.");
        rule.getNotes().add("The same row means the tumors are:");
        rule.getNotes().add("  - The same histology (same four-digit ICD-O code) OR");
        rule.getNotes().add("  - One is the preferred term (column 1) and the other is a synonym for the preferred term (column 2) OR");
        rule.getNotes().add("  - A NOS (column 1/column 2) and the other is a subtype/variant of that NOS (column 3)");
        _rules.add(rule);

        // Rule M14 Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 3 in the Equivalent Terms and Definitions or a combination code in Table 2 and a code from Table 3
        rule = new MphRule(MphConstants.MP_2018_BREAST_GROUP_ID, "M14") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                String h1 = i1.getHistology(), icd1 = h1 + "/" + i1.getBehavior(), h2 = i2.getHistology(), icd2 = h2 + "/" + i2.getBehavior();
                String row1 = MphConstants.BREAST_2018_TABLE3_ROWS.containsKey(h1) ? MphConstants.BREAST_2018_TABLE3_ROWS.get(h1) : MphConstants.BREAST_2018_TABLE3_ROWS.get(icd1);
                if (row1 == null && (MphConstants.BREAST_2018_TABLE2.contains(h1) || MphConstants.BREAST_2018_TABLE2.contains(icd1)))
                    row1 = "table2";
                String row2 = MphConstants.BREAST_2018_TABLE3_ROWS.containsKey(h2) ? MphConstants.BREAST_2018_TABLE3_ROWS.get(h2) : MphConstants.BREAST_2018_TABLE3_ROWS.get(icd2);
                if (row2 == null && (MphConstants.BREAST_2018_TABLE2.contains(h2) || MphConstants.BREAST_2018_TABLE2.contains(icd2)))
                    row2 = "table2";
                if (row1 == null || row2 == null) {
                    result.setFinalResult(MpResult.QUESTIONABLE);
                    String histologyNotInTable;
                    boolean bothNotInTable = false;
                    if (row1 == null && row2 == null) {
                        bothNotInTable = true;
                        histologyNotInTable = "Both " + icd1 + " and " + icd2;
                    }
                    else
                        histologyNotInTable = row1 == null ? icd1 : icd2;

                    result.setMessageNotInTable(this.getStep(), this.getGroupId(), histologyNotInTable, bothNotInTable);
                }
                else if (!row1.equals(row2))
                    result.setFinalResult(MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Are separate/non-contiguous tumors on different rows in Table 3 in the Equivalent Terms and Definitions or a combination code in Table 2 and a code from Table 3?");
        rule.setReason(
                "Separate/non-contiguous tumors on different rows in Table 3 or a combination code in Table 2 and a code from Table 3 in the Equivalent Terms and Definitions are multiple primaries.");
        rule.getNotes().add("Timing is irrelevant. Tumors may be synchronous or non-synchronous.");
        rule.getNotes().add("Each row in the table is a distinctly different histology.");
        rule.getExamples().add("Paget disease of the nipple with underlying lobular are multiple primaries.  Paget and lobular are on different rows in Table 3.");
        _rules.add(rule);

        // Rule M15 Abstract a single primary (the invasive) when an in situ tumor is diagnosed after an invasive tumor in the same breast.
        rule = new MpRuleInsituAfterInvasiveSameSide(MphConstants.MP_2018_BREAST_GROUP_ID, "M15");
        rule.getNotes().add("Once the patient has an invasive tumor, the in situ is recorded as a recurrence for those registrars who collect recurrence data.");
        rule.getNotes().add("The rules are hierarchical. Only use this rule when none of the previous rules apply.");
        rule.getNotes().add("The tumors may be a NOS and a subtype/variant of that NOS.");
        _rules.add(rule);

        // Rule M16 Abstract a single primary (the invasive) when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor in the same breast.
        rule = new MpRuleInvasiveAfterInsituLessThan60DaysSameSide(MphConstants.MP_2018_BREAST_GROUP_ID, "M16");
        rule.getNotes().add("The rules are hierarchical. Only use this rule when none of the previous rules apply.");
        rule.getNotes().add("The tumors may be a NOS and a subtype/variant of that NOS.");
        rule.getNotes().add("When the case has been abstracted, change behavior code on original abstract from /2 to /3.");
        rule.getNotes().add("Do not change date of diagnosis.");
        rule.getNotes().add("If the case has already been submitted to the central registry, report all changes.");
        rule.getNotes().add(
                "The physician may stage both tumors because staging and determining multiple primaries are done for different reasons. Staging determines which treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).");
        rule.getNotes().add("See the COC and SEER manuals for instructions on coding other data items such as Date of Diagnosis, Accession Year and Sequence Number.");
        _rules.add(rule);

        // Rule M17 Abstract multiple primaries when an invasive tumor occurs more than 60 days after an in situ tumor in the same breast.
        rule = new MpRuleInvasiveAfterInsituGreaterThan60Days(MphConstants.MP_2018_BREAST_GROUP_ID, "M17");
        rule.getNotes().add("The rules are hierarchical. Only use this rule when none of the previous rules apply.");
        rule.getNotes().add("Abstract both the invasive and in situ tumors.");
        rule.getNotes().add("Abstract as multiple primaries even if physician states the invasive tumor is disease recurrence or progression.");
        rule.getNotes().add(
                "This rule is based on long-term epidemiologic studies of recurrence intervals. The specialty medical experts (SMEs) reviewed and approved these rules.  Many of the SMEs were also authors, co-authors, or editors of the AJCC Staging Manual.");
        _rules.add(rule);

        // Rule M18 Abstract a single primary when none of the previous rules apply.
        rule = new MpRuleNoCriteriaSatisfied(MphConstants.MP_2018_BREAST_GROUP_ID, "M18");
        rule.getNotes().add("Use this rule as a last resort.  Please confirm that you have not overlooked an applicable rule.");
        rule.getExamples().add(
                "One tumor is invasive carcinoma NST/ductal 8500/3 and a separate non-contiguous tumor in the same breast is DCIS 8500/2.  Abstract a single primary: invasive carcinoma NST/ductal 8500/3.");

        _rules.add(rule);
    }
}
