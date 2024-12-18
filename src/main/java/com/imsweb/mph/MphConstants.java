/*
 * Copyright (C) 2016 Information Management Services, Inc.
 */
package com.imsweb.mph;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.imsweb.mph.mpgroups.GroupUtility;

@SuppressWarnings("java:S2386") //Mutable fields should not be "public static", Sonar's suggestion to make these protected doesn't work
public class MphConstants {

    private MphConstants() {
    }

    public static final String USE_FOR_MULTIPLE_RULES = "Use this rule only for multiple tumors.";
    public static final String TIMING_IS_IRRELEVANT = "Timing is irrelevant.";
    public static final String HISTOLOGY_IS_IRRELEVANT = "Histology is irrelevant.";
    public static final String HIERARCHICAL_RULES = "The rules are hierarchical. Only use this rule when none of the previous rules apply.";
    public static final String AND_CONNECTOR = " and ";
    public static final String TRANSFORMATION_NOTE = "Transformations to (acute neoplasms) and Transformations from (chronic neoplasms) are defined for each applicable histology in the database.";
    public static final String HODGKIN_NOTE = "For the purpose of using the rules, a non-Hodgkin lymphoma is any lymphoma (including the leukemia/lymphomas) not stated to be Hodgkin "
            + "lymphoma, NOS or a type of Hodgkin lymphoma.\n - Hodgkin lymphomas are: 9650/-9653/3, 9655/3, 9659/3, 9663/3";

    //Compare dx date constants
    public static final int COMPARE_DX_UNKNOWN = -1;
    public static final int COMPARE_DX_EQUAL = 0;
    public static final int COMPARE_DX_FIRST_LATEST = 1;
    public static final int COMPARE_DX_SECOND_LATEST = 2;

    //Verify days/years apart constants
    public static final int DATE_VERIFY_UNKNOWN = -1;
    public static final int DATE_VERIFY_WITHIN = 0;
    public static final int DATE_VERIFY_APART = 1;

    //Group Ids for the set of rules
    public static final String HEMATO_2000_AND_EARLIER = "hemato_2000_and_earlier";
    public static final String HEMATO_2001_TO_2009 = "hemato_2001_to_2009";
    public static final String HEMATO_2010_AND_LATER = "hemato_2010_and_later";
    public static final String MP_2004_SOLID_MALIGNANT_GROUP_ID = "2006_and_earlier_solid_malignant";
    public static final String MP_2004_BENIGN_BRAIN_GROUP_ID = "2006_and_earlier_benign_brain";
    public static final String MPH_2007_BENIGN_BRAIN_GROUP_ID = "mph_2007_to_2017_benign_brain";
    public static final String MPH_2007_BREAST_GROUP_ID = "mph_2007_to_2017_breast";
    public static final String MPH_2007_COLON_GROUP_ID = "mph_2007_to_2017_colon";
    public static final String MPH_2007_HEAD_AND_NECK_GROUP_ID = "mph_2007_to_2017_head_and_neck";
    public static final String MPH_2007_KIDNEY_GROUP_ID = "mph_2007_to_2017_kidney";
    public static final String MPH_2007_LUNG_GROUP_ID = "mph_2007_to_2017_lung";
    public static final String MPH_2007_MALIGNANT_BRAIN_GROUP_ID = "mph_2007_to_2017_malignant_brain";
    public static final String MPH_2007_MELANOMA_GROUP_ID = "mph_2007_to_2017_melanoma";
    public static final String MPH_2007_OTHER_SITES_GROUP_ID = "mph_2007_to_2022_other_sites";
    public static final String MPH_2007_URINARY_GROUP_ID = "mph_2007_to_2017_urinary";
    public static final String STR_2018_AND_LATER_BREAST = "str_2018_and_later_breast";
    public static final String STR_2018_AND_LATER_COLON = "str_2018_and_later_colon";
    public static final String STR_2018_AND_LATER_HEAD_AND_NECK = "str_2018_and_later_head_and_neck";
    public static final String STR_2018_AND_LATER_KIDNEY = "str_2018_and_later_kidney";
    public static final String STR_2018_AND_LATER_LUNG = "str_2018_and_later_lung";
    public static final String STR_2018_AND_LATER_MALIGNANT_CNS = "str_2018_and_later_malignant_cns";
    public static final String STR_2018_AND_LATER_NON_MALIGNANT_CNS = "str_2018_and_later_non_malignant_cns";
    public static final String STR_2023_AND_LATER_OTHER_SITES = "str_2023_and_later_other_sites";
    public static final String STR_2018_AND_LATER_URINARY = "str_2018_and_later_urinary";

    public static final String STR_2021_AND_LATER_CUTANEOUS_MELANOMA = "str_2021_and_later_cutaneous_melanoma";

    //Group Names for the set of rules
    public static final String HEMATOPOIETIC_AND_LYMPHOID_2000_AND_EARLIER = "Hematopoietic and Lymphoid 2000 and earlier";
    public static final String HEMATOPOIETIC_AND_LYMPHOID_2001_2009 = "Hematopoietic and Lymphoid 2001-2009";
    public static final String HEMATOPOIETIC_AND_LYMPHOID_2010 = "Hematopoietic and Lymphoid 2010+";
    public static final String MP_2004_SOLID_MALIGNANT = "2006 and earlier Solid Malignant";
    public static final String MP_2004_BENIGN_BRAIN = "2006 and earlier Benign Brain";
    public static final String MPH_2007_2017_BENIGN_BRAIN = "MPH 2007-2017 Benign Brain";
    public static final String MPH_2007_2017_BREAST = "MPH 2007-2017 Breast";
    public static final String MPH_2007_2017_COLON = "MPH 2007-2017 Colon";
    public static final String MPH_2007_2017_HEAD_AND_NECK = "MPH 2007-2017 Head and Neck";
    public static final String MPH_2007_2017_KIDNEY = "MPH 2007-2017 Kidney";
    public static final String MPH_2007_2017_LUNG = "MPH 2007-2017 Lung";
    public static final String MPH_2007_2017_MALIGNANT_BRAIN = "MPH 2007-2017 Malignant Brain";
    public static final String MPH_2007_2020_MELANOMA = "MPH 2007-2020 Melanoma";
    public static final String MPH_2007_2022_OTHER_SITES = "MPH 2007-2022 Other Sites";
    public static final String MPH_2007_2017_URINARY = "MPH 2007-2017 Urinary";

    public static final String SOLID_TUMOR_2018_BREAST = "Solid Tumor 2018+ Breast";
    public static final String SOLID_TUMOR_2018_COLON = "Solid Tumor 2018+ Colon";
    public static final String SOLID_TUMOR_2018_HEAD_AND_NECK = "Solid Tumor 2018+ Head And Neck";
    public static final String SOLID_TUMOR_2018_KIDNEY = "Solid Tumor 2018+ Kidney";
    public static final String SOLID_TUMOR_2018_LUNG = "Solid Tumor 2018+ Lung";
    public static final String SOLID_TUMOR_2018_MALIGNANT_CNS = "Solid Tumor 2018+ Malignant CNS";
    public static final String SOLID_TUMOR_2018_NON_MALIGNANT_CNS = "Solid Tumor 2018+ Non Malignant CNS";
    public static final String SOLID_TUMOR_2023_OTHER_SITES = "Solid Tumor 2023+ Other Sites";
    public static final String SOLID_TUMOR_2018_URINARY = "Solid Tumor 2018+ Urinary";
    public static final String SOLID_TUMOR_2021_CUTANEOUS_MELANOMA = "Solid Tumor 2021+ Cutaneous Melanoma";

    //Topographies

    public static final String SMALL_BOWEL = "C17";
    public static final String COLON = "C18";
    public static final String RECTOSIGMOID = "C199";
    public static final String RECTUM = "C209";
    public static final String BREAST = "C50";
    public static final String OVARY = "C569";
    public static final String PROSTATE = "C619";
    public static final String RENAL_PELVIS = "C659";
    public static final String URETER = "C669";
    public static final String BLADDER = "C67";
    public static final String URETHRA = "C680";
    public static final String THYROID = "C739";
    public static final String LYMPH_NODE = "C77";

    public static final String PERITONEUM = "C482";

    public static final String FALLOPIAN = "C570";

    public static final String VAGINA = "C529";

    public static final List<String> EXACT_MATCH_SITES = Collections.unmodifiableList(Arrays.asList("C18", "C21", "C38", "C40", "C41", "C44", "C47", "C49"));
    public static final List<String> TONGUE = Collections.unmodifiableList(Arrays.asList("C01", "C02"));
    public static final List<String> MOUTH = Collections.unmodifiableList(Arrays.asList("C05", "C06"));
    public static final List<String> SALIVARY = Collections.unmodifiableList(Arrays.asList("C07", "C08"));
    public static final List<String> OROPHARYNX = Collections.unmodifiableList(Arrays.asList("C09", "C10"));
    public static final List<String> HYPOPHARYNX = Collections.unmodifiableList(Arrays.asList("C12", "C13"));
    public static final List<String> BILIARY = Collections.unmodifiableList(Arrays.asList("C23", "C24"));
    public static final List<String> SINUS = Collections.unmodifiableList(Arrays.asList("C30", "C31"));
    public static final List<String> LUNG = Collections.unmodifiableList(Arrays.asList("C33", "C34"));

    public static final String MEDIASTINUM = "C370-C383,C388";
    public static final String FEMALE_GENITAL = "C510-C529,C577-C579";
    public static final String OVARY_OR_FEMALE_GENITAL = "C569-C574";

    public static final List<String> MALE_GENITAL = Collections.unmodifiableList(Arrays.asList("C60", "C63"));
    public static final List<String> KIDNEY_OR_URINARY = Collections.unmodifiableList(Arrays.asList("C64", "C65", "C66", "C68"));
    public static final List<String> ENDOCRINE = Collections.unmodifiableList(Arrays.asList("C74", "C75"));
    public static final List<String> UPPER_LIP = Collections.unmodifiableList(Arrays.asList("C000", "C003"));
    public static final List<String> LOWER_LIP = Collections.unmodifiableList(Arrays.asList("C001", "C004"));
    public static final List<String> UPPER_GUM = Collections.singletonList("C030");
    public static final List<String> LOWER_GUM = Collections.singletonList("C031");
    public static final List<String> NASAL_CAVITY = Collections.singletonList("C300");
    public static final List<String> MIDDLE_EAR = Collections.singletonList("C301");
    public static final List<String> SUBMANDIBULAR_GLAND = Collections.singletonList("C080");
    public static final List<String> SUBLINGUAL_GLAND = Collections.singletonList("C081");
    public static final List<String> HARD_PALATE = Collections.singletonList("C050");
    public static final List<String> SOFT_PALATE = Collections.singletonList("C051");
    public static final List<String> UVULA = Collections.singletonList("C052");
    public static final List<String> MAXILLARY_SINUS = Collections.singletonList("C310");
    public static final List<String> ETHMOID_SINUS = Collections.singletonList("C311");
    public static final List<String> FRONTAL_SINUS = Collections.singletonList("C312");
    public static final List<String> SPHENOID_SINUS = Collections.singletonList("C313");
    public static final List<String> GLOTTIS_AND_LARYNGEAL_SITES = Collections.unmodifiableList(Arrays.asList("C320", "C321", "C322", "C323"));
    public static final List<String> MAXILLA = Collections.singletonList("C410");
    public static final List<String> MANDIBLE = Collections.singletonList("C411");
    public static final List<String> POSTCRICOID = Collections.singletonList("C130");
    public static final List<String> HYPOPHARYNGEAL_ASPECT_OF_ARYEPIGLOTTIC_FOLD = Collections.singletonList("C131");
    public static final List<String> POSTERIOR_WALL_OF_HYPOPHARYNX = Collections.singletonList("C132");

    public static final String ALL_PAIRED_SITES =
            "C079,C080-C081,C090-C099,C300-C301,C310,C312,C340-C349,C384,C400-C403,C413-C414,C441-C443,C445-C447,C471-C472,C491-C492,C500-C509,C569,C570,C620-C629,C630-C631,C649,C659,C669,C690-C699,C700,C710-C714,C722-C725,C740-C749,C754";

    //Behavior
    public static final String BENIGN = "0";
    public static final String UNCERTAIN = "1";
    public static final String INSITU = "2";
    public static final String MALIGNANT = "3";

    //Laterality
    public static final String NOT_PAIRED = "0";
    public static final String RIGHT = "1";
    public static final String LEFT = "2";
    public static final String ONLY_ONE_SIDE_NS = "3";
    public static final String BOTH = "4";
    public static final String MID_LINE = "5";
    public static final String PAIRED_NO_INFORMATION = "9";

    //Histologies
    public static final List<String> ANAPLASTIC_CARCINOMA = Collections.singletonList("8021/3");

    public static final List<String> OTHER_THYROID_HISTOLOGIES = Collections.unmodifiableList(Arrays.asList("8330/3", "8339/3", "8335/3", "8331/3", "8332/3", "8345/3", "8290/3", "8260/3", "8344/3", "8350/3", "8343/3", "8340/3",
            "8343/2", "8337/3"));
    public static final List<String> CARCINOMA_NOS = GroupUtility.expandList("8010");
    public static final List<String> CARCINOMA_SPECIFIC = GroupUtility.expandList(
            "8000-8005,8011-8015,8020-8022,8030-8035,8041-8046,8050-8052,8070-8078,8080-8084,8090-8098,8102,8110,8120-8124,8130-8131,8140-8148,8150-8157,8160-8162,8170-8175,"
                    + "8180,8190,8200-8201,8210-8211,8214-8215,8220-8221,8230-8231,8240-8247,8249,8250-8256,8260-8263,8270-8272,8280-8281,8290,8300,8310,8312-8319,8320-8323,"
                    + "8330-8333,8335,8337,8340-8347,8350,8370,8380-8384,8390,8400-8403,8407-8410,8413,8420,8430,8440-8442,8450-8453,8460-8462,"
                    + "8470-8473,8480-8482,8490,8500-8504,8507-8508,8510,8512-8514,8520-8525,8530,8540-8543,8550-8551,8560-8562,8570-8576,8580-8586,8588-8589,9070,9100,9110");
    public static final List<String> NON_SMALL_CELL_CARCINOMA = GroupUtility.expandList("8046");
    public static final List<String> SPECIFIC_NON_SMALL_CELL_CARCINOMA = GroupUtility.expandList(
            "8012-8014,8022,8031-8033,8052,8070-8073,8082-8084,8123,8140,8200,8230,8250-8255,8260,8310,8333,8430,8470,8480-8481,8490,8550,8560,8972,8980");
    public static final List<String> SMALL_CELL_CARCINOMA = GroupUtility.expandList("8041-8045");
    public static final String INFLAMMATORY_CARCINOMA = "8530";
    public static final List<String> ADENOCARCINOMA_NOS = GroupUtility.expandList("8140");

    public static final List<String> ADENOCARCINOMA_PROSTATE_SUBTYPES = Collections.unmodifiableList(Arrays.asList("8140", "8572/3", "8500", "8201/3", "8260/3", "8230/3", "8480/3", "8490/3"));
    public static final List<String> ADENOCARCINOMA_SPECIFIC = GroupUtility.expandList("8000-8005,8010-8011,8020-8022,8046,8141-8148,8154,8160-8162,8190,"
            + "8200-8201,8210-8211,8214-8215,8220-8221,8230-8231,8244-8245,8250-8255,8260-8263,8270-8272,8280-8281,8290,8300,8310,8312-8320,8322-8323,8330-8333,8335,8337,8350,"
            + "8370,8380-8384,8390,8400-8403,8407-8409,8410,8413,8420,8440-8442,8450-8453,8460-8462,8470-8473,8480-8482,8490,8500-8504,8507-8508,8510,8512-8514,8520-8525,8530,"
            + "8540-8543,8550-8551,8561-8562,8570-8576");
    public static final List<String> ADENOCARCINOMA_WITH_MIXED_SUBTYPES = GroupUtility.expandList("8255");
    public static final List<String> BRONCHIOALVEOLAR = GroupUtility.expandList("8250-8254");
    public static final List<String> MELANOMA_NOS = GroupUtility.expandList("8720");
    public static final List<String> MELANOMA_SPECIFIC = GroupUtility.expandList("8000-8005,8721-8790");
    public static final List<String> SARCOMA_NOS = GroupUtility.expandList("8800");
    public static final List<String> SARCOMA_SPECIFIC = GroupUtility.expandList("8000-8005,8801-8806,8810-8815,8830,8832-8833,8840,8842,8850-8855,8857-8858,8890-8891,"
            + "8894-8896,8900-8902,8910,8912,8920-8921,8930-8931,8933,8935-8936,8990-8991,9040-9044,9180-9243,9260-9261");
    public static final List<String> FOLLICULAR = Collections.unmodifiableList(Arrays.asList("8330/3", "8339/3", "8335/3", "8331/3", "8332/3"));
    public static final List<String> PAPILLARY = Collections.unmodifiableList(Arrays.asList("8260/3", "8344/3", "8350/3", "8343/3", "8340/3"));

