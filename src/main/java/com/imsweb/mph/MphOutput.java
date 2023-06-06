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

    private String _groupId;

    private String _groupName;

    private String _step;

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

    public String getGroupId() {
        return _groupId;
    }

    public void setGroupId(String groupId) {
        _groupId = groupId;
    }

    public String getGroupName() {
        return _groupName;
    }

    public void setGroupName(String groupName) {
        _groupName = groupName;
    }

    public String getStep() {
        return _step;
    }

    public void setStep(String step) {
        _step = step;
    }
}
