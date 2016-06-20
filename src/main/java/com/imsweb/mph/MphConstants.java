/*
 * Copyright (C) 2016 Information Management Services, Inc.
 */
package com.imsweb.mph;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MphConstants {

    //Histologies
    public static final List<String> _CARCINOMA_NOS = Collections.singletonList("8010");
    public static final List<String> _CARCINOMA_SPECIFIC = MphGroup.expandList(Collections.singletonList(
            "8000-8005,8011-8015,8020-8022,8030-8035,8041-8046,8050-8052,8070-8078,8080-8084,8090-8098,8102,8110,8120-8124,8130-8131,8140-8148,8150-8157,8160-8162,8170-8175,"
                    + "8180,8190,8200-8201,8210-8211,8214-8215,8220-8221,8230-8231,8240-8247,8249,8250-8256,8260-8263,8270-8272,8280-8281,8290,8300,8310,8312-8319,8320-8323,"
                    + "8330-8333,8335,8337,8340-8347,8350,8370,8380-8384,8390,8400-8403,8407-8410,8413,8420,8430,8440-8442,8450-8453,8460-8462,"
                    + "8470-8473,8480-8482,8490,8500-8504,8507-8508,8510,8512-8514,8520-8525,8530,8540-8543,8550-8551,8560-8562,8570-8576,8580-8586,8588-8589,9070,9100,9110"));
    public static final List<String> _ADENOCARCINOMA_NOS = Collections.singletonList("8140");
    public static final List<String> _ADENOCARCINOMA_SPECIFIC = MphGroup.expandList(Collections.singletonList("8000-8005,8010-8011,8020-8022,8046,8141-8148,8154,8160-8162,8190,"
            + "8200-8201,8210-8211,8214-8215,8220-8221,8230-8231,8244-8245,8250-8255,8260-8263,8270-8272,8280-8281,8290,8300,8310,8312-8320,8322-8323,8330-8333,8335,8337,8350,"
            + "8370,8380-8384,8390,8400-8403,8407-8409,8410,8413,8420,8440-8442,8450-8453,8460-8462,8470-8473,8480-8482,8490,8500-8504,8507-8508,8510,8512-8514,8520-8525,8530,"
            + "8540-8543,8550-8551,8561-8562,8570-8576"));
    public static final List<String> _MELANOMA_NOS = Collections.singletonList("8720");
    public static final List<String> _MELANOMA_SPECIFIC = MphGroup.expandList(Collections.singletonList("8000-8005,8721-8790"));
    public static final List<String> _SARCOMA_NOS = Collections.singletonList("8800");
    public static final List<String> _SARCOMA_SPECIFIC = MphGroup.expandList(Collections.singletonList("8000-8005,8801-8806,8810-8815,8830,8832-8833,8840,8842,8850-8855,8857-8858,8890-8891,"
            + "8894-8896,8900-8902,8910,8912,8920-8921,8930-8931,8933,8935-8936,8990-8991,9040-9044,9180-9243,9260-9261"));
    public static final List<String> _FAMILIAL_ADENOMATOUS_POLYPOSIS = Collections.singletonList("8220");
    public static final List<String> _FOLLICULAR = MphGroup.expandList(Collections.singletonList("8290,8330-8332,8335,8340,8346"));
    public static final List<String> _PAPILLARY = MphGroup.expandList(Collections.singletonList("8050,8052,8260,8340-8343,8344,8347"));
    public static final String _PAPILLARY_CARCINOMA = "8050";
    public static final List<String> _TRANSITIONAL_CELL_CARCINOMA = MphGroup.expandList(Collections.singletonList("8120-8124"));
    public static final List<String> _PAPILLARY_TRANSITIONAL_CELL_CARCINOMA = Arrays.asList("8130", "8131");
    public static final List<String> _PAGET_DISEASE = MphGroup.expandList(Collections.singletonList("8540-8543"));
    public static final List<String> _INTRADUCTAL_CARCINOMA = MphGroup.expandList(Collections.singletonList("8201,8230,8401,8500-8501,8503-8504,8507"));
    public static final List<String> _DUCT_CARCINOMA = MphGroup.expandList(Collections.singletonList("8022,8035,8500-8503,8508,8522"));
    public static final List<String> _LOBULAR_CARCINOMA = Arrays.asList("8520", "8522", "8524");

    //Histology Charts
    public static final Map<String, String> _BENIGN_BRAIN_2004_HISTOLOGY_GROUPING = new HashMap<>();

    static {
        _BENIGN_BRAIN_2004_HISTOLOGY_GROUPING.put("9390", "Choroid plexus neoplasms");
        _BENIGN_BRAIN_2004_HISTOLOGY_GROUPING.put("9383", "Ependymomas");
        _BENIGN_BRAIN_2004_HISTOLOGY_GROUPING.put("9394", "Ependymomas");
        _BENIGN_BRAIN_2004_HISTOLOGY_GROUPING.put("9444", "Ependymomas");
        _BENIGN_BRAIN_2004_HISTOLOGY_GROUPING.put("9384", "Neuronal and neuronal-glial neoplasms");
        _BENIGN_BRAIN_2004_HISTOLOGY_GROUPING.put("9412", "Neuronal and neuronal-glial neoplasms");
        _BENIGN_BRAIN_2004_HISTOLOGY_GROUPING.put("9413", "Neuronal and neuronal-glial neoplasms");
        _BENIGN_BRAIN_2004_HISTOLOGY_GROUPING.put("9442", "Neuronal and neuronal-glial neoplasms");
        _BENIGN_BRAIN_2004_HISTOLOGY_GROUPING.put("9505/1", "Neuronal and neuronal-glial neoplasms");
        _BENIGN_BRAIN_2004_HISTOLOGY_GROUPING.put("9506", "Neuronal and neuronal-glial neoplasms");
        _BENIGN_BRAIN_2004_HISTOLOGY_GROUPING.put("9540", "Neurofibromas");
        _BENIGN_BRAIN_2004_HISTOLOGY_GROUPING.put("9541", "Neurofibromas");
        _BENIGN_BRAIN_2004_HISTOLOGY_GROUPING.put("9550", "Neurofibromas");
        _BENIGN_BRAIN_2004_HISTOLOGY_GROUPING.put("9560/0", "Neurofibromas");
        _BENIGN_BRAIN_2004_HISTOLOGY_GROUPING.put("9560/1", "Neurinomatosis ");
        _BENIGN_BRAIN_2004_HISTOLOGY_GROUPING.put("9562", "Neurothekeoma");
        _BENIGN_BRAIN_2004_HISTOLOGY_GROUPING.put("9570", "Neuroma");
        _BENIGN_BRAIN_2004_HISTOLOGY_GROUPING.put("9571/0", "Perineurioma, NOS");
    }

    //Topographies
    public static final String _COLON = "C18";
    public static final String _RECTUM = "C219";
    public static final String _THYROID = "C739";
    public static final String _BLADDER = "C67";
    public static final String _BREAST = "C50";

}
