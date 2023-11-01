/*
 * Copyright (C) 2013 Information Management Services, Inc.
 */
package com.imsweb.mph.mpgroups;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.imsweb.mph.MphConstants;
import com.imsweb.mph.MphGroup;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphUtils;
import com.imsweb.mph.MphUtils.MpResult;
import com.imsweb.mph.RuleExecutionContext;
import com.imsweb.mph.internal.TempRuleResult;
import com.imsweb.mph.mprules.MpRuleInvasiveAfterInsituGreaterThan60Days;
import com.imsweb.mph.mprules.MpRuleKaposiSarcoma;
import com.imsweb.mph.mprules.MpRuleNoCriteriaSatisfied;
import com.imsweb.mph.mprules.MpRulePrimarySite;
import com.imsweb.mph.mprules.MpRuleRetinoblastoma;
import com.imsweb.mph.mprules.MpRuleThyroidFollicularPapillary;
import com.imsweb.mph.mprules.MpRuleYearsApart;

//S3776 - Cognitive Complexity of methods should not be too high => some of the rules are complicated by definition
@SuppressWarnings("java:S3776")
public class Mp2023OtherSitesGroup extends MphGroup {

    //Excludes Head and Neck, Colon, Lung, Melanoma of Skin, Breast, Kidney, Renal Pelvis, Ureter, Bladder, Brain, Lymphoma and Leukemia
    public Mp2023OtherSitesGroup() {
        super(MphConstants.STR_2023_AND_LATER_OTHER_SITES, MphConstants.SOLID_TUMOR_2023_OTHER_SITES, null, null, null, "9590-9993", "2-3,6", "2023-9999");

        //M3- Acinar Adenocarcinoma (8140) of the prostate is always a single primary. (C619, 8140)
        MphRule rule = new MphRule(MphConstants.SOLID_TUMOR_2023_OTHER_SITES, "M3") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
                TempRuleResult result = new TempRuleResult();
                String h1 = i1.getHistology();
                String icd1 = i1.getIcdCode();
                String h2 = i2.getHistology();
                String icd2 = i2.getIcdCode();
                if (MphConstants.PROSTATE.equals(i1.getPrimarySite()) && MphConstants.PROSTATE.equals(i2.getPrimarySite()) &&
                        (MphConstants.ADENOCARCINOMA_PROSTATE_SUBTYPES.contains(h1) || MphConstants.ADENOCARCINOMA_PROSTATE_SUBTYPES.contains(icd1)) &&
                        (MphConstants.ADENOCARCINOMA_PROSTATE_SUBTYPES.contains(h2) || MphConstants.ADENOCARCINOMA_PROSTATE_SUBTYPES.contains(icd2)))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                return result;
            }
        };
        rule.setQuestion("Is the diagnosis acinar adenocarcinoma of the prostate?");
        rule.setReason("Adenocarcinoma of the prostate is always a single primary.");
        rule.getNotes().add("Report only one acinar/adenocarcinoma of the prostate per patient lifetime.");
        rule.getNotes().add("95% of prostate malignancies are the common (acinar) adenocarcinoma histology (8140/3).");
        rule.getNotes().add("If the patient has a previous acinar adenocarcinoma of the prostate in the database and is diagnosed with adenocarcinoma in 2023, it is a single primary.");
        rule.getNotes().add("The rule applies to multiple occurrences of acinar adenocarcinoma of prostate and/or subtype variants of acinar adenocarcinoma of prostate listed in Table 3.");
        _rules.add(rule);

        //M4 - Abstract multiple primaries when the patient has a subsequent small cell carcinoma of the prostate more than 1 year following a diagnosis of acinar adenocarcinoma and/or subtype/variant of acinar adenocarcinoma of prostate (Table 3).
        rule = new MphRule(MphConstants.SOLID_TUMOR_2023_OTHER_SITES, "M4") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
                TempRuleResult result = new TempRuleResult();
                String smallCellCarcinoma = "8041/3";
                List<String> adenocarcinoma = Arrays.asList("8140/2", "8140/3", "8201/3", "8260/3", "8230/3", "8500/3", "8572/3", "8574/3", "8480/3", "8490/3");
                String site1 = i1.getPrimarySite();
                String site2 = i2.getPrimarySite();
                String icd1 = i1.getIcdCode();
                String icd2 = i2.getIcdCode();
                if (MphConstants.PROSTATE.equals(site1) && MphConstants.PROSTATE.equals(site2) && GroupUtility.differentCategory(icd1, icd2, Collections.singletonList(smallCellCarcinoma),
                        adenocarcinoma)) {
                    int laterDx = GroupUtility.compareDxDate(i1, i2);
                    if (MphConstants.COMPARE_DX_UNKNOWN == laterDx) {
                        result.setPotentialResult(MpResult.MULTIPLE_PRIMARIES);
                        result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupName());
                    }
                    else if ((MphConstants.COMPARE_DX_FIRST_LATEST == laterDx && smallCellCarcinoma.equals(icd1)) || (MphConstants.COMPARE_DX_SECOND_LATEST == laterDx && smallCellCarcinoma.equals(
                            icd2)))
                        result.setFinalResult(MpResult.MULTIPLE_PRIMARIES);
                }
                return result;
            }
        };
        rule.setQuestion(
                "Does the patient have a subsequent small cell carcinoma of the prostate more than 1 year following a diagnosis of acinar adenocarcinoma and/or subtype/variant of acinar adenocarcinoma of prostate (Table 3)?");
        rule.setReason(
                "Abstract multiple primaries when the patient has a subsequent small cell carcinoma of the prostate more than 1 year following a diagnosis of acinar adenocarcinoma and/or subtype/variant of acinar adenocarcinoma of prostate (Table 3).");
        rule.getNotes().add("Small cell carcinoma (SmCC) of the prostate is rare and accounts for less than 1% of prostate cancers.");
        rule.getNotes().add("50% of SmCC of the prostate cases present as a de novo malignancy");
        rule.getNotes().add("SmCC of the prostate often occurs following androgen deprivation treatment (ADVT) and/or radiation therapy for acinar adenocarcinoma");
        rule.getNotes().add("SmCC of the prostate are aggressive with poor clinical outcomes and survival.");
        _rules.add(rule);

        //M5- Retinoblastoma is always a single primary (unilateral or bilateral). (9510, 9511, 9512, 9513)
        rule = new MpRuleRetinoblastoma(MphConstants.SOLID_TUMOR_2023_OTHER_SITES, "M5");
        _rules.add(rule);

        //M6- Kaposi sarcoma (any site or sites) is always a single primary.
        rule = new MpRuleKaposiSarcoma(MphConstants.SOLID_TUMOR_2023_OTHER_SITES, "M6");
        _rules.add(rule);

        //M7- Follicular and papillary tumors in the thyroid within 60 days of diagnosis are a single primary.
        Set<String> follicularAndPapillary = new HashSet<>(MphConstants.FOLLICULAR);
        follicularAndPapillary.addAll(MphConstants.PAPILLARY);
        rule = new MpRuleThyroidFollicularPapillary(MphConstants.SOLID_TUMOR_2023_OTHER_SITES, "M7", follicularAndPapillary);
        rule.setQuestion("Are there follicular and papillary tumors of the thyroid within 60 days of diagnosis?");
        rule.setReason("Abstract a single primary when follicular and papillary tumors in the thyroid are diagnosed within 60 days and tumors are: \n"
                + "Papillary thyroid carcinoma, NOS and follicular carcinoma, NOS OR\n"
                + "Papillary carcinoma, follicular variant and papillary thyroid carcinoma OR\n"
                + "Papillary carcinoma, follicular variant and follicular carcinoma OR\n"
                + "Any papillary thyroid carcinoma subtype/variant and any follicular subtype/variant listed in Column 3, Table 12.");
        _rules.add(rule);

        //M8- Abstract multiple primaries when separate/non-contiguous tumors are anaplastic carcinoma and any other histologies in the thyroid.
        rule = new MphRule(MphConstants.SOLID_TUMOR_2023_OTHER_SITES, "M8") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
                TempRuleResult result = new TempRuleResult();
                String site1 = i1.getPrimarySite();
                String site2 = i2.getPrimarySite();
                String icd1 = i1.getIcdCode();
                String icd2 = i2.getIcdCode();
                if (MphConstants.THYROID.equals(site1) && MphConstants.THYROID.equals(site2) && GroupUtility.differentCategory(icd1, icd2, MphConstants.ANAPLASTIC_CARCINOMA,
                        MphConstants.OTHER_THYROID_HISTOLOGIES))
                    result.setFinalResult(MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Are separate/non-contigous tumors anaplastic carcinoma and any other histologies in the thyroid?");
        rule.setReason("Abstract multiple primaries when separate/non-contiguous tumors are anaplastic carcinoma and any other histologies in the thyroid.");
        _rules.add(rule);

        //M9- Bilateral epithelial tumors (8000-8799) of the ovary within 60 days are a single primary. Ovary = C569
        rule = new MphRule(MphConstants.SOLID_TUMOR_2023_OTHER_SITES, "M9") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
                TempRuleResult result = new TempRuleResult();
                String site1 = i1.getPrimarySite().toUpperCase();
                String site2 = i2.getPrimarySite().toUpperCase();
                String hist1 = i1.getHistology();
                String hist2 = i2.getHistology();
                if (MphConstants.OVARY.equals(site1) && MphConstants.OVARY.equals(site2) && Integer.parseInt(hist1) <= 8799 && Integer.parseInt(hist2) <= 8799) {
                    Map<String, String> map1 = MphConstants.OTHER_SITES_2023_TABLE_ROWS_FOR_SITE.get(i1.getPrimarySite());
                    Map<String, String> map2 = MphConstants.OTHER_SITES_2023_TABLE_ROWS_FOR_SITE.get(i2.getPrimarySite());
                    String row1 = map1.containsKey(hist1) ? map1.get(hist1) : map1.get(i1.getIcdCode());
                    String row2 = map2.containsKey(hist2) ? map2.get(hist2) : map2.get(i2.getIcdCode());
                    if (hist1.equals(hist2) || (row1 != null && row1.equals(row2))) {
                        int sixtyDaysApart = GroupUtility.verifyDaysApart(i1, i2, 60);
                        if (MphConstants.DATE_VERIFY_UNKNOWN == sixtyDaysApart) {
                            result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                            result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupName());
                        }
                        else if (MphConstants.DATE_VERIFY_WITHIN == sixtyDaysApart)
                            result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                    }
                }
                return result;
            }
        };
        rule.setQuestion("Are there bilateral epithelial tumors (8000-8799) of the ovary within 60 days of diagnosis?");
        rule.setReason("Bilateral epithelial tumors (8000-8799) of the ovary within 60 days are a single primary.");
        _rules.add(rule);

        // M10 - Tumors on both sides (right and left) of a site listed in Table 1 are multiple primaries.
        rule = new MphRule(MphConstants.SOLID_TUMOR_2023_OTHER_SITES, "M10") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
                TempRuleResult result = new TempRuleResult();
                List<String> pairedSites = Arrays.asList("C384", "C400", "C401", "C402", "C403", "C413", "C414", "C441", "C442", "C443", "C444", "C445", "C446", "C447", "C471", "C472", "C491", "C492",
                        "C569",
                        "C570", "C620-C629", "C630", "C631", "C690-C699", "C740-C749", "C754");

                if (GroupUtility.isPairedSites(i1.getPrimarySite(), i2.getPrimarySite(), pairedSites)) {
                    if (!GroupUtility.validPairedSiteLaterality(i1.getLaterality(), i2.getLaterality())) {
                        result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                        result.setMessageUnknownLaterality(this.getStep(), this.getGroupName());
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

        //M11 - Adenocarcinoma in adenomatous polyposis coli (familial polyposis) with one or more in situ or malignant polyps is a single primary.
        rule = new MphRule(MphConstants.SOLID_TUMOR_2023_OTHER_SITES, "M11") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
                TempRuleResult result = new TempRuleResult();
                String site1 = i1.getPrimarySite();
                String site2 = i2.getPrimarySite();
                boolean isSiteInRange = site1.startsWith(MphConstants.SMALL_BOWEL) && site2.startsWith(MphConstants.SMALL_BOWEL);
                boolean isOneMalignant = MphConstants.MALIGNANT.equals(i1.getBehavior()) || MphConstants.MALIGNANT.equals(i2.getBehavior());
                if (isSiteInRange && isOneMalignant && GroupUtility.differentCategory(i1.getHistology(), i2.getHistology(), MphConstants.FAMILIAL_ADENOMATOUS_POLYPOSIS, MphConstants.POLYP))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);

                return result;
            }
        };
        rule.setQuestion("Is the diagnosis adenocarcinoma in adenomatous polyposis coli (familialpolyposis ) with one or more malignant polyps?");
        rule.setReason("Adenocarcinoma in adenomatous polyposis coli (familial polyposis) with one or more in situ or malignant polyps is a single primary.");
        rule.getNotes().add("Tumors may be present in a single or multiple segments of small bowel, colon, rectosigmoid, rectum. ");
        _rules.add(rule);

        //M12 - Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than one year after the original diagnosis or recurrence.
        rule = new MpRuleYearsApart(MphConstants.SOLID_TUMOR_2023_OTHER_SITES, "M12", 1);
        rule.setQuestion("Are there tumors diagnosed more than one (1) year apart?");
        rule.setReason("Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than one year after the original diagnosis or recurrence.");
        _rules.add(rule);

        //M13 - Tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx) and/or third (Cx?x) character are multiple primaries.
        rule = new MpRulePrimarySite(MphConstants.SOLID_TUMOR_2023_OTHER_SITES, "M13");
        rule.getExamples().add("A tumor in the penis C609 and a tumor in the rectum C209 have different second characters in their ICD-O-3 topography codes, so they are multiple primaries.");
        rule.getExamples().add("A tumor in the cervix C539 and a tumor in the vulva C519 have different third characters in their ICD-O-3 topography codes, so they are multiple primaries.");
        _rules.add(rule);

        //M14 - Tumors with ICD-O-3 topography codes that differ only at the fourth character (Cxx?) and are in any one of the following primary sites are multiple primaries. ** Anus and anal canal (C21_) Bones, joints, and articular cartilage (C40_- C41_) Connective subcutaneous and other soft tissues (C49_) Skin (C44_)
        rule = new MphRule(MphConstants.SOLID_TUMOR_2023_OTHER_SITES, "M14") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
                TempRuleResult result = new TempRuleResult();
                List<String> list = Arrays.asList("C21", "C40", "C41", "C49", "C44");
                //primary sites should be the same at their 2nd and 3rd digit to pass M11, so if site 1 is in the list site 2 also is.
                if ((list.contains(i1.getPrimarySite().substring(0, 3)) && i1.getPrimarySite().charAt(3) != i2.getPrimarySite().charAt(3)))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Are there tumors in sites with ICD-O-3 topography codes that differ at only the fourth character (Cxx?) and are in any one of the following primary sites:\n" +
                "Anus and anal canal (C21_)\n" +
                "Bones, joints, and articular cartilage (C40_- C41_)\n" +
                "Connective subcutaneous and other soft tissues (C49_)\n" +
                "Skin (C44_)");
        rule.setReason("Tumors with ICD-O-3 topography codes that differ only at the fourth character (Cxx?) and are in any one of the following primary sites are multiple primaries.\n" +
                "Anus and anal canal (C21_)\n" +
                "Bones, joints, and articular cartilage (C40_- C41_)\n" +
                "Connective subcutaneous and other soft tissues (C49_)\n" +
                "Skin (C44_)");
        _rules.add(rule);

        //M15 - A de novo (frank) in situ or malignant adenocarcinoma and an in situ or malignant tumor in a polyp are a single primary.
        rule = new MphRule(MphConstants.SOLID_TUMOR_2023_OTHER_SITES, "M15") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
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
        rule.setReason("A de novo (frank) in situ or malignant adenocarcinoma and an in situ or malignant tumor in a polyp are a single primary.");
        _rules.add(rule);

        //M16 - Multiple in situ and/or malignant polyps are a single primary.
        rule = new MphRule(MphConstants.SOLID_TUMOR_2023_OTHER_SITES, "M16") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
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

        //M17 - Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 3-21
        rule = new MphRule(MphConstants.SOLID_TUMOR_2023_OTHER_SITES, "M17") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
                TempRuleResult result = new TempRuleResult();
                Map<String, String> map1 = MphConstants.OTHER_SITES_2023_TABLE_SUBTYPES_FOR_SITE.get(i1.getPrimarySite());
                Map<String, String> map2 = MphConstants.OTHER_SITES_2023_TABLE_SUBTYPES_FOR_SITE.get(i2.getPrimarySite());
                if (map1 != null && map2 != null) {
                    String h1 = i1.getHistology();
                    String icd1 = i1.getIcdCode();
                    String h2 = i2.getHistology();
                    String icd2 = i2.getIcdCode();
                    String subtype1 = map1.containsKey(h1) ? map1.get(h1) : map1.get(icd1);
                    String subtype2 = map2.containsKey(h2) ? map2.get(h2) : map2.get(icd2);
                    if (subtype1 != null && subtype2 != null && !subtype1.contains(subtype2) && !subtype2.contains(subtype1))
                        result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                }
                return result;
            }
        };
        rule.setQuestion("Are tumors different subtypes/variants in Column 3, Table 3-21?");
        rule.setReason("Abstract multiple primaries when separate/non-contiguous tumors are two or more different subtypes/variants in Column 3, Table 3-21.");
        _rules.add(rule);

        //M18 Abstract a single primary when synchronous, separate/non-contiguous tumors are on the same row in Table 3-21.
        rule = new MphRule(MphConstants.SOLID_TUMOR_2023_OTHER_SITES, "M18") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
                TempRuleResult result = new TempRuleResult();
                Map<String, String> map1 = MphConstants.OTHER_SITES_2023_TABLE_ROWS_FOR_SITE.get(i1.getPrimarySite());
                Map<String, String> map2 = MphConstants.OTHER_SITES_2023_TABLE_ROWS_FOR_SITE.get(i2.getPrimarySite());
                if (map1 != null && map1.equals(map2)) {
                    String h1 = i1.getHistology();
                    String icd1 = i1.getIcdCode();
                    String h2 = i2.getHistology();
                    String icd2 = i2.getIcdCode();
                    String row1 = map1.containsKey(h1) ? map1.get(h1) : map1.get(icd1);
                    String row2 = map2.containsKey(h2) ? map2.get(h2) : map2.get(icd2);
                    if (GroupUtility.sameHistologies(icd1, icd2) || (row1 != null && row1.equals(row2))) {
                        int sixtyDaysApart = GroupUtility.verifyDaysApart(i1, i2, 60);
                        if (MphConstants.DATE_VERIFY_UNKNOWN == sixtyDaysApart) {
                            result.setPotentialResult(MpResult.SINGLE_PRIMARY);
                            result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupName());
                        }
                        else if (MphConstants.DATE_VERIFY_WITHIN == sixtyDaysApart)
                            result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                    }
                }
                return result;
            }
        };
        rule.setQuestion("Are tumors on the same row in Table 3-21?");
        rule.setReason("Abstract a single primary when synchronous, separate/non-contiguous tumors are on the same row in Table 3-21.");
        rule.getNotes().add("The same histology (same four-digit ICD-O code) OR One is the preferred term (column 1) and the other is a synonym for the preferred term (column 2) OR"
                + "A NOS (column 1/column 2) and the other is a subtype/variant of that NOS (column 3) ");
        _rules.add(rule);

        //M19 - Abstract multiple primaries when separate/non-contiguous tumors are on multiple rows in Table 2-21.
        rule = new MphRule(MphConstants.SOLID_TUMOR_2023_OTHER_SITES, "M19") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, RuleExecutionContext context) {
                TempRuleResult result = new TempRuleResult();
                Map<String, String> map1 = MphConstants.OTHER_SITES_2023_TABLE_ROWS_FOR_SITE.get(i1.getPrimarySite());
                Map<String, String> map2 = MphConstants.OTHER_SITES_2023_TABLE_ROWS_FOR_SITE.get(i2.getPrimarySite());
                String h1 = i1.getHistology();
                String icd1 = i1.getIcdCode();
                String h2 = i2.getHistology();
                String icd2 = i2.getIcdCode();
                //If they are same code, no need to check if they are in different rows.
                if (GroupUtility.sameHistologies(icd1, icd2))
                    return result;

                String row1 = null;
                if (map1 != null)
                    row1 = map1.containsKey(h1) ? map1.get(h1) : map1.get(icd1);
                if (row1 == null && MphConstants.OTHER_SITES_2023_TABLE_2.contains(h1))
                    row1 = h1 + " in Table 2";

                String row2 = null;
                if (map2 != null)
                    row2 = map2.containsKey(h2) ? map2.get(h2) : map2.get(icd2);
                if (row2 == null && MphConstants.OTHER_SITES_2023_TABLE_2.contains(h2))
                    row2 = h2 + " in Table 2";

                if (row1 == null || row2 == null) {
                    result.setFinalResult(MpResult.QUESTIONABLE);
                    result.setMessageNotInTable(this.getStep(), this.getGroupName(), row1, row2, icd1, icd2);
                }
                else if (!row1.equals(row2))
                    result.setFinalResult(MpResult.MULTIPLE_PRIMARIES);

                return result;
            }
        };
        rule.setQuestion("Are tumors on multiple rows in Table 2-21?");
        rule.setReason("Abstract multiple primaries when separate/non-contiguous tumors are on multiple rows in Table 2-21.");
        _rules.add(rule);

        //M20 - An invasive tumor following an in situ tumor more than 60 days after diagnosis is a multiple primary.
        rule = new MpRuleInvasiveAfterInsituGreaterThan60Days(MphConstants.SOLID_TUMOR_2023_OTHER_SITES, "M20");
        rule.getNotes().add("This rule applies to multiple tumors, one in situ and a separate malignant tumor.");
        rule.getNotes().add("The purpose of this rule is to ensure that the case is counted as an incident (invasive) case when incidence data are analyzed.");
        rule.getNotes().add("Abstract as multiple primaries even if the medical record/physician states it is recurrence or progression of disease.");
        _rules.add(rule);

        //M21- Tumors that do not meet any of the criteria are abstracted as a single primary.
        rule = new MpRuleNoCriteriaSatisfied(MphConstants.SOLID_TUMOR_2023_OTHER_SITES, "M21");
        rule.getNotes().add("Use this rule as a last resort. Confirm that you have not overlooked an applicable rule.");
        rule.getNotes().add("When an invasive tumor follows an in situ tumor within 60 days, abstract as a single primary.");
        _rules.add(rule);
    }

    @Override
    public boolean isApplicable(String primarySite, String histology, String behavior, int year) {
        if (GroupUtility.isContained(GroupUtility.computeRange(_histExclusions, false), Integer.parseInt(histology)) || !_behavInclusions.contains(behavior) || !GroupUtility.isContained(
                GroupUtility.computeRange(_yearInclusions, false), year) || !GroupUtility.validateProperties(primarySite, histology, behavior, year))
            return false;

        List<MphGroup> specificGroups = new ArrayList<>();
        specificGroups.add(new Mp2007HeadAndNeckGroup());
        specificGroups.add(new Mp2007ColonGroup());
        specificGroups.add(new Mp2007LungGroup());
        specificGroups.add(new Mp2007MelanomaGroup());
        specificGroups.add(new Mp2007BreastGroup());
        specificGroups.add(new Mp2007KidneyGroup());
        specificGroups.add(new Mp2007UrinaryGroup());
        specificGroups.add(new Mp2007BenignBrainGroup());
        specificGroups.add(new Mp2007MalignantBrainGroup());

        specificGroups.add(new Mp2018BreastGroup());
        specificGroups.add(new Mp2018ColonGroup());
        specificGroups.add(new Mp2018HeadAndNeckGroup());
        specificGroups.add(new Mp2018KidneyGroup());
        specificGroups.add(new Mp2018LungGroup());
        specificGroups.add(new Mp2018MalignantCNSAndPeripheralNervesGroup());
        specificGroups.add(new Mp2018NonMalignantCNSTumorsGroup());
        specificGroups.add(new Mp2018UrinarySitesGroup());

        specificGroups.add(new Mp2021CutaneousMelanomaGroup());

        for (MphGroup group : specificGroups) {
            if (group.isApplicable(primarySite, histology, behavior, year))
                return false;
        }
        return true;
    }
}
