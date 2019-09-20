/*
 * Copyright (C) 2017 Information Management Services, Inc.
 */
package com.imsweb.mph;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import com.imsweb.mph.MphUtils.MpResult;

public class VprTest {

    public static void main(String[] args) throws Exception {

        MphUtils.initialize(new DefaultHematoDbUtilsProvider());
        MphUtils utils = MphUtils.getInstance();
        File inputFile = new File("C:\\Users\\bekeles\\Documents\\vpr\\ny_nj_mph_input.csv");
        File ctDataFile = new File("C:\\Users\\bekeles\\Documents\\vpr\\CT.VPRDeDup.20180531.txt");
        File iaDataFile = new File("C:\\Users\\bekeles\\Documents\\vpr\\IA.VPRDeDup.20180613.txt");
        File gaDataFile = new File("C:\\Users\\bekeles\\Documents\\vpr\\GA.VPRDeDup.20180607a.txt");
        File laDataFile = new File("C:\\Users\\bekeles\\Documents\\vpr\\LA.VPRDeDup.20180530.txt");
        File njDataFile = new File("C:\\Users\\bekeles\\Documents\\vpr\\NJ.VPRDeDup.20180604.txt");
        File nyDataFile = new File("C:\\Users\\bekeles\\Documents\\vpr\\NY.VPRDeDup.20180601.txt");
        Map<String, List<MphInput>> allBaseCtcs = new HashMap<>();
        Map<String, List<MphInput>> allMatchCtcs = new HashMap<>();

        LineNumberReader baseReader = new LineNumberReader(new InputStreamReader(new FileInputStream(nyDataFile)));
        String baseLine = baseReader.readLine();
        while (baseLine != null) {
            String patId = baseLine.substring(41, 49);
            MphInput input = new MphInput();
            input.setPrimarySite(baseLine.substring(539, 543));
            input.setHistologyIcdO3(baseLine.substring(549, 553));
            input.setBehaviorIcdO3(baseLine.substring(553, 554));
            input.setLaterality(baseLine.substring(543, 544));
            input.setDateOfDiagnosisYear(baseLine.substring(529, 533));
            input.setDateOfDiagnosisMonth(baseLine.substring(533, 535));
            input.setDateOfDiagnosisDay(baseLine.substring(535, 537));
            input.setTxStatus(baseLine.substring(1565, 1566));
            input.setSequence(baseLine.substring(527, 529));
            input.setDco("7".equals(baseLine.substring(562, 563)));
            allBaseCtcs.computeIfAbsent(patId, k -> new ArrayList<>()).add(input);
            baseLine = baseReader.readLine();
        }
        baseReader.close();

        LineNumberReader matchReader = new LineNumberReader(new InputStreamReader(new FileInputStream(njDataFile)));
        String matchLine = matchReader.readLine();
        while (matchLine != null) {
            String patId = matchLine.substring(41, 49);
            MphInput input = new MphInput();
            input.setPrimarySite(matchLine.substring(539, 543));
            input.setHistologyIcdO3(matchLine.substring(549, 553));
            input.setBehaviorIcdO3(matchLine.substring(553, 554));
            input.setLaterality(matchLine.substring(543, 544));
            input.setDateOfDiagnosisYear(matchLine.substring(529, 533));
            input.setDateOfDiagnosisMonth(matchLine.substring(533, 535));
            input.setDateOfDiagnosisDay(matchLine.substring(535, 537));
            input.setTxStatus(matchLine.substring(1565, 1566));
            input.setSequence(matchLine.substring(527, 529));
            input.setDco("7".equals(matchLine.substring(562, 563)));
            allMatchCtcs.computeIfAbsent(patId, k -> new ArrayList<>()).add(input);
            matchLine = matchReader.readLine();
        }
        matchReader.close();

        File outputFile = new File("C:\\Users\\bekeles\\Documents\\vpr\\mph\\ny_nj_mph.txt");
        FileWriter writer = new FileWriter(outputFile);

        CSVReader reader = new CSVReaderBuilder(new InputStreamReader(new FileInputStream(inputFile))).withSkipLines(1).build();

        String[] line = reader.readNext();
        while (line != null) {
            if (line.length != 4) {
                System.out.println(reader.getLinesRead() + " has invalid line");
                return;
            }

            List<MphInput> baseCtcs = allBaseCtcs.get(line[1]);
            List<MphInput> matchCtcs = allMatchCtcs.get(line[3]);

            if (baseCtcs == null || baseCtcs.isEmpty()) {
                System.out.println(line[1] + " could not be found (base)");
                return;
            }

            if (matchCtcs == null || matchCtcs.isEmpty()) {
                System.out.println(line[3] + " could not be found (match)");
                return;
            }

            for (MphInput baseInput : baseCtcs) {
                for (MphInput matchInput : matchCtcs) {
                    String res = "";
                    MpResult result = utils.computePrimaries(baseInput, matchInput).getResult();
                    if (MpResult.SINGLE_PRIMARY.equals(result))
                        res = "Match (Strict)";
                    else {
                        MphComputeOptions options = new MphComputeOptions();
                        options.setHistologyMatchingMode(MphComputeOptions.MpHistologyMatching.LENIENT);
                        result = utils.computePrimaries(baseInput, matchInput, options).getResult();
                        if (MpResult.SINGLE_PRIMARY.equals(result))
                            res = "Match (Lenient)";
                        else if (MpResult.MULTIPLE_PRIMARIES.equals(result))
                            res = "No Match";
                        else if (MpResult.QUESTIONABLE.equals(result))
                            res = "Questionable";
                        else if (MpResult.INVALID_INPUT.equals(result))
                            res = "Invalid Input";
                    }
                    writer.write(line[0] + "|" + line[1] + "|" + line[2] + "|" + line[3] + "|" + res + "|" + mph(baseInput) + "|" + mph(matchInput) + "\n");
                }
            }
            line = reader.readNext();
        }

        reader.close();
        writer.close();

        System.out.println(reader.getLinesRead() + " lines processed");
    }

    private static String mph(MphInput i) {
        return i.getCtc() + ", " + i.getPrimarySite() + "/" + i.getLaterality() + ", " + i.getHistology() + "/" + i.getBehavior() + ", " + i.getDateOfDiagnosisYear() + "/" + i
                .getDateOfDiagnosisMonth() + "/" + i.getDateOfDiagnosisDay();
    }

}
