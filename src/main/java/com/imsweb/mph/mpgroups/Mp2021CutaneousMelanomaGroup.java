/*
 * Copyright (C) 2013 Information Management Services, Inc.
 */
package com.imsweb.mph.mpgroups;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.imsweb.mph.MphComputeOptions;
import com.imsweb.mph.MphConstants;
import com.imsweb.mph.MphGroup;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphUtils;
import com.imsweb.mph.MphUtils.MpResult;
import com.imsweb.mph.internal.TempRuleResult;
import com.imsweb.mph.mprules.MpRuleNoCriteriaSatisfied;

public class Mp2021CutaneousMelanomaGroup extends MphGroup {

    public Mp2021CutaneousMelanomaGroup() {
        super(MphConstants.MP_2021_CUTANEOUS_MELANOMA_GROUP_ID, MphConstants.MP_2021_CUTANEOUS_MELANOMA_GROUP_NAME, "C440-C449", null, "8720-8780",
                null, "2-3,6", "2021-9999");

        //M3- Melanomas in sites with ICD-O-3 topography codes that are different at the second (C?xx), third (Cx?x) or fourth (C44?) character are multiple primaries.
        MphRule rule = new MphRule(MphConstants.MP_2021_CUTANEOUS_MELANOMA_GROUP_ID, "M3") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (!i1.getPrimarySite().equals(i2.getPrimarySite()))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Are there melanomas in sites withICD-O-3 topography codes that are different at the second (C?xx) , third (Cx?x) and/or fourth (C18?) character?");
        rule.setReason("Melanomas in sites with ICD-O-3 topography codes that are different at the second (C?xx), third (Cx?x) or fourth (C44?) character are multiple primaries.");
        _rules.add(rule);

        //M4- Abstract multiple primaries when there are separate, non-contiguous melanomas with different lateralities.
        rule = new MphRule(MphConstants.MP_2021_CUTANEOUS_MELANOMA_GROUP_ID, "M4") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                List<String> lateralityNotRequiredSites = Arrays.asList("C440", "C448", "C449");
                if (lateralityNotRequiredSites.contains(i1.getPrimarySite()) || MphConstants.PAIRED_NO_INFORMATION.equals(i1.getLaterality()) || MphConstants.PAIRED_NO_INFORMATION.equals(
                        i2.getLaterality()) || StringUtils.isEmpty(i1.getLaterality()) || StringUtils.isEmpty(i2.getLaterality()))
                    return result;
                // mid-line (5) is considered (look the example)
                if (!Arrays.asList(MphConstants.RIGHT, MphConstants.LEFT, MphConstants.MID_LINE).containsAll(Arrays.asList(i1.getLaterality(), i2.getLaterality()))) {
                    result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    result.setMessageUnknownLaterality(this.getStep(), this.getGroupId());
                }
                else if (!i1.getLaterality().equals(i2.getLaterality()))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);

