# Multiple Primary and Histology Coding Rules

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=imsweb_mph&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=imsweb_mph)
[![integration](https://github.com/imsweb/mph/workflows/integration/badge.svg)](https://github.com/imsweb/mph/actions)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.imsweb/algorithms/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.imsweb/mph)

This library contains the SEER Java implementations of the Multiple Primary and Histology Coding Rules.

The implementation was partially based on the KCR Multiple Primary Rules Library developed by the Kentucky Cancer Registry.

## Download

The library is available on [Maven Central](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.imsweb%22%20AND%20a%3A%mph%22).

To include it to your Maven or Gradle project, use the group ID `com.imsweb` and the artifact ID `mph`.

You can check out the [release page](https://github.com/imsweb/mph/releases) for a list of the releases and their changes.

## Usage

The entry point to the library is the [MphUtils.computePrimary](https://github.com/imsweb/mph/blob/master/src/main/java/com/imsweb/mph/MphUtils.java) method. 
It takes two [MphInput](https://github.com/imsweb/mph/blob/master/src/main/java/com/imsweb/mph/MphInput.java) representing the two tumors to evaluate, 
and returns an [MphOutput](https://github.com/imsweb/mph/blob/master/src/main/java/com/imsweb/mph/MphOutput.java) that contains the result 
(SINGLE_PRIMARY, MULTIPLE_PRIMARIES, QUESTIONABLE or INVALID_INPUT) and a description of the rules that were executed to obtain that result.

Here is a typical call to the library:

```java
public class MphTest {
    
    public static void main(String[] args) {
        
        MphInput input1 = new MphInput();
        input1.setDateOfDiagnosisYear("2016");
        input1.setDateOfDiagnosisMonth("06");
        input1.setDateOfDiagnosisDay("15");
        input1.setPrimarySite("C509");
        input1.setLaterality("0");
        input1.setHistologyIcdO3("8000"); // use for DX year 2001+
        input1.setBehaviorIcdO3("3"); // use for DX year 2001+
        //input1.setHistologyIcdO2("8000");  // use for DX year prior to 2001
        //input1.setBehaviorIcdO2("3");  // use for DX year prior to 2001
        
        MphInput input2 = new MphInput();
        input2.setDateOfDiagnosisYear("2016");
        input2.setDateOfDiagnosisMonth("06");
        input2.setDateOfDiagnosisDay("15");
        input2.setPrimarySite("C501");
        input2.setLaterality("0");
        input2.setHistologyIcdO3("8000"); // use for DX year 2001+
        input2.setBehaviorIcdO3("3"); // use for DX year 2001+
        //input2.setHistologyIcdO2("8000");  // use for DX year prior to 2001
        //input2.setBehaviorIcdO2("3");  // use for DX year prior to 2001
        
        MphOutput output = MphUtils.getInstance().computePrimaries(input1, input2);
        System.out.println("Result: " + output.getResult());
    }
}
```

That simple example was copied from [this lab class](https://github.com/imsweb/mph/blob/master/src/test/java/lab/SimpleExample.java).

## Rules

Different sets of rules are used based on the diagnosis year and the histology.

If histology is in the range 9590-9993, one of the Hematopoietic set of rules will be used:

1. If DX year 2010 and later, the "Hematopoietic 2010+" rules will be used.
2. If DX year 2001-2009, the "Hematopoietic 2001-2009" rules will be used.
3. If DX year 2000 or earlier, the "Hematopoietic 2000 and earlier" rules will be used.

If histology is not in the range 9590-9993, one of the following solid tumors sets will be used:

**Solid Tumor**

- 2021+ Cutaneous Melanoma
- 2023+ Other Sites
- 2018+ all other modules


**MPH**
- 2007-2020 Cutaneous Melanoma
- 2007-2022 Other Sites
- 2007-2017 for all other modules

If DX year is 2006 or earlier and the case is not Benign Brain (C700-C729, C751-C753 with behavior 0/1), the "2006 and earlier Solid Malignant" rules will be used.<br/><br/>
If DX year is 2006 or earlier and the case is Benign Brain (C700-C729, C751-C753 with behavior 0/1), the "2006 and earlier Benign Brain" rules will be used.

## Testing Data

The project contains [a lab class](https://github.com/imsweb/mph/blob/master/src/test/java/lab/TestingDataCreation.java) that can be used to generate CSV files 
that contains fake data along with the library result.

A sample testing file is available in the project: [mph-testing-2000-2022.csv.gz](https://github.com/imsweb/mph/blob/master/src/test/resources/mph-testing-2000-2022.csv.gz)

To create larger file, clone the project and execute the main method of that class locally.

The class allows the following parameters (defined in the top of the main method):
 - numTests: the number of rows for the generated CSV file
 - minDxYear: the minimum DX year to use
 - maxDxYear: the maximum DX year to use

The CSV files will contain the following columns:
 - year1
 - month1
 - day1
 - site1
 - hist1
 - beh1
 - lat1
 - year2
 - month2
 - day2
 - site2
 - hist2
 - beh2
 - lat2
 - result
 - reason

The lab class uses the [Data Generator](https://github.com/imsweb/data-generator) library to create the fake data.

## About SEER

This library was developed through the [SEER](http://seer.cancer.gov/) program.

The Surveillance, Epidemiology and End Results program is a premier source for cancer statistics in the United States.
The SEER program collects information on incidence, prevalence and survival from specific geographic areas representing
a large portion of the US population and reports on all these data plus cancer mortality data for the entire country.

## About KCR

The [Kentucky Cancer Registry](https://www.kcr.uky.edu/) is the population-based central cancer registry for the Commonwealth of Kentucky.
