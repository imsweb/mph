/*
 * Copyright (C) 2016 Information Management Services, Inc.
 */
package lab;

import java.io.BufferedWriter;
import java.io.File;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.imsweb.mph.internal.CsvUtils;
import com.imsweb.seerapi.client.NotFoundException;
import com.imsweb.seerapi.client.SeerApi;
import com.imsweb.seerapi.client.disease.Disease;
import com.imsweb.seerapi.client.disease.DiseaseSearchResults;
import com.imsweb.seerapi.client.disease.YearRangeString;

/**
 * To run this lab locally, you will need a ".seerapi" file under your user folder with a line "apikey=XXX" set to your SEER*API key.
 */
public class HematoDataLab {

    @SuppressWarnings("DataFlowIssue")
    public static void main(String[] args) throws Exception {
        File dir = new File(System.getProperty("user.dir") + "/src/main/resources/");

        File hematoDataInfoFile = new File(dir, "hemato_data_info.properties");
        if (!hematoDataInfoFile.exists())
            throw new IllegalStateException("Unable to find embedded properties file!");

        File samePrimaryFile = new File(dir, "Hematopoietic2010SamePrimaryPairs.csv");
        File transformToFile = new File(dir, "Hematopoietic2010TransformToPairs.csv");
        File transformFromFile = new File(dir, "Hematopoietic2010TransformFromPairs.csv");

        try (OutputStream hematoDataInfoOutput = Files.newOutputStream(hematoDataInfoFile.toPath());
             BufferedWriter samePrimaryWriter = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(samePrimaryFile.toPath()), StandardCharsets.UTF_8));
             BufferedWriter transformToWriter = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(transformToFile.toPath()), StandardCharsets.UTF_8));
             BufferedWriter transformFromWriter = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(transformFromFile.toPath()), StandardCharsets.UTF_8))) {

            SeerApi api = new SeerApi.Builder().connect();
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

            // Get the complete values of each of the diseases.
            Map<String, Disease> allFullDiseases = new HashMap<>();
            if (total == allDiseases.size()) {
                for (Disease d : allDiseases) {
                    Disease disease = api.disease().getById("latest", d.getId()).execute().body();
                    allFullDiseases.put(disease.getId(), disease);
                }
            }

            if (!allFullDiseases.isEmpty()) {
                List<String[]> samePrimaryPairs = new ArrayList<>();
                List<String[]> transformTo = new ArrayList<>();
                List<String[]> transformFrom = new ArrayList<>();
                samePrimaryPairs.add(new String[] {"morphology", "valid start year", "valid end year", "start year", "end year", "same primary"});
                transformTo.add(new String[] {"morphology", "valid start year", "valid end year", "start year", "end year", "transform to"});
                transformFrom.add(new String[] {"morphology", "valid start year", "valid end year", "start year", "end year", "transform from"});
                for (Disease disease : allFullDiseases.values()) {
                    String morphology = disease.getIcdO3Morphology();
                    if (disease.getSamePrimaries() != null)
                        for (YearRangeString range : disease.getSamePrimaries()) {
                            String validStartYear = disease.getValid() != null && disease.getValid().getStartYear() != null ? String.valueOf(disease.getValid().getStartYear()) : null;
                            String validEndYear = disease.getValid() != null && disease.getValid().getEndYear() != null ? String.valueOf(disease.getValid().getEndYear()) : null;
                            String startYear = range.getStartYear() != null ? String.valueOf(range.getStartYear()) : null;
                            String endYear = range.getEndYear() != null ? String.valueOf(range.getEndYear()) : null;
                            Disease samePrimary = null;
                            try {
                                samePrimary = allFullDiseases.get(range.getValue());
                            }
                            catch (NotFoundException e) {
                                //If a disease is listed as same primary but not existed, don't add it.
                            }
                            if (samePrimary != null)
                                samePrimaryPairs.add(new String[] {morphology, validStartYear, validEndYear, startYear, endYear, samePrimary.getIcdO3Morphology()});
                        }

                    if (disease.getTransformTo() != null)
                        for (YearRangeString range : disease.getTransformTo()) {
                            String validStartYear = disease.getValid() != null && disease.getValid().getStartYear() != null ? String.valueOf(disease.getValid().getStartYear()) : null;
                            String validEndYear = disease.getValid() != null && disease.getValid().getEndYear() != null ? String.valueOf(disease.getValid().getEndYear()) : null;
                            String startYear = range.getStartYear() != null ? String.valueOf(range.getStartYear()) : null;
                            String endYear = range.getEndYear() != null ? String.valueOf(range.getEndYear()) : null;
                            Disease transformToMorphology = null;
                            try {
                                transformToMorphology = allFullDiseases.get(range.getValue());
                            }
                            catch (NotFoundException e) {
                                //If a disease is listed as transform to but not existed, don't add it.
                            }
                            if (transformToMorphology != null)
                                transformTo.add(new String[] {morphology, validStartYear, validEndYear, startYear, endYear, transformToMorphology.getIcdO3Morphology()});
                        }

                    if (disease.getTransformFrom() != null)
                        for (YearRangeString range : disease.getTransformFrom()) {
                            String validStartYear = disease.getValid() != null && disease.getValid().getStartYear() != null ? String.valueOf(disease.getValid().getStartYear()) : null;
                            String validEndYear = disease.getValid() != null && disease.getValid().getEndYear() != null ? String.valueOf(disease.getValid().getEndYear()) : null;
                            String startYear = range.getStartYear() != null ? String.valueOf(range.getStartYear()) : null;
                            String endYear = range.getEndYear() != null ? String.valueOf(range.getEndYear()) : null;
                            Disease transformFromMorphology = null;
                            try {
                                transformFromMorphology = allFullDiseases.get(range.getValue());
                            }
                            catch (NotFoundException e) {
                                //If a disease is listed as transform from but not existed, don't add it.
                            }
                            if (transformFromMorphology != null)
                                transformFrom.add(new String[] {morphology, validStartYear, validEndYear, startYear, endYear, transformFromMorphology.getIcdO3Morphology()});
                        }
                }

                for (String[] line : samePrimaryPairs) {
                    samePrimaryWriter.write(CsvUtils.writeCsvValues(line));
                    samePrimaryWriter.write("\r\n");
                }
                for (String[] line : transformTo) {
                    transformToWriter.write(CsvUtils.writeCsvValues(line));
                    transformToWriter.write("\r\n");
                }
                for (String[] line : transformFrom) {
                    transformFromWriter.write(CsvUtils.writeCsvValues(line));
                    transformFromWriter.write("\r\n");
                }

                Properties prop = new Properties();
                prop.setProperty("last_updated", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddkkmm")));
                prop.store(hematoDataInfoOutput, null);
            }
            else
                System.out.println("Something wasn't right. The total number of diseases you got is different from what the API returns. Please try again.");
        }
    }
}
