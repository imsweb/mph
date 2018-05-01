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

    Rule M3     Abstract a single primary when
                • The diagnosis is adenomatous polyposis coli (familial polyposis/FAP) OR
                • There is no diagnosis of FAP BUT
                    o >100 polyps are documented AND
                    o Adenocarcinoma (in situ /2 or invasive /3) is present in at least one polyp
                Note 1: A diagnosis of adenomatous polyposis coli (familial polyposis/FAP) is made when the patient has > 100 adenomatous polyps. Polyps with adenocarcinoma and benign polyps will be present. Because there are many polyps, the pathologist does not examine every polyp.
                Note 2: In situ (/2) and malignant (/3) adenocarcinoma in polyps, malignancies with remnants of a polyp as well as de novo (previously called frank) malignancies may be present in multiple segments of the colon or in the colon and rectum. Polyposis may be present in other GI sites such as stomach. (a de novo does not have to be present. All adenocarcinoma may be in polyps)
                Note 3: FAP is a genetic disease. The characteristics of FAP are numerous precancerous polyps in the colon and rectum when the patient reaches puberty. If not treated, the polyps typically become malignant. In summary, multiple polyps in the colorectum is not equivalent to FAP. FAP is a genetic disorder which gives rise to more than a hundred polyps. Patients often have total colectomies.
                Note 4: Code primary site as follows
                • Present in more than one segment of colon: code C189 colon, NOS
                • Present in colon and rectosigmoid OR colon and rectum: code C199 rectosigmoid junction
                • Present in colon and small intestine: code C260 intestinal tract, NOS (there is no code for large and small bowel)
                • Present in colon and small intestine (may also involve rectum): code C269 gastrointestinal tract, NOS
                Example: The patient has a diagnosis of FAP. The operative report and physician’s documentation say that polyps with adenocarcinoma were present in specimens removed from the ascending colon and the sigmoid colon. The ascending and sigmoid colon are part of the large bowel. Code the primary site C189 colon NOS.

    Rule M4     Abstract multiple primaries when tumors are diagnosed more than 1 year apart.
                Note 1: The rules are hierarchical. Do not use this rule when rules M1-3 apply.
                Note 2: The time interval means the patient has been clinically disease-free for more than one year.
                • Clinically disease-free means there are no clinical evidence of disease. Colonoscopies, scans, and all other work-ups show no evidence of disease (NED)
                • When the first course of treatment was a polypectomy only, this rule means there were no recurrences in the same segment of colon for >1 year
                • When the first course of treatment was a colectomy or A&P resection, there were no anastomotic recurrences for >1 year
                Note 3: When the patient has a recurrence less than or equal to 1 year after the original diagnosis, the “clock” starts over. The one-year disease-free interval is no longer computed from the date of diagnosis, it starts from the date of the last known recurrence. In other words, the patient must have been disease-free for >1 year after the last recurrence.
                Note 4: When it is unknown whether there was a recurrence, default to date of diagnosis to compute the >1-year interval.

    Rule M5     Abstract multiple primaries when a subsequent tumor with a different histology arises at the anastomotic site such as:
                • Two different subtypes/variants of a NOS
                Example: The original tumor was micropapillary carcinoma 8265 and the tumor at the anastomosis was medullary carcinoma 8510. Although both are subtype/variants of adenocarcinoma, they are different histologies. Code two primaries, one for the original micropapillary tumor and another for the medullary tumor at the anastomotic site.
                • One tumor is a NOS and the other is a subtype/variant of the NOS
                Example: The original tumor was adenocarcinoma NOS 8140. The patient had a hemicolectomy. There was a recurrence at the anastomotic site diagnosed exactly as mucinous adenocarcinoma 8480. Mucinous adenocarcinoma is a subtype/variant of the NOS adenocarcinoma, but they are two different histologies. Code two primaries, one for the original adenocarcinoma NOS and another for the subsequent anastomotic site mucinous adenocarcinoma.
                • Original tumor is a cancer/adenocarcinoma and recurrence is a sarcoma (leiomyosarcoma or angiosarcoma)
                Example: First course therapy for a rectal primary is surgery and radiation therapy. The diagnosis from first surgical resection was adenocarcinoma 8140. The diagnosis from the anastomotic site tumor was leiomyosarcoma 8890. It is believed the etiology of sarcomas is radiation therapy in lower rectosigmoid and rectal primaries.

    Rule M6     Abstract a single primary when a subsequent tumor arises at the anastomotic site AND
                • The tumor arises in colon/rectal wall and/or surrounding tissue; there is no involvement of the mucosa AND/OR
                • The pathologist or clinician documents an anastomotic recurrence
                Note 1: The physician may stage the subsequent tumor because the depth of invasion determines the second course of treatment.
                Note 2: These tumors are a single primary/recurrence. Registrars that collect recurrence information should record the information in the recurrence fields.

    Rule M7     Abstract multiple primaries when a subsequent tumor arises at the anastomotic site AND
                • Arises in the mucosa AND
                • No documentation of an anastomotic recurrence
                Note 1: The tumor may or may not invade into the colon wall or adjacent tissue.
                Note 2: These rules are hierarchical. Use this rule only when rules M1-M6 do not apply.

    Rule M8     Abstract multiple primaries when separate, non-contiguous tumors (any histology) are present in sites where the ICDO site/topography codes differ at the second character CXxx, third character CxXx or the fourth character C18X.
                Note 1: Definition of separate/non-contiguous tumors: at least two malignancies which do not overlap/merge.
                Note 2: The rules are hierarchical. Do not use this rule if M3 applies (the patient has FAP).
                Note 3: Differences at the fourth character include different segments of the colon. Abstract a primary for each separate noncontiguous tumor in a different segment of the colon.
                Example: The patient has adenocarcinoma in situ in a sigmoid polyp and mucinous adenocarcinoma in a polyp in the descending colon, the ICD-O site/topography code differs at the fourth character (sigmoid C187 and descending C186). Code two primaries, one for the sigmoid and another for the descending colon.
                Note 4: Differences at either the second or third characters are different primary sites/multiple primaries.
                Example 1: Breast C50x and colon C18x
                Example 2: Colon C18x and rectum C209
                Note: This rule does not apply to a single overlapping malignancy of colon and rectum.

    Rule M9     Abstract a single primary when any of the following tumor combinations are present simultaneously:
                • A de novo (formerly called “frank”) carcinoma and carcinoma in a polyp OR
                • A NOS and a variant/subtype of that NOS OR
                    Note: The NOS may be in situ and the variant/subtype malignant/invasive OR the NOS may be malignant/invasive and the variant/subtype in situ
                • Adenocarcinoma in multiple polyps OR
                • An in situ and an invasive tumor OR
                    Note: The in situ may be in a polyp and the invasive may be de novo OR the in situ may be de novo and the invasive in a polyp.
                • The same adenocarcinoma subtype/variant (in situ /2 or invasive /3) in multiple polyps
                Note 1: Rules are hierarchical. Do not use this rule when rule M8 applies (tumors must be in the same segment of colon OR only in the rectum).
                Note 2: See Table 1 in Equivalent Terms and Definitions for adenocarcinoma subtypes/variants.
                Note 3: See Histology Coding Rules for coding instructions.

    Rule M10    Abstract a single primary when there is a subsequent invasive tumor less than or equal to 60 days after an in situ tumor.
                Note 1: Abstract the invasive tumor.
                Note 2: Change behavior code. If an in situ tumor has been reported to central registry, report all changes to central registry.
                Note 3: See the COC and SEER manuals for instructions on coding other data items such as Date of Diagnosis, Accession Year and Sequence Number.
                Note 4: The physician may stage both tumors.

    Rule M11    Abstract multiple primaries when there is an invasive tumor more than 60 day after an in situ tumor AND
                • The patient had a resection of the in situ tumor AND
                • The subsequent invasive tumor occurs more than 60 days after the last in situ tumor was resected
                Note 1: The in situ tumor was resected so the invasive is a new tumor. The resection may be a polypectomy or hemicolectomy.
                Note 2: The purpose of this rule is to ensure that the case is counted as an incidence (invasive) case when incidence data are analyzed
                Note 3: Abstract as multiple primaries even if the medical record/physician states it is recurrence or progression of disease
                Note 4: The physician may stage each of the tumors because the depth of invasion will determine first and second course of treatment.

    Rule M12    Abstract multiple primaries when there are tumors with ICD-O histology/morphology codes that differ at the first (Xxxx) AND/OR second (xXxx) AND/OR third digit (xxXx).

    Rule M13    Abstract multiple primaries when there are
                • Collision tumors OR
                • An in situ and an invasive tumor OR
                • De novo and polyp OR
                • Two de novo separate, non-contiguous malignancies
                Note 1: Do not use this rule when M8, M9 or M12 apply.
                    • M8: Tumors are multiple primaries when they occur
                        • In different sections of colon OR
                        • One in colon, one in rectum
                    • M9: A de novo and polyp in same segment of colon are a single primary
                    • M12: Code multiple primaries when ICD-O codes differ at the first, second, or third digits
                Note 2: Collision tumors were originally two separate tumors that arose in close proximity. As the tumors increase in size, they merge or overlap each other. They are most frequently different histologies.

    Rule M14    Abstract a single primary when rules M1-M13 do not apply.

    */

    // TODO
    public Mp2018ColonGroup() {
        super(MphConstants.MP_2018_COLON_GROUP_ID, MphConstants.MP_2018_COLON_GROUP_NAME, "C180-C189", null, null, "9590-9989, 9140", "2-3,6", "2019-9999");

        // Rule M3 - Abstract a single primary when
        // • The diagnosis is adenomatous polyposis coli (familial polyposis/FAP) OR
        // • There is no diagnosis of FAP BUT
        //     o >100 polyps are documented AND
        //     o Adenocarcinoma (in situ /2 or invasive /3) is present in at least one polyp
        // TODO
        MphRule rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_COLON_GROUP_ID, "M3");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add(
                "A diagnosis of adenomatous polyposis coli (familial polyposis/FAP) is made when the patient has > 100 adenomatous polyps. Polyps with adenocarcinoma and benign polyps will be present. Because there are many polyps, the pathologist does not examine every polyp.");
        rule.getNotes().add(
                "In situ (/2) and malignant (/3) adenocarcinoma in polyps, malignancies with remnants of a polyp as well as de novo (previously called frank) malignancies may be present in multiple segments of the colon or in the colon and rectum. Polyposis may be present in other GI sites such as stomach. (a de novo does not have to be present. All adenocarcinoma may be in polyps)");
        rule.getNotes().add(
                "FAP is a genetic disease. The characteristics of FAP are numerous precancerous polyps in the colon and rectum when the patient reaches puberty. If not treated, the polyps typically become malignant. In summary, multiple polyps in the colorectum is not equivalent to FAP. FAP is a genetic disorder which gives rise to more than a hundred polyps. Patients often have total colectomies.");
        rule.getNotes().add("Code primary site as follows");
        rule.getNotes().add("  • Present in more than one segment of colon: code C189 colon, NOS");
        rule.getNotes().add("  • Present in colon and rectosigmoid OR colon and rectum: code C199 rectosigmoid junction");
        rule.getNotes().add("  • Present in colon and small intestine: code C260 intestinal tract, NOS (there is no code for large and small bowel)");
        rule.getNotes().add("  • Present in colon and small intestine (may also involve rectum): code C269 gastrointestinal tract, NOS");
        rule.getExamples().add(
                "The patient has a diagnosis of FAP. The operative report and physician’s documentation say that polyps with adenocarcinoma were present in specimens removed from the ascending colon and the sigmoid colon. The ascending and sigmoid colon are part of the large bowel. Code the primary site C189 colon NOS.");
        _rules.add(rule);

        // Rule M4 - Abstract multiple primaries when tumors are diagnosed more than 1 year apart.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_COLON_GROUP_ID, "M4");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The rules are hierarchical. Do not use this rule when rules M1-3 apply.");
        rule.getNotes().add("The time interval means the patient has been clinically disease-free for more than one year.");
        rule.getNotes().add("  • Clinically disease-free means there are no clinical evidence of disease. Colonoscopies, scans, and all other work-ups show no evidence of disease (NED)");
        rule.getNotes().add("  • When the first course of treatment was a polypectomy only, this rule means there were no recurrences in the same segment of colon for >1 year");
        rule.getNotes().add("  • When the first course of treatment was a colectomy or A&P resection, there were no anastomotic recurrences for >1 year");
        rule.getNotes().add(
                "When the patient has a recurrence less than or equal to 1 year after the original diagnosis, the “clock” starts over. The one-year disease-free interval is no longer computed from the date of diagnosis, it starts from the date of the last known recurrence. In other words, the patient must have been disease-free for >1 year after the last recurrence.");
        rule.getNotes().add("When it is unknown whether there was a recurrence, default to date of diagnosis to compute the >1-year interval.");
        _rules.add(rule);

        // Rule M5 - Abstract multiple primaries when a subsequent tumor with a different histology arises at the anastomotic site such as:
        // • Two different subtypes/variants of a NOS
        // • One tumor is a NOS and the other is a subtype/variant of the NOS
        // • Original tumor is a cancer/adenocarcinoma and recurrence is a sarcoma (leiomyosarcoma or angiosarcoma)
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_COLON_GROUP_ID, "M5");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("");
        rule.getExamples().add(
                "The original tumor was micropapillary carcinoma 8265 and the tumor at the anastomosis was medullary carcinoma 8510. Although both are subtype/variants of adenocarcinoma, they are different histologies. Code two primaries, one for the original micropapillary tumor and another for the medullary tumor at the anastomotic site.");
        rule.getExamples().add(
                "The original tumor was adenocarcinoma NOS 8140. The patient had a hemicolectomy. There was a recurrence at the anastomotic site diagnosed exactly as mucinous adenocarcinoma 8480. Mucinous adenocarcinoma is a subtype/variant of the NOS adenocarcinoma, but they are two different histologies. Code two primaries, one for the original adenocarcinoma NOS and another for the subsequent anastomotic site mucinous adenocarcinoma.");
        rule.getExamples().add(
                "First course therapy for a rectal primary is surgery and radiation therapy. The diagnosis from first surgical resection was adenocarcinoma 8140. The diagnosis from the anastomotic site tumor was leiomyosarcoma 8890. It is believed the etiology of sarcomas is radiation therapy in lower rectosigmoid and rectal primaries.");
        _rules.add(rule);

        // Rule M6 - Abstract a single primary when a subsequent tumor arises at the anastomotic site AND
        // • The tumor arises in colon/rectal wall and/or surrounding tissue; there is no involvement of the mucosa AND/OR
        // • The pathologist or clinician documents an anastomotic recurrence
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_COLON_GROUP_ID, "M6");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The physician may stage the subsequent tumor because the depth of invasion determines the second course of treatment.");
        rule.getNotes().add("These tumors are a single primary/recurrence. Registrars that collect recurrence information should record the information in the recurrence fields.");
        _rules.add(rule);

        // Rule M7 - Abstract multiple primaries when a subsequent tumor arises at the anastomotic site AND
        // • Arises in the mucosa AND
        // • No documentation of an anastomotic recurrence
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_COLON_GROUP_ID, "M7");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The tumor may or may not invade into the colon wall or adjacent tissue.");
        rule.getNotes().add("These rules are hierarchical. Use this rule only when rules M1-M6 do not apply.");
        _rules.add(rule);

        // Rule M8 - Abstract multiple primaries when separate, non-contiguous tumors (any histology) are present in sites where the ICDO site/topography codes differ at the second character CXxx, third character CxXx or the fourth character C18X.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_COLON_GROUP_ID, "M8");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("Definition of separate/non-contiguous tumors: at least two malignancies which do not overlap/merge.");
        rule.getNotes().add("The rules are hierarchical. Do not use this rule if M3 applies (the patient has FAP).");
        rule.getNotes().add(
                "Differences at the fourth character include different segments of the colon. Abstract a primary for each separate noncontiguous tumor in a different segment of the colon.");
        rule.getExamples().add(
                "The patient has adenocarcinoma in situ in a sigmoid polyp and mucinous adenocarcinoma in a polyp in the descending colon, the ICD-O site/topography code differs at the fourth character (sigmoid C187 and descending C186). Code two primaries, one for the sigmoid and another for the descending colon.");
        rule.getNotes().add("Differences at either the second or third characters are different primary sites/multiple primaries.");
        rule.getExamples().add("Breast C50x and colon C18x");
        rule.getExamples().add("Colon C18x and rectum C209");
        rule.getNotes().add("This rule does not apply to a single overlapping malignancy of colon and rectum.");
        _rules.add(rule);

        // Rule M9 - Abstract a single primary when any of the following tumor combinations are present simultaneously:
        // • A de novo (formerly called “frank”) carcinoma and carcinoma in a polyp OR
        // • A NOS and a variant/subtype of that NOS OR
        //     Note: The NOS may be in situ and the variant/subtype malignant/invasive OR the NOS may be malignant/invasive and the variant/subtype in situ
        // • Adenocarcinoma in multiple polyps OR
        // • An in situ and an invasive tumor OR
        //     Note: The in situ may be in a polyp and the invasive may be de novo OR the in situ may be de novo and the invasive in a polyp.
        // • The same adenocarcinoma subtype/variant (in situ /2 or invasive /3) in multiple polyps
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_COLON_GROUP_ID, "M9");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("Rules are hierarchical. Do not use this rule when rule M8 applies (tumors must be in the same segment of colon OR only in the rectum).");
        rule.getNotes().add("See Table 1 in Equivalent Terms and Definitions for adenocarcinoma subtypes/variants.");
        rule.getNotes().add("See Histology Coding Rules for coding instructions.");
        _rules.add(rule);

        // Rule M10 - Abstract a single primary when there is a subsequent invasive tumor less than or equal to 60 days after an in situ tumor.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_COLON_GROUP_ID, "M10");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("");
        rule.getNotes().add("Abstract the invasive tumor.");
        rule.getNotes().add("Change behavior code. If an in situ tumor has been reported to central registry, report all changes to central registry.");
        rule.getNotes().add("See the COC and SEER manuals for instructions on coding other data items such as Date of Diagnosis, Accession Year and Sequence Number.");
        rule.getNotes().add("The physician may stage both tumors.");
        _rules.add(rule);

        // Rule M11 - Abstract multiple primaries when there is an invasive tumor more than 60 day after an in situ tumor AND
        // • The patient had a resection of the in situ tumor AND
        // • The subsequent invasive tumor occurs more than 60 days after the last in situ tumor was resected
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_COLON_GROUP_ID, "M11");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The in situ tumor was resected so the invasive is a new tumor. The resection may be a polypectomy or hemicolectomy.");
        rule.getNotes().add("The purpose of this rule is to ensure that the case is counted as an incidence (invasive) case when incidence data are analyzed");
        rule.getNotes().add("Abstract as multiple primaries even if the medical record/physician states it is recurrence or progression of disease");
        rule.getNotes().add("The physician may stage each of the tumors because the depth of invasion will determine first and second course of treatment.");
        _rules.add(rule);

        // Rule M12 - Abstract multiple primaries when there are tumors with ICD-O histology/morphology codes that differ at the first (Xxxx) AND/OR second (xXxx) AND/OR third digit (xxXx).
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_COLON_GROUP_ID, "M12");
        rule.setQuestion("");
        rule.setReason("");
        _rules.add(rule);

        // Rule M13 - Abstract multiple primaries when there are
        // • Collision tumors OR
        // • An in situ and an invasive tumor OR
        // • De novo and polyp OR
        // • Two de novo separate, non-contiguous malignancies
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_COLON_GROUP_ID, "M13");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("Do not use this rule when M8, M9 or M12 apply.");
        rule.getNotes().add("  • M8: Tumors are multiple primaries when they occur");
        rule.getNotes().add("    • In different sections of colon OR");
        rule.getNotes().add("    • One in colon, one in rectum");
        rule.getNotes().add("  • M9: A de novo and polyp in same segment of colon are a single primary");
        rule.getNotes().add("  • M12: Code multiple primaries when ICD-O codes differ at the first, second, or third digits");
        rule.getNotes().add(
                "Collision tumors were originally two separate tumors that arose in close proximity. As the tumors increase in size, they merge or overlap each other. They are most frequently different histologies.");
        _rules.add(rule);

        // Rule M14 - Abstract a single primary when rules M1-M13 do not apply.
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_COLON_GROUP_ID, "M14");
        rule.setQuestion("");
        rule.setReason("");
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

