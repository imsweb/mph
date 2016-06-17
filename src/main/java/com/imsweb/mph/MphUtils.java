/*
 * Copyright (C) 2013 Information Management Services, Inc.
 */
package com.imsweb.mph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.joda.time.LocalDate;

import com.imsweb.mph.mpgroups.Mp1998HematopoieticGroup;
import com.imsweb.mph.mpgroups.Mp2001HematopoieticGroup;
import com.imsweb.mph.mpgroups.Mp2004BenignBrainGroup;
import com.imsweb.mph.mpgroups.Mp2004SolidMalignantGroup;
import com.imsweb.mph.mpgroups.Mp2007BenignBrainGroup;
import com.imsweb.mph.mpgroups.Mp2007BreastGroup;
import com.imsweb.mph.mpgroups.Mp2007ColonGroup;
import com.imsweb.mph.mpgroups.Mp2007HeadAndNeckGroup;
import com.imsweb.mph.mpgroups.Mp2007KidneyGroup;
import com.imsweb.mph.mpgroups.Mp2007LungGroup;
import com.imsweb.mph.mpgroups.Mp2007MalignantBrainGroup;
import com.imsweb.mph.mpgroups.Mp2007MelanomaGroup;
import com.imsweb.mph.mpgroups.Mp2007OtherSitesGroup;
import com.imsweb.mph.mpgroups.Mp2007UrinaryGroup;
import com.imsweb.mph.mpgroups.Mp2010HematopoieticGroup;

/**
 * This class is used to determine single versus multiple primaries. More information can be found on the following websites:
 * <a href="http://seer.cancer.gov/archive/manuals/codeman.pdf">SEER 1998 multiple primary rules for hematopoietic cancer</a>
 * <br/><br/>
 * <a href="http://training.seer.cancer.gov/hemat/">SEER 2001 multiple primary rules for hematopoietic cancer</a>
 * <br/><br/>
 * <a href="http://seer.cancer.gov/tools/heme/Hematopoietic_Instructions_and_Rules.pdf">SEER multiple primary rules for hematopoietic cancer for cases diagnosed in 2010 and later</a>
 * <br/><br/>
 * <a href="http://seer.cancer.gov/archive/manuals/2004Revision1/SPM_2004_maindoc.r1.pdf">SEER 2004 multiple primary rules</a>
 * <br/><br/>
 * <a href="http://www.seer.cancer.gov/tools/mphrules">SEER 2007 multiple primary rules</a>
 * <br/><br/>
 * This Java implementation is based on the the documentation provided on the above websites.
 * Created in December 2013 by Sewbesew Bekele
 */
public class MphUtils {

    private static HematoDbUtilsProvider _provider = null;

    public static final String PROP_PRIMARY_SITE = "primarySite";
    public static final String PROP_HISTOLOGY_ICDO3 = "histologyIcdO3";
    public static final String PROP_BEHAVIOR_ICDO3 = "behaviorIcdO3";
    public static final String PROP_HISTOLOGY_ICDO2 = "histologyIcdO2";
    public static final String PROP_BEHAVIOR_ICDO2 = "behaviorIcdO2";
    public static final String PROP_LATERALITY = "laterality";
    public static final String PROP_DX_YEAR = "dateOfDiagnosisYear";
    public static final String PROP_DX_MONTH = "dateOfDiagnosisMonth";
    public static final String PROP_DX_DAY = "dateOfDiagnosisDay";
    public static final String PROP_TX_STATUS = "rxSummTreatmentStatus";

    //when we apply the rule, it might be true, false or unknown if we don't have enough information.
    public enum RuleResult {
        TRUE, FALSE, UNKNOWN
    }

    //Based on the applied rule results, we would say two tumors are single or multiple primary or questionable if we don't have enough information.
    public enum MPResult {
        SINGLE_PRIMARY, MULTIPLE_PRIMARIES, QUESTIONABLE
    }

    private static List<MphGroup> _GROUPS = new ArrayList<>();

    static {
        _GROUPS.add(new Mp2007HeadAndNeckGroup());
        _GROUPS.add(new Mp2007ColonGroup());
        _GROUPS.add(new Mp2007LungGroup());
        _GROUPS.add(new Mp2007MelanomaGroup());
        _GROUPS.add(new Mp2007BreastGroup());
        _GROUPS.add(new Mp2007KidneyGroup());
        _GROUPS.add(new Mp2007UrinaryGroup());
        _GROUPS.add(new Mp2007BenignBrainGroup());
        _GROUPS.add(new Mp2007MalignantBrainGroup());
        _GROUPS.add(new Mp2007OtherSitesGroup());
        _GROUPS.add(new Mp1998HematopoieticGroup());
        _GROUPS.add(new Mp2001HematopoieticGroup());
        _GROUPS.add(new Mp2010HematopoieticGroup());
        _GROUPS.add(new Mp2004BenignBrainGroup());
        _GROUPS.add(new Mp2004SolidMalignantGroup());
    }

    private MphUtils(HematoDbUtilsProvider provider) {
        _provider = provider;
    }

