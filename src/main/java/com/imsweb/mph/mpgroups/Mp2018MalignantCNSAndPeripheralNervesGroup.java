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
    (Excludes lymphoma and leukemia M9590-M9989 and Kaposi sarcoma

    Rule M5     Abstract multiple primaries when there is at least one invasive (/3) intracranial or intraspinal tumor AND at least one non-malignant (/0 or /1) intracranial or intraspinal tumor.
                Note 1: The rules are hierarchical. Use only when previous rules do not apply.
                Note 2: See Table 2 in the Equivalent Terms and Definitions for a listing of intracranial and intraspinal sites.
                Note 3: A non-malignant CNS tumor and a malignant CNS tumor are always single primaries (timing and primary sites are irrelevant). Prepare two abstracts; one for the non-malignant and another for the malignant tumor.

    Rule M6     Abstract multiple primaries when multiple tumors are present in:
                • Brain C71_ and any other part of CNS
                • Cerebral meninges C700 and spinal meninges C701
                • Cranial nerves C721-C725 and any other part of the CNS
                • Meninges of cranial or peripheral nerves C709 and any other part of the CNS
                • Peripheral nerves C47_ and any other part of the CNS

    Rule M7     Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 3, first or second column, in the Equivalent Terms and Definitions. Tumors may be:
                • Simultaneous tumors OR
                • Original and subsequent tumors
                Note: “Different rows” means separate rows in the first column OR second column
                    • The first column is the specific or NOS histology term and code. Each row in the table is a distinctly different histology.
                    • The second column has synonyms for the specific or NOS histology term listed in the first column. A synonym is an alternate term for the histology in column 1, so when the synonyms are on different rows (different histologies) in Table 3 they are multiple primaries.

    Rule M8     Abstract multiple primaries when separate/non-contiguous tumors are different subtypes/variants in Column 3, Table 3 in the Equivalent Terms and Definitions. Tumors may be:
                • Simultaneous tumors OR
                • Original and subsequent tumors
                Note: The tumors may be subtypes/variants of the same or different NOS histologies.
                    • Same NOS: Anaplastic astrocytoma IDH-mutant 9401 and gemistocytic astrocytoma IDH-mutant 9411 are both subtypes of astrocytoma NOS 9400/3 but are distinctly different histologies. Abstract multiple primaries
                    • Different NOS: Papillary ependymoma 9393 is a subtype of ependymoma NOS 9391; gliosarcoma 9442 is a subtype of glioblastoma NOS 9440. They are distinctly different histologies. Abstract multiple primaries.

    Rule M9     Abstract a single primary when there are separate, non-contiguous tumors in
                • Same lobe, for example: two tumors in temporal lobe (same site code)
                • Different lateralities, for example: left and right frontal lobes (same site code)
                • Different lobes, for example: parietal lobe and occipital lobe (different site codes)
                Note 1: Multiple sites/subsites and/or different lateralities imply either metastatic or multifocal/multicentric disease. The following histologies commonly have multiple tumors which are described as multifocal/multicentric:
                    • Glioblastoma multiforme
                    • Gliomatosis cerebri
                Note 2: Metastases are never used to determine multiple primaries. Seeding metastasis is often noted for the following tumors:
                    • Glioblastoma multiforme
                    • pNET-medulloblastoma
                    • Ependymoma
                    • Oligodendroglioma
                Note 3: Hereditary syndromes frequently exhibit multiple tumors including the following:
                • Neurofibromatosis type 1 (NF1)
                    ο Gliomas
                    ο Malignant peripheral nerve sheath tumors (MPNST)
                • Neurofibromatosis type 2 (NF2)
                    ο Anaplastic ependymomas
                    ο Meningiomas
                    ο Vestibular schwannomas
                Note 4: Malignant tumors are usually single with the exception of those listed in this rule.
                Note 5: This is a change from/clarification to previous rules.

    Rule M10    Abstract multiple primaries when there are multiple tumors with ICD-O histology codes which differ at the first (Xxxx), second (xXxx), or third digit (xxXx).

    Rule M11    Abstract a single primary when multiple tumors do not meet any of the above criteria.
                Example: The patient had a resection of an anaplastic astrocytoma (9401) in the right parietal lobe. Three months later the patient is diagnosed with a de novo anaplastic astrocytoma in the left parietal lobe. This is one primary because neither laterality nor timing are used to determine multiple primary status.

    */

    // TODO
    public Mp2018MalignantCNSAndPeripheralNervesGroup() {
        super(MphConstants.MP_2007_MALIGNANT_CNS_AND_PERIPHERAL_NERVES_GROUP_ID, MphConstants.MP_2007_MALIGNANT_CNS_AND_PERIPHERAL_NERVES_GROUP_NAME, "C700-C701,C709-C725,C728-C729,C751-C753", null,
                null, "9590-9989,9140", "3",
                "2018-9999");

        // Rule M5 - Abstract multiple primaries when there is at least one invasive (/3) intracranial or intraspinal tumor AND at least one non-malignant (/0 or /1) intracranial or intraspinal tumor.
        // TODO
        MphRule rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2007_MALIGNANT_CNS_AND_PERIPHERAL_NERVES_GROUP_ID, "M5");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The rules are hierarchical. Use only when previous rules do not apply.");
        rule.getNotes().add("See Table 2 in the Equivalent Terms and Definitions for a listing of intracranial and intraspinal sites.");
        rule.getNotes().add("A non-malignant CNS tumor and a malignant CNS tumor are always single primaries (timing and primary sites are irrelevant). Prepare two abstracts; one for the non-malignant and another for the malignant tumor.");
        _rules.add(rule);

        // Rule M6 - Abstract multiple primaries when multiple tumors are present in:
        // • Brain C71_ and any other part of CNS
        // • Cerebral meninges C700 and spinal meninges C701
        // • Cranial nerves C721-C725 and any other part of the CNS
        // • Meninges of cranial or peripheral nerves C709 and any other part of the CNS
        // • Peripheral nerves C47_ and any other part of the CNS
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2007_MALIGNANT_CNS_AND_PERIPHERAL_NERVES_GROUP_ID, "M6");
        rule.setQuestion("");
        rule.setReason("");
        _rules.add(rule);

        // Rule M7 - Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 3, first or second column, in the Equivalent Terms and Definitions. Tumors may be:
        // • Simultaneous tumors OR
        // • Original and subsequent tumors
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2007_MALIGNANT_CNS_AND_PERIPHERAL_NERVES_GROUP_ID, "M7");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("“Different rows” means separate rows in the first column OR second column");
        rule.getNotes().add("  • The first column is the specific or NOS histology term and code. Each row in the table is a distinctly different histology.");
        rule.getNotes().add("  • The second column has synonyms for the specific or NOS histology term listed in the first column. A synonym is an alternate term for the histology in column 1, so when the synonyms are on different rows (different histologies) in Table 3 they are multiple primaries.");
        _rules.add(rule);

        // Rule M8 - Abstract multiple primaries when separate/non-contiguous tumors are different subtypes/variants in Column 3, Table 3 in the Equivalent Terms and Definitions. Tumors may be:
        // • Simultaneous tumors OR
        // • Original and subsequent tumors
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2007_MALIGNANT_CNS_AND_PERIPHERAL_NERVES_GROUP_ID, "M8");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The tumors may be subtypes/variants of the same or different NOS histologies.");
        rule.getNotes().add("  • Same NOS: Anaplastic astrocytoma IDH-mutant 9401 and gemistocytic astrocytoma IDH-mutant 9411 are both subtypes of astrocytoma NOS 9400/3 but are distinctly different histologies. Abstract multiple primaries");
        rule.getNotes().add("  • Different NOS: Papillary ependymoma 9393 is a subtype of ependymoma NOS 9391; gliosarcoma 9442 is a subtype of glioblastoma NOS 9440. They are distinctly different histologies. Abstract multiple primaries.");
        _rules.add(rule);

        // Rule M9 - Abstract a single primary when there are separate, non-contiguous tumors in
        // • Same lobe, for example: two tumors in temporal lobe (same site code)
        // • Different lateralities, for example: left and right frontal lobes (same site code)
        // • Different lobes, for example: parietal lobe and occipital lobe (different site codes)
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2007_MALIGNANT_CNS_AND_PERIPHERAL_NERVES_GROUP_ID, "M9");
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
        rule.getNotes().add("    ο Gliomas");
        rule.getNotes().add("    ο Malignant peripheral nerve sheath tumors (MPNST)");
        rule.getNotes().add("  • Neurofibromatosis type 2 (NF2)");
        rule.getNotes().add("    ο Anaplastic ependymomas");
        rule.getNotes().add("    ο Meningiomas");
        rule.getNotes().add("    ο Vestibular schwannomas");
        rule.getNotes().add("Malignant tumors are usually single with the exception of those listed in this rule.");
        rule.getNotes().add("This is a change from/clarification to previous rules.");
        _rules.add(rule);

        // Rule M10 - Abstract multiple primaries when there are multiple tumors with ICD-O histology codes which differ at the first (Xxxx), second (xXxx), or third digit (xxXx).
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2007_MALIGNANT_CNS_AND_PERIPHERAL_NERVES_GROUP_ID, "M10");
        rule.setQuestion("");
        rule.setReason("");
        _rules.add(rule);

        // Rule M11 - Abstract a single primary when multiple tumors do not meet any of the above criteria.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2007_MALIGNANT_CNS_AND_PERIPHERAL_NERVES_GROUP_ID, "M11");
        rule.setQuestion("");
        rule.setReason("");
        rule.getExamples().add("The patient had a resection of an anaplastic astrocytoma (9401) in the right parietal lobe. Three months later the patient is diagnosed with a de novo anaplastic astrocytoma in the left parietal lobe. This is one primary because neither laterality nor timing are used to determine multiple primary status.");
        _rules.add(rule);

        /*
        // M4 - An invasive brain tumor (/3) and either a benign brain tumor (/0) or an uncertain/borderline brain tumor (/1) are always multiple primaries.
        MphRule rule = new MphRule(MphConstants.MP_2007_MALIGNANT_BRAIN_GROUP_ID, "M4") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                //This will never happen, since the two conditions belong to different cancer groups.
                return new TempRuleResult();
            }
        };
        rule.setQuestion("Is there an invasive tumor (/3) and either a benign brain tumor (/0) or an uncertain/borderline brain tumor (/1)?");
        rule.setReason("An invasive brain tumor (/3) and either a benign brain tumor (/0) or an uncertain/borderline brain tumor (/1) are always multiple primaries.");
        _rules.add(rule);

        // M5- Tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx) and/or third (Cx?x) character are multiple primaries.
        rule = new MphRulePrimarySiteCode(MphConstants.MP_2007_MALIGNANT_BRAIN_GROUP_ID, "M5");
        _rules.add(rule);

        // M6 - A glioblastoma or glioblastoma multiforme (9440) following a glial tumor is a single primary.
        rule = new MphRule(MphConstants.MP_2007_MALIGNANT_BRAIN_GROUP_ID, "M6") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (GroupUtility.differentCategory(i1.getHistology(), i2.getHistology(), MphConstants.GLIAL_TUMOR, Collections.singletonList(MphConstants.GLIOBLASTOMA_NOS_AND_MULTIFORME))) {
                    int laterDiagnosedTumor = GroupUtility.compareDxDate(i1, i2);
                    if (-1 == laterDiagnosedTumor) { //If impossible to decide which tumor is diagnosed later
                        result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        result.setMessage("Unable to apply Rule" + this.getStep() + " of " + this.getGroupId() + ". Known diagnosis date should be provided.");
                    }
                    else if (1 == laterDiagnosedTumor && MphConstants.GLIOBLASTOMA_NOS_AND_MULTIFORME.equals(i1.getHistology()) && MphConstants.GLIAL_TUMOR.contains(i2.getHistology()))
                        result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                    else if (2 == laterDiagnosedTumor && MphConstants.GLIOBLASTOMA_NOS_AND_MULTIFORME.equals(i2.getHistology()) && MphConstants.GLIAL_TUMOR.contains(i1.getHistology()))
                        result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                }
                return result;
            }
        };
        rule.setQuestion("Is there a glioblastoma or glioblastoma multiforme (9440) following a glial tumor (See Chart 1) ?");
        rule.setReason("A glioblastoma or glioblastoma multiforme (9440) following a glial tumor is a single primary.");
        _rules.add(rule);

        // M7 - Tumors with ICD-O-3 histology codes on the same branch in Chart 1 or Chart 2 are a single primary.
        rule = new MphRule(MphConstants.MP_2007_MALIGNANT_BRAIN_GROUP_ID, "M7") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                String branch1 = MphConstants.MALIGNANT_BRAIN_2007_CHART1.get(i1.getHistology()), branch2 = MphConstants.MALIGNANT_BRAIN_2007_CHART1.get(i2.getHistology());
                if (branch1 != null && branch2 != null && (branch1.equals(branch2) || "Neuroepithelial".equals(branch1) || "Neuroepithelial".equals(branch2)))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                else {
                    branch1 = MphConstants.MALIGNANT_BRAIN_2007_CHART2.get(i1.getHistology());
                    branch2 = MphConstants.MALIGNANT_BRAIN_2007_CHART2.get(i2.getHistology());
                    if (branch1 != null && branch2 != null && (branch1.equals(branch2)))
                        result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                }
                return result;
            }
        };
        rule.setQuestion("Do the tumors have ICD-O-3 histology on the same branch in Chart 1 or Chart 2?");
        rule.setReason("Tumors with ICD-O-3 histology codes on the same branch in Chart 1 or Chart 2 are a single primary.");
        rule.getNotes().add("Recurrence, progression, or any reappearance of histologies on the same branch in Chart 1 or Chart 2 is always the same disease process.");
        rule.getExamples().add("Patient has an astrocytoma. Ten years later the patient is diagnosed with glioblastoma multiforme. This is a progression or recurrence of the earlier astrocytoma.");
        _rules.add(rule);

        // M8 - Tumors with ICD-O-3 histology codes on different branches in Chart 1 or Chart 2 are multiple primaries.
        rule = new MphRule(MphConstants.MP_2007_MALIGNANT_BRAIN_GROUP_ID, "M8") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                String branch1 = MphConstants.MALIGNANT_BRAIN_2007_CHART1.get(i1.getHistology()), branch2 = MphConstants.MALIGNANT_BRAIN_2007_CHART1.get(i2.getHistology());
                if (branch1 != null && branch2 != null && !branch1.equals(branch2) && !"Neuroepithelial".equals(branch1) && !"Neuroepithelial".equals(branch2))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                else {
                    branch1 = MphConstants.MALIGNANT_BRAIN_2007_CHART2.get(i1.getHistology());
                    branch2 = MphConstants.MALIGNANT_BRAIN_2007_CHART2.get(i2.getHistology());
                    if (branch1 != null && branch2 != null && !branch1.equals(branch2))
                        result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                }
                return result;
            }
        };
        rule.setQuestion("Do the tumors have ICD-O-3 histology codes on different branches in Chart 1 or Chart 2?");
        rule.setReason("Tumors with ICD-O-3 histology codes on different branches in Chart 1 or Chart 2 are multiple primaries.");
        _rules.add(rule);

        // M9- Tumors with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.
        rule = new MphRuleHistologyCode(MphConstants.MP_2007_MALIGNANT_BRAIN_GROUP_ID, "M9");
        _rules.add(rule);

        // M10- Tumors that do not meet any of the criteria are abstracted as a single primary.
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2007_MALIGNANT_BRAIN_GROUP_ID, "M10");
        rule.getNotes().add("Multicentric brain tumors which involve different lobes of the brain that do not meet any of the above criteria are the same disease process.");
        rule.getNotes().add("Neither timing nor laterality is used to determine multiple primaries for malignant intracranial and CNS tumors.");
        rule.getExamples().add(
                "The patient is treated for an anaplastic astrocytoma (9401) in the right parietal lobe. Three months later the patient is diagnosed with a separate anaplastic astrocytoma in the left parietal lobe. This is one primary because laterality is not used to determine multiple primary status.");
        _rules.add(rule);
        */
    }
}

