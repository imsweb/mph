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

    /**
     * The possible result of determining if two tumors are single or multiple primaries.
     */
    public enum MPResult {
        SINGLE_PRIMARY, MULTIPLE_PRIMARIES, QUESTIONABLE
    }

    // the unique instance of this utility class
    private static MphUtils _INSTANCE = null;

    // the Hematopoietic diseases provider used by the instance
    private HematoDbUtilsProvider _provider = null;

    // the cached groups of rules used by the instance
    private List<MphGroup> _groups = new ArrayList<>();

    /**
     * Initialized the instance with the given provider; this allows to use a customized provider instead of the default one.
     * This method must be called before trying to get an instance, of the default provider will be used instead.
     * @param provider
     */
    public static synchronized void initialize(HematoDbUtilsProvider provider) {
        if (provider == null)
            throw new NullPointerException("Hemato DB Utils provider cannot be null.");
        _INSTANCE = new MphUtils(provider);
    }

    /**
     * Returns true if the instance has been initialized, false otherwise.
     */
    public static synchronized boolean isInitialized() {
        return _INSTANCE != null;
    }

    public static synchronized MphUtils getInstance() {
        if (!isInitialized())
            initialize(new DefaultHematoDbUtilsProvider());
        return _INSTANCE;
    }

    /**
     * Private constructor, use the getInstance() method.
     * @param provider the provider to use for this instance.
     */
    private MphUtils(HematoDbUtilsProvider provider) {
        _provider = provider;

        // 1998 Hematopoietic rules
        _groups.add(new Mp1998HematopoieticGroup());

        // 2001 Hematopoietic rules
        _groups.add(new Mp2001HematopoieticGroup());

        // 2010 Hematopoietic rules
        _groups.add(new Mp2010HematopoieticGroup());

        // 2004 solid tumor rules 
        _groups.add(new Mp2004BenignBrainGroup());
        _groups.add(new Mp2004SolidMalignantGroup());

        // 2007 solid tumor rules
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
    }

    //when we apply the rule, it might be true, false or unknown if we don't have enough information.
    public enum RuleResult {
        TRUE, FALSE, UNKNOWN
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

    /**
     * Returns the HematoDB provider that was registered with the instance.
     * @return
     */
    public HematoDbUtilsProvider getProvider() {
        return _provider;
    }

    /**
     * Calculates the cancer group for the provided naaccr properties.
     * @param primarySite primary site
     * @param histology histology ICD-O-3
     * @param behavior behavior ICD-O-3
     * @return the corresponding cancer group, null if not found
     */
    public MphGroup findCancerGroup(String primarySite, String histology, String behavior, int year) {
        if (!GroupUtility.validateProperties(primarySite, histology, behavior, year))
            return null;

        for (MphGroup group : getAllGroups())
            if (group.isApplicable(primarySite, histology, behavior, year))
                return group;

        return null;
    }

    /**
     * Rerurns the list of all group of rules used by this instance.
     */
    public List<MphGroup> getAllGroups() {
        return Collections.unmodifiableList(_groups);
    }
}
