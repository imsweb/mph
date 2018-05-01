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

public class Mp2018NonMalignantCNSTumorsGroup extends MphGroup {

    /*
    Non-Malignant CNS Neoplasms
    Multiple Primary Rules
    C700, C701, C709, C710-C719, C720-C725, C728, C729, C751-C753

    Rule M5     Abstract multiple primaries when a non-malignant tumor /0 or /1 transforms into a malignant /3 tumor AND
                • The patient had a resection of the non-malignant tumor as part of the first course of treatment OR
                • It is unknown if the non-malignant tumor was resected during the first course of treatment.
                Note 1: See Table 8 for a list of histologic types of non-malignant tumors which have the greatest potential to transform to malignant behavior.
                Note 2: Use the Malignant CNS and Peripheral Nerves rules to abstract the second primary (the malignant /3 tumor).

    Rule M6     Abstract a single primary when the patient has bilateral
                • Acoustic neuromas/vestibular schwannoma 9560/0
                • Optic nerve gliomas 9380/0
                Note 1: The bilateral tumors may appear simultaneously (at the same time) OR the contralateral tumor may be diagnosed at any time following the original diagnosis.
                Note 2: When the bilateral tumors are diagnosed at different times, the physician may stage each tumor because staging and determining multiple primaries are done for different reasons. Staging determines which course of treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).

    Rule M7     Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 6, first or second column, in the Equivalent Terms and Definitions. Tumors may be
                • Simultaneous tumors OR
                • Original and subsequent tumors
                Note: “Different rows” means separate rows in the first column OR second column
                    • The first column is the specific or NOS histology term and code. Each row in the table is a distinctly different histology.
                    • The second column has synonyms for the specific or NOS histology term listed in the first column. A synonym is an alternate term for the histology in column 1, so when the synonyms are on different rows (different histologies) in Table 6 they are multiple primaries.

    Rule M8     Abstract multiple primaries when separate/non-contiguous tumors are different subtypes/variants in Column 3, Table 6 in the Equivalent Terms and Definitions. Tumors may be
                • Simultaneous tumors OR
                • Original and subsequent tumors
                Note: The tumors may be subtypes/variants of the same or different NOS histologies.
                    • Same NOS: Atypical meningioma 9539/1 and fibrous meningioma 9532/0 are both subtypes of meningioma NOS 9530 but are distinctly different histologies. Abstract multiple primaries.
                    • Different NOS: Melanotic schwannoma 9560/1 is a subtype of schwannoma NOS 9560/0; papillary craniopharyngioma 9352/1 is a subtype of craniopharyngioma 9350/1. They are distinctly different histologies. Abstract multiple primaries.

    Rule M9     Abstract a single primary when there is a NOS and a subtype/variant of that NOS in the same CNS site code (same second, third and fourth digit CXXX).
                Note 1: See Table 6 in the Equivalent Terms and Definitions for NOS and subtypes/variants.
                Note 2: NOS and subtypes/variants are:
                    • Choroid plexus papilloma 9390/0 and subtypes/variants of choroid plexus papilloma NOS
                    • Craniopharyngioma 9350/1 and subtypes/variants of craniopharyngioma NOS
                    • Lipoma 8860/0 and subtypes/variants of lipoma NOS
                    • Meningeal melanocytosis 8728/0 and subtypes/variants of meningeal melanocytosis NOS
                    • Meningioma 9530/0 and subtypes/variants of meningioma NOS
                    • Myofibroblastoma 8825/0 and subtypes/variants of myofibroblastoma NOS
                    • Neurofibroma 9540/0 and subtypes/variants of neurofibroma NOS
                    • Schwannoma 9560/0 and subtypes/variants of schwannoma NOS
                    • Solitary fibrous tumor Grade 1 8815/0 and subtypes/variants of solitary fibrous tumor Grade 1 NOS

    Rule M10    Abstract a single primary when two or more separate, non-contiguous meningiomas arise:
                • On the same side (left or right) of the cranial meninges OR
                • On both sides (left and right) of the cranial meninges OR
                • In the midline AND in either the right or left cranial meninges
                Note 1: This rule applies ONLY to meningiomas.
                Note 2: Laterality is irrelevant.

    Rule M11    Abstract multiple primaries when multiple tumors are present in any of the following sites or subsites:
                • Brain C71_ AND any other part of CNS
                • Cerebral meninges C700 AND spinal meninges C701
                • Cranial nerves C721-C725 AND any other part of the CNS
                • Meninges of cranial or peripheral nerves C709 AND any other part of the CNS

    Rule M12    Abstract a single primary when there are multiple tumors in the brain.
                Note 1: These rules are hierarchical. Use this rule ONLY when the previous rules apply.
                Note 2: The physician may stage each tumor because staging and determining multiple primaries are done for different reasons. Staging determines which course of treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).

    Rule M13    Abstract multiple primaries when there are multiple tumors with histology codes that differ at the first (Xxxx), second (xXxx) AND/OR third (xxXx) digit.
                Note 1: These rules are hierarchical. Use this rule ONLY when the previous rules apply.
                Note 2: See the rules for different subtypes/variants, and NOS and a subtype/variant.

    Rule M14    Abstract a single primary when the tumors do not meet any of the above criteria.
                Note: Use this rule ONLY when the previous rules do NOT apply.

    */

