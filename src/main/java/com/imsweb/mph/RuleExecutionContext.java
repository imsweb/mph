/*
 * Copyright (C) 2023 Information Management Services, Inc.
 */
package com.imsweb.mph;

public class RuleExecutionContext {

    private final MphUtils _mphUtils;

    public RuleExecutionContext(MphUtils mphUtils) {
        _mphUtils = mphUtils;
    }

    public boolean isHematoSamePrimary(String morph1, String morph2, int year1, int year2) {
        return _mphUtils.isHematoSamePrimary(morph1, morph2, year1, year2);
    }

    public boolean isTransformation(String leftCode, String rightCode, int leftYear, int rightYear) {
        return _mphUtils.isTransformation(leftCode, rightCode, leftYear, rightYear);
    }

    public boolean isChronicToAcuteTransformation(String earlierMorph, String laterMorph, int earlierYear, int laterYear) {
        return _mphUtils.isChronicToAcuteTransformation(earlierMorph, laterMorph, earlierYear, laterYear);
    }

    //when a neoplasm is originally diagnosed as acute AND reverts to a chronic neoplasm
    public boolean isAcuteToChronicTransformation(String earlierMorph, String laterMorph, int earlierYear, int laterYear) {
        return _mphUtils.isAcuteToChronicTransformation(earlierMorph, laterMorph, earlierYear, laterYear);
    }

    public MphUtils getMphUtils() {
        return _mphUtils;
    }
}
