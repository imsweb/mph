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

public class Mp2018OtherSitesGroup extends MphGroup {

    /*
    Other Sites Multiple Primary Rules – Text
    Excludes Head and Neck, Colon, Lung, Melanoma of Skin, Breast,
    Kidney, Renal Pelvis, Ureter, Bladder, Brain, Lymphoma and Leukemia

    Multiple tumors may be a single primary or multiple primaries.
    Note 1: Tumors not described as metastases
    Note 2: Includes combinations of in situ and invasive

    Rule M3	Adenocarcinoma of the prostate is always a single primary. *
        Note 1:  Report only one adenocarcinoma of the prostate per patient per lifetime.
        Note 2: 95% of prostate malignancies are the common (acinar) adenocarcinoma histology (8140). See Equivalent Terms, Definitions and Tables for more information.
        Note 3: If patient has a previous acinar adenocarcinoma of the prostate in the database and is diagnosed with adenocarcinoma in 2007 it is a single primary.

    Rule M4	Retinoblastoma is always a single primary (unilateral or bilateral). *

    Rule M5	Kaposi sarcoma (any site or sites) is always a single primary. *

    Rule M6	Follicular and papillary tumors in the thyroid within 60 days of diagnosis are a single primary. *

    Rule M7	Bilateral epithelial tumors (8000-8799) of the ovary within 60 days are a single primary. *

    Rule M8	Tumors on both sides (right and left) of a site listed in Table 1 are multiple primaries. **
        Note: Table 1 – Paired Organs and Sites with Laterality)

    Rule M9	Adenocarcinoma in adenomatous polyposis coli (familial polyposis) with one or more in situ or malignant polyps is a single primary.*
        Note: Tumors may be present in a single or multiple segments of the colon, rectosigmoid, rectum.

    Rule M10	Tumors diagnosed more than one (1) year apart are multiple primaries. **

    Rule M11	Tumors with ICD-O-3 topography codes that are different at the second (Cxxx) and/or third characters (Cxxx) are multiple primaries. **
        Example 1:	A tumor in the penis C609 and a tumor in the rectum C209 have different second characters in their ICD-O-3 topography codes, so they are multiple primaries.
        Example 2:	A tumor in the cervix C539 and a tumor in the vulva C519 have different third characters in their ICD-O-3 topography codes, so they are multiple primaries.

    Rule M12	Tumors with ICD-O-3 topography codes that differ only at the fourth character (Cxxx) and are in any one of the following primary sites are multiple primaries. **
        •	Anus and anal canal (C21_)
        •	Bones, joints, and articular cartilage (C40_- C41_)
        •	Peripheral nerves and autonomic nervous system (C47_) (Cases diagnosed 1/1/2007 to 12/31/2017 ONLY)
        •	Connective subcutaneous and other soft tissues (C49_)
        •	Skin (C44_)

    Rule M13	A frank in situ or malignant adenocarcinoma and an in situ or malignant tumor in a polyp are a single primary. *

    Rule M14	Multiple in situ and/or malignant polyps are a single primary. *
        Note: Includes all combinations of adenomatous, tubular, villous, and tubulovillous adenomas or polyps.

    Rule M15	An invasive tumor following an in situ tumor more than 60 days after diagnosis is a multiple primary. **
        Note 1: The purpose of this rule is to ensure that the case is counted as an incident (invasive) case when incidence data are analyzed.
        Note 2: Abstract as multiple primaries even if the medical record/physician states it is recurrence or progression of disease.

    Rule M16	Abstract as a single primary* when one tumor is:
        •	Cancer/malignant neoplasm, NOS (8000) and another is a specific histology or
        •	Carcinoma, NOS (8010) and another is a specific carcinoma or
        •	Squamous cell carcinoma, NOS (8070) and another is specific squamous cell carcinoma or
        •	Adenocarcinoma, NOS (8140) and another is a specific adenocarcinoma or
        •	Melanoma, NOS (8720) and another is a specific melanoma
        •	Sarcoma, NOS (8800) and another is a specific sarcoma

    Rule M17	Tumors with ICD-O-3 histology codes that are different at the first (xxxx), second (xxxx) or third (xxxx) number are multiple primaries. **

    Rule M18	Tumors that do not meet any of the above criteria are a single primary. *
        Note: When an invasive tumor follows an in situ tumor within 60 days, abstract as a single primary.


    */

