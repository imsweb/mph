/*
 * Copyright (C) 2013 Information Management Services, Inc.
 */
package com.imsweb.mph;

import java.util.ArrayList;
import java.util.List;

public class MphOutput {

    private MphUtils.MpResult _result;

    private String _reason;
    
    private List<MphRule> _appliedRules;

    public MphOutput() {
        _appliedRules = new ArrayList<>();
    }

    public MphUtils.MpResult getResult() {
        return _result;
    }

    public void setResult(MphUtils.MpResult result) {
        _result = result;
    }

    public String getReason() {
        return _reason;
    }

    public void setReason(String reason) {
        _reason = reason;
    }

    public List<MphRule> getAppliedRules() {
        return _appliedRules;
    }
}