    public static final List<String> FOLLICULAR_NOS = Collections.singletonList("8330/3");
    public static final List<String> PAPILLARY_NOS = Collections.singletonList("8260/3");
    public static final String PAPILLARY_CARCINOMA = "8050";
    public static final String KAPOSI_SARCOMA = "9140";
    public static final List<String> TRANSITIONAL_CELL_CARCINOMA = GroupUtility.expandList("8120-8124");
    public static final List<String> PAPILLARY_TRANSITIONAL_CELL_CARCINOMA = GroupUtility.expandList("8130-8131");
    public static final List<String> PAGET_DISEASE = GroupUtility.expandList("8540-8543");
    public static final List<String> INTRADUCTAL_CARCINOMA = GroupUtility.expandList("8201,8230,8401,8500-8501,8503-8504,8507");
    public static final List<String> DUCT_CARCINOMA = GroupUtility.expandList("8022,8035,8500-8503,8508");
    public static final List<String> LOBULAR_CARCINOMA = GroupUtility.expandList("8520,8522,8524");
    public static final List<String> LYMPHOMA_NOS_AND_NON_HODGKIN_LYMPHOMA = GroupUtility.expandList("9590-9591,9673,9678,9679,9687,9735-9738,9823,9827");
    public static final List<String> NON_HODGKIN_LYMPHOMA = GroupUtility.expandList("9591,9670-9729");
    public static final List<String> HODGKIN_LYMPHOMA = GroupUtility.expandList("9650-9653,9655,9659,9663");
    public static final List<String> POLYP = GroupUtility.expandList("8210-8211,8213,8220-8221,8261-8263");
    public static final List<String> FAMILLIAL_POLYPOSIS = GroupUtility.expandList("8220-8221");
    public static final List<String> FAMILIAL_ADENOMATOUS_POLYPOSIS = GroupUtility.expandList("8220");
    public static final List<String> PTLD = GroupUtility.expandList("9971"); // post-transplant lymphoproliferative disorder
    public static final List<String> BCELL = GroupUtility.expandList("9671,9673,9678-9680,9687-9691,9695,9698,9699");
    public static final List<String> TCELL = GroupUtility.expandList("9702,9705,9708,9709,9716-9719,9724,9726,9827,9831,9834,9837");
    public static final List<String> PLASMACYTOMA = GroupUtility.expandList("9731-9732,9734");
    public static final List<String> MAST_CELL_SARCOMA = GroupUtility.expandList("9740");
    public static final List<String> MAST_CELL_LEUKEMIA = GroupUtility.expandList("9742");
    public static final List<String> MYELOID_SARCOMA = GroupUtility.expandList("9930");
    public static final List<String> MYELOID_LEUKEMIA = GroupUtility.expandList("9840,9861,9865-9867,9869-9874,9891,9895-9898,9910-9911,9931");
    public static final List<String> RETINO_BLASTOMA = GroupUtility.expandList("9510-9513");
    public static final String WILMS = "8960";
    public static final String NEUROFIBROMATOSIS = "9540";
    public static final List<String> SPECIFIC_RENAL_CELL_HISTOLOGIES = GroupUtility.expandList("8260,8310,8316-8320,8510,8959");
    public static final String GLIOBLASTOMA_NOS_AND_MULTIFORME = "9440";
    public static final List<String> GLIAL_TUMOR = GroupUtility.expandList("9380-9382,9400-9401,9410-9411,9420-9421,9423-9424,9430,9440-9442");
    public static final List<String> UROTHELIAL = GroupUtility.expandList("8120,8130,8131,8082,8122,8031,8020");
    public static final List<String> GLIAL_TUMOR_2018 = GroupUtility.expandList("9380,9385,9391,9392,9393,9396,9400,9401,9411,9424,9430,9440,9441,9442,9445,9450,9451");
    public static final String MALT = "9699/3";

    //Histology Charts
    public static final Map<String, List<String>> NOS_VS_SPECIFIC;

    static {
        Map<String, List<String>> content = new HashMap<>();
        content.put("8000", GroupUtility.expandList("8001-9999")); //Cancer/malignant neoplasm, NOS
        content.put("8010", GroupUtility.expandList("8011-8015")); //Carcinoma, NOS
        content.put("8140", GroupUtility.expandList("8141-8145,8147-8148")); //Adenocarcinoma, NOS
        content.put("8070", GroupUtility.expandList("8071-8078,8080-8084")); //Squamous cell carcinoma, NOS
        content.put("8720", GroupUtility.expandList("8721-8723,8726,8728,8730,8740,8741-8746,8761,8770-8774,8780")); //Melanoma, NOS
        content.put("8800", GroupUtility.expandList("8801-8806")); //Sarcoma, NOS
        content.put("8312", GroupUtility.expandList("8313-8320")); //Renal cell carcinoma, NOS
        NOS_VS_SPECIFIC = Collections.unmodifiableMap(content);
    }

    public static final Map<String, String> BENIGN_BRAIN_2004_HISTOLOGY_GROUPING;
    private static final String _EPENDYMOMAS = "Ependymomas";
    private static final String _NEURONAL = "Neuronal and neuronal-glial neoplasms";
    private static final String _NEUROFIBROMAS = "Neurofibromas";

    static {
        Map<String, String> content = new HashMap<>();
        content.put("9390", "Choroid plexus neoplasms");
        content.put("9383", _EPENDYMOMAS);
        content.put("9394", _EPENDYMOMAS);
        content.put("9444", _EPENDYMOMAS);
        content.put("9384", _NEURONAL);
        content.put("9412", _NEURONAL);
        content.put("9413", _NEURONAL);
        content.put("9442", _NEURONAL);
        content.put("9505/1", _NEURONAL);
        content.put("9506", _NEURONAL);
        content.put("9540", _NEUROFIBROMAS);
        content.put("9541", _NEUROFIBROMAS);
        content.put("9550", _NEUROFIBROMAS);
        content.put("9560/0", _NEUROFIBROMAS);
        content.put("9560/1", "Neurinomatosis ");
        content.put("9562", "Neurothekeoma");
        content.put("9570", "Neuroma");
        content.put("9571/0", "Perineurioma, NOS");
        BENIGN_BRAIN_2004_HISTOLOGY_GROUPING = Collections.unmodifiableMap(content);
    }

    public static final Map<String, String> BENIGN_BRAIN_2007_CHART1;

    static {
        Map<String, String> content = new HashMap<>();
        content.put("9383/1", _EPENDYMOMAS); //Subependymoma
        content.put("9394/1", _EPENDYMOMAS); //Myxopapillary Ependymoma
        content.put("9444/1", _EPENDYMOMAS); //Choroid glioma
        content.put("9384/1", _NEURONAL); //Subependymal giant cell astrocytoma
        content.put("9412/1", _NEURONAL); //Desmoplastic infantile astrocytoma
        content.put("9413/0", _NEURONAL); //Dysembryoplastic neuroepithelial tumor
        content.put("9442/1", _NEURONAL); //Gliofibroma
        content.put("9505/1", _NEURONAL); //Ganglioglioma
        content.put("9506/1", _NEURONAL); //Central neurocytoma
        content.put("9540/0", _NEUROFIBROMAS); //Neurofibroma, NOS
        content.put("9540/1", _NEUROFIBROMAS); //Neurofibromatosis, NOS
        content.put("9541/0", _NEUROFIBROMAS); //Melanotic neurofibroma
        content.put("9550/0", _NEUROFIBROMAS); //Plexiform neurofibroma
        content.put("9560/0", _NEUROFIBROMAS); //Neurilemoma, NOS
        content.put("9560/1", "Neurinomatosis"); //Neurinomatosis
        content.put("9562", "Neurothekeoma"); //Neurothekeoma
        content.put("9570", "Neuroma"); //Neuroma
        content.put("9571/0", "Perineurioma, NOS"); //Perineurioma, NOS
        BENIGN_BRAIN_2007_CHART1 = Collections.unmodifiableMap(content);
    }

    public static final Map<String, List<String>> MALIGNANT_BRAIN_2007_CHART1;


    static {
        Map<String, List<String>> content = new HashMap<>();
        String branch1 = "branch_1";
        String branch2 = "branch_2";
        String branch3 = "branch_3";
        String branch4 = "branch_4";
        String branch5 = "branch_5";
        String branch6 = "branch_6";
        String branch7 = "branch_7";
        String branch8 = "branch_8";
        String branch9 = "branch_9";
        String branch10 = "branch_10";
        String branch11 = "branch_11";
        String branch12 = "branch_12";
        String branch13 = "branch_13";
        String branch14 = "branch_14";
        String branch15 = "branch_15";
        String branch16 = "branch_16";
        content.put("9503", Arrays.asList(branch1, branch2, branch3, branch4, branch5, branch6, branch7, branch8, branch9, branch10, branch11, branch12, branch13, branch14,
                branch15, branch16)); //This is included in all branches
        content.put("9508", Collections.singletonList(branch1)); //Atypical tetratoid/rhabdoid tumor
        content.put("9392", Arrays.asList(branch2, branch6)); //Ependymoblastoma //Anasplastic ependymoma
        content.put("9501", Collections.singletonList(branch3)); //Medulloepithelioma
        content.put("9502", Collections.singletonList(branch3)); //Teratoid medulloepthelioma
        content.put("9470", Collections.singletonList(branch4)); //Medulloblastoma
        content.put("9471", Collections.singletonList(branch4)); //Demoplastic
        content.put("9474", Collections.singletonList(branch4)); //Large cell
        content.put("9472", Collections.singletonList(branch4)); //Medullomyoblastoma
        content.put("9473", Collections.singletonList(branch5)); //Supratentorial primitive neuroectodermal tumor (PNET)
        content.put("9500", Collections.singletonList(branch5)); //Neuroblastoma
        content.put("9490", Collections.singletonList(branch5)); //Ganglioneuroblastoma
        content.put("9391", Collections.singletonList(branch6)); //Ependymoma, NOS
        content.put("9393", Collections.singletonList(branch6)); //Papillary ependymoma
        content.put("9362", Collections.singletonList(branch7)); //Pineoblastoma
        content.put("9390", Collections.singletonList(branch8)); //Choroid plexus carcinoma
        content.put("9505", Collections.singletonList(branch9)); //Ganglioglioma, anaplastic  // Ganglioglioma, malignant
        content.put("9522", Collections.singletonList(branch10)); //Olfactory neuroblastoma
        content.put("9521", Collections.singletonList(branch10)); //Olfactory neurocytoma
        content.put("9523", Collections.singletonList(branch10)); //Olfactory neuroepithlioma
        content.put("9380", Arrays.asList(branch11, branch12, branch13, branch14, branch15)); //Glioma, NOS
        content.put("9430", Collections.singletonList(branch11)); //Astroblastoma
        content.put("9381", Collections.singletonList(branch11)); //Gliomatosis cerebri
        content.put("9423", Collections.singletonList(branch11)); //Polar spongioblastoma
        content.put("9382", Collections.singletonList(branch12)); //Mixed glioma
        content.put("9400", Collections.singletonList(branch13)); //Astrocytoma, NOS
        content.put("9401", Collections.singletonList(branch13)); //Anaplastic astrocytoma
        content.put("9420", Collections.singletonList(branch13)); //Fibrillary astrocytoma
        content.put("9411", Collections.singletonList(branch13)); //Gemistocytic astrocytoma
        content.put("9410", Collections.singletonList(branch13)); //Protoplasmic astromytoma
        content.put("9421", Collections.singletonList(branch14)); //Pilocytic astrocytoma
        content.put("9424", Collections.singletonList(branch15)); //Pleomorphic xanthoastrocytoma
        content.put("9440", Collections.singletonList(branch15)); //Glioblastoma, NOS and Glioblastoma multiforme
        content.put("9441", Collections.singletonList(branch15)); //Giant cell glioblastoma
        content.put("9442", Collections.singletonList(branch15)); //Gliosarcoma
        content.put("9450", Collections.singletonList(branch16)); //Oligodendroglioma NOS
        content.put("9451", Collections.singletonList(branch16)); //Oligodendroglioma anaplastic
        content.put("9460", Collections.singletonList(branch16)); //Oligodendroblastoma
        MALIGNANT_BRAIN_2007_CHART1 = Collections.unmodifiableMap(content);
    }

    public static final Map<String, List<String>> MALIGNANT_BRAIN_2007_CHART2;

    static {
        Map<String, List<String>> content = new HashMap<>();
        content.put("9540", Collections.singletonList("Periphera Nerve")); //Malignant peripheral nerve sheath tumor
        content.put("9561", Collections.singletonList("Periphera Nerve")); //Malignant peripheral nerve sheath tumor with rhabdomyoblastic differentiation (MPNST)
        content.put("9560", Collections.singletonList("Periphera Nerve")); //Neurilemoma, malignant
        content.put("9571", Collections.singletonList("Periphera Nerve")); //Perineurioma, malignant
        content.put("9100", Collections.singletonList("Germ Cell Tumors")); //Choriocarcinoma
        content.put("9070", Collections.singletonList("Germ Cell Tumors")); //Embryonal carcionoma
        content.put("9064", Collections.singletonList("Germ Cell Tumors")); //Germinoma
        content.put("9080", Collections.singletonList("Germ Cell Tumors")); //Immature teratoma
        content.put("9085", Collections.singletonList("Germ Cell Tumors")); //Mixed germ cell tumor
        content.put("9084", Collections.singletonList("Germ Cell Tumors")); //Teratoma with malignant transformation
        content.put("9071", Collections.singletonList("Germ Cell Tumors")); //Yolk sac tumor
        content.put("9539", Collections.singletonList("Meningioma, malignant")); //Meningeal sarcomatosis
        content.put("9538", Collections.singletonList("Meningioma, malignant")); //Papillary meningioma, rhadboid meningioma
        MALIGNANT_BRAIN_2007_CHART2 = Collections.unmodifiableMap(content);
    }

    //--------------------------------------------------------------------------------------------------------------------------------------
    // Breast
    //--------------------------------------------------------------------------------------------------------------------------------------
    // See Breast 2019 AS OF date at top of this file.

    public static final List<String> BREAST_NST_DUCT_CARCINOMA_2018 = GroupUtility.expandList("8500/2,8500/3,8035/3,8201/3,8022/3");
    public static final List<String> BREAST_LOBULAR_CARCINOMA_2018 = GroupUtility.expandList("8520/2,8519/2,8520/3");
    public static final List<String> BREAST_DUCT_2018 = GroupUtility.expandList("8500/2,8500/3,8035/3,8520/2,8519/2,8520/3");
    public static final List<String> BREAST_LOBULAR_2018 = GroupUtility.expandList("8522/3,8522/2");



    public static final List<String> BREAST_2018_TABLE2 = Collections.unmodifiableList(Arrays.asList("8500", "8520", "8522", "8540", "8519/2", "8523", "8524", "8575", "8543", "8541/3", "8255/3"));





    public static final Map<String, String> BREAST_2018_TABLE3_SUBTYPES;

    static {
        Map<String, String> content = new HashMap<>();
        content.put("8035", "8035"); // Carcinoma with osteoclastic-like stromal giant cells
        content.put("8562", "8562"); // Epithelial-myoepithelial carcinoma 8562
        content.put("8201", "8201"); // Cribriform carcinoma
        content.put("8022/3", "8022/3"); // Pleomorphic carcinoma
        content.put("8230/2", "8230/2"); // Ductal carcinoma in situ, solid type/intraductal carcinoma, solid type
        content.put("8230/3", "8230/3"); // Solid carcinoma/solid adenocarcinoma
        content.put("8310", "8310"); // Clear cell carcinoma
        content.put("8519/2", "8519/2"); // Pleomorphic lobular carcinoma in situ
        content.put("8513", "8513"); // Atypical medullary carcinoma (AMC)
        content.put("8980/3", "8980/3"); // Carcinosarcoma
        content.put("8572", "8572"); // Fibromatosis-like metaplastic carcinoma
        content.put("8570", "8570"); // Low grade adenosquamous carcinoma
        content.put("8032", "8032"); // Metaplastic carcinoma spindle-cell type/spindle cell carcinoma
        content.put("8571", "8571"); // Metaplastic carcinoma with chondroid differentiation/with osseous differentiation
        content.put("8982", "8982"); // Myoepithelial carcinoma
        content.put("8070", "8070"); // Squamous cell carcinoma
        content.put("8574/3", "8574/3"); // Carcinoma with neuroendocrine differentiation
        content.put("8013/3", "8013/3"); // Large cell neuroendocrine carcinoma/large cell carcinoma
        content.put("8041/3", "8041/3"); // Small cell neuroendocrine carcinoma/small cell carcinoma
        content.put("8249/3", "8249/3"); // Neuroendocrine tumor, grade 2 8249/3
        content.put("8504", "8504"); // Encapsulated papillary carcinoma
        content.put("8507", "8507"); // Micropapillary carcinoma
        content.put("8509", "8509"); // Solid papillary carcinoma
        content.put("9120/3", "9170/3,9130/3"); // Angiosarcoma
        content.put("9170/3", "9170/3"); // Lymphangiosarcoma
        content.put("9130/3", "9130/3");// hemangioendothelioma
        content.put("8850/3", "8850/3"); // Liposarcoma
        content.put("8890/3", "8890/3"); // Leiomyosarcoma
        content.put("9180/3", "9180/3"); // Osteosarcoma
        content.put("8900/3", "8920/3,8910/3,8901/3"); // Rhabdomyosarcoma
        content.put("8920/3", "8920/3"); // Rhabdomyosarcoma - Alveolar type
        content.put("8910/3", "8910/3"); // Rhabdomyosarcoma - Embryonal type
        content.put("8901/3", "8901/3"); // Rhabdomyosarcoma - Pleomorphic

        BREAST_2018_TABLE3_SUBTYPES = Collections.unmodifiableMap(content);
    }

    public static final Map<String, String> BREAST_2018_TABLE3_ROWS;

