/*
 * Copyright (C) 2013 Information Management Services, Inc.
 */
package com.imsweb.mph;

import java.util.ArrayList;
import java.util.List;

import com.imsweb.mph.internal.TempRuleResult;

public abstract class MphRule {

    private String _groupId;

    private String _step;

    private String _question;

    private String _reason;

    private List<String> _notes;

    private List<String> _examples;

    public MphRule(String groupId, String step) {
        _groupId = groupId;
        _step = step;
        _notes = new ArrayList<>();
        _examples = new ArrayList<>();
    }

    public String getGroupId() {
        return _groupId;
    }

    public String getStep() {
        return _step;
    }

    public String getQuestion() {
        return _question;
    }

    public void setQuestion(String question) {
        _question = question;
    }

    public String getReason() {
        return _reason;
    }

    public void setReason(String reason) {
        _reason = reason;
    }

    public List<String> getNotes() {
        return _notes;
    }

    public List<String> getExamples() {
        return _examples;
    }

    public abstract TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options);
}
