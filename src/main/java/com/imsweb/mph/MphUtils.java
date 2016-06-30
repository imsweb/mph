/*
 * Copyright (C) 2013 Information Management Services, Inc.
 */
package com.imsweb.mph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;

import com.imsweb.mph.mpgroups.GroupUtility;
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

    private static MphUtils _UTILS = null;
    private HematoDbUtilsProvider _provider = null;
    private List<MphGroup> _groups = new ArrayList<>();

    public static synchronized void initialize(HematoDbUtilsProvider provider) {
        if (provider == null)
            throw new RuntimeException("Hemato Db Utils provider should be provided. Please provide one or use the default DefaultHematoDbUtilsProvider class.");
        _UTILS = new MphUtils(provider);
    }

    public static synchronized MphUtils getInstance() {
        if (_UTILS == null)
            throw new RuntimeException("Please initialize the class with a Hemato Db Utils Provider.");
        return _UTILS;
    }

    private MphUtils(HematoDbUtilsProvider provider) {
        _provider = provider;
        _groups.add(new Mp2007HeadAndNeckGroup());
        _groups.add(new Mp2007ColonGroup());
        _groups.add(new Mp2007LungGroup());
        _groups.add(new Mp2007MelanomaGroup());
        _groups.add(new Mp2007BreastGroup());
        _groups.add(new Mp2007KidneyGroup());
        _groups.add(new Mp2007UrinaryGroup());
        _groups.add(new Mp2007BenignBrainGroup());
        _groups.add(new Mp2007MalignantBrainGroup());
        _groups.add(new Mp2007OtherSitesGroup());
        _groups.add(new Mp1998HematopoieticGroup());
        _groups.add(new Mp2001HematopoieticGroup());
        _groups.add(new Mp2010HematopoieticGroup());
        _groups.add(new Mp2004BenignBrainGroup());
        _groups.add(new Mp2004SolidMalignantGroup());
    }

    //when we apply the rule, it might be true, false or unknown if we don't have enough information.
    public enum RuleResult {
        TRUE, FALSE, UNKNOWN
    }

    //Based on the applied rule results, we would say two tumors are single or multiple primary or questionable if we don't have enough information.
    public enum MPResult {
        SINGLE_PRIMARY, MULTIPLE_PRIMARIES, QUESTIONABLE
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

        if (!GroupUtility.validateProperties(site1, hist1, beh1, year1)) {
            output.setResult(MPResult.QUESTIONABLE);
            output.setReason(
                    "Unable to identify cancer group for first set of parameters. Valid primary site (C000-C999 excluding C809), histology (8000-9999), behavior (0-3, 6) and diagnosis year are required.");
            return output;
        }
        else if (!GroupUtility.validateProperties(site2, hist2, beh2, year2)) {
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

    public HematoDbUtilsProvider getProvider() {
        return _provider;
    }

    /**
     * Calculates the cancer group for the provided naaccr properties.
     * @param primarySite
     * @param histology
     * @param behavior
     * @return the computed cancer group
     */
    public MphGroup findCancerGroup(String primarySite, String histology, String behavior, int year) {
        if (!GroupUtility.validateProperties(primarySite, histology, behavior, year))
            return null;
        for (MphGroup group : getAllGroups()) {
            if (group.isApplicable(primarySite, histology, behavior, year))
                return group;
        }
        return null;
    }

    /**
     * @return the list of cancer groups.
     */
    public List<MphGroup> getAllGroups() {
        return Collections.unmodifiableList(_groups);
    }
}
