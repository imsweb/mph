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
    Renal Pelvis, Ureter, Bladder, and Other Urinary Multiple Primary Rules
    C659, C669, C670-C679, C680-C689
    (Excludes lymphoma and leukemia M9590-M9992 and Kaposi sarcoma M9140)

    Note: Multiple tumors may be a single primary or multiple primaries.

    Rule M3	Abstract multiple primaries  when there are:
        •	Tumor(s) in both the right AND left renal pelvis AND
        •	No other urinary sites are involved
        Note 1:	Only abstract a single primary when pathology confirms tumor(s) in the contralateral renal pelvis tumors are metastatic.
        Note 2:	This rule is used only when there is no involvement (tumor) in the ureter(s), bladder, or urethra.

    Rule M4	Abstract multiple primaries when there are:
        •	Tumor(s) in the right AND left ureter AND
        •	No other urinary sites are involved
        Note 1:	Only abstract a single primary when pathology confirms tumor(s) in contralateral ureter are metastatic.
        Note 2:	This rule is used only when there is no involvement (tumor) in the renal pelvis, bladder, and urethra.

    Rule M5	Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3 of Table 2 in the Equivalent Terms and Definitions. Timing is irrelevant.
        Note: The tumors may be subtypes/variants of the same or different NOS histologies.
        •	Same NOS: Leiomyosarcoma 8890/3 and liposarcoma 8850/3 are both subtypes of sarcoma NOS 8800/3 but are distinctly different histologies. Abstract multiple primaries.
        •	Different NOS: Verrucous carcinoma 8051 is a subtype of squamous cell carcinoma NOS 8070; giant cell urothelial carcinoma 8031 is a subtype of urothelial carcinoma 8120. They are distinctly different histologies. Abstract multiple primaries.

    Rule M6	Abstract a single primary when separate/non-contiguous tumors are on the same row in Table 2 in the Equivalent Terms and Definitions.  Timing is irrelevant.
        Note 1:	The same row means the tumors are:
        •	The same histology (same four-digit ICD-O code) OR
        •	One is the preferred term (column 1) and the other is a synonym for the preferred term (column 2) OR
        •	A NOS (column 1/column 2) and the other is a subtype/variant of that NOS (column 3)
        Note 2:	The tumors may:
        •	Occur in the same urinary site OR
        •	Be multifocal/multicentric in multiple urinary sites

    Rule M7	Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 2 in the Equivalent Terms and Definitions. Timing is irrelevant.
        Note: 	Each row in the table is a distinctly different histology. EXCEPTION is adenocarcinoma 8140 and carcinoma NOS 8010 are not multiple primaries.
        •	Carcinoma NOS is a very broad category which includes adenocarcinoma
        •	Adenocarcinoma NOS and all adenocarcinoma subtypes/variants are included in the carcinoma NOS category (they are all subtypes/variants of carcinoma NOS)
        Example: 	Small cell neuroendocrine carcinoma 8041 and urothelial carcinoma 8120 are on different rows in the first column of Table 2. Abstract two primaries, one for the small cell neuroendocrine carcinoma and a second for the urothelial carcinoma.

    Rule M8	Abstract a single primary when the patient has multiple recurrence(s) of in situ carcinoma which:
        •	Occur in the same urinary site OR
        •	Are multifocal/multicentric tumors in multiple urinary sites
        Note 1:	Once the patient has the original in situ tumor, subsequent in situ tumors are recorded as a recurrence for those registrars who collect recurrence data.
        Note 2:	This rule includes multiple in situ tumors of the bladder.
        Note 3:	Timing does not apply.

    Rule M9	Abstract a single primary when tumors are diagnosed in the bladder C67_ and one or both ureter(s) C669 AND tumors are:
        •	Noninvasive in situ /2 urothelial carcinoma (flat tumor) 8120/2 OR
        •	Any one of the subtypes/variants of noninvasive in situ urothelial carcinoma
        Note 1:	No other urinary organs are involved.
        Note 2:	Use this rule ONLY for noninvasive in situ urothelial carcinoma. For other histologies, continue through the rules.
        Note 3:	Urothelial carcinoma in situ spreads by intramucosal extension and may involve large areas of mucosal surface.  The default for these cases is coding a bladder primary.
    
    Rule M10	Abstract a single primaryi (the invasive) when an in situ tumor is diagnosed after an invasive tumor AND tumors:
        •	Occur in the same urinary site OR
        •	Are multifocal/multicentric tumors in multiple urinary sites
        Example:	The first presentation was multifocal/multicentric invasive tumors in multiple urinary organs; the subsequent presentation was in situ tumor in at least one of the previously involved urinary organs.
        Note 1:	The rules are hierarchical. Only use this rule when previous rules do not apply.
        Note 2:	The tumors may be a NOS and a subtype/variant of that NOS. See Table 2 in the Equivalent Terms and Definitions for listings of NOS and subtype/variants.
        Note 3:	Once the patient has an invasive tumor, the subsequent in situ is recorded as a recurrence for those registrars who collect recurrence data.
     
    Rule M11	Abstract a single primaryi (the invasive) when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor AND tumors:
        •	Occur in the same urinary site OR
        •	Are multifocal/multicentric tumors in multiple urinary sites
        Example:	The first presentation was multifocal/multicentric in situ tumors in multiple urinary organs; the subsequent presentation was invasive tumor in at least one of the previously involved urinary organs.
        Note 1:	The rules are hierarchical. Only use this rule if none of the previous rules apply.
        Note 2:	The tumors may be an NOS and a subtype/variant of that NOS
        Note 3:	When the case has been abstracted, change behavior code on original abstract from /2 to /3. Do not change date of diagnosis.
        Note 4:	If the case has already been submitted to the central registry, report all changes.
        Note 5:	The physician may stage both tumors because staging and determining multiple primaries are done for different reasons. Staging determines which treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).
        Note 6:	See the COC and SEER manuals for instructions on coding other data items such as Date of Diagnosis, Accession Year and Sequence Number

    Rule M12	Abstract multiple primariesii when an invasive tumor occurs more than 60 days after an in situ tumor AND tumors:
        •	Occur in the same urinary site OR
        •	Are multifocal/multicentric tumors in multiple urinary sites
        Example:	The first presentation was multifocal/multicentric in situ tumors in multiple urinary organs; the subsequent presentation was invasive tumor in at least one of the previously involved urinary organs.
        Note 1:	The rules are hierarchical. Only use this rule when none of the previous rules apply.
        Note 2:	Abstract both the invasive and in situ tumors.
        Note 3:	Abstract as multiple primaries even if physician states the invasive tumor is disease recurrence or progression.
        Note 4:	This rule is based on long-term epidemiologic studies of recurrence intervals. The specialty medical experts (SMEs) reviewed and approved these rules.  Many of the SMEs were also authors, co-authors, or editors of the AJCC Staging Manual.
     
    Rule M13	Abstract a single primaryi when there is a NOS and a subtype/variant of that NOS in:
        •	A single urinary organ OR
        •	Multifocal/multicentric tumors in multiple urinary sites
        Example: 	Pathology from TURB shows areas of micropapillary urothelial carcinoma 8131 and areas of urothelial carcinoma NOS 8120. Abstract a single primary, micropapillary urothelial carcinoma 8131.
        Note 1:	See Table 2 in the Equivalent Terms and Definitions for NOS and subtypes/variants.
        Note 2:	NOS and subtypes/variants include:
        •	Adenocarcinoma 8140 and subtypes/variants of adenocarcinoma
        •	Carcinoma 8010 and subtypes/variants of carcinoma
        •	Sarcoma 8800 and subtypes/variants of sarcoma
        •	Small cell neuroendocrine carcinoma 8041 and subtypes/variants of small cell neuroendocrine carcinoma
        •	Urothelial carcinoma 8120 and subtypes/variants of urothelial carcinoma

    Rule M14	Abstract a single primaryi when the patient has multiple occurrences of invasive urothelial carcinoma of the bladder.
        Note 1:	This rule applies to urothelial carcinoma NOS 8120 and all urothelial carcinoma subtypes/variants.
        Note 2:	A patient can have only one invasive urothelial bladder tumor per lifetime.
        Note 3:	The rules are hierarchical. Only use this rule when previous rules do not apply.

    Rule M15	Abstract multiple primariesii when the patient has a subsequent tumor after being clinically disease-free for greater than three years after the original diagnosis or last recurrence.
        Note 1:	This rule applies to all histologies and urinary sites with the exception of invasive urothelial carcinoma of the bladder (see previous rules).
        Note 2:	Clinically disease-free means that there was no evidence of recurrence on follow-up.
        •	Scans are NED
        •	Urine cytology is NED
        •	Scopes are NED
        Note 3:	When there is a recurrence within three years of diagnosis, the “clock” starts over. The time interval is calculated from the date of last recurrence.
        Note 4:	When it is unknown/not documented whether the patient had a recurrence, default to date of diagnosis to compute the time interval.
        Note 5:	The physician may state this is a recurrence, meaning the patient had a previous urinary site tumor and now has another urinary site tumor. Follow the rules; do not attempt to interpret the physician’s statement.
        Example: 	Patient is diagnosed with multifocal/multicentric urothelial carcinomas in the ureter and renal pelvis in January 2018. Both the kidney and ureter are surgically removed. In June 2022 the patient presents with tumor in the contralateral ureter. The physician states this is a recurrence of the original urothelial carcinoma. Code a new primary for the 2022 ureter carcinoma.

    Rule M16	Abstract a single primaryi when multifocal/multicentric urothelial carcinoma NOS 8120 OR any subtypes/variants of urothelial carcinoma NOS are diagnosed simultaneously in two or more of the following sites:
        •	Renal pelvis C659
        •	Ureter C669
        •	Bladder C670-C679
        •	Urethra/prostatic urethra C680
        Note 1:	These rules are hierarchical. Only use this rule when previous rules do not apply. A previous rule specifically lists noninvasive urothelial carcinoma of bladder and ureter as a single primary.
        Note 2:	This rule does not apply to multiple tumors in a single organ.

    Rule M17	Abstract a single primaryi when tumors do not meet any of the above criteria.
        Note: Use caution when applying this default rule. Please confirm that you have not overlooked an applicable rule.




    */

    // Renal Pelvis, Ureter, Bladder, and Other Urinary Multiple Primary Rules
    // C659, C669, C670-C679, C680-C689
    // (Excludes lymphoma and leukemia M9590-M9992 and Kaposi sarcoma M9140)

    public Mp2018UrinarySitesGroup() {
        super(MphConstants.MP_2018_URINARY_GROUP_ID, MphConstants.MP_2018_URINARY_GROUP_NAME, "C659, C669, C670-C679, C680-C689", null, null,
                "9590-9992, 9140","2-3,6", "2018-9999");

        // Rule M3	Abstract multiple primaries when there are:
        // •	Tumor(s) in both the right AND left renal pelvis AND
        // •	No other urinary sites are involved
        MphRule rule = new MphRule(MphConstants.MP_2018_URINARY_GROUP_ID, "M3") {
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
        rule.getNotes().add("Only abstract a single primary when pathology confirms tumor(s) in the contralateral renal pelvis tumors are metastatic.");
        rule.getNotes().add("This rule is used only when there is no involvement (tumor) in the ureter(s), bladder, or urethra.");
        _rules.add(rule);

        // Rule M4	Abstract multiple primaries when there are:
        // •	Tumor(s) in the right AND left ureter AND
        // •	No other urinary sites are involved
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
        rule.getNotes().add("Only abstract a single primary when pathology confirms tumor(s) in contralateral ureter are metastatic.");
        rule.getNotes().add("This rule is used only when there is no involvement (tumor) in the renal pelvis, bladder, and urethra.");
        _rules.add(rule);

        // Rule M5	Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3 of Table 2 in the Equivalent Terms and Definitions. Timing is irrelevant.
        rule = new MphRuleTwoOrMoreDifferentSubTypesInTable(MphConstants.MP_2018_URINARY_GROUP_ID, "M5", MphConstants.URINARY_2018_TABLE2, false);
        rule.setQuestion("Are separate/non-contiguous tumors two or more different subtypes/variants in Column 3, Table 2 in the Equivalent Terms and Definitions?");
        rule.setReason("Separate/non-contiguous tumors that are two or more different subtypes/variants in Column 3, Table 2 in the Equivalent Terms and Definitions, are multiple primaries.");
        rule.getNotes().add("The tumors may be subtypes/variants of the same or different NOS histologies.");
        rule.getNotes().add("  • Same NOS: Leiomyosarcoma 8890/3 and liposarcoma 8850/3 are both subtypes of sarcoma NOS 8800/3 but are distinctly different histologies. Abstract multiple primaries.");
        rule.getNotes().add("  • Different NOS: Verrucous carcinoma 8051 is a subtype of squamous cell carcinoma NOS 8070; giant cell urothelial carcinoma 8031 is a subtype of urothelial carcinoma 8120. They are distinctly different histologies. Abstract multiple primaries.");
        _rules.add(rule);

        // Rule M6	Abstract a single primary when separate/non-contiguous tumors are on the same row in Table 2 in the Equivalent Terms and Definitions.  Timing is irrelevant.
        rule = new MphRuleSameRowInTable(MphConstants.MP_2018_URINARY_GROUP_ID, "M6", MphConstants.URINARY_2018_TABLE2, false);
        rule.setQuestion("Are separate/non-contiguous tumors on different rows in Table 2 in the Equivalent Terms and Definitions?");
        rule.setReason("Separate/non-contiguous tumors that are on different rows in Table 2 in the Equivalent Terms and Definitions, are multiple primaries.");
        rule.getNotes().add("The same row means the tumors are:");
        rule.getNotes().add("  • The same histology (same four-digit ICD-O code) OR");
        rule.getNotes().add("  • One is the preferred term (column 1) and the other is a synonym for the preferred term (column 2) OR");
        rule.getNotes().add("  • A NOS (column 1/column 2) and the other is a subtype/variant of that NOS (column 3)");
        rule.getNotes().add("Note 2:	The tumors may:");
        rule.getNotes().add("  • Occur in the same urinary site OR");
        rule.getNotes().add("  • Be multifocal/multicentric in multiple urinary sites");
        _rules.add(rule);

        // Rule M7	Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 2 in the Equivalent Terms and Definitions. Timing is irrelevant.
        rule = new MphRuleDifferentRowsInTable(MphConstants.MP_2018_URINARY_GROUP_ID, "M7", MphConstants.URINARY_2018_TABLE2, false);
        rule.setQuestion("Are separate/non-contiguous tumors on different rows in Table 2 in the Equivalent Terms and Definitions?");
        rule.setReason("Separate/non-contiguous tumors that are on different rows in Table 2 in the Equivalent Terms and Definitions, are multiple primaries.");
        rule.getNotes().add("Each row in the table is a distinctly different histology. EXCEPTION is adenocarcinoma 8140 and carcinoma NOS 8010 are not multiple primaries.");
        rule.getNotes().add("  • Carcinoma NOS is a very broad category which includes adenocarcinoma");
        rule.getNotes().add("  • Adenocarcinoma NOS and all adenocarcinoma subtypes/variants are included in the carcinoma NOS category (they are all subtypes/variants of carcinoma NOS)");
        rule.getExamples().add("Small cell neuroendocrine carcinoma 8041 and urothelial carcinoma 8120 are on different rows in the first column of Table 2. Abstract two primaries, one for the small cell neuroendocrine carcinoma and a second for the urothelial carcinoma.");
        _rules.add(rule);

        // Rule M8	Abstract a single primary when the patient has multiple recurrence(s) of in situ carcinoma which:
        // •	Occur in the same urinary site OR
        // •	Are multifocal/multicentric tumors in multiple urinary sites
        rule = new MphRule(MphConstants.MP_2018_URINARY_GROUP_ID, "M8") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if ((i1.getBehavior() == MphConstants.INSITU) && (i2.getBehavior() == MphConstants.INSITU)) {
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                }
                return result;
            }
        };
        rule.setQuestion("Are tumors both In Situ?");
        rule.setReason("Multiple recurrences of an In Situ carcinoma is a single primary.");
        rule.getNotes().add("Once the patient has the original in situ tumor, subsequent in situ tumors are recorded as a recurrence for those registrars who collect recurrence data.");
        rule.getNotes().add("This rule includes multiple in situ tumors of the bladder.");
        rule.getNotes().add("Timing does not apply.");
        _rules.add(rule);

        // Rule M9	Abstract a single primary when tumors are diagnosed in the bladder C67_ and one or both ureter(s) C669 AND tumors are:
        // •	Noninvasive in situ /2 urothelial carcinoma (flat tumor) 8120/2 OR
        // •	Any one of the subtypes/variants of noninvasive in situ urothelial carcinoma
        rule = new MphRule(MphConstants.MP_2007_URINARY_GROUP_ID, "M9") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if ((GroupUtility.isSiteContained("C670-C679", i1.getPrimarySite()) || i1.getPrimarySite().equals("C669")) &&
                    (GroupUtility.isSiteContained("C670-C679", i2.getPrimarySite()) || i2.getPrimarySite().equals("C669"))) {
                    String icd1 = i1.getHistology() + "/" + i1.getBehavior(), icd2 = i2.getHistology() + "/" + i2.getBehavior();
                    if ((icd1.equals("8120/2") || icd1.equals("8130/2")) &&
                        (icd2.equals("8120/2") || icd2.equals("8130/2")))   {
                        result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                    }
                }
                return result;
            }
        };
        rule.setQuestion("Are tumors of bladder (C670-C679) or ureter (C669) in situ utothelial carcinoma or a sub type?");
        rule.setReason("Tumors of the bladder (C670-C679) or ureter (C669) and are in situ utothelial carcinoma or a sub type are a single primary.");
        rule.getNotes().add("No other urinary organs are involved.");
        rule.getNotes().add("Use this rule ONLY for noninvasive in situ urothelial carcinoma. For other histologies, continue through the rules.");
        rule.getNotes().add("Urothelial carcinoma in situ spreads by intramucosal extension and may involve large areas of mucosal surface.  The default for these cases is coding a bladder primary.");
        _rules.add(rule);

        // Rule M10	Abstract a single primary (the invasive) when an in situ tumor is diagnosed after an invasive tumor AND tumors:
        // •	Occur in the same urinary site OR
        // •	Are multifocal/multicentric tumors in multiple urinary sites
        rule = new MphRule(MphConstants.MP_2018_URINARY_GROUP_ID, "M10") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (GroupUtility.isOneBehaviorBeforeTheOther(i1, i2, MphConstants.MALIGNANT, MphConstants.INSITU)) {
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                }
                return result;
            }
        };
        rule.setQuestion("Is there an in situ tumor following an invasive tumor?");
        rule.setReason("An in situ tumor following an invasive tumor is a single primary.");
        rule.getExamples().add("The first presentation was multifocal/multicentric invasive tumors in multiple urinary organs; the subsequent presentation was in situ tumor in at least one of the previously involved urinary organs.");
        rule.getNotes().add("The rules are hierarchical. Only use this rule when previous rules do not apply.");
        rule.getNotes().add("The tumors may be a NOS and a subtype/variant of that NOS. See Table 2 in the Equivalent Terms and Definitions for listings of NOS and subtype/variants.");
        rule.getNotes().add("Once the patient has an invasive tumor, the subsequent in situ is recorded as a recurrence for those registrars who collect recurrence data.");
        _rules.add(rule);

        // Rule M11	Abstract a single primary (the invasive) when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor AND tumors:
        // •	Occur in the same urinary site OR
        // •	Are multifocal/multicentric tumors in multiple urinary sites
        rule = new MphRuleInvasiveAfterInSituLess60Days(MphConstants.MP_2018_URINARY_GROUP_ID, "M11", false);
        rule.getExamples().add("The first presentation was multifocal/multicentric in situ tumors in multiple urinary organs; the subsequent presentation was invasive tumor in at least one of the previously involved urinary organs.");
        rule.getNotes().add("The rules are hierarchical. Only use this rule if none of the previous rules apply.");
        rule.getNotes().add("The tumors may be an NOS and a subtype/variant of that NOS");
        rule.getNotes().add("When the case has been abstracted, change behavior code on original abstract from /2 to /3. Do not change date of diagnosis.");
        rule.getNotes().add("If the case has already been submitted to the central registry, report all changes.");
        rule.getNotes().add("The physician may stage both tumors because staging and determining multiple primaries are done for different reasons. Staging determines which treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).");
        rule.getNotes().add("See the COC and SEER manuals for instructions on coding other data items such as Date of Diagnosis, Accession Year and Sequence Number");
        _rules.add(rule);

        // Rule M12	Abstract multiple primaries when an invasive tumor occurs more than 60 days after an in situ tumor AND tumors:
        // •	Occur in the same urinary site OR
        // •	Are multifocal/multicentric tumors in multiple urinary sites
        rule = new MphRuleInvasiveAfterInSituGreaterThan60Days(MphConstants.MP_2018_URINARY_GROUP_ID, "M12", false);
        rule.getExamples().add("The first presentation was multifocal/multicentric in situ tumors in multiple urinary organs; the subsequent presentation was invasive tumor in at least one of the previously involved urinary organs.");
        rule.getNotes().add("The rules are hierarchical. Only use this rule when none of the previous rules apply.");
        rule.getNotes().add("Abstract both the invasive and in situ tumors.");
        rule.getNotes().add("Abstract as multiple primaries even if physician states the invasive tumor is disease recurrence or progression.");
        rule.getNotes().add("This rule is based on long-term epidemiologic studies of recurrence intervals. The specialty medical experts (SMEs) reviewed and approved these rules.  Many of the SMEs were also authors, co-authors, or editors of the AJCC Staging Manual.");
        _rules.add(rule);

        // Rule M13	Abstract a single primary when there is a NOS and a subtype/variant of that NOS in:
        // •	A single urinary organ OR
        // •	Multifocal/multicentric tumors in multiple urinary sites
        rule = new MphRuleMainTypeAndSubTypeInTable(MphConstants.MP_2018_URINARY_GROUP_ID, "M13", MphConstants.URINARY_2018_TABLE2);
        rule.setQuestion("Are tumors a NOS and a subtype/variant of that NOS?");
        rule.setReason("Tumors that are a NOS and a subtype/variant of that NOS are a single primary.");
        rule.getExamples().add("Pathology from TURB shows areas of micropapillary urothelial carcinoma 8131 and areas of urothelial carcinoma NOS 8120. Abstract a single primary, micropapillary urothelial carcinoma 8131.");
        rule.getNotes().add("See Table 2 in the Equivalent Terms and Definitions for NOS and subtypes/variants.");
        rule.getNotes().add("NOS and subtypes/variants include:");
        rule.getNotes().add("•	Adenocarcinoma 8140 and subtypes/variants of adenocarcinoma");
        rule.getNotes().add("•	Carcinoma 8010 and subtypes/variants of carcinoma");
        rule.getNotes().add("•	Sarcoma 8800 and subtypes/variants of sarcoma");
        rule.getNotes().add("•	Small cell neuroendocrine carcinoma 8041 and subtypes/variants of small cell neuroendocrine carcinoma");
        rule.getNotes().add("•	Urothelial carcinoma 8120 and subtypes/variants of urothelial carcinoma");
        _rules.add(rule);

        // Rule M14	Abstract a single primary when the patient has multiple occurrences of invasive urothelial carcinoma of the bladder.
        // Bladder (C670-C679)
        rule = new MphRule(MphConstants.MP_2018_URINARY_GROUP_ID, "M14") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (MphConstants.BLADDER.equals(i1.getPrimarySite()) && MphConstants.BLADDER.equals(i2.getPrimarySite())) {
                    String icd1 = i1.getHistology() + "/" + i1.getBehavior(), icd2 = i2.getHistology() + "/" + i2.getBehavior();
                    List<String> types = MphConstants.URINARY_2018_TABLE2.get("8120");
                    types.add("8120/2");
                    types.add("8120/3");
                    if (types.contains(icd1) && types.contains(icd2))
                        result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                }
                return result;
            }
        };
        rule.setQuestion("Are there multiple occurrences of invasive urothelial carcinoma of the bladder?");
        rule.setReason("Multiple occurrences of invasive urothelial carcinoma of the bladder is a single primary.");
        rule.getNotes().add("This rule applies to urothelial carcinoma NOS 8120 and all urothelial carcinoma subtypes/variants.");
        rule.getNotes().add("A patient can have only one invasive urothelial bladder tumor per lifetime.");
        rule.getNotes().add("The rules are hierarchical. Only use this rule when previous rules do not apply.");
        _rules.add(rule);

        // Rule M15	Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than three years after the original diagnosis or last recurrence.
        rule = new MphRuleDiagnosisDateGreaterThan3Years(MphConstants.MP_2018_URINARY_GROUP_ID, "M15");
        rule.getExamples().add("Patient is diagnosed with multifocal/multicentric urothelial carcinomas in the ureter and renal pelvis in January 2018. Both the kidney and ureter are surgically removed. In June 2022 the patient presents with tumor in the contralateral ureter. The physician states this is a recurrence of the original urothelial carcinoma. Code a new primary for the 2022 ureter carcinoma.");
        rule.getNotes().add("This rule applies to all histologies and urinary sites with the exception of invasive urothelial carcinoma of the bladder (see previous rules).");
        rule.getNotes().add("Clinically disease-free means that there was no evidence of recurrence on follow-up.");
        rule.getNotes().add("  • Scans are NED");
        rule.getNotes().add("  • Urine cytology is NED");
        rule.getNotes().add("  • Scopes are NED");
        rule.getNotes().add("When there is a recurrence within three years of diagnosis, the “clock” starts over. The time interval is calculated from the date of last recurrence.");
        rule.getNotes().add("When it is unknown/not documented whether the patient had a recurrence, default to date of diagnosis to compute the time interval.");
        rule.getNotes().add("The physician may state this is a recurrence, meaning the patient had a previous urinary site tumor and now has another urinary site tumor. Follow the rules; do not attempt to interpret the physician’s statement.");
        _rules.add(rule);

        // Rule M16	Abstract a single primary when multifocal/multicentric urothelial carcinoma NOS 8120 OR any subtypes/variants of urothelial carcinoma NOS are diagnosed simultaneously in two or more of the following sites:
        // •	Renal pelvis C659
        // •	Ureter C669
        // •	Bladder C670-C679
        // •	Urethra/prostatic urethra C680
        rule = new MphRule(MphConstants.MP_2018_URINARY_GROUP_ID, "M16") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                // Urothelial or subtype?
                String icd1 = i1.getHistology() + "/" + i1.getBehavior(), icd2 = i2.getHistology() + "/" + i2.getBehavior();
                List<String> types = MphConstants.URINARY_2018_TABLE2.get("8120");
                types.add("8120/2");
                types.add("8120/3");
                if (types.contains(icd1) && types.contains(icd2)) {
                    // Allowed site?
                    if (GroupUtility.isSiteContained(MphConstants.URINARY_UROTHELIAL_CARCINAOMA_SITES_2018, i1.getPrimarySite()) &&
                        GroupUtility.isSiteContained(MphConstants.URINARY_UROTHELIAL_CARCINAOMA_SITES_2018, i2.getPrimarySite())) {
                        // Simultaneous?
                        int sixtyDaysApart = GroupUtility.verifyDaysApart(i1, i2, 60);
                        if (-1 == sixtyDaysApart) {
                            result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                            result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". There is not enough diagnosis date information.");
                        }
                        else if (0 == sixtyDaysApart) {
                            result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        }
                    }
                }
                return result;
            }
        };
        rule.setQuestion("Are tumors urothelial carcinoma NOS 8120 (or a subtype), diagnosed simultaneously, and one of these sites C659, C669, C670-C679, or C680?");
        rule.setReason("Tumors that are urothelial carcinoma NOS 8120 (or a subtype), diagnosed simultaneously, and one of these sites C659, C669, C670-C679, or C680 are a single primary.");
        rule.getNotes().add("These rules are hierarchical. Only use this rule when previous rules do not apply. A previous rule specifically lists noninvasive urothelial carcinoma of bladder and ureter as a single primary.");
        rule.getNotes().add("This rule does not apply to multiple tumors in a single organ.");
        _rules.add(rule);

        // Rule M17	Abstract a single primary when tumors do not meet any of the above criteria.
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_URINARY_GROUP_ID, "M17");
        rule.getNotes().add("Use caution when applying this default rule. Please confirm that you have not overlooked an applicable rule.");
        _rules.add(rule);

    }
}

