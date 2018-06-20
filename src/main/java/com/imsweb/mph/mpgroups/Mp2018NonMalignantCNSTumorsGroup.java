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

    Note: Multiple tumors may be a single primary or multiple primaries.

    IMPORTANT: The major difference between M3 and M5 is:
    M3:  No resection at original diagnosis AND, when subsequently resected, pathology proves malignant behavior
    M5: Tumor resected at original diagnosis. The recurrence or de novo (new) tumor is malignant

    Rule M5	Abstract multiple primaries  when a non-malignant tumor /0 or /1 transforms into a malignant /3 tumor AND
        •	The patient had a resection of the non-malignant tumor as part of the first course of treatment OR
        •	It is unknown if the non-malignant tumor was resected during the first course of treatment.
        Note 1:	See Table 8 for a list of histologic types of non-malignant tumors which have the greatest potential to transform to malignant behavior.
        Note 2:	Use the Malignant CNS and Peripheral Nerves rules to abstract the second primary (the malignant /3 tumor).

    Rule M6	Abstract a single primary when the patient has bilateral
        •	Acoustic neuromas/vestibular schwannoma 9560/0
        •	Optic nerve gliomas 9380/0
        Note 1:	The bilateral tumors may appear simultaneously (at the same time) OR the contralateral tumor may be diagnosed at any time following the original diagnosis.
        Note 2:	When the bilateral tumors are diagnosed at different times, the physician may stage each tumor because staging and determining multiple primaries are done for different reasons. Staging determines which course of treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).

    Rule M7	Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 6 in the Equivalent Terms and Definitions. Tumors may be
        •	Simultaneous OR
        •	Original and subsequent
        Note:	Each row in the table is a distinctly different histology.

    Rule M8	Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 6 in the Equivalent Terms and Definitions. Tumors may be
        •	Simultaneous OR
        •	Original and subsequent
        Note: 	The tumors may be subtypes/variants of the same or different NOS histologies.
        •	Same NOS: Atypical meningioma 9539/1 and fibrous meningioma 9532/0 are both subtypes of meningioma NOS 9530 but are distinctly different histologies. Abstract multiple primaries.
        •	Different NOS: Melanotic schwannoma 9560/1 is a subtype of schwannoma NOS 9560/0; papillary craniopharyngioma 9352/1 is a subtype of craniopharyngioma 9350/1. They are distinctly different histologies. Abstract multiple primaries.

    Rule M9	Abstract a single primary when there is a NOS and a subtype/variant of that NOS in the same CNS site code (same second, third and fourth digit CXXX).
        Note 1:	See Table 6 in the Equivalent Terms and Definitions for NOS and subtypes/variants.
        Note 2:	NOS and subtypes/variants are:
        •	Choroid plexus papilloma 9390/0 and subtypes/variants of choroid plexus papilloma
        •	Craniopharyngioma 9350/1 and subtypes/variants of craniopharyngioma
        •	Lipoma 8860/0 and subtypes/variants of lipoma
        •	Meningeal melanocytosis 8728/0 and subtypes/variants of meningeal melanocytosis
        •	Meningioma 9530/0 and subtypes/variants of meningioma
        •	Myofibroblastoma 8825/0 and subtypes/variants of myofibroblastoma
        •	Neurofibroma 9540/0 and subtypes/variants of neurofibroma
        •	Schwannoma 9560/0 and subtypes/variants of schwannoma
        •	Solitary fibrous tumor Grade 1 8815/0 and subtypes/variants of solitary fibrous tumor Grade 1
     
    Rule M10	Abstract a single primary when two or more separate, non-contiguous meningiomas arise:
        •	On the same side (left or right) of the cranial meninges OR
        •	On both sides (left and right) of the cranial meninges OR
        •	In the midline AND in either the right or left cranial meninges
        Note 1:	This rule applies ONLY to meningiomas.
        Note 2:	Laterality is irrelevant.

    Rule M11	Abstract multiple primaries when multiple tumors are present in any of the following sites or subsites:
        •	Brain C71_ AND any other part of CNS
        •	Cerebral meninges C700 AND spinal meninges C701
        •	Cranial nerves C721-C725 AND any other part of the CNS
        •	Meninges of cranial or peripheral nerves C709 AND any other part of the CNS

    Rule M12	Abstract a single primary when there are multiple tumors in the brain.
        Note 1:	These rules are hierarchical.  Use this rule ONLY when the previous rules do not apply.
        Note 2:	The physician may stage each tumor because staging and determining multiple primaries are done for different reasons. Staging determines which course of treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).

    Rule M13	Abstract a single primary when the tumors do not meet any of the above criteria.
        Note:  These rules are hierarchical.  Use this rule ONLY when the previous rules do not apply.
    */

    // Non-Malignant CNS Neoplasms
    // Multiple Primary Rules
    // C700, C701, C709, C710-C719, C720-C725, C728, C729, C751-C753

    public Mp2018NonMalignantCNSTumorsGroup() {
        super(MphConstants.MP_2018_NON_MALIGNANT_CNS_TUMORS_GROUP_ID, MphConstants.MP_2018_NON_MALIGNANT_CNS_TUMORS_GROUP_NAME,
                "C700, C701, C709, C710-C719, C720-C725, C728, C729, C751-C753", null, null,
                "", "0-1", "2018-9999");

        // Rule M5	Abstract multiple primaries  when a non-malignant tumor /0 or /1 transforms into a malignant /3 tumor AND
        // •	The patient had a resection of the non-malignant tumor as part of the first course of treatment OR
        // •	It is unknown if the non-malignant tumor was resected during the first course of treatment.
        // TODO
        MphRule rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_NON_MALIGNANT_CNS_TUMORS_GROUP_ID, "M5");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("See Table 8 for a list of histologic types of non-malignant tumors which have the greatest potential to transform to malignant behavior.");
        rule.getNotes().add("Use the Malignant CNS and Peripheral Nerves rules to abstract the second primary (the malignant /3 tumor).");
        _rules.add(rule);

        // Rule M6	Abstract a single primary when the patient has bilateral
        // •	Acoustic neuromas/vestibular schwannoma 9560/0
        // •	Optic nerve gliomas 9380/0
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_NON_MALIGNANT_CNS_TUMORS_GROUP_ID, "M6");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The bilateral tumors may appear simultaneously (at the same time) OR the contralateral tumor may be diagnosed at any time following the original diagnosis.");
        rule.getNotes().add("When the bilateral tumors are diagnosed at different times, the physician may stage each tumor because staging and determining multiple primaries are done for different reasons. Staging determines which course of treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).");
        _rules.add(rule);

        // Rule M7	Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 6 in the Equivalent Terms and Definitions. Tumors may be
        // •	Simultaneous OR
        // •	Original and subsequent
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_NON_MALIGNANT_CNS_TUMORS_GROUP_ID, "M7");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("Each row in the table is a distinctly different histology.");
        _rules.add(rule);

        // Rule M8	Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 6 in the Equivalent Terms and Definitions. Tumors may be
        // •	Simultaneous OR
        // •	Original and subsequent
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_NON_MALIGNANT_CNS_TUMORS_GROUP_ID, "M8");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The tumors may be subtypes/variants of the same or different NOS histologies.");
        rule.getNotes().add("  • Same NOS: Atypical meningioma 9539/1 and fibrous meningioma 9532/0 are both subtypes of meningioma NOS 9530 but are distinctly different histologies. Abstract multiple primaries.");
        rule.getNotes().add("  • Different NOS: Melanotic schwannoma 9560/1 is a subtype of schwannoma NOS 9560/0; papillary craniopharyngioma 9352/1 is a subtype of craniopharyngioma 9350/1. They are distinctly different histologies. Abstract multiple primaries.");
        _rules.add(rule);

        // Rule M9	Abstract a single primary when there is a NOS and a subtype/variant of that NOS in the same CNS site code (same second, third and fourth digit CXXX).
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_NON_MALIGNANT_CNS_TUMORS_GROUP_ID, "M9");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("See Table 6 in the Equivalent Terms and Definitions for NOS and subtypes/variants.");
        rule.getNotes().add("NOS and subtypes/variants are:");
        rule.getNotes().add("  • Choroid plexus papilloma 9390/0 and subtypes/variants of choroid plexus papilloma");
        rule.getNotes().add("  • Craniopharyngioma 9350/1 and subtypes/variants of craniopharyngioma");
        rule.getNotes().add("  • Lipoma 8860/0 and subtypes/variants of lipoma");
        rule.getNotes().add("  • Meningeal melanocytosis 8728/0 and subtypes/variants of meningeal melanocytosis");
        rule.getNotes().add("  • Meningioma 9530/0 and subtypes/variants of meningioma");
        rule.getNotes().add("  • Myofibroblastoma 8825/0 and subtypes/variants of myofibroblastoma");
        rule.getNotes().add("  • Neurofibroma 9540/0 and subtypes/variants of neurofibroma");
        rule.getNotes().add("  • Schwannoma 9560/0 and subtypes/variants of schwannoma");
        rule.getNotes().add("  • Solitary fibrous tumor Grade 1 8815/0 and subtypes/variants of solitary fibrous tumor Grade 1");
        _rules.add(rule);

        // Rule M10	Abstract a single primary when two or more separate, non-contiguous meningiomas arise:
        // •	On the same side (left or right) of the cranial meninges OR
        // •	On both sides (left and right) of the cranial meninges OR
        // •	In the midline AND in either the right or left cranial meninges
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_NON_MALIGNANT_CNS_TUMORS_GROUP_ID, "M10");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("This rule applies ONLY to meningiomas.");
        rule.getNotes().add("Laterality is irrelevant.");
        _rules.add(rule);

        // Rule M11	Abstract multiple primaries when multiple tumors are present in any of the following sites or subsites:
        // •	Brain C71_ AND any other part of CNS
        // •	Cerebral meninges C700 AND spinal meninges C701
        // •	Cranial nerves C721-C725 AND any other part of the CNS
        // •	Meninges of cranial or peripheral nerves C709 AND any other part of the CNS
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_NON_MALIGNANT_CNS_TUMORS_GROUP_ID, "M11");
        rule.setQuestion("");
        rule.setReason("");
        _rules.add(rule);

        // Rule M12	Abstract a single primary when there are multiple tumors in the brain.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_NON_MALIGNANT_CNS_TUMORS_GROUP_ID, "M12");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("These rules are hierarchical.  Use this rule ONLY when the previous rules do not apply.");
        rule.getNotes().add("The physician may stage each tumor because staging and determining multiple primaries are done for different reasons. Staging determines which course of treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).");
        _rules.add(rule);

        // Rule M13	Abstract a single primary when the tumors do not meet any of the above criteria.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_NON_MALIGNANT_CNS_TUMORS_GROUP_ID, "M13");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("These rules are hierarchical.  Use this rule ONLY when the previous rules do not apply.");
        _rules.add(rule);
    }
}


