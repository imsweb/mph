/*
 * Copyright (C) 2023 Information Management Services, Inc.
 */
package lab;

import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphOutput;
import com.imsweb.mph.MphUtils;

public class SimpleExample {

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
