/*
 * Copyright (C) 2023 Information Management Services, Inc.
 */
package lab;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import com.imsweb.datagenerator.naaccr.NaaccrDataGeneratorOptions;
import com.imsweb.datagenerator.naaccr.NaaccrXmlDataGenerator;
import com.imsweb.layout.LayoutFactory;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphOutput;
import com.imsweb.mph.MphUtils;
import com.imsweb.mph.MphUtils.MpResult;
import com.imsweb.naaccrxml.entity.Tumor;

/**
 * This class can be used to generate CSV files with fake data, along with the result of the library.
 * <br/><br/>
 * The class uses another library (data-generator) to create the data. The simplest way to run it
 * is to clone the project from GitHub and run it within your preferred IDE.
 */
public class TestingDataCreation {

    public static void main(String[] args) throws IOException {

        // global parameters
        int numTests = 500;
        int minDxYear = 2010;
        int maxDxYear = 2020;

        // the fake data generator
        NaaccrXmlDataGenerator generator = new NaaccrXmlDataGenerator(LayoutFactory.getNaaccrXmlLayout(LayoutFactory.LAYOUT_ID_NAACCR_XML_23));

        // the options for the generator
        NaaccrDataGeneratorOptions options = new NaaccrDataGeneratorOptions();
        options.setMinDxYear(minDxYear);
        options.setMaxDxYear(maxDxYear);

        // by default, the testing file will be created in the "build" folder of the project
        File targetFolder = new File(System.getProperty("user.dir") + "/build");
        if (!targetFolder.exists() && !targetFolder.mkdir())
            throw new IOException("Unable to create target folder");
        File targetFile = new File(targetFolder, "mph-testing-" + minDxYear + "-" + maxDxYear + ".csv.gz");

        // execute the run
        int numTestsCrated = 0;
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new GZIPOutputStream(Files.newOutputStream(targetFile.toPath())), StandardCharsets.US_ASCII))) {
            writer.write("year1,month1,day1,site1,hist1,beh1,lat1,year2,month2,day2,site2,hist2,beh2,lat2,result,reason\n");
            while (numTestsCrated < numTests) {
                Tumor tumor1 = generator.generatePatient(1).getTumor(0);
                Tumor tumor2 = generator.generatePatient(1).getTumor(0);

                MphInput input1 = new MphInput();
                String dxDate1 = tumor1.getItemValue("dateOfDiagnosis");
                input1.setDateOfDiagnosisYear(dxDate1.substring(0, 4));
                input1.setDateOfDiagnosisMonth(dxDate1.substring(4, 6));
                input1.setDateOfDiagnosisDay(dxDate1.substring(6, 8));
                input1.setPrimarySite(tumor1.getItemValue("primarySite"));
                input1.setHistologyIcdO3(tumor1.getItemValue("histologicTypeIcdO3"));
                input1.setBehaviorIcdO3(tumor1.getItemValue("behaviorCodeIcdO3"));
                input1.setLaterality(tumor1.getItemValue("laterality"));

                MphInput input2 = new MphInput();
                String dxDate2 = tumor2.getItemValue("dateOfDiagnosis");
                input2.setDateOfDiagnosisYear(dxDate2.substring(0, 4));
                input2.setDateOfDiagnosisMonth(dxDate2.substring(4, 6));
                input2.setDateOfDiagnosisDay(dxDate2.substring(6, 8));
                input2.setPrimarySite(tumor2.getItemValue("primarySite"));
                input2.setHistologyIcdO3(String.valueOf(tumor2.getItemValue("histologicTypeIcdO3")));
                input2.setBehaviorIcdO3(String.valueOf(tumor2.getItemValue("behaviorCodeIcdO3")));
                input2.setLaterality(String.valueOf(tumor2.getItemValue("laterality")));

                MphOutput output = MphUtils.getInstance().computePrimaries(input1, input2);

                // this is a bit tricky, but since we use fake data, most of the inputs will return a multiple-primaries result with a reason
                // that the two tumors are in different groups; those cases are not very interesting to test, and so we filter them out...
                if (output.getResult() != MpResult.INVALID_INPUT && !output.getReason().equals("The two sets of parameters belong to two different cancer groups.")) {

                    List<String> row = new ArrayList<>();
                    row.add(input1.getDateOfDiagnosisYear());
                    row.add(input1.getDateOfDiagnosisMonth());
                    row.add(input1.getDateOfDiagnosisDay());
                    row.add(input1.getPrimarySite());
                    row.add(input1.getHistology());
                    row.add(input1.getBehavior());
                    row.add(input1.getLaterality());
                    row.add(input2.getDateOfDiagnosisYear());
                    row.add(input2.getDateOfDiagnosisMonth());
                    row.add(input2.getDateOfDiagnosisDay());
                    row.add(input2.getPrimarySite());
                    row.add(input2.getHistology());
                    row.add(input2.getBehavior());
                    row.add(input2.getLaterality());
                    row.add(output.getResult().toString());
                    row.add(output.getReason().replace("\n", "\\n"));

                    writer.write(String.join(",", row));
                    writer.write("\n");

                    numTestsCrated++;
                }
            }
        }
    }
}
