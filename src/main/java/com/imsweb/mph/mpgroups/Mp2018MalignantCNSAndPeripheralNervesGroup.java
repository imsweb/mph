/*
 * Copyright (C) 2018 Information Management Services, Inc.
 */
package com.imsweb.mph.mpgroups;

import java.util.Collections;

import com.imsweb.mph.MphComputeOptions;
import com.imsweb.mph.MphConstants;
import com.imsweb.mph.MphGroup;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphUtils;
import com.imsweb.mph.internal.TempRuleResult;

public class Mp2018MalignantCNSAndPeripheralNervesGroup extends MphGroup {

    /*
    Malignant CNS and Peripheral Nerves
    Multiple Primary Rules
    C470-C479, C700, C701, C709, C710-C719, C720-C725, C728, C729, C751-C753
    (Excludes lymphoma and leukemia M9590-M9992 and Kaposi sarcoma M9140)

    Note 1:	Multiple tumors may be a single primary or multiple primaries.
    Note 2:	Laterality is not used to determine multiple primaries for malignant CNS tumors.
    Note 3:	Timing is not used to determine multiple primaries for tumors with the same histology.

    Rule M6	Abstract multiple primaries when there is at least one invasive /3 intracranial or intraspinal tumor AND at least one non-malignant /0 or /1 intracranial or intraspinal tumor.
        Note 1:	The rules are hierarchical. Only use when previous rules do not apply.
        Note 2:	See Table 2 in the Equivalent Terms and Definitions for a listing of intracranial and intraspinal sites.
        Note 3:	A non-malignant CNS tumor and a malignant CNS tumor are always single primaries (timing and primary sites are irrelevant). Prepare two abstracts; one for the non-malignant and another for the malignant tumor.

    Rule M7	Abstract multiple primaries when multiple tumors are present in:
        •	Brain C71_ AND any other part of CNS
        •	Cerebral meninges C700 AND spinal meninges C701
        •	Cranial nerves C721-C725 AND any other part of the CNS
        •	Meninges of cranial or peripheral nerves C709 AND any other part of the CNS
        •	Peripheral nerves C47_ AND any other part of the CNS

    Rule M8	Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 3 in the Equivalent Terms and Definitions. Tumors may be:
        •	Simultaneous OR
        •	Original and subsequent
        Note:	Each row in the table is a distinctly different histology.

    Rule M9	Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 3 in the Equivalent Terms and Definitions. Tumors may be:
        •	Simultaneous OR
        •	Original and subsequent
        Note: 	The tumors may be subtypes/variants of the same or different NOS histologies.
        •	Same NOS: Anaplastic astrocytoma IDH-mutant 9401 and gemistocytic astrocytoma IDH-mutant 9411 are both subtypes of astrocytoma NOS 9400/3 but are distinctly different histologies. Abstract multiple primaries.
        •	Different NOS: Papillary ependymoma 9393 is a subtype of ependymoma NOS 9391; gliosarcoma 9442 is a subtype of glioblastoma NOS 9440. They are distinctly different histologies. Abstract multiple primaries.

    Rule M10	Abstract a single primary when there are separate, non-contiguous tumors in
        •	Same lobe, for example: two tumors in temporal lobe (same site code)
        •	Different lateralities, for example: left and right frontal lobes (same site code)
        •	Different lobes, for example: parietal lobe and occipital lobe (different site codes)
        Note 1:	Multiple sites/subsites and/or different lateralities imply either metastatic or multifocal/multicentric disease. The following histologies commonly have multiple tumors which are described as multifocal/multicentric:
        •	Glioblastoma multiforme
        •	Gliomatosis cerebri
        Note 2:	Metastases are never used to determine multiple primaries. Seeding metastasis is often noted for the following tumors:
        •	Glioblastoma multiforme
        •	pNET-medulloblastoma
        •	Ependymoma
        •	Oligodendroglioma
        Note 3:	Hereditary syndromes frequently exhibit multiple tumors including the following:
        •	Neurofibromatosis type 1 (NF1)
            	Gliomas
            	Malignant peripheral nerve sheath tumors (MPNST)
        •	Neurofibromatosis type 2 (NF2)
            	Anaplastic ependymomas
            	Meningiomas
            	Vestibular schwannomas
        Note 4:	Malignant tumors are usually single with the exception of those listed in this rule.
        Note 5:	This is a change from/clarification to previous rules.

    Rule M11	Abstract a single primary when multiple tumors do not meet any of the above criteria.
        Example: 	The patient had a resection of an anaplastic astrocytoma 9401 in the right parietal lobe. Three months later the patient is diagnosed with a de novo anaplastic astrocytoma in the left parietal lobe. This is one primary because neither laterality nor timing are used to determine multiple primary status.
    */

    // Malignant CNS and Peripheral Nerves
    // Multiple Primary Rules
    // C470-C479, C700, C701, C709, C710-C719, C720-C725, C728, C729, C751-C753
    // (Excludes lymphoma and leukemia M9590-M9992 and Kaposi sarcoma M9140)

