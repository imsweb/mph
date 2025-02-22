/*
 * Copyright (C) 2018 Information Management Services, Inc.
 */
package com.imsweb.mph.mpgroups;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.imsweb.mph.MphConstants;
import com.imsweb.mph.MphGroup;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphUtils;
import com.imsweb.mph.MphUtils.MpResult;
import com.imsweb.mph.RuleExecutionContext;
import com.imsweb.mph.internal.TempRuleResult;
import com.imsweb.mph.mprules.MpRuleCNS;
import com.imsweb.mph.mprules.MpRuleNoCriteriaSatisfied;

public class Mp2018NonMalignantCNSTumorsGroup extends MphGroup {

    // Non-Malignant CNS Multiple Primary Rules
    // C700, C701, C709, C710-C719, C720-C725, C728, C729, C751-C753
    // (Excludes lymphoma and leukemia M9590 – M9992 and Kaposi sarcoma M9140)
    public Mp2018NonMalignantCNSTumorsGroup() {
        super(MphConstants.STR_2018_AND_LATER_NON_MALIGNANT_CNS, MphConstants.SOLID_TUMOR_2018_NON_MALIGNANT_CNS,
                "C700, C701, C709, C710-C719, C720-C725, C728, C729, C751-C753", null, null,
                "9590-9993, 9140", "0-1", "2018-9999");

        // Rule M6 Abstract multiple primaries when a malignant tumor /3 occurs after a non-malignant tumor /0 or /1 AND:
        // - The patient had a resection of the non-malignant tumor OR
        // - It is unknown/not documented whether a resection was done
        MphRule rule = new MphRule(MphConstants.SOLID_TUMOR_2018_NON_MALIGNANT_CNS, "M6") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
                //This will never happen, since the two conditions belong to different cancer group.
                return new TempRuleResult();
            }
        };
        rule.setQuestion("Is there a malignant tumor following a non-malignant tumor?");
        rule.setReason("A malignant tumor diagnosed following an non-malignant tumor is multiple primaries.");
        _rules.add(rule);

        // Rule M7 Abstract a single primary when the patient has bilateral:
        // - Acoustic neuromas/ vestibular schwannomas 9560/0, OR
        // - Optic gliomas/pilocytic astrocytomas 9421/1
        rule = new MphRule(MphConstants.SOLID_TUMOR_2018_NON_MALIGNANT_CNS, "M7") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
                TempRuleResult result = new TempRuleResult();
                List<String> pairedSites = Arrays.asList("C724", "C700", "C710", "C725", "C711", "C714", "C722", "C723", "C713", "C712");
                if (new HashSet<>(pairedSites).containsAll(Arrays.asList(i1.getPrimarySite(), i2.getPrimarySite())) && i1.getPrimarySite().equals(i2.getPrimarySite()) && GroupUtility.areOppositeSides(
                        i1.getLaterality(), i2.getLaterality())) {
                    String icd1 = i1.getIcdCode();
                    String icd2 = i2.getIcdCode();
                    if (icd1.equals(icd2) && Arrays.asList("9560/0", "9421/1").contains(icd1))
                        result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                }

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

        // Rule M8 Abstract multiple primaries when multiple tumors are present in any of the following sites:
        // - Any lobe(s) of the brain C710-C719 AND any other part of CNS
        // - Cauda equina C721 AND any other part of CNS
        // - Cerebral meninges C700 AND spinal meninges C701
        // - Cerebral meninges C700 AND any other part of CNS
        // - Any cranial nerve(s) C722-C725 AND any other part of the CNS
        // - Meninges of cranial nerves C709 AND any other part of the CNS
        // -Spinal cord C720 AND any other part of CNS
        // - Spinal meninges C701 AND any other part of CNS
        // (Any other part of the CNS is any other site in the header...for example "cerebral meninges C700 and any other part of the CNS" equates to C700 and any
        //  other site in the header besides C700 (C701, C709, C710-C719, C720, C721-C725, C728, C729, C751-C753))
        rule = new MpRuleCNS(MphConstants.SOLID_TUMOR_2018_NON_MALIGNANT_CNS, "M8", false);
        _rules.add(rule);

        // Rule M9 Abstract multiple primaries when separate, non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 6 in the Equivalent Terms and Definitions. Timing is irrelevant.
        rule = new MphRule(MphConstants.SOLID_TUMOR_2018_NON_MALIGNANT_CNS, "M9") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
                TempRuleResult result = new TempRuleResult();
                String icd1 = i1.getIcdCode();
                String icd2 = i2.getIcdCode();
                if (!i1.getHistology().equals(i2.getHistology()) && MphConstants.NON_MALIGNANT_CNS_2018_TABLE6_SUBTYPES.containsAll(Arrays.asList(icd1, icd2)))
                    result.setFinalResult(MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Are separate/non-contiguous tumors two or more different subtypes/variants in Column 3, Table 6 in the Equivalent Terms and Definitions?");
        rule.setReason("Separate/non-contiguous tumors that are two or more different subtypes/variants in Column 3, Table 6 in the Equivalent Terms and Definitions are multiple primaries.");
        rule.getNotes().add("The tumors may be subtypes/variants of the same or different NOS histologies.");
        rule.getNotes().add(
                "  - Same NOS: Atypical meningioma 9539/1 and fibrous meningioma 9532/0 are both subtypes of meningioma NOS 9530 but are distinctly different histologies. Abstract multiple primaries.");
        rule.getNotes().add(
                "  - Different NOS: Melanotic schwannoma 9560/1 is a subtype of schwannoma NOS 9560/0; papillary craniopharyngioma 9352/1 is a subtype of craniopharyngioma 9350/1. They are distinctly different histologies. Abstract multiple primaries.");
        _rules.add(rule);

        // Rule M10 Abstract a single primary when two or more separate/non-contiguous meningiomas arise in the cranial meninges.  Laterality is irrelevant and may be any of the following combinations:
        // - The same laterality (left or right) of the cranial meninges
        // - Bilateral (both left and right) cranial meninges
        // - The midline AND in either the right or left cranial meninges
        rule = new MphRule(MphConstants.SOLID_TUMOR_2018_NON_MALIGNANT_CNS, "M10") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
                TempRuleResult result = new TempRuleResult();
                String icd1 = i1.getIcdCode();
                String icd2 = i2.getIcdCode();
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

        // Rule M11 Abstract a single primary when there are separate/non-contiguous tumors in the brain (multicentric/multifocal) with the same histology XXXX.  Tumors may be in any of the following locations and/or lateralities:
        // - Same laterality: In the same lobe; for example, two tumors in right temporal lobe C712 (same site code)
        // - Different lateralities of the same lobe; for example, left and right frontal lobes C711 (same site code)
        // - Different lobes; for example, parietal lobe C713 and occipital lobe C714 (different site codes)
        rule = new MphRule(MphConstants.SOLID_TUMOR_2018_NON_MALIGNANT_CNS, "M11") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
                TempRuleResult result = new TempRuleResult();
                if (i1.getHistology().equals(i2.getHistology()) && GroupUtility.isSiteContained(MphConstants.CNS_2018_BRAIN_SITES, i1.getPrimarySite()) && GroupUtility.isSiteContained(
                        MphConstants.CNS_2018_BRAIN_SITES, i2.getPrimarySite())) {
                    //Special case 9413 and 9509
                    if ("9413".equals(i1.getHistology())) {
                        result.setFinalResult(MpResult.QUESTIONABLE);
                        result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupName() + ". The ICD-O code (9413) can be used for both DNET and PLNTY.");
                    }
                    else if ("9509".equals(i1.getHistology()) && !i1.getBehavior().equals(i2.getBehavior())) {
                        result.setFinalResult(MpResult.QUESTIONABLE);
                        result.setMessage(
                                "Unable to apply Rule " + this.getStep() + " of " + this.getGroupName() + ". The ICD-O code (9509) can be used for both MVNT and papillary glioneural tumor.");
                    }
                    else
                        result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                }
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

        // Rule M12 Abstract a single primary when separate/non-contiguous tumors are on the same row in Table 6 in the Equivalent Terms and Definitions.  Timing is irrelevant.
        rule = new MphRule(MphConstants.SOLID_TUMOR_2018_NON_MALIGNANT_CNS, "M12") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
                TempRuleResult result = new TempRuleResult();
                String h1 = i1.getHistology();
                String icd1 = i1.getIcdCode();
                String h2 = i2.getHistology();
                String icd2 = i2.getIcdCode();
                Map<String, String> i1Table6 = Integer.parseInt(i1.getDateOfDiagnosisYear())
                        >= 2023 ? MphConstants.NON_MALIGNANT_CNS_2023_TABLE6_ROWS : MphConstants.NON_MALIGNANT_CNS_2018_TABLE6_ROWS;
                Map<String, String> i2Table6 = Integer.parseInt(i2.getDateOfDiagnosisYear())
                        >= 2023 ? MphConstants.NON_MALIGNANT_CNS_2023_TABLE6_ROWS : MphConstants.NON_MALIGNANT_CNS_2018_TABLE6_ROWS;
                String row1 = i1Table6.containsKey(h1) ? i1Table6.get(h1) : i1Table6.get(icd1);
                String row2 = i2Table6.containsKey(h2) ? i2Table6.get(h2) : i2Table6.get(icd2);
                if (!GroupUtility.sameHistologies(icd1, icd2) && (row1 == null || row2 == null)) {
                    result.setFinalResult(MpResult.QUESTIONABLE);
                    result.setMessageNotInTable(this.getStep(), this.getGroupName(), row1, row2, icd1, icd2);
                }
                else if (GroupUtility.sameHistologies(icd1, icd2) || row1.equals(row2)) {
                    //Special case 9413 and 9509
                    if ("9413".equals(i1.getHistology())) {
                        result.setFinalResult(MpResult.QUESTIONABLE);
                        result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupName() + ". The ICD-O code (9413) can be used for both DNET and PLNTY.");
                    }
                    else if ("9509".equals(i1.getHistology()) && !i1.getBehavior().equals(i2.getBehavior())) {
                        result.setFinalResult(MpResult.QUESTIONABLE);
                        result.setMessage(
                                "Unable to apply Rule " + this.getStep() + " of " + this.getGroupName() + ". The ICD-O code (9509) can be used for both MVNT and papillary glioneural tumor.");
                    }
                    else
                        result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                }

                return result;
            }
        };
        rule.setQuestion("Are separate/non-contiguous tumors on the same rows in Table 6 in the Equivalent Terms and Definitions?");
        rule.setReason("Separate/non-contiguous tumors on the same row in Table 6 in the Equivalent Terms and Definitions are a single primary.");
        rule.getNotes().add("The same row means the tumors are:");
        rule.getNotes().add("  - The same histology (same four-digit ICD-O code) OR");
        rule.getNotes().add("  - One is the preferred term (column 1) and the other is a synonym for the preferred term (column 2) OR");
        rule.getNotes().add("  - A NOS (column 1/column 2) and the other is a subtype/variant of that NOS (column 3). NOS and subtype/variants are:");
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

        // Rule M13 Abstract a single primary when separate, non-contiguous tumors are Glioma NOS and a subtype/variant of Glioma NOS.
        rule = new MphRule(MphConstants.SOLID_TUMOR_2018_NON_MALIGNANT_CNS, "M13") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
                TempRuleResult result = new TempRuleResult();
                List<String> gilomaNOS = Collections.singletonList("9380/1");
                List<String> gilomaVariants = Arrays.asList("9383/1", "9394/1", "9444/1", "9384/1", "9412/1", "9413/0", "9505/1", "9506/1", "9492/0", "9493/0", "9509/1", "8693/1");
                if (GroupUtility.differentCategory(i1.getIcdCode(), i2.getIcdCode(), gilomaNOS, gilomaVariants))
                    result.setFinalResult(MpResult.SINGLE_PRIMARY);
                return result;
            }
        };
        rule.setQuestion("Are separate, non-contiguous tumors Glioma NOS and a subtype/variant of Glioma NOS?");
        rule.setReason("Abstract a single primary when separate, non-contiguous tumors are Glioma NOS and a subtype/variant of Glioma NOS.");
        rule.getNotes().add("Low-grade glioma is considered an umbrella term or non-specific diagnosis, primarily seen on radiographic reports such as CT scans and MRIs. Often the patient is actively followed with scans and surgical intervention delayed or not recommended that would provide a definitive histology type. A diagnosis of low-grade glioma is not recommended and may be used when the diagnosis is based on imaging and/or additional tests were inconclusive.");
        _rules.add(rule);

        // Rule M14 Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 6 in the Equivalent Terms and Definitions. Timing is irrelevant.
        rule = new MphRule(MphConstants.SOLID_TUMOR_2018_NON_MALIGNANT_CNS, "M14") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
                TempRuleResult result = new TempRuleResult();
                String h1 = i1.getHistology();
                String icd1 = i1.getIcdCode();
                String h2 = i2.getHistology();
                String icd2 = i2.getIcdCode();
                //If they are same code, no need to check if they are in different rows.
                if (GroupUtility.sameHistologies(icd1, icd2))
                    return result;
                Map<String, String> i1Table6 = Integer.parseInt(i1.getDateOfDiagnosisYear())
                        >= 2023 ? MphConstants.NON_MALIGNANT_CNS_2023_TABLE6_ROWS : MphConstants.NON_MALIGNANT_CNS_2018_TABLE6_ROWS;
                Map<String, String> i2Table6 = Integer.parseInt(i2.getDateOfDiagnosisYear())
                        >= 2023 ? MphConstants.NON_MALIGNANT_CNS_2023_TABLE6_ROWS : MphConstants.NON_MALIGNANT_CNS_2018_TABLE6_ROWS;
                String row1 = i1Table6.containsKey(h1) ? i1Table6.get(h1) : i1Table6.get(icd1);
                String row2 = i2Table6.containsKey(h2) ? i2Table6.get(h2) : i2Table6.get(icd2);
                if (row1 == null || row2 == null) {
                    result.setFinalResult(MpResult.QUESTIONABLE);
                    result.setMessageNotInTable(this.getStep(), this.getGroupName(), row1, row2, icd1, icd2);
                }
                else if (row1.startsWith("8000") || row2.startsWith("8000")) {
                    result.setFinalResult(MpResult.QUESTIONABLE);
                    result.setMessage("Unable to determine if " + (row1.startsWith("8000") ? icd1 : icd2) + " is a different row.");
                }
                else if (!row1.equals(row2))
                    result.setFinalResult(MpResult.MULTIPLE_PRIMARIES);

                return result;
            }
        };
        rule.setQuestion("Are separate/non-contiguous tumors on different rows in Table 6 in the Equivalent Terms and Definitions?");
        rule.setReason("Separate/non-contiguous tumors on different rows in Table 6 in the Equivalent Terms and Definitions are multiple primaries.");
        rule.getNotes().add("Each row in the table is a distinctly different histology.");
        _rules.add(rule);

        // Rule M15 Abstract a single primary when the tumors do not meet any of the above criteria.
        rule = new MpRuleNoCriteriaSatisfied(MphConstants.SOLID_TUMOR_2018_NON_MALIGNANT_CNS, "M15");
        rule.getNotes().add("These rules are hierarchical.  Use this rule ONLY when the previous rules do not apply.");
        _rules.add(rule);
    }
}


