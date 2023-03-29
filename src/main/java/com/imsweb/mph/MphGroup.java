/*
 * Copyright (C) 2013 Information Management Services, Inc.
 */
package com.imsweb.mph;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Range;

import com.imsweb.mph.mpgroups.GroupUtility;

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
        if (_siteIncRanges != null && !_siteInclusions.isEmpty())
            siteOk = GroupUtility.isContained(_siteIncRanges, site);
        else
            siteOk = !GroupUtility.isContained(_siteExcRanges, site);

        // check histology (only if site matched)
        if (siteOk) {
            if (_histIncRanges != null && !_histIncRanges.isEmpty())
                histOk = GroupUtility.isContained(_histIncRanges, hist);
            else
                histOk = !GroupUtility.isContained(_histExcRanges, hist);
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

}
