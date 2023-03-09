/*
 * Copyright (C) 2013 Information Management Services, Inc.
 */
package com.imsweb.mph.mpgroups;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.imsweb.mph.MphConstants;
import com.imsweb.mph.MphGroup;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphUtils;
import com.imsweb.mph.MphUtils.MpResult;
import com.imsweb.mph.internal.TempRuleResult;
import com.imsweb.mph.mprules.MpRuleInvasiveAfterInsituGreaterThan60Days;
import com.imsweb.mph.mprules.MpRuleNoCriteriaSatisfied;
import com.imsweb.mph.mprules.MpRulePrimarySite;

public class Mp2023OtherSitesGroup extends MphGroup {

    //Excludes Head and Neck, Colon, Lung, Melanoma of Skin, Breast, Kidney, Renal Pelvis, Ureter, Bladder, Brain, Lymphoma and Leukemia
    public Mp2023OtherSitesGroup() {
        super(MphConstants.MP_2023_OTHER_SITES_GROUP_ID, MphConstants.MP_2023_OTHER_SITES_GROUP_NAME, null, null, null, "9590-9989", "2-3,6", "2023-9999");

        //M3- Acinar Adenocarcinoma (8140) of the prostate is always a single primary. (C619, 8140)
        MphRule rule = new MphRule(MphConstants.MP_2023_OTHER_SITES_GROUP_ID, "M3") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                if (MphConstants.PROSTATE.equals(i1.getPrimarySite()) && MphConstants.PROSTATE.equals(i2.getPrimarySite()) && MphConstants.ADENOCARCINOMA_NOS.contains(i1.getHistology())
                        && MphConstants.ADENOCARCINOMA_NOS.contains(i2.getHistology()))
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
        rule = new MphRule(MphConstants.MP_2023_OTHER_SITES_GROUP_ID, "M4") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                if (MphConstants.PROSTATE.equals(i1.getPrimarySite()) && MphConstants.PROSTATE.equals(i2.getPrimarySite()) && GroupUtility.differentCategory(i1.getHistology(), i2.getHistology(), MphConstants.SMALL_CELL_CARCINOMA, MphConstants.ADENOCARCINOMA_NOS)) {
                    int laterDx = GroupUtility.compareDxDate(i1, i2);
                    if (MphConstants.COMPARE_DX_UNKNOWN == laterDx) {
                        result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupId());
                    }
                    else if ((MphConstants.COMPARE_DX_FIRST_LATEST == laterDx && MphConstants.SMALL_CELL_CARCINOMA.contains(i1.getHistology())) || (
                            MphConstants.COMPARE_DX_SECOND_LATEST == laterDx && MphConstants.SMALL_CELL_CARCINOMA.contains(i2.getHistology())))
                        result.setFinalResult(MpResult.SINGLE_PRIMARY);
                }
                return result;
            }
        };
        rule.setQuestion("Does the patient have a subsequent small cell carcinoma of the prostate more than 1 year following a diagnosis of acinar adenocarcinoma and/or subtype/variant of acinar adenocarcinoma of prostate (Table 3)?");
        rule.setReason("Abstract multiple primaries when the patient has a subsequent small cell carcinoma of the prostate more than 1 year following a diagnosis of acinar adenocarcinoma and/or subtype/variant of acinar adenocarcinoma of prostate (Table 3).");
        rule.getNotes().add("Small cell carcinoma (SmCC) of the prostate is rare and accounts for less than 1% of prostate cancers.");
        rule.getNotes().add("50% of SmCC of the prostate cases present as a de novo malignancy");
        rule.getNotes().add("SmCC of the prostate often occurs following androgen deprivation treatment (ADVT) and/or radiation therapy for acinar adenocarcinoma");
        rule.getNotes().add("SmCC of the prostate are aggressive with poor clinical outcomes and survival.");
        _rules.add(rule);

        //M5- Retinoblastoma is always a single primary (unilateral or bilateral). (9510, 9511, 9512, 9513)
        rule = new MphRule(MphConstants.MP_2023_OTHER_SITES_GROUP_ID, "M5") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                if (MphConstants.RETINO_BLASTOMA.containsAll(Arrays.asList(i1.getHistology(), i2.getHistology())))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                return result;
            }
        };
        rule.setQuestion("Is the diagnosis retinoblastoma (unilateral or bilateral)?");
        rule.setReason("Retinoblastoma is always a single primary (unilateral or bilateral).");
        _rules.add(rule);

        //M6- Kaposi sarcoma (any site or sites) is always a single primary.
        rule = new MphRule(MphConstants.MP_2023_OTHER_SITES_GROUP_ID, "M6") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                if (MphConstants.KAPOSI_SARCOMA.equals(i1.getHistology()) && MphConstants.KAPOSI_SARCOMA.equals(i2.getHistology()))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                return result;
            }
        };
        rule.setQuestion("Is the diagnosis Kaposi sarcoma (any site or sites)?");
        rule.setReason("Kaposi sarcoma (any site or sites) is always a single primary.");
        _rules.add(rule);

        //M7- Follicular and papillary tumors in the thyroid within 60 days of diagnosis are a single primary.
        rule = new MphRule(MphConstants.MP_2023_OTHER_SITES_GROUP_ID, "M7") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                List<String> follicularAndPapillary = new ArrayList<>(MphConstants.FOLLICULAR);
                follicularAndPapillary.addAll(MphConstants.PAPILLARY);
                String site1 = i1.getPrimarySite(), site2 = i2.getPrimarySite(), hist1 = i1.getHistology(), hist2 = i2.getHistology();
                if (MphConstants.THYROID.equals(site1) && MphConstants.THYROID.equals(site2) && follicularAndPapillary.containsAll(Arrays.asList(hist1, hist2))) {
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
        rule.setQuestion("Are there follicular and papillary tumors of the thyroid within 60 days of diagnosis?");
        rule.setReason("Abstract a single primary when follicular and papillary tumors in the thyroid are diagnosed within 60 days and tumors are: \n"
        + "Papillary thyroid carcinoma, NOS and follicular carcinoma, NOS OR\n"
        + "Papillary carcinoma, follicular variant and papillary thyroid carcinoma OR\n"
        + "Papillary carcinoma, follicular variant and follicular carcinoma OR\n"
        + "Any papillary thyroid carcinoma subtype/variant and any follicular subtype/variant listed in Column 3, Table 12.");
        _rules.add(rule);

        //M8- Abstract multiple primaries when separate/non-contiguous tumors are anaplastic carcinoma and any other histologies in the thyroid.
        rule = new MphRule(MphConstants.MP_2023_OTHER_SITES_GROUP_ID, "M8") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                String site1 = i1.getPrimarySite(), site2 = i2.getPrimarySite();
                String icd1 = i1.getHistology() + "/" + i1.getBehavior(), icd2 = i2.getHistology() + "/" + i2.getBehavior();
                if (MphConstants.THYROID.equals(site1) && MphConstants.THYROID.equals(site2) && GroupUtility.differentCategory(icd1, icd2, MphConstants.ANAPLASTIC_CARCINOMA, MphConstants.OTHER_THYROID_HISTOLOGIES))
                        result.setFinalResult(MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Are separate/non-contigous tumors anaplastic carcinoma and any other histologies in the thyroid?");
        rule.setReason("Abstract multiple primaries when separate/non-contiguous tumors are anaplastic carcinoma and any other histologies in the thyroid.");
        _rules.add(rule);

        //M9- Bilateral epithelial tumors (8000-8799) of the ovary within 60 days are a single primary. Ovary = C569
        rule = new MphRule(MphConstants.MP_2023_OTHER_SITES_GROUP_ID, "M9") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                String site1 = i1.getPrimarySite().toUpperCase(), site2 = i2.getPrimarySite().toUpperCase(), hist1 = i1.getHistology(), hist2 = i2.getHistology();
                if (MphConstants.OVARY.equals(site1) && MphConstants.OVARY.equals(site2) && Integer.parseInt(hist1) <= 8799 && Integer.parseInt(hist2) <= 8799) {
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
        rule.setQuestion("Are there bilateral epithelial tumors (8000-8799) of the ovary within 60 days of diagnosis?");
        rule.setReason("Bilateral epithelial tumors (8000-8799) of the ovary within 60 days are a single primary.");
        _rules.add(rule);

        // M10 - Tumors on both sides (right and left) of a site listed in Table 1 are multiple primaries.
        rule = new MphRule(MphConstants.MP_2023_OTHER_SITES_GROUP_ID, "M10") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                List<String> pairedSites = Arrays.asList("C384", "C400", "C401", "C402", "C403", "C413", "C414", "C441", "C442", "C443", "C445", "C446", "C447", "C471", "C472", "C491", "C492", "C569",
                        "C570", "C620-C629", "C630", "C631", "C690-C699", "C740-C749", "C754");

                if (GroupUtility.isPairedSites(i1.getPrimarySite(), i2.getPrimarySite(), pairedSites)) {
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
        rule.setQuestion("Are there tumors in both the left and right sides of a paired site (Table 1)?");
        rule.setReason("Tumors on both sides (right and left) of a site listed in Table 1 are multiple primaries.");
        rule.getNotes().add("Table 1 – Paired Organs and Sites with Laterality.");
        _rules.add(rule);

        //M11 - Adenocarcinoma in adenomatous polyposis coli (familial polyposis) with one or more in situ or malignant polyps is a single primary.
        rule = new MphRule(MphConstants.MP_2023_OTHER_SITES_GROUP_ID, "M11") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
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
        rule.getNotes().add("Tumors may be present in a single or multiple segments of small bowel, colon, rectosigmoid, rectum. ");
        _rules.add(rule);

        //M12 - Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than one year after the original diagnosis or recurrence.
        rule = new MphRule(MphConstants.MP_2023_OTHER_SITES_GROUP_ID, "M12") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                int diff = GroupUtility.verifyYearsApart(i1, i2, 1);
                if (MphConstants.DATE_VERIFY_UNKNOWN == diff) {
                    result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupId());
                }
                else if (MphConstants.DATE_VERIFY_APART == diff)
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Are there tumors diagnosed more than one (1) year apart?");
        rule.setReason("Abstract multiple primaries when the patient has a subsequent tumor after being clinically disease-free for greater than one year after the original diagnosis or recurrence.");
        _rules.add(rule);

        //M13 - Tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx) and/or third (Cx?x) character are multiple primaries.
        rule = new MpRulePrimarySite(MphConstants.MP_2023_OTHER_SITES_GROUP_ID, "M13");
        rule.getExamples().add("A tumor in the penis C609 and a tumor in the rectum C209 have different second characters in their ICD-O-3 topography codes, so they are multiple primaries.");
        rule.getExamples().add("A tumor in the cervix C539 and a tumor in the vulva C519 have different third characters in their ICD-O-3 topography codes, so they are multiple primaries.");
        _rules.add(rule);

        //M14 - Tumors with ICD-O-3 topography codes that differ only at the fourth character (Cxx?) and are in any one of the following primary sites are multiple primaries. ** Anus and anal canal (C21_) Bones, joints, and articular cartilage (C40_- C41_) Peripheral nerves and autonomic nervous system (C47_) Connective subcutaneous and other soft tissues (C49_) Skin (C44_)
        rule = new MphRule(MphConstants.MP_2023_OTHER_SITES_GROUP_ID, "M14") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
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

        //M15 - A de novo (frank) in situ or malignant adenocarcinoma and an in situ or malignant tumor in a polyp are a single primary.
        rule = new MphRule(MphConstants.MP_2023_OTHER_SITES_GROUP_ID, "M15") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
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
        rule = new MphRule(MphConstants.MP_2023_OTHER_SITES_GROUP_ID, "M16") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
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

        //M17 Abstract a single primary when synchronous, separate/non-contiguous tumors are on the same row in Table 3-21.
        rule = new MphRule(MphConstants.MP_2023_OTHER_SITES_GROUP_ID, "M17") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                Map<String, String> map1 = MphConstants.OTHER_SITES_2023_TABLE_ROWS_FOR_SITE.get(i1.getPrimarySite());
                Map<String, String> map2 = MphConstants.OTHER_SITES_2023_TABLE_ROWS_FOR_SITE.get(i2.getPrimarySite());
                if (map1 != null && map1.equals(map2)) {
                    String h1 = i1.getHistology(), icd1 = h1 + "/" + i1.getBehavior(), h2 = i2.getHistology(), icd2 = h2 + "/" + i2.getBehavior();
                    String row1 = map1.containsKey(h1) ? map1.get(h1) : map1.get(icd1);
                    String row2 = map2.containsKey(h2) ? map2.get(h2) : map2.get(icd2);
                    if (row1 != null && row1.equals(row2))
                        result.setFinalResult(MpResult.SINGLE_PRIMARY);
                }
                return result;
            }
        };
        rule.setQuestion("Are tumors on the same row in Table 3-21?");
        rule.setReason("Abstract a single primary when synchronous, separate/non-contiguous tumors are on the same row in Table 3-21.");
        _rules.add(rule);

        //M18 - Abstract multiple primaries when separate/non-contiguous tumors are on multiple rows in Table 2-21.
        rule = new MphRule(MphConstants.MP_2023_OTHER_SITES_GROUP_ID, "M18") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                Map<String, String> map1 = MphConstants.OTHER_SITES_2023_TABLE_ROWS_FOR_SITE.get(i1.getPrimarySite());
                Map<String, String> map2 = MphConstants.OTHER_SITES_2023_TABLE_ROWS_FOR_SITE.get(i2.getPrimarySite());
                String h1 = i1.getHistology(), icd1 = h1 + "/" + i1.getBehavior(), h2 = i2.getHistology(), icd2 = h2 + "/" + i2.getBehavior();
                String row1 = null;
                if (map1 != null)
                    row1 = map1.containsKey(h1) ? map1.get(h1) : map1.get(icd1);
                else if (MphConstants.OTHER_SITES_2023_TABLE_2.contains(h1))
                    row1 = h1 + " in Table 2";

                String row2 = null;
                if (map2 != null)
                    row2 = map2.containsKey(h2) ? map2.get(h2) : map2.get(icd2);
                else if (MphConstants.OTHER_SITES_2023_TABLE_2.contains(h2))
                    row2 = h2 + " in Table 2";

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
        rule.setQuestion("Are tumors on multiple rows in Table 2-21?");
        rule.setReason("Abstract multiple primaries when separate/non-contiguous tumors are on multiple rows in Table 2-21.");
        _rules.add(rule);

        //M19 - An invasive tumor following an in situ tumor more than 60 days after diagnosis is a multiple primary.
        rule = new MpRuleInvasiveAfterInsituGreaterThan60Days(MphConstants.MP_2023_OTHER_SITES_GROUP_ID, "M19");
        rule.getNotes().add("This rule applies to multiple tumors, one in situ and a separate malignant tumor.");
        rule.getNotes().add("The purpose of this rule is to ensure that the case is counted as an incident (invasive) case when incidence data are analyzed.");
        rule.getNotes().add("Abstract as multiple primaries even if the medical record/physician states it is recurrence or progression of disease.");
        _rules.add(rule);

        //M20- Tumors that do not meet any of the criteria are abstracted as a single primary.
        rule = new MpRuleNoCriteriaSatisfied(MphConstants.MP_2023_OTHER_SITES_GROUP_ID, "M20");
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

        specificGroups.add(new Mp2022BreastGroup());
        specificGroups.add(new Mp2022ColonGroup());
        specificGroups.add(new Mp2022HeadAndNeckGroup());
        specificGroups.add(new Mp2022KidneyGroup());
        specificGroups.add(new Mp2022MalignantCNSAndPeripheralNervesGroup());
        for (MphGroup group : specificGroups) {
            if (group.isApplicable(primarySite, histology, behavior, year))
                return false;
        }
        return true;
    }
}
