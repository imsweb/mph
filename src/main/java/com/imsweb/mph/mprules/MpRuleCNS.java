/*
 * Copyright (C) 2019 Information Management Services, Inc.
 */
package com.imsweb.mph.mprules;

import com.imsweb.mph.HematoDataProvider;
import com.imsweb.mph.MphConstants;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphUtils;
import com.imsweb.mph.internal.TempRuleResult;
import com.imsweb.mph.mpgroups.GroupUtility;

public class MpRuleCNS extends MphRule {

    private boolean _malignant;

    public MpRuleCNS(String groupName, String step, boolean malignant) {
        super(groupName, step);
        _malignant = malignant;
        setQuestion("Are multiple tumors present in the following sites:\n" +
                " - Any lobe(s) of the brain C710-C719 AND any other part of CNS\n" +
                " - Cauda equina C721 AND any other part of CNS\n" +
                " - Cerebral meninges C700 AND spinal meninges C701\n" +
                " - Cerebral meninges C700 AND any other part of CNS\n" +
                " - Any cranial nerve(s) C722-C725 AND any other part of the CNS\n" +
                (malignant ? " - Any two or more of the cranial nerves: C722 Olfactory, C723 Optic, C724 Acoustic, C725 Cranial nerves NOS,\n" : "") +
                " - Meninges of cranial nerves C709 AND any other part of the CNS\n" +
                " - Spinal cord C720 AND any other part of CNS\n" +
                " - Spinal meninges C701 AND any other part of CNS?");
        setReason("Multiple tumors present in the following sites:\n" +
                " - Any lobe(s) of the brain C710-C719 AND any other part of CNS\n" +
                " - Cauda equina C721 AND any other part of CNS\n" +
                " - Cerebral meninges C700 AND spinal meninges C701\n" +
                " - Cerebral meninges C700 AND any other part of CNS\n" +
                " - Any cranial nerve(s) C722-C725 AND any other part of the CNS\n" +
                (malignant ? " - Any two or more of the cranial nerves: C722 Olfactory, C723 Optic, C724 Acoustic, C725 Cranial nerves NOS,\n" : "") +
                " - Meninges of cranial nerves C709 AND any other part of the CNS\n" +
                " - Spinal cord C720 AND any other part of CNS\n" +
                " - Spinal meninges C701 AND any other part of CNS\n" +
                "are multiple primaries.");
    }

