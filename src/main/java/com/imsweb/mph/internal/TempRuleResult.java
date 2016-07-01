/*
 * Copyright (C) 2014 Information Management Services, Inc.
 */
package com.imsweb.mph.internal;

import com.imsweb.mph.MphUtils;

public class TempRuleResult {

    private MphUtils.MpResult _result;

    private String _message;

    public TempRuleResult() {
    }

    public MphUtils.MpResult getResult() {
        return _result;
    }

    public void setResult(MphUtils.MpResult result) {
        _result = result;
    }

    public String getMessage() {
        return _message;
    }

    public void setMessage(String message) {
        _message = message;
    }
}
