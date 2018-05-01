/*
 * Copyright (C) 2018 Information Management Services, Inc.
 */
package com.imsweb.mph.mpgroups;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.imsweb.mph.MphComputeOptions;
import com.imsweb.mph.MphConstants;
import com.imsweb.mph.MphGroup;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphUtils;
import com.imsweb.mph.internal.TempRuleResult;

public class Mp2018UrinarySitesGroup extends MphGroup {

    /*
    Urinary Sites Multiple Primary Rules
    C659, C669, C670-C679, C680-C681, C688-C689
    (Excludes lymphoma and leukemia M9590-M9992 and Kaposi sarcoma M9140)

    Rule M3     Abstract multiple primaries when there are:
                • Tumor(s) in both the right AND left renal pelvis AND
                • No other urinary sites are involved
                Note 1: Only abstract a single primary when pathology confirms tumor(s) in the contralateral renal pelvis tumors are metastatic.
                Note 2: This rule is used only when there is no involvement (tumor) in the ureter(s), bladder, or urethra.

    Rule M4     Abstract multiple primaries when there are:
                • Tumor(s) in the right AND left ureter AND
                • No other urinary sites are involved
                Note 1: Only abstract a single primary when pathology confirms tumor(s) in contralateral ureter are metastatic.
                Note 2: This rule is used only when there is no involvement (tumor) in the renal pelvis, bladder, and urethra.

    Rule M5     Abstract multiple primaries when separate/non-contiguous tumors are on different lines in Table 2 in the Equivalent Terms and Definitions. Tumors may be:
                1. Simultaneous multiple tumors OR
                2. Original and subsequent tumors
                3. “Different lines” mean separate lines in the first column or second column.
                    Note 1: The first column is the specific or NOS histology/morphology term and code. Each line in the table is a distinctly different histology. EXCEPTION is adenocarcinoma 8140 and carcinoma NOS 8010.
                        • Carcinoma NOS is a very broad category which includes adenocarcinoma
                        • Do not use this rule when the diagnosis is carcinoma NOS AND adenocarcinoma NOS OR any subtypes/variants of adenocarcinoma NOS
                        Example: Small cell neuroendocrine carcinoma 8041 and urothelial carcinoma 8120 are on different lines in the first column of Table 2. Abstract two primaries, one for the small cell neuroendocrine carcinoma and a second for the urothelial carcinoma.
                    Note 2: The second column has synonyms for the specific or NOS histology/morphology term listed in the first column. A synonym is an alternate term for the histology in column 1, so when the synonyms are on different lines (different histologies) in Table 2 they are multiple primaries.

    Rule M6     Abstract multiple primaries when separate/non-contiguous tumors are different subtypes/variants in Column 3, Table 2 in the Equivalent Terms and Definitions. Tumors may be:
                • Simultaneous multiple tumors OR
                • Original and subsequent tumors
                Note: The tumors may be subtypes/variants of the same or different NOS histologies.
                    a. Same NOS: Leiomyosarcoma 8890/3 and liposarcoma 8850/3 are both subtypes of sarcoma NOS 8800/3 but are distinctly different histologies. Abstract multiple primaries.
                    b. Different NOS: Verrucous carcinoma 8051 is a synonym of squamous cell carcinoma NOS 8070; giant cell urothelial carcinoma 8031 is a subtype of urothelial carcinoma 8120. They are distinctly different histologies. Abstract multiple primaries.

    Rule M7     Abstract a single primary when noninvasive in situ (/2) urothelial carcinoma 8120/2 OR any one of the subtypes/variants of noninvasive in situ urothelial carcinoma is diagnosed in the bladder C670-C679 and one or both ureter(s) C669.
                Note 1: No other urinary organs are involved.
                Note 2: Use this rule ONLY for noninvasive or in situ urothelial carcinoma. For other histologies, continue through the rules.

    Rule M8     Abstract a single primary when an in situ tumor is diagnosed after an invasive tumor AND tumors occur in
                • The same urinary site OR
                • Multifocal/multicentric tumors in multiple urinary sites. The first presentation was multifocal/multicentric invasive tumors in multiple urinary organs; the subsequent presentation was invasive tumor in at least one of the previously involved urinary organs.
                Note 1: The rules are hierarchical. Do not use this rule if any of the rules M3-M7 apply.
                Note 2: The tumors may be a NOS and a subtype/variant of that NOS. See Table 2 in the Equivalent Terms and Definitions for listings of NOS and subtype/variants.
                Note 3: Once the patient has an invasive tumor, the in situ is recorded as a recurrence for those registrars who collect recurrence data.

    Rule M9     Abstract a single primary (the invasive) when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor in
                • The same urinary site OR
                • Multifocal/multicentric tumors in multiple urinary sites. The first presentation was multifocal/multicentric in situ tumors in multiple urinary organs; the subsequent presentation was invasive tumor in at least one of the previously involved urinary organs.
                Note 1: The rules are hierarchical. Do not use this rule if any of the rules M3-M8 apply
                Note 2: The tumors may be an NOS and a subtype/variant of that NOS
                Note 3: When the case has been abstracted, change behavior code on original abstract from /2 to /3. Do not change date of diagnosis.
                Note 4: If the case has already been submitted to the central registry, report all changes.
                Note 5: The physician may stage both tumors because staging and determining multiple primaries are done for different reasons. Staging determines which treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).
                Note 6: See the COC and SEER manuals for instructions on coding other data items such as Date of Diagnosis, Accession Year and Sequence Number

    Rule M10    Abstract multiple primaries when an invasive tumor occurs more than 60 days after an in situ tumor in
                • The same urinary site OR
                • Multifocal/multicentric tumors in multiple urinary sites.
                    Example: The first presentation was multifocal/multicentric in situ tumors in multiple urinary organs; the subsequent presentation was invasive tumor in at least one of the previously involved urinary organs.
                Note 1: The rules are hierarchical. Do not use this rule if rules M3-M9 apply.
                Note 2: Abstract both the invasive and in situ tumors.
                Note 3: Abstract as multiple primaries even if physician states the invasive tumor is disease recurrence or progression.
                Note 4: This rule is based on long-term epidemiologic studies of recurrence intervals. The specialty medical experts (SMEs) reviewed and approved these rules. Many of the SMEs were also authors, co-authors, or editors of the AJCC Staging Manual.

    Rule M11    Abstract a single primary when there is a NOS and a subtype/variant of that NOS in
                • A single urinary organ OR
                • Multifocal/multicentric tumors in multiple urinary sites.
                • Example 1: The first presentation was multifocal/multicentric in situ tumors in multiple urinary organs; the subsequent presentation was invasive tumor in at least one of the previously involved urinary organs.
                • Example 2: Pathology from TURB shows areas of micropapillary urothelial carcinoma 8131 and areas of urothelial carcinoma NOS 8120. Abstract a single primary, micropapillary urothelial carcinoma 8131.
                Note 1: See Table 2 in the Equivalent Terms and Definitions for NOS and subtypes/variants.
                Note 2: NOS and subtypes/variants are:
                    • Adenocarcinoma NOS 8140 and subtypes/variants of adenocarcinoma NOS
                    • Carcinoma NOS 8010 and subtypes/variants of carcinoma NOS
                    • Sarcoma NOS 8800 and subtypes/variants of sarcoma NOS
                    • Small cell neuroendocrine carcinoma 8041 and subtypes/variants of small cell neuroendocrine carcinoma
                    • Urothelial carcinoma 8120 and subtypes/variants of urothelial carcinoma

    Rule M12    Abstract a single primary when the patient has multiple occurrences of invasive urothelial carcinoma of the bladder.
                • Includes urothelial carcinoma NOS 8120 and all urothelial carcinoma subtypes/variants.
                Note 1: A patient can have only one invasive urothelial bladder tumor per lifetime.
                Note 2: The rules are hierarchical. Do not use this rule if rules M1-M11 apply.

    Rule M13    Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for more than three years after the original diagnosis.
                Note 1: This rule applies to all histologies and urinary sites with the exception of invasive urothelial carcinoma of the bladder (M12).
                Note 2: Clinically disease-free means that there was no evidence of recurrence on follow-up.
                Note 3: When there is a recurrence within three years of diagnosis, the “clock” starts over. The time interval is calculated from the date of last recurrence.
                Note 4: When it is unknown/not documented whether the patient had a recurrence, default to date of diagnosis to compute the time interval.
                Note 5: The physician may state this is a recurrence, meaning the patient had a previous urinary site tumor and now has another urinary site tumor. Follow the rules. Do not attempt to interpret the physician’s statement.
                Example: Patient is diagnosed with multifocal/multicentric urothelial carcinomas in the ureter and renal pelvis in January 2018. Both the kidney and ureter are surgically removed. In June 2022 the patient presents with tumor in the contralateral ureter. The physician states this is a recurrence of the original urothelial carcinoma. Code a new primary for the 2022 ureter carcinoma.

    Rule M14    Abstract a single primary when multifocal/multicentric urothelial carcinoma NOS 8120 OR any subtypes/variants of urothelial carcinoma NOS are diagnosed simultaneously in two or more of the following sites:
                • Renal pelvis C659
                • Ureter C669
                • Bladder C670-C679
                • Urethra /prostatic urethra C680
                Note 1: These rules are hierarchical. Do not use this rule when any of the rules M3-M13 apply. M7 specifically lists noninvasive urothelial carcinoma of bladder and ureter as a single primary.
                Note 2: This rule does not apply to multiple tumors in a single organ. See Equivalent Terms and Definitions for further clarifications.

    Rule M15    Abstract a single primary when tumors do not meet any of the above criteria.

    */

