/*
 * Copyright (C) 2016 Information Management Services, Inc.
 */
package com.imsweb.mph.mpgroups;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.imsweb.mph.MphConstants;
import com.imsweb.mph.MphInput;

public class GroupUtility {

    private GroupUtility() {
    }

    /**
     * Validates the provided input's primary site, histology, behavior and diagnosis year. These properties are required to determine the cancer group and used at least in one of the rules in each group.
     */
    public static boolean validateProperties(String primarySite, String histology, String behavior, int year) {
        return validateSite(primarySite) && validateHistology(histology) && validateBehavior(behavior) && validateYear(year);
    }

    /**
     * Validates primary site
     */
    public static boolean validateSite(String site) {
        return site != null && site.length() == 4 && site.startsWith("C") && NumberUtils.isDigits(site.substring(1)) && !"C809".equalsIgnoreCase(site);
    }

    /**
     * Validates histology
     */
    public static boolean validateHistology(String hist) {
        return NumberUtils.isDigits(hist) && Integer.parseInt(hist) >= 8000 && Integer.parseInt(hist) <= 9999;
    }

    /**
     * Validates behavior
     */
    public static boolean validateBehavior(String behavior) {
        return Arrays.asList("0", "1", "2", "3", "6").contains(behavior);
    }

    /**
     * Validates diagnosis year
     */
    public static boolean validateYear(int year) {
        return year >= 0 && year <= LocalDate.now().getYear();
    }

    /**
     * check if diagnosis dates are valid and same
     */
    public static boolean sameValidDates(Integer year1, Integer month1, Integer day1, Integer year2, Integer month2, Integer day2) {
        if (year1 == null || year2 == null || month1 == null || month2 == null || day1 == null || day2 == null)
            return false;
        try {
            return LocalDate.of(year1, month1, day1).isEqual(LocalDate.of(year2, month2, day2));
        }
        catch (Exception e) {
            return false;
        }
    }

    /**
     * checks if two morphologies are same except 8000 and 8010.
     */
    public static boolean sameHistologies(String icd1, String icd2) {
        return icd1 != null && icd1.equals(icd2) && !icd1.startsWith("8000") && !icd1.startsWith("8010");

    }

    /**
     * check if diagnosis dates are valid and same
     */
    public static boolean sameKnownDateParts(MphInput i1, MphInput i2) {
        DateFieldParts date = new DateFieldParts(i1, i2);
        return date.getYear1() != null && date.getYear1().equals(date.getYear2()) &&
                (date.getMonth1() == null || date.getMonth2() == null || (date.getMonth1().equals(date.getMonth2()) &&
                (date.getDay1() == null || date.getDay2() == null || date.getDay1().equals(date.getDay2()))));
    }

    /**
     * Checks if integer value is in a list of ranges
     */
    public static boolean isContained(List<Range<Integer>> list, Integer value) {
        if (list != null && !list.isEmpty())
            for (Range<Integer> range : list)
                if (range.contains(value))
                    return true;
        return false;
    }

    /**
     * computes list of range values from string
     */
    public static List<Range<Integer>> computeRange(String rawValue, boolean isSite) {
        if (rawValue == null)
            return Collections.emptyList();

        List<Range<Integer>> result = new ArrayList<>();

        for (String item : StringUtils.split(rawValue, ',')) {
            String[] parts = StringUtils.split(item.trim(), '-');
            if (parts.length == 1) {
                if (isSite)
                    result.add(Range.is(Integer.parseInt(parts[0].trim().substring(1))));
                else
                    result.add(Range.is(Integer.parseInt(parts[0].trim())));
            }
            else {
                if (isSite)
                    result.add(Range.between(Integer.parseInt(parts[0].trim().substring(1)), Integer.parseInt(parts[1].trim().substring(1))));
                else
                    result.add(Range.between(Integer.parseInt(parts[0].trim()), Integer.parseInt(parts[1].trim())));
            }
        }

        return result;
    }

    /**
     * Expands list of string splitted by commas and ranges.
     */
    public static List<String> expandList(List<String> list) {
        List<String> result = new ArrayList<>();
        if (list == null || list.isEmpty())
            return list;
        for (String item : list) {
            String[] ranges = StringUtils.split(item.trim(), ',');
            for (String range : ranges) {
                String[] parts = StringUtils.split(range.trim(), '-');
                if (parts.length <= 1)
                    result.add(range);
                else {
                    int start = Integer.parseInt(parts[0]);
                    int end = Integer.parseInt(parts[1]);
                    while (start <= end) {
                        result.add(String.valueOf(start++));
                    }
                }
            }
        }
        return result;
    }

    /**
     * Expands list of string splitted by commas and ranges.
     */
    public static List<String> expandList(String rawValue) {
        return expandList(Collections.singletonList(rawValue));
    }