    static {
        Map<String, String> content = new HashMap<>();
        content.put("8550", "8550"); // Acinic cell carcinoma 8550
        content.put("8200", "8200"); // Adenoid cystic carcinoma (ACC) 8200
        content.put("8983", "8983"); // Adenomyoepithelioma with carcinoma 8983
        content.put("8562", "8983"); // Adenomyoepithelioma with carcinoma 8983
        content.put("8401", "8401"); // Apocrine carcinoma 8401
        content.put("8500", "8500"); // Carcinoma NST 8500
        content.put("8035", "8500"); // Carcinoma NST 8500
        content.put("8201", "8500"); // Carcinoma NST 8500
        content.put("8022/3", "8500"); // Carcinoma NST 8500
        content.put("8230/2", "8500"); // Carcinoma NST 8500
        content.put("8230/3", "8500"); // Carcinoma NST 8500
        content.put("8315", "8315"); // Glycogen-rich clear cell carcinoma 8315
        content.put("8310", "8315"); // Glycogen-rich clear cell carcinoma 8315
        content.put("8530", "8530"); // Inflammatory carcinoma 8530
        content.put("8314", "8314"); // Lipid-rich carcinoma 8314
        content.put("8520", "8520"); // Lobular carcinoma 8520
        content.put("8519/2", "8520"); // Lobular carcinoma 8520
        content.put("8510", "8510"); // Medullary carcinoma 8510
        content.put("8513", "8510"); // Medullary carcinoma 8510
        content.put("8575", "8575"); // Metaplastic carcinoma NOS or of no special type (NST) 8575
        content.put("8980/3", "8575"); // Metaplastic carcinoma NOS or of no special type (NST) 8575
        content.put("8572", "8575"); // Metaplastic carcinoma NOS or of no special type (NST) 8575
        content.put("8570", "8575"); // Metaplastic carcinoma NOS or of no special type (NST) 8575
        content.put("8032", "8575"); // Metaplastic carcinoma NOS or of no special type (NST) 8575
        content.put("8571", "8575"); // Metaplastic carcinoma NOS or of no special type (NST) 8575
        content.put("8982", "8575"); // Metaplastic carcinoma NOS or of no special type (NST) 8575
        content.put("8070", "8575"); // Metaplastic carcinoma NOS or of no special type (NST) 8575
        content.put("8480", "8480"); // Mucinous carcinoma 8480
        content.put("8430", "8430"); // Mucoepidermoid carcinoma 8430
        content.put("8246/3", "8246/3"); //Neuroendocrine carcinoma NOS 8246/3
        content.put("8574/3", "8246/3"); //Neuroendocrine carcinoma NOS 8246/3
        content.put("8013/3", "8246/3"); //Neuroendocrine carcinoma NOS 8246/3
        content.put("8041/3", "8246/3"); //Neuroendocrine carcinoma NOS 8246/3
        content.put("8240/3", "8240/3"); //Neuroendocrine tumor NOS 8240/3
        content.put("8249/3", "8240/3"); //Neuroendocrine tumor NOS 8240/3
        //content.put("8982", "8982"); // Myoepithelial carcinoma 8982
        content.put("8290", "8290"); // Oncocytic carcinoma 8290
        content.put("8540", "8540"); // Paget disease of the nipple with no underlying tumor 8540
        content.put("8503", "8503"); // Papillary carcinoma 8503/3
        content.put("8504", "8503"); // Papillary carcinoma 8503/3
        content.put("8507", "8503"); // Papillary carcinoma 8503/3
        content.put("8509", "8503"); // Papillary carcinoma 8503/3
        content.put("9020/3", "9020/3"); // Periductal stromal tumor, low grade 9020/3
        content.put("8525", "8525"); // Polymorphous carcinoma 8525
        content.put("8800/3", "8800/3"); // Sarcoma NOS 8800
        content.put("9120/3", "8800/3"); // Sarcoma NOS 8800
        content.put("9170/3", "8800/3"); // Sarcoma NOS 8800
        content.put("8850/3", "8800/3"); // Sarcoma NOS 8800
        content.put("8890/3", "8800/3"); // Sarcoma NOS 8800
        content.put("9180/3", "8800/3"); // Sarcoma NOS 8800
        content.put("8900/3", "8800/3"); // Sarcoma NOS 8800
        content.put("8920/3", "8800/3"); // Sarcoma NOS 8800
        content.put("8910/3", "8800/3"); // Sarcoma NOS 8800
        content.put("8901/3", "8800/3"); // Sarcoma NOS 8800
        content.put("9130/3", "8800/3"); // Sarcoma NOS 8800
        content.put("8410", "8410"); // Sebaceous carcinoma 8410
        content.put("8502", "8502"); // Secretory carcinoma 8502
        content.put("8490", "8490"); // Signet ring carcinoma 8490
        content.put("8211", "8211"); // Tubular carcinoma 8211

        BREAST_2018_TABLE3_ROWS = Collections.unmodifiableMap(content);
    }

    //--------------------------------------------------------------------------------------------------------------------------------------
    // Colon
    //--------------------------------------------------------------------------------------------------------------------------------------
    // See Colon 2019 AS OF date at top of this file.

    public static final List<String> COLON_2018_TABLE1_SUBTYPES = Collections.unmodifiableList(Arrays.asList(
            "8200", "8201", "8145", "8142/3", "8510", "8265", "8480", "8430", "8213", "8490", "8143", "8263", "8020",
            "8243", "8013", "8041", "8241", "8249", "8156", "9120/3", "8890/3"));

    public static final Map<String, String> COLON_2018_TABLE1_ROWS;

    static {
        Map<String, String> content = new HashMap<>();
        content.put("8140", "8140"); // Adenocarcinoma 8140
        content.put("8200", "8140"); // Adenocarcinoma 8140
        content.put("8201", "8140"); // Adenocarcinoma 8140
        content.put("8145", "8140"); // Adenocarcinoma 8140
        content.put("8142/3", "8140"); // Adenocarcinoma 8140
        content.put("8510", "8140"); // Adenocarcinoma 8140
        content.put("8265", "8140"); // Adenocarcinoma 8140
        content.put("8480", "8140"); // Adenocarcinoma 8140
        content.put("8430", "8140"); // Adenocarcinoma 8140
        content.put("8213", "8140"); // Adenocarcinoma 8140
        content.put("8490", "8140"); // Adenocarcinoma 8140
        content.put("8143", "8140"); // Adenocarcinoma 8140
        content.put("8263", "8140"); // Adenocarcinoma 8140
        content.put("8020", "8140"); // Adenocarcinoma 8140
        content.put("8560", "8560"); // Adenosquamous carcinoma 8560
        content.put("8045", "8045"); // Combined small cell carcinoma 8045
        content.put("8153", "8153"); // Gastrinoma 8153
        content.put("8936/3", "8936/3"); // Gastrointestinal stromal tumor classified as malignant 8936
        content.put("8244", "8244"); // Mixed adenoneuroendocrine carcinoma 8244
        content.put("8243", "8244"); // Mixed adenoneuroendocrine carcinoma 8244
        content.put("8154", "8154"); // MiNEN
        content.put("8246", "8246"); // Neuroendocrine carcinoma 8246
        content.put("8013", "8246"); // Neuroendocrine carcinoma 8246
        content.put("8041", "8246"); // Neuroendocrine carcinoma 8246
        content.put("8240", "8240"); // Neuroendocrine tumor Grade 1 (G1) 8240
        content.put("8241", "8240"); // Neuroendocrine tumor Grade 1 (G1) 8240
        content.put("8249", "8240"); // Neuroendocrine tumor Grade 1 (G1) 8240
        content.put("8156", "8240"); // Neuroendocrine tumor Grade 1 (G1) 8240
        content.put("8800/3", "8800/3"); // Sarcoma NOS 8800/3
        content.put("9120/3", "8800/3"); // Sarcoma NOS 8800/3
        content.put("8890/3", "8800/3"); // Sarcoma NOS 8800/3
        content.put("8032", "8032"); // Spindle cell carcinoma 8032
        content.put("8070", "8070"); // Squamous cell carcinoma 8070
        COLON_2018_TABLE1_ROWS = Collections.unmodifiableMap(content);
    }

    //--------------------------------------------------------------------------------------------------------------------------------------
    // Kidney
    //--------------------------------------------------------------------------------------------------------------------------------------
    // See Kidney 2019 AS OF date at top of this file.

    public static final Map<String, String> KIDNEY_2018_TABLE1_SUBTYPES;

    static {
        Map<String, String> content = new HashMap<>();
        content.put("8693/3", "8693/3"); //Extra-adrenal paraganglioma 8693/3
        content.put("8013/3", "8013/3"); //Large cell neuroendocrine carcinoma/tumor
        content.put("8041/3", "8041/3"); //Small cell neuroendocrine carcinoma
        content.put("8316", "8316"); //Acquired cystic disease-associated renal cell carcinoma/tubulocystic renal cell carcinoma
        content.put("8317", "8317"); //Chromophobe renal cell carcinoma (ChRCC)
        content.put("8323/3", "8323/3"); //Clear cell papillary renal cell carcinoma
        content.put("8310", "8310"); //Clear cell renal cell carcinoma (ccRCC)
        content.put("8319", "8319"); //Collecting duct carcinoma
        content.put("8311", "8311"); //Hereditary leiomyomatosis and renal cell carcinomaassociated renal cell carcinoma
        content.put("8480", "8480"); //Mucinous tubular and spindle cell carcinoma
        content.put("8260", "8260"); //Papillary renal cell carcinoma
        content.put("9120/3", "9120/3"); // Angiosarcoma
        content.put("8964/3", "8964/3"); // Clear cell sarcoma/bone-metastasizing renal tumor of childhood
        content.put("8890/3", "8890/3"); // Leiomyosarcoma/renal vein leiomyosarcoma
        content.put("9180/3", "9180/3"); //Osteosarcoma
        content.put("9364/3", "9364/3"); //Primitive/peripheral neuroectodermal tumor (pNET)/Ewing sarcoma
        content.put("8900/3", "8920/3,8910/3,8901/3,8912/3"); //Rhabdomyosarcoma
        content.put("8920/3", "8920/3"); //Rhabdomyosarcoma
        content.put("8910/3", "8910/3"); //Rhabdomyosarcoma
        content.put("8901/3", "8901/3"); //Rhabdomyosarcoma
        content.put("8912/3", "8912/3"); //Rhabdomyosarcoma
        content.put("9040/3", "9040/3"); //Synovial sarcoma
        KIDNEY_2018_TABLE1_SUBTYPES = Collections.unmodifiableMap(content);
    }

    public static final Map<String, String> KIDNEY_2018_TABLE1_ROWS;

    static {
        Map<String, String> content = new HashMap<>();
        content.put("8510", "8510"); // Medullary carcinoma 8510
        content.put("8960", "8960"); // Nephroblastoma 8960
        content.put("8693/3", "8240/3"); // Neuroendocrine tumor (NET) 8240
        content.put("8041/3", "8240/3"); // Neuroendocrine tumor (NET) 8240
        content.put("8013/3", "8240/3"); // Neuroendocrine tumor (NET) 8240
        content.put("8240/3", "8240/3"); // Neuroendocrine tumor (NET) 8240
        content.put("8312", "8312"); // Renal cell carcinoma NOS 8312
        content.put("8316", "8312"); // Renal cell carcinoma NOS 8312
        content.put("8317", "8312"); // Renal cell carcinoma NOS 8312
        content.put("8323/3", "8312"); // Renal cell carcinoma NOS 8312
        content.put("8310", "8312"); // Renal cell carcinoma NOS 8312
        content.put("8319", "8312"); // Renal cell carcinoma NOS 8312
        content.put("8311", "8312"); // Renal cell carcinoma NOS 8312
        content.put("8480", "8312"); // Renal cell carcinoma NOS 8312
        content.put("8260", "8312"); // Renal cell carcinoma NOS 8312
        content.put("8800/3", "8800/3"); // Sarcoma 8800/3
        content.put("9120/3", "8800/3"); // Sarcoma 8800/3
        content.put("8964/3", "8800/3"); // Sarcoma 8800/3
        content.put("8890/3", "8800/3"); // Sarcoma 8800/3
        content.put("9180/3", "8800/3"); // Sarcoma 8800/3
        content.put("9364/3", "8800/3"); // Sarcoma 8800/3
        content.put("8900/3", "8800/3"); // Sarcoma 8800/3
        content.put("8920/3", "8800/3"); // Sarcoma 8800/3
        content.put("8910/3", "8800/3"); // Sarcoma 8800/3
        content.put("8901/3", "8800/3"); // Sarcoma 8800/3
        content.put("8912/3", "8800/3"); // Sarcoma 8800/3
        content.put("9040/3", "8800/3"); // Sarcoma 8800/3
        KIDNEY_2018_TABLE1_ROWS = Collections.unmodifiableMap(content);
    }

    //--------------------------------------------------------------------------------------------------------------------------------------
    // Urinary
    //--------------------------------------------------------------------------------------------------------------------------------------
    // See Urinary 2019 AS OF date at top of this file.

    public static final List<String> URINARY_2018_URINARY_SITES = Collections.unmodifiableList(
            Arrays.asList("C659", "C669", "C670", "C671", "C672", "C673", "C674", "C675", "C676", "C677", "C678", "C679", "C680"));

    public static final List<String> URINARY_2018_UROTHELIAL_CARCINOMAS = Collections.unmodifiableList(Arrays.asList("8120", "8031", "8082", "8130", "8131", "8020", "8122"));

    public static final List<String> URINARY_2018_UROTHELIAL_CARCINOMAS_EXCLUDE_MICROPAPILLARY = Collections.unmodifiableList(Arrays.asList("8120", "8031", "8082", "8130", "8020", "8122"));

    public static final Map<String, String> URINARY_2018_TABLE2_SUBTYPES;

    static {
        Map<String, String> content = new HashMap<>();
        content.put("8310", "8310");
        content.put("8380", "8380");
        content.put("8144", "8144");
        content.put("8480", "8480");
        content.put("8013/3", "8013/3");
        content.put("8041/3", "8041/3");
        content.put("8249/3", "8249/3");
        content.put("9120/3", "9120/3");
        content.put("9220/3", "9220/3");
        content.put("8890/3", "8890/3");
        content.put("8850/3", "8850/3");
        content.put("9540/3", "9540/3");
        content.put("8802/3", "8802/3");
        content.put("8900/3", "8910/3");
        content.put("8051", "8051");
        content.put("8031/3", "8031/3");
        content.put("8082/3", "8082/3");
        content.put("8130", "8130");
        content.put("8131/3", "8131/3");
        content.put("8020/3", "8020/3");
        content.put("8122/3", "8122/3");
        URINARY_2018_TABLE2_SUBTYPES = Collections.unmodifiableMap(content);
    }

    public static final Map<String, String> URINARY_2018_TABLE2_ROWS;

    static {
        Map<String, String> content = new HashMap<>();
        content.put("8140", "8140"); // Carcinoma NOS 8010
        content.put("8310", "8140"); // Carcinoma NOS 8010
        content.put("8380", "8140"); // Carcinoma NOS 8010
        content.put("8144", "8140"); // Carcinoma NOS 8010
        content.put("8480", "8140"); // Carcinoma NOS 8010
        content.put("8720/3", "8720/3"); // Malignant melanoma 8720/3
        content.put("8714/3", "8714/3"); // Malignant perivascular epithelioid cell tumor 8714/3
        content.put("8246/3", "8246/3"); // Neuroendocrine carcinoma, NOS 8246/3
        content.put("8013/3", "8246/3"); // Neuroendocrine carcinoma, NOS 8246/3
        content.put("8041/3", "8246/3"); // Neuroendocrine carcinoma, NOS 8246/3
        content.put("8240/3", "8240/3"); // Neuroendocrine tumor, NOS 8240/3
        content.put("8249/3", "8240/3"); // Neuroendocrine tumor, NOS 8240/3
        content.put("8800/3", "8800/3"); // Sarcoma NOS 8800/3
        content.put("9120/3", "8800/3"); // Sarcoma NOS 8800/3
        content.put("9220/3", "8800/3"); // Sarcoma NOS 8800/3
        content.put("8890/3", "8800/3"); // Sarcoma NOS 8800/3
        content.put("8850/3", "8800/3"); // Sarcoma NOS 8800/3
        content.put("9540/3", "8800/3"); // Sarcoma NOS 8800/3
        content.put("8802/3", "8800/3"); // Sarcoma NOS 8800/3
        content.put("8900/3", "8800/3"); // Sarcoma NOS 8800/3
        content.put("8910/3", "8800/3"); // Sarcoma NOS 8800/3
        content.put("8070", "8070"); // Squamous cell carcinoma 8070
        content.put("8051", "8070"); // Squamous cell carcinoma 8070
        content.put("8120", "8120"); // Urothelial carcinoma 8120
        content.put("8031/3", "8120"); // Urothelial carcinoma 8120
        content.put("8082/3", "8120"); // Urothelial carcinoma 8120
        content.put("8130/2", "8120"); // Urothelial carcinoma 8120
        content.put("8130/3", "8120"); // Urothelial carcinoma 8120
        content.put("8131/3", "8120"); // Urothelial carcinoma 8120
        content.put("8020/3", "8120"); // Urothelial carcinoma 8120
        content.put("8122/3", "8120"); // Urothelial carcinoma 8120
        URINARY_2018_TABLE2_ROWS = Collections.unmodifiableMap(content);
    }

    //--------------------------------------------------------------------------------------------------------------------------------------
    // Lung
    //--------------------------------------------------------------------------------------------------------------------------------------
    // See Lung 2019 AS OF date at top of this file.

    public static final List<String> LUNG_2018_8046_AND_SUBTYPES = Collections.unmodifiableList(Arrays.asList(
            "8012", "8022", "8023", "8031", "8032", "8046", "8070", "8071", "8072", "8082", "8083", "8140", "8144", "8200", "8230", "8250",
            "8253", "8254", "8256", "8257", "8260", "8265", "8333", "8430", "8480", "8551", "8560", "8562", "8580",
            "8714", "8720", "8800", "8842", "8982", "9040", "9041", "9042", "9043", "9133", "9173"));

    public static final List<String> LUNG_2018_8041_AND_SUBTYPES = Collections.unmodifiableList(Arrays.asList("8041", "8249", "8045", "8013", "8240"));

    public static final List<String> LUNG_2018_TABLE2 = Collections.unmodifiableList(Arrays.asList("8560", "8033", "8562", "8013", "8254/3", "8045", "8072", "8073", "8070", "8074", "8255/3"));

    public static final Map<String, String> LUNG_2018_TABLE3_SUBTYPES;

    static {
        Map<String, String> content = new HashMap<>();
        content.put("8551", "8551");
        content.put("8200", "8200");
        content.put("8480", "8480");
        content.put("8144", "8144");
        content.put("8333", "8333");
        content.put("8250/3", "8250/3");
        content.put("8253", "8253");
        content.put("8257/3", "8257/3");
        content.put("8265", "8265");
        content.put("8254", "8254");
        content.put("8256/3", "8256/3");
        content.put("8250/2", "8250/2");
        content.put("8260", "8260");
        content.put("8230", "8230");
        content.put("8045", "8045");
        content.put("8041", "8041");
        content.put("8249", "8249");
        content.put("9043/3", "9043/3");
        content.put("9042/3", "9042/3");
        content.put("9137/3", "9137/3");
        content.put("8842/3", "8842/3");
        content.put("9041/3", "9041/3");
        content.put("9040/3", "9040/3");
        content.put("8083", "8083");
        content.put("8071", "8071");
        content.put("8072", "8072");
        LUNG_2018_TABLE3_SUBTYPES = Collections.unmodifiableMap(content);
    }

    public static final Map<String, String> LUNG_2018_TABLE3_ROWS;

