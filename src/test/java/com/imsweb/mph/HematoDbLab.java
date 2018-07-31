/*
 * Copyright (C) 2016 Information Management Services, Inc.
 */
package com.imsweb.mph;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVWriter;

import com.imsweb.seerapi.client.NotFoundException;
import com.imsweb.seerapi.client.SeerApi;
import com.imsweb.seerapi.client.disease.Disease;
import com.imsweb.seerapi.client.disease.DiseaseSearchResults;
import com.imsweb.seerapi.client.disease.YearRangeString;

public class HematoDbLab {

    public static void main(String[] args) throws Exception {
        createHematoDbCsvFiles();
    }

    @SuppressWarnings("ConstantConditions")
    private static void createHematoDbCsvFiles() throws IOException {
        File samePrimaryFile = new File(System.getProperty("user.dir") + "/src/main/resources/Hematopoietic2010SamePrimaryPairs.csv");
        File transformToFile = new File(System.getProperty("user.dir") + "/src/main/resources/Hematopoietic2010TransformToPairs.csv");
        File transformFromFile = new File(System.getProperty("user.dir") + "/src/main/resources/Hematopoietic2010TransformFromPairs.csv");

        try (CSVWriter samePrimaryWriter = new CSVWriter(new OutputStreamWriter(new FileOutputStream(samePrimaryFile), StandardCharsets.UTF_8));
             CSVWriter transformToWriter = new CSVWriter(new OutputStreamWriter(new FileOutputStream(transformToFile), StandardCharsets.UTF_8));
             CSVWriter transformFromWriter = new CSVWriter(new OutputStreamWriter(new FileOutputStream(transformFromFile), StandardCharsets.UTF_8))) {

            String key = "ENTER YOUR API KEY HERE";
            SeerApi api = new SeerApi.Builder().apiKey(key).connect();
            List<Disease> allDiseases = new ArrayList<>();
            int total, previousTotal = 0, offset = 0;
            do {
                Map<String, String> props = new HashMap<>();
                props.put("count", "100");
                props.put("offset", String.valueOf(offset));
                DiseaseSearchResults results = api.disease().search("latest", props).execute().body();
                total = results.getTotal();
                if (previousTotal == 0)
                    previousTotal = total;
                else if (previousTotal != total) {
                    System.out.println("Disease may be added or removed from the data base while this code is running. Please try again.");
                    samePrimaryWriter.close();
                    transformToWriter.close();
                    transformFromWriter.close();
                    return;
                }
                allDiseases.addAll(results.getResults());
                offset += 100;
            } while (allDiseases.size() < total);

            if (total == allDiseases.size()) {
                List<String[]> samePrimaryPairs = new ArrayList<>(), transformTo = new ArrayList<>(), transformFrom = new ArrayList<>();
                samePrimaryPairs.add(new String[] {"morphology", "start year", "end year", "same primary"});
                transformTo.add(new String[] {"morphology", "start year", "end year", "transform to"});
                transformFrom.add(new String[] {"morphology", "start year", "end year", "transform from"});
                for (Disease d : allDiseases) {
                    Disease disease = api.disease().getById("latest", d.getId()).execute().body();
                    String morphology = disease.getIcdO3Morphology();
                    if (disease.getSamePrimaries() != null)
                        for (YearRangeString range : disease.getSamePrimaries()) {
                            int startYear =
                                    range.getStartYear() != null ? range.getStartYear() : (
                                            disease.getValid() != null && disease.getValid().getStartYear() != null ? disease.getValid().getStartYear() : 0);
                            int endYear =
                                    range.getEndYear() != null ? range.getEndYear() : (disease.getValid() != null && disease.getValid().getEndYear() != null ? disease.getValid().getEndYear() : 9999);
                            Disease samePrimary = null;
                            try {
                                samePrimary = api.disease().getById("latest", range.getValue()).execute().body();
                            }
                            catch (NotFoundException e) {
                                //If a disease is listed as same primary but not existed, don't add it.
                            }
                            if (samePrimary != null)
                                samePrimaryPairs.add(new String[] {morphology, String.valueOf(startYear), String.valueOf(endYear), samePrimary.getIcdO3Morphology()});
                        }

                    if (disease.getTransformTo() != null)
                        for (YearRangeString range : disease.getTransformTo()) {
                            int startYear =
                                    range.getStartYear() != null ? range.getStartYear() : (
                                            disease.getValid() != null && disease.getValid().getStartYear() != null ? disease.getValid().getStartYear() : 0);
                            int endYear =
                                    range.getEndYear() != null ? range.getEndYear() : (disease.getValid() != null && disease.getValid().getEndYear() != null ? disease.getValid().getEndYear() : 9999);
                            Disease transformToMorphology = null;
                            try {
                                transformToMorphology = api.disease().getById("latest", range.getValue()).execute().body();
                            }
                            catch (NotFoundException e) {
                                //If a disease is listed as transform to but not existed, don't add it.
                            }
                            if (transformToMorphology != null)
                                transformTo.add(new String[] {morphology, String.valueOf(startYear), String.valueOf(endYear), transformToMorphology.getIcdO3Morphology()});
                        }

                    if (disease.getTransformFrom() != null)
                        for (YearRangeString range : disease.getTransformFrom()) {
                            int startYear =
                                    range.getStartYear() != null ? range.getStartYear() : (
                                            disease.getValid() != null && disease.getValid().getStartYear() != null ? disease.getValid().getStartYear() : 0);
                            int endYear =
                                    range.getEndYear() != null ? range.getEndYear() : (disease.getValid() != null && disease.getValid().getEndYear() != null ? disease.getValid().getEndYear() : 9999);
                            Disease transformFromMorphology = null;
                            try {
                                transformFromMorphology = api.disease().getById("latest", range.getValue()).execute().body();
                            }
                            catch (NotFoundException e) {
                                //If a disease is listed as transform from but not existed, don't add it.
                            }
                            if (transformFromMorphology != null)
                                transformFrom.add(new String[] {morphology, String.valueOf(startYear), String.valueOf(endYear), transformFromMorphology.getIcdO3Morphology()});
                        }
                }
                samePrimaryWriter.writeAll(samePrimaryPairs);
                transformToWriter.writeAll(transformTo);
                transformFromWriter.writeAll(transformFrom);
            }
            else
                System.out.println("Something wasn't right. The total number of diseases you got is different from what the API returns. Please try again.");
        }
    }
}
