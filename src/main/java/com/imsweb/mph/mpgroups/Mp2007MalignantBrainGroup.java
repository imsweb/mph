/*
 * Copyright (C) 2013 Information Management Services, Inc.
 */
package com.imsweb.mph.mpgroups;

import java.util.Collections;
import java.util.List;

import com.imsweb.mph.MphConstants;
import com.imsweb.mph.MphGroup;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphUtils;
import com.imsweb.mph.internal.TempRuleResult;
import com.imsweb.mph.mprules.MpRuleHistology;
import com.imsweb.mph.mprules.MpRuleNoCriteriaSatisfied;
import com.imsweb.mph.mprules.MpRulePrimarySite;

public class Mp2007MalignantBrainGroup extends MphGroup {

    public Mp2007MalignantBrainGroup() {
        super(MphConstants.MP_2007_MALIGNANT_BRAIN_GROUP_ID, MphConstants.MP_2007_MALIGNANT_BRAIN_GROUP_NAME, "C700-C701,C709-C725,C728-C729,C751-C753", null, null, "9590-9993,9140", "3",
                "2007-2017");

        // M4 - An invasive brain tumor (/3) and either a benign brain tumor (/0) or an uncertain/borderline brain tumor (/1) are always multiple primaries.
        MphRule rule = new MphRule(MphConstants.MP_2007_MALIGNANT_BRAIN_GROUP_NAME, "M4") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                //This will never happen, since the two conditions belong to different cancer groups.
                return new TempRuleResult();
            }
        };
        rule.setQuestion("Is there an invasive tumor (/3) and either a benign brain tumor (/0) or an uncertain/borderline brain tumor (/1)?");
        rule.setReason("An invasive brain tumor (/3) and either a benign brain tumor (/0) or an uncertain/borderline brain tumor (/1) are always multiple primaries.");
        _rules.add(rule);

        // M5- Tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx) and/or third (Cx?x) character are multiple primaries.
        rule = new MpRulePrimarySite(MphConstants.MP_2007_MALIGNANT_BRAIN_GROUP_ID, "M5");
        _rules.add(rule);

        // M6 - A glioblastoma or glioblastoma multiforme (9440) following a glial tumor is a single primary.
        rule = new MphRule(MphConstants.MP_2007_MALIGNANT_BRAIN_GROUP_NAME, "M6") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                String h1 = i1.getHistology();
                String h2 = i2.getHistology();
                if (!h1.equals(h2) && GroupUtility.differentCategory(h1, h2, MphConstants.GLIAL_TUMOR, Collections.singletonList(MphConstants.GLIOBLASTOMA_NOS_AND_MULTIFORME))) {
                    int laterDiagnosedTumor = GroupUtility.compareDxDate(i1, i2);
                    if (MphConstants.COMPARE_DX_UNKNOWN == laterDiagnosedTumor) { //If impossible to decide which tumor is diagnosed later
                        result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupName());
                    }
                    else if (MphConstants.COMPARE_DX_FIRST_LATEST == laterDiagnosedTumor && MphConstants.GLIOBLASTOMA_NOS_AND_MULTIFORME.equals(h1))
                        result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                    else if (MphConstants.COMPARE_DX_SECOND_LATEST == laterDiagnosedTumor && MphConstants.GLIOBLASTOMA_NOS_AND_MULTIFORME.equals(h2))
                        result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                }
                return result;
            }
        };
        rule.setQuestion("Is there a glioblastoma or glioblastoma multiforme (9440) following a glial tumor (See Chart 1) ?");
        rule.setReason("A glioblastoma or glioblastoma multiforme (9440) following a glial tumor is a single primary.");
        _rules.add(rule);

        // M7 - Tumors with ICD-O-3 histology codes on the same branch in Chart 1 or Chart 2 are a single primary.
        rule = new MphRule(MphConstants.MP_2007_MALIGNANT_BRAIN_GROUP_NAME, "M7") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                List<String> branch1 = MphConstants.MALIGNANT_BRAIN_2007_CHART1.get(i1.getHistology());
                List<String> branch2 = MphConstants.MALIGNANT_BRAIN_2007_CHART1.get(i2.getHistology());
                if (branch1 != null && branch2 != null && (!Collections.disjoint(branch1, branch2) || branch1.contains("Neuroepithelial") || branch2.contains("Neuroepithelial")))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                else {
                    branch1 = MphConstants.MALIGNANT_BRAIN_2007_CHART2.get(i1.getHistology());
                    branch2 = MphConstants.MALIGNANT_BRAIN_2007_CHART2.get(i2.getHistology());
                    if (branch1 != null && branch2 != null && (!Collections.disjoint(branch1, branch2)))
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
        rule = new MphRule(MphConstants.MP_2007_MALIGNANT_BRAIN_GROUP_NAME, "M8") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                List<String> branch1 = MphConstants.MALIGNANT_BRAIN_2007_CHART1.get(i1.getHistology());
                List<String> branch2 = MphConstants.MALIGNANT_BRAIN_2007_CHART1.get(i2.getHistology());
                if (branch1 != null && branch2 != null && Collections.disjoint(branch1, branch2) && !branch1.contains("Neuroepithelial") && !branch2.contains("Neuroepithelial"))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                else {
                    branch1 = MphConstants.MALIGNANT_BRAIN_2007_CHART2.get(i1.getHistology());
                    branch2 = MphConstants.MALIGNANT_BRAIN_2007_CHART2.get(i2.getHistology());
                    if (branch1 != null && branch2 != null && Collections.disjoint(branch1, branch2))
                        result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                }
                return result;
            }
        };
        rule.setQuestion("Do the tumors have ICD-O-3 histology codes on different branches in Chart 1 or Chart 2?");
        rule.setReason("Tumors with ICD-O-3 histology codes on different branches in Chart 1 or Chart 2 are multiple primaries.");
        _rules.add(rule);

        // M9- Tumors with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.        
        rule = new MpRuleHistology(MphConstants.MP_2007_MALIGNANT_BRAIN_GROUP_ID, "M9");
        _rules.add(rule);

        // M10- Tumors that do not meet any of the criteria are abstracted as a single primary.
        rule = new MpRuleNoCriteriaSatisfied(MphConstants.MP_2007_MALIGNANT_BRAIN_GROUP_ID, "M10");
        rule.getNotes().add("Multicentric brain tumors which involve different lobes of the brain that do not meet any of the above criteria are the same disease process.");
        rule.getNotes().add("Neither timing nor laterality is used to determine multiple primaries for malignant intracranial and CNS tumors.");
        rule.getExamples().add(
                "The patient is treated for an anaplastic astrocytoma (9401) in the right parietal lobe. Three months later the patient is diagnosed with a separate anaplastic astrocytoma in the left parietal lobe. This is one primary because laterality is not used to determine multiple primary status.");
        _rules.add(rule);
    }
}
