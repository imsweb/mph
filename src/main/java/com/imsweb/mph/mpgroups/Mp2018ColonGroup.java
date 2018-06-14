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
    (Excludes lymphoma and leukemia M9590 – M9992 and Kaposi sarcoma M9140)

    Note 1: 	Multiple tumors may be a single primary or multiple primaries.
    Note 2: 	Collision tumors are counted as two individual tumors for the purpose of determining multiple primaries.  Collision tumors were originally two separate tumors that arose in close proximity. As the tumors increased in size, they merged or overlapped each other.  Use the multiple tumors module.

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
        •	Present in more than one segment of colon: code C189 colon, NOS
        •	Present in colon and rectosigmoid OR colon and rectum: code C199 rectosigmoid junction
        •	Present in colon and small intestine: code C260 intestinal tract, NOS (there is no code for large and small bowel)
        •	Present in colon and small intestine (may also involve rectum): code C269 gastrointestinal tract, NOS
        Example: 	The patient has a diagnosis of FAP.  The operative report and physician’s documentation say that polyps with adenocarcinoma were present in specimens removed from the ascending colon and the sigmoid colon. The ascending and sigmoid colon are part of the large bowel. Code the primary site C189 colon NOS.

    Rule M4	Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than one year after the original diagnosis or last recurrence.
        Note 1:	Clinically disease-free means that there was no evidence of recurrence on follow-up.
        •	Colonoscopies are NED
        •	Scans are NED
        Note 2:	When there is a recurrence less than or equal to one year of diagnosis, the “clock” starts over. The time interval is calculated from the date of last recurrence. In other words, the patient must have been disease-free for greater than one year from the date of the last recurrence.
        Note 3:	When the first course of treatment was a polypectomy only, this rule means there were no recurrences for greater than one year.
        Note 4:	When the first course of treatment was a colectomy or A&P resection, there were no anastomotic recurrences for greater than one year.
        Note 5:	When it is unknown/not documented whether the patient had a recurrence, default to date of diagnosis to compute the time interval.
        Note 6:	The physician may state this is a recurrence, meaning the patient had a previous colon tumor and now has another colon tumor. Follow the rules; do not attempt to interpret the physician’s statement.

    Rule M5	Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 1 in the Equivalent Terms and Definitions. Tumors may be:
        •	Simultaneous OR
        •	Original and subsequent
        Note:	 Each row in the table is a distinctly different histology.

    Rule M6	Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 1 in the Equivalent Terms and Definitions. Tumors may be
        •	Simultaneous OR
        •	Original and subsequent
        Note: 	The tumors may be subtypes/variants of the same or different NOS histologies.
        •	Same NOS: Medullary carcinoma NOS 8510/3 and tubulopapillary adenocarcinoma 8263/3 are both subtypes of adenocarcinoma NOS 8140/3 but are distinctly different histologies. Abstract multiple primaries.
        •	Different NOS: Goblet cell carcinoid 8243/3 is a subtype of mixed adenoneuroendocrine carcinoma 8244/3; somatostatin-producing NET 8156/3 is a subtype of neuroendocrine tumor Grade 1 (G1) 8240/3. They are distinctly different histologies. Abstract multiple primaries.

    Rule M7	Abstract multiple primaries when a subsequent tumor arises at the anastomotic site and one tumor is a NOS and the other is a subtype/variant of the NOS.
        Example: 	The original tumor was adenocarcinoma NOS 8140. The patient had a hemicolectomy. There was a recurrence at the anastomotic site diagnosed exactly as mucinous adenocarcinoma 8480. Mucinous adenocarcinoma is a subtype/variant of the NOS adenocarcinoma, but they are two different histologies. Code two primaries, one for the original adenocarcinoma NOS and another for the subsequent anastomotic site mucinous adenocarcinoma.

    Rule M8	Abstract a single primary when a subsequent tumor arises at the anastomotic site AND
        •	The tumor arises in colon/rectal wall and/or surrounding tissue; there is no involvement of the mucosa AND/OR
        •	The pathologist or clinician documents an anastomotic recurrence
        •	The original tumor was coded to adenocarcinoma in a polyp and a subsequent anastomotic site tumor is an adenocarcinoma
        Note 1:	The physician may stage the subsequent tumor because the depth of invasion determines the second course of treatment.
        Note 2:	These tumors are a single primary/recurrence.  Registrars that collect recurrence information should record the information in the recurrence fields.

    Rule M9	Abstract multiple primaries when a subsequent tumor arises at the anastomotic site AND
        •	Arises in the mucosa AND
        •	No documentation of an anastomotic recurrence
        Note 1:	The tumor may or may not invade into the colon wall or adjacent tissue.
        Note 2:	These rules are hierarchical. Only use this rule when previous rules do not apply.
        Note 3:	This rule does not apply to GIST.  GISTs only start in the wall; never in the mucosa.

    Rule M10	Abstract multiple primaries when separate, non-contiguous tumors are present in sites where the site codes differ at the second CXxx, third CxXx or the fourth character C18X.
        Note 1:	Definition of separate/non-contiguous tumors: at least two malignancies which do not overlap/merge.
        Note 2:	The rules are hierarchical. Only use this rule if the patient does not have FAP.
        Note 3:	Differences at the fourth character include different segments of the colon. Abstract a primary for each separate non-contiguous tumor in a different segment of the colon.
        Example: 	The patient has adenocarcinoma in situ in a sigmoid polyp and mucinous adenocarcinoma in a polyp in the descending colon, the site code differs at the fourth character (sigmoid C187 and descending C186). Code two primaries, one for the sigmoid and another for the descending colon.
        Note 4:	Differences at either the second or third characters are different primary sites/multiple primaries.
        Example 1: 	Breast C50x and colon C18x
        Example 2: 	Colon C18x and rectum C209
        Note: This rule does not apply to a single overlapping malignancy of colon and rectum.

    Rule M11	Abstract a single primary when there are simultaneous combinations of:
        •	A de novo (formerly called “frank”) carcinoma AND
            	A carcinoma in a polyp
        •	A NOS AND
            	A subtype/variant of that NOS
        Note: 	The NOS may be in situ and the subtype/variant malignant/invasive OR the NOS may be malignant/invasive and the subtype/variant in situ.
        •	Adenocarcinoma in multiple polyps 8221
        •	An in situ AND
            	An invasive tumor
        Note: 	The in situ may be in a polyp and the invasive may be de novo OR the in situ may be de novo and the invasive in a polyp.

    Rule M12	Abstract a single primary when an in situ tumor is diagnosed after an invasive tumor.
        Note 1:	The rules are hierarchical. Only use this rule when none of the previous rules apply.
        Note 2:	The tumors may be a NOS and a subtype/variant of that NOS. See Table 1 in the Equivalent Terms and Definitions for listings of NOS and subtype/variants.
        Note 3:	Once the patient has an invasive tumor, the in situ is recorded as a recurrence for those registrars who collect recurrence data.

    Rule M13	Abstract a single primary (the invasive) when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor.
        Note 1:	The rules are hierarchical. Only use this rule when previous rules do not apply.
        Note 2:	When the case has been abstracted, change behavior code on original abstract from /2 to /3. Do not change date of diagnosis.
        Note 3:	 If the case has already been submitted to the central registry, report all changes.
        Note 4:	The physician may stage both tumors because staging and determining multiple primaries are done for different reasons. Staging determines which treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).
        Note 5:	See the COC and SEER manuals for instructions on coding other data items such as Date of Diagnosis, Accession Year and Sequence Number.

    Rule M14	Abstract multiple primaries when an invasive tumor occurs more than 60 days after an in situ tumor.
        Note 1:	The rules are hierarchical. Only use this rule if none of the previous rules apply.
        Note 2:	Abstract both the invasive and in situ tumors.
        Note 3:	Abstract as multiple primaries even if physician states the invasive tumor is disease recurrence or progression.
        Note 4:	This rule is based on long-term epidemiologic studies of recurrence intervals. The specialty medical experts (SMEs) reviewed and approved these rules.  Many of the SMEs were also authors, co-authors, or editors of the AJCC Staging Manual.

    Rule M15	Abstract a single primary when tumors do not meet any of the above criteria.
    */

    // TODO - Question M7 - Doesn't rule M7 go to multiple primaries, while M11 uses the same logic to go to single primary?
    // TODO - Question M7, M8, M9 - How do you determine an "anastomotic site"?
    // TODO - Question M8 -	How do you determine "The tumor arises in colon/rectal wall and/or surrounding tissue; there is no involvement of the mucosa"?
    // TODO - Question M8 -	How do you determine "The pathologist or clinician documents an anastomotic recurrence"?
    // TODO - Question M9 - How do you determine "Arises in the mucosa"?
    // TODO - Question M9 - How do you determine "No documentation of an anastomotic recurrence"?
    // TODO - Question M11 - Should this rule actually work like this?:
    //                      A de novo (formerly called “frank”) carcinoma AND a carcinoma in a polyp; OR
    //                  	A NOS AND a subtype/variant of that NOS; OR
    //                      Adenocarcinoma in multiple polyps 8221; OR
    //                      An in situ AND an invasive tumor
    // TODO - Question M11 - How do I determine a de novo (formerly called “frank”) carcinoma?



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
        // TODO
        MphRule rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_COLON_GROUP_ID, "M3");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("A diagnosis of adenomatous polyposis coli (familial polyposis/FAP) is made when the patient has greater than 100 adenomatous polyps. Polyps with adenocarcinoma and benign polyps will be present. Because there are many polyps, the pathologist does not examine every polyp.");
        rule.getNotes().add("In situ /2 and malignant /3 adenocarcinoma in polyps, malignancies with remnants of a polyp as well as de novo (previously called frank) malignancies may be present in multiple segments of the colon or in the colon and rectum.  Polyposis may be present in other GI sites such as stomach (a de novo does not have to be present; all adenocarcinoma may be in polyps).");
        rule.getNotes().add("FAP is a genetic disease. The characteristics of FAP are numerous precancerous polyps in the colon and rectum when the patient reaches puberty. If not treated, the polyps typically become malignant. Patients often have total colectomies.");
        rule.getNotes().add("Multiple polyps in the colorectum is not equivalent to FAP.");
        rule.getNotes().add("Code primary site as follows:");
        rule.getNotes().add("  • Present in more than one segment of colon: code C189 colon, NOS");
        rule.getNotes().add("  • Present in colon and rectosigmoid OR colon and rectum: code C199 rectosigmoid junction");
        rule.getNotes().add("  • Present in colon and small intestine: code C260 intestinal tract, NOS (there is no code for large and small bowel)");
        rule.getNotes().add("  • Present in colon and small intestine (may also involve rectum): code C269 gastrointestinal tract, NOS");
        rule.getExamples().add("The patient has a diagnosis of FAP.  The operative report and physician’s documentation say that polyps with adenocarcinoma were present in specimens removed from the ascending colon and the sigmoid colon. The ascending and sigmoid colon are part of the large bowel. Code the primary site C189 colon NOS.");
        _rules.add(rule);

        // Rule M4	Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than one year after the original diagnosis or last recurrence.
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_COLON_GROUP_ID, "M4") {
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

        // Rule M5	Abstract multiple primaries when separate/non-contiguous tumors are on different rows in Table 1 in the Equivalent Terms and Definitions. Tumors may be:
        // •	Simultaneous OR
        // •	Original and subsequent
        rule = new MphRuleDifferentRowsInTable(MphConstants.MP_2018_COLON_GROUP_ID, "M5", MphConstants.COLON_2018_TABLE1, false);
        rule.setQuestion("Are separate/non-contiguous tumors on different rows in Table 1 in the Equivalent Terms and Definitions?");
        rule.setReason("Separate/non-contiguous tumors that are on different rows in Table 1 in the Equivalent Terms and Definitions are multiple primaries.");
        rule.getNotes().add("Each row in the table is a distinctly different histology.");
        _rules.add(rule);

        // Rule M6	Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 1 in the Equivalent Terms and Definitions. Tumors may be
        // •	Simultaneous OR
        // •	Original and subsequent
        rule = new MphRuleTwoOrMoreDifferentSubTypesInTable(MphConstants.MP_2018_COLON_GROUP_ID, "M6", MphConstants.COLON_2018_TABLE1,false);
        rule.setQuestion("Are separate/non-contiguous tumors two or more different subtypes/variants in Column 3, Table 1 in the Equivalent Terms and Definitions?");
        rule.setReason("Separate/non-contiguous tumors that are two or more different subtypes/variants in Column 3, Table 1 in the Equivalent Terms and Definitions are multiple primaries.");
        rule.getNotes().add("The tumors may be subtypes/variants of the same or different NOS histologies.");
        rule.getNotes().add("  • Same NOS: Medullary carcinoma NOS 8510/3 and tubulopapillary adenocarcinoma 8263/3 are both subtypes of adenocarcinoma NOS 8140/3 but are distinctly different histologies. Abstract multiple primaries.");
        rule.getNotes().add("  • Different NOS: Goblet cell carcinoid 8243/3 is a subtype of mixed adenoneuroendocrine carcinoma 8244/3; somatostatin-producing NET 8156/3 is a subtype of neuroendocrine tumor Grade 1 (G1) 8240/3. They are distinctly different histologies. Abstract multiple primaries.");
        _rules.add(rule);

        // Rule M7	Abstract multiple primaries when a subsequent tumor arises at the anastomotic site and one tumor is a NOS and the other is a subtype/variant of the NOS.
        rule = new MphRule(MphConstants.MP_2018_COLON_GROUP_ID, "M7") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                String hist1 = i1.getHistology(), hist2 = i2.getHistology();

                String icd1 = i1.getHistology() + "/" + i1.getBehavior(), icd2 = i2.getHistology() + "/" + i2.getBehavior();
                List<String> subTypes1 = MphConstants.COLON_2018_TABLE1.get(icd1);
                if (subTypes1 == null) subTypes1 = MphConstants.COLON_2018_TABLE1.get(i1.getHistology());
                List<String> subTypes2 = MphConstants.COLON_2018_TABLE1.get(icd2);
                if (subTypes2 == null) subTypes2 = MphConstants.COLON_2018_TABLE1.get(i2.getHistology());

                // •	A NOS AND a subtype/variant of that NOS
                if (((subTypes1 != null) && (subTypes1.contains(icd2) || subTypes1.contains(hist2))) ||
                    ((subTypes2 != null) && (subTypes2.contains(icd1) || subTypes2.contains(hist1)))) {
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                }
                return result;
            }
        };
        rule.setQuestion("Did a subsequent tumor arise at the anastomotic site and one tumor is a NOS and the other is a subtype/variant of the NOS?");
        rule.setReason("A subsequent tumor arises at the anastomotic site and one tumor is a NOS and the other is a subtype/variant of the NOS is multiple primaries");
        rule.getExamples().add("The original tumor was adenocarcinoma NOS 8140. The patient had a hemicolectomy. There was a recurrence at the anastomotic site diagnosed exactly as mucinous adenocarcinoma 8480. Mucinous adenocarcinoma is a subtype/variant of the NOS adenocarcinoma, but they are two different histologies. Code two primaries, one for the original adenocarcinoma NOS and another for the subsequent anastomotic site mucinous adenocarcinoma.");
        _rules.add(rule);

        // Rule M8	Abstract a single primary when a subsequent tumor arises at the anastomotic site AND
        // •	The tumor arises in colon/rectal wall and/or surrounding tissue; there is no involvement of the mucosa AND/OR
        // •	The pathologist or clinician documents an anastomotic recurrence
        // •	The original tumor was coded to adenocarcinoma in a polyp and a subsequent anastomotic site tumor is an adenocarcinoma
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_COLON_GROUP_ID, "M8");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The physician may stage the subsequent tumor because the depth of invasion determines the second course of treatment.");
        rule.getNotes().add("These tumors are a single primary/recurrence.  Registrars that collect recurrence information should record the information in the recurrence fields.");
        _rules.add(rule);

        // Rule M9	Abstract multiple primaries when a subsequent tumor arises at the anastomotic site AND
        // •	Arises in the mucosa AND
        // •	No documentation of an anastomotic recurrence
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_COLON_GROUP_ID, "M9");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The tumor may or may not invade into the colon wall or adjacent tissue.");
        rule.getNotes().add("These rules are hierarchical. Only use this rule when previous rules do not apply.");
        rule.getNotes().add("This rule does not apply to GIST.  GISTs only start in the wall; never in the mucosa.");
        _rules.add(rule);

        // Rule M10	Abstract multiple primaries when separate, non-contiguous tumors are present in sites where the site codes differ at the second CXxx, third CxXx or the fourth character C18X.
        rule = new MphRuleTopography234Code(MphConstants.MP_2018_COLON_GROUP_ID, "M10");
        rule.getNotes().add("Definition of separate/non-contiguous tumors: at least two malignancies which do not overlap/merge.");
        rule.getNotes().add("The rules are hierarchical. Only use this rule if the patient does not have FAP.");
        rule.getNotes().add("Differences at the fourth character include different segments of the colon. Abstract a primary for each separate non-contiguous tumor in a different segment of the colon.");
        rule.getExamples().add("The patient has adenocarcinoma in situ in a sigmoid polyp and mucinous adenocarcinoma in a polyp in the descending colon, the site code differs at the fourth character (sigmoid C187 and descending C186). Code two primaries, one for the sigmoid and another for the descending colon.");
        rule.getNotes().add("Differences at either the second or third characters are different primary sites/multiple primaries.");
        rule.getExamples().add("Breast C50x and colon C18x");
        rule.getExamples().add("Colon C18x and rectum C209");
        rule.getNotes().add("This rule does not apply to a single overlapping malignancy of colon and rectum.");
        _rules.add(rule);

        // Rule M11	Abstract a single primary when there are simultaneous combinations of:
        // •	A de novo (formerly called “frank”) carcinoma AND a carcinoma in a polyp
        // •	A NOS AND a subtype/variant of that NOS
        // •	Adenocarcinoma in multiple polyps 8221
        // •	An in situ AND An invasive tumor
        // TODO
        rule = new MphRule(MphConstants.MP_2018_COLON_GROUP_ID, "M11") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                int sixtyDaysApart = GroupUtility.verifyDaysApart(i1, i2, 60);
                if (-1 == sixtyDaysApart) {
                    result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                    result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". There is not enough diagnosis date information.");
                }
                else if (0 == sixtyDaysApart) {
                    String beh1 = i1.getBehavior(), beh2 = i2.getBehavior();
                    String hist1 = i1.getHistology(), hist2 = i2.getHistology();
                    String icd1 = i1.getHistology() + "/" + i1.getBehavior(), icd2 = i2.getHistology() + "/" + i2.getBehavior();
                    List<String> subTypes1 = MphConstants.COLON_2018_TABLE1.get(icd1);
                    if (subTypes1 == null) subTypes1 = MphConstants.COLON_2018_TABLE1.get(i1.getHistology());
                    List<String> subTypes2 = MphConstants.COLON_2018_TABLE1.get(icd2);
                    if (subTypes2 == null) subTypes2 = MphConstants.COLON_2018_TABLE1.get(i2.getHistology());

                    // •	A de novo (formerly called “frank”) carcinoma AND a carcinoma in a polyp
                    if ((MphConstants.POLYP.contains(hist1) && (false)) ||
                            (MphConstants.POLYP.contains(hist2) && (false))) {

                    }
                    // •	A NOS AND a subtype/variant of that NOS
                    else if (((subTypes1 != null) && (subTypes1.contains(icd2) || subTypes1.contains(hist2))) ||
                            ((subTypes2 != null) && (subTypes2.contains(icd1) || subTypes2.contains(hist1)))) {
                        result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                    }
                    // •	Adenocarcinoma in multiple polyps 8221
                    else if (hist1.equals("8221") && hist2.equals("8221")) {
                        result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                    }
                    // •	An in situ AND An invasive tumor
                    else if ((beh1.equals(MphConstants.INSITU) && beh2.equals(MphConstants.MALIGNANT)) ||
                            (beh2.equals(MphConstants.INSITU) && beh1.equals(MphConstants.MALIGNANT))) {
                        result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                    }
                }
                return result;
            }
        };
        rule.setQuestion("Is this a simultaneous combination of de novo carcinoma and carcinoma in a polyp, NOS and subtype of that NOS, Adenocarcinoma in multiple polyps, or in situ and invasive tumor?");
        rule.setReason("A simultaneous combination of de novo carcinoma and carcinoma in a polyp, NOS and subtype of that NOS, Adenocarcinoma in multiple polyps, or in situ and invasive tumor is single primary.");
        rule.getNotes().add("The NOS may be in situ and the subtype/variant malignant/invasive OR the NOS may be malignant/invasive and the subtype/variant in situ.");
        rule.getNotes().add("The in situ may be in a polyp and the invasive may be de novo OR the in situ may be de novo and the invasive in a polyp.");
        _rules.add(rule);

        // Rule M12	Abstract a single primary when an in situ tumor is diagnosed after an invasive tumor.
        rule = new MphRuleInSituAfterInvasive(MphConstants.MP_2018_COLON_GROUP_ID, "M12");
        rule.getNotes().add("The rules are hierarchical. Only use this rule when none of the previous rules apply.");
        rule.getNotes().add("The tumors may be a NOS and a subtype/variant of that NOS. See Table 1 in the Equivalent Terms and Definitions for listings of NOS and subtype/variants.");
        rule.getNotes().add("Once the patient has an invasive tumor, the in situ is recorded as a recurrence for those registrars who collect recurrence data.");
        _rules.add(rule);

        // Rule M13	Abstract a single primary (the invasive) when an invasive tumor is diagnosed less than or equal to 60 days after an in situ tumor.
        rule = new MphRuleInvasiveAfterInSituLess60Days(MphConstants.MP_2018_COLON_GROUP_ID, "M13", false);
        rule.getNotes().add("The rules are hierarchical. Only use this rule when previous rules do not apply.");
        rule.getNotes().add("When the case has been abstracted, change behavior code on original abstract from /2 to /3. Do not change date of diagnosis.");
        rule.getNotes().add("If the case has already been submitted to the central registry, report all changes.");
        rule.getNotes().add("The physician may stage both tumors because staging and determining multiple primaries are done for different reasons. Staging determines which treatment would be most effective. Determining multiple primaries is done to stabilize the data for the study of epidemiology (long-term studies done on incidence, mortality, and causation of a disease with the goal of reducing or eliminating that disease).");
        rule.getNotes().add("See the COC and SEER manuals for instructions on coding other data items such as Date of Diagnosis, Accession Year and Sequence Number.");
        _rules.add(rule);

        // Rule M14	Abstract multiple primaries when an invasive tumor occurs more than 60 days after an in situ tumor.
        rule = new MphRuleInvasiveAfterInSituGreaterThan60Days(MphConstants.MP_2018_COLON_GROUP_ID, "M14", false);
        rule.getNotes().add("The rules are hierarchical. Only use this rule if none of the previous rules apply.");
        rule.getNotes().add("Abstract both the invasive and in situ tumors.");
        rule.getNotes().add("Abstract as multiple primaries even if physician states the invasive tumor is disease recurrence or progression.");
        rule.getNotes().add("This rule is based on long-term epidemiologic studies of recurrence intervals. The specialty medical experts (SMEs) reviewed and approved these rules.  Many of the SMEs were also authors, co-authors, or editors of the AJCC Staging Manual.");
        _rules.add(rule);

        // Rule M15	Abstract a single primary when tumors do not meet any of the above criteria.
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_COLON_GROUP_ID, "M15");
        _rules.add(rule);





        /*
        // M3 - Adenocarcinoma in adenomatous polyposis coli (familial polyposis) with one or more malignant polyps is a single primary.
        MphRule rule = new MphRule(MphConstants.MP_2007_COLON_GROUP_ID, "M3") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (GroupUtility.differentCategory(i1.getHistology(), i2.getHistology(), MphConstants.FAMILLIAL_POLYPOSIS, MphConstants.POLYP) && (MphConstants.MALIGNANT.equals(i1.getBehavior())
                        || MphConstants.MALIGNANT.equals(i2.getBehavior())))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                return result;
            }
        };
        rule.setQuestion("Is there adenocarcinoma in adenomatous polyposis coli (familialpolyposis) with one or more malignant polyps?");
        rule.setReason("Adenocarcinoma in adenomatous polyposis coli (familial polyposis) with one or more malignant polyps is a single primary.");
        rule.getNotes().add("Tumors may be present in multiple segments of the colon or in a single segment of the colon.");
        _rules.add(rule);

        //M4- Tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx), third (Cx?x) and/or fourth (C18?) character are multiple primaries.
        rule = new MphRule(MphConstants.MP_2007_COLON_GROUP_ID, "M4") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (!i1.getPrimarySite().equals(i2.getPrimarySite()))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Are there tumors in sites withICD-O-3 topography codes that are different at the second (C?xx) , third (Cx?x) and/or fourth (C18?) character?");
        rule.setReason("Tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx), third (Cx?x) and/or fourth (C18?) character are multiple primaries.");
        _rules.add(rule);

        //M5- Tumors diagnosed more than one (1) year apart are multiple primaries.
        rule = new MphRule(MphConstants.MP_2007_COLON_GROUP_ID, "M5") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                int diff = GroupUtility.verifyYearsApart(i1, i2, 1);
                if (-1 == diff) {
                    result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". There is no enough diagnosis date information.");
                }
                else if (1 == diff)
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Are there tumors diagnosed more than one (1) year apart?");
        rule.setReason("Tumors diagnosed more than one (1) year apart are multiple primaries.");
        _rules.add(rule);

        //M6- An invasive tumor following an insitu tumor more than 60 days after diagnosis is a multiple primary.
        rule = new MphRuleBehavior(MphConstants.MP_2007_COLON_GROUP_ID, "M6");
        _rules.add(rule);

        //M7- A frank malignant or in situ adenocarcinoma and an insitu or malignant tumor in a polyp are a single primary.
        rule = new MphRule(MphConstants.MP_2007_COLON_GROUP_ID, "M7") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                List<String> adenocarcinoma = new ArrayList<>(MphConstants.ADENOCARCINOMA_SPECIFIC);
                adenocarcinoma.addAll(MphConstants.ADENOCARCINOMA_NOS);
                if (GroupUtility.differentCategory(i1.getHistology(), i2.getHistology(), adenocarcinoma, MphConstants.POLYP))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                return result;
            }
        };
        rule.setQuestion("Is there a frank malignant or in situ adenocarcinoma and an in situ ormalignant tumor in a polyp?");
        rule.setReason("A frank malignant or in situ adenocarcinoma and an in situ or malignant tumor in a polyp are a single primary.");
        _rules.add(rule);

        //M8 -
        rule = new MphRule(MphConstants.MP_2007_COLON_GROUP_ID, "M8") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                String hist1 = i1.getHistology(), hist2 = i2.getHistology();
                List<String> nosList = Arrays.asList("8000", "8010", "8140", "8800");
                if ((nosList.contains(hist1) && MphConstants.NOS_VS_SPECIFIC.containsKey(hist1) && MphConstants.NOS_VS_SPECIFIC.get(hist1).contains(hist2)) || (nosList.contains(hist2)
                        && MphConstants.NOS_VS_SPECIFIC.containsKey(hist2) && MphConstants.NOS_VS_SPECIFIC.get(hist2).contains(hist1)))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                return result;
            }
        };
        rule.setQuestion("Is there cancer/malignant neoplasm, NOS (8000) and another is a specific histology? or\n" +
                "Is there carcinoma, NOS (8010) and another is a specific carcinoma? or\n" +
                "Is there adenocarcinoma, NOS (8140) and another is a specific adenocarcinoma? or\n" +
                "Is there sarcoma, NOS (8800) and another is a specific sarcoma?");
        rule.setReason("Abstract as a single primary when one tumor is:\n" +
                "- Cancer/malignant neoplasm, NOS (8000) and another is a specific histology or\n" +
                "- Carcinoma, NOS (8010) and another is a specific carcinoma or\n" +
                "- Adenocarcinoma, NOS (8140) and another is a specific adenocarcinoma or\n" +
                "- Sarcoma, NOS (8800) and another is a specific sarcoma");
        _rules.add(rule);

        //M9- Multiple insitu and/or malignant polyps are a single primary.
        rule = new MphRule(MphConstants.MP_2007_COLON_GROUP_ID, "M9") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (MphConstants.POLYP.containsAll(Arrays.asList(i1.getHistology(), i2.getHistology())))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                return result;
            }
        };
        rule.setQuestion("Are there multiple in situ and /or malignant polyps?");
        rule.setReason("Multiple in situ and/or malignant polyps are a single primary.");
        rule.getNotes().add("Includes all combinations of adenomatous, tubular, villous, and tubulovillous adenomas or polyps.");
        _rules.add(rule);

        //M10- Tumors with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.
        rule = new MphRuleHistologyCode(MphConstants.MP_2007_COLON_GROUP_ID, "M10");
        _rules.add(rule);

        //M11- Tumors that do not meet any of the criteria are abstracted as a single primary.
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2007_COLON_GROUP_ID, "M11");
        rule.getNotes().add("When an invasive tumor follows an in situ tumor within 60 days, abstract as a single primary.");
        rule.getNotes().add("All cases covered by Rule M11 are in the same segment of the colon.");
        _rules.add(rule);
        */
    }
}

