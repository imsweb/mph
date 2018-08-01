/*
 * Copyright (C) 2018 Information Management Services, Inc.
 */
package com.imsweb.mph.mpgroups;

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

public class Mp2018NonMalignantCNSTumorsGroup extends MphGroup {

    /*
    Non-Malignant CNS Multiple Primary Rules
    C700, C701, C709, C710-C719, C721-C725, C728, C729, C751-C753
    Peripheral nerves C470, C473, C475, C476 (for nerve roots only)

    Rule M5	Abstract a single primary when the patient has:
        •	Acoustic neuromas/ vestibular schwannomas 9560/0
        •	Optic gliomas/pilocytic astrocytomas 9421/1
        Note 1:	The bilateral tumors may appear simultaneously (at the same time) OR the contralateral tumor may be diagnosed at any time following the original diagnosis.
        Note 2:	When the bilateral tumors are diagnosed at different times, the physician may stage each tumor because staging and determining multiple primaries are done for different reasons. Staging determines which course of treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).

    Rule M6	Abstract multiple primaries when separate, non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 6 in the Equivalent Terms and Definitions. Timing is irrelevant.
        Note: 	The tumors may be subtypes/variants of the same or different NOS histologies.
        •	Same NOS: Atypical meningioma 9539/1 and fibrous meningioma 9532/0 are both subtypes of meningioma NOS 9530 but are distinctly different histologies. Abstract multiple primaries.
        •	Different NOS: Melanotic schwannoma 9560/1 is a subtype of schwannoma NOS 9560/0; papillary craniopharyngioma 9352/1 is a subtype of craniopharyngioma 9350/1. They are distinctly different histologies. Abstract multiple primaries.
     
    Rule M7	Abstract a single primary when separate, non-contiguous tumors are on the same row in Table 6 in the Equivalent Terms and Definitions.  Timing is irrelevant.
        Note: 	The same row means the tumors are:
        •	The same histology (same four-digit ICD-O code) OR
        •	One is the preferred term (column 1) and the other is a synonym for the preferred term (column 2) OR
        •	A NOS (column 1/column 2) and the other is a subtype/variant of that NOS (column 3)

    Rule M8	Abstract multiple primaries when separate, non-contiguous tumors are on different rows in Table 6 in the Equivalent Terms and Definitions. Timing is irrelevant.
        Note:	Each row in the table is a distinctly different histology.

    Rule M9	Abstract a single primary when there is a NOS and a subtype/variant of that NOS in the same CNS site (same second, third and fourth digit CXXX).
        Note 1:	See Table 6 in the Equivalent Terms and Definitions for NOS and subtypes/variants.
        Note 2:	NOS and subtypes/variants are:
        •	Choroid plexus papilloma 9390/0 and a subtype/variant of choroid plexus papilloma
        •	Craniopharyngioma 9350/1 and a subtype/variant of craniopharyngioma
        •	Gangliocytoma 9492/0 and a subtype/variant of craniopharyngioma
        •	Lipoma 8860/0 and a subtype/variant of lipoma
        •	Meningeal melanocytosis 8728/0 and a subtype/variant of meningeal melanocytosis
        •	Meningioma 9530/0 and a subtype/variant of meningioma
        •	Myofibroblastoma 8825/0 and a subtype/variant of myofibroblastoma
        •	Neurofibroma 9540/0 and a subtype/variant of neurofibroma
        •	Schwannoma 9560/0 and a subtype/variant of schwannoma
        •	Solitary fibrous tumor WHO Grade 1 8815/0 and a subtype/variant of solitary fibrous tumor WHO Grade 1

    Rule M10	Abstract multiple primaries when a malignant tumor /3 occurs after a non-malignant tumor /0 or /1 AND:
        •	The patient had a resection of the non-malignant tumor OR
        •	It is unknown/not documented whether a resection was done
     
    Rule M11	Abstract a single primary when two or more separate, non-contiguous meningiomas arise in the cranial meninges.  Laterality is irrelevant and may be any of the following combinations:
        •	The same laterality (left or right) of the cranial meninges
        •	Bilateral (both left and right) cranial meninges
        •	The midline AND in either the right or left cranial meninges
        Note:	This rule applies ONLY to meningiomas.

    Rule M12	Abstract a single primary when there are separate, non-contiguous tumors in the brain (multicentric/multifocal).  Tumors may be any of the following combinations:
        •	In the same lobe; for example, two tumors in right temporal lobe C712 (same site code)
        •	Different lateralities of the same lobe; for example, left and right frontal lobes C711 (same site code)
        •	In different lobes; for example, parietal lobe C713 and occipital lobe C714 (different site codes)
        Note 1:	Metastases are never used to determine multiple primaries. Seeding metastasis is often noted in ependymomas.
        Note 2:	Hereditary syndromes frequently exhibit multiple tumors, including the following:
            •	Neurofibromatosis type 1 (NF1)
                	Gliomas
                	Malignant peripheral nerve sheath tumors (MPNST)
            •	Neurofibromatosis type 2 (NF2)
                	Anaplastic ependymomas
                	Meningiomas
                	Vestibular schwannomas
        Note 3:	This is a change from/clarification to previous rules.
        Note 4:	These rules are hierarchical.  Use this rule ONLY when the previous rules do not apply.
        Note 5:	The physician may stage each tumor because staging and determining multiple primaries are done for different reasons. Staging determines which course of treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).
     
    Rule M13	Abstract multiple primaries when multiple tumors are present in any of the following sites or subsites:
        •	Brain C710-C719 AND any other part of CNS
        •	Cerebral meninges C700 AND spinal meninges C701
        •	Cerebral meninges C700 AND any other part of CNS
        •	Cranial nerves C721-C725 AND any other part of the CNS
        •	Meninges of cranial or peripheral nerves C709 AND any other part of the CNS
        •	Spinal meninges C701 AND any other part of CNS

    Rule M14	Abstract a single primary when the tumors do not meet any of the above criteria.
        Note:  These rules are hierarchical.  Use this rule ONLY when the previous rules do not apply.
    */

