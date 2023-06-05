/*
 * Copyright (C) 2013 Information Management Services, Inc.
 */
package com.imsweb.mph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.imsweb.mph.internal.TempRuleResult;
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
import com.imsweb.mph.mpgroups.Mp2018BreastGroup;
import com.imsweb.mph.mpgroups.Mp2018ColonGroup;
import com.imsweb.mph.mpgroups.Mp2018HeadAndNeckGroup;
import com.imsweb.mph.mpgroups.Mp2018KidneyGroup;
import com.imsweb.mph.mpgroups.Mp2018LungGroup;
import com.imsweb.mph.mpgroups.Mp2018MalignantCNSAndPeripheralNervesGroup;
import com.imsweb.mph.mpgroups.Mp2018NonMalignantCNSTumorsGroup;
import com.imsweb.mph.mpgroups.Mp2018UrinarySitesGroup;
import com.imsweb.mph.mpgroups.Mp2021CutaneousMelanomaGroup;
import com.imsweb.mph.mpgroups.Mp2023OtherSitesGroup;

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
 * <a href="https://seer.cancer.gov/tools/solidtumor/">2018 Solid Tumor Rules</a>
 * <br/><br/>
 * This Java implementation is based on the the documentation provided on the above websites.
 * Created in December 2013 by Sewbesew Bekele
 */
public final class MphUtils {

    /**
     * The possible result of determining if two tumors are single or multiple primaries.
     */
    public enum MpResult {
        // indicates the two tumors are the same primary
        SINGLE_PRIMARY,
        // indicates the two tumors are different primaries
        MULTIPLE_PRIMARIES,
        // indicates there is not enough information to make a proper determination
        QUESTIONABLE,
        // indicates one or more of the requires inputs (site, hist, behavior, year) are missing or invalid
        INVALID_INPUT
    }

    // the unique instance of this utility class
    private static MphUtils _INSTANCE = null;

    // the Hematopoietic diseases provider used by the instance
    private HematoDbUtilsProvider _provider = null;

    // the cached groups of rules used by the instance
    private Map<String, MphGroup> _groups = new LinkedHashMap<>();

    /**
     * Initialized the instance with the given provider; this allows to use a customized provider instead of the default one.
     * This method must be called before trying to get an instance, of the default provider will be used instead.
     * @param provider
     */
    public static synchronized void initialize(HematoDbUtilsProvider provider) {
        _INSTANCE = new MphUtils(provider);
    }

    /**
     * Returns true if the instance has been initialized, false otherwise.
     */
    public static synchronized boolean isInitialized() {
        return _INSTANCE != null;
    }

    /**
     * Returns the instance of MPH utils.
     */
    public static synchronized MphUtils getInstance() {
        if (!isInitialized())
            initialize(new DefaultHematoDbUtilsProvider());
        return _INSTANCE;
    }

    /**
     * Adds an ID and a MphGroup to the _groups Map.
     */
    private void addGroup(MphGroup newGroup) {
        _groups.put(newGroup.getId(), newGroup);
    }

