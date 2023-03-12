/*
 * Copyright (C) 2018 Information Management Services, Inc.
 */
package com.imsweb.mph.mpgroups;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import com.imsweb.mph.MphConstants;
import com.imsweb.mph.MphGroup;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphUtils;
import com.imsweb.mph.MphUtils.MpResult;
import com.imsweb.mph.internal.TempRuleResult;
import com.imsweb.mph.mprules.MpRuleInsituAfterInvasive;
import com.imsweb.mph.mprules.MpRuleInvasiveAfterInsituGreaterThan60Days;
import com.imsweb.mph.mprules.MpRuleInvasiveAfterInsituLessThan60Days;
import com.imsweb.mph.mprules.MpRuleNoCriteriaSatisfied;
import com.imsweb.mph.mprules.MpRulePrimarySite;

public class Mp2018UrinarySitesGroup extends MphGroup {

    // Renal Pelvis, Ureter, Bladder, and Other Urinary Multiple Primary Rules
    // C659, C669, C670-C679, C680-C689
    // (Excludes lymphoma and leukemia M9590 – M9992 and Kaposi sarcoma M9140)
    public Mp2018UrinarySitesGroup() {
        super(MphConstants.MP_2018_URINARY_GROUP_ID, MphConstants.MP_2018_URINARY_GROUP_NAME, "C659, C669, C670-C679, C680-C689", null, null,
                "9590-9992, 9140", "2-3,6", "2018-9999");

        // Rule M3 Abstract multiple primaries when there are:
        // - Separate/non-contiguous tumors in both the right AND left renal pelvis AND
        // - No other urinary sites are involved with separate/non-contiguous tumors
        MphRule rule = new MphRule(MphConstants.MP_2018_URINARY_GROUP_ID, "M3") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                if (MphConstants.RENAL_PELVIS.equals(i1.getPrimarySite()) && MphConstants.RENAL_PELVIS.equals(i2.getPrimarySite())) {
                    if (!GroupUtility.validPairedSiteLaterality(i1.getLaterality(), i2.getLaterality())) {
                        result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                        result.setMessageUnknownLaterality(this.getStep(), this.getGroupId());
                    }
                    else if (GroupUtility.areOppositeSides(i1.getLaterality(), i2.getLaterality()))
                        result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                }
                return result;
            }
        };
        rule.setQuestion(
                "Are there separate/non-contiguous tumors in both the right renal pelvis and the left renal pelvis and no other urinary sites are involved with separate/non-contiguous tumors?");
        rule.setReason(
                "When no other urinary sites are involved with separate/non-contiguous tumors, and separate/non-contiguous tumors in in the right renal pelvis AND tumor(s) in the left renal pelvis are multiple primaries.");
        rule.getNotes().add("Only abstract a single primary when pathology confirms tumor(s) in the contralateral renal pelvis are metastatic.");
        rule.getNotes().add("This rule is used only when there is no involvement by separate/non-contiguous tumors in the ureter(s), bladder, or urethra.");
        _rules.add(rule);

        // Rule M4 Abstract multiple primaries when there are:
        // - Separate/non-contiguous tumors in the right AND left ureter AND
        // - No other urinary sites are involved with separate/non-contiguous tumors
        rule = new MphRule(MphConstants.MP_2018_URINARY_GROUP_ID, "M4") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                if (MphConstants.URETER.equals(i1.getPrimarySite()) && MphConstants.URETER.equals(i2.getPrimarySite())) {
                    if (!GroupUtility.validPairedSiteLaterality(i1.getLaterality(), i2.getLaterality())) {
                        result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                        result.setMessageUnknownLaterality(this.getStep(), this.getGroupId());
                    }
                    else if (GroupUtility.areOppositeSides(i1.getLaterality(), i2.getLaterality()))
                        result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                }
                return result;
            }
        };
        rule.setQuestion("Are there separate/non-contiguous tumors in both the right ureter and the left ureter and no other urinary sites are involved with separate/non-contiguous tumors?");
        rule.setReason(
                "When no other urinary sites are involved with separate/non-contiguous tumors, and there are separate/non-contiguous tumors in both the right ureter AND tumor(s) in the left ureter are multiple primaries.");
        rule.getNotes().add("Only abstract a single primary when pathology confirms tumor(s) in contralateral ureter are metastatic.");
        rule.getNotes().add("This rule is used only when there is no involvement by separate/non-contiguous tumors in the renal pelvis, bladder, and urethra.");
        _rules.add(rule);

        // Rule M5 Abstract a single primary when synchronous or simultaneous tumors are noninvasive in situ /2 urothelial carcinoma (flat tumor) 8120/2 in the following sites:
        // - Bladder C67_ AND
        // - One or both ureter(s) C669
        rule = new MphRule(MphConstants.MP_2018_URINARY_GROUP_ID, "M5") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                String icd1 = i1.getHistology() + "/" + i1.getBehavior(), icd2 = i2.getHistology() + "/" + i2.getBehavior();
                String s1 = i1.getPrimarySite(), s2 = i2.getPrimarySite();
                if ("8120/2".equals(icd1) && "8120/2".equals(icd2) && ((s1.startsWith(MphConstants.BLADDER) && "C669".equals(s2)) || (s2.startsWith(MphConstants.BLADDER) && "C669".equals(s1)))) {
                    int sixtyDaysApart = GroupUtility.verifyDaysApart(i1, i2, 60);
                    if (MphConstants.DATE_VERIFY_UNKNOWN == sixtyDaysApart) {
                        result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupId());
                    }
                    else if (MphConstants.DATE_VERIFY_WITHIN == sixtyDaysApart)
                        result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);

                }
                return result;
            }
        };
        rule.setQuestion("Are tumors of the bladder (C670-C679) and ureter (C669) in situ urothelial carcinoma (8120/2)?");
        rule.setReason("Tumors of the bladder (C670-C679) and ureter (C669) and are in situ urothelial carcinoma (8120/2) are a single primary.");
        rule.getNotes().add("No other urinary organs are involved.");
        rule.getNotes().add(
                "Use this rule ONLY for noninvasive in situ urothelial carcinoma (may be called noninvasive urothelial carcinoma or noninvasive flat tumor). For other histologies, continue through the rules.");
        rule.getNotes().add("Urothelial carcinoma in situ spreads by intramucosal extension and may involve large areas of mucosal surface.  The default for these cases is coding a bladder primary.");
        _rules.add(rule);

        // Rule M6 Abstract multiple primariesii when an invasive tumor occurs more than 60 days after an in situ tumor.
        rule = new MpRuleInvasiveAfterInsituGreaterThan60Days(MphConstants.MP_2018_URINARY_GROUP_ID, "M6");
        rule.getNotes().add("Abstract both the invasive and in situ tumors.");
        rule.getNotes().add("Abstract as multiple primaries even if physician states the invasive tumor is disease recurrence or progression.");
        rule.getNotes().add(
                "This rule is based on long-term epidemiologic studies of recurrence intervals. The specialty medical experts (SMEs) reviewed and approved these rules.  Many of the SMEs were also authors, co-authors, or editors of the AJCC Staging Manual.");
        _rules.add(rule);

        // Rule M7 Abstract a single primary when the patient has multiple occurrences of /2 urothelial carcinoma in the bladder. Tumors may be any combination of:
        //        - In situ urothelial carcinoma 8120/2 AND/OR
        //        - Papillary urothelial carcinoma noninvasive 8130/2 (does not include micropapillary subtype)
        rule = new MphRule(MphConstants.MP_2018_URINARY_GROUP_ID, "M7") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                if (MphConstants.INSITU.equals(i1.getBehavior()) && MphConstants.INSITU.equals(i2.getBehavior()) && i1.getPrimarySite().startsWith(MphConstants.BLADDER) && i2.getPrimarySite()
                        .startsWith(MphConstants.BLADDER) && MphConstants.URINARY_2018_UROTHELIAL_CARCINOMAS_EXCLUDE_MICROPAPILLARY.containsAll(Arrays.asList(i1.getHistology(), i2.getHistology())))
                    result.setFinalResult(MpResult.SINGLE_PRIMARY);
                return result;
            }
        };
        rule.setQuestion(
                "Are there multiple occurrences of noninvasive in situ /2 urothelial carcinoma (flat tumor) 8120/2 and/or Papillary urothelial carcinoma noninvasive 8130/2 tumors in the bladder?");
        rule.setReason(
                "Multiple occurrences of noninvasive in situ /2 urothelial carcinoma (flat tumor) 8120/2 and/or Papillary urothelial carcinoma noninvasive 8130/2 tumors in the bladder is a single primary.");
        rule.getNotes().add("Timing is irrelevant.");
        rule.getNotes().add("Abstract only one in situ urothelial bladder tumor per the patient’s lifetime.");
        _rules.add(rule);

        //Rule M8 Abstract multiple primaries when the patient has micropapillary urothelial carcinoma 8131/3 of the bladder AND a
        //urothelial carcinoma 8120/3 (including papillary 8130/3) of the bladder.
        rule = new MphRule(MphConstants.MP_2018_URINARY_GROUP_ID, "M8") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                if (i1.getPrimarySite().startsWith(MphConstants.BLADDER) && i2.getPrimarySite().startsWith(MphConstants.BLADDER) && GroupUtility.differentCategory(i1.getHistology(), i2.getHistology(),
                        Collections.singletonList("8131"), MphConstants.URINARY_2018_UROTHELIAL_CARCINOMAS_EXCLUDE_MICROPAPILLARY))
                    result.setFinalResult(MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Are there micropapillary urothelial carcinoma 8131/3 of the bladder AND a urothelial carcinoma 8120/3 (including papillary 8130/3) of the bladder?");
        rule.setReason("Micropapillary urothelial carcinoma 8131/3 of the bladder AND a urothelial carcinoma 8120/3 (including papillary 8130/3) of the bladder are multiple primaries.");
        rule.getNotes().add("This is a new rule for 2018.");
        rule.getNotes().add(
                "Micropapillary urothelial cell carcinoma is an extremely aggressive neoplasm. It is important to abstract a new primary to capture the incidence of micropapillary urothelial carcinoma. Micropapillary is excluded from the typical \"NOS and subtype/variant\" rule (same row in Table 2).");
        _rules.add(rule);

        // Rule M9 Abstract a single primary when the patient has multiple invasive urothelial cell carcinomas in the bladder. All tumors are either:
        // - Multiple occurrences of urothelial or urothelial subtypes (with exception of micropapillary) OR
        // - Multiple occurrences of micropapillary
        rule = new MphRule(MphConstants.MP_2018_URINARY_GROUP_ID, "M9") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                String h1 = i1.getHistology(), h2 = i2.getHistology();
                if (MphConstants.MALIGNANT.equals(i1.getBehavior()) && MphConstants.MALIGNANT.equals(i2.getBehavior()) && i1.getPrimarySite().startsWith(MphConstants.BLADDER) && i2.getPrimarySite()
                        .startsWith(MphConstants.BLADDER) && MphConstants.URINARY_2018_UROTHELIAL_CARCINOMAS.containsAll(Arrays.asList(h1, h2)))
                    result.setFinalResult(MpResult.SINGLE_PRIMARY);
                return result;
            }
        };
        rule.setQuestion("Are there multiple invasive urothelial cell carcinomas in the bladder?");
        rule.setReason("Multiple invasive urothelial cell carcinomas in the bladder is a single primary.");
        rule.getNotes().add("Timing is irrelevant. Tumors may be synchronous or non-synchronous.");
        rule.getNotes().add("Abstract only one /3 invasive urothelial bladder primary AND only one micropapillary urothelial 8131/3 bladder primaryper the patient’s lifetime.");
        rule.getNotes().add("An occurrence of micropapillary and an occurrence of urothelial carcinoma would be multiple primaries (see previous rules).");
        _rules.add(rule);

        // Rule M10 Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than three years after the original diagnosis or last recurrence.
        rule = new MphRule(MphConstants.MP_2018_URINARY_GROUP_ID, "M10") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                if (!new HashSet<>(MphConstants.URINARY_2018_UROTHELIAL_CARCINOMAS_EXCLUDE_MICROPAPILLARY).containsAll(Arrays.asList(i1.getHistology(), i2.getHistology()))) {
                    int diff = GroupUtility.verifyYearsApart(i1, i2, 3);
                    if (MphConstants.DATE_VERIFY_UNKNOWN == diff) {
                        result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                        result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupId());
                    }
                    else if (MphConstants.DATE_VERIFY_APART == diff)
                        result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                }

                return result;
            }
        };
        rule.setQuestion("Are there tumors diagnosed greater than three (3) years apart?");
        rule.setReason("Tumors diagnosed greater than three (3) years apart are multiple primaries.");
        rule.getNotes().add("This rule does not apply when both/all tumors are urothelial carcinoma of the bladder.");
        rule.getNotes().add("Clinically disease-free means that there was no evidence of recurrence on follow-up.");
        rule.getNotes().add("  - Scans are NED");
        rule.getNotes().add("  - Urine cytology is NED");
        rule.getNotes().add("  - Scopes are NED");
        rule.getNotes().add("When there is a recurrence within three years of diagnosis, the \"clock\" starts over. The time interval is calculated from the date of last recurrence.");
        rule.getNotes().add("When it is unknown/not documented whether the patient had a recurrence, default to date of diagnosis to compute the time interval.");
        rule.getNotes().add(
                "The physician may state this is a recurrence, meaning the patient had a previous urinary site tumor and now has another urinary site tumor. Follow the rules; do not attempt to interpret the physician’s statement.");
        rule.getExamples().add(
                "Patient is diagnosed with multifocal/multicentric urothelial carcinomas in the ureter and renal pelvis in January 2018. Both the kidney and ureter are surgically removed. In June 2022 the patient presents with tumor in the contralateral ureter. The physician states this is a recurrence of the original urothelial carcinoma. Code a new primary for the 2022 ureter carcinoma.");
        rule.setReason("Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than three years after the original diagnosis or last recurrence. This rule does not apply when both/all tumors are urothelial carcinoma of the bladder.");
        _rules.add(rule);

        // Rule M11 Abstract a single primary when there are urothelial carcinomas in multiple urinary organs.
        rule = new MphRule(MphConstants.MP_2018_URINARY_GROUP_ID, "M11") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                String h1 = i1.getHistology(), h2 = i2.getHistology();
                if (areTwoDifferentUrinarySites(i1.getPrimarySite(), i2.getPrimarySite()) && MphConstants.URINARY_2018_UROTHELIAL_CARCINOMAS.contains(h1)
                        && MphConstants.URINARY_2018_UROTHELIAL_CARCINOMAS.contains(h2))
                    result.setFinalResult(MpResult.SINGLE_PRIMARY);
                return result;
            }
        };
        rule.setQuestion("Are there urothelial carcinomas in multiple urinary organs?");
        rule.setReason("Urothelial carcinomas in multiple urinary organs is a single primary.");
        rule.getNotes().add("This rule is ONLY for urothelial carcinoma 8120 and all subtypes/variants of urothelial carcinoma. This rule does not apply to any other carcinomas or sarcomas.");
        rule.getNotes().add("The behavior is irrelevant.");
        rule.getNotes().add("This rule applies to multifocal/multicentric carcinoma which involves two or more of the following urinary sites:");
        rule.getNotes().add(" - Renal pelvis");
        rule.getNotes().add(" - Ureter");
        rule.getNotes().add(" - Bladder");
        rule.getNotes().add(" - Urethra");
        _rules.add(rule);

        // Rule M12 Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3 of Table 2 in the Equivalent Terms and Definitions. Timing is irrelevant.
        rule = new MphRule(MphConstants.MP_2018_URINARY_GROUP_ID, "M12") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                String h1 = i1.getHistology(), icd1 = h1 + "/" + i1.getBehavior(), h2 = i2.getHistology(), icd2 = h2 + "/" + i2.getBehavior();
                String subtype1 = MphConstants.URINARY_2018_TABLE2_SUBTYPES.containsKey(h1) ? MphConstants.URINARY_2018_TABLE2_SUBTYPES.get(h1) : MphConstants.URINARY_2018_TABLE2_SUBTYPES.get(icd1);
                String subtype2 = MphConstants.URINARY_2018_TABLE2_SUBTYPES.containsKey(h2) ? MphConstants.URINARY_2018_TABLE2_SUBTYPES.get(h2) : MphConstants.URINARY_2018_TABLE2_SUBTYPES.get(icd2);
                if (subtype1 != null && subtype2 != null && !subtype1.equals(subtype2))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Are separate/non-contiguous tumors two or more different subtypes/variants in Column 3, Table 2 in the Equivalent Terms and Definitions?");
        rule.setReason("Separate/non-contiguous tumors that are two or more different subtypes/variants in Column 3, Table 2 in the Equivalent Terms and Definitions, are multiple primaries.");
        rule.getNotes().add("The tumors may be subtypes/variants of the same or different NOS histologies.");
        rule.getNotes().add(
                "  - Same NOS: Leiomyosarcoma 8890/3 and liposarcoma 8850/3 are both subtypes of sarcoma NOS 8800/3 but are distinctly different histologies. Abstract multiple primaries.");
        rule.getNotes().add(
                "  -  Different NOS: Verrucous carcinoma 8051 is a subtype of squamous cell carcinoma NOS 8070; giant cell urothelial carcinoma 8031 is a subtype of urothelial carcinoma 8120. They are distinctly different histologies. Abstract multiple primaries.");
        _rules.add(rule);

        // Rule M13 Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 2 in the Equivalent Terms and Definitions. Timing is irrelevant.
        rule = new MphRule(MphConstants.MP_2018_URINARY_GROUP_ID, "M13") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                String h1 = i1.getHistology(), icd1 = h1 + "/" + i1.getBehavior(), h2 = i2.getHistology(), icd2 = h2 + "/" + i2.getBehavior();
                String row1 = MphConstants.URINARY_2018_TABLE2_ROWS.containsKey(h1) ? MphConstants.URINARY_2018_TABLE2_ROWS.get(h1) : MphConstants.URINARY_2018_TABLE2_ROWS.get(icd1);
                String row2 = MphConstants.URINARY_2018_TABLE2_ROWS.containsKey(h2) ? MphConstants.URINARY_2018_TABLE2_ROWS.get(h2) : MphConstants.URINARY_2018_TABLE2_ROWS.get(icd2);
                if (row1 == null || row2 == null) {
                    result.setFinalResult(MpResult.QUESTIONABLE);
                    String histologyNotInTable;
                    boolean bothNotInTable = false;
                    if (row1 == null && row2 == null) {
                        bothNotInTable = true;
                        histologyNotInTable = "Both " + icd1 + " and " + icd2;
                    }
                    else
                        histologyNotInTable = row1 == null ? icd1 : icd2;

                    result.setMessageNotInTable(this.getStep(), this.getGroupId(), histologyNotInTable, bothNotInTable);
                }
                else if (!row1.equals(row2))
                    result.setFinalResult(MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Are separate/non-contiguous tumors on different rows in Table 2 in the Equivalent Terms and Definitions?");
        rule.setReason("Separate/non-contiguous tumors that are on different rows in Table 2 in the Equivalent Terms and Definitions, are multiple primaries.");
        rule.getNotes().add("Each row in the table is a distinctly different histology.");
        rule.getExamples().add(
                "Small cell neuroendocrine carcinoma 8041 and urothelial carcinoma 8120 are on different rows of Table 2. Abstract two primaries, one for the small cell neuroendocrine carcinoma and a second for the urothelial carcinoma.");
        _rules.add(rule);

        // M14- Tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx) and/or third (Cx?x) character are multiple primaries.
        rule = new MpRulePrimarySite(MphConstants.MP_2018_URINARY_GROUP_ID, "M14");
        _rules.add(rule);

        // Rule M15 Abstract a single primary when synchronous, separate/non-contiguous tumors are on the same row in Table 2 in the Equivalent Terms and Definitions.
        rule = new MphRule(MphConstants.MP_2018_URINARY_GROUP_ID, "M15") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                String h1 = i1.getHistology(), icd1 = h1 + "/" + i1.getBehavior(), h2 = i2.getHistology(), icd2 = h2 + "/" + i2.getBehavior();
                String row1 = MphConstants.URINARY_2018_TABLE2_ROWS.containsKey(h1) ? MphConstants.URINARY_2018_TABLE2_ROWS.get(h1) : MphConstants.URINARY_2018_TABLE2_ROWS.get(icd1);
                String row2 = MphConstants.URINARY_2018_TABLE2_ROWS.containsKey(h2) ? MphConstants.URINARY_2018_TABLE2_ROWS.get(h2) : MphConstants.URINARY_2018_TABLE2_ROWS.get(icd2);
                if (row1 == null || row2 == null) {
                    result.setFinalResult(MpResult.QUESTIONABLE);
                    String histologyNotInTable;
                    boolean bothNotInTable = false;
                    if (row1 == null && row2 == null) {
                        bothNotInTable = true;
                        histologyNotInTable = "Both " + icd1 + " and " + icd2;
                    }
                    else
                        histologyNotInTable = row1 == null ? icd1 : icd2;

                    result.setMessageNotInTable(this.getStep(), this.getGroupId(), histologyNotInTable, bothNotInTable);
                }
                else if (row1.equals(row2)) {
                    int sixtyDaysApart = GroupUtility.verifyDaysApart(i1, i2, 60);
                    if (MphConstants.DATE_VERIFY_UNKNOWN == sixtyDaysApart) {
                        result.setPotentialResult(MpResult.SINGLE_PRIMARY);
                        result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupId());
                    }
                    else if (MphConstants.DATE_VERIFY_WITHIN == sixtyDaysApart)
                        result.setFinalResult(MpResult.SINGLE_PRIMARY);
                }
                return result;
            }
        };
        rule.setQuestion("Are synchronous, separate/non-contiguous tumors on the same row in Table 2 in the Equivalent Terms and Definitions?");
        rule.setReason("Synchronous, separate/non-contiguous tumors that are on the same row in Table 2 in the Equivalent Terms and Definitions are a single primary.");
        rule.getNotes().add("The same row means the tumors are:");
        rule.getNotes().add("  - The same histology (same four-digit ICD-O code) OR");
        rule.getNotes().add("  - One is the preferred term (column 1) and the other is a synonym for the preferred term (column 2) OR");
        rule.getNotes().add("  - A NOS (column 1/column 2) and the other is a subtype/variant of that NOS (column 3)");

        _rules.add(rule);

        // Rule M16 Abstract a single primary (the invasive) when an in situ tumor is diagnosed after an invasive tumor AND tumors:
        // - Occur in the same urinary site OR
        // - The original tumors are multifocal/multicentric and occur in multiple urinary sites; subsequent tumor(s) are in at least one of the previously involved urinary sites
        rule = new MpRuleInsituAfterInvasive(MphConstants.MP_2018_URINARY_GROUP_ID, "M16");
        rule.setQuestion("Is there an in situ tumor following an invasive tumor?");
        rule.setReason("An in situ tumor following an invasive tumor is a single primary.");
        rule.getNotes().add("The rules are hierarchical. Only use this rule when previous rules do not apply.");
        rule.getNotes().add("The tumors may be a NOS and a subtype/variant of that NOS. See Table 2 in the Equivalent Terms and Definitions for listings of NOS and subtype/variants.");
        rule.getNotes().add("Once the patient has an invasive tumor, the subsequent in situ is recorded as a recurrence for those registrars who collect recurrence data.");
        _rules.add(rule);

        // Rule M17 Abstract a single primary (the invasive) when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor AND tumors:
        // - Occur in the same urinary site OR
        // - Original tumor is multifocal/multicentric and involves multiple urinary sites; the subsequent invasive tumor(s) occur in at least one of the previously involved urinary sites
        rule = new MpRuleInvasiveAfterInsituLessThan60Days(MphConstants.MP_2018_URINARY_GROUP_ID, "M17");
        rule.getExamples().add(
                "The first presentation was multifocal/multicentric in situ tumors in multiple urinary organs; the subsequent presentation was invasive tumor in at least one of the previously involved urinary organs.");
        rule.getNotes().add("The rules are hierarchical. Only use this rule if none of the previous rules apply.");
        rule.getNotes().add("The tumors may be an NOS and a subtype/variant of that NOS.");
        rule.getNotes().add("When the case has been abstracted, change behavior code on original abstract from /2 to /3. Do not change date of diagnosis.");
        rule.getNotes().add("If the case has already been submitted to the central registry, report all changes.");
        rule.getNotes().add(
                "The physician may stage both tumors because staging and determining multiple primaries are done for different reasons. Staging determines which treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).");
        rule.getNotes().add("See the COC and SEER manuals for instructions on coding other data items such as Date of Diagnosis, Accession Year and Sequence Number");
        _rules.add(rule);

        // Rule M18 Abstract a single primary when tumors do not meet any of the above criteria.
        rule = new MpRuleNoCriteriaSatisfied(MphConstants.MP_2018_URINARY_GROUP_ID, "M18");
        rule.getNotes().add("Use this rule as a last resort.  Please confirm that you have not overlooked an applicable rule.");
        rule.getExamples().add("TURB shows invasive urothelial carcinoma 8120/3 and CIS/in situ urothelial carcinoma 8120/2. Abstract a single primary.");

        _rules.add(rule);
    }

    private boolean areTwoDifferentUrinarySites(String s1, String s2) {
        return MphConstants.URINARY_2018_URINARY_SITES.contains(s1) && MphConstants.URINARY_2018_URINARY_SITES.contains(s2) && !s1.substring(0, 3).equals(s2.substring(0, 3));
    }
}

