/*
 * Copyright (C) 2013 Information Management Services, Inc.
 */
package com.imsweb.mph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Range;

import com.imsweb.mph.internal.TempRuleResult;
import com.imsweb.mph.mpgroups.GroupUtility;

import static com.imsweb.mph.mpgroups.GroupUtility.containsElement;
import static com.imsweb.mph.mpgroups.GroupUtility.createHistologyBehaviorList;

public abstract class MphGroup {

    protected String _id;

    protected String _name;

    protected String _siteInclusions;

    protected String _siteExclusions;

    protected String _histInclusions;

    protected String _histExclusions;

    protected String _behavInclusions;

    protected String _yearInclusions;

    protected List<MphRule> _rules;

    private List<Range<Integer>> _siteIncRanges;

    private List<Range<Integer>> _siteExcRanges;

    private List<Range<Integer>> _histIncRanges;

    private List<Range<Integer>> _histExcRanges;

    private List<Range<Integer>> _behavIncRanges;

    private List<Range<Integer>> _yearIncRanges;

    public MphGroup(String id, String name, String siteInclusions, String siteExclusions, String histInclusions, String histExclusions, String behavInclusions, String yearInclusions) {
        _id = id;
        _name = name;
        _siteInclusions = siteInclusions;
        _siteExclusions = siteExclusions;
        _histInclusions = histInclusions;
        _histExclusions = histExclusions;
        _behavInclusions = behavInclusions;
        _yearInclusions = yearInclusions;
        _rules = new ArrayList<>();

        // compute the raw inclusions/exclusions into ranges
        _siteIncRanges = GroupUtility.computeRange(siteInclusions, true);
        _siteExcRanges = GroupUtility.computeRange(siteExclusions, true);
        _histIncRanges = GroupUtility.computeRange(histInclusions, false);
        _histExcRanges = GroupUtility.computeRange(histExclusions, false);
        _behavIncRanges = GroupUtility.computeRange(behavInclusions, false);
        _yearIncRanges = GroupUtility.computeRange(yearInclusions, false);
    }

    public String getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public String getSiteInclusions() {
        return _siteInclusions;
    }

    public String getSiteExclusions() {
        return _siteExclusions;
    }

    public String getHistInclusions() {
        return _histInclusions;
    }

    public String getHistExclusions() {
        return _histExclusions;
    }

    public String getBehavInclusions() {
        return _behavInclusions;
    }

    public String getYearInclusions() {
        return _yearInclusions;
    }

    public List<MphRule> getRules() {
        return _rules;
    }

    public boolean isApplicable(String primarySite, String histology, String behavior, int year) {
        if (!GroupUtility.validateProperties(primarySite, histology, behavior, year))
            return false;

        //Check behavior and diagnosis year
        if (!GroupUtility.isContained(_behavIncRanges, Integer.parseInt(behavior)) || !GroupUtility.isContained(_yearIncRanges, year))
            return false;

        boolean siteOk, histOk = false;

        Integer site = Integer.parseInt(primarySite.substring(1)), hist = Integer.parseInt(histology);

        // check site
        if (_siteIncRanges != null)
            siteOk = GroupUtility.isContained(_siteIncRanges, site);
        else
            siteOk = _siteExcRanges == null || !GroupUtility.isContained(_siteExcRanges, site);

        // check histology (only if site matched)
        if (siteOk) {
            if (_histIncRanges != null)
                histOk = GroupUtility.isContained(_histIncRanges, hist);
            else
                histOk = _histExcRanges == null || !GroupUtility.isContained(_histExcRanges, hist);
        }

        return siteOk && histOk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MphGroup mphGroup = (MphGroup)o;

        return _id.equals(mphGroup._id);

    }

    @Override
    public int hashCode() {
        return _id.hashCode();
    }

    public static class MphRuleHistologyCode extends MphRule {

        public MphRuleHistologyCode(String groupId, String step) {
            super(groupId, step);
            setQuestion("Do the tumors have ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number?");
            setReason("Tumors with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.");
        }

