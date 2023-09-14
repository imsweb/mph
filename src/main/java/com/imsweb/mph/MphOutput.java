/*
 * Copyright (C) 2013 Information Management Services, Inc.
 */
package com.imsweb.mph;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to encapsulate the output for the MP evaluation.
 * </br></br>
 * The most important field is the result, which is an enumeration containing the following possible outcome:
 *  - SINGLE_PRIMARY (the two tumors are the same primary)
 *  - MULTIPLE_PRIMARIES (the two tumors are different primaries)
 *  - QUESTIONABLE (not enough information to make a proper determination)
 *  - INVALID_INPUT (one or more of the required inputs (site, hist, behavior, year) are missing or invalid)
 */
public class MphOutput {

    // the final outcome of the MP evaluation
    private MphUtils.MpResult _result;

    // the reason that goes with the final evaluation (the description of the last applied step (rule) that provided the outcome)
    private String _reason;

    // the applied rules (steps); each rule can be seen as a question, some rule provide a final result while others trigger the next rule to run
    private final List<MphRule> _appliedRules;

    // the ID of set of rule that was applied (rules are encapsulated into groups)
    private String _groupId;

    // the ID of set of rule that was applied (rules are encapsulated into groups)
    private String _groupName;

    // the step (rule) that provided the final outcome
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
