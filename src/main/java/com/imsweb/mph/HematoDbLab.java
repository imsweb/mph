/*
 * Copyright (C) 2016 Information Management Services, Inc.
 */
package com.imsweb.mph;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import au.com.bytecode.opencsv.CSVWriter;

import com.imsweb.seerapi.client.NotFoundException;
import com.imsweb.seerapi.client.SeerApi;
import com.imsweb.seerapi.client.disease.Disease;
import com.imsweb.seerapi.client.disease.DiseaseSearchResults;
import com.imsweb.seerapi.client.disease.YearRangeString;

public class HematoDbLab {

    public static void main(String[] args) throws Exception {
        createHematoDbCsvFile();
    }

    private static void createHematoDbCsvFile() throws IOException {
        File csvFile = new File(System.getProperty("user.dir") + "/src/main/resources/Hematopoietic2010MorphologyPairs.csv");
        CSVWriter writer = new CSVWriter(new FileWriter(csvFile));

        String key = "ENTER YOUR KEY HERE";
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
                writer.close();
                return;
            }
            allDiseases.addAll(results.getResults());
            offset += 100;
        } while (allDiseases.size() < total);

        if (total == allDiseases.size()) {
            List<String[]> samePrimaryPairs = new ArrayList<>();
            samePrimaryPairs.add(new String[] {"morphology", "start year", "end year", "same primary"});
            for (Disease d : allDiseases) {
                Disease disease = api.disease().getById("latest", d.getId()).execute().body();
                String morphology = disease.getIcdO3Morphology();
                if (disease.getSamePrimaries() != null)
                    for (YearRangeString range : disease.getSamePrimaries()) {
                        int startYear =
                                range.getStartYear() != null ? range.getStartYear() : (disease.getValid() != null && disease.getValid().getStartYear() != null ? disease.getValid().getStartYear() : 0);
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
            }
            writer.writeAll(samePrimaryPairs);
        }
        else
            System.out.println("Something wasn't right. The total number of diseases you got is different from what the API returns. Please try again.");

        writer.close();
    }
}