    public Mp2018MalignantCNSAndPeripheralNervesGroup() {
        super(MphConstants.MP_2018_MALIGNANT_CNS_AND_PERIPHERAL_NERVES_GROUP_ID, MphConstants.MP_2018_MALIGNANT_CNS_AND_PERIPHERAL_NERVES_GROUP_NAME,
                "C470-C479, C700, C701, C709, C710-C719, C720-C725, C728, C729, C751-C753", null,
                null, "9590-9992, 9140", "3", "2018-9999");

        // Rule M6	Abstract multiple primaries when there is at least one invasive /3 intracranial or intraspinal tumor AND at least one non-malignant /0 or /1 intracranial or intraspinal tumor.
        // TODO
        MphRule rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_MALIGNANT_CNS_AND_PERIPHERAL_NERVES_GROUP_ID, "M6");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The rules are hierarchical. Only use when previous rules do not apply.");
        rule.getNotes().add("See Table 2 in the Equivalent Terms and Definitions for a listing of intracranial and intraspinal sites.");
        rule.getNotes().add("A non-malignant CNS tumor and a malignant CNS tumor are always single primaries (timing and primary sites are irrelevant). Prepare two abstracts; one for the non-malignant and another for the malignant tumor.");
        _rules.add(rule);

        // Rule M7	Abstract multiple primaries when multiple tumors are present in:
        // •	Brain C71_ AND any other part of CNS
        // •	Cerebral meninges C700 AND spinal meninges C701
        // •	Cranial nerves C721-C725 AND any other part of the CNS
        // •	Meninges of cranial or peripheral nerves C709 AND any other part of the CNS
        // •	Peripheral nerves C47_ AND any other part of the CNS
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_MALIGNANT_CNS_AND_PERIPHERAL_NERVES_GROUP_ID, "M7");
        rule.setQuestion("");
        rule.setReason("");
        _rules.add(rule);

        // Rule M8	Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 3 in the Equivalent Terms and Definitions. Tumors may be:
        // •	Simultaneous OR
        // •	Original and subsequent
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_MALIGNANT_CNS_AND_PERIPHERAL_NERVES_GROUP_ID, "M8");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("Each row in the table is a distinctly different histology.");
        _rules.add(rule);

        // Rule M9	Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 3 in the Equivalent Terms and Definitions. Tumors may be:
        // •	Simultaneous OR
        // •	Original and subsequent
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_MALIGNANT_CNS_AND_PERIPHERAL_NERVES_GROUP_ID, "M9");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The tumors may be subtypes/variants of the same or different NOS histologies.");
        rule.getNotes().add("  • Same NOS: Anaplastic astrocytoma IDH-mutant 9401 and gemistocytic astrocytoma IDH-mutant 9411 are both subtypes of astrocytoma NOS 9400/3 but are distinctly different histologies. Abstract multiple primaries.");
        rule.getNotes().add("  • Different NOS: Papillary ependymoma 9393 is a subtype of ependymoma NOS 9391; gliosarcoma 9442 is a subtype of glioblastoma NOS 9440. They are distinctly different histologies. Abstract multiple primaries.");
        _rules.add(rule);

        // Rule M10	Abstract a single primary when there are separate, non-contiguous tumors in
        // •	Same lobe, for example: two tumors in temporal lobe (same site code)
        // •	Different lateralities, for example: left and right frontal lobes (same site code)
        // •	Different lobes, for example: parietal lobe and occipital lobe (different site codes)
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_MALIGNANT_CNS_AND_PERIPHERAL_NERVES_GROUP_ID, "M10");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("Multiple sites/subsites and/or different lateralities imply either metastatic or multifocal/multicentric disease. The following histologies commonly have multiple tumors which are described as multifocal/multicentric:");
        rule.getNotes().add("  • Glioblastoma multiforme");
        rule.getNotes().add("  • Gliomatosis cerebri");
        rule.getNotes().add("Metastases are never used to determine multiple primaries. Seeding metastasis is often noted for the following tumors:");
        rule.getNotes().add("  • Glioblastoma multiforme");
        rule.getNotes().add("  • pNET-medulloblastoma");
        rule.getNotes().add("  • Ependymoma");
        rule.getNotes().add("  • Oligodendroglioma");
        rule.getNotes().add("Hereditary syndromes frequently exhibit multiple tumors including the following:");
        rule.getNotes().add("  • Neurofibromatosis type 1 (NF1)");
        rule.getNotes().add("     Gliomas");
        rule.getNotes().add("     Malignant peripheral nerve sheath tumors (MPNST)");
        rule.getNotes().add("  • Neurofibromatosis type 2 (NF2)");
        rule.getNotes().add("     Anaplastic ependymomas");
        rule.getNotes().add("     Meningiomas");
        rule.getNotes().add("     Vestibular schwannomas");
        rule.getNotes().add("Malignant tumors are usually single with the exception of those listed in this rule.");
        rule.getNotes().add("This is a change from/clarification to previous rules.");
        _rules.add(rule);

        // Rule M11	Abstract a single primary when multiple tumors do not meet any of the above criteria.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_MALIGNANT_CNS_AND_PERIPHERAL_NERVES_GROUP_ID, "M11");
        rule.setQuestion("");
        rule.setReason("");
        rule.getExamples().add("The patient had a resection of an anaplastic astrocytoma 9401 in the right parietal lobe. Three months later the patient is diagnosed with a de novo anaplastic astrocytoma in the left parietal lobe. This is one primary because neither laterality nor timing are used to determine multiple primary status.");
        _rules.add(rule);
    }
}

