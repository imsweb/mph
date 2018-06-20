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

    Rule M5	Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 2 in the Equivalent Terms and Definitions. Tumors may be:
        •	Simultaneous OR
        •	Original and subsequent
        Note: 	Each row in the table is a distinctly different histology. EXCEPTION is adenocarcinoma 8140 and carcinoma NOS 8010 are not multiple primaries.
        •	Carcinoma NOS is a very broad category which includes adenocarcinoma
        •	Adenocarcinoma NOS and all adenocarcinoma subtypes/variants are included in the carcinoma NOS category (they are all subtypes/variants of carcinoma NOS)
        Example: 	Small cell neuroendocrine carcinoma 8041 and urothelial carcinoma 8120 are on different rows in the first column of Table 2. Abstract two primaries, one for the small cell neuroendocrine carcinoma and a second for the urothelial carcinoma.

    Rule M6	Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 2 in the Equivalent Terms and Definitions. Tumors may be:
        •	Simultaneous OR
        •	Original and subsequent
        Note: The tumors may be subtypes/variants of the same or different NOS histologies.
        •	Same NOS: Leiomyosarcoma 8890/3 and liposarcoma 8850/3 are both subtypes of sarcoma NOS 8800/3 but are distinctly different histologies. Abstract multiple primaries.
        •	Different NOS: Verrucous carcinoma 8051 is a subtype of squamous cell carcinoma NOS 8070; giant cell urothelial carcinoma 8031 is a subtype of urothelial carcinoma 8120. They are distinctly different histologies. Abstract multiple primaries.

    Rule M7	Abstract a single primary when tumors are diagnosed in the bladder C67_ and one or both ureter(s) C669 AND tumors are:
        •	Noninvasive in situ /2 urothelial carcinoma (flat tumor) 8120/2 OR
        •	Any one of the subtypes/variants of noninvasive in situ urothelial carcinoma
        Note 1:	No other urinary organs are involved.
        Note 2:	Use this rule ONLY for noninvasive in situ urothelial carcinoma. For other histologies, continue through the rules.
        Note 3:	Urothelial carcinoma in situ spreads by intramucosal extension and may involve large areas of mucosal surface.  The default for these cases is coding a bladder primary.

    Rule M8	Abstract a single primary when an in situ tumor is diagnosed after an invasive tumor AND tumors:
        •	Occur in the same urinary site OR
        •	Are multifocal/multicentric tumors in multiple urinary sites. The first presentation was multifocal/multicentric invasive tumors in multiple urinary organs; the subsequent presentation was in situ tumor in at least one of the previously involved urinary organs.
        Note 1:	The rules are hierarchical. Only use this rule when previous rules do not apply.
        Note 2:	The tumors may be a NOS and a subtype/variant of that NOS. See Table 2 in the Equivalent Terms and Definitions for listings of NOS and subtype/variants.
        Note 3:	Once the patient has an invasive tumor, the subsequent in situ is recorded as a recurrence for those registrars who collect recurrence data.

    Rule M9	Abstract a single primary (the invasive) when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor in
        •	The same urinary site OR
        •	Multifocal/multicentric tumors in multiple urinary sites. The first presentation was multifocal/multicentric in situ tumors in multiple urinary organs; the subsequent presentation was invasive tumor in at least one of the previously involved urinary organs.
        Note 1:	The rules are hierarchical. Only use this rule if none of the previous rules apply.
        Note 2:	The tumors may be an NOS and a subtype/variant of that NOS
        Note 3:	When the case has been abstracted, change behavior code on original abstract from /2 to /3. Do not change date of diagnosis.
        Note 4:	If the case has already been submitted to the central registry, report all changes.
        Note 5:	The physician may stage both tumors because staging and determining multiple primaries are done for different reasons. Staging determines which treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).
        Note 6:	See the COC and SEER manuals for instructions on coding other data items such as Date of Diagnosis, Accession Year and Sequence Number

    Rule M10	Abstract multiple primaries when an invasive tumor occurs more than 60 days after an in situ tumor in
        •	The same urinary site OR
        •	Multifocal/multicentric tumors in multiple urinary sites.
        Example: The first presentation was multifocal/multicentric in situ tumors in multiple urinary organs; the subsequent presentation was invasive tumor in at least one of the previously involved urinary organs.
        Note 1:	The rules are hierarchical. Only use this rule when none of the previous rules apply.
        Note 2:	Abstract both the invasive and in situ tumors.
        Note 3:	Abstract as multiple primaries even if physician states the invasive tumor is disease recurrence or progression.
        Note 4:	This rule is based on long-term epidemiologic studies of recurrence intervals. The specialty medical experts (SMEs) reviewed and approved these rules.  Many of the SMEs were also authors, co-authors, or editors of the AJCC Staging Manual.
     
    Rule M11	Abstract a single primary when there is a NOS and a subtype/variant of that NOS in
        •	A single urinary organ OR
        •	Multifocal/multicentric tumors in multiple urinary sites.
        Example 1: 	The first presentation was multifocal/multicentric in situ tumors in multiple urinary organs; the subsequent presentation was in situ AND/OR invasive tumor in at least one of the previously involved urinary organs.
        Example 2: 	Pathology from TURB shows areas of micropapillary urothelial carcinoma 8131 and areas of urothelial carcinoma NOS 8120. Abstract a single primary, micropapillary urothelial carcinoma 8131.
        Note 1:	See Table 2 in the Equivalent Terms and Definitions for NOS and subtypes/variants.
        Note 2:	NOS and subtypes/variants include:
        •	Adenocarcinoma 8140 and subtypes/variants of adenocarcinoma
        •	Carcinoma 8010 and subtypes/variants of carcinoma
        •	Sarcoma 8800 and subtypes/variants of sarcoma
        •	Small cell neuroendocrine carcinoma 8041 and subtypes/variants of small cell neuroendocrine carcinoma
        •	Urothelial carcinoma 8120 and subtypes/variants of urothelial carcinoma

    Rule M12	Abstract a single primary when the patient has multiple occurrences of in situ carcinoma of:
        •	Any one urinary organ OR
        •	Multifocal/multicentric tumors in multiple organs

    Rule M13	Abstract a single primary when the patient has multiple occurrences of invasive urothelial carcinoma of the bladder.
        •	Includes urothelial carcinoma NOS 8120 and all urothelial carcinoma subtypes/variants.
        Note 1:	A patient can have only one invasive urothelial bladder tumor per lifetime.
        Note 2:	The rules are hierarchical. Only use this rule when previous rules do not apply.

    Rule M14	Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for more than three years after the original diagnosis or last recurrence.
        Note 1:	This rule applies to all histologies and urinary sites with the exception of invasive urothelial carcinoma of the bladder (see previous rules).
        Note 2:	Clinically disease-free means that there was no evidence of recurrence on follow-up.
        •	Scans are NED
        •	Urine cytology is NED
        •	Scopes are NED
        Note 3:	When there is a recurrence within three years of diagnosis, the “clock” starts over. The time interval is calculated from the date of last recurrence.
        Note 4:	When it is unknown/not documented whether the patient had a recurrence, default to date of diagnosis to compute the time interval.
        Note 5:	The physician may state this is a recurrence, meaning the patient had a previous urinary site tumor and now has another urinary site tumor. Follow the rules. Do not attempt to interpret the physician’s statement.
        Example: Patient is diagnosed with multifocal/multicentric urothelial carcinomas in the ureter and renal pelvis in January 2018. Both the kidney and ureter are surgically removed. In June 2022 the patient presents with tumor in the contralateral ureter. The physician states this is a recurrence of the original urothelial carcinoma. Code a new primary for the 2022 ureter carcinoma.

        Rule M15	Abstract a single primary when multifocal/multicentric urothelial carcinoma NOS 8120 OR any subtypes/variants of urothelial carcinoma NOS are diagnosed simultaneously in two or more of the following sites:
        •	Renal pelvis C659
        •	Ureter C669
        •	Bladder C670-C679
        •	Urethra /prostatic urethra C680
        Note 1:	These rules are hierarchical. Only use this rule when previous rules do not apply. A previous rule specifically lists noninvasive urothelial carcinoma of bladder and ureter as a single primary.
        Note 2:	This rule does not apply to multiple tumors in a single organ. See Equivalent Terms and Definitions for further clarifications.

    Rule M16	Abstract a single primary when tumors do not meet any of the above criteria.

    */

    // Urinary Sites Multiple Primary Rules
    // C659, C669, C670-C679, C680-C681, C688-C689
    // (Excludes lymphoma and leukemia M9590-M9992 and Kaposi sarcoma M9140)

    public Mp2018UrinarySitesGroup() {
        super(MphConstants.MP_2018_URINARY_GROUP_ID, MphConstants.MP_2018_URINARY_GROUP_NAME, "C659, C669, C670-C679, C680-C681, C688-C689", null, null,
                "9590-9992, 9140","2-3,6", "2018-9999");

        // Rule M3	Abstract multiple primaries  when there are:
        // •	Tumor(s) in both the right AND left renal pelvis AND
        // •	No other urinary sites are involved
        // TODO
        MphRule rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_URINARY_GROUP_ID, "M3");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("Only abstract a single primary when pathology confirms tumor(s) in the contralateral renal pelvis tumors are metastatic.");
        rule.getNotes().add("This rule is used only when there is no involvement (tumor) in the ureter(s), bladder, or urethra.");
        _rules.add(rule);

        // Rule M4	Abstract multiple primaries when there are:
        // •	Tumor(s) in the right AND left ureter AND
        // •	No other urinary sites are involved
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_URINARY_GROUP_ID, "M4");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("Only abstract a single primary when pathology confirms tumor(s) in contralateral ureter are metastatic.");
        rule.getNotes().add("This rule is used only when there is no involvement (tumor) in the renal pelvis, bladder, and urethra.");
        _rules.add(rule);

        // Rule M5	Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 2 in the Equivalent Terms and Definitions. Tumors may be:
        // •	Simultaneous OR
        // •	Original and subsequent
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_URINARY_GROUP_ID, "M5");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("Each row in the table is a distinctly different histology. EXCEPTION is adenocarcinoma 8140 and carcinoma NOS 8010 are not multiple primaries.");
        rule.getNotes().add("  • Carcinoma NOS is a very broad category which includes adenocarcinoma");
        rule.getNotes().add("  • Adenocarcinoma NOS and all adenocarcinoma subtypes/variants are included in the carcinoma NOS category (they are all subtypes/variants of carcinoma NOS)");
        rule.getExamples().add("Small cell neuroendocrine carcinoma 8041 and urothelial carcinoma 8120 are on different rows in the first column of Table 2. Abstract two primaries, one for the small cell neuroendocrine carcinoma and a second for the urothelial carcinoma.");
        _rules.add(rule);

        // Rule M6	Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 2 in the Equivalent Terms and Definitions. Tumors may be:
        // •	Simultaneous OR
        // •	Original and subsequent
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_URINARY_GROUP_ID, "M6");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The tumors may be subtypes/variants of the same or different NOS histologies.");
        rule.getNotes().add("  • Same NOS: Leiomyosarcoma 8890/3 and liposarcoma 8850/3 are both subtypes of sarcoma NOS 8800/3 but are distinctly different histologies. Abstract multiple primaries.");
        rule.getNotes().add("  • Different NOS: Verrucous carcinoma 8051 is a subtype of squamous cell carcinoma NOS 8070; giant cell urothelial carcinoma 8031 is a subtype of urothelial carcinoma 8120. They are distinctly different histologies. Abstract multiple primaries.");
        _rules.add(rule);

        // Rule M7	Abstract a single primary when tumors are diagnosed in the bladder C67_ and one or both ureter(s) C669 AND tumors are:
        // •	Noninvasive in situ /2 urothelial carcinoma (flat tumor) 8120/2 OR
        // •	Any one of the subtypes/variants of noninvasive in situ urothelial carcinoma
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_URINARY_GROUP_ID, "M7");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("No other urinary organs are involved.");
        rule.getNotes().add("Use this rule ONLY for noninvasive in situ urothelial carcinoma. For other histologies, continue through the rules.");
        rule.getNotes().add("Urothelial carcinoma in situ spreads by intramucosal extension and may involve large areas of mucosal surface.  The default for these cases is coding a bladder primary.");
        _rules.add(rule);

        // Rule M8	Abstract a single primary when an in situ tumor is diagnosed after an invasive tumor AND tumors:
        // •	Occur in the same urinary site OR
        // •	Are multifocal/multicentric tumors in multiple urinary sites. The first presentation was multifocal/multicentric invasive tumors in multiple urinary organs; the subsequent presentation was in situ tumor in at least one of the previously involved urinary organs.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_URINARY_GROUP_ID, "M8");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The rules are hierarchical. Only use this rule when previous rules do not apply.");
        rule.getNotes().add("The tumors may be a NOS and a subtype/variant of that NOS. See Table 2 in the Equivalent Terms and Definitions for listings of NOS and subtype/variants.");
        rule.getNotes().add("Once the patient has an invasive tumor, the subsequent in situ is recorded as a recurrence for those registrars who collect recurrence data.");
        _rules.add(rule);

        // Rule M9	Abstract a single primary (the invasive) when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor in
        // •	The same urinary site OR
        // •	Multifocal/multicentric tumors in multiple urinary sites. The first presentation was multifocal/multicentric in situ tumors in multiple urinary organs; the subsequent presentation was invasive tumor in at least one of the previously involved urinary organs.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_URINARY_GROUP_ID, "M9");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The rules are hierarchical. Only use this rule if none of the previous rules apply.");
        rule.getNotes().add("The tumors may be an NOS and a subtype/variant of that NOS");
        rule.getNotes().add("When the case has been abstracted, change behavior code on original abstract from /2 to /3. Do not change date of diagnosis.");
        rule.getNotes().add("If the case has already been submitted to the central registry, report all changes.");
        rule.getNotes().add("The physician may stage both tumors because staging and determining multiple primaries are done for different reasons. Staging determines which treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).");
        rule.getNotes().add("See the COC and SEER manuals for instructions on coding other data items such as Date of Diagnosis, Accession Year and Sequence Number");
        _rules.add(rule);

        // Rule M10	Abstract multiple primaries when an invasive tumor occurs more than 60 days after an in situ tumor in
        // •	The same urinary site OR
        // •	Multifocal/multicentric tumors in multiple urinary sites.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_URINARY_GROUP_ID, "M10");
        rule.setQuestion("");
        rule.setReason("");
        rule.getExamples().add("The first presentation was multifocal/multicentric in situ tumors in multiple urinary organs; the subsequent presentation was invasive tumor in at least one of the previously involved urinary organs.");
        rule.getNotes().add("The rules are hierarchical. Only use this rule when none of the previous rules apply.");
        rule.getNotes().add("Abstract both the invasive and in situ tumors.");
        rule.getNotes().add("Abstract as multiple primaries even if physician states the invasive tumor is disease recurrence or progression.");
        rule.getNotes().add("This rule is based on long-term epidemiologic studies of recurrence intervals. The specialty medical experts (SMEs) reviewed and approved these rules.  Many of the SMEs were also authors, co-authors, or editors of the AJCC Staging Manual.");
        _rules.add(rule);

        // Rule M11	Abstract a single primary when there is a NOS and a subtype/variant of that NOS in
        // •	A single urinary organ OR
        // •	Multifocal/multicentric tumors in multiple urinary sites.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_URINARY_GROUP_ID, "M11");
        rule.setQuestion("");
        rule.setReason("");
        rule.getExamples().add("The first presentation was multifocal/multicentric in situ tumors in multiple urinary organs; the subsequent presentation was in situ AND/OR invasive tumor in at least one of the previously involved urinary organs.");
        rule.getExamples().add("Pathology from TURB shows areas of micropapillary urothelial carcinoma 8131 and areas of urothelial carcinoma NOS 8120. Abstract a single primary, micropapillary urothelial carcinoma 8131.");
        rule.getNotes().add("See Table 2 in the Equivalent Terms and Definitions for NOS and subtypes/variants.");
        rule.getNotes().add("NOS and subtypes/variants include:");
        rule.getNotes().add("  • Adenocarcinoma 8140 and subtypes/variants of adenocarcinoma");
        rule.getNotes().add("  • Carcinoma 8010 and subtypes/variants of carcinoma");
        rule.getNotes().add("  • Sarcoma 8800 and subtypes/variants of sarcoma");
        rule.getNotes().add("  • Small cell neuroendocrine carcinoma 8041 and subtypes/variants of small cell neuroendocrine carcinoma");
        rule.getNotes().add("  • Urothelial carcinoma 8120 and subtypes/variants of urothelial carcinoma");
        _rules.add(rule);
    }
}

