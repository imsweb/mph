/*
 * Copyright (C) 2016 Information Management Services, Inc.
 */
package com.imsweb.mph.mpgroups;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.imsweb.mph.MphGroup;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphRuleResult;
import com.imsweb.mph.MphUtils;

public class Mp2004SolidMalignantGroup extends MphGroup {

    public Mp2004SolidMalignantGroup() {
        super("solid-malignant-2004", "Solid Malignant 2004", null, null, null, "9590-9989,9140", "2-3,6", "0000-2006");

        // Rule 1 TODO
        MphRule rule = new MphRule("solid-malignant-2004", "M1", MphUtils.MPResult.SINGLE_PRIMARY) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                result.setResult(MphUtils.RuleResult.FALSE);
                return result;
            }
        };
        rule.setReason("A single lesion composed of one histologic type is a single primary, even if the lesion crosses site boundaries.");
        _rules.add(rule);

        // Rule 2 TODO
        rule = new MphRule("solid-malignant-2004", "M2", MphUtils.MPResult.SINGLE_PRIMARY) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                result.setResult(MphUtils.RuleResult.FALSE);
                return result;
            }
        };
        rule.setReason("A single lesion composed of multiple (different) histologic types is a single primary even if it crosses site boundaries.");

        _rules.add(rule);

        // Rule 3
        rule = new MphRule("solid-malignant-2004", "M3", MphUtils.MPResult.SINGLE_PRIMARY) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                result.setResult(MphUtils.RuleResult.FALSE);
                int daysApart = verifyDaysApart(i1, i2, 60);
                if (isSameSite(i1.getPrimarySite(), i2.getPrimarySite()) && isSameHistology(i1.getHistologyIcdO3(), i2.getHistologyIcdO3()) && daysApart != 1) {
                    if (isPairedSite(i1.getPrimarySite()) && isPairedSite(i2.getPrimarySite())) {
                        //Each side of a paired organ is considered a separate site.
                        if (differentCategory(i1.getLaterality(), i2.getLaterality(), Collections.singletonList("1"), Collections.singletonList("2")))
                            return result;
                        else if (!Arrays.asList("1", "2").containsAll(Arrays.asList(i1.getLaterality(), i2.getLaterality()))) {
                            result.setResult(MphUtils.RuleResult.UNKNOWN);
                            if (daysApart == -1)
                                result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". Valid and known laterality and diagnosis date should be provided.");
                            else
                                result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". Valid and known laterality for paired sites should be provided.");
                            return result;
                        }
                    }
                    if (daysApart == -1) {
                        result.setResult(MphUtils.RuleResult.UNKNOWN);
                        result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". Valid and known diagnosis date should be provided.");
                    }
                    else
                        result.setResult(MphUtils.RuleResult.TRUE);
                }
                return result;
            }
        };
        rule.setReason("Simultaneous multiple lesions of the same histologic type within the same site (i.e., multifocal tumors in a single organ or site) are a single primary. "
                + "If a new cancer of the same histology as an earlier one is diagnosed in the same site within two months, this is a single primary cancer");

        _rules.add(rule);

        // Rule 4
        rule = new MphRule("solid-malignant-2004", "M3", MphUtils.MPResult.SINGLE_PRIMARY) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                result.setResult(MphUtils.RuleResult.FALSE);
                int daysApart = verifyDaysApart(i1, i2, 60);
                if (isSameSite(i1.getPrimarySite(), i2.getPrimarySite()) && isSameHistology(i1.getHistologyIcdO3(), i2.getHistologyIcdO3()) && daysApart != 1) {
                    if (isPairedSite(i1.getPrimarySite()) && isPairedSite(i2.getPrimarySite())) {
                        //Each side of a paired organ is considered a separate site.
                        if (differentCategory(i1.getLaterality(), i2.getLaterality(), Collections.singletonList("1"), Collections.singletonList("2")))
                            return result;
                        else if (!Arrays.asList("1", "2").containsAll(Arrays.asList(i1.getLaterality(), i2.getLaterality()))) {
                            result.setResult(MphUtils.RuleResult.UNKNOWN);
                            if (daysApart == -1)
                                result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". Valid and known laterality and diagnosis date should be provided.");
                            else
                                result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". Valid and known laterality for paired sites should be provided.");
                            return result;
                        }
                    }
                    if (daysApart == -1) {
                        result.setResult(MphUtils.RuleResult.UNKNOWN);
                        result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". Valid and known diagnosis date should be provided.");
                    }
                    else
                        result.setResult(MphUtils.RuleResult.TRUE);
                }
                return result;
            }
        };
        rule.setReason("Simultaneous multiple lesions of the same histologic type within the same site (i.e., multifocal tumors in a single organ or site) are a single primary. "
                + "If a new cancer of the same histology as an earlier one is diagnosed in the same site within two months, this is a single primary cancer");

        _rules.add(rule);
    }

    private boolean isSameSite(String site1, String site2) {
        List<String> exactMatches = Arrays.asList("C18", "C21", "C38", "C40", "C41", "C44", "C47", "C49");
        String s1 = site1.substring(0, 3), s2 = site2.substring(0, 3);
        if (site1.equals(site2))
            return true;
        else if (s1.equals(s2) && !exactMatches.contains(s1) && !exactMatches.contains(s2))
            return true;
        else if (Arrays.asList("C01", "C02").containsAll(Arrays.asList(s1, s2)))
            return true;
        else if (Arrays.asList("C05", "C06").containsAll(Arrays.asList(s1, s2)))
            return true;
        else if (Arrays.asList("C07", "C08").containsAll(Arrays.asList(s1, s2)))
            return true;
        else if (Arrays.asList("C09", "C10").containsAll(Arrays.asList(s1, s2)))
            return true;
        else if (Arrays.asList("C12", "C13").containsAll(Arrays.asList(s1, s2)))
            return true;
        else if (Arrays.asList("C23", "C24").containsAll(Arrays.asList(s1, s2)))
            return true;
        else if (Arrays.asList("C30", "C31").containsAll(Arrays.asList(s1, s2)))
            return true;
        else if (Arrays.asList("C33", "C34").containsAll(Arrays.asList(s1, s2)))
            return true;
        else if (Arrays.asList("C37", "C38").containsAll(Arrays.asList(s1, s2)) && !"C384".equals(site1) && !"C384".equals(site2))
            return true;
        else if ((Arrays.asList("C51", "C52").contains(s1) || Arrays.asList("C577", "C578", "C579").contains(site1)) &&
                (Arrays.asList("C51", "C52").contains(s2) || Arrays.asList("C577", "C578", "C579").contains(site2)))
            return true;
        else if (("C56".equals(s1) || Arrays.asList("C570", "C571", "C572", "C573", "C574").contains(site1)) &&
                ("C56".equals(s2) || Arrays.asList("C570", "C571", "C572", "C573", "C574").contains(site2)))
            return true;
        else if (Arrays.asList("C60", "C63").containsAll(Arrays.asList(s1, s2)))
            return true;
        else if (Arrays.asList("C64", "C65", "C66", "C68").containsAll(Arrays.asList(s1, s2)))
            return true;
        else if (Arrays.asList("C74", "C75").containsAll(Arrays.asList(s1, s2)))
            return true;
        else
            return false;

    }

    private boolean isSameHistology(String hist1, String hist2) {
        return hist1.substring(0, 3).equals(hist2.substring(0, 3)) && !differentCategory(hist1, hist2, Collections.singletonList("8046"), expandList(Collections.singletonList("8041-8045")));
    }

    private boolean isPairedSite(String site) {
        String paired =
                "C079,C080-C081,C090-C099,C300-C301,C310,C312,C340-C349,C384,C400-C403,C413-C414,C441-C443,C445-C447,C471-C472,C491-C492,C500-C509,C569,C570,C620-C629,C630-C631,C649,C659,C669,C690-C699,C700,C710-C714,C722-C725,C740-C749,C754";
        return isContained(computeRange(paired, true), Integer.valueOf(site.substring(1)));
    }

}
