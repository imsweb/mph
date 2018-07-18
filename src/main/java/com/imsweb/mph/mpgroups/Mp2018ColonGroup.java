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

public class Mp2018ColonGroup extends MphGroup {

    /*
    Colon, Rectosigmoid, and Rectum Multiple Primary Rules
    C180-C189, C199, C209
    (Excludes lymphoma and leukemia M9590-M9992 and Kaposi sarcoma M9140)


    Rule M3	Abstract a single primary when
        •	The diagnosis is adenomatous polyposis coli (familial polyposis/FAP) OR
        •	There is no diagnosis of FAP BUT
            	Greater than 100 polyps are documented AND
            	Adenocarcinoma in situ /2 or invasive /3 is present in at least one polyp
        Note 1:	A diagnosis of adenomatous polyposis coli (familial polyposis/FAP) is made when the patient has greater than 100 adenomatous polyps. Polyps with adenocarcinoma and benign polyps will be present. Because there are many polyps, the pathologist does not examine every polyp.
        Note 2:	In situ /2 and malignant /3 adenocarcinoma in polyps, malignancies with remnants of a polyp as well as de novo (previously called frank) malignancies may be present in multiple segments of the colon or in the colon and rectum.  Polyposis may be present in other GI sites such as stomach (a de novo does not have to be present; all adenocarcinoma may be in polyps).
        Note 3:	FAP is a genetic disease. The characteristics of FAP are numerous precancerous polyps in the colon and rectum when the patient reaches puberty. If not treated, the polyps typically become malignant. Patients often have total colectomies.
        Note 4:	Multiple polyps in the colorectum is not equivalent to FAP.
        Note 5:	Code primary site as follows:
        •	Present in more than one segment of colon: C189 colon, NOS
        •	Present in colon and rectosigmoid OR colon and rectum: C199 rectosigmoid junction
        •	Present in colon and small intestine: C260 intestinal tract, NOS (there is no code for large and small bowel)
        •	Present in colon and small intestine (may also involve rectum): C269 gastrointestinal tract, NOS
        Example: 	The patient has a diagnosis of FAP.  The operative report and physician’s documentation say that polyps with adenocarcinoma were present in specimens removed from the ascending colon and the sigmoid colon. The ascending and sigmoid colon are part of the large bowel. Code the primary site C189 colon NOS.

    Rule M4	Abstract multiple primaries when there are separate, non-contiguous tumors in sites with ICD-O site codes that differ at the second CXxx, third CxXx and/or fourth characters C18X.
        Note 1:	Definition of separate/non-contiguous tumors: at least two malignancies which do not overlap/merge.
        Note 2:	The rules are hierarchical. Only use this rule if the patient does not have FAP.
        Note 3:	Differences at the fourth character include different segments of the colon. Abstract a primary for each separate non-contiguous tumor in a different segment of the colon. This rule is not used for colon NOS C189.  C189 is rarely used other than DCO.
        Example: 	The patient has adenocarcinoma in situ in a sigmoid polyp and mucinous adenocarcinoma in a polyp in the descending colon, the site code differs at the fourth character (sigmoid C187 and descending C186). Code two primaries, one for the sigmoid and another for the descending colon.
        Note 4:	Differences at either the second or third characters are different primary sites/multiple primaries.
        Example 1: 	Breast C50x and colon C18x
        Example 2: 	Colon C18x and rectum C209
        Note: This rule does not apply to a single overlapping malignancy of colon and rectum.

    Rule M5	Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 1 in the Equivalent Terms and Definitions. Timing is irrelevant.
        Note: 	The tumors may be subtypes/variants of the same or different NOS histologies.
        •	Same NOS: Medullary carcinoma NOS 8510/3 and tubulopapillary adenocarcinoma 8263/3 are both subtypes of adenocarcinoma NOS 8140/3 but are distinctly different histologies. Abstract multiple primaries.
        •	Different NOS: Goblet cell carcinoid 8243/3 is a subtype of mixed adenoneuroendocrine carcinoma 8244/3; somatostatin-producing NET 8156/3 is a subtype of neuroendocrine tumor Grade 1 (G1) 8240/3. They are distinctly different histologies. Abstract multiple primaries.

    Rule M6	Abstract a single primary when separate/non-contiguous tumors are on the same row in Table 1 in the Equivalent Terms and Definitions. Timing is irrelevant.
        Note 1:	The tumors must be the same behavior. When one tumor is in situ and the other invasive, continue through the rules.
        Note 2:	The same row means the tumors are:
        •	The same histology (same four-digit ICD-O code) OR
        •	One is the preferred term (column 1) and the other is a synonym for the preferred term (column 2) OR
        •	A NOS (column 1/column 2) and the other is a subtype/variant of that NOS (column 3)
        Note 3:	The tumors may be de novo (formerly called frank) and carcinoma in a polyp.
        Note 4:	The tumors may be adenocarcinoma in multiple polyps 8221.

    Rule M7	Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 1 in the Equivalent Terms and Definitions. Timing is irrelevant.
        Note:	 Each row in the table is a distinctly different histology.

    Rule M8	Abstract multiple primaries when a subsequent tumor arises at the anastomotic site AND:
        •	One tumor is a NOS and the other is a subtype/variant of that NOS OR
        •	The subsequent tumor occurs greater than 24 months after original tumor resection OR
        •	The subsequent tumor arises in the mucosa
        Note:	Bullet three does not apply to GIST.  GISTs only start in the wall; never in the mucosa.
        Example: 	(For bullet 1: NOS and subtype/variant) The original tumor was adenocarcinoma NOS 8140. The patient had a hemicolectomy. There was a recurrence at the anastomotic site diagnosed exactly as mucinous adenocarcinoma 8480. Mucinous adenocarcinoma is a subtype/variant of the NOS adenocarcinoma, but they are two different histologies. Code two primaries, one for the original adenocarcinoma NOS and another for the subsequent anastomotic site mucinous adenocarcinoma.
        Note 1:	There may or may not be physician documentation of anastomotic recurrence.  Follow the rules.
        Note 2:	When the original tumor was diagnosed prior to 1/1/2018 and was coded to adenocarcinoma in a polyp, and the anastomotic site tumor is adenocarcinoma, the tumors are the same histology. ICD-O codes differ because of changes in histology coding rules.  Continue through the rules.
        Note 3:	The tumor may or may not invade into the colon wall or adjacent tissue.
        Note 4:	These rules are hierarchical. Only use this rule when previous rules do not apply.

    Rule M9	Abstract a single primary when a subsequent tumor arises at the anastomotic site AND:
        •	The subsequent tumor occurs less than or equal to 24 months after original tumor resection OR
        •	The tumor arises in colon/rectal wall and/or surrounding tissue; there is no involvement of the mucosa OR
        •	The pathologist or clinician documents an anastomotic recurrence
        Note 1:	The physician may stage the subsequent tumor because the depth of invasion determines the second course of treatment.
        Note 2:	These tumors are a single primary/recurrence.  Registrars that collect recurrence information should record the information in the recurrence fields.

    Rule M10	Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than one year after the original diagnosis or last recurrence.
        Note 1:	Clinically disease-free means that there was no evidence of recurrence on follow-up.
        •	Colonoscopies are NED
        •	Scans are NED
        Note 2:	When there is a recurrence less than or equal to one year of diagnosis, the “clock” starts over. The time interval is calculated from the date of last recurrence. In other words, the patient must have been disease-free for greater than one year from the date of the last recurrence.
        Note 3:	When the first course of treatment was a polypectomy only, this rule means there were no recurrences for greater than one year.
        Note 4:	When the first course of treatment was a colectomy or A&P resection, there were no anastomotic recurrences for greater than one year.
        Note 5:	When it is unknown/not documented whether the patient had a recurrence, default to date of diagnosis to compute the time interval.
        Note 6:	The physician may state this is a recurrence, meaning the patient had a previous colon tumor and now has another colon tumor. Follow the rules; do not attempt to interpret the physician’s statement.

    Rule M11	Abstract a single primary (the invasive) when an in situ tumor is diagnosed after an invasive tumor.
        Note 1:	The rules are hierarchical. Only use this rule when none of the previous rules apply.
        Note 2:	The tumors may be a NOS and a subtype/variant of that NOS. See Table 1 in the Equivalent Terms and Definitions for listings of NOS and subtype/variants.
        Note 3:	The in situ is recorded as a recurrence for those registrars who collect recurrence data.

    Rule M12	Abstract a single primary (the invasive) when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor.
        Note 1:	The rules are hierarchical. Only use this rule when previous rules do not apply.
        Note 2:	Change behavior code on original abstract from /2 to /3. Do not change date of diagnosis.
        Note 3:	If the case has already been submitted to the central registry, report all changes.
        Note 4:	The physician may stage both tumors because staging and determining multiple primaries are done for different reasons. Staging determines which treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).
        Note 5:	See the COC and SEER manuals for instructions on coding data items such as Date of Diagnosis, Accession Year and Sequence Number.

    Rule M13	Abstract multiple primaries when an invasive tumor occurs more than 60 days after an in situ tumor.
        Note 1:	Abstract both the invasive and in situ tumors.
        Note 2:	Abstract as multiple primaries even if physician states the invasive tumor is disease recurrence or progression.
        Note 3:	This rule is based on long-term epidemiologic studies of recurrence intervals. The specialty medical experts (SMEs) reviewed and approved these rules.  Many of the SMEs were authors, co-authors, or editors of the AJCC Staging Manual.

    Rule M14	Abstract a single primary when tumors do not meet any of the above criteria.
        Note:	Use caution when applying this default rule.  Please confirm that you have not overlooked an applicable rule.

    */

