# Multiple Primary and Histology Coding Rules

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.imsweb/algorithms/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.imsweb/mph)

This library contains the SEER Java implementations of the Multiple Primary and Histology Coding Rules.

The implementation was partially based on the KCR Multiple Primary Rules Library developed by the Kentucky Cancer Registry.

## Download

The library is available on [Maven Central](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.imsweb%22%20AND%20a%3A%mph%22).

To include it to your Maven or Gradle project, use the group ID `com.imsweb` and the artifact ID `mph`.

You can check out the [release page](https://github.com/imsweb/mph/releases) for a list of the releases and their changes.

## Usage

The entry point to the library is the **MphUtils.computePrimary** method. It takes two **MphInput** representing the two tumors to evaluate, and returns an **MphOutput** that contains 
the result (SINGLE_PRIMARY, MULTIPLE_PRIMARIES or QUESTIONABLE) and a description of the rules that were executed to obtain that result.

Here is a typical call to the library:

```java
public class MphTest {
    
    public static void main(String[] args) {
        
        MphInput input1 = new MphInput();
        input1.setDateOfDiagnosisYear("2016");
        input1.setPrimarySite("C509");
        input1.setHistologyIcdO3("8000");
        input1.setBehaviorIcdO3("3");
        
        MphInput input2 = new MphInput();
        input2.setDateOfDiagnosisYear("2016");
        input2.setPrimarySite("C501");
        input2.setHistologyIcdO3("8000");
        input2.setBehaviorIcdO3("3");
        
        MphOutput output = MphUtils.getInstance().computePrimaries(input1, input2);
        System.out.println("Result: " + output.getResult());
    }
}
```

## Rules

Different sets of rules are used based on the diagnosis year and the histology.

If histology is in the range 9590-9989, one of the Hematopoietic set of rules will be used:

1. If DX year 2010 or later, the "Hematopoietic 2010" rules will be used.
2. If DX year 2001-2009, the "Hematopoietic 2001" rules will be used.
3. If DX year 2000 or earlier, the "Hematopoietic 1998" rules will be used.

If histology is not in the range 9590-9989, one of the following solid tumors sets will be used:

1. If DX year is 2018 or later, the "2018 Solid Tumor" set of rules will be used.
2. If DX year is 2007 or later, the "2007 MPH" set of rules will be used.
3. If DX year is 2006 or earlier and the case is not Benign Brain (C700-C729, C751-C753 with behavior 0/1), the "2004 Solid Malignant" rules will be used.
4. If DX year is 2006 or earlier and the case is Benign Brain (C700-C729, C751-C753 with behavior 0/1), the "2004 Benign Brain" rules will be used.

## About SEER

This library was developed through the [SEER](http://seer.cancer.gov/) program.

The Surveillance, Epidemiology and End Results program is a premier source for cancer statistics in the United States.
The SEER program collects information on incidence, prevalence and survival from specific geographic areas representing
a large portion of the US population and reports on all these data plus cancer mortality data for the entire country.

## About KCR

The [Kentucky Cancer Registry](https://www.kcr.uky.edu/) is the population-based central cancer registry for the Commonwealth of Kentucky.
