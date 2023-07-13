/*
 * Copyright (C) 2014 Information Management Services, Inc.
 */
package com.imsweb.mph.internal;

import com.imsweb.mph.MphUtils;

public class TempRuleResult {

    private MphUtils.MpResult _finalResult;

    private MphUtils.MpResult _potentialResult;

    private String _message;

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

    public void setMessageNotInTable(String step, String groupId, String row1, String row2, String icd1, String icd2) {
        String histologyNotInTable;
        boolean bothNotInTable = false;
        if (row1 == null && row2 == null) {
            bothNotInTable = true;
            histologyNotInTable = "Both " + icd1 + " and " + icd2;
        }
        else
            histologyNotInTable = row1 == null ? icd1 : icd2;
        _message = "Unable to apply Rule " + step + " of " + groupId + ". " + histologyNotInTable + (bothNotInTable ? " are " : " is ") + "not in the table.";
    }
}
