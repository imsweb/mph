/*
 * Copyright (C) 2016 Information Management Services, Inc.
 */
package com.imsweb.mph;

import java.util.Date;
import java.util.List;

import com.imsweb.mph.internal.HematoDbDTO;

public interface HematoDbDataProvider {

    /**
     * Returns same primary information for a given morphology
     * <p/>
     * morphology should have the format "9999/9".
     * <p/>
     * @param morphology
     * @return List of same primary morphologies with their applicable year
     */
    List<HematoDbDTO> getSamePrimary(String morphology);

    /**
     * Returns "transform to" information for a given morphology
     * <p/>
     * morphology should have the format "9999/9".
     * <p/>
     * @param morphology
     * @return List of transform to morphologies with their applicable year
     */
    List<HematoDbDTO> getTransformTo(String morphology);

    /**
     * Returns "transform from" information for a given morphology
     * <p/>
     * morphology should have the format "9999/9".
     * <p/>
     * @param morphology
     * @return List of transform from morphologies with their applicable year
     */
    List<HematoDbDTO> getTransformFrom(String morphology);

    /**
     * @return the date when the hemato data is last updated
     */
    Date getDataLastUpdated();
}
