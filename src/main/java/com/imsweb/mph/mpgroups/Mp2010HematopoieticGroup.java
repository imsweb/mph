/*
 * Copyright (C) 2016 Information Management Services, Inc.
 */
package com.imsweb.mph.mpgroups;

import java.util.Arrays;
import java.util.List;

import com.imsweb.mph.MphGroup;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphRuleResult;
import com.imsweb.mph.MphUtils;

public class Mp2010HematopoieticGroup extends MphGroup {

    public Mp2010HematopoieticGroup() {
        super("hematopoietic-2010", "Hematopoietic 2010", "C000-C809", null, "9590-9989", null, "2-3,6", "2010-9999");

        // M3
        MphRule rule = new MphRule("hematopoietic-2010", "M3", MphUtils.MPResult.SINGLE_PRIMARY) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                int laterDx = MphGroup.compareDxDate(i1, i2);
                if (laterDx == -1) {
                    result.setResult(MphUtils.RuleResult.UNKNOWN);
                    result.setMessage("Unable to apply Rule" + this.getStep() + " of " + this.getGroupId() + ". Known diagnosis date should be provided.");
                }
                else if (!"3".equals(i1.getHistologyIcdO3()) || !"3".equals(i2.getHistologyIcdO3()))
                    result.setResult(MphUtils.RuleResult.FALSE);
                else if ("9740".equals(i1.getHistologyIcdO3()) && "9742".equals(i2.getHistologyIcdO3()) && (laterDx == 0 || laterDx == 1))
                    result.setResult(MphUtils.RuleResult.TRUE);
                else if ("9740".equals(i2.getHistologyIcdO3()) && "9742".equals(i1.getHistologyIcdO3()) && (laterDx == 0 || laterDx == 2))
                    result.setResult(MphUtils.RuleResult.TRUE);
                else if ("9930".equals(i1.getHistologyIcdO3()) && "9742".equals(i2.getHistologyIcdO3()) && (laterDx == 0 || laterDx == 1))
                    result.setResult(MphUtils.RuleResult.TRUE);
                else if ("9740".equals(i2.getHistologyIcdO3()) && "9742".equals(i1.getHistologyIcdO3()) && (laterDx == 0 || laterDx == 2))
                    result.setResult(MphUtils.RuleResult.TRUE);

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

        // M4 - When no other urinary sites are involved, tumor(s) in both the right ureter AND tumor(s) in the left ureter are multiple primaries. (C669)
        rule = new MphRule("urinary-2007", "M4", MphUtils.MPResult.MULTIPLE_PRIMARIES) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                if (!"C669".equalsIgnoreCase(i1.getPrimarySite()) || !"C669".equalsIgnoreCase(i2.getPrimarySite())) {
                    result.setResult(MphUtils.RuleResult.FALSE);
                }
                else if (!Arrays.asList("1", "2").containsAll(Arrays.asList(i1.getLaterality(), i2.getLaterality()))) {
                    result.setResult(MphUtils.RuleResult.UNKNOWN);
                    result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". Valid and known laterality for ureter tumors should be provided.");
                }
                else
                    result.setResult(!i1.getLaterality().equals(i2.getLaterality()) ? MphUtils.RuleResult.TRUE : MphUtils.RuleResult.FALSE);