    /**
     * Checks if primary site is in range
     */
    public static boolean isSiteContained(String list, String site) {
        return isContained(computeRange(list, true), Integer.valueOf(site.substring(1)));
    }

    /**
     * checks if one property belongs to some category and the other to different category
     */
    public static boolean differentCategory(String prop1, String prop2, List<String> cat1, List<String> cat2) {
        return !(cat1 == null || cat2 == null || cat1.isEmpty() || cat2.isEmpty()) && ((cat1.contains(prop1) && cat2.contains(prop2)) || (cat1.contains(prop2) && cat2.contains(prop1)));
    }

    /**
     * checks if 2 sites are paired
     */
    public static boolean isPairedSites(String site1, String site2, List<String> pairedSites) {
        if (pairedSites != null && !pairedSites.isEmpty())
            for (String pairedSite : pairedSites)
                if (isContained(computeRange(pairedSite, true), Integer.parseInt(site1.substring(1))) && isContained(computeRange(pairedSite, true), Integer.parseInt(site2.substring(1))))
                    return true;
        return false;
    }

    /**
     * checks if lateralities are valid for paired sites, if they are either left or right
     */
    public static boolean validPairedSiteLaterality(String lat1, String lat2) {
        return (MphConstants.RIGHT.equals(lat1) || MphConstants.LEFT.equals(lat1)) && (MphConstants.RIGHT.equals(lat2) || MphConstants.LEFT.equals(lat2));
    }

    /**
     * checks if laterality is valid
     */
    public static boolean validateLaterality(String lat) {
        return Arrays.asList(MphConstants.NOT_PAIRED, MphConstants.RIGHT, MphConstants.LEFT, MphConstants.ONLY_ONE_SIDE_NS, MphConstants.BOTH, MphConstants.MID_LINE,
                MphConstants.PAIRED_NO_INFORMATION).contains(lat);
    }

    /**
     * checks if cancers are left and right side
     */
    public static boolean areOppositeSides(String lat1, String lat2) {
        return (MphConstants.RIGHT.equals(lat1) && MphConstants.LEFT.equals(lat2)) || (MphConstants.RIGHT.equals(lat2) && MphConstants.LEFT.equals(lat1));
    }

    /**
     * checks if cancers are both on the left or right side
     */
    public static boolean areSameSide(String lat1, String lat2) {
        return (MphConstants.RIGHT.equals(lat1) && MphConstants.RIGHT.equals(lat2)) || (MphConstants.LEFT.equals(lat2) && MphConstants.LEFT.equals(lat1));
    }

    /**
     * checks if two cases have same and valid site, hist, behavior, date, laterality
     */
    public static boolean sameAndValidMainFields(MphInput i1, MphInput i2) {
        DateFieldParts date = new DateFieldParts(i1, i2);

        String site1 = i1.getPrimarySite();
        String site2 = i2.getPrimarySite();
        String hist1 = i1.getHistology();
        String hist2 = i2.getHistology();
        String beh1 = i1.getBehavior();
        String beh2 = i2.getBehavior();
        String lat1 = i1.getLaterality();
        String lat2 = i2.getLaterality();
        return date.getYear1() != null && date.getYear2() != null &&
                validateProperties(site1, hist1, beh1, date.getYear1()) &&
                validateProperties(site2, hist2, beh2, date.getYear2()) &&
                validateLaterality(lat1) && validateLaterality(lat2) &&
                Objects.equals(site1, site2) &&
                Objects.equals(hist1, hist2) &&
                Objects.equals(beh1, beh2) &&
                sameValidDates(date.getYear1(), date.getMonth1(), date.getDay1(), date.getYear2(), date.getMonth2(), date.getDay2()) &&
                Objects.equals(lat1, lat2);
    }

    /**
     * checks which tumor is diagnosed later. It returns 1 (if tumor 1 is diagnosed after tumor 2), 2 (if tumor 2 is diagnosed after tumor 1),
     * 0 (if the diagnosis takes at the same day) or -1 (if there is insufficient information e.g if both year is 2007, but month and day is unknown)
     */
    public static int compareDxDate(MphInput input1, MphInput input2) {
        DateFieldParts date = new DateFieldParts(input1, input2);
        //If year is missing or in the future, return unknown
        int currYear = LocalDate.now().getYear();
        if (date.getYear1() == null || date.getYear2() == null || date.getYear1() > currYear || date.getYear2() > currYear)
            return MphConstants.COMPARE_DX_UNKNOWN;
        else if (date.getYear1() > date.getYear2())
            return MphConstants.COMPARE_DX_FIRST_LATEST;
        else if (date.getYear2() > date.getYear1())
            return MphConstants.COMPARE_DX_SECOND_LATEST;

        // if month is missing, return unknown
        if (date.getMonth1() == null || date.getMonth2() == null)
            return MphConstants.COMPARE_DX_UNKNOWN;
        else if (date.getMonth1() > date.getMonth2())
            return MphConstants.COMPARE_DX_FIRST_LATEST;
        else if (date.getMonth2() > date.getMonth1())
            return MphConstants.COMPARE_DX_SECOND_LATEST;

        // if day is missing, return unknown
        if (date.getDay1() == null || date.getDay2() == null)
            return MphConstants.COMPARE_DX_UNKNOWN;
        else if (date.getDay1() > date.getDay2())
            return MphConstants.COMPARE_DX_FIRST_LATEST;
        else if (date.getDay2() > date.getDay1())
            return MphConstants.COMPARE_DX_SECOND_LATEST;

        return MphConstants.COMPARE_DX_EQUAL;
    }