    // Non-Malignant CNS Multiple Primary Rules
    // C700, C701, C709, C710-C719, C721-C725, C728, C729, C751-C753
    // Peripheral nerves C470, C473, C475, C476 (for nerve roots only)

    public Mp2018NonMalignantCNSTumorsGroup() {
        super(MphConstants.MP_2018_NON_MALIGNANT_CNS_TUMORS_GROUP_ID, MphConstants.MP_2018_NON_MALIGNANT_CNS_TUMORS_GROUP_NAME,
                "C700, C701, C709, C710-C719, C721-C725, C728, C729, C751-C753, C470, C473, C475, C476", null, null,
                "", "0-1, 3", "2018-9999");

        // Rule M5	Abstract a single primary when the patient has:
        // •	Acoustic neuromas/ vestibular schwannomas 9560/0
        // •	Optic gliomas/pilocytic astrocytomas 9421/1
        MphRule rule = new MphRule(MphConstants.MP_2018_NON_MALIGNANT_CNS_TUMORS_GROUP_ID, "M5") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                String icd1 = i1.getHistology() + "/" + i1.getBehavior(), icd2 = i2.getHistology() + "/" + i2.getBehavior();
                if ((icd1.equals("9560/0") && icd2.equals("9421/1")) || (icd1.equals("9421/1") && icd2.equals("9560/0"))) {
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                }
                return result;
            }
        };
        rule.setQuestion("Is one tumor Acoustic neuromas/vestibular schwannomas 9560/0 and the other Optic gliomas/pilocytic astrocytomas 9421/1?");
        rule.setReason("Acoustic neuromas/vestibular schwannomas 9560/0 and Optic gliomas/pilocytic astrocytomas 9421/1 are a single primary.");
        rule.getNotes().add("The bilateral tumors may appear simultaneously (at the same time) OR the contralateral tumor may be diagnosed at any time following the original diagnosis.");
        rule.getNotes().add("When the bilateral tumors are diagnosed at different times, the physician may stage each tumor because staging and determining multiple primaries are done for different reasons. Staging determines which course of treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).");
        _rules.add(rule);

        // Rule M6	Abstract multiple primaries when separate, non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 6 in the Equivalent Terms and Definitions. Timing is irrelevant.
        rule = new MphRuleTwoOrMoreDifferentSubTypesInTable(MphConstants.MP_2018_NON_MALIGNANT_CNS_TUMORS_GROUP_ID, "M6", MphConstants.NON_MALIGNANT_CNS_2018_TABLE6_SUBTYPES, false);
        rule.setQuestion("Are separate/non-contiguous tumors two or more different subtypes/variants in Column 3, Table 6 in the Equivalent Terms and Definitions?");
        rule.setReason("Separate/non-contiguous tumors that are two or more different subtypes/variants in Column 3, Table 6 in the Equivalent Terms and Definitions are multiple primaries.");
        rule.getNotes().add("The tumors may be subtypes/variants of the same or different NOS histologies.");
        rule.getNotes().add("  • Same NOS: Atypical meningioma 9539/1 and fibrous meningioma 9532/0 are both subtypes of meningioma NOS 9530 but are distinctly different histologies. Abstract multiple primaries.");
        rule.getNotes().add("  • Different NOS: Melanotic schwannoma 9560/1 is a subtype of schwannoma NOS 9560/0; papillary craniopharyngioma 9352/1 is a subtype of craniopharyngioma 9350/1. They are distinctly different histologies. Abstract multiple primaries.");
        _rules.add(rule);

        // Rule M7	Abstract a single primary when separate, non-contiguous tumors are on the same row in Table 6 in the Equivalent Terms and Definitions.  Timing is irrelevant.
        rule = new MphRuleSameRowInTable(MphConstants.MP_2018_NON_MALIGNANT_CNS_TUMORS_GROUP_ID, "M7", MphConstants.NON_MALIGNANT_CNS_2018_TABLE6_ROWS, false);
        rule.setQuestion("Are separate/non-contiguous tumors on the same rows in Table 6 in the Equivalent Terms and Definitions?");
        rule.setReason("Separate/non-contiguous tumors on the same row in Table 6 in the Equivalent Terms and Definitions is a single primary.");
        rule.getNotes().add("The same row means the tumors are:");
        rule.getNotes().add("•	The same histology (same four-digit ICD-O code) OR");
        rule.getNotes().add("•	One is the preferred term (column 1) and the other is a synonym for the preferred term (column 2) OR");
        rule.getNotes().add("•	A NOS (column 1/column 2) and the other is a subtype/variant of that NOS (column 3)");
        _rules.add(rule);

        // Rule M8	Abstract multiple primaries when separate, non-contiguous tumors are on different rows in Table 6 in the Equivalent Terms and Definitions. Timing is irrelevant.
        rule = new MphRuleDifferentRowsInTable(MphConstants.MP_2018_NON_MALIGNANT_CNS_TUMORS_GROUP_ID, "M8", MphConstants.NON_MALIGNANT_CNS_2018_TABLE6_ROWS, false);
        rule.setQuestion("Are separate/non-contiguous tumors on different rows in Table 6 in the Equivalent Terms and Definitions?");
        rule.setReason("Separate/non-contiguous tumors on different rows in Table 6 in the Equivalent Terms and Definitions is multiple primaries.");
        rule.getNotes().add("Each row in the table is a distinctly different histology.");
        _rules.add(rule);

        // Rule M9	Abstract a single primary when there is a NOS and a subtype/variant of that NOS in the same CNS site (same second, third and fourth digit CXXX).
        rule = new MphRule(MphConstants.MP_2018_NON_MALIGNANT_CNS_TUMORS_GROUP_ID, "M9") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (!i1.getPrimarySite().substring(1, 4).equals(i2.getPrimarySite().substring(1, 4))) {

                    List<String> allowedNOS = Collections.unmodifiableList(Arrays.asList(
                            "9390/0", "9350/1", "9492/0", "8860/0", "8728/0", "9530/0", "8825/0", "9540/0", "9560/0", "8815/0"));

                    String icd1 = i1.getHistology() + "/" + i1.getBehavior(), icd2 = i2.getHistology() + "/" + i2.getBehavior();
                    List<String> subTypes1 = MphConstants.NON_MALIGNANT_CNS_2018_TABLE6_ROWS.get(icd1);
                    List<String> subTypes2 = MphConstants.NON_MALIGNANT_CNS_2018_TABLE6_ROWS.get(icd2);

                    if (subTypes1 != null && subTypes2 != null) {
                        if ((allowedNOS.contains(icd1) && subTypes1.contains(icd2)) || (allowedNOS.contains(icd2) && subTypes2.contains(icd1))) {
                            result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        }
                    }
                }
                return result;
            }
        };
        rule.setQuestion("Is one tumor a NOS and the other a subtype/variant of that NOS in the same CNS site (same second, third and fourth digit CXXX)?");
        rule.setReason("One tumor a NOS and the other a subtype/variant of that NOS in the same CNS site (same second, third and fourth digit CXXX) is a single primary.");
        rule.getNotes().add("See Table 6 in the Equivalent Terms and Definitions for NOS and subtypes/variants.");
        rule.getNotes().add("NOS and subtypes/variants are:");
        rule.getNotes().add("  • Choroid plexus papilloma 9390/0 and a subtype/variant of choroid plexus papilloma");
        rule.getNotes().add("  • Craniopharyngioma 9350/1 and a subtype/variant of craniopharyngioma");
        rule.getNotes().add("  • Gangliocytoma 9492/0 and a subtype/variant of craniopharyngioma");
        rule.getNotes().add("  • Lipoma 8860/0 and a subtype/variant of lipoma");
        rule.getNotes().add("  • Meningeal melanocytosis 8728/0 and a subtype/variant of meningeal melanocytosis");
        rule.getNotes().add("  • Meningioma 9530/0 and a subtype/variant of meningioma");
        rule.getNotes().add("  • Myofibroblastoma 8825/0 and a subtype/variant of myofibroblastoma");
        rule.getNotes().add("  • Neurofibroma 9540/0 and a subtype/variant of neurofibroma");
        rule.getNotes().add("  • Schwannoma 9560/0 and a subtype/variant of schwannoma");
        rule.getNotes().add("  • Solitary fibrous tumor WHO Grade 1 8815/0 and a subtype/variant of solitary fibrous tumor WHO Grade 1");
        _rules.add(rule);

        // Rule M10	Abstract multiple primaries when a malignant tumor /3 occurs after a non-malignant tumor /0 or /1 AND:
        // •	The patient had a resection of the non-malignant tumor OR
        // •	It is unknown/not documented whether a resection was done
        rule = new MphRuleMalignantAfterNonMalignant(MphConstants.MP_2018_NON_MALIGNANT_CNS_TUMORS_GROUP_ID, "M10", false);
        _rules.add(rule);

        // Rule M11	Abstract a single primary when two or more separate, non-contiguous meningiomas arise in the cranial meninges.  Laterality is irrelevant and may be any of the following combinations:
        // •	The same laterality (left or right) of the cranial meninges
        // •	Bilateral (both left and right) cranial meninges
        // •	The midline AND in either the right or left cranial meninges
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_NON_MALIGNANT_CNS_TUMORS_GROUP_ID, "M11");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("This rule applies ONLY to meningiomas.");
        _rules.add(rule);

        // Rule M12	Abstract a single primary when there are separate, non-contiguous tumors in the brain (multicentric/multifocal).  Tumors may be any of the following combinations:
        // •	In the same lobe; for example, two tumors in right temporal lobe C712 (same site code)
        // •	Different lateralities of the same lobe; for example, left and right frontal lobes C711 (same site code)
        // •	In different lobes; for example, parietal lobe C713 and occipital lobe C714 (different site codes)
        rule = new MphRule(MphConstants.MP_2018_NON_MALIGNANT_CNS_TUMORS_GROUP_ID, "M12") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (GroupUtility.isSiteContained(MphConstants.CNS_2018_BRAIN_SITES, i1.getPrimarySite()) &&
                    GroupUtility.isSiteContained(MphConstants.CNS_2018_BRAIN_SITES, i2.getPrimarySite())) {
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                }
                return result;
            }
        };
        rule.setQuestion("Are the tumors separate, non-contiguous and in the brain (multicentric/multifocal)?");
        rule.setReason("Tumors which are separate, non-contiguous and in the brain (multicentric/multifocal) are a single primary.");
        rule.getNotes().add("Metastases are never used to determine multiple primaries. Seeding metastasis is often noted in ependymomas.");
        rule.getNotes().add("Hereditary syndromes frequently exhibit multiple tumors, including the following:");
        rule.getNotes().add("  • Neurofibromatosis type 1 (NF1)");
        rule.getNotes().add("     Gliomas");
        rule.getNotes().add("     Malignant peripheral nerve sheath tumors (MPNST)");
        rule.getNotes().add("  • Neurofibromatosis type 2 (NF2)");
        rule.getNotes().add("     Anaplastic ependymomas");
        rule.getNotes().add("     Meningiomas");
        rule.getNotes().add("     Vestibular schwannomas");
        rule.getNotes().add("This is a change from/clarification to previous rules.");
        rule.getNotes().add("These rules are hierarchical.  Use this rule ONLY when the previous rules do not apply.");
        rule.getNotes().add("The physician may stage each tumor because staging and determining multiple primaries are done for different reasons. " +
                            "Staging determines which course of treatment would be most effective. Determining multiple primaries is done to stabilize " +
                            "the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the " +
                            "goal of reducing or eliminating that disease).");
        _rules.add(rule);

        // Rule M13	Abstract multiple primaries when multiple tumors are present in any of the following sites or subsites:
        // •	Brain C710-C719 AND any other part of CNS
        // •	Cerebral meninges C700 AND spinal meninges C701
        // •	Cerebral meninges C700 AND any other part of CNS
        // •	Cranial nerves C721-C725 AND any other part of the CNS
        // •	Meninges of cranial or peripheral nerves C709 AND any other part of the CNS
        // •	Spinal meninges C701 AND any other part of CNS
        rule = new MphRule(MphConstants.MP_2018_NON_MALIGNANT_CNS_TUMORS_GROUP_ID, "M13") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();

                if ((GroupUtility.isSiteContained(MphConstants.CNS_2018_BRAIN_SITES, i1.getPrimarySite()) || GroupUtility.isSiteContained(MphConstants.CNS_2018_BRAIN_SITES, i2.getPrimarySite())) &&
                    (GroupUtility.isSiteContained(MphConstants.CNS_2018_CNS_SITES, i1.getPrimarySite()) || GroupUtility.isSiteContained(MphConstants.CNS_2018_CNS_SITES, i2.getPrimarySite()))) {
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                }
                else if ((GroupUtility.isSiteContained(MphConstants.CNS_2018_CEREBRAL_MENINGES_SITES, i1.getPrimarySite()) || GroupUtility.isSiteContained(MphConstants.CNS_2018_CEREBRAL_MENINGES_SITES, i2.getPrimarySite())) &&
                        (GroupUtility.isSiteContained(MphConstants.CNS_2018_SPINAL_MENINGES_SITES, i1.getPrimarySite()) || GroupUtility.isSiteContained(MphConstants.CNS_2018_SPINAL_MENINGES_SITES, i2.getPrimarySite()))) {
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                }
                else if ((GroupUtility.isSiteContained(MphConstants.CNS_2018_CEREBRAL_MENINGES_SITES, i1.getPrimarySite()) || GroupUtility.isSiteContained(MphConstants.CNS_2018_CEREBRAL_MENINGES_SITES, i2.getPrimarySite())) &&
                        (GroupUtility.isSiteContained(MphConstants.CNS_2018_CNS_SITES, i1.getPrimarySite()) || GroupUtility.isSiteContained(MphConstants.CNS_2018_CNS_SITES, i2.getPrimarySite()))) {
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                }
                else if ((GroupUtility.isSiteContained(MphConstants.CNS_2018_CRANIAL_NERVES_SITES, i1.getPrimarySite()) || GroupUtility.isSiteContained(MphConstants.CNS_2018_CRANIAL_NERVES_SITES, i2.getPrimarySite())) &&
                        (GroupUtility.isSiteContained(MphConstants.CNS_2018_CNS_SITES, i1.getPrimarySite()) || GroupUtility.isSiteContained(MphConstants.CNS_2018_CNS_SITES, i2.getPrimarySite()))) {
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                }
                else if ((GroupUtility.isSiteContained(MphConstants.CNS_2018_MENINGES_OF_CRANIAL_OR_PERIPH_NERVES_SITES, i1.getPrimarySite()) || GroupUtility.isSiteContained(MphConstants.CNS_2018_MENINGES_OF_CRANIAL_OR_PERIPH_NERVES_SITES, i2.getPrimarySite())) &&
                        (GroupUtility.isSiteContained(MphConstants.CNS_2018_CNS_SITES, i1.getPrimarySite()) || GroupUtility.isSiteContained(MphConstants.CNS_2018_CNS_SITES, i2.getPrimarySite()))) {
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                }
                else if ((GroupUtility.isSiteContained(MphConstants.CNS_2018_SPINAL_MENINGES_SITES, i1.getPrimarySite()) || GroupUtility.isSiteContained(MphConstants.CNS_2018_SPINAL_MENINGES_SITES, i2.getPrimarySite())) &&
                        (GroupUtility.isSiteContained(MphConstants.CNS_2018_CNS_SITES, i1.getPrimarySite()) || GroupUtility.isSiteContained(MphConstants.CNS_2018_CNS_SITES, i2.getPrimarySite()))) {
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                }
                return result;
            }
        };
        rule.setQuestion("Are multiple tumors are present in the following sites:\n" +
            "Brain C710-C719 AND any other part of CNS\n" +
            "Cerebral meninges C700 AND spinal meninges C701\n" +
            "Cerebral meninges C700 AND any other part of CNS\n" +
            "Cranial nerves C721-C725 AND any other part of the CNS\n" +
            "Meninges of cranial or peripheral nerves C709 AND any other part of the CNS\n" +
            "Spinal meninges C701 AND any other part of CNS?");
        rule.setReason("Multiple tumors present in the following sites:\n" +
                "Brain C710-C719 AND any other part of CNS\n" +
                "Cerebral meninges C700 AND spinal meninges C701\n" +
                "Cerebral meninges C700 AND any other part of CNS\n" +
                "Cranial nerves C721-C725 AND any other part of the CNS\n" +
                "Meninges of cranial or peripheral nerves C709 AND any other part of the CNS\n" +
                "Spinal meninges C701 AND any other part of CNS\n" +
                "are multiple primaries.");
        _rules.add(rule);

        // Rule M14	Abstract a single primary when the tumors do not meet any of the above criteria.
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_NON_MALIGNANT_CNS_TUMORS_GROUP_ID, "M14");
        rule.getNotes().add("These rules are hierarchical.  Use this rule ONLY when the previous rules do not apply.");
        _rules.add(rule);


    }
}