                return result;
            }
        };
        rule.setQuestion("Are there tumors in both the right ureter and the left ureter and no other urinary sites are involved?");
        rule.setReason("When no other urinary sites are involved, tumor(s) in both the right ureter AND tumor(s) in the left ureter are multiple primaries.");
        rule.getNotes().add("Use this rule and abstract as a multiple primary unless documented to be metastatic.");
        _rules.add(rule);

        // M5- An invasive tumor following an in situ tumor more than 60 days after diagnosis are multiple primaries.
        rule = new MphRuleBehavior("urinary-2007", "M5");
        _rules.add(rule);

        // M6 - Bladder tumors with any combination of the following histologies: papillary carcinoma (8050), transitional cell carcinoma (8120-8124),
        // or papillary transitional cell carcinoma (8130-8131), are a single primary.
        rule = new MphRule("urinary-2007", "M6", MphUtils.MPResult.SINGLE_PRIMARY) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                List<String> carcinomaHist = Arrays.asList("8050", "8120", "8121", "8122", "8123", "8124", "8130", "8131");
                if (!i1.getPrimarySite().toLowerCase().startsWith("c67") || !i2.getPrimarySite().toLowerCase().startsWith("c67")) {
                    result.setResult(MphUtils.RuleResult.FALSE);
                }
                else
                    result.setResult(carcinomaHist.containsAll(Arrays.asList(i1.getHistologyIcdO3(), i2.getHistologyIcdO3())) ? MphUtils.RuleResult.TRUE : MphUtils.RuleResult.FALSE);

                return result;
            }
        };
        rule.setQuestion("Are there bladder tumors with any combination of the following histologies:\n" +
                "- papillary carcinoma (8050)\n" +
                "- transitional cell carcinoma (8120-8124)\n" +
                "- papillary tansitional cell carcinoma (8130-8131)?");
        rule.setReason(
                "Bladder tumors with any combination of the following histologies: papillary carcinoma (8050), transitional cell carcinoma (8120-8124), or papillary transitional cell carcinoma (8130-8131), are a single primary.");
        _rules.add(rule);

        // M7 - Tumors diagnosed more than three (3) years apart are multiple primaries.
        rule = new MphRule("urinary-2007", "M7", MphUtils.MPResult.MULTIPLE_PRIMARIES) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                int diff = verifyYearsApart(i1, i2, 3);
                if (-1 == diff) {
                    result.setResult(MphUtils.RuleResult.UNKNOWN);
                    result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". There is no enough diagnosis date information.");
                }
                else
                    result.setResult(1 == diff ? MphUtils.RuleResult.TRUE : MphUtils.RuleResult.FALSE);

                return result;
            }
        };
        rule.setQuestion("Are there tumors diagnosed more than three (3) years apart?");
        rule.setReason("Tumors diagnosed more than three (3) years apart are multiple primaries.");
        _rules.add(rule);

        // M8 - Urothelial tumors in two or more of the following sites are a single primary* (See Table 1 of pdf)
        // Renal pelvis (C659), Ureter(C669), Bladder (C670-C679), Urethra /prostatic urethra (C680)
        rule = new MphRule("urinary-2007", "M8", MphUtils.MPResult.SINGLE_PRIMARY) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                MphRuleResult result = new MphRuleResult();
                List<String> urothelialTumors = Arrays.asList("8120", "8130", "8131", "8082", "8122", "8031", "8020");
                //The only sites not included are those from C681-C689
                if (i1.getPrimarySite().toLowerCase().startsWith("c68") && !"c680".equals(i1.getPrimarySite().toLowerCase()) || i2.getPrimarySite().toLowerCase().startsWith("c68") && !i2
                        .getPrimarySite().toLowerCase().equals("c680"))
                    result.setResult(MphUtils.RuleResult.FALSE);
                else
                    result.setResult(urothelialTumors.containsAll(Arrays.asList(i1.getHistologyIcdO3(), i2.getHistologyIcdO3())) ? MphUtils.RuleResult.TRUE : MphUtils.RuleResult.FALSE);
                return result;
            }
        };
        rule.setQuestion("Are there urothelial tumors in two or more of the following sites:\n" +
                "- Renal pelvis (C659)\n" +
                "- Ureter(C669)\n" +
                "- Bladder (C670-C679)\n" +
                "- Urethra /prostatic urethra (C680)");
        rule.setReason("Urothelial tumors in two or more of the following sites are a single primary.\n" +
                "- Renal pelvis (C659)\n" +
                "- Ureter(C669)\n" +
                "- Bladder (C670-C679)\n" +
                "- Urethra /prostatic urethra (C680)");
        _rules.add(rule);

        // M9- Tumors with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.
        rule = new MphRuleHistologyCode("urinary-2007", "M9");
        _rules.add(rule);

        // M10- Tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx) and/or third (Cx?x) character are multiple primaries.
        rule = new MphRulePrimarySiteCode("urinary-2007", "M10");
        _rules.add(rule);

        // M11- Tumors that do not meet any of the criteria are abstracted as a single primary.
        rule = new MphRuleNoCriteriaSatisfied("urinary-2007", "M11");
        rule.getNotes().add("When an invasive tumor follows an in situ tumor within 60 days, abstract as a single primary.");
        _rules.add(rule);
    }

}