    // TODO
    public Mp2018UrinarySitesGroup() {
        super(MphConstants.MP_2018_URINARY_GROUP_ID, MphConstants.MP_2018_URINARY_GROUP_NAME, "C659, C669, C670-C679, C680-C689", null, null, "9590-9989, 9140", "2-3,6", "2018-9999");

        // Rule M3 - Abstract multiple primaries when there are:
        // • Tumor(s) in both the right AND left renal pelvis AND
        // • No other urinary sites are involved
        // TODO
        MphRule rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_URINARY_GROUP_ID, "M3");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("Only abstract a single primary when pathology confirms tumor(s) in the contralateral renal pelvis tumors are metastatic.");
        rule.getNotes().add("This rule is used only when there is no involvement (tumor) in the ureter(s), bladder, or urethra.");
        _rules.add(rule);

        // Rule M4 - Abstract multiple primaries when there are:
        // • Tumor(s) in the right AND left ureter AND
        // • No other urinary sites are involved
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_URINARY_GROUP_ID, "M4");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("Only abstract a single primary when pathology confirms tumor(s) in contralateral ureter are metastatic.");
        rule.getNotes().add("This rule is used only when there is no involvement (tumor) in the renal pelvis, bladder, and urethra.");
        _rules.add(rule);

        // Rule M5 - Abstract multiple primaries when separate/non-contiguous tumors are on different lines in Table 2 in the Equivalent Terms and Definitions. Tumors may be:
        // 1. Simultaneous multiple tumors OR
        // 2. Original and subsequent tumors
        // 3. “Different lines” mean separate lines in the first column or second column.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_URINARY_GROUP_ID, "M5");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The first column is the specific or NOS histology/morphology term and code. Each line in the table is a distinctly different histology. EXCEPTION is adenocarcinoma 8140 and carcinoma NOS 8010.");
        rule.getNotes().add("  • Carcinoma NOS is a very broad category which includes adenocarcinoma");
        rule.getNotes().add("  • Do not use this rule when the diagnosis is carcinoma NOS AND adenocarcinoma NOS OR any subtypes/variants of adenocarcinoma NOS");
        rule.getExamples().add("Small cell neuroendocrine carcinoma 8041 and urothelial carcinoma 8120 are on different lines in the first column of Table 2. Abstract two primaries, one for the small cell neuroendocrine carcinoma and a second for the urothelial carcinoma.");
        rule.getNotes().add("The second column has synonyms for the specific or NOS histology/morphology term listed in the first column. A synonym is an alternate term for the histology in column 1, so when the synonyms are on different lines (different histologies) in Table 2 they are multiple primaries.");
        _rules.add(rule);

        // Rule M6 - Abstract multiple primaries when separate/non-contiguous tumors are different subtypes/variants in Column 3, Table 2 in the Equivalent Terms and Definitions. Tumors may be:
        // • Simultaneous multiple tumors OR
        // • Original and subsequent tumors
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_URINARY_GROUP_ID, "M6");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The tumors may be subtypes/variants of the same or different NOS histologies.");
        rule.getNotes().add("  a. Same NOS: Leiomyosarcoma 8890/3 and liposarcoma 8850/3 are both subtypes of sarcoma NOS 8800/3 but are distinctly different histologies. Abstract multiple primaries.");
        rule.getNotes().add("  b. Different NOS: Verrucous carcinoma 8051 is a synonym of squamous cell carcinoma NOS 8070; giant cell urothelial carcinoma 8031 is a subtype of urothelial carcinoma 8120. They are distinctly different histologies. Abstract multiple primaries.");
        _rules.add(rule);

        // Rule M7 - Abstract a single primary when noninvasive in situ (/2) urothelial carcinoma 8120/2 OR any one of the subtypes/variants of noninvasive in situ urothelial carcinoma is diagnosed in the bladder C670-C679 and one or both ureter(s) C669.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_URINARY_GROUP_ID, "M7");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("No other urinary organs are involved.");
        rule.getNotes().add("Use this rule ONLY for noninvasive or in situ urothelial carcinoma. For other histologies, continue through the rules.");
        _rules.add(rule);

        // Rule M8 - Abstract a single primary when an in situ tumor is diagnosed after an invasive tumor AND tumors occur in
        // • The same urinary site OR
        // • Multifocal/multicentric tumors in multiple urinary sites. The first presentation was multifocal/multicentric invasive tumors in multiple urinary organs; the subsequent presentation was invasive tumor in at least one of the previously involved urinary organs.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_URINARY_GROUP_ID, "M8");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The rules are hierarchical. Do not use this rule if any of the rules M3-M7 apply.");
        rule.getNotes().add("The tumors may be a NOS and a subtype/variant of that NOS. See Table 2 in the Equivalent Terms and Definitions for listings of NOS and subtype/variants.");
        rule.getNotes().add("Once the patient has an invasive tumor, the in situ is recorded as a recurrence for those registrars who collect recurrence data.");
        _rules.add(rule);

        // Rule M9 - Abstract a single primary (the invasive) when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor in
        // • The same urinary site OR
        // • Multifocal/multicentric tumors in multiple urinary sites. The first presentation was multifocal/multicentric in situ tumors in multiple urinary organs; the subsequent presentation was invasive tumor in at least one of the previously involved urinary organs.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_URINARY_GROUP_ID, "M9");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The rules are hierarchical. Do not use this rule if any of the rules M3-M8 apply");
        rule.getNotes().add("The tumors may be an NOS and a subtype/variant of that NOS");
        rule.getNotes().add("When the case has been abstracted, change behavior code on original abstract from /2 to /3. Do not change date of diagnosis.");
        rule.getNotes().add("If the case has already been submitted to the central registry, report all changes.");
        rule.getNotes().add("The physician may stage both tumors because staging and determining multiple primaries are done for different reasons. Staging determines which treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).");
        rule.getNotes().add("See the COC and SEER manuals for instructions on coding other data items such as Date of Diagnosis, Accession Year and Sequence Number");
        _rules.add(rule);

        // Rule M10 - Abstract multiple primaries when an invasive tumor occurs more than 60 days after an in situ tumor in
        // • The same urinary site OR
        // • Multifocal/multicentric tumors in multiple urinary sites.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_URINARY_GROUP_ID, "M10");
        rule.setQuestion("");
        rule.setReason("");
        rule.getExamples().add("The first presentation was multifocal/multicentric in situ tumors in multiple urinary organs; the subsequent presentation was invasive tumor in at least one of the previously involved urinary organs.");
        rule.getNotes().add("The rules are hierarchical. Do not use this rule if rules M3-M9 apply.");
        rule.getNotes().add("Abstract both the invasive and in situ tumors.");
        rule.getNotes().add("Abstract as multiple primaries even if physician states the invasive tumor is disease recurrence or progression.");
        rule.getNotes().add("This rule is based on long-term epidemiologic studies of recurrence intervals. The specialty medical experts (SMEs) reviewed and approved these rules. Many of the SMEs were also authors, co-authors, or editors of the AJCC Staging Manual.");
        _rules.add(rule);

        // Rule M11 - Abstract a single primary when there is a NOS and a subtype/variant of that NOS in
        // • A single urinary organ OR
        // • Multifocal/multicentric tumors in multiple urinary sites.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_URINARY_GROUP_ID, "M11");
        rule.setQuestion("");
        rule.setReason("");
        rule.getExamples().add("The first presentation was multifocal/multicentric in situ tumors in multiple urinary organs; the subsequent presentation was invasive tumor in at least one of the previously involved urinary organs.");
        rule.getExamples().add("Pathology from TURB shows areas of micropapillary urothelial carcinoma 8131 and areas of urothelial carcinoma NOS 8120. Abstract a single primary, micropapillary urothelial carcinoma 8131.");
        rule.getNotes().add("See Table 2 in the Equivalent Terms and Definitions for NOS and subtypes/variants.");
        rule.getNotes().add("NOS and subtypes/variants are:");
        rule.getNotes().add("  • Adenocarcinoma NOS 8140 and subtypes/variants of adenocarcinoma NOS");
        rule.getNotes().add("  • Carcinoma NOS 8010 and subtypes/variants of carcinoma NOS");
        rule.getNotes().add("  • Sarcoma NOS 8800 and subtypes/variants of sarcoma NOS");
        rule.getNotes().add("  • Small cell neuroendocrine carcinoma 8041 and subtypes/variants of small cell neuroendocrine carcinoma");
        rule.getNotes().add("  • Urothelial carcinoma 8120 and subtypes/variants of urothelial carcinoma");
        _rules.add(rule);

        // Rule M12 - Abstract a single primary when the patient has multiple occurrences of invasive urothelial carcinoma of the bladder.
        // • Includes urothelial carcinoma NOS 8120 and all urothelial carcinoma subtypes/variants.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_URINARY_GROUP_ID, "M12");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("A patient can have only one invasive urothelial bladder tumor per lifetime.");
        rule.getNotes().add("The rules are hierarchical. Do not use this rule if rules M1-M11 apply.");
        _rules.add(rule);

        // Rule M13 - Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for more than three years after the original diagnosis.");
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_URINARY_GROUP_ID, "M13");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("This rule applies to all histologies and urinary sites with the exception of invasive urothelial carcinoma of the bladder (M12).");
        rule.getNotes().add("Clinically disease-free means that there was no evidence of recurrence on follow-up.");
        rule.getNotes().add("When there is a recurrence within three years of diagnosis, the “clock” starts over. The time interval is calculated from the date of last recurrence.");
        rule.getNotes().add("When it is unknown/not documented whether the patient had a recurrence, default to date of diagnosis to compute the time interval.");
        rule.getNotes().add("The physician may state this is a recurrence, meaning the patient had a previous urinary site tumor and now has another urinary site tumor. Follow the rules. Do not attempt to interpret the physician’s statement.");
        rule.getExamples().add("Patient is diagnosed with multifocal/multicentric urothelial carcinomas in the ureter and renal pelvis in January 2018. Both the kidney and ureter are surgically removed. In June 2022 the patient presents with tumor in the contralateral ureter. The physician states this is a recurrence of the original urothelial carcinoma. Code a new primary for the 2022 ureter carcinoma.");
        _rules.add(rule);

        // Rule M14 - Abstract a single primary when multifocal/multicentric urothelial carcinoma NOS 8120 OR any subtypes/variants of urothelial carcinoma NOS are diagnosed simultaneously in two or more of the following sites:
        // • Renal pelvis C659
        // • Ureter C669
        // • Bladder C670-C679
        // • Urethra /prostatic urethra C680
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_URINARY_GROUP_ID, "M14");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("These rules are hierarchical. Do not use this rule when any of the rules M3-M13 apply. M7 specifically lists noninvasive urothelial carcinoma of bladder and ureter as a single primary.");
        rule.getNotes().add("This rule does not apply to multiple tumors in a single organ. See Equivalent Terms and Definitions for further clarifications.");
        _rules.add(rule);

        // Rule M15 - Abstract a single primary when tumors do not meet any of the above criteria.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_URINARY_GROUP_ID, "M15");
        rule.setQuestion("");
        rule.setReason("");
        _rules.add(rule);


        /*
        // M3 - When no other urinary sites are involved, tumor(s) in the right renal pelvis AND tumor(s) in the left renal pelvis are multiple primaries. (C659)
        MphRule rule = new MphRule(MphConstants.MP_2007_URINARY_GROUP_ID, "M3") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (MphConstants.RENAL_PELVIS.equals(i1.getPrimarySite()) && MphConstants.RENAL_PELVIS.equals(i2.getPrimarySite())) {
                    if (!GroupUtility.validPairedSiteLaterality(i1.getLaterality(), i2.getLaterality())) {
                        result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                        result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". Valid and known laterality for renal pelvis tumors should be provided.");
                    }
                    else if (GroupUtility.areOppositeSides(i1.getLaterality(), i2.getLaterality()))
                        result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                }
                return result;
            }
        };
        rule.setQuestion("Are there tumors in both the right renal pelvis and the left renal pelvis and no other urinary sites are involved?");
        rule.setReason("When no other urinary sites are involved, tumor(s) in the right renal pelvis AND tumor(s) in the left renal pelvis are multiple primaries.");
        rule.getNotes().add("Use this rule and abstract as a multiple primary unless documented to be metastatic.");
        _rules.add(rule);

        // M4 - When no other urinary sites are involved, tumor(s) in both the right ureter AND tumor(s) in the left ureter are multiple primaries. (C669)
        rule = new MphRule(MphConstants.MP_2007_URINARY_GROUP_ID, "M4") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (MphConstants.URETER.equals(i1.getPrimarySite()) && MphConstants.URETER.equals(i2.getPrimarySite())) {
                    if (!GroupUtility.validPairedSiteLaterality(i1.getLaterality(), i2.getLaterality())) {
                        result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                        result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". Valid and known laterality for renal pelvis tumors should be provided.");
                    }
                    else if (GroupUtility.areOppositeSides(i1.getLaterality(), i2.getLaterality()))
                        result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                }
                return result;
            }
        };
        rule.setQuestion("Are there tumors in both the right ureter and the left ureter and no other urinary sites are involved?");
        rule.setReason("When no other urinary sites are involved, tumor(s) in both the right ureter AND tumor(s) in the left ureter are multiple primaries.");
        rule.getNotes().add("Use this rule and abstract as a multiple primary unless documented to be metastatic.");
        _rules.add(rule);

        // M5- An invasive tumor following an in situ tumor more than 60 days after diagnosis are multiple primaries.
        rule = new MphRuleBehavior(MphConstants.MP_2007_URINARY_GROUP_ID, "M5");
        _rules.add(rule);

        // M6 - Bladder tumors with any combination of the following histologies: papillary carcinoma (8050), transitional cell carcinoma (8120-8124),
        // or papillary transitional cell carcinoma (8130-8131), are a single primary.
        rule = new MphRule(MphConstants.MP_2007_URINARY_GROUP_ID, "M6") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
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
        rule = new MphRule(MphConstants.MP_2007_URINARY_GROUP_ID, "M7") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                int diff = GroupUtility.verifyYearsApart(i1, i2, 3);
                if (-1 == diff) {
                    result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". There is no enough diagnosis date information.");
                }
                else if (1 == diff)
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);

                return result;
            }
        };
        rule.setQuestion("Are there tumors diagnosed more than three (3) years apart?");
        rule.setReason("Tumors diagnosed more than three (3) years apart are multiple primaries.");
        _rules.add(rule);

        // M8 - Urothelial tumors in two or more of the following sites are a single primary* (See Table 1 of pdf)
        // Renal pelvis (C659), Ureter(C669), Bladder (C670-C679), Urethra /prostatic urethra (C680)
        rule = new MphRule(MphConstants.MP_2007_URINARY_GROUP_ID, "M8") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                String site1 = i1.getPrimarySite(), site2 = i2.getPrimarySite();
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
        rule = new MphRuleHistologyCode(MphConstants.MP_2007_URINARY_GROUP_ID, "M9");
        _rules.add(rule);

        // M10- Tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx) and/or third (Cx?x) character are multiple primaries.
        rule = new MphRulePrimarySiteCode(MphConstants.MP_2007_URINARY_GROUP_ID, "M10");
        _rules.add(rule);

        // M11- Tumors that do not meet any of the criteria are abstracted as a single primary.
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2007_URINARY_GROUP_ID, "M11");
        rule.getNotes().add("When an invasive tumor follows an in situ tumor within 60 days, abstract as a single primary.");
        _rules.add(rule);
        */

    }
}

