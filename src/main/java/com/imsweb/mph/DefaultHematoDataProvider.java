/*
 * Copyright (C) 2016 Information Management Services, Inc.
 */
package com.imsweb.mph;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.NamedCsvRecord;

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

        _samePrimaryDto = new HashMap<>();
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("Hematopoietic2010SamePrimaryPairs.csv")) {
            if (is == null)
                throw new IllegalStateException("Unable to get Hematopoietic2010SamePrimaryPairs.csv");
            try (Reader reader = new InputStreamReader(is, StandardCharsets.US_ASCII); CsvReader<NamedCsvRecord> csvReader = CsvReader.builder().ofNamedCsvRecord(reader)) {
                csvReader.stream().forEach(line -> {
                    Short validStartYear = StringUtils.isNotBlank(line.getField(1)) ? Short.valueOf(line.getField(1)) : null;
                    Short validEndYear = StringUtils.isNotBlank(line.getField(2)) ? Short.valueOf(line.getField(2)) : null;
                    Short startYear = StringUtils.isNotBlank(line.getField(3)) ? Short.valueOf(line.getField(3)) : null;
                    Short endYear = StringUtils.isNotBlank(line.getField(4)) ? Short.valueOf(line.getField(4)) : null;
                    if (_samePrimaryDto.containsKey(line.getField(0)))
                        _samePrimaryDto.get(line.getField(0)).add(new HematoDTO(validStartYear, validEndYear, startYear, endYear, line.getField(5)));
                    else {
                        List<HematoDTO> list = new ArrayList<>();
                        list.add(new HematoDTO(validStartYear, validEndYear, startYear, endYear, line.getField(5)));
                        _samePrimaryDto.put(line.getField(0), list);
                    }
                });
            }
        }
        catch (IOException e) {
            throw new IllegalStateException(e);
        }

        _transformToDto = new HashMap<>();
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("Hematopoietic2010TransformToPairs.csv")) {
            if (is == null)
                throw new IllegalStateException("Unable to get Hematopoietic2010TransformToPairs.csv");
            try (Reader reader = new InputStreamReader(is, StandardCharsets.US_ASCII); CsvReader<NamedCsvRecord> csvReader = CsvReader.builder().ofNamedCsvRecord(reader)) {
                csvReader.stream().forEach(line -> {
                    Short validStartYear = StringUtils.isNotBlank(line.getField(1)) ? Short.valueOf(line.getField(1)) : null;
                    Short validEndYear = StringUtils.isNotBlank(line.getField(2)) ? Short.valueOf(line.getField(2)) : null;
                    Short startYear = StringUtils.isNotBlank(line.getField(3)) ? Short.valueOf(line.getField(3)) : null;
                    Short endYear = StringUtils.isNotBlank(line.getField(4)) ? Short.valueOf(line.getField(4)) : null;
                    if (_transformToDto.containsKey(line.getField(0)))
                        _transformToDto.get(line.getField(0)).add(new HematoDTO(validStartYear, validEndYear, startYear, endYear, line.getField(5)));
                    else {
                        List<HematoDTO> list = new ArrayList<>();
                        list.add(new HematoDTO(validStartYear, validEndYear, startYear, endYear, line.getField(5)));
                        _transformToDto.put(line.getField(0), list);
                    }
                });
            }
        }
        catch (IOException e) {
            throw new IllegalStateException(e);
        }

        _transformFromDto = new HashMap<>();
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("Hematopoietic2010TransformFromPairs.csv")) {
            if (is == null)
                throw new IllegalStateException("Unable to get Hematopoietic2010TransformFromPairs.csv");
            try (Reader reader = new InputStreamReader(is, StandardCharsets.US_ASCII); CsvReader<NamedCsvRecord> csvReader = CsvReader.builder().ofNamedCsvRecord(reader)) {
                csvReader.stream().forEach(line -> {
                    Short validStartYear = StringUtils.isNotBlank(line.getField(1)) ? Short.valueOf(line.getField(1)) : null;
                    Short validEndYear = StringUtils.isNotBlank(line.getField(2)) ? Short.valueOf(line.getField(2)) : null;
                    Short startYear = StringUtils.isNotBlank(line.getField(3)) ? Short.valueOf(line.getField(3)) : null;
                    Short endYear = StringUtils.isNotBlank(line.getField(4)) ? Short.valueOf(line.getField(4)) : null;
                    if (_transformFromDto.containsKey(line.getField(0)))
                        _transformFromDto.get(line.getField(0)).add(new HematoDTO(validStartYear, validEndYear, startYear, endYear, line.getField(5)));
                    else {
                        List<HematoDTO> list = new ArrayList<>();
                        list.add(new HematoDTO(validStartYear, validEndYear, startYear, endYear, line.getField(5)));
                        _transformFromDto.put(line.getField(0), list);
                    }
                });
            }
        }
        catch (IOException e) {
            throw new IllegalStateException(e);
        }

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
