/*
 * Copyright (C) 2014 Information Management Services, Inc.
 */
package com.imsweb.mph;

import com.imsweb.mph.MphUtils.RuleResult;

public class MphRuleResult {

    private RuleResult _result;

    private String _message;

    public MphRuleResult() {
    }

    public RuleResult getResult() {
        return _result;
    }

    public void setResult(RuleResult result) {
        _result = result;
    }

    public String getMessage() {
        return _message;
    }

    public void setMessage(String message) {
        _message = message;
    }
}
