/*
 * Copyright (C) 2023 Information Management Services, Inc.
 */
package com.imsweb.mph;

import java.util.regex.Pattern;

public final class HematoUtils {

    private static Pattern _MORPHOLOGY = Pattern.compile("^(\\d{4}/\\d)");

    public static boolean isSamePrimary(HematoDataProvider hematoProvider, String morph1, String morph2, int year1, int year2) {
        if (morph1 == null || morph2 == null || !_MORPHOLOGY.matcher(morph1).matches() || !_MORPHOLOGY.matcher(morph2).matches())
            return false;
        if (morph1.equals(morph2))
            return true;

        return hematoProvider.getSamePrimary(morph1).stream().anyMatch(r -> r.matches(morph2, year1)) && hematoProvider.getSamePrimary(morph2).stream().anyMatch(r -> r.matches(morph1, year2));
    }

    public static boolean isTransformation(HematoDataProvider hematoProvider, String leftCode, String rightCode, int leftYear, int rightYear) {
        return isChronicToAcuteTransformation(hematoProvider, leftCode, rightCode, leftYear, rightYear) || isAcuteToChronicTransformation(hematoProvider, leftCode, rightCode, leftYear, rightYear);
    }

    //when a neoplasm is originally diagnosed as a chronic neoplasm AND there is a second diagnosis of an acute neoplasm
    public static boolean isChronicToAcuteTransformation(HematoDataProvider hematoProvider, String earlierMorph, String laterMorph, int earlierYear, int laterYear) {
        return confirmTransformTo(hematoProvider, earlierMorph, laterMorph, earlierYear) && confirmTransformFrom(hematoProvider, laterMorph, earlierMorph, laterYear);
    }

    //when a neoplasm is originally diagnosed as acute AND reverts to a chronic neoplasm
    public static boolean isAcuteToChronicTransformation(HematoDataProvider hematoProvider, String earlierMorph, String laterMorph, int earlierYear, int laterYear) {
        return confirmTransformFrom(hematoProvider, earlierMorph, laterMorph, earlierYear) && confirmTransformTo(hematoProvider, laterMorph, earlierMorph, laterYear);
    }

    private static boolean confirmTransformTo(HematoDataProvider hematoProvider, String leftCode, String rightCode, int year) {
        return hematoProvider.getTransformTo(leftCode).stream().anyMatch(d -> d.matches(rightCode, year));
    }

    private static boolean confirmTransformFrom(HematoDataProvider hematoProvider, String leftCode, String rightCode, int year) {
        return hematoProvider.getTransformFrom(leftCode).stream().anyMatch(d -> d.matches(rightCode, year));
    }
}
