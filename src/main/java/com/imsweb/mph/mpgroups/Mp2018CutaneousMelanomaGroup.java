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
import com.imsweb.mph.internal.TempRuleResult;

public class Mp2018CutaneousMelanomaGroup extends MphGroup {

    /*
    Cutaneous Melanoma Multiple Primary Rules – Text
    C440-C449 with Histology 8720-8780 (Excludes melanoma of any other site)
    Rules Apply to Cases Diagnosed 1/1/2007 to 12/31/2018

    Multiple melanomas may be a single primary or multiple primaries
    Note 1: Melanoma not described as metastases
    Note 2: Includes combinations of in situ and invasive

    Rule M3	Melanomas in sites with ICD-O-3 topography codes that are different at the second (Cxxx), third (Cxxx) or fourth (C44x) character are multiple primaries. **

    Rule M4	Melanomas with different laterality are multiple primaries. **
        Note:  A midline melanoma is a different laterality than right or left.
        Example 1:  Melanoma of the right side of the chest and melanoma at midline of the chest are different laterality, multiple primaries
        Example 2:  A melanoma of the right side of the chest and a melanoma of the left side of the chest are multiple primaries.

    Rule M5	Melanomas with ICD-O-3 histology codes that are different at the first (Xxxx), second (xXxx) or third number (xxXx) are multiple primaries. **

    Rule M6	An invasive melanoma that occurs more than 60 days after an in situ melanoma is a multiple primary. **
        Note 1: 	The purpose of this rule is to ensure that the case is counted as an incident (invasive) case when incidence data are analyzed.
        Note 2: 	Abstract as multiple primaries even if the medical record/physician states it is recurrence or progression of disease.

    Rule M7	Melanomas diagnosed more than 60 days apart are multiple primaries. **

    Rule M8	Melanomas that do not meet any of the above criteria are abstracted as a single primary. *
        Note 1: 	Use the data item “Multiplicity Counter” to record the number of melanomas abstracted as a single primary.
        Note 2:	 When an invasive melanoma follows an in situ melanoma within 60 days, abstract as a single primary.
        Note 3: 	All cases covered by this rule are the same site and histology.
        Example 1: Solitary melanoma on the left back and another solitary melanoma on the left chest.
        Example 2: Solitary melanoma on the right thigh and another solitary melanoma on the right ankle.
        Note: 	The above examples are not exhaustive.


    */

    // TODO
    // Cutaneous Melanoma Multiple Primary Rules – Text
    // C440-C449 with Histology 8720-8780 (Excludes melanoma of any other site)
    // Rules Apply to Cases Diagnosed 1/1/2007 to 12/31/2018
    public Mp2018CutaneousMelanomaGroup() {
        super(MphConstants.MP_2007_CUTANEOUS_MELANOMA_GROUP_ID, MphConstants.MP_2007_CUTANEOUS_MELANOMA_GROUP_NAME, "C440-C449", null,
                "8720-8780", null, "2-3,6", "2007-2018");

        // Rule M3	Melanomas in sites with ICD-O-3 topography codes that are different at the second (Cxxx), third (Cxxx) or fourth (C44x) character are multiple primaries. **
        // TODO
        MphRule rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2007_CUTANEOUS_MELANOMA_GROUP_ID, "M3");
        rule.setQuestion("");
        rule.setReason("");
        _rules.add(rule);

