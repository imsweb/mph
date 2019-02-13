/*
 * Copyright (C) 2014 Information Management Services, Inc.
 */
package com.imsweb.mph.internal;

import com.imsweb.mph.MphUtils;

public class TempRuleResult {

    private MphUtils.MpResult _finalResult;

    private MphUtils.MpResult _potentialResult;

    private String _message;

    public TempRuleResult() {
    }

    public MphUtils.MpResult getFinalResult() {
        return _finalResult;
    }

    public void setFinalResult(MphUtils.MpResult finalResult) {
        _finalResult = finalResult;
    }

    public MphUtils.MpResult getPotentialResult() {
        return _potentialResult;
    }

    public void setPotentialResult(MphUtils.MpResult potentialResult) {
        _potentialResult = potentialResult;
    }

    public String getMessage() {
        return _message;
    }

    public void setMessage(String message) {
        _message = message;
    }

    public void setMessageUnknownLatAndDate(String step, String groupId) {
        _message = "Unable to apply Rule " + step + " of " + groupId + ". Valid and known laterality and diagnosis date should be provided.";
    }

    public void setMessageUnknownDiagnosisDate(String step, String groupId) {
        _message = "Unable to apply Rule " + step + " of " + groupId + ". Valid and known diagnosis date should be provided.";
    }

    public void setMessageUnknownLaterality(String step, String groupId) {
        _message = "Unable to apply Rule " + step + " of " + groupId + ". Valid and known laterality should be provided.";
    }
}