    // TODO - Question M8, M9 - How do you determine an "anastomotic site"?
    // TODO - Question M8 - How do you determine "One tumor is a NOS and the other is a subtype/variant of that NOS OR"?
    // TODO - Question M8 - How do you determine "The subsequent tumor arises in the mucosa"?

    // TODO - Question M9 - How do you determine "The tumor arises in colon/rectal wall and/or surrounding tissue; there is no involvement of the mucosa OR"?
    // TODO - Question M9 - How do you determine "The pathologist or clinician documents an anastomotic recurrence"?


    // Colon, Rectosigmoid, and Rectum Multiple Primary Rules
    // C180-C189, C199, C209
    // (Excludes lymphoma and leukemia M9590 – M9992 and Kaposi sarcoma M9140)
    public Mp2018ColonGroup() {
        super(MphConstants.MP_2018_COLON_GROUP_ID, MphConstants.MP_2018_COLON_GROUP_NAME, "C180-C189, C199, C209", null, null,
                "9590-9992, 9140", "2-3,6", "2018-9999");

        // Rule M3	Abstract a single primary when
        // •	The diagnosis is adenomatous polyposis coli (familial polyposis/FAP) OR
        // •	There is no diagnosis of FAP BUT
            // 	 Greater than 100 polyps are documented AND
            // 	 Adenocarcinoma in situ /2 or invasive /3 is present in at least one polyp
        MphRule rule = new MphRule(MphConstants.MP_2018_COLON_GROUP_ID, "M3") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (i1.getHistology().equals("8220") || i2.getHistology().equals("8220")) {
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                }
                return result;
            }
        };
        rule.setQuestion("Is one tumor a FAP (8220)?");
        rule.setReason("If one tumor is a FAP (8220) then this is a single primary.");
        rule.getNotes().add("A diagnosis of adenomatous polyposis coli (familial polyposis/FAP) is made when the patient has greater than 100 adenomatous polyps. Polyps with adenocarcinoma and benign polyps will be present. Because there are many polyps, the pathologist does not examine every polyp.");
        rule.getNotes().add("In situ /2 and malignant /3 adenocarcinoma in polyps, malignancies with remnants of a polyp as well as de novo (previously called frank) malignancies may be present in multiple segments of the colon or in the colon and rectum.  Polyposis may be present in other GI sites such as stomach (a de novo does not have to be present; all adenocarcinoma may be in polyps).");
        rule.getNotes().add("FAP is a genetic disease. The characteristics of FAP are numerous precancerous polyps in the colon and rectum when the patient reaches puberty. If not treated, the polyps typically become malignant. Patients often have total colectomies.");
        rule.getNotes().add("Multiple polyps in the colorectum is not equivalent to FAP.");
        rule.getNotes().add("Code primary site as follows:");
        rule.getNotes().add("  • Present in more than one segment of colon: C189 colon, NOS");
        rule.getNotes().add("  • Present in colon and rectosigmoid OR colon and rectum: C199 rectosigmoid junction");
        rule.getNotes().add("  • Present in colon and small intestine: C260 intestinal tract, NOS (there is no code for large and small bowel)");
        rule.getNotes().add("  • Present in colon and small intestine (may also involve rectum): C269 gastrointestinal tract, NOS");
        rule.getExamples().add("The patient has a diagnosis of FAP.  The operative report and physician’s documentation say that polyps with adenocarcinoma were present in specimens removed from the ascending colon and the sigmoid colon. The ascending and sigmoid colon are part of the large bowel. Code the primary site C189 colon NOS.");
        _rules.add(rule);

        // Rule M4	Abstract multiple primaries when there are separate, non-contiguous tumors in sites with ICD-O site codes that differ at the second CXxx, third CxXx and/or fourth characters C18X.
        rule = new MphRuleTopography234Code(MphConstants.MP_2018_COLON_GROUP_ID, "M4");
        rule.getNotes().add("Definition of separate/non-contiguous tumors: at least two malignancies which do not overlap/merge.");
        rule.getNotes().add("The rules are hierarchical. Only use this rule if the patient does not have FAP.");
        rule.getNotes().add("Differences at the fourth character include different segments of the colon. Abstract a primary for each separate non-contiguous tumor in a different segment of the colon. This rule is not used for colon NOS C189.  C189 is rarely used other than DCO.");
        rule.getExamples().add("The patient has adenocarcinoma in situ in a sigmoid polyp and mucinous adenocarcinoma in a polyp in the descending colon, the site code differs at the fourth character (sigmoid C187 and descending C186). Code two primaries, one for the sigmoid and another for the descending colon.");
        rule.getNotes().add("Differences at either the second or third characters are different primary sites/multiple primaries.");
        rule.getExamples().add("Breast C50x and colon C18x");
        rule.getExamples().add("Colon C18x and rectum C209");
        rule.getNotes().add("This rule does not apply to a single overlapping malignancy of colon and rectum.");
        _rules.add(rule);

        // Rule M5	Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 1 in the Equivalent Terms and Definitions. Timing is irrelevant.
        rule = new MphRuleTwoOrMoreDifferentSubTypesInTable(MphConstants.MP_2018_COLON_GROUP_ID, "M5", MphConstants.COLON_2018_TABLE1_SUBTYPES,false);
        rule.setQuestion("Are separate/non-contiguous tumors two or more different subtypes/variants in Column 3, Table 1 in the Equivalent Terms and Definitions?");
        rule.setReason("Separate/non-contiguous tumors that are two or more different subtypes/variants in Column 3, Table 1 in the Equivalent Terms and Definitions are multiple primaries.");
        rule.getNotes().add("The tumors may be subtypes/variants of the same or different NOS histologies.");
        rule.getNotes().add("  • Same NOS: Medullary carcinoma NOS 8510/3 and tubulopapillary adenocarcinoma 8263/3 are both subtypes of adenocarcinoma NOS 8140/3 but are distinctly different histologies. Abstract multiple primaries.");
        rule.getNotes().add("  • Different NOS: Goblet cell carcinoid 8243/3 is a subtype of mixed adenoneuroendocrine carcinoma 8244/3; somatostatin-producing NET 8156/3 is a subtype of neuroendocrine tumor Grade 1 (G1) 8240/3. They are distinctly different histologies. Abstract multiple primaries.");
        _rules.add(rule);

        // Rule M6	Abstract a single primary when separate/non-contiguous tumors are on the same row in Table 1 in the Equivalent Terms and Definitions. Timing is irrelevant.
        rule = new MphRuleSameRowInTable(MphConstants.MP_2018_COLON_GROUP_ID, "M6", MphConstants.COLON_2018_TABLE1_ROWS, true);
        rule.setQuestion("Are separate/non-contiguous tumors on the same in Table 1 in the Equivalent Terms and Definitions?");
        rule.setReason("Separate/non-contiguous tumors that are on the same row in Table 1 in the Equivalent Terms and Definitions are multiple primaries.");
        rule.getNotes().add("The tumors must be the same behavior. When one tumor is in situ and the other invasive, continue through the rules.");
        rule.getNotes().add("The same row means the tumors are:");
        rule.getNotes().add("  • The same histology (same four-digit ICD-O code) OR");
        rule.getNotes().add("  • One is the preferred term (column 1) and the other is a synonym for the preferred term (column 2) OR");
        rule.getNotes().add("  • A NOS (column 1/column 2) and the other is a subtype/variant of that NOS (column 3)");
        rule.getNotes().add("The tumors may be de novo (formerly called frank) and carcinoma in a polyp.");
        rule.getNotes().add("The tumors may be adenocarcinoma in multiple polyps 8221.");
        _rules.add(rule);

        // Rule M7	Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 1 in the Equivalent Terms and Definitions. Timing is irrelevant.
        rule = new MphRuleDifferentRowsInTable(MphConstants.MP_2018_COLON_GROUP_ID, "M7", MphConstants.COLON_2018_TABLE1_ROWS, false);
        rule.setQuestion("Are separate/non-contiguous tumors on different rows in Table 1 in the Equivalent Terms and Definitions?");
        rule.setReason("Separate/non-contiguous tumors that are on different rows in Table 1 in the Equivalent Terms and Definitions are multiple primaries.");
        rule.getNotes().add("Each row in the table is a distinctly different histology.");
        _rules.add(rule);

        // Rule M8	Abstract multiple primaries when a subsequent tumor arises at the anastomotic site AND:
        // •	 One tumor is a NOS and the other is a subtype/variant of that NOS OR
        // •	 The subsequent tumor occurs greater than 24 months after original tumor resection OR
        // • The subsequent tumor arises in the mucosa
        // Note:	Bullet three does not apply to GIST.  GISTs only start in the wall; never in the mucosa.
        // Example: 	(For bullet 1: NOS and subtype/variant) The original tumor was adenocarcinoma NOS 8140. The patient had a hemicolectomy. There was a recurrence at the anastomotic site diagnosed exactly as mucinous adenocarcinoma 8480. Mucinous adenocarcinoma is a subtype/variant of the NOS adenocarcinoma, but they are two different histologies. Code two primaries, one for the original adenocarcinoma NOS and another for the subsequent anastomotic site mucinous adenocarcinoma.
        // TODO
        rule = new MphRule(MphConstants.MP_2018_COLON_GROUP_ID, "M8") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                //result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Did a subsequent tumor arise at the anastomotic site and \n" +
                         "- One tumor is a NOS and the other is a subtype/variant of that NOS, or \n" +
                         "- The subsequent tumor occurs greater than 24 months after original tumor resection, or \n" +
                         "- The subsequent tumor arises in the mucosa?");
        rule.setReason("A subsequent tumor arising at the anastomotic site and \n" +
                       "- One tumor is a NOS and the other is a subtype/variant of that NOS, or \n" +
                       "- The subsequent tumor occurs greater than 24 months after original tumor resection, or \n" +
                       "- The subsequent tumor arises in the mucosa, \n" +
                       "is multiple primaries.");
        rule.getNotes().add("There may or may not be physician documentation of anastomotic recurrence.  Follow the rules.");
        rule.getNotes().add("When the original tumor was diagnosed prior to 1/1/2018 and was coded to adenocarcinoma in a polyp, and the anastomotic site tumor is adenocarcinoma, the tumors are the same histology. ICD-O codes differ because of changes in histology coding rules.  Continue through the rules.");
        rule.getNotes().add("The tumor may or may not invade into the colon wall or adjacent tissue.");
        rule.getNotes().add("These rules are hierarchical. Only use this rule when previous rules do not apply.");
        _rules.add(rule);

        // Rule M9	Abstract a single primary when a subsequent tumor arises at the anastomotic site AND:
        // •	 The subsequent tumor occurs less than or equal to 24 months after original tumor resection OR
        // •	 The tumor arises in colon/rectal wall and/or surrounding tissue; there is no involvement of the mucosa OR
        // •	 The pathologist or clinician documents an anastomotic recurrence
        // TODO
        rule = new MphRule(MphConstants.MP_2018_COLON_GROUP_ID, "M9") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
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

        // Rule M10	Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than one year after the original diagnosis or last recurrence.
        rule = new MphRule(MphConstants.MP_2018_COLON_GROUP_ID, "M10") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                int diff = GroupUtility.verifyYearsApart(i1, i2, 1);
                if (-1 == diff) {
                    result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". There is not enough diagnosis date information.");
                }
                else if (1 == diff)
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Are there tumors diagnosed more than one (1) year apart?");
        rule.setReason("Tumors diagnosed more than one (1) year apart are multiple primaries.");
        rule.getNotes().add("Clinically disease-free means that there was no evidence of recurrence on follow-up.");
        rule.getNotes().add("  • Colonoscopies are NED");
        rule.getNotes().add("  • Scans are NED");
        rule.getNotes().add("When there is a recurrence less than or equal to one year of diagnosis, the “clock” starts over. The time interval is calculated from the date of last recurrence. In other words, the patient must have been disease-free for greater than one year from the date of the last recurrence.");
        rule.getNotes().add("When the first course of treatment was a polypectomy only, this rule means there were no recurrences for greater than one year.");
        rule.getNotes().add("When the first course of treatment was a colectomy or A&P resection, there were no anastomotic recurrences for greater than one year.");
        rule.getNotes().add("When it is unknown/not documented whether the patient had a recurrence, default to date of diagnosis to compute the time interval.");
        rule.getNotes().add("The physician may state this is a recurrence, meaning the patient had a previous colon tumor and now has another colon tumor. Follow the rules; do not attempt to interpret the physician’s statement.");
        _rules.add(rule);

        // Rule M11	Abstract a single primary (the invasive) when an in situ tumor is diagnosed after an invasive tumor.
        rule = new MphRuleInSituAfterInvasive(MphConstants.MP_2018_COLON_GROUP_ID, "M11");
        rule.getNotes().add("The rules are hierarchical. Only use this rule when none of the previous rules apply.");
        rule.getNotes().add("The tumors may be a NOS and a subtype/variant of that NOS. See Table 1 in the Equivalent Terms and Definitions for listings of NOS and subtype/variants.");
        rule.getNotes().add("The in situ is recorded as a recurrence for those registrars who collect recurrence data.");
        _rules.add(rule);

        // Rule M12	Abstract a single primary (the invasive) when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor.
        rule = new MphRuleInvasiveAfterInSituLess60Days(MphConstants.MP_2018_COLON_GROUP_ID, "M12", false);
        rule.getNotes().add("The rules are hierarchical. Only use this rule when previous rules do not apply.");
        rule.getNotes().add("Change behavior code on original abstract from /2 to /3. Do not change date of diagnosis.");
        rule.getNotes().add("If the case has already been submitted to the central registry, report all changes.");
        rule.getNotes().add("The physician may stage both tumors because staging and determining multiple primaries are done for different reasons. Staging determines which treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).");
        rule.getNotes().add("See the COC and SEER manuals for instructions on coding data items such as Date of Diagnosis, Accession Year and Sequence Number.");
        _rules.add(rule);

        // Rule M13	Abstract multiple primaries when an invasive tumor occurs more than 60 days after an in situ tumor.
        rule = new MphRuleInvasiveAfterInSituGreaterThan60Days(MphConstants.MP_2018_COLON_GROUP_ID, "M13", false);
        rule.getNotes().add("Abstract both the invasive and in situ tumors.");
        rule.getNotes().add("Abstract as multiple primaries even if physician states the invasive tumor is disease recurrence or progression.");
        rule.getNotes().add("This rule is based on long-term epidemiologic studies of recurrence intervals. The specialty medical experts (SMEs) reviewed and approved these rules.  Many of the SMEs were also authors, co-authors, or editors of the AJCC Staging Manual.");
        _rules.add(rule);

        // Rule M14	Abstract a single primary when tumors do not meet any of the above criteria.
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_COLON_GROUP_ID, "M14");
        rule.getNotes().add("Use caution when applying this default rule.  Please confirm that you have not overlooked an applicable rule.");
        _rules.add(rule);
    }
}