    static {
        Map<String, String> content = new HashMap<>();
        content.put("8140", "8140"); //Adenocarcinoma 8140
        content.put("8551", "8140"); //Adenocarcinoma 8140
        content.put("8200", "8140"); //Adenocarcinoma 8140
        content.put("8480", "8140"); //Adenocarcinoma 8140
        content.put("8144", "8140"); //Adenocarcinoma 8140
        content.put("8333", "8140"); //Adenocarcinoma 8140
        content.put("8250/3", "8140"); //Adenocarcinoma 8140
        content.put("8253/2", "8140"); //Adenocarcinoma 8140
        content.put("8253/3", "8140"); //Adenocarcinoma 8140
        content.put("8257/3", "8140"); //Adenocarcinoma 8140
        content.put("8265", "8140"); //Adenocarcinoma 8140
        content.put("8254", "8140"); //Adenocarcinoma 8140
        content.put("8250/2", "8140"); //Adenocarcinoma 8140
        content.put("8256/3", "8140"); //Adenocarcinoma 8140
        content.put("8260", "8140"); //Adenocarcinoma 8140
        content.put("8230", "8140"); //Adenocarcinoma 8140
        content.put("8560", "8560"); // Adenosquamous carcinoma 8560
        content.put("8980/3", "8980/3"); // Carcinosarcoma 8980/3
        content.put("9170/3", "9170/3"); // Diffuse pulmonary lymphangiomatosis
        content.put("8562", "8562"); // Epithelial-myoepithelial carcinoma 8562
        content.put("9133", "9133"); // Epithelioid hemangioepithelioma 9133
        content.put("8031", "8031"); // Giant cell carcinoma 8031
        content.put("8310", "8310"); //Hyalinizing clear cell carcinoma 8310
        content.put("8580/3", "8580/3"); // Intrapulmonary thymoma (arising within lung) 8580/3
        content.put("8012", "8012"); // Large cell carcinoma 8012
        content.put("9174/3", "9174/3"); // Lymphoangioleiomyomatosis 9174/3
        content.put("8013", "8013"); // Large cell neuroendocrine carcinoma 8013
        content.put("8082", "8082"); // Lymphoepithelioma-like carcinoma 8082
        content.put("8720", "8720"); // Melanoma 8720
        content.put("8430", "8430"); // Mucoepidermoid carcinoma 8430
        content.put("8982", "8982"); // Myoepithelial carcinoma 8982
        content.put("8246", "8246"); //Neuroendocrine carcinoma (NEC) 8246
        content.put("8045", "8246"); //Neuroendocrine carcinoma (NEC) 8246
        content.put("8041", "8246"); //Neuroendocrine carcinoma (NEC) 8246
        content.put("8240", "8240"); //Neuroendocrine tumor, NOS (NET) 8240
        content.put("8249", "8240"); //Neuroendocrine tumor, NOS (NET) 8240
        content.put("8023/3", "8023/3"); // NUT carcinoma 8023/3
        content.put("8714/3", "8714/3"); // PEComa malignant 8714/3
        content.put("8022", "8022"); // Pleomorphic carcinoma 8022
        content.put("8973/3", "8973/3"); // Pleuropulmonary blastoma 8973/3
        content.put("8972/3", "8972/3"); // Pulmonary blastoma 8972/3
        content.put("8800/3", "8800/3"); // Sarcoma NOS 8800/3
        content.put("9043/3", "8800/3"); // Sarcoma NOS 8800/3
        content.put("9042/3", "8800/3"); // Sarcoma NOS 8800/3
        content.put("9137/3", "8800/3"); // Sarcoma NOS 8800/3
        content.put("8842/3", "8800/3"); // Sarcoma NOS 8800/3
        content.put("9041/3", "8800/3"); // Sarcoma NOS 8800/3
        content.put("9040/3", "8800/3"); // Sarcoma NOS 8800/3
        content.put("8032", "8032"); // Spindle cell carcinoma 8032
        content.put("8070", "8070"); // Squamous carcinoma 8070
        content.put("8083", "8070"); // Squamous carcinoma 8070
        content.put("8071", "8070"); // Squamous carcinoma 8070
        content.put("8072", "8070"); // Squamous carcinoma 8070
        content.put("8044/3", "8044/3"); // Thoracic SMARCA4-deficient undifferentiated tumor 8044/3
        LUNG_2018_TABLE3_ROWS = Collections.unmodifiableMap(content);
    }

    //--------------------------------------------------------------------------------------------------------------------------------------
    // General Brain Sites
    //--------------------------------------------------------------------------------------------------------------------------------------
    public static final String CNS_2018_BRAIN_SITES = "C710-C719";
    public static final String CNS_2018_CAUDA_EQUINA = "C721";
    public static final String CNS_2018_CEREBRAL_MENINGES_SITES = "C700";
    public static final String CNS_2018_SPINAL_MENINGES_SITES = "C701";
    public static final String CNS_2018_SPINAL_CORD_SITES = "C720";
    public static final String CNS_2018_CRANIAL_NERVES_SITES_NON_CAUDA_EQUINA = "C722-C725";
    public static final String CNS_2018_MENINGES_OF_CRANIAL_OR_PERIPH_NERVES_SITES = "C709";

    public static final List<String> CNS_2018_MENINGIOMAS = Collections.unmodifiableList(Arrays.asList(
            "9530/0", "9534/0", "9539/1", "9538/1", "9532/0", "9531/0", "9537/0"));

    //--------------------------------------------------------------------------------------------------------------------------------------
    // Non-Malignant CNS
    //--------------------------------------------------------------------------------------------------------------------------------------
    // See Non-Malignant CNS 2019 AS OF date at top of this file.

    public static final List<String> NON_MALIGNANT_CNS_2018_TABLE6_SUBTYPES = Collections.unmodifiableList(Arrays.asList(
            "9390/1", "9351/1", "9352/1", "9431/1", "9493/0", "9121/0", "9122/0", "8880/0", "8728/1", "9534/0", "9539/1", "9538/1", "9532/0", "9531/0", "9533/0", "9537/0",
            "8825/1", "9550/0", "9560/1"));

    public static final Map<String, String> NON_MALIGNANT_CNS_2018_TABLE6_ROWS;
    public static final Map<String, String> NON_MALIGNANT_CNS_2023_TABLE6_ROWS;

    static {
        Map<String, String> content = new HashMap<>();
        content.put("8830/0", "8830/0"); // Benign fibrous histiocytoma 8830/0
        content.put("9506/1", "9506/1"); // Central neurocytoma 9506/1
        content.put("9220/0", "9220/0"); // Chondroma 9220/0
        content.put("9444/1", "9444/1"); // Chordoid glioma of the third ventricle 9444/1
        content.put("9390/0", "9390/0"); // Choroid plexus papilloma 9390/0
        content.put("9390/1", "9390/0"); // Choroid plexus papilloma 9390/0
        content.put("9350/1", "9350/1"); // Craniopharyngioma 9350/1
        content.put("9351/1", "9350/1"); // Craniopharyngioma 9350/1
        content.put("9352/1", "9350/1"); // Craniopharyngioma 9350/1
        content.put("9412/1", "9412/1"); // Desmoplastic infantile astrocytoma and ganglioglioma 9412/1
        content.put("9421/1", "9421/1"); // Diffuse astrocytoma, MYB- or MYBL1 altered
        content.put("9431/1", "9421/1"); // Angiocentric glioma 9431/1
        content.put("9413/0", "9413/0"); // Dysembryoplastic neuroepithelial tumor 9413/0
        content.put("9492/0", "9492/0"); // Gangliocytoma 9492/0
        content.put("9493/0", "9492/0"); // Gangliocytoma 9492/0
        content.put("9505/1", "9505/1"); // Ganglioglioma 9505/1
        content.put("9582/0", "9582/0"); // Granular cell tumor of the sellar region 9582/0
        content.put("9161/1", "9161/1"); // Hemangioblastoma 9161/1
        content.put("9121/0", "9120/0"); // Hemangioma 9120/0
        content.put("9122/0", "9120/0"); // Hemangioma 9120/0
        content.put("9120/0", "9120/0"); // Hemangioma 9120/0
        content.put("9749/1", "9749/1"); // Juvenile xanthogranuloma
        content.put("8890/0", "8890/0"); // Leiomyoma 8890/0
        content.put("8850/0", "8850/0"); // Lipoma 8860/0
        content.put("9380/1", "9380/1"); // Low-grade glioma 9380/1
        content.put("8880/0", "8850/0"); // Lipoma 8860/0
        content.put("8728/0", "8728/0"); // Meningeal melanocytosis 8728/0
        content.put("8728/1", "8728/0"); // Meningeal melanocytosis 8728/0
        content.put("9530/0", "9530/0"); // Meningioma 9530/0
        content.put("9534/0", "9530/0"); // Meningioma 9530/0
        content.put("9539/1", "9530/0"); // Meningioma 9530/0
        content.put("9538/1", "9530/0"); // Meningioma 9530/0
        content.put("9532/0", "9530/0"); // Meningioma 9530/0
        content.put("9531/0", "9530/0"); // Meningioma 9530/0
        content.put("9533/0", "9530/0"); // Meningioma 9530/0
        content.put("9537/0", "9530/0"); // Meningioma 9530/0
        content.put("9509/0", "9509/0"); // Multinodular and vacuolating neuronal tumor
        content.put("8825/0", "8825/0"); // Myofibroblastoma 8825/0
        content.put("8825/1", "8825/0"); // Myofibroblastoma 8825/0
        content.put("9394/1", "9394/1"); // Myxopapillary ependymoma 9394/1
        content.put("9506/1", "9506/1"); // Neurocytoma
        content.put("8000/0", "8000/0"); // Neuroepithelial tumor, benign
        content.put("8000/1", "8000/0"); // Neuroepithelial tumor, benign
        content.put("9540/0", "9540/0"); // Neurofibroma 9540/0
        content.put("9550/0", "9540/0"); // Neurofibroma 9540/0
        content.put("9421/1", "9421/1"); // Optic glioma/pilocytic astrocytoma 9421/1
        content.put("9509/1", "9509/1"); // Papillary glioneuronal tumor 9509/1
        content.put("9180/0", "9180/0"); // Osteoma 9180/0
        content.put("9509/1", "9509/1"); // Papillary glioneuronal tumor 9509/1
        content.put("8693/1", "8693/1"); // Paraganglioma 8693/1
        content.put("9571/0", "9571/0"); // Perineurioma 9571/0
        content.put("9361/1", "9361/1"); // Pineocytoma 9361/1
        content.put("9432/1", "9432/1"); // Pituicytoma 9432/1
        content.put("8272/0", "8272/0"); // Pituitary adenoma 8272/0
        content.put("9413/0", "9413/0"); // Polymorphous low-grade neuroepithelial tumor of the young 9413/0
        content.put("8271/0", "8271/0"); // Pituitary adenoma 8271/0
        content.put("8900/0", "8900/0"); // Rhabdomyoma 8900/0
        content.put("9560/0", "9560/0"); // Schwannoma 9560/0
        content.put("9560/1", "9560/0"); // Schwannoma 9560/0
        content.put("8815/1", "8815/1"); // Solitary fibrous tumor Grade 1
        content.put("8290/0", "8290/0"); // Spindle cell oncocytoma 8290/0
        content.put("9384/1", "9384/1"); // Subependymal giant cell astrocytoma 9384/1
        content.put("9383/1", "9383/1"); // Subependymoma 9383/1
        content.put("9080/1", "9080/1"); // Teratoma 9080/1
        NON_MALIGNANT_CNS_2018_TABLE6_ROWS = Collections.unmodifiableMap(content);
        content.remove("9180/0");
        NON_MALIGNANT_CNS_2023_TABLE6_ROWS = Collections.unmodifiableMap(content);
    }

    //--------------------------------------------------------------------------------------------------------------------------------------
    // Malignant CNS
    //--------------------------------------------------------------------------------------------------------------------------------------
    public static final Map<String, String> MALIGNANT_CNS_2018_TABLE3_SUBTYPES;

    static {
        Map<String, String> content = new HashMap<>();
        content.put("9401", "9401");
        content.put("9411", "9411");
        content.put("9424", "9424");
        content.put("9473", "9473");
        content.put("9071", "9071");
        content.put("9392", "9392");
        content.put("9396", "9396");
        content.put("9393", "9393");
        content.put("9441", "9441");
        content.put("9445", "9445");
        content.put("9442", "9442");
        content.put("9085", "9085");
        content.put("9084", "9084");
        content.put("9538", "9538");
        content.put("9542", "9542");
        content.put("9474", "9474");
        content.put("9471", "9471");
        content.put("9477", "9477");
        content.put("9476", "9476");
        content.put("9475", "9475");
        content.put("9451", "9451");
        content.put("9425", "9425");
        content.put("9395", "9395");
        content.put("9120", "9120");
        content.put("9220", "9220");
        content.put("9240", "9220");
        content.put("8890", "8891,8896");
        content.put("8891", "8891");
        content.put("8896", "8896");
        content.put("8859", "8859");
        content.put("9180", "9180");
        content.put("9480", "9480");
        content.put("9366", "9366");
        content.put("9268", "9268");
        content.put("8802", "8802");
        MALIGNANT_CNS_2018_TABLE3_SUBTYPES = Collections.unmodifiableMap(content);
    }

    public static final Map<String, String> MALIGNANT_CNS_2018_TABLE3_ROWS;

    static {
        Map<String, String> content = new HashMap<>();
        content.put("9505", "9505"); // Anaplastic ganglioglioma 9505
        content.put("9430", "9430"); // Astroblastoma 9430
        content.put("9400", "9400"); // Astrocytoma NOS 9400
        content.put("9401", "9400"); // Astrocytoma NOS 9400
        content.put("9411", "9400"); // Astrocytoma NOS 9400
        content.put("9424", "9400"); // Astrocytoma NOS 9400
        content.put("8693/3", "8693/3"); // Cauda equina neuroendocrine tumor
        content.put("9100", "9100"); // Choriocarcinoma 9100
        content.put("9390", "9390"); // Choroid plexus carcinoma 9390
        content.put("9508", "9508"); // CNS embryonal tumor with rhabdoid features 9508
        content.put("9490", "9490"); // CNS ganglioneuroblastoma 9490
        content.put("9473", "9490"); // CNS ganglioneuroblastoma 9490
        content.put("9500", "9500"); // CNS neuroblastoma 9500
        content.put("9509", "9509"); // Diffuse leptomeningeal glioneuronal  tumor
        content.put("9385", "9385"); // Diffuse midline glioma H3 K27M mutant 9385*
        content.put("9070", "9070"); // Embryonal carcinoma 9070
        content.put("9071", "9070"); // Embryonal carcinoma 9070
        content.put("9478", "9478"); // Embryonal tumor with multilayered rosettes C19MC-altered 9478*
        content.put("9391", "9391"); // Ependymoma 9391
        content.put("9392", "9391"); // Ependymoma 9391
        content.put("9396", "9391"); // Ependymoma 9391
        content.put("9393", "9391"); // Ependymoma 9391
        content.put("9133", "9133"); // Epithelioid hemangioendothelioma 9133
        content.put("9064", "9064"); // Germinoma 9064
        content.put("9440", "9440"); // Glioblastoma multiforme 9440
        content.put("9441", "9440"); // Glioblastoma multiforme 9440
        content.put("9445", "9440,9400"); // Glioblastoma multiforme 9440
        content.put("9442", "9440"); // Glioblastoma multiforme 9440
        content.put("9380", "9380"); // Glioma, NOS 9380
        content.put("9421/3", "9421/3"); // High-grade astrocytoma with piloid features
        content.put("9080", "9080"); // Immature teratoma 9080
        content.put("9085", "9080"); // Immature teratoma 9080
        content.put("9084", "9080"); // Immature teratoma 9080
        content.put("9530", "9530"); // Malignant meningioma 9530
        content.put("9538", "9530"); // Malignant meningioma 9530
        content.put("9540", "9540"); // Malignant peripheral nerve sheath tumor 9540
        content.put("9542", "9540"); // Malignant peripheral nerve sheath tumor 9540
        content.put("9470", "9470"); // Medulloblastoma NOS 9470
        content.put("9474", "9470"); // Medulloblastoma NOS 9470
        content.put("9471", "9470"); // Medulloblastoma NOS 9470
        content.put("9477", "9470"); // Medulloblastoma NOS 9470
        content.put("9476", "9470"); // Medulloblastoma NOS 9470
        content.put("9475", "9470"); // Medulloblastoma NOS 9470
        content.put("9501", "9501"); // Medulloepithelioma 9501
        content.put("8720", "8720"); // Meningeal melanoma 8720
        content.put("8000/3", "8000/3"); // Neuroepithelial tumor, malignant
        content.put("9382", "9382"); // Oligoastrocytoma NOS 9382
        content.put("9450", "9450"); // Oligodendroglioma NOS 9450
        content.put("9451", "9450"); // Oligodendroglioma NOS 9450
        content.put("9364", "9364"); // Peripheral primitive neuroectodermal tumor 9364
        content.put("9421", "9421"); // Pilocytic astrocytoma 9421
        content.put("9425", "9421"); // Pilocytic astrocytoma 9421
        content.put("9362", "9362"); // Pineal parenchymal tumor of intermediate differentiation 9362
        content.put("9365", "9362"); // Pineal parenchymal tumor of intermediate differentiation 9362
        content.put("8272/3", "8272/3");
        content.put("8800", "8800"); // Sarcoma NOS 8800
        content.put("9120", "8800"); // Sarcoma NOS 8800
        content.put("9220", "8800"); // Sarcoma NOS 8800
        content.put("9240", "8800"); // Sarcoma NOS 8800
        content.put("8890", "8800"); // Sarcoma NOS 8800
        content.put("8891", "8800"); // Sarcoma NOS 8800
        content.put("8896", "8800"); // Sarcoma NOS 8800
        content.put("8859", "8800"); // Sarcoma NOS 8800
        content.put("9180", "8800"); // Sarcoma NOS 8800
        content.put("9480", "8800"); // Sarcoma NOS 8800
        content.put("9366", "8800"); // Sarcoma NOS 8800
        content.put("9268", "8800"); // Sarcoma NOS 8800
        content.put("8802", "8800"); // Sarcoma NOS 8800
        content.put("8815/3", "8815/3"); // Solitary fibrous tumor grade 3 8815
        MALIGNANT_CNS_2018_TABLE3_ROWS = Collections.unmodifiableMap(content);
    }

    //--------------------------------------------------------------------------------------------------------------------------------------
    // Head and Neck
    //--------------------------------------------------------------------------------------------------------------------------------------
    // See Head and Neck 2018 AS OF date at top of this file.

    //Changes made to this list per Sewbesew's request and Suzanne's approval
    public static final List<String> HEAD_AND_NECK_2018_PAIRED_SITES = Collections.unmodifiableList(Arrays.asList("C079", "C080,C081", "C098,C099", "C300", "C301", "C310,C312", "C754"));

    private static final List<String> HEAD_AND_NECK_2018_TABLE1_SITES = Collections.unmodifiableList(Arrays.asList(
            "C300", "C310", "C311", "C312", "C313", "C318", "C319"));
    private static final List<String> HEAD_AND_NECK_2018_TABLE2_SITES = Collections.unmodifiableList(Arrays.asList(
            "C110", "C112", "C113", "C118", "C119"));
    public static final List<String> HEAD_AND_NECK_2018_TABLE3_SITES = Collections.unmodifiableList(Arrays.asList(
            "C129", "C130", "C131", "C132", "C138", "C139", "C320", "C321", "C322", "C323", "C328", "C329", "C339"));

