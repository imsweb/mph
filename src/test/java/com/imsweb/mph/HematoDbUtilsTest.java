/*
 * Copyright (C) 2016 Information Management Services, Inc.
 */
package com.imsweb.mph;

import org.junit.Assert;
import org.junit.Test;

public class HematoDbUtilsTest {


    @Test
    public void testIsSamePrimary() {

        Assert.assertFalse(HematoDbUtils.isSamePrimary(null, null, 2010, 2010));
        Assert.assertFalse(HematoDbUtils.isSamePrimary("", "", 2010, 2010));
        Assert.assertFalse(HematoDbUtils.isSamePrimary("TEST", "TEST", 2010, 2010));
        Assert.assertTrue(HematoDbUtils.isSamePrimary("9861/3", "9861/3", 2010, 2010));
        Assert.assertTrue(HematoDbUtils.isSamePrimary("9861/3", "9861/3", 2010, 2000));

        Assert.assertTrue(HematoDbUtils.isSamePrimary("9870/3", "9590/3", 2016, 2011));
        Assert.assertTrue(HematoDbUtils.isSamePrimary("9590/3", "9870/3", 2009, 2016));
        Assert.assertFalse(HematoDbUtils.isSamePrimary("9590/3", "9870/3", 1990, 1998));

        Assert.assertFalse(HematoDbUtils.isSamePrimary("9870/3", "9805/3", 2016, 2016));
        Assert.assertFalse(HematoDbUtils.isSamePrimary("9870/3", "9805/3", 2010, 2010));
        Assert.assertTrue(HematoDbUtils.isSamePrimary("9870/3", "9805/3", 2001, 2001));
        Assert.assertFalse(HematoDbUtils.isSamePrimary("9671/3", "9823/3", 2018, 2009));

        Assert.assertTrue(HematoDbUtils.isSamePrimary("9738/3", "9590/3", 2019, 2022));
        Assert.assertFalse(HematoDbUtils.isSamePrimary("9738/3", "9590/3", 2021, 2022));
    }

    @Test
    public void testIsChronicToAcute() {

        Assert.assertFalse(HematoDbUtils.isChronicToAcuteTransformation(null, null, 2010, 2010));
        Assert.assertFalse(HematoDbUtils.isChronicToAcuteTransformation("", "", 2010, 2010));
        Assert.assertFalse(HematoDbUtils.isChronicToAcuteTransformation("TEST", "TEST", 2010, 2010));
        Assert.assertTrue(HematoDbUtils.isChronicToAcuteTransformation("9761/3", "9659/3", 2010, 2010));
        Assert.assertFalse(HematoDbUtils.isChronicToAcuteTransformation("9659/3", "9761/3", 2010, 2010));
        Assert.assertTrue(HematoDbUtils.isChronicToAcuteTransformation("9992/3", "9872/3", 2010, 2010));
        Assert.assertFalse(HematoDbUtils.isChronicToAcuteTransformation("9992/3", "9872/3", 2005, 2010));
        Assert.assertTrue(HematoDbUtils.isChronicToAcuteTransformation("9989/3", "9861/3", 2010, 2010));

        Assert.assertTrue(HematoDbUtils.isChronicToAcuteTransformation("9993/3", "9867/3", 2021, 2021));
        Assert.assertFalse(HematoDbUtils.isChronicToAcuteTransformation("9867/3", "9993/3", 2021, 2021));
        Assert.assertFalse(HematoDbUtils.isChronicToAcuteTransformation("9993/3", "9867/3", 2020, 2021));
    }

    @Test
    public void testIsAcuteToChronic() {

        Assert.assertFalse(HematoDbUtils.isAcuteToChronicTransformation(null, null, 2010, 2010));
        Assert.assertFalse(HematoDbUtils.isAcuteToChronicTransformation("", "", 2010, 2010));
        Assert.assertFalse(HematoDbUtils.isAcuteToChronicTransformation("TEST", "TEST", 2010, 2010));
        Assert.assertTrue(HematoDbUtils.isAcuteToChronicTransformation("9653/3", "9671/3", 2010, 2010));
        Assert.assertFalse(HematoDbUtils.isAcuteToChronicTransformation("9671/3", "9653/3", 2010, 2010));

        Assert.assertFalse(HematoDbUtils.isAcuteToChronicTransformation("9993/3", "9867/3", 2021, 2021));
        Assert.assertTrue(HematoDbUtils.isAcuteToChronicTransformation("9867/3", "9993/3", 2021, 2021));
        Assert.assertFalse(HematoDbUtils.isAcuteToChronicTransformation("9993/3", "9867/3", 2020, 2021));
    }
}