    /**
     * checks if the two tumors are diagnosed "x" years apart. It returns Yes (1), No (0) or Unknown (-1) (If there is no enough information)
     */
    //S3776 - Cognitive Complexity of methods should not be too high => Handling unknown dates made this a little bit complicated
    @SuppressWarnings("java:S3776")
    public static int verifyYearsApart(MphInput input1, MphInput input2, int yearsApart) {

        DateFieldParts date = new DateFieldParts(input1, input2);
        //If year is missing or in the future, return unknown
        int currYear = LocalDate.now().getYear();
        if (date.getYear1() == null || date.getYear2() == null || date.getYear1() > currYear || date.getYear2() > currYear)
            return MphConstants.DATE_VERIFY_UNKNOWN;
        else if (Math.abs(date.getYear1() - date.getYear2()) > yearsApart)
            return MphConstants.DATE_VERIFY_APART;
        else if (Math.abs(date.getYear1() - date.getYear2()) < yearsApart)
            return MphConstants.DATE_VERIFY_WITHIN;
        else if (date.getMonth1() == null || date.getMonth2() == null)
            return MphConstants.DATE_VERIFY_UNKNOWN;
        else if ((date.getYear1() > date.getYear2() && date.getMonth1() > date.getMonth2()) || (date.getYear2() > date.getYear1() && date.getMonth2() > date.getMonth1()))
            return MphConstants.DATE_VERIFY_APART;
        else if ((date.getYear1() > date.getYear2() && date.getMonth1() < date.getMonth2()) || (date.getYear2() > date.getYear1() && date.getMonth2() < date.getMonth1()))
            return MphConstants.DATE_VERIFY_WITHIN;
        else if (date.getDay1() == null || date.getDay2() == null)
            return MphConstants.DATE_VERIFY_UNKNOWN;
        return Math.abs(ChronoUnit.YEARS.between(LocalDate.of(date.getYear1(), date.getMonth1(), date.getDay1()), LocalDate.of(date.getYear2(), date.getMonth2(), date.getDay2())))
                >= yearsApart ? MphConstants.DATE_VERIFY_APART : MphConstants.DATE_VERIFY_WITHIN;
    }

    /**
     * checks if the two tumors are diagnosed "x" days apart. It returns Yes (1), No (0) or Unknown (-1) (If there is no enough information)
     */
    public static int verifyDaysApart(MphInput input1, MphInput input2, int days) {

        int latestDx = compareDxDate(input1, input2);
        if (latestDx == MphConstants.COMPARE_DX_EQUAL)
            return MphConstants.DATE_VERIFY_WITHIN;

        DateFieldParts date = new DateFieldParts(input1, input2);
        //If year is missing or in the future, return unknown
        int currYear = LocalDate.now().getYear();
        if (date.getYear1() == null || date.getYear2() == null || date.getYear1() > currYear || date.getYear2() > currYear)
            return MphConstants.DATE_VERIFY_UNKNOWN;

        int minDaysInBetween = daysInBetween(date.getYear2(), date.getMonth2(), date.getDay2(), date.getYear1(), date.getMonth1(), date.getDay1(), true);
        int maxDaysInBetween = daysInBetween(date.getYear2(), date.getMonth2(), date.getDay2(), date.getYear1(), date.getMonth1(), date.getDay1(), false);
        if (MphConstants.COMPARE_DX_UNKNOWN == latestDx)
            return Math.max(Math.abs(minDaysInBetween), Math.abs(maxDaysInBetween)) <= days ? MphConstants.DATE_VERIFY_WITHIN : MphConstants.DATE_VERIFY_UNKNOWN;
        else if (MphConstants.COMPARE_DX_SECOND_LATEST == latestDx) {
            minDaysInBetween = daysInBetween(date.getYear1(), date.getMonth1(), date.getDay1(), date.getYear2(), date.getMonth2(), date.getDay2(), true);
            maxDaysInBetween = daysInBetween(date.getYear1(), date.getMonth1(), date.getDay1(), date.getYear2(), date.getMonth2(), date.getDay2(), false);
        }

        if (minDaysInBetween > days)
            return MphConstants.DATE_VERIFY_APART;
        else if (maxDaysInBetween <= days)
            return MphConstants.DATE_VERIFY_WITHIN;
        else
            return MphConstants.DATE_VERIFY_UNKNOWN;
    }

