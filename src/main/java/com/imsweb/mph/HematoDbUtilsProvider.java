/*
 * Copyright (C) 2016 Information Management Services, Inc.
 */
package com.imsweb.mph;

import java.util.Date;

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
     * @param leftYear dx year of left disease
     * @param rightYear dx year of right disease
     * @return true if the codes are same primary, false otherwise
     */
    boolean isSamePrimary(String leftCode, String rightCode, int leftYear, int rightYear);

    /**
     * Checks if the second disease is in 'transform to' list of the first disease.
     * or the first disease is in 'transform from' list of the second disease
     * <p/>
     * Codes should have the format "9999/9".
     * <p/>
     * @param fromCode the first disease code
     * @param toCode the second disease code
     * @param fromYear The diagnosis year of the first disease
     * @param toYear The diagnosis year of the second disease
     * @return true if the the second disease is in 'transform to' list of the first disease or the first disease is in 'transform from' list of the second.
     */
    boolean canTransformTo(String fromCode, String toCode, int fromYear, int toYear);

    /**
     * @return the date when the hemato data is last updated
     */
    Date getDataLastUpdated();
}
