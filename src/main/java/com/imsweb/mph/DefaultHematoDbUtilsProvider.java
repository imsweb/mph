/*
 * Copyright (C) 2016 Information Management Services, Inc.
 */
package com.imsweb.mph;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import au.com.bytecode.opencsv.CSVReader;

import com.imsweb.mph.internal.HematoDbDTO;

/**
 * This is a default hemato db utils provider which uses seer-api to determine whether two morphologies are same primary, transform to or transform from according to
 * hematopoietic and lymphoid neoplasm database.
 */
public class DefaultHematoDbUtilsProvider implements HematoDbUtilsProvider {

    private Map<String, List<HematoDbDTO>> _samePrimaryDto = new HashMap<>();
    private Map<String, List<HematoDbDTO>> _transformToDto = new HashMap<>();
    private Map<String, List<HematoDbDTO>> _transformFromDto = new HashMap<>();
    private static Pattern _MORPHOLOGY = Pattern.compile("^(\\d{4}/\\d)");

    public DefaultHematoDbUtilsProvider() {
        if (_samePrimaryDto.isEmpty()) {
            try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("Hematopoietic2010SamePrimaryPairs.csv")) {
                Reader reader = new InputStreamReader(is, "US-ASCII");
                for (String[] row : new CSVReader(reader, ',', '\"', 1).readAll()) {
                    if (_samePrimaryDto.containsKey(row[0]))
                        _samePrimaryDto.get(row[0]).add(new HematoDbDTO(Short.valueOf(row[1]), Short.valueOf(row[2]), row[3]));
                    else {
                        List<HematoDbDTO> list = new ArrayList<>();
                        list.add(new HematoDbDTO(Short.valueOf(row[1]), Short.valueOf(row[2]), row[3]));
                        _samePrimaryDto.put(row[0], list);
                    }
                }
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (_transformToDto.isEmpty()) {
            try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("Hematopoietic2010TransformToPairs.csv")) {
                Reader reader = new InputStreamReader(is, "US-ASCII");
                for (String[] row : new CSVReader(reader, ',', '\"', 1).readAll()) {
                    if (_transformToDto.containsKey(row[0]))
                        _transformToDto.get(row[0]).add(new HematoDbDTO(Short.valueOf(row[1]), Short.valueOf(row[2]), row[3]));
                    else {
                        List<HematoDbDTO> list = new ArrayList<>();
                        list.add(new HematoDbDTO(Short.valueOf(row[1]), Short.valueOf(row[2]), row[3]));
                        _transformToDto.put(row[0], list);
                    }
                }
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (_transformFromDto.isEmpty()) {
            try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("Hematopoietic2010TransformFromPairs.csv")) {
                Reader reader = new InputStreamReader(is, "US-ASCII");
                for (String[] row : new CSVReader(reader, ',', '\"', 1).readAll()) {
                    if (_transformFromDto.containsKey(row[0]))
                        _transformFromDto.get(row[0]).add(new HematoDbDTO(Short.valueOf(row[1]), Short.valueOf(row[2]), row[3]));
                    else {
                        List<HematoDbDTO> list = new ArrayList<>();
                        list.add(new HematoDbDTO(Short.valueOf(row[1]), Short.valueOf(row[2]), row[3]));
                        _transformFromDto.put(row[0], list);
                    }
                }
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean isSamePrimary(String leftCode, String rightCode, int year) {
        if (leftCode == null || rightCode == null || !_MORPHOLOGY.matcher(leftCode).matches() || !_MORPHOLOGY.matcher(rightCode).matches())
            return false;
        else if (leftCode.equals(rightCode))
            return true;
        else if (_samePrimaryDto.containsKey(leftCode)) {
            for (HematoDbDTO dto : _samePrimaryDto.get(leftCode))
                if (dto.matches(rightCode, year))
                    return true;
        }
        else if (_samePrimaryDto.containsKey(rightCode)) {
            for (HematoDbDTO dto : _samePrimaryDto.get(rightCode))
                if (dto.matches(leftCode, year))
                    return true;
        }
        return false;
    }

    @Override
    public boolean isAcuteTransformation(String leftCode, String rightCode, int year) {
        if (leftCode == null || rightCode == null || !_MORPHOLOGY.matcher(leftCode).matches() || !_MORPHOLOGY.matcher(rightCode).matches())
            return false;
        else if (_transformToDto.containsKey(leftCode)) {
            for (HematoDbDTO dto : _transformToDto.get(leftCode))
                if (dto.matches(rightCode, year))
                    return true;
        }
        return false;
    }

    @Override
    public boolean isChronicTransformation(String leftCode, String rightCode, int year) {
        if (leftCode == null || rightCode == null || !_MORPHOLOGY.matcher(leftCode).matches() || !_MORPHOLOGY.matcher(rightCode).matches())
            return false;
        else if (_transformFromDto.containsKey(leftCode)) {
            for (HematoDbDTO dto : _transformFromDto.get(leftCode))
                if (dto.matches(rightCode, year))
                    return true;
        }
        return false;
    }
}