                return result;
            }
        };
        rule.setQuestion("Do the melanomas have different lateralities?");
        rule.setReason("Abstract multiple primaries when there are separate, non-contiguous melanomas with different lateralities.");
        rule.getExamples().add("A midline melanoma is a different laterality than right or left.");
        rule.getExamples().add("If the laterality of one or both melanomas is unknown, then continue through the rules");
        rule.getExamples().add("If one or more of the sites is not in the required laterality table, then continue through the rules. See Table 1.");
        rule.getExamples().add("Melanoma of the right side of the chest and a melanoma at midline of the chest are different laterality, multiple primaries.");
        rule.getExamples().add("A melanoma of the right side of the chest and a melanoma of the left side of the chest are multiple primaries.");
        _rules.add(rule);

        //M5- Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in
        //Column 3, Table 2 in the Equivalent Terms and Definitions. Timing is irrelevant.
        rule = new MphRule(MphConstants.MP_2021_CUTANEOUS_MELANOMA_GROUP_ID, "M5") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                boolean icd1IsSubtype = MphConstants.CUTANEOUS_MELANOMA_2021_TABLE2_SUBTYPES.contains(i1.getHistology()) || MphConstants.CUTANEOUS_MELANOMA_2021_TABLE2_SUBTYPES.contains(
                        i1.getIcdCode());
                boolean icd2IsSubtype = MphConstants.CUTANEOUS_MELANOMA_2021_TABLE2_SUBTYPES.contains(i2.getHistology()) || MphConstants.CUTANEOUS_MELANOMA_2021_TABLE2_SUBTYPES.contains(
                        i2.getIcdCode());
                if (icd1IsSubtype && icd2IsSubtype)
                    result.setFinalResult(MpResult.MULTIPLE_PRIMARIES);

                return result;
            }

        };
        rule.setQuestion("Are separate/non-contiguous tumors different subtypes/variants in Column 3, Table 2?");
        rule.setReason("Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 2 in the Equivalent Terms and Definitions.");
        rule.getExamples().add("Epithelioid cell melanoma 8771/3 and nodular melanoma 8721/3 are both subtypes/variants of melanoma, NOS 8720/3");
        _rules.add(rule);

        //M6- Abstract a single primary when synchronous, separate/non-contiguous tumors are on the same row in Table 2 in the Equivalent Terms and Definitions. Tumors must have same site and same laterality.
        rule = new MphRule(MphConstants.MP_2021_CUTANEOUS_MELANOMA_GROUP_ID, "M6") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                boolean icd1InTable = MphConstants.CUTANEOUS_MELANOMA_2021_TABLE2_ROWS.contains(i1.getHistology()) || MphConstants.CUTANEOUS_MELANOMA_2021_TABLE2_ROWS.contains(i1.getIcdCode());
                boolean icd2InTable = MphConstants.CUTANEOUS_MELANOMA_2021_TABLE2_ROWS.contains(i2.getHistology()) || MphConstants.CUTANEOUS_MELANOMA_2021_TABLE2_ROWS.contains(i2.getIcdCode());
                if (icd1InTable && icd2InTable) {
                    List<String> lateralityNotRequiredSites = Arrays.asList("C440", "C448", "C449");
                    if (!lateralityNotRequiredSites.contains(i1.getPrimarySite()) && !Arrays.asList(MphConstants.RIGHT, MphConstants.LEFT, MphConstants.MID_LINE).containsAll(Arrays.asList(i1.getLaterality(), i2.getLaterality()))) {
                        result.setFinalResult(MpResult.QUESTIONABLE);
                        result.setMessageUnknownLaterality(this.getStep(), this.getGroupId());
                        return result;
                    }
                    else if (lateralityNotRequiredSites.contains(i1.getPrimarySite()) || i1.getLaterality().equals(i2.getLaterality())) {
                        int sixtyDaysApart = GroupUtility.verifyDaysApart(i1, i2, 60);
                        if (MphConstants.DATE_VERIFY_UNKNOWN == sixtyDaysApart) {
                            result.setFinalResult(MpResult.QUESTIONABLE);
                            result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupId());
                        }
                        else if (MphConstants.DATE_VERIFY_WITHIN == sixtyDaysApart)
                            result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                    }
                }
                return result;
            }

        };
        rule.setQuestion("Are tumors synchronous, on the same row in Table 2 and have same laterality?");
        rule.setReason(
                "Abstract a single primary when synchronous, separate/non-contiguous tumors are on the same row in Table 2 in the Equivalent Terms and Definitions. And they have same laterality.");
        _rules.add(rule);

        //M7- Melanomas diagnosed more than 60 days apart are multiple primaries. 
        rule = new MphRule(MphConstants.MP_2021_CUTANEOUS_MELANOMA_GROUP_ID, "M7") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                int sixtyDaysApart = GroupUtility.verifyDaysApart(i1, i2, 60);
                if (MphConstants.DATE_VERIFY_UNKNOWN == sixtyDaysApart) {
                    result.setFinalResult(MpResult.QUESTIONABLE);
                    result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupId());
                }
                else if (MphConstants.DATE_VERIFY_APART == sixtyDaysApart)
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Are there melanomas diagnosed more than 60 days apart?");
        rule.setReason("Melanomas diagnosed more than 60 days apart are multiple primaries.");
        _rules.add(rule);

        //M8- Melanomas that do not meet any of the above criteria are abstracted as a single primary.
        rule = new MpRuleNoCriteriaSatisfied(MphConstants.MP_2021_CUTANEOUS_MELANOMA_GROUP_ID, "M8");
        rule.setReason("Melanomas that do not meet any of the above criteria are abstracted as a single primary.");
        rule.getNotes().add("Use the data item \"Multiplicity Counter\" to record the number of melanomas abstracted as a single primary.");
        rule.getNotes().add("When an invasive melanoma follows an in situ melanoma within 60 days, abstract as a single primary.");
        rule.getNotes().add("All cases covered by this rule are the same site and histology.");
        _rules.add(rule);
    }
}