    //This method is called if one diagnosis is after the other, It returns the minimum or maximum days between two diagnosis dates based on boolean minimum
    private static int daysInBetween(Integer startYr, Integer startMon, Integer startDay, Integer endYr, Integer endMon, Integer endDay, boolean minimum) {
        LocalDate startDateMin = LocalDate.of(startYr, 1, 1);
        LocalDate startDateMax = LocalDate.of(startYr, 12, 31);
        LocalDate endDateMin = LocalDate.of(endYr, 1, 1);
        LocalDate endDateMax = LocalDate.of(endYr, 12, 31);
        if (startDay != null) {
            startDateMin = LocalDate.of(startYr, startMon, startDay);
            startDateMax = LocalDate.of(startYr, startMon, startDay);
        }
        else if (startMon != null) {
            startDateMin = LocalDate.of(startYr, startMon, 1);
            startDateMax = LocalDate.of(startYr, startMon, YearMonth.of(startYr, startMon).lengthOfMonth());
        }
        if (endDay != null) {
            endDateMin = LocalDate.of(endYr, endMon, endDay);
            endDateMax = LocalDate.of(endYr, endMon, endDay);
        }
        else if (endMon != null) {
            endDateMin = LocalDate.of(endYr, endMon, 1);
            endDateMax = LocalDate.of(endYr, endMon, YearMonth.of(endYr, endMon).lengthOfMonth());
        }

        return minimum ? (int)ChronoUnit.DAYS.between(startDateMax, endDateMin) : (int)ChronoUnit.DAYS.between(startDateMin, endDateMax);
    }

    /**
     * Returns the site, hist/beh information of the input
     */
    public static String getSiteHistInfo(String site, String hist, String beh, int year) {
        return (StringUtils.isBlank(site) ? "Unknown Site" : site) + ", "
                + (StringUtils.isBlank(hist) ? "Unknown Histology" : hist) + "/"
                + (StringUtils.isBlank(beh) ? "Unknown Behavior" : beh) + " "
                + (validateYear(year) ? ("with year of diagnosis " + year) : "with unknown year of diagnosis");
    }

    static class DateFieldParts {

        private final Integer _year1;
        private Integer _month1;
        private Integer _day1;
        private final Integer _year2;
        private Integer _month2;
        private Integer _day2;

        public DateFieldParts(MphInput input1, MphInput input2) {
            _year1 = NumberUtils.isDigits(input1.getDateOfDiagnosisYear()) ? Integer.parseInt(input1.getDateOfDiagnosisYear()) : null;
            _year2 = NumberUtils.isDigits(input2.getDateOfDiagnosisYear()) ? Integer.parseInt(input2.getDateOfDiagnosisYear()) : null;
            _month1 = NumberUtils.isDigits(input1.getDateOfDiagnosisMonth()) ? Integer.parseInt(input1.getDateOfDiagnosisMonth()) : null;
            if (_month1 != null && (_month1 < 1 || _month1 > 12))
                _month1 = null;
            _month2 = NumberUtils.isDigits(input2.getDateOfDiagnosisMonth()) ? Integer.parseInt(input2.getDateOfDiagnosisMonth()) : null;
            if (_month2 != null && (_month2 < 1 || _month2 > 12))
                _month2 = null;
            _day1 = _month1 != null && NumberUtils.isDigits(input1.getDateOfDiagnosisDay()) ? Integer.parseInt(input1.getDateOfDiagnosisDay()) : null;
            if (_year1 != null && _day1 != null && (_day1 < 1 || _day1 > LocalDate.of(_year1, _month1, 1).lengthOfMonth()))
                _day1 = null;
            _day2 = _month2 != null && NumberUtils.isDigits(input2.getDateOfDiagnosisDay()) ? Integer.parseInt(input2.getDateOfDiagnosisDay()) : null;
            if (_year2 != null && _day2 != null && (_day2 < 1 || _day2 > LocalDate.of(_year2, _month2, 1).lengthOfMonth()))
                _day2 = null;
        }

        public Integer getYear1() {
            return _year1;
        }

        public Integer getMonth1() {
            return _month1;
        }

        public Integer getDay1() {
            return _day1;
        }

        public Integer getYear2() {
            return _year2;
        }

        public Integer getMonth2() {
            return _month2;
        }

        public Integer getDay2() {
            return _day2;
        }

    }
}