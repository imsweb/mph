/*
 * Copyright (C) 2018 Information Management Services, Inc.
 */
package com.imsweb.mph.mpgroups;

import java.util.ArrayList;
import java.util.Arrays;
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

    Rule M9	Abstract a single primary when simultaneous carcinoma NST and lobular carcinoma the same breast.
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
        •	Same NOS: Pleomorphic carcinoma 8022 and carcinoma with osteoclastic-like stromal giant cells 8035 are both subtypes of carcinoma NST 8500 but are distinctly different histologies. Abstract multiple primaries.
        •	Different NOS: Encapsulated papillary carcinoma 8504/2 is a subtype/variant of in situ papillary carcinoma 8503/2.  Pleomorphic lobular carcinoma in situ 8519/2 is a subtype/variant of lobular carcinoma in situ 8520/2. They are distinctly different histologies. Abstract multiple primaries.

    Rule M12	Abstract a single primary when there are multiple tumors (DCIS/duct/carcinoma NST and lobular carcinoma) in the same breast (same or multiple quadrants/subsite) which are:
        •	In situ and invasive
            	One tumor is invasive and the other is in situ OR
            	Both/all tumors are mixed in situ and invasive
        Note: See Histology rules. Only the invasive histology is abstracted
        •	In situ
            	At least one tumor is DCIS and at least one tumor is in situ lobular carcinoma
            	Both/all tumors are mixed DCIS and in situ lobular carcinoma
        •	 Invasive
            	One tumor is carcinoma NST, the other is invasive lobular carcinoma OR
            	Both/all tumors are mixed carcinoma NST and invasive lobular carcinoma

    Rule M13	Abstract a single primary when an in situ tumor is diagnosed after an invasive tumor in the same breast.
        Note 1:	The rules are hierarchical. Only use this rule when none of the previous rules apply.
        Note 2:	Once the patient has an invasive tumor, the in situ is recorded as a recurrence for those registrars who collect recurrence data.

    Rule M14	Abstract a single primary (the invasive) when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor in the same breast.
        Note 1:	The rules are hierarchical. Only use this rule when none of the previous rules apply.
        Note 2:	The tumors may be an NOS and a subtype/variant of that NOS.
        Note 3:	When the case has been abstracted, change behavior code on original abstract from /2 to /3. Do not change date of diagnosis.
        Note 4:	If the case has already been submitted to the central registry, report all changes.
        Note 5:	The physician may stage both tumors because staging and determining multiple primaries are done for different reasons. Staging determines which treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).
        Note 6:	See the COC and SEER manuals for instructions on coding other data items such as Date of Diagnosis, Accession Year and Sequence Number

    Rule M15	Abstract multiple primaries when an invasive tumor occurs more than 60 days after an in situ tumor in the same breast.
        Note 1:	The rules are hierarchical. Only use this rule when none of the previous rules apply.
        Note 2:	Abstract both the invasive and in situ tumors.
        Note 3:	Abstract as multiple primaries even if physician states the invasive tumor is disease recurrence or progression.
        Note 4:	This rule is based on long-term epidemiologic studies of recurrence intervals. The specialty medical experts (SMEs) reviewed and approved these rules.  Many of the SMEs were also authors, co-authors, or editors of the AJCC Staging Manual.

    Rule M16	Abstract a single primary when tumors that do not meet any of the above criteria.

    */

    // TODO
    // Breast Multiple Primary Rules - Text
    // C500-C506, C508-C509
    // (Excludes lymphoma and leukemia M9590 – M9992 and Kaposi sarcoma M9140)
    public Mp2018BreastGroup() {
        super(MphConstants.MP_2018_BREAST_GROUP_ID, MphConstants.MP_2018_BREAST_GROUP_NAME, "C500-C506", null, null, "9590-9992,9140", "2-3,6", "2018-9999");

        // Rule M4	Abstract a single primary when there is inflammatory carcinoma in:
        // •	Multiple quadrants of same breast OR
        // •	Bilateral breasts
        // TODO
        MphRule rule = new MphRule(MphConstants.MP_2018_BREAST_GROUP_ID, "M4") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
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

        // Rule M5	Abstract multiple primaries when there are separate, non-contiguous tumors in sites with ICD-O site codes (C50¬_) that are different at the second (CXxx) and/or third characters (CxXx).
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_BREAST_GROUP_ID, "M5");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add(
                "Tumors with site codes that differ at the second or third character are in different primary sites; for example, a breast tumor C50_ and a colon tumor C18_ differ at the second and third character.");
        rule.getNotes().add("This rule does not include metastases. Metastatic tumors are not used to determine multiple primaries; for example, liver metastases from the breast cancer would not be counted as a second primary.");
        _rules.add(rule);

        // Rule M6	Abstract multiple primaries when there is bilateral breast cancer (both right and left breast).
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_BREAST_GROUP_ID, "M6");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add(
                "Physician statement “bilateral breast cancer” should not be interpreted as meaning a single primary. The term is descriptive and not used consistently.  The literal definition of bilateral is cancer in both breasts.");
        rule.getNotes().add("It is irrelevant how many tumors are in each breast. Abstract as separate primaries.");
        rule.getNotes().add("The histologies within each breast may be the same or different.");
        _rules.add(rule);

        // Rule M7	Abstract a single primary when the diagnosis is Paget disease with underlying:
        // •	In situ or invasive carcinoma NST (duct/ductal) OR
        // •	In situ or invasive lobular carcinoma
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_BREAST_GROUP_ID, "M7");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The underlying tumor may be either in situ or invasive.");
        _rules.add(rule);

        // Rule M8	Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than five years after the original diagnosis or last recurrence.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_BREAST_GROUP_ID, "M8");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The rules are hierarchical. This rule only applies when there is a subsequent breast tumor.");
        rule.getNotes().add("Clinically disease-free means that there was no evidence of recurrence on follow-up.");
        rule.getNotes().add("  • Mammograms are NED");
        rule.getNotes().add("  • Scans are NED");
        rule.getNotes().add("  • Tumor biomarkers are NED");
        rule.getNotes().add(
                "When there is a recurrence less than or equal to five years of diagnosis, the “clock” starts over. The time interval is calculated from the date of last recurrence. In other words, the patient must have been disease-free for greater than five years from the date of the last recurrence.");
        rule.getNotes().add("When it is unknown/not documented whether the patient had a recurrence, default to date of diagnosis to compute the time interval.");
        rule.getNotes().add(
                "The physician may state this is a recurrence, meaning the patient had a previous breast tumor and now has another breast tumor. Follow the rules; do not attempt to interpret the physician’s statement.");
        _rules.add(rule);

        // Rule M9	Abstract a single primary when simultaneous carcinoma NST and lobular carcinoma the same breast.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_BREAST_GROUP_ID, "M9");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The tumors must be the same behavior.");
        _rules.add(rule);

        // Rule M10	Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 3 in the Equivalent Terms and Definitions. Tumors may be
        // •	Simultaneous OR
        // •	Original and subsequent
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_BREAST_GROUP_ID, "M10");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("Each row in the table is a distinctly different histology.  EXCEPTION is adenocarcinoma 8140 and carcinoma NOS 8010.");
        rule.getNotes().add("  • Carcinoma NOS is a very broad category which includes adenocarcinoma");
        rule.getNotes().add("  • Do not use this rule when the diagnosis is carcinoma NOS AND adenocarcinoma NOS OR any subtypes/variants of adenocarcinoma NOS");
        _rules.add(rule);

        // Rule M11	Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 3 in the Equivalent Terms and Definitions. Tumors may be
        // •	Simultaneous OR
        // •	Original and subsequent
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_BREAST_GROUP_ID, "M11");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The tumors may be subtypes/variants of the same or different NOS histologies.");
        rule.getNotes().add(
                "  • Same NOS: Pleomorphic carcinoma 8022 and carcinoma with osteoclastic-like stromal giant cells 8035 are both subtypes of carcinoma NST 8500 but are distinctly different histologies. Abstract multiple primaries.");
        rule.getNotes().add(
                "  • Different NOS: Encapsulated papillary carcinoma 8504/2 is a subtype/variant of in situ papillary carcinoma 8503/2.  Pleomorphic lobular carcinoma in situ 8519/2 is a subtype/variant of lobular carcinoma in situ 8520/2. They are distinctly different histologies. Abstract multiple primaries.");
        _rules.add(rule);

        // Rule M12	Abstract a single primary when there are multiple tumors (DCIS/duct/carcinoma NST and lobular carcinoma) in the same breast (same or multiple quadrants/subsite) which are:
        // •	In situ and invasive
        // 	One tumor is invasive and the other is in situ OR
        // 	Both/all tumors are mixed in situ and invasive
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_BREAST_GROUP_ID, "M12");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("See Histology rules. Only the invasive histology is abstracted");
        rule.getNotes().add("• In situ");
        rule.getNotes().add("     At least one tumor is DCIS and at least one tumor is in situ lobular carcinoma");
        rule.getNotes().add("     Both/all tumors are mixed DCIS and in situ lobular carcinoma");
        rule.getNotes().add("  • Invasive");
        rule.getNotes().add("     One tumor is carcinoma NST, the other is invasive lobular carcinoma OR");
        rule.getNotes().add("     Both/all tumors are mixed carcinoma NST and invasive lobular carcinoma");
        _rules.add(rule);

        // Rule M13	Abstract a single primary when an in situ tumor is diagnosed after an invasive tumor in the same breast.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_BREAST_GROUP_ID, "M13");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The rules are hierarchical. Only use this rule when none of the previous rules apply.");
        rule.getNotes().add("Once the patient has an invasive tumor, the in situ is recorded as a recurrence for those registrars who collect recurrence data.");
        _rules.add(rule);

        // Rule M14	Abstract a single primary (the invasive) when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor in the same breast.
        // TODO
        rule = new MphRuleHistologyCode(MphConstants.MP_2018_BREAST_GROUP_ID, "M14");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The rules are hierarchical. Only use this rule when none of the previous rules apply.");
        rule.getNotes().add("The tumors may be an NOS and a subtype/variant of that NOS.");
        rule.getNotes().add("When the case has been abstracted, change behavior code on original abstract from /2 to /3. Do not change date of diagnosis.");
        rule.getNotes().add("If the case has already been submitted to the central registry, report all changes.");
        rule.getNotes().add(
                "The physician may stage both tumors because staging and determining multiple primaries are done for different reasons. Staging determines which treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).");
        rule.getNotes().add("See the COC and SEER manuals for instructions on coding other data items such as Date of Diagnosis, Accession Year and Sequence Number");
        _rules.add(rule);

        // Rule M15	Abstract multiple primaries when an invasive tumor occurs more than 60 days after an in situ tumor in the same breast.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_BREAST_GROUP_ID, "M15");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The rules are hierarchical. Only use this rule when none of the previous rules apply.");
        rule.getNotes().add("Abstract both the invasive and in situ tumors.");
        rule.getNotes().add("Abstract as multiple primaries even if physician states the invasive tumor is disease recurrence or progression.");
        rule.getNotes().add(
                "This rule is based on long-term epidemiologic studies of recurrence intervals. The specialty medical experts (SMEs) reviewed and approved these rules.  Many of the SMEs were also authors, co-authors, or editors of the AJCC Staging Manual.");
        _rules.add(rule);

        // Rule M16	Abstract a single primary when tumors that do not meet any of the above criteria.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_BREAST_GROUP_ID, "M16");
        rule.setQuestion("");
        rule.setReason("");
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

        //M8- An invasive tumor following an in situ tumor more than 60 days after diagnosis are multiple primaries.
        rule = new MphRuleBehavior(MphConstants.MP_2018_BREAST_GROUP_ID, "M8");
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
                List<String> lobular = GroupUtility.expandList("8520"); //TODO if lobular of KY is correct use the constant in MphConstants
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

        //M13- Tumors that do not meet any of the criteria are abstracted as a single primary.
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_BREAST_GROUP_ID, "M13");
        rule.getNotes().add("When an invasive tumor follows an in situ tumor within 60 days, abstract as a single primary.");
        rule.getNotes().add("All cases covered by Rule M13 have the same first 3 numbers in ICD-O-3 histology code.");
        rule.getExamples().add("Invasive duct and intraductal carcinoma in the same breast.");
        rule.getExamples().add("Multi-centric lobular carcinoma, left breast.");
        _rules.add(rule);
        */
    }
}
