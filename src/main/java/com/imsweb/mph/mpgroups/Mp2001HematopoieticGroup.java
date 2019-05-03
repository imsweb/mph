/*
 * Copyright (C) 2016 Information Management Services, Inc.
 */
package com.imsweb.mph.mpgroups;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReaderBuilder;

import com.imsweb.mph.MphComputeOptions;
import com.imsweb.mph.MphConstants;
import com.imsweb.mph.MphGroup;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphUtils;
import com.imsweb.mph.internal.TempRuleResult;

public class Mp2001HematopoieticGroup extends MphGroup {

    private static List<String[]> _2001_HEMATOPOIETIC_GROUPS = new ArrayList<>();
    private static List<String[]> _2001_HEMATOPOIETIC_GROUP_PAIRS = new ArrayList<>();

    public Mp2001HematopoieticGroup() {
        super(MphConstants.MP_2001_HEMATO_GROUP_ID, MphConstants.MP_2001_HEMATO_GROUP_NAME, "C000-C809", null, "9590-9989", null, "2-3,6", "2001-2009");
        initializeLookups();

        MphRule rule = new MphRule(MphConstants.MP_2001_HEMATO_GROUP_ID, "M1") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                String hist1 = i1.getHistology(), hist2 = i2.getHistology();
                //find the group for both histologies
                String group1 = null, group2 = null;
                for (String[] row : _2001_HEMATOPOIETIC_GROUPS) {
                    if (group1 == null && hist1.compareTo(row[1]) >= 0 && hist1.compareTo(row[2]) <= 0)
                        group1 = row[0];
                    if (group2 == null && hist2.compareTo(row[1]) >= 0 && hist2.compareTo(row[2]) <= 0)
                        group2 = row[0];
                    if (group1 != null && group2 != null)
                        break;
                }
                //If we found both groups, let's check if they are same primaries
                if (group1 != null && group2 != null) {
                    int laterDx = GroupUtility.compareDxDate(i1, i2);
                    if (MphConstants.COMPARE_DX_UNKNOWN == laterDx) {
                        result.setFinalResult(MphUtils.MpResult.QUESTIONABLE);
                        result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupId());
                        return result;
                    }
                    String firstDx = MphConstants.COMPARE_DX_FIRST_LATEST == laterDx ? group2 : group1, secondDx = MphConstants.COMPARE_DX_FIRST_LATEST == laterDx ? group1 : group2;
                    for (String[] row : _2001_HEMATOPOIETIC_GROUP_PAIRS)
                        if ((firstDx.equals(row[0]) && secondDx.equals(row[1])) || (MphConstants.COMPARE_DX_EQUAL == laterDx && secondDx.equals(row[0]) && firstDx.equals(row[1]))) {
                            result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                            return result;
                        }
                }
                result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        _rules.add(rule);
    }

    private static synchronized void initializeLookups() {
        if (_2001_HEMATOPOIETIC_GROUPS.isEmpty() || _2001_HEMATOPOIETIC_GROUP_PAIRS.isEmpty()) {
            try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("Hematopoietic2001HistologyGroups.csv")) {
                _2001_HEMATOPOIETIC_GROUPS.addAll(new CSVReaderBuilder(new InputStreamReader(is, StandardCharsets.US_ASCII)).withSkipLines(1).build().readAll());
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
            try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("Hematopoietic2001HistologyGroupPairs.csv")) {
                _2001_HEMATOPOIETIC_GROUP_PAIRS.addAll(new CSVReaderBuilder(new InputStreamReader(is, StandardCharsets.US_ASCII)).withSkipLines(1).build().readAll());
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
