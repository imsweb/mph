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

    /*
    Breast Multiple Primary Rules - Text
    C500-C506, C508-C509
    (Excludes lymphoma and leukemia M9590 – M9992 and Kaposi sarcoma M9140)

    Rule M4	Abstract a single primary when there is inflammatory carcinoma in:
        •	Multiple quadrants of same breast OR
        •	Bilateral breasts

    Rule M5	Abstract multiple primaries when there are separate, non-contiguous tumors in sites with ICD-O site codes (C50¬_) that are different at the second (CXxx) and/or third characters (CxXx).
        Note 1:	Tumors with site codes that differ at the second or third character are in different primary sites; for example, a breast tumor C50_ and a colon tumor C18_ differ at the second and third character.
        Note 2:	This rule does not include metastases. Metastatic tumors are not used to determine multiple primaries; for example, liver metastases from the breast cancer would not be counted as a second primary.

    Rule M6	Abstract multiple primaries when there is bilateral breast cancer (both right and left breast).
        Note 1:	Physician statement “bilateral breast cancer” should not be interpreted as meaning a single primary. The term is descriptive and not used consistently.  The literal definition of bilateral is cancer in both breasts.
        Note 2:	It is irrelevant how many tumors are in each breast. Abstract as separate primaries.
        Note 3:	The histologies within each breast may be the same or different.

    Rule M7	Abstract a single primary when the diagnosis is Paget disease with underlying:
        •	In situ or invasive carcinoma NST (duct/ductal) OR
        •	In situ or invasive lobular carcinoma

    Rule M8	Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than five years after the original diagnosis or last recurrence.
        Note 1:	The rules are hierarchical. This rule only applies when there is a subsequent breast tumor.
        Note 2:	Clinically disease-free means that there was no evidence of recurrence on follow-up.
        •	Mammograms are NED
        •	Scans are NED
        •	Tumor biomarkers are NED
        Note 3:	When there is a recurrence less than or equal to five years of diagnosis, the “clock” starts over. The time interval is calculated from the date of last recurrence. In other words, the patient must have been disease-free for greater than five years from the date of the last recurrence.
        Note 4:	When it is unknown/not documented whether the patient had a recurrence, default to date of diagnosis to compute the time interval.
        Note 5:	The physician may state this is a recurrence, meaning the patient had a previous breast tumor and now has another breast tumor. Follow the rules; do not attempt to interpret the physician’s statement.

    Rule M9	Abstract a single primary when simultaneous carcinoma NST and lobular carcinoma occur in the same breast.
        Note 1:	The tumors must be the same behavior.

    Rule M10	Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 3 in the Equivalent Terms and Definitions. Tumors may be
        •	Simultaneous OR
        •	Original and subsequent
        Note:	 Each row in the table is a distinctly different histology.  EXCEPTION is adenocarcinoma 8140 and carcinoma NOS 8010.
        •	Carcinoma NOS is a very broad category which includes adenocarcinoma
        •	Do not use this rule when the diagnosis is carcinoma NOS AND adenocarcinoma NOS OR any subtypes/variants of adenocarcinoma NOS

    Rule M11	Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 3 in the Equivalent Terms and Definitions. Tumors may be
        •	Simultaneous OR
        •	Original and subsequent
        Note: The tumors may be subtypes/variants of the same or different NOS histologies.
        •	Same NOS: Encapsulated papillary carcinoma with invasion 8504/3 and solid papillary carcinoma with invasion 8509/3 are both subtypes of invasive papillary carcinoma 8503/3 but are distinctly different histologies. Abstract multiple primaries.
        •	Different NOS: Encapsulated papillary carcinoma 8504/2 is a subtype/variant of in situ papillary carcinoma 8503/2.  Pleomorphic lobular carcinoma in situ 8519/2 is a subtype/variant of lobular carcinoma in situ 8520/2. They are distinctly different histologies. Abstract multiple primaries.

    Rule M12	Abstract a single primaryi when multiple tumors are duct (invasive or in situ) and lobular (invasive or in situ) AND are:
        •	In situ and invasive
            	One tumor is invasive and the other is in situ OR
            	Both/all tumors are mixed in situ and invasive
        Note: See Histology Rules. Only the invasive histology is abstracted.
        •	In situ
            	At least one tumor is DCIS and at least one tumor is in situ lobular carcinoma OR
            	Both/all tumors are mixed DCIS and in situ lobular carcinoma
        •	 Invasive
            	One tumor is duct/carcinoma NST, the other is invasive lobular carcinoma OR
            	Both/all tumors are mixed duct/carcinoma NST and invasive lobular carcinoma
        Note 1:	Tumors must be in the same breast.
        Note 2:	Tumors may be in the same quadrant or multiple quadrants/subsites.

    Rule M13	Abstract a single primary (the invasive) when an in situ tumor is diagnosed after an invasive tumor in the same breast.
        Note 1:	Once the patient has an invasive tumor, the in situ is recorded as a recurrence for those registrars who collect recurrence data.
        Note 2:	The rules are hierarchical. Only use this rule when none of the previous rules apply.
        Note 3:	The tumors may be a NOS and a subtype/variant of that NOS.

    Rule M14	Abstract a single primary (the invasive) when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor in the same breast.
        Note 1:	The rules are hierarchical. Only use this rule when none of the previous rules apply.
        Note 2:	The tumors may be an NOS and a subtype/variant of that NOS.
        Note 3:	When the case has been abstracted, change behavior code on original abstract from /2 to /3.
        Note 4:	Do not change date of diagnosis.
        Note 5:	If the case has already been submitted to the central registry, report all changes.
        Note 6:	The physician may stage both tumors because staging and determining multiple primaries are done for different reasons. Staging determines which treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).
        Note 7:	See the COC and SEER manuals for instructions on coding other data items such as Date of Diagnosis, Accession Year and Sequence Number

    Rule M15	Abstract multiple primaries when an invasive tumor occurs more than 60 days after an in situ tumor in the same breast.
        Note 1:	The rules are hierarchical. Only use this rule when none of the previous rules apply.
        Note 2:	Abstract both the invasive and in situ tumors.
        Note 3:	Abstract as multiple primaries even if physician states the invasive tumor is disease recurrence or progression.
        Note 4:	This rule is based on long-term epidemiologic studies of recurrence intervals. The specialty medical experts (SMEs) reviewed and approved these rules.  Many of the SMEs were also authors, co-authors, or editors of the AJCC Staging Manual.

    Rule M16	Abstract a single primary when none of the previous rules apply.
        Note:	Use caution when applying this default rule.  Please confirm that you have not overlooked an applicable rule.

    */

    // TODO - Question M4 - How do you determine Multiple Quadrants or Bilateral?
    // TODO - Question M5 - Does "separate, non-contiguous" mean different Lateraltiy?
    // TODO - Question M9 - What does "simultaneous" mean?
    // TODO - Question M10 - If a histology is not in the table, does that count as being a different row?
    // TODO - Question M10, M11 - Does "separate/non-contiguous tumor" have any specific meaning?
    // TODO - Question M10, M11 - How do you determine "Simultaneous OR Original and subsequent"?
    // TODO - Question M11 - The phrase "tumors are two or more different subtypes/variants in Column 3" - Does that mean the subtype for each tumor does not match,
    //                       or that each tumor has at least 2 subtypes?
    // TODO - Question M12 - Should we be using DUCT_CARCINOMA and LOBULAR_CARCINOMA, or the specific histologies from Table 3?

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

        // Rule M5	Abstract multiple primaries when there are separate, non-contiguous tumors in sites with ICD-O site codes (C50_) that are different at the
        // second (CXxx) and/or third characters (CxXx).
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
        rule.getNotes().add("Tumors with site codes that differ at the second or third character are in different primary sites; for example, a breast tumor C50_ and a colon tumor C18_ differ at the second and third character.");
        rule.getNotes().add("This rule does not include metastases. Metastatic tumors are not used to determine multiple primaries; for example, liver metastases from the breast cancer would not be counted as a second primary.");
        _rules.add(rule);

        // Rule M6	Abstract multiple primaries when there is bilateral breast cancer (both right and left breast).
        rule = new MphRule(MphConstants.MP_2018_BREAST_GROUP_ID, "M6") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (!GroupUtility.validPairedSiteLaterality(i1.getLaterality(), i2.getLaterality())) {
                    result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". Valid and known laterality should be provided.");
                }
                else if (GroupUtility.areOppositeSides(i1.getLaterality(), i2.getLaterality()))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);

                return result;
            }
        };
        rule.setQuestion("Is there a tumor(s) in each breast?");
        rule.setReason("Tumors on both sides (right and left breast) are multiple primaries.");
        rule.getNotes().add("Physician statement “bilateral breast cancer” should not be interpreted as meaning a single primary. The term is descriptive and not used consistently.  The literal definition of bilateral is cancer in both breasts.");
        rule.getNotes().add("It is irrelevant how many tumors are in each breast. Abstract as separate primaries.");
        rule.getNotes().add("The histologies within each breast may be the same or different.");
        _rules.add(rule);

        // Rule M7	Abstract a single primary when the diagnosis is Paget disease with underlying:
        // •	In situ or invasive carcinoma NST (duct/ductal) OR
        // •	In situ or invasive lobular carcinoma
        rule = new MphRule(MphConstants.MP_2018_BREAST_GROUP_ID, "M7") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                String beh1 = i1.getBehavior(), beh2 = i2.getBehavior();
                List<String> inSituOrMal = Arrays.asList(MphConstants.INSITU, MphConstants.MALIGNANT);
                if (MphConstants.PAGET_DISEASE.contains(i1) && MphConstants.PAGET_DISEASE.contains(i2)) {
                    if (inSituOrMal.contains(beh1) && inSituOrMal.contains(beh2)) {
                        if ((i1.getHistology().equals("8500") || i1.getHistology().equals("8520")) &&
                            (i2.getHistology().equals("8500") || i2.getHistology().equals("8520"))) {
                            result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        }
                    }
                }
                return result;
            }
        };
        rule.setQuestion("Is Paget disease with Carcinoma NST (In situ or invasive) or Lobular Carcinoma (In situ ot invasive)?");
        rule.setReason("Paget disease with Carcinoma NST (In situ or invasive) or Lobular Carcinoma (In situ or invasive) is single primary.");
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
                    result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". There is no enough diagnosis date information.");
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

        // Rule M9	Abstract a single primary when simultaneous carcinoma NST and lobular carcinoma occur in the same breast.
        rule = new MphRule(MphConstants.MP_2018_BREAST_GROUP_ID, "M9") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if ((i1.getLaterality().equals(MphConstants.LEFT) && i2.getLaterality().equals(MphConstants.LEFT)) ||
                    (i1.getLaterality().equals(MphConstants.RIGHT) && i2.getLaterality().equals(MphConstants.RIGHT))) {
                    if ((i1.getHistology().equals("8500") && i2.getHistology().equals("8520")) ||
                        (i2.getHistology().equals("8500") && i1.getHistology().equals("8520"))) {
                        result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                    }
                }
                return result;
            }
        };
        rule.setQuestion("Is this a simultaneous carcinoma NST and lobular carcinoma of the same breast?");
        rule.setReason("Simultaneous carcinoma NST and lobular carcinoma of the same breast is a single primary.");
        rule.getNotes().add("The tumors must be the same behavior.");
        _rules.add(rule);

        // Rule M10	Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 3 in the Equivalent Terms and Definitions. Tumors may be
        // •	Simultaneous OR
        // •	Original and subsequent
        rule = new MphRule(MphConstants.MP_2018_BREAST_GROUP_ID, "M10") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();

                String icd1 = i1.getHistology() + "/" + i1.getBehavior(), icd2 = i2.getHistology() + "/" + i2.getBehavior();
                List<String> subTypes1 = MphConstants.BREAST_2018_TABLE3.get(icd1);
                if (subTypes1 == null) subTypes1 = MphConstants.BREAST_2018_TABLE3.get(i1.getHistology());
                List<String> subTypes2 = MphConstants.BREAST_2018_TABLE3.get(icd2);
                if (subTypes2 == null) subTypes2 = MphConstants.BREAST_2018_TABLE3.get(i2.getHistology());

                if (subTypes1 != null && subTypes2 != null && !subTypes1.equals(subTypes2))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);

                return result;
            }
        };
        rule.setQuestion("Are separate/non-contiguous tumors on different rows in Table 3 in the Equivalent Terms and Definitions?");
        rule.setReason("Separate/non-contiguous tumors on different rows in Table 3 in the Equivalent Terms and Definitions is multiple primaries.");
        rule.getNotes().add("Each row in the table is a distinctly different histology.  EXCEPTION is adenocarcinoma 8140 and carcinoma NOS 8010.");
        rule.getNotes().add("  • Carcinoma NOS is a very broad category which includes adenocarcinoma");
        rule.getNotes().add("  • Do not use this rule when the diagnosis is carcinoma NOS AND adenocarcinoma NOS OR any subtypes/variants of adenocarcinoma NOS");
        _rules.add(rule);

        // Rule M11	Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 3 in the Equivalent Terms and Definitions.
        // Tumors may be
        //   • Simultaneous OR
        //   • Original and subsequent
        rule = new MphRule(MphConstants.MP_2018_BREAST_GROUP_ID, "M11") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();

                String icd1 = i1.getHistology() + "/" + i1.getBehavior(), icd2 = i2.getHistology() + "/" + i2.getBehavior();
                List<String> subTypes1 = MphConstants.BREAST_2018_TABLE3.get(icd1);
                if (subTypes1 == null) subTypes1 = MphConstants.BREAST_2018_TABLE3.get(i1.getHistology());
                List<String> subTypes2 = MphConstants.BREAST_2018_TABLE3.get(icd2);
                if (subTypes2 == null) subTypes2 = MphConstants.BREAST_2018_TABLE3.get(i2.getHistology());

                if (subTypes1 != null && subTypes2 != null && !subTypes1.equals(subTypes2)) {
                    if ((subTypes1.size() >= 2) && (subTypes2.size() >= 2)) {
                        result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    }
                }
                return result;
            }
        };
        rule.setQuestion("Are separate/non-contiguous tumors two or more different subtypes/variants in Column 3, Table 3 in the Equivalent Terms and Definitions?");
        rule.setReason("Separate/non-contiguous tumors that are two or more different subtypes/variants in Column 3, Table 3 in the Equivalent Terms and Definitions are multiple primaries.");
        rule.getNotes().add("The tumors may be subtypes/variants of the same or different NOS histologies.");
        rule.getNotes().add("  • Same NOS: Encapsulated papillary carcinoma with invasion 8504/3 and solid papillary carcinoma with invasion 8509/3 are both subtypes of invasive papillary carcinoma 8503/3 but are distinctly different histologies. Abstract multiple primaries.");
        rule.getNotes().add("  • Different NOS: Encapsulated papillary carcinoma 8504/2 is a subtype/variant of in situ papillary carcinoma 8503/2.  Pleomorphic lobular carcinoma in situ 8519/2 is a subtype/variant of lobular carcinoma in situ 8520/2. They are distinctly different histologies. Abstract multiple primaries.");
        _rules.add(rule);

        // Rule M12	Abstract a single primary when multiple tumors are duct (invasive or in situ) and lobular (invasive or in situ) AND are:
        // •	In situ and invasive
            // 	One tumor is invasive and the other is in situ OR
            // 	Both/all tumors are mixed in situ and invasive
        // •	In situ
            // 	At least one tumor is DCIS and at least one tumor is in situ lobular carcinoma OR
            // 	Both/all tumors are mixed DCIS and in situ lobular carcinoma
        // •	 Invasive
            // 	One tumor is duct/carcinoma NST, the other is invasive lobular carcinoma OR
            // 	Both/all tumors are mixed duct/carcinoma NST and invasive lobular carcinoma
        rule = new MphRule(MphConstants.MP_2018_BREAST_GROUP_ID, "M12") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                String beh1 = i1.getBehavior(), beh2 = i2.getBehavior();
                String hist1 = i1.getHistology(), hist2 = i2.getHistology();
                List<String> inSituOrMal = Arrays.asList(MphConstants.INSITU, MphConstants.MALIGNANT);
                if((i1.getLaterality().equals(MphConstants.LEFT) && i2.getLaterality().equals(MphConstants.LEFT)) ||
                   (i1.getLaterality().equals(MphConstants.RIGHT) && i2.getLaterality().equals(MphConstants.RIGHT))) {
                    if (inSituOrMal.contains(beh1) && inSituOrMal.contains(beh2)) {
                        if ((MphConstants.DUCT_CARCINOMA.contains(hist1) && MphConstants.LOBULAR_CARCINOMA.contains(hist2)) ||
                            (MphConstants.DUCT_CARCINOMA.contains(hist2) && MphConstants.LOBULAR_CARCINOMA.contains(hist1))) {
                            result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        }
                    }
                }
                return result;
            }
        };
        rule.setQuestion("Are there multiple tumors that are duct (invasive or in situ) and lobular (invasive or in situ)?");
        rule.setReason("Multiple tumors that are duct (invasive or in situ) and lobular (invasive or in situ) is a single primary.");
        rule.getNotes().add("See Histology Rules. Only the invasive histology is abstracted.");
        rule.getNotes().add("Tumors must be in the same breast.");
        rule.getNotes().add("Tumors may be in the same quadrant or multiple quadrants/subsites.");
        _rules.add(rule);

        // Rule M13	Abstract a single primary (the invasive) when an in situ tumor is diagnosed after an invasive tumor in the same breast.
        rule = new MphRule(MphConstants.MP_2018_BREAST_GROUP_ID, "M13") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                String beh1 = i1.getBehavior(), beh2 = i2.getBehavior();
                if (GroupUtility.differentCategory(beh1, beh2, Collections.singletonList(MphConstants.INSITU), Collections.singletonList(MphConstants.MALIGNANT))) {
                    if((i1.getLaterality().equals(MphConstants.LEFT) && i2.getLaterality().equals(MphConstants.LEFT)) ||
                       (i1.getLaterality().equals(MphConstants.RIGHT) && i2.getLaterality().equals(MphConstants.RIGHT))) {
                        int latestDx = GroupUtility.compareDxDate(i1, i2);
                        //If they are diagnosed at same date or in situ is following invasive
                        if ((1 == latestDx && MphConstants.MALIGNANT.equals(beh1) && MphConstants.INSITU.equals(beh2)) ||
                            (2 == latestDx && MphConstants.MALIGNANT.equals(beh2) && MphConstants.INSITU.equals(beh1))) {
                            result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        }
                    }
                }
                return result;
            }
        };
        rule.setQuestion("Is there an in situ tumor diagnosed after an in invasive tumor in the same breast?");
        rule.setReason("An in situ tumor diagnosed after an invasive tumor in the same breast is a single primary.");
        rule.getNotes().add("Once the patient has an invasive tumor, the in situ is recorded as a recurrence for those registrars who collect recurrence data.");
        rule.getNotes().add("The rules are hierarchical. Only use this rule when none of the previous rules apply.");
        rule.getNotes().add("The tumors may be a NOS and a subtype/variant of that NOS.");
        _rules.add(rule);

        // Rule M14	Abstract a single primary (the invasive) when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor in the same breast.
        rule = new MphRule(MphConstants.MP_2018_BREAST_GROUP_ID, "M14") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                String beh1 = i1.getBehavior(), beh2 = i2.getBehavior();
                if (GroupUtility.differentCategory(beh1, beh2, Collections.singletonList(MphConstants.INSITU), Collections.singletonList(MphConstants.MALIGNANT))) {
                    if((i1.getLaterality().equals(MphConstants.LEFT) && i2.getLaterality().equals(MphConstants.LEFT)) ||
                       (i1.getLaterality().equals(MphConstants.RIGHT) && i2.getLaterality().equals(MphConstants.RIGHT))) {
                        int latestDx = GroupUtility.compareDxDate(i1, i2);
                        //If they are diagnosed at same date or invasive is not following insitu
                        if (0 == latestDx || (1 == latestDx && !MphConstants.MALIGNANT.equals(beh1)) || (2 == latestDx && !MphConstants.MALIGNANT.equals(beh2)))
                            return result;
                        else {
                            int sixtyDaysApart = GroupUtility.verifyDaysApart(i1, i2, 60);
                            if (-1 == sixtyDaysApart) {
                                result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                                result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". There is no enough diagnosis date information.");
                            }
                            else if (0 == sixtyDaysApart)
                                result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        }
                    }
                }
                return result;
            }
        };
        rule.setQuestion("Is there an invasive tumor diagnosed less than or equal to 60 days after an in situ tumor in the same breast?");
        rule.setReason("An invasive tumor diagnosed less than or equal to 60 days after an in situ tumor in the same breast is a single primary.");
        rule.getNotes().add("The rules are hierarchical. Only use this rule when none of the previous rules apply.");
        rule.getNotes().add("The tumors may be an NOS and a subtype/variant of that NOS.");
        rule.getNotes().add("When the case has been abstracted, change behavior code on original abstract from /2 to /3.");
        rule.getNotes().add("Do not change date of diagnosis.");
        rule.getNotes().add("If the case has already been submitted to the central registry, report all changes.");
        rule.getNotes().add("The physician may stage both tumors because staging and determining multiple primaries are done for different reasons. Staging determines which treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).");
        rule.getNotes().add("See the COC and SEER manuals for instructions on coding other data items such as Date of Diagnosis, Accession Year and Sequence Number");
        _rules.add(rule);

        // Rule M15	Abstract multiple primaries when an invasive tumor occurs more than 60 days after an in situ tumor in the same breast.
        rule = new MphRule(MphConstants.MP_2018_BREAST_GROUP_ID, "M15") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                String beh1 = i1.getBehavior(), beh2 = i2.getBehavior();
                if (GroupUtility.differentCategory(beh1, beh2, Collections.singletonList(MphConstants.INSITU), Collections.singletonList(MphConstants.MALIGNANT))) {
                    if((i1.getLaterality().equals(MphConstants.LEFT) && i2.getLaterality().equals(MphConstants.LEFT)) ||
                       (i1.getLaterality().equals(MphConstants.RIGHT) && i2.getLaterality().equals(MphConstants.RIGHT))) {
                        int latestDx = GroupUtility.compareDxDate(i1, i2);
                        //If they are diagnosed at same date or invasive is not following insitu
                        if (0 == latestDx || (1 == latestDx && !MphConstants.MALIGNANT.equals(beh1)) || (2 == latestDx && !MphConstants.MALIGNANT.equals(beh2)))
                            return result;
                        else {
                            int sixtyDaysApart = GroupUtility.verifyDaysApart(i1, i2, 60);
                            if (-1 == sixtyDaysApart) {
                                result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                                result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". There is no enough diagnosis date information.");
                            }
                            else if (1 == sixtyDaysApart)
                                result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                        }
                    }
                }
                return result;
            }
        };
        rule.setQuestion("Is there an invasive tumor following an in situ tumor in the same breast more than 60 days after diagnosis?");
        rule.setReason("An invasive tumor following an in situ tumor in the same breast more than 60 days after diagnosis are multiple primaries.");
        rule.getNotes().add("The rules are hierarchical. Only use this rule when none of the previous rules apply.");
        rule.getNotes().add("Abstract both the invasive and in situ tumors.");
        rule.getNotes().add("Abstract as multiple primaries even if physician states the invasive tumor is disease recurrence or progression.");
        rule.getNotes().add("This rule is based on long-term epidemiologic studies of recurrence intervals. The specialty medical experts (SMEs) reviewed and approved these rules.  Many of the SMEs were also authors, co-authors, or editors of the AJCC Staging Manual.");
        _rules.add(rule);

        // Rule M16	Abstract a single primary when none of the previous rules apply.
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_BREAST_GROUP_ID, "M16");
        rule.getNotes().add("Use caution when applying this default rule.  Please confirm that you have not overlooked an applicable rule.");
        _rules.add(rule);




        /*
        // M4- Tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx) and/or third (Cx?x) character are multiple primaries.
        MphRule rule = new MphRulePrimarySiteCode(MphConstants.MP_2018_BREAST_GROUP_ID, "M4");
        _rules.add(rule);

        //M5- Tumors diagnosed more than five (5) years apart are multiple primaries.
        rule = new MphRuleDiagnosisDate(MphConstants.MP_2018_BREAST_GROUP_ID, "M5");
        _rules.add(rule);

        //M6- Inflammatory carcinoma in one or both breasts is a single primary. (8530/3)
        rule = new MphRule(MphConstants.MP_2018_BREAST_GROUP_ID, "M6") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (MphConstants.MALIGNANT.equals(i1.getBehavior()) && MphConstants.MALIGNANT.equals(i2.getBehavior()) && MphConstants.INFLAMMATORY_CARCINOMA.equals(i1.getHistology())
                        && MphConstants.INFLAMMATORY_CARCINOMA.equals(i2.getHistology()))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                return result;
            }
        };
        rule.setQuestion("Is there inflammatory carcinoma in one or both breasts?");
        rule.setReason("Inflammatory carcinoma in one or both breasts is a single primary.");
        _rules.add(rule);

        //M7- Tumors on both sides (right and left breast) are multiple primaries.
        rule = new MphRule(MphConstants.MP_2018_BREAST_GROUP_ID, "M7") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (!GroupUtility.validPairedSiteLaterality(i1.getLaterality(), i2.getLaterality())) {
                    result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". Valid and known laterality should be provided.");
                }
                else if (GroupUtility.areOppositeSides(i1.getLaterality(), i2.getLaterality()))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);

                return result;
            }
        };
        rule.setQuestion("Is there a tumor(s) in each breast?");
        rule.setReason("Tumors on both sides (right and left breast) are multiple primaries.");
        rule.getNotes().add("Lobular carcinoma in both breasts (\"mirror image\") is a multiple primary.");
        _rules.add(rule);


        //M9- Tumors that are intraductal or duct and Paget Disease are a single primary.
        rule = new MphRule(MphConstants.MP_2018_BREAST_GROUP_ID, "M9") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                List<String> intraductalOrDuct = new ArrayList<>(MphConstants.INTRADUCTAL_CARCINOMA);
                intraductalOrDuct.addAll(MphConstants.DUCT_CARCINOMA);
                if (GroupUtility.differentCategory(i1.getHistology(), i2.getHistology(), MphConstants.PAGET_DISEASE, intraductalOrDuct))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                return result;
            }
        };
        rule.setQuestion("Are the tumors intraductal or duct and Paget Disease?");
        rule.setReason("Tumors that are intraductal or duct and Paget Disease are a single primary.");
        rule.getNotes().add("Use Table 1 and Table 2 to identify intraductal and duct carcinomas.");
        _rules.add(rule);

        //M10- Tumors that are lobular (8520) and intraductal or duct are a single primary.
        rule = new MphRule(MphConstants.MP_2018_BREAST_GROUP_ID, "M10") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                List<String> lobular = GroupUtility.expandList("8520");
                List<String> intraductalOrDuct = new ArrayList<>(MphConstants.INTRADUCTAL_CARCINOMA);
                intraductalOrDuct.addAll(MphConstants.DUCT_CARCINOMA);
                if (GroupUtility.differentCategory(i1.getHistology(), i2.getHistology(), lobular, intraductalOrDuct))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                return result;
            }
        };
        rule.setQuestion("Are the tumors lobular (8520) and intraductal or duct?");
        rule.setReason("Tumors that are lobular (8520) and intraductal or duct are a single primary.");
        rule.getNotes().add("Use Table 1 and Table 2 to identify intraductal and duct carcinomas.");
        _rules.add(rule);

        //M11- Multiple intraductal and/or duct carcinomas are a single primary.
        rule = new MphRule(MphConstants.MP_2018_BREAST_GROUP_ID, "M11") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                List<String> intraductalOrDuct = new ArrayList<>(MphConstants.INTRADUCTAL_CARCINOMA);
                intraductalOrDuct.addAll(MphConstants.DUCT_CARCINOMA);
                if (intraductalOrDuct.containsAll(Arrays.asList(i1.getHistology(), i2.getHistology())))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                return result;
            }
        };
        rule.setQuestion("Are there multiple intraductal and/or duct carcinomas?");
        rule.setReason("Multiple intraductal and/or duct carcinomas are a single primary.");
        rule.getNotes().add("Use Table 1 and Table 2 to identify intraductal and duct carcinomas.");
        _rules.add(rule);

        //M12- Tumors with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.
        rule = new MphRuleHistologyCode(MphConstants.MP_2018_BREAST_GROUP_ID, "M12");
        _rules.add(rule);


        */
    }
}
