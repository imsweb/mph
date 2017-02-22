/*
 * Copyright (C) 2017 Information Management Services, Inc.
 */
package com.imsweb.mph;

public class MphComputeOptions {

    /**
     * How to consider histology match, if it is strict 8000 is considered a different histology than 8010-9999
     * for rule : Do the tumors have ICD-O-3 histology codes that are different at the first (Xxxx), second (Xxxx), or third (xxXx) number?
     * If lenient mode is on 8000 is considered as NOS and be considered to match any 8nnn histologies.
     */
    public enum MpHistologyMatching {
        STRICT,
        LENIENT
    }

    // Histology matching mode, default mode is strict
    private MpHistologyMatching _histologyMatchingMode;

    public MphComputeOptions() {
        _histologyMatchingMode = MpHistologyMatching.STRICT;
    }

    public MpHistologyMatching getHistologyMatchingMode() {
        return _histologyMatchingMode;
    }

    public void setHistologyMatchingMode(MpHistologyMatching histologyMatchingMode) {
        _histologyMatchingMode = histologyMatchingMode;
    }
}