    public static MphUtils getInstance(HematoDbUtilsProvider provider) {
        return new MphUtils(provider);
    }

    /**
     * Determines whether two records of solid tumors are single or multiple primary. It returns "questionable" if there is no enough information to decide.
     * <br/><br/>
     * The provided record doesn't need to contain all the input variables, but the algorithm will use the following ones:
     * <ul>
     * <li>primarySite (#400)</li>
     * <li>histologyIcdO3 (#522)</li>
     * <li>behaviorIcdO3 (#523)</li>
     * <li>histologyIcdO2 (#420)</li>
     * <li>behaviorIcdO2 (#430)</li>
     * <li>laterality (#410)</li>
     * <li>dateOfDiagnosisYear (#390)</li>
     * <li>dateOfDiagnosisMonth (#390)</li>
     * <li>dateOfDiagnosisDay (#390)</li>
     * <li>rxSummTreatmentStatus (#1285)</li>
     * </ul>
     * <br/><br/>
     * All those properties are defined as constants in this class.
     * <br/><br/>
     * @param record1 a map of properties representing a NAACCR line
     * @param record2 a map of properties representing a NAACCR line
     * @return the computed output which is an object which has result (Single Primary, Multiple Primaries or Questionable), reason and rules applied to make a decision.
     */
    public MphOutput computePrimaries(Map<String, String> record1, Map<String, String> record2) {
        MphInput input1 = new MphInput();
        input1.setPrimarySite(record1.get(PROP_PRIMARY_SITE));
        input1.setHistologyIcdO3(record1.get(PROP_HISTOLOGY_ICDO3));
        input1.setBehaviorIcdO3(record1.get(PROP_BEHAVIOR_ICDO3));
        input1.setHistologyIcdO2(record1.get(PROP_HISTOLOGY_ICDO2));
        input1.setBehaviorIcdO2(record1.get(PROP_BEHAVIOR_ICDO2));
        input1.setLaterality(record1.get(PROP_LATERALITY));
        input1.setDateOfDiagnosisYear(record1.get(PROP_DX_YEAR));
        input1.setDateOfDiagnosisMonth(record1.get(PROP_DX_MONTH));
        input1.setDateOfDiagnosisDay(record1.get(PROP_DX_DAY));
        input1.setTxStatus(record1.get(PROP_TX_STATUS));

        MphInput input2 = new MphInput();
        input2.setPrimarySite(record2.get(PROP_PRIMARY_SITE));
        input2.setHistologyIcdO3(record2.get(PROP_HISTOLOGY_ICDO3));
        input2.setBehaviorIcdO3(record2.get(PROP_BEHAVIOR_ICDO3));
        input2.setHistologyIcdO2(record2.get(PROP_HISTOLOGY_ICDO2));
        input2.setBehaviorIcdO2(record2.get(PROP_BEHAVIOR_ICDO2));
        input2.setLaterality(record2.get(PROP_LATERALITY));
        input2.setDateOfDiagnosisYear(record2.get(PROP_DX_YEAR));
        input2.setDateOfDiagnosisMonth(record2.get(PROP_DX_MONTH));
        input2.setDateOfDiagnosisDay(record2.get(PROP_DX_DAY));
        input2.setTxStatus(record2.get(PROP_TX_STATUS));

        return computePrimaries(input1, input2);
    }