    // TODO
    public Mp2018NonMalignantCNSTumorsGroup() {
        super(MphConstants.MP_2007_NON_MALIGNANT_CNS_TUMORS_GROUP_ID, MphConstants.MP_2007_NON_MALIGNANT_CNS_TUMORS_GROUP_NAME, "C700-C701, C709-C725, C728-C729, C751-C753", null, null,
                "9590-9989,9140", "0-1", "2018-9999");

        // Rule M5 - Abstract multiple primaries when a non-malignant tumor /0 or /1 transforms into a malignant /3 tumor AND
        // • The patient had a resection of the non-malignant tumor as part of the first course of treatment OR
        // • It is unknown if the non-malignant tumor was resected during the first course of treatment.
        // TODO
        MphRule rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2007_NON_MALIGNANT_CNS_TUMORS_GROUP_ID, "M5");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("See Table 8 for a list of histologic types of non-malignant tumors which have the greatest potential to transform to malignant behavior.");
        rule.getNotes().add("Use the Malignant CNS and Peripheral Nerves rules to abstract the second primary (the malignant /3 tumor).");
        _rules.add(rule);

        // Rule M6 - Abstract a single primary when the patient has bilateral
        // • Acoustic neuromas/vestibular schwannoma 9560/0
        // • Optic nerve gliomas 9380/0
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2007_NON_MALIGNANT_CNS_TUMORS_GROUP_ID, "M6");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The bilateral tumors may appear simultaneously (at the same time) OR the contralateral tumor may be diagnosed at any time following the original diagnosis.");
        rule.getNotes().add(
                "When the bilateral tumors are diagnosed at different times, the physician may stage each tumor because staging and determining multiple primaries are done for different reasons. Staging determines which course of treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).");
        _rules.add(rule);

        // Rule M7 - Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 6, first or second column, in the Equivalent Terms and Definitions. Tumors may be
        // • Simultaneous tumors OR
        // • Original and subsequent tumors
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2007_NON_MALIGNANT_CNS_TUMORS_GROUP_ID, "M7");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("“Different rows” means separate rows in the first column OR second column");
        rule.getNotes().add("  • The first column is the specific or NOS histology term and code. Each row in the table is a distinctly different histology.");
        rule.getNotes().add(
                "  • The second column has synonyms for the specific or NOS histology term listed in the first column. A synonym is an alternate term for the histology in column 1, so when the synonyms are on different rows (different histologies) in Table 6 they are multiple primaries.");
        _rules.add(rule);

        // Rule M8 - Abstract multiple primaries when separate/non-contiguous tumors are different subtypes/variants in Column 3, Table 6 in the Equivalent Terms and Definitions. Tumors may be
        // • Simultaneous tumors OR
        // • Original and subsequent tumors
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2007_NON_MALIGNANT_CNS_TUMORS_GROUP_ID, "M8");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The tumors may be subtypes/variants of the same or different NOS histologies.");
        rule.getNotes().add(
                "  • Same NOS: Atypical meningioma 9539/1 and fibrous meningioma 9532/0 are both subtypes of meningioma NOS 9530 but are distinctly different histologies. Abstract multiple primaries.");
        rule.getNotes().add(
                "  • Different NOS: Melanotic schwannoma 9560/1 is a subtype of schwannoma NOS 9560/0; papillary craniopharyngioma 9352/1 is a subtype of craniopharyngioma 9350/1. They are distinctly different histologies. Abstract multiple primaries.");
        _rules.add(rule);

        // Rule M9 - Abstract a single primary when there is a NOS and a subtype/variant of that NOS in the same CNS site code (same second, third and fourth digit CXXX).
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2007_NON_MALIGNANT_CNS_TUMORS_GROUP_ID, "M9");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("See Table 6 in the Equivalent Terms and Definitions for NOS and subtypes/variants.");
        rule.getNotes().add("NOS and subtypes/variants are:");
        rule.getNotes().add("  • Choroid plexus papilloma 9390/0 and subtypes/variants of choroid plexus papilloma NOS");
        rule.getNotes().add("  • Craniopharyngioma 9350/1 and subtypes/variants of craniopharyngioma NOS");
        rule.getNotes().add("  • Lipoma 8860/0 and subtypes/variants of lipoma NOS");
        rule.getNotes().add("  • Meningeal melanocytosis 8728/0 and subtypes/variants of meningeal melanocytosis NOS");
        rule.getNotes().add("  • Meningioma 9530/0 and subtypes/variants of meningioma NOS");
        rule.getNotes().add("  • Myofibroblastoma 8825/0 and subtypes/variants of myofibroblastoma NOS");
        rule.getNotes().add("  • Neurofibroma 9540/0 and subtypes/variants of neurofibroma NOS");
        rule.getNotes().add("  • Schwannoma 9560/0 and subtypes/variants of schwannoma NOS");
        rule.getNotes().add("  • Solitary fibrous tumor Grade 1 8815/0 and subtypes/variants of solitary fibrous tumor Grade 1 NOS");
        _rules.add(rule);

        // Rule M10 - Abstract a single primary when two or more separate, non-contiguous meningiomas arise:
        // • On the same side (left or right) of the cranial meninges OR
        // • On both sides (left and right) of the cranial meninges OR
        // • In the midline AND in either the right or left cranial meninges
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2007_NON_MALIGNANT_CNS_TUMORS_GROUP_ID, "M10");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("This rule applies ONLY to meningiomas.");
        rule.getNotes().add("Laterality is irrelevant.");
        _rules.add(rule);

        // Rule M11 - Abstract multiple primaries when multiple tumors are present in any of the following sites or subsites:
        // • Brain C71_ AND any other part of CNS
        // • Cerebral meninges C700 AND spinal meninges C701
        // • Cranial nerves C721-C725 AND any other part of the CNS
        // • Meninges of cranial or peripheral nerves C709 AND any other part of the CNS
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2007_NON_MALIGNANT_CNS_TUMORS_GROUP_ID, "M11");
        rule.setQuestion("");
        rule.setReason("");
        _rules.add(rule);

        // Rule M12 - Abstract a single primary when there are multiple tumors in the brain.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2007_NON_MALIGNANT_CNS_TUMORS_GROUP_ID, "M12");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("These rules are hierarchical. Use this rule ONLY when the previous rules apply.");
        rule.getNotes().add(
                "The physician may stage each tumor because staging and determining multiple primaries are done for different reasons. Staging determines which course of treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).");
        _rules.add(rule);

        // Rule M13 - Abstract multiple primaries when there are multiple tumors with histology codes that differ at the first (Xxxx), second (xXxx) AND/OR third (xxXx) digit.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2007_NON_MALIGNANT_CNS_TUMORS_GROUP_ID, "M13");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("These rules are hierarchical. Use this rule ONLY when the previous rules apply.");
        rule.getNotes().add("See the rules for different subtypes/variants, and NOS and a subtype/variant.");
        _rules.add(rule);

        // Rule M14 - Abstract a single primary when the tumors do not meet any of the above criteria.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2007_NON_MALIGNANT_CNS_TUMORS_GROUP_ID, "M14");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("Use this rule ONLY when the previous rules do NOT apply.");
        _rules.add(rule);



        /*
        // M3 - An invasive brain tumor (/3) and either a benign brain tumor (/0) or an uncertain/borderline brain tumor (/1) are always multiple primaries.
        MphRule rule = new MphRule(MphConstants.MP_2007_BENIGN_BRAIN_GROUP_ID, "M3") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                //This will never happen, since the two conditions belong to different cancer group.
                return new TempRuleResult();
            }
        };
        rule.setQuestion("Is there an invasive tumor (/3) and either a benign brain tumor (/0) or an uncertain/borderline brain tumor (/1)?");
        rule.setReason("An invasive brain tumor (/3) and either a benign brain tumor (/0) or an uncertain/borderline brain tumor (/1) are always multiple primaries.");
        _rules.add(rule);

        // M4 - Tumors with ICD-O-3 topography codes that are different at the second (C?xx) and/or third characters (Cx?x), or fourth (Cxx?) are multiple primaries.
        rule = new MphRule(MphConstants.MP_2007_BENIGN_BRAIN_GROUP_ID, "M4") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (!i1.getPrimarySite().equals(i2.getPrimarySite()))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Are there tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx), third character (Cx?x) and/or fourth character (Cxx?)?");
        rule.setReason("Tumors with ICD-O-3 topography codes that are different at the second (C?xx) and/or third characters (Cx?x), or fourth (Cxx?) are multiple primaries.");
        _rules.add(rule);

        // M5 - Tumors on both sides (left and right) of a paired site (Table 1) are multiple primaries.
        rule = new MphRule(MphConstants.MP_2007_BENIGN_BRAIN_GROUP_ID, "M5") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                List<String> pairedSites = Arrays.asList("C700", "C710", "C711", "C712", "C713", "C714", "C722", "C723", "C724", "C725");
                if (GroupUtility.isPairedSites(i1.getPrimarySite(), i2.getPrimarySite(), pairedSites)) {
                    if (!GroupUtility.validPairedSiteLaterality(i1.getLaterality(), i2.getLaterality())) {
                        result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                        result.setMessage(
                                "Unable to apply Rule" + this.getStep() + " of " + this.getGroupId() + ". Valid and known laterality for paired sites of " + this.getGroupId()
                                        + " should be provided.");
                    }
                    else if (GroupUtility.areOppositeSides(i1.getLaterality(), i2.getLaterality()))
                        result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                }
                return result;
            }
        };
        rule.setQuestion("Are there tumors on both sides (left and right) of a paired site?");
        rule.setReason("Tumors on both sides (left and right) of a paired site are multiple primaries.");
        _rules.add(rule);

        // M6 - An atypical choroid plexus papilloma (9390/1) following a choroid plexus papilloma, NOS (9390/0) is a single primary.
        rule = new MphRule(MphConstants.MP_2007_BENIGN_BRAIN_GROUP_ID, "M6") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (MphConstants.PAPILLOMA.equals(i1.getHistology()) && MphConstants.PAPILLOMA.equals(i2.getHistology()) && ((MphConstants.UNCERTAIN.equals(i1.getBehavior()) && MphConstants.BENIGN
                        .equals(i2.getBehavior())) || (MphConstants.UNCERTAIN.equals(i2.getBehavior()) && MphConstants.BENIGN.equals(i1.getBehavior())))) {
                    int laterDiagnosedTumor = GroupUtility.compareDxDate(i1, i2);
                    if (-1 == laterDiagnosedTumor) { //If impossible to decide which tumor is diagnosed later
                        result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        result.setMessage("Unable to apply Rule" + this.getStep() + " of " + this.getGroupId() + ". Known diagnosis date should be provided.");
                    }
                    else if (1 == laterDiagnosedTumor && MphConstants.UNCERTAIN.equals(i1.getBehavior()) && MphConstants.BENIGN.equals(i2.getBehavior()))
                        result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                    else if (2 == laterDiagnosedTumor && MphConstants.UNCERTAIN.equals(i2.getBehavior()) && MphConstants.BENIGN.equals(i1.getBehavior()))
                        result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                }
                return result;
            }
        };
        rule.setQuestion("Is there an atypicalchoroid plexuspapilloma (9390/1) following achoroid plexus papilloma,NOS (9390/0)?");
        rule.setReason("An atypical choroid plexus papilloma (9390/1) following a choroid plexus papilloma, NOS (9390/0) is a single primary.");
        rule.getNotes().add("Do not code progression of disease as multiple primaries.");
        _rules.add(rule);

        // M7 - A neurofibromatosis, NOS (9540/1) following a neurofibroma, NOS (9540/0) is a single primary.
        rule = new MphRule(MphConstants.MP_2007_BENIGN_BRAIN_GROUP_ID, "M7") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (MphConstants.NEUROFIBROMATOSIS.equals(i1.getHistology()) && MphConstants.NEUROFIBROMATOSIS.equals(i2.getHistology()) && ((MphConstants.UNCERTAIN.equals(i1.getBehavior())
                        && MphConstants.BENIGN.equals(i2.getBehavior())) || (MphConstants.UNCERTAIN.equals(i2.getBehavior()) && MphConstants.BENIGN.equals(i1.getBehavior())))) {
                    int laterDiagnosedTumor = GroupUtility.compareDxDate(i1, i2);
                    if (-1 == laterDiagnosedTumor) { //If impossible to decide which tumor is diagnosed first
                        result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        result.setMessage("Unable to apply Rule" + this.getStep() + " of " + this.getGroupId() + ". Known diagnosis date should be provided.");
                    }
                    else if (1 == laterDiagnosedTumor && MphConstants.UNCERTAIN.equals(i1.getBehavior()) && MphConstants.BENIGN.equals(i2.getBehavior()))
                        result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                    else if (2 == laterDiagnosedTumor && MphConstants.UNCERTAIN.equals(i2.getBehavior()) && MphConstants.BENIGN.equals(i1.getBehavior()))
                        result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                }
                return result;
            }
        };
        rule.setQuestion("Is there a neurofibromatosis, NOS (9540/1) following a neurofibroma, NOS (9540/0)?");
        rule.setReason("A neurofibromatosis, NOS (9540/1) following a neurofibroma, NOS (9540/0) is a single primary.");
        rule.getNotes().add("Do not code progression of disease as multiple primaries.");
        _rules.add(rule);

        // M8 - Tumors with two or more histologic types on the same branch in Chart 1 are a single primary.
        rule = new MphRule(MphConstants.MP_2007_BENIGN_BRAIN_GROUP_ID, "M8") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                String icd1 = i1.getHistology() + "/" + i1.getBehavior(), icd2 = i2.getHistology() + "/" + i2.getBehavior();
                String branch1 = MphConstants.BENIGN_BRAIN_2007_CHART1.get(icd1) != null ? MphConstants.BENIGN_BRAIN_2007_CHART1.get(icd1) : MphConstants.BENIGN_BRAIN_2007_CHART1.get(
                        i1.getHistology());
                String branch2 = MphConstants.BENIGN_BRAIN_2007_CHART1.get(icd2) != null ? MphConstants.BENIGN_BRAIN_2007_CHART1.get(icd2) : MphConstants.BENIGN_BRAIN_2007_CHART1.get(
                        i2.getHistology());
                if (branch1 != null && branch2 != null && branch1.equals(branch2))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);

                return result;
            }
        };
        rule.setQuestion("Do the tumors have two or more histologic types on the same branch in Chart 1?");
        rule.setReason("Tumors with two or more histologic types on the same branch in Chart 1 are a single primary.");
        _rules.add(rule);

        // M9 - Tumors with multiple histologic types on different branches in Chart 1 are multiple primaries.
        rule = new MphRule(MphConstants.MP_2007_BENIGN_BRAIN_GROUP_ID, "M9") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                String icd1 = i1.getHistology() + "/" + i1.getBehavior(), icd2 = i2.getHistology() + "/" + i2.getBehavior();
                String branch1 = MphConstants.BENIGN_BRAIN_2007_CHART1.get(icd1) != null ? MphConstants.BENIGN_BRAIN_2007_CHART1.get(icd1) : MphConstants.BENIGN_BRAIN_2007_CHART1.get(
                        i1.getHistology());
                String branch2 = MphConstants.BENIGN_BRAIN_2007_CHART1.get(icd2) != null ? MphConstants.BENIGN_BRAIN_2007_CHART1.get(icd2) : MphConstants.BENIGN_BRAIN_2007_CHART1.get(
                        i2.getHistology());
                if (branch1 != null && branch2 != null && !branch1.equals(branch2))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);

                return result;
            }
        };
        rule.setQuestion("Do the tumors have multiple histologic types on different branches in Chart 1?");
        rule.setReason("Tumors with multiple histologic types on different branches in Chart 1 are multiple primaries.");
        _rules.add(rule);

        // M10 - Tumors with two or more histologic types and at least one of the histologies is not listed in Chart 1 are multiple primaries.
        rule = new MphRule(MphConstants.MP_2007_BENIGN_BRAIN_GROUP_ID, "M10") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                String icd1 = i1.getHistology() + "/" + i1.getBehavior(), icd2 = i2.getHistology() + "/" + i2.getBehavior();
                String branch1 = MphConstants.BENIGN_BRAIN_2007_CHART1.get(icd1) != null ? MphConstants.BENIGN_BRAIN_2007_CHART1.get(icd1) : MphConstants.BENIGN_BRAIN_2007_CHART1.get(
                        i1.getHistology());
                String branch2 = MphConstants.BENIGN_BRAIN_2007_CHART1.get(icd2) != null ? MphConstants.BENIGN_BRAIN_2007_CHART1.get(icd2) : MphConstants.BENIGN_BRAIN_2007_CHART1.get(
                        i2.getHistology());
                //This rule is used only when one histology code is listed in chart and the other not, see note for M11
                if ((branch1 != null && branch2 == null) || (branch2 != null && branch1 == null))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Do the tumors have two or more histologic types and at least one of the histologies is not listed in Chart 1?");
        rule.setReason("Tumors with two or more histologic types and at least one of the histologies is not listed in Chart 1 are multiple primaries.");
        _rules.add(rule);

        //M11- Tumors with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.
        rule = new MphRuleHistologyCode(MphConstants.MP_2007_BENIGN_BRAIN_GROUP_ID, "M11");
        rule.getNotes().add("Use this rule when none of the histology codes are listed in Chart 1.");
        _rules.add(rule);

        //M12- Tumors that do not meet any of the criteria are abstracted as a single primary.
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2007_BENIGN_BRAIN_GROUP_ID, "M12");
        rule.getNotes().add("Timing is not used to determine multiple primaries for benign and borderline intracranial and CNS tumors.");
        rule.getExamples().add("Tumors in the same site with the same histology (Chart 1) and the same laterality as the original tumor are a single primary.");
        rule.getExamples().add("Tumors in the same site with the same histology (Chart 1) and it is unknown if laterality is the same as the original tumor are a single primary.");
        rule.getExamples().add("Tumors in the same site and same laterality with histology codes not listed in Chart 1 that have the same first three numbers are a single primary.");
        _rules.add(rule);
        */

    }
}


