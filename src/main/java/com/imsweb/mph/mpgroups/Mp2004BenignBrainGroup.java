/*
 * Copyright (C) 2016 Information Management Services, Inc.
 */
package com.imsweb.mph.mpgroups;

import java.util.Arrays;
import java.util.Collections;

import com.imsweb.mph.MphConstants;
import com.imsweb.mph.MphGroup;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphRuleResult;
import com.imsweb.mph.MphUtils;

public class Mp2004BenignBrainGroup extends MphGroup {

    public Mp2004BenignBrainGroup() {
        super("benign-brain-2004", "Benign Brain 2004", "C700-C729,C751-C753", null, null, "9590-9989,9140", "0-1", "0000-2006");

        // Rule 1
        MphRule rule = new MphRule("benign-brain-2004", "M1", MphUtils.MPResult.SINGLE_PRIMARY) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                result.setResult(MphUtils.RuleResult.FALSE);
                if (isSameSite(i1.getPrimarySite(), i2.getPrimarySite()) && isSameHistology(i1.getHistology(), i1.getBehavior(), i2.getHistology(), i2.getBehavior())
                        && Arrays.asList("1", "2").containsAll(Arrays.asList(i1.getLaterality(), i2.getLaterality())) && i1.getLaterality().equals(i2.getLaterality()))
                    result.setResult(MphUtils.RuleResult.TRUE);
                return result;
            }
        };
        rule.setReason(
                "Multiple non-malignant tumors of the same histology that recur in the same site and same side (laterality) as the original tumor are recurrences (single primary) even after 20 years.");
        _rules.add(rule);

        // Rule 2
        rule = new MphRule("benign-brain-2004", "M2", MphUtils.MPResult.SINGLE_PRIMARY) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                result.setResult(MphUtils.RuleResult.FALSE);
                if (isSameSite(i1.getPrimarySite(), i2.getPrimarySite()) && isSameHistology(i1.getHistology(), i1.getBehavior(), i2.getHistology(), i2.getBehavior())
                        && !differentCategory(i1.getLaterality(), i2.getLaterality(), Collections.singletonList("1"), Collections.singletonList("2")))
                    result.setResult(MphUtils.RuleResult.TRUE);

                return result;
            }
        };
        rule.setReason("Multiple non-malignant tumors of the same histology that recur in the same site and it is unknown if it is the same side (laterality) as the original "
                + "tumor are recurrences (single primary) even after 20 years.");
        _rules.add(rule);

        // Rule 3
        rule = new MphRule("benign-brain-2004", "M3", MphUtils.MPResult.MULTIPLE_PRIMARIES) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                result.setResult(MphUtils.RuleResult.FALSE);
                if (!isSameSite(i1.getPrimarySite(), i2.getPrimarySite()) && isSameHistology(i1.getHistology(), i1.getBehavior(), i2.getHistology(), i2.getBehavior()))
                    result.setResult(MphUtils.RuleResult.TRUE);
                return result;
            }
        };
        rule.setReason("Multiple non-malignant tumors of the same histology in different sites of the CNS are separate (multiple) primaries.");
        _rules.add(rule);

        // Rule 4
        rule = new MphRule("benign-brain-2004", "M4", MphUtils.MPResult.MULTIPLE_PRIMARIES) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                result.setResult(MphUtils.RuleResult.FALSE);
                if (isSameHistology(i1.getHistology(), i1.getBehavior(), i2.getHistology(), i2.getBehavior()) && differentCategory(i1.getLaterality(), i2.getLaterality(),
                        Collections.singletonList("1"), Collections.singletonList("2")))
                    result.setResult(MphUtils.RuleResult.TRUE);
                return result;
            }
        };
        rule.setReason("Multiple non-malignant tumors of the same histology in different sides (laterality) of the CNS are separate (multiple) primaries.");
        _rules.add(rule);

        // Rule 5
        rule = new MphRule("benign-brain-2004", "M5", MphUtils.MPResult.MULTIPLE_PRIMARIES) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                result.setResult(!isSameHistology(i1.getHistology(), i1.getBehavior(), i2.getHistology(), i2.getBehavior()) ? MphUtils.RuleResult.TRUE : MphUtils.RuleResult.FALSE);
                return result;
            }
        };
        rule.setReason("Multiple non-malignant tumors of different histologies are separate (multiple) primaries)");
        _rules.add(rule);
    }

    private boolean isSameSite(String site1, String site2) {
        return site1.substring(0, 3).equals(site2.substring(0, 3));
    }

    private boolean isSameHistology(String hist1, String beh1, String hist2, String beh2) {
        String group1 = MphConstants._BENIGN_BRAIN_2004_HISTOLOGY_GROUPING.get(hist1);
        if (group1 == null)
            group1 = MphConstants._BENIGN_BRAIN_2004_HISTOLOGY_GROUPING.get(hist1 + "/" + beh1);
        String group2 = MphConstants._BENIGN_BRAIN_2004_HISTOLOGY_GROUPING.get(hist2);
        if (group2 == null)
            group2 = MphConstants._BENIGN_BRAIN_2004_HISTOLOGY_GROUPING.get(hist2 + "/" + beh2);
        if (group1 != null && group2 != null)
            return group1.equals(group2);
        else
            return hist1.substring(0, 3).equals(hist2.substring(0, 3));
    }
}
