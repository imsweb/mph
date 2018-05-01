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

    Rule M4     Abstract a single primary when there is inflammatory carcinoma in:
                • Multiple quadrants of same breast OR
                • Bilateral breasts

    Rule M5     Abstract multiple primaries when there are separate, non-contiguous tumors in sites with ICD-O site/topography codes (C50_) that are different at the second (CXxx) and/or third characters (CxXx).
                Note 1: Tumors with topography codes that differ at the second or third character are in different primary sites, for example, a breast tumor C50_ and a colon tumor C18_ differ at the second and third character.
                Note 2: This rule does not include metastases. Metastatic tumors are not used to determine multiple primaries; for example, liver metastases from the breast cancer would not be counted as a second primary.

    Rule M6     Abstract multiple primaries when there is bilateral breast cancer (both right and left breast).
                Note 1: Physician statement “bilateral breast cancer” should not be interpreted as meaning a single primary. The term is descriptive and not used consistently. The literal definition of bilateral is cancer in both breasts.
                Note 2: It is irrelevant how many tumors are in each breast. Abstract as multiple primaries.
                Note 3: The histologies within each breast may be the same or different.

    Rule M7     Abstract a single primary when there is synchronous Paget disease of the nipple and an underlying cancerous tumor.
                Note: The underlying tumor may be either in situ or invasive.

    Rule M8     Abstract multiple primaries when breast tumors are diagnosed more than five (5) years apart.
                Note 1: The rules are hierarchical. This rule only applies when there is a subsequent breast tumor.
                Note 2: The time interval means the patient has been clinically disease-free for >5 years.
                    Definition clinically free: There has been no clinical evidence of disease greater than 5 years from date of original diagnosis. Mammograms, scans, and all other work-ups show no evidence of disease (NED). There are no subsequent breast tumor and/or any metastases from the breast tumor within this period.
                    Definition clinically free when neoadjuvant treatment: When there is a core needle biopsy or a fine needle aspiration (FNA) followed by neoadjuvant treatment, then surgical removal of the tumor, the greater than 5-year interval starts from the date of surgery
                Note 3: When the patient has a recurrence less than or equal to 5 years from the date of initial diagnosis:
                    • The “clock” starts over
                    • The greater than 5-year disease-free interval is computed from the date of the last known recurrence
                    • The patient must have been disease-free for more than 5 years after the last recurrence of a breast tumor
                Note 4: Default to date of diagnosis to compute the >5-year time interval when:
                    • There was no known recurrence AND/OR
                    • Documentation is not available to confirm whether or not there was recurrence
                Note 5: The location and histology of the subsequent breast tumor is irrelevant. Breast tumors that occur more than 5 years apart are always multiple primaries.
                Note 6: Code a new primary even if the physician states “recurrence”.

    Rule M9     Abstract a single primary when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor
                AND:
                • Both tumors have identical four-digit ICD-O histology codes (XXXX) OR
                • Tumors are NOS/NST and a subtype/variant of that NOS/NST (See Table 3 in Equivalent Terms and Definitions for NOS/NST and subtypes)
                Note 1: Abstract the invasive tumor only.
                Note 2: When the in situ tumor has been abstracted and reported to the central registry:
                • Change behavior code on original abstract from /2 to /3 after the invasive is diagnosed
                • Change histology when in situ tumor is a NOS/NST and the invasive tumor is subtype/variant of that NOS/NST
                • Do not change date of diagnosis
                • When the case has been submitted to the central registry, report all changes
                Note 3: The physician may stage both tumors because staging and determining multiple primaries are done for different reasons. Staging determines which course of treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).

    Rule M10    Abstract multiple primaries when an invasive tumor is diagnosed greater than 60 days after the diagnosis of an in situ tumor.
                Note 1: The rules are hierarchical. Use this rule only when rules M4-M9 are not applicable.
                Note 2: The purpose of this rule is to ensure that the case is counted as an incidence (invasive) case when incidence data are analyzed.
                Note 3: Abstract as multiple primaries even if the medical record/physician states it is a disease recurrence or progression.
                Note 4: This rule only applies when the in situ tumor was treated OR there was a decision not to treat.
                Note 5: Default to previous rule (code a single primary, the invasive) when the patient is still being worked-up OR waiting for surgery.

    Rule M11    Abstract a single primary when the diagnosis is Paget disease with underlying:
                • In situ or invasive carcinoma NST (duct/ductal) OR
                • In situ or invasive lobular carcinoma

    Rule M12    Abstract a single primary when there are multiple tumors (DCIS/duct/carcinoma NST and lobular carcinoma) in the same breast (same or multiple quadrants)/subsites which are:
                • In situ and invasive
                     One tumor is invasive and the other is in situ OR
                     Both/all tumors are mixed in situ and invasive
                        Note: See Histology rules. Only the invasive histology is abstracted
                • In situ
                     At least one tumor is DCIS and at least one tumor is in situ lobular carcinoma
                     Both/all tumors are mixed DCIS and in situ lobular carcinoma
                • Invasive
                     One tumor is carcinoma NST, the other is invasive lobular carcinoma OR
                     Both/all tumors are mixed carcinoma NST and invasive lobular carcinoma

    Rule M13    Abstract a single primary when there are multiple tumors in the same breast which:
                • Have identical ICD-O histology/morphology codes XXXX OR
                • Are a NOS and a subtype/variant of the NOS

    Rule M14    Abstract multiple primaries when there are multiple tumors with ICD-O histology codes that are different at the first (Xxxx), second (xXxx) or third (xxXx) number.
                Note: The rules are hierarchical. Do not use this rule if any of the rules M4-M13 apply

    Rule M15    Abstract a single primary when tumors that do not meet any of the above criteria in rules M1-M14.

    */

    // TODO
    public Mp2018BreastGroup() {
        super(MphConstants.MP_2018_BREAST_GROUP_ID, MphConstants.MP_2018_BREAST_GROUP_NAME, "C500-C509", null, null, "9590-9989,9140", "2-3,6", "2018-9999");

        // Rule M4 - Abstract a single primary when there is inflammatory carcinoma in:
        // • Multiple quadrants of same breast OR
        // • Bilateral breasts
        // TODO
        MphRule rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_BREAST_GROUP_ID, "M4");
        rule.setQuestion("");
        rule.setReason("");
        _rules.add(rule);

        // Rule M5 - Abstract multiple primaries when there are separate, non-contiguous tumors in sites with ICD-O site/topography codes (C50_) that are different at the second (CXxx) and/or third characters (CxXx).
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_BREAST_GROUP_ID, "M5");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add(
                "Tumors with topography codes that differ at the second or third character are in different primary sites, for example, a breast tumor C50_ and a colon tumor C18_ differ at the second and third character.");
        rule.getNotes().add(
                "This rule does not include metastases. Metastatic tumors are not used to determine multiple primaries; for example, liver metastases from the breast cancer would not be counted as a second primary.");
        _rules.add(rule);

        // Rule M6 - Abstract multiple primaries when there is bilateral breast cancer (both right and left breast).
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_BREAST_GROUP_ID, "M6");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add(
                "Physician statement “bilateral breast cancer” should not be interpreted as meaning a single primary. The term is descriptive and not used consistently. The literal definition of bilateral is cancer in both breasts.");
        rule.getNotes().add("It is irrelevant how many tumors are in each breast. Abstract as multiple primaries.");
        rule.getNotes().add("The histologies within each breast may be the same or different.");
        _rules.add(rule);

        // Rule M7 - Abstract a single primary when there is synchronous Paget disease of the nipple and an underlying cancerous tumor.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_BREAST_GROUP_ID, "M7");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The underlying tumor may be either in situ or invasive.");
        _rules.add(rule);

        // Rule M8 - Abstract multiple primaries when breast tumors are diagnosed more than five (5) years apart.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_BREAST_GROUP_ID, "M8");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The rules are hierarchical. This rule only applies when there is a subsequent breast tumor.");
        rule.getNotes().add("The time interval means the patient has been clinically disease-free for >5 years.");
        rule.getNotes().add(
                "  Definition clinically free: There has been no clinical evidence of disease greater than 5 years from date of original diagnosis. Mammograms, scans, and all other work-ups show no evidence of disease (NED). There are no subsequent breast tumor and/or any metastases from the breast tumor within this period.");
        rule.getNotes().add(
                "  Definition clinically free when neoadjuvant treatment: When there is a core needle biopsy or a fine needle aspiration (FNA) followed by neoadjuvant treatment, then surgical removal of the tumor, the greater than 5-year interval starts from the date of surgery");
        rule.getNotes().add("When the patient has a recurrence less than or equal to 5 years from the date of initial diagnosis:");
        rule.getNotes().add("• The “clock” starts over");
        rule.getNotes().add("  • The greater than 5-year disease-free interval is computed from the date of the last known recurrence");
        rule.getNotes().add("  • The patient must have been disease-free for more than 5 years after the last recurrence of a breast tumor");
        rule.getNotes().add("Default to date of diagnosis to compute the >5-year time interval when:");
        rule.getNotes().add("  • There was no known recurrence AND/OR");
        rule.getNotes().add("  • Documentation is not available to confirm whether or not there was recurrence");
        rule.getNotes().add("The location and histology of the subsequent breast tumor is irrelevant. Breast tumors that occur more than 5 years apart are always multiple primaries.");
        rule.getNotes().add("Code a new primary even if the physician states “recurrence”.");
        _rules.add(rule);

        // Rule M9 - Abstract a single primary when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor
        // AND:
        //    • Both tumors have identical four-digit ICD-O histology codes (XXXX) OR
        //    • Tumors are NOS/NST and a subtype/variant of that NOS/NST (See Table 3 in Equivalent Terms and Definitions for NOS/NST and subtypes)
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_BREAST_GROUP_ID, "M9");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("Abstract the invasive tumor only.");
        rule.getNotes().add("When the in situ tumor has been abstracted and reported to the central registry:");
        rule.getNotes().add("  • Change behavior code on original abstract from /2 to /3 after the invasive is diagnosed");
        rule.getNotes().add("  • Change histology when in situ tumor is a NOS/NST and the invasive tumor is subtype/variant of that NOS/NST");
        rule.getNotes().add("  • Do not change date of diagnosis");
        rule.getNotes().add("  • When the case has been submitted to the central registry, report all changes");
        rule.getNotes().add(
                "The physician may stage both tumors because staging and determining multiple primaries are done for different reasons. Staging determines which course of treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).");
        _rules.add(rule);

        // Rule M10 - Abstract multiple primaries when an invasive tumor is diagnosed greater than 60 days after the diagnosis of an in situ tumor.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_BREAST_GROUP_ID, "M10");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The rules are hierarchical. Use this rule only when rules M4-M9 are not applicable.");
        rule.getNotes().add("The purpose of this rule is to ensure that the case is counted as an incidence (invasive) case when incidence data are analyzed.");
        rule.getNotes().add("Abstract as multiple primaries even if the medical record/physician states it is a disease recurrence or progression.");
        rule.getNotes().add("This rule only applies when the in situ tumor was treated OR there was a decision not to treat.");
        rule.getNotes().add("Default to previous rule (code a single primary, the invasive) when the patient is still being worked-up OR waiting for surgery.");
        _rules.add(rule);

        // Rule M11 - Abstract a single primary when the diagnosis is Paget disease with underlying:
        // • In situ or invasive carcinoma NST (duct/ductal) OR
        // • In situ or invasive lobular carcinoma
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_BREAST_GROUP_ID, "M11");
        rule.setQuestion("");
        rule.setReason("");
        _rules.add(rule);

        // Rule M12 - Abstract a single primary when there are multiple tumors (DCIS/duct/carcinoma NST and lobular carcinoma) in the same breast (same or multiple quadrants)/subsites which are:
        // • In situ and invasive
        //      One tumor is invasive and the other is in situ OR
        //      Both/all tumors are mixed in situ and invasive
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_BREAST_GROUP_ID, "M12");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("See Histology rules. Only the invasive histology is abstracted");
        rule.getNotes().add("  • In situ");
        rule.getNotes().add("     At least one tumor is DCIS and at least one tumor is in situ lobular carcinoma");
        rule.getNotes().add("     Both/all tumors are mixed DCIS and in situ lobular carcinoma");
        rule.getNotes().add("  • Invasive");
        rule.getNotes().add("     One tumor is carcinoma NST, the other is invasive lobular carcinoma OR");
        rule.getNotes().add("     Both/all tumors are mixed carcinoma NST and invasive lobular carcinoma");
        _rules.add(rule);

        // Rule M13 - Abstract a single primary when there are multiple tumors in the same breast which:
        // • Have identical ICD-O histology/morphology codes XXXX OR
        // • Are a NOS and a subtype/variant of the NOS
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_BREAST_GROUP_ID, "M13");
        rule.setQuestion("");
        rule.setReason("");
        _rules.add(rule);

        // Rule M14 - Abstract multiple primaries when there are multiple tumors with ICD-O histology codes that are different at the first (Xxxx), second (xXxx) or third (xxXx) number.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_BREAST_GROUP_ID, "M14");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The rules are hierarchical. Do not use this rule if any of the rules M4-M13 apply");
        _rules.add(rule);

        // Rule M15 - Abstract a single primary when tumors that do not meet any of the above criteria in rules M1-M14.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_BREAST_GROUP_ID, "M15");
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
