/*
 * Copyright (C) 2018 Information Management Services, Inc.
 */
package com.imsweb.mph.mpgroups;

import java.util.Arrays;

import com.imsweb.mph.MphComputeOptions;
import com.imsweb.mph.MphConstants;
import com.imsweb.mph.MphGroup;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphUtils;
import com.imsweb.mph.MphUtils.MpResult;
import com.imsweb.mph.internal.TempRuleResult;
import com.imsweb.mph.mprules.MpRuleNoCriteriaSatisfied;

public class Mp2018NonMalignantCNSTumorsGroup extends MphGroup {

    // NON-MALIGNANT 2019 - See MphConstants for AS OF date.

    /*
    Non-Malignant CNS Multiple Primary Rules
    C700, C701, C709, C710-C719, C720-C725, C728, C729, C751-C753
    (Excludes lymphoma and leukemia M9590 – M9992 and Kaposi sarcoma M9140)

    Rule M5	Abstract multiple primaries when a malignant tumor /3 occurs after a non-malignant tumor /0 or /1 AND:
        •	The patient had a resection of the non-malignant tumor OR
        •	It is unknown/not documented whether a resection was done
        Note:	Abstract the second tumor (malignant) using the Malignant CNS rules.

    Rule M6	Abstract a single primary when the patient has bilateral:
        •	Acoustic neuromas/ vestibular schwannomas 9560/0
        •	Optic gliomas/pilocytic astrocytomas 9421/1
        Note 1:	The bilateral tumors may appear simultaneously (at the same time) OR the contralateral tumor may be diagnosed at any time following the original diagnosis.
        Note 2:	WHO and IARC designate pilocytic astrocytoma as a synonym for optic glioma.  When the primary site is optic nerve, the behavior is non-malignant.
        Note 3:	When the bilateral tumors are diagnosed at different times, the physician may stage each tumor because staging and determining multiple primaries are done for different reasons. Staging determines which course of treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).

    Rule M7	Abstract multiple primaries when multiple tumors are present in any of the following sites:
        •	Any lobe(s) of the brain C710-C719 AND any other part of CNS
        •	Cerebral meninges C700 AND spinal meninges C701
        •	Cerebral meninges C700 AND any other part of CNS
        •	Any cranial nerve(s) C721-C725 AND any other part of the CNS
        •	Meninges of cranial nerves C709 AND any other part of the CNS
        •   Spinal cord C720 AND any other part of CNS
        •	Spinal meninges C701 AND any other part of CNS
        (Any other part of the CNS is any other site in the header...for example "cerebral meninges C700 and any other part of the CNS" equates to C700 and any
         other site in the header besides C700 (C701, C709, C710-C719, C721-C725, C728, C729, C751-C753))

    Rule M8	Abstract multiple primaries when separate, non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 6 in the Equivalent Terms and Definitions. Timing is irrelevant.
        Note: 	The tumors may be subtypes/variants of the same or different NOS histologies.
        •	Same NOS: Atypical meningioma 9539/1 and fibrous meningioma 9532/0 are both subtypes of meningioma NOS 9530 but are distinctly different histologies. Abstract multiple primaries.
        •	Different NOS: Melanotic schwannoma 9560/1 is a subtype of schwannoma NOS 9560/0; papillary craniopharyngioma 9352/1 is a subtype of craniopharyngioma 9350/1. They are distinctly different histologies. Abstract multiple primaries.

    Rule M9	Abstract a single primary when two or more separate/non-contiguous meningiomas arise in the cranial meninges.  Laterality is irrelevant and may be any of the following combinations:
        •	The same laterality (left or right) of the cranial meninges
        •	Bilateral (both left and right) cranial meninges
        •	The midline AND in either the right or left cranial meninges
        Note:	This rule applies ONLY to meningiomas.

    Rule M10	Abstract a single primary when there are separate/non-contiguous tumors in the brain (multicentric/multifocal) with the same histology XXXX.  Tumors may be in any of the following locations and/or lateralities:
        •	Same laterality: In the same lobe; for example, two tumors in right temporal lobe C712 (same site code)
        •	Different lateralities of the same lobe; for example, left and right frontal lobes C711 (same site code)
        •	Different lobes; for example, parietal lobe C713 and occipital lobe C714 (different site codes)
        Note 1:	Metastases are never used to determine multiple primaries. Seeding metastasis is often noted in ependymomas.
        Note 2:	This is a change from/clarification to previous rules.
        Note 3:	These rules are hierarchical.  Use this rule ONLY when the previous rules do not apply.
        Note 4:	An example of a non-malignant brain tumor that may be multi-focal/multi-centric is hemangioblastoma 9161/1.
        Note 5:	The physician may stage each tumor because staging and determining multiple primaries are done for different reasons. Staging determines which course of treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).

    Rule M11	Abstract a single primary when separate/non-contiguous tumors are on the same row in Table 6 in the Equivalent Terms and Definitions.  Timing is irrelevant.
        Note: 	The same row means the tumors are:
            •	The same histology (same four-digit ICD-O code) OR
            •	One is the preferred term (column 1) and the other is a synonym for the preferred term (column 2) OR
            •	A NOS (column 1/column 2) and the other is a subtype/variant of that NOS (column 3). NOS and subtype/variants are:
                	Choroid plexus papilloma 9390/0 and a subtype/variant of choroid plexus papilloma
                	Craniopharyngioma 9350/1 and a subtype/variant of craniopharyngioma
                	Gangliocytoma 9492/0 and a subtype/variant of craniopharyngioma
                	Lipoma 8860/0 and a subtype/variant of lipoma
                	Meningeal melanocytosis 8728/0 and a subtype/variant of meningeal melanocytosis
                	Meningioma 9530/0 and a subtype/variant of meningioma
                	Myofibroblastoma 8825/0 and a subtype/variant of myofibroblastoma
                	Neurofibroma 9540/0 and a subtype/variant of neurofibroma
                	Schwannoma 9560/0 and a subtype/variant of schwannoma
                	Solitary fibrous tumor WHO Grade 1 8815/0 and a subtype/variant of solitary fibrous tumor WHO Grade 1

    Rule M12	Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 6 in the Equivalent Terms and Definitions. Timing is irrelevant.
        Note:	Each row in the table is a distinctly different histology.

    Rule M13	Abstract a single primary when the tumors do not meet any of the above criteria.
        Note:	These rules are hierarchical.  Use this rule ONLY when the previous rules do not apply.

    */

