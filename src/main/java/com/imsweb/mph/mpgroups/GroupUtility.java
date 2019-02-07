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
        return hist != null && NumberUtils.isDigits(hist) && Integer.parseInt(hist) >= 8000 && Integer.parseInt(hist) <= 9999;
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
    public static boolean sameValidDates(int year1, int month1, int day1, int year2, int month2, int day2) {
        try {
            return LocalDate.of(year1, month1, day1).isEqual(LocalDate.of(year2, month2, day2));
        }
        catch (Exception e) {
            return false;
        }
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
            return null;

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
            return null;
        for (String item : list) {
            String[] ranges = StringUtils.split(item.trim(), ',');
            for (String range : ranges) {
                String[] parts = StringUtils.split(range.trim(), '-');
                if (parts.length <= 1)
                    result.add(range);
                else {
                    Integer start = Integer.valueOf(parts[0]);
                    Integer end = Integer.valueOf(parts[1]);
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
     * checks if one cancers is BOTH, and the other is left or right side
     */
    public static boolean areBothAndLeftOrRightSides(String lat1, String lat2) {
        return (MphConstants.BOTH.equals(lat1) && (MphConstants.RIGHT.equals(lat2) || MphConstants.LEFT.equals(lat2))) ||
               (MphConstants.BOTH.equals(lat2) && (MphConstants.RIGHT.equals(lat1) || MphConstants.LEFT.equals(lat1)));
    }

    /**
     * checks if two cases have same and valid site, hist, behavior, date, laterality
     */
    public static boolean sameAndValidMainFields(MphInput i1, MphInput i2) {
        int year1 = NumberUtils.isDigits(i1.getDateOfDiagnosisYear()) ? Integer.parseInt(i1.getDateOfDiagnosisYear()) : -1;
        int year2 = NumberUtils.isDigits(i2.getDateOfDiagnosisYear()) ? Integer.parseInt(i2.getDateOfDiagnosisYear()) : -1;
        int month1 = NumberUtils.isDigits(i1.getDateOfDiagnosisMonth()) ? Integer.parseInt(i1.getDateOfDiagnosisMonth()) : -1;
        int month2 = NumberUtils.isDigits(i2.getDateOfDiagnosisMonth()) ? Integer.parseInt(i2.getDateOfDiagnosisMonth()) : -1;
        int day1 = NumberUtils.isDigits(i1.getDateOfDiagnosisDay()) ? Integer.parseInt(i1.getDateOfDiagnosisDay()) : -1;
        int day2 = NumberUtils.isDigits(i2.getDateOfDiagnosisDay()) ? Integer.parseInt(i2.getDateOfDiagnosisDay()) : -1;

        String site1 = i1.getPrimarySite(), site2 = i2.getPrimarySite(), hist1 = i1.getHistology(), hist2 = i2.getHistology();
        String beh1 = i1.getBehavior(), beh2 = i2.getBehavior(), lat1 = i1.getLaterality(), lat2 = i2.getLaterality();
        return validateProperties(site1, hist1, beh1, year1) &&
                validateProperties(site2, hist2, beh2, year2) &&
                validateLaterality(lat1) && validateLaterality(lat2) &&
                Objects.equals(site1, site2) &&
                Objects.equals(hist1, hist2) &&
                Objects.equals(beh1, beh2) &&
                sameValidDates(year1, month1, day1, year2, month2, day2) &&
                Objects.equals(lat1, lat2);
    }

    /**
     * checks which tumor is diagnosed later. It returns 1 (if tumor 1 is diagnosed after tumor 2), 2 (if tumor 2 is diagnosed after tumor 1),
     * 0 (if the diagnosis takes at the same day) or -1 (if there is insufficient information e.g if both year is 2007, but month and day is unknown)
     */
    public static int compareDxDate(MphInput input1, MphInput input2) {
        int tumor1 = 1, tumor2 = 2, sameDay = 0, unknown = -1;
        int year1 = NumberUtils.isDigits(input1.getDateOfDiagnosisYear()) ? Integer.parseInt(input1.getDateOfDiagnosisYear()) : 9999;
        int year2 = NumberUtils.isDigits(input2.getDateOfDiagnosisYear()) ? Integer.parseInt(input2.getDateOfDiagnosisYear()) : 9999;
        int month1 = NumberUtils.isDigits(input1.getDateOfDiagnosisMonth()) ? Integer.parseInt(input1.getDateOfDiagnosisMonth()) : 99;
        int month2 = NumberUtils.isDigits(input2.getDateOfDiagnosisMonth()) ? Integer.parseInt(input2.getDateOfDiagnosisMonth()) : 99;
        int day1 = NumberUtils.isDigits(input1.getDateOfDiagnosisDay()) ? Integer.parseInt(input1.getDateOfDiagnosisDay()) : 99;
        int day2 = NumberUtils.isDigits(input2.getDateOfDiagnosisDay()) ? Integer.parseInt(input2.getDateOfDiagnosisDay()) : 99;
        //If year is missing or in the future, return unknown
        int currYear = LocalDate.now().getYear();
        if (year1 == 9999 || year2 == 9999 || year1 > currYear || year2 > currYear)
            return unknown;
        else if (year1 > year2)
            return tumor1;
        else if (year2 > year1)
            return tumor2;

        if (month1 == 99)
            day1 = 99;
        if (month2 == 99)
            day2 = 99;
        //if month and day are invalid set them to 99 (Example: if month is 13 or day is 35)
        try {
            LocalDate.of(year1, month1 == 99 ? 1 : month1, day1 == 99 ? 1 : day1);
        }
        catch (Exception e) {
            day1 = 99;
            if (month1 < 1 || month1 > 12)
                month1 = 99;
        }

        try {
            LocalDate.of(year2, month2 == 99 ? 1 : month2, day2 == 99 ? 1 : day2);
        }
        catch (Exception e) {
            day2 = 99;
            if (month2 < 1 || month2 > 12)
                month2 = 99;
        }

        if (month1 == 99 || month2 == 99)
            return unknown;
        else if (month1 > month2)
            return tumor1;
        else if (month2 > month1)
            return tumor2;
        else if (day1 == 99 || day2 == 99)
            return unknown;
        else if (day1 > day2)
            return tumor1;
        else if (day2 > day1)
            return tumor2;
        else
            return sameDay;
    }

    /**
     * checks if the two tumors are diagnosed "x" years apart. It returns Yes (1), No (0) or Unknown (-1) (If there is no enough information)
     */
    public static int verifyYearsApart(MphInput input1, MphInput input2, int yearsApart) {
        int yes = 1, no = 0, unknown = -1;
        int year1 = NumberUtils.isDigits(input1.getDateOfDiagnosisYear()) ? Integer.parseInt(input1.getDateOfDiagnosisYear()) : 9999;
        int year2 = NumberUtils.isDigits(input2.getDateOfDiagnosisYear()) ? Integer.parseInt(input2.getDateOfDiagnosisYear()) : 9999;
        int month1 = NumberUtils.isDigits(input1.getDateOfDiagnosisMonth()) ? Integer.parseInt(input1.getDateOfDiagnosisMonth()) : 99;
        int month2 = NumberUtils.isDigits(input2.getDateOfDiagnosisMonth()) ? Integer.parseInt(input2.getDateOfDiagnosisMonth()) : 99;
        int day1 = NumberUtils.isDigits(input1.getDateOfDiagnosisDay()) ? Integer.parseInt(input1.getDateOfDiagnosisDay()) : 99;
        int day2 = NumberUtils.isDigits(input2.getDateOfDiagnosisDay()) ? Integer.parseInt(input2.getDateOfDiagnosisDay()) : 99;
        //If year is missing or in the future, return unknown
        int currYear = LocalDate.now().getYear();
        if (year1 == 9999 || year2 == 9999 || year1 > currYear || year2 > currYear)
            return unknown;
        else if (Math.abs(year1 - year2) > yearsApart)
            return yes;
        else if (Math.abs(year1 - year2) < yearsApart)
            return no;
        else {
            //if month is missing, set day to 99
            if (month1 == 99)
                day1 = 99;
            if (month2 == 99)
                day2 = 99;
            //if month and day are invalid set them to 99 (Example: if month is 13 or day is 35)
            try {
                LocalDate.of(year1, month1 == 99 ? 1 : month1, day1 == 99 ? 1 : day1);
            }
            catch (Exception e) {
                day1 = 99;
                if (month1 < 1 || month1 > 12)
                    month1 = 99;
            }

            try {
                LocalDate.of(year2, month2 == 99 ? 1 : month2, day2 == 99 ? 1 : day2);
            }
            catch (Exception e) {
                day2 = 99;
                if (month2 < 1 || month2 > 12)
                    month2 = 99;
            }

            if (month1 == 99 || month2 == 99)
                return unknown;
            else if ((year1 > year2 && month1 > month2) || (year2 > year1 && month2 > month1))
                return yes;
            else if ((year1 > year2 && month1 < month2) || (year2 > year1 && month2 < month1))
                return no;
            else if (day1 == 99 || day2 == 99)
                return unknown;
            else
                return Math.abs(ChronoUnit.YEARS.between(LocalDate.of(year1, month1, day1), LocalDate.of(year2, month2, day2))) >= yearsApart ? yes : no;
        }
    }

    /**
     * checks if the two tumors are diagnosed "x" days apart. It returns Yes (1), No (0) or Unknown (-1) (If there is no enough information)
     */
    public static int verifyDaysApart(MphInput input1, MphInput input2, int days) {
        int unknown = -1, apart = 1, within = 0;
        int latestDx = compareDxDate(input1, input2);
        if (latestDx == 0)
            return within;

        int year1 = NumberUtils.isDigits(input1.getDateOfDiagnosisYear()) ? Integer.parseInt(input1.getDateOfDiagnosisYear()) : 9999;
        int year2 = NumberUtils.isDigits(input2.getDateOfDiagnosisYear()) ? Integer.parseInt(input2.getDateOfDiagnosisYear()) : 9999;
        //If year is missing or in the future, return unknown
        int currYear = LocalDate.now().getYear();
        if (year1 == 9999 || year2 == 9999 || year1 > currYear || year2 > currYear)
            return unknown;
        int month1 = NumberUtils.isDigits(input1.getDateOfDiagnosisMonth()) ? Integer.parseInt(input1.getDateOfDiagnosisMonth()) : 99;
        int month2 = NumberUtils.isDigits(input2.getDateOfDiagnosisMonth()) ? Integer.parseInt(input2.getDateOfDiagnosisMonth()) : 99;
        int day1 = NumberUtils.isDigits(input1.getDateOfDiagnosisDay()) ? Integer.parseInt(input1.getDateOfDiagnosisDay()) : 99;
        int day2 = NumberUtils.isDigits(input2.getDateOfDiagnosisDay()) ? Integer.parseInt(input2.getDateOfDiagnosisDay()) : 99;

        //if month is missing, set day to 99
        if (month1 == 99)
            day1 = 99;
        if (month2 == 99)
            day2 = 99;
        //if month and day are invalid set them to 99 (Example: if month is 13 or day is 35)
        try {
            LocalDate.of(year1, month1 == 99 ? 1 : month1, day1 == 99 ? 1 : day1);
        }
        catch (Exception e) {
            day1 = 99;
            if (month1 < 1 || month1 > 12)
                month1 = 99;
        }

        try {
            LocalDate.of(year2, month2 == 99 ? 1 : month2, day2 == 99 ? 1 : day2);
        }
        catch (Exception e) {
            day2 = 99;
            if (month2 < 1 || month2 > 12)
                month2 = 99;
        }

        int minDaysInBetween = daysInBetween(year2, month2, day2, year1, month1, day1, true);
        int maxDaysInBetween = daysInBetween(year2, month2, day2, year1, month1, day1, false);
        if (latestDx == -1) {
            return Math.max(Math.abs(minDaysInBetween), Math.abs(maxDaysInBetween)) <= days ? within : unknown;
        }
        else if (latestDx == 2) {
            minDaysInBetween = daysInBetween(year1, month1, day1, year2, month2, day2, true);
            maxDaysInBetween = daysInBetween(year1, month1, day1, year2, month2, day2, false);
        }

        if (minDaysInBetween > days)
            return apart;
        else if (maxDaysInBetween <= days)
            return within;
        else
            return unknown;
    }

    //This method is called if one diagnosis is after the other, It returns the minimum or maximum days between two diagnosis dates based on boolean minimum
    private static int daysInBetween(int startYr, int startMon, int startDay, int endYr, int endMon, int endDay, boolean minimum) {
        LocalDate startDateMin = LocalDate.of(startYr, 1, 1);
        LocalDate startDateMax = LocalDate.of(startYr, 12, 31);
        LocalDate endDateMin = LocalDate.of(endYr, 1, 1);
        LocalDate endDateMax = LocalDate.of(endYr, 12, 31);
        if (startDay != 99) {
            startDateMin = LocalDate.of(startYr, startMon, startDay);
            startDateMax = LocalDate.of(startYr, startMon, startDay);
        }
        else if (startMon != 99) {
            startDateMin = LocalDate.of(startYr, startMon, 1);
            startDateMax = LocalDate.of(startYr, startMon, YearMonth.of(startYr, startMon).lengthOfMonth());
        }
        if (endDay != 99) {
            endDateMin = LocalDate.of(endYr, endMon, endDay);
            endDateMax = LocalDate.of(endYr, endMon, endDay);
        }
        else if (endMon != 99) {
            endDateMin = LocalDate.of(endYr, endMon, 1);
            endDateMax = LocalDate.of(endYr, endMon, YearMonth.of(endYr, endMon).lengthOfMonth());
        }

        return minimum ? (int)ChronoUnit.DAYS.between(startDateMax, endDateMin) : (int)ChronoUnit.DAYS.between(startDateMin, endDateMax);
    }

    /**
     * checks if firstBehavior occurs before secondBehavior.
     */
    public static boolean isOneBehaviorBeforeTheOther(MphInput i1, MphInput i2, String firstBehavior, String secondBehavior) {
        String beh1 = i1.getBehavior(), beh2 = i2.getBehavior();
        if (differentCategory(beh1, beh2, Collections.singletonList(secondBehavior), Collections.singletonList(firstBehavior))) {
            int latestDx = compareDxDate(i1, i2);
            if ((1 == latestDx && secondBehavior.equals(beh1)) || (2 == latestDx && secondBehavior.equals(beh2)))
                return true;
        }
        return false;
    }

    /**
     * checks if first Input occurs before second Input.
     */
    public static boolean isFirstTumorBeforeSecondTumor(MphInput i1, MphInput i2) {
        if (compareDxDate(i1, i2) == 2)
            return true;
        return false;
    }

    /**
     * Checks if one element from the first list is in the second.
     */
    public static boolean containsElement(List<String> list1, List<String> list2) {
        for (String s : list1) {
            if (list2.contains(s)) return true;
        }
        return false;
    }

    /**
     * Creates a list of Histology and Histology / Behavior.
     */
    public static List<String> createHistologyBehaviorList(MphInput i1) {
        List<String> i1list = new ArrayList<String>();
        i1list.add(i1.getHistology() + "/" + i1.getBehavior());
        i1list.add(i1.getHistology());
        return i1list;
    }

    /**
     * Return true if one site is in each list but they are not the same site.
     */
    /*
    public static boolean areSitesInBothGroupsButNotTheSame(String site1, String site2, String groupList1, String groupList2) {

        if (isSiteContained(groupList1, site1) && isSiteContained(groupList2, site2) && !site1.equals(site2)) {
            return true;
        }
        else if (isSiteContained(groupList2, site1) && isSiteContained(groupList1, site2) && !site1.equals(site2)) {
            return true;
        }

        return false;
    }
    */


    /**
     * Return true if one site is in the first group, and the second site is in the second group AND not in the first group.
     */
    public static boolean areSitesInBothGroupsAndSecondNotInFirstGroup(String site1, String site2, String groupList1, String groupList2) {

        if (isSiteContained(groupList1, site1)) {
            if (isSiteContained(groupList2, site2) && !isSiteContained(groupList1, site2)) {
                return true;
            }
        }
        else if (isSiteContained(groupList1, site2)) {
            if (isSiteContained(groupList2, site1) && !isSiteContained(groupList1, site1)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return true if tumors occur within 60 days of each other.
     */
    public static boolean areSimultaneousTumors(MphInput i1, MphInput i2) {
        if (GroupUtility.verifyDaysApart(i1, i2, 60) == 0) {
            return true;
        }
        return false;
    }



}