    private static final List<String> HEAD_AND_NECK_2018_TABLE4_SITES = Collections.unmodifiableList(Arrays.asList(
            "C020", "C021", "C022", "C023", "C028", "C029",
            "C030", "C031", "C039", "C040", "C041", "C048", "C049",
            "C050", "C051", "C052", "C058", "C059", "C060", "C061", "C062", "C068", "C069"));
    private static final List<String> HEAD_AND_NECK_2018_TABLE5_SITES = Collections.unmodifiableList(Arrays.asList(
            "C024", "C100", "C101", "C102", "C103", "C104", "C108", "C109", "C019", "C090", "C091", "C098", "C099"));
    private static final List<String> HEAD_AND_NECK_2018_TABLE6_SITES = Collections.unmodifiableList(Arrays.asList(
            "C079", "C080", "C081", "C088", "C089"));
    private static final List<String> HEAD_AND_NECK_2018_TABLE7_SITES = Collections.unmodifiableList(Arrays.asList(
            "C410", "C411"));
    private static final List<String> HEAD_AND_NECK_2018_TABLE8_SITES = Collections.singletonList("C301");
    public static final List<String> HEAD_AND_NECK_2018_TABLE9_SITES = Collections.unmodifiableList(Arrays.asList("C479", "C754", "C755"));
    private static final List<String> HEAD_AND_NECK_2018_TABLEC111_SITES = Collections.singletonList("C111");


    public static final Map<String, Map<String, String>> HEAD_AND_NECK_2018_SUBTYPES_FOR_SITE;

    static {

        Map<String, Map<String, String>> content = new HashMap<>();

        // Table 1 ----------------------------------------
        Map<String, String> thisTableSubTypes = new HashMap<>();
        thisTableSubTypes.put("8144", "8144");
        thisTableSubTypes.put("9560/3", "9560/3");
        thisTableSubTypes.put("9521/3", "9521/3");
        thisTableSubTypes.put("9523/3", "9523/3");
        thisTableSubTypes.put("8121", "8121");
        thisTableSubTypes.put("9120/3", "9120/3");
        thisTableSubTypes.put("9045/3", "9045/3");
        thisTableSubTypes.put("9133/3", "9133/3");
        thisTableSubTypes.put("8810/3", "8810/3");
        thisTableSubTypes.put("8890/3", "8890/3");
        thisTableSubTypes.put("8900/3", "8920/3,8910/3,8901/3,8912/3");
        thisTableSubTypes.put("8920/3", "8920/3");
        thisTableSubTypes.put("8910/3", "8910/3");
        thisTableSubTypes.put("8901/3", "8901/3");
        thisTableSubTypes.put("8912/3", "8912/3");
        thisTableSubTypes.put("9040/3", "9040/3");
        thisTableSubTypes.put("8802/3", "8802/3");
        thisTableSubTypes.put("8083", "8083");
        thisTableSubTypes.put("8071", "8071");
        thisTableSubTypes.put("8052", "8052");
        thisTableSubTypes.put("8074", "8074");
        thisTableSubTypes.put("8072", "8072");
        thisTableSubTypes.put("8051", "8051");
        for (String site : HEAD_AND_NECK_2018_TABLE1_SITES)
            content.put(site, thisTableSubTypes);

        // Table 2 ----------------------------------------
        thisTableSubTypes = new HashMap<>();
        thisTableSubTypes.put("8083", "8083");
        thisTableSubTypes.put("8071", "8071");
        thisTableSubTypes.put("8072", "8072");
        thisTableSubTypes.put("8082", "8082");
        thisTableSubTypes.put("8020", "8020");
        for (String site : HEAD_AND_NECK_2018_TABLE2_SITES)
            content.put(site, thisTableSubTypes);

        // Table 3 ----------------------------------------
        thisTableSubTypes = new HashMap<>();
        thisTableSubTypes.put("9222/3", "9222/3");
        thisTableSubTypes.put("8851/3", "8851/3");
        thisTableSubTypes.put("8013/3", "8013/3");
        thisTableSubTypes.put("8249/3", "8249/3");
        thisTableSubTypes.put("8041/3", "8041/3");
        thisTableSubTypes.put("8560", "8560");
        thisTableSubTypes.put("8083", "8083");
        thisTableSubTypes.put("8082", "8082");
        thisTableSubTypes.put("8071", "8071");
        thisTableSubTypes.put("8072", "8072");
        thisTableSubTypes.put("8052", "8052");
        thisTableSubTypes.put("8074", "8074");
        thisTableSubTypes.put("8051", "8051");
        for (String site : HEAD_AND_NECK_2018_TABLE3_SITES)
            content.put(site, thisTableSubTypes);

        // Table 4 ----------------------------------------
        thisTableSubTypes = new HashMap<>();
        thisTableSubTypes.put("8075", "8075");
        thisTableSubTypes.put("8071", "8071");
        thisTableSubTypes.put("8072", "8072");
        thisTableSubTypes.put("8051", "8051");
        for (String site : HEAD_AND_NECK_2018_TABLE4_SITES)
            content.put(site, thisTableSubTypes);

        // Table 5 ----------------------------------------
        thisTableSubTypes = new HashMap<>();
        thisTableSubTypes.put("8083", "8083");
        thisTableSubTypes.put("8071", "8071");
        thisTableSubTypes.put("8082", "8082");
        thisTableSubTypes.put("8072", "8072");
        thisTableSubTypes.put("8052", "8052");
        thisTableSubTypes.put("8086", "8086");
        thisTableSubTypes.put("8085", "8085");
        thisTableSubTypes.put("8074", "8074");
        thisTableSubTypes.put("8051", "8051");
        for (String site : HEAD_AND_NECK_2018_TABLE5_SITES)
            content.put(site, thisTableSubTypes);

        // Table 6 ----------------------------------------
        thisTableSubTypes = new HashMap<>();
        thisTableSubTypes.put("8147", "8147");
        thisTableSubTypes.put("8941", "8941");
        thisTableSubTypes.put("8310", "8310");
        thisTableSubTypes.put("8201", "8201");
        thisTableSubTypes.put("8144", "8144");
        thisTableSubTypes.put("8012", "8012");
        thisTableSubTypes.put("8520", "8520");
        thisTableSubTypes.put("8470", "8470");
        thisTableSubTypes.put("8430", "8430");
        thisTableSubTypes.put("8450", "8450");
        thisTableSubTypes.put("8525", "8525");
        thisTableSubTypes.put("8500", "8500");
        thisTableSubTypes.put("8020", "8020");
        thisTableSubTypes.put("8013", "8013");
        thisTableSubTypes.put("8041", "8041");
        for (String site : HEAD_AND_NECK_2018_TABLE6_SITES)
            content.put(site, thisTableSubTypes);

        // Table 7 ----------------------------------------
        thisTableSubTypes = new HashMap<>();
        thisTableSubTypes.put("9310/3", "9310/3");
        thisTableSubTypes.put("9330/3", "9330/3");
        thisTableSubTypes.put("9220/3", "9220/3");
        thisTableSubTypes.put("9240/3", "9220/3");
        thisTableSubTypes.put("9180/3", "9181/3,9187/3,9192/3,9193/3");
        thisTableSubTypes.put("9181/3", "9181/3");
        thisTableSubTypes.put("9187/3", "9187/3");
        thisTableSubTypes.put("9192/3", "9192/3");
        thisTableSubTypes.put("9193/3", "9193/3");
        for (String site : HEAD_AND_NECK_2018_TABLE7_SITES)
            content.put(site, thisTableSubTypes);

        // Table 8: No SubTypes
        // Table 9: No SubTypes

        // Special C111 Site - Combine Table 2 and 5.
        thisTableSubTypes = new HashMap<>();
        thisTableSubTypes.put("8083", "8083");
        thisTableSubTypes.put("8071", "8071");
        thisTableSubTypes.put("8072", "8072");
        thisTableSubTypes.put("8086", "8086");
        thisTableSubTypes.put("8085", "8085");
        thisTableSubTypes.put("8082", "8082");
        thisTableSubTypes.put("8020", "8020");
        for (String site : HEAD_AND_NECK_2018_TABLEC111_SITES)
            content.put(site, thisTableSubTypes);

        HEAD_AND_NECK_2018_SUBTYPES_FOR_SITE = Collections.unmodifiableMap(content);
    }

    public static final Map<String, Map<String, String>> HEAD_AND_NECK_2018_TABLE_FOR_SITE;

    static {
        Map<String, Map<String, String>> content = new HashMap<>();

        // Table 1 ----------------------------------------------------------------------------------------------------------------------
        Map<String, String> thisTableRows = new HashMap<>();
        thisTableRows.put("8140", "8140"); // Adenocarcinoma 8140
        thisTableRows.put("8144", "8140"); // Adenocarcinoma 8140
        thisTableRows.put("8082", "8082"); // Lymphoepithelial carcinoma 8082
        thisTableRows.put("9540/3", "9540/3"); // Malignant peripheral nerve sheath tumor 9540/3
        thisTableRows.put("9560/3", "9540/3"); // Malignant peripheral nerve sheath tumor 9540/3
        thisTableRows.put("8430", "8430"); // Mucoepidermoid carcinoma 8430
        thisTableRows.put("8720", "8720"); // Mucosal melanoma 8720
        thisTableRows.put("8982", "8982"); // Myoepithelial carcinoma 8982
        thisTableRows.put("8023", "8023"); // NUT carcinoma 8023*
        thisTableRows.put("9522/3", "9522/3"); // Olfactory neuroblastoma 9522/3
        thisTableRows.put("9521/3", "9522/3"); // Olfactory neuroblastoma 9522/3
        thisTableRows.put("9523/3", "9522/3"); // Olfactory neuroblastoma 9522/3
        thisTableRows.put("9364", "9364"); // Primitive neuroectodermal tumor 9364
        thisTableRows.put("8800/3", "8800/3"); // Sarcoma 8800/3
        thisTableRows.put("9120/3", "8800/3"); // Sarcoma 8800/3
        thisTableRows.put("9045/3", "8800/3"); // Sarcoma 8800/3
        thisTableRows.put("9133/3", "8800/3"); // Sarcoma 8800/3
        thisTableRows.put("8810/3", "8800/3"); // Sarcoma 8800/3
        thisTableRows.put("8890/3", "8800/3"); // Sarcoma 8800/3
        thisTableRows.put("8900/3", "8800/3"); // Sarcoma 8800/3
        thisTableRows.put("8920/3", "8800/3"); // Sarcoma 8800/3
        thisTableRows.put("8910/3", "8800/3"); // Sarcoma 8800/3
        thisTableRows.put("8901/3", "8800/3"); // Sarcoma 8800/3
        thisTableRows.put("8912/3", "8800/3"); // Sarcoma 8800/3
        thisTableRows.put("9040/3", "8800/3"); // Sarcoma 8800/3
        thisTableRows.put("8802/3", "8800/3"); // Sarcoma 8800/3
        thisTableRows.put("8020", "8020"); // Sinonasal undifferentiated carcinoma 8020
        thisTableRows.put("8070", "8070"); // Squamous cell carcinoma 8070
        thisTableRows.put("8071", "8070"); // Squamous cell carcinoma 8070
        thisTableRows.put("8074", "8070"); // Squamous cell carcinoma 8070
        thisTableRows.put("8072", "8070"); // Squamous cell carcinoma 8070
        thisTableRows.put("8121", "8070"); // Squamous cell carcinoma 8070
        thisTableRows.put("8083", "8070"); // Squamous cell carcinoma 8070
        thisTableRows.put("8052", "8070"); // Squamous cell carcinoma 8070
        thisTableRows.put("8051", "8070"); // Squamous cell carcinoma 8070
        thisTableRows.put("9081", "9081"); // Teratocarcinosarcoma 9081
        for (String site : HEAD_AND_NECK_2018_TABLE1_SITES)
            content.put(site, thisTableRows);

        // Table 2 ----------------------------------------------------------------------------------------------------------------------
        thisTableRows = new HashMap<>();
        thisTableRows.put("8200", "8200"); // Adenoid cystic carcinoma 8200
        thisTableRows.put("9370", "9370"); // Chordoma 9370
        thisTableRows.put("8260", "8260"); // Nasopharyngeal papillary adenocarcinoma 8260
        thisTableRows.put("8070", "8070"); // Squamous cell carcinoma NOS 8070
        thisTableRows.put("8083", "8070"); // Squamous cell carcinoma NOS 8070
        thisTableRows.put("8071", "8070"); // Squamous cell carcinoma NOS 8070
        thisTableRows.put("8072", "8070"); // Squamous cell carcinoma NOS 8070
        thisTableRows.put("8082", "8070"); // Squamous cell carcinoma NOS 8070
        thisTableRows.put("8020", "8070"); // Squamous cell carcinoma NOS 8070
        for (String site : HEAD_AND_NECK_2018_TABLE2_SITES)
            content.put(site, thisTableRows);

        // Table 3 ----------------------------------------------------------------------------------------------------------------------
        thisTableRows = new HashMap<>();
        thisTableRows.put("8200", "8200"); // Adenoid cystic carcinoma 8200
        thisTableRows.put("9220/3", "9220/3"); // Chondrosarcoma 9220/3
        thisTableRows.put("9222/3", "9220/3"); // Chondrosarcoma 9220/3, for 2022+
        thisTableRows.put("8850/3", "8850/3"); // Liposarcoma 8850/3
        thisTableRows.put("8851/3", "8850/3"); // Liposarcoma 8850/3
        thisTableRows.put("8240/3", "8240/3"); //Neuroendocrine tumor, NOS 8240/3
        thisTableRows.put("8013/3", "8240/3"); //Neuroendocrine tumor, NOS 8240/3
        thisTableRows.put("8249/3", "8240/3"); //Neuroendocrine tumor, NOS 8240/3
        thisTableRows.put("8041/3", "8240/3"); //Neuroendocrine tumor, NOS 8240/3
        thisTableRows.put("8070", "8070"); // Squamous cell carcinoma (SCC) 8070
        thisTableRows.put("8560", "8070"); // Squamous cell carcinoma (SCC) 8070
        thisTableRows.put("8083", "8070"); // Squamous cell carcinoma (SCC) 8070
        thisTableRows.put("8082", "8070"); // Squamous cell carcinoma (SCC) 8070
        thisTableRows.put("8052", "8070"); // Squamous cell carcinoma (SCC) 8070
        thisTableRows.put("8074", "8070"); // Squamous cell carcinoma (SCC) 8070
        thisTableRows.put("8051", "8070"); // Squamous cell carcinoma (SCC) 8070
        thisTableRows.put("8071", "8070"); // Squamous cell carcinoma (SCC) 8070
        thisTableRows.put("8072", "8070"); // Squamous cell carcinoma (SCC) 8070
        for (String site : HEAD_AND_NECK_2018_TABLE3_SITES)
            content.put(site, thisTableRows);

        // Table 4 ----------------------------------------------------------------------------------------------------------------------
        thisTableRows = new HashMap<>();

        thisTableRows.put("8430", "8430"); // Mucoepidermoid carcinoma 8430
        thisTableRows.put("8825", "8825"); // Myofibroblastic sarcoma 8825
        thisTableRows.put("8720", "8720"); // Oral mucosal melanoma 8720
        thisTableRows.put("8070", "8070"); // Squamous cell carcinoma 8070
        thisTableRows.put("8075", "8070"); // Squamous cell carcinoma 8070
        thisTableRows.put("8071", "8070"); // Squamous cell carcinoma 8070
        thisTableRows.put("8072", "8070"); // Squamous cell carcinoma 8070
        thisTableRows.put("8051", "8070"); // Squamous cell carcinoma 8070
        for (String site : HEAD_AND_NECK_2018_TABLE4_SITES)
            content.put(site, thisTableRows);

        // Table 5 ----------------------------------------------------------------------------------------------------------------------
        thisTableRows = new HashMap<>();
        thisTableRows.put("8200", "8200"); // Adenoid cystic carcinoma 8200
        thisTableRows.put("8525", "8525"); //Polymorphous adenocarcinoma 8525
        thisTableRows.put("8070", "8070"); //Squamous cell carcinoma 8070
        thisTableRows.put("8083", "8070"); //Squamous cell carcinoma 8070
        thisTableRows.put("8071", "8070"); //Squamous cell carcinoma 8070
        thisTableRows.put("8082", "8070"); //Squamous cell carcinoma 8070
        thisTableRows.put("8072", "8070"); //Squamous cell carcinoma 8070
        thisTableRows.put("8052", "8070"); //Squamous cell carcinoma 8070
        thisTableRows.put("8086", "8070"); //Squamous cell carcinoma 8070
        thisTableRows.put("8085", "8070"); //Squamous cell carcinoma 8070
        thisTableRows.put("8074", "8070"); //Squamous cell carcinoma 8070
        thisTableRows.put("8051", "8070"); //Squamous cell carcinoma 8070
        for (String site : HEAD_AND_NECK_2018_TABLE5_SITES)
            content.put(site, thisTableRows);

        // Table 6 ----------------------------------------------------------------------------------------------------------------------
        thisTableRows = new HashMap<>();
        thisTableRows.put("8550", "8550"); // Acinic cell carcinoma 8550
        thisTableRows.put("8140", "8140"); // Adenocarcinoma 8140
        thisTableRows.put("8147", "8140"); // Adenocarcinoma 8140
        thisTableRows.put("8941", "8140"); // Adenocarcinoma 8140
        thisTableRows.put("8310", "8140"); // Adenocarcinoma 8140
        thisTableRows.put("8201", "8140"); // Adenocarcinoma 8140
        thisTableRows.put("8144", "8140"); // Adenocarcinoma 8140
        thisTableRows.put("8012", "8140"); // Adenocarcinoma 8140
        thisTableRows.put("8520", "8140"); // Adenocarcinoma 8140
        thisTableRows.put("8470", "8140"); // Adenocarcinoma 8140
        thisTableRows.put("8430", "8140"); // Adenocarcinoma 8140
        thisTableRows.put("8450", "8140"); // Adenocarcinoma 8140
        thisTableRows.put("8525", "8140"); // Adenocarcinoma 8140
        thisTableRows.put("8500", "8140"); // Adenocarcinoma 8140
        thisTableRows.put("8020", "8140"); // Adenocarcinoma 8140
        thisTableRows.put("8200", "8200"); // Adenoid cystic carcinoma 8200
        thisTableRows.put("8980", "8980"); // Carcinosarcoma 8980
        thisTableRows.put("8440", "8440"); // Cystadenocarcinoma 8440
        thisTableRows.put("8562", "8562"); // Epithelial-myoepithelial carcinoma 8562
        thisTableRows.put("8082", "8082"); // Lymphoepithelial carcinoma (LEC) 8082
        thisTableRows.put("8982", "8982"); // Myoepithelial carcinoma 8982
        thisTableRows.put("8246", "8246"); // Neuroendocrine carcinoma 8246
        thisTableRows.put("8013", "8246"); // Neuroendocrine carcinoma 8246
        thisTableRows.put("8041", "8246"); // Neuroendocrine carcinoma 8246
        thisTableRows.put("8290", "8290"); // Oncocytic carcinoma 8290
        thisTableRows.put("8410", "8410"); // Sebaceous adenocarcinoma 8410
        thisTableRows.put("8502", "8502"); // Secretory carcinoma 8502
        thisTableRows.put("8070", "8070"); // Squamous cell carcinoma 8070
        for (String site : HEAD_AND_NECK_2018_TABLE6_SITES)
            content.put(site, thisTableRows);

        // Table 7 ----------------------------------------------------------------------------------------------------------------------
        thisTableRows = new HashMap<>();
        thisTableRows.put("9270/3", "9270/3"); // Ameloblastic carcinoma-primary type 9270/3
        thisTableRows.put("9310/3", "9270/3"); // Ameloblastic carcinoma-primary type 9270/3
        thisTableRows.put("9341", "9341"); // Clear cell odontogenic carcinoma 9341*
        thisTableRows.put("9302", "9302"); // Ghost cell odontogenic carcinoma 9302*
        thisTableRows.put("8980/3", "8980/3"); // Odontogenic carcinosarcoma 8980/3
        //thisTableRows.put("9310/3", "8980/3"); // Odontogenic carcinosarcoma 8980/3
        thisTableRows.put("9330/3", "8980/3"); // Odontogenic carcinosarcoma 8980/3
        thisTableRows.put("8800/3", "8800/3"); // Sarcoma NOS 8800/3
        thisTableRows.put("9220/3", "8800/3"); // Sarcoma NOS 8800/3
        thisTableRows.put("9240/3", "8800/3"); // Sarcoma NOS 8800/3
        thisTableRows.put("9180/3", "8800/3"); // Sarcoma NOS 8800/3
        thisTableRows.put("9181/3", "8800/3"); // Sarcoma NOS 8800/3
        thisTableRows.put("9187/3", "8800/3"); // Sarcoma NOS 8800/3
        thisTableRows.put("9192/3", "8800/3"); // Sarcoma NOS 8800/3
        thisTableRows.put("9193/3", "8800/3"); // Sarcoma NOS 8800/3
        for (String site : HEAD_AND_NECK_2018_TABLE7_SITES)
            content.put(site, thisTableRows);

        // Table 8 ----------------------------------------------------------------------------------------------------------------------
        thisTableRows = new HashMap<>();
        thisTableRows.put("8140", "8140"); // Endolymphatic sac tumor 8140
        thisTableRows.put("8070", "8070"); // Squamous cell carcinoma of the middle ear 8070
        for (String site : HEAD_AND_NECK_2018_TABLE8_SITES)
            content.put(site, thisTableRows);

        // Table 9 ----------------------------------------------------------------------------------------------------------------------
        thisTableRows = new HashMap<>();
        thisTableRows.put("8690", "8690");
        thisTableRows.put("8691", "8691");
        thisTableRows.put("8692", "8692");
        thisTableRows.put("8693", "8693");
        thisTableRows.put("8680", "8680");
        thisTableRows.put("8681", "8681");
        thisTableRows.put("8682", "8682");
        for (String site : HEAD_AND_NECK_2018_TABLE9_SITES)
            content.put(site, thisTableRows);

        // Special Table C111 -------------------------------------------------------------------------------------------------------------
        thisTableRows = new HashMap<>();
        thisTableRows.put("8200", "8200"); // Adenoid cystic carcinoma 8200
        thisTableRows.put("9370", "9370"); // Chordoma 9370
        thisTableRows.put("8260", "8260"); // Nasopharyngeal papillary adenocarcinoma 8260
        thisTableRows.put("8525", "8525"); //Polymorphous adenocarcinoma 8525
        thisTableRows.put("8070", "8070"); //Squamous cell carcinoma 8070
        thisTableRows.put("8083", "8070"); //Squamous cell carcinoma 8070
        thisTableRows.put("8071", "8070"); //Squamous cell carcinoma 8070
        thisTableRows.put("8072", "8070"); //Squamous cell carcinoma 8070
        thisTableRows.put("8086", "8070"); //Squamous cell carcinoma 8070
        thisTableRows.put("8085", "8070"); //Squamous cell carcinoma 8070
        thisTableRows.put("8082", "8070"); // Squamous cell carcinoma NOS 8070
        thisTableRows.put("8020", "8070"); // Squamous cell carcinoma NOS 8070
        for (String site : HEAD_AND_NECK_2018_TABLEC111_SITES)
            content.put(site, thisTableRows);

        HEAD_AND_NECK_2018_TABLE_FOR_SITE = Collections.unmodifiableMap(content);
    }