        // Rule M4	Melanomas with different laterality are multiple primaries. **
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2007_CUTANEOUS_MELANOMA_GROUP_ID, "M4");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("A midline melanoma is a different laterality than right or left.");
        rule.getExamples().add("Melanoma of the right side of the chest and melanoma at midline of the chest are different laterality, multiple primaries");
        rule.getExamples().add("A melanoma of the right side of the chest and a melanoma of the left side of the chest are multiple primaries.");
        _rules.add(rule);

        // Rule M5	Melanomas with ICD-O-3 histology codes that are different at the first (Xxxx), second (xXxx) or third number (xxXx) are multiple primaries. **
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2007_CUTANEOUS_MELANOMA_GROUP_ID, "M5");
        rule.setQuestion("");
        rule.setReason("");
        _rules.add(rule);

        // Rule M6	An invasive melanoma that occurs more than 60 days after an in situ melanoma is a multiple primary. **
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2007_CUTANEOUS_MELANOMA_GROUP_ID, "M6");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The purpose of this rule is to ensure that the case is counted as an incident (invasive) case when incidence data are analyzed.");
        rule.getNotes().add("Abstract as multiple primaries even if the medical record/physician states it is recurrence or progression of disease.");
        _rules.add(rule);

        // Rule M7	Melanomas diagnosed more than 60 days apart are multiple primaries. **
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2007_CUTANEOUS_MELANOMA_GROUP_ID, "M7");
        rule.setQuestion("");
        rule.setReason("");
        _rules.add(rule);

        // Rule M8	Melanomas that do not meet any of the above criteria are abstracted as a single primary. *
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2007_CUTANEOUS_MELANOMA_GROUP_ID, "M8");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("Use the data item “Multiplicity Counter” to record the number of melanomas abstracted as a single primary.");
        rule.getNotes().add("When an invasive melanoma follows an in situ melanoma within 60 days, abstract as a single primary.");
        rule.getNotes().add("All cases covered by this rule are the same site and histology.");
        rule.getExamples().add("Solitary melanoma on the left back and another solitary melanoma on the left chest.");
        rule.getExamples().add("Solitary melanoma on the right thigh and another solitary melanoma on the right ankle.");
        rule.getNotes().add("The above examples are not exhaustive.");
        _rules.add(rule);



        /*
        //M3- Melanomas in sites with ICD-O-3 topography codes that are different at the second (C?xx), third (Cx?x) or fourth (C44?) character are multiple primaries.
        MphRule rule = new MphRule(MphConstants.MP_2007_MELANOMA_GROUP_ID, "M3") {
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

        //M4- Melanomas with different laterality are multiple primaries.
        rule = new MphRule(MphConstants.MP_2007_MELANOMA_GROUP_ID, "M4") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                // mid-line (5) is considered (look the example)
                if (!Arrays.asList(MphConstants.RIGHT, MphConstants.LEFT, MphConstants.MID_LINE).containsAll(Arrays.asList(i1.getLaterality(), i2.getLaterality()))) {
                    result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". Valid and known laterality should be provided.");
                }
                else if (!i1.getLaterality().equals(i2.getLaterality()))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);

                return result;
            }
        };
        rule.setQuestion("Do the melanomas have different lateralities?");
        rule.setReason("Melanomas with different laterality are multiple primaries.");
        rule.getExamples().add("Melanoma of the right side of the chest and a melanoma at midline of the chest are different laterality, multiple primaries.");
        rule.getExamples().add("A melanoma of the right side of the chest and a melanoma of the left side of the chest are multiple primaries.");
        _rules.add(rule);

        //M5- Melanomas with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.
        rule = new MphRuleHistologyCode(MphConstants.MP_2007_MELANOMA_GROUP_ID, "M5");
        rule.setQuestion("Do the melanomas haveICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number?");
        rule.setReason("Melanomas with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.");
        _rules.add(rule);

        //M6- An invasive melanoma that occurs more than 60 days after an in situ melanoma is a multiple primary.
        rule = new MphRuleBehavior(MphConstants.MP_2007_MELANOMA_GROUP_ID, "M6");
        rule.setQuestion("Is there an invasive melanoma following an in situ tumor more than 60 days after diagnosis?");
        rule.setReason("An invasive melanoma that occurs more than 60 days after an in situ melanoma is a multiple primary.");
        _rules.add(rule);

        //M7- Melanomas diagnosed more than 60 days apart are multiple primaries.
        rule = new MphRule(MphConstants.MP_2007_MELANOMA_GROUP_ID, "M7") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                int diff = GroupUtility.verifyDaysApart(i1, i2, 60);
                if (-1 == diff) {
                    result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". There is no enough diagnosis date information.");
                }
                else if (1 == diff)
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Are there melanomas diagnosed more than 60 days apart?");
        rule.setReason("Melanomas diagnosed more than 60 days apart are multiple primaries.");
        _rules.add(rule);

        //M8- Melanomas that do not meet any of the above criteria are abstracted as a single primary.
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2007_MELANOMA_GROUP_ID, "M8");
        rule.setReason("Melanomas that do not meet any of the above criteria are abstracted as a single primary.");
        rule.getNotes().add("Use the data item \"Multiplicity Counter\" to record the number of melanomas abstracted as a single primary.");
        rule.getNotes().add("When an invasive melanoma follows an in situ melanoma within 60 days, abstract as a single primary.");
        rule.getNotes().add("All cases covered by this rule are the same site and histology.");
        _rules.add(rule);
        */
    }
}


