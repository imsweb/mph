/*
 * Copyright (C) 2016 Information Management Services, Inc.
 */
package com.imsweb.mph.mpgroups;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.imsweb.mph.MphConstants;
import com.imsweb.mph.MphGroup;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphUtils;
import com.imsweb.mph.internal.TempRuleResult;

@SuppressWarnings({"java:S6541", "java:S3776"})
public class Mp2004SolidMalignantGroup extends MphGroup {

    public Mp2004SolidMalignantGroup() {
        super(MphConstants.MP_2004_SOLID_MALIGNANT_GROUP_ID, MphConstants.MP_2004_SOLID_MALIGNANT, null, null, "8000-9589", null, "2-3,6", "0000-2006");

        // Rule 1
        MphRule rule = new MphRule(MphConstants.MP_2004_SOLID_MALIGNANT, "M1") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                //TODO
                return new TempRuleResult();
            }
        };
        rule.setReason("A single lesion composed of one histologic type is a single primary, even if the lesion crosses site boundaries.");
        rule.getExamples().add("A single lesion involving the tongue and floor of mouth is one primary.");
        rule.getExamples().add("A single, large mucinous adenocarcinoma involving the sigmoid and descending colon segments is one primary.");
        _rules.add(rule);

        // Rule 2
        rule = new MphRule(MphConstants.MP_2004_SOLID_MALIGNANT, "M2") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                //TODO
                return new TempRuleResult();
            }
        };        rule.setReason("A single lesion composed of multiple (different) histologic types is a single primary even if it crosses site boundaries.");
        rule.getExamples().add(" A single lesion containing both embryonal cell carcinoma and teratoma is a single primary and would be coded to 9081/3, mixed embryonal carcinoma and teratoma.");
        rule.getExamples().add(
                "A single lesion of the liver composed of neuroendocrine carcinoma (8246/3) and hepatocellular carcinoma (8170/3) is a single primary and would be coded to the more specific histology, neuroendocrine carcinoma 8246/3.");
        _rules.add(rule);

        // Rule 3
        rule = new MphRule(MphConstants.MP_2004_SOLID_MALIGNANT, "M3") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                int daysApart = GroupUtility.verifyDaysApart(i1, i2, 60);
                if (isSameSite(i1.getPrimarySite(), i2.getPrimarySite()) && isSameHistology(i1.getHistology(), i2.getHistology()) && daysApart != MphConstants.DATE_VERIFY_APART) {
                    if (isPairedSite(i1.getPrimarySite()) && isPairedSite(i2.getPrimarySite())) {
                        //Each side of a paired organ is considered a separate site.
                        if (GroupUtility.areOppositeSides(i1.getLaterality(), i2.getLaterality()))
                            return result;
                        else if (!GroupUtility.validPairedSiteLaterality(i1.getLaterality(), i2.getLaterality())) {
                            result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                            if (MphConstants.DATE_VERIFY_UNKNOWN == daysApart)
                                result.setMessageUnknownLatAndDate(this.getStep(), this.getGroupName());
                            else
                                result.setMessageUnknownLaterality(this.getStep(), this.getGroupName());
                            return result;
                        }
                    }
                    if (MphConstants.DATE_VERIFY_UNKNOWN == daysApart) {
                        result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupName());
                    }
                    else
                        result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                }
                return result;
            }
        };
        rule.setReason("Simultaneous multiple lesions of the same histologic type within the same site (i.e., multifocal tumors in a single organ or site) are a single primary. "
                + "If a new cancer of the same histology as an earlier one is diagnosed in the same site within two months, this is a single primary cancer");
        rule.getExamples().add(
                "At nephrectomy, two separate, distinct foci of renal cell carcinoma are found in the specimen, in addition to the 3.5 cm primary renal cell carcinoma. Abstract as a single primary.");
        rule.getExamples().add("At mastectomy for removal of a 2 cm invasive ductal carcinoma, an additional 5 cm area of intraductal carcinoma was noted. Abstract as one invasive primary.");
        rule.getExamples().add("Adenocarcinoma in adenomatous polyp (8210) in sigmoid colon removed by polypectomy in December 2004. At segmental resection in January 2005, an adenocarcinoma in a "
                + "tubular adenoma (8210) adjacent to the previous polypectomy site was removed. Count as one primary.");
        _rules.add(rule);

        // Rule 4
        rule = new MphRule(MphConstants.MP_2004_SOLID_MALIGNANT, "M4") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                String site1 = i1.getPrimarySite();
                String site2 = i2.getPrimarySite();
                String hist1 = i1.getHistology();
                String hist2 = i2.getHistology();
                String lat1 = i1.getLaterality();
                String lat2 = i2.getLaterality();
                int daysApart = GroupUtility.verifyDaysApart(i1, i2, 60);
                if (isSameSite(site1, site2) && isSameHistology(hist1, hist2) && isPairedSite(site1) && isPairedSite(site2) && daysApart != MphConstants.DATE_VERIFY_APART) {
                    if (!GroupUtility.validPairedSiteLaterality(lat1, lat2)) {
                        result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                        //Exceptions
                        if ((MphConstants.OVARY.equals(site1) && MphConstants.OVARY.equals(site2)) || (MphConstants.RETINO_BLASTOMA.containsAll(Arrays.asList(hist1, hist2))) ||
                                (MphConstants.WILMS.equals(hist1) && MphConstants.WILMS.equals(hist2)))
                            result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        if (MphConstants.DATE_VERIFY_UNKNOWN == daysApart)
                            result.setMessageUnknownLatAndDate(this.getStep(), this.getGroupName());
                        else
                            result.setMessageUnknownLaterality(this.getStep(), this.getGroupName());
                    }
                    else if (GroupUtility.areOppositeSides(lat1, lat2)) {
                        if (MphConstants.DATE_VERIFY_UNKNOWN == daysApart) {
                            result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                            //Exceptions
                            if ((MphConstants.OVARY.equals(site1) && MphConstants.OVARY.equals(site2)) || (MphConstants.RETINO_BLASTOMA.containsAll(Arrays.asList(hist1, hist2))) ||
                                    (MphConstants.WILMS.equals(hist1) && MphConstants.WILMS.equals(hist2)))
                                result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                            result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupName());
                        }
                        else {
                            result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                            //Exceptions
                            if ((MphConstants.OVARY.equals(site1) && MphConstants.OVARY.equals(site2)) || (MphConstants.RETINO_BLASTOMA.containsAll(Arrays.asList(hist1, hist2))) ||
                                    (MphConstants.WILMS.equals(hist1) && MphConstants.WILMS.equals(hist2)))
                                result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        }
                    }
                }
                return result;
            }
        };
        rule.setReason("If both sides of a paired organ are involved with the same histologic type within two months of the initial diagnosis:\n"
                + "It is one primary if the physician states the tumor in one organ is metastatic from the other.\n"
                + "Code as multiple primaries if the physician states these are independent primaries or when there is no physician statement that one is metastatic from the other.\n"
                + "Exception 1 : Simultaneous bilateral involvement of the ovaries with the same histology is one primary and laterality is coded 4 when it is unknown which ovary was the primary site.\n"
                + "Exception 2: Bilateral retinoblastomas are a single primary with laterality of 4.\n"
                + "Exception 2:  Bilateral Wilms tumors are always a single primary with laterality of 4.");

        _rules.add(rule);

        // Rule 5
        rule = new MphRule(MphConstants.MP_2004_SOLID_MALIGNANT, "M5") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                String site1 = i1.getPrimarySite();
                String site2 = i2.getPrimarySite();
                String hist1 = i1.getHistology();
                String hist2 = i2.getHistology();
                String beh1 = i1.getBehavior();
                String beh2 = i2.getBehavior();
                int daysApart = GroupUtility.verifyDaysApart(i1, i2, 60);
                //Exceptions
                List<String> adenoCarcinoma = new ArrayList<>(MphConstants.ADENOCARCINOMA_SPECIFIC);
                adenoCarcinoma.addAll(MphConstants.ADENOCARCINOMA_NOS);
                List<String> carcinoma = new ArrayList<>(MphConstants.TRANSITIONAL_CELL_CARCINOMA);
                carcinoma.addAll(MphConstants.PAPILLARY_TRANSITIONAL_CELL_CARCINOMA);
                if (MphConstants.PROSTATE.equals(site1) && site1.equals(site2) && MphConstants.MALIGNANT.equals(beh1) && beh1.equals(beh2) && adenoCarcinoma.containsAll(
                        Arrays.asList(hist1, hist2)))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                else if (site1.startsWith(MphConstants.BLADDER) && site2.startsWith(MphConstants.BLADDER) && MphConstants.MALIGNANT.equals(beh1) && beh1.equals(beh2) && carcinoma.containsAll(
                        Arrays.asList(hist1, hist2)))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                else if (MphConstants.KAPOSI_SARCOMA.equals(hist1) && hist1.equals(hist2))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                else if (isSameSite(site1, site2) && isSameHistology(hist1, hist2) && daysApart != MphConstants.DATE_VERIFY_WITHIN) {
                    if (MphConstants.DATE_VERIFY_UNKNOWN == daysApart) {
                        result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                        result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupName());
                    }
                    else
                        result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                }
                return result;
            }
        };
        rule.setReason("If a tumor with the same histology is identified in the same site at least two months after the initial/original diagnosis (metachronous), this is a separate primary.\n"
                + "Exception 1: This is a single primary only when the physician documents that the initial/original tumor gave rise to the later tumor.\n"
                + "Exception 2: Effective with cases diagnosed January 1995 and later, if an in situ tumor is followed by an invasive cancer in the same site more than two months apart, report as two "
                + "primaries even if stated to be a recurrence.\n"
                + "Exception 3: Report as a single primary and prepare a single abstract for the first invasive lesion:\n"
                + "Multiple invasive adenocarcinomas of the prostate (C619) and Multiple invasive blader cancers (C670 – C679) with histology codes 8120-8131\n"
                + "Exception 4: Kaposi sarcoma (9140) is reported only once and is coded to the site in which it arises. Code the primary site to skin (C44_) when Kaposi sarcoma arises in skin "
                + "and another site simultaneously. If no primary site is stated, code the primary site to skin, NOS (C449).");
        rule.getExamples().add("Infiltrating duct carcinoma of the upper outer quadrant of the right breast diagnosed March 2004 and treated with lumpectomy. Previously "
                + "unidentified mass in left inner quadrant right breast noted in July 2004 mammogram. This was removed and found to be infiltrating duct carcinoma. "
                + "Abstract the case as two primaries");
        rule.getExamples().add("During the workup for a squamous cell carcinoma of the vocal cord, a second squamous cell carcinoma is discovered in the tonsillar fossa. Abstract as two primaries.");
        rule.getExamples().add(
                "Urothelial bladder tumor removed by transurethral resection of the bladder (TURB). At three month check-up, a new urothelial tumor is removed. Abstract as one primary of the bladder.");
        rule.getExamples().add("Patient has elevated PSA and a needle biopsy that shows adenocarcinoma in the right lobe of the prostate. Patient and clinician opt for "
                + "watchful waiting. Four months later, PSA is higher and patient has a second biopsy, which shows adenocarcinoma in the left lobe. Abstract as one primary of the prostate.");
        _rules.add(rule);

        // Rule 6
        rule = new MphRule(MphConstants.MP_2004_SOLID_MALIGNANT, "M6") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                String site1 = i1.getPrimarySite();
                String site2 = i2.getPrimarySite();
                String hist1 = i1.getHistology();
                String hist2 = i2.getHistology();
                String icd1 = i1.getIcdCode();
                String icd2 = i2.getIcdCode();
                String lat1 = i1.getLaterality();
                String lat2 = i2.getLaterality();
                int daysApart = GroupUtility.verifyDaysApart(i1, i2, 60);
                if (isSameSite(site1, site2) && !isSameHistology(hist1, hist2) && daysApart != MphConstants.DATE_VERIFY_APART) {
                    if (isPairedSite(i1.getPrimarySite()) && isPairedSite(i2.getPrimarySite())) {
                        //only single paired
                        if (GroupUtility.areOppositeSides(lat1, lat2))
                            return result;
                        else if (!GroupUtility.validPairedSiteLaterality(lat1, lat2)) {
                            result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                            //Exceptions
                            if (GroupUtility.differentCategory(hist1, hist2, MphConstants.CARCINOMA_NOS, MphConstants.CARCINOMA_SPECIFIC))
                                result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                            else if (GroupUtility.differentCategory(hist1, hist2, MphConstants.ADENOCARCINOMA_NOS, MphConstants.ADENOCARCINOMA_SPECIFIC))
                                result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                            else if (GroupUtility.differentCategory(hist1, hist2, MphConstants.MELANOMA_NOS, MphConstants.MELANOMA_SPECIFIC))
                                result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                            else if (GroupUtility.differentCategory(hist1, hist2, MphConstants.SARCOMA_NOS, MphConstants.SARCOMA_SPECIFIC))
                                result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                            else if ((site1.startsWith(MphConstants.COLON) || MphConstants.RECTUM.equals(site1)) && (site2.startsWith(MphConstants.COLON) || MphConstants.RECTUM.equals(site2))
                                    && MphConstants.FAMILIAL_ADENOMATOUS_POLYPOSIS.containsAll(Arrays.asList(hist1, hist2)))
                                result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                            else if (MphConstants.THYROID.equals(site1) && MphConstants.THYROID.equals(site2) && (MphConstants.FOLLICULAR.contains(icd1) || MphConstants.PAPILLARY.contains(icd1))
                                    && (MphConstants.FOLLICULAR.contains(icd2) || MphConstants.PAPILLARY.contains(icd2)))
                                result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                            else if (site1.startsWith(MphConstants.BLADDER) && site2.startsWith(MphConstants.BLADDER) && (MphConstants.PAPILLARY_CARCINOMA.equals(hist1)
                                    || MphConstants.TRANSITIONAL_CELL_CARCINOMA.contains(hist1) || MphConstants.PAPILLARY_TRANSITIONAL_CELL_CARCINOMA.contains(hist1)) && (
                                    MphConstants.PAPILLARY_CARCINOMA.equals(hist2) || MphConstants.TRANSITIONAL_CELL_CARCINOMA.contains(hist2) || MphConstants.PAPILLARY_TRANSITIONAL_CELL_CARCINOMA
                                            .contains(hist2)))
                                result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                            else if (site1.startsWith(MphConstants.BREAST) && site2.startsWith(MphConstants.BREAST) && ((MphConstants.DUCT_CARCINOMA.contains(hist1) || MphConstants.LOBULAR_CARCINOMA.contains(hist1)) && (MphConstants.DUCT_CARCINOMA.contains(hist2)
                                        || MphConstants.LOBULAR_CARCINOMA.contains(hist2)) || ((MphConstants.PAGET_DISEASE.contains(hist1) || MphConstants.DUCT_CARCINOMA.contains(hist1) || MphConstants.INTRADUCTAL_CARCINOMA.contains(hist1)) && (
                                        MphConstants.PAGET_DISEASE.contains(hist2) || MphConstants.DUCT_CARCINOMA.contains(hist2) || MphConstants.INTRADUCTAL_CARCINOMA.contains(hist2)))))
                                    {result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                            }
                            if (MphConstants.DATE_VERIFY_UNKNOWN == daysApart)
                                result.setMessageUnknownLatAndDate(this.getStep(), this.getGroupName());
                            else
                                result.setMessageUnknownLaterality(this.getStep(), this.getGroupName());
                            return result;
                        }
                    }
                    if (MphConstants.DATE_VERIFY_UNKNOWN == daysApart) {
                        result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                        //Exceptions
                        if (GroupUtility.differentCategory(hist1, hist2, MphConstants.CARCINOMA_NOS, MphConstants.CARCINOMA_SPECIFIC))
                            result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        else if (GroupUtility.differentCategory(hist1, hist2, MphConstants.ADENOCARCINOMA_NOS, MphConstants.ADENOCARCINOMA_SPECIFIC))
                            result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        else if (GroupUtility.differentCategory(hist1, hist2, MphConstants.MELANOMA_NOS, MphConstants.MELANOMA_SPECIFIC))
                            result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        else if (GroupUtility.differentCategory(hist1, hist2, MphConstants.SARCOMA_NOS, MphConstants.SARCOMA_SPECIFIC))
                            result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        else if ((site1.startsWith(MphConstants.COLON) || MphConstants.RECTUM.equals(site1)) && (site2.startsWith(MphConstants.COLON) || MphConstants.RECTUM.equals(site2))
                                && MphConstants.FAMILIAL_ADENOMATOUS_POLYPOSIS.containsAll(Arrays.asList(hist1, hist2)))
                            result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        else if (MphConstants.THYROID.equals(site1) && MphConstants.THYROID.equals(site2) && (MphConstants.FOLLICULAR.contains(icd1) || MphConstants.PAPILLARY.contains(icd1))
                                && (MphConstants.FOLLICULAR.contains(icd2) || MphConstants.PAPILLARY.contains(icd2)))
                            result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        else if (site1.startsWith(MphConstants.BLADDER) && site2.startsWith(MphConstants.BLADDER) && (MphConstants.PAPILLARY_CARCINOMA.equals(hist1)
                                || MphConstants.TRANSITIONAL_CELL_CARCINOMA.contains(hist1) || MphConstants.PAPILLARY_TRANSITIONAL_CELL_CARCINOMA.contains(hist1)) && (
                                MphConstants.PAPILLARY_CARCINOMA.equals(hist2) || MphConstants.TRANSITIONAL_CELL_CARCINOMA.contains(hist2) || MphConstants.PAPILLARY_TRANSITIONAL_CELL_CARCINOMA
                                        .contains(hist2)))
                            result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        else if (site1.startsWith(MphConstants.BREAST) && site2.startsWith(MphConstants.BREAST) && ((MphConstants.DUCT_CARCINOMA.contains(hist1) || MphConstants.LOBULAR_CARCINOMA.contains(hist1)) && (MphConstants.DUCT_CARCINOMA.contains(hist2)
                                    || MphConstants.LOBULAR_CARCINOMA.contains(hist2)) || ((MphConstants.PAGET_DISEASE.contains(hist1) || MphConstants.DUCT_CARCINOMA.contains(hist1) || MphConstants.INTRADUCTAL_CARCINOMA.contains(hist1)) && (
                                    MphConstants.PAGET_DISEASE.contains(hist2) || MphConstants.DUCT_CARCINOMA.contains(hist2) || MphConstants.INTRADUCTAL_CARCINOMA.contains(hist2)))))
                                {result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        }
                        result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupName());
                    }
                    else {
                        result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                        //Exceptions
                        if (GroupUtility.differentCategory(hist1, hist2, MphConstants.CARCINOMA_NOS, MphConstants.CARCINOMA_SPECIFIC))
                            result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        else if (GroupUtility.differentCategory(hist1, hist2, MphConstants.ADENOCARCINOMA_NOS, MphConstants.ADENOCARCINOMA_SPECIFIC))
                            result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        else if (GroupUtility.differentCategory(hist1, hist2, MphConstants.MELANOMA_NOS, MphConstants.MELANOMA_SPECIFIC))
                            result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        else if (GroupUtility.differentCategory(hist1, hist2, MphConstants.SARCOMA_NOS, MphConstants.SARCOMA_SPECIFIC))
                            result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        else if ((site1.startsWith(MphConstants.COLON) || MphConstants.RECTUM.equals(site1)) && (site2.startsWith(MphConstants.COLON) || MphConstants.RECTUM.equals(site2))
                                && MphConstants.FAMILIAL_ADENOMATOUS_POLYPOSIS.containsAll(Arrays.asList(hist1, hist2)))
                            result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        else if (MphConstants.THYROID.equals(site1) && MphConstants.THYROID.equals(site2) && (MphConstants.FOLLICULAR.contains(icd1) || MphConstants.PAPILLARY.contains(icd1))
                                && (MphConstants.FOLLICULAR.contains(icd2) || MphConstants.PAPILLARY.contains(icd2)))
                            result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        else if (site1.startsWith(MphConstants.BLADDER) && site2.startsWith(MphConstants.BLADDER) && (MphConstants.PAPILLARY_CARCINOMA.equals(hist1)
                                || MphConstants.TRANSITIONAL_CELL_CARCINOMA.contains(hist1) || MphConstants.PAPILLARY_TRANSITIONAL_CELL_CARCINOMA.contains(hist1)) && (
                                MphConstants.PAPILLARY_CARCINOMA.equals(hist2) || MphConstants.TRANSITIONAL_CELL_CARCINOMA.contains(hist2) || MphConstants.PAPILLARY_TRANSITIONAL_CELL_CARCINOMA
                                        .contains(hist2)))
                            result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        else if (site1.startsWith(MphConstants.BREAST) && site2.startsWith(MphConstants.BREAST) && ((MphConstants.DUCT_CARCINOMA.contains(hist1) || MphConstants.LOBULAR_CARCINOMA.contains(hist1)) && (MphConstants.DUCT_CARCINOMA.contains(hist2)
                                    || MphConstants.LOBULAR_CARCINOMA.contains(hist2)) || ((MphConstants.PAGET_DISEASE.contains(hist1) || MphConstants.DUCT_CARCINOMA.contains(hist1) || MphConstants.INTRADUCTAL_CARCINOMA.contains(hist1)) && (
                                    MphConstants.PAGET_DISEASE.contains(hist2) || MphConstants.DUCT_CARCINOMA.contains(hist2) || MphConstants.INTRADUCTAL_CARCINOMA.contains(hist2)))))
                                {result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        }
                    }
                }
                return result;
            }
        };
        rule.setReason("Multiple synchronous lesions of different histologic types within a single paired or unpaired organ are separate primaries. "
                + "Exception 1: Multiple lesions in a single site occurring within two months: if one lesion is carcinoma, NOS, adenocarcinoma, NOS, sarcoma, NOS, or melanoma, NOS "
                + "and the second lesion is more specific, such as large cell carcinoma, mucinous adenocarcinoma, spindle cell sarcoma, or superficial spreading melanoma, abstract as "
                + "a single primary and code the histology to the more specific term.\n"
                + "Exception 2: For colon and rectum tumors:\n a) When an adenocarcinoma (8140/_; in situ or invasive) arises in the same segment of the colon or rectum as an "
                + "adenocarcinoma in a polyp (8210/_, 8261/_, 8263/_), abstract a single primary and code the histology as adenocarcinoma (8140/_).\n"
                + "b) Familial adenomatous polyposis (FAP) (8220) with malignancies arising in polyps in the same or multiple segments of the colon or rectum, abstract as a single primary.\n"
                + "Exception 3: There are certain sites in which multiple foci of tumor and multiple histologic types are commonly found together. These multifocal, multi-histologic "
                + "tumors occur most frequently in the thyroid (papillary and follicular), bladder (papillary and transitional cell) and breast (combinations of ductal and lobular, and "
                + "combinations of Paget disease and ductal/intraductal). They are abstracted as a single primary with a mixed histology. In such cases, consult ICD-O-3 for a list of the most "
                + "frequent histologic combinations.");
        rule.getExamples().add("A patient undergoes a partial gastrectomy for adenocarcinoma of the body of the stomach. In the resected specimen, the pathologist finds both adenocarcinoma and "
                + "nodular non-Hodgkin lymphoma. Abstract two primaries.");
        rule.getExamples().add(
                "A thyroid specimen contains two separate carcinomas— one papillary and the other follicular. Abstract one primary when the histology is papillary and follicular (8340).");
        rule.getExamples().add("Abstract one primary when multiple bladder tumors are papillary urothelial (8130) and/or transitional cell (8120).");
        rule.getExamples().add("A left mastectomy specimen yields lobular carcinoma in the upper inner quadrant and intraductal carcinoma in the lower inner quadrant. Code one primary.");
        rule.getExamples().add(
                " A right mastectomy specimen yields Paget in the nipple and a separate underlying ductal carcinoma. Code one primary. Assign the combination code 8543 (Ductal and Paget disease).");
        _rules.add(rule);

        //Rule 7
        rule = new MphRule(MphConstants.MP_2004_SOLID_MALIGNANT, "M7") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                String site1 = i1.getPrimarySite();
                String site2 = i2.getPrimarySite();
                String hist1 = i1.getHistology();
                String hist2 = i2.getHistology();
                String lat1 = i1.getLaterality();
                String lat2 = i2.getLaterality();
                int daysApart = GroupUtility.verifyDaysApart(i1, i2, 60);
                if (isSameSite(site1, site2) && !isSameHistology(hist1, hist2) && daysApart != MphConstants.DATE_VERIFY_APART && isPairedSite(site1) && isPairedSite(site2)) {
                    if (!GroupUtility.validPairedSiteLaterality(lat1, lat2)) {
                        result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                        if (MphConstants.DATE_VERIFY_UNKNOWN == daysApart)
                            result.setMessageUnknownLatAndDate(this.getStep(), this.getGroupName());
                        else
                            result.setMessageUnknownLaterality(this.getStep(), this.getGroupName());
                    }
                    else if (MphConstants.DATE_VERIFY_UNKNOWN == daysApart) {
                        result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                        result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupName());
                    }
                    else if (GroupUtility.areOppositeSides(lat1, lat2))
                        result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                }
                return result;
            }
        };
        rule.setReason("Multiple synchronous lesions of different histologic types in paired organs are multiple primaries. If one histologic type is reported in one side of a paired organ and a "
                + "different histologic type is reported in the other paired organ, these are two primaries unless there is a statement to the contrary.");
        rule.getExamples().add("If a ductal tumor occurs in one breast and a lobular tumor occurs in the opposite breast, these are two separate primaries.");
        _rules.add(rule);

        //Rule 8
        rule = new MphRule(MphConstants.MP_2004_SOLID_MALIGNANT, "M8") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                String site1 = i1.getPrimarySite();
                String site2 = i2.getPrimarySite();
                String hist1 = i1.getHistology();
                String hist2 = i2.getHistology();
                int daysApart = GroupUtility.verifyDaysApart(i1, i2, 60);
                if (isSameSite(site1, site2) && !isSameHistology(hist1, hist2) && daysApart != MphConstants.DATE_VERIFY_WITHIN) {
                    if (MphConstants.DATE_VERIFY_UNKNOWN == daysApart) {
                        result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                        result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupName());
                    }
                    else
                        result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                }
                return result;
            }
        };
        rule.setReason("Multiple metachronous lesions of different histologic types within a single site are separate primaries.");
        _rules.add(rule);

        //Rule 9
        rule = new MphRule(MphConstants.MP_2004_SOLID_MALIGNANT, "M9") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                String site1 = i1.getPrimarySite();
                String site2 = i2.getPrimarySite();
                String hist1 = i1.getHistology();
                String hist2 = i2.getHistology();
                if (!isSameSite(site1, site2) && !isSameHistology(hist1, hist2))
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setReason("Multiple lesions of different histologic types occurring in different sites are separate primaries whether occurring simultaneously or at different times.");
        rule.getExamples().add(
                "In 1999, the patient had a mucin-producing carcinoma of the transverse colon. In 2002, the patient was diagnosed with an astrocytoma of the frontal lobe of the brain. Abstract as separate primaries.");
        rule.getExamples().add("During the workup for a transitional cell carcinoma of the bladder, the patient has a TURP that shows adenocarcinoma of the prostate. Abstract as separate primaries.");
        _rules.add(rule);

        //Rule 10
        rule = new MphRule(MphConstants.MP_2004_SOLID_MALIGNANT, "M10") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                String site1 = i1.getPrimarySite();
                String site2 = i2.getPrimarySite();
                String hist1 = i1.getHistology();
                String hist2 = i2.getHistology();
                result.setFinalResult(!isSameSite(site1, site2) && isSameHistology(hist1, hist2) ? MphUtils.MpResult.MULTIPLE_PRIMARIES : MphUtils.MpResult.SINGLE_PRIMARY);
                return result;
            }
        };
        rule.setReason("Multiple lesions of the same histologic type occurring in different sites are separate primaries unless stated to be metastatic. Otherwise tumors that do not meet any of the criteria are abstracted as a single primary.");
        _rules.add(rule);
    }

    private boolean isSameSite(String site1, String site2) {

        String s1 = site1.substring(0, 3);
        String s2 = site2.substring(0, 3);
        return site1.equals(site2) || (s1.equals(s2) && !MphConstants.EXACT_MATCH_SITES.contains(s1)) || MphConstants.TONGUE.containsAll(Arrays.asList(s1, s2)) || MphConstants.MOUTH.containsAll(
                Arrays.asList(s1, s2)) || MphConstants.SALIVARY.containsAll(Arrays.asList(s1, s2)) || MphConstants.OROPHARYNX.containsAll(Arrays.asList(s1, s2)) || MphConstants.HYPOPHARYNX
                .containsAll(Arrays.asList(s1, s2)) || MphConstants.BILIARY.containsAll(Arrays.asList(s1, s2)) || MphConstants.SINUS.containsAll(Arrays.asList(s1, s2)) || MphConstants.LUNG
                .containsAll(Arrays.asList(s1, s2)) || (GroupUtility.isSiteContained(MphConstants.MEDIASTINUM, site1) && GroupUtility.isSiteContained(MphConstants.MEDIASTINUM, site2)) || (
                GroupUtility.isSiteContained(MphConstants.FEMALE_GENITAL, site1) && GroupUtility.isSiteContained(MphConstants.FEMALE_GENITAL, site2)) || (GroupUtility.isSiteContained(
                MphConstants.OVARY_OR_FEMALE_GENITAL, site1) && GroupUtility.isSiteContained(MphConstants.OVARY_OR_FEMALE_GENITAL, site2)) || MphConstants.MALE_GENITAL.containsAll(
                Arrays.asList(s1, s2)) || MphConstants.KIDNEY_OR_URINARY.containsAll(Arrays.asList(s1, s2)) || MphConstants.ENDOCRINE.containsAll(Arrays.asList(s1, s2));
    }

    private boolean isSameHistology(String hist1, String hist2) {
        return hist1.substring(0, 3).equals(hist2.substring(0, 3)) && !GroupUtility.differentCategory(hist1, hist2, MphConstants.SMALL_CELL_CARCINOMA, MphConstants.NON_SMALL_CELL_CARCINOMA);
    }

    private boolean isPairedSite(String site) {
        return GroupUtility.isSiteContained(MphConstants.ALL_PAIRED_SITES, site);
    }

}