    public static final Map<String, String> CUTANEOUS_MELANOMA_2021_TABLE2_SUBTYPES;

    static {
        Map<String, String> content = new HashMap<>();
        content.put("8744/3", "8744/3");
        content.put("8730/3", "8730/3");
        content.put("8722/3", "8722/3");
        content.put("8745/3", "8745/3");
        content.put("8771/3", "8771/3");
        content.put("8742", "8742");
        content.put("8743/3", "8743/3");
        content.put("8780/3", "8780/3");
        content.put("8761/3", "8761/3");
        content.put("8741/3", "8741/3");
        content.put("8723/3", "8723/3");
        content.put("8770/3", "8770/3");
        content.put("8721/3", "8721/3");
        content.put("8772/3", "8772/3");
        content.put("8773/3", "8773/3");
        content.put("8774/3", "8774/3");
        CUTANEOUS_MELANOMA_2021_TABLE2_SUBTYPES = Collections.unmodifiableMap(content);
    }

    public static final List<String> CUTANEOUS_MELANOMA_2021_TABLE2_ROWS = Collections.unmodifiableList(Arrays.asList("8720", "8744/3", "8730/3", "8722/3", "8745/3", "8771/3", "8742", "8743/3", "8780/3", "8761/3", "8741/3",
            "8723/3", "8770/3", "8721/3", "8772/3", "8773/3", "8774/3"));


    public static final List<String> TESTIS_SITES = Collections.unmodifiableList(Arrays.asList("C620", "C621", "C629"));
    public static final List<String> ESOPHAGUS_SITES = Collections.unmodifiableList(Arrays.asList("C150", "C151", "C152", "C153", "C154", "C155", "C158", "C159"));
    public static final List<String> STOMACH_SITES = Collections.unmodifiableList(Arrays.asList("C160", "C161", "C162", "C163", "C164", "C165", "C166", "C168", "C169"));
    public static final List<String> INTESTINE_SITES = Collections.unmodifiableList(Arrays.asList("C170", "C171", "C172", "C173", "C178", "C179", "C241"));
    public static final List<String> ANUS_SITES = Collections.unmodifiableList(Arrays.asList("C210", "C211", "C212", "C218"));
    public static final List<String> LIVER_SITES = Collections.unmodifiableList(Arrays.asList("C220", "C221"));
    public static final List<String> BLADDER_SITES = Collections.unmodifiableList(Arrays.asList("C239", "C240", "C248", "C249"));
    public static final List<String> PANCREAS_SITES = Collections.unmodifiableList(Arrays.asList("C250", "C251", "C252", "C253", "C254", "C257", "C258", "C259"));
    public static final List<String> UTERINE_CORPUS_SITES = Collections.unmodifiableList(Arrays.asList("C540", "C541", "C542", "C543", "C548", "C549", "C559"));
    public static final List<String> UTERINE_CERVIX_SITES = Collections.unmodifiableList(Arrays.asList("C530", "C531", "C538", "C539"));
    public static final List<String> VULVA_SITES = Collections.unmodifiableList(Arrays.asList("C510", "C511", "C512", "C518", "C519"));
    public static final List<String> SOFT_TISSUES_SITES = Collections.unmodifiableList(Arrays.asList("C490", "C491", "C492", "C493", "C494", "C495", "C496", "C498", "C499"));
    public static final List<String> BONE_SITES = Collections.unmodifiableList(Arrays.asList("C400", "C401", "C402", "C403", "C408", "C409", "C412", "C413", "C414", "C418", "C419"));
    public static final String THYMUS = "C379";
    public static final List<String> PENIS_AND_SCROTUM_SITES = Collections.unmodifiableList(Arrays.asList("C600", "C601", "C602", "C608", "C609", "C632"));
    public static final Map<String, Map<String, String>> OTHER_SITES_2023_TABLE_ROWS_FOR_SITE;

