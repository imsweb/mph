/*
 * Copyright (C) 2013 Information Management Services, Inc.
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

public class Mp2007LungGroup extends MphGroup {

    public Mp2007LungGroup() {
        super(MphConstants.MP_2007_LUNG_GROUP_ID, MphConstants.MP_2007_LUNG_GROUP_NAME, "C340-C349", null, null, "9590-9989, 9140", "2-3,6", "2007-2017");

        // M3- Tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx) and/or third (Cx?x) character are multiple primaries.
        MphRule rule = new MphRulePrimarySiteCode(MphConstants.MP_2007_LUNG_GROUP_ID, "M3");
        rule.getNotes().add("This is a change in rules; tumors in the trachea (C33) and in the lung (C34) were a single lung primary in the previous rules.");
        _rules.add(rule);

        // M4- At least one tumor that is non-small cell carcinoma (8046) and another tumor that is small cell carcinoma (8041-8045) are multiple primaries.
        rule = new MphRule(MphConstants.MP_2007_LUNG_GROUP_ID, "M4") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (GroupUtility.differentCategory(i1.getHistology(), i2.getHistology(), MphConstants.NON_SMALL_CELL_CARCINOMA, MphConstants.SMALL_CELL_CARCINOMA))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Is at least one tumor non-small cell carcinoma (8046) and another tumor small cell carcinoma (8041-8045)?");
        rule.setReason("At least one tumor that is non-small cell carcinoma (8046) and another tumor that is small cell carcinoma (8041-8045) are multiple primaries.");
        _rules.add(rule);

        // M5- A tumor that is adenocarcinoma with mixed subtypes (8255) and another that is bronchioloalveolar (8250-8254) are multiple primaries.
        rule = new MphRule(MphConstants.MP_2007_LUNG_GROUP_ID, "M5") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (GroupUtility.differentCategory(i1.getHistology(), i2.getHistology(), MphConstants.ADENOCARCINOMA_WITH_MIXED_SUBTYPES, MphConstants.BRONCHIOALVEOLAR))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Is there a tumor that is adenocarcinoma with mixed subtypes (8255) and another that is bronchioalveolar (8250-8254)?");
        rule.setReason("A tumor that is adenocarcinoma with mixed subtypes (8255) and another that is bronchioloalveolar (8250-8254) are multiple primaries.");
        _rules.add(rule);

        // M6- A single tumor in each lung is multiple primaries.
        rule = new MphRule(MphConstants.MP_2007_LUNG_GROUP_ID, "M6") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (!Arrays.asList(MphConstants.RIGHT, MphConstants.LEFT, MphConstants.BOTH).containsAll(Arrays.asList(i1.getLaterality(), i2.getLaterality()))) {
                    result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". Valid and known laterality for lung cancer should be provided.");
                }
                else if (GroupUtility.areOppositeSides(i1.getLaterality(), i2.getLaterality()))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);

                return result;
            }
        };
        rule.setQuestion("Is there a single tumor in each lung?");
        rule.setReason("A single tumor in each lung is multiple primaries.");
        rule.getNotes().add("When there is a single tumor in each lung abstract as multiple primaries unless stated or proven to be metastatic.");
        _rules.add(rule);

        // M7- Multiple tumors in both lungs with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.
        rule = new MphRule(MphConstants.MP_2007_LUNG_GROUP_ID, "M7") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                String hist1 = i1.getHistology(), hist2 = i2.getHistology();
                //if they are on the same lung, don't apply this
                if ((MphConstants.BOTH.equals(i2.getLaterality()) || MphConstants.BOTH.equals(i1.getLaterality())) && (!hist1.substring(0, 3).equals(hist2.substring(0, 3))))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Are there multiple tumors in both lungs with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number?");
        rule.setReason("Multiple tumors in both lungs with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (x?xx) number are multiple primaries.");
        _rules.add(rule);

        // M8- Tumors diagnosed more than three (3) years apart are multiple primaries.
        rule = new MphRule(MphConstants.MP_2007_LUNG_GROUP_ID, "M8") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                int diff = GroupUtility.verifyYearsApart(i1, i2, 3);
                if (-1 == diff) {
                    result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". There is not enough diagnosis date information.");
                }
                else if (1 == diff)
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);

                return result;
            }
        };
        rule.setQuestion("Are there tumors diagnosed more than three (3) years apart?");
        rule.setReason("Tumors diagnosed more than three (3) years apart are multiple primaries.");
        _rules.add(rule);

        // M9- An invasive tumor following an in situ tumor more than 60 days after diagnosis are multiple primaries.
        rule = new MphRuleBehavior(MphConstants.MP_2007_LUNG_GROUP_ID, "M9");
        _rules.add(rule);

        // M10- Tumors with non-small cell carcinoma, NOS (8046) and a more specific non-small cell carcinoma type (chart 1) are a single primary.
        rule = new MphRule(MphConstants.MP_2007_LUNG_GROUP_ID, "M10") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (GroupUtility.differentCategory(i1.getHistology(), i2.getHistology(), MphConstants.NON_SMALL_CELL_CARCINOMA, MphConstants.SPECIFIC_NON_SMALL_CELL_CARCINOMA))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                return result;
            }
        };
        rule.setQuestion("Are there tumors with non-small cell carcinoma (8046) and a more specific non-small cell carcinoma type (chart 1)?");
        rule.setReason("Tumors with non-small cell carcinoma, NOS (8046) and a more specific non-small cell carcinoma type (chart 1) are a single primary.");
        _rules.add(rule);

        // M11- Tumors with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.
        rule = new MphRuleHistologyCode(MphConstants.MP_2007_LUNG_GROUP_ID, "M11");
        rule.getNotes().add("Adenocarcinoma in one tumor and squamous cell carcinoma in another tumor are multiple primaries.");
        _rules.add(rule);

        // M12- Tumors that do not meet any of the criteria are abstracted as a single primary.
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2007_LUNG_GROUP_ID, "M12");
        rule.getNotes().add("When an invasive tumor follows an in situ tumor within 60 days, abstract as a single primary.");
        rule.getNotes().add("All cases covered by this rule are the same histology.");
        _rules.add(rule);
    }
}
