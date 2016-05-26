/*
 * Copyright (C) 2016 Information Management Services, Inc.
 */
package com.imsweb.mph;

public interface HematoDbUtilsProvider {

    /**
     * Compares the two ICD-O-3 morphology codes and determine whether they are same primaries or not.
     * <p/>
     * Codes should have the format "9999/9".
     * <p/>
     * If both codes are the same, true is returned (same primaries).
     * <p/>
     * @param leftCode left code to compare
     * @param rightCode right code to compare
     * @param year The requested DX year, cannot be null
     * @return true if the codes are same primary, false otherwise
     */
    boolean isSamePrimary(String leftCode, String rightCode, int year);
}
