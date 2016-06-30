# Multiple Primary and Histology Coding Rules

[![Build Status](https://travis-ci.org/imsweb/algorithms.svg?branch=master)](https://travis-ci.org/imsweb/mph)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.imsweb/algorithms/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.imsweb/mph)

This library contains the SEER Java implementations of the Multiple Primary and Histology Coding Rules.

The implementation was partially based on the KCR Multiple Primary Rules Library developed by the Kentucky Cancer Registry.

## Download

The library will soon be available on [Maven Central](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.imsweb%22%20AND%20a%3A%mph%22).

To include it to your Maven or Gradle project, use the group ID `com.imsweb` and the artifact ID `mph`.

You can check out the [release page](https://github.com/imsweb/mph/releases) for a list of the releases and their changes.

## Usage

The entry point to the library is the **MphUtils** class. Because the library uses information from Hematopoietic diseases, it needs to be initialized with a **HematoDbUtilsProvider**.

The library comes with an embedded default provider, but you have the option to provide your own implementation.

The main method on the **MphUtils** class is **computePrimaries**, it takes two **MphInput** representing the two tumors to evaluate, and returns an **MphOutput** that contains 
the result (SINGLE_PRIMARY, MULTIPLE_PRIMARIES or QUESTIONABLE) and a description of the rules that were executed to obtain that result.

Here is a typical call to the library:

```java
MphUtils.initialize(new DefaultHematoDbUtilsProvider());

MphInput input1 = new MphInput();
input1.setDateOfDiagnosisYear("2016");
input1.setPrimarySite("C509");
input1.setHistologyIcdO3("8000");

MphInput input2 = new MphInput();
input2.setDateOfDiagnosisYear("2016");
input2.setPrimarySite("C501");
input2.setHistologyIcdO3("8000");

MphOutput output = MphUtils.getInstance().computePrimaries(input1, input2);
System.out.println("Result: " + output.getResult());
```

## About SEER

This library was developed through the [SEER](http://seer.cancer.gov/) program.

The Surveillance, Epidemiology and End Results program is a premier source for cancer statistics in the United States.
The SEER program collects information on incidence, prevalence and survival from specific geographic areas representing
a large portion of the US population and reports on all these data plus cancer mortality data for the entire country.

## About KCR

The [Kentucky Cancer Registry](https://www.kcr.uky.edu/) is the population-based central cancer registry for the Commonwealth of Kentucky.