    // TODO
    // Other Sites Multiple Primary Rules – Text
    // Excludes Head and Neck, Colon, Lung, Melanoma of Skin, Breast, Kidney, Renal Pelvis, Ureter, Bladder, Brain, Lymphoma and Leukemia

    public Mp2018OtherSitesGroup() {
        super(MphConstants.MP_2018_OTHER_SITES_GROUP_ID, MphConstants.MP_2018_OTHER_SITES_GROUP_NAME, null, null, null,
                "", "2-3,6", "2018-9999");

        // Rule M3	Adenocarcinoma of the prostate is always a single primary. *
        // TODO
        MphRule rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_OTHER_SITES_GROUP_ID, "M3");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("Report only one adenocarcinoma of the prostate per patient per lifetime.");
        rule.getNotes().add("95% of prostate malignancies are the common (acinar) adenocarcinoma histology (8140). See Equivalent Terms, Definitions and Tables for more information.");
        rule.getNotes().add("If patient has a previous acinar adenocarcinoma of the prostate in the database and is diagnosed with adenocarcinoma in 2007 it is a single primary.");
        _rules.add(rule);

        // Rule M4	Retinoblastoma is always a single primary (unilateral or bilateral). *
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_OTHER_SITES_GROUP_ID, "M4");
        rule.setQuestion("");
        rule.setReason("");
        _rules.add(rule);

        // Rule M5	Kaposi sarcoma (any site or sites) is always a single primary. *
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_OTHER_SITES_GROUP_ID, "M5");
        rule.setQuestion("");
        rule.setReason("");
        _rules.add(rule);

        // Rule M6	Follicular and papillary tumors in the thyroid within 60 days of diagnosis are a single primary. *
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_OTHER_SITES_GROUP_ID, "M6");
        rule.setQuestion("");
        rule.setReason("");
        _rules.add(rule);

        // Rule M7	Bilateral epithelial tumors (8000-8799) of the ovary within 60 days are a single primary. *
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_OTHER_SITES_GROUP_ID, "M7");
        rule.setQuestion("");
        rule.setReason("");
        _rules.add(rule);

        // Rule M8	Tumors on both sides (right and left) of a site listed in Table 1 are multiple primaries. **
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_OTHER_SITES_GROUP_ID, "M8");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("Table 1 – Paired Organs and Sites with Laterality)");
        _rules.add(rule);

        // Rule M9	Adenocarcinoma in adenomatous polyposis coli (familial polyposis) with one or more in situ or malignant polyps is a single primary.*
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_OTHER_SITES_GROUP_ID, "M9");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("Tumors may be present in a single or multiple segments of the colon, rectosigmoid, rectum.");
        _rules.add(rule);

        // Rule M10	Tumors diagnosed more than one (1) year apart are multiple primaries. **
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_OTHER_SITES_GROUP_ID, "M10");
        rule.setQuestion("");
        rule.setReason("");
        _rules.add(rule);

        // Rule M11	Tumors with ICD-O-3 topography codes that are different at the second (Cxxx) and/or third characters (Cxxx) are multiple primaries. **
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_OTHER_SITES_GROUP_ID, "M11");
        rule.setQuestion("");
        rule.setReason("");
        rule.getExamples().add("A tumor in the penis C609 and a tumor in the rectum C209 have different second characters in their ICD-O-3 topography codes, so they are multiple primaries.");
        rule.getExamples().add("A tumor in the cervix C539 and a tumor in the vulva C519 have different third characters in their ICD-O-3 topography codes, so they are multiple primaries.");
        _rules.add(rule);

        // Rule M12	Tumors with ICD-O-3 topography codes that differ only at the fourth character (Cxxx) and are in any one of the following primary sites are multiple primaries. **
        // •	Anus and anal canal (C21_)
        // •	Bones, joints, and articular cartilage (C40_- C41_)
        // •	Peripheral nerves and autonomic nervous system (C47_) (Cases diagnosed 1/1/2007 to 12/31/2017 ONLY)
        // •	Connective subcutaneous and other soft tissues (C49_)
        // •	Skin (C44_)
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_OTHER_SITES_GROUP_ID, "M12");
        rule.setQuestion("");
        rule.setReason("");
        _rules.add(rule);

        // Rule M13	A frank in situ or malignant adenocarcinoma and an in situ or malignant tumor in a polyp are a single primary. *
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_OTHER_SITES_GROUP_ID, "M13");
        rule.setQuestion("");
        rule.setReason("");
        _rules.add(rule);

        // Rule M14	Multiple in situ and/or malignant polyps are a single primary. *
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_OTHER_SITES_GROUP_ID, "M14");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("Includes all combinations of adenomatous, tubular, villous, and tubulovillous adenomas or polyps.");
        _rules.add(rule);

        // Rule M15	An invasive tumor following an in situ tumor more than 60 days after diagnosis is a multiple primary. **
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_OTHER_SITES_GROUP_ID, "M15");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("The purpose of this rule is to ensure that the case is counted as an incident (invasive) case when incidence data are analyzed.");
        rule.getNotes().add("Abstract as multiple primaries even if the medical record/physician states it is recurrence or progression of disease.");
        _rules.add(rule);

        // Rule M16	Abstract as a single primary* when one tumor is:
        // •	Cancer/malignant neoplasm, NOS (8000) and another is a specific histology or
        // •	Carcinoma, NOS (8010) and another is a specific carcinoma or
        // •	Squamous cell carcinoma, NOS (8070) and another is specific squamous cell carcinoma or
        // •	Adenocarcinoma, NOS (8140) and another is a specific adenocarcinoma or
        // •	Melanoma, NOS (8720) and another is a specific melanoma
        // •	Sarcoma, NOS (8800) and another is a specific sarcoma
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_OTHER_SITES_GROUP_ID, "M16");
        rule.setQuestion("");
        rule.setReason("");
        _rules.add(rule);

        // Rule M17	Tumors with ICD-O-3 histology codes that are different at the first (xxxx), second (xxxx) or third (xxxx) number are multiple primaries. **
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_OTHER_SITES_GROUP_ID, "M17");
        rule.setQuestion("");
        rule.setReason("");
        _rules.add(rule);

        // Rule M18	Tumors that do not meet any of the above criteria are a single primary. *
        // TODO
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2018_OTHER_SITES_GROUP_ID, "M18");
        rule.setQuestion("");
        rule.setReason("");
        rule.getNotes().add("When an invasive tumor follows an in situ tumor within 60 days, abstract as a single primary.");
        _rules.add(rule);




        /*
        //M3- Adenocarcinoma of the prostate is always a single primary. (C619, 8140)
        MphRule rule = new MphRule(MphConstants.MP_2007_OTHER_SITES_GROUP_ID, "M3") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (MphConstants.PROSTATE.equals(i1.getPrimarySite()) && MphConstants.PROSTATE.equals(i2.getPrimarySite()) && MphConstants.ADENOCARCINOMA_NOS.contains(i1.getHistology())
                        && MphConstants.ADENOCARCINOMA_NOS.contains(i2.getHistology()))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                return result;
            }
        };
        rule.setQuestion("Is the diagnosis adenocarcinoma of the prostate?");
        rule.setReason("Adenocarcinoma of the prostate is always a single primary.");
        rule.getNotes().add("Report only one adenocarcinoma of the prostate per patient per lifetime.");
        rule.getNotes().add("95% of prostate malignancies are the common (acinar) adenocarcinoma histology (8140). See Equivalent Terms, Definitions and Tables for more information.");
        rule.getNotes().add("If patient has a previous acinar adenocarcinoma of the prostate in the database and is diagnosed with adenocarcinoma in 2007 it is a single primary.");
        _rules.add(rule);

        //M4- Retinoblastoma is always a single primary (unilateral or bilateral). (9510, 9511, 9512, 9513)
        rule = new MphRule(MphConstants.MP_2007_OTHER_SITES_GROUP_ID, "M4") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (MphConstants.RETINO_BLASTOMA.containsAll(Arrays.asList(i1.getHistology(), i2.getHistology())))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                return result;
            }
        };
        rule.setQuestion("Is the diagnosis retinoblastoma (unilateral or bilateral)?");
        rule.setReason("Retinoblastoma is always a single primary (unilateral or bilateral).");
        _rules.add(rule);

        //M5- Kaposi sarcoma (any site or sites) is always a single primary.
        rule = new MphRule(MphConstants.MP_2007_OTHER_SITES_GROUP_ID, "M5") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                if (MphConstants.KAPOSI_SARCOMA.equals(i1.getHistology()) && MphConstants.KAPOSI_SARCOMA.equals(i2.getHistology()))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                return result;
            }
        };
        rule.setQuestion("Is the diagnosis Kaposi sarcoma (any site or sites)?");
        rule.setReason("Kaposi sarcoma (any site or sites) is always a single primary.");
        _rules.add(rule);

        //M6- Follicular and papillary tumors in the thyroid within 60 days of diagnosis are a single primary.
        rule = new MphRule(MphConstants.MP_2007_OTHER_SITES_GROUP_ID, "M6") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                List<String> follicularAndPapillary = new ArrayList<>(MphConstants.FOLLICULAR);
                follicularAndPapillary.addAll(MphConstants.PAPILLARY);
                String site1 = i1.getPrimarySite(), site2 = i2.getPrimarySite(), hist1 = i1.getHistology(), hist2 = i2.getHistology();
                if (MphConstants.THYROID.equals(site1) && MphConstants.THYROID.equals(site2) && follicularAndPapillary.containsAll(Arrays.asList(hist1, hist2))) {
                    int diff = GroupUtility.verifyDaysApart(i1, i2, 60);
                    if (-1 == diff) {
                        result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". There is no enough diagnosis date information.");
                    }
                    else if (0 == diff)
                        result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                }
                return result;
            }
        };
        rule.setQuestion("Are there follicular and papillary tumors of the thyroid within 60 days of diagnosis?");
        rule.setReason("Follicular and papillary tumors in the thyroid within 60 days of diagnosis are a single primary.");
        _rules.add(rule);

        //M7- Bilateral epithelial tumors (8000-8799) of the ovary within 60 days are a single primary. Ovary = C569
        rule = new MphRule(MphConstants.MP_2007_OTHER_SITES_GROUP_ID, "M7") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                String site1 = i1.getPrimarySite().toUpperCase(), site2 = i2.getPrimarySite().toUpperCase(), hist1 = i1.getHistology(), hist2 = i2.getHistology();
                if (MphConstants.OVARY.equals(site1) && MphConstants.OVARY.equals(site2) && Integer.parseInt(hist1) <= 8799 && Integer.parseInt(hist2) <= 8799) {
                    int diff = GroupUtility.verifyDaysApart(i1, i2, 60);
                    if (-1 == diff) {
                        result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". There is no enough diagnosis date information.");
                    }
                    else if (0 == diff)
                        result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                }
                return result;
            }
        };
        rule.setQuestion("Are there bilateral epithelial tumors (8000-8799) of the ovary within 60 days of diagnosis?");
        rule.setReason("Bilateral epithelial tumors (8000-8799) of the ovary within 60 days are a single primary.");
        _rules.add(rule);

        // M8 - Tumors on both sides (right and left) of a site listed in Table 1 are multiple primaries.
        rule = new MphRule(MphConstants.MP_2007_OTHER_SITES_GROUP_ID, "M8") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                List<String> pairedSites = Arrays.asList("C384", "C400", "C401", "C402", "C403", "C413", "C414", "C441", "C442", "C443", "C445", "C446", "C447", "C471", "C472", "C491", "C492", "C569",
                        "C570", "C620-C629", "C630", "C631", "C690-C699", "C740-C749", "C754");

                if (GroupUtility.isPairedSites(i1.getPrimarySite(), i2.getPrimarySite(), pairedSites)) {
                    if (!GroupUtility.validPairedSiteLaterality(i1.getLaterality(), i2.getLaterality())) {
                        result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                        result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". Valid and known laterality for paired sites of other-sites should be provided.");
                    }
                    else if (GroupUtility.areOppositeSides(i1.getLaterality(), i2.getLaterality()))
                        result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                }

                return result;
            }
        };
        rule.setQuestion("Are there tumors in both the left and right sides of a paired site (Table 1)?");
        rule.setReason("Tumors on both sides (right and left) of a site listed in Table 1 are multiple primaries.");
        rule.getNotes().add("Table 1 – Paired Organs and Sites with Laterality.");
        _rules.add(rule);

        //M9 - Adenocarcinoma in adenomatous polyposis coli (familial polyposis) with one or more in situ or malignant polyps is a single primary.
        rule = new MphRule(MphConstants.MP_2007_OTHER_SITES_GROUP_ID, "M9") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                String site1 = i1.getPrimarySite(), site2 = i2.getPrimarySite(), s1 = i1.getPrimarySite().substring(0, 3), s2 = i2.getPrimarySite().substring(0, 3);
                boolean isSiteInRange = (MphConstants.COLON.equals(s1) || MphConstants.RECTOSIGMOID.equals(site1) || MphConstants.RECTUM.equals(site1)) && (MphConstants.COLON.equals(s2)
                        || MphConstants.RECTOSIGMOID.equals(site2) || MphConstants.RECTUM.equals(site2));
                boolean isOneMalignant = MphConstants.MALIGNANT.equals(i1.getBehavior()) || MphConstants.MALIGNANT.equals(i2.getBehavior());
                if (isSiteInRange && isOneMalignant && GroupUtility.differentCategory(i1.getHistology(), i2.getHistology(), MphConstants.FAMILIAL_ADENOMATOUS_POLYPOSIS, MphConstants.POLYP))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);

                return result;
            }
        };
        rule.setQuestion("Is the diagnosis adenocarcinoma in adenomatous polyposis coli (familialpolyposis ) with one or more malignant polyps?");
        rule.setReason("Adenocarcinoma in adenomatous polyposis coli (familial polyposis) with one or more in situ or malignant polyps is a single primary.");
        _rules.add(rule);

        //M10 - Tumors diagnosed more than one (1) year apart are multiple primaries.
        rule = new MphRule(MphConstants.MP_2007_OTHER_SITES_GROUP_ID, "M10") {
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

        //M11 - Tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx) and/or third (Cx?x) character are multiple primaries.
        rule = new MphRulePrimarySiteCode(MphConstants.MP_2007_OTHER_SITES_GROUP_ID, "M11");
        rule.getExamples().add("A tumor in the penis C609 and a tumor in the rectum C209 have different second characters in their ICD-O-3 topography codes, so they are multiple primaries.");
        rule.getExamples().add("A tumor in the cervix C539 and a tumor in the vulva C519 have different third characters in their ICD-O-3 topography codes, so they are multiple primaries.");
        _rules.add(rule);

        //M12 - Tumors with ICD-O-3 topography codes that differ only at the fourth character (Cxx?) and are in any one of the following primary sites are multiple primaries. ** Anus and anal canal (C21_) Bones, joints, and articular cartilage (C40_- C41_) Peripheral nerves and autonomic nervous system (C47_) Connective subcutaneous and other soft tissues (C49_) Skin (C44_)
        rule = new MphRule(MphConstants.MP_2007_OTHER_SITES_GROUP_ID, "M12") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                List<String> list = Arrays.asList("C21", "C40", "C41", "C47", "C49", "C44");
                //primary sites should be the same at their 2nd and 3rd digit to pass M11, so if site 1 is in the list site 2 also is.
                if ((list.contains(i1.getPrimarySite().substring(0, 3)) && i1.getPrimarySite().charAt(3) != i2.getPrimarySite().charAt(3)))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Are there tumors in sites with ICD-O-3 topography codes that differ at only the fourth character (Cxx?) and are in any one of the following primary sites:\n" +
                "Anus and anal canal (C21_)\n" +
                "Bones, joints, and articular cartilage (C40_- C41_)\n" +
                "Peripheral nerves and autonomic nervous system (C47_)\n" +
                "Connective subcutaneous and other soft tissues (C49_)\n" +
                "Skin (C44_)");
        rule.setReason("Tumors with ICD-O-3 topography codes that differ only at the fourth character (Cxx?) and are in any one of the following primary sites are multiple primaries.\n" +
                "Anus and anal canal (C21_)\n" +
                "Bones, joints, and articular cartilage (C40_- C41_)\n" +
                "Peripheral nerves and autonomic nervous system (C47_)\n" +
                "Connective subcutaneous and other soft tissues (C49_)\n" +
                "Skin (C44_)");
        _rules.add(rule);

        //M13 - A frank in situ or malignant adenocarcinoma and an in situ or malignant tumor in a polyp are a single primary.
        rule = new MphRule(MphConstants.MP_2007_OTHER_SITES_GROUP_ID, "M13") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                List<String> insituOrMalignant = Arrays.asList(MphConstants.INSITU, MphConstants.MALIGNANT);
                List<String> adenocarcinoma = new ArrayList<>(MphConstants.ADENOCARCINOMA_SPECIFIC);
                adenocarcinoma.addAll(MphConstants.ADENOCARCINOMA_NOS);
                if (insituOrMalignant.containsAll(Arrays.asList(i1.getBehavior(), i2.getBehavior())) && !MphConstants.POLYP.containsAll(Arrays.asList(i1.getHistology(), i2.getHistology()))
                        && GroupUtility.differentCategory(i1.getHistology(), i2.getHistology(), adenocarcinoma, MphConstants.POLYP))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                return result;
            }
        };
        rule.setQuestion("Is there a frank in situ or malignant adenocarcinoma and an in situ or malignant tumor in a polyp?");
        rule.setReason("A frank in situ or malignant adenocarcinoma and an in situ or malignant tumor in a polyp are a single primary.");
        _rules.add(rule);

        //M14 - Multiple in situ and/or malignant polyps are a single primary.
        rule = new MphRule(MphConstants.MP_2007_OTHER_SITES_GROUP_ID, "M14") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                List<String> insituOrMalignant = Arrays.asList(MphConstants.INSITU, MphConstants.MALIGNANT);
                if (insituOrMalignant.containsAll(Arrays.asList(i1.getBehavior(), i2.getBehavior())) && MphConstants.POLYP.containsAll(Arrays.asList(i1.getHistology(), i2.getHistology())))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                return result;
            }
        };
        rule.setQuestion("Are there multiple in situ and/or malignant polyps?");
        rule.setReason("Multiple in situ and/or malignant polyps are a single primary.");
        rule.getNotes().add("Includes all combinations of adenomatous, tubular, villous, and tubulovillous adenomas or polyps.");
        _rules.add(rule);

        //M15 - An invasive tumor following an in situ tumor more than 60 days after diagnosis is a multiple primary.
        rule = new MphRuleBehavior(MphConstants.MP_2007_OTHER_SITES_GROUP_ID, "M15");
        _rules.add(rule);

        //M16 -
        rule = new MphRule(MphConstants.MP_2007_OTHER_SITES_GROUP_ID, "M16") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                String hist1 = i1.getHistology(), hist2 = i2.getHistology();
                List<String> nosList = Arrays.asList("8000", "8010", "8070", "8140", "8720", "8800");
                if ((nosList.contains(hist1) && MphConstants.NOS_VS_SPECIFIC.containsKey(hist1) && MphConstants.NOS_VS_SPECIFIC.get(hist1).contains(hist2)) || (nosList.contains(hist2)
                        && MphConstants.NOS_VS_SPECIFIC.containsKey(hist2) && MphConstants.NOS_VS_SPECIFIC.get(hist2).contains(hist1)))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                return result;
            }
        };
        rule.setQuestion("Is there cancer/malignant neoplasm, NOS (8000) and another is a specific histology? or\n" +
                "Is there carcinoma, NOS (8010) and another is a specific carcinoma? or\n" +
                "Is there squamous cell carcinoma, NOS (8070) and another is a specific squamous cell carcinoma? or\n" +
                "Is there adenocarcinoma, NOS (8140) and another is a specific adenocarcinoma? or\n" +
                "Is there melanoma, NOS (8720) and another is a specific melanoma? or\n" +
                "Is there sarcoma, NOS (8800) and another is a specific sarcoma?");
        rule.setReason("Abstract as a single primary* when one tumor is:\n" +
                "- Cancer/malignant neoplasm, NOS (8000) and another is a specific histology or\n" +
                "- Carcinoma, NOS (8010) and another is a specific carcinoma or\n" +
                "- Squamous cell carcinoma, NOS (8070) and another is specific squamous cell carcinoma or\n" +
                "- Adenocarcinoma, NOS (8140) and another is a specific adenocarcinoma or\n" +
                "- Melanoma, NOS (8720) and another is a specific melanoma or\n" +
                "- Sarcoma, NOS (8800) and another is a specific sarcoma");
        _rules.add(rule);

        //M17- Tumors with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.
        rule = new MphRuleHistologyCode(MphConstants.MP_2007_OTHER_SITES_GROUP_ID, "M17");
        _rules.add(rule);

        //M18- Tumors that do not meet any of the criteria are abstracted as a single primary.
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2007_OTHER_SITES_GROUP_ID, "M18");
        rule.getNotes().add("When an invasive tumor follows an in situ tumor within 60 days, abstract as a single primary.");
        _rules.add(rule);
        */

    }

    @Override
    public boolean isApplicable(String primarySite, String histology, String behavior, int year) {
        if (GroupUtility.isContained(GroupUtility.computeRange(_histExclusions, false), Integer.parseInt(histology)) || !_behavInclusions.contains(behavior) || !GroupUtility.isContained(
                GroupUtility.computeRange(_yearInclusions, false), year) || !GroupUtility.validateProperties(primarySite, histology, behavior, year))
            return false;

        List<MphGroup> specificGroups = new ArrayList<>();
        specificGroups.add(new Mp2018BreastGroup());
        specificGroups.add(new Mp2007ColonGroup());
        specificGroups.add(new Mp2018CutaneousMelanomaGroup());
        specificGroups.add(new Mp2018HeadAndNeckGroup());
        specificGroups.add(new Mp2018KidneyGroup());
        specificGroups.add(new Mp2018LungGroup());
        specificGroups.add(new Mp2018MalignantCNSAndPeripheralNervesGroup());
        specificGroups.add(new Mp2018NonMalignantCNSTumorsGroup());
        specificGroups.add(new Mp2018UrinarySitesGroup());

        for (MphGroup group : specificGroups) {
            if (group.isApplicable(primarySite, histology, behavior, year))
                return false;
        }
        return true;
    }
}