    /**
     * Determines whether two input objects of solid tumors are single or multiple primary. It returns "questionable" if there is no enough information to decide.
     * <br/><br/>
     * <br/><br/>
     * The provided record dto has the following parameters:
     * <ul>
     * <li>primarySite (#400)</li>
     * <li>histologyIcdO3 (#522)</li>
     * <li>behaviorIcdO3 (#523)</li>
     * <li>histologyIcdO2 (#420)</li>
     * <li>behaviorIcdO2 (#430)</li>
     * <li>laterality (#410)</li>
     * <li>dateOfDiagnosisYear (#390)</li>
     * <li>dateOfDiagnosisMonth (#390)</li>
     * <li>dateOfDiagnosisDay (#390)</li>
     * <li>rxSummTreatmentStatus (#1285)</li>
     * </ul>
     * <br/><br/>
     * All those properties are defined as constants in this class.
     * <br/><br/>
     * @param input1 an input dto which has a list of parameters used in the calculation.
     * @param input2 an input dto which has a list of parameters used in the calculation.
     * @return the computed output which is an object which has result (Single Primary, Multiple Primaries or Questionable), reason and rules applied to make a decision.
     */
    public MphOutput computePrimaries(MphInput input1, MphInput input2) {
        MphOutput output = new MphOutput();

        int year1 = NumberUtils.isDigits(input1.getDateOfDiagnosisYear()) ? Integer.parseInt(input1.getDateOfDiagnosisYear()) : -1;
        int year2 = NumberUtils.isDigits(input2.getDateOfDiagnosisYear()) ? Integer.parseInt(input2.getDateOfDiagnosisYear()) : -1;
        String site1 = input1.getPrimarySite(), site2 = input2.getPrimarySite(), hist1 = input1.getHistology(), hist2 = input2.getHistology();
        String beh1 = input1.getBehavior(), beh2 = input2.getBehavior();

        if (!validateProperties(site1, hist1, beh1, year1)) {
            output.setResult(MPResult.QUESTIONABLE);
            output.setReason(
                    "Unable to identify cancer group for first set of parameters. Valid primary site (C000-C999 excluding C809), histology (8000-9999), behavior (0-3, 6) and diagnosis year are required.");
            return output;
        }
        else if (!validateProperties(site2, hist2, beh2, year2)) {
            output.setResult(MPResult.QUESTIONABLE);
            output.setReason(
                    "Unable to identify cancer group for second set of parameters. Valid primary site (C000-C999 excluding C809), histology (8000-9999), behavior (0-3, 6) and diagnosis year are required.");
            return output;
        }

        //calculate cancer group based on latest year
        int latestYear = year1 > year2 ? year1 : year2;
        MphGroup group1 = findCancerGroup(site1, hist1, beh1, latestYear);
        MphGroup group2 = findCancerGroup(site2, hist2, beh2, latestYear);

        if (group1 == null) {
            output.setResult(MPResult.QUESTIONABLE);
            output.setReason("The first tumor provided does not belong to any of the cancer groups.");
        }
        else if (group2 == null) {
            output.setResult(MPResult.QUESTIONABLE);
            output.setReason("The second tumor provided does not belong to any of the cancer groups.");
        }
        else if (!group1.getId().equals(group2.getId())) {
            output.setResult(MPResult.MULTIPLE_PRIMARIES);
            output.setReason("The two sets of parameters belong to two different cancer groups.");
        }
        else {
            for (MphRule rule : group1.getRules()) {
                output.getAppliedRules().add(rule);
                MphRuleResult result = rule.apply(input1, input2);
                if (RuleResult.TRUE.equals(result.getResult())) {
                    output.setResult(rule.getResult());
                    output.setReason(rule.getReason());
                    break;
                }
                else if (RuleResult.UNKNOWN.equals(result.getResult())) {
                    output.setResult(MPResult.QUESTIONABLE);
                    output.setReason(result.getMessage());
                    break;
                }
            }
        }

        return output;
    }

    /**
     * Returns true if two morphologies are same primaries based on the hemato DB provider.
     * @param leftCode
     * @param rightCode
     * @param year
     * @return true if two morphologies are same primaries based on the hemato DB provider.
     */
    public static boolean isSamePrimary(String leftCode, String rightCode, int year) {
        return _provider.isSamePrimary(leftCode, rightCode, year);
    }

    /**
     * Returns true if second morphology is in the first disease transform to list based on the hemato DB provider.
     * @param leftCode
     * @param rightCode
     * @param year
     * @return true if second morphology is in the first disease transform to list
     */
    public static boolean isAcuteTransformation(String leftCode, String rightCode, int year) {
        return _provider.isAcuteTransformation(leftCode, rightCode, year);
    }

    /**
     * Returns true if second morphology is in the first disease transform from list based on the hemato DB provider.
     * @param leftCode
     * @param rightCode
     * @param year
     * @return true if second morphology is in the first disease transform from list
     */
    public static boolean isChronicTransformation(String leftCode, String rightCode, int year) {
        return _provider.isChronicTransformation(leftCode, rightCode, year);
    }

    /**
     * Calculates the cancer group for the provided naaccr properties.
     * @param primarySite
     * @param histology
     * @param behavior
     * @return the computed cancer group
     */
    public static MphGroup findCancerGroup(String primarySite, String histology, String behavior, int year) {
        if (!validateProperties(primarySite, histology, behavior, year))
            return null;
        for (MphGroup group : _GROUPS) {
            if (group.isApplicable(primarySite, histology, behavior, year))
                return group;
        }
        return null;
    }

    /**
     * @return the list of cancer groups.
     */
    public List<MphGroup> getAllGroups() {
        return Collections.unmodifiableList(_GROUPS);
    }

    /**
     * Validates the provided input's primary site, histology, behavior and diagnosis year. These properties are required to determine the cancer group and used at least in one of the rules in each group.
     */
    public static boolean validateProperties(String primarySite, String histology, String behavior, int validateBehavior) {
        return validateSite(primarySite) && validateHistology(histology) && validateBehavior(behavior) && validateYear(validateBehavior);
    }

    public static boolean validateSite(String site) {
        return site != null && site.length() == 4 && site.startsWith("C") && NumberUtils.isDigits(site.substring(1)) && !"C809".equalsIgnoreCase(site);
    }

    public static boolean validateHistology(String hist) {
        return hist != null && NumberUtils.isDigits(hist) && Integer.parseInt(hist) >= 8000 && Integer.parseInt(hist) <= 9999;
    }

    public static boolean validateBehavior(String behavior) {
        return Arrays.asList("0", "1", "2", "3", "6").contains(behavior);
    }

    public static boolean validateYear(int year) {
        return year >= 0 && year <= LocalDate.now().getYear();
    }
}
