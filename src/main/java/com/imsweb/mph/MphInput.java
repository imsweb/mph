/*
 * Copyright (C) 2014 Information Management Services, Inc.
 */
package com.imsweb.mph;

import org.apache.commons.lang3.math.NumberUtils;

import com.imsweb.mph.mpgroups.GroupUtility;

public class MphInput {

    private String _primarySite;

    private String _histologyIcdO3;

    private String _histologyIcdO2;

    private String _behaviorIcdO3;

    private String _behaviorIcdO2;

    private String _laterality;

    private String _dateOfDiagnosisYear;

    private String _dateOfDiagnosisMonth;

    private String _dateOfDiagnosisDay;

    private String _txStatus;

    private boolean _dco;

    private String _sequence;

    public String getPrimarySite() {
        return _primarySite;
    }

    public void setPrimarySite(String primarySite) {
        _primarySite = primarySite;
    }


    public void setHistologyIcdO3(String histologyIcdO3) {
        _histologyIcdO3 = histologyIcdO3;
    }

    public void setBehaviorIcdO3(String behaviorIcdO3) {
        _behaviorIcdO3 = behaviorIcdO3;
    }

    public void setHistologyIcdO2(String histologyIcdO2) {
        _histologyIcdO2 = histologyIcdO2;
    }

    public void setBehaviorIcdO2(String behaviorIcdO2) {
        _behaviorIcdO2 = behaviorIcdO2;
    }

    public String getHistology() {
        int year = NumberUtils.isDigits(_dateOfDiagnosisYear) ? Integer.parseInt(_dateOfDiagnosisYear) : 9999;
        return year < 2001 && GroupUtility.validateHistology(_histologyIcdO2) ? _histologyIcdO2 : _histologyIcdO3;
    }

    public String getBehavior() {
        int year = NumberUtils.isDigits(_dateOfDiagnosisYear) ? Integer.parseInt(_dateOfDiagnosisYear) : 9999;
        return year < 2001 && GroupUtility.validateBehavior(_behaviorIcdO2) ? _behaviorIcdO2 : _behaviorIcdO3;
    }

    public String getLaterality() {
        return _laterality;
    }

    public void setLaterality(String laterality) {
        _laterality = laterality;
    }

    public String getDateOfDiagnosisYear() {
        return _dateOfDiagnosisYear;
    }

    public void setDateOfDiagnosisYear(String dateOfDiagnosisYear) {
        _dateOfDiagnosisYear = dateOfDiagnosisYear;
    }

    public String getDateOfDiagnosisMonth() {
        return _dateOfDiagnosisMonth;
    }

    public void setDateOfDiagnosisMonth(String dateOfDiagnosisMonth) {
        _dateOfDiagnosisMonth = dateOfDiagnosisMonth;
    }

    public String getDateOfDiagnosisDay() {
        return _dateOfDiagnosisDay;
    }

    public void setDateOfDiagnosisDay(String dateOfDiagnosisDay) {
        _dateOfDiagnosisDay = dateOfDiagnosisDay;
    }

    public String getTxStatus() {
        return _txStatus;
    }

    public void setTxStatus(String txStatus) {
        _txStatus = txStatus;
    }

    public String getCtc() {
        return _sequence + (_dco ? "(DCO)" : "");
    }

    public void setDco(boolean dco) {
        _dco = dco;
    }

    public void setSequence(String sequence) {
        _sequence = sequence;
    }
}
