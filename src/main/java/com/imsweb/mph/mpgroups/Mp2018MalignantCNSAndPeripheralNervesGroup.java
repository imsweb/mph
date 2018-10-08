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

public class Mp2018MalignantCNSAndPeripheralNervesGroup extends MphGroup {

    // MALIGNANT 2018 - AS OF 9/14/2018

    /*
    Malignant CNS and Peripheral Nerves Histology Rules
    C470-C479, C700, C701, C709, C710-C719, C721-C725, C728, C729, C751-C753
    (Excludes lymphoma and leukemia M9590-M9992 and Kaposi sarcoma M9140)

    Rule M5	Abstract multiple primaries when there are multiple CNS tumors, one of which is malignant /3 and the other is non-malignant /0 or /1.
        •	Original non-malignant tumor followed by malignant tumor
            	Patient had a resection of the non-malignant tumor (not the same tumor) OR
            	It is unknown/not documented if the patient had a resection
        •	Simultaneous non-malignant and malignant tumors
            	Abstract both the malignant and the non-malignant tumors
        Note 1:	The rules are hierarchical. Only use when previous rules do not apply.
        Note 2:	See Table 2 in the Equivalent Terms and Definitions for a listing of CNS sites.
        Note 3:	A non-malignant CNS tumor and a malignant CNS tumor are always multiple primaries (timing and primary sites are irrelevant). Prepare two abstracts; one for the non-malignant and another for the malignant tumor.

    Rule M6	Abstract multiple primaries when a patient has a glial or astrocytic tumor and is subsequently diagnosed with a glioblastoma multiforme 9440 (GBM).
        Note 1:	This is a change from the 2007 Rules.
        Note 2:	Abstracting GBM as a new primary will allow analysis of:
        •	The number of tumors that recur as a more aggressive histology (GBM)
        •	The time interval between occurrence of the glial or astrocytic tumors and a GBM
        •	Which histologies are more likely to recur as a GBM

    Rule M7	Abstract a single primary when there are separate, non-contiguous tumors in the brain (multicentric/multifocal).  Tumors may be any of the following combinations:
        •	In the same lobe; for example, two tumors in right temporal lobe C712 (same site code)
        •	Different lateralities of the same lobe; for example, left and right frontal lobes C711 (same site code)
        •	In different lobes; for example, parietal lobe C713 and occipital lobe C714 (different site codes)
        Example: 	The patient had a resection of an anaplastic astrocytoma 9401 in the right parietal lobe. Three months later the patient is diagnosed with a de novo anaplastic astrocytoma in the left parietal lobe. This is one primary because neither laterality nor timing are used to determine multiple primary status.
        Note 1:	Multiple sites/subsites and/or different lateralities imply either metastatic or multifocal/multicentric disease. The following histologies commonly have multiple tumors which are described as multifocal/multicentric:
        •	Glioblastoma multiforme
        •	Gliomatosis cerebri
        Note 2:	Metastases are never used to determine multiple primaries. Seeding metastasis is often noted for the following tumors:
        •	Glioblastoma multiforme
        •	pNET-medulloblastoma
        •	Oligodendroglioma
        Note 3:	Hereditary syndromes frequently exhibit multiple tumors including the following:
        •	Neurofibromatosis type 1 (NF1)
            	Malignant peripheral nerve sheath tumors (MPNST)
        •	Neurofibromatosis type 2 (NF2)
            	Anaplastic ependymomas
            	Meningiomas
        Note 4:	Most malignant neoplasms are single tumors with the exception of those listed in this rule.
        Note 5:	This is a change from/clarification to previous rules.

    Rule M8	Abstract multiple primaries when multiple tumors are present in any of the following sites or subsites:
        •	Any lobe of the brain C710-C719 AND any other part of CNS
        •	Cerebral meninges C700 AND spinal meninges C701
        •	Cerebral meninges C700 AND any other part of CNS
        •	Any of the cranial nerves C721-C725 AND any other part of the CNS
        •	Meninges of cranial or peripheral nerves C709 AND any other part of the CNS
        •	Spinal meninges C701 AND any other part of CNS
        Rule M9	Abstract multiple primariesii when separate, non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 3 in the Equivalent Terms and Definitions. Timing is irrelevant.
        Note: 	The tumors may be subtypes/variants of the same or different NOS histologies.
        •	Same NOS: Anaplastic astrocytoma IDH-mutant 9401 and gemistocytic astrocytoma IDH-mutant 9411 are both subtypes of astrocytoma NOS 9400/3 but are distinctly different histologies. Abstract multiple primaries.
        •	Different NOS: Papillary ependymoma 9393 is a subtype of ependymoma NOS 9391; gliosarcoma 9442 is a subtype of glioblastoma NOS 9440. They are distinctly different histologies. Abstract multiple primaries.

    Rule M9	Abstract multiple primaries when separate, non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 3 in the Equivalent Terms and Definitions. Timing is irrelevant.
        Note: 	The tumors may be subtypes/variants of the same or different NOS histologies.
        •	Same NOS: Anaplastic astrocytoma IDH-mutant 9401 and gemistocytic astrocytoma IDH-mutant 9411 are both subtypes of astrocytoma NOS 9400/3 but are distinctly different histologies. Abstract multiple primaries.
        •	Different NOS: Papillary ependymoma 9393 is a subtype of ependymoma NOS 9391; gliosarcoma 9442 is a subtype of glioblastoma NOS 9440. They are distinctly different histologies. Abstract multiple primaries.

    Rule M10	Abstract a single primary when separate, non-contiguous tumors are on the same row in Table 3 in the Equivalent Terms and Definitions. Timing is irrelevant.
        Note:	The same row means the tumors are:
        •	The same histology (same four-digit ICD-O code) OR
        •	One is the preferred term (column 1) and the other is a synonym for the preferred term (column 2) OR
        •	A NOS (column 1/column 2) and the other is a subtype/variant of that NOS (column 3)

    Rule M11	Abstract multiple primaries when separate, non-contiguous tumors are on different rows in Table 3 in the Equivalent Terms and Definitions. Timing is irrelevant.
        Note:	Each row in the table is a distinctly different histology.

    Rule M12	Abstract a single primary when multiple tumors do not meet any of the above criteria.
        Note:	Use this rule as a last resort.  Please confirm that you have not overlooked an applicable rule.

    */

