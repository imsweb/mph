/*
 * Copyright (C) 2016 Information Management Services, Inc.
 */
package com.imsweb.mph.internal;

public class HematoDTO {

    private Short _startYear;

    private Short _endYear;

    private String _morphology;

    public HematoDTO(Short startYear, Short endYear, String morphology) {
        _startYear = startYear;
        _endYear = endYear;
        _morphology = morphology;
    }

    public boolean matches(String morphology, int year) {
        return year >= _startYear && year <= _endYear && morphology.equals(_morphology);
    }
}
