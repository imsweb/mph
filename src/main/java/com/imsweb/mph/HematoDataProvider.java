/*
 * Copyright (C) 2016 Information Management Services, Inc.
 */
package com.imsweb.mph;

import java.util.List;

import com.imsweb.mph.internal.HematoDTO;

public interface HematoDataProvider {

    /**
     * Returns same primary information for a given morphology
     * <p/>
     * morphology should have the format "9999/9".
     * <p/>
     * @param morphology
     * @return List of same primary morphologies with their applicable year
     */
    List<HematoDTO> getSamePrimary(String morphology);

    /**
     * Returns "transform to" information for a given morphology
     * <p/>
     * morphology should have the format "9999/9".
     * <p/>
     * @param morphology
     * @return List of transform to morphologies with their applicable year
     */
    List<HematoDTO> getTransformTo(String morphology);

    /**
     * Returns "transform from" information for a given morphology
     * <p/>
     * morphology should have the format "9999/9".
     * <p/>
     * @param morphology
     * @return List of transform from morphologies with their applicable year
     */
    List<HematoDTO> getTransformFrom(String morphology);
}