    // Malignant CNS and Peripheral Nerves Histology Rules
    // C470-C479, C700, C701, C709, C710-C719, C721-C725, C728, C729, C751-C753
    // (Excludes lymphoma and leukemia M9590-M9992 and Kaposi sarcoma M9140)
    public Mp2018MalignantCNSAndPeripheralNervesGroup() {
        super(MphConstants.MP_2018_MALIGNANT_CNS_AND_PERIPHERAL_NERVES_GROUP_ID, MphConstants.MP_2018_MALIGNANT_CNS_AND_PERIPHERAL_NERVES_GROUP_NAME,
                "C470-C479, C700, C701, C709, C710-C719, C721-C725, C728, C729, C751-C753", null,
                null, "9590-9992, 9140", "3", "2018-9999");

        // Rule M5	Abstract multiple primaries when there are multiple CNS tumors, one of which is malignant /3 and the other is non-malignant /0 or /1.
        // •	Original non-malignant tumor followed by malignant tumor
        //     	Patient had a resection of the non-malignant tumor (not the same tumor) OR
        //     	It is unknown/not documented if the patient had a resection
        // •	Simultaneous non-malignant and malignant tumors
        //     	Abstract both the malignant and the non-malignant tumors
        MphRule rule = new MphRuleMalignantAndNonMalignant(MphConstants.MP_2018_MALIGNANT_CNS_AND_PERIPHERAL_NERVES_GROUP_ID, "M5", false);
        rule.getNotes().add("The rules are hierarchical. Only use when previous rules do not apply.");
        rule.getNotes().add("See Table 2 in the Equivalent Terms and Definitions for a listing of CNS sites.");
        rule.getNotes().add("A non-malignant CNS tumor and a malignant CNS tumor are always multiple primaries (timing and primary sites are irrelevant). Prepare two abstracts; one for the non-malignant and another for the malignant tumor.");
        _rules.add(rule);

        // Rule M6	Abstract multiple primaries when a patient has a glial or astrocytic tumor and is subsequently diagnosed with a glioblastoma multiforme 9440 (GBM).
        rule = new MphRule(MphConstants.MP_2018_MALIGNANT_CNS_AND_PERIPHERAL_NERVES_GROUP_ID, "M6") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                int latestDx = GroupUtility.compareDxDate(i1, i2);
                if ((MphConstants.GLIAL_TUMOR.contains(i1.getHistology()) && i2.getHistology().equals("9440") && (2 == latestDx)) ||
                    (MphConstants.GLIAL_TUMOR.contains(i2.getHistology()) && i1.getHistology().equals("9440") && (1 == latestDx)))
                {
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                }
                return result;
            }
        };
        rule.setQuestion("Does the patient have a glial or astrocytic tumor and is subsequently diagnosed with a glioblastoma multiforme 9440 (GBM)?");
        rule.setReason("A glial or astrocytic tumor that is subsequently diagnosed with a glioblastoma multiforme 9440 (GBM) is multiple primaries.");
        rule.getNotes().add("This is a change from the 2007 Rules.");
        rule.getNotes().add("Abstracting GBM as a new primary will allow analysis of:");
        rule.getNotes().add("  • The number of tumors that recur as a more aggressive histology (GBM)");
        rule.getNotes().add("  • The time interval between occurrence of the glial or astrocytic tumors and a GBM");
        rule.getNotes().add("  • Which histologies are more likely to recur as a GBM");
        _rules.add(rule);

        // Rule M7	Abstract a single primary when there are separate, non-contiguous tumors in the brain (multicentric/multifocal).  Tumors may be any of the following combinations:
        // •	In the same lobe; for example, two tumors in right temporal lobe C712 (same site code)
        // •	Different lateralities of the same lobe; for example, left and right frontal lobes C711 (same site code)
        // •	In different lobes; for example, parietal lobe C713 and occipital lobe C714 (different site codes)
        rule = new MphRule(MphConstants.MP_2018_MALIGNANT_CNS_AND_PERIPHERAL_NERVES_GROUP_ID, "M7") {
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
        rule.getExamples().add("The patient had a resection of an anaplastic astrocytoma 9401 in the right parietal lobe. Three months later the patient is diagnosed with a de novo anaplastic astrocytoma in the left parietal lobe. This is one primary because neither laterality nor timing are used to determine multiple primary status.");
        rule.getNotes().add("Multiple sites/subsites and/or different lateralities imply either metastatic or multifocal/multicentric disease. The following histologies commonly have multiple tumors which are described as multifocal/multicentric:");
        rule.getNotes().add("  • Glioblastoma multiforme");
        rule.getNotes().add("  • Gliomatosis cerebri");
        rule.getNotes().add("Metastases are never used to determine multiple primaries. Seeding metastasis is often noted for the following tumors:");
        rule.getNotes().add("  • Glioblastoma multiforme");
        rule.getNotes().add("  • pNET-medulloblastoma");
        rule.getNotes().add("  • Oligodendroglioma");
        rule.getNotes().add("Hereditary syndromes frequently exhibit multiple tumors including the following:");
        rule.getNotes().add("  • Neurofibromatosis type 1 (NF1)");
        rule.getNotes().add("     Malignant peripheral nerve sheath tumors (MPNST)");
        rule.getNotes().add("  • Neurofibromatosis type 2 (NF2)");
        rule.getNotes().add("     Anaplastic ependymomas");
        rule.getNotes().add("     Meningiomas");
        rule.getNotes().add("Most malignant neoplasms are single tumors with the exception of those listed in this rule.");
        rule.getNotes().add("This is a change from/clarification to previous rules.");
        _rules.add(rule);

        // Rule M8	Abstract multiple primaries when multiple tumors are present in any of the following sites or subsites:
        // •	Any lobe of the brain C710-C719 AND any other part of CNS
        // •	Cerebral meninges C700 AND spinal meninges C701
        // •	Cerebral meninges C700 AND any other part of CNS
        // •	Any of the cranial nerves C721-C725 AND any other part of the CNS
        // •	Meninges of cranial or peripheral nerves C709 AND any other part of the CNS
        // •	Spinal meninges C701 AND any other part of CNS
        rule = new MphRule(MphConstants.MP_2018_MALIGNANT_CNS_AND_PERIPHERAL_NERVES_GROUP_ID, "M8") {
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
                "Any lobe of the brain C710-C719 AND any other part of CNS\n" +
                "Cerebral meninges C700 AND spinal meninges C701\n" +
                "Cerebral meninges C700 AND any other part of CNS\n" +
                "Any of the cranial nerves C721-C725 AND any other part of the CNS\n" +
                "Meninges of cranial or peripheral nerves C709 AND any other part of the CNS\n" +
                "Spinal meninges C701 AND any other part of CNS?");
        rule.setReason("Multiple tumors present in the following sites:\n" +
                "Any lobe of the brain C710-C719 AND any other part of CNS\n" +
                "Cerebral meninges C700 AND spinal meninges C701\n" +
                "Cerebral meninges C700 AND any other part of CNS\n" +
                "Any of the cranial nerves C721-C725 AND any other part of the CNS\n" +
                "Meninges of cranial or peripheral nerves C709 AND any other part of the CNS\n" +
                "Spinal meninges C701 AND any other part of CNS\n" +
                "are multiple primaries.");
        _rules.add(rule);

        // Rule M9	Abstract multiple primaries when separate, non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 3 in the Equivalent Terms and Definitions. Timing is irrelevant.
        rule = new MphRuleTwoOrMoreDifferentSubTypesInTable(MphConstants.MP_2018_MALIGNANT_CNS_AND_PERIPHERAL_NERVES_GROUP_ID, "M9", MphConstants.MALIGNANT_CNS_2018_TABLE3_SUBTYPES,
                                                            MphConstants.MALIGNANT_CNS_2018_SUBTYPE_NOS, false);
        rule.setQuestion("Are separate/non-contiguous tumors two or more different subtypes/variants in Column 3, Table 3 in the Equivalent Terms and Definitions?");
        rule.setReason("Separate/non-contiguous tumors that are two or more different subtypes/variants in Column 3, Table 3 in the Equivalent Terms and Definitions are multiple primaries.");
        rule.getNotes().add("The tumors may be subtypes/variants of the same or different NOS histologies.");
        rule.getNotes().add("  • Same NOS: Anaplastic astrocytoma IDH-mutant 9401 and gemistocytic astrocytoma IDH-mutant 9411 are both subtypes of astrocytoma NOS 9400/3 but are distinctly different histologies. Abstract multiple primaries.");
        rule.getNotes().add("  • Different NOS: Papillary ependymoma 9393 is a subtype of ependymoma NOS 9391; gliosarcoma 9442 is a subtype of glioblastoma NOS 9440. They are distinctly different histologies. Abstract multiple primaries.");
        _rules.add(rule);

        // Rule M10	Abstract a single primary when separate, non-contiguous tumors are on the same row in Table 3 in the Equivalent Terms and Definitions. Timing is irrelevant.
        rule = new MphRuleSameRowInTable(MphConstants.MP_2018_MALIGNANT_CNS_AND_PERIPHERAL_NERVES_GROUP_ID, "M10", MphConstants.MALIGNANT_CNS_2018_TABLE3_ROWS,
                                         MphConstants.MALIGNANT_CNS_2018_SUBTYPE_NOS, false, false);
        rule.setQuestion("Are separate/non-contiguous tumors on the same rows in Table 3 in the Equivalent Terms and Definitions?");
        rule.setReason("Separate/non-contiguous tumors on the same row in Table 3 in the Equivalent Terms and Definitions is a single primary.");
        rule.getNotes().add("The same row means the tumors are:");
        rule.getNotes().add("  • The same histology (same four-digit ICD-O code) OR");
        rule.getNotes().add("  • One is the preferred term (column 1) and the other is a synonym for the preferred term (column 2) OR");
        rule.getNotes().add("  • A NOS (column 1/column 2) and the other is a subtype/variant of that NOS (column 3)");
        _rules.add(rule);

        // Rule M11	Abstract multiple primaries when separate, non-contiguous tumors are on different rows in Table 3 in the Equivalent Terms and Definitions. Timing is irrelevant.
        rule = new MphRuleDifferentRowsInTable(MphConstants.MP_2018_MALIGNANT_CNS_AND_PERIPHERAL_NERVES_GROUP_ID, "M11", MphConstants.MALIGNANT_CNS_2018_TABLE3_ROWS, false);
        rule.setQuestion("Are separate/non-contiguous tumors on different rows in Table 3 in the Equivalent Terms and Definitions?");
        rule.setReason("Separate/non-contiguous tumors on different rows in Table 3 in the Equivalent Terms and Definitions is multiple primaries.");
        rule.getNotes().add("Each row in the table is a distinctly different histology.");
        _rules.add(rule);

        // Rule M12	Abstract a single primary when multiple tumors do not meet any of the above criteria.
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_MALIGNANT_CNS_AND_PERIPHERAL_NERVES_GROUP_ID, "M12");
        rule.getNotes().add("Use caution when applying this default rule.  Please confirm that you have not overlooked an applicable rule.");
        _rules.add(rule);
    }
}