    static {
        Map<String, Map<String, String>> content = new HashMap<>();
        //Table 3 Prostate
        Map<String, String> prostateRows = new HashMap<>();

        prostateRows.put("8140", "8140");
        prostateRows.put("8201", "8140");
        prostateRows.put("8260", "8140");
        prostateRows.put("8230", "8140");
        prostateRows.put("8500", "8140");
        prostateRows.put("8572", "8140");
        prostateRows.put("8480", "8140");
        prostateRows.put("8490", "8140");

        prostateRows.put("8574/3","8574/3");

        prostateRows.put("8560", "8560");

        prostateRows.put("8147", "8147");

        prostateRows.put("8552", "8552");

        prostateRows.put("8240/3", "8240/3");
        prostateRows.put("8013/3", "8240/3");
        prostateRows.put("8041/3", "8240/3");

        prostateRows.put("8800/3", "8800/3");
        prostateRows.put("8935/3", "8800/3");
        prostateRows.put("8890/3", "8800/3");
        prostateRows.put("8900/3", "8800/3");
        prostateRows.put("9120/3", "8800/3");
        prostateRows.put("9040/3", "8800/3");
        prostateRows.put("9180/3", "8800/3");
        prostateRows.put("8802/3", "8800/3");
        prostateRows.put("8815/3", "8800/3");

        prostateRows.put("8070", "8070");

        prostateRows.put("8120", "8120");

        content.put(PROSTATE, prostateRows);

        //Table 4 Testis
        Map<String, String> testisRows = new HashMap<>();
        testisRows.put("9064", "9064");
        testisRows.put("9100", "9064");
        testisRows.put("9070", "9064");
        testisRows.put("9063", "9064");
        testisRows.put("9071", "9064");
        testisRows.put("9084", "9064");

        testisRows.put("8650/3", "8650/3");

        testisRows.put("8640/3", "8640/3");

        testisRows.put("9061", "9061");

        for (String site : TESTIS_SITES)
            content.put(site, testisRows);

        //Table 5 esophagus
        Map<String, String> esophagusRows = new HashMap<>();
        esophagusRows.put("8140", "8140");
        esophagusRows.put("8200", "8200");
        esophagusRows.put("8560", "8560");
        esophagusRows.put("8936", "8936");
        esophagusRows.put("8430", "8430");
        esophagusRows.put("8720/3", "8720/3");

        esophagusRows.put("8070", "8070");
        esophagusRows.put("8083", "8070");
        esophagusRows.put("8074", "8070");
        esophagusRows.put("8051", "8070");

        esophagusRows.put("8020/3", "8020/3");

        esophagusRows.put("8240/3", "8240/3");
        esophagusRows.put("8246/3", "8240/3");
        esophagusRows.put("8013/3", "8240/3");
        esophagusRows.put("8041/3", "8240/3");

        esophagusRows.put("8154/3", "8154/3");

        for (String site : ESOPHAGUS_SITES)
            content.put(site, esophagusRows);

        //Table 6 stomach
        Map<String, String> stomachRows = new HashMap<>();
        stomachRows.put("8140", "8140");
        stomachRows.put("8145/3", "8140");
        stomachRows.put("8144", "8140");
        stomachRows.put("8576", "8140");
        stomachRows.put("8512", "8140");
        stomachRows.put("8265", "8140");
        stomachRows.put("8480", "8140");
        stomachRows.put("8430", "8140");
        stomachRows.put("8260", "8140");
        stomachRows.put("8214", "8140");
        stomachRows.put("8490", "8140");
        stomachRows.put("8211", "8140");

        stomachRows.put("8210/2", "8210/2");
        stomachRows.put("8560", "8560");
        stomachRows.put("8936", "8936");
        stomachRows.put("8976/3", "8976/3");
        stomachRows.put("8148/2", "8148/2");
        stomachRows.put("8244/3", "8244/3");
        stomachRows.put("8154/3", "8154/3");

        stomachRows.put("8246/3", "8246/3");
        stomachRows.put("8013/3", "8246/3");
        stomachRows.put("8041/3", "8246/3");

        stomachRows.put("8240/3", "8240/3");
        stomachRows.put("8242/3", "8240/3");
        stomachRows.put("8241/3", "8240/3");
        stomachRows.put("8153/3", "8240/3");
        stomachRows.put("8249/3", "8240/3");

        stomachRows.put("8213/2", "8213/2");

        stomachRows.put("8070", "8070");

        stomachRows.put("8020/3", "8020/3");
        stomachRows.put("8035/3", "8020/3");
        stomachRows.put("8014/3", "8020/3");
        stomachRows.put("8022/3", "8020/3");
        stomachRows.put("8033/3", "8020/3");

        for (String site : STOMACH_SITES)
            content.put(site, stomachRows);

        //Table 7 intestine
        Map<String, String> intestineRows = new HashMap<>();
        intestineRows.put("8140", "8140");
        intestineRows.put("8144", "8140");
        intestineRows.put("8510", "8140");
        intestineRows.put("8480", "8140");
        intestineRows.put("8163", "8140");
        intestineRows.put("8490", "8140");
        intestineRows.put("8211", "8140");

        intestineRows.put("8210/2", "8210/2");
        intestineRows.put("8936", "8936");
        intestineRows.put("8154/3", "8154/3");

        intestineRows.put("8246/3", "8246/3");
        intestineRows.put("8013/3", "8246/3");
        intestineRows.put("8041/3", "8246/3");

        intestineRows.put("8240/3", "8240/3");
        intestineRows.put("8249/3", "8240/3");

        intestineRows.put("8213/2", "8213/2");

        for (String site : INTESTINE_SITES)
            content.put(site, intestineRows);

        //Table 8 ANUS
        Map<String, String> anusRows = new HashMap<>();
        anusRows.put("8140", "8140");
        anusRows.put("8154/3", "8154/3");
        anusRows.put("8720/3", "8720/3");

        anusRows.put("8246/3", "8246/3");
        anusRows.put("8013/3", "8246/3");
        anusRows.put("8041/3", "8246/3");

        anusRows.put("8240/3", "8240/3");
        anusRows.put("8249/3", "8240/3");

        anusRows.put("8070", "8070");
        anusRows.put("8086", "8070");
        anusRows.put("8085", "8070");
        anusRows.put("8051", "8070");

        anusRows.put("8077/2", "8077/2");

        for (String site : ANUS_SITES)
            content.put(site, anusRows);

        //Table 9 Liver
        Map<String, String> liverRows = new HashMap<>();
        liverRows.put("8020/3", "8020/3");
        liverRows.put("8160", "8160");
        liverRows.put("8180", "8180");
        liverRows.put("8970/3", "8970/3");

        liverRows.put("8170", "8170");
        liverRows.put("8171", "8170");
        liverRows.put("8172", "8170");
        liverRows.put("8174", "8170");

        liverRows.put("8503", "8503");
        liverRows.put("8154/3", "8154/3");

        liverRows.put("8470", "8470");

        liverRows.put("8246/3", "8246/3");
        liverRows.put("8013/3", "8246/3");
        liverRows.put("8041/3", "8246/3");

        liverRows.put("8240/3", "8240/3");
        liverRows.put("8249/3", "8240/3");

        for (String site : LIVER_SITES)
            content.put(site, liverRows);

        //Table 10 gallbladder
        Map<String, String> bladderRows = new HashMap<>();
        bladderRows.put("8140", "8140");
        bladderRows.put("8144", "8140");
        bladderRows.put("8310", "8140");
        bladderRows.put("8480", "8140");
        bladderRows.put("8163", "8140");
        bladderRows.put("8490", "8140");

        bladderRows.put("8560", "8560");

        bladderRows.put("8160", "8160");
        bladderRows.put("8161", "8160");
        bladderRows.put("8162", "8160");

        bladderRows.put("8148/2", "8148/2");

        bladderRows.put("8010", "8010");
        bladderRows.put("8020", "8010");

        bladderRows.put("8503", "8503");
        bladderRows.put("8154/3", "8154/3");
        bladderRows.put("8470/3", "8470/3");

        bladderRows.put("8246/3", "8246/3");
        bladderRows.put("8013/3", "8246/3");
        bladderRows.put("8041/3", "8246/3");

        bladderRows.put("8240/3", "8240/3");
        bladderRows.put("8249/3", "8240/3");

        bladderRows.put("8070", "8070");

        for (String site : BLADDER_SITES)
            content.put(site, bladderRows);

        //Table 11 Pancreas
        Map<String, String> pancreasRows = new HashMap<>();
        pancreasRows.put("8140", "8140");
        pancreasRows.put("8550", "8140");
        pancreasRows.put("8480", "8140");
        pancreasRows.put("8500", "8140");
        pancreasRows.put("8576", "8140");
        pancreasRows.put("8265", "8140");
        pancreasRows.put("8510", "8140");
        pancreasRows.put("8552", "8140");
        pancreasRows.put("8154/3", "8140");
        pancreasRows.put("8490", "8140");

        pancreasRows.put("8560", "8560");

        pancreasRows.put("8148/2", "8148/2");

        pancreasRows.put("8455", "8455");

        pancreasRows.put("8453", "8453");

        pancreasRows.put("8503", "8503");

        pancreasRows.put("8154", "8154");

        pancreasRows.put("8470", "8470");

        pancreasRows.put("8246", "8246");
        pancreasRows.put("8013", "8246");
        pancreasRows.put("8041", "8246");

        pancreasRows.put("8240", "8240");
        pancreasRows.put("8158", "8240");
        pancreasRows.put("8241", "8240");
        pancreasRows.put("8153", "8240");
        pancreasRows.put("8152", "8240");
        pancreasRows.put("8151", "8240");
        pancreasRows.put("8249", "8240");
        pancreasRows.put("8150", "8240");
        pancreasRows.put("8156", "8240");
        pancreasRows.put("8155", "8240");

        pancreasRows.put("8971/3", "8971/3");

        pancreasRows.put("8452", "8452");

        pancreasRows.put("8070", "8070");

        pancreasRows.put("8020/3", "8020/3");
        pancreasRows.put("8035/3", "8020/3");
        pancreasRows.put("8014/3", "8020/3");

        for (String site : PANCREAS_SITES)
            content.put(site, pancreasRows);

        //Table 12 Thyroid
        Map<String, String> thyroidRows = new HashMap<>();
        thyroidRows.put("8021/3", "8021/3");
        thyroidRows.put("8020/3", "8021/3");

        thyroidRows.put("8330", "8330");
        thyroidRows.put("8339/3", "8330");
        thyroidRows.put("8335/3", "8330");
        thyroidRows.put("8331", "8330");
        thyroidRows.put("8332", "8330");

        thyroidRows.put("8345", "8345");
        thyroidRows.put("8290", "8290");

        thyroidRows.put("8260", "8260");
        thyroidRows.put("8344", "8260");
        thyroidRows.put("8350", "8260");
        thyroidRows.put("8343/3", "8260");
        thyroidRows.put("8340", "8260");
        thyroidRows.put("8342", "8260");

        thyroidRows.put("8337/3", "8337/3");

        content.put(THYROID, thyroidRows);

        //Table 13 Ovary
        Map<String, String> ovaryRows = new HashMap<>();
        ovaryRows.put("8140", "8140");
        ovaryRows.put("8310", "8140");
        ovaryRows.put("8380", "8140");
        ovaryRows.put("8480", "8140");

        ovaryRows.put("9110/3", "9110/3");
        ovaryRows.put("8933/3", "8933/3");
        ovaryRows.put("8620/3", "8620/3");
        ovaryRows.put("8980/3", "8980/3");
        ovaryRows.put("9100", "9100");

        ovaryRows.put("9064", "9064");
        ovaryRows.put("9080", "9064");
        ovaryRows.put("9060", "9064");
        ovaryRows.put("9071/3", "9064");
        ovaryRows.put("9070", "9064");
        ovaryRows.put("9085", "9064");

        ovaryRows.put("9000/3", "9000/3");
        ovaryRows.put("9111/3", "9111/3");

        ovaryRows.put("8800/3", "8800/3");
        ovaryRows.put("8930/3", "8800/3");
        ovaryRows.put("8931/3", "8800/3");
        ovaryRows.put("8890/3", "8800/3");
        ovaryRows.put("8810/3", "8800/3");

        ovaryRows.put("8441", "8441");
        ovaryRows.put("8461/3", "8441");
        ovaryRows.put("8460", "8441");

        ovaryRows.put("8044/3", "8044/3");
        ovaryRows.put("8670/3", "8670/3");
        ovaryRows.put("9090/3", "9090/3");
        ovaryRows.put("9084/3", "9084/3");
        ovaryRows.put("8020/3", "8020/3");

        content.put(OVARY, ovaryRows);

        //Table 14 Peritoneum Histologies
        Map<String, String> peritoneumRows = new HashMap<>();
        peritoneumRows.put("8936/3", "8936/3");
        peritoneumRows.put("8461/3", "8461/3");
        peritoneumRows.put("8460/3", "8460/3");

        peritoneumRows.put("9050/3", "9050/3");
        peritoneumRows.put("9051/3", "9050/3");
        peritoneumRows.put("9052/3", "9050/3");
        peritoneumRows.put("9053/3", "9050/3");

        peritoneumRows.put("8800/3", "8800/3");
        peritoneumRows.put("8806/3", "8800/3");
        peritoneumRows.put("8930/3", "8800/3");
        peritoneumRows.put("8931/3", "8800/3");

        peritoneumRows.put("8815/3", "8815/3");

        content.put(PERITONEUM, peritoneumRows);

        //Table 15 Fallopian Tube Histologies
        Map<String, String> fallopianRows = new HashMap<>();
        fallopianRows.put("8933/3", "8933/3");
        fallopianRows.put("8980/3", "8980/3");
        fallopianRows.put("8380", "8380");

        fallopianRows.put("8441", "8441");
        fallopianRows.put("8460/3", "8441");
        fallopianRows.put("8461/3", "8441");

        fallopianRows.put("9080/3", "9080/3");

        content.put(FALLOPIAN, fallopianRows);

        //Table 16: Uterine Corpus Histologies
        Map<String, String> uterineCorpusRows = new HashMap<>();
        uterineCorpusRows.put("8933/3", "8933/3");
        uterineCorpusRows.put("8020/3", "8020/3");
        uterineCorpusRows.put("8980/3", "8980/3");
        uterineCorpusRows.put("8310", "8310");

        uterineCorpusRows.put("8380", "8380");
        uterineCorpusRows.put("8570/3", "8380");

        uterineCorpusRows.put("9110/3", "9110/3");
        uterineCorpusRows.put("9111/3", "9110/3");

        uterineCorpusRows.put("8323", "8323");

        uterineCorpusRows.put("8480", "8480");
        uterineCorpusRows.put("8144", "8480");

        uterineCorpusRows.put("8246/3", "8246/3");
        uterineCorpusRows.put("8013/3", "8246/3");
        uterineCorpusRows.put("8154/3", "8246/3");
        uterineCorpusRows.put("8041/3", "8246/3");

        uterineCorpusRows.put("8714/3", "8714/3");
        uterineCorpusRows.put("9473/3", "9473/3");

        uterineCorpusRows.put("8800/3", "8800/3");
        uterineCorpusRows.put("8930/3", "8800/3");
        uterineCorpusRows.put("8931/3", "8800/3");
        uterineCorpusRows.put("8891/3", "8800/3");
        uterineCorpusRows.put("8890/3", "8800/3");
        uterineCorpusRows.put("8896/3", "8800/3");
        uterineCorpusRows.put("8805/3", "8800/3");

        uterineCorpusRows.put("8441", "8441");
        uterineCorpusRows.put("8070", "8070");

        for (String site : UTERINE_CORPUS_SITES)
            content.put(site, uterineCorpusRows);

        //Table 17: Uterine Cervix Histologies
        Map<String, String> uterineCervixRows = new HashMap<>();
        uterineCervixRows.put("8140/3", "8140/3");
        uterineCervixRows.put("8483/3", "8140/3");
        uterineCervixRows.put("8484/3", "8140/3");
        uterineCervixRows.put("8482/3", "8140/3");
        uterineCervixRows.put("8310/3", "8140/3");
        uterineCervixRows.put("9110/3", "8140/3");

        uterineCervixRows.put("8098/3", "8098/3");
        uterineCervixRows.put("8933/3", "8933/3");
        uterineCervixRows.put("8560/3", "8560/3");
        uterineCervixRows.put("8980/3", "8980/3");
        uterineCervixRows.put("8380/3", "8380/3");
        uterineCervixRows.put("9064/3", "9064/3");
        uterineCervixRows.put("9100/3", "9064/3");
        uterineCervixRows.put("9071/3", "9064/3");

        uterineCervixRows.put("8430/3", "8430/3");

        uterineCervixRows.put("8246/3", "8246/3");
        uterineCervixRows.put("8013/3", "8246/3");
        uterineCervixRows.put("8154/3", "8246/3");
        uterineCervixRows.put("8041/3", "8246/3");

        uterineCervixRows.put("8240/3", "8240/3");
        uterineCervixRows.put("8249/3", "8240/3");

        uterineCervixRows.put("8714/3", "8714/3");

        uterineCervixRows.put("8800/3", "8800/3");
        uterineCervixRows.put("8930/3", "8800/3");
        uterineCervixRows.put("8931/3", "8800/3");
        uterineCervixRows.put("8891/3", "8800/3");
        uterineCervixRows.put("8890/3", "8800/3");
        uterineCervixRows.put("8896/3", "8800/3");
        uterineCervixRows.put("8805/3", "8800/3");

        uterineCervixRows.put("8070/3", "8070/3");
        uterineCervixRows.put("8085/3", "8070/3");
        uterineCervixRows.put("8086/3", "8070/3");

        for (String site : UTERINE_CERVIX_SITES)
            content.put(site, uterineCervixRows);

        //Table 18: Vagina Histologies
        Map<String, String> vaginaRows = new HashMap<>();
        vaginaRows.put("8140", "8140");
        vaginaRows.put("8483", "8140");

        vaginaRows.put("8098", "8098");
        vaginaRows.put("8933/3", "8933/3");
        vaginaRows.put("8560", "8560");
        vaginaRows.put("8980/3", "8980/3");
        vaginaRows.put("8310", "8310");
        vaginaRows.put("8380", "8380");

        vaginaRows.put("9064/3", "9064/3");
        vaginaRows.put("9071/3", "9064/3");

        vaginaRows.put("9110/3", "9110/3");

        vaginaRows.put("8480", "8480");
        vaginaRows.put("8482", "8480");
        vaginaRows.put("8144", "8480");

        vaginaRows.put("8720/3", "8720/3");
        vaginaRows.put("8246/3", "8246/3");
        vaginaRows.put("8045/3", "8246/3");
        vaginaRows.put("8013/3", "8246/3");
        vaginaRows.put("8041/3", "8246/3");

        vaginaRows.put("8240/3", "8240/3");

        vaginaRows.put("8070", "8070");
        vaginaRows.put("8077/2", "8070");
        vaginaRows.put("8085", "8070");
        vaginaRows.put("8086", "8070");

        vaginaRows.put("8020/3", "8020/3");
        content.put(VAGINA, vaginaRows);

        //Table 19: Vulva Histologies
        Map<String, String> vulvaRows = new HashMap<>();
        vulvaRows.put("8140", "8140");
        vulvaRows.put("8144", "8140");

        vulvaRows.put("8200", "8200");
        vulvaRows.put("8560", "8560");
        vulvaRows.put("8090", "8090");
        vulvaRows.put("8020/3", "8020/3");
        vulvaRows.put("8562/3", "8562/3");

        vulvaRows.put("9064/3", "9064/3");
        vulvaRows.put("9071/3", "9064/3");

        vulvaRows.put("8720/3", "8720/3");
        vulvaRows.put("8982/3", "8982/3");

        vulvaRows.put("8246/3", "8246/3");
        vulvaRows.put("8045/3", "8246/3");
        vulvaRows.put("8013/3", "8246/3");
        vulvaRows.put("8041/3", "8246/3");

        vulvaRows.put("8240/3", "8240/3");
        vulvaRows.put("8249/3", "8240/3");

        vulvaRows.put("8542/3", "8542/3");
        vulvaRows.put("9020/3", "9020/3");

        vulvaRows.put("8070", "8070");
        vulvaRows.put("8085", "8070");
        vulvaRows.put("8086", "8070");
        vulvaRows.put("8071", "8070");
        vulvaRows.put("8072", "8070");
        vulvaRows.put("8077/2", "8070");

        vulvaRows.put("8400", "8400");
        vulvaRows.put("8200", "8400");
        vulvaRows.put("8401", "8400");
        vulvaRows.put("8413", "8400");
        vulvaRows.put("8409", "8400");

        for (String site : VULVA_SITES)
            content.put(site, vulvaRows);

        //Table 20: Soft Tissue Histologies
        Map<String, String> softTissueRows = new HashMap<>();
        softTissueRows.put("9120/3", "9120/3");
        softTissueRows.put("9133/3", "9133/3");

        softTissueRows.put("8810/3", "8810/3");
        softTissueRows.put("8814/3", "8810/3");
        softTissueRows.put("8840/3", "8810/3");
        softTissueRows.put("8825/3", "8810/3");
        softTissueRows.put("8811/3", "8810/3");
        softTissueRows.put("8815/3", "8810/3");

        softTissueRows.put("8890/3", "8890/3");

        softTissueRows.put("8850/3", "8850/3");
        softTissueRows.put("8859/3", "8850/3");
        softTissueRows.put("8858/3", "8850/3");
        softTissueRows.put("8854/3", "8850/3");
        softTissueRows.put("8852/3", "8850/3");
        softTissueRows.put("8851/3", "8850/3");

        softTissueRows.put("9180/3", "9180/3");

        softTissueRows.put("8900/3", "8900/3");
        softTissueRows.put("8920/3", "8900/3");
        softTissueRows.put("8921/3", "8900/3");
        softTissueRows.put("8910/3", "8900/3");
        softTissueRows.put("8901/3", "8900/3");
        softTissueRows.put("8912/3", "8900/3");

        softTissueRows.put("8800/3", "8800/3");
        softTissueRows.put("9044/3", "8800/3");
        softTissueRows.put("9043/3", "8800/3");
        softTissueRows.put("9367/3", "8800/3");
        softTissueRows.put("8804/3", "8800/3");
        softTissueRows.put("9364/3", "8800/3");
        softTissueRows.put("9231/3", "8800/3");
        softTissueRows.put("8940/3", "8800/3");
        softTissueRows.put("8982/3", "8800/3");
        softTissueRows.put("8842/3", "8800/3");
        softTissueRows.put("8990/3", "8800/3");
        softTissueRows.put("9366/3", "8800/3");
        softTissueRows.put("9368/3", "8800/3");
        softTissueRows.put("9040/3", "8800/3");
        softTissueRows.put("9043/3", "8800/3");

        softTissueRows.put("8805/3", "8805/3");
        softTissueRows.put("8802/3", "8805/3");
        softTissueRows.put("8803/3", "8805/3");
        softTissueRows.put("8801/3", "8805/3");

        for (String site : SOFT_TISSUES_SITES)
            content.put(site, softTissueRows);

        //Table 21: Bone Histologies
        Map<String, String> boneRows = new HashMap<>();
        boneRows.put("9261/3", "9261/3");
        boneRows.put("9120/3", "9120/3");

        boneRows.put("9220/3", "9220/3");
        boneRows.put("9222/3", "9220/3");
        boneRows.put("9242/3", "9220/3");
        boneRows.put("9243/3", "9220/3");
        boneRows.put("9240/3", "9220/3");
        boneRows.put("9221/3", "9220/3");

        boneRows.put("9370/3", "9370/3");
        boneRows.put("9371/3", "9370/3");
        boneRows.put("9372/3", "9370/3");

        boneRows.put("9133/3", "9133/3");
        boneRows.put("8810/3", "8810/3");
        boneRows.put("9250/3", "9250/3");
        boneRows.put("8890/3", "8890/3");

        boneRows.put("9180/3", "9180/3");
        boneRows.put("9194/3", "9180/3");
        boneRows.put("9192/3", "9180/3");
        boneRows.put("9193/3", "9180/3");
        boneRows.put("9184/3", "9180/3");

        boneRows.put("8800/3", "8800/3");
        boneRows.put("9367/3", "8800/3");
        boneRows.put("9364/3", "8800/3");
        boneRows.put("9366/3", "8800/3");
        boneRows.put("9368/3", "8800/3");

        boneRows.put("8802/3", "8802/3");

        boneRows.put("8830/3", "8830/3");

        for (String site : BONE_SITES)
            content.put(site, boneRows);

        //Table 22: Thymus Histologies
        Map<String, String> thymusRows = new HashMap<>();
        thymusRows.put("8144/3", "8140");
        thymusRows.put("8260/2", "8140");
        thymusRows.put("8200/3", "8140");
        thymusRows.put("8140", "8140");

        thymusRows.put("8560/3", "8560/3");

        thymusRows.put("8980/3", "8980/3");

        thymusRows.put("8310/3", "8310/3");

        thymusRows.put("8430/3", "8430/3");

        thymusRows.put("8045/3", "8246/3");
        thymusRows.put("8013/3", "8246/3");
        thymusRows.put("8041/3", "8246/3");
        thymusRows.put("8246/3", "8246/3");

        thymusRows.put("8249/3", "8240/3");
        thymusRows.put("8240/3", "8240/3");

        thymusRows.put("8023/3", "8023/3");

        thymusRows.put("8033/3", "8033/3");

        thymusRows.put("8123/3", "8070");
        thymusRows.put("8083/3", "8070");
        thymusRows.put("8082/3", "8070");
        thymusRows.put("8070", "8070");

        thymusRows.put("8586/3", "8586/3");

        thymusRows.put("8580/3", "8580/3");
        thymusRows.put("8581/3", "8580/3");
        thymusRows.put("8582/3", "8580/3");
        thymusRows.put("8583/3", "8580/3");
        thymusRows.put("8584/3", "8580/3");
        thymusRows.put("8585/3", "8580/3");

        thymusRows.put("8020/3", "8020/3");

        content.put(THYMUS, thymusRows);

        //Table 23: Penis and scrotum
        Map<String, String> penisAndScrotumRows = new HashMap<>();
        penisAndScrotumRows.put("8140", "8140");
        penisAndScrotumRows.put("8560", "8140");
        penisAndScrotumRows.put("8430", "8140");

        penisAndScrotumRows.put("8542/3", "8542/3");

        penisAndScrotumRows.put("8070", "8070");
        penisAndScrotumRows.put("8083", "8070");
        penisAndScrotumRows.put("8084", "8070");
        penisAndScrotumRows.put("8071/2", "8070");
        penisAndScrotumRows.put("8077/2", "8070");
        penisAndScrotumRows.put("8082", "8070");
        penisAndScrotumRows.put("8052", "8070");
        penisAndScrotumRows.put("8075", "8070");
        penisAndScrotumRows.put("8074", "8070");
        penisAndScrotumRows.put("8085", "8070");
        penisAndScrotumRows.put("8086", "8070");
        penisAndScrotumRows.put("8051", "8070");

        for (String site: PENIS_AND_SCROTUM_SITES)
            content.put(site, penisAndScrotumRows);

        OTHER_SITES_2023_TABLE_ROWS_FOR_SITE = Collections.unmodifiableMap(content);
    }

    public static final List<String> OTHER_SITES_2023_TABLE_2 = Collections.unmodifiableList(Arrays.asList("8045", "8013", "8094", "8552", "8154", "8180", "8244", "8255", "8323", "8340", "8346", "8347", "8560", "8855", "8902", "9081", "9085", "9101"));

    public static final Map<String, Map<String, String>> OTHER_SITES_2023_TABLE_SUBTYPES_FOR_SITE;

