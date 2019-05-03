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

public class Mp1998HematopoieticGroup extends MphGroup {

    private static List<String[]> _1998_HEMATOPOIETIC = new ArrayList<>();

    public Mp1998HematopoieticGroup() {
        super(MphConstants.MP_1998_HEMATO_GROUP_ID, MphConstants.MP_1998_HEMATO_GROUP_NAME, "C000-C809", null, "9590-9989", null, "2-3,6", "0000-2000");
        initializeLookup();

        MphRule rule = new MphRule(MphConstants.MP_1998_HEMATO_GROUP_ID, "M1") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
                TempRuleResult result = new TempRuleResult();
                int laterDx = GroupUtility.compareDxDate(i1, i2);
                if (MphConstants.COMPARE_DX_UNKNOWN == laterDx) {
                    result.setFinalResult(MphUtils.MpResult.QUESTIONABLE);
                    result.setMessageUnknownDiagnosisDate(this.getStep(), this.getGroupId());
                    return result;
                }
                String firstDx = MphConstants.COMPARE_DX_FIRST_LATEST == laterDx ? i2.getHistology() : i1.getHistology(), secondDx =
                        MphConstants.COMPARE_DX_FIRST_LATEST == laterDx ? i1.getHistology() : i2.getHistology();
                for (String[] row : _1998_HEMATOPOIETIC)
                    if ((firstDx.compareTo(row[0]) >= 0 && firstDx.compareTo(row[1]) <= 0 && secondDx.compareTo(row[2]) >= 0 && secondDx.compareTo(row[3]) <= 0) ||
                            (MphConstants.COMPARE_DX_EQUAL == laterDx && (secondDx.compareTo(row[0]) >= 0 && secondDx.compareTo(row[1]) <= 0 && firstDx.compareTo(row[2]) >= 0 && firstDx.compareTo(
                                    row[3]) <= 0))) {
                        result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
                        return result;
                    }

                //if they don't match
                result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        _rules.add(rule);
    }

    private static synchronized void initializeLookup() {
        if (_1998_HEMATOPOIETIC.isEmpty()) {
            try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("Hematopoietic1998HistologyPairs.csv")) {
                _1998_HEMATOPOIETIC.addAll(new CSVReaderBuilder(new InputStreamReader(is, StandardCharsets.US_ASCII)).withSkipLines(1).build().readAll());
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
