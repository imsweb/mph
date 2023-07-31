/*
 * Copyright (C) 2016 Information Management Services, Inc.
 */
package com.imsweb.mph.internal;

import java.util.Objects;

public class HematoDTO {

    private Short _validStartYear;

    private Short _validEndYear;

    private Short _startYear;

    private Short _endYear;

    private String _morphology;

    public HematoDTO(Short validStartYear, Short validEndYear, Short startYear, Short endYear, String morphology) {
        _validStartYear = validStartYear;
        _validEndYear = validEndYear;
        _startYear = startYear;
        _endYear = endYear;
        _morphology = morphology;
    }

    public boolean matches(String morphology, int year) {
        Short startYear = _startYear;
        if (startYear == null)
            startYear = _validStartYear != null ? _validStartYear : 0;
        Short endYear = _endYear;
        if (endYear == null)
            endYear = _validEndYear != null ? _validEndYear : 9999;

        return year >= startYear && year <= endYear && morphology.equals(_morphology);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HematoDTO)) return false;
        HematoDTO hematoDTO = (HematoDTO)o;
        return Objects.equals(_validStartYear, hematoDTO._validStartYear) && Objects.equals(_validEndYear, hematoDTO._validEndYear) && Objects.equals(_startYear,
                hematoDTO._startYear) && Objects.equals(_endYear, hematoDTO._endYear) && Objects.equals(_morphology, hematoDTO._morphology);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_validStartYear, _validEndYear, _startYear, _endYear, _morphology);
    }
}
