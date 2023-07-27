/*
 * Copyright (C) 2013 Information Management Services, Inc.
 */
package com.imsweb.mph.mpgroups;

import java.util.Arrays;
import java.util.List;

import com.imsweb.mph.HematoDataProvider;
import com.imsweb.mph.MphConstants;
import com.imsweb.mph.MphGroup;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphUtils;
import com.imsweb.mph.internal.TempRuleResult;
import com.imsweb.mph.mprules.MpRuleFollowing;
import com.imsweb.mph.mprules.MpRuleHistology;
import com.imsweb.mph.mprules.MpRuleLateralityPairedSites;
import com.imsweb.mph.mprules.MpRuleNoCriteriaSatisfied;

public class Mp2007BenignBrainGroup extends MphGroup {

    public Mp2007BenignBrainGroup() {
        super(MphConstants.MPH_2007_BENIGN_BRAIN_GROUP_ID, MphConstants.MPH_2007_2017_BENIGN_BRAIN, "C700-C701, C709-C725, C728-C729, C751-C753", null, null, "9590-9993,9140", "0-1", "2007-2017");

        // M3 - An invasive brain tumor (/3) and either a benign brain tumor (/0) or an uncertain/borderline brain tumor (/1) are always multiple primaries.        
        MphRule rule = new MphRule(MphConstants.MPH_2007_2017_BENIGN_BRAIN, "M3") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, HematoDataProvider provider) {
                //This will never happen, since the two conditions belong to different cancer group.           
                return new TempRuleResult();
            }
        };
        rule.setQuestion("Is there an invasive tumor (/3) and either a benign brain tumor (/0) or an uncertain/borderline brain tumor (/1)?");
        rule.setReason("An invasive brain tumor (/3) and either a benign brain tumor (/0) or an uncertain/borderline brain tumor (/1) are always multiple primaries.");
        _rules.add(rule);

        // M4 - Tumors with ICD-O-3 topography codes that are different at the second (C?xx) and/or third characters (Cx?x), or fourth (Cxx?) are multiple primaries.
        rule = new MphRule(MphConstants.MPH_2007_2017_BENIGN_BRAIN, "M4") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, HematoDataProvider provider) {
                TempRuleResult result = new TempRuleResult();
                if (!i1.getPrimarySite().equals(i2.getPrimarySite()))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Are there tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx), third character (Cx?x) and/or fourth character (Cxx?)?");
        rule.setReason("Tumors with ICD-O-3 topography codes that are different at the second (C?xx) and/or third characters (Cx?x), or fourth (Cxx?) are multiple primaries.");
        _rules.add(rule);

        // M5 - Tumors on both sides (left and right) of a paired site (Table 1) are multiple primaries.
        List<String> pairedSites = Arrays.asList("C700", "C710", "C711", "C712", "C713", "C714", "C722", "C723", "C724", "C725");
        rule = new MpRuleLateralityPairedSites(MphConstants.MPH_2007_2017_BENIGN_BRAIN, "M5", pairedSites);
        rule.setQuestion("Are there tumors on both sides (left and right) of a paired site?");
        rule.setReason("Tumors on both sides (left and right) of a paired site are multiple primaries.");
        _rules.add(rule);

        // M6 - An atypical choroid plexus papilloma (9390/1) following a choroid plexus papilloma, NOS (9390/0) is a single primary.
        rule = new MpRuleFollowing(MphConstants.MPH_2007_2017_BENIGN_BRAIN, "M6", "9390/1", "9390/0");
        rule.setQuestion("Is there an atypicalchoroid plexuspapilloma (9390/1) following achoroid plexus papilloma,NOS (9390/0)?");
        rule.setReason("An atypical choroid plexus papilloma (9390/1) following a choroid plexus papilloma, NOS (9390/0) is a single primary.");
        rule.getNotes().add("Do not code progression of disease as multiple primaries.");
        _rules.add(rule);

        // M7 - A neurofibromatosis, NOS (9540/1) following a neurofibroma, NOS (9540/0) is a single primary.
        rule = new MpRuleFollowing(MphConstants.MPH_2007_2017_BENIGN_BRAIN, "M7", "9540/1", "9540/0");
        rule.setQuestion("Is there a neurofibromatosis, NOS (9540/1) following a neurofibroma, NOS (9540/0)?");
        rule.setReason("A neurofibromatosis, NOS (9540/1) following a neurofibroma, NOS (9540/0) is a single primary.");
        rule.getNotes().add("Do not code progression of disease as multiple primaries.");
        _rules.add(rule);

        // M8 - Tumors with two or more histologic types on the same branch in Chart 1 are a single primary.
        rule = new MphRule(MphConstants.MPH_2007_2017_BENIGN_BRAIN, "M8") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, HematoDataProvider provider) {
                TempRuleResult result = new TempRuleResult();
                String icd1 = i1.getIcdCode();
                String icd2 = i2.getIcdCode();
                String branch1 = MphConstants.BENIGN_BRAIN_2007_CHART1.get(icd1) != null ? MphConstants.BENIGN_BRAIN_2007_CHART1.get(icd1) : MphConstants.BENIGN_BRAIN_2007_CHART1.get(
                        i1.getHistology());
                String branch2 = MphConstants.BENIGN_BRAIN_2007_CHART1.get(icd2) != null ? MphConstants.BENIGN_BRAIN_2007_CHART1.get(icd2) : MphConstants.BENIGN_BRAIN_2007_CHART1.get(
                        i2.getHistology());
                if (branch1 != null && branch1.equals(branch2))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);

                return result;
            }
        };
        rule.setQuestion("Do the tumors have two or more histologic types on the same branch in Chart 1?");
        rule.setReason("Tumors with two or more histologic types on the same branch in Chart 1 are a single primary.");
        _rules.add(rule);

        // M9 - Tumors with multiple histologic types on different branches in Chart 1 are multiple primaries.
        rule = new MphRule(MphConstants.MPH_2007_2017_BENIGN_BRAIN, "M9") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, HematoDataProvider provider) {
                TempRuleResult result = new TempRuleResult();
                String icd1 = i1.getIcdCode();
                String icd2 = i2.getIcdCode();
                String branch1 = MphConstants.BENIGN_BRAIN_2007_CHART1.get(icd1) != null ? MphConstants.BENIGN_BRAIN_2007_CHART1.get(icd1) : MphConstants.BENIGN_BRAIN_2007_CHART1.get(
                        i1.getHistology());
                String branch2 = MphConstants.BENIGN_BRAIN_2007_CHART1.get(icd2) != null ? MphConstants.BENIGN_BRAIN_2007_CHART1.get(icd2) : MphConstants.BENIGN_BRAIN_2007_CHART1.get(
                        i2.getHistology());
                if (branch1 != null && branch2 != null && !branch1.equals(branch2))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);

                return result;
            }
        };
        rule.setQuestion("Do the tumors have multiple histologic types on different branches in Chart 1?");
        rule.setReason("Tumors with multiple histologic types on different branches in Chart 1 are multiple primaries.");
        _rules.add(rule);

        // M10 - Tumors with two or more histologic types and at least one of the histologies is not listed in Chart 1 are multiple primaries.
        rule = new MphRule(MphConstants.MPH_2007_2017_BENIGN_BRAIN, "M10") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, HematoDataProvider provider) {
                TempRuleResult result = new TempRuleResult();
                String icd1 = i1.getIcdCode();
                String icd2 = i2.getIcdCode();
                String branch1 = MphConstants.BENIGN_BRAIN_2007_CHART1.get(icd1) != null ? MphConstants.BENIGN_BRAIN_2007_CHART1.get(icd1) : MphConstants.BENIGN_BRAIN_2007_CHART1.get(
                        i1.getHistology());
                String branch2 = MphConstants.BENIGN_BRAIN_2007_CHART1.get(icd2) != null ? MphConstants.BENIGN_BRAIN_2007_CHART1.get(icd2) : MphConstants.BENIGN_BRAIN_2007_CHART1.get(
                        i2.getHistology());
                //This rule is used only when one histology code is listed in chart and the other not, see note for M11
                if ((branch1 != null && branch2 == null) || (branch2 != null && branch1 == null))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Do the tumors have two or more histologic types and at least one of the histologies is not listed in Chart 1?");
        rule.setReason("Tumors with two or more histologic types and at least one of the histologies is not listed in Chart 1 are multiple primaries.");
        _rules.add(rule);

        //M11- Tumors with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.        
        rule = new MpRuleHistology(MphConstants.MPH_2007_2017_BENIGN_BRAIN, "M11");
        rule.getNotes().add("Use this rule when none of the histology codes are listed in Chart 1.");
        _rules.add(rule);

        //M12- Tumors that do not meet any of the criteria are abstracted as a single primary.
        rule = new MpRuleNoCriteriaSatisfied(MphConstants.MPH_2007_2017_BENIGN_BRAIN, "M12");
        rule.getNotes().add("Timing is not used to determine multiple primaries for benign and borderline intracranial and CNS tumors.");
        rule.getExamples().add("Tumors in the same site with the same histology (Chart 1) and the same laterality as the original tumor are a single primary.");
        rule.getExamples().add("Tumors in the same site with the same histology (Chart 1) and it is unknown if laterality is the same as the original tumor are a single primary.");
        rule.getExamples().add("Tumors in the same site and same laterality with histology codes not listed in Chart 1 that have the same first three numbers are a single primary.");
        _rules.add(rule);
    }
}