    @Override
    public TempRuleResult apply(MphInput i1, MphInput i2, HematoDataProvider provider) {
        TempRuleResult result = new TempRuleResult();
        String s1 = i1.getPrimarySite();
        String s2 = i2.getPrimarySite();

        //Any lobe(s) of the brain C710-C719 AND any other part of CNS
        boolean lobesAndOtherPart = (GroupUtility.isSiteContained(MphConstants.CNS_2018_BRAIN_SITES, s1) && !GroupUtility.isSiteContained(MphConstants.CNS_2018_BRAIN_SITES, s2))
                || (GroupUtility.isSiteContained(MphConstants.CNS_2018_BRAIN_SITES, s2) && !GroupUtility.isSiteContained(MphConstants.CNS_2018_BRAIN_SITES, s1));
        // - Cauda equina C721 AND any other part of CNS
        boolean caudaEquinaAndOtherPart = (GroupUtility.isSiteContained(MphConstants.CNS_2018_CAUDA_EQUINA, s1) && !GroupUtility.isSiteContained(MphConstants.CNS_2018_CAUDA_EQUINA, s2))
                || (GroupUtility.isSiteContained(MphConstants.CNS_2018_CAUDA_EQUINA, s2) && !GroupUtility.isSiteContained(MphConstants.CNS_2018_CAUDA_EQUINA, s1));
        // - Cerebral meninges C700 AND spinal meninges C701, Cerebral meninges C700 AND any other part of CNS
        boolean celebralMeningesAndOtherPart = (GroupUtility.isSiteContained(MphConstants.CNS_2018_CEREBRAL_MENINGES_SITES, s1) && !GroupUtility.isSiteContained(MphConstants.CNS_2018_CEREBRAL_MENINGES_SITES, s2))
                || (GroupUtility.isSiteContained(MphConstants.CNS_2018_CEREBRAL_MENINGES_SITES, s2) && !GroupUtility.isSiteContained(MphConstants.CNS_2018_CEREBRAL_MENINGES_SITES, s1));
        // - Any cranial nerve(s) C722-C725 AND any other part of the CNS
        boolean cranialAndOtherPart = (GroupUtility.isSiteContained(MphConstants.CNS_2018_CRANIAL_NERVES_SITES_NON_CAUDA_EQUINA, s1) && !GroupUtility.isSiteContained(
                MphConstants.CNS_2018_CRANIAL_NERVES_SITES_NON_CAUDA_EQUINA, s2))
                || (GroupUtility.isSiteContained(MphConstants.CNS_2018_CRANIAL_NERVES_SITES_NON_CAUDA_EQUINA, s2) && !GroupUtility.isSiteContained(
                MphConstants.CNS_2018_CRANIAL_NERVES_SITES_NON_CAUDA_EQUINA, s1));
        // - Any two or more of the cranial nerves: C722 Olfactory, C723 Optic, C724 Acoustic, C725 Cranial nerves NOS
        boolean cranialNerves = GroupUtility.isSiteContained(MphConstants.CNS_2018_CRANIAL_NERVES_SITES_NON_CAUDA_EQUINA, s1) && GroupUtility.isSiteContained(
                MphConstants.CNS_2018_CRANIAL_NERVES_SITES_NON_CAUDA_EQUINA, s2) && !s1.equals(s2);
        // - Meninges of cranial nerves C709 AND any other part of the CNS
        boolean meningesOfCranialAndOtherPart = (GroupUtility.isSiteContained(MphConstants.CNS_2018_MENINGES_OF_CRANIAL_OR_PERIPH_NERVES_SITES, s1) && !GroupUtility.isSiteContained(
                MphConstants.CNS_2018_MENINGES_OF_CRANIAL_OR_PERIPH_NERVES_SITES, s2))
                || (GroupUtility.isSiteContained(MphConstants.CNS_2018_MENINGES_OF_CRANIAL_OR_PERIPH_NERVES_SITES, s2) && !GroupUtility.isSiteContained(
                MphConstants.CNS_2018_MENINGES_OF_CRANIAL_OR_PERIPH_NERVES_SITES, s1));
        // -Spinal cord C720 AND any other part of CNS
        boolean spinalCordAndOtherPart = (GroupUtility.isSiteContained(MphConstants.CNS_2018_SPINAL_CORD_SITES, s1) && !GroupUtility.isSiteContained(MphConstants.CNS_2018_SPINAL_CORD_SITES, s2))
                || (GroupUtility.isSiteContained(MphConstants.CNS_2018_SPINAL_CORD_SITES, s2) && !GroupUtility.isSiteContained(MphConstants.CNS_2018_SPINAL_CORD_SITES, s1));
        // - Spinal meninges C701 AND any other part of CNS
        boolean spinalMeningesAndOtherPart = (GroupUtility.isSiteContained(MphConstants.CNS_2018_SPINAL_MENINGES_SITES, s1) && !GroupUtility.isSiteContained(MphConstants.CNS_2018_SPINAL_MENINGES_SITES, s2))
                || (GroupUtility.isSiteContained(MphConstants.CNS_2018_SPINAL_MENINGES_SITES, s2) && !GroupUtility.isSiteContained(MphConstants.CNS_2018_SPINAL_MENINGES_SITES, s1));

        if (lobesAndOtherPart || caudaEquinaAndOtherPart || celebralMeningesAndOtherPart || cranialAndOtherPart || meningesOfCranialAndOtherPart || spinalCordAndOtherPart || spinalMeningesAndOtherPart || (cranialNerves && _malignant))
            result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);

        return result;
    }
}
