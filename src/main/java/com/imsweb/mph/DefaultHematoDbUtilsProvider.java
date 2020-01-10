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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import com.imsweb.mph.internal.HematoDbDTO;

/**
 * This is a default hemato db utils provider which uses seer-api to determine whether two morphologies are same primary, transform to or transform from according to
 * hematopoietic and lymphoid neoplasm database.
 */
public class DefaultHematoDbUtilsProvider implements HematoDbUtilsProvider {

    private Map<String, List<HematoDbDTO>> _samePrimaryDto;
    private Map<String, List<HematoDbDTO>> _transformToDto;
    private Map<String, List<HematoDbDTO>> _transformFromDto;
    private static Pattern _MORPHOLOGY = Pattern.compile("^(\\d{4}/\\d)");

    public DefaultHematoDbUtilsProvider() {
        _samePrimaryDto = new HashMap<>();
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("Hematopoietic2010SamePrimaryPairs.csv")) {
            if (is == null)
                throw new RuntimeException("Unable to get Hematopoietic2010SamePrimaryPairs.csv");
            for (String[] row : new CSVReaderBuilder(new InputStreamReader(is, StandardCharsets.US_ASCII)).withSkipLines(1).build().readAll()) {
                if (_samePrimaryDto.containsKey(row[0]))
                    _samePrimaryDto.get(row[0]).add(new HematoDbDTO(Short.valueOf(row[1]), Short.valueOf(row[2]), row[3]));
                else {
                    List<HematoDbDTO> list = new ArrayList<>();
                    list.add(new HematoDbDTO(Short.valueOf(row[1]), Short.valueOf(row[2]), row[3]));
                    _samePrimaryDto.put(row[0], list);
                }
            }
        }
        catch (CsvException | IOException e) {
            throw new RuntimeException(e);
        }
        _transformToDto = new HashMap<>();
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("Hematopoietic2010TransformToPairs.csv")) {
            if (is == null)
                throw new RuntimeException("Unable to get Hematopoietic2010TransformToPairs.csv");
            for (String[] row : new CSVReaderBuilder(new InputStreamReader(is, StandardCharsets.US_ASCII)).withSkipLines(1).build().readAll()) {
                if (_transformToDto.containsKey(row[0]))
                    _transformToDto.get(row[0]).add(new HematoDbDTO(Short.valueOf(row[1]), Short.valueOf(row[2]), row[3]));
                else {
                    List<HematoDbDTO> list = new ArrayList<>();
                    list.add(new HematoDbDTO(Short.valueOf(row[1]), Short.valueOf(row[2]), row[3]));
                    _transformToDto.put(row[0], list);
                }
            }
        }
        catch (CsvException | IOException e) {
            throw new RuntimeException(e);
        }
        _transformFromDto = new HashMap<>();
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("Hematopoietic2010TransformFromPairs.csv")) {
            if (is == null)
                throw new RuntimeException("Unable to get Hematopoietic2010TransformFromPairs.csv");
            for (String[] row : new CSVReaderBuilder(new InputStreamReader(is, StandardCharsets.US_ASCII)).withSkipLines(1).build().readAll()) {
                if (_transformFromDto.containsKey(row[0]))
                    _transformFromDto.get(row[0]).add(new HematoDbDTO(Short.valueOf(row[1]), Short.valueOf(row[2]), row[3]));
                else {
                    List<HematoDbDTO> list = new ArrayList<>();
                    list.add(new HematoDbDTO(Short.valueOf(row[1]), Short.valueOf(row[2]), row[3]));
                    _transformFromDto.put(row[0], list);
                }
            }
        }
        catch (CsvException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean isSamePrimary(String leftCode, String rightCode, int leftYear, int rightYear) {
        if (leftCode == null || rightCode == null || !_MORPHOLOGY.matcher(leftCode).matches() || !_MORPHOLOGY.matcher(rightCode).matches())
            return false;
        else if (leftCode.equals(rightCode))
            return true;
        if (_samePrimaryDto.containsKey(leftCode)) {
            for (HematoDbDTO dto : _samePrimaryDto.get(leftCode))
                if (dto.matches(rightCode, leftYear))
                    return true;
        }
        if (_samePrimaryDto.containsKey(rightCode)) {
            for (HematoDbDTO dto : _samePrimaryDto.get(rightCode))
                if (dto.matches(leftCode, rightYear))
                    return true;
        }
        return false;
    }

    @Override
    public boolean canTransformTo(String fromCode, String toCode, int fromYear, int toYear) {
        return confirmTransformTo(fromCode, toCode, fromYear) || confirmTransformFrom(toCode, fromCode, toYear);
    }

    private boolean confirmTransformTo(String leftCode, String rightCode, int year) {
        if (invalidCodes(leftCode, rightCode))
            return false;

        if (_transformToDto.containsKey(leftCode)) {
            for (HematoDbDTO dto : _transformToDto.get(leftCode))
                if (dto.matches(rightCode, year))
                    return true;
        }

        return false;
    }

    private boolean confirmTransformFrom(String leftCode, String rightCode, int year) {
        if (invalidCodes(leftCode, rightCode))
            return false;

        if (_transformFromDto.containsKey(leftCode)) {
            for (HematoDbDTO dto : _transformFromDto.get(leftCode))
                if (dto.matches(rightCode, year))
                    return true;
        }

        return false;
    }

    private boolean invalidCodes(String leftCode, String rightCode) {
        return leftCode == null || rightCode == null || !_MORPHOLOGY.matcher(leftCode).matches() || !_MORPHOLOGY.matcher(rightCode).matches();
    }

    @Override
    public Date getDataLastUpdated() {

        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("hemato_data_info.properties")) {
            if (is == null)
                throw new RuntimeException("Unable to get info properties");
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
