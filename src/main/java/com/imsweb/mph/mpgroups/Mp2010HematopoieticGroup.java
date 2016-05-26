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
    }

}