        @Override
        public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
            TempRuleResult result = new TempRuleResult();
            String hist1 = i1.getHistology(), hist2 = i2.getHistology();
            //If lenient mode is on 8000 is considered as same histology as 8nnn histologies
            if (MphComputeOptions.MpHistologyMatching.LENIENT.equals(options.getHistologyMatchingMode()) && (("8000".equals(hist1) && hist2.startsWith("8")) || ("8000".equals(hist2) && hist1
                    .startsWith("8"))))
                return result;
            if (!hist1.substring(0, 3).equals(hist2.substring(0, 3)))
                result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
            return result;
        }

    }

    public static class MphRulePrimarySiteCode extends MphRule {

        public MphRulePrimarySiteCode(String groupId, String step) {
            super(groupId, step);
            setQuestion("Are there tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx) and/or third character (Cx?x)?");
            setReason("Tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx) and/or third (Cx?x) character are multiple primaries.");
        }

        @Override
        public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
            TempRuleResult result = new TempRuleResult();
            if (!i1.getPrimarySite().substring(1, 3).equals(i2.getPrimarySite().substring(1, 3)))
                result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
            return result;
        }
    }


    public static class MphRuleTopography234Code extends MphRule {

        public MphRuleTopography234Code(String groupId, String step) {
            super(groupId, step);
            setQuestion("Are there tumors in sites with ICD-O-3 topography codes that differ at the second (CXxx), third (CxXx) and/or fourth characters (CxxX)?");
            setReason("Tumors in sites with ICD-O-3 topography codes that differ at the second (CXxx), third (CxXx) and/or fourth characters (CxxX) are multiple primaries.");
        }

        @Override
        public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
            TempRuleResult result = new TempRuleResult();
            if (!i1.getPrimarySite().substring(1, 4).equals(i2.getPrimarySite().substring(1, 4)))
                result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
            return result;
        }
    }

    public static class MphRuleTopography4Code extends MphRule {

        public MphRuleTopography4Code(String groupId, String step) {
            super(groupId, step);
            setQuestion("Are there tumors in sites with ICD-O site codes that differ at the fourth character (C18X)?");
            setReason("Tumors in sites with ICD-O site codes that differ at the fourth character (C18X) are multiple primaries.");
        }

        @Override
        public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
            TempRuleResult result = new TempRuleResult();
            if (!i1.getPrimarySite().substring(3, 4).equals(i2.getPrimarySite().substring(3, 4)))
                result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
            return result;
        }
    }


    public static class MphRuleBehavior extends MphRule {

        public MphRuleBehavior(String groupId, String step) {
            super(groupId, step);
            setQuestion("Is there an invasive tumor following an in situ tumor more than 60 days after diagnosis?");
            setReason("An invasive tumor following an in situ tumor more than 60 days after diagnosis are multiple primaries.");
            getNotes().add("The purpose of this rule is to ensure that the case is counted as an incident (invasive) case when incidence data are analyzed.");
            getNotes().add("Abstract as multiple primaries even if the medical record/physician states it is recurrence or progression of disease.");
        }

        @Override
        public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
            TempRuleResult result = new TempRuleResult();
            String beh1 = i1.getBehavior(), beh2 = i2.getBehavior();
            if (GroupUtility.differentCategory(beh1, beh2, Collections.singletonList(MphConstants.INSITU), Collections.singletonList(MphConstants.MALIGNANT))) {
                int latestDx = GroupUtility.compareDxDate(i1, i2);
                //If they are diagnosed at same date or invasive is not following insitu
                if (0 == latestDx || (1 == latestDx && !"3".equals(beh1)) || (2 == latestDx && !"3".equals(beh2)))
                    return result;
                else {
                    int sixtyDaysApart = GroupUtility.verifyDaysApart(i1, i2, 60);
                    if (-1 == sixtyDaysApart) {
                        result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                        result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". There is not enough diagnosis date information.");
                    }
                    else if (1 == sixtyDaysApart)
                        result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                }
            }
            return result;
        }
    }


    public static class MphRuleInvasiveAfterInSituGreaterThan60Days extends MphRule {

        boolean _mustBeSameSide;

        public MphRuleInvasiveAfterInSituGreaterThan60Days(String groupId, String step, boolean mustBeSameSide) {
            super(groupId, step);
            setQuestion("Is there an invasive tumor following an in situ tumor more than 60 days after diagnosis?");
            setReason("An invasive tumor following an in situ tumor more than 60 days after diagnosis are multiple primaries.");
            _mustBeSameSide =  mustBeSameSide;
        }

        @Override
        public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
            TempRuleResult result = new TempRuleResult();
            if ((!_mustBeSameSide) || (GroupUtility.areSameSide(i1.getLaterality(), i2.getLaterality())))
                if (GroupUtility.isOneBehaviorBeforeTheOther(i1, i2, MphConstants.INSITU, MphConstants.MALIGNANT)) {
                    int sixtyDaysApart = GroupUtility.verifyDaysApart(i1, i2, 60);
                    if (-1 == sixtyDaysApart) {
                        result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                        result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". There is not enough diagnosis date information.");
                    }
                    else if (1 == sixtyDaysApart)
                        result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                }
            return result;
        }
    }

    public static class MphRuleInvasiveAfterInSituLess60Days extends MphRule {

        boolean _mustBeSameSide;

        public MphRuleInvasiveAfterInSituLess60Days(String groupId, String step, boolean mustBeSameSide) {
            super(groupId, step);
            setQuestion("Is there an invasive tumor following an in situ tumor less than or equal to 60 days after diagnosis?");
            setReason("An invasive tumor following an in situ tumor less than or equal to 60 days after diagnosis is a single primary.");
            _mustBeSameSide =  mustBeSameSide;
        }

        @Override
        public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
            TempRuleResult result = new TempRuleResult();
            if ((!_mustBeSameSide) || (GroupUtility.areSameSide(i1.getLaterality(), i2.getLaterality())))
                if (GroupUtility.isOneBehaviorBeforeTheOther(i1, i2, MphConstants.INSITU, MphConstants.MALIGNANT)) {
                    int sixtyDaysApart = GroupUtility.verifyDaysApart(i1, i2, 60);
                    if (-1 == sixtyDaysApart) {
                        result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". There is not enough diagnosis date information.");
                    }
                    else if (0 == sixtyDaysApart)
                        result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                }
            return result;
        }
    }


    public static class MphRuleInSituAfterInvasive extends MphRule {

        boolean _mustBeSameSide;

        public MphRuleInSituAfterInvasive(String groupId, String step, boolean mustBeSameSide) {
            super(groupId, step);
            setQuestion("Is there an in situ tumor following an invasive tumor?");
            setReason("An in situ tumor diagnosed following an invasive tumor is a single primary.");
            _mustBeSameSide =  mustBeSameSide;
        }

        @Override
        public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
            TempRuleResult result = new TempRuleResult();
            if ((!_mustBeSameSide) || (GroupUtility.areSameSide(i1.getLaterality(), i2.getLaterality())))
                if (GroupUtility.isOneBehaviorBeforeTheOther(i1, i2, MphConstants.MALIGNANT, MphConstants.INSITU))
                    result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);

            return result;
        }
    }

    public static class MphRuleMalignantAfterNonMalignant extends MphRule {

        boolean _mustBeSameSide;

        public MphRuleMalignantAfterNonMalignant(String groupId, String step, boolean mustBeSameSide) {
            super(groupId, step);
            setQuestion("Is there a malignant tumor following a non-malignant tumor?");
            setReason("A malignant tumor diagnosed following an non-malignant tumor is multiple primaries.");
            _mustBeSameSide =  mustBeSameSide;
        }

        @Override
        public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
            TempRuleResult result = new TempRuleResult();
            if ((!_mustBeSameSide) || (GroupUtility.areSameSide(i1.getLaterality(), i2.getLaterality())))
                if (GroupUtility.isOneBehaviorBeforeTheOther(i1, i2, MphConstants.MALIGNANT, MphConstants.BENIGN) ||
                    GroupUtility.isOneBehaviorBeforeTheOther(i1, i2, MphConstants.MALIGNANT, MphConstants.UNCERTAIN)) {
                result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
            }
            return result;
        }
    }

    public static class MphRuleMalignantAndNonMalignant extends MphRule {

        boolean _mustBeSameSide;

        public MphRuleMalignantAndNonMalignant(String groupId, String step, boolean mustBeSameSide) {
            super(groupId, step);
            setQuestion("Is there a malignant tumor and a non-malignant tumor?");
            setReason("A malignant tumor and a non-malignant tumor is multiple primaries.");
            _mustBeSameSide =  mustBeSameSide;
        }

        @Override
        public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
            TempRuleResult result = new TempRuleResult();
            if ((!_mustBeSameSide) || (GroupUtility.areSameSide(i1.getLaterality(), i2.getLaterality())))
                if ((i1.getBehavior() == MphConstants.MALIGNANT &&  i2.getBehavior() == MphConstants.BENIGN) ||
                    (i1.getBehavior() == MphConstants.MALIGNANT &&  i2.getBehavior() == MphConstants.UNCERTAIN) ||
                    (i1.getBehavior() == MphConstants.BENIGN &&  i2.getBehavior() == MphConstants.MALIGNANT) ||
                    (i1.getBehavior() == MphConstants.UNCERTAIN &&  i2.getBehavior() == MphConstants.MALIGNANT)) {
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                }
            return result;
        }
    }


    public static class MphRuleDiagnosisDate extends MphRule {

        public MphRuleDiagnosisDate(String groupId, String step) {
            super(groupId, step);
            setQuestion("Are there tumors diagnosed more than five (5) years apart?");
            setReason("Tumors diagnosed more than five (5) years apart are multiple primaries.");
        }

        @Override
        public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
            TempRuleResult result = new TempRuleResult();
            int diff = GroupUtility.verifyYearsApart(i1, i2, 5);
            if (-1 == diff) {
                result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". There is not enough diagnosis date information.");
            }
            else if (1 == diff)
                result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);

            return result;
        }
    }

    public static class MphRuleDiagnosisDateLess3Years extends MphRule {

        public MphRuleDiagnosisDateLess3Years(String groupId, String step) {
            super(groupId, step);
            setQuestion("Are there tumors diagnosed less than or equal to three (3) years apart?");
            setReason("Tumors diagnosed less than or equal to three (3) years apart is a single primary.");
        }

        @Override
        public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
            TempRuleResult result = new TempRuleResult();

            int diff = GroupUtility.verifyYearsApart(i1, i2, 3);
            if (-1 == diff) {
                result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". There is not enough diagnosis date information.");
            }
            else if (0 == diff)
                result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);

            return result;
        }
    }

    public static class MphRuleDiagnosisDateGreaterThan3Years extends MphRule {

        public MphRuleDiagnosisDateGreaterThan3Years(String groupId, String step) {
            super(groupId, step);
            setQuestion("Are there tumors diagnosed greater than three (3) years apart?");
            setReason("Tumors diagnosed greater than three (3) years apart are multiple primaries.");
        }

        @Override
        public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
            TempRuleResult result = new TempRuleResult();

            int diff = GroupUtility.verifyYearsApart(i1, i2, 3);
            if (-1 == diff) {
                result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". There is not enough diagnosis date information.");
            }
            else if (1 == diff)
                result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);

            return result;
        }
    }

    public static class MphRuleSimultaneousTumors extends MphRule {

        public MphRuleSimultaneousTumors(String groupId, String step) {
            super(groupId, step);
            setQuestion("Are there two or more tumors diagnosed less than or equal to 60 days of each other?");
            setReason("Two or more tumors diagnosed less than or equal to 60 days of each other is a single primary.");
        }

        @Override
        public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
            TempRuleResult result = new TempRuleResult();
            int sixtyDaysApart = GroupUtility.verifyDaysApart(i1, i2, 60);
            if (-1 == sixtyDaysApart) {
                result.setPotentialResult(MphUtils.MpResult.SINGLE_PRIMARY);
                result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". There is not enough diagnosis date information.");
            }
            else if (0 == sixtyDaysApart) {
                result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
            }
            return result;
        }
    }


    public static class MphRuleNoCriteriaSatisfied extends MphRule {

        public MphRuleNoCriteriaSatisfied(String groupId, String step) {
            super(groupId, step);
            setQuestion("Does not meet any of the criteria?");
            setReason("Tumors that do not meet any of the criteria are abstracted as a single primary.");
        }

        @Override
        public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
            TempRuleResult result = new TempRuleResult();
            result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
            return result;
        }
    }

    public static class MphRuleSameRowInTable extends MphRule {

        private Map<String, List<String>> _tableToTest;
        private Map<String, List<String>> _subtypeNOS;
        boolean _mustBeSameBehavior;
        boolean _mustBeSameSide;

        public MphRuleSameRowInTable(String groupId, String step, Map<String, List<String>> tableToTest, Map<String, List<String>> subtypeNOS,
                                     boolean mustBeSameBehavior, boolean mustBeSameSide) {
            super(groupId, step);
            _tableToTest = tableToTest;
            _subtypeNOS = subtypeNOS;
            _mustBeSameBehavior = mustBeSameBehavior;
            _mustBeSameSide = mustBeSameSide;
        }

        @Override
        public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
            TempRuleResult result = new TempRuleResult();
            if (((!_mustBeSameBehavior) || (i1.getBehavior().equals(i2.getBehavior())))  &&
                ((!_mustBeSameSide) || (GroupUtility.areSameSide(i1.getLaterality(), i2.getLaterality())))) {

                String icd1 = i1.getHistology() + "/" + i1.getBehavior(), icd2 = i2.getHistology() + "/" + i2.getBehavior();

                // Same histology for both tumors,OR
                // In the same row, one tumor is in Column 1, and one tumor is in Column 3.

                // 1. Histology A = Histology B (in either column 1 or column 3)
                // 2. Histology A in Column 1; Histology B in column 3
                // 3. It is not possible for Histology A != Histology B and both be in column 3. In other words, two subtypes in column 3 cannot satisfy this rule.

                Map<String, List<String>> loopList = null;
                boolean valueSet = false;
                for (int loop = 0; (loop < 2) && (!valueSet); loop++) {
                    if (loop == 0) loopList = _subtypeNOS;
                    else if (loop == 1) loopList = _tableToTest;

                    if (loopList != null) {
                        List<String> subTypes1 = loopList.get(icd1);
                        if (subTypes1 == null) subTypes1 = loopList.get(i1.getHistology());
                        List<String> subTypes2 = loopList.get(icd2);
                        if (subTypes2 == null) subTypes2 = loopList.get(i2.getHistology());

                        // Same histology and both in the table.
                        if (i1.getHistology().equals(i2.getHistology())) {
                            if (subTypes1 != null && subTypes2 != null) {
                                // Both in Column 1
                                result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                            } else {
                                // Both in Column 3
                                for (Map.Entry<String, List<String>> entry : loopList.entrySet()) {
                                    if ((entry.getValue().contains(icd1) || entry.getValue().contains(i1.getHistology())) &&
                                            (entry.getValue().contains(icd2) || entry.getValue().contains(i2.getHistology()))) {
                                        result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                                        break;
                                    }
                                }
                            }
                            // One in Column 1, and one in Column 3.
                        } else if (subTypes1 != null && (subTypes1.contains(icd2) || subTypes1.contains(i2.getHistology()))) {
                            result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                            // One in Column 1, and one in Column 3.
                        } else if (subTypes2 != null && (subTypes2.contains(icd1) || subTypes2.contains(i1.getHistology()))) {
                            result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        }

                    }
                }
            }
            return result;
        }
    }


    public static class MphRuleDifferentRowsInTable extends MphRule {

        private Map<String, List<String>> _tableToTest;
        boolean _mustBeSameSide;

        public MphRuleDifferentRowsInTable(String groupId, String step, Map<String, List<String>> tableToTest, boolean mustBeSameSide) {
            super(groupId, step);
            _tableToTest = tableToTest;
            _mustBeSameSide = mustBeSameSide;
        }

        @Override
        public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
            TempRuleResult result = new TempRuleResult();
            if ((!_mustBeSameSide) || (GroupUtility.areSameSide(i1.getLaterality(), i2.getLaterality()))) {

                // Different rows in any column. So a histology in column 1 and a histology on a different row in column 3 would be a situation where this rule would apply.
                // A histology not on the table counts as a different row. Both not in the table raises a Questionable.

                String icd1 = i1.getHistology() + "/" + i1.getBehavior(), icd2 = i2.getHistology() + "/" + i2.getBehavior();
                int foundRow1 = -1; int foundRow2 = -1; int rowIndex = 0;
                for (Map.Entry<String, List<String>> entry : _tableToTest.entrySet()) {
                    if (entry.getKey().equals(icd1) || entry.getValue().contains(icd1) || entry.getKey().equals(i1.getHistology()) || entry.getValue().contains(i1.getHistology())) {
                        foundRow1 = rowIndex;
                    }
                    if (entry.getKey().equals(icd2) || entry.getValue().contains(icd2) || entry.getKey().equals(i2.getHistology()) || entry.getValue().contains(i2.getHistology())) {
                        foundRow2 = rowIndex;
                    }
                    if (foundRow1 >= 0 && foundRow2 >= 0) break;
                    rowIndex++;
                }

                // Both histologies not in the table is a manual review.
                if (foundRow1 == -1 && foundRow2 == -1) {
                    result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                    result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". Could not find both histologies in the table.");
                }
                // One histology not in the table counts as a different row.
                if ((foundRow1 >= 0 && foundRow2 == -1) || (foundRow1 == -1 && foundRow2 >= 0)) {
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                }
                // Different rows.
                else if (foundRow1 != foundRow2) {
                    result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                }
            }
            return result;
        }
    }

    public static class MphRuleTwoOrMoreDifferentSubTypesInTable extends MphRule {

        private List<String> _listToTest;
        private Map<String, List<String>> _subtypeNOS;
        boolean _mustBeSameSide;

        public MphRuleTwoOrMoreDifferentSubTypesInTable(String groupId, String step, List<String> listToTest, Map<String, List<String>> subtypeNOS, boolean mustBeSameSide) {
            super(groupId, step);
            _listToTest = listToTest;
            _mustBeSameSide = mustBeSameSide;
            _subtypeNOS = subtypeNOS;
        }

        @Override
        public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
            TempRuleResult result = new TempRuleResult();
            if ((!_mustBeSameSide) || (GroupUtility.areSameSide(i1.getLaterality(), i2.getLaterality()))) {
                String icd1 = i1.getHistology() + "/" + i1.getBehavior(), icd2 = i2.getHistology() + "/" + i2.getBehavior();

                if (!i1.getHistology().equals(i2.getHistology())) {

                    boolean bothAreSubTypeNOS = false;
                    if (_subtypeNOS != null) {
                        List<String> subtypeNOSList1 = _subtypeNOS.get(icd1);
                        if (subtypeNOSList1 == null) subtypeNOSList1 = _subtypeNOS.get(i1.getHistology());
                        List<String> subtypeNOSList2 = _subtypeNOS.get(icd2);
                        if (subtypeNOSList2 == null) subtypeNOSList2 = _subtypeNOS.get(i2.getHistology());

                        if (subtypeNOSList1 != null) {
                            bothAreSubTypeNOS = subtypeNOSList1.contains(icd2);
                            if (!bothAreSubTypeNOS) bothAreSubTypeNOS = subtypeNOSList1.contains(i2.getHistology());
                        } else if (subtypeNOSList2 != null) {
                            bothAreSubTypeNOS = subtypeNOSList2.contains(icd1);
                            if (!bothAreSubTypeNOS) bothAreSubTypeNOS = subtypeNOSList2.contains(i1.getHistology());
                        }
                    }

                    if (!bothAreSubTypeNOS) {
                        boolean isPresent1 = _listToTest.contains(icd1);
                        if (!isPresent1) isPresent1 = _listToTest.contains(i1.getHistology());
                        boolean isPresent2 = _listToTest.contains(icd2);
                        if (!isPresent2) isPresent2 = _listToTest.contains(i2.getHistology());

                        if (isPresent1 && isPresent2) {
                            result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                        }
                    }
                }
            }
            return result;
        }
    }

    public static class MphRuleMainTypeAndSubTypeInTable extends MphRule {

        private Map<String, List<String>> _tableToTest;

        public MphRuleMainTypeAndSubTypeInTable(String groupId, String step, Map<String, List<String>> tableToTest) {
            super(groupId, step);
            _tableToTest = tableToTest;
        }

        @Override
        public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
            TempRuleResult result = new TempRuleResult();

            String icd1 = i1.getHistology() + "/" + i1.getBehavior(), icd2 = i2.getHistology() + "/" + i2.getBehavior();
            List<String> subTypes1 = _tableToTest.get(icd1);
            if (subTypes1 == null) subTypes1 = _tableToTest.get(i1.getHistology());
            List<String> subTypes2 = _tableToTest.get(icd2);
            if (subTypes2 == null) subTypes2 = _tableToTest.get(i2.getHistology());

            if ((subTypes1 != null && subTypes2 == null && (subTypes1.contains(icd2) || subTypes1.contains(i2.getHistology()))) ||
                (subTypes1 == null && subTypes2 != null && (subTypes2.contains(icd1) || subTypes2.contains(i1.getHistology())))) {
                result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
            }
            return result;
        }
    }

    public static class MphRuleLeftAndRight extends MphRule {

        List<String> _pairedSites;
        String _requiredSite;

        public MphRuleLeftAndRight(String groupId, String step, List<String> pairedSites, String requiredSite) {
            super(groupId, step);
            _pairedSites = pairedSites;
            _requiredSite = requiredSite;
        }

        @Override
        public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
            TempRuleResult result = new TempRuleResult();
            if ((_requiredSite == null) || (_requiredSite.equals(i1.getPrimarySite()) && _requiredSite.equals(i2.getPrimarySite()))) {
                if ((_pairedSites == null) || (GroupUtility.isPairedSites(i1.getPrimarySite(), i2.getPrimarySite(), _pairedSites))) {
                    if (!GroupUtility.validPairedSiteLaterality(i1.getLaterality(), i2.getLaterality())) {
                        result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                        result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". Valid and known laterality should be provided.");
                    }
                    else if (GroupUtility.areOppositeSides(i1.getLaterality(), i2.getLaterality()))
                        result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                }
            }

            return result;
        }
    };




}
