/*
 * Copyright (C) 2016 Information Management Services, Inc.
 */
package com.imsweb.mph;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.imsweb.mph.internal.CsvUtils;
import com.imsweb.mph.internal.HematoDTO;

/**
 * This is a default hemato db data provider which uses seer-api to get same primary, transform to or transform from data from
 * hematopoietic and lymphoid neoplasm database.
 */
public class DefaultHematoDataProvider implements HematoDataProvider {

    private final Map<String, List<HematoDTO>> _samePrimaryDto;
    private final Map<String, List<HematoDTO>> _transformToDto;
    private final Map<String, List<HematoDTO>> _transformFromDto;

    public DefaultHematoDataProvider() {
        _samePrimaryDto = CsvUtils.parseHematoCsvFile("Hematopoietic2010SamePrimaryPairs.csv");
        _transformToDto = CsvUtils.parseHematoCsvFile("Hematopoietic2010TransformToPairs.csv");
        _transformFromDto = CsvUtils.parseHematoCsvFile("Hematopoietic2010TransformFromPairs.csv");
    }

    @Override
    public List<HematoDTO> getSamePrimary(String morphology) {
        return _samePrimaryDto.getOrDefault(morphology, Collections.emptyList());
    }

    @Override
    public List<HematoDTO> getTransformTo(String morphology) {
        return _transformToDto.getOrDefault(morphology, Collections.emptyList());
    }

    @Override
    public List<HematoDTO> getTransformFrom(String morphology) {
        return _transformFromDto.getOrDefault(morphology, Collections.emptyList());
    }

    @Override
    public Date getDataLastUpdated() {

        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("hemato_data_info.properties")) {
            if (is == null)
                throw new IllegalStateException("Unable to get info properties");
            Properties prop = new Properties();
            prop.load(is);
            String lastUpdateDate = prop.getProperty("last_updated");
            return new SimpleDateFormat("yyyyMMddHHmm").parse(lastUpdateDate);
        }
        catch (IOException | ParseException e) {
            return null;
        }
    }
}
