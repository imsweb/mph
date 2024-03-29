/*
 * Copyright (C) 2013 Information Management Services, Inc.
 */
package com.imsweb.mph.mpgroups;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.imsweb.mph.MphConstants;
import com.imsweb.mph.MphGroup;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphUtils;
import com.imsweb.mph.RuleExecutionContext;
import com.imsweb.mph.internal.TempRuleResult;
import com.imsweb.mph.mprules.MpRuleHistology;
import com.imsweb.mph.mprules.MpRuleInvasiveAfterInsituGreaterThan60Days;
import com.imsweb.mph.mprules.MpRuleNoCriteriaSatisfied;
import com.imsweb.mph.mprules.MpRulePrimarySite;
import com.imsweb.mph.mprules.MpRuleRenalPelvis;
import com.imsweb.mph.mprules.MpRuleUreter;
import com.imsweb.mph.mprules.MpRuleYearsApart;

public class Mp2007UrinaryGroup extends MphGroup {

    public Mp2007UrinaryGroup() {
        super(MphConstants.MPH_2007_URINARY_GROUP_ID, MphConstants.MPH_2007_2017_URINARY, "C659, C669, C670-C679, C680-C689", null, null, "9590-9993, 9140", "2-3,6", "2007-2017");

        // M3 - When no other urinary sites are involved, tumor(s) in the right renal pelvis AND tumor(s) in the left renal pelvis are multiple primaries. (C659) 
        MphRule rule = new MpRuleRenalPelvis(MphConstants.MPH_2007_2017_URINARY, "M3");
        rule.setQuestion("Are there tumors in both the right renal pelvis and the left renal pelvis and no other urinary sites are involved?");
        rule.setReason("When no other urinary sites are involved, tumor(s) in the right renal pelvis AND tumor(s) in the left renal pelvis are multiple primaries.");
        rule.getNotes().add("Use this rule and abstract as a multiple primary unless documented to be metastatic.");
        _rules.add(rule);

        // M4 - When no other urinary sites are involved, tumor(s) in both the right ureter AND tumor(s) in the left ureter are multiple primaries. (C669) 
        rule = new MpRuleUreter(MphConstants.MPH_2007_2017_URINARY, "M4");
        rule.setQuestion("Are there tumors in both the right ureter and the left ureter and no other urinary sites are involved?");
        rule.setReason("When no other urinary sites are involved, tumor(s) in both the right ureter AND tumor(s) in the left ureter are multiple primaries.");
        rule.getNotes().add("Use this rule and abstract as a multiple primary unless documented to be metastatic.");
        _rules.add(rule);

        // M5- An invasive tumor following an in situ tumor more than 60 days after diagnosis are multiple primaries.
        rule = new MpRuleInvasiveAfterInsituGreaterThan60Days(MphConstants.MPH_2007_2017_URINARY, "M5");
        rule.getNotes().add("The purpose of this rule is to ensure that the case is counted as an incident (invasive) case when incidence data are analyzed.");
        rule.getNotes().add("Abstract as multiple primaries even if the medical record/physician states it is recurrence or progression of disease.");
        _rules.add(rule);

        // M6 - Bladder tumors with any combination of the following histologies: papillary carcinoma (8050), transitional cell carcinoma (8120-8124), 
        // or papillary transitional cell carcinoma (8130-8131), are a single primary.       
        rule = new MphRule(MphConstants.MPH_2007_2017_URINARY, "M6") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
                TempRuleResult result = new TempRuleResult();
                List<String> carcinomaHist = new ArrayList<>(MphConstants.TRANSITIONAL_CELL_CARCINOMA);
                carcinomaHist.addAll(MphConstants.PAPILLARY_TRANSITIONAL_CELL_CARCINOMA);
                carcinomaHist.add(MphConstants.PAPILLARY_CARCINOMA);
                if (i1.getPrimarySite().startsWith(MphConstants.BLADDER) && i2.getPrimarySite().startsWith(MphConstants.BLADDER) && carcinomaHist.containsAll(
                        Arrays.asList(i1.getHistology(), i2.getHistology())))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                return result;
            }
        };
        rule.setQuestion("Are there bladder tumors with any combination of the following histologies:\n" +
                "- papillary carcinoma (8050)\n" +
                "- transitional cell carcinoma (8120-8124)\n" +
                "- papillary tansitional cell carcinoma (8130-8131)?");
        rule.setReason(
                "Bladder tumors with any combination of the following histologies: papillary carcinoma (8050), transitional cell carcinoma (8120-8124), or papillary transitional cell carcinoma (8130-8131), are a single primary.");
        _rules.add(rule);

        // M7 - Tumors diagnosed more than three (3) years apart are multiple primaries.
        rule = new MpRuleYearsApart(MphConstants.MPH_2007_2017_URINARY, "M7", 3);
        _rules.add(rule);

        // M8 - Urothelial tumors in two or more of the following sites are a single primary* (See Table 1 of pdf)
        // Renal pelvis (C659), Ureter(C669), Bladder (C670-C679), Urethra /prostatic urethra (C680)
        rule = new MphRule(MphConstants.MPH_2007_2017_URINARY, "M8") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
                TempRuleResult result = new TempRuleResult();
                String site1 = i1.getPrimarySite();
                String site2 = i2.getPrimarySite();
                if (MphConstants.UROTHELIAL.containsAll(Arrays.asList(i1.getHistology(), i2.getHistology())) && (MphConstants.RENAL_PELVIS.equals(site1) || MphConstants.URETER.equals(site1) || site1
                        .startsWith(MphConstants.BLADDER) || MphConstants.URETHRA.equals(site1)) && (MphConstants.RENAL_PELVIS.equals(site2) || MphConstants.URETER.equals(site2) || site2.startsWith(
                        MphConstants.BLADDER) || MphConstants.URETHRA.equals(site2)))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                return result;
            }
        };
        rule.setQuestion("Are there urothelial tumors in two or more of the following sites:\n" +
                "- Renal pelvis (C659)\n" +
                "- Ureter(C669)\n" +
                "- Bladder (C670-C679)\n" +
                "- Urethra /prostatic urethra (C680)");
        rule.setReason("Urothelial tumors in two or more of the following sites are a single primary.\n" +
                "- Renal pelvis (C659)\n" +
                "- Ureter(C669)\n" +
                "- Bladder (C670-C679)\n" +
                "- Urethra /prostatic urethra (C680)");
        _rules.add(rule);

        // M9- Tumors with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.        
        rule = new MpRuleHistology(MphConstants.MPH_2007_2017_URINARY, "M9");
        _rules.add(rule);

        // M10- Tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx) and/or third (Cx?x) character are multiple primaries.
        rule = new MpRulePrimarySite(MphConstants.MPH_2007_2017_URINARY, "M10");
        _rules.add(rule);

        // M11- Tumors that do not meet any of the criteria are abstracted as a single primary.
        rule = new MpRuleNoCriteriaSatisfied(MphConstants.MPH_2007_2017_URINARY, "M11");
        rule.getNotes().add("When an invasive tumor follows an in situ tumor within 60 days, abstract as a single primary.");
        _rules.add(rule);
    }
}