    // Non-Malignant CNS Multiple Primary Rules
    // C700, C701, C709, C710-C719, C720-C725, C728, C729, C751-C753
    // (Excludes lymphoma and leukemia M9590 – M9992 and Kaposi sarcoma M9140)
    public Mp2018NonMalignantCNSTumorsGroup() {
        super(MphConstants.MP_2018_NON_MALIGNANT_CNS_TUMORS_GROUP_ID, MphConstants.MP_2018_NON_MALIGNANT_CNS_TUMORS_GROUP_NAME,
                "C700, C701, C709, C710-C719, C720-C725, C728, C729, C751-C753", null, null,
                "9590-9992, 9140", "0-1", "2018-9999");

        // Rule M5	Abstract multiple primaries when a malignant tumor /3 occurs after a non-malignant tumor /0 or /1 AND:
        // •	The patient had a resection of the non-malignant tumor OR
        // •	It is unknown/not documented whether a resection was done
        MphRule rule = new MphRule(MphConstants.MP_2018_NON_MALIGNANT_CNS_TUMORS_GROUP_ID, "M5") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                //This will never happen, since the two conditions belong to different cancer group.
                return new TempRuleResult();
            }
        };
        rule.setQuestion("Is there a malignant tumor following a non-malignant tumor?");
        rule.setReason("A malignant tumor diagnosed following an non-malignant tumor is multiple primaries.");
        _rules.add(rule);

        // Rule M6	Abstract a single primary when the patient has bilateral:
        // •	Acoustic neuromas/ vestibular schwannomas 9560/0, OR
        // •	Optic gliomas/pilocytic astrocytomas 9421/1
        rule = new MphRule(MphConstants.MP_2018_NON_MALIGNANT_CNS_TUMORS_GROUP_ID, "M6") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                String icd1 = i1.getHistology() + "/" + i1.getBehavior(), icd2 = i2.getHistology() + "/" + i2.getBehavior();
                if (icd1.equals(icd2) && Arrays.asList("9560/0", "9421/1").contains(icd1))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);

                return result;
            }
        };
        rule.setQuestion("Are the tumors bilateral acoustic neuromas/vestibular schwannomas 9560/0 or bilateral optic gliomas/pilocytic astrocytomas 9421/1?");
        rule.setReason("Bilateral acoustic neuromas/vestibular schwannomas 9560/0 or bilateral optic gliomas/pilocytic astrocytomas 9421/1 are a single primary.");
        rule.getNotes().add("The bilateral tumors may appear simultaneously (at the same time) OR the contralateral tumor may be diagnosed at any time following the original diagnosis.");
        rule.getNotes().add("WHO and IARC designate pilocytic astrocytoma as a synonyn for optic glioma.  When the primary site is optic nerve, the behavior is non-malignant.");
        rule.getNotes().add(
                "When the bilateral tumors are diagnosed at different times, the physician may stage each tumor because staging and determining multiple primaries are done for different reasons. Staging determines which course of treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).");
        _rules.add(rule);

        // Rule M7	Abstract multiple primaries when multiple tumors are present in any of the following sites:
        // •	Any lobe(s) of the brain C710-C719 AND any other part of CNS
        // •	Cerebral meninges C700 AND spinal meninges C701
        // •	Cerebral meninges C700 AND any other part of CNS
        // •	Any cranial nerve(s) C721-C725 AND any other part of the CNS
        // •	Meninges of cranial nerves C709 AND any other part of the CNS
        // •Spinal cord C720 AND any other part of CNS
        // •	Spinal meninges C701 AND any other part of CNS
        // (Any other part of the CNS is any other site in the header...for example "cerebral meninges C700 and any other part of the CNS" equates to C700 and any
        //  other site in the header besides C700 (C701, C709, C710-C719, C720, C721-C725, C728, C729, C751-C753))
        rule = new MphRule(MphConstants.MP_2018_NON_MALIGNANT_CNS_TUMORS_GROUP_ID, "M7") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                String s1 = i1.getPrimarySite(), s2 = i2.getPrimarySite();

                // •	Any lobe(s) of the brain C710-C719 AND any other part of CNS
                if ((GroupUtility.isSiteContained(MphConstants.CNS_2018_BRAIN_SITES, s1) && !GroupUtility.isSiteContained(MphConstants.CNS_2018_BRAIN_SITES, s2))
                        || (GroupUtility.isSiteContained(MphConstants.CNS_2018_BRAIN_SITES, s2) && !GroupUtility.isSiteContained(MphConstants.CNS_2018_BRAIN_SITES, s1)))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    // • Cerebral meninges C700 AND spinal meninges C701, Cerebral meninges C700 AND any other part of CNS
                else if ((GroupUtility.isSiteContained(MphConstants.CNS_2018_CEREBRAL_MENINGES_SITES, s1) && !GroupUtility.isSiteContained(MphConstants.CNS_2018_CEREBRAL_MENINGES_SITES, s2))
                        || (GroupUtility.isSiteContained(MphConstants.CNS_2018_CEREBRAL_MENINGES_SITES, s2) && !GroupUtility.isSiteContained(MphConstants.CNS_2018_CEREBRAL_MENINGES_SITES, s1)))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    // •	Any cranial nerve(s) C721-C725 AND any other part of the CNS
                else if ((GroupUtility.isSiteContained(MphConstants.CNS_2018_CRANIAL_NERVES_SITES_ALL, s1) && !GroupUtility.isSiteContained(MphConstants.CNS_2018_CRANIAL_NERVES_SITES_ALL, s2))
                        || (GroupUtility.isSiteContained(MphConstants.CNS_2018_CRANIAL_NERVES_SITES_ALL, s2) && !GroupUtility.isSiteContained(MphConstants.CNS_2018_CRANIAL_NERVES_SITES_ALL, s1)))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    // •	Meninges of cranial nerves C709 AND any other part of the CNS
                else if ((GroupUtility.isSiteContained(MphConstants.CNS_2018_MENINGES_OF_CRANIAL_OR_PERIPH_NERVES_SITES, s1) && !GroupUtility.isSiteContained(
                        MphConstants.CNS_2018_MENINGES_OF_CRANIAL_OR_PERIPH_NERVES_SITES, s2))
                        || (GroupUtility.isSiteContained(MphConstants.CNS_2018_MENINGES_OF_CRANIAL_OR_PERIPH_NERVES_SITES, s2) && !GroupUtility.isSiteContained(
                        MphConstants.CNS_2018_MENINGES_OF_CRANIAL_OR_PERIPH_NERVES_SITES, s1)))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    // •Spinal cord C720 AND any other part of CNS
                else if ((GroupUtility.isSiteContained(MphConstants.CNS_2018_SPINAL_CORD_SITES, s1) && !GroupUtility.isSiteContained(MphConstants.CNS_2018_SPINAL_CORD_SITES, s2))
                        || (GroupUtility.isSiteContained(MphConstants.CNS_2018_SPINAL_CORD_SITES, s2) && !GroupUtility.isSiteContained(MphConstants.CNS_2018_SPINAL_CORD_SITES, s1)))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    // •	Spinal meninges C701 AND any other part of CNS
                else if ((GroupUtility.isSiteContained(MphConstants.CNS_2018_SPINAL_MENINGES_SITES, s1) && !GroupUtility.isSiteContained(MphConstants.CNS_2018_SPINAL_MENINGES_SITES, s2))
                        || (GroupUtility.isSiteContained(MphConstants.CNS_2018_SPINAL_MENINGES_SITES, s2) && !GroupUtility.isSiteContained(MphConstants.CNS_2018_SPINAL_MENINGES_SITES, s1)))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Are multiple tumors present in the following sites:\n" +
                "Any lobe(s) of the brain C710-C719 AND any other part of CNS\n" +
                "Cerebral meninges C700 AND spinal meninges C701\n" +
                "Cerebral meninges C700 AND any other part of CNS\n" +
                "Any cranial nerve(s) C721-C725 AND any other part of the CNS\n" +
                "Meninges of cranial nerves C709 AND any other part of the CNS\n" +
                "Spinal meninges C701 AND any other part of CNS?");
        rule.setReason("Multiple tumors present in the following sites:\n" +
                "Any lobe(s) of the brain C710-C719 AND any other part of CNS\n" +
                "Cerebral meninges C700 AND spinal meninges C701\n" +
                "Cerebral meninges C700 AND any other part of CNS\n" +
                "Any cranial nerve(s) C721-C725 AND any other part of the CNS\n" +
                "Meninges of cranial nerves C709 AND any other part of the CNS\n" +
                "Spinal meninges C701 AND any other part of CNS\n" +
                "are multiple primaries.");
        _rules.add(rule);

        // Rule M8	Abstract multiple primaries when separate, non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 6 in the Equivalent Terms and Definitions. Timing is irrelevant.
        rule = new MphRule(MphConstants.MP_2018_NON_MALIGNANT_CNS_TUMORS_GROUP_ID, "M8") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                String icd1 = i1.getHistology() + "/" + i1.getBehavior(), icd2 = i2.getHistology() + "/" + i2.getBehavior();
                if (!i1.getHistology().equals(i2.getHistology()) && MphConstants.NON_MALIGNANT_CNS_2018_TABLE6_SUBTYPES.containsAll(Arrays.asList(icd1, icd2)))
                    result.setFinalResult(MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Are separate/non-contiguous tumors two or more different subtypes/variants in Column 3, Table 6 in the Equivalent Terms and Definitions?");
        rule.setReason("Separate/non-contiguous tumors that are two or more different subtypes/variants in Column 3, Table 6 in the Equivalent Terms and Definitions are multiple primaries.");
        rule.getNotes().add("The tumors may be subtypes/variants of the same or different NOS histologies.");
        rule.getNotes().add(
                "  • Same NOS: Atypical meningioma 9539/1 and fibrous meningioma 9532/0 are both subtypes of meningioma NOS 9530 but are distinctly different histologies. Abstract multiple primaries.");
        rule.getNotes().add(
                "  • Different NOS: Melanotic schwannoma 9560/1 is a subtype of schwannoma NOS 9560/0; papillary craniopharyngioma 9352/1 is a subtype of craniopharyngioma 9350/1. They are distinctly different histologies. Abstract multiple primaries.");
        _rules.add(rule);

        // Rule M9	Abstract a single primary when two or more separate/non-contiguous meningiomas arise in the cranial meninges.  Laterality is irrelevant and may be any of the following combinations:
        // •	The same laterality (left or right) of the cranial meninges
        // •	Bilateral (both left and right) cranial meninges
        // •	The midline AND in either the right or left cranial meninges
        rule = new MphRule(MphConstants.MP_2018_NON_MALIGNANT_CNS_TUMORS_GROUP_ID, "M9") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                String icd1 = i1.getHistology() + "/" + i1.getBehavior(), icd2 = i2.getHistology() + "/" + i2.getBehavior();
                if (MphConstants.CNS_2018_CEREBRAL_MENINGES_SITES.equals(i1.getPrimarySite()) && i2.getPrimarySite().equals(i1.getPrimarySite()) && MphConstants.CNS_2018_MENINGIOMAS.containsAll(
                        Arrays.asList(icd1, icd2)))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                return result;
            }
        };
        rule.setQuestion("Are two or more separate/non-contiguous meningiomas arising in the cranial meninges?");
        rule.setReason("Two or more separate/non-contiguous meningiomas arising in the cranial meninges is a single primary.");
        rule.getNotes().add("This rule applies ONLY to meningiomas.");
        _rules.add(rule);

        // Rule M10	Abstract a single primary when there are separate/non-contiguous tumors in the brain (multicentric/multifocal) with the same histology XXXX.  Tumors may be in any of the following locations and/or lateralities:
        // •	Same laterality: In the same lobe; for example, two tumors in right temporal lobe C712 (same site code)
        // •	Different lateralities of the same lobe; for example, left and right frontal lobes C711 (same site code)
        // •	Different lobes; for example, parietal lobe C713 and occipital lobe C714 (different site codes)
        rule = new MphRule(MphConstants.MP_2018_NON_MALIGNANT_CNS_TUMORS_GROUP_ID, "M10") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (i1.getHistology().equals(i2.getHistology()) && GroupUtility.isSiteContained(MphConstants.CNS_2018_BRAIN_SITES, i1.getPrimarySite()) && GroupUtility.isSiteContained(
                        MphConstants.CNS_2018_BRAIN_SITES, i2.getPrimarySite()))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                return result;
            }
        };
        rule.setQuestion("Are the tumors separate, non-contiguous and in the brain (multicentric/multifocal)?");
        rule.setReason("Tumors which are separate, non-contiguous and in the brain (multicentric/multifocal) are a single primary.");
        rule.getNotes().add("Metastases are never used to determine multiple primaries. Seeding metastasis is often noted in ependymomas.");
        rule.getNotes().add("This is a change from/clarification to previous rules.");
        rule.getNotes().add("These rules are hierarchical.  Use this rule ONLY when the previous rules do not apply.");
        rule.getNotes().add("An example of a non-malignant brain tumor that may be multi-focal/multi-centric is hemangioblastoma 9161/1.");
        rule.getNotes().add(
                "The physician may stage each tumor because staging and determining multiple primaries are done for different reasons. Staging determines which course of treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).");
        _rules.add(rule);

        // Rule M11	Abstract a single primary when separate/non-contiguous tumors are on the same row in Table 6 in the Equivalent Terms and Definitions.  Timing is irrelevant.
        rule = new MphRule(MphConstants.MP_2018_NON_MALIGNANT_CNS_TUMORS_GROUP_ID, "M11") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                String h1 = i1.getHistology(), icd1 = h1 + "/" + i1.getBehavior(), h2 = i2.getHistology(), icd2 = h2 + "/" + i2.getBehavior();
                String row1 = MphConstants.NON_MALIGNANT_CNS_2018_TABLE6_ROWS.containsKey(h1) ? MphConstants.NON_MALIGNANT_CNS_2018_TABLE6_ROWS.get(
                        h1) : MphConstants.NON_MALIGNANT_CNS_2018_TABLE6_ROWS.get(icd1);
                String row2 = MphConstants.NON_MALIGNANT_CNS_2018_TABLE6_ROWS.containsKey(h2) ? MphConstants.NON_MALIGNANT_CNS_2018_TABLE6_ROWS.get(
                        h2) : MphConstants.NON_MALIGNANT_CNS_2018_TABLE6_ROWS.get(icd2);
                if (row1 == null || row2 == null) {
                    result.setFinalResult(MpResult.QUESTIONABLE);
                    result.setMessageNotInTable(this.getStep(), this.getGroupId());
                }
                else if (row1.equals(row2))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);

                return result;
            }
        };
        rule.setQuestion("Are separate/non-contiguous tumors on the same rows in Table 6 in the Equivalent Terms and Definitions?");
        rule.setReason("Separate/non-contiguous tumors on the same row in Table 6 in the Equivalent Terms and Definitions is a single primary.");
        rule.getNotes().add("The same row means the tumors are:");
        rule.getNotes().add("  • The same histology (same four-digit ICD-O code) OR");
        rule.getNotes().add("  • One is the preferred term (column 1) and the other is a synonym for the preferred term (column 2) OR");
        rule.getNotes().add("  • A NOS (column 1/column 2) and the other is a subtype/variant of that NOS (column 3). NOS and subtype/variants are:");
        rule.getNotes().add("     Choroid plexus papilloma 9390/0 and a subtype/variant of choroid plexus papilloma");
        rule.getNotes().add("     Craniopharyngioma 9350/1 and a subtype/variant of craniopharyngioma");
        rule.getNotes().add("     Gangliocytoma 9492/0 and a subtype/variant of craniopharyngioma");
        rule.getNotes().add("     Lipoma 8860/0 and a subtype/variant of lipoma");
        rule.getNotes().add("     Meningeal melanocytosis 8728/0 and a subtype/variant of meningeal melanocytosis");
        rule.getNotes().add("     Meningioma 9530/0 and a subtype/variant of meningioma");
        rule.getNotes().add("     Myofibroblastoma 8825/0 and a subtype/variant of myofibroblastoma");
        rule.getNotes().add("     Neurofibroma 9540/0 and a subtype/variant of neurofibroma");
        rule.getNotes().add("     Schwannoma 9560/0 and a subtype/variant of schwannoma");
        rule.getNotes().add("     Solitary fibrous tumor WHO Grade 1 8815/0 and a subtype/variant of solitary fibrous tumor WHO Grade 1");
        _rules.add(rule);

        // Rule M12	Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 6 in the Equivalent Terms and Definitions. Timing is irrelevant.
        rule = new MphRule(MphConstants.MP_2018_NON_MALIGNANT_CNS_TUMORS_GROUP_ID, "M12") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                String h1 = i1.getHistology(), icd1 = h1 + "/" + i1.getBehavior(), h2 = i2.getHistology(), icd2 = h2 + "/" + i2.getBehavior();
                String row1 = MphConstants.NON_MALIGNANT_CNS_2018_TABLE6_ROWS.containsKey(h1) ? MphConstants.NON_MALIGNANT_CNS_2018_TABLE6_ROWS.get(
                        h1) : MphConstants.NON_MALIGNANT_CNS_2018_TABLE6_ROWS.get(icd1);
                String row2 = MphConstants.NON_MALIGNANT_CNS_2018_TABLE6_ROWS.containsKey(h2) ? MphConstants.NON_MALIGNANT_CNS_2018_TABLE6_ROWS.get(
                        h2) : MphConstants.NON_MALIGNANT_CNS_2018_TABLE6_ROWS.get(icd2);
                if (row1 == null || row2 == null) {
                    result.setFinalResult(MpResult.QUESTIONABLE);
                    result.setMessageNotInTable(this.getStep(), this.getGroupId());
                }
                else if (!row1.equals(row2))
                    result.setFinalResult(MpResult.MULTIPLE_PRIMARIES);

                return result;
            }
        };
        rule.setQuestion("Are separate/non-contiguous tumors on different rows in Table 6 in the Equivalent Terms and Definitions?");
        rule.setReason("Separate/non-contiguous tumors on different rows in Table 6 in the Equivalent Terms and Definitions is multiple primaries.");
        rule.getNotes().add("Each row in the table is a distinctly different histology.");
        _rules.add(rule);

        // Rule M13	Abstract a single primary when the tumors do not meet any of the above criteria.
        rule = new MpRuleNoCriteriaSatisfied(MphConstants.MP_2018_NON_MALIGNANT_CNS_TUMORS_GROUP_ID, "M13");
        rule.getNotes().add("These rules are hierarchical.  Use this rule ONLY when the previous rules do not apply.");
        _rules.add(rule);
    }
}