    /**
     * Private constructor, use the getInstance() method.
     * @param provider the provider to use for this instance, cannot be null
     */
    private MphUtils(HematoDbUtilsProvider provider) {
        if (provider == null)
            throw new NullPointerException("Hemato DB Utils provider cannot be null.");
        _provider = provider;

        // 1998 Hematopoietic rules
        addGroup(new Mp1998HematopoieticGroup());

        // 2001 Hematopoietic rules
        addGroup(new Mp2001HematopoieticGroup());

        // 2010 Hematopoietic rules
        addGroup(new Mp2010HematopoieticGroup());

        // 2004 solid tumor rules 
        addGroup(new Mp2004BenignBrainGroup());
        addGroup(new Mp2004SolidMalignantGroup());

        // 2007 solid tumor rules
        addGroup(new Mp2007HeadAndNeckGroup());
        addGroup(new Mp2007ColonGroup());
        addGroup(new Mp2007LungGroup());
        addGroup(new Mp2007MelanomaGroup());
        addGroup(new Mp2007BreastGroup());
        addGroup(new Mp2007KidneyGroup());
        addGroup(new Mp2007UrinaryGroup());
        addGroup(new Mp2007BenignBrainGroup());
        addGroup(new Mp2007MalignantBrainGroup());
        addGroup(new Mp2007OtherSitesGroup());

        // 2018 solid tumor rules
        addGroup(new Mp2018BreastGroup());
        addGroup(new Mp2018ColonGroup());
        addGroup(new Mp2018HeadAndNeckGroup());
        addGroup(new Mp2018KidneyGroup());
        addGroup(new Mp2018LungGroup());
        addGroup(new Mp2018MalignantCNSAndPeripheralNervesGroup());
        addGroup(new Mp2018NonMalignantCNSTumorsGroup());
        addGroup(new Mp2018UrinarySitesGroup());

        //2021 solid tumor rules
        addGroup(new Mp2021CutaneousMelanomaGroup());

        //2023
        addGroup(new Mp2023OtherSitesGroup());
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
        String site1 = input1.getPrimarySite();
        String site2 = input2.getPrimarySite();
        String hist1 = input1.getHistology();
        String hist2 = input2.getHistology();
        String beh1 = input1.getBehavior();
        String beh2 = input2.getBehavior();

        if (!GroupUtility.validateProperties(site1, hist1, beh1, year1)) {
            output.setResult(MpResult.INVALID_INPUT);
            output.setReason(
                    "Cannot identify rule set for " + GroupUtility.getSiteHistInfo(site1, hist1, beh1, year1)
                            + ". Valid primary site (C000-C999 excluding C809), histology (8000-9999), behavior (0-3, 6) and diagnosis year are required.");
            return output;
        }
        else if (!GroupUtility.validateProperties(site2, hist2, beh2, year2)) {
            output.setResult(MpResult.INVALID_INPUT);
            output.setReason(
                    "Cannot identify rule set for " + GroupUtility.getSiteHistInfo(site2, hist2, beh2, year2)
                            + ".Valid primary site (C000-C999 excluding C809), histology (8000-9999), behavior (0-3, 6) and diagnosis year are required.");
            return output;
        }

        //calculate cancer group based on latest year
        int latestYear = Math.max(year1, year2);
        MphGroup group1 = findCancerGroup(site1, hist1, beh1, latestYear);
        MphGroup group2 = findCancerGroup(site2, hist2, beh2, latestYear);

        //Generic rule, if both groups can not be determined and they have same valid site, hist, behavior, date, laterality return single primary.
        if (group1 == null && group2 == null && GroupUtility.sameAndValidMainFields(input1, input2)) {
            output.setResult(MpResult.SINGLE_PRIMARY);
            output.setReason("The two sets of parameters have same values for site, histology, behavior, diagnosis date and laterality.");
        }
        else if (group1 == null) {
            output.setResult(MpResult.QUESTIONABLE);
            output.setReason("Could not find rule sets for " + GroupUtility.getSiteHistInfo(site1, hist1, beh1, year1) + ".");
        }
        else if (group2 == null) {
            output.setResult(MpResult.QUESTIONABLE);
            output.setReason("Could not find rule sets for " + GroupUtility.getSiteHistInfo(site2, hist2, beh2, year2) + ".");
        }
        else if (!group1.getId().equals(group2.getId())) {
            output.setResult(MpResult.MULTIPLE_PRIMARIES);
            output.setReason("The two sets of parameters belong to two different cancer groups.");
        }
        else {
            TempRuleResult potentialResult = null;
            List<MphRule> rulesAppliedAfterQuestionable = new ArrayList<>();
            for (MphRule rule : group1.getRules()) {
                if (potentialResult == null)
                    output.getAppliedRules().add(rule);
                else
                    rulesAppliedAfterQuestionable.add(rule);
                TempRuleResult result = rule.apply(input1, input2);
                if (result.getPotentialResult() != null) {
                    if (potentialResult == null)
                        potentialResult = result;
                    else if (!result.getPotentialResult().equals(potentialResult.getPotentialResult())) {
                        output.setResult(MpResult.QUESTIONABLE);
                        output.setGroupName(rule.getGroupName());
                        output.setStep(output.getAppliedRules().get(output.getAppliedRules().size() - 1).getStep());
                        output.setReason(potentialResult.getMessage());
                        break;
                    }
                }
                else if (result.getFinalResult() != null) {
                    if (potentialResult == null || potentialResult.getPotentialResult().equals(result.getFinalResult())) {
                        output.setResult(result.getFinalResult());
                        output.setGroupName(rule.getGroupName());
                        output.setStep(rule.getStep());
                        output.setReason(StringUtils.isNotBlank(result.getMessage()) ? result.getMessage() : rule.getReason());
                        if (potentialResult != null && potentialResult.getPotentialResult().equals(result.getFinalResult()))
                            output.getAppliedRules().addAll(rulesAppliedAfterQuestionable);
                    }
                    else {
                        output.setResult(MpResult.QUESTIONABLE);
                        output.setGroupName(rule.getGroupName());
                        output.setStep(output.getAppliedRules().get(output.getAppliedRules().size() - 1).getStep());
                        output.setReason(potentialResult.getMessage());
                    }
                    break;
                }
            }
        }

        return output;
    }

    /**
     * Returns the HematoDB provider that was registered with the instance.
     */
    public HematoDbUtilsProvider getHematoDbUtilsProvider() {
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

        //for (MphGroup group : getAllGroups())
        for (Map.Entry<String, MphGroup> entry : getAllGroups().entrySet())
            if (entry.getValue().isApplicable(primarySite, histology, behavior, year))
                return entry.getValue();

        return null;
    }

    /**
     * Returns a map of all groups of rules used by this instance.
     */
    public Map<String, MphGroup> getAllGroups() {
        return Collections.unmodifiableMap(_groups);
    }
}
