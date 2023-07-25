/*
 * Copyright (C) 2016 Information Management Services, Inc.
 */
package com.imsweb.mph;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import com.imsweb.mph.internal.HematoDTO;

/**
 * This is a default hemato db data provider which uses seer-api to get same primary, transform to or transform from data from
 * hematopoietic and lymphoid neoplasm database.
 */
public class DefaultHematoDataProvider implements HematoDataProvider {

    private Map<String, List<HematoDTO>> _samePrimaryDto;
    private Map<String, List<HematoDTO>> _transformToDto;
    private Map<String, List<HematoDTO>> _transformFromDto;

    public DefaultHematoDataProvider() {
        _samePrimaryDto = new HashMap<>();
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("Hematopoietic2010SamePrimaryPairs.csv")) {
            if (is == null)
                throw new IllegalStateException("Unable to get Hematopoietic2010SamePrimaryPairs.csv");
            for (String[] row : new CSVReaderBuilder(new InputStreamReader(is, StandardCharsets.US_ASCII)).withSkipLines(1).build().readAll()) {
                Short validStartYear = StringUtils.isNotBlank(row[1]) ? Short.valueOf(row[1]) : null;
                Short validEndYear = StringUtils.isNotBlank(row[2]) ? Short.valueOf(row[2]) : null;
                Short startYear = StringUtils.isNotBlank(row[3]) ? Short.valueOf(row[3]) : null;
                Short endYear = StringUtils.isNotBlank(row[4]) ? Short.valueOf(row[4]) : null;
                if (_samePrimaryDto.containsKey(row[0]))
                    _samePrimaryDto.get(row[0]).add(new HematoDTO(validStartYear, validEndYear, startYear, endYear, row[5]));
                else {
                    List<HematoDTO> list = new ArrayList<>();
                    list.add(new HematoDTO(validStartYear, validEndYear, startYear, endYear, row[5]));
                    _samePrimaryDto.put(row[0], list);
                }
            }
        }
        catch (CsvException | IOException e) {
            throw new IllegalStateException(e);
        }
        _transformToDto = new HashMap<>();
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("Hematopoietic2010TransformToPairs.csv")) {
            if (is == null)
                throw new IllegalStateException("Unable to get Hematopoietic2010TransformToPairs.csv");
            for (String[] row : new CSVReaderBuilder(new InputStreamReader(is, StandardCharsets.US_ASCII)).withSkipLines(1).build().readAll()) {
                Short validStartYear = StringUtils.isNotBlank(row[1]) ? Short.valueOf(row[1]) : null;
                Short validEndYear = StringUtils.isNotBlank(row[2]) ? Short.valueOf(row[2]) : null;
                Short startYear = StringUtils.isNotBlank(row[3]) ? Short.valueOf(row[3]) : null;
                Short endYear = StringUtils.isNotBlank(row[4]) ? Short.valueOf(row[4]) : null;
                if (_transformToDto.containsKey(row[0]))
                    _transformToDto.get(row[0]).add(new HematoDTO(validStartYear, validEndYear, startYear, endYear, row[5]));
                else {
                    List<HematoDTO> list = new ArrayList<>();
                    list.add(new HematoDTO(validStartYear, validEndYear, startYear, endYear, row[5]));
                    _transformToDto.put(row[0], list);
                }
            }
        }
        catch (CsvException | IOException e) {
            throw new IllegalStateException(e);
        }
        _transformFromDto = new HashMap<>();
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("Hematopoietic2010TransformFromPairs.csv")) {
            if (is == null)
                throw new IllegalStateException("Unable to get Hematopoietic2010TransformFromPairs.csv");
            for (String[] row : new CSVReaderBuilder(new InputStreamReader(is, StandardCharsets.US_ASCII)).withSkipLines(1).build().readAll()) {
                Short validStartYear = StringUtils.isNotBlank(row[1]) ? Short.valueOf(row[1]) : null;
                Short validEndYear = StringUtils.isNotBlank(row[2]) ? Short.valueOf(row[2]) : null;
                Short startYear = StringUtils.isNotBlank(row[3]) ? Short.valueOf(row[3]) : null;
                Short endYear = StringUtils.isNotBlank(row[4]) ? Short.valueOf(row[4]) : null;
                if (_transformFromDto.containsKey(row[0]))
                    _transformFromDto.get(row[0]).add(new HematoDTO(validStartYear, validEndYear, startYear, endYear, row[5]));
                else {
                    List<HematoDTO> list = new ArrayList<>();
                    list.add(new HematoDTO(validStartYear, validEndYear, startYear, endYear, row[5]));
                    _transformFromDto.put(row[0], list);
                }
            }
        }
        catch (CsvException | IOException e) {
            throw new IllegalStateException(e);
        }

    }

    @Override
    public List<HematoDTO> getSamePrimary(String morphology) {
        return _samePrimaryDto.computeIfAbsent(morphology, k -> Collections.emptyList());
    }

    @Override
    public List<HematoDTO> getTransformTo(String morphology) {
        return _transformToDto.computeIfAbsent(morphology, k -> Collections.emptyList());
    }

    @Override
    public List<HematoDTO> getTransformFrom(String morphology) {
        return _transformFromDto.computeIfAbsent(morphology, k -> Collections.emptyList());
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
