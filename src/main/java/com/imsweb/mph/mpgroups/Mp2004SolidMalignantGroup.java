/*
 * Copyright (C) 2016 Information Management Services, Inc.
 */
package com.imsweb.mph.mpgroups;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.imsweb.mph.MphConstants;
import com.imsweb.mph.MphGroup;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphRuleResult;
import com.imsweb.mph.MphUtils;

public class Mp2004SolidMalignantGroup extends MphGroup {

    public Mp2004SolidMalignantGroup() {
        super("solid-malignant-2004", "Solid Malignant 2004", null, null, null, "9590-9989,9140", "2-3,6", "0000-2006");

        // Rule 1 TODO
        MphRule rule = new MphRule("solid-malignant-2004", "M1", MphUtils.MPResult.SINGLE_PRIMARY) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                result.setResult(MphUtils.RuleResult.FALSE);
                return result;
            }
        };
        rule.setReason("A single lesion composed of one histologic type is a single primary, even if the lesion crosses site boundaries.");
        rule.getExamples().add("A single lesion involving the tongue and floor of mouth is one primary.");
        rule.getExamples().add("A single, large mucinous adenocarcinoma involving the sigmoid and descending colon segments is one primary.");
        _rules.add(rule);

        // Rule 2 TODO
        rule = new MphRule("solid-malignant-2004", "M2", MphUtils.MPResult.SINGLE_PRIMARY) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                result.setResult(MphUtils.RuleResult.FALSE);
                return result;
            }
        };
        rule.setReason("A single lesion composed of multiple (different) histologic types is a single primary even if it crosses site boundaries.");
        rule.getExamples().add(" A single lesion containing both embryonal cell carcinoma and teratoma is a single primary and would be coded to 9081/3, mixed embryonal carcinoma and teratoma.");
        rule.getExamples().add(
                "A single lesion of the liver composed of neuroendocrine carcinoma (8246/3) and hepatocellular carcinoma (8170/3) is a single primary and would be coded to the more specific histology, neuroendocrine carcinoma 8246/3.");
        _rules.add(rule);

        // Rule 3
        rule = new MphRule("solid-malignant-2004", "M3", MphUtils.MPResult.SINGLE_PRIMARY) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                result.setResult(MphUtils.RuleResult.FALSE);
                int daysApart = verifyDaysApart(i1, i2, 60);
                if (isSameSite(i1.getPrimarySite(), i2.getPrimarySite()) && isSameHistology(i1.getHistology(), i2.getHistology()) && daysApart != 1) {
                    if (isPairedSite(i1.getPrimarySite()) && isPairedSite(i2.getPrimarySite())) {
                        //Each side of a paired organ is considered a separate site.
                        if (differentCategory(i1.getLaterality(), i2.getLaterality(), Collections.singletonList("1"), Collections.singletonList("2")))
                            return result;
                        else if (!Arrays.asList("1", "2").containsAll(Arrays.asList(i1.getLaterality(), i2.getLaterality()))) {
                            result.setResult(MphUtils.RuleResult.UNKNOWN);
                            if (daysApart == -1)
                                result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". Valid and known laterality and diagnosis date should be provided.");
                            else
                                result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". Valid and known laterality for paired sites should be provided.");
                            return result;
                        }
                    }
                    if (daysApart == -1) {
                        result.setResult(MphUtils.RuleResult.UNKNOWN);
                        result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". Valid and known diagnosis date should be provided.");
                    }
                    else
                        result.setResult(MphUtils.RuleResult.TRUE);
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
        rule = new MphRule("solid-malignant-2004", "M4", MphUtils.MPResult.MULTIPLE_PRIMARIES) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                result.setResult(MphUtils.RuleResult.FALSE);
                String site1 = i1.getPrimarySite(), site2 = i2.getPrimarySite(), hist1 = i1.getHistology(), hist2 = i2.getHistology(), lat1 = i1.getLaterality(), lat2 = i2.getLaterality();
                int daysApart = verifyDaysApart(i1, i2, 60);
                if (isSameSite(site1, site2) && isSameHistology(hist1, hist2) && isPairedSite(site1) && isPairedSite(site2) && daysApart != 1) {
                    if (!Arrays.asList("1", "2").containsAll(Arrays.asList(lat1, lat2))) {
                        result.setResult(MphUtils.RuleResult.UNKNOWN);
                        if (daysApart == -1)
                            result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". Valid and known laterality and diagnosis date should be provided.");
                        else
                            result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". Valid and known laterality for paired sites should be provided.");
                    }
                    else if (daysApart == -1 && !differentCategory(lat1, lat2, Collections.singletonList("1"), Collections.singletonList("2"))) {
                        result.setResult(MphUtils.RuleResult.UNKNOWN);
                        result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". Valid and known diagnosis date should be provided.");
                    }
                    else if (daysApart == 0 && differentCategory(lat1, lat2, Collections.singletonList("1"), Collections.singletonList("2"))) {
                        result.setResult(MphUtils.RuleResult.TRUE);
                        //Exceptions
                        List<String> retino = Arrays.asList("9510", "9511", "9512", "9513");
                        if (("C569".equals(site1) && "C569".equals(site2)) || (retino.containsAll(Arrays.asList(hist1, hist2))) || ("8960".equals(hist1) && hist1.equals(hist2)))
                            this.setResult(MphUtils.MPResult.SINGLE_PRIMARY);
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
        rule = new MphRule("solid-malignant-2004", "M5", MphUtils.MPResult.MULTIPLE_PRIMARIES) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                result.setResult(MphUtils.RuleResult.FALSE);
                String site1 = i1.getPrimarySite(), site2 = i2.getPrimarySite(), hist1 = i1.getHistology(), hist2 = i2.getHistology(), beh1 = i1.getBehavior(), beh2 =
                        i2.getBehavior();
                int daysApart = verifyDaysApart(i1, i2, 60);
                if (isSameSite(site1, site2) && isSameHistology(hist1, hist2) && isPairedSite(site1) && isPairedSite(site2) && daysApart != 0) {
                    if (daysApart == -1) {
                        result.setResult(MphUtils.RuleResult.UNKNOWN);
                        result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". Valid and known diagnosis date should be provided.");
                    }
                    else {
                        result.setResult(MphUtils.RuleResult.TRUE);
                        //Exceptions
                        String adenocarcinoma = "8000-8005,8010-8011,8020-8022,8046,8141-8148,8154,8160-8162,8190,8200-8201,8210-8211,8214-8215,8220-8221,8230-8231," +
                                "8244-8245,8250-8255,8260-8263,8270-8272,8280-8281,8290,8300,8310,8312-8320,8322-8323,8330-8333,8335," +
                                "8337,8350,8370,8380-8384,8390,8400-8403,8407-8409,8410,8413,8420,8440-8442,8450-8453,8460-8462,8470-8473," +
                                "8480-8482,8490,8500-8504,8507-8508,8510,8512-8514,8520-8525,8530,8540-8543,8550-8551,8561-8562,8570-8576";
                        if ("C619".equals(site1) && "C619".equals(site2) && "2".equals(beh1) && "2".equals(beh2) && isContained(computeRange(adenocarcinoma, false), Integer.valueOf(hist1))
                                && isContained(computeRange(adenocarcinoma, false), Integer.valueOf(hist2)))
                            this.setResult(MphUtils.MPResult.SINGLE_PRIMARY);

                        String carcinoma = "8120-8131";
                        if (site1.startsWith("C67") && site2.startsWith("C67") && "2".equals(beh1) && "2".equals(beh2) && isContained(computeRange(carcinoma, false), Integer.valueOf(hist1))
                                && isContained(computeRange(carcinoma, false), Integer.valueOf(hist2)))
                            this.setResult(MphUtils.MPResult.SINGLE_PRIMARY);

                        if ("9140".equals(hist1) && "9140".equals(hist2))
                            this.setResult(MphUtils.MPResult.SINGLE_PRIMARY);
                    }
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
        rule = new MphRule("solid-malignant-2004", "M6", MphUtils.MPResult.MULTIPLE_PRIMARIES) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                result.setResult(MphUtils.RuleResult.FALSE);
                String site1 = i1.getPrimarySite(), site2 = i2.getPrimarySite(), hist1 = i1.getHistology(), hist2 = i2.getHistology(), lat1 = i1.getLaterality(), lat2 = i2.getLaterality();
                int daysApart = verifyDaysApart(i1, i2, 60);
                if (isSameSite(site1, site2) && !isSameHistology(hist1, hist2) && daysApart != 1) {
                    if (isPairedSite(i1.getPrimarySite()) && isPairedSite(i2.getPrimarySite())) {
                        //only single paired
                        if (differentCategory(lat1, lat2, Collections.singletonList("1"), Collections.singletonList("2")))
                            return result;
                        else if (!Arrays.asList("1", "2").containsAll(Arrays.asList(lat1, lat2))) {
                            result.setResult(MphUtils.RuleResult.UNKNOWN);
                            if (daysApart == -1)
                                result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". Valid and known laterality and diagnosis date should be provided.");
                            else
                                result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". Valid and known laterality for paired sites should be provided.");
                            return result;
                        }
                    }
                    if (daysApart == -1) {
                        result.setResult(MphUtils.RuleResult.UNKNOWN);
                        result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". Valid and known diagnosis date should be provided.");
                    }
                    else {
                        result.setResult(MphUtils.RuleResult.TRUE);
                        //Exceptions
                        if (differentCategory(hist1, hist2, MphConstants._CARCINOMA_NOS, MphConstants._CARCINOMA_SPECIFIC))
                            this.setResult(MphUtils.MPResult.SINGLE_PRIMARY);
                        else if (differentCategory(hist1, hist2, MphConstants._ADENOCARCINOMA_NOS, MphConstants._ADENOCARCINOMA_SPECIFIC))
                            this.setResult(MphUtils.MPResult.SINGLE_PRIMARY);
                        else if (differentCategory(hist1, hist2, MphConstants._MELANOMA_NOS, MphConstants._MELANOMA_SPECIFIC))
                            this.setResult(MphUtils.MPResult.SINGLE_PRIMARY);
                        else if (differentCategory(hist1, hist2, MphConstants._SARCOMA_NOS, MphConstants._SARCOMA_SPECIFIC))
                            this.setResult(MphUtils.MPResult.SINGLE_PRIMARY);
                        else if ((site1.startsWith(MphConstants._COLON) || MphConstants._RECTUM.equals(site1)) && (site2.startsWith(MphConstants._COLON) || MphConstants._RECTUM.equals(site2))
                                && MphConstants._FAMILIAL_ADENOMATOUS_POLYPOSIS.containsAll(Arrays.asList(hist1, hist2)))
                            this.setResult(MphUtils.MPResult.SINGLE_PRIMARY);
                        else if (MphConstants._THYROID.equals(site1) && MphConstants._THYROID.equals(site2) && (MphConstants._FOLLICULAR.contains(hist1) || MphConstants._PAPILLARY.contains(hist1))
                                && (MphConstants._FOLLICULAR.contains(hist2) || MphConstants._PAPILLARY.contains(hist2)))
                            this.setResult(MphUtils.MPResult.SINGLE_PRIMARY);
                        else if (MphConstants._BLADDER.equals(site1) && MphConstants._BLADDER.equals(site2) && (MphConstants._PAPILLARY_CARCINOMA.equals(hist1)
                                || MphConstants._TRANSITIONAL_CELL_CARCINOMA.contains(hist1) || MphConstants._PAPILLARY_TRANSITIONAL_CELL_CARCINOMA.contains(hist1)) && (
                                MphConstants._PAPILLARY_CARCINOMA.equals(hist2) || MphConstants._TRANSITIONAL_CELL_CARCINOMA.contains(hist2) || MphConstants._PAPILLARY_TRANSITIONAL_CELL_CARCINOMA
                                        .contains(hist2)))
                            this.setResult(MphUtils.MPResult.SINGLE_PRIMARY);
                        else if (MphConstants._BREAST.equals(site1) && MphConstants._BREAST.equals(site2)) {
                            if ((MphConstants._DUCT_CARCINOMA.contains(hist1) || MphConstants._LOBULAR_CARCINOMA.contains(hist1)) && (MphConstants._DUCT_CARCINOMA.contains(hist2)
                                    || MphConstants._LOBULAR_CARCINOMA.contains(hist2)))
                                this.setResult(MphUtils.MPResult.SINGLE_PRIMARY);
                            else if ((MphConstants._PAGET_DISEASE.contains(hist1) || MphConstants._DUCT_CARCINOMA.contains(hist1) || MphConstants._INTRADUCTAL_CARCINOMA.contains(hist1)) && (
                                    MphConstants._PAGET_DISEASE.contains(hist2) || MphConstants._DUCT_CARCINOMA.contains(hist2) || MphConstants._INTRADUCTAL_CARCINOMA.contains(hist2)))
                                this.setResult(MphUtils.MPResult.SINGLE_PRIMARY);
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
        rule = new MphRule("solid-malignant-2004", "M7", MphUtils.MPResult.MULTIPLE_PRIMARIES) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                result.setResult(MphUtils.RuleResult.FALSE);
                String site1 = i1.getPrimarySite(), site2 = i2.getPrimarySite(), hist1 = i1.getHistology(), hist2 = i2.getHistology(), lat1 = i1.getLaterality(), lat2 = i2.getLaterality();
                int daysApart = verifyDaysApart(i1, i2, 60);
                if (isSameSite(site1, site2) && !isSameHistology(hist1, hist2) && daysApart != 1 && isPairedSite(site1) && isPairedSite(site2)) {
                    if (!Arrays.asList("1", "2").containsAll(Arrays.asList(lat1, lat2))) {
                        result.setResult(MphUtils.RuleResult.UNKNOWN);
                        if (daysApart == -1)
                            result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". Valid and known laterality and diagnosis date should be provided.");
                        else
                            result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". Valid and known laterality for paired sites should be provided.");
                    }
                    else if (daysApart == -1) {
                        result.setResult(MphUtils.RuleResult.UNKNOWN);
                        result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". Valid and known diagnosis date should be provided.");
                    }
                    else if (!lat1.equals(lat2))
                        result.setResult(MphUtils.RuleResult.TRUE);
                }
                return result;
            }
        };
        rule.setReason("Multiple synchronous lesions of different histologic types in paired organs are multiple primaries. If one histologic type is reported in one side of a paired organ and a "
                + "different histologic type is reported in the other paired organ, these are two primaries unless there is a statement to the contrary.");
        rule.getExamples().add("If a ductal tumor occurs in one breast and a lobular tumor occurs in the opposite breast, these are two separate primaries.");
        _rules.add(rule);

        //Rule 8
        rule = new MphRule("solid-malignant-2004", "M8", MphUtils.MPResult.MULTIPLE_PRIMARIES) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                result.setResult(MphUtils.RuleResult.FALSE);
                String site1 = i1.getPrimarySite(), site2 = i2.getPrimarySite(), hist1 = i1.getHistology(), hist2 = i2.getHistology();
                int daysApart = verifyDaysApart(i1, i2, 60);
                if (isSameSite(site1, site2) && !isSameHistology(hist1, hist2) && daysApart != 0) {
                    if (daysApart == -1) {
                        result.setResult(MphUtils.RuleResult.UNKNOWN);
                        result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". Valid and known diagnosis date should be provided.");
                    }
                    else
                        result.setResult(MphUtils.RuleResult.TRUE);
                }
                return result;
            }
        };
        rule.setReason("Multiple metachronous lesions of different histologic types within a single site are separate primaries.");
        _rules.add(rule);

        //Rule 9
        rule = new MphRule("solid-malignant-2004", "M9", MphUtils.MPResult.MULTIPLE_PRIMARIES) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                String site1 = i1.getPrimarySite(), site2 = i2.getPrimarySite(), hist1 = i1.getHistology(), hist2 = i2.getHistology();
                result.setResult(!isSameSite(site1, site2) && !isSameHistology(hist1, hist2) ? MphUtils.RuleResult.TRUE : MphUtils.RuleResult.FALSE);
                return result;
            }
        };
        rule.setReason("Multiple lesions of different histologic types occurring in different sites are separate primaries whether occurring simultaneously or at different times.");
        rule.getExamples().add(
                "In 1999, the patient had a mucin-producing carcinoma of the transverse colon. In 2002, the patient was diagnosed with an astrocytoma of the frontal lobe of the brain. Abstract as separate primaries.");
        rule.getExamples().add("During the workup for a transitional cell carcinoma of the bladder, the patient has a TURP that shows adenocarcinoma of the prostate. Abstract as separate primaries.");
        _rules.add(rule);

        //Rule 10
        rule = new MphRule("solid-malignant-2004", "M10", MphUtils.MPResult.MULTIPLE_PRIMARIES) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                String site1 = i1.getPrimarySite(), site2 = i2.getPrimarySite(), hist1 = i1.getHistology(), hist2 = i2.getHistology();
                result.setResult(!isSameSite(site1, site2) && isSameHistology(hist1, hist2) ? MphUtils.RuleResult.TRUE : MphUtils.RuleResult.FALSE);
                return result;
            }
        };
        rule.setReason("Multiple lesions of the same histologic type occurring in different sites are separate primaries unless stated to be metastatic.");
        _rules.add(rule);
    }

    private boolean isSameSite(String site1, String site2) {
        List<String> exactMatches = Arrays.asList("C18", "C21", "C38", "C40", "C41", "C44", "C47", "C49");
        String s1 = site1.substring(0, 3), s2 = site2.substring(0, 3);
        if (site1.equals(site2))
            return true;
        else if (s1.equals(s2) && !exactMatches.contains(s1) && !exactMatches.contains(s2))
            return true;
        else if (Arrays.asList("C01", "C02").containsAll(Arrays.asList(s1, s2)))
            return true;
        else if (Arrays.asList("C05", "C06").containsAll(Arrays.asList(s1, s2)))
            return true;
        else if (Arrays.asList("C07", "C08").containsAll(Arrays.asList(s1, s2)))
            return true;
        else if (Arrays.asList("C09", "C10").containsAll(Arrays.asList(s1, s2)))
            return true;
        else if (Arrays.asList("C12", "C13").containsAll(Arrays.asList(s1, s2)))
            return true;
        else if (Arrays.asList("C23", "C24").containsAll(Arrays.asList(s1, s2)))
            return true;
        else if (Arrays.asList("C30", "C31").containsAll(Arrays.asList(s1, s2)))
            return true;
        else if (Arrays.asList("C33", "C34").containsAll(Arrays.asList(s1, s2)))
            return true;
        else if (Arrays.asList("C37", "C38").containsAll(Arrays.asList(s1, s2)) && !"C384".equals(site1) && !"C384".equals(site2))
            return true;
        else if ((Arrays.asList("C51", "C52").contains(s1) || Arrays.asList("C577", "C578", "C579").contains(site1)) &&
                (Arrays.asList("C51", "C52").contains(s2) || Arrays.asList("C577", "C578", "C579").contains(site2)))
            return true;
        else if (("C56".equals(s1) || Arrays.asList("C570", "C571", "C572", "C573", "C574").contains(site1)) &&
                ("C56".equals(s2) || Arrays.asList("C570", "C571", "C572", "C573", "C574").contains(site2)))
            return true;
        else if (Arrays.asList("C60", "C63").containsAll(Arrays.asList(s1, s2)))
            return true;
        else if (Arrays.asList("C64", "C65", "C66", "C68").containsAll(Arrays.asList(s1, s2)))
            return true;
        else if (Arrays.asList("C74", "C75").containsAll(Arrays.asList(s1, s2)))
            return true;
        else
            return false;

    }

    private boolean isSameHistology(String hist1, String hist2) {
        return hist1.substring(0, 3).equals(hist2.substring(0, 3)) && !differentCategory(hist1, hist2, Collections.singletonList("8046"), expandList(Collections.singletonList("8041-8045")));
    }

    private boolean isPairedSite(String site) {
        String paired =
                "C079,C080-C081,C090-C099,C300-C301,C310,C312,C340-C349,C384,C400-C403,C413-C414,C441-C443,C445-C447,C471-C472,C491-C492,C500-C509,C569,C570,C620-C629,C630-C631,C649,C659,C669,C690-C699,C700,C710-C714,C722-C725,C740-C749,C754";
        return isContained(computeRange(paired, true), Integer.valueOf(site.substring(1)));
    }

}
