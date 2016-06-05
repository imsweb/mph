/*
 * Copyright (C) 2016 Information Management Services, Inc.
 */
package com.imsweb.mph.mpgroups;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.imsweb.mph.MphGroup;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphRuleResult;
import com.imsweb.mph.MphUtils;

public class Mp2010HematopoieticGroup extends MphGroup {

    private static final List<String> _LYMPH_NODE_SITES = Arrays.asList("C770", "C771", "C772", "C773", "C774", "C775", "C776", "C777", "C778", "C779");
    private static final List<String> _LYMPHOMA_NOS_AND_NON_HODGKIN_LYMPHOMA = expandList(Collections.singletonList("9590-9591,9670-9729"));
    private static final List<String> _NON_HODGKIN_LYMPHOMA = expandList(Collections.singletonList("9591,9670-9729"));
    private static final List<String> _HODGKIN_LYMPHOMA = expandList(Collections.singletonList("9596,9650-9667"));
    private static final List<String> _HEMATOPOIETIC_NOS_HISTOLOGIES = expandList(
            Collections.singletonList("9591,9670,9702,9729,9760,9800,9808,9809,9811,9820,9832,9835,9860,9861,9863,9960,9964,9987"));

    public Mp2010HematopoieticGroup() {
        super("hematopoietic-2010", "Hematopoietic 2010", "C000-C809", null, "9590-9989", null, "2-3,6", "2010-9999");

        // M3
        MphRule rule = new MphRule("hematopoietic-2010", "M3", MphUtils.MPResult.SINGLE_PRIMARY) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                String hist1 = i1.getHistologyIcdO3(), hist2 = i2.getHistologyIcdO3();
                List<String> myeloidLeukemia = Arrays.asList("9861", "9840", "9865", "9866", "9867", "9869", "9870", "9871", "9872", "9873", "9874", "9891", "9895", "9896", "9897", "9898", "9910",
                        "9911", "9931");
                if (!differentCategory(hist1, hist2, Collections.singletonList("9740"), Collections.singletonList("9742")) || !differentCategory(hist1, hist2, Collections.singletonList("9930"),
                        myeloidLeukemia) || !"3".equals(i1.getHistologyIcdO3()) || !"3".equals(i2.getHistologyIcdO3())) {
                    result.setResult(MphUtils.RuleResult.FALSE);
                    return result;
                }

                int laterDx = MphGroup.compareDxDate(i1, i2);
                int simultaneouslyPresent = MphGroup.verifyDaysApart(i1, i2, 21);
                if (laterDx == -1 && simultaneouslyPresent == -1) {
                    result.setResult(MphUtils.RuleResult.UNKNOWN);
                    result.setMessage("Unable to apply Rule" + this.getStep() + " of " + this.getGroupId() + ". Known diagnosis date should be provided.");
                }
                else if (simultaneouslyPresent == 0) {
                    result.setResult(MphUtils.RuleResult.TRUE);
                }
                else if (laterDx == 1 && ("9740".equals(hist1) || "99300".equals(hist1)))
                    result.setResult(MphUtils.RuleResult.TRUE);
                else if (laterDx == 2 && ("9740".equals(hist2) || "99300".equals(hist2)))
                    result.setResult(MphUtils.RuleResult.TRUE);
                else
                    result.setResult(MphUtils.RuleResult.FALSE);

                return result;
            }
        };
        rule.setReason("Abstract a single primary when a sarcoma is diagnosed simultaneously or after a leukemia of the same lineage, " +
                "Mast cell sarcoma (9740/3) diagnosed simultaneously with or after mast cell leukemia (9742/3), " +
                "Myeloid sarcoma (9930/3) diagnosed simultaneously with or after acute myeloid leukemia (9861/3) or another leukemia of the myeloid lineage (9840/3, 9865/3-9867/3, 9869/3-9874/3, 9891/3, 9895/3-9898/3, 9910/3, 9911/3 and 9931/3)"
                +
                "Exception: Chronic myeloid leukemia (CML) codes: 9863/3, 9875/3, 9876/3 are not classified as leukemias of the same lineage as myeloid sarcoma");
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
        rule = new MphRule("hematopoietic-2010", "M4", MphUtils.MPResult.SINGLE_PRIMARY) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                String hist1 = i1.getHistologyIcdO3(), hist2 = i2.getHistologyIcdO3(), site1 = i1.getPrimarySite(), site2 = i2.getPrimarySite();
                List<String> cutaneousLymphoma = Arrays.asList("9597", "9709", "9718", "9726");
                boolean sameLocation = site1.equals(site2) || (site1.substring(0, 3).equals(site2.substring(0, 3)) && !_LYMPH_NODE_SITES.containsAll(Arrays.asList(site1, site2)));
                if (hist1.equals(hist2) || !_LYMPHOMA_NOS_AND_NON_HODGKIN_LYMPHOMA.containsAll(Arrays.asList(hist1, hist2)) || cutaneousLymphoma.containsAll(Arrays.asList(hist1, hist2))
                        || !sameLocation) {
                    result.setResult(MphUtils.RuleResult.FALSE);
                    return result;
                }
                int simultaneouslyPresent = verifyDaysApart(i1, i2, 21);
                if (simultaneouslyPresent == -1) {
                    result.setResult(MphUtils.RuleResult.UNKNOWN);
                    result.setMessage("Unable to apply Rule" + this.getStep() + " of " + this.getGroupId() + ". Known diagnosis date should be provided.");
                }
                result.setResult(0 == simultaneouslyPresent ? MphUtils.RuleResult.TRUE : MphUtils.RuleResult.FALSE);

                return result;
            }
        };
        rule.setReason("Abstract a single primary when two or more types of non-Hodgkin lymphoma are simultaneously present in the same anatomic location(s), such "
                + "as the same lymph node or lymph node region(s), the same organ(s), and/or the same tissue(s)");
        rule.getNotes().add("For the purpose of using the rules, a non-Hodgkin lymphoma is any lymphoma (including the leukemia/lymphomas) not stated to be Hodgkin "
                + "lymphoma, NOS or a type of Hodgkin lymphoma.\n • Hodgkin lymphomas are: 9650/-9653/3, 9655/3, 9659/3, 9663/3");
        rule.getNotes().add("Use Rule M15 for simultaneous occurrences of two or more cutaneous lymphomas. Do not use this rule for cutaneous lymphomas. Simultaneous "
                + "occurrences of two or more cutaneous lymphomas, other than an NOS and more specific, are extremely rare. If there are simultaneous cutaneous "
                + "lymphomas, DO NOT use this rule; proceed to rule M15 (use Multiple Primaries Calculator)");
        rule.getNotes().add("When the neoplasm is in an early stage, the involved lymph node(s) will be in the same region as defined by ICD-O-3 codes. See Appendix C for help "
                + "identifying lymph node names, chains, regions and codes.");
        rule.getNotes().add("When the neoplasm is in a more advanced stage, both non-Hodgkin lymphomas may be present in multiple lymph nodes in the same regions as defined "
                + "by ICD-O-3, or in an organ and that organ’s regional lymph nodes, or in multiple organs.\n"
                + "• Although the combination of two or more types of non-Hodgkin lymphoma must be present in each of the involved sites in order to abstract as a "
                + "single primary, it is not required that all involved organs be biopsied. If the physician biopsies one of the involved sites and diagnoses the "
                + "combination of two or more types of non-Hodgkin lymphoma, assume that all of the nodes, tissues, and/or organs and associated lymph nodes are "
                + "involved with the same combination of non-Hodgkin lymphomas");
        rule.getNotes().add("Do not query the Heme DB Multiple Primaries Calculator in this situation");
        rule.getNotes().add("See Rules PH11 and PH15 for assigning primary site and histology.");
        rule.getExamples().add("Biopsy of cervical lymph node shows follicular lymphoma and DLBCL. Abstract as a single primary.");
        _rules.add(rule);

        // M5
        rule = new MphRule("hematopoietic-2010", "M5", MphUtils.MPResult.SINGLE_PRIMARY) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                String hist1 = i1.getHistologyIcdO3(), hist2 = i2.getHistologyIcdO3(), site1 = i1.getPrimarySite(), site2 = i2.getPrimarySite();
                boolean sameLocation = site1.equals(site2) || (site1.substring(0, 3).equals(site2.substring(0, 3)) && !_LYMPH_NODE_SITES.containsAll(Arrays.asList(site1, site2)));
                if (!differentCategory(hist1, hist2, _HODGKIN_LYMPHOMA, _LYMPHOMA_NOS_AND_NON_HODGKIN_LYMPHOMA) || !sameLocation) {
                    result.setResult(MphUtils.RuleResult.FALSE);
                    return result;
                }
                int simultaneouslyPresent = verifyDaysApart(i1, i2, 21);
                if (simultaneouslyPresent == -1) {
                    result.setResult(MphUtils.RuleResult.UNKNOWN);
                    result.setMessage("Unable to apply Rule" + this.getStep() + " of " + this.getGroupId() + ". Known diagnosis date should be provided.");
                }
                result.setResult(0 == simultaneouslyPresent ? MphUtils.RuleResult.TRUE : MphUtils.RuleResult.FALSE);

                return result;
            }
        };
        rule.setReason("Abstract a single primary when both Hodgkin and non-Hodgkin lymphoma are simultaneously present in the same anatomic location(s), such as "
                + "the same lymph node or same lymph node region(s), the same organ(s), and/or the same tissue(s).");
        rule.getNotes().add("For the purpose of using the rules, a non-Hodgkin lymphoma is any lymphoma (including the leukemia/lymphomas) not stated to be Hodgkin "
                + "lymphoma, NOS or a type of Hodgkin lymphoma.\n • Hodgkin lymphomas are: 9650/-9653/3, 9655/3, 9659/3, 9663/3");
        rule.getNotes().add("Do not query the Heme DB Multiple Primaries Calculator in this situation");
        rule.getNotes().add("When the neoplasm is in an early stage, the involved lymph node(s) will be in the same region as defined by ICD-O-3 codes. See Appendix C for help "
                + "identifying lymph node names, chains, regions and codes.");
        rule.getNotes().add("When the neoplasm is in a more advanced stage, both Hodgkin and non-Hodgkin lymphomas may be present in multiple lymph node regions as defined "
                + "by ICD-O-3, or in an organ and that organ’s regional lymph nodes, or in multiple organs.\n"
                + "• Although both Hodgkin and non-Hodgkin lymphomas must be present in each of the involved sites in order to abstract as a single primary, it is not "
                + "required that all involved organs be biopsied. If the physician biopsies one of the involved sites and diagnoses the combination Hodgkin and nonHodgkin "
                + "lymphomas, assume that all of the nodes, tissue, and/or organs are involved with the combination of Hodgkin and non-Hodgkin "
                + "lymphomas.");

        rule.getNotes().add("See PH14 for information regarding primary site and histology.");
        rule.getExamples().add("Biopsy of cervical lymph node shows Hodgkin and non-Hodgkin lymphomas. Abstract as a single primary.");
        _rules.add(rule);

        // M6
        rule = new MphRule("hematopoietic-2010", "M6", MphUtils.MPResult.MULTIPLE_PRIMARIES) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                String hist1 = i1.getHistologyIcdO3(), hist2 = i2.getHistologyIcdO3(), site1 = i1.getPrimarySite(), site2 = i2.getPrimarySite();
                boolean differentLocation = (!site1.equals(site2) && _LYMPH_NODE_SITES.containsAll(Arrays.asList(site1, site2))) || !site1.substring(0, 3).equals(site2.substring(0, 3));
                result.setResult(differentCategory(hist1, hist2, _HODGKIN_LYMPHOMA, _NON_HODGKIN_LYMPHOMA) && differentLocation ? MphUtils.RuleResult.TRUE : MphUtils.RuleResult.FALSE);
                return result;
            }
        };
        rule.setReason("Abstract as multiple primaries when Hodgkin lymphoma is diagnosed in one anatomic location and non-Hodgkin lymphoma is diagnosed in "
                + "another anatomic location.");
        rule.getNotes().add("For the purpose of using the rules, a non-Hodgkin lymphoma is any lymphoma (including the leukemia/lymphomas) not stated to be Hodgkin "
                + "lymphoma, NOS or a type of Hodgkin lymphoma.\n • Hodgkin lymphomas are: 9650/-9653/3, 9655/3, 9659/3, 9663/3");
        rule.getExamples().add("Patient diagnosed with HL in the cervical lymph nodes and with NHL in the GI tract. Abstract as multiple primaries.");
        rule.getExamples().add("Hodgkin lymphoma in a mediastinal mass and non-Hodgkin lymphoma in the tonsil. Abstract as multiple primaries.");
        rule.getExamples().add("NHL in a right cervical node and HL in a left cervical node. Abstract as multiple primaries. Left and right node chains are separate regions. See "
                + "Appendix C.");
        _rules.add(rule);

        // M7
        rule = new MphRule("hematopoietic-2010", "M7", MphUtils.MPResult.SINGLE_PRIMARY) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                String hist1 = i1.getHistologyIcdO3(), hist2 = i2.getHistologyIcdO3();
                String morph1 = hist1 + "/" + i1.getBehaviorIcdO3(), morph2 = hist2 + "/" + i2.getBehaviorIcdO3();
                int latestDx = MphGroup.compareDxDate(i1, i2);
                int latestYear = latestDx == 1 ? Integer.valueOf(i1.getDateOfDiagnosisYear()) : Integer.valueOf(i2.getDateOfDiagnosisYear());
                if (_HEMATOPOIETIC_NOS_HISTOLOGIES.containsAll(Arrays.asList(hist1, hist2)) || (!_HEMATOPOIETIC_NOS_HISTOLOGIES.contains(hist1) && !_HEMATOPOIETIC_NOS_HISTOLOGIES.contains(hist2))
                        || !MphUtils.isSamePrimary(morph1, morph2, latestYear) || latestDx == 0) {
                    result.setResult(MphUtils.RuleResult.FALSE);
                    return result;
                }

                if (latestDx == -1) {
                    result.setResult(MphUtils.RuleResult.UNKNOWN);
                    result.setMessage("Unable to apply Rule" + this.getStep() + " of " + this.getGroupId() + ". Known diagnosis date should be provided.");
                }
                else if ((latestDx == 1 && _HEMATOPOIETIC_NOS_HISTOLOGIES.contains(hist2)) || (latestDx == 2 && _HEMATOPOIETIC_NOS_HISTOLOGIES.contains(hist1)))
                    result.setResult(MphUtils.RuleResult.TRUE);
                else
                    result.setResult(MphUtils.RuleResult.FALSE);

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

        // M8 TODO
        rule = new MphRule("hematopoietic-2010", "M8", MphUtils.MPResult.SINGLE_PRIMARY) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                result.setResult(MphUtils.RuleResult.FALSE);
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

        // M9 TODO
        rule = new MphRule("hematopoietic-2010", "M9", MphUtils.MPResult.SINGLE_PRIMARY) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                result.setResult(MphUtils.RuleResult.FALSE);
                return result;
            }
        };
        rule.setReason("Abstract a single primary* and code the later diagnosis when both a chronic and an acute neoplasm are diagnosed simultaneously or within 21 "
                + "days AND there is no available documentation on biopsy (bone marrow biopsy, lymph node biopsy, or tissue biopsy.) The later diagnosis could be "
                + "either the chronic or the acute neoplasm. ");
        rule.getNotes().add("The two diagnoses are likely the result of an ongoing diagnostic work-up. The later diagnosis is usually based on all of the test results and correlated "
                + "with any clinical information.");
        rule.getNotes().add("Transformations to (acute neoplasms) and Transformations from (chronic neoplasms) are defined for each applicable histology in the database.");

        _rules.add(rule);

        // M10
        rule = new MphRule("hematopoietic-2010", "M8", MphUtils.MPResult.MULTIPLE_PRIMARIES) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                String morph1 = i1.getHistologyIcdO3() + "/" + i1.getBehaviorIcdO3();
                String morph2 = i2.getHistologyIcdO3() + "/" + i2.getBehaviorIcdO3();
                int latestDx = MphGroup.compareDxDate(i1, i2);
                int latestYear = latestDx == 1 ? Integer.valueOf(i1.getDateOfDiagnosisYear()) : Integer.valueOf(i2.getDateOfDiagnosisYear());
                if (!isTransformation(morph1, morph2, latestYear))
                    result.setResult(MphUtils.RuleResult.FALSE);
                else {
                    int daysApart = verifyDaysApart(i1, i2, 21);
                    if (latestDx == -1 || daysApart == -1) {
                        result.setResult(MphUtils.RuleResult.UNKNOWN);
                        result.setMessage("Unable to apply Rule" + this.getStep() + " of " + this.getGroupId() + ". Known diagnosis date should be provided.");
                    }
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
    }

    private static boolean isTransformation(String leftCode, String rightCode, int year) {
        return MphUtils.isAcuteTransformation(leftCode, rightCode, year) || MphUtils.isAcuteTransformation(rightCode, leftCode, year) ||
                MphUtils.isChronicTransformation(leftCode, rightCode, year) || MphUtils.isChronicTransformation(rightCode, leftCode, year);
    }

    private static boolean isAcuteToChronicTransformation(String earlierMorph, String latestMorph, int year) {
        return MphUtils.isChronicTransformation(earlierMorph, latestMorph, year) || MphUtils.isAcuteTransformation(latestMorph, earlierMorph, year);
    }

    private static boolean isChronicToAcuteTransformation(String earlierMorph, String latestMorph, int year) {
        return MphUtils.isAcuteTransformation(earlierMorph, latestMorph, year) || MphUtils.isChronicTransformation(latestMorph, earlierMorph, year);
    }

}
