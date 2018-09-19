/*
 * Copyright (C) 2016 Information Management Services, Inc.
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

public class Mp2010HematopoieticGroup extends MphGroup {

    public Mp2010HematopoieticGroup() {
        super(MphConstants.MP_2010_HEMATO_GROUP_ID, MphConstants.MP_2010_HEMATO_GROUP_NAME, "C000-C809", null, "9590-9989", null, "2-3,6", "2010-9999");

        // M1 
        MphRule rule = new MphRule(MphConstants.MP_2010_HEMATO_GROUP_ID, "M1") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                //TODO
                return result;
            }
        };
        rule.setReason("Abstract a single primary when minimal information is available (such as a death certificate only [DCO] case or a pathology-report-only case).");
        _rules.add(rule);

        // M2 
        rule = new MphRule(MphConstants.MP_2010_HEMATO_GROUP_ID, "M2") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                //TODO
                return result;
            }
        };
        rule.setReason("Abstract a single primary when there is a single histology");
        rule.getNotes().add("Bilateral involvement of lymph nodes and/or organs with a single histology is a single primary.");
        rule.getNotes().add("Recurrence of the same histology is always the same primary (timing is not relevant).");
        rule.getNotes().add("A single histology is diagnosed by the definitive diagnostic method as defined in the Heme DB. For example, the patient had several provisional diagnoses "
                + "but the definitive diagnostic method identified a single histology. Abstract as a single primary.");
        rule.getExamples().add("The diagnosis is multiple myeloma (9732/3). Abstract as a single primary.");
        rule.getExamples().add("Right and left breast both involved with diffuse large B-cell lymphoma (9680/3). Abstract as a single primary.");
        _rules.add(rule);

        // M3
        rule = new MphRule(MphConstants.MP_2010_HEMATO_GROUP_ID, "M3") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                String hist1 = i1.getHistology(), hist2 = i2.getHistology();
                if ((GroupUtility.differentCategory(hist1, hist2, MphConstants.MAST_CELL_SARCOMA, MphConstants.MAST_CELL_LEUKEMIA) || GroupUtility.differentCategory(hist1, hist2,
                        MphConstants.MYELOID_SARCOMA, MphConstants.MYELOID_LEUKEMIA)) && MphConstants.MALIGNANT.equals(i1.getBehavior()) && MphConstants.MALIGNANT.equals(i2.getBehavior())) {
                    int laterDx = GroupUtility.compareDxDate(i1, i2);
                    int simultaneouslyPresent = GroupUtility.verifyDaysApart(i1, i2, 21);
                    if (laterDx == -1 && simultaneouslyPresent == -1) {
                        result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupId());
                    }
                    else if (simultaneouslyPresent == 0)
                        result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                    else if (laterDx == 1 && (MphConstants.MAST_CELL_SARCOMA.contains(hist1) || MphConstants.MYELOID_SARCOMA.contains(hist1)))
                        result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                    else if (laterDx == 2 && (MphConstants.MAST_CELL_SARCOMA.contains(hist2) || MphConstants.MYELOID_SARCOMA.contains(hist2)))
                        result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                }
                return result;
            }
        };
        rule.setReason("Abstract a single primary when a sarcoma is diagnosed simultaneously or after a leukemia of the same lineage, " +
                "Mast cell sarcoma (9740/3) diagnosed simultaneously with or after mast cell leukemia (9742/3), " +
                "Myeloid sarcoma (9930/3) diagnosed simultaneously with or after acute myeloid leukemia (9861/3) or another leukemia of the myeloid lineage (9840/3, 9865/3-9867/3, 9869/3-9874/3, 9891/3, 9895/3-9898/3, 9910/3, 9911/3 and 9931/3)"
                + "Exception: Chronic myeloid leukemia (CML) codes: 9863/3, 9875/3, 9876/3 are not classified as leukemias of the same lineage as myeloid sarcoma");
        rule.getNotes().add("These sarcomas are solid manifestations of the associated leukemias. For example, when acute myeloid leukemia and myeloid sarcoma are diagnosed "
                + "simultaneously, the myeloid sarcoma is the result of myeloid cells migrating from the bone marrow or blood into tissue. It is part of the disease process "
                + "for the acute leukemia.");
        rule.getNotes().add("See Module 5 (PH9 and PH10) for information regarding primary site and histology");
        rule.getExamples().add("Acute myeloid leukemia (AML) diagnosed in 2012. In 2013, a soft tissue mass was biopsied and the pathology report final diagnosis was myeloid "
                + "sarcoma. The myeloid sarcoma is a manifestation of the AML. The malignant myeloid cells are present in the blood. One of the malignant myeloid "
                + "cells lodged in a capillary and grew in the tissue forming a myeloid cell soft tissue mass (referred to as myeloid sarcoma). This is not a second "
                + "primary; it is a direct result of the myeloid cells circulating in the blood. It is not unlike a solid tumor in the colon metastasizing to the liver.");
        _rules.add(rule);

        // M4
        rule = new MphRule(MphConstants.MP_2010_HEMATO_GROUP_ID, "M4") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                String hist1 = i1.getHistology(), hist2 = i2.getHistology(), site1 = i1.getPrimarySite(), site2 = i2.getPrimarySite();
                boolean sameLocation = site1.equals(site2) || (site1.substring(0, 3).equals(site2.substring(0, 3)) && !MphConstants.LYMPH_NODE.equals(site1.substring(0, 3)));
                if (!hist1.equals(hist2) && MphConstants.LYMPHOMA_NOS_AND_NON_HODGKIN_LYMPHOMA.containsAll(Arrays.asList(hist1, hist2)) && sameLocation) {
                    int simultaneouslyPresent = GroupUtility.verifyDaysApart(i1, i2, 21);
                    if (simultaneouslyPresent == -1) {
                        result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupId());
                    }
                    else if (0 == simultaneouslyPresent)
                        result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                }
                return result;
            }
        };
        rule.setReason("Abstract a single primary when two or more types of non-Hodgkin lymphoma are simultaneously present in the same anatomic location(s), such "
                + "as the same lymph node or lymph node region(s), the same organ(s), and/or the same tissue(s)");
        rule.getNotes().add("For the purpose of using the rules, a non-Hodgkin lymphoma is any lymphoma (including the leukemia/lymphomas) not stated to be Hodgkin "
                + "lymphoma, NOS or a type of Hodgkin lymphoma.\n - Hodgkin lymphomas are: 9650/-9653/3, 9655/3, 9659/3, 9663/3");
        rule.getNotes().add("Use Rule M15 for simultaneous occurrences of two or more cutaneous lymphomas. Do not use this rule for cutaneous lymphomas. Simultaneous "
                + "occurrences of two or more cutaneous lymphomas, other than an NOS and more specific, are extremely rare. If there are simultaneous cutaneous "
                + "lymphomas, DO NOT use this rule; proceed to rule M15 (use Multiple Primaries Calculator)");
        rule.getNotes().add("When the neoplasm is in an early stage, the involved lymph node(s) will be in the same region as defined by ICD-O-3 codes. See Appendix C for help "
                + "identifying lymph node names, chains, regions and codes.");
        rule.getNotes().add("When the neoplasm is in a more advanced stage, both non-Hodgkin lymphomas may be present in multiple lymph nodes in the same regions as defined "
                + "by ICD-O-3, or in an organ and that organ’s regional lymph nodes, or in multiple organs.\n"
                + "- Although the combination of two or more types of non-Hodgkin lymphoma must be present in each of the involved sites in order to abstract as a "
                + "single primary, it is not required that all involved organs be biopsied. If the physician biopsies one of the involved sites and diagnoses the "
                + "combination of two or more types of non-Hodgkin lymphoma, assume that all of the nodes, tissues, and/or organs and associated lymph nodes are "
                + "involved with the same combination of non-Hodgkin lymphomas");
        rule.getNotes().add("Do not query the Heme DB Multiple Primaries Calculator in this situation");
        rule.getNotes().add("See Rules PH11 and PH15 for assigning primary site and histology.");
        rule.getExamples().add("Biopsy of cervical lymph node shows follicular lymphoma and DLBCL. Abstract as a single primary.");
        _rules.add(rule);

        // M5
        rule = new MphRule(MphConstants.MP_2010_HEMATO_GROUP_ID, "M5") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                String hist1 = i1.getHistology(), hist2 = i2.getHistology(), site1 = i1.getPrimarySite(), site2 = i2.getPrimarySite();
                boolean sameLocation = site1.equals(site2) || (site1.substring(0, 3).equals(site2.substring(0, 3)) && !MphConstants.LYMPH_NODE.equals(site1.substring(0, 3)));
                if (GroupUtility.differentCategory(hist1, hist2, MphConstants.HODGKIN_LYMPHOMA, MphConstants.LYMPHOMA_NOS_AND_NON_HODGKIN_LYMPHOMA) && sameLocation) {
                    int simultaneouslyPresent = GroupUtility.verifyDaysApart(i1, i2, 21);
                    if (simultaneouslyPresent == -1) {
                        result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupId());
                    }
                    else if (0 == simultaneouslyPresent)
                        result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                }

                return result;
            }
        };
        rule.setReason("Abstract a single primary when both Hodgkin and non-Hodgkin lymphoma are simultaneously present in the same anatomic location(s), such as "
                + "the same lymph node or same lymph node region(s), the same organ(s), and/or the same tissue(s).");
        rule.getNotes().add("For the purpose of using the rules, a non-Hodgkin lymphoma is any lymphoma (including the leukemia/lymphomas) not stated to be Hodgkin "
                + "lymphoma, NOS or a type of Hodgkin lymphoma.\n - Hodgkin lymphomas are: 9650/-9653/3, 9655/3, 9659/3, 9663/3");
        rule.getNotes().add("Do not query the Heme DB Multiple Primaries Calculator in this situation");
        rule.getNotes().add("When the neoplasm is in an early stage, the involved lymph node(s) will be in the same region as defined by ICD-O-3 codes. See Appendix C for help "
                + "identifying lymph node names, chains, regions and codes.");
        rule.getNotes().add("When the neoplasm is in a more advanced stage, both Hodgkin and non-Hodgkin lymphomas may be present in multiple lymph node regions as defined "
                + "by ICD-O-3, or in an organ and that organ’s regional lymph nodes, or in multiple organs.\n"
                + "- Although both Hodgkin and non-Hodgkin lymphomas must be present in each of the involved sites in order to abstract as a single primary, it is not "
                + "required that all involved organs be biopsied. If the physician biopsies one of the involved sites and diagnoses the combination Hodgkin and nonHodgkin "
                + "lymphomas, assume that all of the nodes, tissue, and/or organs are involved with the combination of Hodgkin and non-Hodgkin "
                + "lymphomas.");

        rule.getNotes().add("See PH14 for information regarding primary site and histology.");
        rule.getExamples().add("Biopsy of cervical lymph node shows Hodgkin and non-Hodgkin lymphomas. Abstract as a single primary.");
        _rules.add(rule);

        // M6
        rule = new MphRule(MphConstants.MP_2010_HEMATO_GROUP_ID, "M6") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                String hist1 = i1.getHistology(), hist2 = i2.getHistology(), site1 = i1.getPrimarySite(), site2 = i2.getPrimarySite();
                boolean differentLocation = (!site1.equals(site2) && MphConstants.LYMPH_NODE.equals(site1.substring(0, 3))) || !site1.substring(0, 3).equals(site2.substring(0, 3));
                if (GroupUtility.differentCategory(hist1, hist2, MphConstants.HODGKIN_LYMPHOMA, MphConstants.NON_HODGKIN_LYMPHOMA) && differentLocation)
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setReason("Abstract as multiple primaries when Hodgkin lymphoma is diagnosed in one anatomic location and non-Hodgkin lymphoma is diagnosed in "
                + "another anatomic location.");
        rule.getNotes().add("For the purpose of using the rules, a non-Hodgkin lymphoma is any lymphoma (including the leukemia/lymphomas) not stated to be Hodgkin "
                + "lymphoma, NOS or a type of Hodgkin lymphoma.\n - Hodgkin lymphomas are: 9650/-9653/3, 9655/3, 9659/3, 9663/3");
        rule.getExamples().add("Patient diagnosed with HL in the cervical lymph nodes and with NHL in the GI tract. Abstract as multiple primaries.");
        rule.getExamples().add("Hodgkin lymphoma in a mediastinal mass and non-Hodgkin lymphoma in the tonsil. Abstract as multiple primaries.");
        rule.getExamples().add("NHL in a right cervical node and HL in a left cervical node. Abstract as multiple primaries. Left and right node chains are separate regions. See "
                + "Appendix C.");
        _rules.add(rule);

        // M7
        rule = new MphRule(MphConstants.MP_2010_HEMATO_GROUP_ID, "M7") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                String hist1 = i1.getHistology(), hist2 = i2.getHistology();
                String morph1 = hist1 + "/" + i1.getBehavior(), morph2 = hist2 + "/" + i2.getBehavior();
                int latestDx = GroupUtility.compareDxDate(i1, i2);
                int year1 = Integer.valueOf(i1.getDateOfDiagnosisYear()), year2 = Integer.valueOf(i2.getDateOfDiagnosisYear());
                if (!MphConstants.HEMATOPOIETIC_NOS_HISTOLOGIES.containsAll(Arrays.asList(hist1, hist2)) && (MphConstants.HEMATOPOIETIC_NOS_HISTOLOGIES.contains(hist1)
                        || MphConstants.HEMATOPOIETIC_NOS_HISTOLOGIES.contains(hist2)) && MphUtils.getInstance().getHematoDbUtilsProvider().isSamePrimary(morph1, morph2, year1, year2)
                        && latestDx != 0) {
                    if (latestDx == -1) {
                        result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupId());
                    }
                    else if ((latestDx == 1 && MphConstants.HEMATOPOIETIC_NOS_HISTOLOGIES.contains(hist2)) || (latestDx == 2 && MphConstants.HEMATOPOIETIC_NOS_HISTOLOGIES.contains(hist1)))
                        result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                }
                return result;
            }
        };
        rule.setReason("Abstract as a single primary when a more specific histology is diagnosed after an NOS ONLY when the Heme DB Multiple Primaries Calculator "
                + "confirms that the NOS and the more specific histology are the same primary.");
        rule.getNotes().add("The more specific histology confirmation does not have to occur in the same anatomic location. ");
        rule.getNotes().add("There are no time restrictions on these diagnoses; the interval between the NOS and the more specific histology does not affect this rule.");
        rule.getNotes().add("The Heme DB Multiple Primaries Calculator will identify these histologies as a single primary");
        rule.getNotes().add("Change the histology code on the original abstract to the more specific histology when the original diagnosis is in your registry database. Use previous "
                + "editions of ICD-O (i.e. ICD-O-1, ICD-O-2) or the Heme DB to assign the code applicable to the year of diagnosis for the more specific histology.");
        rule.getExamples().add("Patient diagnosed with non-Hodgkin lymphoma (9591/3) in 2003. Patient returns in 2013 with a diagnosis of CD30 positive lymphoproliferative "
                + "disorder (9718/3). 9591/3 is an NOS histology and 9718/3 is more specific. Per the Multiple Primaries Calculator, 9591/3 and 9718/3 are the same "
                + "primary. 9718/3 was a valid code in 2003; change the histology to 9718/3 for the 2003 diagnosis.");
        rule.getExamples().add("CT guided core biopsy pelvic mass positive for lymphoma (9590/3) diagnosed in 2008. In November 2014, Mediastinoscopy with biopsy shows "
                + "intravascular large B-cell lymphoma. (9712/3). 9590/3 is an NOS histology and 9712/3 is more specific. Per the Multiple Primaries Calculator, 9590/3 "
                + "and 9712/3 are the same primary. Per the Hematopoietic Database, 9712/3 was not valid until 2010. Since the original diagnosis was in 2008, 9712/3 "
                + "cannot be used. Keep the original code of 9590/3.");
        _rules.add(rule);

        // M8
        rule = new MphRule(MphConstants.MP_2010_HEMATO_GROUP_ID, "M8") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                //TODO
                return result;
            }
        };
        rule.setReason("Abstract as a single primary and code the acute neoplasm when both a chronic and an acute neoplasm are diagnosed simultaneously or within 21 "
                + "days AND there is documentation of only one positive biopsy (bone marrow biopsy, lymph node biopsy, or tissue biopsy).");
        rule.getNotes().add("When these diagnoses happen within 21 days, it is most likely that one diagnosis was provisional and the biopsy identified the correct diagnosis. "
                + "Abstract the acute neoplasm.");
        rule.getNotes().add("Transformations to (acute neoplasms) and Transformations from (chronic neoplasms) are defined for each applicable histology in the database.");
        rule.getExamples().add("Clinical workup shows plasmacytoma (9731/3). Lytic lesions also seen on clinical workup. Bone marrow biopsy done which shows multiple myeloma. "
                + "Plasmacytoma transforms to multiple myeloma. Code the multiple myeloma (9732/3) since this is the acute neoplasm and there is only one bone marrow "
                + "biopsy.");
        _rules.add(rule);

        // M9
        rule = new MphRule(MphConstants.MP_2010_HEMATO_GROUP_ID, "M9") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                //TODO
                return result;
            }
        };
        rule.setReason("Abstract a single primary and code the later diagnosis when both a chronic and an acute neoplasm are diagnosed simultaneously or within 21 "
                + "days AND there is no available documentation on biopsy (bone marrow biopsy, lymph node biopsy, or tissue biopsy.) The later diagnosis could be "
                + "either the chronic or the acute neoplasm. ");
        rule.getNotes().add("The two diagnoses are likely the result of an ongoing diagnostic work-up. The later diagnosis is usually based on all of the test results and correlated "
                + "with any clinical information.");
        rule.getNotes().add("Transformations to (acute neoplasms) and Transformations from (chronic neoplasms) are defined for each applicable histology in the database.");

        _rules.add(rule);

        // M10
        rule = new MphRule(MphConstants.MP_2010_HEMATO_GROUP_ID, "M10") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                String morph1 = i1.getHistology() + "/" + i1.getBehavior();
                String morph2 = i2.getHistology() + "/" + i2.getBehavior();
                int year1 = Integer.valueOf(i1.getDateOfDiagnosisYear()), year2 = Integer.valueOf(i2.getDateOfDiagnosisYear());
                //If one disease can not be converted to another, no need to check other criteria
                if (isTransformation(morph1, morph2, year1, year2)) {
                    int latestDx = GroupUtility.compareDxDate(i1, i2);
                    int daysApart = GroupUtility.verifyDaysApart(i1, i2, 21);
                    if (daysApart == -1 || latestDx == -1) {
                        result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                        result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupId());
                    }
                    else if (daysApart == 1 && latestDx > 0 && isChronicToAcuteTransformation(latestDx == 1 ? morph2 : morph1, latestDx == 1 ? morph1 : morph2, latestDx == 1 ? year2 : year1, latestDx == 1 ? year1 : year2))
                        result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                }
                return result;
            }
        };
        rule.setReason("Abstract as multiple primaries when a neoplasm is originally diagnosed as a chronic neoplasm AND there is a second diagnosis of an acute "
                + "neoplasm more than 21 days after the chronic diagnosis.");
        rule.getNotes().add("This is a change from the pre-2010 rules. Use the Heme DB Multiple Primaries Calculator to determine multiple primaries when a transformation "
                + "from a chronic to an acute neoplasm occurs.");
        rule.getNotes().add("Transformations to (acute neoplasms) and Transformations from (chronic neoplasms) are defined for each applicable histology in the database.");
        rule.getExamples().add("Patient was diagnosed with MDS, unclassifiable in 2010. The patient presents in 2013 with a diagnosis of acute myeloid leukemia (AML) (9861/3). The "
                + "transformation paragraph in the Heme DB says MDS (chronic neoplasm) transforms to AML (acute neoplasm). Because the chronic neoplasm (MDS) "
                + "and the acute neoplasm (AML) are diagnosed more than 21 days apart, abstract the MDS and the AML (9861/3) as multiple primaries");
        _rules.add(rule);

        // M11
        rule = new MphRule(MphConstants.MP_2010_HEMATO_GROUP_ID, "M11") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                //TODO
                return result;
            }
        };
        rule.setReason("Abstract as multiple primaries when both a chronic and an acute neoplasm are diagnosed simultaneously or within 21 days AND there is "
                + "documentation of two bone marrow examinations, lymph node biopsies, or tissue biopsies: one confirming the chronic neoplasm and another "
                + "confirming the acute neoplasm.");

        rule.getNotes().add("Transformations to (acute neoplasms) and Transformations from (chronic neoplasms) are defined for each applicable histology in the database.");
        rule.getExamples().add("Vertebral biopsy on 2/13/2013 positive for plasmacytoma and 3/2/2013 bone marrow biopsy was positive for multiple myeloma. Biopsies and diagnoses "
                + "were less than 21 days apart. Code as two primaries, solitary plasmacytoma of bone (9731/3) and plasma cell myeloma/multiple myeloma (9732/3)");
        _rules.add(rule);

        // M12
        rule = new MphRule(MphConstants.MP_2010_HEMATO_GROUP_ID, "M12") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                String morph1 = i1.getHistology() + "/" + i1.getBehavior();
                String morph2 = i2.getHistology() + "/" + i2.getBehavior();
                int year1 = Integer.valueOf(i1.getDateOfDiagnosisYear()), year2 = Integer.valueOf(i2.getDateOfDiagnosisYear());
                //If one disease can not be converted to another, no need to check other criteria
                if (isTransformation(morph1, morph2, year1, year2)) {
                    int latestDx = GroupUtility.compareDxDate(i1, i2);
                    if (latestDx == -1) {
                        result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupId());
                    }
                    else if (latestDx > 0 && isAcuteToChronicTransformation(latestDx == 1 ? morph2 : morph1, latestDx == 1 ? morph1 : morph2, latestDx == 1 ? year2 : year1, latestDx == 1 ? year1 : year2) &&
                            !MphConstants.TREATMENT_GIVEN.equals(latestDx == 1 ? i2.getTxStatus() : i1.getTxStatus()))
                        result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                }
                return result;
            }
        };
        rule.setReason("Abstract a single primary when a neoplasm is originally diagnosed as acute AND reverts to a chronic neoplasm AND there is no confirmation "
                + "available that the patient has been treated for the acute neoplasm");

        rule.getNotes().add("When these diagnoses happen within 21 days, it is most likely that the first diagnosis of acute neoplasm was a provisional diagnosis.");
        rule.getNotes().add("When the subsequent diagnosis occurs more than 21 days after the original diagnosis of acute neoplasm, it is important to follow-back to obtain information "
                + "on treatment or a subsequent bone marrow biopsy that negates the diagnosis of acute neoplasm");
        rule.getNotes().add("Transformations to (acute neoplasms) and Transformations from (chronic neoplasms) are defined for each applicable histology in the database.");
        rule.getExamples().add("3/16/2013 biopsy of cervical nodes positive for diffuse large B-cell lymphoma (DLBCL) (9680/3). 4/18/2013 bone marrow shows follicular lymphoma "
                + "(9690/3). No treatment given between the diagnoses of acute neoplasm (DLBCL) and chronic (follicular). Abstract one primary, DLBCL (9680/3).");
        _rules.add(rule);

        // M13
        rule = new MphRule(MphConstants.MP_2010_HEMATO_GROUP_ID, "M13") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                String morph1 = i1.getHistology() + "/" + i1.getBehavior();
                String morph2 = i2.getHistology() + "/" + i2.getBehavior();

                int year1 = Integer.valueOf(i1.getDateOfDiagnosisYear()), year2 = Integer.valueOf(i2.getDateOfDiagnosisYear());
                //If one disease can not be converted to another, no need to check other criteria
                if (isTransformation(morph1, morph2, year1, year2)) {
                    int latestDx = GroupUtility.compareDxDate(i1, i2);
                    if (latestDx == -1) {
                        result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                        result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupId());
                    }
                    else if (latestDx > 0 && isAcuteToChronicTransformation(latestDx == 1 ? morph2 : morph1, latestDx == 1 ? morph1 : morph2, latestDx == 1 ? year2 : year1, latestDx == 1 ? year1 : year2) &&
                            MphConstants.TREATMENT_GIVEN.equals(latestDx == 1 ? i2.getTxStatus() : i1.getTxStatus()))
                        result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                }
                return result;
            }
        };
        rule.setReason("Abstract multiple primaries when a neoplasm is originally diagnosed as acute AND reverts to a chronic neoplasm after treatment.");

        rule.getNotes().add("Only abstract as multiple primaries when the patient has been treated for the acute neoplasm.");
        rule.getNotes().add("Apply this rule when treatment for the acute neoplasm is given, even when all planned treatment is not completed.");
        rule.getNotes().add("The rules regarding first course of treatment are not the same for Solid Tumors and Hematopoietic. Do not apply the Note 2 to Solid Tumors.");
        rule.getNotes().add("Transformations to (acute neoplasms) and Transformations from (chronic neoplasms) are defined for each applicable histology in the database.");
        rule.getExamples().add("Patient was diagnosed in 2009 with AML, NOS (9861/3). The patient was treated with chemotherapy and a subsequent stem cell transplant. On "
                + "2/25/2013 a bone marrow biopsy was positive for myelodysplastic syndrome. Abstract a second primary with the histology MDS (9989/3).");
        rule.getExamples().add("Patient diagnosed with AML (9861/3). Plan of treatment chemotherapy. If remission achieved, followed by bone marrow transplant. After chemotherapy, "
                + "bone marrow biopsy is done and shows a complete remission regarding the AML, but the bone marrow shows MDS (9989/3). The MDS is a second primary even "
                + "though the planned first course of treatment was not completed prior to the diagnosis of the MDS.");
        _rules.add(rule);

        // M14
        rule = new MphRule(MphConstants.MP_2010_HEMATO_GROUP_ID, "M14") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                List<String> combined = new ArrayList<>(MphConstants.BCELL);
                combined.addAll(MphConstants.TCELL);
                combined.addAll(MphConstants.HODGKIN_LYMPHOMA);
                combined.addAll(MphConstants.PLASMACYTOMA);
                if (GroupUtility.differentCategory(i1.getHistology(), i2.getHistology(), MphConstants.PTLD, combined)) {
                    int daysApart = GroupUtility.verifyDaysApart(i1, i2, 21);
                    if (daysApart == -1) {
                        result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupId());
                    }
                    else if (daysApart == 0)
                        result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                }
                return result;
            }
        };
        rule.setReason("Abstract a single primary when post-transplant lymphoproliferative disorder is diagnosed simultaneously with any B-cell lymphoma, T-cell "
                + "lymphoma, Hodgkin lymphoma or plasmacytoma/myeloma.");

        rule.getNotes().add("This is a change from previous instructions. Previously, lymphomas were listed as PTLD transformations. If there is a diagnosis of a lymphoma AFTER "
                + "PTLD, abstract it is a second primary.");
        rule.getNotes().add("See Rule PH1 for information regarding histology and Module 7 for assigning primary site.");
        rule.getNotes().add("Registrars are NOT required to review cases previously abstracted.");

        _rules.add(rule);

        // M15
        rule = new MphRule(MphConstants.MP_2010_HEMATO_GROUP_ID, "M15") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                String morph1 = i1.getHistology() + "/" + i1.getBehavior(), morph2 = i2.getHistology() + "/" + i2.getBehavior();
                int year1 = Integer.valueOf(i1.getDateOfDiagnosisYear()), year2 = Integer.valueOf(i2.getDateOfDiagnosisYear());
                result.setFinalResult(
                        MphUtils.getInstance().getHematoDbUtilsProvider().isSamePrimary(morph1, morph2, year1, year2) ? MphUtils.MpResult.SINGLE_PRIMARY : MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setReason("Use the Heme DB Multiple Primaries Calculator to determine the number of primaries for all cases that do not meet the criteria of M1-M14.");
        rule.getExamples().add("Polycythemia vera (PV) diagnosed in 2001, receiving anagrelide. Increasing leukocytosis seen, bone marrow biopsy done in 2013 showing primary "
                + "myelofibrosis (PMF) with myeloid metaplasia. No rule in M1-M14 applies. Abstract multiple primaries because the Multiple Primaries Calculator shows that PV "
                + "(9950/3) and PMF (9961/3) are separate primaries.");
        _rules.add(rule);
    }

    private static boolean isTransformation(String leftCode, String rightCode, int leftYear, int rightYear) {
        return isChronicToAcuteTransformation(leftCode, rightCode, leftYear, rightYear) || isAcuteToChronicTransformation(leftCode, rightCode, leftYear, rightYear);
    }

    //when a neoplasm is originally diagnosed as a chronic neoplasm AND there is a second diagnosis of an acute neoplasm
    private static boolean isChronicToAcuteTransformation(String earlierMorph, String latestMorph, int earlierYear, int latestYear) {
        return MphUtils.getInstance().getHematoDbUtilsProvider().canTransformTo(earlierMorph, latestMorph, earlierYear, latestYear);
    }

    //when a neoplasm is originally diagnosed as acute AND reverts to a chronic neoplasm
    private static boolean isAcuteToChronicTransformation(String earlierMorph, String latestMorph, int earlierYear, int latestYear) {
        return MphUtils.getInstance().getHematoDbUtilsProvider().canTransformTo(latestMorph, earlierMorph, latestYear, earlierYear);
    }

}