    static {
        Map<String, Map<String, String>> content = new HashMap<>();
        //Table 3 Prostate
        Map<String, String> prostateSubtypes = new HashMap<>();

        prostateSubtypes.put("8572", "8572");
        prostateSubtypes.put("8500", "8201,8260,8230");
        prostateSubtypes.put("8201", "8201");
        prostateSubtypes.put("8260", "8260");
        prostateSubtypes.put("8230", "8230");
        prostateSubtypes.put("8480", "8480");
        prostateSubtypes.put("8490", "8490");

        prostateSubtypes.put("8013/3", "8013/3");
        prostateSubtypes.put("8041/3", "8041/3");

        prostateSubtypes.put("8935/3", "8935/3");
        prostateSubtypes.put("8890/3", "8890/3");
        prostateSubtypes.put("8900/3", "8900/3");
        prostateSubtypes.put("9120/3", "9120/3");
        prostateSubtypes.put("9040/3", "9040/3");
        prostateSubtypes.put("9180/3", "9180/3");
        prostateSubtypes.put("8802/3", "8802/3");
        prostateSubtypes.put("8815/3", "8815/3");
        content.put(PROSTATE, prostateSubtypes);

        //Table 4 Testis
        Map<String, String> testisSubtypes = new HashMap<>();
        testisSubtypes.put("9100", "9100");
        testisSubtypes.put("9070", "9070");
        testisSubtypes.put("9063", "9063");
        testisSubtypes.put("9071", "9071");
        testisSubtypes.put("9084", "9084");
        for (String site : TESTIS_SITES)
            content.put(site, testisSubtypes);

        //Table 5 esophagus
        Map<String, String> esophagusSubtypes = new HashMap<>();

        esophagusSubtypes.put("8083", "8083");
        esophagusSubtypes.put("8074", "8074");
        esophagusSubtypes.put("8051", "8051");
        esophagusSubtypes.put("8246/3", "8246/3");
        esophagusSubtypes.put("8013/3", "8013/3");
        esophagusSubtypes.put("8041/3", "8041/3");

        for (String site : ESOPHAGUS_SITES)
            content.put(site, esophagusSubtypes);

        //Table 6 stomach
        Map<String, String> stomachSubtypes = new HashMap<>();
        stomachSubtypes.put("8145/3", "8145/3");
        stomachSubtypes.put("8144", "8144");
        stomachSubtypes.put("8576", "8576");
        stomachSubtypes.put("8512", "8512");
        stomachSubtypes.put("8265", "8265");
        stomachSubtypes.put("8480", "8480");
        stomachSubtypes.put("8430", "8430");
        stomachSubtypes.put("8260", "8260");
        stomachSubtypes.put("8214", "8214");
        stomachSubtypes.put("8490", "8490");
        stomachSubtypes.put("8211", "8211");

        stomachSubtypes.put("8013/3", "8013/3");
        stomachSubtypes.put("8041/3", "8041/3");
        stomachSubtypes.put("8242/3", "8242/3");
        stomachSubtypes.put("8241/3", "8241/3");
        stomachSubtypes.put("8153/3", "8153/3");
        stomachSubtypes.put("8249/3", "8249/3");
        stomachSubtypes.put("8035/3", "8035/3");
        stomachSubtypes.put("8014/3", "8014/3");
        stomachSubtypes.put("8022/3", "8022/3");
        stomachSubtypes.put("8033/3", "8033/3");
        for (String site : STOMACH_SITES)
            content.put(site, stomachSubtypes);

        //Table 7 intestine
        Map<String, String> intestineSubtypes = new HashMap<>();
        intestineSubtypes.put("8144", "8144");
        intestineSubtypes.put("8510", "8510");
        intestineSubtypes.put("8480", "8480");
        intestineSubtypes.put("8163", "8163");
        intestineSubtypes.put("8490", "8490");
        intestineSubtypes.put("8211", "8211");

        intestineSubtypes.put("8013/3", "8013/3");
        intestineSubtypes.put("8041/3", "8041/3");
        intestineSubtypes.put("8249/3", "8249/3");

        for (String site : INTESTINE_SITES)
            content.put(site, intestineSubtypes);

        //Table 8 ANUS
        Map<String, String> anusSubtypes = new HashMap<>();

        anusSubtypes.put("8013/3", "8013/3");
        anusSubtypes.put("8041/3", "8041/3");
        anusSubtypes.put("8249/3", "8249/3");
        anusSubtypes.put("8086", "8086");
        anusSubtypes.put("8085", "8085");
        anusSubtypes.put("8051", "8051");
        for (String site : ANUS_SITES)
            content.put(site, anusSubtypes);

        //Table 9 Liver
        Map<String, String> liverSubtypes = new HashMap<>();
        liverSubtypes.put("8171", "8171");
        liverSubtypes.put("8172", "8172");
        liverSubtypes.put("8174", "8174");
        liverSubtypes.put("8013/3", "8013/3");
        liverSubtypes.put("8041/3", "8041/3");
        liverSubtypes.put("8249/3", "8249/3");

        for (String site : LIVER_SITES)
            content.put(site, liverSubtypes);

        //Table 10 gallbladder
        Map<String, String> bladderSubtypes = new HashMap<>();
        bladderSubtypes.put("8144", "8144");
        bladderSubtypes.put("8310", "8310");
        bladderSubtypes.put("8480", "8480");
        bladderSubtypes.put("8163", "8163");
        bladderSubtypes.put("8490", "8490");
        bladderSubtypes.put("8161", "8161");
        bladderSubtypes.put("8162", "8162");
        bladderSubtypes.put("8020", "8020");
        bladderSubtypes.put("8013/3", "8013/3");
        bladderSubtypes.put("8041/3", "8041/3");
        bladderSubtypes.put("8249/3", "8249/3");
        for (String site : BLADDER_SITES)
            content.put(site, bladderSubtypes);

        //Table 11 Pancreas
        Map<String, String> pancreasSubtypes = new HashMap<>();
        pancreasSubtypes.put("8550", "8550");
        pancreasSubtypes.put("8480", "8480");
        pancreasSubtypes.put("8500", "8500");
        pancreasSubtypes.put("8576", "8576");
        pancreasSubtypes.put("8265", "8265");
        pancreasSubtypes.put("8510", "8510");
        pancreasSubtypes.put("8552", "8552");
        pancreasSubtypes.put("8154/3", "8154/3");
        pancreasSubtypes.put("8490", "8490");
        pancreasSubtypes.put("8013", "8013");
        pancreasSubtypes.put("8041", "8041");
        pancreasSubtypes.put("8158", "8158");
        pancreasSubtypes.put("8241", "8241");
        pancreasSubtypes.put("8153", "8153");
        pancreasSubtypes.put("8152", "8152");
        pancreasSubtypes.put("8151", "8151");
        pancreasSubtypes.put("8249", "8249");
        pancreasSubtypes.put("8150", "8150");
        pancreasSubtypes.put("8156", "8156");
        pancreasSubtypes.put("8155", "8155");
        pancreasSubtypes.put("8035/3", "8035/3");
        pancreasSubtypes.put("8014/3", "8014/3");
        for (String site : PANCREAS_SITES)
            content.put(site, pancreasSubtypes);

        //Table 12 Thyroid
        Map<String, String> thyroidSubtypes = new HashMap<>();
        thyroidSubtypes.put("8020/3", "8021/3");
        thyroidSubtypes.put("8339/3", "8339/3");
        thyroidSubtypes.put("8335/3", "8335/3");
        thyroidSubtypes.put("8331", "8331");
        thyroidSubtypes.put("8332", "8332");
        thyroidSubtypes.put("8344", "8344");
        thyroidSubtypes.put("8350", "8350");
        thyroidSubtypes.put("8343/3", "8343/3");
        thyroidSubtypes.put("8340", "8340");
        thyroidSubtypes.put("8342", "8342");
        content.put(THYROID, thyroidSubtypes);

        //Table 13 Ovary
        Map<String, String> ovarySubtypes = new HashMap<>();
        ovarySubtypes.put("8310", "8310");
        ovarySubtypes.put("8380", "8380");
        ovarySubtypes.put("8480", "8480");

        ovarySubtypes.put("9080", "9080");
        ovarySubtypes.put("9060", "9060");
        ovarySubtypes.put("9071/3", "9071/3");
        ovarySubtypes.put("9070", "9070");
        ovarySubtypes.put("9085", "9085");
        ovarySubtypes.put("8930/3", "8930/3");
        ovarySubtypes.put("8931/3", "8931/3");
        ovarySubtypes.put("8890/3", "8890/3");
        ovarySubtypes.put("8810/3", "8810/3");
        ovarySubtypes.put("8461/3", "8461/3");
        ovarySubtypes.put("8460/3", "8460/3");
        ovarySubtypes.put("8460/2", "8460/2");

        content.put(OVARY, ovarySubtypes);

        //Table 14 Peritoneum Histologies
        Map<String, String> peritoneumSubtypes = new HashMap<>();
        peritoneumSubtypes.put("9051/3", "9051/3");
        peritoneumSubtypes.put("9052/3", "9052/3");
        peritoneumSubtypes.put("9053/3", "9053/3");
        peritoneumSubtypes.put("8806/3", "8806/3");
        peritoneumSubtypes.put("8930/3", "8930/3");
        peritoneumSubtypes.put("8931/3", "8931/3");
        content.put(PERITONEUM, peritoneumSubtypes);

        //Table 15 Fallopian Tube Histologies
        Map<String, String> fallopianSubtypes = new HashMap<>();
        fallopianSubtypes.put("8461/3", "8461/3");
        fallopianSubtypes.put("8460/3", "8460/3");
        content.put(FALLOPIAN, fallopianSubtypes);

        //Table 16: Uterine Corpus Histologies
        Map<String, String> uterineCorpusSubtypes = new HashMap<>();
        uterineCorpusSubtypes.put("8570/3", "8570/3");
        uterineCorpusSubtypes.put("9111/3", "9111/3");
        uterineCorpusSubtypes.put("8144", "8144");
        uterineCorpusSubtypes.put("8013/3", "8013/3");
        uterineCorpusSubtypes.put("8154/3", "8154/3");
        uterineCorpusSubtypes.put("8041/3", "8041/3");
        uterineCorpusSubtypes.put("8930/3", "8930/3");
        uterineCorpusSubtypes.put("8931/3", "8931/3");
        uterineCorpusSubtypes.put("8891/3", "8891/3");
        uterineCorpusSubtypes.put("8890/3", "8890/3");
        uterineCorpusSubtypes.put("8896/3", "8896/3");
        uterineCorpusSubtypes.put("8805/3", "8805/3");
        for (String site : UTERINE_CORPUS_SITES)
            content.put(site, uterineCorpusSubtypes);

        //Table 17: Uterine Cervix Histologies
        Map<String, String> uterineCervixSubtypes = new HashMap<>();
        uterineCervixSubtypes.put("8483/3", "8483/3");
        uterineCervixSubtypes.put("8484/3", "8484/3");
        uterineCervixSubtypes.put("8482/3", "8482/3");
        uterineCervixSubtypes.put("8310/3", "8310/3");
        uterineCervixSubtypes.put("9110/3", "9110/3");
        uterineCervixSubtypes.put("9100/3", "9100/3");
        uterineCervixSubtypes.put("9071/3", "9071/3");
        uterineCervixSubtypes.put("8013/3", "8013/3");
        uterineCervixSubtypes.put("8154/3", "8154/3");
        uterineCervixSubtypes.put("8041/3", "8041/3");
        uterineCervixSubtypes.put("8249/3", "8249/3");
        uterineCervixSubtypes.put("8930/3", "8930/3");
        uterineCervixSubtypes.put("8931/3", "8931/3");
        uterineCervixSubtypes.put("8891/3", "8891/3");
        uterineCervixSubtypes.put("8890/3", "8890/3");
        uterineCervixSubtypes.put("8896/3", "8896/3");
        uterineCervixSubtypes.put("8805/3", "8805/3");
        uterineCervixSubtypes.put("8085/3", "8085/3");
        uterineCervixSubtypes.put("8086/3", "8086/3");

        for (String site : UTERINE_CERVIX_SITES)
            content.put(site, uterineCervixSubtypes);

        //Table 18: Vagina Histologies
        Map<String, String> vaginaSubtypes = new HashMap<>();
        vaginaSubtypes.put("8483", "8483");
        vaginaSubtypes.put("9071/3", "9071/3");
        vaginaSubtypes.put("8482", "8482");
        vaginaSubtypes.put("8144", "8144");
        vaginaSubtypes.put("8045/3", "8045/3");
        vaginaSubtypes.put("8013/3", "8013/3");
        vaginaSubtypes.put("8041/3", "8041/3");
        vaginaSubtypes.put("8077/2", "8077/2");
        vaginaSubtypes.put("8085", "8085");
        vaginaSubtypes.put("8086", "8086");
        content.put(VAGINA, vaginaSubtypes);

        //Table 19: Vulva Histologies
        Map<String, String> vulvaSubtypes = new HashMap<>();
        vulvaSubtypes.put("8144", "8144");
        vulvaSubtypes.put("9071/3", "9071/3");
        vulvaSubtypes.put("8045/3", "8045/3");
        vulvaSubtypes.put("8013/3", "8013/3");
        vulvaSubtypes.put("8041/3", "8041/3");
        vulvaSubtypes.put("8249/3", "8249/3");
        vulvaSubtypes.put("8085", "8085");
        vulvaSubtypes.put("8086", "8086");
        vulvaSubtypes.put("8071", "8071");
        vulvaSubtypes.put("8072", "8072");
        vulvaSubtypes.put("8077/2", "8077/2");

        vulvaSubtypes.put("8200", "8200");
        vulvaSubtypes.put("8401", "8401");
        vulvaSubtypes.put("8413", "8413");
        vulvaSubtypes.put("8409", "8409");

        for (String site : VULVA_SITES)
            content.put(site, vulvaSubtypes);

        //Table 20: Soft Tissue Histologies
        Map<String, String> softTissueSubtypes = new HashMap<>();
        softTissueSubtypes.put("8814/3", "8814/3");
        softTissueSubtypes.put("8840/3", "8840/3");
        softTissueSubtypes.put("8825/3", "8825/3");
        softTissueSubtypes.put("8811/3", "8811/3");
        softTissueSubtypes.put("8815/3", "8815/3");
        softTissueSubtypes.put("8858/3", "8858/3");
        softTissueSubtypes.put("8859/3", "8859/3");
        softTissueSubtypes.put("8854/3", "8854/3");
        softTissueSubtypes.put("8852/3", "8852/3");
        softTissueSubtypes.put("8851/3", "8851/3");
        softTissueSubtypes.put("8920/3", "8920/3");
        softTissueSubtypes.put("8921/3", "8921/3");
        softTissueSubtypes.put("8910/3", "8910/3");
        softTissueSubtypes.put("8901/3", "8901/3");
        softTissueSubtypes.put("8912/3", "8912/3");
        softTissueSubtypes.put("9367/3", "9367/3");
        softTissueSubtypes.put("9044/3", "9044/3");

        softTissueSubtypes.put("8804/3", "8804/3");
        softTissueSubtypes.put("9364/3", "9364/3");
        softTissueSubtypes.put("9231/3", "9231/3");
        softTissueSubtypes.put("8940/3", "8940/3");
        softTissueSubtypes.put("8982/3", "8982/3");
        softTissueSubtypes.put("8842/3", "8842/3");
        softTissueSubtypes.put("8990/3", "8990/3");
        softTissueSubtypes.put("9366/3", "9366/3");
        softTissueSubtypes.put("9368/3", "9368/3");
        softTissueSubtypes.put("9040/3", "9040/3,9043/3");
        softTissueSubtypes.put("9043/3", "9043/3");

        softTissueSubtypes.put("8802/3", "8802/3");
        softTissueSubtypes.put("8803/3", "8803/3");
        softTissueSubtypes.put("8801/3", "8801/3");



        for (String site : SOFT_TISSUES_SITES)
            content.put(site, softTissueSubtypes);

        //Table 21: Bone Histologies
        Map<String, String> boneSubtypes = new HashMap<>();
        boneSubtypes.put("9222/3", "9222/3");
        boneSubtypes.put("9242/3", "9242/3");
        boneSubtypes.put("9243/3", "9243/3");
        boneSubtypes.put("9240/3", "9240/3");
        boneSubtypes.put("9221/3", "9221/3");
        boneSubtypes.put("9371/3", "9371/3");
        boneSubtypes.put("9372/3", "9372/3");
        boneSubtypes.put("9194/3", "9194/3");
        boneSubtypes.put("9192/3", "9192/3");
        boneSubtypes.put("9193/3", "9193/3");
        boneSubtypes.put("9184/3", "9184/3");
        boneSubtypes.put("9367/3", "9367/3");
        boneSubtypes.put("9364/3", "9364/3");
        boneSubtypes.put("9366/3", "9366/3");
        boneSubtypes.put("9368/3", "9368/3");
        for (String site : BONE_SITES)
            content.put(site, boneSubtypes);

        //Table 22: Thymus Histologies
        Map<String, String> thymusSubTypes = new HashMap<>();
        thymusSubTypes.put("8144/3", "8144/3");
        thymusSubTypes.put("8260/2", "8260/2");
        thymusSubTypes.put("8200/3", "8200/3");

        thymusSubTypes.put("8045/3", "8045/3");
        thymusSubTypes.put("8013/3", "8013/3");
        thymusSubTypes.put("8041/3", "8041/3");

        thymusSubTypes.put("8249/3", "8249/3");

        thymusSubTypes.put("8123/3", "8123/3");
        thymusSubTypes.put("8083/3", "8083/3");
        thymusSubTypes.put("8082/3", "8082/3");

        thymusSubTypes.put("8581/3", "8581/3");
        thymusSubTypes.put("8582/3", "8582/3");
        thymusSubTypes.put("8583/3", "8583/3");
        thymusSubTypes.put("8584/3", "8584/3");
        thymusSubTypes.put("8585/3", "8585/3");

        content.put(THYMUS, thymusSubTypes);

        //Table 23: Penis and scrotum
        Map<String, String> penisAndScrotumSubtypes = new HashMap<>();
        penisAndScrotumSubtypes.put("8560", "8560");
        penisAndScrotumSubtypes.put("8430", "8430");

        penisAndScrotumSubtypes.put("8083", "8083");
        penisAndScrotumSubtypes.put("8084", "8084");
        penisAndScrotumSubtypes.put("8071/2", "8071/2");
        penisAndScrotumSubtypes.put("8077/2", "8077/2");
        penisAndScrotumSubtypes.put("8082", "8082");
        penisAndScrotumSubtypes.put("8052", "8052");
        penisAndScrotumSubtypes.put("8075", "8075");
        penisAndScrotumSubtypes.put("8074", "8074");
        penisAndScrotumSubtypes.put("8085", "8085");
        penisAndScrotumSubtypes.put("8086", "8086");
        penisAndScrotumSubtypes.put("8051", "8051");
        for (String site: PENIS_AND_SCROTUM_SITES)
            content.put(site, penisAndScrotumSubtypes);

        OTHER_SITES_2023_TABLE_SUBTYPES_FOR_SITE = Collections.unmodifiableMap(content);
    }

}
