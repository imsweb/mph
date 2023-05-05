/*
 * Copyright (C) 2018 Information Management Services, Inc.
 */
package com.imsweb.mph.mpgroups;

import java.util.Collections;

import com.imsweb.mph.MphConstants;
import com.imsweb.mph.MphGroup;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphUtils;
import com.imsweb.mph.MphUtils.MpResult;
import com.imsweb.mph.internal.TempRuleResult;
import com.imsweb.mph.mprules.MpRuleNoCriteriaSatisfied;

public class Mp2018MalignantCNSAndPeripheralNervesGroup extends MphGroup {

    // Malignant CNS and Peripheral Nerves Histology Rules
    // C470-C479, C700, C701, C709, C710-C719, C720-C725, C728, C729, C751-C753
    // (Excludes lymphoma and leukemia M9590 – M9992 and Kaposi sarcoma M9140)
    public Mp2018MalignantCNSAndPeripheralNervesGroup() {
        super(MphConstants.MP_2018_MALIGNANT_CNS_AND_PERIPHERAL_NERVES_GROUP_ID, MphConstants.MP_2018_MALIGNANT_CNS_AND_PERIPHERAL_NERVES_GROUP_NAME,
                "C470-C479, C700, C701, C709, C710-C719, C720-C725, C728, C729, C751-C753", null,
                null, "9590-9993, 9140", "3", "2018-9999");

        // Rule M5 Abstract multiple primaries when there are multiple CNS tumors, one of which is malignant /3 and the other is non-malignant /0 or /1.
        // - Original non-malignant tumor followed by malignant tumor
        //      Patient had a resection of the non-malignant tumor (not the same tumor) OR
        //      It is unknown/not documented if the patient had a resection
        // - Simultaneous non-malignant and malignant tumors
        //      Abstract both the malignant and the non-malignant tumors
        MphRule rule = new MphRule(MphConstants.MP_2018_MALIGNANT_CNS_AND_PERIPHERAL_NERVES_GROUP_ID, "M5") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                //This will never happen, since the two conditions belong to different cancer groups.
                return new TempRuleResult();
            }
        };
        rule.getNotes().add("The rules are hierarchical. Only use when previous rules do not apply.");
        rule.getNotes().add("See Table 2 in the Equivalent Terms and Definitions for a listing of CNS sites.");
        rule.getNotes().add(
                "A non-malignant CNS tumor and a malignant CNS tumor are always multiple primaries (timing and primary sites are irrelevant). Prepare two abstracts; one for the non-malignant and another for the malignant tumor.");
        _rules.add(rule);

        // Rule M6 Abstract multiple primaries when a patient has a glial tumor and is subsequently diagnosed with a glioblastoma multiforme 9440 (GBM).
        rule = new MphRule(MphConstants.MP_2018_MALIGNANT_CNS_AND_PERIPHERAL_NERVES_GROUP_ID, "M6") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                String h1 = i1.getHistology();
                String h2 = i2.getHistology();
                if (!h1.equals(h2) && GroupUtility.differentCategory(h1, h2, MphConstants.GLIAL_TUMOR_2018, Collections.singletonList(MphConstants.GLIOBLASTOMA_NOS_AND_MULTIFORME))) {
                    int laterDiagnosedTumor = GroupUtility.compareDxDate(i1, i2);
                    if (MphConstants.COMPARE_DX_UNKNOWN == laterDiagnosedTumor) { //If impossible to decide which tumor is diagnosed later
                        result.setPotentialResult(MpResult.MULTIPLE_PRIMARIES);
                        result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupId());
                    }
                    else if (MphConstants.COMPARE_DX_FIRST_LATEST == laterDiagnosedTumor && MphConstants.GLIOBLASTOMA_NOS_AND_MULTIFORME.equals(h1))
                        result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    else if (MphConstants.COMPARE_DX_SECOND_LATEST == laterDiagnosedTumor && MphConstants.GLIOBLASTOMA_NOS_AND_MULTIFORME.equals(h2))
                        result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                }
                return result;
            }
        };
        rule.setQuestion("Does the patient have a glial tumor and is subsequently diagnosed with a glioblastoma multiforme 9440 (GBM)?");
        rule.setReason("A glial tumor that is subsequently diagnosed with a glioblastoma multiforme 9440 (GBM) is multiple primaries.");
        rule.getNotes().add("Definition of a glial tumor: Any tumor arising from the CNS glial cells.  The following is simply a list of all tumors which would be classified as glial.");
        rule.getNotes().add("  - Astroblastoma 9430");
        rule.getNotes().add("  - Astrocytomas 9400 and all subtypes");
        rule.getNotes().add("     Anaplastic astrocytoma IDH-mutant/wildtype; anaplastic astrocytoma NOS 9401");
        rule.getNotes().add("     Gemistocytic astrocytoma IDH-mutant 9411");
        rule.getNotes().add("  - Diffuse midline glioma H3 K27M Mutant 9385");
        rule.getNotes().add("  - Ependymoma 9391 and all subtypes");
        rule.getNotes().add("     Anaplastic ependymoma 9392");
        rule.getNotes().add("     Ependymoma, RELA fusion-positive 9396");
        rule.getNotes().add("     Papillary ependymoma 9393");
        rule.getNotes().add("  - Glioblastoma 9440 and all subtypes (this is a glial tumor; however do not apply this rule to a GBM followed by a GBM)");
        rule.getNotes().add("     Giant cell glioblastoma 9441");
        rule.getNotes().add("     Glioblastoma IDH-mutant 9445");
        rule.getNotes().add("     Gliosarcoma 9442");
        rule.getNotes().add("  - Oligodendroglioma and all subtypes 9450");
        rule.getNotes().add("     Anaplastic oligodendroglioma; IDH-mutant; 1p/19q-codeleted; IDH-mutant and 1p/19q-codeleted 9451");
        rule.getNotes().add("  - Pleomorphic xanthroastrocytoma 9424");
        rule.getNotes().add("This is a change from the 2007 Rules.");
        rule.getNotes().add("Abstracting GBM as a new primary will allow analysis of:");
        rule.getNotes().add("  - The number of tumors that recur as a more aggressive histology (GBM)");
        rule.getNotes().add("  - The time interval between occurrence of the glial or astrocytic tumors and a GBM");
        rule.getNotes().add("  - Which histologies are more likely to recur as a GBM");
        _rules.add(rule);

        // Rule M7 Abstract a single primary when there are separate, non-contiguous tumors in the brain (multicentric/multifocal) with the same histology XXXX.  Tumors may be any of the following combinations:
        // - In the same lobe; for example, two tumors in right temporal lobe C712 (same site code)
        // - Different lateralities of the same lobe; for example, left and right frontal lobes C711 (same site code)
        // - In different lobes; for example, parietal lobe C713 and occipital lobe C714 (different site codes)
        rule = new MphRule(MphConstants.MP_2018_MALIGNANT_CNS_AND_PERIPHERAL_NERVES_GROUP_ID, "M7") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                if (i1.getHistology().equals(i2.getHistology()) && GroupUtility.isSiteContained(MphConstants.CNS_2018_BRAIN_SITES, i1.getPrimarySite()) && GroupUtility.isSiteContained(
                        MphConstants.CNS_2018_BRAIN_SITES, i2.getPrimarySite()))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                return result;
            }
        };
        rule.setQuestion("Are the tumors separate, non-contiguous, in the brain (multicentric/multifocal), and have the same histology?");
        rule.setReason("Tumors which are separate, non-contiguous, in the brain (multicentric/multifocal), and have the same histology are a single primary.");
        rule.getExamples().add(
                "The patient had a resection of an anaplastic astrocytoma 9401 in the right parietal lobe. Three months later the patient is diagnosed with a de novo anaplastic astrocytoma in the left parietal lobe. This is one primary because neither laterality nor timing are used to determine multiple primary status.");
        rule.getNotes().add(
                "Multiple sites/subsites and/or different lateralities imply either metastatic or multifocal/multicentric disease. Glioblastoma multiforme commonly exhibits multiple tumors which are described as multifocal/multicentric.");
        rule.getNotes().add("Metastases are never used to determine multiple primaries. Seeding metastasis is often noted for the following tumors:");
        rule.getNotes().add("  - Glioblastoma multiforme");
        rule.getNotes().add("  - pNET-medulloblastoma");
        rule.getNotes().add("  - Oligodendroglioma");
        rule.getNotes().add("Hereditary syndromes frequently exhibit multiple tumors including the following:");
        rule.getNotes().add("  - Neurofibromatosis type 1 (NF1)");
        rule.getNotes().add("     Malignant peripheral nerve sheath tumors (MPNST)");
        rule.getNotes().add("  - Neurofibromatosis type 2 (NF2)");
        rule.getNotes().add("     Anaplastic ependymomas");
        rule.getNotes().add("     Meningiomas");
        rule.getNotes().add("Most malignant neoplasms are single tumors with the exception of those listed in this rule.");
        rule.getNotes().add("This is a change from/clarification to previous rules.");
        _rules.add(rule);

        // Rule M8 Abstract multiple primaries when multiple tumors are present in any of the following sites or subsites:
        // - Any lobe of the brain C710-C719 AND any other part of CNS
        // - Cauda equina C721 AND any other part of CNS
        // - Cerebral meninges C700 AND spinal meninges C701
        // - Cerebral meninges C700 AND any other part of CNS
        // - Any one of the cranial nerves C722-C725 AND any other part of the CNS
        // - Any two or more of the cranial nerves
        //    C722 Olfactory, C723 Optic, C724 Acoustic, C725 Cranial nerves NOS
        // - Meninges of cranial or peripheral nerves C709 AND any other part of the CNS
        // - Spinal cord C720 AND any other part of CNS
        // - Spinal meninges C701 AND any other part of CNS
        rule = new MphRule(MphConstants.MP_2018_MALIGNANT_CNS_AND_PERIPHERAL_NERVES_GROUP_ID, "M8") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                String s1 = i1.getPrimarySite();
                String s2 = i2.getPrimarySite();

                // - Any lobe of the brain C710-C719 AND any other part of CNS
                if ((GroupUtility.isSiteContained(MphConstants.CNS_2018_BRAIN_SITES, s1) && !GroupUtility.isSiteContained(MphConstants.CNS_2018_BRAIN_SITES, s2))
                        || (GroupUtility.isSiteContained(MphConstants.CNS_2018_BRAIN_SITES, s2) && !GroupUtility.isSiteContained(MphConstants.CNS_2018_BRAIN_SITES, s1)))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    // - Cauda equina C721 AND any other part of CNS
                else if ((GroupUtility.isSiteContained(MphConstants.CNS_2018_CAUDA_EQUINA, s1) && !GroupUtility.isSiteContained(MphConstants.CNS_2018_CAUDA_EQUINA, s2))
                        || (GroupUtility.isSiteContained(MphConstants.CNS_2018_CAUDA_EQUINA, s2) && !GroupUtility.isSiteContained(MphConstants.CNS_2018_CAUDA_EQUINA, s1)))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    // - Cerebral meninges C700 AND spinal meninges C701, Cerebral meninges C700 AND any other part of CNS
                else if ((GroupUtility.isSiteContained(MphConstants.CNS_2018_CEREBRAL_MENINGES_SITES, s1) && !GroupUtility.isSiteContained(MphConstants.CNS_2018_CEREBRAL_MENINGES_SITES, s2))
                        || (GroupUtility.isSiteContained(MphConstants.CNS_2018_CEREBRAL_MENINGES_SITES, s2) && !GroupUtility.isSiteContained(MphConstants.CNS_2018_CEREBRAL_MENINGES_SITES, s1)))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);

                    // - Any one of the cranial nerves C722-C725 AND any other part of the CNS
                else if ((GroupUtility.isSiteContained(MphConstants.CNS_2018_CRANIAL_NERVES_SITES_NON_CAUDA_EQUINA, s1) && !GroupUtility.isSiteContained(
                        MphConstants.CNS_2018_CRANIAL_NERVES_SITES_NON_CAUDA_EQUINA, s2))
                        || (GroupUtility.isSiteContained(MphConstants.CNS_2018_CRANIAL_NERVES_SITES_NON_CAUDA_EQUINA, s2) && !GroupUtility.isSiteContained(
                        MphConstants.CNS_2018_CRANIAL_NERVES_SITES_NON_CAUDA_EQUINA, s1)))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    // - Any two or more of the cranial nerves
                    //    C722 Olfactory, C723 Optic, C724 Acoustic, C725 Cranial nerves NOS
                else if (GroupUtility.isSiteContained(MphConstants.CNS_2018_CRANIAL_NERVES_SITES_NON_CAUDA_EQUINA, s1) && GroupUtility.isSiteContained(
                        MphConstants.CNS_2018_CRANIAL_NERVES_SITES_NON_CAUDA_EQUINA, s2) && !s1.equals(s2))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    // - Meninges of cranial or peripheral nerves C709 AND any other part of the CNS
                else if ((GroupUtility.isSiteContained(MphConstants.CNS_2018_MENINGES_OF_CRANIAL_OR_PERIPH_NERVES_SITES, s1) && !GroupUtility.isSiteContained(
                        MphConstants.CNS_2018_MENINGES_OF_CRANIAL_OR_PERIPH_NERVES_SITES, s2))
                        || (GroupUtility.isSiteContained(MphConstants.CNS_2018_MENINGES_OF_CRANIAL_OR_PERIPH_NERVES_SITES, s2) && !GroupUtility.isSiteContained(
                        MphConstants.CNS_2018_MENINGES_OF_CRANIAL_OR_PERIPH_NERVES_SITES, s1)))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);

                    // - Spinal cord C720 AND any other part of CNS
                else if ((GroupUtility.isSiteContained(MphConstants.CNS_2018_SPINAL_CORD_SITES, s1) && !GroupUtility.isSiteContained(MphConstants.CNS_2018_SPINAL_CORD_SITES, s2))
                        || (GroupUtility.isSiteContained(MphConstants.CNS_2018_SPINAL_CORD_SITES, s2) && !GroupUtility.isSiteContained(MphConstants.CNS_2018_SPINAL_CORD_SITES, s1)))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    // - Spinal meninges C701 AND any other part of CNS
                else if ((GroupUtility.isSiteContained(MphConstants.CNS_2018_SPINAL_MENINGES_SITES, s1) && !GroupUtility.isSiteContained(MphConstants.CNS_2018_SPINAL_MENINGES_SITES, s2))
                        || (GroupUtility.isSiteContained(MphConstants.CNS_2018_SPINAL_MENINGES_SITES, s2) && !GroupUtility.isSiteContained(MphConstants.CNS_2018_SPINAL_MENINGES_SITES, s1)))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Are multiple tumors present in the following sites:\n" +
                "Any lobe of the brain C710-C719 AND any other part of CNS,\n" +
                "Cauda equina C721 AND any other part of CNS,\n" +
                "Cerebral meninges C700 AND spinal meninges C701,\n" +
                "Cerebral meninges C700 AND any other part of CNS,\n" +
                "Any of the cranial nerves C722-C725 AND any other part of the CNS,\n" +
                "Any two or more of the cranial nerves: C722 Olfactory, C723 Optic, C724 Acoustic, C725 Cranial nerves NOS,\n" +
                "Meninges of cranial or peripheral nerves C709 AND any other part of the CNS,\n" +
                "Spinal cord C720 AND any other part of CNS,\n" +
                "Spinal meninges C701 AND any other part of CNS?");
        rule.setReason("Multiple tumors present in the following sites:\n" +
                "Any lobe of the brain C710-C719 AND any other part of CNS,\n" +
                "Cauda equina C721 AND any other part of CNS,\n" +
                "Cerebral meninges C700 AND spinal meninges C701,\n" +
                "Cerebral meninges C700 AND any other part of CNS,\n" +
                "Any of the cranial nerves C722-C725 AND any other part of the CNS,\n" +
                "Any two or more of the cranial nerves: C722 Olfactory, C723 Optic, C724 Acoustic, C725 Cranial nerves NOS,\n" +
                "Meninges of cranial or peripheral nerves C709 AND any other part of the CNS,\n" +
                "Spinal cord C720 AND any other part of CNS,\n" +
                "Spinal meninges C701 AND any other part of CNS,\n" +
                "are multiple primaries.");
        _rules.add(rule);

        // Rule M9 Abstract multiple primaries when separate, non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 3 in the Equivalent Terms and Definitions. Timing is irrelevant.
        rule = new MphRule(MphConstants.MP_2018_MALIGNANT_CNS_AND_PERIPHERAL_NERVES_GROUP_ID, "M9") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                String h1 = i1.getHistology();
                String icd1 = i1.getIcdCode();
                String h2 = i2.getHistology();
                String icd2 = i2.getIcdCode();
                String subtype1 = MphConstants.MALIGNANT_CNS_2018_TABLE3_SUBTYPES.containsKey(h1) ? MphConstants.MALIGNANT_CNS_2018_TABLE3_SUBTYPES.get(
                        h1) : MphConstants.MALIGNANT_CNS_2018_TABLE3_SUBTYPES.get(icd1);
                String subtype2 = MphConstants.MALIGNANT_CNS_2018_TABLE3_SUBTYPES.containsKey(h2) ? MphConstants.MALIGNANT_CNS_2018_TABLE3_SUBTYPES.get(
                        h2) : MphConstants.MALIGNANT_CNS_2018_TABLE3_SUBTYPES.get(icd2);
                if (subtype1 != null && subtype2 != null && !subtype1.contains(subtype2) && !subtype2.contains(subtype1))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Are separate/non-contiguous tumors two or more different subtypes/variants in Column 3, Table 3 in the Equivalent Terms and Definitions?");
        rule.setReason("Separate/non-contiguous tumors that are two or more different subtypes/variants in Column 3, Table 3 in the Equivalent Terms and Definitions are multiple primaries.");
        rule.getNotes().add("The tumors may be subtypes/variants of the same or different NOS histologies.");
        rule.getNotes().add(
                "  - Same NOS: Anaplastic astrocytoma IDH-mutant 9401 and gemistocytic astrocytoma IDH-mutant 9411 are both subtypes of astrocytoma NOS 9400/3 but are distinctly different histologies. Abstract multiple primaries.");
        rule.getNotes().add(
                "  - Different NOS: Papillary ependymoma 9393 is a subtype of ependymoma NOS 9391; gliosarcoma 9442 is a subtype of glioblastoma NOS 9440. They are distinctly different histologies. Abstract multiple primaries.");
        _rules.add(rule);

        // Rule M10 Abstract a single primary when separate, non-contiguous tumors are on the same row in Table 3 in the Equivalent Terms and Definitions. Timing is irrelevant.
        rule = new MphRule(MphConstants.MP_2018_MALIGNANT_CNS_AND_PERIPHERAL_NERVES_GROUP_ID, "M10") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                String h1 = i1.getHistology();
                String icd1 = i1.getIcdCode();
                String h2 = i2.getHistology();
                String icd2 = i2.getIcdCode();
                String row1 = MphConstants.MALIGNANT_CNS_2018_TABLE3_ROWS.containsKey(h1) ? MphConstants.MALIGNANT_CNS_2018_TABLE3_ROWS.get(h1) : MphConstants.MALIGNANT_CNS_2018_TABLE3_ROWS.get(icd1);
                String row2 = MphConstants.MALIGNANT_CNS_2018_TABLE3_ROWS.containsKey(h2) ? MphConstants.MALIGNANT_CNS_2018_TABLE3_ROWS.get(h2) : MphConstants.MALIGNANT_CNS_2018_TABLE3_ROWS.get(icd2);
                if (row1 == null || row2 == null) {
                    result.setFinalResult(MpResult.QUESTIONABLE);
                    String histologyNotInTable;
                    boolean bothNotInTable = false;
                    if (row1 == null && row2 == null) {
                        bothNotInTable = true;
                        histologyNotInTable = "Both " + icd1 + " and " + icd2;
                    }
                    else
                        histologyNotInTable = row1 == null ? icd1 : icd2;

                    result.setMessageNotInTable(this.getStep(), this.getGroupId(), histologyNotInTable, bothNotInTable);
                }
                else if (row1.equals(row2))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);

                return result;
            }
        };
        rule.setQuestion("Are separate/non-contiguous tumors on the same rows in Table 3 in the Equivalent Terms and Definitions?");
        rule.setReason("Separate/non-contiguous tumors on the same row in Table 3 in the Equivalent Terms and Definitions are a single primary.");
        rule.getNotes().add("The same row means the tumors are:");
        rule.getNotes().add("  - The same histology (same four-digit ICD-O code) OR");
        rule.getNotes().add("  - One is the preferred term (column 1) and the other is a synonym for the preferred term (column 2) OR");
        rule.getNotes().add("  - A NOS (column 1/column 2) and the other is a subtype/variant of that NOS (column 3)");
        _rules.add(rule);

        // Rule M11 Abstract multiple primaries when separate, non-contiguous tumors are on different rows in Table 3 in the Equivalent Terms and Definitions. Timing is irrelevant.
        rule = new MphRule(MphConstants.MP_2018_MALIGNANT_CNS_AND_PERIPHERAL_NERVES_GROUP_ID, "M11") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                String h1 = i1.getHistology();
                String icd1 = i1.getIcdCode();
                String h2 = i2.getHistology();
                String icd2 = i2.getIcdCode();
                String row1 = MphConstants.MALIGNANT_CNS_2018_TABLE3_ROWS.containsKey(h1) ? MphConstants.MALIGNANT_CNS_2018_TABLE3_ROWS.get(h1) : MphConstants.MALIGNANT_CNS_2018_TABLE3_ROWS.get(icd1);
                String row2 = MphConstants.MALIGNANT_CNS_2018_TABLE3_ROWS.containsKey(h2) ? MphConstants.MALIGNANT_CNS_2018_TABLE3_ROWS.get(h2) : MphConstants.MALIGNANT_CNS_2018_TABLE3_ROWS.get(icd2);
                if (row1 == null || row2 == null) {
                    result.setFinalResult(MpResult.QUESTIONABLE);
                    String histologyNotInTable;
                    boolean bothNotInTable = false;
                    if (row1 == null && row2 == null) {
                        bothNotInTable = true;
                        histologyNotInTable = "Both " + icd1 + " and " + icd2;
                    }
                    else
                        histologyNotInTable = row1 == null ? icd1 : icd2;

                    result.setMessageNotInTable(this.getStep(), this.getGroupId(), histologyNotInTable, bothNotInTable);
                }
                else if (!row1.equals(row2))
                    result.setFinalResult(MpResult.MULTIPLE_PRIMARIES);

                return result;
            }
        };
        rule.setQuestion("Are separate/non-contiguous tumors on different rows in Table 3 in the Equivalent Terms and Definitions?");
        rule.setReason("Separate/non-contiguous tumors on different rows in Table 3 in the Equivalent Terms and Definitions are multiple primaries.");
        rule.getNotes().add("Each row in the table is a distinctly different histology.");
        _rules.add(rule);

        // Rule M12 Abstract a single primary when multiple tumors do not meet any of the above criteria.
        rule = new MpRuleNoCriteriaSatisfied(MphConstants.MP_2018_MALIGNANT_CNS_AND_PERIPHERAL_NERVES_GROUP_ID, "M12");
        rule.getNotes().add("Use caution when applying this default rule.  Please confirm that you have not overlooked an applicable rule.");
        _rules.add(rule);
    }
}

