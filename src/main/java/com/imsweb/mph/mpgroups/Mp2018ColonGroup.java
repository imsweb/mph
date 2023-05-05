/*
 * Copyright (C) 2018 Information Management Services, Inc.
 */
package com.imsweb.mph.mpgroups;

import com.imsweb.mph.MphConstants;
import com.imsweb.mph.MphGroup;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphUtils.MpResult;
import com.imsweb.mph.internal.TempRuleResult;
import com.imsweb.mph.mprules.MpRuleInsituAfterInvasive;
import com.imsweb.mph.mprules.MpRuleInvasiveAfterInsituGreaterThan60Days;
import com.imsweb.mph.mprules.MpRuleInvasiveAfterInsituLessThan60Days;
import com.imsweb.mph.mprules.MpRuleNoCriteriaSatisfied;
import com.imsweb.mph.mprules.MpRulePrimarySite;

public class Mp2018ColonGroup extends MphGroup {

    // Colon, Rectosigmoid, and Rectum Multiple Primary Rules
    // C180-C189, C199, C209
    // (Excludes lymphoma and leukemia M9590 – M9992 and Kaposi sarcoma M9140)
    public Mp2018ColonGroup() {
        super(MphConstants.MP_2018_COLON_GROUP_ID, MphConstants.MP_2018_COLON_GROUP_NAME, "C180-C189, C199, C209", null, null,
                "9590-9993, 9140", "2-3,6", "2018-9999");

        // Rule M3 Abstract a single primary when there is adenocarcinoma in situ and/or invasive in at least one polyp AND
        //  - There is a clinical diagnosis of familial polyposis (FAP) OR
        //  - Greater than 100 polyps are documented (no diagnosis of FAP)
        MphRule rule = new MphRule(MphConstants.MP_2018_COLON_GROUP_ID, "M3") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                if ("8220".equals(i1.getHistology()) && "8220".equals(i2.getHistology()))
                    result.setFinalResult(MpResult.SINGLE_PRIMARY);
                return result;
            }
        };
        rule.setQuestion("Is there adenocarcinoma in situ and/or invasive in at least one polyp and one tumor a familial polyposis (FAP) or there are greater than 100 polyps (no FAP)?");
        rule.setReason(
                "If there is an adenocarcinoma in situ and/or invasive in at least one polyp and one tumor is a familial polyposis (FAP) or there are greater than 100 polyps (no FAP), then this is a single primary.");
        rule.getNotes().add(
                "A diagnosis of familial polyposis (FAP) is made when the patient has greater than 100 adenomatous polyps. Polyps with adenocarcinoma and benign polyps will be present. Because there are many polyps, the pathologist does not examine every polyp.");
        rule.getNotes().add(
                "In situ /2 and malignant /3 adenocarcinoma in polyps, malignancies with remnants of a polyp, as well as de novo (previously called frank) malignancies may be present in multiple segments of the colon or in both the colon and rectum.  Polyposis may be present in other GI sites such as stomach (a de novo does not have to be present; all adenocarcinoma may be in polyps).");
        rule.getNotes().add(
                "FAP is a genetic disease. The characteristics of FAP are numerous precancerous polyps in the colon and rectum when the patient reaches puberty. If not treated, the polyps typically become malignant. Patients often have total colectomies.");
        rule.getNotes().add("Multiple polyps in the colorectum is not equivalent to FAP.");
        rule.getNotes().add("Code primary site as follows:");
        rule.getNotes().add("  - Present in more than one segment of colon: C189 colon, NOS");
        rule.getNotes().add("  - Present in colon and rectosigmoid OR colon and rectum: C199 rectosigmoid junction");
        rule.getNotes().add("  - Present in colon and small intestine: C260 intestinal tract, NOS (there is no code for large and small bowel)");
        rule.getNotes().add("In addition to the colon and small intestine, FAP may also be present in the:");
        rule.getNotes().add("  - Stomach AND/OR");
        rule.getNotes().add("  - Rectosigmoid AND/OR");
        rule.getNotes().add("  - Rectum");
        rule.getExamples().add(
                "The patient has a diagnosis of FAP.  The operative report and physician’s documentation say that polyps with adenocarcinoma were present in specimens removed from the ascending colon and the sigmoid colon. The ascending and sigmoid colon are part of the large bowel. Code the primary site C189 colon NOS.");
        _rules.add(rule);

        // Rule M4 Abstract multiple primaries when there are separate, non-contiguous tumors in sites with ICD-O site codes that differ at the second CXxx and/or third CxXx character.
        rule = new MpRulePrimarySite(MphConstants.MP_2018_COLON_GROUP_ID, "M4");
        rule.getNotes().add("Definition of separate/non-contiguous tumors: at least two malignancies which do not overlap/merge.");
        rule.getNotes().add("Differences at either the second or third characters are different primary sites/multiple primaries.");
        rule.getExamples().add("Breast C50x and colon C18x");
        rule.getExamples().add("Colon C18x and rectum C209 (This does not include FAP- see earlier rules)");
        rule.getNotes().add("This rule does not apply to a single overlapping malignancy of colon and rectum.");
        _rules.add(rule);

        // Rule M5 Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 1 in the Equivalent Terms and Definitions. Timing is irrelevant.
        rule = new MphRule(MphConstants.MP_2018_COLON_GROUP_ID, "M5") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                String h1 = i1.getHistology(), icd1 = i1.getIcdCode(), h2 = i2.getHistology(), icd2 = i2.getIcdCode();
                if (!h1.equals(h2) && (MphConstants.COLON_2018_TABLE1_SUBTYPES.contains(h1) || MphConstants.COLON_2018_TABLE1_SUBTYPES.contains(icd1)) && (
                        MphConstants.COLON_2018_TABLE1_SUBTYPES.contains(h2) || MphConstants.COLON_2018_TABLE1_SUBTYPES.contains(icd2)))
                    result.setFinalResult(MpResult.MULTIPLE_PRIMARIES);

                return result;
            }
        };
        rule.setQuestion("Are separate/non-contiguous tumors two or more different subtypes/variants in Column 3, Table 1 in the Equivalent Terms and Definitions?");
        rule.setReason("Separate/non-contiguous tumors that are two or more different subtypes/variants in Column 3, Table 1 in the Equivalent Terms and Definitions are multiple primaries.");
        rule.getNotes().add("The tumors may be subtypes/variants of the same or different NOS histologies.");
        rule.getNotes().add(
                "  - Same NOS: Medullary carcinoma NOS 8510/3 and tubulopapillary adenocarcinoma 8263/3 are both subtypes of adenocarcinoma NOS 8140/3 but are distinctly different histologies. Abstract multiple primaries.");
        rule.getNotes().add(
                "  - Different NOS: Goblet cell carcinoid 8243/3 is a subtype of mixed adenoneuroendocrine carcinoma 8244/3; somatostatin-producing NET 8156/3 is a subtype of neuroendocrine tumor Grade 1 (G1) 8240/3. They are distinctly different histologies. Abstract multiple primaries.");
        _rules.add(rule);

        // Rule M6 Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 1 in the Equivalent Terms and Definitions. Timing is irrelevant.
        rule = new MphRule(MphConstants.MP_2018_COLON_GROUP_ID, "M6") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                String h1 = i1.getHistology(), icd1 = i1.getIcdCode(), h2 = i2.getHistology(), icd2 = i2.getIcdCode();
                String row1 = MphConstants.COLON_2018_TABLE1_ROWS.containsKey(h1) ? MphConstants.COLON_2018_TABLE1_ROWS.get(h1) : MphConstants.COLON_2018_TABLE1_ROWS.get(icd1);
                String row2 = MphConstants.COLON_2018_TABLE1_ROWS.containsKey(h2) ? MphConstants.COLON_2018_TABLE1_ROWS.get(h2) : MphConstants.COLON_2018_TABLE1_ROWS.get(icd2);
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
        rule.setQuestion("Are separate/non-contiguous tumors on different rows in Table 1 in the Equivalent Terms and Definitions?");
        rule.setReason("Separate/non-contiguous tumors that are on different rows in Table 1 in the Equivalent Terms and Definitions are multiple primaries.");
        rule.getNotes().add("Each row in the table is a distinctly different histology.");
        _rules.add(rule);

        // Rule M7 Abstract multiple primaries when a subsequent tumor arises at the anastomotic site AND:
        // -  One tumor is a NOS and the other is a subtype/variant of that NOS OR
        // -  The subsequent tumor occurs greater than 36 months after original tumor resection OR
        // - The subsequent tumor arises in the mucosa
        // ABH 7/19/18 - Revised per https://www.squishlist.com/ims/seerdms_dev/81114/
        // Incoming record is a tumor in a segment of colon/rectal/rectosigmoid.
        // There is a previous diagnosis of a tumor in a different segment of colon/rectum/rectosigmoid,
        // AND there was surgery done (surgery codes 30, 32, 40, 31),
        // ABH 9/14/18 - Disabled now per https://www.squishlist.com/ims/seerdms_dev/81114/
        /*
        rule = new MphRule(MphConstants.MP_2018_COLON_GROUP_ID, "M7") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                List<String> surgerySites = Arrays.asList("30", "31", "32", "40");
                // Different segments
                if (!i1.getPrimarySite().substring(3, 4).equals(i2.getPrimarySite().substring(3, 4))) {
                    int compDateRes = GroupUtility.compareDxDate(i1, i2);
                    if ((compDateRes == 1 && surgerySites.contains(i2.getSurgeryOfPrimarySite())) ||
                            (compDateRes == 2 && surgerySites.contains(i1.getSurgeryOfPrimarySite()))) {
                        result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    }
                }
                return result;
            }
        };
        rule.setQuestion("Did a subsequent tumor arise at the anastomotic site and \n" +
                "- One tumor is a NOS and the other is a subtype/variant of that NOS, or \n" +
                "- The subsequent tumor occurs greater than 36 months after original tumor resection, or \n" +
                "- The subsequent tumor arises in the mucosa?");
        rule.setReason("A subsequent tumor arising at the anastomotic site and \n" +
                "- One tumor is a NOS and the other is a subtype/variant of that NOS, or \n" +
                "- The subsequent tumor occurs greater than 36 months after original tumor resection, or \n" +
                "- The subsequent tumor arises in the mucosa, \n" +
                "is multiple primaries.");

        rule.getNotes().add("Bullet three does not apply to GIST.  GISTs only start in the wall; never in the mucosa.");
        rule.getExamples().add("(For bullet 1: NOS and subtype/variant) The original tumor was adenocarcinoma NOS 8140. The patient had a hemicolectomy. There was a recurrence at the anastomotic site diagnosed exactly as mucinous adenocarcinoma 8480. Mucinous adenocarcinoma is a subtype/variant of the NOS adenocarcinoma, but they are two different histologies. Code two primaries, one for the original adenocarcinoma NOS and another for the subsequent anastomotic site mucinous adenocarcinoma.");
        rule.getNotes().add("There may or may not be physician documentation of anastomotic recurrence.  Follow the rules.");
        rule.getNotes().add("When the original tumor was diagnosed prior to 1/1/2018 and was coded to adenocarcinoma in a polyp, and the anastomotic site tumor is adenocarcinoma per 2018 rules, the tumors are the same histology. ICD-O codes differ because of changes in histology coding rules.  Continue through the rules.");
        rule.getNotes().add("The tumor may or may not invade into the colon wall or adjacent tissue.");
        rule.getNotes().add("These rules are hierarchical. Only use this rule when previous rules do not apply.");
        _rules.add(rule);
        */

        // Rule M8 Abstract a single primary when a subsequent tumor arises at the anastomotic site AND:
        // -  The subsequent tumor occurs less than or equal to 24 months after original tumor resection OR
        // -  The tumor arises in colon/rectal wall and/or surrounding tissue; there is no involvement of the mucosa OR
        // -  The pathologist or clinician documents an anastomotic recurrence
        // ABH 9/14/18 - Disabled now per https://www.squishlist.com/ims/seerdms_dev/81114/
        /*
        rule = new MphRule(MphConstants.MP_2018_COLON_GROUP_ID, "M8") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                //result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                return result;
            }
        };
        rule.setQuestion("Did a subsequent tumor arise at the anastomotic site and \n" +
                "- The subsequent tumor occurs less than or equal to 24 months after original tumor resection, or \n" +
                "- The tumor arises in colon/rectal wall and/or surrounding tissue; there is no involvement of the mucosa, or \n" +
                "- The pathologist or clinician documents an anastomotic recurrence?");
        rule.setReason("A subsequent tumor arising at the anastomotic site and \n" +
                "- The subsequent tumor occurs less than or equal to 24 months after original tumor resection, or \n" +
                "- The tumor arises in colon/rectal wall and/or surrounding tissue; there is no involvement of the mucosa, or \n" +
                "- The pathologist or clinician documents an anastomotic recurrence, \n" +
                "is a single primary.");
        rule.getNotes().add("The physician may stage the subsequent tumor because the depth of invasion determines the second course of treatment.");
        rule.getNotes().add("These tumors are a single primary/recurrence.  Registrars that collect recurrence information should record the information in the recurrence fields.");
        _rules.add(rule);
        */

        // Rule M9 Abstract multiple primaries when there are separate, non-contiguous tumors in sites with ICD-O site codes that differ at the fourth characters C18X.
        rule = new MphRule(MphConstants.MP_2018_COLON_GROUP_ID, "M9") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                if (i1.getPrimarySite().equals("C189") || i2.getPrimarySite().equals("C189")) {
                    result.setPotentialResult(MpResult.MULTIPLE_PRIMARIES);
                    result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". One of the sites is C189.");
                }
                else if (!i1.getPrimarySite().equals(i2.getPrimarySite()))
                    result.setFinalResult(MpResult.MULTIPLE_PRIMARIES);

                return result;
            }
        };

        rule.setQuestion("Are there tumors in sites with ICD-O site codes that differ at the fourth character (C18X)?");
        rule.setReason("Tumors in sites with ICD-O site codes that differ at the fourth character (C18X) are multiple primaries.");
        rule.getNotes().add(
                "Differences at the fourth character include different segments of the colon. Abstract a primary for each separate non-contiguous tumor in a different segment of the colon. This rule is not used for colon NOS C189.  C189 is rarely used other than DCO.");
        rule.getExamples().add(
                "The patient has adenocarcinoma in situ in a sigmoid polyp and mucinous adenocarcinoma in a polyp in the descending colon, the site code differs at the fourth character (sigmoid C187 and descending C186). Code two primaries, one for the sigmoid and another for the descending colon.");
        _rules.add(rule);

        // Rule M10 Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than one year after the original diagnosis or last recurrence.
        rule = new MphRule(MphConstants.MP_2018_COLON_GROUP_ID, "M10") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                int diff = GroupUtility.verifyYearsApart(i1, i2, 1);
                if (MphConstants.DATE_VERIFY_UNKNOWN == diff) {
                    result.setPotentialResult(MpResult.MULTIPLE_PRIMARIES);
                    result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". There is not enough diagnosis date information.");
                }
                else if (MphConstants.DATE_VERIFY_APART == diff)
                    result.setFinalResult(MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Are there tumors diagnosed more than one (1) year apart?");
        rule.setReason("Tumors diagnosed more than one (1) year apart are multiple primaries.");
        rule.getNotes().add("Clinically disease-free means that there was no evidence of recurrence on follow-up.");
        rule.getNotes().add("  - Colonoscopies are NED");
        rule.getNotes().add("  - Scans are NED");
        rule.getNotes().add(
                "When there is a recurrence less than or equal to one year of diagnosis, the \"clock\" starts over. The time interval is calculated from the date of last recurrence. In other words, the patient must have been disease-free for greater than one year from the date of the last recurrence.");
        rule.getNotes().add("When the first course of treatment was a polypectomy only, this rule means there were no recurrences for greater than one year.");
        rule.getNotes().add("When the first course of treatment was a colectomy or A&P resection, there were no anastomotic recurrences for greater than one year.");
        rule.getNotes().add("When it is unknown/not documented whether the patient had a recurrence, default to date of diagnosis to compute the time interval.");
        rule.getNotes().add(
                "The physician may state this is a recurrence, meaning the patient had a previous colon tumor and now has another colon tumor. Follow the rules; do not attempt to interpret the physician’s statement.");
        _rules.add(rule);

        // Rule M11 Abstract a single primary when synchronous, separate/non-contiguous tumors are on the same row in Table 1 in the Equivalent Terms and Definitions.
        rule = new MphRule(MphConstants.MP_2018_COLON_GROUP_ID, "M11") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();

                String h1 = i1.getHistology(), icd1 = i1.getIcdCode(), h2 = i2.getHistology(), icd2 = i2.getIcdCode();
                String row1 = MphConstants.COLON_2018_TABLE1_ROWS.containsKey(h1) ? MphConstants.COLON_2018_TABLE1_ROWS.get(h1) : MphConstants.COLON_2018_TABLE1_ROWS.get(icd1);
                String row2 = MphConstants.COLON_2018_TABLE1_ROWS.containsKey(h2) ? MphConstants.COLON_2018_TABLE1_ROWS.get(h2) : MphConstants.COLON_2018_TABLE1_ROWS.get(icd2);
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
                    int diff = GroupUtility.verifyDaysApart(i1, i2, 60);
                    if (MphConstants.DATE_VERIFY_UNKNOWN == diff) {
                        result.setPotentialResult(MpResult.SINGLE_PRIMARY);
                        result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupId());
                    }
                    else if (MphConstants.DATE_VERIFY_WITHIN == diff)
                        result.setFinalResult(MpResult.SINGLE_PRIMARY);
                }

                return result;
            }
        };
        rule.setQuestion("Are synchronous, separate/non-contiguous tumors on the same in Table 1 in the Equivalent Terms and Definitions?");
        rule.setReason("Synchronous, separate/non-contiguous tumors that are on the same row in Table 1 in the Equivalent Terms and Definitions are a single primary.");
        rule.getNotes().add("The same row means the tumors are:");
        rule.getNotes().add("  - The same histology (same four-digit ICD-O code) OR");
        rule.getNotes().add("  - One is the preferred term (column 1) and the other is a synonym for the preferred term (column 2) OR");
        rule.getNotes().add("  - A NOS (column 1/column 2) and the other is a subtype/variant of that NOS (column 3)");
        rule.getNotes().add("The tumors may be de novo (formerly called frank) and carcinoma in a polyp.");
        rule.getNotes().add("The tumors may be adenocarcinoma in multiple polyps 8221.");
        _rules.add(rule);

        // Rule M12 Abstract a single primary (the invasive) when an in situ tumor is diagnosed after an invasive tumor.
        rule = new MpRuleInsituAfterInvasive(MphConstants.MP_2018_COLON_GROUP_ID, "M12");
        rule.getNotes().add("The rules are hierarchical. Only use this rule when none of the previous rules apply.");
        rule.getNotes().add("The tumors may be a NOS and a subtype/variant of that NOS. See Table 1 in the Equivalent Terms and Definitions for listings of NOS and subtype/variants.");
        rule.getNotes().add("The in situ is recorded as a recurrence for those registrars who collect recurrence data.");
        _rules.add(rule);

        // Rule M13 Abstract a single primary (the invasive) when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor.
        rule = new MpRuleInvasiveAfterInsituLessThan60Days(MphConstants.MP_2018_COLON_GROUP_ID, "M13");
        rule.getNotes().add("The rules are hierarchical. Only use this rule when previous rules do not apply.");
        rule.getNotes().add("Change behavior code on original abstract from /2 to /3. Do not change date of diagnosis.");
        rule.getNotes().add("If the case has already been submitted to the central registry, report all changes.");
        rule.getNotes().add(
                "The physician may stage both tumors because staging and determining multiple primaries are done for different reasons. Staging determines which treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).");
        rule.getNotes().add("See the COC and SEER manuals for instructions on coding data items such as Date of Diagnosis, Accession Year and Sequence Number.");
        _rules.add(rule);

        // Rule M14 Abstract multiple primaries when an invasive tumor occurs more than 60 days after an in situ tumor.
        rule = new MpRuleInvasiveAfterInsituGreaterThan60Days(MphConstants.MP_2018_COLON_GROUP_ID, "M14");
        rule.getNotes().add("Abstract both the invasive and in situ tumors.");
        rule.getNotes().add("Abstract as multiple primaries even if physician states the invasive tumor is disease recurrence or progression.");
        rule.getNotes().add(
                "This rule is based on long-term epidemiologic studies of recurrence intervals. The specialty medical experts (SMEs) reviewed and approved these rules.  Many of the SMEs were also authors, co-authors, or editors of the AJCC Staging Manual.");
        _rules.add(rule);

        // Rule M15 Abstract a single primary when tumors do not meet any of the above criteria.
        rule = new MpRuleNoCriteriaSatisfied(MphConstants.MP_2018_COLON_GROUP_ID, "M15");
        rule.getNotes().add("Use caution when applying this default rule.  Please confirm that you have not overlooked an applicable rule.");
        rule.getExamples().add(
                "The pathology states adenocarcinoma in situ 8140/2 and a second non-contiguous invasive adenocarcinoma 8140/3 in the sigmoid colon C187.  Multiple tumors that are the same histology in the same primary site (same four characters of ICD-O topography code) are a single primary.");
        _rules.add(rule);
    }
}

