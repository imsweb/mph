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

    // URINARY 2019 - See MphConstants for AS OF date.

    /*
    Renal Pelvis, Ureter, Bladder, and Other Urinary Multiple Primary Rules
    C659, C669, C670-C679, C680-C689
    (Excludes lymphoma and leukemia M9590 – M9992 and Kaposi sarcoma M9140)

    Rule M3	Abstract multiple primaries when there are:
        •	Separate/non-contiguous tumors in both the right AND left renal pelvis AND
        •	No other urinary sites are involved with separate/non-contiguous tumors
        Note 1:	Only abstract a single primary when pathology confirms tumor(s) in the contralateral renal pelvis are metastatic.
        Note 2:	This rule is used only when there is no involvement by separate/non-contiguous tumors in the ureter(s), bladder, or urethra.

    Rule M4	Abstract multiple primaries when there are:
        •	Separate/non-contiguous tumors in the right AND left ureter AND
        •	No other urinary sites are involved with separate/non-contiguous tumors
        Note 1:	Only abstract a single primary when pathology confirms tumor(s) in contralateral ureter are metastatic.
        Note 2:	This rule is used only when there is no involvement by separate/non-contiguous tumors in the renal pelvis, bladder, and urethra.

    Rule M5	Abstract a single primary when synchronous or simultaneous tumors are noninvasive in situ /2 urothelial carcinoma (flat tumor) 8120/2 in the following sites:
        •	Bladder C67_ AND
        •	One or both ureter(s) C669
        Note 1:	No other urinary organs are involved.
        Note 2:	Use this rule ONLY for noninvasive in situ urothelial carcinoma (may be called noninvasive urothelial carcinoma or noninvasive flat tumor). For other histologies, continue through the rules.
        Note 3:	Urothelial carcinoma in situ spreads by intramucosal extension and may involve large areas of mucosal surface.  The default for these cases is coding a bladder primary.

    Rule M6	Abstract a single primary when the patient has multiple occurrences of noninvasive in situ /2 urothelial carcinoma (flat tumor) 8120/2 tumors in the bladder.  Original tumor and subsequent tumor are 8120/2.
        Note 1:	Timing is irrelevant.
        Note 2:	Abstract only one in situ urothelial bladder tumor per the patient’s lifetime.

    Rule M7	Abstract a single primary when the patient has multiple occurrences of invasive tumors in the bladder.  Original tumor and subsequent tumor are either:
        •	Papillary urothelial carcinoma and a recurrence of papillary urothelial carcinoma 8130/3 OR
        •	Urothelial carcinoma and a recurrence of urothelial carcinoma 8120/3
        Note 1:	Timing is irrelevant.
        Note 2:	This rule does not include subtypes/variants of 8120/3 or 8130/3.
        Note 3:	Abstract only one invasive urothelial bladder tumor per the patient’s lifetime.
        Note 4:	The rules are hierarchical. Only use this rule when previous rules do not apply.
     
    Rule M8	Abstract a single primary when there are synchronous urothelial carcinomas in multiple urinary organs.
        Note 1:	This rule is ONLY for urothelial carcinoma 8120 and all subtypes/variants of urothelial carcinoma. This rule does not apply to any other carcinomas or sarcomas.
        Note 2:	The histology for all tumors must be identical. The behavior is irrelevant.
        Note 3:	This rule applies to multifocal/multicentric carcinoma which involves two or more of the following urinary sites:
        •	Renal pelvis
        •	Ureter
        •	Bladder
        •	Urethra

    Rule M9	Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than three years after the original diagnosis or last recurrence.
        Note 1:	The rules are hierarchical.  This rule does not apply to urothelial carcinoma of the bladder.
        Note 2:	Clinically disease-free means that there was no evidence of recurrence on follow-up.
        •	Scans are NED
        •	Urine cytology is NED
        •	Scopes are NED
        Note 3:	When there is a recurrence within three years of diagnosis, the “clock” starts over. The time interval is calculated from the date of last recurrence.
        Note 4:	When it is unknown/not documented whether the patient had a recurrence, default to date of diagnosis to compute the time interval.
        Note 5:	The physician may state this is a recurrence, meaning the patient had a previous urinary site tumor and now has another urinary site tumor. Follow the rules; do not attempt to interpret the physician’s statement.
        Example: 	Patient is diagnosed with multifocal/multicentric urothelial carcinomas in the ureter and renal pelvis in January 2018. Both the kidney and ureter are surgically removed. In June 2022 the patient presents with tumor in the contralateral ureter. The physician states this is a recurrence of the original urothelial carcinoma. Code a new primary for the 2022 ureter carcinoma.
     
    Rule M10	Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3 of Table 2 in the Equivalent Terms and Definitions. Timing is irrelevant.
        Note: The tumors may be subtypes/variants of the same or different NOS histologies.
        •	Same NOS: Leiomyosarcoma 8890/3 and liposarcoma 8850/3 are both subtypes of sarcoma NOS 8800/3 but are distinctly different histologies. Abstract multiple primaries.
        •	Different NOS: Verrucous carcinoma 8051 is a subtype of squamous cell carcinoma NOS 8070; giant cell urothelial carcinoma 8031 is a subtype of urothelial carcinoma 8120. They are distinctly different histologies. Abstract multiple primaries.

    Rule M11	Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 2 in the Equivalent Terms and Definitions. Timing is irrelevant.
        Note: 	Each row in the table is a distinctly different histology.
        Example: 	Small cell neuroendocrine carcinoma 8041 and urothelial carcinoma 8120 are on different rows of Table 2. Abstract two primaries, one for the small cell neuroendocrine carcinoma and a second for the urothelial carcinoma.

    Rule M12	Abstract multiple primaries when the patient has non-synchronous tumors which are:
        •	Papillary urothelial/transitional cell NOS 8130/3 AND
        •	Micropapillary urothelial/transitional cell 8131/3
        Note 1:	This is a new rule for 2019.
        Note 2:	Micropapillary urothelial cell carcinoma is an extremely aggressive neoplasm.  It is important to capture the incidence of micropapillary urothelial carcinoma, therefore it is excluded from the typical “NOS and subtype/variant” rule (same row in table 2).
        Note 3:	For synchronous tumors, continue through the rules.  Code the most specific histology.
     
    Rule M13	Abstract multiple primaries when the original tumor and subsequent tumor occur in different urinary sites.
        Note 1:	These tumors are not synchronous. Treatment has been given for the first tumor prior to the occurrence of the second tumor.
        Note 2:	Histology and behavior are irrelevant.  These tumors are always multiple primaries.
        Note 3:	The original tumor occurs in one of the following urinary sites; the second tumor occurs in a different urinary site:
        •	Renal pelvis (original tumor was not in renal pelvis)
        •	Ureter (original tumor was not in ureter)
        •	Bladder (original tumor was not in bladder)
        •	Urethra (original tumor was not in urethra)
        Example:	The patient was diagnosed 1/1/2018 with squamous cell carcinoma of the renal pelvis 8070/3.  Patient had a nephrectomy.  On routine follow-up six months later, the patient was diagnosed with urothelial carcinoma of the bladder 8120/3.  The patient has two non-synchronous tumors involving different urinary organs. Abstract multiple primaries.

    Rule M14	Abstract a single primary when synchronous, separate/non-contiguous tumors are on the same row in Table 2 in the Equivalent Terms and Definitions.
        Note 1:	The tumors must be the same behavior.  When one tumor is in situ and the other invasive, continue through the rules.
        Note 2:	The same row means the tumors are:
        •	The same histology (same four-digit ICD-O code) OR
        •	One is the preferred term (column 1) and the other is a synonym for the preferred term (column 2) OR
        •	A NOS (column 1/column 2) and the other is a subtype/variant of that NOS (column 3)
        Note 3:	The multiple tumors may:
        •	Occur in the same urinary site OR
        •	Be multifocal/multicentric occurring in at least two of the following urinary sites:
        	Renal pelvis C659
        	Ureter C669
        	Bladder C670-C679
        	Urethra/prostatic urethra C680
        Note:	A previous rule specifically lists in situ urothelial carcinoma of bladder and ureter as a single primary.
     
    Rule M15	Abstract a single primary (the invasive) when an in situ tumor is diagnosed after an invasive tumor AND tumors:
        •	Occur in the same urinary site OR
        •	The original tumors are multifocal/multicentric and occur in multiple urinary sites; subsequent tumor(s) are in at least one of the previously involved urinary sites
        Note 1:	The rules are hierarchical. Only use this rule when previous rules do not apply.
        Note 2:	The tumors may be a NOS and a subtype/variant of that NOS. See Table 2 in the Equivalent Terms and Definitions for listings of NOS and subtype/variants.
        Note 3:	Once the patient has an invasive tumor, the subsequent in situ is recorded as a recurrence for those registrars who collect recurrence data.

    Rule M16	Abstract a single primary (the invasive) when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor AND tumors:
        •	Occur in the same urinary site OR
        •	Original tumor is multifocal/multicentric and involves multiple urinary sites; the subsequent invasive tumor(s) occur in at least one of the previously involved urinary sites
        Note 1:	The rules are hierarchical. Only use this rule if none of the previous rules apply.
        Note 2:	The tumors may be an NOS and a subtype/variant of that NOS.
        Note 3:	When the case has been abstracted, change behavior code on original abstract from /2 to /3. Do not change date of diagnosis.
        Note 4:	If the case has already been submitted to the central registry, report all changes.
        Note 5:	The physician may stage both tumors because staging and determining multiple primaries are done for different reasons. Staging determines which treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).
        Note 6:	See the COC and SEER manuals for instructions on coding other data items such as Date of Diagnosis, Accession Year and Sequence Number.

    Rule M17	Abstract multiple primaries when an invasive tumor occurs more than 60 days after an in situ tumor AND tumors:
        •	Occur in the same urinary site OR
        •	Are multifocal/multicentric tumors in multiple urinary sites
        Example:	The first presentation was multifocal/multicentric in situ tumors in multiple urinary organs; the subsequent presentation was invasive tumor in at least one of the previously involved urinary organs.
        Note 1:	The rules are hierarchical. Only use this rule when none of the previous rules apply.
        Note 2:	Abstract both the invasive and in situ tumors.
        Note 3:	Abstract as multiple primaries even if physician states the invasive tumor is disease recurrence or progression.
        Note 4:	This rule is based on long-term epidemiologic studies of recurrence intervals. The specialty medical experts (SMEs) reviewed and approved these rules.  Many of the SMEs were also authors, co-authors, or editors of the AJCC Staging Manual.

    Rule M18	Abstract a single primary when tumors do not meet any of the above criteria.
        Note:	Use this rule as a last resort.  Please confirm that you have not overlooked an applicable rule.
        Example:	TURB shows invasive urothelial carcinoma 8120/3 and CIS/in situ urothelial carcinoma 8120/2. Abstract a single primary.

    */


    // Renal Pelvis, Ureter, Bladder, and Other Urinary Multiple Primary Rules
    // C659, C669, C670-C679, C680-C689
    // (Excludes lymphoma and leukemia M9590 – M9992 and Kaposi sarcoma M9140)
    public Mp2018UrinarySitesGroup() {
        super(MphConstants.MP_2018_URINARY_GROUP_ID, MphConstants.MP_2018_URINARY_GROUP_NAME, "C659, C669, C670-C679, C680-C689", null, null,
                "9590-9992, 9140","2-3,6", "2018-9999");

        // Rule M3	Abstract multiple primaries when there are:
        // •	Separate/non-contiguous tumors in both the right AND left renal pelvis AND
        // •	No other urinary sites are involved with separate/non-contiguous tumors
        MphRule rule = new MphRuleLeftAndRight(MphConstants.MP_2018_URINARY_GROUP_ID, "M3", null, MphConstants.RENAL_PELVIS);
        rule.setQuestion("Are there separate/non-contiguous tumors in both the right renal pelvis and the left renal pelvis and no other urinary sites are involved with separate/non-contiguous tumors?");
        rule.setReason("When no other urinary sites are involved with separate/non-contiguous tumors, and separate/non-contiguous tumors in in the right renal pelvis AND tumor(s) in the left renal pelvis are multiple primaries.");
        rule.getNotes().add("Only abstract a single primary when pathology confirms tumor(s) in the contralateral renal pelvis are metastatic.");
        rule.getNotes().add("This rule is used only when there is no involvement by separate/non-contiguous tumors in the ureter(s), bladder, or urethra.");
        _rules.add(rule);

        // Rule M4	Abstract multiple primaries when there are:
        // •	Separate/non-contiguous tumors in the right AND left ureter AND
        // •	No other urinary sites are involved with separate/non-contiguous tumors
        rule = new MphRuleLeftAndRight(MphConstants.MP_2018_URINARY_GROUP_ID, "M4", null, MphConstants.URETER);
        rule.setQuestion("Are there separate/non-contiguous tumors in both the right ureter and the left ureter and no other urinary sites are involved with separate/non-contiguous tumors?");
        rule.setReason("When no other urinary sites are involved with separate/non-contiguous tumors, and there are separate/non-contiguous tumors in both the right ureter AND tumor(s) in the left ureter are multiple primaries.");
        rule.getNotes().add("Only abstract a single primary when pathology confirms tumor(s) in contralateral ureter are metastatic.");
        rule.getNotes().add("This rule is used only when there is no involvement by separate/non-contiguous tumors in the renal pelvis, bladder, and urethra.");
        _rules.add(rule);

        // Rule M5	Abstract a single primary when synchronous or simultaneous tumors are noninvasive in situ /2 urothelial carcinoma (flat tumor) 8120/2 in the following sites:
        // • Bladder C67_ AND
        // • One or both ureter(s) C669
        rule = new MphRule(MphConstants.MP_2018_URINARY_GROUP_ID, "M5") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if ((GroupUtility.isSiteContained("C670-C679", i1.getPrimarySite()) && i2.getPrimarySite().equals("C669")) ||
                    (GroupUtility.isSiteContained("C670-C679", i2.getPrimarySite()) && i1.getPrimarySite().equals("C669"))) {
                    if (GroupUtility.areSimultaneousTumors(i1, i2)) {
                        String icd1 = i1.getHistology() + "/" + i1.getBehavior(), icd2 = i2.getHistology() + "/" + i2.getBehavior();
                        if (icd1.equals("8120/2") && icd2.equals("8120/2"))   {
                            result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        }
                    }
                }
                return result;
            }
        };
        rule.setQuestion("Are tumors of the bladder (C670-C679) and ureter (C669) in situ utothelial carcinoma (8120/2)?");
        rule.setReason("Tumors of the bladder (C670-C679) and ureter (C669) and are in situ utothelial carcinoma (8120/2) are a single primary.");
        rule.getNotes().add("No other urinary organs are involved.");
        rule.getNotes().add("Use this rule ONLY for noninvasive in situ urothelial carcinoma (may be called noninvasive urothelial carcinoma or noninvasive flat tumor). For other histologies, continue through the rules.");
        rule.getNotes().add("Urothelial carcinoma in situ spreads by intramucosal extension and may involve large areas of mucosal surface.  The default for these cases is coding a bladder primary.");
        _rules.add(rule);

        // Rule M6	Abstract a single primary when the patient has multiple occurrences of noninvasive in situ /2 urothelial carcinoma (flat tumor) 8120/2 tumors in the bladder.  Original tumor and subsequent tumor are 8120/2.
        rule = new MphRule(MphConstants.MP_2018_URINARY_GROUP_ID, "M6") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (i1.getPrimarySite().startsWith(MphConstants.BLADDER) && i2.getPrimarySite().startsWith(MphConstants.BLADDER)) {
                    String icd1 = i1.getHistology() + "/" + i1.getBehavior(), icd2 = i2.getHistology() + "/" + i2.getBehavior();
                    if (icd1.equals("8120/2") && icd2.equals("8120/2")) {
                        result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                    }
                }
                return result;
            }
        };
        rule.setQuestion("Are there multiple occurrences of noninvasive in situ /2 urothelial carcinoma (flat tumor) 8120/2 tumors in the bladder?");
        rule.setReason("Multiple occurrences of noninvasive in situ /2 urothelial carcinoma (flat tumor) 8120/2 tumors in the bladder is a single primary.");
        rule.getNotes().add("Timing is irrelevant.");
        rule.getNotes().add("Abstract only one in situ urothelial bladder tumor per the patient’s lifetime.");
        _rules.add(rule);

        // Rule M7	Abstract a single primary when the patient has multiple occurrences of invasive tumors in the bladder.  Original tumor and subsequent tumor are either:
        // •	Papillary urothelial carcinoma and a recurrence of papillary urothelial carcinoma 8130/3 OR
        // •	Urothelial carcinoma and a recurrence of urothelial carcinoma 8120/3
        rule = new MphRule(MphConstants.MP_2018_URINARY_GROUP_ID, "M7") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (i1.getPrimarySite().startsWith(MphConstants.BLADDER) && i2.getPrimarySite().startsWith(MphConstants.BLADDER)) {
                    String icd1 = i1.getHistology() + "/" + i1.getBehavior(), icd2 = i2.getHistology() + "/" + i2.getBehavior();
                    if ((icd1.equals("8130/3") && icd2.equals("8130/3")) || (icd1.equals("8120/3") && icd2.equals("8120/3"))) {
                        result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                    }
                }
                return result;
            }
        };
        rule.setQuestion("Are there multiple occurrences of papillary urothelial carcinoma or urothelial carcinoma of the bladder?");
        rule.setReason("Multiple occurrences of papillary urothelial carcinoma or urothelial carcinoma of the bladder is a single primary.");
        rule.getNotes().add("Timing is irrelevant.");
        rule.getNotes().add("This rule does not include subtypes/variants of 8120/3 or 8130/3.");
        rule.getNotes().add("Abstract only one invasive urothelial bladder tumor per the patient’s lifetime.");
        rule.getNotes().add("The rules are hierarchical. Only use this rule when previous rules do not apply.");
        _rules.add(rule);

        // Rule M8	Abstract a single primary when there are synchronous urothelial carcinomas in multiple urinary organs.
        rule = new MphRule(MphConstants.MP_2018_URINARY_GROUP_ID, "M8") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                String hist1 = i1.getHistology();
                String hist2 = i2.getHistology();
                if (hist1 != null && hist2 != null) {
                    if (hist1.equals(hist2) && (MphConstants.URINARY_2018_UROTHELIAL_CARCINOMAS.contains(hist1))) {
                        result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                    }
                }
                return result;
            }
        };
        rule.setQuestion("Are there synchronous urothelial carcinomas in multiple urinary organs?");
        rule.setReason("Synchronous urothelial carcinomas in multiple urinary organs is a single primary.");
        rule.getNotes().add("This rule is ONLY for urothelial carcinoma 8120 and all subtypes/variants of urothelial carcinoma. This rule does not apply to any other carcinomas or sarcomas.");
        rule.getNotes().add("The histology for all tumors must be identical. The behavior is irrelevant.");
        rule.getNotes().add("This rule applies to multifocal/multicentric carcinoma which involves two or more of the following urinary sites:");
        rule.getNotes().add(" - Renal pelvis");
        rule.getNotes().add(" - Ureter");
        rule.getNotes().add(" - Bladder");
        rule.getNotes().add(" - Urethra");
        _rules.add(rule);

        // Rule M9	Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than three years after the original diagnosis or last recurrence.
        rule = new MphRuleDiagnosisDateGreaterThan3Years(MphConstants.MP_2018_URINARY_GROUP_ID, "M9");
        rule.getNotes().add("The rules are hierarchical.  This rule does not apply to urothelial carcinoma of the bladder.");
        rule.getNotes().add("Clinically disease-free means that there was no evidence of recurrence on follow-up.");
        rule.getNotes().add("  • Scans are NED");
        rule.getNotes().add("  • Urine cytology is NED");
        rule.getNotes().add("  • Scopes are NED");
        rule.getNotes().add("When there is a recurrence within three years of diagnosis, the “clock” starts over. The time interval is calculated from the date of last recurrence.");
        rule.getNotes().add("When it is unknown/not documented whether the patient had a recurrence, default to date of diagnosis to compute the time interval.");
        rule.getNotes().add("The physician may state this is a recurrence, meaning the patient had a previous urinary site tumor and now has another urinary site tumor. Follow the rules; do not attempt to interpret the physician’s statement.");
        rule.getExamples().add("Patient is diagnosed with multifocal/multicentric urothelial carcinomas in the ureter and renal pelvis in January 2018. Both the kidney and ureter are surgically removed. In June 2022 the patient presents with tumor in the contralateral ureter. The physician states this is a recurrence of the original urothelial carcinoma. Code a new primary for the 2022 ureter carcinoma.");
        _rules.add(rule);

        // Rule M10	Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3 of Table 2 in the Equivalent Terms and Definitions. Timing is irrelevant.
        rule = new MphRuleTwoOrMoreDifferentSubTypesInTable(MphConstants.MP_2018_URINARY_GROUP_ID, "M10", MphConstants.URINARY_2018_TABLE2_SUBTYPES, MphConstants.URINARY_2018_SUBTYPE_NOS, false);
        rule.setQuestion("Are separate/non-contiguous tumors two or more different subtypes/variants in Column 3, Table 2 in the Equivalent Terms and Definitions?");
        rule.setReason("Separate/non-contiguous tumors that are two or more different subtypes/variants in Column 3, Table 2 in the Equivalent Terms and Definitions, are multiple primaries.");
        rule.getNotes().add("The tumors may be subtypes/variants of the same or different NOS histologies.");
        rule.getNotes().add("  • Same NOS: Leiomyosarcoma 8890/3 and liposarcoma 8850/3 are both subtypes of sarcoma NOS 8800/3 but are distinctly different histologies. Abstract multiple primaries.");
        rule.getNotes().add("  •	 Different NOS: Verrucous carcinoma 8051 is a subtype of squamous cell carcinoma NOS 8070; giant cell urothelial carcinoma 8031 is a subtype of urothelial carcinoma 8120. They are distinctly different histologies. Abstract multiple primaries.");
        _rules.add(rule);

        // Rule M11	Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 2 in the Equivalent Terms and Definitions. Timing is irrelevant.
        rule = new MphRuleDifferentRowsInTable(MphConstants.MP_2018_URINARY_GROUP_ID, "M11", MphConstants.URINARY_2018_TABLE2_ROWS, false);
        rule.setQuestion("Are separate/non-contiguous tumors on different rows in Table 2 in the Equivalent Terms and Definitions?");
        rule.setReason("Separate/non-contiguous tumors that are on different rows in Table 2 in the Equivalent Terms and Definitions, are multiple primaries.");
        rule.getNotes().add("Each row in the table is a distinctly different histology.");
        rule.getExamples().add("Small cell neuroendocrine carcinoma 8041 and urothelial carcinoma 8120 are on different rows of Table 2. Abstract two primaries, one for the small cell neuroendocrine carcinoma and a second for the urothelial carcinoma.");
        _rules.add(rule);

        // Rule M12	Abstract multiple primaries when the patient has multiple non-synchronous tumors which are:
        // •	Papillary urothelial/transitional cell NOS 8130/3 AND
        // •	Micropapillary urothelial/transitional cell 8131/3
        rule = new MphRule(MphConstants.MP_2018_URINARY_GROUP_ID, "M12") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                int sixtyDaysApart = GroupUtility.verifyDaysApart(i1, i2, 60);
                if (sixtyDaysApart == 1) {
                    String icd1 = i1.getHistology() + "/" + i1.getBehavior(), icd2 = i2.getHistology() + "/" + i2.getBehavior();
                    if ((icd1.equals("8130/3") && icd2.equals("8131/3")) || (icd1.equals("8131/3") && icd2.equals("8130/3"))) {
                        result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    }
                }
                return result;
            }
        };
        rule.setQuestion("Are tumors non-synchronous Papillary urothelial/transitional cell NOS 8130/3 AND Micropapillary urothelial/transitional cell 8131/3?");
        rule.setReason("Non-synchronous Papillary urothelial/transitional cell NOS 8130/3 AND Micropapillary urothelial/transitional cell 8131/3 are multiple primaries.");
        rule.getNotes().add("This is a new rule for 2019.");
        rule.getNotes().add("Micropapillary urothelial cell carcinoma is an extremely aggressive neoplasm.  It is important to capture the incidence of micropapillary urothelial carcinoma, therefore it is excluded from the typical “NOS and subtype/variant” rule (same row in table 2).");
        rule.getNotes().add("For synchronous tumors, continue through the rules.  Code the most specific histology.");
        _rules.add(rule);

        // Rule M13	Abstract multiple primaries when the original tumor and subsequent tumor occur in different urinary sites.
        rule = new MphRule(MphConstants.MP_2018_URINARY_GROUP_ID, "M13") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (!i1.getPrimarySite().equals(i2.getPrimarySite())) {
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                }
                return result;
            }
        };
        rule.setQuestion("Do the original tumor and subsequent tumor occur in different urinary sites?");
        rule.setReason("An original tumor and a subsequent tumor that occur in different urinary sites are multiple primaries.");
        rule.getNotes().add("These tumors are not synchronous. Treatment has been given for the first tumor prior to the occurrence of the second tumor.");
        rule.getNotes().add("Histology and behavior are irrelevant.  These tumors are always multiple primaries.");
        rule.getNotes().add("The original tumor occurs in one of the following urinary sites; the second tumor occurs in a different urinary site:");
        rule.getNotes().add(" - Renal pelvis (original tumor was not in renal pelvis)");
        rule.getNotes().add(" - Ureter (original tumor was not in ureter)");
        rule.getNotes().add(" - Bladder (original tumor was not in bladder)");
        rule.getNotes().add(" - Urethra (original tumor was not in urethra)");
        rule.getExamples().add("The patient was diagnosed 1/1/2018 with squamous cell carcinoma of the renal pelvis 8070/3.  Patient had a nephrectomy.  On routine follow-up six months later, the patient was diagnosed with urothelial carcinoma of the bladder 8120/3.  The patient has two non-synchronous tumors involving different urinary organs. Abstract multiple primaries.");
        _rules.add(rule);

        // Rule M14 Abstract a single primary when synchronous, separate/non-contiguous tumors are on the same row in Table 2 in the Equivalent Terms and Definitions.
        rule = new MphRuleSameRowInTable(MphConstants.MP_2018_URINARY_GROUP_ID, "M14", MphConstants.URINARY_2018_TABLE2_ROWS, MphConstants.URINARY_2018_SUBTYPE_NOS, true, false, true);
        rule.setQuestion("Are synchronous, separate/non-contiguous tumors on the same row in Table 2 in the Equivalent Terms and Definitions?");
        rule.setReason("Synchronous, separate/non-contiguous tumors that are on the same row in Table 2 in the Equivalent Terms and Definitions, are multiple primaries.");
        rule.getNotes().add("The tumors must be the same behavior.  When one tumor is in situ and the other invasive, continue through the rules.");
        rule.getNotes().add("The same row means the tumors are:");
        rule.getNotes().add("  • The same histology (same four-digit ICD-O code) OR");
        rule.getNotes().add("  • One is the preferred term (column 1) and the other is a synonym for the preferred term (column 2) OR");
        rule.getNotes().add("  • A NOS (column 1/column 2) and the other is a subtype/variant of that NOS (column 3)");
        rule.getNotes().add("The multiple tumors may:");
        rule.getNotes().add("  • Occur in the same urinary site OR");
        rule.getNotes().add("  • Be multifocal/multicentric occurring in at least two of the following urinary sites:");
        rule.getNotes().add("     Renal pelvis C659");
        rule.getNotes().add("     Ureter C669");
        rule.getNotes().add("     Bladder C670-C679");
        rule.getNotes().add("     Urethra/prostatic urethra C680");
        rule.getNotes().add("A previous rule specifically lists noninvasive urothelial carcinoma of bladder and ureter as a single primary.");
        _rules.add(rule);

        // Rule M15	Abstract a single primary (the invasive) when an in situ tumor is diagnosed after an invasive tumor AND tumors:
        // •	Occur in the same urinary site OR
        // •	The original tumors are multifocal/multicentric and occur in multiple urinary sites; subsequent tumor(s) are in at least one of the previously involved urinary sites
        rule = new MphRuleInSituAfterInvasive(MphConstants.MP_2018_URINARY_GROUP_ID, "M15", false, false);
        rule.setQuestion("Is there an in situ tumor following an invasive tumor?");
        rule.setReason("An in situ tumor following an invasive tumor is a single primary.");
        rule.getNotes().add("The rules are hierarchical. Only use this rule when previous rules do not apply.");
        rule.getNotes().add("The tumors may be a NOS and a subtype/variant of that NOS. See Table 2 in the Equivalent Terms and Definitions for listings of NOS and subtype/variants.");
        rule.getNotes().add("Once the patient has an invasive tumor, the subsequent in situ is recorded as a recurrence for those registrars who collect recurrence data.");
        _rules.add(rule);

        // Rule M16	Abstract a single primary (the invasive) when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor AND tumors:
        // •	Occur in the same urinary site OR
        // •	Original tumor is multifocal/multicentric and involves multiple urinary sites; the subsequent invasive tumor(s) occur in at least one of the previously involved urinary sites
        rule = new MphRuleInvasiveAfterInSituLess60Days(MphConstants.MP_2018_URINARY_GROUP_ID, "M16", false, false);
        rule.getExamples().add("The first presentation was multifocal/multicentric in situ tumors in multiple urinary organs; the subsequent presentation was invasive tumor in at least one of the previously involved urinary organs.");
        rule.getNotes().add("The rules are hierarchical. Only use this rule if none of the previous rules apply.");
        rule.getNotes().add("The tumors may be an NOS and a subtype/variant of that NOS.");
        rule.getNotes().add("When the case has been abstracted, change behavior code on original abstract from /2 to /3. Do not change date of diagnosis.");
        rule.getNotes().add("If the case has already been submitted to the central registry, report all changes.");
        rule.getNotes().add("The physician may stage both tumors because staging and determining multiple primaries are done for different reasons. Staging determines which treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).");
        rule.getNotes().add("See the COC and SEER manuals for instructions on coding other data items such as Date of Diagnosis, Accession Year and Sequence Number");
        _rules.add(rule);

        // Rule M17	Abstract multiple primaries when an invasive tumor occurs more than 60 days after an in situ tumor AND tumors:
        // •	Occur in the same urinary site OR
        // •	Are multifocal/multicentric tumors in multiple urinary sites
        rule = new MphRuleInvasiveAfterInSituGreaterThan60Days(MphConstants.MP_2018_URINARY_GROUP_ID, "M17", false);
        rule.getExamples().add("The first presentation was multifocal/multicentric in situ tumors in multiple urinary organs; the subsequent presentation was invasive tumor in at least one of the previously involved urinary organs.");
        rule.getNotes().add("The rules are hierarchical. Only use this rule when none of the previous rules apply.");
        rule.getNotes().add("Abstract both the invasive and in situ tumors.");
        rule.getNotes().add("Abstract as multiple primaries even if physician states the invasive tumor is disease recurrence or progression.");
        rule.getNotes().add("This rule is based on long-term epidemiologic studies of recurrence intervals. The specialty medical experts (SMEs) reviewed and approved these rules.  Many of the SMEs were also authors, co-authors, or editors of the AJCC Staging Manual.");
        _rules.add(rule);

        // Rule M18	Abstract a single primary when tumors do not meet any of the above criteria.
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_URINARY_GROUP_ID, "M18");
        rule.getNotes().add("Use this rule as a last resort.  Please confirm that you have not overlooked an applicable rule.");
        rule.getExamples().add("TURB shows invasive urothelial carcinoma 8120/3 and CIS/in situ urothelial carcinoma 8120/2. Abstract a single primary.");

        _rules.add(rule);
    }
}

